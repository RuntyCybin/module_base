package com.example.ddbbport;

import org.mapstruct.Mapper;

import com.example.domain.Articulo;

@Mapper(componentModel = "spring")
public interface ArticuloMapper {

    Articulo toArticulo(ArticuloDdbb articuloDdbb);
}
