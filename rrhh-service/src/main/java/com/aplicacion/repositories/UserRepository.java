package com.aplicacion.repositories;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.aplicacion.classes.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@CrossOrigin(origins = "http://localhost:4200")
public interface UserRepository extends CrudRepository<User, Long> {

	@Query
	User findByName(String name);

	@Query
	User findById(int id);

	@Modifying
	@Query("SELECT name,email,departamento.id,departamento.nombredepartamento FROM User")
	Iterable<User> find();

}
