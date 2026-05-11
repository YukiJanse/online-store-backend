package se.jensen.yuki.onlinestorebackend.product.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.jensen.yuki.onlinestorebackend.product.infrastructure.ProductJpaEntity;
import se.jensen.yuki.onlinestorebackend.product.web.dto.ProductDTO;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    ProductJpaEntity toProductJpaEntity(ProductDTO productDto);
    ProductDTO toProductDTO(ProductJpaEntity entity);
}
