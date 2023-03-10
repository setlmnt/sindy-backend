package com.ifba.educampo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifba.educampo.domain.Filiacao;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.repository.FiliacaoRepository;
import com.ifba.educampo.requests.FiliacaoPostRequestBody;
import com.ifba.educampo.requests.FiliacaoPutRequestBody;

import java.util.List;
import lombok.RequiredArgsConstructor;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class FiliacaoService {
	@Autowired
	private FiliacaoRepository filiacaoRepository;
	
	public Filiacao findFiliacao(Long id) {
		return filiacaoRepository.findById(id)
				.orElseThrow(()-> new BadRequestException("Filiacao Not Found"));
	}
	
	public List<Filiacao> listAll() {
        return filiacaoRepository.findAll();
    }
	
	public void delete(long id) {
		filiacaoRepository.delete(findFiliacao(id));
	}
	
	@Transactional
	public Filiacao save(FiliacaoPostRequestBody filiacaoPostRequestBody) {
		return filiacaoRepository.save(Filiacao.builder()
						.nomeMae(filiacaoPostRequestBody.getNomeMae())
						.nomePai(filiacaoPostRequestBody.getNomePai())
						.build()
				);
	}
	
	public void replace(FiliacaoPutRequestBody filiacaoPutRequestBody) {
		Filiacao savedFiliacao = findFiliacao(filiacaoPutRequestBody.getId());
		Filiacao filiacao = Filiacao.builder()
										.id(savedFiliacao.getId())
										.nomeMae(filiacaoPutRequestBody.getNomeMae())
										.nomePai(filiacaoPutRequestBody.getNomePai())
										.build();
		
		filiacaoRepository.save(filiacao);
	}
	
}
