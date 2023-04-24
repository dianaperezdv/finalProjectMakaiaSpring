package com.example.finalProject.DTO;

import com.example.finalProject.Modules.Enums.EstadoEnvio;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoEnvioDTO {

    private String idGuia;
    private EstadoEnvio ultimoEstado;


    public EstadoEnvioDTO(String idGuia, EstadoEnvio ultimoEstado){
        this.ultimoEstado = ultimoEstado;
        this.idGuia = idGuia;

    }
}
