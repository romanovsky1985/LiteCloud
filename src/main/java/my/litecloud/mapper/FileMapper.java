package my.litecloud.mapper;

import my.litecloud.dto.FileCreateDTO;
import my.litecloud.dto.FileReadDTO;
import my.litecloud.entity.FileEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class FileMapper {
    public abstract FileEntity map(FileCreateDTO dto);
    @AfterMapping
    public void setSharedAndSize(@MappingTarget FileEntity entity) {
        entity.setShared(false);
        entity.setSize(entity.getData().length);
    }
    public abstract FileReadDTO map(FileEntity entity);

}
