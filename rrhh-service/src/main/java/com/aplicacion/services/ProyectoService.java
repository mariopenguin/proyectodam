package com.aplicacion.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aplicacion.classes.Proyectos;
import com.aplicacion.repositories.ProyectosRepository;

@Service("proyectosServices")
public class ProyectoService {
	@Autowired
	ProyectosRepository proyectosRepository;

	public String borrarProyecto(long id) {

		Optional<Proyectos> e = proyectosRepository.findById(id);
		if (e != null) {
			proyectosRepository.deleteById(id);
			return "se se elimina";
		}
		return null;
	}

	public String actualizarProyecto(Proyectos pro) {

		if (proyectosRepository.existsById((long) pro.getId())) {
			System.out.println("Existe");
			proyectosRepository.save(pro);
			return "Actualizado";
		}

		return "No se ha podido actualizar";

	}
}