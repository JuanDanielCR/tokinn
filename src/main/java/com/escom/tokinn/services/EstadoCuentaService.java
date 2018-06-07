package com.escom.tokinn.services;

import java.io.OutputStream;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.escom.tokinn.component.HMAC;
import com.escom.tokinn.converter.EstadoCuentaConverter;
import com.escom.tokinn.entity.EstadoCuenta;
import com.escom.tokinn.entity.Usuario;
import com.escom.tokinn.model.EstadoCuentaModel;
import com.escom.tokinn.repository.EstadoCuentaRepository;

@Service("estadoCuentaService")
public class EstadoCuentaService {
	@Autowired
	@Qualifier("estadoCuentaRepository")
	private EstadoCuentaRepository estadoCuentaRepository;
	
	@Autowired
	@Qualifier("tokenService")
	private TokenService tokenService;
	
	@Autowired
	@Qualifier("hmacService")
	private HMACService hmacService;
	
	@Autowired
	@Qualifier("rsaService")
	private RSAService rsaService;  
	
	@Autowired
	@Qualifier("estadoCuentaConverter")
	private EstadoCuentaConverter estadoCuentaConverter;
	
	public OutputStream firmarEstadoDeCuenta(Usuario usuario, byte[] bytesArchivo) {
		EstadoCuenta entidad = new EstadoCuenta();
		entidad.setCuentaEstado(usuario.getCuentas().get(0));
		entidad.setFechaExpedicion(new Date());
		//TODO Cambiar fechas inicio y fin
		entidad.setFechaInicio(new Date());
		entidad.setFechaFin(new Date());
		try {
			
			HMAC firma = hmacService.signFile(tokenService.getHash(usuario.getPassword()).substring(0, 16), bytesArchivo);
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
	
	public String rsear(byte[]elarchivo) throws NoSuchAlgorithmException {
	    KeyPair keypair = rsaService.buildKeyPair();
	    PublicKey publicKey = keypair.getPublic();
	    PrivateKey privateKey = keypair.getPrivate();
	    
	    try {
			rsaService.RSAencrypt(privateKey, elarchivo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return Base64.getEncoder().encodeToString(privateKey.getEncoded());
	    
	}
	
	public List<EstadoCuentaModel> findByIdCuenta(Long idCuenta) {
		List<EstadoCuenta> estadoCuentaList = estadoCuentaRepository.findAllByIdCuenta(idCuenta);
		List<EstadoCuentaModel> estadoCuentaModelList = new ArrayList<>();
		EstadoCuentaModel estadoCuentaModel;
		for(EstadoCuenta estadoCuenta : estadoCuentaList) {
			estadoCuentaModel = new EstadoCuentaModel();
			estadoCuentaModel = estadoCuentaConverter.entityToModel(estadoCuenta);
			estadoCuentaModelList.add(estadoCuentaModel);
		}
		return estadoCuentaModelList;
	}
}
