package pl.mateuszzweigert.config.dev;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.mateuszzweigert.config.Environment;
import pl.mateuszzweigert.site.common.Routes;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(Environment.DEV)
@AutoConfigureMockMvc
public class SecurityConfigDevTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testEndpointsRoute() throws Exception {
        this.mockMvc.perform(get(Routes.ENDPOINTS))
                .andDo(print())
                .andExpect(status().isOk());
    }
}