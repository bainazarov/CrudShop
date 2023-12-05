package com.crudshop.demo.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    @Override
    public List<String> getReportFileNames() {
        final List<String> fileNames = new ArrayList<>();
        final File reportsFolder = new File("src/main/resources/reports");
        if (reportsFolder.exists() && reportsFolder.isDirectory()) {
            File[] files = reportsFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        fileNames.add(file.getName());
                    }
                }
            }
        }
        return fileNames;
    }
}
