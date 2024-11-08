package com.example.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoPromedioDTO implements Serializable {

	private static final long serialVersionUID = 2445247993956960711L;

	private String dni;
	private String nombres;
	private List<Double> notas;
	private Double promedio;
	
}
