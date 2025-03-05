package co.edu.uniandes.dse.parcialprueba.services;

import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.entities.ConsultaMedicaEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.PacienteRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.ConsultaMedicaRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Date;
import java.util.ArrayList;

@Service
public class PacienteConsultaService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaMedicaRepository consultaMedicaRepository;

    @Transactional
    public PacienteEntity addConsulta(Long pacienteId, Long consultaId) throws EntityNotFoundException, IllegalOperationException {

        Optional<PacienteEntity> optionalPaciente = pacienteRepository.findById(pacienteId);
        if (!optionalPaciente.isPresent()) {
            throw new EntityNotFoundException("El paciente con ID " + pacienteId + " no existe.");
        }


        Optional<ConsultaMedicaEntity> optionalConsulta = consultaMedicaRepository.findById(consultaId);
        if (!optionalConsulta.isPresent()) {
            throw new EntityNotFoundException("La consulta con ID " + consultaId + " no existe.");
        }

        PacienteEntity paciente = optionalPaciente.get();
        ConsultaMedicaEntity consulta = optionalConsulta.get();

        for (ConsultaMedicaEntity consultaExistente : paciente.getConsultasMedicas()) {
            if (consultaExistente.getFecha().equals(consulta.getFecha())) {
                throw new IllegalOperationException("El paciente ya tiene una consulta programada en esta fecha.");
            }
        }


        paciente.getConsultasMedicas().add(consulta);
        consulta.setPaciente(paciente);
        return pacienteRepository.save(paciente);
    }


    @Transactional
    public List<ConsultaMedicaEntity> getConsultasProgramadas(Long pacienteId) throws EntityNotFoundException {

        Optional<PacienteEntity> optionalPaciente = pacienteRepository.findById(pacienteId);
        if (!optionalPaciente.isPresent()) {
            throw new EntityNotFoundException("El paciente con ID " + pacienteId + " no existe.");
        }

        PacienteEntity paciente = optionalPaciente.get();
        List<ConsultaMedicaEntity> consultasFuturas = new ArrayList<>();

    
        for (ConsultaMedicaEntity consultaMedica : paciente.getConsultasMedicas()) {
            if (consultaMedica.getFecha().after(new Date())) {
                consultasFuturas.add(consultaMedica);
            }
        }

        return consultasFuturas;
    }
}
