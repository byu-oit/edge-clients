package edu.byu.edge.academic.client;

/**
 * Created by eric on 2/4/16.
 */
public interface CreditHourClient {
	/**
	 * Returns the number of credit hours a student is taking during a semester
	 * @param personId ID of the student
	 * @param yearTerm Year term in question
	 * @return Decimal amount of credit hours being taken. If no credit hours are found, 0 will be returned.
	 */
	public double getCreditHoursByPersonIdAndYearTerm(String personId, String yearTerm);
}
