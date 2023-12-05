package com.crudshop.demo.controller.document;

import com.crudshop.demo.service.document.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentControllerImpl implements DocumentController {
    private final DocumentService documentService;

    @Override
    public List<String> getReportFileNames() {
        return documentService.getReportFileNames();
    }
}