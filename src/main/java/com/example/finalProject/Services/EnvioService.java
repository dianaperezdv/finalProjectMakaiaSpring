package com.example.finalProject.Services;

import com.example.finalProject.DTO.EnvioCreadoDTO;
import com.example.finalProject.DTO.EnvioNuevoDTO;
import com.example.finalProject.DTO.EstadoEnvioDTO;
import com.example.finalProject.Exception.InvalidStatementException;
import com.example.finalProject.Modules.*;
import com.example.finalProject.Modules.Enums.EstadoEnvio;
import com.example.finalProject.Modules.Enums.TipoEmpleado;
import com.example.finalProject.Modules.Enums.TipoPaquete;
import com.example.finalProject.Repositories.ClienteRepository;
import com.example.finalProject.Repositories.EmpleadoRepository;
import com.example.finalProject.Repositories.EnvioRepository;
import com.example.finalProject.Repositories.PaqueteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class EnvioService {

    final private EnvioRepository envioRepository;
    final private EmpleadoRepository empleadoRepository;
    final private ClienteRepository clienteRepository;
    final private PaqueteRepository paqueteRepository;



    @Autowired
    public EnvioService(EnvioRepository envioRepository, EmpleadoRepository empleadoRepository , ClienteRepository clienteRepository, PaqueteRepository paqueteRepository) {
        this.envioRepository = envioRepository;
        this.empleadoRepository = empleadoRepository;
        this.clienteRepository = clienteRepository;
        this.paqueteRepository = paqueteRepository;
    }



    public List<Envio> obtenerEnvios() {
        return this.envioRepository.findAll();
    }

    public EnvioCreadoDTO crearEnvio(EnvioNuevoDTO envio)
    {
            if (envio.getCedula() == null || envio.getCiudadOrigen() == null || envio.getCiudadDestino() == null || envio.getDireccionDestino() == null
                    || envio.getNombreRecibe() == null || envio.getCelularRecibe() == null || envio.getValorDeclarado() < 0 || envio.getPeso() < 0) {
                throw new InvalidStatementException("La solicitud está incompleta, debe llenar todos los datos para poder generar un envío");
            }
            Optional<Cliente> cliente = this.clienteRepository.findById(envio.getCedula());
            if (cliente.isPresent()) {
                Paquete paquete1 = paqueteRepository.save(new Paquete(envio.getPeso(),envio.getValorDeclarado()));
                double valorEnvio = calcularValor(paquete1.getTipoDePaquete());
                Envio envio1 = new Envio(cliente.get(),envio.getCiudadOrigen(),envio.getCiudadDestino(),envio.getDireccionDestino(),envio.getNombreRecibe(), envio.getCelularRecibe(), LocalTime.now(),EstadoEnvio.RECIBIDO,valorEnvio,paquete1);
                this.envioRepository.save(envio1);
                return new EnvioCreadoDTO(envio1.getIdGuia(),envio1.getEstadoEnvio());
            }
            else {
                throw new InvalidStatementException("El cliente con cedula " + envio.getCedula() + " debe de estar registrado para poder enviar un paquete");
            }

    }

    public Envio obtenerEnvio(String idGuia) {
        Optional<Envio> envio = this.envioRepository.findById(idGuia);
        if (envio.isPresent()) {
            return envio.get();
        }
        else {
            throw new InvalidStatementException("No existe un envío con número de guía " + idGuia + " en el sistema");
        }
    }

    public List<Envio> obtenerEnviosPorCedula(Integer cedula ) {
        if(this.clienteRepository.findById(cedula).isEmpty()){
            throw new InvalidStatementException("No existe un cliente con cedula " + cedula + " en nuestra compañía");
        }
        else{
            List<Envio> envios = this.envioRepository.findAllByClienteCedula(cedula);
            if (envios.isEmpty()) {
                throw new InvalidStatementException("El cliente con " + cedula + " no tiene envíos registrados en el sistema");
            }
            else {
                return envios;
            }
        }
    }

    public List<Envio> obtenerEnviosPorEstado(String estado, Integer cedulaEmpleado) {
        Optional<Empleado> empleado = this.empleadoRepository.findById(cedulaEmpleado);
        if(empleado.isPresent()){
            List<Envio> envios = this.envioRepository.findAllByEstadoEnvio(EstadoEnvio.valueOf(estado));
            if (envios.isEmpty()) {
                throw new InvalidStatementException("No existen envíos en estado " + estado + " en el sistema");
            }
            else {
                return envios;
            }
        }
        else{
            throw new InvalidStatementException("No existe un empleado con cedula " + cedulaEmpleado + " en nuestra compañía");
        }
    }

    public ResponseEntity eliminar(String idGuia) {
        Optional<Envio> envio = this.envioRepository.findById(idGuia);
        if(envio.isPresent()){
            this.envioRepository.deleteById(idGuia);
            return new ResponseEntity("El envio con número de guía " + idGuia + " se ha eliminado con éxito", HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity("No existe una guía con esta identificación en el sistema", HttpStatus.BAD_REQUEST);
        }
    }
    public EstadoEnvioDTO actualizarEstado(String idGuia, String estado, Integer cedulaEmpleado) {
        Optional<Empleado> empleado = this.empleadoRepository.findById(cedulaEmpleado);
        if(empleado.isPresent()){
            TipoEmpleado tipo = empleado.get().getTipoDeEmpleado();
            if(tipo == TipoEmpleado.CONDUCTOR){
                throw new InvalidStatementException("El empleado con cedula " + cedulaEmpleado + " no tiene permisos para actualizar el estado de un envío");
            }
            else{
                Optional<Envio> envio = this.envioRepository.findById(idGuia);
                if (envio.isPresent()) {
                    if(estado.toUpperCase()== "RECIBIDO" ||
                            (envio.get().getEstadoEnvio() == EstadoEnvio.RECIBIDO && estado.equalsIgnoreCase("ENTREGADO"))
                            || (envio.get().getEstadoEnvio() == EstadoEnvio.ENRUTA && estado.equalsIgnoreCase("EN RUTA"))){
                        throw new InvalidStatementException("El cambio de estado no cumple con las validaciones");
                    }
                    else{
                        if(envio.get().getEstadoEnvio() == EstadoEnvio.ENTREGADO){
                            throw new InvalidStatementException("El envío con número de guía " + idGuia + " ya fue entregado");
                        }
                        else{
                            if(envio.get().getEstadoEnvio()==EstadoEnvio.ENRUTA && estado.equalsIgnoreCase("ENTREGADO")){
                                    envio.get().setEstadoEnvio(EstadoEnvio.ENTREGADO);
                                    this.envioRepository.save(envio.get());
                                    return new EstadoEnvioDTO(envio.get().getIdGuia(),envio.get().getEstadoEnvio());
                            }
                            else{
                                envio.get().setEstadoEnvio(EstadoEnvio.ENRUTA);
                                this.envioRepository.save(envio.get());
                                return new EstadoEnvioDTO(envio.get().getIdGuia(),envio.get().getEstadoEnvio());
                            }
                        }
                    }
                }
                else {

                    throw new InvalidStatementException("No existe un envío con número de guía " + idGuia + " en el sistema");
                }
            }
        }
        else{
            throw new InvalidStatementException("El empleado con cedula " + cedulaEmpleado + " no existe en nuestra compañía");
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
