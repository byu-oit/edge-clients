package edu.byu.edge.client.controldates;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import edu.byu.common.domain.YearTerm;
import edu.byu.edge.client.controldates.domain.ControlDateType;
import edu.byu.edge.client.controldates.domain.DateRowType;

/**
 * Client interface to the control dates web service.
 * 
 * Note: The service's xsd provides for an errors field as part of the service
 * call response. Experience has shown this is not used and we are assuming as
 * much for this service. Any successful result will be returned as the service
 * provides it without regard for the errors field.
 * 
 */
public interface ControlDatesClient {

	String WINTER_TERM_SUFFIX = "1";
	String SPRING_TERM_SUFFIX = "3";
	String SUMMER_TERM_SUFFIX = "4";
	String FALL_TERM_SUFFIX = "5";

	/**
	 * @param type
	 * @return All possible control dates of the given type.
	 */
	List<DateRowType> getAll(ControlDateType type);

	/**
	 * @param startYearTerm
	 * @param endYearTerm
	 * @param controlDateType
	 * @return A list of control dates of the given type for the given range.
	 */
	List<DateRowType> getRange(YearTerm startYearTerm, YearTerm endYearTerm, ControlDateType controlDateType);

	/**
	 * @param yearTerm
	 * @param controlDateTypes
	 * @return A list of control dates with the same year term (that of the
	 *         given one) and types corresponding to those given. If no control
	 *         date of a given type exists, that control date will be full of
	 *         empty fields. This behavior differs from getByDateAndTypes. If no
	 *         control dates are found and empty list will be returned.
	 */
	List<DateRowType> getByYearTermAndTypes(YearTerm yearTerm, ControlDateType... controlDateTypes);

	/**
	 * @param yearTerm
	 * @param controlDateType
	 * @return A single control date corresponding to the given year term and
	 *         type. See getByYearTermAndTypes for more details. If no control
	 *         date can be found null will be returned.
	 */
	DateRowType getByYearTermAndType(YearTerm yearTerm, ControlDateType controlDateType);

	/**
	 * @param asOfDate
	 * @param controlDateTypes
	 * @return A list of control dates with the year term of the given date and
	 *         types corresponding to those given. If no control date of a given
	 *         type exists, that control date will not be included in the
	 *         resulting list. This behavior differs from getByYearTermAndTypes.
	 *         If no control dates are found and empty list will be returned.
	 */
	List<DateRowType> getByDateAndTypes(Date asOfDate, ControlDateType... controlDateTypes);

	/**
	 * @param asOfDate
	 * @param controlDateType
	 * @return A single control date corresponding to the given type and year
	 *         term determined by the given date. See getByDateAndTypes for more
	 *         details. If no control date can be found null will be returned.
	 */
	DateRowType getByDateAndType(Date asOfDate, ControlDateType controlDateType);

	/**
	 * A Utility method to convert the String dates the service returns into a
	 * java Date object.
	 * 
	 * @param responseDateString
	 * @return response
	 * @throws ParseException
	 */
	Date parseResponseDateString(String responseDateString) throws ParseException;

	/**
	 * Calculates which term would be paid for based on a specified date
	 *
	 * @param onDate Date for which the payment is being requested
	 * @param winterStartDate
	 * @param fallStartDate
	 * @return Resulting year term
	 */
	String getYearTermWithAdjustedFallWinterStartDates(Date onDate, Date winterStartDate, Date fallStartDate);
}
