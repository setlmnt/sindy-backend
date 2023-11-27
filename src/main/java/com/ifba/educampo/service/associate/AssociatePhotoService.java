package com.ifba.educampo.service.associate;

import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.dto.ImageResponseDto;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.ImageMapper;
import com.ifba.educampo.model.entity.Image;
import com.ifba.educampo.repository.ImageRepository;
import com.ifba.educampo.repository.associate.AssociateRepository;
import com.ifba.educampo.service.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Log
public class AssociatePhotoService { // Foto do associado
    private final ImageMapper imageMapper;
    private final ImageRepository imageRepository;
    private final ImageService imageService;
    private final AssociateService associateService;
    private final AssociateRepository associateRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Value("${app.upload.url}")
    private String uploadUrl;

    public ImageResponseDto findByAssociateId(Long id) {
        log.info("Finding image by associate id {}", id);
        Image image = imageRepository.findByAssociateId(id)
                .orElseThrow(() -> new NotFoundException("Associate Image not found"));
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
        log.info("Deleting associate photo from associate");
        associateService.deleteImage(associateId);
        log.info("Deleting associate photo from directory");
        imageService.delete(imageResponseDto.id());
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
        imageService.store(file, newFileName);

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

    private String generateNewFilename(Long associateId, MultipartFile file) {
        log.info("Generating new file name for associate {}", associateId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String formattedDate = LocalDateTime.now().format(formatter);
        return associateId + "-" + formattedDate + Objects.requireNonNull(
                file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf('.')
        );
    }
}
