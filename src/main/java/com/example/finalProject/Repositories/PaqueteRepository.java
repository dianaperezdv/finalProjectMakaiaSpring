package com.example.finalProject.Repositories;

import com.example.finalProject.Modules.Paquete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaqueteRepository extends JpaRepository<Paquete,Integer> {
}
