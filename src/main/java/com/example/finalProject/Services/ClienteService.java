package com.example.finalProject.Services;

import com.example.finalProject.Exception.InvalidStatementException;
import com.example.finalProject.Modules.Cliente;
import com.example.finalProject.Repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    public Cliente crearCliente(Cliente cliente) {
        if (verificarClienteExiste(cliente.getCedula())){
            throw new InvalidStatementException("Ya existe un cliente con la cédula "+cliente.getCedula() + " en el sistema");
        }
        else{
            if((cliente.getCedula().toString()).length() == 10){
                if(validateEmail(cliente.getCorreoElectronico())){
                    return this.clienteRepository.save(cliente);
                }
                else{
                    throw new InvalidStatementException("El correo electrónico no es válido");
                }
            }
            else{
                throw new InvalidStatementException("La cédula debe tener 10 dígitos");
            }
        }
    }

    public List<Cliente> obtenerClientes() {
        return this.clienteRepository.findAll();
    }

    public Cliente obtenerClienteCedula(Integer cedula) {
        if (verificarClienteExiste(cedula)){
            return this.clienteRepository.findById(cedula).get();
        }

            throw new InvalidStatementException("No existe un cliente con la cédula " + cedula + " en el sistema");
    }


    public Cliente actualizarCliente (Cliente cliente) {
        if(verificarClienteExiste(cliente.getCedula())){
            return this.clienteRepository.save(cliente);
        }
        else {
            throw new InvalidStatementException("No existe un cliente con la cédula " + cliente.getCedula() + " en el sistema");
        }
    }


    public ResponseEntity eliminar(Integer cedula) {
        if (!verificarClienteExiste(cedula)){
            throw new InvalidStatementException("No existe un cliente la cédula " + cedula + " en el sistema");
        }
        else{
            this.clienteRepository.deleteById(cedula);
            return new ResponseEntity("El cliente con cédula " + cedula+ " se ha eliminado con éxito", HttpStatus.ACCEPTED);
        }
    }

    public boolean validateEmail(String email) {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

}
