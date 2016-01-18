/*
 * Copyright (C) 14.06.2008 | Naoghuman (Peter Rogge) | peter.rogge@yahoo.de
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
package com.github.naoghuman.lib.java.light.awt;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.io.Serializable;

/**
 * @author PRo (Peter Rogge) | Copyright (c) | 14.06.2008
 * @version 1.0
 */
public class SmartGridLayoutLight extends Object implements LayoutManager,
	Serializable {

	private static final long serialVersionUID = 3731585711603166535L;

	private int rows = 1;
	private int cols = 1;
	private int hgap = 0;
	private int vgap = 0;
	private int preferredWidth = 0;

	private Component[][] layoutGrid = null;
	
	public SmartGridLayoutLight() {
		this(1, 1, 0);
	}
	
	public SmartGridLayoutLight(final int width) {
		this(1, 1, width);
	}

	public SmartGridLayoutLight(final int rows, final int cols) {
		
		this(cols, rows, 0);
	}
	
	public SmartGridLayoutLight(final int rows, final int cols, final int width) {
		this(rows, cols, width, 0, 0);
	}
	
	public SmartGridLayoutLight(
			final int rows, final int cols, final int hgap, final int vgap
	) {
		this(rows, cols, 0, hgap, vgap);
	}
	
	public SmartGridLayoutLight(
			final int rows, final int cols,
			final int width,
			final int hgap, final int vgap
	) {
		super();
		
		this.rows = rows;
		this.cols = cols;
		this.hgap = hgap;
		this.vgap = vgap;
		this.preferredWidth = width;
		
		layoutGrid = new Component[cols][rows];
	}

	public final void addLayoutComponent(final String s, final Component c) { }

	private final void buildLayoutGrid(final Container c) {
		final Component[] children = c.getComponents();

		int row = 0;
		int col = 0;
		for (int i = 0; i < children.length; i++) {

			if (i != 0) {
				
				col = i % cols;
				row = (i - col) / cols;
			}

			layoutGrid[col][row] = children[i];
		}
	}

	private final int computeColumnWidth(final int colsNr) {
		
		int maxWidth = 1;
		for (int row = 0; row < rows; row++) {
			int width = preferredWidth;
			if (preferredWidth == 0) {
				width = layoutGrid[colsNr][row].getPreferredSize().width;
			}
			maxWidth = Math.max(width, maxWidth);
		}
		
		return maxWidth;
	}

	private final int computeRowHeight(final int rowsNr) {
		
		int maxHeight = 1;
		for (int column = 0; column < cols; column++) {
			
			final int height = layoutGrid[column][rowsNr].getPreferredSize().height;
			maxHeight = Math.max(height, maxHeight);
		}
		
		return maxHeight;
	}
	public final void layoutContainer(final Container c) {
		this.buildLayoutGrid(c);

		final int[] rowHeights = new int[rows];
		final int[] columnWidths = new int[cols];

		for (int row = 0; row < rows; row++) {
			rowHeights[row] = this.computeRowHeight(row);
		}

		for (int column = 0; column < cols; column++) {
			columnWidths[column] = this.computeColumnWidth(column);
		}

		final Insets insets = c.getInsets();

		if (c.getComponentOrientation().isLeftToRight()) {
			
			int horizLoc = insets.left;
			for (int column = 0; column < cols; column++) {
				int vertLoc = insets.top;

				for (int row = 0; row < rows; row++) {
					
					final Component current = layoutGrid[column][row];

					current.setBounds(
							horizLoc, vertLoc,
							columnWidths[column],
							rowHeights[row]
							           );
					
					vertLoc += (rowHeights[row] + vgap);
				}
				horizLoc += (columnWidths[column] + hgap);
			}
		} else {
			
			int horizLoc = c.getWidth() - insets.right;
			for (int column = 0; column < cols; column++) {
				
				int vertLoc = insets.top;
				horizLoc -= columnWidths[column];

				for (int row = 0; row < rows; row++) {
					
					final Component current = layoutGrid[column][row];
					current.setBounds(
							horizLoc, vertLoc,
							columnWidths[column],
							rowHeights[row]
							           );
					
					vertLoc += (rowHeights[row] + vgap);
				}
				horizLoc -= hgap;
			}
		}
	}

	public final Dimension minimumLayoutSize(final Container c) {
		this.buildLayoutGrid(c);
		final Insets insets = c.getInsets();

		int height = 0;
		int width = 0;
		for (int row = 0; row < rows; row++) {
			height += this.computeRowHeight(row);
		}

		for (int column = 0; column < cols; column++) {
			width += this.computeColumnWidth(column);
		}

		height += (vgap * (rows - 1)) + insets.top + insets.bottom;
		width += (hgap * (cols - 1)) + insets.right + insets.left;

		return new Dimension(width, height);

	}

	public final Dimension preferredLayoutSize(final Container c) {
		return minimumLayoutSize(c);
	}

	public final void removeLayoutComponent(final Component c) { }
}
