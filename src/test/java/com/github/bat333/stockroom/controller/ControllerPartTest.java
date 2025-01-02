package com.github.bat333.stockroom.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ControllerPartTest {

    @Test
    @DisplayName("Scenario 01 Registering Part in the Controller")
    void partRegistration() {
    }

    @Test
    @DisplayName("Scenario 02 Searching by Part in controller")
    void getPart() {
    }

    @Test
    @DisplayName("Scenario 03 Part Update")
    void updatePart() {
    }

    @Test
    @DisplayName("Scenario 04 Part Delete")
    void deletePart() {
    }

    @Test
    @DisplayName("Scenario 05 Searching for all sectors in controller")
    void getAllPart() {
    }

    @Test
    @DisplayName("Scenario 06 Search Parts by Cod and Name in Controller")
    void searchPart() {
    }
}