package pl.mateuszzweigert.statistic.trace.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.mateuszzweigert.site.common.Routes;

import java.util.*;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/requestTraceLogs.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RequestTraceLogEndpointTest {

    private static final String REQUEST_LOGS_PATH = "/request-logs";

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RequestTraceLogRepository requestTraceLogRepository;

    @Autowired
    private RequestTraceLogEndpoint endpoint;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.endpoint.init();
        initMapper();
    }

    private void initMapper() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    public void init() throws Exception {
        LinkedHashSet<RequestTraceLog> all = new LinkedHashSet<>(requestTraceLogRepository
                .findAll(Sort.by(Sort.Direction.DESC, "saveTime")));

        //WHEN
        this.endpoint.init();

        //THEN
        assertThat(this.endpoint.getAll()).isEqualTo(all);
    }

    @Test
    public void getAll() throws Exception {
        List<RequestTraceLog> all = requestTraceLogRepository
                .findAll(Sort.by(Sort.Direction.DESC, "saveTime"));

        this.mockMvc.perform(get(Routes.ENDPOINTS + REQUEST_LOGS_PATH))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(all)));
    }

    @Test
    public void getAll_whenLogsIsMoreThan100() throws Exception {
        // GIVEN
        for (int i = 0; i < 100; i++) {
            RequestTraceLog log = new RequestTraceLog("test" + i, HttpMethod.GET);
            endpoint.addToLogs(log);
        }

        // WHEN
        String contentAsString = this.mockMvc.perform(get(Routes.ENDPOINTS + REQUEST_LOGS_PATH))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Set<RequestTraceLog> setOfLogs = objectMapper.readValue(contentAsString, new TypeReference<Set<RequestTraceLog>>() {
        });

        // THEN
        assertThat(setOfLogs).isEqualTo(endpoint.getAll());
        assertThat(setOfLogs).hasSize(100);
    }

    @Test
    public void sortedById() throws Exception {
        List<RequestTraceLog> all = requestTraceLogRepository
                .findAll(Sort.by(Sort.Direction.DESC, "id"));

        this.mockMvc.perform(get(Routes.ENDPOINTS + REQUEST_LOGS_PATH + "/id/true"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(all)));
    }

    @Test
    public void sortedByVisitedCount() throws Exception {
        List<RequestTraceLog> all = requestTraceLogRepository
                .findAll(Sort.by(Sort.Direction.DESC, "visitedCount"));

        this.mockMvc.perform(get(Routes.ENDPOINTS + REQUEST_LOGS_PATH + "/visitedCount/true"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(all)));
    }

    @Test
    public void sortedBySaveTime() throws Exception {
        List<RequestTraceLog> all = requestTraceLogRepository
                .findAll(Sort.by(Sort.Direction.DESC, "saveTime"));

        this.mockMvc.perform(get(Routes.ENDPOINTS + REQUEST_LOGS_PATH + "/saveTime/true"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(all)));
    }

    @Test
    public void sortedByNotExistingField() throws Exception {
        List<RequestTraceLog> all = requestTraceLogRepository.findAll();
        TreeSet<RequestTraceLog> requestTraceLogs = Sets.newTreeSet(Comparator
                .comparing(RequestTraceLog::getVisitedCount)
                .reversed());
        requestTraceLogs.addAll(all);

        this.mockMvc.perform(get(Routes.ENDPOINTS + REQUEST_LOGS_PATH + "/notExistingField/true"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(requestTraceLogs)));
    }

    @Test
    public void addToLogs_whenLogNotExist() {
        //GIVEN
        RequestTraceLog log = new RequestTraceLog("new", HttpMethod.DELETE);

        //WHEN
        this.endpoint.addToLogs(log);

        //THEN
        Set<RequestTraceLog> all = this.endpoint.getAll();
        assertThat(all).contains(log);
        List<RequestTraceLog> inRepository = requestTraceLogRepository.findByRequestUriAndMethod(log.getRequestUri(), log.getMethod());
        assertThat(inRepository).isNotEmpty();
        assertThat(inRepository).hasSize(1);
    }

    @Test
    public void addToLogs_whenLogExist() {
        //GIVEN
        RequestTraceLog existing = new RequestTraceLog("requestURI1", HttpMethod.GET);
        Long previousVisitedCount = existing.getVisitedCount();
        //WHEN
        this.endpoint.addToLogs(existing);

        //THEN
        Set<RequestTraceLog> all = this.endpoint.getAll();
        List<RequestTraceLog> existingList = requestTraceLogRepository.findByRequestUriAndMethod("requestURI1", HttpMethod.GET);
        assertThat(all).contains(existingList.get(0));
        assertThat(previousVisitedCount).isLessThan(existingList.get(0).getVisitedCount());
    }

    @Test
    public void addToLogs_whenLogIsDuplicated() {
        //GIVEN
        RequestTraceLog existing = new RequestTraceLog("requestURI1", HttpMethod.GET);
        requestTraceLogRepository.save(new RequestTraceLog("requestURI1", HttpMethod.GET));
        requestTraceLogRepository.save(new RequestTraceLog("requestURI1", HttpMethod.GET));
        requestTraceLogRepository.save(new RequestTraceLog("requestURI1", HttpMethod.GET));

        Long previousVisitedCount = existing.getVisitedCount();
        //WHEN
        this.endpoint.addToLogs(existing);

        //THEN
        Set<RequestTraceLog> all = this.endpoint.getAll();
        List<RequestTraceLog> existingList = requestTraceLogRepository.findByRequestUriAndMethod("requestURI1", HttpMethod.GET);
        assertThat(all).contains(existingList.get(0));
        assertThat(previousVisitedCount).isLessThan(existingList.get(0).getVisitedCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addToLogs_whenLogIsNotValid() {
        //GIVEN
        RequestTraceLog notValid = new RequestTraceLog("", null);

        //WHEN
        this.endpoint.addToLogs(notValid);
    }

}