package br.com.sis.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.sis.entity.Responsavel;
import br.com.sis.repository.ResponsavelRepository;

@FacesConverter(forClass = Responsavel.class)
public class ResponsavelConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ResponsavelRepository repository;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Responsavel responsavel = null;
		if (StringUtils.isNotBlank(value)) {
			Long id = new Long(value);
			responsavel = repository.porId(id);
		}
		return responsavel;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Responsavel responsavel = (Responsavel) value;
			return responsavel.getId() == null ? null : responsavel.getId().toString();
		}
		return "";
	}

}
