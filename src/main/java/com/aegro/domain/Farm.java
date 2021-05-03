package com.aegro.domain;

import lombok.Getter;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Getter
@Document
public class Farm {

    @Indexed
    private UUID id;
    private String name;
    private List<Field> fields;

    @PersistenceConstructor
    private Farm(UUID id, String name, List<Field> fields) {
        Objects.requireNonNull(id, "Id cannot be null");
        Objects.requireNonNull(name, "Name cannot be null");
        Objects.requireNonNull(fields, "Fields cannot be null");

        this.id = id;
        this.name = name;
        this.fields = fields;
    }

    public static Farm of(String name) {
        return new Farm(UUID.randomUUID(), name, new ArrayList<>());
    }

    public void addField(Field field) {
        fields.add(field);
    }

    public void addGoods(Goods goods, UUID fieldId) {
        var field = fields.stream()
                .filter(f -> f.getId().equals(fieldId))
                .findFirst()
                .orElseThrow();

        field.addGoods(goods);
    }

    public List<Field> getFields() {
        return Collections.unmodifiableList(fields);
    }
}
