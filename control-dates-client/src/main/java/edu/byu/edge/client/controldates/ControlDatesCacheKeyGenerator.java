package edu.byu.edge.client.controldates;

import edu.byu.common.domain.YearTerm;
import edu.byu.edge.client.controldates.domain.ControlDateType;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Scott Hutchings on 5/17/2017.
 * edge-clients
 */

public class ControlDatesCacheKeyGenerator implements KeyGenerator {
	@Override
	public Object generate(Object target, Method method, Object... params) {
		List<String> result = new ArrayList<String>(params.length);
		for (Object param : params) {
			result.add(convertParam(param));
		}
		Collections.sort(result);
		StringBuilder builder = new StringBuilder();
		for (String s : result) {
			builder.append(s);
			builder.append(',');
		}
		return builder.toString();
	}

	private String convertParam(Object param){
		if (param == null) {
			return "null";
		} else if (Date.class.isAssignableFrom(param.getClass())){
			return convertDate((Date) param);
		} else if (YearTerm.class.isAssignableFrom(param.getClass())){
			return convertYearTerm((YearTerm) param);
		} else if (ControlDateType.class.isAssignableFrom(param.getClass())){
			return convertControlDateType((ControlDateType) param);
		} else {
			return param.toString();
		}
	}

	private String convertDate(Date date){
		if (date == null) {
			return "null";
		}
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		final int year = cal.get(Calendar.YEAR);
		final int month = cal.get(Calendar.MONTH) + 1;
		final int day = cal.get(Calendar.DAY_OF_MONTH);
		return year + "-" + (month < 10 ? "0" + month: month) + "-" + (day < 10 ? "0" + day: day);
	}

	private String convertYearTerm(YearTerm yearTerm){
		return yearTerm == null ? "null" : yearTerm.getYearTerm();
	}

	private String convertControlDateType(ControlDateType controlDateType){
		return controlDateType == null ? "null" : controlDateType.getControlDateType();
	}
}
