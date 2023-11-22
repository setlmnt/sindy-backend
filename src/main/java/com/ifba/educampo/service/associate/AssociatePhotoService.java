package com.ifba.educampo.service.associate;

import com.ifba.educampo.dto.ImageResponseDto;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.exception.EntityNotFoundException;
import com.ifba.educampo.mapper.ImageMapper;
import com.ifba.educampo.model.entity.Image;
import com.ifba.educampo.repository.ImageRepository;
import com.ifba.educampo.repository.associate.AssociateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AssociatePhotoService { // Foto do associado
    private final ImageMapper imageMapper;
    private final ImageRepository imageRepository;
    private final AssociateService associateService;
    private final AssociateRepository associateRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Value("${app.upload.url}")
    private String uploadUrl;

    public Page<ImageResponseDto> listAll(Pageable pageable) {
        log.info("Listing all images");
        return imageRepository.findAll(pageable).map(imageMapper::toResponseDto);
    }

    public ImageResponseDto findByAssociateId(Long id) {
        log.info("Finding image by associate id {}", id);
        Image image = imageRepository.findByAssociateId(id)
                .orElseThrow(() -> new EntityNotFoundException("Associate Image not found"));
        return imageMapper.toResponseDto(image);
    }

    public ImageResponseDto save(Long associateId, MultipartFile file) {
        log.info("Saving associate photo with {}", associateId);
        // Se tiver, deleta a imagem antiga do produto
        Optional<Image> image = imageRepository.findByAssociateId(associateId);
        if (image.isPresent()) {
            log.info("Deleting old associate photo");
            delete(associateId);
            log.info("Old associate photo deleted");
        }

        log.info("Uploading associate photo");
        ImageResponseDto associateImage = upload(associateId, file);
        log.info("Associate photo uploaded");

        log.info("Associate Photo saved");
        Image savedImage = imageRepository.save(imageMapper.responseDtoToEntity(associateImage));
        associateRepository.savePhoto(associateId, savedImage.getId());
        return imageMapper.toResponseDto(savedImage);
    }

    public void delete(Long associateId) {
        log.info("Deleting associate photo with id {}", associateId);
        ImageResponseDto imageResponseDto = findByAssociateId(associateId);
        Image image = imageRepository.getReferenceById(imageResponseDto.id());
        image.delete();

        log.info("Deleting associate photo from associate");
        associateService.deleteImage(associateId);

        log.info("Deleting associate photo from directory");
        deleteFile(imageResponseDto.archiveName());
    }

    public Resource load(String name) {
        try {
            log.info("Loading file {}", name);
            Path filePath = Paths.get(uploadDir).resolve(name);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }

            log.error("File not found: " + name);
            throw new FileNotFoundException("File not found: " + name);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (MalformedURLException e) {
            log.error("An error occurred while loading the file", e);
            throw new RuntimeException("An error occurred while loading the file", e);
        }
    }

    private ImageResponseDto upload(Long associateId, MultipartFile file) {
        log.info("Uploading file with id {}", associateId);
        // Valida se o arquivo é uma imagem
        if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
            throw new BadRequestException("File must be an image");
        }

        log.info("Uploading associate photo from associate");
        // Upload da imagem
        String newFileName = generateNewFilename(associateId, file);
        store(file, newFileName);

        log.info("Saving associate photo from directory");
        // Coloca os dados da imagem que foi feito o upload no objeto para retornar
        Image image = getAssociatePhotoFromFile(file, newFileName);
        return imageMapper.toResponseDto(image);
    }

    private Image getAssociatePhotoFromFile(MultipartFile file, String newFileName) {
        log.info("Getting associate photo from file");
        Image image = new Image();
        image.setOriginalName(file.getOriginalFilename());
        image.setArchiveName(newFileName);
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setUrl(uploadUrl + "/" + newFileName);
        return image;
    }

    private void store(MultipartFile file, String fileName) {
        try {
            log.info("Storing file {}", fileName);
            Path targetPath = Paths.get(uploadDir).resolve(fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("An error occurred while storing the file", e);
            throw new RuntimeException("An error occurred while storing the file", e);
        }
    }

    private void deleteFile(String fileName) {
        try {
            log.info("Deleting file {}", fileName);
            Path filePath = Paths.get(uploadDir).resolve(fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error("An error occurred while storing the file", e);
            throw new RuntimeException("An error occurred while deleting the file", e);
        }
    }

    private String generateNewFilename(Long associateId, MultipartFile file) {
        log.info("Generating new file name for associate {}", associateId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String formattedDate = LocalDateTime.now().format(formatter);
        return associateId + "-" + formattedDate + Objects.requireNonNull(
                file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf('.')
        );
    }
}
