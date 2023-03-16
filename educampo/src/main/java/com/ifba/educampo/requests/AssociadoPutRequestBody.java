package com.ifba.educampo.requests;

import java.sql.Date;

import com.ifba.educampo.domain.CarteiraProfissional;
import com.ifba.educampo.domain.Dependentes;
import com.ifba.educampo.domain.Endereco;
import com.ifba.educampo.domain.EstadoCivil;
import com.ifba.educampo.domain.Filiacao;
import com.ifba.educampo.domain.FotoAssociado;
import com.ifba.educampo.domain.Naturalidade;

import lombok.Data;

@Data
public class AssociadoPutRequestBody {
	public Long Id;
	private Endereco endereco;
	private Dependentes dependentes;
	private Filiacao filiacao;
	private Naturalidade naturalidade;
	private FotoAssociado fotoAssociado;
	private CarteiraProfissional carteiraProfissional;
	private EstadoCivil estadoCivil;
	private String nome;
	private String profissao;
	private String nacionalidade;
	private Date edataNascimento;
	private Date dataAssociacao;
	private String localTrabalho;
	private Long carteiraSindical;
	private String cpf;
	private String rg;
	private boolean sabeLer;
	private boolean eleitor;
}
