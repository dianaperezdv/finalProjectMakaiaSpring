package com.example.finalProject.Services;

import com.example.finalProject.Exception.ApiRequestException;
import com.example.finalProject.Modules.Cliente;
import com.example.finalProject.Repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ClienteServiceTest {
    ClienteRepository clienteRepository;
    ClienteService clienteService;

    @BeforeEach
    public void setUp(){
        this.clienteRepository = mock(ClienteRepository.class);
        this.clienteService = new ClienteService(clienteRepository);
    }

    @Test
    void verificarClienteExiste() {
        //Arrange
        Integer cedulaExistente = 1234567890;
        Integer cedulaNoExistente = 1234567891;
        Cliente clienteConCedula = new Cliente();
        clienteConCedula.setCedula(cedulaExistente);
        when(clienteRepository.findById(cedulaExistente)).thenReturn(Optional.of(clienteConCedula));
        when(clienteRepository.findById(cedulaNoExistente)).thenReturn(Optional.empty());
        //Act
        boolean resultadoTrue = clienteService.verificarClienteExiste(cedulaExistente);
        boolean resultadoFalse = clienteService.verificarClienteExiste(cedulaNoExistente);
        //Assert
        assertTrue(resultadoTrue);
        assertFalse(resultadoFalse);
        }


    @Test
    void crearClienteCedulaExistente() throws ApiRequestException {
        //Arrange
        Cliente cliente1 = new Cliente();
        cliente1.setCedula(1234);
        when(clienteRepository.findById(1234)).thenReturn(Optional.of(cliente1));
        //Act
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
            clienteService.crearCliente(cliente1);
        });
        //Assert
        assertEquals("Ya existe un cliente con la cédula 1234 en el sistema",exception.getMessage());
    }

    @Test
    void crearClienteCorreoIncorrecto() throws ApiRequestException {
        //Arrange
        Cliente cliente1 = new Cliente();
        cliente1.setCedula(1234567890);
        cliente1.setCorreoElectronico("correoIncorrecto");
        when(clienteRepository.findById(1234567890)).thenReturn(Optional.empty());
        //Act
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
            clienteService.crearCliente(cliente1);
        });
        //Assert
        assertEquals("El correo electrónico no es válido", exception.getMessage());
    }
    @Test
    void crearClienteCedulaIncorrecta() throws ApiRequestException {
        //Arrange
        Cliente cliente1 = new Cliente();
        cliente1.setCedula(1234);
        when(clienteRepository.findById(1234)).thenReturn(Optional.empty());
        //Act
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
            clienteService.crearCliente(cliente1);
        });
        //Assert
        assertEquals("La cédula debe tener 10 dígitos", exception.getMessage());
    }

    @Test
    void crearCliente(){
        //Arrange
        Cliente cliente1 = new Cliente();
        cliente1.setCedula(1234567890);
        cliente1.setCorreoElectronico("diana@gmail.com");
        when(clienteRepository.findById(1234567890)).thenReturn(Optional.empty());
        //Act
        Cliente resultado = clienteService.crearCliente(cliente1);
        //Assert
        verify(clienteRepository, times(1)).save(cliente1);
    }

    @Test
    void obtenerClientes() {
        //Arrange
        Cliente cliente1 = new Cliente();
        cliente1.setCedula(1234);
        Cliente cliente2 = new Cliente();
        cliente2.setCedula(5678);
        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);
        when(clienteRepository.findAll()).thenReturn(clientes);
        //Act
        List<Cliente> clientesResultado = this.clienteService.obtenerClientes();
        //Assert
        assertEquals(2, clientesResultado.size());
        assertEquals(1234, cliente1.getCedula());
        assertEquals(5678, cliente2.getCedula());
    }

    @Test
    void obtenerClienteCedula() {
        //Arrange
        Integer cedula = 1234;
        Cliente cliente = new Cliente();
        cliente.setCedula(cedula);
        when(clienteRepository.findById(cedula)).thenReturn(Optional.of(cliente));
        //Act
        Cliente resultado = this.clienteService.obtenerClienteCedula(cedula);
        //Assert
        assertEquals(cliente, resultado);
    }

    @Test
    void obtenerClienteCedulaInexistente() throws ApiRequestException {
        //Arrange
        Integer cedula = 1234;
        when(clienteRepository.findById(cedula)).thenReturn(Optional.empty());
        //Act
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
            clienteService.obtenerClienteCedula(cedula);
        });
        //Assert
        assertEquals("No existe un cliente con la cédula " + cedula + " en el sistema", exception.getMessage());

    }


    @Test
    void ActualizarCliente() {
        //Arrange
        Integer cedula = 1234;
        Cliente cliente = new Cliente();
        cliente.setCedula(cedula);
        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setCedula(cedula);
        when(this.clienteRepository.findById(cedula)).thenReturn(Optional.of(cliente));
        //Act
        Cliente resultado = clienteService.actualizarCliente(clienteActualizado);
        //Verify
        verify(this.clienteRepository, times(1)).findById(cedula);
        verify(this.clienteRepository, times(1)).save(clienteActualizado);
    }

    @Test
    void actualizarClienteInexistente() throws ApiRequestException {
        //Arrange
        Integer cedula = 1234;
        Cliente cliente = new Cliente();
        cliente.setCedula(cedula);
        when(this.clienteRepository.findById(cedula)).thenReturn(Optional.empty());
        //Act
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
            clienteService.actualizarCliente(cliente);
        });
        //Assert
        assertEquals("No existe un cliente con la cédula 1234 en el sistema", exception.getMessage());
    }


    @Test
    void eliminarCliente() {
        //Arrange
        Integer cedula = 1234;
        Cliente cliente = new Cliente();
        cliente.setCedula(cedula);
        when(clienteRepository.findById(cedula)).thenReturn(Optional.of(cliente));
        //Act
        ResponseEntity resultado = this.clienteService.eliminar(cedula);
        //Assert
        assertEquals(HttpStatus.ACCEPTED, resultado.getStatusCode());
        assertEquals("El cliente con cédula " + cedula + " se ha eliminado con éxito", resultado.getBody());
    }


}
