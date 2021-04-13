package br.edward.exceptions.matchers;

import java.util.Calendar;

public class MatchersProprios {
	public static DiaSemanaMatcher caiEm(Integer diaSemana) {
		return new DiaSemanaMatcher(diaSemana);
	}
	
	public static DiaSemanaMatcher caiNumaSegunda() {
		return new DiaSemanaMatcher(Calendar.MONDAY);
	}
	
	public static DataDiferencaDiaMatcher ehHoje() {
		return new DataDiferencaDiaMatcher(0);
	}
	
	public static DataDiferencaDiaMatcher ehHojeComDiferencaDias(Integer qtdeDias) {
		return new DataDiferencaDiaMatcher(qtdeDias);
	}
}
