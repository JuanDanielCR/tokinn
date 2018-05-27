package com.escom.tokinn.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.escom.tokinn.entity.Cuenta;

@Repository("cuentaRepository")
public interface CuentaRepository extends JpaRepository<Cuenta, Serializable>{

}
