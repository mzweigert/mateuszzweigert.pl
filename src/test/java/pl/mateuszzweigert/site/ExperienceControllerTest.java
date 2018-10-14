package pl.mateuszzweigert.site;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.mateuszzweigert.site.common.Routes;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExperienceControllerTest extends AbstractContextLoadTest<ExperienceController> {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void experienceRoute() throws Exception {
        this.mockMvc.perform(get(Routes.EXPERIENCE))
                .andExpect(status().isOk());
    }
}