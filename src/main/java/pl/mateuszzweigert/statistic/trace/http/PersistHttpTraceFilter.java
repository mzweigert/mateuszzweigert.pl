package pl.mateuszzweigert.statistic.trace.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.trace.http.HttpExchangeTracer;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.web.trace.servlet.HttpTraceFilter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.io.File;

@Component
public class PersistHttpTraceFilter extends HttpTraceFilter {

    private static String[] excludedPaths = new String[]{"/favicon.ico", "/resources/**", "/endpoints/**", "/h2/**"};

    @Value("${domain.name}")
    private String domainName;

    @Autowired
    public PersistHttpTraceFilter(HttpTraceRepository repository, HttpExchangeTracer tracer) {
        super(repository, tracer);
        readExcludedPathsFromFile();
    }

    private void readExcludedPathsFromFile() {
        try {
            ClassPathResource cpr = new ClassPathResource("/request_logs_blacklist.txt");
            byte[] bytes = FileCopyUtils.copyToByteArray(cpr.getInputStream());
            String content = new String(bytes, StandardCharsets.UTF_8);
            if (!content.isEmpty()) {
                String[] excludedFromFile = String.join("", content).split("\\s*(,|\\s)\\s*");
                excludedPaths = Stream.concat(Arrays.stream(excludedPaths), Arrays.stream(excludedFromFile))
                        .distinct()
                        .toArray(String[]::new);
            }

        } catch (IOException e) {
            logger.error("Could not find blacklist of request logs. Load default list");
            e.printStackTrace();
        }
    }

    @Override
    public boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURL().toString().contains(domainName) &&
                Arrays.stream(excludedPaths)
                        .anyMatch(e -> new AntPathMatcher().match(e, request.getServletPath()));
    }
}
