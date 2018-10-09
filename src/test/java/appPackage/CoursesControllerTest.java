package appPackage;

import appPackage.courses.Course;
import appPackage.courses.CoursesController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("111")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql",
              "/topics-list-before.sql",
              "/courses-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/courses-list-after.sql",
              "/topics-list-after.sql",
              "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CoursesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CoursesController controller;


    @Test
    public void mainPageTest() throws Exception {

        this.mockMvc.perform(get("/topics/java/courses"))
                .andDo(print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div[2]/h1")
                        .string("Здравствуй, 111! "));
    }

    @Test
    public void coursesListTest() throws Exception {

        this.mockMvc.perform(get("/topics/java/courses"))
                .andDo(print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.xpath("//*[@id=\"courses-list\"]/div/div")
                        .nodeCount(4));
    }
}