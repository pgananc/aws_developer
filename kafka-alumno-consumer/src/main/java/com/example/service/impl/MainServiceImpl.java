package com.example.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.AlumnoDao;
import com.example.model.Alumno;
import com.example.model.AlumnoDTO;
import com.example.model.Nota;
import com.example.service.MainService;

@Service
@SuppressWarnings("deprecation")
public class MainServiceImpl implements MainService {


	@Autowired
	private AlumnoDao alumnoDao;


	@Override
	public List<AlumnoDTO> findByDni(String dni) {
		List<AlumnoDTO> lta = new ArrayList<>();
		this.alumnoDao.findByDni(dni).forEach(c -> {
			lta.add(AlumnoDTO.builder().dni(c.getDni()).nombres(c.getNombres())
					.notas(c.getNotas().stream().map(Nota::getNota).collect(Collectors.toList()))
					.promedio(c.getNotas().stream().collect(Collectors.averagingDouble(Nota::getNota))).build());
		});
		return lta;
	}



	@Override
	@Transactional("transactionManager")
	public void saveAlumno(List<AlumnoDTO> alumnos) throws InterruptedException, ExecutionException, TimeoutException {
		AlumnoDTO alumnoDTO = null;
		if (alumnos != null && !alumnos.isEmpty()) {
			alumnoDTO = alumnos.get(0);
		}
		Alumno alumno = Alumno.builder().dni(alumnoDTO.getDni()).nombres(alumnoDTO.getNombres()).build();
		alumno.setNotas(alumnoDTO.getNotas().stream()
				.map(nota -> Nota.builder().nota(nota).alumno(alumno).isActivo(true).build())
				.collect(Collectors.toList()));
		this.alumnoDao.save(alumno);
	
	}

}
