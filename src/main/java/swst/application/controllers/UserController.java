package swst.application.controllers;

import java.time.LocalDate;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import swst.application.authenSecurity.TokenUtills;
import swst.application.entities.Roles;
import swst.application.entities.UsernamesModels;
import swst.application.entities.seperated.UserNameModelEdit;
import swst.application.errorsHandlers.ExceptionFoundation;
import swst.application.errorsHandlers.ExceptionresponsesModel;
import swst.application.errorsHandlers.ExceptionresponsesModel.EXCEPTION_CODES;
import swst.application.models.ActionResponseModel;
import swst.application.models.CreateNewUserModel;
import swst.application.models.GetUserModel;
import swst.application.models.LoginModel;
import swst.application.models.LoginResponseModel;
import swst.application.repositories.RolesRepository;
import swst.application.repositories.UsernameRepository;
import swst.application.services.FileStorageService;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserController {

	@Autowired
	private final UsernameRepository usernameRepository;
	@Autowired
	private final RolesRepository rolesRepository;
	@Autowired
	private final FileStorageService fileStorageService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${application.pagerequest.maxsize.users}")
	private int maxsizeUsers;

	@Value("${application.pagerequest.defaultsize.users}")
	private int defaultSizeUsers;

	// [ register ]
	public UsernamesModels register(CreateNewUserModel newuser) {

		if (!Pattern.matches("[a-zA-Z0-9_]+", newuser.getUserName())) {
			throw new ExceptionFoundation(EXCEPTION_CODES.AUTHEN_ILLEGAL_CHAR,
					"[ ILLEGAL USER NAME ] Username can only contain A-Z or digits.");
		}
		if (usernameRepository.existsByUserNameIgnoreCase(newuser.getUserName())) {
			throw new ExceptionFoundation(ExceptionresponsesModel.EXCEPTION_CODES.AUTHEN_USERNAME_ALREADY_EXISTED,
					"[ EXISTED ] This username is used.");
		}
		if (usernameRepository.existsByPhoneNumber(newuser.getPhone())) {
			throw new ExceptionFoundation(EXCEPTION_CODES.AUTHEN_PHONE_NUMBER_ALREADY_EXISTED,
					"[ EXISTED ] This phone number is occupied.");
		}

		UsernamesModels newModel = new UsernamesModels();
		newModel.setUserName(newuser.getUserName());
		newModel.setUserPassword(passwordEncoder.encode(newuser.getUserPassword()));
		newModel.setFirstName(newuser.getFirstName());
		newModel.setLastName(newuser.getLastName());
		newModel.setPhoneNumber(newuser.getPhone());
		newModel.setRole(rolesRepository.findByroleName("customer"));

		usernameRepository.save(newModel);
		return newModel;

	}

	// [ authenUser ]
	public LoginResponseModel authenUser(LoginModel loginUser, HttpServletResponse response) {
		UsernamesModels requestUser = usernameRepository.findByUserName(loginUser.getUserName());
		if (requestUser == null
				|| !passwordEncoder.matches(loginUser.getUserPassword(), requestUser.getUserPassword())) {
			throw new ExceptionFoundation(EXCEPTION_CODES.AUTHEN_BAD_CREDENTIALS,
					"[ AUTHEN FAILED ] Username or password doesn't match.");
		}
		if (requestUser.getRole() == rolesRepository.findByroleName("suspended")) {
			throw new ExceptionFoundation(EXCEPTION_CODES.AUTHEN_NOT_ALLOWED, "[ SUS ] This accound is suspended.");
		}

		String[] roles = { "" };
		Roles getUserRoles = requestUser.getRole();
		roles[0] = getUserRoles.getRoleName();

		String token = TokenUtills.createToken(requestUser.getUserName(), roles);
		response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		LoginResponseModel loginResponse = new LoginResponseModel(loginUser.getUserName().toLowerCase(), token,
				getUserRoles);
		return loginResponse;
	}

	// [ listUserByPage ]
	public Page<UsernamesModels> listUserByPage(int page, int size, String searchContent) {
		if (page < 0) {
			page = 0;
		}
		if (size < 1 || size > maxsizeUsers) {
			size = defaultSizeUsers;
		}
		Pageable sendPageRequest = PageRequest.of(page, size);
		Page<UsernamesModels> result;

		result = usernameRepository.findByUserNameContainingOrEmailContaining(searchContent, sendPageRequest);
		if (result.getTotalPages() < page + 1) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND, "[ NOT FOUND ] Nothing here. :(");
		}
		return result;
	}

	// [ listUserByPhone ]
	public Page<UsernamesModels> listUserByPhone(String phoneNumber) {
		Pageable sendPageRequest = PageRequest.of(0, 20);
		Page<UsernamesModels> result = usernameRepository.findByPhoneNumber(phoneNumber, sendPageRequest);
		if (result.getTotalElements() < 1) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND, "[ NOT FOUND ] Nothing here. :(");
		}
		return result;
	}

	// [ assignRole ]
	public ActionResponseModel assignRole(int userNameID, int roleID) {
		UsernamesModels assignTo = usernameRepository.findById(userNameID)
				.orElseThrow(() -> new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND,
						"[ NOT EXIST] This user is not exist in our database."));
		if (assignTo == null) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND,
					"[ NOT EXIST] This user is not exist in our database.");
		}
		assignTo.setRole(rolesRepository.findById(roleID)
				.orElseThrow(() -> new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND,
						"[ NOT EXIST] This user is not exist in our database.")));
		usernameRepository.save(assignTo);
		return new ActionResponseModel("[ ROLE CHANGED ] change role for " + assignTo.getUserName() + " to "
				+ assignTo.getRole().getRoleName(), true);
	}

	// [ editUser ]
	public UsernamesModels editUser(UsernamesModels newUserInfo, MultipartFile imageFile, HttpServletRequest request) {
		UsernamesModels currentUser = usernameRepository.findByUserName(TokenUtills.getUserNameFromToken(request));
		if (currentUser == null) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND,
					"[ NOT FOUND ] The user with this name is not exist");
		}

		String profileImage = (newUserInfo.getProfileImage() == "" ? currentUser.getProfileImage() : newUserInfo.getProfileImage());
		String address = (newUserInfo.getAddress() == "" ? currentUser.getAddress() : newUserInfo.getAddress());
		String firstName = (newUserInfo.getFirstName() == "" ? currentUser.getFirstName() : newUserInfo.getFirstName());
		String lastName = (newUserInfo.getLastName() == "" ? currentUser.getLastName() : newUserInfo.getLastName());
		String email = (newUserInfo.getEmail() == "" ? currentUser.getEmail() : newUserInfo.getEmail());
		String phone = (newUserInfo.getPhoneNumber() == "" ? currentUser.getPhoneNumber() : newUserInfo.getPhoneNumber());

		currentUser.setProfileImage(profileImage);
		currentUser.setAddress(address);
		currentUser.setFirstName(firstName);
		currentUser.setLastName(lastName);
		currentUser.setEmail(email);
		currentUser.setPhoneNumber(phone);

		if (newUserInfo.getEmail() != "") {
			if (usernameRepository.existsByEmailIgnoreCase(email)) {
				throw new ExceptionFoundation(EXCEPTION_CODES.AUTHEN_EMAIL_ALREADY_EXIST, "[ EMAIL TAKEN ] This email is taken bu another user.");
			}
		}
		if (newUserInfo.getPhoneNumber() != "") {
			if (usernameRepository.existsByPhoneNumber(phone)) {
				throw new ExceptionFoundation(EXCEPTION_CODES.AUTHEN_PHONE_NUMBER_ALREADY_EXISTED, "[ PHONE TAKEN ] This phone number is taken by another user.");
			}
		}
		
		currentUser = usernameRepository.save(currentUser);
		
		if(imageFile != null) {
			fileStorageService.deleteImage(currentUser.getProfileImage(), "profiles");
			currentUser.setProfileImage(fileStorageService.saveImage(imageFile, "profiles"));
		}

		currentUser = usernameRepository.save(currentUser);
		return currentUser;
	}

	// [ changePassword ]
	public ActionResponseModel changePassword(String oldPassword, String newPassword, HttpServletRequest request) {
		UsernamesModels currentUser = usernameRepository.findByUserName(TokenUtills.getUserNameFromToken(request));
		if (currentUser == null) {
			throw new ExceptionFoundation(EXCEPTION_CODES.SEARCH_NOT_FOUND,
					"[ NOT FOUND ] The user with this name is not exist");
		}
		if (!passwordEncoder.encode(oldPassword).equals(currentUser.getUserPassword())) {
			throw new ExceptionFoundation(EXCEPTION_CODES.AUTHEN_BAD_CREDENTIALS,
					"[ BAD CREDENTIALS ] Wrond old password");
		}
		currentUser.setUserPassword(passwordEncoder.encode(newPassword));
		usernameRepository.save(currentUser);
		return new ActionResponseModel("Password Changed.", true);
	}

	// [ deleteUserAccount ]
	public ActionResponseModel deleteUserAccount(String userName, HttpServletRequest request) {
		String currentName;
		if (userName == null) {
			currentName = TokenUtills.getUserNameFromToken(request);
		} else {
			currentName = userName;
		}

		UsernamesModels currentUser = usernameRepository.findByUserName(currentName);

		if (!currentUser.getUserName().equalsIgnoreCase(TokenUtills.getUserNameFromToken(request))) {
			if (!usernameRepository.findByUserName(currentName).getRole().getRoleName().equalsIgnoreCase("admin")) {
				throw new ExceptionFoundation(EXCEPTION_CODES.AUTHEN_NOT_ALLOWED,
						"[ NOPE ] This account is not belong to you.");
			}
		}

		if (currentUser.getRole().getRoleName().equals("admin")) {
			throw new ExceptionFoundation(EXCEPTION_CODES.AUTHEN_NOT_ALLOWED,
					"[ NOPE ] Admins are not permitted to resign.");
		}

		int alLength = 10;
		int aal = 65;
		int zal = 90;

		Random randomString = new Random();
		StringBuilder buffer = new StringBuilder(alLength);

		for (int i = 0; i < alLength; i++) {
			int ramdomLimitedInt = aal + (int) (randomString.nextFloat() * (zal - aal + 1));
			buffer.append((char) ramdomLimitedInt);
		}

		currentUser.setAddress("[ DELETED ACCOUNT ]");
		currentUser.setEmail(null);
		currentUser.setFirstName("[ DELETED ACCOUNT ]");
		currentUser.setLastName("[ DELETED ACCOUNT ]");
		currentUser.setPhoneNumber(buffer.toString());
		currentUser.setProfileImage("profile-default");
		currentUser.setRole(rolesRepository.findByroleName("suspended"));

		usernameRepository.save(currentUser);

		return new ActionResponseModel("[ DELETED ] Your account has been deleted.", true);
	}

}
