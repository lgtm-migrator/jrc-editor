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

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Polygon;

class NotebookPage {
	String label;
	boolean hidden;
	Component comp;
	Color color;
	Polygon poly;
	String name;
	Image img;
	ImageResolver imgres;

	NotebookPage() {
	}

	public void setImage(String name) {
		if ((name == null) || (imgres == null)) {
			img = null;
		}
		else {
			img = imgres.getImage(name);
		}
	}

	public Image getImage() {
		return img;
	}

	public void setImageResolver(ImageResolver r) {
		imgres = r;
	}
}
