package com.aplicacion.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aplicacion.classes.Departamento;
import com.aplicacion.repositories.DepartamentoRepository;

@Service("departamentoservice")
public class DepartamentoService {

	@Autowired
	DepartamentoRepository departamentoRepository;

	public String borrarDepartamento(int id) {

		Optional<Departamento> e = departamentoRepository.findById(id);
		if (e != null) {
			departamentoRepository.deleteById(id);
			return "se se elimina";
		}
		System.out.println("faefafaiufh");
		return null;
	}

	public String actualizarDepartamento(Departamento dep) {
		// Comprobación de si existe
		System.out.println(departamentoRepository.existsById(dep.getId()));
		if (departamentoRepository.existsById(dep.getId())) {
			System.out.println("Existe");
			departamentoRepository.save(dep);
			return "Actualizado";
		}

		return "No se ha podido actualizar";

	}
}
