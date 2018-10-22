package pl.mateuszzweigert.site;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.mateuszzweigert.site.common.Routes;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PrivacyPolicyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void privacyPolicyRoute_WithFractalRealityAppName() throws Exception {
        this.mockMvc.perform(get(Routes.PRIVACY_POLICY + "/fractal-reality"))
                .andExpect(status().isOk());
    }

    @Test
    public void privacyPolicyRoute_WithTapTheDotsAppName() throws Exception {
        this.mockMvc.perform(get(Routes.PRIVACY_POLICY + "/tap-the-dots"))
                .andExpect(status().isOk());
    }

    @Test
    public void privacyPolicyRoute_WithNotExistingAppName() throws Exception {
        this.mockMvc.perform(get(Routes.PRIVACY_POLICY + "/NotExistingApp"))
                .andExpect(status().isNotFound());
    }
}