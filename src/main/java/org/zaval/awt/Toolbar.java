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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Toolbar extends Panel implements LayoutManager {
	private final List<Serializable> v = new ArrayList<>();

	public Toolbar() {
		super();
		setLayout(this);
	}

	public void add(int id, Component button) {
		add(button);
		while (v.size() <= id) {
			v.add("");
		}
		v.set(id, button);
	}

	@Override
	public boolean action(Event e, Object o) {
		if (e.target instanceof SpeedButton) {
			int id = v.indexOf(e.target);
			getParent().postEvent(new Event(this, Event.ACTION_EVENT, Integer.toString(id)));
			return true;
		}
		return false;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void removeLayoutComponent(Component comp) {
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return preferredLayoutSize(parent);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		int maxx = 0;
		int maxy = 0;
		for (Component c : getComponents()) {
			Dimension d = c.preferredSize();
			maxx += /* 1 + */ d.width;
			maxy = Math.max(maxy, d.height);
		}
		return new Dimension(maxx/*-1*/, maxy);
	}

	@Override
	public void layoutContainer(Container parent) {
		Dimension real = parent.size();
		preferredLayoutSize(parent);
		Insets p_i = parent.insets();

		if ((real.width == 0) || (real.height == 0)) {
			return;
		}

		int x = p_i.left;
		int y = p_i.top;

		for (Component c : getComponents()) {
			Dimension d = c.preferredSize();
			int w = d.width;
			int h = real.height;

			c.resize(w, h);
			c.move(x + p_i.left, y + p_i.top);
			x += w /* + 1 */;
		}
	}

	public void setEnabled(int j, boolean state) {
		Component c = (Component) v.get(j);
		if (state) {
			c.enable();
		}
		else {
			c.disable();
		}
	}
}
