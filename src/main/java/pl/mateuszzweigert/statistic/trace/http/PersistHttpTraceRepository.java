package pl.mateuszzweigert.statistic.trace.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.stereotype.Repository;
import pl.mateuszzweigert.statistic.trace.request.RequestTraceLog;
import pl.mateuszzweigert.statistic.trace.request.RequestTraceLogEndpoint;

@Repository
public class PersistHttpTraceRepository extends InMemoryHttpTraceRepository {

    private RequestTraceLogEndpoint requestTraceLogEndpoint;

    @Autowired
    public PersistHttpTraceRepository(RequestTraceLogEndpoint requestTraceLogEndpoint) {
        this.requestTraceLogEndpoint = requestTraceLogEndpoint;
    }

    @Override
    public void add(HttpTrace trace) {
        super.add(trace);
        RequestTraceLog log = RequestTraceLog.from(trace.getRequest());
        requestTraceLogEndpoint.addToLogs(log);
    }

}
