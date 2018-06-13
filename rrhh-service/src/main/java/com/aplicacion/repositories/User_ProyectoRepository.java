package com.aplicacion.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.aplicacion.classes.Departamento;
import com.aplicacion.classes.User_Proyecto;

public interface User_ProyectoRepository extends CrudRepository<User_Proyecto, Integer> {
	// @Modifying
	@Query("SELECT id, nombredepartamento FROM Departamento")
	Iterable<Departamento> findDep();

}
