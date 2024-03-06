package br.com.sindy.domain.service.impl;

import br.com.sindy.core.annotation.Log;
import br.com.sindy.domain.dto.FileResponseDto;
import br.com.sindy.domain.entity.File;
import br.com.sindy.domain.entity.associate.Associate;
import br.com.sindy.domain.enums.ErrorEnum;
import br.com.sindy.domain.exception.ApiException;
import br.com.sindy.domain.mapper.FileMapper;
import br.com.sindy.domain.repository.AssociateRepository;
import br.com.sindy.domain.repository.FileRepository;
import br.com.sindy.domain.service.AssociateFileService;
import br.com.sindy.domain.service.FileService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Log
public class AssociateFileServiceImpl implements AssociateFileService {
    @Value("${app.file.url}")
    private String uploadUrl;

    @Value("${app.upload.images.dir}")
    private String uploadProfilePictureDir;

    @Value("${app.upload.docs.dir}")
    private String uploadDocsDir;

    private final FileMapper fileMapper;
    private final FileRepository fileRepository;
    private final FileService fileService;
    private final AssociateServiceImpl associateService;
    private final AssociateRepository associateRepository;

    @PostConstruct
    public void postConstruct() {
        log.info("Ensuring upload directory exists");
        ensureUploadDirExists(uploadProfilePictureDir);
        ensureUploadDirExists(uploadDocsDir);
    }

    @Override
    public FileResponseDto findByAssociateId(Long id) {
        log.info("Finding file by associate id {}", id);
        File file = fileRepository.findProfilePictureByAssociateId(id)
                .orElseThrow(() -> new ApiException(ErrorEnum.ASSOCIATE_IMAGE_NOT_FOUND));
        return fileMapper.toResponseDto(file);
    }

    @Override
    @Transactional
    public FileResponseDto saveProfilePicture(Long associateId, MultipartFile file) {
        log.info("Saving associate photo with {}", associateId);

        Optional<File> newFile = fileRepository.findProfilePictureByAssociateId(associateId);
        if (newFile.isPresent()) {
            log.info("Deleting old associate photo");
            deleteProfilePicture(associateId);
            log.info("Old associate photo deleted");
        }

        log.info("Uploading associate photo");
        FileResponseDto associateImage = upload(associateId, file, uploadProfilePictureDir);
        log.info("Associate photo uploaded");

        log.info("Associate Photo saved");
        File savedFile = fileRepository.save(fileMapper.responseDtoToEntity(associateImage));
        associateRepository.savePhoto(associateId, savedFile.getId());
        return fileMapper.toResponseDto(savedFile);
    }

    @Override
    @Transactional
    public void deleteProfilePicture(Long associateId) {
        log.info("Deleting associate photo with id {}", associateId);
        FileResponseDto fileResponseDto = findByAssociateId(associateId);
        log.info("Deleting associate photo from associate");
        associateService.deleteImage(associateId);
        log.info("Deleting associate photo from directory");
        fileService.delete(fileResponseDto.id(), uploadProfilePictureDir);
    }

    @Override
    public FileResponseDto findByAssociateIdAndDocumentId(Long associateId, Long documentId) {
        log.info("Finding document by associate id {} and document id {}", associateId, documentId);
        File file = fileRepository.findByIdAndDeletedFalse(documentId)
                .orElseThrow(() -> new ApiException(ErrorEnum.DOCUMENT_NOT_FOUND));
        return fileMapper.toResponseDto(file);
    }

    @Override
    @Transactional
    public void deleteDocument(Long associateId, Long documentId) {
        log.info("Deleting document with associate id {} and document id {}", associateId, documentId);
        FileResponseDto fileResponseDto = findByAssociateIdAndDocumentId(associateId, documentId);
        fileService.delete(fileResponseDto.id(), uploadDocsDir);
    }

    @Override
    public Page<FileResponseDto> findByAssociateId(Long associateId, Pageable pageable) {
        log.info("Finding documents by associate id {}", associateId);
        return fileRepository.findImagesByAssociateId(associateId, pageable)
                .map(fileMapper::toResponseDto);
    }

    @Override
    @Transactional
    public FileResponseDto saveDocument(Long associateId, MultipartFile file) {
        log.info("Saving document with associate id {}", associateId);
        Associate associate = associateRepository.findById(associateId)
                .orElseThrow(() -> new ApiException(ErrorEnum.ASSOCIATE_NOT_FOUND));

        FileResponseDto fileResponseDto = upload(associateId, file, uploadDocsDir);
        File newFile = fileMapper.responseDtoToEntity(fileResponseDto);
        newFile.setAssociate(associate);

        File savedDocument = fileRepository.save(newFile);
        return fileMapper.toResponseDto(savedDocument);
    }

    private FileResponseDto upload(Long associateId, MultipartFile file, String uploadDir) {
        log.info("Uploading file with id {}", associateId);
        String newFileName = generateNewFilename(associateId, file);
        fileService.store(file, newFileName, uploadDir);
        File newFile = getNewFile(file, newFileName);
        return fileMapper.toResponseDto(newFile);
    }

    private File getNewFile(MultipartFile file, String newFileName) {
        log.info("Getting new file from {}", file);
        File newFile = new File();
        newFile.setOriginalName(file.getOriginalFilename());
        newFile.setArchiveName(newFileName);
        newFile.setContentType(file.getContentType());
        newFile.setSize(file.getSize());
        newFile.setUrl(uploadUrl + "/" + newFileName);
        return newFile;
    }

    private String generateNewFilename(Long associateId, MultipartFile file) {
        log.info("Generating new file name for associate {}", associateId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSS");
        String formattedDate = LocalDateTime.now().format(formatter);
        return associateId + "-" + formattedDate + Objects.requireNonNull(
                file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf('.')
        );
    }

    private void ensureUploadDirExists(String dir) {
        Path path = Paths.get(dir);
        if (!Files.exists(path)) {
            log.info("Creating upload directory at {}", dir);
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                log.error("An error occurred while creating the upload directory", e);
                throw new ApiException(ErrorEnum.ERROR_WHILE_CREATING_DIRECTORY);
            }
        }
    }
}
