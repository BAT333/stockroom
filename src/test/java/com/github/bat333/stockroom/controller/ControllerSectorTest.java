package com.github.bat333.stockroom.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bat333.stockroom.domain.Sector;
import com.github.bat333.stockroom.model.DataAllSector;
import com.github.bat333.stockroom.model.DataSector;
import com.github.bat333.stockroom.repository.SectorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
class ControllerSectorTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<DataSector> jsonDto;
    @Autowired
    private SectorRepository repository;

    private static final String BASE_URL = "/api/sector";
    private static final ObjectMapper objectMapper = new ObjectMapper();



    @Test
    @DisplayName("Scenario 01: Attempting to register a sector with invalid data (empty JSON) should return 400 Bad Request")
    void registerSectorScenario01() throws Exception {
        //ARRANGE

        String json = "{}";

        //ACT

        var response = mvc.perform(
                post(BASE_URL)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Scenario 02: Successfully registering a sector with valid data should return 201 Created and the correct sector data")
    void registerSectorScenario02() throws Exception {
        //ARRANGE

        DataSector dataSector = new DataSector("sector", "shelf", "column", "rom");

        //ACT
        var response = mvc.perform(
                post(BASE_URL)
                        .content(jsonDto.write(dataSector).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        ObjectMapper objectMapper = new ObjectMapper();

        String responseBody = response.getContentAsString();


        JsonNode jsonNode = objectMapper.readTree(responseBody);

        long id = jsonNode.get("id").asLong();

        var jsonContent = this.ContentEquals(null,new DataAllSector(id, dataSector.sector(), dataSector.shelf(), dataSector.column(), dataSector.row(), List.of()));
        Assertions.assertEquals(201, response.getStatus());
        Assertions.assertEquals(jsonContent, response.getContentAsString());
    }

    @Test
    @DisplayName("Scenario 03: Verifying the behavior when requesting all sectors, expecting an empty list with pagination details")
    void getAllSectorScenario03() throws Exception {
        //ARRANGE
        Sort sort = Sort.by(Sort.Order.asc("id"));
        PageImpl<DataSector> emptyPage = new PageImpl<>(List.of(), PageRequest.of(0, 10,sort), 0);

        //ACT

        var response = mvc.perform(
                get(BASE_URL)
        ).andReturn().getResponse();

        //ASSERT

        String expectedJsonContent = objectMapper.writeValueAsString(emptyPage);
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(expectedJsonContent, response.getContentAsString());
    }

    @Test
    @DisplayName("Scenario 04: Should return sector details when searching by sector ID")
    void getSectorScenario04() throws Exception {
        // ARRANGE
        var sector = this.register();

        // ACT
        var response = mvc.perform(
                get(BASE_URL+"/"+sector.getId())
        ).andReturn().getResponse();

        // ASSERT


        var jsonContent = this.ContentEquals(sector,null);
        Assertions.assertEquals(200, response.getStatus());


        Assertions.assertEquals(jsonContent, response.getContentAsString());
    }

    @Test
    @DisplayName("Scenario 05: Should update sector details when updating by sector ID")
    void updateSectorScenario05() throws Exception {
        // ARRANGE
        var sector = this.register();
        DataSector dataSector = new DataSector("sector2", "shelf2", "column2", "rom2");

        // ACT
        var response = mvc.perform(
                patch(BASE_URL+"/"+sector.getId())
                        .content(jsonDto.write(dataSector).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // ASSERT
        var jsonContent = this.ContentEquals(sector,new DataAllSector(sector.getId(),dataSector.sector(),dataSector.shelf(),dataSector.column(),dataSector.row(),sector.listPart()));

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(jsonContent, response.getContentAsString());
    }


    @Test
    @DisplayName("Scenario 06: Should delete a sector when deleting by sector ID")
    void deleteSectorScenario06() throws Exception {
        // ARRANGE
        var sector = this.register();
        // ACT
        var response = mvc.perform(
                delete(BASE_URL+"/"+sector.getId())
        ).andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(204, response.getStatus());
    }

    private Sector register() {
        DataSector dataSectorNew = new DataSector("sector2", "shelf2", "column2", "rom2");
        return  repository.save(new Sector(dataSectorNew));
    }

    private String ContentEquals(Sector sector, DataAllSector dataSector) throws JsonProcessingException {
        if(dataSector == null){
            return objectMapper.writeValueAsString(new DataAllSector(sector.getId(), sector.getSectors(), sector.getShelf(), sector.getColumn(), sector.getRow(),sector.listPart()));
        }else{
            return objectMapper.writeValueAsString(new DataAllSector(dataSector.id(), dataSector.sector(), dataSector.shelf(), dataSector.column(), dataSector.row(), List.of()));
        }
    }

}