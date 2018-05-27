package com.escom.tokinn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.escom.tokinn.repository.CuentaRepository;

@Service("cuentaService")
public class CuentaService {
	@Autowired
	@Qualifier("cuentaRepository")
	private CuentaRepository cuentaRepository; 
}
