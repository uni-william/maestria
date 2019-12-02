package br.com.sis.service;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.sis.entity.Colaborador;
import br.com.sis.entity.Usuario;
import br.com.sis.repository.ColaboradorRepository;
import br.com.sis.repository.UsuarioRepository;
import br.com.sis.util.jpa.Transactional;
import br.com.sis.util.jsf.FacesUtil;

public class UsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioRepository usuarioRepository;
	@Inject
	private ColaboradorRepository colaboradorRepository;

	@Transactional
	public Usuario salvar(Usuario usuario) {
		Colaborador colExistente = colaboradorRepository.porCPF(usuario.getColaborador().getCpf());

		if (colExistente != null && !colExistente.equals(usuario.getColaborador())) {
			FacesUtil.addErroMessage("Já existe um colaborador cadastrado com esse CPF.");
			return null;
		} else {
			Usuario usuarioExistente = usuarioRepository.porLogin(usuario.getLogin());
			if (usuarioExistente != null && !usuarioExistente.equals(usuario)) {
				FacesUtil.addErroMessage("Já existe um colaborador cadastrado com esse Login.");
				return null;
			} else {
				usuario.setColaborador(colaboradorRepository.salvar(usuario.getColaborador()));
				if (usuario.getId() == null) {
					String senhaCripto = new BCryptPasswordEncoder().encode(usuario.getColaborador().getCpf());
					usuario.setPassword(senhaCripto);
				}
				usuario = usuarioRepository.salvar(usuario);
			}
		}
		return usuario;

	}

	@Transactional
	public boolean removerUsuario(Usuario usuario) {
		return usuarioRepository.remover(usuario);
	}

}
