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
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toCollection;
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

    // ________________________________ Entity to DTO ________________________________
    protected DTO toDTO(Entity entity) {
        return mapper.convertValue(entity, dtoClass);
    }

    protected Stream<DTO> toDTOStream(List<Entity> entityList) {
        return entityList.stream().map(this::toDTO);
    }

    protected List<DTO> toDTOList(List<Entity> entityList) {
        return toDTOStream(entityList).collect(toList());
    }

    protected Collection<DTO> toDTOCollection(List<Entity> entityList, Supplier<Collection<DTO>> supplier) {
        return toDTOStream(entityList).collect(toCollection(supplier));
    }

    // ________________________________ DTO to Entity ________________________________
    protected Entity toEntity(DTO dto) {
        return mapper.convertValue(dto, entityClass);
    }

    protected Stream<Entity> toEntityStream(List<DTO> dtoList) {
        return dtoList.stream().map(this::toEntity);
    }

    protected List<Entity> toEntityList(List<DTO> dtoList) {
        return toEntityStream(dtoList).collect(toList());
    }

    protected Collection<Entity> toEntityCollection(List<DTO> dtoList, Supplier<Collection<Entity>> supplier) {
        return toEntityStream(dtoList).collect(toCollection(supplier));
    }

    // ________________________________ Get DTO By Id________________________________
    public DTO get(Id id) {
        return toDTO(jpaRepository.findOne(id));
    }

    public Optional<DTO> getOptional(Id id) {
        return Optional.ofNullable(get(id));
    }

    // ________________________________ Get DTOs By Ids________________________________
    public Stream<DTO> streamByIds(List<Id> ids) {
        return toDTOStream(jpaRepository.findAll(ids));
    }

    public List<DTO> listByIds(List<Id> ids) {
        return toDTOList(jpaRepository.findAll(ids));
    }

    public Collection<DTO> collectByIds(List<Id> ids, Supplier<Collection<DTO>> supplier) {
        return toDTOCollection(jpaRepository.findAll(ids), supplier);
    }

    // ________________________________ Get All DTOs________________________________
    public Stream<DTO> streamAll() {
        return toDTOStream(jpaRepository.findAll());
    }

    public List<DTO> listAll() {
        return toDTOList(jpaRepository.findAll());
    }

    public Collection<DTO> collectAll(Supplier<Collection<DTO>> supplier) {
        return toDTOCollection(jpaRepository.findAll(), supplier);
    }

    // ________________________________ Post & Put________________________________
    public DTO save(DTO dto) {
        return toDTO(jpaRepository.save(toEntity(dto)));
    }

    public List<DTO> saveAll(List<DTO> dtoList) {
        return toDTOList(jpaRepository.save(toEntityList(dtoList)));
    }

    // ________________________________ Post & Put with Consumer________________________________
    public DTO save(DTO dto, Consumer<DTO> dtoMaker) {
        dtoMaker.accept(dto);
        return toDTO(jpaRepository.save(toEntity(dto)));
    }

    public List<DTO> saveAll(List<DTO> dtoList, Consumer<DTO> dtoMaker) {
        dtoList = dtoList.stream().peek(dtoMaker).collect(toList());
        return toDTOList(jpaRepository.save(toEntityList(dtoList)));
    }

    // ________________________________ Delete ________________________________
    public void delete(Id id) {
        jpaRepository.delete(jpaRepository.findOne(id));
    }

    public void deleteAll(List<Id> ids) {
        jpaRepository.delete(jpaRepository.findAll(ids));
    }

    // ________________________________ Patch ________________________________
    public DTO patch(DTO dto, Function<DTO, Id> idExtractor) {
        Entity existing = jpaRepository.findOne(idExtractor.apply(dto));
        try {
            copyNonNulls(existing, toEntity(dto));
            return toDTO(jpaRepository.save(existing));
        } catch(Exception ignored) {}
        return null;
    }

    public List<DTO> patchAll(List<DTO> dtoList, Function<DTO, Id> idExtractor) {
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