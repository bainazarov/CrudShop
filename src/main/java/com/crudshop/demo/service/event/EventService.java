package com.crudshop.demo.service.event;

import com.crudshop.demo.controller.event.request.EventSource;

public interface EventService {
    String orderEvent(EventSource eventSource);
}
