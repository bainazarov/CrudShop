package com.crudshop.demo.service.document;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public interface DocumentService {
    List<String> getReportFileNames();

    String uploadFile(final MultipartFile file);

}
