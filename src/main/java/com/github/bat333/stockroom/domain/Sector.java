package com.github.bat333.stockroom.domain;

import com.github.bat333.stockroom.model.DataPart;
import com.github.bat333.stockroom.model.DataSector;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "sectors")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sector", nullable = false)
    private String sectors;
    @Column(name = "shelves", nullable = false)
    private String shelf;
    @Column(name = "columns", nullable = false)
    private String column;
    @Column(name = "row_data", nullable = false)
    private String row;
    @Column(name = "actives",nullable = false)
    private Boolean active = true;
    @OneToMany(mappedBy = "sector",cascade = CascadeType.ALL)
    private List<Part> parts = new ArrayList<>();

    public Sector(DataSector dataSector) {
        this.sectors = dataSector.sector();
        this.shelf = dataSector.shelf();
        this.column = dataSector.column();
        this.row = dataSector.row();
        this.active = true;
    }

    public void update(DataSector dataSector) {
        if(dataSector.shelf() !=null){
            this.shelf =dataSector.shelf();
        }
        if(dataSector.column() !=null){
            this.column =dataSector.column();

        }
        if(dataSector.row() !=null){
            this.row =dataSector.row();

        }

    }

    public void delete() {
        this.active = false;
    }

    public List<DataPart> listPart() {
        return this.parts.stream().map(DataPart::new).collect(Collectors.toList());
    }
/*
    public void addOrder(Order order){
        order.setOrderTotal(this);
        this.orders.add(order);
        this.total =this.total.add(order.getValuesTotal());
    }

 */


}
