package com.example.finalProject.Modules.Enums;

import javax.persistence.Table;

@Table(name = "estadoEnvio")
public enum EstadoEnvio {
    RECIBIDO, EN_RUTA, ENTREGADO;

    public static EstadoEnvio toEnum(String estado) {
        if (estado.equalsIgnoreCase("en ruta")) {
            return EstadoEnvio.EN_RUTA;
        }
        return EstadoEnvio.valueOf(estado.toUpperCase());
    }
}



