package edu.byu.edge.client.controldates;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import com.google.common.base.Preconditions;
import com.sun.jersey.api.client.UniformInterfaceException;

import edu.byu.common.domain.YearTerm;
import edu.byu.commons.exception.ByuException;
import edu.byu.edge.client.controldates.domain.ControlDateType;
import edu.byu.edge.client.controldates.domain.ControldateswsServiceType;
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

	private static final String productionBaseUrl = "https://soaregistry.byu.edu:443/services/rest/v1/academic/control/controldatesws";
	private static final int timeoutMs = 30000;

	public ControlDatesClientImpl() {
		super(productionBaseUrl, timeoutMs);
	}

	@Cacheable(key = "#type", value = "controlDatesClientCache")
	@Path("/all")
	@Override
	public List<DateRowType> getAll(ControlDateType type) {
		Preconditions.checkArgument(type != null, "Invalid (null) Control Date Type.");
		String path = "all/" + type.toString();
		ControldateswsServiceType cdws = executeCall(path);
		ResponseType response = cdws.getResponse();
		if (response != null && response.getDateList() != null && response.getDateList().getDateRow() != null) {
			return response.getDateList().getDateRow();
		}
		return new ArrayList<DateRowType>();
	}

	@Override
	public List<DateRowType> getRange(YearTerm startYearTerm, YearTerm endYearTerm, ControlDateType controlDateType) {
		Preconditions.checkArgument(startYearTerm != null, "Invalid (null) Start YearTerm.");
		Preconditions.checkArgument(endYearTerm != null, "Invalid (null) End YearTerm.");
		Preconditions.checkArgument(controlDateType != null, "Invalid (null) Control Date Type.");

		String path = "range/" + startYearTerm.getYearTerm() + "," + endYearTerm.getYearTerm() + "/" + controlDateType;
		ControldateswsServiceType cdws = executeCall(path);
		ResponseType response = cdws.getResponse();
		if (response != null && response.getDateList() != null && response.getDateList().getDateRow() != null) {
			return response.getDateList().getDateRow();
		}
		return new ArrayList<DateRowType>();
	}

	@Override
	public List<DateRowType> getByYearTermAndTypes(YearTerm yearTerm, ControlDateType... controlDateTypes) {
		Preconditions.checkArgument(yearTerm != null, "Invalid (null) YearTerm.");
		Preconditions.checkArgument(controlDateTypes != null && controlDateTypes.length > 0 && controlDateTypes.length < 10
				&& !isAllNulls(Arrays.asList(controlDateTypes)),
				"At least 1 and no more than 10 Control Date Types are required.");
		String path = "yearterm/" + yearTerm + "/";
		for (ControlDateType controlDateType : controlDateTypes) {
			if (controlDateType == null)
				continue;
			path += controlDateType + ",";
		}
		path = path.substring(0, path.lastIndexOf(","));
		ControldateswsServiceType cdws = executeCall(path);
		ResponseType response = cdws.getResponse();
		if (response != null && response.getDateList() != null && response.getDateList().getDateRow() != null) {
			return response.getDateList().getDateRow();
		}
		return new ArrayList<DateRowType>();
	}

	@Override
	public DateRowType getByYearTermAndType(YearTerm yearTerm, ControlDateType controlDateType) {
		List<DateRowType> byDate = getByYearTermAndTypes(yearTerm, controlDateType);
		if (byDate != null && byDate.size() > 0) {
			return byDate.get(0);
		}
		return null;
	}

	@Override
	public List<DateRowType> getByDateAndTypes(Date asOfDate, ControlDateType... controlDateTypes) {
		Preconditions.checkArgument(asOfDate != null, "Invalid (null) asOfDate Date.");
		Preconditions.checkArgument(
				controlDateTypes != null && controlDateTypes.length > 0 && controlDateTypes.length < 10 && !isAllNulls(Arrays.asList(controlDateTypes)),
				"At least 1 and no more than 10 Control Date Types are required.");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String path = "asofdate/" + sdf.format(asOfDate) + "/";
		for (ControlDateType controlDateType : controlDateTypes) {
			path += controlDateType + ",";
		}
		path = path.substring(0, path.lastIndexOf(","));
		ControldateswsServiceType cdws = executeCall(path);
		ResponseType response = cdws.getResponse();
		if (response != null && response.getDateList() != null && response.getDateList().getDateRow() != null) {
			return response.getDateList().getDateRow();
		}
		return new ArrayList<DateRowType>();
	}

	@Override
	public DateRowType getByDateAndType(Date asOfDate, ControlDateType controlDateType) {
		List<DateRowType> byDate = getByDateAndTypes(asOfDate, controlDateType);
		if (byDate != null && byDate.size() > 0) {
			return byDate.get(0);
		}
		return null;
	}

	private ControldateswsServiceType executeCall(String path) {
		try {
			return getResource().path(path).accept(MediaType.APPLICATION_XML_TYPE).get(ControldateswsServiceType.class);
		} catch (final UniformInterfaceException e) {
			if (super.processExceptionForRetry(e)) {
				LOG.info("retrying GET due to '502 Bad Gateway'");
				return getResource().path(path).accept(MediaType.APPLICATION_XML_TYPE).get(ControldateswsServiceType.class);
			} else {
				final Throwable t = processExceptionToCommon(e);
				if (ByuException.class.isAssignableFrom(t.getClass())) {
					throw (ByuException) t;
				} else {
					throw e;
				}
			}
		}
	}

	public boolean isAllNulls(Iterable<?> array) {
		for (Object element : array)
			if (element != null)
				return false;
		return true;
	}
}
