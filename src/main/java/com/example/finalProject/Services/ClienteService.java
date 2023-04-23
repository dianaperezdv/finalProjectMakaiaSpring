package com.example.finalProject.Services;

import com.example.finalProject.Exception.InvalidStatementException;
import com.example.finalProject.Modules.Cliente;
import com.example.finalProject.Repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    public boolean verificarClienteExiste(Integer cedula) {
        Optional<Cliente> cliente1 = this.clienteRepository.findById(cedula);
        return cliente1.isPresent();
    }


    public ResponseEntity crearCliente(Cliente cliente) {
        if (verificarClienteExiste(cliente.getCedula())){
            return new ResponseEntity("Ya existe un cliente con esta cédula", HttpStatus.BAD_REQUEST);
        }
        else{
            if((cliente.getCedula().toString()).length() != 10){
            this.clienteRepository.save(cliente);
            return new ResponseEntity(cliente,HttpStatus.ACCEPTED);
            }
            else{
                return new ResponseEntity("La cédula debe tener 10 dígitos", HttpStatus.BAD_REQUEST);
            }
        }
    }

    public List<Cliente> obtenerClientes() {
        return this.clienteRepository.findAll();
    }

    public ResponseEntity obtenerClienteCedula(Integer cedula) {
        try{
            return ResponseEntity.ok(this.clienteRepository.findById(cedula));
        }
        catch (Exception e){
            return new ResponseEntity("No existe un cliente con esta cédula", HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity actualizarCliente (Cliente cliente) {
        if(verificarClienteExiste(cliente.getCedula())){
            this.clienteRepository.save(cliente);
            return ResponseEntity.ok(obtenerClienteCedula(cliente.getCedula()));
        }
        else {
            return new ResponseEntity("No existe un cliente con esta cédula", HttpStatus.BAD_REQUEST);
        }

    }


    public ResponseEntity eliminar(Integer cedula) {
        try{
            this.clienteRepository.deleteById(cedula);
            return new ResponseEntity("El cliente " + cedula+ " se ha eliminado con éxito", HttpStatus.ACCEPTED);

        }
        catch (Exception e){
            return new ResponseEntity("No existe un cliente con esta cédula", HttpStatus.BAD_REQUEST);
            }
    }

}
