package com.example.trabalho.application.services;


import com.example.trabalho.application.ports.in.service.AuditServicePort;
import com.example.trabalho.application.ports.out.persistence.h2.AuditRepositoryPort;
import com.example.trabalho.core.domain.bo.DeadLetterBO;
import org.springframework.stereotype.Service;

@Service
public class AuditService implements AuditServicePort {

    private final AuditRepositoryPort auditRepositoryPort;

    public AuditService(AuditRepositoryPort auditRepositoryPort) {
        this.auditRepositoryPort = auditRepositoryPort;
    }

    @Override
    public void execute(DeadLetterBO deadLetterBO) {
        this.auditRepositoryPort.save(deadLetterBO);
    }
}