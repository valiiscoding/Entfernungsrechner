package com.example.entfernungsrechner.infrastructure.setup;

import com.example.entfernungsrechner.core.entities.Station;
import com.example.entfernungsrechner.infrastructure.StationCrudRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;


@Component
public class CsvInitialization {

    private final StationCrudRepository stationCrudRepository;

    @Value("${csv_path}")
    private String csv_path;

    public CsvInitialization(StationCrudRepository stationCrudRepository) {
        this.stationCrudRepository = stationCrudRepository;
    }

    @PostConstruct
    public void readCsvInRepository() throws IOException {
        Reader in = new FileReader(csv_path);

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
