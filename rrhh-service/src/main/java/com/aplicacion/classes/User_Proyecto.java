package com.aplicacion.classes;

import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity // This tells Hibernate to make a table out of this class
public class User_Proyecto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	@JoinColumn(name = "User_id")
	private User User_id;

	@ManyToOne
	@JoinColumn(name = "Proyectos_id")
	private Proyectos Proyectos_id;

	private Date date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser_id() {
		return User_id;
	}

	public Proyectos getProyectos_id() {
		return Proyectos_id;
	}

	public void setProyectos_id(Proyectos proyectos_id) {
		Proyectos_id = proyectos_id;
	}

	public void setUser_id(User user_id) {
		User_id = user_id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
