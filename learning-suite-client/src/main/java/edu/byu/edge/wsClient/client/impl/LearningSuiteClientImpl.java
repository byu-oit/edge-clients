package edu.byu.edge.wsClient.client.impl;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

import edu.byu.edge.client.domain.StudentRatingOutcome;
import edu.byu.edge.client.impl.BaseClient;
import edu.byu.edge.wsClient.client.LearningSuiteClient;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import javax.ws.rs.core.UriBuilder;

import java.net.URI;
import java.util.List;

public class LearningSuiteClientImpl extends BaseClient implements LearningSuiteClient {

	private static final Logger LOG = Logger.getLogger(LearningSuiteClientImpl.class);

	protected final URI baseUrl;

	public LearningSuiteClientImpl(final URI baseUrl, final int readTimeout) {
		super(baseUrl, readTimeout);
		this.baseUrl = baseUrl;
	}

	@Override
	public List<StudentRatingOutcome> getStudentRatingOutcomeByCourseAndInstructorNetId(String curriculumId, String titleCode,
			String instructorNetId) {
		return getStudentRatingOutcomeByCourseAndInstructor(curriculumId, titleCode, instructorNetId, true);
	}

	@Override
	public List<StudentRatingOutcome> getStudentRatingOutcomeByCourseAndInstructorPersonId(String curriculumId, String titleCode,
			String instructorPersonId) {
		return getStudentRatingOutcomeByCourseAndInstructor(curriculumId, titleCode, instructorPersonId, true);
	}

	private List<StudentRatingOutcome> getStudentRatingOutcomeByCourseAndInstructor(String curriculumId, String titleCode,
			String instructorId, boolean isNetId) {
		Assert.notNull(curriculumId, "CurriculumId is required.");
		Assert.notNull(titleCode, "titleCode is required.");

		String urlTemplate = "student-ratings-outcome/curriculumID/{curriculumId}/titleCode/{titleCode}/instructorPersonID/{instructorId}";
		String missingInstructorIdText = "Instructor's personId is required.";
		if (isNetId) {
			urlTemplate.replace("instructorPersonId", "instructorNetId");
			missingInstructorIdText = "Instructor's netId is required.";
		}
		Assert.notNull(instructorId, missingInstructorIdText);

		GenericType<List<StudentRatingOutcome>> genericType = new GenericType<List<StudentRatingOutcome>>() {
		};

		URI uri = UriBuilder.fromPath(urlTemplate).build(curriculumId, titleCode, instructorId);

		
		WebResource resource = getClient().resource(baseUrl).uri(uri);
		return resource.get(genericType);
	}
}
