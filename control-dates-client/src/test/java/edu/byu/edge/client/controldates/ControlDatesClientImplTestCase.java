package edu.byu.edge.client.controldates;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Strings;

import edu.byu.common.domain.YearTerm;
import edu.byu.edge.client.controldates.domain.ControlDateType;
import edu.byu.edge.client.controldates.domain.ControldateswsServiceType;
import edu.byu.edge.client.controldates.domain.DateRowType;

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
		List<DateRowType> results = controlDatesClient.getAll(ControlDateType.CURRICULUM);

		assertTrue("Control Dates' getAll returned a smaller than expected number of results", results.size() > 75);

		DateRowType firstControlDate = results.get(0);
		assertTrue(firstControlDate.getDateType()
									.equals(ControlDateType.CURRICULUM.toString()));

		assertTrue(new YearTerm(firstControlDate.getYearTerm()).equals(new YearTerm("19001")));

		// The last date is way in the future, so use the second to last
		DateRowType secondToLastControlDate = results.get(results.size() - 2);
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
		List<DateRowType> results = controlDatesClient.getRange(new YearTerm("20121"), new YearTerm("20125"), ControlDateType.CURRENT_YYT);
		assertTrue("Control Dates' getRange returned a smaller than expected number of results", results.size() == 4);

		DateRowType first = results.get(0);
		DateRowType fourth = results.get(3);
		
		assertTrue(first.getDateType().equals(ControlDateType.CURRENT_YYT.toString()));
		assertTrue(fourth.getYearTerm().equals("20125"));
	}

	@Test
	public void getByYearTermAndTypesTestInvalidInput() {
		YearTerm someYearTerm = new YearTerm("20121");

		try {
			controlDatesClient.getByYearTermAndTypes(null, ControlDateType.CURRICULUM);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			controlDatesClient.getByYearTermAndTypes(someYearTerm, null);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			controlDatesClient.getByYearTermAndTypes(someYearTerm, null, null, null);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}

		// Attempt 11 types
		try {
			controlDatesClient.getByYearTermAndTypes(someYearTerm, ControlDateType.CURRICULUM, ControlDateType.CURRICULUM, ControlDateType.CURRICULUM,
					ControlDateType.CURRICULUM, ControlDateType.CURRICULUM, ControlDateType.CURRICULUM, ControlDateType.CURRICULUM, ControlDateType.CURRICULUM,
					ControlDateType.CURRICULUM, ControlDateType.CURRICULUM, ControlDateType.CURRICULUM);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@Test
	public void getByYearTermAndTypesTest() {
		YearTerm someYearTerm = new YearTerm("20125");
		List<DateRowType> results = controlDatesClient.getByYearTermAndTypes(someYearTerm, ControlDateType.CURRICULUM, ControlDateType.CURRENT_YYT, ControlDateType.DEGREE);
		assertTrue("Control Dates' getByYearTermAndTypes returned a smaller than expected number of results", results.size() == 3);

		// Test getting a date type that doesn't exist. In that case the service
		// returns a result but all the values are empty
		results = controlDatesClient.getByYearTermAndTypes(someYearTerm, ControlDateType.ID_CARD_EXP_BLACKOUT);
		assertTrue("Control Dates' getByYearTermAndTypes expected a single record returned (even though values should be null).", results.size() == 1);
	}

	@Test
	public void getByYearTermAndTypeTestInvalidInput() {
		YearTerm someYearTerm = new YearTerm("20121");

		try {
			controlDatesClient.getByYearTermAndType(null, ControlDateType.CURRICULUM);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			controlDatesClient.getByYearTermAndType(someYearTerm, null);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@Test
	public void getByYearTermAndTypeTest() {
		YearTerm someYearTerm = new YearTerm("20125");
		DateRowType result = controlDatesClient.getByYearTermAndType(someYearTerm, ControlDateType.CURRICULUM);
		assertTrue(result != null);

		// Test getting a date type that doesn't exist. In that case the service
		// returns a result but all the values are empty
		result = controlDatesClient.getByYearTermAndType(someYearTerm, ControlDateType.ID_CARD_EXP_BLACKOUT);
		assertTrue(result != null && Strings.isNullOrEmpty(result.getDateType()));
	}

	@Test
	public void getByDateAndTypesTestInvalidInput() {
		Date today = new Date();

		try {
			controlDatesClient.getByDateAndTypes(null, ControlDateType.CURRICULUM);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			controlDatesClient.getByDateAndTypes(today, null);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			controlDatesClient.getByDateAndTypes(today, null, null, null);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}

		// Attempt 11 types
		try {
			controlDatesClient.getByDateAndTypes(today, ControlDateType.CURRICULUM, ControlDateType.CURRICULUM, ControlDateType.CURRICULUM,
					ControlDateType.CURRICULUM,
					ControlDateType.CURRICULUM, ControlDateType.CURRICULUM, ControlDateType.CURRICULUM, ControlDateType.CURRICULUM, ControlDateType.CURRICULUM,
					ControlDateType.CURRICULUM, ControlDateType.CURRICULUM);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@Test
	public void getByDateAndTypesTest() {
		Date today = new Date();
		List<DateRowType> results = controlDatesClient.getByDateAndTypes(today, ControlDateType.CURRICULUM, ControlDateType.CURRENT_YYT, ControlDateType.DEGREE);
		assertTrue("Control Dates' getByDateAndTypes returned a smaller than expected number of results", results.size() == 3);

		// Test getting a date type that doesn't exist. This call behaves like
		// you would expect (unlike getByYearTermAndTypes)
		results = controlDatesClient.getByDateAndTypes(today, ControlDateType.ID_CARD_EXP_BLACKOUT);
		assertTrue("Control Dates' getByDateAndTypes expected no results.", results.size() == 0);
	}

	@Test
	public void getByDateAndTypeTestInvalidInput() {
		Date today = new Date();

		try {
			controlDatesClient.getByDateAndType(null, ControlDateType.CURRICULUM);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}

		try {
			controlDatesClient.getByDateAndType(today, null);
			fail("Expected Exception was not thrown");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@Test
	public void getByDateAndTypeTest() {
		Date today = new Date();
		DateRowType result = controlDatesClient.getByDateAndType(today, ControlDateType.CURRICULUM);
		assertTrue(result != null);

		// Test getting a date type that doesn't exist. This call behaves like
		// you would expect (unlike getByYearTermAndTypes)
		result = controlDatesClient.getByDateAndType(today, ControlDateType.ID_CARD_EXP_BLACKOUT);
		assertTrue(result == null);
	}
}
