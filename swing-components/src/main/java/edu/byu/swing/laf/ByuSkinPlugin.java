package edu.byu.swing.laf;

import org.jvnet.substance.plugin.SubstanceSkinPlugin;
import org.jvnet.substance.skin.SkinInfo;

import java.util.HashSet;
import java.util.Set;

/**
 * User: jmooreoa
 * Date: May 29, 2010
 * Time: 1:52:25 AM
 */
public class ByuSkinPlugin implements SubstanceSkinPlugin {
	public Set<SkinInfo> getSkins() {
		Set<SkinInfo> result = new HashSet<SkinInfo>();
		result.add(new SkinInfo(LovePinkSkin.NAME, LovePinkSkin.class.getName()));
		return result;
	}

	public String getDefaultSkinClassName() {
		return null;
	}
}