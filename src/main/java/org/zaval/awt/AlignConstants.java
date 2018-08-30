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

public interface AlignConstants {
	int LEFT = 0x1;
	int RIGHT = 0x2;
	int TOP = 0x4;
	int BOTTOM = 0x8;
	int FIT = 0x10;
	int JUSTIFY = 0x20;

	int TLEFT = LEFT | TOP;
	int TRIGHT = RIGHT | TOP;
	int BLEFT = LEFT | BOTTOM;
	int BRIGHT = RIGHT | BOTTOM;
	int CENTER = 0;

	String STR_LEFT = "left";
	String STR_RIGHT = "right";
	String STR_TOP = "top";
	String STR_BOTTOM = "bottom";
	String STR_TLEFT = "topLeft";
	String STR_TRIGHT = "topRight";
	String STR_BLEFT = "bottomLeft";
	String STR_BRIGHT = "bottomRight";
	String STR_CENTER = "center";
}
