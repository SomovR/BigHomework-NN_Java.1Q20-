import com.application.controller.TicketController;
import com.application.dao.Dao;
import com.application.model.Ticket;
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
        TicketController.class,
})
@WebAppConfiguration
@EnableWebMvc
@TestPropertySource("classpath:application.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TicketControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    Dao<Ticket> ticketDao;

    @BeforeAll
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Ticket ticket1 = new Ticket();
        ticket1.setCost(250);
        Ticket ticket2 = new Ticket();
        ticket2.setCost(250);
        entityManager.getTransaction().begin();
        entityManager.persist(ticket1);
        entityManager.persist(ticket2);
        entityManager.getTransaction().commit();
    }

    @AfterAll
    public void tearDown() {
        if(entityManager.isOpen()) {
            entityManager.close();
        }
    }

    @Test
    public void findAllTickets() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/tickets/all")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void findTicketById() throws Exception {
        int ticketId = ticketDao.findAll().get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/tickets/{id}", ticketId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void editTicket() throws Exception {
        Ticket ticket = ticketDao.findAll().get(0);
        ticket.setCost(550);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(ticket);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/tickets/edit/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addTicket() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setCost(100);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(ticket);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/tickets/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTicket() throws Exception {
        int ticketId = ticketDao.findAll().get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/tickets/delete/{id}", ticketId))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
