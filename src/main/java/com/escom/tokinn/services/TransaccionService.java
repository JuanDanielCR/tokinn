package com.escom.tokinn.services;

import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.escom.tokinn.component.HMAC;
import com.escom.tokinn.entity.Transaccion;
import com.escom.tokinn.repository.TransaccionRepository;

@Service("transaccionService")
public class TransaccionService {
	@Autowired
	@Qualifier("transaccionRepository")
	private TransaccionRepository transaccionRepository;
	
	@Autowired
	@Qualifier("hmacService")
	private HMACService hmacService;
	
	public List<Transaccion> findTransacciones(){
		return transaccionRepository.findAll();
	}
	
	public OutputStream firmarEstadoDeCuenta(String passwordUsuario, byte[] bytesArchivo) {
		try {
			HMAC firma = hmacService.signFile(passwordUsuario, "", bytesArchivo);
			System.out.println("firma: "+firma.sign);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
