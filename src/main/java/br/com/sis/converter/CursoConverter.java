package br.com.sis.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.sis.entity.Curso;
import br.com.sis.repository.CursoRepository;

@FacesConverter(forClass = Curso.class)
public class CursoConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CursoRepository repository;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Curso curso = null;
		if (StringUtils.isNotBlank(value)) {
			Long id = new Long(value);
			curso = repository.porId(id);
		}
		return curso;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Curso curso = (Curso) value;
			return curso.getId() == null ? null : curso.getId().toString();
		}
		return "";
	}

}
