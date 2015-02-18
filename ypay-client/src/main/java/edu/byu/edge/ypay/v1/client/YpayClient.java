package edu.byu.edge.ypay.v1.client;

import edu.byu.edge.ypay.v1.domain.invoice.InvoiceListType;

import java.util.Date;

/**
 * Created by wct5 on 2/18/15.
 */
public interface YpayClient {
	public InvoiceListType findInvoicesForPersonOnDay(String personId, Date day);
}
