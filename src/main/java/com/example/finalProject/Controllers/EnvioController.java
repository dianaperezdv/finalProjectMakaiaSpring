package com.example.finalProject.Controllers;
import com.example.finalProject.DTO.CambioEnvioDTO;
import com.example.finalProject.DTO.EnvioCreadoDTO;
import com.example.finalProject.DTO.EnvioNuevoDTO;
import com.example.finalProject.DTO.EstadoEnvioDTO;
import com.example.finalProject.Modules.Envio;
import com.example.finalProject.Services.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("apiMensajeria/v1")
public class EnvioController {
    private EnvioService envioService;

    @Autowired
    public EnvioController(EnvioService envioService){

        this.envioService = envioService;
    }

    @PreAuthorize("hasRole('WRITE')")
    @PostMapping("/envios")
    public EnvioCreadoDTO crearEnvio(@RequestBody EnvioNuevoDTO envio) {
        return envioService.crearEnvio(envio);

    }
    @PreAuthorize("hasRole('READ')")
    @GetMapping("/envios")
    public List<Envio> obtenerEnvios() {
        return envioService.obtenerEnvios();
    }

    @PreAuthorize("hasRole('READ')")
    @GetMapping("/envios/{idGuia}")
    public Envio obtenerEnvioPorId(@PathVariable String idGuia){
        return envioService.obtenerEnvio(idGuia);
    }
    @PreAuthorize("hasRole('READ')")
    @GetMapping("/envios/cliente/{cedula}")
    public List<Envio> obtenerEnviosPorCliente(@PathVariable Integer cedula){
        return envioService.obtenerEnviosPorCedula(cedula);
    }

    @PreAuthorize("hasRole('READ')")
    @GetMapping("/envios/{cedula}")
    public List<Envio> obtenerEnviosPorEstado(@RequestParam String estado, @PathVariable Integer cedula){
        return envioService.obtenerEnviosPorEstado(estado,cedula);
    }

    @PreAuthorize("hasRole('WRITE')")
    @DeleteMapping("/envios/{idGuia}")
    public ResponseEntity eliminar(@PathVariable String idGuia) {
        return envioService.eliminar(idGuia);
    }

    @PreAuthorize("hasRole('WRITE')")
    @PutMapping ("/envios/{idGuia}")
    public EstadoEnvioDTO actualizarEnvio(@PathVariable String idGuia, @RequestBody CambioEnvioDTO solicitud)
    {
        return envioService.actualizarEstado(idGuia,solicitud.getEstadoEnvio(),solicitud.getCedulaEmpleado());
    }
}