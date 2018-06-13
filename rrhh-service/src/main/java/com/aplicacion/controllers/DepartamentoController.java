package com.aplicacion.controllers;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aplicacion.classes.Departamento;
import com.aplicacion.repositories.DepartamentoRepository;
import com.aplicacion.repositories.ProyectosRepository;
import com.aplicacion.repositories.UserRepository;
import com.aplicacion.services.DepartamentoService;

@Controller("departmentController")
@RequestMapping(path = "/Departamento") // This means URL's start with /demo (after Application path)
public class DepartamentoController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DepartamentoRepository departamentoRepository;
	@Autowired
	private ProyectosRepository proyectosRepository;
	@Qualifier("departamentoservice")
	@Autowired
	DepartamentoService departamentoService;

	// AÑADIR
	@PostMapping(path = "/add") // Map ONLY GET Requests
	public @ResponseBody String addNewDepartment(@RequestBody Departamento dep) {

		departamentoRepository.save(dep);

		return "Saved";
	}

	@PostMapping("/eliminar")
	public @ResponseBody String deleteById(@RequestParam int id) {
		return departamentoService.borrarDepartamento(id);

	}

	@PostMapping("/update")
	public @ResponseBody String updateById(@RequestBody Departamento dep) {

		return departamentoService.actualizarDepartamento(dep);

	}

	@GetMapping("/all")
	public @ResponseBody Iterable<Departamento> all() {

		return departamentoRepository.findAll();

	}
}
