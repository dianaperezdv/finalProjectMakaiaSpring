package com.example.finalProject.Controllers;

import com.example.finalProject.DTO.CambioEnvioDTO;
import com.example.finalProject.DTO.EnvioCreadoDTO;
import com.example.finalProject.DTO.EnvioNuevoDTO;
import com.example.finalProject.DTO.EstadoEnvioDTO;
import com.example.finalProject.Modules.Empleado;
import com.example.finalProject.Modules.Envio;
import com.example.finalProject.Services.EnvioService;
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
@Api(value = "Envíos", description = "Controlador de gestión de envíos")
public class EnvioController {
    private EnvioService envioService;

    @Autowired
    public EnvioController(EnvioService envioService){

        this.envioService = envioService;
    }

    @PreAuthorize("hasRole('WRITE')")
    @PostMapping("/envios")
    @ApiOperation(value = "Crear envio", response = EnvioCreadoDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Envío creado exitosamente", response = EnvioCreadoDTO.class),
            @ApiResponse(code = 400, message = "Solicitud incorrecta", response = Exception.class),
            @ApiResponse(code = 401, message = "No autorizado", response = EnvioCreadoDTO.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = EnvioCreadoDTO.class)
    })
    public EnvioCreadoDTO crearEnvio(@RequestBody EnvioNuevoDTO envio) {
        return envioService.crearEnvio(envio);

    }
    @PreAuthorize("hasRole('READ')")
    @GetMapping("/envios")
    @ApiOperation(value = "Obtener lista de los envíos registrados en la base de datos", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Lista de envíos obtenida exitosamente", response = List.class),
            @ApiResponse(code = 401, message = "No autorizado", response = List.class),
            @ApiResponse(code = 403, message = "Prohibido", response = List.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = List.class)
    })
    public List<Envio> obtenerEnvios() {
        return envioService.obtenerEnvios();
    }

    @PreAuthorize("hasRole('READ')")
    @GetMapping("/envios/{idGuia}")
    @ApiOperation(value = "Obtener un envio", response = Envio.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "El envío fue encontrado", response = Envio.class),
            @ApiResponse(code = 400, message = "Solicitud incorrecta", response = Exception.class),
            @ApiResponse(code = 401, message = "No autorizado", response = Empleado.class),
            @ApiResponse(code = 404, message = "Recurso no encontrado o envío inexistente", response = Exception.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = Envio.class)
    })
    public Envio obtenerEnvioPorId(@PathVariable String idGuia){
        return envioService.obtenerEnvio(idGuia);
    }
    @PreAuthorize("hasRole('READ')")
    @GetMapping("/envios/cliente/{cedula}")
    @ApiOperation(value = "Obtener lista de envíos por cédula del cliente", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Lista de envíos obtenida exitosamente", response = List.class),
            @ApiResponse(code = 401, message = "No autorizado", response = List.class),
            @ApiResponse(code = 403, message = "Prohibido", response = List.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = List.class)
    })
    public List<Envio> obtenerEnviosPorCliente(@PathVariable Integer cedula){
        return envioService.obtenerEnviosPorCedula(cedula);
    }

    @PreAuthorize("hasRole('READ')")
    @GetMapping("/envios/estado/")
    @ApiOperation(value = "Obtener lista de envíos por por estado del envío", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Lista de envíos obtenida exitosamente", response = List.class),
            @ApiResponse(code = 401, message = "No autorizado", response = List.class),
            @ApiResponse(code = 403, message = "Prohibido", response = List.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = List.class)
    })
    public List<Envio> obtenerEnviosPorEstado(@RequestParam String estado, @RequestParam Integer cedula ){
        return envioService.obtenerEnviosPorEstado(estado,cedula);
    }

    @PreAuthorize("hasRole('WRITE')")
    @DeleteMapping("/envios/{idGuia}")
    @ApiOperation(value = "Eliminar un envío de la base de datos", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Envío eliminado exitosamente", response = ResponseEntity.class),
            @ApiResponse(code = 401, message = "No autorizado", response = ResponseEntity.class),
            @ApiResponse(code = 403, message = "Prohibido", response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "Recurso no encontrado o envío inexistente", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = ResponseEntity.class)})

    public ResponseEntity eliminar(@PathVariable String idGuia) {
        return envioService.eliminar(idGuia);
    }

    @PreAuthorize("hasRole('WRITE')")
    @PutMapping ("/envios/{idGuia}")
    @ApiOperation(value = "Actualizar estado de un envío", response = EstadoEnvioDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Estado del envío fue actualizado exitosamente", response = EstadoEnvioDTO.class),
            @ApiResponse(code = 400, message = "Solicitud incorrecta", response = Exception.class),
            @ApiResponse(code = 404, message = "El envío no existe", response = Exception.class),
            @ApiResponse(code = 401, message = "No autorizado", response = Exception.class),
            @ApiResponse(code = 500, message = "Error interno del servidor", response = Exception.class)})
    public EstadoEnvioDTO actualizarEnvio(@PathVariable String idGuia, @RequestBody CambioEnvioDTO solicitud)
    {
        return envioService.actualizarEstado(idGuia,solicitud.getEstadoEnvio(),solicitud.getCedulaEmpleado());
    }
}