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

import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Panel;
import java.awt.Toolkit;
import java.util.Vector;

public class IEChoice extends Panel {
	private Choice ch;
	private boolean fakeAdded = false;

	private Vector ids;
	private Vector items;
	private String lastval = null;

	public Vector getI1() {
		return items;
	}

	public Vector getI2() {
		return ids;
	}

	public void setItems(Vector items, Vector ids) {
		int i, k = ids.size();
		this.ids = ids;
		this.items = items;

		Choice c = new Choice();
		for (i = 0; i < k; ++i) {
			c.addItem((String) items.elementAt(i));
		}
		for (; i < 2; ++i) {
			c.addItem(" ");
		}
		setChoice(c);
	}

	public void select(String value) {
		if (value == null) {
			value = lastval;
		}
		if (value == null) {
			return;
		}
		select(ids.indexOf(lastval = value));
	}

	public String getValue() {
		if (lastval == null) {
			try {
				int x = ch.getSelectedIndex();
				String s = (String) ids.elementAt(x);
				return lastval = s;
			}
			catch (Exception e) {
				return null;
			}
		}
		return lastval;
	}

	public IEChoice(Choice c) {
		super();
		ch = c;
		setLayout(new FitLayout(0, 0, 0, 0));
		add(this.ch);
	}

	@Override
	public void resize(int wx, int wy) {
		Dimension d = ch.preferredSize();
		if (d.height == 0) {
			Font font = getFont();
			if (font == null) {
				font = getParent().getFont();
			}
			if (font != null) {
				FontMetrics fm = getFontMetrics(font);
				if (fm == null) {
					fm = Toolkit.getDefaultToolkit().getFontMetrics(font);
				}
				d.height = fm.getHeight() + 10;
			}
			else {
				d.height = wy;
			}
		}
		super.resize(wx, d.height);
	}

	public Choice getChoice() {
		return ch;
	}

	private void setChoice(Choice x) {
		String osName = System.getProperty("os.name");
		boolean solaris = ((osName != null) && (osName.equalsIgnoreCase("Solaris") || osName.equalsIgnoreCase("Linux")));

		Color c1 = ch.getBackground();
		Color c2 = ch.getForeground();
		Font f = ch.getFont();

		Container parent = getParent();
		if (solaris && (parent != null)) {
			parent.remove(this);
		}
		removeAll();
		add(x);

		x.setBackground(c1);
		x.setForeground(c2);
		x.setFont(f);
		if (isEnabled()) {
			x.enable();
		}
		else {
			x.disable();
		}

		this.ch = x;
		if (solaris && (parent != null)) {
			parent.add(this);
			addNotify();
		}

		((Component) this).invalidate();
		((Component) this).validate();
		this.ch.requestFocus();
	}

	@Override
	public void enable() {
		ch.enable();
		super.enable();
	}

	@Override
	public void disable() {
		ch.disable();
		super.disable();
	}

	private final static int acceptE[] = { Event.LIST_SELECT, Event.ACTION_EVENT };

	@Override
	public boolean handleEvent(Event e) {
		if ((e.target != this) && (e.target != ch)) {
			return super.handleEvent(e);
		}
		if (isSelectionEvent(e)) {
			try {
				String s = (String) ids.elementAt(ch.getSelectedIndex());
				sendSelectionEvent(s);
			}
			catch (Exception eflt) {
			}
			return true;
		}
		return super.handleEvent(e);
	}

	@Override
	public boolean keyDown(Event e, int key) {
		char c = (char) key;
		if (Character.isLetter(c)) {
			c = Character.toLowerCase(c);
			int size = ch.countItems();
			for (int i = 0; i < size; i++) {
				StringBuilder item = new StringBuilder(ch.getItem(i));
				if (Character.toLowerCase(item.charAt(0)) == c) {
					if (ch.getSelectedIndex() != i) {
						ch.select(i);
					}
					return true;
				}
			}
		}

		if (key == (char) 0x1B) {
			try {
				ch.select(ids.indexOf(lastval));
			}
			catch (Exception eee) {
			}
			return false;
		}

		if ((key == '\n') || (key == '\t')) {
			try {
				String s = (String) ids.elementAt(ch.getSelectedIndex());
				if (s.equals(lastval)) {
					return false;
				}
				sendSelectionEvent(s);
			}
			catch (Exception eee) {
			}
			return false;
		}

		if (key == Event.UP) {
			int z = ch.getSelectedIndex() - 1;
			if (z < 0) {
				return true;
			}
			ch.select(z);
			return true;
		}

		if (key == Event.DOWN) {
			int z = ch.getSelectedIndex() + 1;
			if (z >= ch.countItems()) {
				return true;
			}
			ch.select(z);
			return true;
		}

		return super.keyDown(e, key);
	}

	@Override
	public void requestFocus() {
		ch.requestFocus();
	}

	@Override
	public boolean lostFocus(Event e, Object o) {
		if ((e.target == this.ch) && (ids != null) && (lastval != null)) {
			try {
				String s = (String) ids.elementAt(ch.getSelectedIndex());
				if (lastval.equals(s)) {
					return true;
				}
				sendSelectionEvent(s);
			}
			catch (Exception eee) {
			}
			return false;
		}
		else {
			return e.target == this;
		}
	}

	@Override
	public boolean gotFocus(Event e, Object o) {
		return ids == null;
	}

	private boolean isSelectionEvent(Event e) {
		if (e.target == ch) {
			for (int element : acceptE) {
				if (element == e.id) {
					return true;
				}
			}
		}
		return false;
	}

	private void sendSelectionEvent(String s) {
		if (!s.equals(lastval)) {
			lastval = s;
			getParent().postEvent(new Event(this, Event.ACTION_EVENT, s));
		}
	}

	private void fakeFix() {
		if (fakeAdded) {
			Choice x = new Choice();
			Choice v = ch;
			int j, k = v.countItems() - 1;
			for (j = 0; j < k; ++j) {
				String s = v.getItem(j);
				if ((j == (k - 1)) && (s.trim().length() == 0)) {
					break;
				}
				x.addItem(s);
			}
			fakeAdded = false;
			setChoice(x);
			select(lastval);
		}
	}

	@Override
	public void addNotify() {
		try {
			super.addNotify();
		}
		catch (Exception efck) {
		}
	}

	public void select(int i) {
		if ((i < 0) && !checkIeHack()) {
			Choice x = getChoice();
			int j = 0, k = x.countItems();
			for (j = 0; j < k; ++j) {
				if (x.getItem(j).trim().length() == 0) {
					x.select(j);
					return;
				}
			}
			x.addItem(" ");
			x.select(" ");
			fakeAdded = true;
			return;
		}
		fakeFix();
		try {
			getChoice().select(i);
		}
		catch (Exception efck) {
			System.err.println("SEL Error: " + efck + " for " + getChoice());
			efck.printStackTrace();
		}
	}

	private static boolean checkIeHack() {
		String jver = "1.0.2.";
		String jven = "Sun";
		try {
			jver = System.getProperty("java.version");
		}
		catch (Throwable t) {
		}
		try {
			jven = System.getProperty("java.vendor");
		}
		catch (Throwable t) {
		}

		return !jver.startsWith("1.0") && jven.startsWith("Microsoft");
	}
}
