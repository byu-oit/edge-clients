package edu.byu.edge.client.controldates;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import edu.byu.common.domain.YearTerm;
import edu.byu.edge.client.controldates.domain.ControlDateType;
import edu.byu.edge.client.controldates.domain.ControldateswsServiceType;
import edu.byu.edge.client.controldates.domain.DateRowType;
import edu.byu.edge.client.controldates.domain.ErrorsType;
import edu.byu.edge.client.controldates.domain.ResponseType;

public class ControlDatesClientImplTestCase {

	private static ControlDatesClient controlDatesClient = new ControlDatesClientImpl();

	@Test
	public void getAllTestgetAllInvalidInput() {
		try {
			controlDatesClient.getAll(null);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@Test
	public void getAllTest() throws ParseException {
		ControldateswsServiceType cdws = controlDatesClient.getAll(ControlDateType.CURRICULUM);
		ensureNoErrors(cdws);
		ResponseType response = cdws.getResponse();
		assertTrue("Control Dates' getAll returned a smaller than expected number of results", response.getRequestCount().compareTo(BigInteger.valueOf(75)) > 0);

		DateRowType firstControlDate = response.getDateList()
												.getDateRow()
												.get(0);
		assertTrue(firstControlDate.getDateType()
									.equals(ControlDateType.CURRICULUM.toString()));

		assertTrue(new YearTerm(firstControlDate.getYearTerm()).equals(new YearTerm("19001")));

		// The last date is way in the future, so use the second to last
		DateRowType secondToLastControlDate = response.getDateList()
												.getDateRow()
												.get(response.getRequestCount()
																.intValue() - 2);
		assertTrue(secondToLastControlDate.getDateType()
											.equals(ControlDateType.CURRICULUM.toString()));

		assertTrue(ControldateswsServiceType.dateFormatter.parse(secondToLastControlDate.getStartDate())
															.compareTo(new Date()) >= 0);
	}

	@Test
	public void getRangeTestInvalidInput() {
		YearTerm startYearTerm = new YearTerm("20121");
		YearTerm endYearTerm = new YearTerm("20125");

		try {
			controlDatesClient.getRange(null, endYearTerm, ControlDateType.CURRICULUM);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			controlDatesClient.getRange(startYearTerm, null, ControlDateType.CURRICULUM);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			controlDatesClient.getRange(startYearTerm, endYearTerm, null);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}
	

	@Test
	public void getRangeTest() {
		ControldateswsServiceType cdws = controlDatesClient.getRange(new YearTerm("20121"), new YearTerm("20125"), ControlDateType.CURRENT_YYT);
		ensureNoErrors(cdws);
		ResponseType response = cdws.getResponse();
		assertTrue("Control Dates' getRange returned a smaller than expected number of results", response.getRequestCount()
																											.equals(BigInteger.valueOf(4)));

		DateRowType first = response.getDateList().getDateRow().get(0);
		DateRowType fourth = response.getDateList()
										.getDateRow()
										.get(3);
		
		assertTrue(first.getDateType()
						.equals(ControlDateType.CURRENT_YYT.toString()));
		
		assertTrue(fourth.getYearTerm()
							.equals("20125"));
	}

	@Test
	public void getByYearTermTestInvalidInput() {
		YearTerm someYearTerm = new YearTerm("20121");

		try {
			controlDatesClient.getByYearTerm(null, ControlDateType.CURRICULUM);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			controlDatesClient.getByYearTerm(someYearTerm, null);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			controlDatesClient.getByYearTerm(someYearTerm, null, null, null);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}

		// Attempt 11 types
		try {
			controlDatesClient.getByYearTerm(someYearTerm, ControlDateType.CURRICULUM, ControlDateType.CURRICULUM, ControlDateType.CURRICULUM,
					ControlDateType.CURRICULUM, ControlDateType.CURRICULUM, ControlDateType.CURRICULUM, ControlDateType.CURRICULUM, ControlDateType.CURRICULUM,
					ControlDateType.CURRICULUM, ControlDateType.CURRICULUM, ControlDateType.CURRICULUM);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@Test
	public void getByYearTermTest() {
		YearTerm someYearTerm = new YearTerm("20125");
		ControldateswsServiceType byYearTerm = controlDatesClient.getByYearTerm(someYearTerm, ControlDateType.CURRICULUM, ControlDateType.CURRENT_YYT,
				ControlDateType.DEGREE);
		ResponseType response = byYearTerm.getResponse();
		assertTrue("Control Dates' getByYearTerm returned a smaller than expected number of results", response.getRequestCount()
																										.equals(BigInteger.valueOf(3)));

		// Test getting a date type that doesn't exist. In that case the service
		// returns a result but all the values are empty
		byYearTerm = controlDatesClient.getByYearTerm(someYearTerm, ControlDateType.ID_CARD_EXP_BLACKOUT);
		response = byYearTerm.getResponse();
		assertTrue("Control Dates' getByYearTerm expected a single record returned (even though values should be null).",
				response.getRequestCount()
																					.equals(BigInteger.valueOf(1)));
	}

	@Test
	public void getByDateTestInvalidInput() {
		Date today = new Date();

		try {
			controlDatesClient.getByDate(null, ControlDateType.CURRICULUM);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			controlDatesClient.getByDate(today, null);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			controlDatesClient.getByDate(today, null, null, null);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}

		// Attempt 11 types
		try {
			controlDatesClient.getByDate(today, ControlDateType.CURRICULUM, ControlDateType.CURRICULUM, ControlDateType.CURRICULUM, ControlDateType.CURRICULUM,
					ControlDateType.CURRICULUM, ControlDateType.CURRICULUM, ControlDateType.CURRICULUM, ControlDateType.CURRICULUM, ControlDateType.CURRICULUM,
					ControlDateType.CURRICULUM, ControlDateType.CURRICULUM);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@Test
	public void getByDateTest() {
		Date today = new Date();
		ControldateswsServiceType ByDate = controlDatesClient.getByDate(today, ControlDateType.CURRICULUM, ControlDateType.CURRENT_YYT, ControlDateType.DEGREE);
		ResponseType response = ByDate.getResponse();
		assertTrue("Control Dates' getByDate returned a smaller than expected number of results", response.getRequestCount()
																											.equals(BigInteger.valueOf(3)));

		// Test getting a date type that doesn't exist. This call behaves like
		// you would expect (unlike getByYearTerm)
		ByDate = controlDatesClient.getByDate(today, ControlDateType.ID_CARD_EXP_BLACKOUT);
		response = ByDate.getResponse();
		assertTrue("Control Dates' getByDate expected no results.", response.getRequestCount()
																			.equals(BigInteger.valueOf(0)));
	}

	protected void ensureNoErrors(ControldateswsServiceType cdws) {
		ErrorsType errors = cdws.getErrors();
		if (errors != null) {
			fail("Unexpected error: " + errors.getError());
		}
	}

}
