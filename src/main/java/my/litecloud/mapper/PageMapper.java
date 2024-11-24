package my.litecloud.mapper;

import my.litecloud.dto.PageDTO;
import my.litecloud.entity.PageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper (componentModel = MappingConstants.ComponentModel.SPRING,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class PageMapper {
    public abstract PageDTO map(PageEntity entity);
    public abstract PageEntity map(PageDTO dto);
}
