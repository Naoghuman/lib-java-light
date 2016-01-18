/*
 * Copyright (C) 08.10.2006 | Naoghuman (Peter Rogge) | peter.rogge@yahoo.de
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

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.SwingConstants;

/**
 * A component that allows the user to select one or more objects from a
 * list.<p>
 * 
 * How to use a <code>JList</code> see the commentary form the original
 * class <code>javax.swing.JList</code>.
 * <p>
 * 
 * I have add severals methods zu modified the list. For example:<br>
 * With <code>addElement(element)</code> you can add a new element
 * to the list. To remove selected elements you can use
 * <code>removeSelection()</code>.
 * <p>
 * 
 * <b>Benötigte Klassen:</b><br>
 *  - <code>com.github.naoghuman.lib.java.light.swing.ListModelLight</code>
 * 
 * @author Naoghuman (Peter Rogge) | Copyright (c) | 08.10.2006
 * @version 1.0
 * @change 19.02.2007
 * @change 13.05.2007
 */
public final class ListLight extends JList {
	
	private static final byte ZERO = 0;
	private static final byte ONE = 1;
	private static final byte TWO = 2;
	private static final byte THREE = 3;
	private static final byte FIVE = 5;
	
    /**
     * A value for the selectionMode property: select one list index
     * at a time.
     */
    public static final byte SINGLE_SELECTION = ZERO;

    /**
     * A value for the selectionMode property: select one contiguous
     * range of indices at a time.
     */
    public static final byte SINGLE_INTERVAL_SELECTION = ONE;

    /**
     * A value for the selectionMode property: select one or more 
     * contiguous ranges of indices at a time.
     */
    public static final byte MULTIPLE_INTERVAL_SELECTION = TWO;

	private static final long serialVersionUID = 6134919599974188065L;
	
	private ListModelLight proListModel = null;
	
	/**
	 * Konstruktor für die Initialisierung der Klasse <code>PRoList</code>.
	 * Als Defaultwerte wird das <code>erste Element</code> der übergebenen
	 * Daten selektiert. Die Liste hat eine <code>Weite von 180</code> und
	 * <code>5 Zeilen</code> sind sichbar. Die <code>Darstellungsschrift</code>
	 * der Elemente ist: <code>new Font("Dialog", Font.PLAIN, 14)</code>.
	 * Als Selektionsmodus ist <code>SINGLE_SELECTION</code> eingestellt.
	 * 
	 * @param data 		Daten der Liste.
	 */
	public ListLight(final Object[] data) { this(data, SINGLE_SELECTION); }
	
	/**
	 * Konstruktor für die Initialisierung der Klasse <code>PRoList</code>.
	 * Als Defaultwerte wird das <code>erste Element</code> der übergebenen
	 * Daten selektiert. Die Liste hat eine <code>Weite von 180</code> und
	 * <code>5 Zeilen</code> sind sichbar. Die <code>Darstellungsschrift</code>
	 * der Elemente ist: <code>new Font("Dialog", Font.PLAIN, 14)</code>.
	 * 
	 * @param data 		Daten der Liste.
	 * @param mode		Selektionsmodus der Liste.
	 */
	public ListLight(final Object[] data, final byte mode) {
		
		this(data, new Font("Dialog", Font.PLAIN, 14), mode);
	}
	
	/**
	 * Konstruktor für die Initialisierung der Klasse <code>PRoList</code>.
	 * Als Defaultwerte wird das <code>erste Element</code> der übergebenen
	 * Daten selektiert. Die Liste hat eine <code>Weite von 180</code> und
	 * <code>5 Zeilen</code> sind sichbar.
	 * 
	 * @param data 		Daten der Liste.
	 * @param font		die Darstellungsschrift der Elemente.
	 * @param mode		Selektionsmodus der Liste.
	 */
	public ListLight(final Object[] data, final Font font, final byte mode) {
		
		this(
				data, (data.length > ZERO) ? data[ZERO] : ZERO,
				font, mode, 180
				);
	}
	
	/**
	 * Konstruktor für die Initialisierung der Klasse <code>PRoList</code>.
	 * Als Defaultwert sind <code>5 Zeilen</code> der Liste sichtbar.
	 * 
	 * @param data		Daten der Liste.
	 * @param value		das zu selektierende Element.
	 * @param font		die Darstellungsschrift der Elemente.
	 * @param mode 		Selektionsmodus der Liste.
	 * @param width		die Weite der Liste.
	 * 
	 * @exception IllegalArgumentException, wenn
	 * <code>(mode < SINGLE_SELECTION || mode > MULTIPLE_INTERVAL_SELECTION)</code>.
	 */
	public ListLight(
			final Object[] data, final Object value,
			final Font font, final byte mode, final int width
	) {
		this(data, value, font, mode, width, FIVE);
	}
	
	public ListLight(
			final Object[] data, final Object value,
			final Font font, final byte mode, final int width, final int row
	) {
		super();
		
		this.init(data, value, font, mode, width, row);
	}

