package com.example.trabalho.infrastructure.adapters.out.persistence.h2.repository;


import com.example.trabalho.application.ports.out.persistence.h2.AuditRepositoryPort;
import com.example.trabalho.core.domain.bo.DeadLetterBO;
import com.example.trabalho.infrastructure.adapters.out.persistence.h2.entity.AuditEntity;
import com.example.trabalho.infrastructure.adapters.out.persistence.h2.jpa.AuditJpaRepository;
import com.example.trabalho.infrastructure.adapters.out.persistence.h2.mapper.AuditMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public class AuditRepositoryPortAdapter implements AuditRepositoryPort {

    private final AuditJpaRepository jpaRepository;
    private final AuditMapper auditMapper;
    private final String dlqName;

    public AuditRepositoryPortAdapter(AuditJpaRepository jpaRepository,
                                      AuditMapper auditMapper,
                                      @Value("${queue.order-events}") String dlqName) {
        this.jpaRepository = jpaRepository;
        this.auditMapper = auditMapper;
        this.dlqName = dlqName;
    }

    @Override
    public void save(DeadLetterBO deadLetterBO) {
        AuditEntity entity = auditMapper.toEntity(deadLetterBO);

        entity.setErrorId(UUID.randomUUID().toString());
        entity.setQueueName(deadLetterBO.getOrigin());
        entity.setTimestamp(OffsetDateTime.now().toString());
        entity.setStatus("PENDING_ANALYSIS");

        entity.setSeverity(deadLetterBO.calculateSeverity());

        jpaRepository.save(entity);
    }
}