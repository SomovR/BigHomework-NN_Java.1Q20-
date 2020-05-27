import com.application.controller.HallController;
import com.application.dao.Dao;
import com.application.model.Hall;
import com.application.utils.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManager;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        Config.class,
        HallController.class,
})
@WebAppConfiguration
@EnableWebMvc
@TestPropertySource("classpath:application.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HallControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    Dao<Hall> hallDao;

    @BeforeAll
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Hall hall1 = new Hall();
        hall1.setHallType("3D");
        hall1.setNumberOfSeats(200);
        Hall hall2 = new Hall();
        hall2.setHallType("IMAX");
        hall2.setNumberOfSeats(80);
        entityManager.getTransaction().begin();
        entityManager.persist(hall1);
        entityManager.persist(hall2);
        entityManager.getTransaction().commit();
    }

    @AfterAll
    public void tearDown() {
        if(entityManager.isOpen()) {
            entityManager.close();
        }
    }

    @Test
    public void findAllHalls() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/halls/all")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void findHallById() throws Exception {
        int hallId = hallDao.findAll().get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/halls/{id}", hallId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void editHall() throws Exception {
        Hall hall = hallDao.findAll().get(0);
        hall.setHallType("4D");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(hall);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/halls/edit/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addHall() throws Exception {
        Hall hall = new Hall();
        hall.setHallType("2D");
        hall.setNumberOfSeats(100);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(hall);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/halls/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteHall() throws Exception {
        int hallId = hallDao.findAll().get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/halls/delete/{id}", hallId))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
