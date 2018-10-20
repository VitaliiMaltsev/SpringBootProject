package appPackage;

import appPackage.topics.TopicController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("111")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql",
        "/topics-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/topics-list-after.sql",
        "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TopicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TopicController controller;

    @Test
    public void mainPageTest() throws Exception {
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div[2]/h1").string("Hello 111!"));
    }

    @Test
    public void topicsListTest() throws Exception {
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div[2]/div[3]/div/div").nodeCount(4));
    }

    @Test
    public void filterTopicTest() throws Exception{
        this.mockMvc.perform(get("/main").param("name", "my-name"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id=\"topic-list\"]/div/div").nodeCount(2))
                .andExpect(xpath("/html/body/div[2]/div[3]/div/div[@data-id='html']").exists())
                .andExpect(xpath("/html/body/div[2]/div[3]/div/div[@data-id='javascript']").exists());
    }

    @Test
    public void addTopicToListTest()throws Exception{
        MockHttpServletRequestBuilder multipart = multipart("/main")
                .file("file","123".getBytes())
                .param("name","Name")
                .param("description","new desc")
                .param("id","html")
                .with(csrf());
        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id=\"topic-list\"]/div/div").nodeCount(4))
                .andExpect(xpath("/html/body/div[2]/div[3]/div/div[@data-id='java']").exists())
                .andExpect(xpath("/html/body/div[2]/div[3]/div/div[@data-id='java']/div").string("Name"))
                .andExpect(xpath("/html/body/div[2]/div[3]/div/div[@data-id='java']/div[2]").string("new desc"));

    }


}
