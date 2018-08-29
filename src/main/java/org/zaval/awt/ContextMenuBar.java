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

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MenuBar;
import java.awt.MenuComponent;
import java.awt.Rectangle;

import org.zaval.awt.event.Listener;
import org.zaval.awt.event.ListenerSupport;

public class ContextMenuBar extends MenuBar {
	public final static int EV_MENU_REDRAW = 0xFFF0;
	public final static int EV_MENU_EXIT = EV_MENU_REDRAW + 1;
	public final static int EV_MENU_ENTER = EV_MENU_REDRAW + 2;
	public final static int EV_MENU_REDRAWALL = EV_MENU_REDRAW + 3;

	public Container parent = null;
	ContextMenu act_menu = null;
	boolean first = true;
	ListenerSupport support = new ListenerSupport();

	public ContextMenuBar(Container a_parent) {
		parent = a_parent;
	}

	public ContextMenuBar() {
		super();
	}

	public void addListener(Listener l) {
		support.addListener(l);
	}

	public void removeListener(Listener l) {
		support.removeListener(l);
	}

	public void setParent(Container a_parent) {
		parent = a_parent;
	}

	public Dimension getParentSize() {
		if (parent == null) {
			return null;
		}
		else {
			return parent.size();
		}
	}

	public void add(ContextMenu cm) {
		cm.index = countMenus();
		super.add(cm);
	}

	@Override
	public void remove(int index) {
		super.remove(index);
	}

	@Override
	public void remove(MenuComponent mc) {
		super.remove(mc);
	}

	public boolean init(int index, int x, int y) {
		act_menu = get(index);
		if ((act_menu == null) || (!act_menu.isEnabled())) {
			act_menu = null;
			return false;
		}
		act_menu.setPos(x, y);
		first = true;
		return true;
	}

	public void paint(Graphics gr) {
		if (act_menu == null) {
			return;
		}
		if (first) {
			first = false;
			act_menu.paint(gr);
		}
		else {
			act_menu.paintPart(gr);
		}
	}

	public boolean isRedraw() {
		return first;
	}

	public boolean isActive() {
		return (parent != null) && (act_menu != null);
	}

	public ContextMenu get(int index) {
		return (ContextMenu) super.getMenu(index);
	}

	public void disable(int index) {
		get(index).disable();
	}

	public void enable(int index) {
		get(index).enable();
	}

	public void repaint() {
		if (parent == null) {
			return;
		}
		Graphics gr = parent.getGraphics();
		paint(gr);
		gr.dispose();
	}

	public void repaintAll() {
		first = true;
		if ((act_menu != null) && (parent != null)) {
			Rectangle r = act_menu.getBounds();
			parent.repaint(r.x, r.y, r.width, r.height);
		}
		repaint();
	}

	public void sendEvent(java.awt.Event evt) {
		if ((parent == null) || (act_menu == null)) {
			return;
		}
		switch (evt.id) {
			case ContextMenuBar.EV_MENU_REDRAW: {
				repaint();
			}
				break;

			case ContextMenuBar.EV_MENU_REDRAWALL: {
				repaintAll();
			}
				break;
			case ContextMenuBar.EV_MENU_EXIT: {
				repaintAll();
				act_menu = null;
			}
				break;
			case ContextMenuBar.EV_MENU_ENTER: {
				repaintAll();
				act_menu = null;
				evt.id = java.awt.Event.ACTION_EVENT;
				if (support.size() > 0) {
					org.zaval.awt.event.Event e = new org.zaval.awt.event.Event(this, EV_MENU_ENTER);
					e.put("event", evt);
					support.perform(e);
				}
				else {
					parent.postEvent(evt);
				}
			}
		}
	}

	public boolean handleEvent(java.awt.Event evt) {
		if ((act_menu == null) || (!act_menu.isEnabled())) {
			return false;
		}
		return act_menu.handleEvent(evt);
	}
}
