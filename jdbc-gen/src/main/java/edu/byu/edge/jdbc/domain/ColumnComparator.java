package edu.byu.edge.jdbc.domain;

import java.util.Comparator;
import java.util.regex.Pattern;

/**
 * Author: Wyatt Taylor (wyatt_taylor@byu.edu)
 * Date: 12/06/2012
 *
 * @author Wyatt Taylor (wyatt_taylor@byu.edu)
 * @since 12/06/2012
 */
public final class ColumnComparator implements Comparator<Column> {

	public static final ColumnComparator INSTANCE = new ColumnComparator();

	private ColumnComparator() {
	}

	private static final Pattern PK_PATTERN = Pattern.compile("^.*?P{1}[KR]{1}[I]?.*?$", Pattern.CASE_INSENSITIVE);
//	private static final Pattern PK_PATTERN = Pattern.compile("^.*?P{1}(RI)|(K).*?$");

	@Override
	public int compare(final Column a, final Column b) {
		if (a == null) return 1;
		if (b == null) return -1;

		final String ka = cleanString(a.getKey());
		final String kb = cleanString(b.getKey());
		final int pa = a.getPosition();
		final int pb = b.getPosition();

		final int pkdiff = isPk(ka) ? -1 : (isPk(kb) ? 1 : 0);
		final int keydiff = ka.compareTo(kb);
		final int posdiff = pa - pb;
		return pkdiff != 0 ? pkdiff : (keydiff == 0 ? posdiff : keydiff);
	}

	private boolean isPk(final String s) {
		return PK_PATTERN.matcher(s).matches();
	}

	private String cleanString(final String s) {
		if (s == null) return "";
		return s;
	}
}
