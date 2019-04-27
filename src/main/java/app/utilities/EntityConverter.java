package app.utilities;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class EntityConverter<Id extends Serializable, Entity, DTO> {

    private JpaRepository<Entity, Id> jpaRepository;
    private Class<Entity> entityClass;
    private Class<DTO> dtoClass;
    private ObjectMapper mapper;

    protected EntityConverter(JpaRepository<Entity, Id> jpaRepository, Class<Entity> entityClass, Class<DTO> dtoClass) {
        this.jpaRepository = jpaRepository;
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

    public List<DTO> findAll() {
        return toDTOs(jpaRepository.findAll());
    }

    public DTO findOne(Id id) {
        return toDTO(jpaRepository.findOne(id));
    }

    public List<DTO> findAll(List<Id> ids) {
        return toDTOs(jpaRepository.findAll(ids));
    }

    public DTO save(DTO dto) {
        return toDTO(jpaRepository.save(toEntity(dto)));
    }

    public List<DTO> save(List<DTO> dtoList) {
        return toDTOs(jpaRepository.save(toEntities(dtoList)));
    }

    public void delete(DTO dto) {
        jpaRepository.delete(toEntity(dto));
    }

    public void delete(List<DTO> dtoList) {
        jpaRepository.delete(toEntities(dtoList));
    }

}