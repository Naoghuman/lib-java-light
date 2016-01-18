/*
 * Copyright (C) 30.07.2007 | Naoghuman (Peter Rogge) | peter.rogge@yahoo.de
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.naoghuman.lib.java.light.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

/**
 * Einfache <code>Util-Klasse</code>, um ein <code>JLabel</code> mit
 * <code>Text-Antialiasing</code> zu erhalten.
 * 
 * @author Naoghuman (Peter Rogge) | Copyright (c) | 30.07.2007
 * @version 1.0
 */
public class LabelLight extends JLabel {
	
	private static final long serialVersionUID = 8876509714714031378L;
	
	public LabelLight(String text) {
		
		super(text);
	}

	public LabelLight(String text, int horizontalAlignment) {
		
		super(text, horizontalAlignment);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
	
		final Graphics2D g2D = (Graphics2D) g.create();
		g2D.setRenderingHint(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON
				);
		
		super.paint(g2D);
	}
}
