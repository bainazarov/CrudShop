package com.crudshop.demo.controller.document;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/documents")
public interface DocumentController {

    @GetMapping()
    @Operation(summary = "Получить список файлов из папки resources/reports")
    List<String> getReportFileNames();

    @PostMapping("/upload")
    @Operation(summary = "Загрузить файл в каталог resources/reports")
    ResponseEntity<String> uploadFile(@RequestParam("file") final MultipartFile file);

    @GetMapping(value = "/download")
    @Operation(summary = "Скачивание файла")
    HttpEntity<ByteArrayResource> downloadFile(@RequestParam final String fileName);
}