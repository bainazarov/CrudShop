package com.crudshop.demo.service.event;

import com.crudshop.demo.controller.event.request.EventSource;
import com.crudshop.demo.service.event.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final Set<EventHandler<EventSource>> eventHandlers;

    @Override
    public String orderEvent(EventSource eventSource) {
        Assert.notNull(eventSource, "EventSource must not be null");

        return eventHandlers.stream()
                .filter(eventSourceEventHandler -> eventSourceEventHandler.canHandle(eventSource))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Handler for eventsource not found"))
                .handleEvent(eventSource);
    }
}
