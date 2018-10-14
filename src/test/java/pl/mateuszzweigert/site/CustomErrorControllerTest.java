package pl.mateuszzweigert.site;

import net.bytebuddy.utility.RandomString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomErrorControllerTest extends AbstractContextLoadTest<CustomErrorController> {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void errorRoute() throws Exception {
        this.mockMvc.perform(get(RandomString.make(15)))
                .andExpect(status().isNotFound());
    }

}
