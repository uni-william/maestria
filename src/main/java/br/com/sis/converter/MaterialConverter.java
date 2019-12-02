package br.com.sis.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.sis.entity.Material;
import br.com.sis.repository.MaterialRepository;

@FacesConverter(forClass = Material.class)
public class MaterialConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MaterialRepository repository;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Material material = null;
		if (StringUtils.isNotBlank(value)) {
			Long id = new Long(value);
			material = repository.porId(id);
		}
		return material;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Material material = (Material) value;
			return material.getId() == null ? null : material.getId().toString();
		}
		return "";
	}

}
