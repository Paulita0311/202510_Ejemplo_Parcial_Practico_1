package co.edu.uniandes.dse.parcialprueba.services;

import co.edu.uniandes.dse.parcialprueba.entities.ConsultaMedicaEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.ConsultaMedicaRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

@Service
public class ConsultaMedicaService {

    @Autowired
    private ConsultaMedicaRepository consultaMedicaRepository;

    @Transactional
    public ConsultaMedicaEntity createConsulta(ConsultaMedicaEntity consulta) throws IllegalOperationException {
        if (!consulta.getFecha().after(new Date())) {
            throw new IllegalOperationException("La fecha de la consulta debe ser mayor a la fecha actual.");
        }
        return consultaMedicaRepository.save(consulta);
    }
}
