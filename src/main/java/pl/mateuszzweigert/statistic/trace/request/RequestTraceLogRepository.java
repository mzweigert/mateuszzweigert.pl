package pl.mateuszzweigert.statistic.trace.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpMethod;

interface RequestTraceLogRepository extends JpaRepository<RequestTraceLog, String> {

    RequestTraceLog findByRequestUriAndMethod(String requestURI, HttpMethod method);
}
