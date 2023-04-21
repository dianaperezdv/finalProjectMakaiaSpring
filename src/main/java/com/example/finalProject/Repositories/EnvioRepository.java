package com.example.finalProject.Repositories;

import com.example.finalProject.Modules.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, String> {
}

