package br.com.sis.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.sis.entity.Aluno;
import br.com.sis.repository.AlunoRepository;

@FacesConverter(forClass = Aluno.class)
public class AlunoConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private AlunoRepository repository;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Aluno aluno = null;
		if (StringUtils.isNotBlank(value)) {
			Long id = new Long(value);
			aluno = repository.porId(id);
		}
		return aluno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Aluno aluno = (Aluno) value;
			return aluno.getId() == null ? null : aluno.getId().toString();
		}
		return "";
	}

}
