package br.com.sis.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.sis.enuns.DiaSemana;

@FacesConverter(forClass = DiaSemana.class)
public class DiaSemanaConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null) {  
	        return DiaSemana.valueOf(value);  
	    }  

	    return null;  
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && value instanceof DiaSemana) {  
	        return ((DiaSemana) value).name();  
	    }  
	    return null;  
	}

}
