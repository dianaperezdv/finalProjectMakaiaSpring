package com.example.finalProject.Controllers;
import com.example.finalProject.DTO.EnvioDTO;
import com.example.finalProject.Services.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("apiMensajeria/v1")
public class EnvioController {
    private EnvioService envioService;

    @Autowired
    public EnvioController(EnvioService envioService){

        this.envioService = envioService;
    }

    @PostMapping("/envios")
    public EnvioDTO crearEnvio(@RequestBody EnvioDTO envio) {
        return envioService.crearEnvio(envio);

    }
   /* @GetMapping("/envios")
    public List<Envio> obtenerEnvio() {
        return envioService.obtenerEnvio();
    }

   @GetMapping("/envios/{cedula}")
    public ResponseEntity<Cliente> getEnviosPorCliente(@PathVariable String cedula){
        try {
            List<Envio> enviosCliente = envioService.obtenerEnviosPorCliente(cedula);
            return ResponseEntity.ok(enviosCliente);
        }
        catch (Exception e){
            return new ResponseEntity("No existe un cliente con esa cédula", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/envios/{id}")
    public String eliminar(@PathVariable("id") String id) {
        return envioService.eliminar(id);
    }

    @PutMapping ("/envios/{id}")
    public ResponseEntity actualizarEnvio(@PathVariable("id") String id, @RequestBody Envio envio)
    {       try {
        return envioService.actualizarEnvio(envio);
    }
    catch (Exception e){
        return new ResponseEntity("No se pudo actualizar", HttpStatus.BAD_REQUEST);
    }
    }
*/}
