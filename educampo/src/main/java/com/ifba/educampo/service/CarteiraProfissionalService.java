package com.ifba.educampo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifba.educampo.domain.CarteiraProfissional;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.repository.CarteiraProfissionalRepository;
import com.ifba.educampo.requests.CarteiraProfissionalPostRequestBody;
import com.ifba.educampo.requests.CarteiraProfissionalPutRequestBody;

import java.util.List;
import lombok.RequiredArgsConstructor;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CarteiraProfissionalService {
	@Autowired
	private CarteiraProfissionalRepository carteiraRepository;
	
	public CarteiraProfissional findCarteira(Long id) {
		return carteiraRepository.findById(id)
				.orElseThrow(()-> new BadRequestException("Carteira Not Found"));
	}
	
	public List<CarteiraProfissional> listAll() {
        return carteiraRepository.findAll();
    }
	
	public void delete(long id) {
		carteiraRepository.delete(findCarteira(id));
	}
	
	@Transactional
	public CarteiraProfissional save(CarteiraProfissionalPostRequestBody carteiraPostRequestBody) {
		return carteiraRepository.save(CarteiraProfissional.builder()
						.numero(carteiraPostRequestBody.getNumero())
						.serie(carteiraPostRequestBody.getSerie())
						.build()
				);
	}
	
	public void replace(CarteiraProfissionalPutRequestBody carteiraPutRequestBody) {
		CarteiraProfissional savedCarteira = findCarteira(carteiraPutRequestBody.getId());
		CarteiraProfissional carteira = CarteiraProfissional.builder()
										.id(savedCarteira.getId())
										.numero(carteiraPutRequestBody.getNumero())
										.serie(carteiraPutRequestBody.getSerie())
										.build();
		
		carteiraRepository.save(carteira);
	}
	
}
