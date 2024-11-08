package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Alumno;

public interface AlumnoDao extends JpaRepository<Alumno, Long>{
	List<Alumno> findByDni(String dni);
}
