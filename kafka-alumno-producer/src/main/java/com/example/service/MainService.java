package com.example.service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import com.example.model.AlumnoDTO;
import com.example.model.AlumnoPromedioDTO;


public interface MainService {
	List<AlumnoPromedioDTO> findByDni(String dni);
	void saveAlumno(AlumnoDTO alumno) throws InterruptedException, ExecutionException, TimeoutException;
}
