package br.com.sis.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.sis.entity.Colaborador;
import br.com.sis.repository.ColaboradorRepository;

@FacesConverter(forClass = Colaborador.class)
public class ColaboradorConverter implements Converter {

	@Inject
	private ColaboradorRepository repository;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Colaborador colaborador = null;
		if (StringUtils.isNotBlank(value)) {
			Long id = new Long(value);
			colaborador = repository.porId(id);
		}
		return colaborador;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Colaborador colaborador = (Colaborador) value;
			return colaborador.getId() == null ? null : colaborador.getId().toString();
		}
		return "";
	}

}
