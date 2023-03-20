package com.ifba.educampo.requests;

import java.sql.Date;

import com.ifba.educampo.domain.EstadoCivil;

import lombok.Data;

@Data
public class AssociadoPutRequestBody {
	public Long id;
	private EnderecoPutRequestBody endereco;
	private DependentesPutRequestBody dependentes;
	private FiliacaoPutRequestBody filiacao;
	private NaturalidadePutRequestBody naturalidade;
	private FotoAssociadoPutRequestBody fotoAssociado;
	private CarteiraProfissionalPutRequestBody carteiraProfissional;
	private EstadoCivil estadoCivil;
	private String nome;
	private String profissao;
	private String nacionalidade;
	private Date dataNascimento;
	private Date dataAssociacao;
	private String localTrabalho;
	private Long carteiraSindical;
	private String cpf;
	private String rg;
	private boolean sabeLer;
	private boolean eleitor;
	private String celular;
}
