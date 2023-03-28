package ru.practicum.repository;

import ru.practicum.model.ViewStats;

import java.sql.Timestamp;
import java.util.List;

public interface StatsRepositoryCustom {

    List<ViewStats> getViewStats(Timestamp startDate, Timestamp endDate, List<String> uris, boolean unique);
}
