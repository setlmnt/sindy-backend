package com.ifba.educampo.domain;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tb_associados")
public class Associado {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "endereco_id")
	private Endereco endereco;
	
	@OneToOne
	@JoinColumn(name = "dependentes_id")
	private Dependentes dependentes;
	
	@OneToOne
	@JoinColumn(name = "filiacao_id")
	private Filiacao filiacao;
	
	@OneToOne
	@JoinColumn(name = "naturalidade_id")
	private Naturalidade naturalidade;
	
	@OneToOne
	@JoinColumn(name = "fotoAssociado_id")
	private FotoAssociado fotoAssociado;
	
	@OneToOne
	@JoinColumn(name = "carteiraProfissional_id")
	private CarteiraProfissional carteiraProfissional;
	
	private EstadoCivil estadoCivil;
	
	private String nome;
	private String profissao;
	private String nacionalidade;
	private String celular;
	private Date dataNascimento;
	private Date dataAssociacao;
	private String localTrabalho;
	private Long carteiraSindical;
	private String cpf;
	private String rg;
	private boolean sabeLer;
	private boolean eleitor;
	
}


