package br.ages.crud.util;

import java.text.DecimalFormat;

public class TimeConverter {
	public static String ConvertMinuteToHours (int total) {
		String minutos = String.valueOf(total%60);
		String horas = String.valueOf(total/60);
		String horarioFormatado;
		
		if (minutos.length() == 1)
			minutos = "0"+minutos;
		
		horarioFormatado = horas+ ":"+ minutos;
		return horarioFormatado;
	}
	
	public static String ConvertMinuteToHours (float total) {
		DecimalFormat df = new DecimalFormat("0.00");
		float horas = total/60;
		return df.format(horas);
	}
	
	public static String ConvertPorcentagemToString (float total) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(total);
	}
}



