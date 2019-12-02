package br.com.sis.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;

import br.com.sis.entity.Empresa;
import br.com.sis.entity.Mensalidade;
import br.com.sis.enuns.StatusMatricula;
import br.com.sis.repository.EmpresaRepository;
import br.com.sis.repository.MensalidadeRepository;
import br.com.sis.repository.filter.MensalidadeFilter;
import br.com.sis.service.MensalidadeService;
import br.com.sis.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ConsultarPagarParcelaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MensalidadeRepository mensalidadeRepository;

	@Inject
	private MensalidadeService mensalidadeService;

	@Inject
	private EmpresaRepository empresaRepository;

	private List<Mensalidade> mensalidades = new ArrayList<Mensalidade>();
	private MensalidadeFilter mensalidadeFilter;
	private Mensalidade mensalidade;
	private BigDecimal multa;
	private BigDecimal juros;
	private boolean pagandoParcela = false;
	private BigDecimal jurosMulta;

	public List<Mensalidade> getMensalidades() {
		return mensalidades;
	}

	public MensalidadeFilter getMensalidadeFilter() {
		return mensalidadeFilter;
	}

	public void setMensalidadeFilter(MensalidadeFilter mensalidadeFilter) {
		this.mensalidadeFilter = mensalidadeFilter;
	}

	public Mensalidade getMensalidade() {
		return mensalidade;
	}

	public void setMensalidade(Mensalidade mensalidade) {
		this.mensalidade = mensalidade;
	}

	public boolean isPagandoParcela() {
		return pagandoParcela;
	}

	public void setPagandoParcela(boolean pagandoParcela) {
		this.pagandoParcela = pagandoParcela;
	}

	public BigDecimal getJurosMulta() {
		return jurosMulta;
	}

	public void setJurosMulta(BigDecimal jurosMulta) {
		this.jurosMulta = jurosMulta;
	}

	public void inicializar() {
		List<Empresa> empresas = empresaRepository.listarTodos();
		for (Empresa emp : empresas) {
			multa = emp.getConfiguracao().getMulta().divide(BigDecimal.valueOf(100));
			juros = emp.getConfiguracao().getJurosDia().divide(BigDecimal.valueOf(100));
		}
		mensalidade = new Mensalidade();
		mensalidadeFilter = new MensalidadeFilter();
		mensalidadeFilter.setStatusMatricula(StatusMatricula.MATRICULADO);
		Calendar dt = Calendar.getInstance();
		dt.add(Calendar.DAY_OF_MONTH, 15);
		mensalidadeFilter.setDataVenctoFim(dt.getTime());
		this.pesquisar();
	}

	public void pesquisar() {
		mensalidades = mensalidadeRepository.parcelasEmAberto(mensalidadeFilter);
	}

	public void salvar() {
		this.mensalidade = mensalidadeService.salvar(this.mensalidade);
		this.pesquisar();
	}

	public void pagarParcela() {
		this.pagandoParcela = !this.pagandoParcela;
	}

	public void irParaPagamento() {
		this.mensalidade.setPagamento(new Date());
		this.pagarParcela();
		this.calcaularValorApagar();
	}

	public void calcaularValorApagar() {
		Date data = this.mensalidade.getPagamento();
		BigDecimal multaCalculada = BigDecimal.ZERO;
		BigDecimal jurosCalculado = BigDecimal.ZERO;
		jurosMulta = BigDecimal.ZERO;
		int dia = Days.daysBetween(new DateTime(mensalidade.getVencimento()), new DateTime(data)).getDays();
		int mes = Months.monthsBetween(new DateTime(mensalidade.getVencimento()), new DateTime(data)).getMonths();
		if (dia > 0) {
			mes = mes + 1;
		}
		if (mes > 0) {
			multaCalculada = mensalidade.getValorParcela().multiply(multa).multiply(BigDecimal.valueOf(mes));
		}
		if (dia > 0) {
			jurosCalculado = mensalidade.getValorParcela().multiply(juros).multiply(BigDecimal.valueOf(dia));
		}
		BigDecimal valor = mensalidade.getValorParcela();
		valor = valor.add(multaCalculada);
		valor = valor.add(jurosCalculado);
		
		jurosMulta = jurosMulta.add(multaCalculada).add(jurosCalculado);
		mensalidade.setValorPago(valor);

	}

	public void cancelar() {
		this.mensalidade = new Mensalidade();
		this.pagarParcela();
	}

	public void efetuarPagamento() {
		if (this.mensalidade.getValorPago() != null) {
			this.mensalidade = mensalidadeService.salvar(this.mensalidade);
			this.pagarParcela();
			this.pesquisar();
			FacesUtil.addInfoMessage("Mensalidade paga com sucesso!");
		} else {
			FacesUtil.addErroMessage("Informe o valor pago!");
		}

	}

}
