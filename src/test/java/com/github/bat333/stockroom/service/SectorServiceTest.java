package com.github.bat333.stockroom.service;

import com.github.bat333.stockroom.domain.Sector;
import com.github.bat333.stockroom.infra.exceptions.SectorNotFoundException;
import com.github.bat333.stockroom.infra.exceptions.StockExceptions;
import com.github.bat333.stockroom.infra.validator.Validator;
import com.github.bat333.stockroom.infra.validator.sector.SectorDuplicationValidator;
import com.github.bat333.stockroom.model.DataAllSector;
import com.github.bat333.stockroom.model.DataSector;
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

import java.util.List;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class SectorServiceTest {

    @InjectMocks
    private SectorService service;

    @Mock
    private DataSector dto;

    @Mock
    private SectorRepository repository;

    @Mock
    private  SectorDuplicationValidator duplicationValidator;

    @Mock
    private Validator<Sector> sectorValidator;


    @Captor
    private ArgumentCaptor<Sector> argumentCaptor;

    // Scenario 01: Register sector
    @Test
    @DisplayName("Scenario 01: Register sector in Service")
    void registerSector() {
        // ARRANGE:

        BDDMockito.given(dto.sector()).willReturn("sector");
        BDDMockito.given(dto.row()).willReturn("row");
        BDDMockito.given(dto.column()).willReturn("column");
        BDDMockito.given(dto.shelf()).willReturn("shelf");

        Sector mockSector = new Sector();
        doReturn(mockSector).when(repository).save(Mockito.any(Sector.class));

        // ACT:
        service.register(dto);
        // ASSERT:
        then(repository).should().save(argumentCaptor.capture());
        var captor = argumentCaptor.getValue();
        Assertions.assertEquals("sector", captor.getSectors());
        Assertions.assertEquals("row", captor.getRow());
        Assertions.assertEquals("column", captor.getColumn());
        Assertions.assertEquals("shelf", captor.getShelf());

        verify(duplicationValidator).validate(any());
        verify(repository).save(any());
    }

    // Scenario 02: Register sector when it already exists
    @Test
    @DisplayName("Scenario 02: Register sector in Service when sector already exists")
    void registerSectorWhenAlreadyExists() {
        // ARRANGE:

        BDDMockito.given(dto.sector()).willReturn("sector");
        BDDMockito.given(dto.row()).willReturn("row");
        BDDMockito.given(dto.column()).willReturn("column");
        BDDMockito.given(dto.shelf()).willReturn("shelf");

        BDDMockito.lenient().doReturn(new Sector()).when(repository).save(Mockito.any(Sector.class));

        doThrow(new StockExceptions(String.format("Sector already registered: sector=%s, shelf=%s, column=%s, row=%s",
                dto.sector(), dto.shelf(), dto.column(), dto.row())))
                .when(duplicationValidator).validate(any());

        // ACT:

        assertThatThrownBy(() -> service.register(dto))
                .isInstanceOf(StockExceptions.class)
                .hasMessage(String.format("Sector already registered: sector=%s, shelf=%s, column=%s, row=%s",
                        dto.sector(), dto.shelf(), dto.column(), dto.row()));

        // ASSERT:

        verify(duplicationValidator).validate(any());

    }

    // Scenario 03: Get all active sectors
    @Test
    @DisplayName("Scenario 03: Get all active sectors in Service")
    void getAllActiveSectors() {
        // ARRANGE:

        Pageable pageable = PageRequest.of(0, 10);
        Page<Sector> mockedPage = new PageImpl<>(List.of(new Sector(), new Sector()));
        BDDMockito.given(repository.findByActiveTrue(pageable)).willReturn(mockedPage);

        // ACT:

        Page<DataAllSector> result = service.getAll(pageable);

        // ASSERT:

        verify(repository).findByActiveTrue(pageable);
        assertNotNull(result);
        assertEquals(mockedPage.getContent().size(), result.getContent().size());
    }

    // Scenario 04: Get sector by ID when not found
    @Test
    @DisplayName("Scenario 04: Get sector by ID when sector is not found")
    void getSectorWhenNotFound() {
        // ARRANGE:
        doThrow(new SectorNotFoundException("Reported Sector with ID 1 not found or is inactive."))
                .when(sectorValidator).validator(any());

        // ACT:

        assertThatThrownBy(() -> service.getSector(1L))
                .isInstanceOf(SectorNotFoundException.class)
                .hasMessage("Reported Sector with ID 1 not found or is inactive.");

        // ASSERT:

        verify(sectorValidator).validator(any());
    }

    // Scenario 04: Get sector by ID when sector is found
    @Test
    @DisplayName("Scenario 04: Get sector by ID when sector is found")
    void getSectorWhenFound() {
        // ARRANGE:

        Sector mockSector = new Sector();
        mockSector.setId(1L);
        BDDMockito.given(sectorValidator.validator(1L)).willReturn(mockSector);

        // ACT:

        DataAllSector result = service.getSector(1L);

        // ASSERT:

        assertNotNull(result);
        assertEquals(1L, result.id());

        // ASSERT:

        verify(sectorValidator).validator(1L);
    }

    // Scenario 05: Update sector when sector is not found
    @Test
    @DisplayName("Scenario 05: Update sector when sector is not found")
    void updateSectorWhenNotFound() {
        // ARRANGE:

        doThrow(new SectorNotFoundException("Reported Sector with ID 1 not found or is inactive."))
                .when(sectorValidator).validator(any());
        // ACT:

        assertThatThrownBy(() -> service.update(1L, dto))
                .isInstanceOf(SectorNotFoundException.class)
                .hasMessage("Reported Sector with ID 1 not found or is inactive.");

        // ASSERT:

        verify(sectorValidator).validator(1L);
    }

    // Scenario 05: Update sector when sector is found
    @Test
    @DisplayName("Scenario 05: Update sector when sector is found")
    void updateSectorWhenFound() {
        // ARRANGE:

        Sector mockSector = new Sector();
        mockSector.setId(1L);
        BDDMockito.given(sectorValidator.validator(1L)).willReturn(mockSector);

        // ACT:

        DataAllSector result = service.update(1L, dto);

        // ASSERT:

        assertNotNull(result);
        verify(sectorValidator).validator(1L);
    }

    // Scenario 06: Delete sector when sector is not found
    @Test
    @DisplayName("Scenario 06: Delete sector when sector is not found")
    void deleteSectorWhenNotFound() {
        // ARRANGE:

        doThrow(new SectorNotFoundException("Reported Sector with ID 1 not found or is inactive."))
                .when(sectorValidator).validator(any());

        // ACT:

        assertThatThrownBy(() -> service.delete(1L))
                .isInstanceOf(SectorNotFoundException.class)
                .hasMessage("Reported Sector with ID 1 not found or is inactive.");

        // ASSERT:

        verify(sectorValidator).validator(1L);
    }

    // Scenario 06: Delete sector when sector is found
    @Test
    @DisplayName("Scenario 06: Delete sector when sector is found")
    void deleteSectorWhenFound() {
        // ARRANGE:

        Sector mockSector = new Sector();
        mockSector.setId(1L);
        BDDMockito.given(sectorValidator.validator(1L)).willReturn(mockSector);

        // ACT:

        service.delete(1L);

        // ASSERT:

        verify(sectorValidator).validator(1L);
    }
}
