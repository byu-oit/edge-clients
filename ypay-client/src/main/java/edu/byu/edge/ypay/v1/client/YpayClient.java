package edu.byu.edge.ypay.v1.client;

import edu.byu.edge.ypay.v1.domain.invoice.InvoiceListType;
import edu.byu.edge.ypay.v1.domain.invoice.LineItemType;

import java.util.Date;
import java.util.List;

/**
 * Created by wct5 on 2/18/15.
 */
public interface YpayClient {
	public InvoiceListType findInvoicesForPersonOnDay(String personId, Date day);

	/**
	 * Creates an invoice using empty returnUrl and notificationUrl
	 * @param clientTransactionId Unique ID used in table
	 * @param owner Foreign key to the person table
	 * @param lineItemList Must contain at least one LineItemType
	 * @return Invoice ID that was created
	 */
	public String createInvoice(String clientTransactionId, String owner, List<LineItemType> lineItemList);

	/**
	 * Creates an invoice
	 * @param clientTransactionId Unique ID used in table
	 * @param returnUrl
	 * @param notificationUrl
	 * @param owner Foreign key to the person table
	 * @param lineItemList Must contain at least one LineItemType
	 * @return Invoice ID that was created
	 */
	public String createInvoice(String clientTransactionId, String returnUrl, String notificationUrl, String owner, List<LineItemType> lineItemList);

	/**
	 * Marks an invoice as cancelled
	 * @param invoiceId Unique ID provided when the invoice was created
	 * @return Whether or not the cancellation happened properly
	 */
	boolean deleteInvoice(String invoiceId);
}
