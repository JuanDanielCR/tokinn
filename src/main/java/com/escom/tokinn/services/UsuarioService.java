package com.escom.tokinn.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.escom.tokinn.entity.Usuario;
import com.escom.tokinn.repository.UsuarioRepository;

@Service("usuarioService")
public class UsuarioService {
	@Autowired
	@Qualifier("usuarioRepository")
	private UsuarioRepository usuarioRepository;
	
	public List<Usuario> findUsuarios(){
		return usuarioRepository.findAll();
	}
}
