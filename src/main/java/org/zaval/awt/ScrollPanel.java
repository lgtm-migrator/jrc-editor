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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.Scrollbar;

public class ScrollPanel extends Panel implements ScrollArea, LayoutManager {
	private Scrollbar hBar = new Scrollbar(Scrollbar.HORIZONTAL);
	private Scrollbar vBar = new Scrollbar(Scrollbar.VERTICAL);
	private Panel mainPanel = new Panel();
	private ScrollLayout layout = new ScrollLayout();
	private ScrollController metrics;

	public ScrollPanel(ScrollObject obj) {
		init(new ScrollController(this, obj));
	}

	public ScrollPanel(ScrollController m) {
		init(m);
	}

	protected void init(ScrollController m) {
		metrics = m;
		if (metrics == null) {
			metrics = new ScrollController(this, null);
		}
		if (metrics.getScrollArea() == null) {
			metrics.setScrollArea(this);
		}

		setLayout(layout);
		add("Center", mainPanel);
		add("East", vBar);
		add("South", hBar);
		add("Stubb", new StubbComponent());

		ScrollObject obj = metrics.getScrollObject();
		init(mainPanel, obj.getScrollComponent());
	}

	protected void init(Container p, Component c) {
		p.setLayout(this);
		p.add(c);
	}

	@Override
	public boolean handleEvent(Event e) {
		if (metrics.handle(e, 1)) {
			return true;
		}
		return super.handleEvent(e);
	}

	@Override
	public void invalidate() {
		super.invalidate();
		if (metrics != null) {
			metrics.invalidate();
		}
	}

	@Override
	public void reshape(int x, int y, int w, int h) {
		super.reshape(x, y, w, h);
		if (metrics != null) {
			metrics.invalidate();
		}
		invalidate();
		recalc();
	}

	@Override
	public void layout() {
		recalc();
		super.layout();
	}

	public void recalc() {
		if (!metrics.isValid()) {
			metrics.validate();
		}

		ScrollObject sobj = metrics.getScrollObject();
		int maxV = metrics.getMaxVerScroll();
		int maxH = metrics.getMaxHorScroll();

		if (maxV < 0) {
			vBar.hide();
		}
		else {
			vBar.setValues(0, 0, 0, maxV);
			sobj.setSOLocation(0, 0);
			vBar.show();
		}

		if (maxH < 0) {
			hBar.hide();
		}
		else {
			hBar.setValues(0, 0, 0, maxH);
			sobj.setSOLocation(0, 0);
			hBar.show();
		}

		sobj.setSOLocation(0, 0);
	}

	@Override
	public Scrollbar getVBar() {
		return vBar;
	}

	@Override
	public Scrollbar getHBar() {
		return hBar;
	}

	@Override
	public Dimension getSASize() {
		return size();
	}

	protected ScrollController getMetrics() {
		return metrics;
	}

	@Override
	public void addLayoutComponent(String s, Component c) {
	}

	@Override
	public void removeLayoutComponent(Component c) {
	}

	@Override
	public Dimension minimumLayoutSize(Container target) {
		return preferredLayoutSize(target);
	}

	@Override
	public Dimension preferredLayoutSize(Container target) {
		Component[] c = target.getComponents();
		if (c.length > 0) {
			return c[0].preferredSize();
		}
		return new Dimension(0, 0);
	}

	@Override
	public void layoutContainer(Container target) {
		Component[] c = target.getComponents();
		ScrollArea a = metrics.getScrollArea();
		Dimension td = target.size();
		for (Component element : c) {
			Dimension d = element.preferredSize();
			if (!a.getVBar().isVisible()) {
				d.height = td.height;
			}
			if (!a.getHBar().isVisible()) {
				d.width = td.width;
			}

			element.resize(d.width, d.height);
		}
	}
}
