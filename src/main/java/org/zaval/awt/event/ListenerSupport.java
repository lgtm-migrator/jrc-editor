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

package org.zaval.awt.event;

import java.util.Vector;

public class ListenerSupport {
	protected Vector v = new Vector();

	public void addListener(Listener l) {
		if (v.contains(l)) {
			return;
		}
		v.addElement(l);
	}

	public void removeListener(Listener l) {
		if (!v.contains(l)) {
			return;
		}
		v.removeElement(l);
	}

	public int size() {
		return v.size();
	}

	public Vector getListeners() {
		return v;
	}

	public boolean perform(Event e) {
		for (int i = 0; i < v.size(); i++) {
			Listener l = (Listener) v.elementAt(i);
			if (l.eventOccured(e)) {
				return true;
			}
		}
		return false;
	}
}
