package edu.byu.edge.wsClient;

import static org.junit.Assert.assertEquals;
import edu.byu.edge.wsClient.LearningSuiteClient;
import edu.byu.edge.wsClient.domain.StudentRatingOutcome;
import edu.byu.edge.wsClient.impl.LearningSuiteClientImpl;

import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class LearningSuiteClientUnitTest {

	private static final int COURSE1_INSTRUCTOR_OUTCOMES_SIZE = 2;
	private static final String COURSE1_INSTRUCTOR_PERSON_ID = "955275532";
	private static final String COURSE1_INSTRUCTOR_NET_ID = "jaymcc";
	private static final String COURSE1_TITLE_CODE = "002";
	private static final String COURSE1_CURRICULUM_ID = "01489";
	private static URI BASE_URL;
	private static final int READ_TIMEOUT_MS = 10000;
	private final LearningSuiteClient learningSuiteClient = new LearningSuiteClientImpl(BASE_URL, READ_TIMEOUT_MS);

	@Before
	public void setup() throws URISyntaxException {
		BASE_URL = new URI("https://lsapi-dev.byu.edu/learningoutcomes");
	}

	@Test(expected = IllegalArgumentException.class)
	public void getGetStudentRatingOutcomeByCourseAndInstructorNetId_InvalidInput1() {
		learningSuiteClient.getStudentRatingOutcomeByCourseAndInstructorNetId(null, COURSE1_TITLE_CODE, COURSE1_INSTRUCTOR_NET_ID);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getGetStudentRatingOutcomeByCourseAndInstructorNetId_InvalidInput2() {
		learningSuiteClient.getStudentRatingOutcomeByCourseAndInstructorNetId(COURSE1_CURRICULUM_ID, null, COURSE1_INSTRUCTOR_NET_ID);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getGetStudentRatingOutcomeByCourseAndInstructorNetId_InvalidInput3() {
		learningSuiteClient.getStudentRatingOutcomeByCourseAndInstructorNetId(COURSE1_CURRICULUM_ID, COURSE1_TITLE_CODE,
 null);
	}

	@Test
	public void getGetStudentRatingOutcomeByCourseAndInstructorNetId() {
		List<StudentRatingOutcome> outcomes = learningSuiteClient.getStudentRatingOutcomeByCourseAndInstructorNetId(COURSE1_CURRICULUM_ID,
				COURSE1_TITLE_CODE, COURSE1_INSTRUCTOR_NET_ID);
		assertEquals(COURSE1_INSTRUCTOR_OUTCOMES_SIZE, outcomes.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void getGetStudentRatingOutcomeByCourseAndInstructorPersonId_InvalidInput1() {
		learningSuiteClient.getStudentRatingOutcomeByCourseAndInstructorPersonId(null, COURSE1_TITLE_CODE, COURSE1_INSTRUCTOR_PERSON_ID);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getGetStudentRatingOutcomeByCourseAndInstructorPersonId_InvalidInput2() {
		learningSuiteClient.getStudentRatingOutcomeByCourseAndInstructorPersonId(COURSE1_CURRICULUM_ID, null,
 COURSE1_INSTRUCTOR_PERSON_ID);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getGetStudentRatingOutcomeByCourseAndInstructorPersonId_InvalidInput3() {
		learningSuiteClient.getStudentRatingOutcomeByCourseAndInstructorPersonId(COURSE1_CURRICULUM_ID, COURSE1_TITLE_CODE, null);
	}

	@Test
	public void getGetStudentRatingOutcomeByCourseAndInstructorPersonId() {
		List<StudentRatingOutcome> outcomes = learningSuiteClient.getStudentRatingOutcomeByCourseAndInstructorPersonId(
				COURSE1_CURRICULUM_ID, COURSE1_TITLE_CODE, COURSE1_INSTRUCTOR_PERSON_ID);
		assertEquals(COURSE1_INSTRUCTOR_OUTCOMES_SIZE, outcomes.size());
	}
}
