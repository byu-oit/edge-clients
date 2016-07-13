package edu.byu.edge.ypay.v1.client;

import edu.byu.edge.ypay.v1.domain.invoice.InvoiceListType;
import edu.byu.edge.ypay.v1.domain.invoice.InvoiceType;
import edu.byu.edge.ypay.v1.domain.invoice.LineItemType;

import java.util.Date;
import java.util.List;

/**
 * Created by wct5 on 2/18/15.
 */
public interface YpayClient {
	public InvoiceListType findInvoicesForPersonOnDay(String personId, Date day);

	/**
	 * Changes the web service to stage
	 */
	public void setToStageUrl();

	/**
	 * Creates an invoice
	 * @param clientTransactionId Unique ID used in table
	 * @param returnUrl
	 * @param notificationUrl
	 * @param owner Foreign key to the person table
	 * @param lineItemList Must contain at least one LineItemType
	 * @return Invoice ID that was created
	 */
	public long createInvoice(String clientTransactionId, String returnUrl, String notificationUrl, String owner, List<LineItemType> lineItemList);

	/**
	 * Find an invoice's status based off of the clientTransactionId
	 * @param invoiceId
	 * @return Status of the transaction
	 */
	public InvoiceType findInvoice(long invoiceId);

	/**
	 *
	 * @param clientTxId
	 * @return
	 */
	public InvoiceType findInvoiceByClientTransactionId(String clientTxId);

	/**
	 * Marks an invoice as cancelled
	 * @param invoiceId Unique ID provided when the invoice was created
	 * @return Whether or not the cancellation happened properly
	 */
	boolean deleteInvoice(long invoiceId);

	/**
	 * Find the total number of invoices that a person has for a specified clientTxId
	 *
	 * @param clientSystemId Id for the source of the transaction (e.g. parking-citations)
	 * @param personId Id of the person in question
	 * @return Array containing: Count of total invoices, Maximum (last) invoiceId
	 */
	int[] getTotalInvoicesAndMaxInvoiceIdForClientSystemIdAndPersonId(String clientSystemId, String personId);
}
