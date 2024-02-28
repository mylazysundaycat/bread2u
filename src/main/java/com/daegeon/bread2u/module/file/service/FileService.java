package com.daegeon.bread2u.module.file.service;

import com.daegeon.bread2u.module.file.entity.File;
import com.daegeon.bread2u.module.file.entity.FileDto;
import com.daegeon.bread2u.module.file.repository.FileRepository;
import com.daegeon.bread2u.module.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    @Value("${spring.servlet.multipart.location}")
    private String location;
    @Transactional
    public File createFile(MultipartFile file) {

        String uploadDir = location;
        String originName = file.getOriginalFilename();
        String extension = originName.substring( originName.lastIndexOf('.')+1);

        String uploadName = UUID.randomUUID().toString().replace("-", "") + "_" + originName;
        String path = Paths.get(uploadDir, uploadName).toString();

        File saveFile = File.builder()
                .originName(originName)
                .uploadName(uploadName)
                .extension(extension)
                .path(path)
                .build();

        try {
            Files.copy(file.getInputStream(), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {}

        return fileRepository.save(saveFile);
    }

}
