package com.escom.tokinn.converter;

import org.springframework.stereotype.Component;

import com.escom.tokinn.entity.Transaccion;
import com.escom.tokinn.model.TransaccionModel;

@Component("transaccionConverter")
public class TransaccionConverter {
	public TransaccionModel entityToModel(Transaccion entidad) {
		TransaccionModel model = new TransaccionModel();
		model.setIdTransaccion(entidad.getIdTransaccion());
		model.setAmount(entidad.getAmount());
		model.setCantidad(entidad.getCantidad());
		model.setDescripcion(entidad.getDescripcion());
		model.setFechaTransaccion(entidad.getFechaTransaccion());
		model.setNombreItem(entidad.getNombreItem());
		model.setPrecioUnitario(entidad.getPrecioUnitario());
		model.setProducto(entidad.getProducto());
		model.setTokenAutorizacion(entidad.getTokenAutorizacion());
		return model;
	}
	
	public Transaccion modelToEntity(TransaccionModel model) {
		Transaccion entidad = new Transaccion();
		entidad.setIdTransaccion(model.getIdTransaccion());
		entidad.setAmount(model.getAmount());
		entidad.setCantidad(model.getCantidad());
		entidad.setDescripcion(model.getDescripcion());
		entidad.setFechaTransaccion(model.getFechaTransaccion());
		entidad.setNombreItem(model.getNombreItem());
		entidad.setPrecioUnitario(model.getPrecioUnitario());
		entidad.setProducto(model.getProducto());
		entidad.setTokenAutorizacion(model.getTokenAutorizacion());
		return entidad;
	}
}
