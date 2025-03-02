package com.github.bat333.stockroom.start.Infra.Adapters;

import java.util.List;

public interface EntityMapper<D,E> {

    public E toEntity(D domain);

    public D toDomain(E entity);

    public List<D> toListDomain(List<E> entity);

}
