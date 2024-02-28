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
import br.com.sindy.domain.service.AssociatePhotoService;
import br.com.sindy.domain.service.FileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Log
public class AssociatePhotoServiceImpl implements AssociatePhotoService {
    private final FileMapper fileMapper;
    private final FileRepository fileRepository;
    private final FileService fileService;
    private final AssociateServiceImpl associateService;
    private final AssociateRepository associateRepository;

    @Value("${app.file.url}")
    private String uploadUrl;

    public FileResponseDto findByAssociateId(Long id) {
        log.info("Finding image by associate id {}", id);
        File file = fileRepository.findProfilePictureByAssociateId(id)
                .orElseThrow(() -> new ApiException(ErrorEnum.ASSOCIATE_IMAGE_NOT_FOUND));
        return fileMapper.toResponseDto(file);
    }

    @Transactional
    public FileResponseDto save(Long associateId, MultipartFile file, String uploadDir) {
        log.info("Saving associate photo with {}", associateId);
        // Se tiver, deleta a imagem antiga do produto
        Optional<File> image = fileRepository.findProfilePictureByAssociateId(associateId);
        if (image.isPresent()) {
            log.info("Deleting old associate photo");
            delete(associateId, uploadDir);
            log.info("Old associate photo deleted");
        }

        log.info("Uploading associate photo");
        if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
            throw new ApiException(ErrorEnum.FILE_MUST_BE_IMAGE);
        }
        FileResponseDto associateImage = upload(associateId, file, uploadDir);
        log.info("Associate photo uploaded");

        log.info("Associate Photo saved");
        File savedFile = fileRepository.save(fileMapper.responseDtoToEntity(associateImage));
        associateRepository.savePhoto(associateId, savedFile.getId());
        return fileMapper.toResponseDto(savedFile);
    }

    @Transactional
    public void delete(Long associateId, String uploadDir) {
        log.info("Deleting associate photo with id {}", associateId);
        FileResponseDto fileResponseDto = findByAssociateId(associateId);
        log.info("Deleting associate photo from associate");
        associateService.deleteImage(associateId);
        log.info("Deleting associate photo from directory");
        fileService.delete(fileResponseDto.id(), uploadDir);
    }

    public Page<FileResponseDto> findDocumentsByAssociateId(Long associateId, Pageable pageable) {
        log.info("Finding documents by associate id {}", associateId);
        return fileRepository.findImagesByAssociateId(associateId, pageable)
                .map(fileMapper::toResponseDto);
    }

    @Transactional
    public FileResponseDto saveDocument(Long associateId, MultipartFile file, String uploadDir) {
        log.info("Saving document with associate id {}", associateId);
        if (!Objects.requireNonNull(file.getContentType()).startsWith("application/") && !Objects.requireNonNull(file.getContentType()).startsWith("text/")) {
            throw new ApiException(ErrorEnum.FILE_MUST_BE_DOCUMENT);
        }

        Associate associate = associateRepository.findById(associateId)
                .orElseThrow(() -> new ApiException(ErrorEnum.ASSOCIATE_NOT_FOUND));

        FileResponseDto fileResponseDto = upload(associateId, file, uploadDir);
        File image = fileMapper.responseDtoToEntity(fileResponseDto);
        image.setAssociate(associate);

        File savedDocument = fileRepository.save(image);
        return fileMapper.toResponseDto(savedDocument);
    }

    private FileResponseDto upload(Long associateId, MultipartFile file, String uploadDir) {
        log.info("Uploading file with id {}", associateId);
        String newFileName = generateNewFilename(associateId, file);
        fileService.store(file, newFileName, uploadDir);
        File image = getImageFromFile(file, newFileName);
        return fileMapper.toResponseDto(image);
    }

    private File getImageFromFile(MultipartFile file, String newFileName) {
        log.info("Getting associate photo from file");
        File image = new File();
        image.setOriginalName(file.getOriginalFilename());
        image.setArchiveName(newFileName);
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setUrl(uploadUrl + "/" + newFileName);
        return image;
    }

    private String generateNewFilename(Long associateId, MultipartFile file) {
        log.info("Generating new file name for associate {}", associateId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSS");
        String formattedDate = LocalDateTime.now().format(formatter);
        return associateId + "-" + formattedDate + Objects.requireNonNull(
                file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf('.')
        );
    }

    public FileResponseDto findDocumentByAssociateIdAndDocumentId(Long associateId, Long documentId) {
        log.info("Finding document by associate id {} and document id {}", associateId, documentId);
        File file = fileRepository.findByIdAndDeletedFalse(documentId)
                .orElseThrow(() -> new ApiException(ErrorEnum.DOCUMENT_NOT_FOUND));
        return fileMapper.toResponseDto(file);
    }

    @Transactional
    public void deleteDocument(Long associateId, Long documentId, String uploadDir) {
        log.info("Deleting document with associate id {} and document id {}", associateId, documentId);
        FileResponseDto fileResponseDto = findDocumentByAssociateIdAndDocumentId(associateId, documentId);
        fileService.delete(fileResponseDto.id(), uploadDir);
    }
}
