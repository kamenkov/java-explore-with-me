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
        String count = unique ? "COUNT(DISTINCT e.ip)" : "COUNT(e.ip)";
        String jpql = "SELECT new ru.practicum.model.ViewStats(e.app, e.uri, " + count
                + ") FROM EndpointHit e " +
                " WHERE e.timestamp BETWEEN :startDate AND :endDate " +
                (uris != null && !uris.isEmpty() ? "AND e.uri IN :uris " : "") +
                " GROUP BY e.app, e.uri ORDER BY " + count + " DESC";

        TypedQuery<ViewStats> query = entityManager.createQuery(jpql, ViewStats.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        if (uris != null && !uris.isEmpty()) {
            query.setParameter("uris", uris);
        }

        return query.getResultList();
    }
}
