package co.edu.uniandes.dse.parcialprueba.services;

import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.repositories.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Transactional
    public PacienteEntity createPaciente(PacienteEntity paciente) {
        return pacienteRepository.save(paciente);
    }
}
