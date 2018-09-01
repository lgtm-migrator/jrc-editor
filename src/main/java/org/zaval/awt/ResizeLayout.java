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
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.Rectangle;

public class ResizeLayout implements LayoutManager {
	private int fix = -1;

	public ResizeLayout() {
	}

	void setSeparator(int sef, Container parent) {
		fix = sef;
		layoutAll(parent);
	}

	private void layoutAll(Container c) {
		int i;
		LayoutManager l = c.getLayout();
		if (l != null) {
			l.layoutContainer(c);
		}
		Component[] o = c.getComponents();
		c.repaint();

		for (i = 0; i < o.length; ++i) {
			if (o[i] instanceof Container) {
				layoutAll((Container) o[i]);
			}
		}
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void removeLayoutComponent(Component comp) {
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		int i;
		Panel left = null, right = null;
		Component[] obj = parent.getComponents();
		for (i = 0; i < obj.length; ++i) {
			if (obj[i] instanceof Panel) {
				if (left == null) {
					left = (Panel) obj[i];
				}
				else if (right == null) {
					right = (Panel) obj[i];
				}
				else {
					break;
				}
			}
		}

		Dimension d1 = left.preferredSize();
		if (fix != -1) {
			d1.width = fix;
		}
		Dimension d2 = right.preferredSize();
		return new Dimension(d1.width + d2.width + 5, Math.max(d1.height, d2.height));
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		int i;
		Panel left = null, right = null;
		Component[] obj = parent.getComponents();
		for (i = 0; i < obj.length; ++i) {
			if (obj[i] instanceof Panel) {
				if (left == null) {
					left = (Panel) obj[i];
				}
				else if (right == null) {
					right = (Panel) obj[i];
				}
				else {
					break;
				}
			}
		}

		Dimension d1 = left.minimumSize();
		if (fix != -1) {
			d1.width = fix;
		}
		Dimension d2 = right.minimumSize();
		return new Dimension(d1.width + d2.width + 5, Math.max(d1.height, d2.height));
	}

	@Override
	public void layoutContainer(Container parent) {
		int i;
		Panel left = null, right = null;
		Component rl = null;
		Component[] obj = parent.getComponents();
		for (i = 0; i < obj.length; ++i) {
			if (obj[i] instanceof Panel) {
				if (left == null) {
					left = (Panel) obj[i];
				}
				else if (right == null) {
					right = (Panel) obj[i];
				}
				else {
					break;
				}
			}
			else if (rl == null) {
				rl = obj[i];
			}
		}

		Rectangle r = parent.bounds();
		int wx1 = fix == -1 ? left.preferredSize().width : fix;
		int wx2 = r.width - wx1 - 15;
		left.reshape(5, 5, wx1, r.height - 10);
		right.reshape(10 + wx1, 5, wx2, r.height - 10);
		rl.reshape(5 + wx1, 5, 5, r.height - 10);
	}
}
