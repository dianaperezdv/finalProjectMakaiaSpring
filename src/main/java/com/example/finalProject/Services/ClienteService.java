package com.example.finalProject.Services;

import com.example.finalProject.Exception.ApiRequestException;
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

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    private final String sistema = " en el sistema";
    public boolean verificarClienteExiste(Integer cedula) {
        Optional<Cliente> cliente1 = this.clienteRepository.findById(cedula);
        return cliente1.isPresent();
    }


    public Cliente crearCliente(Cliente cliente) {
        if (verificarClienteExiste(cliente.getCedula())){
            throw new ApiRequestException("Ya existe un cliente con la cédula "+cliente.getCedula() + sistema);
        }
        validateCedula(cliente.getCedula());
        validateEmail(cliente.getCorreoElectronico());
        return this.clienteRepository.save(cliente);
    }

    public List<Cliente> obtenerClientes() {
        return this.clienteRepository.findAll();
    }

    public Cliente obtenerClienteCedula(Integer cedula) {
        if (verificarClienteExiste(cedula)){
            return this.clienteRepository.findById(cedula).get();
        }
        throw new ApiRequestException("No existe un cliente con la cédula " + cedula + sistema);
    }


    public Cliente actualizarCliente (Cliente cliente) {
        if(verificarClienteExiste(cliente.getCedula())){
            return this.clienteRepository.save(cliente);
        }
        throw new ApiRequestException("No existe un cliente con la cédula " + cliente.getCedula() + sistema);

    }


    public ResponseEntity eliminar(Integer cedula) {
        if (!verificarClienteExiste(cedula)){
            throw new ApiRequestException("No existe un cliente la cédula " + cedula + sistema);
        }
        this.clienteRepository.deleteById(cedula);
        return new ResponseEntity("El cliente con cédula " + cedula+ " se ha eliminado con éxito", HttpStatus.ACCEPTED);
    }

    public void validateEmail(String email) {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.find()){
            throw new ApiRequestException("El correo electrónico no es válido");
        }
    }
    public void validateCedula(Integer cedula) {
        if((cedula.toString()).length() != 10){
            throw new ApiRequestException("La cédula debe tener 10 dígitos");
        }
    }

}
