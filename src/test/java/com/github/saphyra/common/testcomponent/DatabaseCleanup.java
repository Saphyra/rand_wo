package com.github.saphyra.common.testcomponent;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@RequiredArgsConstructor
@TestComponent
public class DatabaseCleanup {
    private final List<JpaRepository> repositories;

    public void clearRepositories(){
        repositories.forEach(JpaRepository::deleteAll);
    }
}
