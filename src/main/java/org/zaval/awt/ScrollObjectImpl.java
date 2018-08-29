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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Point;

public class ScrollObjectImpl extends Panel implements ScrollObject {
	protected Component target;

	public ScrollObjectImpl(Component c) {
		target = c;
		setLayout(new BorderLayout());
		add("Center", target);
	}

	@Override
	public Point getSOLocation() {
		return location();
	}

	@Override
	public void setSOLocation(int x, int y) {
		move(x, y);
	}

	@Override
	public Dimension getSOSize() {
		return preferredSize();
	}

	@Override
	public Component getScrollComponent() {
		return this;
	}
}
