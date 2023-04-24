package com.example.finalProject.Controllers;

import com.example.finalProject.Modules.Cliente;
import com.example.finalProject.Services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("apiMensajeria/v1")
public class ClienteController {

    private ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService){

        this.clienteService = clienteService;
    }

    @PreAuthorize("hasRole('WRITE')")
    @PostMapping("/clientes")
    public Cliente crearCliente(@RequestBody Cliente cliente) {
        return clienteService.crearCliente(cliente);

    }
    @PreAuthorize("hasRole('READ')")
    @GetMapping("/clientes")
    public List<Cliente> obtenerClientes() {
        return clienteService.obtenerClientes();
    }

    @PreAuthorize("hasRole('READ')")
    @GetMapping("/clientes/{cedula}")
    public Cliente getClientePorCedula(@PathVariable Integer cedula){
            return clienteService.obtenerClienteCedula(cedula);
    }

    @PreAuthorize("hasRole('WRITE')")
    @DeleteMapping("/clientes/{cedula}")
    public ResponseEntity eliminar(@PathVariable("cedula") Integer cedula) {
        return clienteService.eliminar(cedula);
    }

    @PreAuthorize("hasRole('WRITE')")
    @PutMapping ("/clientes")
    public Cliente actualizarCliente(@RequestBody Cliente cliente){
                 return clienteService.actualizarCliente(cliente);
    }
}


