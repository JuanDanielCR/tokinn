package com.escom.tokinn.converter;

import org.springframework.stereotype.Component;

import com.escom.tokinn.entity.EstadoCuenta;
import com.escom.tokinn.model.EstadoCuentaModel;

@Component("estadoCuentaConverter")
public class EstadoCuentaConverter {
	public EstadoCuentaModel entityToModel(EstadoCuenta entidad) {
		EstadoCuentaModel model = new EstadoCuentaModel();
		model.setIdEstadoCuenta(entidad.getIdEstadoCuenta());
		model.setFechaExpedicion(entidad.getFechaExpedicion());
		model.setFechaInicio(entidad.getFechaInicio());
		model.setFechaFin(entidad.getFechaFin());
		model.setFirma(entidad.getFirma());
		model.setKey(entidad.getKey());
		model.setTokenAutorizacion(entidad.getTokenAutorizacion());
		model.setCuentaEstado(entidad.getCuentaEstado());
		return model;
	}
	
	public EstadoCuenta modelToEntity(EstadoCuentaModel model) {
		EstadoCuenta entidad = new EstadoCuenta();
		entidad.setIdEstadoCuenta(model.getIdEstadoCuenta());
		entidad.setFechaExpedicion(model.getFechaExpedicion());
		entidad.setFechaInicio(model.getFechaInicio());
		entidad.setFechaFin(model.getFechaFin());
		entidad.setFirma(model.getFirma());
		entidad.setKey(model.getKey());
		entidad.setTokenAutorizacion(model.getTokenAutorizacion());
		entidad.setCuentaEstado(model.getCuentaEstado());
		return entidad;
	}
}
