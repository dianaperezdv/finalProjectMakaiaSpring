package com.example.finalProject.DTO;

import com.example.finalProject.Modules.Enums.EstadoEnvio;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnvioCreadoDTO {
    private String idGuia;
    private EstadoEnvio estadoEnvio;


    public EnvioCreadoDTO(String idGuia, EstadoEnvio estadoEnvio){
        this.estadoEnvio = estadoEnvio;
        this.idGuia = idGuia;
    }
}
