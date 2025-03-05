package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.ConsultaMedicaEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.ConsultaMedicaRepository;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Date;

@DataJpaTest
@Transactional
@Import(ConsultaMedicaService.class)
class ConsultaMedicaServiceTest {

    @Autowired
    private ConsultaMedicaService consultaMedicaService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    @BeforeEach
    void setUp() {
        entityManager.getEntityManager().createQuery("delete from ConsultaMedicaEntity").executeUpdate();
    }

    @Test
    void testCreateConsultaExitoso() throws IllegalOperationException {
        ConsultaMedicaEntity consulta = factory.manufacturePojo(ConsultaMedicaEntity.class);
        consulta.setFecha(new Date()); 

        ConsultaMedicaEntity result = consultaMedicaService.createConsulta(consulta);
        assertNotNull(result);

        ConsultaMedicaEntity entity = entityManager.find(ConsultaMedicaEntity.class, result.getId());
        assertEquals(consulta.getFecha(), entity.getFecha());
    }

    @Test
    void testCreateConsultaFallaFechaPasada() {
        ConsultaMedicaEntity consulta = factory.manufacturePojo(ConsultaMedicaEntity.class);
        consulta.setFecha(new Date()); 

        assertThrows(IllegalOperationException.class, () -> {
            consultaMedicaService.createConsulta(consulta);
        });
    }
}

