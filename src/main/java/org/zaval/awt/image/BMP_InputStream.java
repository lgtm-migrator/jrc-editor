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

package org.zaval.awt.image;

import java.io.IOException;
import java.io.InputStream;

class BMP_InputStream extends InputStream {
	private InputStream in;

	public BMP_InputStream(InputStream ins) {
		this.in = ins;
	}

	public short readShort() throws IOException {
		short result = 0;
		int ch1, ch2;
		ch1 = ch2 = 0;
		ch2 = in.read();
		ch1 = in.read();
		result = (short) ((ch1 << 8) + (ch2));
		return result;
	}

	public int readInt() throws IOException {
		int result = 0;
		int ch1, ch2, ch3, ch4;
		ch1 = ch2 = ch3 = ch4 = 0;
		ch4 = in.read();
		ch3 = in.read();
		ch2 = in.read();
		ch1 = in.read();
		// now swap bytes
		result = ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4));
		return result;
	}

	@Override
	public int read() throws IOException {
		return in.read();
	}
}
