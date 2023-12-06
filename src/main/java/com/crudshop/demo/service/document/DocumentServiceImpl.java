package com.crudshop.demo.service.document;

import com.crudshop.demo.exception.CopyingFileException;
import com.crudshop.demo.exception.CreatingDirectoryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final String FOLDER_PATH = "src/main/resources/reports";

    @Override
    public List<String> getReportFileNames() {
        final List<String> fileNames = new ArrayList<>();
        final File reportsFolder = new File(FOLDER_PATH);
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

    @Override
    public String uploadFile(final MultipartFile file) {
        final Path path = Paths.get(FOLDER_PATH);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new CreatingDirectoryException("Ошибка при создании дириктории " + path);
            }
        }
        final String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        final Path filePath = path.resolve(fileName);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new CopyingFileException("Ошибка при копировании файла " + fileName);
        }

        return "Файл успешно загружен: " + fileName;
    }
}
