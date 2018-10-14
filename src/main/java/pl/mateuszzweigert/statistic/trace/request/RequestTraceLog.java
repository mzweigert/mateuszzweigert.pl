package pl.mateuszzweigert.statistic.trace.request;

import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.http.HttpMethod;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

@Entity
public class RequestTraceLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty
    private String requestUri;

    @Column(nullable = false)
    private Long visitedCount;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private HttpMethod method;

    @Column(nullable = false)
    private LocalDateTime saveTime;

    public RequestTraceLog() {
    }

    public RequestTraceLog(String requestUri, HttpMethod method) {
        this.requestUri = requestUri;
        this.method = method;
        this.visitedCount = 1L;
    }

    static Comparator<RequestTraceLog> getFieldComparator(String field) {
        Predicate<Method> isGetter = method -> method.getName().equalsIgnoreCase("get" + field);
        Function<Method, Comparator<RequestTraceLog>> mapToComparator =
                method -> Comparator.comparing(l -> {
                    try {
                        return (Comparable) method.invoke(l);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        return (Comparable) l.getVisitedCount();
                    }
                });
        return Arrays.stream(RequestTraceLog.class.getDeclaredMethods())
                .filter(isGetter)
                .findFirst()
                .map(mapToComparator)
                .orElseGet(() -> Comparator.comparingLong(RequestTraceLog::getVisitedCount));
    }

    public static RequestTraceLog from(HttpTrace.Request request) {
        return new RequestTraceLog(request.getUri().toASCIIString(), HttpMethod.resolve(request.getMethod()));
    }

    @PrePersist
    @PreUpdate
    public void beforeSave() {
        this.saveTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public Long getVisitedCount() {
        return visitedCount;
    }

    public void setVisitedCount(Long visitedCount) {
        this.visitedCount = visitedCount;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public LocalDateTime getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(LocalDateTime saveTime) {
        this.saveTime = saveTime;
    }

    void incrementVisitedCount() {
        this.visitedCount += 1L;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestTraceLog)) return false;
        RequestTraceLog log = (RequestTraceLog) o;
        return Objects.equals(getId(), log.getId()) &&
                Objects.equals(getRequestUri(), log.getRequestUri()) &&
                getMethod() == log.getMethod();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRequestUri(), getMethod());
    }

    @Override
    public String toString() {
        return "RequestTraceLog{" +
                "requestUri='" + requestUri + '\'' +
                ", method=" + method +
                '}';
    }

    public Set<ConstraintViolation<RequestTraceLog>> validate() {
        return Validation
                .buildDefaultValidatorFactory()
                .getValidator()
                .validate(this);
    }
}
