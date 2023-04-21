package com.example.finalProject.Services;

import com.example.finalProject.Exception.InvalidStatementException;
import com.example.finalProject.Modules.Paquete;
import com.example.finalProject.Modules.TipoPaquete;
import com.example.finalProject.Repositories.PaqueteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaqueteService {

    private PaqueteRepository paqueteRepository;

    @Autowired
    public PaqueteService(PaqueteRepository paqueteRepository){
        this.paqueteRepository = paqueteRepository;
    }

    public Paquete crearPaquete(float peso, double valorDeclarado){
        try {
            TipoPaquete tipo = calcularTipo(peso);
            return this.paqueteRepository.save(new Paquete(tipo,peso,valorDeclarado));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public TipoPaquete calcularTipo(float peso) throws Exception {
        if (peso<0){
            throw new InvalidStatementException("El peso no es válido, ingrese un número mayor a 0");}
        else{
            try {
                if(peso<2.0){
                    return TipoPaquete.LIVIANO;
                }
                else if (peso>2.0 & peso<5.0){
                    return TipoPaquete.MEDIANO;
                }
                else{
                    return TipoPaquete.GRANDE;
                }
            }
            catch (Exception e){
                throw new InvalidStatementException("El peso no es válido", e);
            }
        }
    }

}
