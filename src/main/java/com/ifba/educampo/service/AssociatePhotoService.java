package com.ifba.educampo.service;

import com.ifba.educampo.exception.AssociatePhotoException;
import com.ifba.educampo.exception.AssociatePhotoNotFoundException;
import com.ifba.educampo.mapper.GenericMapper;
import com.ifba.educampo.model.dto.AssociateDto;
import com.ifba.educampo.model.dto.AssociatePhotoDto;
import com.ifba.educampo.model.entity.Associate;
import com.ifba.educampo.model.entity.AssociatePhoto;
import com.ifba.educampo.repository.AssociatePhotoRepository;
import com.ifba.educampo.repository.AssociateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AssociatePhotoService { // Foto do associado
    private final static Logger LOGGER = LoggerFactory.getLogger(AssociatePhotoService.class);
    private final GenericMapper<AssociatePhotoDto, AssociatePhoto> modelMapper;
    private final AssociatePhotoRepository associatePhotoRepository;
    private final AssociateService associateService;
    private final AssociateRepository associateRepository;

    public AssociatePhoto uploadAndSaveAssociatePhoto(long associateId, MultipartFile file) {
        try {
            Associate associate = associateService.findAssociate(associateId);

            // Valida se o arquivo é uma imagem
            LOGGER.info("Validating file type");
            if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
                LOGGER.error("File is not an image");
                throw new AssociatePhotoException("File is not an image");
            }

            LOGGER.info("Uploading photo: {}", file.getOriginalFilename());
            String newFileName = associateId + Objects.requireNonNull(
                    file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf('.')
            );
            store(file, newFileName);

            LOGGER.info("Saving photo of associate with ID: {}", associateId);
            AssociatePhoto associatePhoto = new AssociatePhoto();
            associatePhoto.setOriginalName(file.getOriginalFilename());
            associatePhoto.setArchiveName(newFileName);
            associatePhoto.setContentType(file.getContentType());
            associatePhoto.setSize(file.getSize());
            associatePhoto.setUrl("/associates/photos/" + newFileName);

            AssociatePhoto savedPhoto = save(modelMapper.mapModelToDto(associatePhoto, AssociatePhotoDto.class));

            associate.setAssociatePhoto(savedPhoto);
            associateRepository.save(associate);

            return savedPhoto;
        } catch (AssociatePhotoException e) {
            LOGGER.error("Invalid file type: {}", e.getMessage());
            throw new AssociatePhotoException("Invalid file type");
        } catch (Exception e) {
            LOGGER.error("An error occurred while uploading photo: {}", e.getMessage());
            throw new RuntimeException("An error occurred while uploading photo.");
        }
    }

    public void deleteAssociatePhoto(long associateId) {
        try {
            Associate associate = associateService.findAssociate(associateId);

            AssociatePhoto associatePhoto = findAssociatePhotoByAssociateId(associateId);

            LOGGER.info("Deleting photo of associate with ID: {} from database", associateId);
            deleteByAssociateId(associateId);

            LOGGER.info("Deleting photo of associate with ID: {} from disk", associateId);
            deleteFile(associatePhoto.getArchiveName());

            LOGGER.info("Updating associate with ID: {} to remove photo", associateId);
            associate.setAssociatePhoto(null);
            associateRepository.save(associate);
        } catch (Exception e) {
            LOGGER.error("An error occurred while deleting photo: {}", e.getMessage());
            throw new RuntimeException("An error occurred while deleting photo.");
        }
    }

    public AssociatePhoto findAssociatePhotoByAssociateId(Long associateId) {
        try {
            LOGGER.info("Finding associate photo by associate ID: {}", associateId);
            return associatePhotoRepository.findAssociatePhotoByAssociateId(associateId);
        } catch (Exception e) {
            LOGGER.error("An error occurred while finding associate photo by associate ID: {}", associateId, e);
            throw new RuntimeException("An error occurred while finding associate photo by associate ID: " + associateId, e);
        }
    }

    public Page<AssociatePhoto> listAll(Pageable pageable) {
        try {
            LOGGER.info("Listing all associate photos.");
            return associatePhotoRepository.findAll(pageable);
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing associate photos.", e);
            throw new RuntimeException("An error occurred while listing associate photos.");
        }
    }

    @Transactional
    public void deleteByAssociateId(long associateId) {
        try {
            LOGGER.info("Deleting associate photo by associate ID: {}", associateId);
            associatePhotoRepository.deleteAssociatePhotoByAssociateId(associateId);
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing associate photos.", e);
            throw new RuntimeException("An error occurred while deleting associate photo.", e);
        }
    }

    @Transactional
    public AssociatePhoto save(AssociatePhotoDto associatePhotoDto) {
        try {
            LOGGER.info("Saving associate photo.");
            return associatePhotoRepository.save(modelMapper.mapDtoToModel(associatePhotoDto, AssociatePhoto.class));
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing associate photos.", e);
            throw new RuntimeException("An error occurred while saving associate photo.", e);
        }
    }

    @Transactional
    public AssociatePhoto replace(AssociatePhotoDto associatePhotoDto, Long associateId) {
        try {
            LOGGER.info("Replacing associate photo.");
            AssociatePhoto savedAssociatePhoto = findAssociatePhotoByAssociateId(associateId);
            associatePhotoDto.setId(savedAssociatePhoto.getId());

            return associatePhotoRepository.save(modelMapper.mapDtoToModel(associatePhotoDto, AssociatePhoto.class));
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing associate photos.", e);
            throw new RuntimeException("An error occurred while replacing associate photo.", e);
        }
    }

    private final String uploadDir = "uploads";

    public void store(MultipartFile file, String fileName) {
        try {
            LOGGER.info("Storing file: {}", fileName);
            Path targetPath = Paths.get(uploadDir).resolve(fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            LOGGER.error("An error occurred while storing the file: {}", e.getMessage());
            throw new RuntimeException("An error occurred while storing the file");
        }
    }

    public Resource load(String fileName) {
        try {
            LOGGER.info("Loading file: {}", fileName);
            Path filePath = Paths.get(uploadDir).resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }

            LOGGER.error("File not found: {}", fileName);
            throw new FileNotFoundException("File not found: " + fileName);
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found: {}", fileName);
            throw new AssociatePhotoNotFoundException("File not found");
        } catch (MalformedURLException e) {
            LOGGER.error("An error occurred while loading the file: {}", e.getMessage());
            throw new RuntimeException("An error occurred while loading the file", e);
        }
    }

    public void deleteFile(String fileName) {
        try {
            LOGGER.info("Deleting file: {}", fileName);
            Path filePath = Paths.get(uploadDir).resolve(fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            LOGGER.error("An error occurred while deleting the file: {}", e.getMessage());
            throw new RuntimeException("An error occurred while deleting the file", e);
        }
    }
}
