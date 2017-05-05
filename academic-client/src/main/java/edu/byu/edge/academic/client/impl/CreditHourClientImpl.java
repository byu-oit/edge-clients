package edu.byu.edge.academic.client.impl;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import edu.byu.edge.academic.client.CreditHourClient;
import edu.byu.edge.academic.client.ServiceException;
import edu.byu.wso2.core.jwt.JwtHeaderHolder;
import edu.byu.wso2.core.jwt.OriginalJwtHolder;
import edu.byu.wso2.core.provider.TokenHeaderProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by eric on 2/4/16.
 */
public class CreditHourClientImpl implements CreditHourClient, InitializingBean {
	private static final Logger LOG = LogManager.getLogger(CreditHourClientImpl.class);
	public static final String DEFAULT_BASE_URL = "https://api.byu.edu:443/domains/legacy/academic/registration/studentschedule/v1/";

	private String baseUrl;
	private final TokenHeaderProvider tokenHeaderProvider;
	private OriginalJwtHolder originalJwtHolder;

	public CreditHourClientImpl(final TokenHeaderProvider tokenHeaderProvider) {
		this (DEFAULT_BASE_URL, tokenHeaderProvider);
	}

	public CreditHourClientImpl (final String baseUrl, final TokenHeaderProvider tokenHeaderProvider) {
		this.baseUrl = _cleanUrl(baseUrl);
		this.tokenHeaderProvider = tokenHeaderProvider;
	}

	public void setOriginalJwtHolder(OriginalJwtHolder originalJwtHolder) {
		this.originalJwtHolder = originalJwtHolder;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.hasText(baseUrl);
		Assert.notNull(tokenHeaderProvider);
	}

	@Override
	public double getCreditHoursByPersonIdAndYearTerm(String personId, String yearTerm) throws ServiceException {
		return getCreditHoursByPersonIdAndYearTerm(personId, yearTerm, null);
	}

	@Override
	public double getCreditHoursByPersonIdAndYearTerm(String personId, String yearTerm, String actingFor) throws ServiceException {
		try {
			final URL url = new URL(baseUrl + personId + "/" + yearTerm);
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Authorization", tokenHeaderProvider.getTokenHeaderValue());
			if (originalJwtHolder != null) {
				connection.setRequestProperty(JwtHeaderHolder.ORIGINAL_JWT_HEADER, originalJwtHolder.getOriginalJwt());
			}
			if (actingFor != null) {
				connection.setRequestProperty("acting-for", actingFor);
			}

			final String result = CharStreams.toString(new InputStreamReader(connection.getInputStream(), Charsets.UTF_8));
			final List<String> list = new LinkedList<String>();
			final Matcher matcher = CRED_HRS.matcher(result);
			while (matcher.find()) {
				list.add(matcher.group(1));
			}
			return getTotal(Lists.transform(list, STR_TO_DBL_FUNC));
		} catch (MalformedURLException e) {
			LOG.error("Error in identity client", e);
			throw new ServiceException("Error determining credit hours.", e);
		} catch (IOException e) {
			LOG.error("Error in identity client", e);
			throw new ServiceException("Error determining credit hours.", e);
		}
	}

	private static double getTotal(final Collection<Double> coll) {
		double total = 0.0;
		for (final Double d : coll) {
			total += d;
		}
		return total;
	}

	private static final Pattern CRED_HRS = Pattern.compile("\"credit_hours\"[^:]*:\\s*\"([0-9\\.]+)\"");

	private static final Function<String, Double> STR_TO_DBL_FUNC = new Function<String, Double>() {
		@Override
		public Double apply(final String input) {
			return Double.parseDouble(input);
		}
	};

	private static String _cleanUrl(final String base) {
		if (base.endsWith("/")) return base;
		else return base + '/';
	}

}
