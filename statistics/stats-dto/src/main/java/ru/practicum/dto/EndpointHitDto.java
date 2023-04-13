package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class EndpointHitDto {

    private Long id;

    @NotBlank
    private String app;

    @NotBlank
    private String uri;

    @NotBlank
    private String ip;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public static EndpointHitDto create(String appName, HttpServletRequest request) {
        EndpointHitDto endpointHitDto = new EndpointHitDto();
        endpointHitDto.setApp(appName);
        endpointHitDto.setIp(request.getRemoteAddr());
        endpointHitDto.setUri(request.getRequestURI());
        endpointHitDto.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        return endpointHitDto;
    }
}
