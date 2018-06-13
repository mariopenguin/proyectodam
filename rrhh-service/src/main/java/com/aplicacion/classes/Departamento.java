package com.aplicacion.classes;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Departamento {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String nombredepartamento;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "departamento")
	private Set<User> empleadoDeDepartamento;

	public Departamento(int id, String nombre) {

		this.id = id;
		this.nombredepartamento = nombre;
	}

	public Departamento() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDepartamento() {
		return nombredepartamento;
	}

	public void setDepartamento(String departamento) {
		this.nombredepartamento = departamento;
	}

	public String getNombredepartamento() {
		return nombredepartamento;
	}

	public void setNombredepartamento(String nombredepartamento) {
		this.nombredepartamento = nombredepartamento;
	}

	public Set<User> getUsuarios() {
		return empleadoDeDepartamento;
	}

	public void setUsuarios(Set<User> usuarios) {
		this.empleadoDeDepartamento = usuarios;
	}

	public Departamento(String nombredepartamento) {
		super();
		this.nombredepartamento = nombredepartamento;
	}

}
