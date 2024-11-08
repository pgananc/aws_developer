package com.example.service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import com.example.model.AlumnoDTO;

public interface MainService {
	List<AlumnoDTO> findByDni(String dni);

	void saveAlumno(List<AlumnoDTO> alumnos) throws InterruptedException, ExecutionException, TimeoutException;
}
