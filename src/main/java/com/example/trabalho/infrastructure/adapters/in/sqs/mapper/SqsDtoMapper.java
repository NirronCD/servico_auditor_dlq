package com.example.trabalho.infrastructure.adapters.in.sqs.mapper;

import com.example.trabalho.core.domain.bo.DeadLetterBO;
import com.example.trabalho.core.domain.bo.OrderItemBO;
import com.example.trabalho.infrastructure.adapters.in.sqs.dto.SqsMessageDTO;
import com.example.trabalho.infrastructure.adapters.in.sqs.dto.SqsOrderItemDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SqsDtoMapper {

    public DeadLetterBO toBO(SqsMessageDTO dto, String rawJson) {
        List<OrderItemBO> itemsBO = new ArrayList<>();

        if (dto.getOrderItems() != null) {
            for (SqsOrderItemDTO itemDto : dto.getOrderItems()) {
                itemsBO.add(new OrderItemBO(itemDto.getSku(), itemDto.getAmount()));
            }
        }

        return new DeadLetterBO(
                dto.getZipCode(),
                dto.getCustomerId(),
                itemsBO,
                dto.getOrigin(),
                dto.getOccurredAt(),
                rawJson
        );
    }
}