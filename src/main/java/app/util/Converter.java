package app.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class Converter<Entity, DTO> {

    private Class<Entity> entityClass;
    private Class<DTO> dtoClass;
    private ObjectMapper mapper;

    protected Converter(Class<Entity> entityClass, Class<DTO> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
    }

    protected DTO toDTO(Entity entity) {
        return mapper.convertValue(entity, dtoClass);
    }

    protected Entity toEntity(DTO dto) {
        return mapper.convertValue(dto, entityClass);
    }

    protected List<DTO> toDTOs(List<Entity> entityList) {
        return entityList
                .stream()
                .map(this::toDTO)
                .collect(toList());
    }

    protected List<Entity> toEntities(List<DTO> dtoList) {
        return dtoList
                .stream()
                .map(this::toEntity)
                .collect(toList());
    }
}
