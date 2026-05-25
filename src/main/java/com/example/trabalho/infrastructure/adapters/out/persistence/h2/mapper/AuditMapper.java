package com.example.trabalho.infrastructure.adapters.out.persistence.h2.mapper;

import com.example.trabalho.core.domain.bo.DeadLetterBO;
import com.example.trabalho.infrastructure.adapters.out.persistence.h2.entity.AuditEntity;
import org.springframework.stereotype.Component;

@Component
public class AuditMapper {

    public AuditEntity toEntity(DeadLetterBO bo) {
        AuditEntity entity = new AuditEntity();
        entity.setPayload(bo.getRawJsonPayload());
        return entity;
    }
}