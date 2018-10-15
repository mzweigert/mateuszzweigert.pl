package pl.mateuszzweigert.statistic.trace.request;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Endpoint(id = "request-logs")
@Component
public class RequestTraceLogEndpoint {

    private static final int LOGS_CAPACITY = 100;
    private static final Logger logger = LoggerFactory.getLogger(RequestTraceLogEndpoint.class);

    private RequestTraceLogRepository traceLogRepository;
    private Set<RequestTraceLog> logs;

    @Autowired
    public RequestTraceLogEndpoint(RequestTraceLogRepository traceLogRepository) {
        this.traceLogRepository = traceLogRepository;
    }

    @PostConstruct
    public void init() {
        this.logs = this.traceLogRepository
                .findAll(Sort.by(Sort.Direction.DESC, "saveTime"))
                .stream()
                .limit(100)
                .collect(Collectors.toCollection(
                        () -> Sets.newLinkedHashSetWithExpectedSize(LOGS_CAPACITY))
                );
    }

    @ReadOperation
    public Set<RequestTraceLog> getAll() {
        return Sets.newLinkedHashSet(logs);
    }

    @ReadOperation
    public Set<RequestTraceLog> sortedBy(@Selector String field, @Selector Boolean desc) {
        Comparator<RequestTraceLog> comparator = RequestTraceLog.getFieldComparator(field);
        return logs.stream()
                .sorted(desc ? comparator.reversed() : comparator)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public void addToLogs(RequestTraceLog log) {
        if (log == null) {
            logger.error("Given log cannot be null!");
            return;
        }

        Set<ConstraintViolation<RequestTraceLog>> violations = log.validate();
        if (!violations.isEmpty()) {
            for (ConstraintViolation<RequestTraceLog> violation : violations) {
                logger.error(violation.getMessage());
            }
            throw new IllegalArgumentException("Some field of " + log.toString() + " is empty or null!");
        }

        log = persistLog(log);
        logs = reorganizeMemoryLogs(log);
    }

    private RequestTraceLog persistLog(RequestTraceLog log) {
        String requestURI = log.getRequestUri();
        HttpMethod method = log.getMethod();
        List<RequestTraceLog> possibleDuplicates = traceLogRepository.findByRequestUriAndMethod(requestURI, method);
        if(!possibleDuplicates.isEmpty()) {
            log = removeDuplicatesAndReturnUnique(possibleDuplicates);
            log.incrementVisitedCount();
        }
        return traceLogRepository.save(log);
    }

    private RequestTraceLog removeDuplicatesAndReturnUnique(List<RequestTraceLog> possibleDuplicates) {
        logger.error("Found duplicates request trace logs : " + possibleDuplicates.toString());
        if(possibleDuplicates.size() > 1) {
            possibleDuplicates.sort(RequestTraceLog.getFieldComparator("saveTime").reversed());
            List<RequestTraceLog> toRemove = possibleDuplicates.subList(1, possibleDuplicates.size());
            logs.removeAll(toRemove);
            traceLogRepository.deleteAll(toRemove);
        }
        return possibleDuplicates.get(0);
    }

    private Set<RequestTraceLog> reorganizeMemoryLogs(RequestTraceLog log) {
        Set<RequestTraceLog> cut = logs.size() < LOGS_CAPACITY ?
                logs : logs.stream()
                .limit(LOGS_CAPACITY - 1)
                .collect(Collectors.toSet());

        Set<RequestTraceLog> newLogs = Sets.newLinkedHashSetWithExpectedSize(LOGS_CAPACITY);
        newLogs.add(log);
        newLogs.addAll(cut);
        return newLogs;
    }

}
