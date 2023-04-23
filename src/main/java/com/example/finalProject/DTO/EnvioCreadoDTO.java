package com.example.finalProject.DTO;

import com.example.finalProject.Modules.Enums.EstadoEnvio;

public class EnvioCreadoDTO {
    private String idGuia;
    private EstadoEnvio estadoEnvio;


    public EnvioCreadoDTO(String idGuia, EstadoEnvio estadoEnvio){
        this.estadoEnvio = estadoEnvio;
        this.idGuia = idGuia;
    }
}
