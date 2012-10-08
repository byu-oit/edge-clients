package edu.byu.edge.client.controldates;

import java.util.Date;

import edu.byu.common.domain.YearTerm;
import edu.byu.edge.client.controldates.domain.ControlDateType;
import edu.byu.edge.client.controldates.domain.ControldateswsServiceType;

public interface ControlDatesClient {

	public ControldateswsServiceType getAll(ControlDateType type);

	public ControldateswsServiceType getRange(YearTerm startYearTerm, YearTerm endYearTerm, ControlDateType controlDateType);

	public ControldateswsServiceType getByYearTerm(YearTerm yearTerm, ControlDateType... controlDateTypes);

	public ControldateswsServiceType getByDate(Date asOfDate, ControlDateType... controlDateTypes);

}
