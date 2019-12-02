package br.com.sis.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Utils implements Serializable {

	private static final long serialVersionUID = 1L;

	public static String cripto(String senha) {
		// Criptografa a String passada por par�metro
		int contador, tamanho, codigoASCII;
		String senhaCriptografada = "";
		tamanho = senha.length();
		// senha = senha.toUpperCase();
		contador = 0;
		while (contador < tamanho) {
			codigoASCII = senha.charAt(contador) + 130;
			senhaCriptografada = senhaCriptografada + (char) codigoASCII;
			contador++;
		}
		return senhaCriptografada;
	}

	public static String decripto(String senha) {
		// Descriptografa a String passada por par�metro
		int contador, tamanho, codigoASCII;
		String senhaCriptografada = "";
		tamanho = senha.length();
		// senha = senha.toUpperCase();
		contador = 0;
		while (contador < tamanho) {
			codigoASCII = senha.charAt(contador) - 130;
			senhaCriptografada = senhaCriptografada + (char) codigoASCII;
			contador++;
		}
		return senhaCriptografada;
	}

	public static String geraSenha() {
		String letras = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvYyWwXxZz1234567890";
		Random random = new Random();
		String armazenaChaves = "";
		int index = -1;
		for (int i = 0; i < 6; i++) {
			index = random.nextInt(letras.length());
			armazenaChaves += letras.substring(index, index + 1);
		}
		return armazenaChaves;

	}

	public static String anoMes(Date data) {
		Integer iAno;
		Integer iMes;
		String sAnoMes;
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		iAno = c.get(Calendar.YEAR);
		iMes = c.get(Calendar.MONTH) + 1;
		sAnoMes = iAno.toString();
		sAnoMes = sAnoMes + String.format("%02d", iMes);
		return sAnoMes;
	}

	public static String dataFormatada(Date data) {
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM");
		return sd.format(data);
	}

	public static String dataCompletaFormatada(Date data) {
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
		return sd.format(data);
	}

	public static String dataPorExtenso(Date data) {
		Locale local = new Locale("pt", "BR");
		DateFormat formato = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", local);
		return formato.format(data);
	}

	public static String removerCaracter(String origem, String caracter) {
		return origem.replace(caracter, "");
	}

	public static String retornaPrimeiroEUltimoNome(String nomeCompleto) {
		String[] split = nomeCompleto.split(" ");
		String retorno = "";
		if (split.length > 0) {
			retorno = split[0] + "." + split[split.length - 1];
			retorno = retorno.toLowerCase();
		}
		return retorno;
	}
	
	public static String idadeExtenso(int meses) {
		int anos = meses/12;
		int mes = meses%12;
		String sAno = "";
		String sMeses = "";
		if (anos == 1) {
			sAno = anos + " ano ";
		} else if (anos > 1) {
			sAno = anos + " anos ";
		}
		
		if (mes == 1) {
			sMeses = mes + " mês";
		} else if (mes > 1) {
			sMeses = mes + " meses";
		}		
		
		String idade = "";
		if (sAno != "" ) {
			idade = idade + sAno;
		}
		if (sMeses != "") {
			if (sAno != "") {
				idade = idade + " e " + sMeses;
			} else {
				idade = idade + sMeses;
			}
		}
		return idade;
	}

}
