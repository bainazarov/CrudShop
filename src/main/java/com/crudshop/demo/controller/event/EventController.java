package com.crudshop.demo.controller.event;

import com.crudshop.demo.controller.event.request.HttpEvent;
import com.crudshop.demo.controller.event.response.EventOrderResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/event")
public interface EventController {

    @PostMapping
    EventOrderResult orderEvent(@Valid @RequestBody HttpEvent httpEvent);
}
