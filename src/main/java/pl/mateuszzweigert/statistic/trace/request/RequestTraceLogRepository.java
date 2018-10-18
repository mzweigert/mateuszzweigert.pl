package pl.mateuszzweigert.statistic.trace.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpMethod;

import java.util.List;

public interface RequestTraceLogRepository extends JpaRepository<RequestTraceLog, String> {

    List<RequestTraceLog> findByRequestUriAndMethod(String requestURI, HttpMethod method);
}
