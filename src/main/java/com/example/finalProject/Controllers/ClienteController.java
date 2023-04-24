package com.example.finalProject.Controllers;

import com.example.finalProject.Modules.Cliente;
import com.example.finalProject.Services.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("apiMensajeria/v1")
@Api(value = "Clientes", description = "Controlador de gestión de clientes")
public class ClienteController {

    private ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService){

        this.clienteService = clienteService;
    }

    @PreAuthorize("hasRole('WRITE')")
    @PostMapping("/clientes")
    @ApiOperation(value = "Crear un nuevo cliente", response = Cliente.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "El cliente ha sido creado exitosamente", response = Cliente.class),
            @ApiResponse(code = 400, message = "La solicitud es incorrecta", response = Exception.class),
            @ApiResponse(code = 401, message = "No autorizado", response = Cliente.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = Exception.class)})
    public Cliente crearCliente(@RequestBody Cliente cliente) {
        return clienteService.crearCliente(cliente);

    }
    @PreAuthorize("hasRole('READ')")
    @GetMapping("/clientes")
    @ApiOperation(value = "Obtener lista de los clientes registrados en la base de datos", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Lista de clientes obtenida exitosamente", response = List.class),
            @ApiResponse(code = 401, message = "No autorizado", response = List.class),
            @ApiResponse(code = 403, message = "Prohibido", response = List.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = List.class)

    })
    public List<Cliente> obtenerClientes() {
        return clienteService.obtenerClientes();
    }

    @PreAuthorize("hasRole('READ')")
    @GetMapping("/clientes/{cedula}")
    @ApiOperation(value = "Obtener un cliente por cédula", response = Cliente.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cliente obtenido exitosamente", response = Cliente.class),
            @ApiResponse(code = 401, message = "No autorizado", response = Cliente.class),
            @ApiResponse(code = 403, message = "Prohibido", response = Cliente.class),
            @ApiResponse(code = 404, message = "Recurso no encontrado o cliente inexistente", response = Exception.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = Cliente.class)

    })
    public Cliente getClientePorCedula(@PathVariable Integer cedula){
            return clienteService.obtenerClienteCedula(cedula);
    }

    @PreAuthorize("hasRole('WRITE')")
    @DeleteMapping("/clientes/{cedula}")
    @ApiOperation(value = "Eliminar un cliente de la base de datos", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cliente eliminado exitosamente", response = ResponseEntity.class),
            @ApiResponse(code = 401, message = "No autorizado", response = ResponseEntity.class),
            @ApiResponse(code = 403, message = "Prohibido", response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "Recurso no encontrado o cliente inexistente", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = ResponseEntity.class)})
    public ResponseEntity eliminar(@PathVariable("cedula") Integer cedula) {
        return clienteService.eliminar(cedula);
    }

    @PreAuthorize("hasRole('WRITE')")
    @PutMapping ("/clientes")
    @ApiOperation(value = "Actualizar información de un cliente", response = Cliente.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cliente fue actualizado exitosamente", response = Cliente.class),
            @ApiResponse(code = 400, message = "Solicitud incorrecta", response = Exception.class),
            @ApiResponse(code = 404, message = "El cliente no existe", response = Exception.class),
            @ApiResponse(code = 401, message = "No autorizado", response = Cliente.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = Cliente.class)})
    public Cliente actualizarCliente(@RequestBody Cliente cliente){
                 return clienteService.actualizarCliente(cliente);
    }
}


