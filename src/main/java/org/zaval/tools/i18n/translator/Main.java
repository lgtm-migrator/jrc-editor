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

import java.io.*;
import java.awt.*;
import java.util.*;
import org.zaval.util.SafeResourceBundle;

public class Main {
	public static void main(String arg[]) throws Exception {
		String altDir = System.getProperty("my.root.dir");
		File f = new File(altDir != null ? altDir : ".");
		String path = f.getAbsolutePath();
		if (path.endsWith("."))
			path = path.substring(0, path.length() - 1);
		path += "/images/";

		Translator t = null;
		if (arg.length > 0) {
			t = new Translator(path, new SafeResourceBundle("jrc-editor", Locale.getDefault()), arg[0]);
		}
		else
			t = new Translator(path, new SafeResourceBundle("jrc-editor", Locale.getDefault()));

		Dimension gdz = Toolkit.getDefaultToolkit().getScreenSize();
		int optimalX = gdz.width / 4 * 3;
		int optimalY = gdz.height / 4 * 3;
		t.move((gdz.width - optimalX) / 2, (gdz.height - optimalY) / 2);
		t.resize(optimalX, optimalY);
		t.show();
	}
}
