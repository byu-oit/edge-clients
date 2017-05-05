package edu.byu.chartblock.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.sun.jersey.api.client.filter.ClientFilter;
import edu.byu.chartblock.AccountCodeValidation;
import edu.byu.chartblock.ChartBlock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 7/2/2014
 * Time: 2:11 PM
 */
public class AccountCodeValidationImpl extends BaseClient implements AccountCodeValidation {

	private static final Logger LOG = LogManager.getLogger(AccountCodeValidationImpl.class);

	private static final String DEFAULT_URL = "https://api.byu.edu:443/domains/erp/fs/chartfieldvalidation/v1";
	private static final Pattern BLANK_EMAIL_OR_PHONE_PATTERN = Pattern.compile("(\"phone_number\"|\"email_address\")\\s*:\\s*\\{\\}");

	private final ObjectMapper mapper;

	public AccountCodeValidationImpl(ClientFilter ... filters){
		this(DEFAULT_URL, 1000, filters);
	}
	
	public AccountCodeValidationImpl(String baseUrl, int readTimeout, ClientFilter... filters) {
		this(baseUrl, readTimeout, new ObjectMapper(), filters);
		this.mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
	}

	public AccountCodeValidationImpl(String baseUrl, int readTimeout, ObjectMapper mapper, ClientFilter... filters) {
		super(baseUrl, readTimeout, filters);
		this.mapper = mapper;
	}

	@Override
	public ChartBlock getAccount(String accountCode) {
		final String[] account = accountCode.split("-");
		if (account.length != 3) {
			throw new IllegalStateException("Invalid account code");
		}
		if (account[0].length() != 8 && account[1].length() != 4 && account[2].length() != 5) {
			throw new IllegalStateException("Invalid account code format");
		}
		final String response = getResource().path("")
				.queryParam("operating_unit", account[0])
				.queryParam("account_field", account[1])
				.queryParam("class_field", account[2])
				.queryParam("journal_source", " ")
				.queryParam("effective_date", " ")
				.accept("application/json")
				.get(String.class);
		try {
			final String chartBlockStr = mapper.readTree(cleanEmptyObjects(response)).findPath("chartfieldvalidation").findPath("chartblock").toString();
			return mapper.readValue(chartBlockStr, ChartBlock.class);
		} catch (IOException e) {
			LOG.error(e);
			return null;
		}
	}

	private static String cleanEmptyObjects(String json){
		final Matcher matcher = BLANK_EMAIL_OR_PHONE_PATTERN.matcher(json);
		if (matcher.find()){
			return matcher.replaceAll("$1:\"\"");
		}
		return json;
	}
}
