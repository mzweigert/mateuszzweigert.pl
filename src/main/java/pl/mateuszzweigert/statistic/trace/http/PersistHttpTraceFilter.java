package pl.mateuszzweigert.statistic.trace.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpExchangeTracer;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.web.trace.servlet.HttpTraceFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
public class PersistHttpTraceFilter extends HttpTraceFilter {

	private final String[] excludedEndpoints = new String[]{"/favicon.ico", "/resources/**", "/endpoints/**", "/h2/**"};

	@Autowired
	public PersistHttpTraceFilter(HttpTraceRepository repository, HttpExchangeTracer tracer) {
		super(repository, tracer);
	}

	@Override
	public boolean shouldNotFilter(HttpServletRequest request){
		return Arrays.stream(excludedEndpoints)
				.anyMatch(e -> new AntPathMatcher().match(e, request.getServletPath()));
	}
}
