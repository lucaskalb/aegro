package com.aegro.rest;


import com.aegro.application.*;
import com.aegro.domain.Farm;
import com.aegro.domain.Field;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/v1/farms", produces = "application/json")
public class FarmRest {

    private FarmService service;

    @GetMapping(path = "/{id}/productivity")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Productivity> getProductivity(@PathVariable UUID id) {
        return service.getProductivity(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Farm> addField(@RequestBody FarmVO body) {
        return Optional.of(CreateFarmCommand.of(body.getName()))
                .map(service::execute)
                .map(ResponseEntity::ok)
                .orElseThrow();
    }

    @PostMapping(path = "/{id}/fields")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Field> addField(@PathVariable UUID id,
                                          @RequestBody FieldVO body) {
        return Optional.of(AddFieldCommand.of(id, body.getName(), body.getArea()))
                .map(service::execute)
                .map(ResponseEntity::ok)
                .orElseThrow();
    }

    @PostMapping(path = "/{id}/fields/{fieldId}/goods")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Farm> addGoods(@PathVariable UUID id,
                                         @PathVariable UUID fieldId,
                                         @RequestBody GoodsVO body) {
        return Optional.of(AddGoodsCommand.of(id, fieldId, body.getQuantity()))
                .map(service::execute)
                .map(ResponseEntity::ok)
                .orElseThrow();
    }
}
