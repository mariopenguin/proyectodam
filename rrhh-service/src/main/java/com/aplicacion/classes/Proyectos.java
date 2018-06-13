package com.aplicacion.classes;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Proyectos {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String nombreProyecto;

	@JsonIgnore
	@ManyToMany(mappedBy = "proyectosSet")
	private Set<User> Setusuarios;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Proyectos_id")
	Set<User_Proyecto> Proyectos_id;

	public int getId() {
		return id;
	}

	public Proyectos(String nombreProyecto) {
		super();
		this.nombreProyecto = nombreProyecto;
	}

	public Proyectos() {
		super();
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombreProyecto() {
		return nombreProyecto;
	}

	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}

	public Set<User> getSetusuarios() {
		return Setusuarios;
	}

	public void setSetusuarios(Set<User> setusuarios) {
		Setusuarios = setusuarios;
	}

}
