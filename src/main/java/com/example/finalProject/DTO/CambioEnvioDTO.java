package com.example.finalProject.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CambioEnvioDTO {
        private String estadoEnvio;
        private Integer cedulaEmpleado;


    public CambioEnvioDTO(String idGuia, Integer cedulaEmpleado){
            this.estadoEnvio = estadoEnvio;
            this.cedulaEmpleado = cedulaEmpleado;
        }

}