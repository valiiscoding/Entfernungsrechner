package com.example.entfernungsrechner.infrastructure;

import com.example.entfernungsrechner.core.entities.Station;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StationCrudRepository extends CrudRepository<Station, Long> {

    /**
     *
     * @param value
     * @return station, where ds100 is
     */
    @Query(value = "select * from haltestelle where ds100 = :value or " +
            "DS100 like CONCAT('%,', :value) or " +
            "DS100 like CONCAT(:value,',%') or " +
            "DS100 like CONCAT('%,',:value,',%')"
            , nativeQuery = true)
    Optional<Station> findByDs100IgnoreCase(@Param("value") String value);
}