	/**
	 * Testet, ob das übergebene Element in der Liste vorhanden ist.
	 * 
	 * @param element das zu überprüfende Element.
	 * @return liefert <code>true</code>, wenn das Element vorhanden ist,
	 * anderfalls <code>false</code>.
	 */
	public final boolean contain(final Object element) {
		
		return proListModel.contains(element);
	}

	/**
	 * Es wird ein neues Element der Liste hinzugefügt.
	 * 
	 * @param element das neue Element.
	 */
	public final void addElement(final Object element) {
		
		proListModel.add(element);
		if (this.getLength() == ONE) {  return; }
		
		proListModel.sort();
	}
	
	/**
	 * Es werden neue Element der Liste hinzugefügt.
	 * 
	 * @param elements die neue Elemente.
	 */
	public final void addElements(final Object[] elements) {
		
		if (this.getLength() == ZERO) {

			Arrays.sort(elements);
			proListModel.add(elements);
			
			return;
		}
		
		proListModel.add(elements);
		proListModel.sort();
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D)g.create();
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		super.paint(g2D);
	}
	
	/**
	 * Liefert das Element an der Stelle <code>index</code>.
	 * 
	 * @param index des zu liefernden Elements.
	 * @return Element an der Stelle <code>index</code>.
	 */
	public final Object getElementAt(final int index) {
		
		return proListModel.getElementAt(index);
	}
	
	/**
	 * Liefert ein <code>Objekt-Array</code> der Elemente der Liste.
	 * 
	 * @return alle Elemente der Liste.
	 */
	public final Object[] getElements() {
		
		return proListModel.getElements();
	}

    /**
	 * Liefert die Anzahl der Elemente in der Liste.
	 * 
	 * @return Anzahl der Elemente.
	 */
    public final int getLength() { return proListModel.getSize(); }
    
    /* (non-Javadoc)
     * @see javax.swing.JList#getScrollableUnitIncrement(
     * java.awt.Rectangle, int, int)
     */
    @Override
    public final int getScrollableUnitIncrement(
    		final Rectangle visibleRect,
    		final int orientation, final int direction
    ) {
    	/*
    	 * Subclass JList to workaround bug 4832765, which can cause the
    	 * scroll pane to not let the user easily scroll up to the beginning
    	 * of the list.  An alternative would be to set the unitIncrement
    	 * of the JScrollBar to a fixed value. You wouldn't get the nice
    	 * aligned scrolling, but it should work.
    	 */
        int row;
        if (
        		(orientation == SwingConstants.VERTICAL)
        		&& (direction < ZERO)
        		&& ((row = super.getFirstVisibleIndex()) != -ONE)
        ) {
        	final Rectangle r = super.getCellBounds(row, row);
            if ((r.y == visibleRect.y) && (row != ZERO))  {
            	
            	final Point loc = r.getLocation();
                loc.y--;
                final int prevIndex = super.locationToIndex(loc);
                final Rectangle prevR = super.getCellBounds(prevIndex, prevIndex);

                if (prevR == null || prevR.y >= r.y) { return ZERO; }
                return prevR.height;
            }
        }
        
        return super.getScrollableUnitIncrement(
                        visibleRect, orientation, direction
                        );
    }
	
	private void init(
			final Object[] data, final Object value,
			final Font font, final byte mode, final int width, int row
	) {
		this.checkMode(mode);
		
		proListModel = new ListModelLight(data);
		
		super.setModel(proListModel);
		super.setBorder(BorderFactory.createEmptyBorder(
				FIVE, FIVE, FIVE, FIVE
				));
		super.setFont(font);
		super.setFixedCellHeight(super.getFont().getSize() + THREE);
		super.setFixedCellWidth(width);
		super.setLayoutOrientation(VERTICAL);
	
		if (data != null && data.length > ZERO) {

			super.setSelectedValue(value, Boolean.TRUE);
		}
		
		super.setSelectionMode(mode);
		super.setVisibleRowCount(row);
	}

	private void checkMode(byte mode) {

		if (mode < SINGLE_SELECTION || mode > MULTIPLE_INTERVAL_SELECTION) {
			
			final String backflash = System.getProperty("line.separator");

			throw new IllegalArgumentException(
					backflash
					+ "Wrong modus: '" + mode + "'." + backflash
					+ "mode must be one of:" + backflash
					+ "SINGLE_SELECTION" + backflash
					+ "SINGLE_INTERVAL_SELECTION" + backflash
					+ "MULTIPLE_INTERVAL_SELECTION"
					);
		}
	}

	/**
	 * Das Element wird aus der Liste entfernt, wenn es vorhanden ist.
	 * Liefert das Objekt zurück, wenn es entfernt werden konnte.
	 * 
	 * @return das entfernte Element.
	 */
	public final Object remove(final Object element) {
		
		return proListModel.remove(element);
	}
	
	/**
	 * Entfernt alle Elemente dieser Liste.
	 */
	public final void removeAll() { proListModel.removeAll(); }
	
	/**
     * Es werden alle selektierten Elemente der Liste entfernt.
     * Liefert die entfernte Elemente.
     * 
     * @return entfernte Elemente.
     */
	public final Object[] removeSelection() {
		
		final Object[] removed = super.getSelectedValues();
		for (final Object obj: removed) { proListModel.remove(obj); }
		
		return removed;
	}
}
