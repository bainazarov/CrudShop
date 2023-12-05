package com.crudshop.demo.controller.document;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DocumentController {

    @GetMapping()
    @Operation(summary = "Получить список файлов из папки resources/reports")
    List<String> getReportFileNames();

    @PostMapping("/upload")
    @Operation(summary = "Загрузить файл в каталог resources/reports")
    ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file);

    @GetMapping("/download")
    @Operation(summary = "Скачивание файла")
    ResponseEntity<Object> downloadFile() throws IOException;
}