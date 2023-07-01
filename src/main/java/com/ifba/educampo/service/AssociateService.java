package com.ifba.educampo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.ifba.educampo.domain.Associate;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.repository.AssociateRepository;
import com.ifba.educampo.requests.AssociatePostRequestBody;
import com.ifba.educampo.requests.AssociatePutRequestBody;

import java.lang.reflect.Field;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AssociateService { // Classe de serviço para o Associado
	private final AssociateRepository associateRepository;
	private final AddressService addressService;
	private final WorkRecordService workRecordService;
	private final DependentsService dependentsService;
	private final AffiliationService affiliationService;
	private final AssociatePhotoService associatePhotoService;
	private final PlaceOfBirthService placeOfBirthService;
	private final LocalOfficeService localOfficeService;
	
	public Associate findAssociate(Long id) {
		return associateRepository.findById(id)
				.orElseThrow(()-> new BadRequestException("Associate Not Found"));
	}

	public Page<Associate> findAssociateByNameOrCpfOrUnionCard(String query, Pageable pageable) {
		return associateRepository.findByNameOrCpfOrUnionCard(query, pageable)
				.orElseThrow(()-> new BadRequestException("Associate Not Found"));
	}
	
	public Page<Associate> listAll(Pageable pageable) {
        return associateRepository.findAll(pageable);
    }

	@Transactional
	public void delete(long id) {
		associateRepository.delete(findAssociate(id));
	}
	
	@Transactional
	public Associate save(AssociatePostRequestBody associatePostRequestBody) {
		return associateRepository.save(Associate.builder()
						.name(associatePostRequestBody.getName())
						.unionCard(associatePostRequestBody.getUnionCard())
						.cpf(associatePostRequestBody.getCpf())
						.rg(associatePostRequestBody.getRg())
						.profession(associatePostRequestBody.getProfession())
						.workplace(associatePostRequestBody.getWorkplace())
						.phone(associatePostRequestBody.getPhone())
						.nationality(associatePostRequestBody.getNationality())
						.birthDate(associatePostRequestBody.getBirthDate())
						.isLiterate(associatePostRequestBody.isLiterate())
						.isVoter(associatePostRequestBody.isVoter())
						.maritalStatus(associatePostRequestBody.getMaritalStatus())
						.associationDate(associatePostRequestBody.getAssociationDate())
						.address(
								associatePostRequestBody.getAddress() != null ?
										addressService.save(associatePostRequestBody.getAddress()) :
										null
						)
						.dependents(dependentsService.save(associatePostRequestBody.getDependents()))
						.affiliation(affiliationService.save(associatePostRequestBody.getAffiliation()))
						.placeOfBirth(placeOfBirthService.save(associatePostRequestBody.getPlaceOfBirth()))
						.associatePhoto(associatePhotoService.save(associatePostRequestBody.getAssociatePhoto()))
						.workRecord(workRecordService.save(associatePostRequestBody.getWorkRecord()))
						.localOffice(
								associatePostRequestBody.getLocalOfficeId() != null ?
								localOfficeService.findLocalOffice(associatePostRequestBody.getLocalOfficeId()) :
								null
						)
						.build()
				);
	}

	@Transactional
	public void replace(AssociatePutRequestBody associatePutRequestBody) {
		Associate savedAssociate = findAssociate(associatePutRequestBody.getId());
		Associate associate = Associate.builder()
										.id(savedAssociate.getId())
										.name(associatePutRequestBody.getName())
										.unionCard(associatePutRequestBody.getUnionCard())
										.cpf(associatePutRequestBody.getCpf())
										.rg(associatePutRequestBody.getRg())
										.profession(associatePutRequestBody.getProfession())
										.workplace(associatePutRequestBody.getWorkplace())
										.phone(associatePutRequestBody.getPhone())
										.nationality(associatePutRequestBody.getNationality())
										.birthDate(associatePutRequestBody.getBirthDate())
										.isLiterate(associatePutRequestBody.isLiterate())
										.isVoter(associatePutRequestBody.isVoter())
										.maritalStatus(associatePutRequestBody.getMaritalStatus())
										.associationDate(associatePutRequestBody.getAssociationDate())
										.address(addressService
												.replace(associatePutRequestBody.getAddress(),
														savedAssociate.getAddress().getId()))
										.dependents(dependentsService
												.replace(associatePutRequestBody.getDependents(),
												savedAssociate.getDependents().getId()))
										.affiliation(affiliationService
												.replace(associatePutRequestBody.getAffiliation(),
												savedAssociate.getAffiliation().getId()))
										.placeOfBirth(placeOfBirthService
												.replace(associatePutRequestBody.getPlaceOfBirth(),
												savedAssociate.getPlaceOfBirth().getId()))
										.associatePhoto(associatePhotoService
												.replace(associatePutRequestBody.getAssociatePhoto(),
												savedAssociate.getAssociatePhoto().getId()))
										.workRecord(workRecordService
												.replace(associatePutRequestBody.getWorkRecord(),
												savedAssociate.getWorkRecord().getId()))
										.localOffice(
												associatePutRequestBody.getLocalOfficeId() != null ?
												localOfficeService.findLocalOffice(associatePutRequestBody.getLocalOfficeId()) :
												null
										)
										.build();
		
		associateRepository.save(associate);
		
	}

	@Transactional
	public void updateByFields(long id, Map<String, Object> fields) {
		Associate savedAssociate = findAssociate(id);
		
		fields.forEach((key,value)->{
			Field field = ReflectionUtils.findField(Associate.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, savedAssociate, value);
		});
		associateRepository.save(savedAssociate);
	}
	
}
