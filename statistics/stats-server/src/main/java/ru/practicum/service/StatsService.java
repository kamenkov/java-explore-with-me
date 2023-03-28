package ru.practicum.service;

import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.mapper.EndpointHitMapper;
import ru.practicum.mapper.ViewStatsMapper;
import ru.practicum.model.EndpointHit;
import ru.practicum.repository.StatsRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatsService {

    private final StatsRepository statsRepository;

    private final EndpointHitMapper endpointHitMapper;
    private final ViewStatsMapper viewStatsMapper;

    public StatsService(StatsRepository statsRepository,
                        EndpointHitMapper endpointHitMapper,
                        ViewStatsMapper viewStatsMapper) {
        this.statsRepository = statsRepository;
        this.endpointHitMapper = endpointHitMapper;
        this.viewStatsMapper = viewStatsMapper;
    }

    public EndpointHitDto create(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = endpointHitMapper.mapToEndpointHit(endpointHitDto);
        endpointHit = statsRepository.save(endpointHit);
        return endpointHitMapper.mapToEndpointHitDto(endpointHit);
    }

    public List<ViewStatsDto> getHits(Timestamp start, Timestamp end, List<String> uris, Boolean unique) {
        return statsRepository.getViewStats(start, end, uris, unique).stream()
                .map(viewStatsMapper::mapToViewStatsDto)
                .collect(Collectors.toList());
    }
}
