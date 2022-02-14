package ru.kpfu.aminovniaz.project.utils;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Set;

public class EntityConverter implements GenericConverter {

    private final Class<?> clazz;

    @PersistenceContext
    private EntityManager em;

    public EntityConverter(Class<?> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        Set<ConvertiblePair> types = new HashSet<>();
        types.add(new ConvertiblePair(String.class, this.clazz));
        return types;
    }

    @Override
    public Object convert(Object o, TypeDescriptor sourceType, TypeDescriptor targetType) {
        System.out.println("CONVERTING FROM " + sourceType.getName() + " TO " + targetType.getName());

        if (String.class.equals(sourceType.getType())) {
            if (StringUtils.hasText((String) o)) {
                Integer id = Integer.parseInt((String) o);
                return this.em.find(this.clazz, id);
            }
        }
        return null;
    }
}
