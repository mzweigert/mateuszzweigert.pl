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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest extends AbstractContextLoadTest<HomeController> {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void firstHomeRoute() throws Exception {
        this.mockMvc.perform(get(Routes.HOME_1))
                .andExpect(status().isOk());
    }

    @Test
    public void secondHomeRoute() throws Exception {
        this.mockMvc.perform(get(Routes.HOME_2))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void thirdHomeRoute() throws Exception {
        this.mockMvc.perform(get(Routes.HOME_3))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
