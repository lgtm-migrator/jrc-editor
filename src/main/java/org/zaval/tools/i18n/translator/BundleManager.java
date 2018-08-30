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

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;
import java.util.Vector;

class BundleManager implements TranslatorConstants {
	private BundleSet set;

	BundleManager() {
		set = new BundleSet();
	}

	void appendResource(InputStream stream, String lang) throws IOException {
		readResource(stream, lang);
	}

	BundleManager(String baseFileName) throws IOException {
		set = new BundleSet();
		readResources(baseFileName);
	}

	BundleSet getBundle() {
		return set;
	}

	String dirName(String fn) {
		fn = replace(fn, "\\", "/");
		int ind = fn.lastIndexOf('/');
		return ind >= 0 ? fn.substring(0, ind + 1) : "./";
	}

	String baseName(String fn) {
		fn = replace(fn, "\\", "/");
		int ind = fn.lastIndexOf('/');
		fn = ind >= 0 ? fn.substring(ind + 1) : fn;
		ind = fn.lastIndexOf('.');
		return ind >= 0 ? fn.substring(0, ind) : fn;
	}

	private String extName(String fn) {
		fn = replace(fn, "\\", "/");
		int ind = fn.lastIndexOf('.');
		return ind >= 0 ? fn.substring(ind) : "";
	}

	private String purifyFileName(String fn) {
		fn = baseName(fn);
		int ind = fn.lastIndexOf('_');
		int in2 = ind > 0 ? fn.lastIndexOf('_', ind - 1) : -1;
		if ((in2 < 0) && (ind > 0)) {
			in2 = ind;
		}
		return in2 >= 0 ? fn.substring(0, in2) : fn;
	}

	private Vector<String> getResFiles(String dir, String baseFileName, String defExt) {
		File f = new File(dir);
		String bpn = purifyFileName(baseFileName);
		String[] fs = f.list();
		if (fs.length == 0) {
			return null;
		}
		Vector<String> res = new Vector<>();
		for (String f1 : fs) {
			if (!f1.startsWith(bpn)) {
				continue;
			}
			String bfn = purifyFileName(f1);
			if (!bfn.equals(bpn)) {
				continue;
			}
			if (!extName(f1).equals(defExt)) {
				continue;
			}
			File f2 = new File(dir + f1);
			if (!f2.isDirectory()) {
				res.addElement(f1);
			}
		}
		return res;
	}

	String determineLanguage(String fn) {
		fn = baseName(fn);
		int ind = fn.lastIndexOf('_');
		int in2 = ind > 0 ? fn.lastIndexOf('_', ind - 1) : -1;
		if ((in2 < 0) && (ind > 0)) {
			in2 = ind;
		}
		return in2 >= 0 ? fn.substring(in2 + 1) : "en";
	}

	private void readResources(String baseFileName) throws IOException {
		String dir = dirName(baseFileName);
		String ext = extName(baseFileName);
		baseFileName = baseName(baseFileName);

		Vector<String> fileNames = getResFiles(dir, baseFileName, ext);
		for (int i = 0; i < fileNames.size(); i++) {
			String fn = fileNames.elementAt(i);
			readResource(dir + fn, determineLanguage(fn));
		}
	}

	private void readResource(String fullName, String lang) throws IOException {
		Vector<String> lines = getLines(fullName);
		proceedLines(lines, lang, fullName);
	}

	private void readResource(InputStream in, String lang) throws IOException {
		Vector<String> lines = getLines(in);
		proceedLines(lines, lang, null);
	}

