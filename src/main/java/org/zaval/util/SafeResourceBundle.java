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

package org.zaval.util;

import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;

public class SafeResourceBundle {
	protected ResourceBundle rb = null;

	public static final String FAILURE_STRING = "?????";
	public static final String START_VAR = "[%";
	public static final String FINISH_VAR = "%]";

	public SafeResourceBundle(String resName, Locale loc) {
		try {
			if (loc == null) {
				Locale saved = Locale.getDefault();
				Locale.setDefault(new Locale("en", "US"));
				rb = ResourceBundle.getBundle(resName);
				Locale.setDefault(saved);
			}
			else {
				rb = ResourceBundle.getBundle(resName, loc);
			}
		}
		catch (Exception e) {
			System.err.println(resName + ": resource not found");
		}
	}

	public String getString(String k) {
		if (rb == null) {
			return FAILURE_STRING;
		}
		String res = null;
		try {
			res = rb.getString(k);
		}
		catch (Exception e) {
			System.err.println(k + ": resource not found");
		}
		if (res != null) {
			return res;
		}
		return FAILURE_STRING;
	}

	public String getString(String k, Hashtable ht) {
		String templ = getString(k);
		String res = "";
		int ind, ind2;
		do {
			ind = templ.indexOf(START_VAR);
			if (ind < 0) {
				res += templ;
			}
			else {
				res += templ.substring(0, ind);
				ind2 = templ.indexOf(FINISH_VAR, ind);
				if (ind2 >= 0) {
					String repl = (String) ht.get(templ.substring(ind + 2, ind2));
					if (repl == null) {
						repl = "";
					}
					res += repl;
					templ = templ.substring(ind2 + 2);
				}
				else {
					res += START_VAR;
					templ = templ.substring(ind + 2);
				}
			}
		} while (ind >= 0);
		return res;
	}

	public static Locale parseSuffix(String suffix) {
		if ((suffix == null) || (suffix.length() == 0)) {
			return null;
		}
		int undInd = suffix.indexOf('_');
		String sl = suffix;
		String sc = "";
		if (undInd > 0) {
			sl = suffix.substring(0, undInd);
			sc = suffix.substring(undInd + 1);
		}
		return new Locale(sl, sc);
	}

	public SafeResourceBundle(String resName, String locale) {
		this(resName, parseSuffix(locale));
	}
}
