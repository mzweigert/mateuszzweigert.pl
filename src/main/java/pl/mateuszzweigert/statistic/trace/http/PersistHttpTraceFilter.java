package pl.mateuszzweigert.statistic.trace.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.trace.http.HttpExchangeTracer;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.web.trace.servlet.HttpTraceFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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
            File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "request_logs_blacklist");
            List<String> content = Files.readAllLines(file.toPath());
            if (!content.isEmpty()) {
                String[] excludedFromFile = String.join("", Files.readAllLines(file.toPath())).split("\\s*(,|\\s)\\s*");
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
