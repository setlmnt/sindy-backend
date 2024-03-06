package br.com.sindy.api.controller;

import br.com.sindy.domain.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.activation.MimetypesFileTypeMap;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

@Tag(name = "Files", description = "Files API")
@RestController
@RequestMapping(value = "/api/v1/files")
@SecurityRequirements({
        @SecurityRequirement(name = "bearerAuth"),
        @SecurityRequirement(name = "cookieAuth")
})
@RequiredArgsConstructor
public class FileController {
    @Value("${app.upload.dir}")
    private String uploadDir;

    private final FileService fileService;

    @Operation(summary = "Load file by name")
    @GetMapping(path = "/{name}")
    public ResponseEntity<?> loadFile(@PathVariable String name) {
        try {
            Resource resource = fileService.load(name, uploadDir);

            if (Objects.isNull(resource) || !resource.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
            String contentType = mimeTypesMap.getContentType(resource.getFilename());

            byte[] fileBytes = Files.readAllBytes(resource.getFile().toPath());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.parseMediaType(contentType).toString());
            headers.setContentLength(fileBytes.length);

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
