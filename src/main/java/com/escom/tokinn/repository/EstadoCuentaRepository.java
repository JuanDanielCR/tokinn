package com.escom.tokinn.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.escom.tokinn.entity.EstadoCuenta;

@Repository("estadoCuentaRepository")
public interface EstadoCuentaRepository extends JpaRepository<EstadoCuenta, Serializable>{
	
	@Query("SELECT ec FROM EstadoCuenta ec WHERE ec.cuentaEstado.idCuenta = ?1")
	List<EstadoCuenta> findAllByIdCuenta(Long idCuenta);

}
