package br.edward.exceptions.matchers;

import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.edward.utils.DataUtils;

public class DataDiferencaDiaMatcher extends TypeSafeMatcher<Date>{

	private Integer quantidade;

	public DataDiferencaDiaMatcher(Integer quantidade) {
		this.quantidade = quantidade;
		
	}
	
	public void describeTo(Description description) {
		
	}

	@Override
	protected boolean matchesSafely(Date item) {
		return DataUtils.isMesmaData(item, DataUtils.obterDataComDiferencaDias(quantidade));
	}

}
