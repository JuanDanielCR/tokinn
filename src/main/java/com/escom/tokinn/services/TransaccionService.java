package com.escom.tokinn.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.escom.tokinn.entity.Transaccion;
import com.escom.tokinn.repository.TransaccionRepository;

@Service("transaccionService")
public class TransaccionService {
	@Autowired
	@Qualifier("transaccionRepository")
	private TransaccionRepository transaccionRepository;
	
	public List<Transaccion> findTransacciones(){
		return transaccionRepository.findAll();
	}
	
	public List<Transaccion> findAll(){
		return transaccionRepository.findAll();
	}
	
}
