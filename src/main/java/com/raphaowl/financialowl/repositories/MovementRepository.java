package com.raphaowl.financialowl.repositories;

import com.raphaowl.financialowl.entities.Movement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MovementRepository extends MongoRepository<Movement, String> {

    @Query("{"
           + "  $expr:{"
           + "      $and:["
           + "          {$eq: [ {$year: '$dueDate'}, ?0 ] }, "
           + "          {$eq: [ {$month: '$dueDate'}, ?1 ] } "
           + "      ]"
           + "  }"
           + "}")
    Page<Movement> findByYearMonth(int year, int month, Pageable pageRequest);

    @Query("{"
           + "  $expr:{"
           + "      $and:["
           + "          {$eq: [ {$year: '$dueDate'}, ?0 ] } "
           + "      ]"
           + "  }"
           + "}")
    List<Movement> findByYear(int year);
}
