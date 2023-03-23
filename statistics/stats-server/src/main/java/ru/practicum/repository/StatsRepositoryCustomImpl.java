package ru.practicum.repository;

import ru.practicum.model.ViewStats;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.List;

public class StatsRepositoryCustomImpl implements StatsRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ViewStats> getViewStats(Timestamp startDate, Timestamp endDate, List<String> uris, boolean unique) {
        String jpql = "SELECT new ru.practicum.model.ViewStats(e.app, e.uri, "
                + (unique ? "COUNT(DISTINCT e.ip)" : "COUNT(e.ip)") +
                ") FROM EndpointHit e " +
                " WHERE e.timestamp BETWEEN :startDate AND :endDate " +
                (uris != null && !uris.isEmpty() ? "AND e.uri IN :uris " : "") +
                " GROUP BY e.app, e.uri";

        TypedQuery<ViewStats> query = entityManager.createQuery(jpql, ViewStats.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        if (uris != null && !uris.isEmpty()) {
            query.setParameter("uris", uris);
        }

        return query.getResultList();
    }
}
