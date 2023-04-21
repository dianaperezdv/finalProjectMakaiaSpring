package com.example.finalProject.Services;

import com.example.finalProject.Modules.Cliente;
import com.example.finalProject.Repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;


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
        Integer cedulaExistente = 1234567890;
        Integer cedulaNoExistente = 1234567891;
        Cliente clienteConCedula = new Cliente();
        clienteConCedula.setCedula(cedulaExistente);

        when(clienteRepository.findById(cedulaExistente)).thenReturn(Optional.of(clienteConCedula));
        when(clienteRepository.findById(cedulaNoExistente)).thenReturn(Optional.empty());

        boolean resultadoTrue = clienteService.verificarClienteExiste(cedulaExistente);
        boolean resultadoFalse = clienteService.verificarClienteExiste(cedulaNoExistente);

        assertTrue(resultadoTrue);
        assertFalse(resultadoFalse);
        }



    @Test
    void obtenerClientes() {
        Cliente cliente1 = new Cliente();
        cliente1.setCedula(1234);
        Cliente cliente2 = new Cliente();
        cliente2.setCedula(5678);
        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> clientesResultado = this.clienteService.obtenerClientes();

        assertEquals(2, clientesResultado.size());
        assertEquals(1234, cliente1.getCedula());
        assertEquals(5678, cliente2.getCedula());

    }

    /*@Test
    void obtenerClienteCedula() {
        Integer cedula = 1234;
        Integer cedula2 = 5678;
        Cliente cliente = new Cliente();
        cliente.setCedula(cedula);
        Cliente cliente2 = new Cliente();
        cliente2.setCedula(cedula2);
        List<Cliente> clientes = Arrays.asList(cliente, cliente2);

        when(clienteRepository.findAll()).thenReturn(clientes);

        Cliente resultado1 = this.clienteService.obtenerClienteCedula(cedula);
        Cliente resultado2 = this.clienteService.obtenerClienteCedula(cedula2);

        assertEquals(cliente, resultado1);
        assertEquals(cliente2, resultado2);

    }*/

    @Test
    void ActualizarCliente() {
        Integer cedula = 1234;
        Cliente cliente = new Cliente();
        cliente.setCedula(cedula);
        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setCedula(cedula);

        when(this.clienteRepository.findById(cedula)).thenReturn(Optional.of(cliente));
        when(this.clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        ResponseEntity resultado = clienteService.actualizarCliente(clienteActualizado);

        assertEquals(HttpStatus.ACCEPTED, resultado.getStatusCode());
        assertEquals(cliente, resultado.getBody());
    }

    @Test
    void actualizarClienteNoExistente() {
        Integer cedula = 1234;
        Cliente cliente = new Cliente();
        cliente.setCedula(cedula);
        when(this.clienteRepository.findById(1234)).thenReturn(Optional.empty());

        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setCedula(cedula);
        ResponseEntity resultado = clienteService.actualizarCliente(clienteActualizado);

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals("No existe un cliente con esta cédula", resultado.getBody());
    }
    /*@Test
    void eliminarClienteNoExiste() {
        Mockito.doNothing().when(clienteRepository).deleteById(1234);
        ResponseEntity resultado = this.clienteService.eliminar(1234);

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals("Este cliente no existe", resultado.getBody());
    }*/
    @Test
    void eliminarClienteExiste() {
        Integer cedula = 1234;
        Cliente cliente = new Cliente();
        cliente.setCedula(cedula);
        when(clienteRepository.findById(cedula)).thenReturn(Optional.of(cliente));

        ResponseEntity resultado = this.clienteService.eliminar(cedula);

        assertEquals(HttpStatus.ACCEPTED, resultado.getStatusCode());
        assertEquals("El cliente " + cedula + " se ha eliminado con éxito", resultado.getBody());
    }

    @Test
    void crearCliente() {
    }

}
