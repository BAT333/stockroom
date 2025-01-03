package com.github.bat333.stockroom.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bat333.stockroom.domain.Part;
import com.github.bat333.stockroom.domain.Sector;
import com.github.bat333.stockroom.model.*;
import com.github.bat333.stockroom.repository.PartRepository;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
class ControllerPartTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private SectorRepository sectorRepository;
    @Autowired
    private JacksonTester<DataPart> jsonDtoRegister;
    @Autowired
    private JacksonTester<DataUpdatePart> jsonDtoUpdate;

    @Autowired
    private PartRepository partRepository;



    private static final String BASE_URL = "/api/part";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Scenario 01: Attempting to register a part with invalid data should return a 400 Bad Request")
    void partRegistrationScenario01() throws Exception {
        // ARRANGE
        String json = "{}";

        // ACT
        var response = mvc.perform(
                post(BASE_URL + "/1")  // Aqui estamos fazendo o post para a URL específica
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(400, response.getStatus());
    }
    @Test
    @DisplayName("Scenario 02: Registering a part with valid data, including image, should return a 201 Created response")
    void partRegistrationScenario02() throws Exception {
        // ARRANGE

        byte[] imageInBytes = this.img();

        DataPart dataPart = new DataPart(12L, "parafuso", imageInBytes, 6);
        var sector = registerSector();

        // ACT
        var response = mvc.perform(
                post(BASE_URL + "/" + sector.getId())
                        .content(jsonDtoRegister.write(dataPart).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        String responseBody = response.getContentAsString();


        JsonNode jsonNode = objectMapper.readTree(responseBody);

        long id = jsonNode.get("id").asLong();



        // ASSERT
        Assertions.assertEquals(201, response.getStatus());
        //Assertions.assertEquals(jsonContent, response.getContentAsString());

        sector.getParts().forEach(this::partDelete);
        this.sectorDelete(sector.getId());
    }


    @Test
    @DisplayName("Scenario 03: Successfully retrieving a part by its ID from the controller")
    void getPartScenario03() throws Exception {
        // ARRANGE: Criar uma parte
        var part = this.registerPart();

        // ACT: Realizar uma requisição GET para buscar a parte pelo ID
        var response = mvc.perform(
                get(BASE_URL + "/" + part.getId())
        ).andReturn().getResponse();

        // ASSERT: Verificar se a resposta foi bem-sucedida e a parte foi retornada corretamente


        var jsonContent = this.ContentEquals(part);

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(jsonContent, response.getContentAsString());
        this.partDelete(part);
    }




    @Test
    @DisplayName("Scenario 04: Successfully updating a part with new data")
    void updatePartScenario04() throws Exception {
        // ARRANGE: Criar uma parte para ser atualizada
        var part = this.registerPart();
        byte[] imageInBytes = this.img();
        var sector = registerSector();

        // Dados para a atualização da parte
        DataUpdatePart dataUpdatePart = new DataUpdatePart(6446L, "parafuso2", imageInBytes, 797, sector.getId());

        // ACT: Realizar a requisição PATCH para atualizar a parte
        var response = mvc.perform(
                patch(BASE_URL + "/" + part.getId())
                        .content(jsonDtoUpdate.write(dataUpdatePart).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // ASSERT: Verificar se a atualização foi bem-sucedida
        this.partDelete(part);

        var jsonContent = this.ContentEquals(part.update(dataUpdatePart,sector));

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(jsonContent, response.getContentAsString());
        this.sectorDelete(sector.getId());

    }

    @Test
    @DisplayName("Scenario 05: Successfully deleting a part from the controller")
    void deletePartScenario05() throws Exception {
        // ARRANGE: Criar uma parte para ser deletada
        var part = this.registerPart();

        // ACT: Realizar a requisição DELETE para excluir a parte
        var response = mvc.perform(
                delete(BASE_URL + "/" + part.getId())
        ).andReturn().getResponse();

        // ASSERT: Verificar se a parte foi deletada corretamente

        this.partDelete(part);


        // Verifica se o status da resposta foi 204 (No Content), indicando sucesso na exclusão
        Assertions.assertEquals(204, response.getStatus());
    }


    @Test
    @DisplayName("Scenario 06: Successfully retrieving all sectors from the controller")
    void getAllPartScenario06() throws Exception {
        // ARRANGE: Configurações para o teste, incluindo ordenação e página vazia
        Sort sort = Sort.by(Sort.Order.asc("id"));
        PageImpl<DataSector> emptyPage = new PageImpl<>(List.of(), PageRequest.of(0, 10, sort), 0);

        // ACT: Realizar a requisição GET para buscar todos os setores
        var response = mvc.perform(
                get(BASE_URL)
        ).andReturn().getResponse();

        // ASSERT: Verificar se a resposta foi bem-sucedida e o conteúdo esperado
        String expectedJsonContent = objectMapper.writeValueAsString(emptyPage);
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(expectedJsonContent, response.getContentAsString());
    }


    @Test
    @DisplayName("Scenario 07: Searching parts by name and verifying the result in the controller")
    void searchPartScenario07() throws Exception {
        // ARRANGE: Criar duas partes para testar a busca
        Part part1 = this.registerPart();
        Part part2 = this.registerPart();
        Sort sort = Sort.by(Sort.Order.asc("id"));
        PageImpl<DataAllPart> emptyPage = new PageImpl<>(List.of(new DataAllPart(part1),new DataAllPart(part2)), PageRequest.of(0, 10,sort), 0);

        // ACT: Realizar a requisição GET para buscar partes pelo nome
        var response = mvc.perform(
                get(BASE_URL + "/search")
                        .param("name", "name")  // Passando o parâmetro de nome
                        .param("page", "0")
                        .param("size", "10")
        ).andReturn().getResponse();

        // ASSERT: Verificar se a resposta foi bem-sucedida e se as partes estão no conteúdo

        String expectedJsonContent = objectMapper.writeValueAsString(emptyPage);
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertTrue(response.getContentAsString().contains("name"));
        Assertions.assertEquals(expectedJsonContent, response.getContentAsString());

        // Deletar as partes após o teste
        this.partDelete(part1);
        this.partDelete(part2);
    }




    @Test
    @DisplayName("Scenario 08: Searching parts by code and verifying the result in the controller")
    void searchPartScenario08() throws Exception {
        // ARRANGE: Criar uma parte para testar a busca pelo código
        Part part1 = this.registerPart();
        Sort sort = Sort.by(Sort.Order.asc("id"));
        PageImpl<DataAllPart> emptyPage = new PageImpl<>(List.of(new DataAllPart(part1)), PageRequest.of(0, 10,sort), 0);

        // ACT: Realizar a requisição GET para buscar partes pelo código
        var response = mvc.perform(
                get(BASE_URL + "/search")
                        .param("cod", "6")  // Passando o parâmetro de código
                        .param("page", "0")
                        .param("size", "10")
        ).andReturn().getResponse();

        // ASSERT: Verificar se a resposta foi bem-sucedida e se a parte está no conteúdo
        String expectedJsonContent = objectMapper.writeValueAsString(emptyPage);
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertTrue(response.getContentAsString().contains("name"));
        Assertions.assertEquals(expectedJsonContent, response.getContentAsString());

        // Deletar a parte após o teste
        this.partDelete(part1);
    }


    @Test
    @DisplayName("Scenario 09: Searching parts by code and name and verifying the result in the controller")
    void searchPartScenario09() throws Exception {
        // ARRANGE: Criar uma parte para testar a busca pelo código e nome
        Part part1 = this.registerPart();
        Sort sort = Sort.by(Sort.Order.asc("id"));
        PageImpl<DataAllPart> emptyPage = new PageImpl<>(List.of(new DataAllPart(part1)), PageRequest.of(0, 10,sort), 0);

        // ACT: Realizar a requisição GET para buscar partes pelo código e nome
        var response = mvc.perform(
                get(BASE_URL + "/search")
                        .param("cod", "6")  // Passando o parâmetro de código
                        .param("name", "name")  // Passando o parâmetro de nome
                        .param("page", "0")
                        .param("size", "10")
        ).andReturn().getResponse();

        // ASSERT: Verificar se a resposta foi bem-sucedida e se a parte está no conteúdo
        String expectedJsonContent = objectMapper.writeValueAsString(emptyPage);
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertTrue(response.getContentAsString().contains("name"));
        Assertions.assertEquals(expectedJsonContent, response.getContentAsString());

        // Deletar a parte após o teste
        this.partDelete(part1);
    }


    private Sector registerSector() {
        DataSector dataSectorNew = new DataSector("sectorPart", "shelf2", "column2", "rom2");
        return sectorRepository.save(new Sector(dataSectorNew));
    }
    private String ContentEquals(Part part) throws JsonProcessingException {

            return objectMapper.writeValueAsString(new DataAllPart(part.getId(),part.getCod(),part.getName(), Base64.getEncoder().encodeToString(part.getImage()),part.getAmount(),new DataSector(part.getSector())));

    }
    private Part registerPart() throws IOException {
        var sector = this.registerSector();
        return partRepository.save(new Part(new DataPart(6L,"name",this.img(),65),sector,this.img()));
    }
    private void partDelete(Part part){
        this.partRepository.deleteById(part.getId());
        this.sectorDelete(part.getSector().getId());
    }
    private void sectorDelete(Long id){
        this.sectorRepository.deleteById(id);
    }

    private byte[] img() throws IOException {
        File imageFile = new File("src/test/java/com/github/bat333/stockroom/controller/baixados.jpg");
        return Files.readAllBytes(imageFile.toPath());
    }
}