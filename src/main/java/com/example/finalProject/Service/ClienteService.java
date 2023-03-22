package com.example.finalProject.Service;

import com.example.finalProject.Modules.Cliente;
import com.example.finalProject.Modules.Empleado;
import com.example.finalProject.Modules.Usuario;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.nio.channels.AlreadyBoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    private List<Cliente> clientes;
    public ClienteService(){
        this.clientes = new ArrayList<>();
    }

    public boolean verificarCedulaNoUsada(String cedula) {
        return (clientes.stream()
                .filter(x -> x.getCedula().equals(cedula))
                .findFirst().isEmpty());
    }
    public ResponseEntity crearCliente(Cliente cliente) {
        if (!verificarCedulaNoUsada(cliente.getCedula())){
            return new ResponseEntity("Ya existe un cliente con esta cédula", HttpStatus.BAD_REQUEST);
        }
        else{
        this.clientes.add(cliente);
        return new ResponseEntity(cliente,HttpStatus.ACCEPTED);
        }
    }
    public List<Cliente> obtenerClientes() {
        return clientes;
    }
    public Cliente obtenerClienteCedula(String cedula) {
        return clientes.stream()
                .filter(x -> x.getCedula().equals(cedula))
                .findFirst().get();
    }

    public ResponseEntity actualizarCliente (Cliente cliente) throws IllegalAccessException {
        Cliente clienteExistente = obtenerClienteCedula(cliente.getCedula());
        if(clienteExistente != null){
            int index = clientes.indexOf(clienteExistente);
            clientes.add(index,cliente);
/*
            Class<?> clienteClass  = cliente.getClass();
            Field[] campos = clienteClass.getDeclaredFields();
            for(Field campo : campos){
                campo.setAccessible(true);
                Object valorCampo = campo.get(cliente);
                if(valorCampo!=null){
                    campo.set(clienteExistente,valorCampo);
                }
            }
            return clienteExistente;
*/
            return new ResponseEntity(obtenerClienteCedula(cliente.getCedula()), HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity("No se pudo actualizar", HttpStatus.BAD_REQUEST);
        }

    }

    public String eliminar(String cedula) {
        try{
            Cliente Cliente = obtenerClienteCedula(cedula);
            clientes.remove(Cliente);
            String exito = "El cliente se ha eliminado con éxito";
            return exito;
        }
        catch (Exception e){
            String error = "No se ha podido guardar el cliente";
            return error;
        }

    }

}
