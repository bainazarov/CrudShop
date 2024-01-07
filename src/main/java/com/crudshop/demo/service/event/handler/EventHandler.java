package com.crudshop.demo.service.event.handler;

import com.crudshop.demo.controller.event.request.EventSource;

public interface EventHandler <T extends EventSource> {

    boolean canHandle(EventSource eventSource);

    String handleEvent(T eventSource);
}