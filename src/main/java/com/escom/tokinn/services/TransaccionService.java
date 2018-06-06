package com.escom.tokinn.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.escom.tokinn.entity.Cuenta;
import com.escom.tokinn.entity.TipoTransaccion;
import com.escom.tokinn.entity.Transaccion;
import com.escom.tokinn.entity.Usuario;
import com.escom.tokinn.model.TransaccionModel;
import com.escom.tokinn.constantes.NavigationConstants;
import com.escom.tokinn.converter.TransaccionConverter;
import com.escom.tokinn.repository.TransaccionRepository;

@Service("transaccionService")
public class TransaccionService {
	@Autowired
	@Qualifier("transaccionRepository")
	private TransaccionRepository transaccionRepository;
	
	@Autowired
	@Qualifier("transaccionConverter")
	private TransaccionConverter transaccionConverter;
	
	@Autowired
	@Qualifier("tokenService")
	private TokenService tokenService;
	
	
	public List<TransaccionModel> findAll(){
		List<TransaccionModel> transacciones = new ArrayList<>();
		TransaccionModel transaccionModel;
		for(Transaccion transaccion : transaccionRepository.findAll()) {
			transaccionModel = new TransaccionModel();
			transaccionModel = transaccionConverter.entityToModel(transaccion);
			transacciones.add(transaccionModel);
		}
		return transacciones;
	}
	
	public List<TransaccionModel> findAllByIdCuenta(Long idCuenta){
		List<TransaccionModel> transacciones = new ArrayList<>();
		TransaccionModel transaccionModel;
		System.out.println("Hasta aqui va bien");
		System.out.println("IdCuenta: "+idCuenta);
		List<Transaccion> transaccionesRepository = transaccionRepository.findAllByIdCuenta(idCuenta);
		for(Transaccion transaccion : transaccionesRepository) { 
			System.out.println("Transaccion: "+transaccion.getDescripcion());
			transaccionModel = new TransaccionModel();
			transaccionModel = transaccionConverter.entityToModel(transaccion);
			transacciones.add(transaccionModel);
		}
		return transacciones;
	}
	
	public Transaccion edit(Transaccion model) {
		return transaccionRepository.save(model);
	}
	
	public Transaccion add(Transaccion entidad, Usuario usuario, Cuenta cuenta) {
		Transaccion respuesta = new Transaccion();
		entidad.setAmount(entidad.getCantidad()*entidad.getPrecioUnitario());
		entidad.setFechaTransaccion(new Date());
		
		TipoTransaccion tipoTransaccion = new TipoTransaccion();
		tipoTransaccion.setIdTipoTransaccion(1L);
		entidad.setTipoTransaccion(tipoTransaccion);
		
		entidad.setCuenta(usuario.getCuentas().get(0));
		System.out.println("usuarioTFB: "+usuario.getIdFacebook()+" hasToken: "+usuario.getHasToken());
		if(usuario.getHasToken() == Boolean.TRUE || usuario.getIdFacebook() != null) {
			System.out.println("token: "+entidad.getTokenAutorizacion());
			if(tokenService.verifyToken(usuario.getIdFacebook(),NavigationConstants.TOKEN_TRANSACCION, entidad.getTokenAutorizacion())) {
				respuesta = transaccionRepository.save(entidad);
			}
		}else {
			respuesta = transaccionRepository.save(entidad);
		}
		return respuesta;
	}
}
