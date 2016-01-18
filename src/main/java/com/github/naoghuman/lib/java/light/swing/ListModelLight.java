/*
 * Copyright (C) 19.02.2007 | Naoghuman (Peter Rogge) | peter.rogge@yahoo.de
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

import java.util.Arrays;

import javax.swing.AbstractListModel;



/**
 * Ein einfaches <code>ListenModel</code>. Veränderungen der Liste
 * werden bekannte <code>ListDataListener</code>s gemeldet.
 * <p>
 * 
 * <b>Benötigte Klassen:</b><br>
 *  - <code>com.github.naoghuman.lib.java.light.swing.ArrayListLight</code>
 * 
 * @author Naoghuman (Peter Rogge) | Copyright (c) | 19.02.2007
 * @version 1.0
 * @change 11.05.2007
 */
public final class ListModelLight extends AbstractListModel {

	private static final long serialVersionUID = -5535671028659674241L;

	private ArrayListLight<Object> palElements = null;

	/**
	 * Standard-Konstruktor der Klasse <code>PRoListModel</code>.
	 * Initialisiert ein <code>ListenModel</code> mit keinen Elementen.
	 */
	public ListModelLight() { this(null); }
	
	/**
	 * Konstruktor, um die Klasse <code>PRoListModel</code> zu initialisieren.
	 * Das <code>ListenModel</code> wird mit den Daten von <code>elements</code>
	 * gefüllt.
	 * 
	 * @param elements Defaultdaten des <code>ListenModels</code>.
	 */
	public ListModelLight(final Object[] elements) {
		
		this.init();
		if (elements != null) { this.add(elements); };
	}

	/**
	 * Inserts the specified element at the specified position in this list.
	 * <p>
	 * Throws an <code>ArrayIndexOutOfBoundsException</code> if the index is
	 * out of range (<code>index &lt; 0 || index &gt; getSize()</code>).
	 * 
	 * @param index index at which the specified element is to be inserted.
	 * @param element element to be inserted.
	 */
	public final void add(final int index, final Object element) {

		palElements.add(index, element);
		super.fireIntervalAdded(this, index, index);
	}
	
	/**
	 * Inserts the specified element at the end of this list.
	 * 
	 * @param element to be inserted.
	 */
	public final void add(final Object element) {

		palElements.add(element);
		final int index = palElements.getSize() - 1;
		super.fireIntervalAdded(this, index, index);
	}

	/**
	 * Inserts the specified <code>elements</code> to the end of this list.
	 * 
	 * @param elements to be inserted.
	 */
	public final void add(final Object[] elements) {

		for (int index = 0; index < elements.length; index++) {

			palElements.add(elements[index]);
			super.fireIntervalAdded(this, index, index);
		}
	}

	/**
	 * Tests whether the specified object is a component in this list.
	 * 
	 * @param element an object.
	 * @return <code>true</code> if the specified object is the same as a
	 *         component in this list.
	 */
	public final boolean contains(final Object element) {
		
		return palElements.contains(element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	public final Object getElementAt(final int index) {
		
		return palElements.get(index);
	}

	/**
	 * Returns an <code>Object-Array</code> of the components of this list.
	 * 
	 * @return All elements from this list.
	 */
	public final Object[] getElements() { return palElements.toArray(); }

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ListModel#getSize()
	 */
	public final int getSize() { return palElements.getSize(); }

	private final void init() { palElements = new ArrayListLight<Object>(); }

	/**
	 * Removes the element at the specified position in this list. Returns the
	 * element that was removed from the list.
	 * <p>
	 * Throws an <code>ArrayIndexOutOfBoundsException</code> if the index is
	 * out of range (<code>index &lt; 0 || index &gt;= getSize()</code>).
	 * 
	 * @param Object.
	 */
	public final Object remove(final int index) {

		final Object removed = palElements.get(index);
		palElements.remove(index);
		super.fireIntervalRemoved(this, index, index);

		return removed;
	}

	/**
	 * Removes the element at the specified position in this list. Returns the
	 * element that was removed from the list.
	 * <p>
	 * Throws an <code>ArrayIndexOutOfBoundsException</code> if the index is
	 * out of range <code>(index &lt; 0 || index &gt;= getSize())</code>.
	 * 
	 * @param removed element.
	 */
	public final Object remove(final Object obj) {

		final int index = palElements.indexOf(obj);
		if (index >= 0) {

			palElements.remove(index);
			super.fireIntervalRemoved(this, index, index);
			
			return obj;
		}

		return null;
	}
	
	/**
	 * Removes all of the elements from this list. The list will be
	 * empty after this call returns (unless it throws an exception).
	 */
	public final void removeAll() {

		final int index = this.getSize() - 1;
		palElements = new ArrayListLight<Object>();
		if (index >= 0) {

			super.fireIntervalRemoved(this, 0, index);
		}
	}
	
	/**
     * Sorts the elements from this list into ascending order, according to
     * the <i>natural ordering</i> of its elements.  All elements in the list
     * must implement the <tt>Comparable</tt> interface.  Furthermore, all
     * elements in the list must be <i>mutually comparable</i> (that is,
     * <tt>e1.compareTo(e2)</tt> must not throw a <tt>ClassCastException</tt>
     * for any elements <tt>e1</tt> and <tt>e2</tt> in the array).<p>
     *
     * This sort is guaranteed to be <i>stable</i>: equal elements will
     * not be reordered as a result of the sort.<p>
     *
     * The sorting algorithm is a modified mergesort (in which the merge is
     * omitted if the highest element in the low sublist is less than the
     * lowest element in the high sublist). This algorithm offers guaranteed
     * n*log(n) performance.
     */
	public final void sort() {
		
		Object[] o = palElements.toArray();
		Arrays.sort(o);
		
		this.removeAll();
		this.add(o);
	}

	/**
	 * Returns a <code>string-representation</code> of this collection. The
	 * string representation consists of a list of the collection's elements
	 * in the  order they are returned by its iterator, enclosed in square
	 * brackets <code>("[]")</code>. Adjacent elements are separated by the
	 * characters <code>", "</code> (comma and space). Elements are converted to
	 * strings as by <code>String.valueOf(Object)</code>.<p>
	 * 
	 * This implementation creates an empty string buffer, appends a left
	 * square bracket, and iterates over the collection appending the string
	 * representation of each element in turn. After appending each element
	 * except the last, the string <code>", "</code> is appended. Finally a
	 * right bracket is appended. A string is obtained from the string
	 * buffer, and returned. 
	 * 
	 * @return a <code>string-representation</code> of this collection.
	 */
	public final String toString() { return palElements.toString(); }
}
