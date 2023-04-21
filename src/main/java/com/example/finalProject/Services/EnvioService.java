package com.example.finalProject.Services;

import com.example.finalProject.DTO.EnvioDTO;
import com.example.finalProject.Exception.InvalidStatementException;
import com.example.finalProject.Modules.*;
import com.example.finalProject.Repositories.ClienteRepository;
import com.example.finalProject.Repositories.EnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnvioService {

    private EnvioRepository envioRepository;
    private ClienteRepository clienteRepository;
    private PaqueteService paqueteService;


    @Autowired
    public EnvioService(EnvioRepository envioRepository, ClienteRepository clienteRepository, PaqueteService paqueteService) {
        this.envioRepository = envioRepository;
        this.clienteRepository = clienteRepository;
        this.paqueteService = paqueteService;
    }



    public List<Envio> obtenerEnvios() {
        return this.envioRepository.findAll();
    }

    public EnvioDTO crearEnvio(EnvioDTO envio)
    {
            if (envio.getCedula() == null || envio.getCiudadOrigen() == null || envio.getCiudadDestino() == null || envio.getDireccionDestino() == null
                    || envio.getNombrePersonaQueRecibe() == null || envio.getCelularPersonaQueRecibe() == null || envio.getValorDeclarado() < 0 || envio.getPeso() < 0) {
                throw new InvalidStatementException("La solicitud está incompleta, debe llenar todos los datos para poder generar un envío");
            }
            Optional<Cliente> cliente = this.clienteRepository.findById(envio.getCedula());
            if (cliente.isPresent()) {
                Paquete paquete1 = paqueteService.crearPaquete(envio.getPeso(),envio.getValorDeclarado());
                double valorEnvio = calcularValor(paquete1.getTipoDePaquete());
                Envio envio1 = new Envio(cliente.get(),envio.getCiudadOrigen(),envio.getCiudadDestino(),envio.getDireccionDestino(),envio.getNombrePersonaQueRecibe(), envio.getCelularPersonaQueRecibe(), LocalTime.now(),"RECIBIDO",valorEnvio,paquete1);
                this.envioRepository.save(envio1);
                return new EnvioDTO(envio1.getIdGuia(),envio1.getEstadoEnvio());
            }
            else {
                throw new InvalidStatementException("El cliente con cedula " + envio.getCedula() + " debe de estar registrado para poder enviar un paquete");
            }

    }


    public double calcularValor(TipoPaquete tipoPaquete){
            if(tipoPaquete == TipoPaquete.LIVIANO){
                return 30000;
            }
            else if (tipoPaquete == TipoPaquete.MEDIANO){
                return 40000;
            }
            else{
                return 50000;
            }
    }

}
