package com.example.trabalho.application.ports.in.service;

import com.example.trabalho.core.domain.bo.DeadLetterBO;

public interface AuditServicePort {
    void execute(DeadLetterBO deadLetterBO);
}