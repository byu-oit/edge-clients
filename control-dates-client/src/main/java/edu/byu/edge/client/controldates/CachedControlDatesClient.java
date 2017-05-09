package edu.byu.edge.client.controldates;

import com.sun.jersey.api.client.filter.ClientFilter;
import edu.byu.common.domain.YearTerm;
import edu.byu.edge.client.controldates.domain.ControlDateType;
import edu.byu.edge.client.controldates.domain.DateRowType;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by Scott Hutchings on 5/5/2017.
 * edge-clients
 */
public class CachedControlDatesClient extends ControlDatesClientImpl {

	public CachedControlDatesClient(ClientFilter... filters) {
		super(filters);
	}

	public CachedControlDatesClient(int timeout, ClientFilter... filters) {
		super(timeout, filters);
	}

	@Override
	@Cacheable(value = "controlDatesCache", unless = "#result == null")
	public List<DateRowType> getAll(ControlDateType type) {
		return super.getAll(type);
	}

	@Override
	@Cacheable(value = "controlDatesCache", unless = "#result == null")
	public List<DateRowType> getRange(YearTerm startYearTerm, YearTerm endYearTerm, ControlDateType controlDateType) {
		return super.getRange(startYearTerm, endYearTerm, controlDateType);
	}

	@Override
	@Cacheable(value = "controlDatesCache", unless = "#result == null")
	public List<DateRowType> getByYearTermAndTypes(YearTerm yearTerm, ControlDateType... controlDateTypes) {
		return super.getByYearTermAndTypes(yearTerm, controlDateTypes);
	}

	@Override
	@Cacheable(value = "controlDatesCache", unless = "#result == null")
	public DateRowType getByYearTermAndType(YearTerm yearTerm, ControlDateType controlDateType) {
		return super.getByYearTermAndType(yearTerm, controlDateType);
	}

	@Override
	@Cacheable(value = "controlDatesCache", unless = "#result == null")
	public Date parseResponseDateString(String responseDateString) throws ParseException {
		return super.parseResponseDateString(responseDateString);
	}

	@Override
	@Cacheable(value = "controlDatesCache", unless = "#result == null")
	public List<DateRowType> getByDateAndTypes(Date asOfDate, ControlDateType... controlDateTypes) {
		return super.getByDateAndTypes(asOfDate, controlDateTypes);
	}

	@Override
	@Cacheable(value = "controlDatesCache", unless = "#result == null")
	public DateRowType getByDateAndType(Date asOfDate, ControlDateType controlDateType) {

		return super.getByDateAndType(asOfDate, controlDateType);
	}

	@Override
	@Cacheable(value = "controlDatesCache", unless = "#result == null")
	public String getYearTermWithAdjustedFallWinterStartDates(Date onDate, Date winterStartDate, Date fallStartDate) {
		return super.getYearTermWithAdjustedFallWinterStartDates(onDate, winterStartDate, fallStartDate);
	}
}
