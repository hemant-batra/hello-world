package app.utilities;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Objects.nonNull;
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

    // GET
    public List<DTO> get() {
        return toDTOs(jpaRepository.findAll());
    }

    public DTO get(Id id) {
        return toDTO(jpaRepository.findOne(id));
    }

    public List<DTO> get(List<Id> ids) {
        return toDTOs(jpaRepository.findAll(ids));
    }

    // POST
    public DTO post(DTO dto, Consumer<DTO> idGenerator) {
        idGenerator.accept(dto);
        return toDTO(jpaRepository.save(toEntity(dto)));
    }

    public List<DTO> post(List<DTO> dtoList, Consumer<DTO> idGenerator) {
        dtoList = dtoList.stream().peek(idGenerator).collect(toList());
        return toDTOs(jpaRepository.save(toEntities(dtoList)));
    }

    // DELETE
    public void delete(Id id) {
        jpaRepository.delete(jpaRepository.findOne(id));
    }

    public void delete(List<Id> ids) {
        jpaRepository.delete(jpaRepository.findAll(ids));
    }

    // PUT
    public DTO put(DTO dto) {
        return toDTO(jpaRepository.save(toEntity(dto)));
    }

    public List<DTO> put(List<DTO> dtoList) {
        return toDTOs(jpaRepository.save(toEntities(dtoList)));
    }

    // PATCH
    public DTO patch(DTO dto, Function<DTO, Id> idExtractor) {
        Entity existing = jpaRepository.findOne(idExtractor.apply(dto));
        try {
            copyNonNulls(existing, toEntity(dto));
            return toDTO(jpaRepository.save(existing));
        } catch(Exception ignored) {}
        return null;
    }

    public List<DTO> patch(List<DTO> dtoList, Function<DTO, Id> idExtractor) {
        return dtoList.stream().map(dto -> patch(dto, idExtractor)).filter(Objects::nonNull).collect(toList());
    }

    private void copyNonNulls(Object destination, Object source) throws IntrospectionException, IllegalArgumentException,
                                IllegalAccessException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(source.getClass());
        PropertyDescriptor[] pdList = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : pdList) {
            Object value = pd.getReadMethod().invoke(source);
            if(nonNull(value))
                pd.getWriteMethod().invoke(destination, value);
        }
    }
}