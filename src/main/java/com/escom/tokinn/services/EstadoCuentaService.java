package com.escom.tokinn.services;

import java.io.OutputStream;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.escom.tokinn.component.HMAC;
import com.escom.tokinn.entity.EstadoCuenta;
import com.escom.tokinn.entity.Usuario;
import com.escom.tokinn.repository.EstadoCuentaRepository;

@Service("estadoCuentaService")
public class EstadoCuentaService {
	@Autowired
	@Qualifier("estadoCuentaRepository")
	private EstadoCuentaRepository estadoCuentaRepository;
	
	
	@Autowired
	@Qualifier("hmacService")
	private HMACService hmacService;
	
	public OutputStream firmarEstadoDeCuenta(Usuario usuario, byte[] bytesArchivo) {
		EstadoCuenta entidad = new EstadoCuenta();
		entidad.setCuentaEstado(usuario.getCuentas().get(0));
		entidad.setFechaExpedicion(new Date());
		//TODO Cambiar fechas inicio y fin
		entidad.setFechaInicio(new Date());
		entidad.setFechaFin(new Date());
		try {
			HMAC firma = hmacService.signFile("$2a$10$Ly2lrYkyz", "", bytesArchivo);
			//HMAC firma = hmacService.signFile(usuario.getNombre().substring(0, 16), bytesArchivo);			
			System.out.println("firma: "+firma.sign);
			entidad.setFirma(firma.sign);
			entidad.setKey(firma.key);	
		} catch (Throwable e) {
			e.printStackTrace();
		}
		estadoCuentaRepository.save(entidad);
		return null;
	}
}
