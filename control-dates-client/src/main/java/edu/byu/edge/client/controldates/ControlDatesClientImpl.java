package edu.byu.edge.client.controldates;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import com.google.common.base.Preconditions;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

import edu.byu.common.domain.YearTerm;
import edu.byu.commons.exception.ByuException;
import edu.byu.edge.client.controldates.domain.ControlDateType;
import edu.byu.edge.client.controldates.domain.ControlDatesWSServiceType;
import edu.byu.edge.client.controldates.domain.DateRowType;
import edu.byu.edge.client.controldates.domain.ResponseType;

/**
 * Client interface to the control dates web service. Note: The service's xsd
 * provides for an errors field as part of the service call response. Experience
 * has shown this is not used and we are assuming as much for this service. Any
 * successful result will be returned as the service provides it without regard
 * for the errors field.
 * 
 */
public class ControlDatesClientImpl extends BaseClient implements ControlDatesClient {
	private static final Logger LOG = Logger.getLogger(ControlDatesClientImpl.class);

	public static final String productionBaseUrl = "https://soaregistry.byu.edu:443/services/rest/v1/academic/control/controldatesws";
	private static final int timeoutMs = 30000;

	public ControlDatesClientImpl() {
		super(productionBaseUrl, timeoutMs);
	}

	public ControlDatesClientImpl(int timeout) {
		super(productionBaseUrl, timeout);
	}

	@Cacheable(value = "controlDatesClientCache")
	@Path("/all")
	@Override
	public List<DateRowType> getAll(ControlDateType type) {
		Preconditions.checkArgument(type != null, "Invalid (null) Control Date Type.");
		String path = "all/" + type.toString();
		ControlDatesWSServiceType cdws = executeCall(path, 1);
		ResponseType response = cdws.getResponse();
		if (response != null && response.getDateList() != null && response.getDateList().getDateRow() != null) {
			return response.getDateList().getDateRow();
		}
		return new ArrayList<DateRowType>();
	}

	@Cacheable(value = "controlDatesClientCache")
	@Override
	public List<DateRowType> getRange(YearTerm startYearTerm, YearTerm endYearTerm, ControlDateType controlDateType) {
		Preconditions.checkArgument(startYearTerm != null, "Invalid (null) Start YearTerm.");
		Preconditions.checkArgument(endYearTerm != null, "Invalid (null) End YearTerm.");
		Preconditions.checkArgument(controlDateType != null, "Invalid (null) Control Date Type.");

		String path = "range/" + startYearTerm.getYearTerm() + "," + endYearTerm.getYearTerm() + "/" + controlDateType;
		ControlDatesWSServiceType cdws = executeCall(path, 1);
		ResponseType response = cdws.getResponse();
		if (response != null && response.getDateList() != null && response.getDateList().getDateRow() != null) {
			return response.getDateList().getDateRow();
		}
		return new ArrayList<DateRowType>();
	}

	@Cacheable("controlDatesClientCache")
	@Override
	public List<DateRowType> getByYearTermAndTypes(YearTerm yearTerm, ControlDateType... controlDateTypes) {
		Preconditions.checkArgument(yearTerm != null, "Invalid (null) YearTerm.");
		Preconditions.checkArgument(controlDateTypes != null && controlDateTypes.length > 0 && controlDateTypes.length < 10
				&& !isAllNulls(Arrays.asList(controlDateTypes)),
				"At least 1 and no more than 10 Control Date Types are required.");
		String path = "yearterm/" + yearTerm.getYearTerm() + "/";
		for (ControlDateType controlDateType : controlDateTypes) {
			if (controlDateType == null)
				continue;
			path += controlDateType.getControlDateType() + ",";
		}
		path = path.substring(0, path.lastIndexOf(","));
		ControlDatesWSServiceType cdws = executeCall(path, 1);
		ResponseType response = cdws.getResponse();
		if (response != null && response.getDateList() != null && response.getDateList().getDateRow() != null) {
			return response.getDateList().getDateRow();
		}
		return new ArrayList<DateRowType>();
	}

	@Cacheable("controlDatesClientCache")
	@Override
	public DateRowType getByYearTermAndType(YearTerm yearTerm, ControlDateType controlDateType) {
		List<DateRowType> byDate = getByYearTermAndTypes(yearTerm, controlDateType);
		if (byDate != null && byDate.size() > 0) {
			return byDate.get(0);
		}
		return null;
	}

