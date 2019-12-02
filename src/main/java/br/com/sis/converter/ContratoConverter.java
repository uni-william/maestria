package br.com.sis.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.sis.entity.Contrato;
import br.com.sis.repository.ContratoRepository;

@FacesConverter(forClass = Contrato.class)
public class ContratoConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ContratoRepository repository;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Contrato contrato = null;
		if (StringUtils.isNotBlank(value)) {
			Long id = new Long(value);
			contrato = repository.porId(id);
		}
		return contrato;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Contrato contrato = (Contrato) value;
			return contrato.getId() == null ? null : contrato.getId().toString();
		}
		return "";
	}

}
