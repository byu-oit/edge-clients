package edu.byu.edge.jdbc;

import edu.byu.edge.jdbc.common.StringExtractor;
import edu.byu.edge.jdbc.util.DefaultResultSet;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 12/13/2012
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 12/13/2012
 */
@RunWith(JUnit4.class)
public class StringExtractorUnitTest {

	private static final Logger LOG = Logger.getLogger(StringExtractorUnitTest.class);

	@Test
	public void testNullMapper() throws SQLException {
		LOG.debug("checking null mapper");
		final StringExtractor ext = StringExtractor.NULL_EXTRACTOR;
		final ResultSet rsa = new MyResultSet(new String[0]);
		final ResultSet rsb = new MyResultSet(new String[]{null});
		final ResultSet rsc = new MyResultSet(new String[]{""});
		assertNull(ext.extractData(rsa));
		assertNull(ext.extractData(rsb));
		assertNotNull(ext.extractData(rsc));
	}

	@Test
	public void testEmptyMapper() throws SQLException {
		LOG.debug("checking empty mapper");
		final StringExtractor ext = StringExtractor.EMPTY_EXTRACTOR;
		final ResultSet rsa = new MyResultSet(new String[0]);
		final ResultSet rsb = new MyResultSet(new String[]{null});
		assertNotNull(ext.extractData(rsa));
		assertNotNull(ext.extractData(rsb));
	}

	private static class MyResultSet extends DefaultResultSet<String> {

		private MyResultSet(final String[] rows) {
			super(rows);
		}

		@Override
		public String getString(final int columnIndex) throws SQLException {
			return getCurRow();
		}

		@Override
		public String getString(final String columnLabel) throws SQLException {
			return getCurRow();
		}

		@Override
		public Object getObject(final int columnIndex) throws SQLException {
			return getCurRow();
		}

		@Override
		public Object getObject(final String columnLabel) throws SQLException {
			return getCurRow();
		}
	}
}
