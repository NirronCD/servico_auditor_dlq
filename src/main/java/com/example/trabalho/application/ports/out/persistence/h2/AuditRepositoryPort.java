package com.example.trabalho.application.ports.out.persistence.h2;

import com.example.trabalho.core.domain.bo.DeadLetterBO;

public interface AuditRepositoryPort {
    void save(DeadLetterBO deadLetterBO);
}