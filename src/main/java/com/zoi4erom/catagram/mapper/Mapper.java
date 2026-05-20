package com.zoi4erom.catagram.mapper;

public interface Mapper<E, D> {

        D toDto(E entity);

        E toEntity(D dto);
}