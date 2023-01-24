package com.example.entfernungsrechner.infrastructure;

import com.example.entfernungsrechner.core.entities.Haltestelle;
import com.example.entfernungsrechner.core.repo_abstractions.IHaltestellenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HaltestellenRepositoryTest {

    @Autowired
    HaltestellenCrudRepository haltestellenCrudRepository;

    @Test
    void testeIstFrankfurtInDatabase() {
        Iterable<Haltestelle> all = haltestellenCrudRepository.findAll();
        //todo man könnte hier testen ob 6423 oder wie viele einträge gefunden werden / ob die db funzt.
    }


}
