package com.crudshop.demo.controller.document;

import com.crudshop.demo.exception.DocumentNotFoundException;
import com.crudshop.demo.service.document.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DocumentControllerImpl implements DocumentController {
    private final DocumentService documentService;

    @Override
    public List<String> getReportFileNames() {
        return documentService.getReportFileNames();
    }

    @Override
    public ResponseEntity<String> uploadFile(@RequestParam("file") final MultipartFile file) {
        try {
            final String result = documentService.uploadFile(file);
            log.info("Загрузили файл с названием " + file);

            return ResponseEntity.ok().body(result);
        } catch (IOException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при загрузке файла: " + e.getMessage());
        }
    }

    @Override
    public HttpEntity<ByteArrayResource> downloadFile(@RequestParam final String fileName) throws IOException {
        final File file = new File("src/main/resources/reports/" + fileName);
        InputStreamResource resource;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new DocumentNotFoundException("Файл с таким названием " + fileName + " не существует");
        }

        final HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

        log.info("Загрузили файл с названием " + fileName);

        return new HttpEntity<>(new ByteArrayResource(resource.getContentAsByteArray()), header);

    }
}