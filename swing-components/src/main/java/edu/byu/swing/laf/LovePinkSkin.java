package edu.byu.swing.laf;

import org.jvnet.substance.api.*;
import org.jvnet.substance.api.painter.overlay.BottomLineOverlayPainter;
import org.jvnet.substance.api.painter.overlay.TopShadowOverlayPainter;
import org.jvnet.substance.painter.border.ClassicBorderPainter;
import org.jvnet.substance.painter.border.CompositeBorderPainter;
import org.jvnet.substance.painter.border.DelegateBorderPainter;
import org.jvnet.substance.painter.decoration.DecorationAreaType;
import org.jvnet.substance.painter.decoration.MarbleNoiseDecorationPainter;
import org.jvnet.substance.painter.gradient.MatteGradientPainter;
import org.jvnet.substance.painter.highlight.ClassicHighlightPainter;
import org.jvnet.substance.shaper.ClassicButtonShaper;

/**
 * @version 1.0.0
 * @author jmooreoa
 */
public class LovePinkSkin extends SubstanceSkin {
	/**
	 * Display name for <code>this</code> skin.
	 */
	public static final String NAME = "Love Pink";

	/**
	 * Overlay painter to paint separator lines on some decoration areas.
	 */
	private BottomLineOverlayPainter bottomLineOverlayPainter;

	/**
	 * Creates a new <code>LovePink/code> skin.
	 */
	public LovePinkSkin() {
		ColorSchemes schemes = SubstanceSkin
				.getColorSchemes(LovePinkSkin.class.getClassLoader().getResource(
						"edu/byu/swing/laf/lovePink.colorschemes"));

		SubstanceColorScheme activeScheme = schemes.get("Love Pink Active");
		SubstanceColorScheme defaultScheme = schemes.get("Love Pink Default");
		SubstanceColorScheme disabledScheme = defaultScheme;

		SubstanceColorSchemeBundle defaultSchemeBundle = new SubstanceColorSchemeBundle(
				activeScheme, defaultScheme, disabledScheme);
		defaultSchemeBundle.registerColorScheme(disabledScheme, 0.6f,
				ComponentState.DISABLED_UNSELECTED);
		defaultSchemeBundle.registerColorScheme(activeScheme, 0.6f,
				ComponentState.DISABLED_SELECTED);

		this.registerDecorationAreaSchemeBundle(defaultSchemeBundle,
				DecorationAreaType.NONE);

		SubstanceColorScheme watermarkScheme = schemes.get("Love Pink Watermark");

		this.registerAsDecorationArea(activeScheme,
				DecorationAreaType.PRIMARY_TITLE_PANE,
				DecorationAreaType.SECONDARY_TITLE_PANE,
				DecorationAreaType.HEADER);

		this.registerAsDecorationArea(watermarkScheme,
				DecorationAreaType.GENERAL, DecorationAreaType.FOOTER,
				DecorationAreaType.TOOLBAR);

		// add an overlay painter to paint a drop shadow along the top
		// edge of toolbars
		this.addOverlayPainter(TopShadowOverlayPainter.getInstance(),
				DecorationAreaType.TOOLBAR);

		// add an overlay painter to paint separator lines along the bottom
		// edges of title panes and menu bars
		this.bottomLineOverlayPainter = new BottomLineOverlayPainter(
				ColorSchemeSingleColorQuery.DARK);
		this.addOverlayPainter(this.bottomLineOverlayPainter,
				DecorationAreaType.PRIMARY_TITLE_PANE,
				DecorationAreaType.SECONDARY_TITLE_PANE,
				DecorationAreaType.HEADER);

		this.buttonShaper = new ClassicButtonShaper();
		this.gradientPainter = new MatteGradientPainter();
		this.borderPainter = new CompositeBorderPainter("Love Pink",
				new ClassicBorderPainter(), new DelegateBorderPainter(
						"Love Pink Inner", new ClassicBorderPainter(),
						new ColorSchemeTransform() {
							public SubstanceColorScheme transform(
									SubstanceColorScheme scheme) {
								return scheme.tint(0.8f);
							}
						}));

		this.highlightPainter = new ClassicHighlightPainter();

		MarbleNoiseDecorationPainter decorationPainter = new MarbleNoiseDecorationPainter();
		decorationPainter.setTextureAlpha(0.7f);
		this.decorationPainter = decorationPainter;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jvnet.substance.skin.SubstanceSkin#getDisplayName()
	 */
	public String getDisplayName() {
		return NAME;
	}



}