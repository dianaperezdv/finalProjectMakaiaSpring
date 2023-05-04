package com.example.finalProject.Services;

import com.example.finalProject.DTO.EnvioCreadoDTO;
import com.example.finalProject.DTO.EnvioNuevoDTO;
import com.example.finalProject.DTO.EstadoEnvioDTO;
import com.example.finalProject.Exception.ApiRequestException;
import com.example.finalProject.Modules.Cliente;
import com.example.finalProject.Modules.Empleado;
import com.example.finalProject.Modules.Enums.EstadoEnvio;
import com.example.finalProject.Modules.Enums.TipoEmpleado;
import com.example.finalProject.Modules.Enums.TipoPaquete;
import com.example.finalProject.Modules.Envio;
import com.example.finalProject.Modules.Paquete;
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

    private final EnvioRepository envioRepository;
    private final EmpleadoRepository empleadoRepository;
    private final ClienteRepository clienteRepository;
    private final PaqueteRepository paqueteRepository;



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
                throw new ApiRequestException("La solicitud está incompleta, debe llenar todos los datos para poder generar un envío");
            }
            Optional<Cliente> cliente = this.clienteRepository.findById(envio.getCedula());
            if (cliente.isPresent()) {
                Paquete paquete1 = paqueteRepository.save(new Paquete(envio.getPeso(),envio.getValorDeclarado()));
                double valorEnvio = calcularValor(paquete1.getTipoDePaquete());
                Envio envio1 = new Envio(cliente.get(),envio.getCiudadOrigen(),envio.getCiudadDestino(),envio.getDireccionDestino(),envio.getNombreRecibe(), envio.getCelularRecibe(), LocalTime.now(),EstadoEnvio.RECIBIDO,valorEnvio,paquete1);
                this.envioRepository.save(envio1);
                return new EnvioCreadoDTO(envio1.getIdGuia(),envio1.getEstadoEnvio());
            }
            throw new ApiRequestException("El cliente con cédula " + envio.getCedula() + " debe de estar registrado para poder enviar un paquete");

    }

    public Envio obtenerEnvio(String idGuia) {
        Optional<Envio> envio = this.envioRepository.findById(idGuia);
        if (envio.isPresent()) {
            return envio.get();
        }
        throw new ApiRequestException("No existe un envío con número de guía " + idGuia + " en el sistema");
    }

    public List<Envio> obtenerEnviosPorCedula(Integer cedula ) {
        if(this.clienteRepository.findById(cedula).isEmpty()){
            throw new ApiRequestException("No existe un cliente con cédula " + cedula + " en nuestra compañía");
        }
        List<Envio> envios = this.envioRepository.findAllByClienteCedula(cedula);
        if (envios.isEmpty()) {
            throw new ApiRequestException("El cliente con cédula " + cedula + " no tiene envíos registrados en el sistema");
        }
        return envios;
    }

    public List<Envio> obtenerEnviosPorEstado(String estado, Integer cedulaEmpleado) {
        Optional<Empleado> empleado = this.empleadoRepository.findById(cedulaEmpleado);
        if(empleado.isPresent()){
            List<Envio> envios = this.envioRepository.findAllByEstadoEnvio(EstadoEnvio.valueOf(estado));
            if (envios.isEmpty()) {
                throw new ApiRequestException("No existen envíos en estado " + estado + " en el sistema");
            }
            return envios;
        }
        throw new ApiRequestException("No existe un empleado con cédula " + cedulaEmpleado + " en nuestra compañía");
        }

    public ResponseEntity eliminar(String idGuia) {
        Optional<Envio> envio = this.envioRepository.findById(idGuia);
        if(envio.isPresent()){
            this.envioRepository.deleteById(idGuia);
            return new ResponseEntity("El envio con número de guía " + idGuia + " se ha eliminado con éxito", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity("No existe una guía con esta identificación en el sistema", HttpStatus.BAD_REQUEST);
    }


    public EstadoEnvioDTO actualizarEstado(String idGuia, String estado, Integer cedulaEmpleado) {
        Envio envio = validacionesPrimariasActualizacion(idGuia,estado,cedulaEmpleado);
        EstadoEnvio estadoEnum = estadoAEnum(estado);
            if(envio.getEstadoEnvio()==EstadoEnvio.EN_RUTA && estadoEnum ==(EstadoEnvio.ENTREGADO)){
                envio.setEstadoEnvio(EstadoEnvio.ENTREGADO);
                Envio envioActualizado = this.envioRepository.save(envio);
                return new EstadoEnvioDTO(envioActualizado.getIdGuia(),envioActualizado.getEstadoEnvio());
            }
            envio.setEstadoEnvio(EstadoEnvio.EN_RUTA);
            Envio envioActualizado = this.envioRepository.save(envio);
            return new EstadoEnvioDTO(envioActualizado.getIdGuia(),envioActualizado.getEstadoEnvio());
        }


    public Envio validacionesPrimariasActualizacion(String idGuia, String estado, Integer cedulaEmpleado){
        validarConductor(cedulaEmpleado);
        Envio envio = obtenerEnvio(idGuia);
        validacionesCambio(estado, envio);
        validarEntregado(idGuia);
        return envio;
    }

    public void validarEntregado(String idGuia) {
        if(obtenerEnvio(idGuia).getEstadoEnvio() == EstadoEnvio.ENTREGADO){
            throw new ApiRequestException("El envío con número de guía " + idGuia + " ya fue entregado");
        }
    }
    public void validarConductor(Integer cedulaEmpleado){
        Optional<Empleado> empleado = this.empleadoRepository.findById(cedulaEmpleado);
        if(empleado.isPresent()){
            TipoEmpleado tipo = empleado.get().getTipoDeEmpleado();
            if(tipo == TipoEmpleado.CONDUCTOR){
                throw new ApiRequestException("El empleado con cédula " + cedulaEmpleado + " no tiene permisos para actualizar el estado de un envío");
            }
        }
        throw new ApiRequestException("El empleado con cédula " + cedulaEmpleado + " no existe en nuestra compañía");
    }

    public void validacionesCambio(String estado, Envio envio){
        if(estado.equalsIgnoreCase("RECIBIDO") ||
                (envio.getEstadoEnvio() == EstadoEnvio.RECIBIDO && estado.equalsIgnoreCase("ENTREGADO"))
                || (envio.getEstadoEnvio() == EstadoEnvio.EN_RUTA && estado.equalsIgnoreCase("EN RUTA"))){
            throw new ApiRequestException("El cambio de estado no cumple con las validaciones");
        }
    }

    public EstadoEnvio estadoAEnum(String estado){
        return EstadoEnvio.toEnum(estado);
    }

    public double calcularValor(TipoPaquete tipoPaquete){
            if(tipoPaquete == TipoPaquete.LIVIANO){
                return 30000;
            }
            else if (tipoPaquete == TipoPaquete.MEDIANO){
                return 40000;
            }
            return 50000;
    }

}
