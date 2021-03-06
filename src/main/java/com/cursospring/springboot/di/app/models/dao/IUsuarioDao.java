package com.cursospring.springboot.di.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.cursospring.springboot.di.app.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{

	
	public Usuario findByUsername(String username);
	
}
