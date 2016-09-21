package br.ages.crud.util;

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
}



