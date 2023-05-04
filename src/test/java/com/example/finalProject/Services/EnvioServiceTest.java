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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;



class EnvioServiceTest {

    EnvioService envioService;
    EnvioRepository envioRepository;
    EmpleadoRepository empleadoRepository;
    ClienteRepository  clienteRepository;
    PaqueteRepository paqueteRepository;

    @BeforeEach
    public void setUp(){
        this.clienteRepository = mock(ClienteRepository.class);
        this.empleadoRepository = mock(EmpleadoRepository.class);
        this.paqueteRepository = mock(PaqueteRepository.class);
        this.envioRepository = mock(EnvioRepository.class);
        this.envioService = new EnvioService(envioRepository, empleadoRepository, clienteRepository, paqueteRepository);
    }

    @Test
    void obtenerEnvios() {
        //Arrange
        Cliente cliente1 = new Cliente();
        cliente1.setCedula(1234567890);
        cliente1.setCorreoElectronico("cliente1@hotmail.com");
        Paquete paquete1 = new Paquete(2,2000);
        Paquete paquete2 = new Paquete(3,3000);
        Envio envio1 = new Envio(cliente1,"Medellín","Bogotá","Cra 17 #20-10","Laura","342352", LocalTime.now(), EstadoEnvio.RECIBIDO,10000,paquete1);
        Envio envio2 = new Envio(cliente1,"Medellín","Bogotá","Cra 17 #20-10","Laura","342352", LocalTime.now(), EstadoEnvio.RECIBIDO,20000,paquete2);
        List<Envio> enviosList = Arrays.asList(envio1, envio2);
        when(this.envioRepository.findAll()).thenReturn(enviosList);
        //Act
        List<Envio> resultado = this.envioService.obtenerEnvios();
        //Assert
        assertEquals(2, resultado.size());
       }

