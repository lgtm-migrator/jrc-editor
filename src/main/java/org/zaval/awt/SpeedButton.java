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

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;

import org.zaval.awt.image.BoxButtonFilter;
import org.zaval.awt.image.ButtonImageFilter;

public class SpeedButton extends Canvas {
	private static final int FREE = 1;
	private static final int UP = 2;
	private static final int DOWN = 3;

	private Image normImg, upImg, disImg, downImg;

	private int w, h, state = FREE;

	public SpeedButton(Image src) {
		int border = -5;
		int light = 30;
		ButtonImageFilter filt = new BoxButtonFilter();
		w = src.getWidth(this);
		h = src.getHeight(this);
		border = -border;
		border = (w * border) / 100;
		if (border < 2) {
			border = 2;
		}
		init(src, getFilter(src, filt, light, border, false), getFilter(src, filt, -light, border, true),
			getFilter(src, filt, -Math.abs(light), 0, false));
	}

	private void init(Image src, Image up, Image down, Image dis) {
		normImg = src;
		upImg = up;
		disImg = dis;
		downImg = down;
	}

	private Image getFilter(Image src, ButtonImageFilter filt, int light, int border, boolean b) {
		filt = (ButtonImageFilter) filt.clone();
		filt.setup(light, border, w, h, b);

		ImageProducer prod = src.getSource();
		ImageProducer ip = new FilteredImageSource(prod, filt);
		return createImage(ip);
	}

	private void setState(int s) {
		state = s;
		repaint();
	}

	@Override
	public Dimension preferredSize() {
		if (w < 0) {
			w = 0;
		}
		if (h < 0) {
			h = 0;
		}
		return new Dimension(w, h);
	}

	@Override
	public void paint(Graphics gr) {
		Dimension d = size();
		int ww = d.width;
		int hh = d.height;

		if ((disImg != null) && !isEnabled()) {
			gr.drawImage(disImg, 0, 0, ww, hh, this);
		}
		else if ((state == FREE) && (normImg != null)) {
			gr.drawImage(normImg, 0, 0, ww, hh, this);
		}
		else if ((state == DOWN) && (downImg != null)) {
			gr.drawImage(downImg, 0, 0, ww, hh, this);
		}
		else if ((state == UP) && (upImg != null)) {
			gr.drawImage(upImg, 0, 0, ww, hh, this);
		}
	}

	@Override
	public boolean mouseUp(Event ev, int x, int y) {
		if (!isEnabled() || (state == FREE)) {
			return true;
		}
		setState(UP);
		getParent().postEvent(new Event(this, Event.ACTION_EVENT, null));
		return true;
	}

	@Override
	public boolean mouseDown(Event ev, int x, int y) {
		if (!isEnabled()) {
			return true;
		}
		setState(DOWN);
		return true;
	}

	@Override
	public boolean mouseEnter(Event ev, int x, int y) {
		setState(UP);
		return true;
	}

	@Override
	public boolean mouseExit(Event ev, int x, int y) {
		setState(FREE);
		return true;
	}
}
