package com.escom.tokinn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.escom.tokinn.repository.EstadoCuentaRepository;

@Service("estadoCuentaService")
public class EstadoCuentaService {
	@Autowired
	@Qualifier("estadoCuentaRepository")
	private EstadoCuentaRepository estadoCuentaRepository;
	
}
