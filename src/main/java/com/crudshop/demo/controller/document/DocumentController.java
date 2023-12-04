package com.crudshop.demo.controller.document;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface DocumentController {

    @GetMapping()
    @Operation(summary = "Получить список файлов из папки resources/reports")
    List<String> getReportFileNames();
}