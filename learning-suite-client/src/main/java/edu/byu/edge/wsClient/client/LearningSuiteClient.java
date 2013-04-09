package edu.byu.edge.wsClient.client;

import edu.byu.edge.client.domain.StudentRatingOutcome;

import java.util.List;

public interface LearningSuiteClient {

	List<StudentRatingOutcome> getStudentRatingOutcomeByCourseAndInstructorNetId(String curriculumId, String titleCode,
			String instructorNetId);

	List<StudentRatingOutcome> getStudentRatingOutcomeByCourseAndInstructorPersonId(String curriculumId, String titleCode,
			String instructorPersonId);

}
