package com.films.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "film")
public class Film {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String titulo;
	
	@Enumerated(EnumType.STRING)
	private Genero genero;
	
	private String director;
	
	@Column(name = "fecha_estreno")
	private LocalDate fechaEstreno;
	
	private int duracion;
	private double puntuacion;
	private String comentarios;
}
