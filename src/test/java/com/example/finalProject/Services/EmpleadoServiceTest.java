package com.example.finalProject.Services;

import com.example.finalProject.Exception.InvalidStatementException;
import com.example.finalProject.Modules.Empleado;
import com.example.finalProject.Repositories.EmpleadoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class EmpleadoServiceTest {
    EmpleadoRepository empleadoRepository;
    EmpleadoService empleadoService;

    @BeforeEach
    public void setUp(){
        this.empleadoRepository = mock(EmpleadoRepository.class);
        this.empleadoService = new EmpleadoService(empleadoRepository);
    }

    @Test
    void verificarEmpleadoExiste() {
        //Arrange
        Integer cedulaExistente = 1234567890;
        Integer cedulaNoExistente = 1234567891;
        Empleado empleadoConCedula = new Empleado();
        empleadoConCedula.setCedula(cedulaExistente);
        when(empleadoRepository.findById(cedulaExistente)).thenReturn(Optional.of(empleadoConCedula));
        when(empleadoRepository.findById(cedulaNoExistente)).thenReturn(Optional.empty());
        //Act
        boolean resultadoTrue = empleadoService.verificarEmpleadoExiste(cedulaExistente);
        boolean resultadoFalse = empleadoService.verificarEmpleadoExiste(cedulaNoExistente);
        //Assert
        assertTrue(resultadoTrue);
        assertFalse(resultadoFalse);
    }


    @Test
    void crearEmpleadoCedulaExistente() throws InvalidStatementException {
        //Arrange
        Empleado empleado1 = new Empleado();
        empleado1.setCedula(1234);
        when(empleadoRepository.findById(1234)).thenReturn(Optional.of(empleado1));
        //Act
        InvalidStatementException exception = assertThrows(InvalidStatementException.class, () -> {
            empleadoService.crearEmpleado(empleado1);
        });
        //Assert
        assertEquals("Ya existe un empleado con la cédula 1234 en el sistema", exception.getMessage());

    }

    @Test
    void crearEmpleadoCorreoIncorrecto() throws InvalidStatementException{
        //Arrange
        Empleado empleado1 = new Empleado();
        empleado1.setCedula(1234567890);
        empleado1.setCorreoElectronico("correoIncorrecto");
        when(empleadoRepository.findById(1234567890)).thenReturn(Optional.empty());
        //Act
        InvalidStatementException exception = assertThrows(InvalidStatementException.class, () -> {
            empleadoService.crearEmpleado(empleado1);
        });
        //Assert
        assertEquals("El correo electrónico no es válido", exception.getMessage());
    }
    @Test
    void crearEmpleadoCedulaIncorrecta() throws InvalidStatementException {
        //Arrange
        Empleado empleado1 = new Empleado();
        empleado1.setCedula(1234);
        when(empleadoRepository.findById(1234)).thenReturn(Optional.empty());
        //Act
        InvalidStatementException exception = assertThrows(InvalidStatementException.class, () -> {
            empleadoService.crearEmpleado(empleado1);
        });
        //Assert
        assertEquals("La cédula debe tener 10 dígitos", exception.getMessage());
    }

    @Test
    void crearEmpleado(){
        //Arrange
        Empleado empleado1 = new Empleado();
        empleado1.setCedula(1234567890);
        empleado1.setCorreoElectronico("empleado@gmail.com");
        when(empleadoRepository.findById(1234567890)).thenReturn(Optional.empty());
        //Act
        Empleado resultado = empleadoService.crearEmpleado(empleado1);
        //Verify
        verify(this.empleadoRepository, times(1)).save(empleado1);
    }

    @Test
    void obtenerEmpleados() {
        //Arrange
        Empleado empleado1 = new Empleado();
        empleado1.setCedula(1234);
        Empleado empleado2 = new Empleado();
        empleado2.setCedula(5678);
        List<Empleado> empleados = Arrays.asList(empleado1, empleado2);
        when(this.empleadoRepository.findAll()).thenReturn(empleados);
        //Act
        List<Empleado> empleadosResultado = this.empleadoService.obtenerEmpleados();
        //Assert
        assertEquals(2, empleadosResultado.size());
        assertEquals(1234, empleadosResultado.get(0).getCedula());
        assertEquals(5678, empleadosResultado.get(1).getCedula());
    }

    @Test
    void obtenerEmpleadoCedula() {
        //Arrange
        Integer cedula = 1234;
        Empleado empleado = new Empleado();
        empleado.setCedula(cedula);
        when(empleadoRepository.findById(cedula)).thenReturn(Optional.of(empleado));
        //Act
        Empleado resultado = this.empleadoService.obtenerEmpleadoCedula(cedula);
        //Assert
        assertEquals(empleado, resultado);
    }

    @Test
    void obtenerEmpleadoCedulaInexistente() throws InvalidStatementException {
        //Arrange
        Integer cedula = 1234;
        when(empleadoRepository.findById(cedula)).thenReturn(Optional.empty());
        //Act
        InvalidStatementException exception = assertThrows(InvalidStatementException.class, () -> {
            empleadoService.obtenerEmpleadoCedula(cedula);
        });
        //Assert
        assertEquals("No existe un empleado con la cédula 1234", exception.getMessage());
    }


    @Test
    void ActualizarEmpleado() {
        //Arrange
        Integer cedula = 1234;
        Empleado empleado = new Empleado();
        empleado.setCedula(cedula);
        Empleado empleadoActualizado = new Empleado();
        empleadoActualizado.setCedula(cedula);
        when(this.empleadoRepository.findById(cedula)).thenReturn(Optional.of(empleado));
        //Act
        Empleado resultado = empleadoService.actualizarEmpleado(empleadoActualizado);
        //Verify
        verify(this.empleadoRepository, times(1)).findById(cedula);
        verify(this.empleadoRepository, times(1)).save(empleadoActualizado);
    }

    @Test
    void actualizarEmpleadoInexistente() throws InvalidStatementException{
        //Arrange
        Integer cedula = 1234;
        Empleado empleado = new Empleado();
        empleado.setCedula(cedula);
        when(this.empleadoRepository.findById(cedula)).thenReturn(Optional.empty());
        //Act
        InvalidStatementException exception = assertThrows(InvalidStatementException.class, () -> {
            empleadoService.actualizarEmpleado(empleado);
        });
        //Assert
        assertEquals("No existe un empleado con la cédula 1234 en el sistema", exception.getMessage());
    }


    @Test
    void eliminarEmpleado() {
        //Arrange
        Integer cedula = 1234;
        Empleado empleado = new Empleado();
        empleado.setCedula(cedula);
        when(empleadoRepository.findById(cedula)).thenReturn(Optional.of(empleado));
        //Act
        ResponseEntity resultado = this.empleadoService.eliminar(cedula);
        //Assert
        assertEquals(HttpStatus.ACCEPTED, resultado.getStatusCode());
        assertEquals("El empleado " + cedula + " se ha eliminado con éxito", resultado.getBody());
    }


}
