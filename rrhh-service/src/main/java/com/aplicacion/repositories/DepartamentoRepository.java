package com.aplicacion.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.aplicacion.classes.Departamento;

public interface DepartamentoRepository extends CrudRepository<Departamento, Integer> {
	// @Modifying
	@Query("SELECT id, nombredepartamento FROM Departamento")
	Iterable<Departamento> findDep();

}
