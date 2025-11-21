package com.example.ddbbport;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.example.domain.Articulo;
import com.example.domain.ArticuloRequest;

@Mapper(componentModel = "spring")
public interface ArticuloMapper {

    Articulo toArticulo(ArticuloDdbb articuloDdbb);

    @Mappings({
            @Mapping(target = "nombre", source = "titulo"),
            @Mapping(target = "descripcion", source = "desc"),
            @Mapping(target = "precio", source = "precio"),
            @Mapping(target = "stock", source = "stock"),
            @Mapping(target = "createdAt", expression = "java(new Timestamp(System.currentTimeMillis()))")
    })
    ArticuloDdbb toArticuloDdbb(ArticuloRequest art);

    @Mappings({
            @Mapping(target = "nombre", source = "titulo"),
            @Mapping(target = "descripcion", source = "desc"),
            @Mapping(target = "precio", source = "precio"),
            @Mapping(target = "stock", source = "stock"),
            @Mapping(target = "createdAt", expression = "java(new Timestamp(System.currentTimeMillis()))")
    })
    Articulo toArticuloFromArticuloRequest(ArticuloRequest request);
}
