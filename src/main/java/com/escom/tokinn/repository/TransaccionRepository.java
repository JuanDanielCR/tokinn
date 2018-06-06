package com.escom.tokinn.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.escom.tokinn.entity.Transaccion;

@Repository("transaccionRepository")
public interface TransaccionRepository extends JpaRepository<Transaccion, Serializable>{
	
	@Query("SELECT t FROM Transaccion t WHERE t.cuenta.idCuenta = ?1")
	List<Transaccion> findAllByIdCuenta(Long idCuenta);
}
