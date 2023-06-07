package com.ifba.educampo.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.ifba.educampo.domain.AssociatePhoto;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.repository.AssociatePhotoRepository;
import com.ifba.educampo.requests.AssociatePhotoPutRequestBody;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AssociatePhotoService { // Foto do associado
	private final AssociatePhotoRepository associatePhotoRepository;
	
	public AssociatePhoto findAssociatePhoto(Long id) {
		return associatePhotoRepository.findById(id)
				.orElseThrow(()-> new BadRequestException("Associate photo Not Found"));
	}
	
	public List<AssociatePhoto> listAll() {
        return associatePhotoRepository.findAll();
    }
	
	public void delete(long id) {
		associatePhotoRepository.delete(findAssociatePhoto(id));
	}
	
	@Transactional
	public AssociatePhoto save(AssociatePhotoPutRequestBody associatePhotoPostRequestBody) {
		return associatePhotoRepository.save(AssociatePhoto.builder()
						.archiveName(associatePhotoPostRequestBody.getArchiveName())
						.contentType(associatePhotoPostRequestBody.getContentType())
						.size(associatePhotoPostRequestBody.getSize())
						.build()
				);
	}
	
	public AssociatePhoto replace(AssociatePhotoPutRequestBody associatePhotoPutRequestBody, Long assocaitePhotoId) {
		AssociatePhoto savedAssociatePhoto = findAssociatePhoto(assocaitePhotoId);
		return associatePhotoRepository.save(AssociatePhoto.builder()
										.id(savedAssociatePhoto.getId())
										.archiveName(associatePhotoPutRequestBody.getArchiveName())
										.contentType(associatePhotoPutRequestBody.getContentType())
										.size(associatePhotoPutRequestBody.getSize())
										.build());
		
	}
	
	public void updateByFields(long id, Map<String, Object> fields) {
		// TODO Auto-generated method stub
		AssociatePhoto savedAssociatePhoto = findAssociatePhoto(id);
		
		fields.forEach((key,value)->{
			Field field = ReflectionUtils.findField(AssociatePhoto.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, savedAssociatePhoto, value);
		});
		associatePhotoRepository.save(savedAssociatePhoto);
	}
	
}
