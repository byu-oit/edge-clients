package edu.byu.edge.wsClient;

import edu.byu.edge.wsClient.domain.StudentRatingOutcome;

import java.util.List;

public interface LearningSuiteClient {

	List<StudentRatingOutcome> getStudentRatingOutcomeByCourseAndInstructorNetId(String curriculumId, String titleCode,
			String instructorNetId);

	List<StudentRatingOutcome> getStudentRatingOutcomeByCourseAndInstructorPersonId(String curriculumId, String titleCode,
			String instructorPersonId);

}
