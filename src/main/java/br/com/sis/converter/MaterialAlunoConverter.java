package br.com.sis.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.sis.entity.MaterialAluno;
import br.com.sis.repository.MaterialAlunoRepository;

@FacesConverter(forClass = MaterialAluno.class)
public class MaterialAlunoConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MaterialAlunoRepository repository;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		MaterialAluno materialAluno = null;
		if (StringUtils.isNotBlank(value)) {
			Long id = new Long(value);
			materialAluno = repository.porId(id);
		}
		return materialAluno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			MaterialAluno materialAluno = (MaterialAluno) value;
			return materialAluno.getId() == null ? null : materialAluno.getId().toString();
		}
		return "";
	}

}
