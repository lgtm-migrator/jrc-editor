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

package org.zaval.tools.i18n.translator;

import java.io.IOException;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Hashtable;

import org.zaval.xml.XmlElement;
import org.zaval.xml.XmlParseException;

class XmlReader {
	private final XmlElement xml;

	public XmlReader(String body) throws IOException, XmlParseException {
		xml = new XmlElement();
		xml.parse(new StringReader(body));
	}

	public Hashtable<String, String> getTable() {
		Hashtable<String, String> ask = new Hashtable<>();
		Enumeration<XmlElement> en = xml.enumerateChildren();
		while (en.hasMoreElements()) {
			XmlElement child = en.nextElement();
			getTable(ask, child, "");
		}
		return ask;
	}

	private void getTable(Hashtable<String, String> place, XmlElement root, String prefix) {
		String xmap = (String) root.getAttribute("lang");
		if (xmap == null) {
			xmap = (String) root.getAttribute("name");
		}
		if (xmap == null) {
			xmap = root.getName();
		}
		String name = prefix + xmap + "!";

		Enumeration<XmlElement> en;
		if (root.getContent() != null) {
			place.put(prefix + xmap, root.getContent());
		}

		en = root.enumerateChildren();
		while (en.hasMoreElements()) {
			XmlElement child = en.nextElement();
			getTable(place, child, name);
		}
	}
}
