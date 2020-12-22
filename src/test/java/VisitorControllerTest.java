import com.application.controller.VisitorController;
import com.application.dao.Dao;
import com.application.model.Hall;
import com.application.model.Visitor;
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
        VisitorController.class,
})
@WebAppConfiguration
@EnableWebMvc
@TestPropertySource("classpath:application.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VisitorControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    Dao<Visitor> visitorDao;

    @BeforeAll
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Visitor visitor1 = new Visitor();
        visitor1.setFirstName("Ivan");
        visitor1.setLastName("Ivanov");
        Visitor visitor2 = new Visitor();
        visitor2.setFirstName("Petr");
        visitor2.setLastName("Petrov");
        entityManager.getTransaction().begin();
        entityManager.persist(visitor1);
        entityManager.persist(visitor2);
        entityManager.getTransaction().commit();
    }

    @AfterAll
    public void tearDown() {
        if(entityManager.isOpen()) {
            entityManager.close();
        }
    }

    @Test
    public void findAllVisitors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/visitors/all")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void findVisitorById() throws Exception {
        int visitorId = visitorDao.findAll().get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/visitors/{id}", visitorId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void editVisitor() throws Exception {
        Visitor visitor = visitorDao.findAll().get(0);
        visitor.setLastName("Updated Last Name");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(visitor);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/visitors/edit/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addVisitor() throws Exception {
        Visitor visitor = new Visitor();
        visitor.setFirstName("Roman");
        visitor.setLastName("Romanov");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(visitor);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/visitors/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteVisitor() throws Exception {
        int visitorId = visitorDao.findAll().get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/visitors/delete/{id}", visitorId))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
