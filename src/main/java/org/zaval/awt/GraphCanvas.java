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

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class GraphCanvas extends Canvas {
	private int h, w;

	public GraphCanvas(int width, int height) {
		h = height;
		w = width;
		resize(width, height);
		setBackground(Color.black);
	}

	@Override
	public Dimension preferredSize() {
		return new Dimension(w, h);
	}

	@Override
	public Dimension minimumSize() {
		return new Dimension(w, h);
	}

	public void put(int persent) {
	}

	@Override
	public void paint(Graphics gr) {
		int w = size().width;
		int h = size().height;

		int count = w / 10;
		gr.setColor(Color.green);
		for (int i = 1; i < count; i++) {
			gr.drawLine(i * 10, 0, i * 10, h);
		}

		count = h / 10;
		for (int i = 1; i < count; i++) {
			gr.drawLine(0, i * 10, w, i * 10);
		}
	}

}
