package edu.byu.swing.models;

import org.apache.log4j.Logger;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


/**
 * An implementation of PlainDocument that limits the number of characters that
 * can be added.  Useful for JTextComponent implementations with a maximum number
 * of characters.
 * @author jmooreoa
 * @author nbunn
 * @version 1.1.0
 * @since 1.0.0
 * @see PlainDocument
 * @see JTextComponent
 */
public class LimitedLengthDocument extends PlainDocument {

	private static final Logger LOG = Logger.getLogger(LimitedLengthDocument.class);

    private int limit;

    /**
     *  Creates a LimitedLengthDocument with maximum length limit
     * @param limit maximum length of document
     */
    public LimitedLengthDocument(int limit) {
        super();
        this.limit = limit;
    }

    /**
     * Returns current maximum length
     * @return maximum length
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Sets maximum length
     * @param limit new maximum length
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * Ipmlementation of insertString that limits total length of document
     * @param offset where to insert
     * @param str what to insert
     * @param attr attributes associated with text
     * @throws javax.swing.text.BadLocationException if offset is invalid
     */
    @Override
    public void insertString(int offset, String str, AttributeSet attr)
            throws BadLocationException {
        if (str == null || (getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }

	@Override
	public void replace(int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
		if (text == null || (getLength() - length + text.length()) <= limit) {
			super.replace(offset, length, text, attrs);
		}
	}
}