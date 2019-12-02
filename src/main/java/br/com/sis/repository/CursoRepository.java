package br.com.sis.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.sis.entity.Curso;
import br.com.sis.util.jpa.Transactional;
import br.com.sis.util.jsf.FacesUtil;

public class CursoRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Curso porId(Long id) {
		return manager.find(Curso.class, id);
	}
	
	public Curso salvar(Curso curso) {
		return manager.merge(curso);
	}
	
	@Transactional
	public boolean remover(Curso curso) {
		try {
			curso = porId(curso.getId());
			manager.remove(curso);
			manager.flush();
			return true;
		} catch (PersistenceException e) {
			FacesUtil.addErroMessage("Curso não pode ser excluído. " + e.getMessage());
			return false;
		}
	}
	
	public List<Curso> listarTodos() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Curso> criteriaQuery = builder.createQuery(Curso.class);
		Root<Curso> cursoRoot = criteriaQuery.from(Curso.class);
		criteriaQuery.select(cursoRoot);
		TypedQuery<Curso> query = manager.createQuery(criteriaQuery);
		List<Curso> lista = query.getResultList();
		return lista;
	}	

}
