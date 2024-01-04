package com.crudshop.demo.controller.event;

import com.crudshop.demo.controller.event.request.HttpEvent;
import com.crudshop.demo.controller.event.response.EventOrderResult;
import com.crudshop.demo.service.event.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EventControllerImpl implements EventController {

    private final EventService eventService;

    @Override
    public EventOrderResult orderEvent(@Valid HttpEvent httpEvent) {
        return EventOrderResult.builder()
                .processId(eventService.orderEvent(httpEvent))
                .build();
    }
}
