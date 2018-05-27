package com.escom.tokinn.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.escom.tokinn.entity.TipoTransaccion;

@Repository("tipoTransaccionRepository")
public interface TipoTransaccionRepository extends JpaRepository<TipoTransaccion, Serializable>{

}
