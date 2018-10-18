package pl.mateuszzweigert.statistic.trace.http;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.mateuszzweigert.site.common.Routes;
import pl.mateuszzweigert.statistic.trace.request.RequestTraceLog;
import pl.mateuszzweigert.statistic.trace.request.RequestTraceLogRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersistHttpTraceFilterTest {

    @Autowired
    private RequestTraceLogRepository requestTraceLogRepository;

    @Autowired
    private PersistHttpTraceFilter persistHttpTraceFilter;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenExcludedPath_whenPerformGetAction_thenNotPersistedInDatabase() throws Exception {
        // GIVEN
        String excludedPath = Routes.ENDPOINTS;

        // WHEN
        this.mockMvc.perform(get(excludedPath).servletPath(excludedPath));

        // THEN
        Optional<RequestTraceLog> notFound = requestTraceLogRepository.findAll()
                .stream()
                .filter(log -> log.getRequestUri().contains(excludedPath))
                .findFirst();
        assertThat(notFound.isPresent()).isFalse();
    }

    @Test
    public void givenNotExcludedPath_whenPerformGetAction_thenPersistedInDatabase() throws Exception {
        // GIVEN
        String path = Routes.CONTACT;

        // WHEN
        this.mockMvc.perform(get(path).servletPath(path));

        // THEN
        Optional<RequestTraceLog> notFound = requestTraceLogRepository.findAll()
                .stream()
                .filter(log -> log.getRequestUri().contains(path))
                .findFirst();
        assertThat(notFound.isPresent()).isTrue();
    }
}