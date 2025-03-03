package com.github.bat333.stockroom.Domain.Entities.part;

import com.github.bat333.stockroom.Domain.Entities.sector.Sector;

import java.util.Arrays;
import java.util.Objects;


public class Part {
    private Long id;
    private Long cod;
    private String name;
    private byte[] image;
    private double amount;
    private boolean active = true;
    private Sector sector;


    public Part(Long id, Long cod, String name, byte[] image, double amount, boolean active, Sector sector) {
        PartValidator.validate(id, cod, name, image, amount,sector);
        this.id = id;
        this.cod = cod;
        this.name = name;
        this.image = image;
        this.amount = amount;
        this.active = active;
        this.sector = sector;
    }

    public Part() {

    }

    public Part( Long cod,  String name,  byte[] image,  double amount) {
        PartValidator.validate(cod,name,image,amount);
        this.cod = cod;
        this.name = name;
        this.image = image;
        this.amount = amount;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCod() {
        return cod;
    }

    public void setCod(Long cod) {
        this.cod = cod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Part part = (Part) o;
        return Double.compare(amount, part.amount) == 0 && active == part.active && Objects.equals(id, part.id) && Objects.equals(cod, part.cod) && Objects.equals(name, part.name) && Objects.deepEquals(image, part.image) && Objects.equals(sector, part.sector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cod, name, Arrays.hashCode(image), amount, active, sector);
    }

    public Part update(Part part) {
        if(part.cod != null){
            this.cod = part.cod;
        }
        if(part.name != null){
            this.name = part.name;
        }
        if(part.image != null){
            //colocar service img
            this.image = part.image;
        }
        if(part.amount > 0){
            this.amount = part.amount;
        }
        if(part.sector != null){
            this.sector = part.sector;
        }

        return this;
    }

    public void delete() {
        this.active = false;
    }
}
