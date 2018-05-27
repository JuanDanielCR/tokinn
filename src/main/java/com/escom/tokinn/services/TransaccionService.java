package com.escom.tokinn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.escom.tokinn.repository.TransaccionRepository;

@Service("transaccionService")
public class TransaccionService {
	@Autowired
	@Qualifier("transaccionRepository")
	private TransaccionRepository transaccionRepository;
}
