package se.jensen.yuki.productservice.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.jensen.yuki.productservice.infrastructure.ProductJpaEntity;
import se.jensen.yuki.productservice.web.dto.ProductDTO;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    ProductJpaEntity toProductJpaEntity(ProductDTO productDto);
    ProductDTO toProductDTO(ProductJpaEntity entity);
}
