package com.crudshop.demo.kafka;

import com.crudshop.demo.controller.event.request.EventSource;
import com.crudshop.demo.controller.event.request.HttpEvent;
import com.crudshop.demo.service.event.handler.EventHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Set;


@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "kafka.enabled", matchIfMissing = false)
public class Consumer {

    private final Set<EventHandler<EventSource>> eventHandlers;

    @KafkaListener(topics = "CustomTopic", containerFactory = "kafkaListenerContainerFactoryString")
    public void listenGroupTopic2(String message) {
        log.info("Receive message: {}", message);

        final ObjectMapper objectMapper = new ObjectMapper();

        try {
            final HttpEvent eventSource = objectMapper.readerFor(HttpEvent.class).readValue(message);
            log.info("EventSource: {}", eventSource);

            eventHandlers.stream()
                    .filter(eventSourceEventHandler -> eventSourceEventHandler.canHandle(eventSource))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Handler for eventsource not found"))
                    .handleEvent(eventSource);

        } catch (JsonProcessingException e) {
            log.error("Couldn't parse message: {}; exception: ", message, e);
        }
    }
}

