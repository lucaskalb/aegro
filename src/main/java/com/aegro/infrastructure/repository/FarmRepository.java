package com.aegro.infrastructure.repository;

import com.aegro.domain.Farm;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FarmRepository extends MongoRepository<Farm, UUID> {

}
