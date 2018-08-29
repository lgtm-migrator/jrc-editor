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

import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.io.IOException;

// BMP Header, Win3.1 and on ( version 3 BMP )
// BMP Palette

class BMP_Palette {
	private ColorModel cm;
	public int readBytes = 0;

	public ColorModel getColorModel() {
		return cm;
	}

	public BMP_Palette(int numcolors, BMP_InputStream in, int pixsize) throws IOException {
		if ((pixsize == 24) || (pixsize == 16)) {
			cm = new DirectColorModel(24, 0x000000ff, 0x0000ff00, 0x00ff0000);
			return;
		}

		byte red[] = new byte[numcolors];
		byte green[] = new byte[numcolors];
		byte blue[] = new byte[numcolors];

		for (int i = 0; i < numcolors; i++) {
			blue[i] = (byte) in.read();
			green[i] = (byte) in.read();
			red[i] = (byte) in.read();
			in.read();
		}

		cm = new IndexColorModel(pixsize, numcolors, red, green, blue);
		readBytes = 3 + (numcolors * 4);
	}
}
