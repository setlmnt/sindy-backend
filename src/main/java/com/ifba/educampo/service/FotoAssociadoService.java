package com.ifba.educampo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.ifba.educampo.domain.FotoAssociado;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.repository.FotoAssociadoRepository;
import com.ifba.educampo.requests.FotoAssociadoPostRequestBody;
import com.ifba.educampo.requests.FotoAssociadoPutRequestBody;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class FotoAssociadoService {
	@Autowired
	private FotoAssociadoRepository fotoAssociadoRepository;
	
	public FotoAssociado findFotoAssociado(Long id) {
		return fotoAssociadoRepository.findById(id)
				.orElseThrow(()-> new BadRequestException("Foto Not Found"));
	}
	
	public List<FotoAssociado> listAll() {
        return fotoAssociadoRepository.findAll();
    }
	
	public void delete(long id) {
		fotoAssociadoRepository.delete(findFotoAssociado(id));
	}
	
	@Transactional
	public FotoAssociado save(FotoAssociadoPostRequestBody fotoAssociadoPostRequestBody) {
		return fotoAssociadoRepository.save(FotoAssociado.builder()
						.nomeArquivo(fotoAssociadoPostRequestBody.getNomeArquivo())
						.contentType(fotoAssociadoPostRequestBody.getContentType())
						.tamanho(fotoAssociadoPostRequestBody.getTamanho())
						.build()
				);
	}
	
	public FotoAssociado replace(FotoAssociadoPutRequestBody fotoAssociadoPutRequestBody) {
		FotoAssociado savedFotoAssociado = findFotoAssociado(fotoAssociadoPutRequestBody.getId());
		return fotoAssociadoRepository.save(FotoAssociado.builder()
										.id(savedFotoAssociado.getId())
										.nomeArquivo(fotoAssociadoPutRequestBody.getNomeArquivo())
										.contentType(fotoAssociadoPutRequestBody.getContentType())
										.tamanho(fotoAssociadoPutRequestBody.getTamanho())
										.build());
		
	}
	
	public void updateByFields(long id, Map<String, Object> fields) {
		// TODO Auto-generated method stub
		FotoAssociado savedFotoAssociado = findFotoAssociado(id);
		
		fields.forEach((key,value)->{
			Field field = ReflectionUtils.findField(FotoAssociado.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, savedFotoAssociado, value);
		});
		fotoAssociadoRepository.save(savedFotoAssociado);
	}
	
}
