package com.crudshop.demo.service.document;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DocumentService {
    List<String> getReportFileNames();
}
