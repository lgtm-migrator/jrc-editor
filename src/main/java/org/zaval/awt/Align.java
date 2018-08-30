/*
 * Copyright (C) 2001-2002  Zaval Creative Engineering Group (http://www.zaval.org)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * (version 2) as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.zaval.awt;

import java.awt.Insets;

public class Align implements AlignConstants {
	private int align = CENTER;
	private String alignStr = STR_CENTER;
	private Insets code = new Insets(0, 0, 0, 0);

	/**
	 * Constructs a new Align.
	 */
	public Align() {
	}

	/**
	 * Sets the arrangement type as int value.
	 * @param a new int value of the align property.
	 */
	public void setAlign(int a) {
		if (align == a) {
			return;
		}
		code = align2insets(a);
		alignStr = align2str(a);
		align = a;
	}

	/**
	 * Returns the align type as int value.
	 * @return int value of the align property.
	 */
	public int getAlign() {
		return align;
	}

	/**
	 * Sets the align type as Insets value.
	 * @param i new java.awt.Insets value of the align property.
	 */
	public void setAlignInsets(Insets i) {
		if (i == code) {
			return;
		}
		align = insets2align(i);
	}

	/**
	 * Returns the align type as Insets value.
	 * @return  java.awt.Insets value of the align property.
	 */
	public Insets getAlignInsets() {
		return code;
	}

	/**
	 * Returns the align type as String value.
	 * @return  String value of the align property.
	 */
	public String getAlignString() {
		return alignStr;
	}

	public static Insets align2insets(int a) {
		Insets i = new Insets(-1, -1, -1, -1);
		i.top = ((a & AlignConstants.TOP) > 0) ? 1 : 0;
		i.left = ((a & AlignConstants.LEFT) > 0) ? 1 : 0;
		i.bottom = ((a & AlignConstants.BOTTOM) > 0) ? 1 : 0;
		i.right = ((a & AlignConstants.RIGHT) > 0) ? 1 : 0;
		if (!check(i)) {
			return null;
		}
		return i;
	}

	public static String align2str(int a) {
		String r = null;
		switch (a) {
			case AlignConstants.TOP:
				r = AlignConstants.STR_TOP;
				break;
			case AlignConstants.BOTTOM:
				r = AlignConstants.STR_BOTTOM;
				break;
			case AlignConstants.LEFT:
				r = AlignConstants.STR_LEFT;
				break;
			case AlignConstants.RIGHT:
				r = AlignConstants.STR_RIGHT;
				break;
			case AlignConstants.TLEFT:
				r = AlignConstants.STR_TLEFT;
				break;
			case AlignConstants.TRIGHT:
				r = AlignConstants.STR_TRIGHT;
				break;
			case AlignConstants.BRIGHT:
				r = AlignConstants.STR_BRIGHT;
				break;
			case AlignConstants.BLEFT:
				r = AlignConstants.STR_BLEFT;
				break;
			case AlignConstants.CENTER:
				r = AlignConstants.STR_CENTER;
				break;
		}
		return r;
	}

	public static int insets2align(Insets i) {
		if ((i == null) || !check(i)) {
			return -1;
		}
		int a = 0;
		a |= ((i.top > 0) ? AlignConstants.TOP : 0);
		a |= ((i.left > 0) ? AlignConstants.LEFT : 0);
		a |= ((i.bottom > 0) ? AlignConstants.BOTTOM : 0);
		a |= ((i.right > 0) ? AlignConstants.RIGHT : 0);
		return a;
	}

	protected static boolean check(Insets i) {
		if (((i.top > 0) && (i.bottom > 0)) || ((i.left > 0) && (i.right > 0))) {
			return false;
		}

		return (i.top >= 0) && (i.bottom >= 0) && (i.left >= 0) && (i.right >= 0);
	}
}
