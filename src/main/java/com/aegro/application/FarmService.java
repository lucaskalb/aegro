package com.aegro.application;

import com.aegro.domain.Farm;
import com.aegro.domain.Field;
import com.aegro.domain.Goods;
import com.aegro.infrastructure.repository.FarmRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FarmService {

    private final FarmRepository repository;

    @Transactional
    public Farm execute(CreateFarmCommand command) {
        return Optional.of(command)
                .map(CreateFarmCommand::getName)
                .map(Farm::of)
                .map(repository::save)
                .orElseThrow();
    }

    @Transactional
    public Field execute(AddFieldCommand command) {
        var farm = repository.findById(command.getFarmId())
                .orElseThrow(IllegalArgumentException::new);

        var field = Field.of(command.getName(), command.getArea());
        farm.addField(field);

        repository.save(farm);
        return field;
    }

    @Transactional
    public Farm execute(AddGoodsCommand command) {
        var farm = repository.findById(command.getFarmId())
                .orElseThrow(IllegalArgumentException::new);

        var good = Goods.of(command.getAmount());
        var fieldId = command.getFieldId();

        farm.addGoods(good, fieldId);

        return repository.save(farm);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<Productivity> getProductivity(UUID id) {
        return repository.findById(id)
                .map(ProductivityFactory::of)
                .map(ProductivityFactory::build);
    }
}
