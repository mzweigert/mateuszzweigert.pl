package pl.mateuszzweigert.site;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.mateuszzweigert.site.common.Routes;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SkillsControllerTest {

    @Autowired
    private SkillsController controller;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testContextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void testSkillsRoute() throws Exception {
        this.mockMvc.perform(get(Routes.SKILLS))
                .andDo(print())
                .andExpect(status().isOk());
    }
}