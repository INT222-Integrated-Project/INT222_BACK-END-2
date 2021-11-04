package swst.application.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ActionResponseModel {
	private String actionTitle;
	private boolean success;
}
