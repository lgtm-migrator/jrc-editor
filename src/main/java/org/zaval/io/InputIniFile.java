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

package org.zaval.io;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class InputIniFile extends Object {
	private Hashtable hash;
	private DataInputStream in;
	private String name;

	public Hashtable getTable() {
		return hash;
	}

	public InputIniFile(String name) throws IOException {
		this.name = name;
		in = new DataInputStream(new FileInputStream(name));
		hash = new Hashtable();
		load();
	}

	public InputIniFile(InputStream in) throws IOException {
		this.name = in.toString();
		this.in = new DataInputStream(in);
		hash = new Hashtable();
		load();
	}

	public synchronized String getString(String key) throws IOException {
		return (String) hash.get(key);
	}

	public int getInt(String key) throws IOException {
		try {
			return Integer.parseInt(getString(key));
		}
		catch (NumberFormatException e) {
			return 0;
		}
	}

	public boolean getBoolean(String key) throws IOException {
		String s = getString(key);
		if ("True".equalsIgnoreCase(s)) {
			return true;
		}
		if ("On".equalsIgnoreCase(s)) {
			return true;
		}
		if ("1".equals(s)) {
			return true;
		}
		return false;
	}

	public synchronized void close() throws IOException {
		if (in != null) {
			in.close();
		}
		in = null;
	}

	private String postponed = null;

	private String readLine(DataInputStream in) throws IOException {
		String x = postponed == null ? in.readLine() : postponed;
		postponed = null;
		if (x == null) {
			return x;
		}
		x = x.trim();

		while (x.endsWith("\\")) {
			String v = in.readLine();
			if (v == null) {
				break;
			}
			if (!v.startsWith(" ")) {
				postponed = v;
				break;
			}
			int i = v.indexOf('#');
			if (i > 0) {
				if (v.endsWith("\\")) {
					v = v.substring(0, i) + " \\";
				}
				else {
					v = v.substring(0, i);
				}
			}
			x = x.substring(0, x.length() - 1) + v.trim();
		}

		int j = x.indexOf('#');
		if (j == 0) {
			return "";
		}
		if (j > 0) {
			x = x.substring(0, j);
		}
		return x;
	}

	private void load() throws IOException {
		String line = null;
		while ((line = readLine(in)) != null) {
			StringTokenizer st = new StringTokenizer(line, "=", true); // key = value
			if (st.countTokens() < 3) {
				continue; // syntax error, ignored
			}
			String key = st.nextToken().trim();
			st.nextToken(); // '='
			String value = st.nextToken("").trim();
			hash.put(key, value);
		}
	}
}
