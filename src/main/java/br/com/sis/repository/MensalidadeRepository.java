package br.com.sis.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.com.sis.entity.Aluno;
import br.com.sis.entity.Contrato;
import br.com.sis.entity.Mensalidade;
import br.com.sis.entity.Responsavel;
import br.com.sis.repository.filter.MensalidadeFilter;
import br.com.sis.util.jpa.Transactional;
import br.com.sis.util.jsf.FacesUtil;

public class MensalidadeRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Mensalidade porId(Long id) {
		return manager.find(Mensalidade.class, id);
	}

	public Mensalidade salvar(Mensalidade mensalidade) {
		return manager.merge(mensalidade);
	}

	@Transactional
	public boolean remover(Mensalidade mensalidade) {
		try {
			mensalidade = porId(mensalidade.getId());
			manager.remove(mensalidade);
			manager.flush();
			return true;
		} catch (PersistenceException e) {
			FacesUtil.addErroMessage("Mensalidade não pode ser excluída. " + e.getMessage());
			return false;
		}
	}

	public List<Mensalidade> listarTodos() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Mensalidade> criteriaQuery = builder.createQuery(Mensalidade.class);
		Root<Mensalidade> mensalidadeRoot = criteriaQuery.from(Mensalidade.class);
		criteriaQuery.select(mensalidadeRoot);
		TypedQuery<Mensalidade> query = manager.createQuery(criteriaQuery);
		List<Mensalidade> lista = query.getResultList();
		return lista;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Mensalidade> filtrados(MensalidadeFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Mensalidade> criteriaQuery = builder.createQuery(Mensalidade.class);
		Root<Mensalidade> mensalidadeRoot = criteriaQuery.from(Mensalidade.class);
		Join<Contrato, Mensalidade> contratoRoot = (Join) mensalidadeRoot.fetch("contrato");
		Join<Aluno, Contrato> alunoRoot = (Join) contratoRoot.fetch("aluno");
		Join<Responsavel, Aluno> responsavelRoot = (Join) alunoRoot.fetch("responsavel");
		criteriaQuery.select(mensalidadeRoot);
		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNotBlank(filter.getCpf())) {
			predicates.add(builder.equal(responsavelRoot.get("cpf"), filter.getCpf()));
		}

		if (StringUtils.isNotBlank(filter.getNomeResponsavel())) {
			predicates.add(builder.like(builder.lower(responsavelRoot.get("nome")),
					"%" + filter.getNomeResponsavel().toLowerCase() + "%"));
		}
		
		if (StringUtils.isNotBlank(filter.getNomeAluno())) {
			predicates.add(builder.like(builder.lower(alunoRoot.get("nome")),
					"%" + filter.getNomeAluno().toLowerCase() + "%"));
		}		

		if (filter.getDataVenctoIni() != null) {
			predicates.add(builder.greaterThanOrEqualTo(mensalidadeRoot.get("vencimento"), filter.getDataVenctoIni()));
		}
		if (filter.getDataVenctoFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(mensalidadeRoot.get("vencimento"), filter.getDataVenctoFim()));
		}

		if (filter.getStatusMatricula() != null) {
			predicates.add(builder.equal(contratoRoot.get("status"), filter.getStatusMatricula()));
		}
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(mensalidadeRoot.get("contrato")), builder.asc(mensalidadeRoot.get("numParcela")));
		TypedQuery<Mensalidade> query = manager.createQuery(criteriaQuery);
		List<Mensalidade> lista = query.getResultList();
		return lista;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Mensalidade> parcelasEmAberto(MensalidadeFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Mensalidade> criteriaQuery = builder.createQuery(Mensalidade.class);
		Root<Mensalidade> mensalidadeRoot = criteriaQuery.from(Mensalidade.class);
		Join<Contrato, Mensalidade> contratoRoot = (Join) mensalidadeRoot.fetch("contrato");
		Join<Aluno, Contrato> alunoRoot = (Join) contratoRoot.fetch("aluno");
		Join<Responsavel, Aluno> responsavelRoot = (Join) alunoRoot.fetch("responsavel");
		criteriaQuery.select(mensalidadeRoot);
		List<Predicate> predicates = new ArrayList<>();
		
		predicates.add(builder.isNull(mensalidadeRoot.get("pagamento")));

		if (StringUtils.isNotBlank(filter.getCpf())) {
			predicates.add(builder.equal(responsavelRoot.get("cpf"), filter.getCpf()));
		}

		if (StringUtils.isNotBlank(filter.getNomeResponsavel())) {
			predicates.add(builder.like(builder.lower(responsavelRoot.get("nome")),
					"%" + filter.getNomeResponsavel().toLowerCase() + "%"));
		}
		
		if (StringUtils.isNotBlank(filter.getNomeAluno())) {
			predicates.add(builder.like(builder.lower(alunoRoot.get("nome")),
					"%" + filter.getNomeAluno().toLowerCase() + "%"));
		}		

		if (filter.getDataVenctoFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(mensalidadeRoot.get("vencimento"), filter.getDataVenctoFim()));
		}

		if (filter.getStatusMatricula() != null) {
			predicates.add(builder.equal(contratoRoot.get("status"), filter.getStatusMatricula()));
		}
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(mensalidadeRoot.get("contrato")), builder.asc(mensalidadeRoot.get("numParcela")));
		TypedQuery<Mensalidade> query = manager.createQuery(criteriaQuery);
		List<Mensalidade> lista = query.getResultList();
		return lista;
	}	

	public boolean parcelaJaGerada(Contrato contrato, Integer numParcela) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Mensalidade> mensalidadeRoot = criteriaQuery.from(Mensalidade.class);
		criteriaQuery.select(builder.count(criteriaQuery.from(Mensalidade.class)));
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(mensalidadeRoot.get("contrato"), contrato));
		predicates.add(builder.equal(mensalidadeRoot.get("numParcela"), numParcela));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Long> query = manager.createQuery(criteriaQuery);
		Long resultado = query.getSingleResult();

		return resultado > 0;
	}

	public Mensalidade porContratoEParcela(Contrato contrato, Integer numParcela) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Mensalidade> criteriaQuery = builder.createQuery(Mensalidade.class);
		Root<Mensalidade> mensalidadeRoot = criteriaQuery.from(Mensalidade.class);
		criteriaQuery.select(mensalidadeRoot);
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(mensalidadeRoot.get("contrato"), contrato));
		predicates.add(builder.equal(mensalidadeRoot.get("numParcela"), numParcela));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		TypedQuery<Mensalidade> query = manager.createQuery(criteriaQuery);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	public Integer maximaParcelaGerada(Contrato contrato) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Integer> criteriaQuery = builder.createQuery(Integer.class);
		Root<Mensalidade> mensalidadeRoot = criteriaQuery.from(Mensalidade.class);
		criteriaQuery.select(builder.max(mensalidadeRoot.get("numParcela")));
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(mensalidadeRoot.get("contrato"), contrato));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		Integer resultado;
		TypedQuery<Integer> query = manager.createQuery(criteriaQuery);
		try {
			resultado = query.getSingleResult();
			if (resultado == null) {
				resultado = 0;
			}
		} catch (NoResultException e) {
			resultado = 0;
		}

		return resultado;
	}

}
