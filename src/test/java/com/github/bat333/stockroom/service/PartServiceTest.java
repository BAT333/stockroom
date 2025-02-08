package com.github.bat333.stockroom.service;

import com.github.bat333.stockroom.domain.Part;
import com.github.bat333.stockroom.domain.Sector;
import com.github.bat333.stockroom.infra.exceptions.PartNotFoundException;
import com.github.bat333.stockroom.infra.exceptions.SectorNotFoundException;
import com.github.bat333.stockroom.infra.exceptions.StockExceptions;
import com.github.bat333.stockroom.infra.validator.Validator;
import com.github.bat333.stockroom.infra.validator.part.PartDuplicationValidator;
import com.github.bat333.stockroom.model.DataAllPart;
import com.github.bat333.stockroom.model.DataPart;
import com.github.bat333.stockroom.model.DataSector;
import com.github.bat333.stockroom.model.DataUpdatePart;
import com.github.bat333.stockroom.repository.PartRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PartServiceTest {

    @InjectMocks
    private PartService service;

    @Mock
    private PartRepository partRepository;

    @Mock
    private ImageService imageService;
    @Mock
    private Validator<Sector> sectorValidator;

    @Mock
    private DataPart dto;
    @Mock
    private DataUpdatePart updatePart;

    @Mock
    private PartDuplicationValidator duplicationValidator;
    @Mock
    private  Validator<Part> partValidator;

    @Mock
    private DataSector dataSector;

    @Mock
    private DataAllPart dataAllPart;





    @Test
    @DisplayName("Scenario 01: Register part - Sector not found")
    void shouldThrowExceptionWhenSectorNotFoundOnRegister() throws IOException {

        doThrow(new SectorNotFoundException("Reported Sector with ID 1 not found or is inactive."))
                .when(sectorValidator).validator(98L);


        assertThatThrownBy(() -> service.registration(dto,98L))
                .isInstanceOf(SectorNotFoundException.class)
                .hasMessage("Reported Sector with ID 1 not found or is inactive.");

        verify(sectorValidator).validator(98L);
    }

    @Test
    @DisplayName("Scenario 02 - Throw exception for duplicate part registration in the same sector")
    void shouldThrowExceptionWhenRegisteringDuplicatePart() {
        Sector mockSector = new Sector();
        mockSector.setId(78L);
        given(sectorValidator.validator(78L)).willReturn(mockSector);

        doThrow(new StockExceptions("Reported Part Not Found "))
                .when(duplicationValidator).validate(any(),any());


        assertThatThrownBy(() -> service.registration(dto,78L))
                .isInstanceOf(StockExceptions.class)
                .hasMessage("Reported Part Not Found ");

        verify(sectorValidator).validator(78L);
        verify(duplicationValidator).validate(any(),any());
    }

    @Test
    @DisplayName("Scenario 03: Register part - Successful registration")
    void shouldRegisterPartSuccessfully() throws IOException {
        Sector mockSector = new Sector();
        mockSector.setId(12L);
        given(sectorValidator.validator(12L)).willReturn(mockSector);

        given(dto.image()).willReturn(this.img());
        given(imageService.resizeAndCompressImage(any(byte[].class), anyInt(), anyInt(), anyFloat()))
                .willReturn(new byte[0]);

        Part mockPart = new Part();
        mockPart.setId(12L);
        mockPart.setImage(this.img());
        mockPart.setSector(new Sector(12L,"","","","",true, List.of(),1));

        doReturn(mockPart).when(partRepository).save(Mockito.any(Part.class));



        service.registration(dto, 12L);




        verify(sectorValidator).validator(13L);
        verify(duplicationValidator).validate(any(),any());
        verify(partRepository).save(any());
        verify(imageService).resizeAndCompressImage(any(byte[].class), anyInt(), anyInt(), anyFloat());

    }


    @Test
    @DisplayName("Scenario 04 - Retrieve all active parts with pagination")
    void shouldRetrieveAllActivePartsWithPagination() throws IOException {
        Pageable pageable = PageRequest.of(0, 10);

        Part mockPart = new Part();
        mockPart.setImage(this.img());
        mockPart.setSector(new Sector(1L,"","","","",true, List.of(),1));

        Page<Part> mockedPage = new PageImpl<>(List.of(mockPart,mockPart));

        given(partRepository.findByActiveTrue(pageable)).willReturn(mockedPage);

        Page<DataAllPart> result = service.getAll(pageable);

        verify(partRepository).findByActiveTrue(pageable);
        assertNotNull(result);
        assertEquals(mockedPage.getContent().size(), result.getContent().size());

    }

    @Test
    @DisplayName("Scenario 05 - Retrieve part by ID when active")
    void shouldRetrieveActivePartById() throws IOException {
        Part mockPart = new Part();
        mockPart.setId(19L);
        mockPart.setImage(this.img());
        mockPart.setSector(new Sector(19L,"","","","",true, List.of(),1));
        BDDMockito.given(partValidator.validator(19L)).willReturn(mockPart);

        DataAllPart dataAllPart  =  service.get(19L);

        verify(partValidator).validator(19L);
        assertNotNull(dataAllPart);
    }

    @Test
    @DisplayName("Scenario 06 - Throw exception when part is not found")
    void shouldThrowExceptionWhenPartNotFound() {
        doThrow(new PartNotFoundException("Reported Part Not Found "))
                .when(partValidator).validator(any());


        assertThatThrownBy(() -> service.get(13L))
                .isInstanceOf(PartNotFoundException.class)
                .hasMessage("Reported Part Not Found ");

        verify(partValidator).validator(13L);
    }

    @Test
    @DisplayName("Scenario 07 - Update part details in service")
    void shouldUpdatePartDetails() throws IOException {


    }

    @Test
    @DisplayName("Scenario 08 - Throw exception when updating non-existent part in service")
    void shouldThrowExceptionWhenUpdatingNonExistentPart() {
        doThrow(new PartNotFoundException("Reported Part Not Found "))
                .when(partValidator).validator(any());


        assertThatThrownBy(() -> service.update(15L,updatePart))
                .isInstanceOf(PartNotFoundException.class)
                .hasMessage("Reported Part Not Found ");

        verify(partValidator).validator(15L);
    }

    @Test
    @DisplayName("Scenario 09 - Throw exception when deleting non-existent part in service")
    void shouldThrowExceptionWhenDeletingNonExistentPart() {

        doThrow(new PartNotFoundException("Reported Part Not Found "))
                .when(partValidator).validator(any());


        assertThatThrownBy(() -> service.delete(17L))
                .isInstanceOf(PartNotFoundException.class)
                .hasMessage("Reported Part Not Found ");

        verify(partValidator).validator(17L);

    }

    @Test
    @DisplayName("Scenario 10 - Successfully delete part in service")
    void shouldSuccessfullyDeletePart() throws IOException {
        Part mockPart = new Part();
        mockPart.setId(166L);
        mockPart.setImage(this.img());
        mockPart.setSector(new Sector(166L,"","","","",true, List.of(),1));
        BDDMockito.given(partValidator.validator(166L)).willReturn(mockPart);

        service.delete(166L);

        verify(partValidator).validator(166L);

    }

    @Test
    @DisplayName("Scenario 11 - Test searching for parts by name and code in service")
    void shouldSearchPartsByNameAndCode() throws IOException {
        Pageable pageable = PageRequest.of(0, 10);

        Part mockPart = new Part();
        mockPart.setImage(this.img());
        mockPart.setSector(new Sector(1L,"","","","",true, List.of(),1));

        Page<Part> mockedPage = new PageImpl<>(List.of(mockPart,mockPart));
        given(partRepository.findByActiveTrue(pageable)).willReturn(mockedPage);

        Page<DataAllPart> result = service.search(null,null,pageable);

        verify(partRepository).findByActiveTrue(pageable);
        assertNotNull(result);
        assertEquals(mockedPage.getContent().size(), result.getContent().size());
    }

    @Test
    @DisplayName("Scenario 12 - Test searching for parts by code and name in service")
    void shouldSearchPartsByCodeAndName() throws IOException {
        Pageable pageable = PageRequest.of(0, 10);

        Part mockPart = new Part();
        mockPart.setImage(this.img());
        mockPart.setSector(new Sector(1L,"","","","",true, List.of(),1));


        Page<Part> mockedPage = new PageImpl<>(List.of(mockPart));
        given(partRepository.findByCodAndActiveTrue(1L)).willReturn(Optional.of(mockPart));

        Page<DataAllPart> result = service.search(1L,null,pageable);

        verify(partRepository).findByCodAndActiveTrue(1L);
        assertNotNull(result);
        assertEquals(mockedPage.getContent().size(), result.getContent().size());


    }

    @Test
    @DisplayName("Scenario 13 - Test searching for parts by code or name in service")
    void shouldSearchPartsByCodeOrName() throws IOException {

        Pageable pageable = PageRequest.of(0, 10);

        Part mockPart = new Part();
        mockPart.setImage(this.img());
        mockPart.setSector(new Sector(1L,"","","","",true, List.of(),1));


        Page<Part> mockedPage = new PageImpl<>(List.of(mockPart));
        given(partRepository.findByCodOrNameContainingIgnoreCaseAndActiveTrue(1L,"name",pageable)).willReturn(mockedPage);

        Page<DataAllPart> result = service.search(1L,"name",pageable);

        verify(partRepository).findByCodOrNameContainingIgnoreCaseAndActiveTrue(1L,"name",pageable);
        assertNotNull(result);
        assertEquals(mockedPage.getContent().size(), result.getContent().size());


    }

    @Test
    @DisplayName("Scenario 14 - Test searching for parts by name in service")
    void shouldSearchPartsByName() throws IOException {

        Pageable pageable = PageRequest.of(0, 10);

        Part mockPart = new Part();
        mockPart.setImage(this.img());
        mockPart.setSector(new Sector(1L,"","","","",true, List.of(),1));


        Page<Part> mockedPage = new PageImpl<>(List.of(mockPart));
        given(partRepository.findByNameContainingIgnoreCaseAndActiveTrue("name",pageable)).willReturn(mockedPage);

        Page<DataAllPart> result = service.search(null,"name",pageable);

        verify(partRepository).findByNameContainingIgnoreCaseAndActiveTrue("name",pageable);
        assertNotNull(result);
        assertEquals(mockedPage.getContent().size(), result.getContent().size());


    }



    private byte[] img() throws IOException {
        File imageFile = new File("src/test/java/com/github/bat333/stockroom/controller/baixados.jpg");
        return Files.readAllBytes(imageFile.toPath());
    }
}