	private void proceedLines(Vector<String> lines, String lang, String fullName) {
		String lastComment = null;
		fullName = fullName != null ? fullName : "tmp_" + lang;
		set.addLanguage(lang);
		set.getLanguage(lang).setLangFile(fullName);
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.elementAt(i);
			line = line.trim();
			if (line.length() == 0) {
				continue;
			}
			if (line.startsWith("#")) {
				lastComment = line.substring(1);
				continue;
			}
			line.indexOf('#');

			StringTokenizer st = new StringTokenizer(line, "=", true); // key = value
			if (st.countTokens() < 2) {
				continue; // syntax error, ignored
			}
			String dname = st.nextToken().trim();
			st.nextToken(); // '='
			String value = "";
			if (st.hasMoreTokens()) {
				value = st.nextToken("");
			}

			BundleItem bi = set.getItem(dname);
			if (bi == null) {
				bi = set.addKey(dname);
			}
			bi.setTranslation(lang, value);
			bi.setComment(lastComment);
			lastComment = null;
		}
		set.resort();
	}

	void setComment(String key, String comment) {
		BundleItem bi = set.getItem(key);
		if (bi == null) {
			return;
		}
		bi.setComment(comment);
	}

	private Vector<String> getLines(String fileName) throws IOException {
		Vector<String> res = new Vector<>();
		if (fileName.endsWith(RES_EXTENSION)) {
			DataInputStream in = new DataInputStream(new FileInputStream(fileName));
			String line = null;
			while ((line = in.readLine()) != null) {
				for (;;) {
					line = line.trim();
					if (line.endsWith("\\")) {
						String line2 = in.readLine();
						if (line2 != null) {
							line = line.substring(0, line.length() - 1) + line2;
						}
						else {
							break;
						}
					}
					else {
						break;
					}
				}
				res.addElement(fromEscape(line));
			}
			in.close();
		}
		else {
			RandomAccessFile in = new RandomAccessFile(fileName, "r");
			StringBuilder sb = new StringBuilder();
			int factor1 = 1;
			int factor2 = 256;
			for (;;) {
				if ((in.length() - in.getFilePointer()) == 0) {
					break;
				}
				int i = (in.readUnsignedByte() * factor1) + (in.readUnsignedByte() * factor2);
				if (i == 0xFFFE) {
					factor1 = 256;
					factor2 = 1;
				}
				if ((i != 0x0D) && (i != 0xFFFE) && (i != 0xFEFF) && (i != 0xFFFF)) {
					if (i != 0x0A) {
						sb.append((char) i);
					}
					else {
						res.addElement(fromEscape(sb.toString()));
						sb.setLength(0);
					}
				}
			}
			in.close();
		}
		return res;
	}

	private Vector<String> getLines(InputStream xin) throws IOException {
		Vector<String> res = new Vector<>();
		DataInputStream in = new DataInputStream(xin);
		String line = null;
		while ((line = in.readLine()) != null) {
			for (;;) {
				line = line.trim();
				if (line.endsWith("\\")) {
					String line2 = in.readLine();
					if (line2 != null) {
						line = line.substring(0, line.length() - 1) + line2;
					}
					else {
						break;
					}
				}
				else {
					break;
				}
			}
			res.addElement(fromEscape(line));
		}
		in.close();
		return res;
	}

	private static String toEscape(String s) {
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			int val = ch;
			if (ch == '\r') {
				continue;
			}
			if ((val >= 0) && (val < 128) && (ch != '\n') && (ch != '\\')) {
				res.append(ch);
			}
			else {
				res.append("\\u");
				String hex = Integer.toHexString(val);
				for (int j = 0; j < (4 - hex.length()); j++) {
					res.append("0");
				}
				res.append(hex);
			}
		}
		return res.toString();
	}

	private static String fromEscape(String s) {
		StringBuilder res = new StringBuilder(s.length());
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if ((ch == '\\') && ((i + 1) >= s.length())) {
				res.append(ch);
				break;
			}
			if (ch != '\\') {
				res.append(ch);
			}
			else {
				switch (s.charAt(i + 1)) {
					case 'u':
						res.append((char) Integer.parseInt(s.substring(i + 2, i + 6), 16));
						i += 5;
						break;
					case 'n':
						res.append('\n');
						i++;
						break;
					case 't':
						res.append('\t');
						i++;
						break;
					case 'r':
						res.append('\r');
						i++;
						break;
					default:
						break;
				}
			}
		}
		return res.toString();
	}

	String replace(String line, String from, String to) {
		StringBuilder res = new StringBuilder(line.length());
		String tmpstr;
		int ind = -1, lastind = 0;

		while ((ind = line.indexOf(from, ind + 1)) != -1) {
			if (lastind < ind) {
				tmpstr = line.substring(lastind, ind);
				res.append(tmpstr);
			}
			res.append(to);
			lastind = ind + from.length();
			ind += from.length() - 1;
		}
		if (lastind == 0) {
			return line;
		}
		res.append(line.substring(lastind));
		return res.toString();
	}

	void store(String fileName) throws IOException {
		int j, k = set.getLangCount();
		for (j = 0; j < k; ++j) {
			LangItem lang = set.getLanguage(j);
			store(lang.getLangId(), fileName);
		}
	}

	void store(String lng, String fn) throws IOException {
		LangItem lang = set.getLanguage(lng);
		if (fn == null) {
			fn = lang.getLangFile();
		}
		else {
			String tmpFn = fn;
			tmpFn = dirName(tmpFn) + purifyFileName(tmpFn);
			if (set.getLanguage(0) != lang) {
				tmpFn += "_" + lang.getLangId();
			}
			tmpFn += RES_EXTENSION;
			fn = tmpFn;
			lang.setLangFile(fn);
		}

		if (fn == null) {
			store(lng, "autosaved.properties");
			return;
		}

		Vector<String> lines = set.store(lang.getLangId());
		if (fn.endsWith(RES_EXTENSION)) {
			PrintStream f = new PrintStream(new FileOutputStream(fn));
			for (int j = 0; j < lines.size(); j++) {
				f.print(toEscape(lines.elementAt(j)) + System.getProperty("line.separator"));
			}
			f.close();
		}
		else {
			FileOutputStream f = new FileOutputStream(fn);
			f.write(0xFF);
			f.write(0xFE);
			for (int j = 0; j < lines.size(); j++) {
				String s = lines.elementAt(j);
				s = replace(s, "\n", toEscape("\n"));
				for (int k = 0; k < s.length(); k++) {
					char ch = s.charAt(k);
					f.write((ch) & 255);
					f.write((ch) >> 8);
				}
				f.write(0x0D);
				f.write(0x00);
				f.write(0x0A);
				f.write(0x00);
			}
			f.close();
		}
	}
}
