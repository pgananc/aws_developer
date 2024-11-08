package com.example.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.AlumnoDao;
import com.example.model.AlumnoDTO;
import com.example.model.AlumnoPromedioDTO;
import com.example.model.Nota;
import com.example.service.MainService;

@Service
public class MainServiceImpl implements MainService {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Value("${app.topic.alumnos}")
	private String topicAlumnos;
	
	@Autowired
	private AlumnoDao alumnoDao;

	@Override
	@Transactional("kafkaTransactionManager")
	public void saveAlumno(AlumnoDTO alumno) throws InterruptedException, ExecutionException, TimeoutException {
		List<AlumnoDTO> listAlumno = new ArrayList<>();
		listAlumno.add(alumno);
		sendAlumnoToTopic(listAlumno);
	}

	@Transactional("kafkaTransactionManager")
	private void sendAlumnoToTopic(List<AlumnoDTO> alumnos)
			throws InterruptedException, ExecutionException, TimeoutException {
		if (!alumnos.isEmpty()) {
			kafkaTemplate.send(topicAlumnos, null, alumnos).get(1, TimeUnit.SECONDS);
		}
	}
	
	@Override
	public List<AlumnoPromedioDTO> findByDni(String dni) {
		List<AlumnoPromedioDTO> lta = new ArrayList<>();
		this.alumnoDao.findByDni(dni).forEach(c -> {
			lta.add(AlumnoPromedioDTO.builder().dni(c.getDni()).nombres(c.getNombres())
					.notas(c.getNotas().stream().map(Nota::getNota).collect(Collectors.toList()))
					.promedio(c.getNotas().stream().collect(Collectors.averagingDouble(Nota::getNota))).build());
		});
		return lta;
	}

}
