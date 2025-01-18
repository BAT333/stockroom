package com.github.bat333.stockroom.service;

import com.github.bat333.stockroom.domain.Part;
import com.github.bat333.stockroom.domain.Sector;
import com.github.bat333.stockroom.infra.exceptions.SectorNotFoundException;
import com.github.bat333.stockroom.infra.exceptions.StockExceptions;
import com.github.bat333.stockroom.model.DataAllPart;
import com.github.bat333.stockroom.model.DataPart;
import com.github.bat333.stockroom.model.DataUpdatePart;
import com.github.bat333.stockroom.repository.PartRepository;
import com.github.bat333.stockroom.repository.SectorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
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
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PartServiceTest {

    @InjectMocks
    private PartService service;

    @Mock
    private SectorRepository sectorRepository;

    @Mock
    private PartRepository partRepository;

    @Captor
    private ArgumentCaptor<Sector> argumentCaptorSector;

    @Captor
    private ArgumentCaptor<Part> argumentCaptorPart;

    @Mock
    private DataPart dto;

    @Mock
    private DataUpdatePart dtoUpdate;

    @Mock
    private ImageService imageService;



    @Test
    @DisplayName("Scenario 01: Register part - Sector not found")
    void shouldThrowExceptionWhenSectorNotFoundOnRegister() {
        BDDMockito.given(sectorRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.registration(dto,1L))
                .isInstanceOf(SectorNotFoundException.class)
                .hasMessage("Reported Sector Not Found ");


        verify(sectorRepository).findById(1L);

    }
    @Test
    @DisplayName("Scenario 02: Register part - Successful registration")
    void shouldRegisterPartSuccessfully() throws IOException {
        // ARRANGE:

        Sector mockSector = new Sector();
        mockSector.setId(1L);

        BDDMockito.given(dto.cod()).willReturn(1L);
        BDDMockito.given(dto.name()).willReturn("name");
        BDDMockito.given(dto.image()).willReturn(this.img());
        BDDMockito.given(dto.amount()).willReturn(5.0);

        BDDMockito.given(sectorRepository.findById(1L)).willReturn(Optional.of(mockSector));

        BDDMockito.given(imageService.resizeAndCompressImage(any(byte[].class), anyInt(), anyInt(), anyFloat()))
                .willReturn(new byte[0]);


        Part mockPart = new Part();
        mockPart.setId(1L);
        mockPart.setImage(this.img());
        mockPart.setSector(new Sector(1L,"","","","",true, List.of(),1));
        doReturn(mockPart).when(partRepository).save(Mockito.any(Part.class));

        // ACT:

        service.registration(dto, 1L);

        // ASSERT:

        then(partRepository).should().save(argumentCaptorPart.capture());
        var captor = argumentCaptorPart.getValue();
        Assertions.assertEquals("name", captor.getName());
        Assertions.assertEquals(1L, captor.getCod());
        Assertions.assertEquals(5.0, captor.getAmount());

        verify(sectorRepository).findById(1L);
        verify(imageService).resizeAndCompressImage(any(byte[].class), anyInt(), anyInt(), anyFloat());
        verify(partRepository).save(any());
        verify(partRepository).existsByCodAndNameAndSector(any(),any(),any());

    }

    @Test
    @DisplayName("Scenario 03 - Throw exception for duplicate part registration in the same sector")
    void shouldThrowExceptionWhenRegisteringDuplicatePart() throws IOException {
        Sector mockSector = new Sector();
        mockSector.setId(1L);

        BDDMockito.given(sectorRepository.findById(1L)).willReturn(Optional.of(mockSector));
        BDDMockito.given(partRepository.existsByCodAndNameAndSector(any(),any(),any()))
                .willReturn(true);

        BDDMockito.given(dto.cod()).willReturn(1L);
        BDDMockito.given(dto.name()).willReturn("name");


        assertThatThrownBy(() -> service.registration(dto,1L))
                .isInstanceOf(StockExceptions.class)
                .hasMessage("Part with code 1 and name name already registered in this sector.");


        verify(partRepository).existsByCodAndNameAndSector(any(),any(),any());

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
        mockPart.setImage(this.img());
        mockPart.setSector(new Sector(1L,"","","","",true, List.of(),1));
        given(partRepository.findByIdAndActiveTrue(1L)).willReturn(Optional.of(mockPart));

        DataAllPart result = service.get(1L);

        verify(partRepository).findByIdAndActiveTrue(1L);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Scenario 06 - Throw exception when part is not found")
    void shouldThrowExceptionWhenPartNotFound() throws IOException {

        given(partRepository.findByIdAndActiveTrue(1L)).willReturn(Optional.empty());


        assertThatThrownBy(() -> service.get(1L))
                .isInstanceOf(SectorNotFoundException.class)
                .hasMessage("Reported Part Not Found ");

        verify(partRepository).findByIdAndActiveTrue(1L);
    }

    @Test
    @DisplayName("Scenario 07 - Update part details in service")
    void shouldUpdatePartDetails() throws IOException {

        Part mockPart = new Part();
        mockPart.setId(1L);
        mockPart.setImage(this.img());
        mockPart.setSector(new Sector(1L, "SectorName", "SectorDescription", "", "", true, List.of(), 1));

        given(partRepository.findById(1L)).willReturn(Optional.of(mockPart));

        Sector mockSector = new Sector(1L, "Name", "Description", "", "", true, List.of(), 1);
        lenient().when(sectorRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.of(mockSector));

        doReturn(mockPart).when(partRepository).save(Mockito.any(Part.class));

        DataAllPart result = service.update(1L, dtoUpdate);

        assertNotNull(result);

        verify(partRepository).findById(1L);
        verify(partRepository).save(any());


    }

    @Test
    @DisplayName("Scenario 08 - Throw exception when updating non-existent part in service")
    void shouldThrowExceptionWhenUpdatingNonExistentPart() {

        given(partRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(1L,dtoUpdate))
                .isInstanceOf(SectorNotFoundException.class)
                .hasMessage("Reported Part Not Found ");

        verify(partRepository).findById(1L);

    }

    @Test
    @DisplayName("Scenario 09 - Throw exception when deleting non-existent part in service")
    void shouldThrowExceptionWhenDeletingNonExistentPart() {
        given(partRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(1L))
                .isInstanceOf(SectorNotFoundException.class)
                .hasMessage("Reported Part Not Found");

        verify(partRepository).findById(1L);
    }

    @Test
    @DisplayName("Scenario 10 - Successfully delete part in service")
    void shouldSuccessfullyDeletePart() throws IOException {
        Part mockPart = new Part();
        mockPart.setId(1L);
        mockPart.setImage(this.img());
        mockPart.setSector(new Sector(1L,"","","","",true, List.of(),1));

        given(partRepository.findById(1L)).willReturn(Optional.of(mockPart));

        service.delete(1L);

        verify(partRepository).findById(1L);
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