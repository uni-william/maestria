package br.com.sis.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.sis.entity.Empresa;
import br.com.sis.repository.EmpresaRepository;

@FacesConverter(forClass = Empresa.class)
public class EmpresaConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EmpresaRepository repository;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Empresa empresa = null;
		if (StringUtils.isNotBlank(value)) {
			Long id = new Long(value);
			empresa = repository.porId(id);
		}
		return empresa;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Empresa empresa = (Empresa) value;
			return empresa.getId() == null ? null : empresa.getId().toString();
		}
		return "";
	}

}
