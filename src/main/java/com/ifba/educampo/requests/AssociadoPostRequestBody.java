package com.ifba.educampo.requests;

import java.sql.Date;

import com.ifba.educampo.domain.EstadoCivil;

import lombok.Data;

@Data
public class AssociadoPostRequestBody {
	private EnderecoPostRequestBody endereco;
	private DependentesPostRequestBody dependentes;
	private FiliacaoPostRequestBody filiacao;
	private NaturalidadePostRequestBody naturalidade;
	private FotoAssociadoPostRequestBody fotoAssociado;
	private CarteiraProfissionalPostRequestBody carteiraProfissional;
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
