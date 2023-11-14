package com.sdia.ebankcqrseventsourcingaxon.queries.repositories;

import com.sdia.ebankcqrseventsourcingaxon.queries.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
