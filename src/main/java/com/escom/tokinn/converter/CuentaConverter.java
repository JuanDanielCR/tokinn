package com.escom.tokinn.converter;

import org.springframework.stereotype.Component;

import com.escom.tokinn.entity.Cuenta;
import com.escom.tokinn.model.CuentaModel;

@Component("cuentaConverter")
public class CuentaConverter {
	public CuentaModel entityToModel(Cuenta entidad) {
		CuentaModel model = new CuentaModel();
		model.setNumeroCuenta(entidad.getNumeroCuenta());
		model.setTipoCuenta(entidad.getTipoCuenta());
		model.setCvv(entidad.getCvv());
		model.setFechaVencimiento(entidad.getFechaVencimiento());
		return model;
	}
	
	public Cuenta modelToEntity(CuentaModel model) {
		Cuenta entidad = new Cuenta();
		entidad.setNumeroCuenta(model.getNumeroCuenta());
		entidad.setTipoCuenta(model.getTipoCuenta());
		entidad.setCvv(model.getCvv());
		entidad.setFechaVencimiento(model.getFechaVencimiento());
		entidad.setUsuario(model.getUsuario());
		return entidad;
	}
}
