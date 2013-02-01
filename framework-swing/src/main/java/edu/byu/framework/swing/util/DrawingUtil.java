package edu.byu.framework.swing.util;

import org.apache.log4j.Logger;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author jmooreoa
 */
public final class DrawingUtil {

	private static final Logger LOG = Logger.getLogger(DrawingUtil.class);

	private DrawingUtil() {
	}

	public static final float drawString(Graphics2D g, String string, Font font, float x, float y) {
		g.setFont(font);
		y += font.getLineMetrics(string, g.getFontRenderContext()).getAscent();
		g.drawString(string, x, y);
		return getTextHeight(g, string, font);
	}

	public static final float drawRightAlignedString(Graphics2D g, String string, Font font, float x, float y) {
		float startX = x - getTextWidth(g, string, font);
		return drawString(g, string, font, startX, y);
	}

	public static final float getTextHeight(Graphics2D g, String string, Font font) {
		LineMetrics lm = font.getLineMetrics(string, g.getFontRenderContext());
		return lm.getHeight();
	}

	public static final float getCenteringStartX(Graphics2D g, String string, Font font, float centerX) {
		float textWidth = getTextWidth(g, string, font);
		return centerX - (textWidth / 2);
	}

	public static final float getTextWidth(Graphics2D g, String string, Font font) {
		return g.getFontMetrics(font).stringWidth(string);
	}

	public static final float drawXCenteredString(Graphics2D g, String string, Font font, float y, float centerX) {
		float textWidth = getTextWidth(g, string, font);
		float x = centerX - (textWidth / 2);
		return drawString(g, string, font, x, y);
	}

	public static final float drawYCenteredString(Graphics2D g, String string, Font font, float x, float centerY) {
		float startY = getCenteringStartY(g, string, font, centerY);
		return drawString(g, string, font, x, startY);
	}

	public static final float getCenteringStartY(Graphics2D g, String string, Font font, float centerY) {
		float textHeight = getTextHeight(g, string, font);
		return centerY - (textHeight / 2);
	}

	public static final void drawCenteredString(Graphics2D g, String string, Font font, float centerX, float centerY) {
		float x = getCenteringStartX(g, string, font, centerX);
		float y = getCenteringStartY(g, string, font, centerY);
		drawString(g, string, font, x, y);
	}

	public static final float getMaxLineHeight(Graphics2D g, Map<String, Font> values) {
		float max = 0;
		FontRenderContext context = g.getFontRenderContext();
		for (String each : values.keySet()) {
			float height = values.get(each).getLineMetrics(each, context).getHeight();
			max = Math.max(max, height);
		}
		return max;
	}

	public static final float getMaxLineHeight(Graphics2D g, Font font, String... strings) {
		float max = 0;
		FontRenderContext context = g.getFontRenderContext();
		for (String each : strings) {
			max = Math.max(max, font.getLineMetrics(each, context).getHeight());
		}
		return max;
	}

	public static final Point getCenteringStartPoint(Graphics2D g, String string, Font font, Point center) {
		int x = (int) getCenteringStartX(g, string, font, center.x);
		int y = (int) getCenteringStartY(g, string, font, center.y);
		return new Point(x, y);
	}

	public static final Dimension getPrintedSize(String string, Font f, Graphics2D g, double allowedWidth) {
		int totalHeight = 0;
		for (String each : getLinesThatFit(string, f, g, allowedWidth)) {
			totalHeight += getTextHeight(g, each, f);
		}
		return new Dimension((int) allowedWidth, totalHeight);
	}

	public static final List<String> getLinesThatFit(String string, Font f, Graphics2D g, double allowedWidth) {
		LOG.trace(String.format("Breaking string %s into lines that fit %s", string, allowedWidth));
		if (string == null) {
			List<String> lines = new LinkedList<String>();
			lines.add("");
			return lines;
		}
		char[] chars = string.toCharArray();
		int lastLineBreak = 0;
		int lastBreakpoint = -1;
		float lineWidth = 0;
		String buffer = "";
		List<String> lines = new LinkedList<String>();
		for (int i = 0; i < chars.length; i++) {
			char each = chars[i];
			if (isLineBreak(each)) {
				lines.add(string.substring(lastLineBreak, i + 1));
				buffer = "";
				lastLineBreak = i;
				lastBreakpoint = -1;
				lineWidth = 0;
				LOG.trace("Found line break char");
				continue;
			}
			if (Character.isSpaceChar(each)) {
				LOG.trace("Found space");
				lastBreakpoint = i;
			}

			buffer += each;
			lineWidth = getTextWidth(g, buffer, f);
			if (i == chars.length - 1) {
				LOG.trace("Reached end");
				lines.add(string.substring(lastLineBreak));
			}
			LOG.trace(String.format("String width: %s  Allowed: %s", lineWidth, allowedWidth));
			if (lineWidth <= allowedWidth) {
				LOG.trace(String.format("String %s fits", buffer));
				continue;
			}
			int breakAt = (lastBreakpoint > 0) ? lastBreakpoint : i;
			lines.add(string.substring(lastLineBreak, breakAt + 1));
			buffer = "";
			lastLineBreak = breakAt;
			lastBreakpoint = -1;
			i = breakAt;
			lineWidth = 0;
		}
		ListIterator<String> li = lines.listIterator();
		while (li.hasNext()) {
			String each = li.next();
			while (each.startsWith(" ") || (!each.isEmpty() && isLineBreak(each.charAt(0))) ) {
				each = each.substring(1);
			}
			li.set(each);
		}
		return lines;
	}

	private static final Set<Character> breaks;

	static {
		Set<Character> set = new HashSet<Character>();
		set.add('\n');
		set.add('\r');
		set.add('\u0085');
		set.add('\u2028');
		set.add('\u2029');
		breaks = Collections.unmodifiableSet(set);
	}

	private static boolean isLineBreak(char c) {
		return breaks.contains(c);
	}
}
