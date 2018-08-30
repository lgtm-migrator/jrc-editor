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

import java.awt.Color;
import java.awt.Graphics;

public class IERadioButton extends BaseCheckbox {
	public IERadioButton() {
	}

	public IERadioButton(boolean state) {
		this(null, state);
	}

	public IERadioButton(String label, boolean state) {
		setState(state);
		setLabel(label);
	}

	@Override
	public void paint(Graphics g, int x, int y, int width, int height) {
		int xx = x + width;
		int yy = y + height;

		g.setColor(Color.lightGray);
		if (isEnabled()) {
			if (!mouse_down) {
				g.setColor(Color.white);
			}
		}

		g.fillOval(x, y, width, height);
		g.setColor(Color.white);
		g.drawLine(x + 4, yy - 1, xx - 5, yy - 1);
		g.drawLine(xx - 1, y + 4, xx - 1, yy - 5);
		g.drawLine(x + 2, yy - 2, xx - 3, yy - 2);
		g.drawLine(xx - 2, y + 2, xx - 2, yy - 3);
		g.setColor(Color.gray);
		g.drawLine(x, y + 4, x, yy - 5);
		g.drawLine(x + 1, y + 2, x + 1, yy - 3);
		g.drawLine(x + 4, y, xx - 5, y);
		g.drawLine(x + 2, y + 1, xx - 3, y + 1);
		g.setColor(Color.black);
		g.drawLine(x + 1, y + 4, x + 1, yy - 5);
		g.drawLine(x + 2, y + 2, x + 2, y + 3);
		g.drawLine(x + 2, yy - 4, x + 2, yy - 4);
		g.drawLine(x + 3, y + 2, x + 3, y + 2);
		g.drawLine(x + 4, y + 1, xx - 5, y + 1);
		g.drawLine(xx - 4, y + 2, xx - 3, y + 2);
		g.setColor(Color.lightGray);
		g.drawLine(x + 2, yy - 3, x + 3, yy - 3);
		g.drawLine(x + 4, yy - 2, xx - 5, yy - 2);
		g.drawLine(xx - 4, yy - 3, xx - 3, yy - 3);
		g.drawLine(xx - 3, yy - 4, xx - 3, yy - 4);
		g.drawLine(xx - 2, y + 4, xx - 2, yy - 5);
		g.drawLine(xx - 3, y + 3, xx - 3, y + 3);

		if (isEnabled()) {
			g.setColor(Color.black);
		}
		else {
			g.setColor(Color.gray);
		}

		if (state) {
			g.fillOval(x + 4, y + 4, 4, 4);
		}
	}

	@Override
	protected boolean condition() {
		return !getState();
	}
}
