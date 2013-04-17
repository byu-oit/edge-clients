package edu.byu.edge.wsClient.impl;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

import edu.byu.edge.wsClient.LearningSuiteClient;
import edu.byu.edge.wsClient.domain.StudentRatingOutcome;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import javax.ws.rs.core.UriBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class LearningSuiteClientImpl extends BaseClient implements LearningSuiteClient {

	private static final Logger LOG = Logger.getLogger(LearningSuiteClientImpl.class);

	protected URI baseUrl;

	protected int readTimeout;

	public LearningSuiteClientImpl() throws URISyntaxException {
		super();
	}
	public LearningSuiteClientImpl(final URI baseUrl, final int readTimeout) {
		super(baseUrl, readTimeout);
		this.baseUrl = baseUrl;
		this.readTimeout = readTimeout;
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

	/**
	 * See {@link LearningSuiteClientImpl#baseUrl}
	 * 
	 * @return the baseUrl
	 */
	public URI getBaseUrl() {
		return baseUrl;
	}

	/**
	 * See {@link LearningSuiteClientImpl#baseUrl}
	 * 
	 * @param baseUrl
	 *            the baseUrl to set
	 */
	public void setBaseUrl(URI baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * See {@link LearningSuiteClientImpl#readTimeout}
	 * 
	 * @return the readTimeout
	 */
	public int getReadTimeout() {
		return readTimeout;
	}

	/**
	 * See {@link LearningSuiteClientImpl#readTimeout}
	 * 
	 * @param readTimeout
	 *            the readTimeout to set
	 */
	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
}
