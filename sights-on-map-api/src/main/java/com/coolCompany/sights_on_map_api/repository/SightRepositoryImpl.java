package com.coolCompany.sights_on_map_api.repository;

import com.coolCompany.sights_on_map_api.entity.SightEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SightRepositoryImpl implements SightRepositoryCustom {

    private final EntityManager entityManager;

    @Override
    public List<SightEntity> findSightsNearby(double latitude, double longitude, double radiusMeters,
                                              String category, Double minAverageRating, int limit) {
        StringBuilder sql = new StringBuilder("""
                SELECT s.* FROM sight s
                LEFT JOIN (
                    SELECT sight_id, AVG(estimation) as avg_rating
                    FROM feedback
                    GROUP BY sight_id
                ) f ON s.id = f.sight_id
                WHERE ST_DWithin(s.position, ST_MakePoint(:lon, :lat)::geography, :radius)
            """);

        if (category != null) {
            sql.append(" AND s.category = :category");
        }
        if (minAverageRating != null) {
            sql.append(" AND (f.avg_rating IS NOT NULL AND f.avg_rating >= :minRating)");
        }

        sql.append(" ORDER BY ST_Distance(s.position, ST_MakePoint(:lon, :lat)::geography) ASC");

        Query query = entityManager.createNativeQuery(sql.toString(), SightEntity.class);
        query.setParameter("lat", latitude);
        query.setParameter("lon", longitude);
        query.setParameter("radius", radiusMeters);
        if (category != null) query.setParameter("category", category);
        if (minAverageRating != null) query.setParameter("minRating", minAverageRating);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    @Override
    public List<SightEntity> findSightsByCity(String city, String category, Double minAverageRating) {
        StringBuilder sql = new StringBuilder("""
                SELECT s.* FROM sight s
                LEFT JOIN (
                    SELECT sight_id, AVG(estimation) as avg_rating
                    FROM feedback
                    GROUP BY sight_id
                ) f ON s.id = f.sight_id
                WHERE lower(s.city) = lower(:city)
            """);

        if (category != null) {
            sql.append(" AND s.category = :category");
        }
        if (minAverageRating != null) {
            sql.append(" AND (f.avg_rating IS NOT NULL AND f.avg_rating >= :minRating)");
        }

        sql.append(" ORDER BY f.avg_rating DESC NULLS LAST");

        Query query = entityManager.createNativeQuery(sql.toString(), SightEntity.class);
        query.setParameter("city", city);
        if (category != null) query.setParameter("category", category);
        if (minAverageRating != null) query.setParameter("minRating", minAverageRating);

        return query.getResultList();
    }

    @Override
    public List<SightEntity> findByNameFiltered(String name, String category, Double minAverageRating) {
        StringBuilder sql = new StringBuilder("""
                SELECT s.* FROM sight s
                LEFT JOIN (
                    SELECT sight_id, AVG(estimation) as avg_rating
                    FROM feedback
                    GROUP BY sight_id
                ) f ON s.id = f.sight_id
                WHERE lower(s.name) = lower(:name)
            """);

        if (category != null) {
            sql.append(" AND s.category = :category");
        }
        if (minAverageRating != null) {
            sql.append(" AND (f.avg_rating IS NOT NULL AND f.avg_rating >= :minRating)");
        }

        Query query = entityManager.createNativeQuery(sql.toString(), SightEntity.class);
        query.setParameter("name", name);
        if (category != null) query.setParameter("category", category);
        if (minAverageRating != null) query.setParameter("minRating", minAverageRating);

        return query.getResultList();
    }
}
