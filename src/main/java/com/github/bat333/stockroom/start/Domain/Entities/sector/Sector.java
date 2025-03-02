package com.github.bat333.stockroom.start.Domain.Entities.sector;

import com.github.bat333.stockroom.start.Domain.Entities.part.Part;



import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Sector {
    private Long id;
    private String sectors;
    private String shelf;
    private String column;
    private String row;
    private boolean active = true;
    private List<Part> parts = new ArrayList<>();

    public Sector(Long id, String sectors, String shelf, String column, String row, Boolean active, List<Part> parts) {
        this.id = id;
        this.sectors = sectors;
        this.shelf = shelf;
        this.column = column;
        this.row = row;
        this.active = active;
        this.parts = parts;
    }

    public Sector() {

    }

    public Sector( String sector,  String column,  String shelf,  String row) {
        SectorValidator.validate(sector,shelf,column,row);
        this.sectors = sector;
        this.shelf = shelf;
        this.column = column;
        this.row = row;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSectors() {
        return sectors;
    }

    public void setSectors(String sectors) {
        this.sectors = sectors;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Sector sector = (Sector) o;
        return Objects.equals(id, sector.id) && Objects.equals(sectors, sector.sectors) && Objects.equals(shelf, sector.shelf) && Objects.equals(column, sector.column) && Objects.equals(row, sector.row) && Objects.equals(active, sector.active) && Objects.equals(parts, sector.parts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sectors, shelf, column, row, active, parts);
    }

    public void update(Sector sector) {

        if (sector.sectors != null) {
            this.sectors = sector.sectors;
        }
        if (sector.shelf != null) {
            this.shelf = sector.shelf;
        }
        if (sector.column != null) {
            this.column = sector.column;

        }
        if (sector.row != null) {
            this.row = sector.row;

        }
    }



    public void delete() {
        this.active = false;
    }
//
//    public List<DataPart> listPart() {
//        return this.parts.stream().map(DataPart::new).collect(Collectors.toList());
//    }


}
