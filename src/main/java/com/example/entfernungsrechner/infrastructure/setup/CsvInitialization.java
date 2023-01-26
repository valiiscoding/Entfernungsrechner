package com.example.entfernungsrechner.infrastructure.setup;

import com.example.entfernungsrechner.core.entities.Station;
import com.example.entfernungsrechner.infrastructure.StationCrudRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.UUID;


@Repository
public class CsvInitialization {

    private final StationCrudRepository stationCrudRepository;

    public CsvInitialization(StationCrudRepository stationCrudRepository) {
        this.stationCrudRepository = stationCrudRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void readCsvInRepository() throws IOException {
        Reader in = new FileReader("src/main/resources/D_Bahnhof_2020_alle.csv");

        Iterable<CSVRecord> records = CSVFormat.EXCEL.builder().setDelimiter(';')
                .setIgnoreHeaderCase(true)
                .setHeader()
                .setSkipHeaderRecord(false)
                .setAllowDuplicateHeaderNames(true).build().parse(in);

        for (CSVRecord record : records) {
            if (record.get("Verkehr").equals("FV")) {
                Station station = Station.builder()
                        .evaNr(Long.parseLong(record.get("EVA_NR")))
                        .ds100(Arrays.asList(record.get("DS100").split(",")))
                        .latitude(Double.parseDouble(record.get("breite").replace(",", ".")))
                        .longitude(Double.parseDouble(record.get("laenge").replace(",", ".")))
                        .ifOpt(record.get("ifopt"))
                        .name(record.get("name"))
                        .operatorName(record.get("betreiber_name"))
                        .operatorNr(record.get("betreiber_nr").isEmpty() ? null : Long.parseLong(record.get("betreiber_nr")))
                        .status(record.get("status").isEmpty() ? null : record.get("status"))
                        .traffic(record.get("verkehr"))
                        .build();

                stationCrudRepository.save(station);
            }


        }
    }
}
