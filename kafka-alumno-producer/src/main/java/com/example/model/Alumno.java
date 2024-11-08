package com.example.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "alumnos")
public class Alumno {
	private static final long serialVersionUID = 2445247993956960711L;

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "dni", length = 10,nullable = false, unique = true)
	private String dni;

	@Column(name = "nombres", nullable = false, unique = true, length = 100)
	private String nombres;
	@OneToMany(mappedBy = "alumno",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Nota> notas;

}
