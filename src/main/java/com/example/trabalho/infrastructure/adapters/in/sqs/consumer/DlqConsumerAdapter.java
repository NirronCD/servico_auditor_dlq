package com.example.trabalho.infrastructure.adapters.in.sqs.consumer;

import com.example.trabalho.application.ports.in.service.AuditServicePort;
import com.example.trabalho.core.domain.bo.DeadLetterBO;
import com.example.trabalho.infrastructure.adapters.in.sqs.dto.SqsMessageDTO;
import com.example.trabalho.infrastructure.adapters.in.sqs.mapper.SqsDtoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class DlqConsumerAdapter {

    private final AuditServicePort auditServicePort;
    private final ObjectMapper objectMapper;
    private final SqsDtoMapper sqsDtoMapper;

    public DlqConsumerAdapter(AuditServicePort auditServicePort, ObjectMapper objectMapper, SqsDtoMapper sqsDtoMapper) {
        this.auditServicePort = auditServicePort;
        this.objectMapper = objectMapper;
        this.sqsDtoMapper = sqsDtoMapper;
    }

    @SqsListener("${queue.order-events}")
    public void receiveMessage(String messageJson) {
        try {
            System.out.println("[DLQ CONSUMER] Mensagem interceptada da fila: {" + messageJson + "}");

            SqsMessageDTO dto = objectMapper.readValue(messageJson, SqsMessageDTO.class);

            DeadLetterBO bo = sqsDtoMapper.toBO(dto, messageJson);
            auditServicePort.execute(bo);

            System.out.println("[DLQ CONSUMER] Mensagem processada e salva com sucesso. Removendo da fila.");
        } catch (Exception e) {
            System.out.println("[DLQ CONSUMER] Falha crítica de persistência. A mensagem será mantida na DLQ." +  e);
            throw new RuntimeException("Erro no processamento da auditoria. Retendo mensagem.", e);
        }
    }
}