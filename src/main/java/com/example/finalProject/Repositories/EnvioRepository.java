package com.example.finalProject.Repositories;

import com.example.finalProject.Modules.Enums.EstadoEnvio;
import com.example.finalProject.Modules.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, String> {

    List<Envio> findAllByClienteCedula(Integer cedula);


    List<Envio> findAllByEstadoEnvio(EstadoEnvio estado);

}

