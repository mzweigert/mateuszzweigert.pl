package pl.mateuszzweigert.statistic.trace.request;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/requestTraceLogs.sql")
public class RequestTraceLogRepositoryTest {

    @Autowired
    private RequestTraceLogRepository repository;

    @Test
    public void findByRequestURIAndMethod_whenLogExists() {
        // GIVEN
        String requestUri = "requestURI5";
        HttpMethod method = HttpMethod.OPTIONS;

        // WHEN
        List<RequestTraceLog> result = repository.findByRequestUriAndMethod(requestUri, method);

        // THEN
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getVisitedCount()).isEqualTo(5);
    }

    @Test
    public void findByRequestURIAndMethod_whenLogNotExists() {

        // GIVEN
        String requestUri = "notExists";
        HttpMethod method = HttpMethod.OPTIONS;

        // WHEN
        List<RequestTraceLog> result = repository.findByRequestUriAndMethod(requestUri, method);

        // THEN
        assertThat(result).isEmpty();
    }
}