    @Test
    void crearEnvio(){
        EnvioNuevoDTO envioDto = new EnvioNuevoDTO();
        envioDto.setCedula(123456789);
        envioDto.setCiudadOrigen("Pereira");
        envioDto.setCiudadDestino("Medellin");
        envioDto.setDireccionDestino("Calle 32 # 14-3");
        envioDto.setNombreRecibe("Maria");
        envioDto.setCelularRecibe("321654987");
        envioDto.setValorDeclarado(50000);
        envioDto.setPeso(2);

        Cliente cliente = new Cliente();
        cliente.setCedula(123456789);
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));
        Paquete paquete1 = new Paquete(2,2000);
        when(paqueteRepository.save(any(Paquete.class))).thenReturn(paquete1);
        Envio envio1 = new Envio(cliente,"Medellín","Bogotá","Cra 17 #20-10","Laura","342352", LocalTime.now(), EstadoEnvio.RECIBIDO,10000,paquete1);
        when(envioRepository.save(any(Envio.class))).thenReturn(envio1);
        // Act
        EnvioCreadoDTO envioCreadoDTO = this.envioService.crearEnvio(envioDto);
        //Assert and Verify that the envio was saved
        verify(this.envioRepository,times(1)).save(any(Envio.class));
        assertNotNull(envioCreadoDTO.getIdGuia());
        assertEquals(EstadoEnvio.RECIBIDO, envioCreadoDTO.getEstadoEnvio());

    }

    @Test
    void crearEnvioDatosIncorrectos() throws ApiRequestException {
        //Arrange
        EnvioNuevoDTO envioDto = new EnvioNuevoDTO();
        envioDto.setCedula(123456789);
        envioDto.setCiudadOrigen("Pereira");
        envioDto.setDireccionDestino("Calle 32 # 14-3");
        envioDto.setNombreRecibe("Maria");
        envioDto.setCelularRecibe("321654987");
        envioDto.setValorDeclarado(50000);
        envioDto.setPeso(2);

        // Act
        ApiRequestException exception =   Assertions.assertThrows(ApiRequestException.class, () ->
            this.envioService.crearEnvio(envioDto));
        //Assert
        assertEquals("La solicitud está incompleta, debe llenar todos los datos para poder generar un envío", exception.getMessage());
    }

    @Test
    void crearEnvioClienteNoExiste() throws ApiRequestException {
        //Arrange
        EnvioNuevoDTO envioDto = new EnvioNuevoDTO();
        envioDto.setCedula(123456789);
        envioDto.setCiudadOrigen("Pereira");
        envioDto.setCiudadDestino("Medellin");
        envioDto.setDireccionDestino("Calle 32 # 14-3");
        envioDto.setNombreRecibe("Maria");
        envioDto.setCelularRecibe("321654987");
        envioDto.setValorDeclarado(50000);
        envioDto.setPeso(2);
        when(this.clienteRepository.findById(123456789)).thenReturn(Optional.empty());
        //Act
        ApiRequestException exception = Assertions.assertThrows(ApiRequestException.class, () ->
            this.envioService.crearEnvio(envioDto));
        //Assert
        assertEquals("El cliente con cédula 123456789 debe de estar registrado para poder enviar un paquete", exception.getMessage());
    }

    @Test
    void obtenerEnvioNoExiste() throws ApiRequestException {
        //Arrange
        when(this.envioRepository.findById("1")).thenReturn(Optional.empty());
        //Act
        ApiRequestException exception = Assertions.assertThrows(ApiRequestException.class, () ->
            this.envioService.obtenerEnvio("1"));
        //Assert
        assertEquals("No existe un envío con número de guía 1 en el sistema", exception.getMessage());
    }

    @Test
    void obtenerEnvio(){
        //Arrange
        Cliente cliente = new Cliente();
        cliente.setCedula(123456789);
        Paquete paquete1 = new Paquete(2,2000);
        Envio envio1 = new Envio(cliente,"Medellín","Bogotá","Cra 17 #20-10","Laura","342352", LocalTime.now(), EstadoEnvio.RECIBIDO,10000,paquete1);
        when(this.envioRepository.findById(any(String.class))).thenReturn(Optional.of(envio1));
        //Act
        Envio resultado = this.envioService.obtenerEnvio("1");
        //Assert
        assertEquals(envio1, resultado);
    }

    @Test
    void obtenerEnvioPorCedulaInexistente()throws ApiRequestException {
        //Arrange
        when(clienteRepository.findById(1234567890)).thenReturn(Optional.empty());
        //Act
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> this.envioService.obtenerEnviosPorCedula(1234567890));
        //Assert
        assertEquals("No existe un cliente con cédula 1234567890 en nuestra compañía", exception.getMessage());
    }

    @Test
    void obtenerEnviosPorCedulaClienteSinEnvios() throws ApiRequestException {
        //Arrange
        Integer cedula = 1234567890;
        Cliente cliente = new Cliente();
        cliente.setCedula(cedula);
        when(clienteRepository.findById(cedula)).thenReturn(Optional.of(cliente));
        when(envioRepository.findAllByClienteCedula(cedula)).thenReturn(new ArrayList<>());
        //Act
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> this.envioService.obtenerEnviosPorCedula(cedula));
        //Assert
        assertEquals("El cliente con cédula 1234567890 no tiene envíos registrados en el sistema", exception.getMessage());

    }

    @Test
    void obtenerEnviosPorCedula(){
        //Arrange
        Cliente cliente1 = new Cliente();
        cliente1.setCedula(1234567890);
        cliente1.setCorreoElectronico("cliente1@hotmail.com");
        when(this.clienteRepository.findById(1234567890)).thenReturn(Optional.of(cliente1));
        Paquete paquete1 = new Paquete(2,2000);
        Paquete paquete2 = new Paquete(3,3000);
        Envio envio1 = new Envio(cliente1,"Medellín","Bogotá","Cra 17 #20-10","Laura","342352", LocalTime.now(), EstadoEnvio.RECIBIDO,10000,paquete1);
        Envio envio2 = new Envio(cliente1,"Medellín","Bogotá","Cra 17 #20-10","Laura","342352", LocalTime.now(), EstadoEnvio.RECIBIDO,20000,paquete2);
        List<Envio> enviosList = Arrays.asList(envio1, envio2);
        when(this.envioRepository.findAllByClienteCedula(1234567890)).thenReturn(enviosList);
        //Act
        List<Envio> resultado = this.envioService.obtenerEnviosPorCedula(1234567890);
        //Assert
        assertEquals(2, resultado.size());
    }

    @Test
    void obtenerEnviosPorEstadoEnvio(){
        //Arrange
        Cliente cliente1 = new Cliente();
        cliente1.setCedula(1234567890);
        cliente1.setCorreoElectronico("cliente1@hotmail.com");
        when(this.clienteRepository.findById(1234567890)).thenReturn(Optional.of(cliente1));
        Empleado empleado1 = new Empleado();
        empleado1.setCedula(1876543219);
        when(this.empleadoRepository.findById(1876543219)).thenReturn(Optional.of(empleado1));
        Paquete paquete1 = new Paquete(2,2000);
        Paquete paquete2 = new Paquete(3,3000);
        Envio envio1 = new Envio(cliente1,"Medellín","Bogotá","Cra 17 #20-10","Laura","342352", LocalTime.now(), EstadoEnvio.ENTREGADO,10000,paquete1);
        Envio envio2 = new Envio(cliente1,"Medellín","Bogotá","Cra 17 #20-10","Laura","342352", LocalTime.now(), EstadoEnvio.ENTREGADO,20000,paquete2);
        List<Envio> enviosList = Arrays.asList(envio1, envio2);
        when(this.envioRepository.findAllByEstadoEnvio(EstadoEnvio.ENTREGADO)).thenReturn(enviosList);
        //Act
        List<Envio> resultado = this.envioService.obtenerEnviosPorEstado("ENTREGADO", 1876543219);
        //Assert
        assertEquals(2, resultado.size());
    }

    @Test
    void obtenerEnviosPorEstadoEnvioEmpleadoNoExiste() throws ApiRequestException {
        //Arrange
        when(this.empleadoRepository.findById(1876543219)).thenReturn(Optional.empty());
        //Act
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> this.envioService.obtenerEnviosPorEstado("ENTREGADO", 1876543219));
        //Assert
        assertEquals("No existe un empleado con cédula 1876543219 en nuestra compañía", exception.getMessage());
    }

    @Test
    void obtenerEnviosPorEstadoClienteNoTieneEnvios() throws ApiRequestException {
        //Arrange
        Cliente cliente1 = new Cliente();
        cliente1.setCedula(1234567890);
        cliente1.setCorreoElectronico("cliente1@hotmail.com");
        when(this.clienteRepository.findById(1234567890)).thenReturn(Optional.of(cliente1));
        Empleado empleado1 = new Empleado();
        empleado1.setCedula(1876543219);
        when(this.empleadoRepository.findById(1876543219)).thenReturn(Optional.of(empleado1));
        when(this.envioRepository.findAllByEstadoEnvio(EstadoEnvio.ENTREGADO)).thenReturn(new ArrayList<>());
        //Act
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> this.envioService.obtenerEnviosPorEstado("ENTREGADO", 1876543219));
        //Assert
        assertEquals("No existen envíos en estado ENTREGADO en el sistema", exception.getMessage());
    }

    @Test
    void eliminarEnvioInexistente() throws ApiRequestException {
        //Arrange
        when(this.envioRepository.findById("1")).thenReturn(Optional.empty());
        //Act
        ResponseEntity resultado = this.envioService.eliminar("1");
        //Assert
        assertEquals("No existe una guía con esta identificación en el sistema", resultado.getBody());
    }

    @Test
    void eliminarEnvio(){
        //Arrange
        Cliente cliente1 = new Cliente();
        cliente1.setCedula(1234567890);
        cliente1.setCorreoElectronico("cliente1@hotmail.com");
        Paquete paquete1 = new Paquete(2,2000);
        Envio envio1 = new Envio(cliente1,"Medellín","Bogotá","Cra 17 #20-10","Laura","342352", LocalTime.now(), EstadoEnvio.RECIBIDO,10000,paquete1);
        when(this.envioRepository.findById("1")).thenReturn(Optional.of(envio1));
        //Act
        ResponseEntity resultado = this.envioService.eliminar("1");
        //Assert
        assertEquals("El envio con número de guía 1 se ha eliminado con éxito", resultado.getBody());
    }

    @Test
    void actualizarEstadoEmpleadoInexistente () throws ApiRequestException {
        //Arrange
        when(this.empleadoRepository.findById(1876543219)).thenReturn(Optional.empty());
        //Act
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> this.envioService.actualizarEstado("1", "ENTREGADO", 1876543219));
        //Assert
        assertEquals("El empleado con cédula 1876543219 no existe en nuestra compañía", exception.getMessage());
    }

    @Test
    void actualizarEstadoEmpleadoConductor() throws ApiRequestException {
        //Arrange
        Empleado empleado1 = new Empleado();
        empleado1.setCedula(1876543219);
        empleado1.setTipoDeEmpleado(TipoEmpleado.CONDUCTOR);
        when(this.empleadoRepository.findById(1876543219)).thenReturn(Optional.of(empleado1));
        //Act
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> this.envioService.actualizarEstado("1", "ENTREGADO", 1876543219));
        //Assert
        assertEquals("El empleado con cédula 1876543219 no tiene permisos para actualizar el estado de un envío", exception.getMessage());
    }

    @Test
    void actualizarEstadoEnvioInexistente () throws ApiRequestException {
        //Arrange
        Empleado empleado1 = new Empleado();
        empleado1.setCedula(1876543219);
        when(this.empleadoRepository.findById(1876543219)).thenReturn(Optional.of(empleado1));
        when(this.envioRepository.findById("1")).thenReturn(Optional.empty());
        //Act
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> this.envioService.actualizarEstado("1", "ENTREGADO", 1876543219));
        //Assert
        assertEquals("No existe un envío con número de guía 1 en el sistema", exception.getMessage());
    }

    @Test
    void actualizarEstadoEnvioIncumpleValidaciones() throws ApiRequestException {
        //Arrange
        Empleado empleado1 = new Empleado();
        empleado1.setCedula(1876543219);
        when(this.empleadoRepository.findById(1876543219)).thenReturn(Optional.of(empleado1));
        Cliente cliente1 = new Cliente();
        cliente1.setCedula(1234567890);
        Paquete paquete1 = new Paquete(2,2000);
        Envio envio1 = new Envio(cliente1,"Medellín","Bogotá","Cra 17 #20-10","Laura","342352", LocalTime.now(), EstadoEnvio.RECIBIDO,10000,paquete1);
        when(this.envioRepository.findById("1")).thenReturn(Optional.of(envio1));
        //Act
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> this.envioService.actualizarEstado("1", "Entregado", 1876543219));
        //Assert
        assertEquals("El cambio de estado no cumple con las validaciones", exception.getMessage());
    }

    @Test
    void actualizarEstadoEnvioEntregado() throws ApiRequestException {
        //Arrange
        Empleado empleado1 = new Empleado();
        empleado1.setCedula(1876543219);
        when(this.empleadoRepository.findById(1876543219)).thenReturn(Optional.of(empleado1));
        Cliente cliente1 = new Cliente();
        cliente1.setCedula(1234567890);
        Paquete paquete1 = new Paquete(2,2000);
        Envio envio1 = new Envio(cliente1,"Medellín","Bogotá","Cra 17 #20-10","Laura","342352", LocalTime.now(), EstadoEnvio.ENTREGADO,10000,paquete1);
        when(this.envioRepository.findById("1")).thenReturn(Optional.of(envio1));
        //Act
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> this.envioService.actualizarEstado("1", "Entregado", 1876543219));
        //Assert
        assertEquals("El envío con número de guía 1 ya fue entregado", exception.getMessage());
    }

    @Test
    void actualizarEstadoEnvioAEnRuta(){
        //Arrange
        Empleado empleado1 = new Empleado();
        empleado1.setCedula(1876543219);
        when(this.empleadoRepository.findById(1876543219)).thenReturn(Optional.of(empleado1));
        Cliente cliente1 = new Cliente();
        cliente1.setCedula(1234567890);
        Paquete paquete1 = new Paquete(2,2000);
        Envio envio1 = new Envio(cliente1,"Medellín","Bogotá","Cra 17 #20-10","Laura","342352", LocalTime.now(), EstadoEnvio.RECIBIDO,10000,paquete1);
        Envio envioCambio = new Envio(cliente1,"Medellín","Bogotá","Cra 17 #20-10","Laura","342352", LocalTime.now(), EstadoEnvio.EN_RUTA,10000,paquete1);
        when(this.envioRepository.findById("1")).thenReturn(Optional.of(envio1));
        when(this.envioRepository.save((any()))).thenReturn(envioCambio);
        //Act
        EstadoEnvioDTO envioActualizado = this.envioService.actualizarEstado("1", "En ruta", 1876543219);
        EstadoEnvioDTO estadoEsperado = new EstadoEnvioDTO("1",EstadoEnvio.EN_RUTA);
        //Assert
        assertEquals(estadoEsperado.getUltimoEstado(), envioActualizado.getUltimoEstado());
    }
    @Test
    void actualizarEstadoEnvioAEntregado(){
        //Arrange
        Empleado empleado1 = new Empleado();
        empleado1.setCedula(1876543219);
        when(this.empleadoRepository.findById(1876543219)).thenReturn(Optional.of(empleado1));
        Cliente cliente1 = new Cliente();
        cliente1.setCedula(1234567890);
        Paquete paquete1 = new Paquete(2,2000);
        Envio envio1 = new Envio(cliente1,"Medellín","Bogotá","Cra 17 #20-10","Laura","342352", LocalTime.now(), EstadoEnvio.EN_RUTA,10000,paquete1);
        Envio envioCambio = new Envio(cliente1,"Medellín","Bogotá","Cra 17 #20-10","Laura","342352", LocalTime.now(), EstadoEnvio.ENTREGADO,10000,paquete1);
        when(this.envioRepository.findById("1")).thenReturn(Optional.of(envio1));
        when(this.envioRepository.save((any()))).thenReturn(envioCambio);
        //Act
        EstadoEnvioDTO envioActualizado = this.envioService.actualizarEstado("1", "ENTREGADO", 1876543219);
        EstadoEnvioDTO estadoEsperado = new EstadoEnvioDTO("1",EstadoEnvio.ENTREGADO);
        //Assert
        assertEquals(estadoEsperado.getUltimoEstado(), envioActualizado.getUltimoEstado());
    }

    @Test
    void calcularValor(){
        //Arrange
        TipoPaquete tipo = TipoPaquete.GRANDE;
        //Act
        double valor = this.envioService.calcularValor(tipo);
        //Assert
        assertEquals(50000, valor);
    }
}