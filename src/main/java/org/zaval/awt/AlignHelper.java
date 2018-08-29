/**
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
import java.awt.Rectangle;

public class AlignHelper {
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

	protected static boolean check(Insets i) {
		if (((i.top > 0) && (i.bottom > 0)) || ((i.left > 0) && (i.right > 0))) {
			return false;
		}

		if ((i.top < 0) || (i.bottom < 0) || (i.left < 0) || (i.right < 0)) {
			return false;
		}
		return true;
	}

	public static boolean isBelongArea(AlignArea a, int x, int y) {
		Rectangle r = a.getAlignRectangle();
		if (r == null) {
			return false;
		}

		switch (a.getMode()) {
			case AlignArea.INSIDE:
				return r.inside(x, y);
			case AlignArea.OUTSIDE:
				return !r.inside(x, y);
		}
		return false;
	}

	public static Insets getPointAlignInsets(AlignArea a, int x, int y) {
		if (!isBelongArea(a, x, y)) {
			return null;
		}

		if (a.getMode() == AlignArea.INSIDE) {
			return a.getAlignInsets();
		}
		Rectangle r = a.getAlignRectangle();
		int maxx = r.x + r.width;
		int maxy = r.y + r.height;
		Insets code = new Insets(0, 0, 0, 0);

		if (x > maxx) {
			code.right++;
		}
		else {
			if (x < r.x) {
				code.left++;
			}
		}

		if (y > maxy) {
			code.bottom++;
		}
		else {
			if (y < r.y) {
				code.top++;
			}
		}

		return code;
	}
}
