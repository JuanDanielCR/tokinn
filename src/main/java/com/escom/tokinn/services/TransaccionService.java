package com.escom.tokinn.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.escom.tokinn.entity.Transaccion;
import com.escom.tokinn.model.TransaccionModel;
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
		System.out.println("IdTransaccion: "+model.getIdTransaccion());
		Transaccion transaccionActualizar = transaccionRepository.getOne(model.getIdTransaccion());
		System.out.println("IdTransaccionActuaizar: "+transaccionActualizar.getIdTransaccion());
		transaccionActualizar.setAmount(model.getAmount());
		transaccionActualizar.setCantidad(model.getCantidad());
		transaccionActualizar.setDescripcion(model.getDescripcion());
		transaccionActualizar.setFechaTransaccion(model.getFechaTransaccion());
		transaccionActualizar.setNombreItem(model.getNombreItem());
		transaccionActualizar.setPrecioUnitario(model.getPrecioUnitario());
		transaccionActualizar.setProducto(model.getProducto());
		transaccionActualizar.setTokenAutorizacion(model.getTokenAutorizacion());
		return transaccionRepository.save(transaccionActualizar);
	}
	
}
