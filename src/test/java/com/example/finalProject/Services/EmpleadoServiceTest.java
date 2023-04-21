package com.example.finalProject.Services;

import com.example.finalProject.Modules.Empleado;
import com.example.finalProject.Repositories.EmpleadoRepository;
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
        Integer cedulaExistente = 1234567890;
        Integer cedulaNoExistente = 1234567891;
        Empleado empleadoConCedula = new Empleado();
        empleadoConCedula.setCedula(cedulaExistente);

        when(empleadoRepository.findById(cedulaExistente)).thenReturn(Optional.of(empleadoConCedula));
        when(empleadoRepository.findById(cedulaNoExistente)).thenReturn(Optional.empty());

        boolean resultadoTrue = empleadoService.verificarEmpleadoExiste(cedulaExistente);
        boolean resultadoFalse = empleadoService.verificarEmpleadoExiste(cedulaNoExistente);

        assertTrue(resultadoTrue);
        assertFalse(resultadoFalse);
    }



    @Test
    void obtenerEmpleados() {
        Empleado empleado1 = new Empleado();
        empleado1.setCedula(1234);
        Empleado empleado2 = new Empleado();
        empleado2.setCedula(5678);
        List<Empleado> empleados = Arrays.asList(empleado1, empleado2);

        when(empleadoRepository.findAll()).thenReturn(empleados);

        List<Empleado> empleadosResultado = this.empleadoService.obtenerEmpleados();

        assertEquals(2, empleadosResultado.size());
        assertEquals(1234, empleado1.getCedula());
        assertEquals(5678, empleado2.getCedula());

    }

   /* @Test
    void obtenerEmpleadoCedula() {
        Integer cedula = 1234;
        Integer cedula2 = 5678;
        Empleado empleado = new Empleado();
        empleado.setCedula(cedula);
        Empleado empleado2 = new Empleado();
        empleado2.setCedula(cedula2);
        List<Empleado> empleados = Arrays.asList(empleado, empleado2);

        when(empleadoRepository.findAll()).thenReturn(empleados);

        Empleado resultado1 = this.empleadoService.obtenerEmpleadoCedula(cedula);
        Empleado resultado2 = this.empleadoService.obtenerEmpleadoCedula(cedula2);

        assertEquals(empleado, resultado1);
        assertEquals(empleado2, resultado2);

    }*/
   /* @Test
    void ActualizarEmpleado() {
        Integer cedula = 1234;
        Empleado empleado = new Empleado();
        empleado.setCedula(cedula);
        Empleado empleadoActualizado = new Empleado();
        empleadoActualizado.setCedula(cedula);

        when(this.empleadoRepository.findById(cedula)).thenReturn(Optional.of(empleado));
        when(this.empleadoRepository.save(any(Empleado.class))).thenReturn(empleado);
        ResponseEntity resultado = this.empleadoService.actualizarEmpleado(empleadoActualizado);

        assertEquals(HttpStatus.ACCEPTED, resultado.getStatusCode());
        assertEquals(empleado, resultado.getBody());
    }*/

    /*@Test
    void actualizarEmpleadoNoExistente() {
        Integer cedula = 1234;
        Empleado empleado = new Empleado();
        empleado.setCedula(cedula);
        when(this.empleadoRepository.findById(1234)).thenReturn(Optional.empty());

        Empleado empleadoActualizado = new Empleado();
        empleadoActualizado.setCedula(cedula);
        ResponseEntity resultado = empleadoService.actualizarEmpleado(empleadoActualizado);

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals("No existe un empleado con esta cédula", resultado.getBody());
    }
    @Test
    void eliminarEmpleadoNoExiste() {
        Mockito.doNothing().when(empleadoRepository).deleteById(1234);
        ResponseEntity resultado = this.empleadoService.eliminar(1234);

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals("Este empleado no existe", resultado.getBody());
    }*/
    @Test
    void eliminarEmpleadoExiste() {
        Integer cedula = 1234;
        Empleado empleado = new Empleado();
        empleado.setCedula(cedula);
        when(empleadoRepository.findById(cedula)).thenReturn(Optional.of(empleado));

        ResponseEntity resultado = this.empleadoService.eliminar(cedula);

        assertEquals(HttpStatus.ACCEPTED, resultado.getStatusCode());
        assertEquals("El empleado " + cedula + " se ha eliminado con éxito", resultado.getBody());
    }

    @Test
    void crearEmpleado() {
    }

}
