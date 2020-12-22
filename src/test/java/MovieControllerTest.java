import com.application.controller.MovieController;
import com.application.dao.Dao;
import com.application.model.Movie;
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
        MovieController.class,
})
@WebAppConfiguration
@EnableWebMvc
@TestPropertySource("classpath:application.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovieControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    Dao<Movie> movieDao;

    @BeforeAll
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Movie movie1 = new Movie();
        movie1.setMovieName("Movie 1");
        movie1.setRating(9);
        movie1.setGenre("Thriller");
        Movie movie2 = new Movie();
        movie2.setMovieName("Movie 2");
        movie2.setRating(6);
        movie2.setGenre("Comedy");
        entityManager.getTransaction().begin();
        entityManager.persist(movie1);
        entityManager.persist(movie2);
        entityManager.getTransaction().commit();
    }

    @AfterAll
    public void tearDown() {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }

    @Test
    public void findAllMovies() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/movies/all")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void findMovieById() throws Exception {
        int movieId = movieDao.findAll().get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/movies/{id}", movieId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void editMovie() throws Exception {
        Movie movie = movieDao.findAll().get(0);
        movie.setRating(6);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(movie);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/movies/edit/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addMovie() throws Exception {
        Movie movie = new Movie();
        movie.setMovieName("Movie 3");
        movie.setRating(5);
        movie.setGenre("Fantasy");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(movie);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/movies/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteMovie() throws Exception {
        int movieId = movieDao.findAll().get(0).getId();;
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/movies/delete/{id}", movieId))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
