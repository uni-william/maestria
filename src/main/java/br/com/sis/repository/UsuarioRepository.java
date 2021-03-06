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
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.com.sis.entity.Colaborador;
import br.com.sis.entity.Endereco;
import br.com.sis.entity.Usuario;
import br.com.sis.repository.filter.UsuarioFilter;
import br.com.sis.util.jsf.FacesUtil;

public class UsuarioRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public List<Usuario> filtrados(UsuarioFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(Usuario.class);

		Root<Usuario> usuarioRoot = criteriaQuery.from(Usuario.class);
		Join<Usuario, Colaborador> colaboradorRoot = (Join) usuarioRoot.fetch("colaborador");
		Join<Colaborador, Endereco> endRoot = (Join) colaboradorRoot.fetch("endereco");

		criteriaQuery.select(usuarioRoot);
		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNotBlank(filter.getLogin())) {
			predicates.add(builder.equal(usuarioRoot.get("login"), filter.getLogin()));
		}

		if (StringUtils.isNotBlank(filter.getNome())) {
			predicates.add(builder.like(builder.lower(colaboradorRoot.get("nome")),
					"%" + filter.getNome().toLowerCase() + "%"));
		}

		if (StringUtils.isNotBlank(filter.getCpf())) {
			predicates.add(builder.equal(colaboradorRoot.get("cpf"), filter.getCpf()));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(colaboradorRoot.get("nome")));

		TypedQuery<Usuario> query = manager.createQuery(criteriaQuery);
		return query.getResultList();

	}

	public Usuario porLogin(String login) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(Usuario.class);
		List<Predicate> predicates = new ArrayList<>();
		Root<Usuario> usuarioRoot = criteriaQuery.from(Usuario.class);
		usuarioRoot.fetch("colaborador", JoinType.INNER);
		predicates.add(builder.equal(usuarioRoot.get("login"), login));
		criteriaQuery.select(usuarioRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		TypedQuery<Usuario> query = manager.createQuery(criteriaQuery);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Usuario usuarioComPaginas(Usuario usuario) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(Usuario.class);
		List<Predicate> predicates = new ArrayList<>();
		Root<Usuario> usuarioRoot = criteriaQuery.from(Usuario.class);
		usuarioRoot.fetch("perfis", JoinType.INNER);
		predicates.add(builder.equal(usuarioRoot.get("id"), usuario.getId()));
		criteriaQuery.select(usuarioRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		TypedQuery<Usuario> query = manager.createQuery(criteriaQuery);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Usuario porId(Long id) {
		return manager.find(Usuario.class, id);
	}

	public Usuario salvar(Usuario usuario) {
		return manager.merge(usuario);
	}

	public Usuario porColaborador(Colaborador colaborador) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(Usuario.class);
		Root<Usuario> usuarioRoot = criteriaQuery.from(Usuario.class);
		usuarioRoot.fetch("colaborador", JoinType.INNER);
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(usuarioRoot.get("colaborador"), colaborador));
		criteriaQuery.select(usuarioRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		TypedQuery<Usuario> query = manager.createQuery(criteriaQuery);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	public boolean remover(Usuario usuario) {
		try { 
			usuario = porId(usuario.getId());
			manager.remove(usuario);
			manager.flush();
			return true;
		} catch (PersistenceException e) {
			FacesUtil.addErroMessage("Usuário de sistema não pode ser excluído.");
			return false;
		}
	}

}
