package com.example.finalProject.Controllers;

import com.example.finalProject.Modules.Cliente;
import com.example.finalProject.Services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/clientes")
    public ResponseEntity crearCliente(@RequestBody Cliente cliente) {
        return clienteService.crearCliente(cliente);

    }
    @GetMapping("/clientes")
    public List<Cliente> obtenerClientes() {
        return clienteService.obtenerClientes();
    }

    @GetMapping("/clientes/{cedula}")
    public ResponseEntity<Cliente> getClientePorCedula(@PathVariable Integer cedula){
        try {
            Cliente cliente1 = clienteService.obtenerClienteCedula(cedula);
            return ResponseEntity.ok(cliente1);
        }
        catch (Exception e){
            return new ResponseEntity("No existe un cliente con esa cédula", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/clientes/{cedula}")
    public ResponseEntity eliminar(@PathVariable("cedula") Integer cedula) {
        return clienteService.eliminar(cedula);
    }

    @PutMapping ("/clientes")
    public ResponseEntity actualizarCliente(@RequestBody Cliente cliente)
    {       try {
                 return clienteService.actualizarCliente(cliente);
             }
             catch (Exception e){
                 return new ResponseEntity("No se pudo actualizar", HttpStatus.BAD_REQUEST);
             }
    }



}
