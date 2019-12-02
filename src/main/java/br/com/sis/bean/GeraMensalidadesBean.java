package br.com.sis.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sis.entity.Contrato;
import br.com.sis.entity.Mensalidade;
import br.com.sis.repository.MensalidadeRepository;
import br.com.sis.service.MensalidadeService;
import br.com.sis.util.jsf.FacesUtil;

@Named
@ViewScoped
public class GeraMensalidadesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MensalidadeRepository mensalidadeRepository;

	@Inject
	private MensalidadeService mensalidadeService;

	private Contrato contrato;

	private Integer parcelaInicial;
	private Integer parcelaFinal;
	private Integer ultimaParcelaGerada;

	private Date dataInicial;

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Integer getParcelaInicial() {
		return parcelaInicial;
	}

	public void setParcelaInicial(Integer parcelaInicial) {
		this.parcelaInicial = parcelaInicial;
	}

	public Integer getParcelaFinal() {
		return parcelaFinal;
	}

	public void setParcelaFinal(Integer parcelaFinal) {
		this.parcelaFinal = parcelaFinal;
	}

	public Integer getUltimaParcelaGerada() {
		return ultimaParcelaGerada;
	}

	public void setUltimaParcelaGerada(Integer ultimaParcelaGerada) {
		this.ultimaParcelaGerada = ultimaParcelaGerada;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public void inicializar() {
		this.ultimaParcelaGerada = this.mensalidadeRepository.maximaParcelaGerada(contrato);
		if (this.ultimaParcelaGerada == 0) {
			this.parcelaInicial = 1;
			this.parcelaFinal = 12;
			this.dataInicial = Calendar.getInstance().getTime();
		} else {
			this.parcelaInicial = this.ultimaParcelaGerada + 1;
			this.parcelaFinal = this.parcelaInicial + 5;
			Calendar cal = Calendar.getInstance();
			cal.setTime(this.getUltimaDataGerada());
			cal.add(Calendar.MONTH, 1);
			this.dataInicial = cal.getTime();
		}
	}

	public Date getUltimaDataGerada() {
		Mensalidade mens = this.mensalidadeRepository.porContratoEParcela(this.contrato, this.ultimaParcelaGerada);
		if (mens != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(mens.getVencimento());
			return cal.getTime();
		}
		return null;

	}

	public void gerarParcelas() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.dataInicial);
		Mensalidade mensalidade;
		boolean fez = true;
		if (this.ultimaParcelaGerada == 0) {
			// Matricula
			mensalidade = new Mensalidade();
			mensalidade.setContrato(this.contrato);
			mensalidade.setNumParcela(0);
			mensalidade.setVencimento(getDataInicial());
			mensalidade.setPagamento(getDataInicial());
			mensalidade.setValorParcela(this.contrato.getMatricula());
			mensalidade.setValorPago(this.contrato.getMatricula());
			if (mensalidadeService.salvar(mensalidade) == null) {
				fez = false;
			}
			// Primeira mensalidade
			mensalidade = new Mensalidade();
			mensalidade.setContrato(this.contrato);
			mensalidade.setNumParcela(1);
			mensalidade.setVencimento(getDataInicial());
			mensalidade.setPagamento(getDataInicial());
			mensalidade.setValorParcela(this.contrato.getValor());
			mensalidade.setValorPago(this.contrato.getValor());
			if (mensalidadeService.salvar(mensalidade) == null) {
				fez = false;
			}

		}

		if (this.ultimaParcelaGerada == 0) {
			for (int i = this.parcelaInicial + 1; i <= this.parcelaFinal; i++) {
				Calendar cal2 = Calendar.getInstance();
				cal.add(Calendar.MONTH, 1);
				cal2.setTime(cal.getTime());
				mensalidade = new Mensalidade();
				mensalidade.setContrato(this.contrato);
				mensalidade.setNumParcela(i);
				mensalidade.setVencimento(cal2.getTime());
				mensalidade.setValorParcela(this.contrato.getValor());
				if (mensalidadeService.salvar(mensalidade) == null) {
					fez = false;
				}
			}
		} else {
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.setTime(cal.getTime());
			mensalidade = new Mensalidade();
			mensalidade.setContrato(this.contrato);
			mensalidade.setNumParcela(this.parcelaInicial);
			mensalidade.setVencimento(cal.getTime());
			mensalidade.setValorParcela(this.contrato.getValor());
			if (mensalidadeService.salvar(mensalidade) == null) {
				fez = false;
			}

			for (int i = this.parcelaInicial + 1; i <= this.parcelaFinal; i++) {
				Calendar cal2 = Calendar.getInstance();
				cal.add(Calendar.MONTH, 1);
				cal2.setTime(cal.getTime());
				mensalidade = new Mensalidade();
				mensalidade.setContrato(this.contrato);
				mensalidade.setNumParcela(i);
				mensalidade.setVencimento(cal2.getTime());
				mensalidade.setValorParcela(this.contrato.getValor());
				if (mensalidadeService.salvar(mensalidade) == null) {
					fez = false;
				}
			}
		}
		if (fez == true) {
			FacesUtil.redirecionarPagina(
					"/pacotes/Matricula.xhtml?contrato=" + this.contrato.getId().toString() + "&geradas=true");
		}

	}

}
