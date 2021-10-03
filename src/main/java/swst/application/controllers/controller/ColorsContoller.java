package swst.application.controllers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import swst.application.entities.Colors;
import swst.application.repositories.ColorsRepository;

@Service
public class ColorsContoller {

	@Autowired
	private ColorsRepository colorsRepository;

}
