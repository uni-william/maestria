package br.com.sis.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.model.UploadedFile;

import br.com.sis.entity.Aluno;
import br.com.sis.entity.Material;
import br.com.sis.entity.MaterialAluno;
import br.com.sis.repository.MaterialAlunoRepository;
import br.com.sis.repository.MaterialRepository;
import br.com.sis.service.MaterialAlunoService;
import br.com.sis.util.jsf.FacesUtil;

@Named
@ViewScoped
public class MateriaisAlunoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MaterialAlunoRepository materialAlunoRepository;

	@Inject
	private MaterialRepository materialRepository;

	@Inject
	private MaterialAlunoService materialAlunoService;
	
	@Inject
	private HttpServletResponse response;
	
	@Inject
	private FacesContext facesContext;		
	

	private Aluno aluno;
	private MaterialAluno materialAluno;
	private List<MaterialAluno> lista = new ArrayList<MaterialAluno>();
	private List<Material> materiais = new ArrayList<Material>();
	private UploadedFile uploadedFile;

	

	public List<Material> getMateriais() {
		return materiais;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public MaterialAluno getMaterialAluno() {
		return materialAluno;
	}

	public void setMaterialAluno(MaterialAluno materialAluno) {
		this.materialAluno = materialAluno;
	}

	public List<MaterialAluno> getLista() {
		return lista;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public void inicializar() {
		if (this.aluno == null) {
			FacesUtil.redirecionarPagina("/Erro.xhtml");
		}
		this.materiais = materialRepository.listarTodos();
		this.lista = materialAlunoRepository.porAluno(this.aluno);
		this.novoMaterial();

	}

	public void novoMaterial() {
		this.materialAluno = new MaterialAluno();
		this.materialAluno.setAluno(this.aluno);
		this.materialAluno.setDataCadastro(new Date());
	}

	public void excluir() {
		String nomeArquivo = this.materialAluno.getNomeArquivo();
		this.materialAlunoRepository.remover(this.materialAluno);
		if (nomeArquivo != null) {
			this.excluirArquivo(nomeArquivo);
		}
		this.lista = materialAlunoRepository.porAluno(this.aluno);
		this.novoMaterial();
	}

	private String numeroFormatado(Long id) {
		return String.format("%03d", id);
	}

	private void excluirArquivo(String nomeArquivo) {
		if (nomeArquivo != null) {
			File file = new File(FacesUtil.localArquivos(), nomeArquivo);
			file.delete();
		}
	}

	public void salvarMaterialAluno() {
		if (this.uploadedFile != null && this.uploadedFile.getContents() != null && this.uploadedFile.getSize() > 0) {
			try {				
				String nomeArquivo = null;
				if (this.materialAluno.getId() != null) {
					nomeArquivo = numeroFormatado(this.materialAluno.getId());
				} else
					nomeArquivo = numeroFormatado(materialAlunoRepository.numeroMaxiArquivo(this.aluno) + 1);
				nomeArquivo = "arquivo_" + numeroFormatado(this.aluno.getId()) + "_" + nomeArquivo + ".png";
				File file = new File(FacesUtil.localArquivos(), nomeArquivo);
				OutputStream out = new FileOutputStream(file);
				out.write(uploadedFile.getContents());
				out.close();
				this.materialAluno.setNomeArquivo(nomeArquivo);
			} catch (IOException e) {
				FacesUtil.addWarnMessage(e.getMessage());
			}
		}
		materialAlunoService.salvar(this.materialAluno);
		this.lista = materialAlunoRepository.porAluno(this.aluno);
		this.novoMaterial();
	}

	public void limparFoto() {
		String nomeArquivo = this.materialAluno.getNomeArquivo();
		this.materialAluno.setNomeArquivo(null);
		this.salvarMaterialAluno();
		excluirArquivo(nomeArquivo);
	}
	public void download() {

		File arquivo = new File(FacesUtil.localArquivos(), this.materialAluno.getNomeArquivo());
		int tamanho = (int) arquivo.length();

		response.setContentType("image/png");
		response.setContentLength(tamanho);
		response.setHeader("Content-Disposition", "attachment; filename=arquivo	.png");

		OutputStream output;
		facesContext.responseComplete();
		try {
			output = response.getOutputStream();
			Files.copy(arquivo.toPath(), output); // escreve bytes no fluxo de saída
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
