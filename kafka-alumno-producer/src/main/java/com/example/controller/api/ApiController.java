package com.example.controller.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.AlumnoDTO;
import com.example.model.AlumnoPromedioDTO;
import com.example.service.MainService;

@RestController
public class ApiController {
	
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

	@Autowired
	private MainService service;
	

	@PostMapping(value="/api/v1/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveCliente(
			@RequestBody AlumnoDTO alumno){
		try {
			logger.info("se envío a procesar saveCliente");
			service.saveAlumno(alumno);
			logger.info("se envío a procesar correctamente");
			return new ResponseEntity<String>("", HttpStatus.OK);
		}catch(Exception e) {
			logger.error("Error: ", e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value="/api/v1/alumnos/{dni}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AlumnoPromedioDTO>> findByDni(@PathVariable("dni") String dni){
		try {
			ResponseEntity<List<AlumnoPromedioDTO>> rpta = new ResponseEntity<List<AlumnoPromedioDTO>>(service.findByDni(dni), HttpStatus.OK);
			logger.info("get alumnos");
			return rpta;
		}catch(Exception e) {
			logger.error("Error: ",e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
