package com.example.finalProject.Modules.Enums;

import javax.persistence.Table;

@Table(name = "estadoEnvio")
public enum EstadoEnvio {
    RECIBIDO, ENRUTA, ENTREGADO
}
