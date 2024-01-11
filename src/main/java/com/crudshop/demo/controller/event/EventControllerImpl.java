package com.crudshop.demo.controller.event;

import com.crudshop.demo.controller.event.request.HttpEvent;
import com.crudshop.demo.controller.event.response.EventOrderResult;
import com.crudshop.demo.kafka.Producer;
import com.crudshop.demo.service.event.EventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EventControllerImpl implements EventController {

    private final EventService eventService;
    private final Producer producer;


    @Override
    public EventOrderResult orderEvent(@Valid HttpEvent httpEvent) {
        return EventOrderResult.builder()
                .processId(eventService.orderEvent(httpEvent))
                .build();
    }


    @Override
    public void sentKafkaEvent(@Valid HttpEvent httpEvent) throws JsonProcessingException {

        UUID kafkaKey = UUID.randomUUID();

        producer.sendEvent(Producer.CUSTOM_TOPIC, String.valueOf(kafkaKey), httpEvent);
    }
}
