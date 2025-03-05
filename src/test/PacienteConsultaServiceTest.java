package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.entities.ConsultaMedicaEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.PacienteRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.ConsultaMedicaRepository;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Date;

@DataJpaTest
@Transactional
@Import(PacienteConsultaService.class)
class PacienteConsultaServiceTest {

    @Autowired
    private PacienteConsultaService pacienteConsultaService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();
    private PacienteEntity paciente;
    private ConsultaMedicaEntity consulta1;
    private ConsultaMedicaEntity consulta2;

    @BeforeEach
    void setUp() {
        entityManager.getEntityManager().createQuery("delete from ConsultaMedicaEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from PacienteEntity").executeUpdate();

        paciente = factory.manufacturePojo(PacienteEntity.class);
        entityManager.persist(paciente);


        consulta2 = factory.manufacturePojo(ConsultaMedicaEntity.class);
        consulta2.setFecha(consulta1.getFecha()); 
        entityManager.persist(consulta2);
    }

    @Test
    void testAddConsultaExitoso() throws EntityNotFoundException, IllegalOperationException {
        PacienteEntity result = pacienteConsultaService.addConsulta(paciente.getId(), consulta1.getId());
        assertNotNull(result);
        assertEquals(1, result.getConsultasMedicas().size());
    }

    @Test
    void testAddConsultaFallaFechaDuplicada() throws EntityNotFoundException, IllegalOperationException {

        pacienteConsultaService.addConsulta(paciente.getId(), consulta1.getId());

    }
}