	@Cacheable("controlDatesClientCache")
	@Override
	public Date parseResponseDateString(String responseDateString) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		return formatter.parse(responseDateString);
	}

	@Cacheable("controlDatesClientCache")
	@Override
	public List<DateRowType> getByDateAndTypes(Date asOfDate, ControlDateType... controlDateTypes) {
		Preconditions.checkArgument(asOfDate != null, "Invalid (null) asOfDate Date.");
		Preconditions.checkArgument(
				controlDateTypes != null && controlDateTypes.length > 0 && controlDateTypes.length < 10 && !isAllNulls(Arrays.asList(controlDateTypes)),
				"At least 1 and no more than 10 Control Date Types are required.");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		StringBuilder path = new StringBuilder();
		path.append("asofdate/");
		path.append(sdf.format(asOfDate));
		path.append("/");
		if (controlDateTypes == null || controlDateTypes.length == 0) {
			//append nothing
		} else if (controlDateTypes.length == 1) {
			path.append(controlDateTypes[0]);
		} else {
			for (int i = 0; i < controlDateTypes.length; i++) {
				ControlDateType cdt = controlDateTypes[i];
				path.append(cdt);
				if (i < controlDateTypes.length - 1) path.append(",");
			}
		}
		ControlDatesWSServiceType cdws = executeCall(path.toString(), 1);
		ResponseType response = cdws.getResponse();
		if (response != null && response.getDateList() != null && response.getDateList().getDateRow() != null) {
			return response.getDateList().getDateRow();
		}
		return new ArrayList<DateRowType>();
	}

	@Cacheable("controlDatesClientCache")
	@Override
	public DateRowType getByDateAndType(Date asOfDate, ControlDateType controlDateType) {
		List<DateRowType> byDate = getByDateAndTypes(asOfDate, controlDateType);
		if (byDate != null && byDate.size() > 0) {
			return byDate.get(0);
		}
		return null;
	}

	@Override
	public String getYearTermWithAdjustedFallWinterStartDates(Date onDate, Date winterStartDate, Date fallStartDate) {
		final DateRowType dateRowType = getByDateAndType(onDate, ControlDateType.CURRICULUM);
		final String currentYearTerm = dateRowType.getYearTerm();
		final int currentYear = Integer.parseInt(currentYearTerm.substring(0, 4));

		if (currentYearTerm.endsWith(ControlDatesClient.FALL_TERM_SUFFIX)) {

			if (!winterStartDate.after(onDate)) {
				return String.valueOf(currentYear + 1) + ControlDatesClient.WINTER_TERM_SUFFIX;
			}
		} else if (currentYearTerm.endsWith(ControlDatesClient.SUMMER_TERM_SUFFIX) || currentYearTerm.endsWith(ControlDatesClient.SPRING_TERM_SUFFIX)) {

			if (!fallStartDate.after(onDate)) {
				return String.valueOf(currentYear) + ControlDatesClient.FALL_TERM_SUFFIX;
			}
		}
		return currentYearTerm;
	}

	private ControlDatesWSServiceType executeCall(String path, int retryOnEmptyResult) {
		try {
			final WebResource webres = getResource().path(path);
			LOG.debug("calling: " + webres.toString());
			final ControlDatesWSServiceType type = webres.accept(MediaType.APPLICATION_XML_TYPE).get(ControlDatesWSServiceType.class);
			if (retryOnEmptyResult > 0 && (type == null || type.getResponse() == null || type.getResponse().getRequestCount() == null || type.getResponse().getRequestCount().intValue() == 0)) {
				return executeCall(path, retryOnEmptyResult - 1);
			} else {
				return type;
			}
		} catch (final UniformInterfaceException e) {
			if (super.processExceptionForRetry(e)) {
				LOG.info("retrying GET due to '502 Bad Gateway'");
				return getResource().path(path).accept(MediaType.APPLICATION_XML_TYPE).get(ControlDatesWSServiceType.class);
			} else {
				final Throwable t = processExceptionToCommon(e);
				if (ByuException.class.isAssignableFrom(t.getClass())) {
					throw (ByuException) t;
				} else {
					throw e;
				}
			}
		} catch (ClientHandlerException e) {
			LOG.error("Could not process the control date response.", e);
			throw e;
		}
	}

	public boolean isAllNulls(Iterable<?> array) {
		for (Object element : array)
			if (element != null)
				return false;
		return true;
	}
}
