package com.github.bat333.stockroom.Adapters.outbound.entities.sector;


import com.github.bat333.stockroom.start.Infra.Dto.sector.DataSector;
import com.github.bat333.stockroom.start.Infra.Persistence.part.PartEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "sectors")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class SectorEntity {
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
    private List<PartEntity> partEntities = new ArrayList<>();
    @Version
    private Integer version;

    public SectorEntity(DataSector dataSector) {
        this.sectors = dataSector.sector();
        this.shelf = dataSector.shelf();
        this.column = dataSector.column();
        this.row = dataSector.row();
        this.active = true;
    }

    public void update(SectorEntity sector) {

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
/*
    public List<DataPart> listPart() {

        return this.parts.stream().map(DataPart::new).collect(Collectors.toList());
    }

    public void addOrder(Order order){
        order.setOrderTotal(this);
        this.orders.add(order);
        this.total =this.total.add(order.getValuesTotal());
    }

 */


}
