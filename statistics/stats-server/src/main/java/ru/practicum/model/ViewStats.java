package ru.practicum.model;

public class ViewStats {

    private String app;

    private String url;

    private Long hits;

    public ViewStats() {
    }

    public ViewStats(String app, String url, Long hits) {
        this.app = app;
        this.url = url;
        this.hits = hits;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getHits() {
        return hits;
    }

    public void setHits(Long hits) {
        this.hits = hits;
    }

}
