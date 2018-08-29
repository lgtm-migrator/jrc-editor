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

package org.zaval.awt.dialog;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.Enumeration;
import java.util.Vector;

import org.zaval.awt.AlignConstants;
import org.zaval.awt.ResultField;
import org.zaval.awt.StaticImage;
import org.zaval.awt.TextAlignArea;

public class MessageBox2 extends Dialog implements LayoutManager {
	private StaticImage icon = null;
	private Vector buttons = null;
	private ResultField text;
	private Color storedColor = null;

	private Vector listeners = new Vector();
	private Button pressedButton = null;

	private static final int INDENT = 10;
	private static final int ICON_TEXT_GAP = 5;
	private static final int w = 4, h = 3, maxlinecount = 15;

	public ResultField getTextContainer() {
		return text;
	}

	public MessageBox2(Frame parent) {
		super(parent, false);
		setLayout(this);
		setTitle("Message");
		text = new ResultField();
		TextAlignArea area = text.getAlignArea();
		if (area != null) {
			area.setMultiLine(true);
			area.setAlign(AlignConstants.TLEFT);
			area.setInsets(new Insets(10, 0, 10, 0));
		}
		add(text);
	}

	@Override
	public void show() {
		pack();
		Dimension d1 = preferredSize();
		Dimension d2 = Toolkit.getDefaultToolkit().getScreenSize();
		resize(d1.width, d1.height);
		move((d2.width - d1.width) / 2, (d2.height - d1.height) / 2);
		setResizable(false);
		super.show();
	}

	@Override
	public void setBackground(Color c) //// new
	{
		if (storedColor != null) {
			c = storedColor;
		}
		storedColor = c;
		if (text != null) {
			text.setBackground(c);
		}
		if (icon != null) {
			icon.setBackground(c);
		}
		for (int i = 0; (buttons != null) && (i < buttons.size()); i++) {
			((Component) buttons.elementAt(i)).setBackground(c);
		}
		super.setBackground(c);
	}

	@Override
	public void setForeground(Color c) {
		super.setForeground(c);
		if (text != null) {
			text.setForeground(c);
		}
		if (icon != null) {
			icon.setForeground(c);
		}
		for (int i = 0; i < buttons.size(); i++) {
			((Component) buttons.elementAt(i)).setForeground(c);
		}
	}

	public void setButtons(Vector b) {
		if (buttons != null) {
			for (int i = 0; i < buttons.size(); i++) {
				remove((Component) buttons.elementAt(i));
			}
		}
		buttons = b;
		for (int i = 0; i < buttons.size(); i++) {
			add((Component) buttons.elementAt(i));
		}
		invalidate();
		validate();
	}

	public Vector getButtons() {
		return buttons;
	}

	public void setButtons(String[] sa) {
		int j;
		Vector z = new Vector(sa.length);
		for (j = 0; j < sa.length; ++j) {
			Button b = new Button(sa[j]);
			z.addElement(b);
		}
		setButtons(z);
	}

	public void setButtons(String one) {
		Vector z = new Vector();
		z.addElement(new Button(one));
		setButtons(z);
	}

	public void setIcon(Image icon1) {
		if (icon != null) {
			remove(icon);
		}
		if (icon1 == null) {
			return;
		}
		icon = new StaticImage(icon1);
		add(icon);
	}

	public void setText(String str) {
		text.setText(str);
	}

	public String getText() {
		return text.getText();
	}

	public void addListener(Component ls) {
		listeners.addElement(ls);
	}

	public void removeListener(Component ls) {
		listeners.removeElement(ls);
	}

	@Override
	public boolean action(Event e, Object o) {
		if (e.target instanceof Button) {
			pressedButton = (Button) e.target;
			Enumeration els = listeners.elements();
			while (els.hasMoreElements()) {
				Component listener = (Component) els.nextElement();
				listener.postEvent(new Event(this, Event.ACTION_EVENT, e.target));
			}
			hide();
			return true;
		}
		return super.action(e, o);
	}

	private void setDefFontMetrics() {
		if ((text != null) && (text.getAlignArea().getFontMetrics() == null)) {
			Font font = getFont();
			if (font == null) {
				return;
			}
			FontMetrics fm = getFontMetrics(font);
			if (fm == null) {
				return;
			}
			text.getAlignArea().setFontMetrics(fm);
		}
	}

	private Dimension getIconSize() {
		if (icon == null) {
			return new Dimension(0, 0);
		}
		return icon.preferredSize();
	}

	private Dimension getTextSize(int maxwidth) {
		if (text == null) {
			return new Dimension(0, 0);
		}
		setDefFontMetrics();

		int prefwidth = 0;
		int prefheight = 0;
		TextAlignArea align = text.getAlignArea();
		if ((align != null) && (align.getFontMetrics() != null)) {
			FontMetrics fm = align.getFontMetrics();
			String[] str = TextAlignArea.breakString(align.getText(), fm, maxwidth);
			for (String element : str) {
				prefwidth = Math.max(fm.stringWidth(element), prefwidth);
			}
			prefheight = fm.getHeight() * Math.min(maxlinecount, str.length);
			Insets ins = align.getInsets();
			prefwidth += ins.left + ins.right;
			prefheight += ins.top + ins.bottom;
		}
		return new Dimension(prefwidth, prefheight);
	}

	private Button getButton(int z) {
		return (Button) buttons.elementAt(z);
	}

	private Dimension getButtonsSize() {
		if ((buttons == null) || (buttons.size() == 0)) {
			return new Dimension(0, 0);
		}
		int h = 23, w = 48; // minimum height and width
		for (int i = 0; i < buttons.size(); i++) {
			getButton(i);
			Dimension d = getButton(i).preferredSize();
			h = Math.max(d.height, h);
			w = Math.max(d.width, w);
		}
		return new Dimension(w, h);
	}

	private Rectangle getPosition(Rectangle area, Dimension size) {
		int w, h;
		w = Math.max(0, (area.width - size.width) / 2);
		h = Math.max(0, (area.height - size.height) / 2);
		return new Rectangle(area.x + w, area.y + h, area.width - (w * 2), area.height - (h * 2));
	}

// LayoutManager implementation
	@Override
	public void addLayoutComponent(String s, Component component) {
	}

	@Override
	public void removeLayoutComponent(Component component) {
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Insets ins = insets();
		Dimension d;
		Dimension d2 = Toolkit.getDefaultToolkit().getScreenSize();
		d = getTextSize(d2.width / 2);
		int prefwidth = d.width;
		int prefheight = d.height;

		d = getIconSize();
		if (d.width != 0) {
			d.width += ICON_TEXT_GAP;
		}
		prefwidth += d.width;
		prefheight = Math.max(d.height, prefheight);

		d = getButtonsSize();
		prefwidth = Math.max(prefwidth, ((d.width + w) * buttons.size()) + w);
		prefheight += d.height + (h * 2) + INDENT;
		if (prefwidth < (d2.width / 4)) {
			prefwidth = d2.width / 4;
		}
		Dimension d111 = new Dimension(prefwidth + ins.left + ins.right + (2 * INDENT), prefheight + ins.top + ins.bottom + (2 * INDENT));
		return d111;

	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return preferredLayoutSize(parent);
	}

	@Override
	public void layoutContainer(Container parent) {
		Dimension size = parent.size();
		Insets inss = insets();
		int insl = inss.left + INDENT;
		int inst = inss.top + INDENT;
		int width = size.width - (insl + inss.right + INDENT);
		int height = size.height - (inst + inss.bottom + INDENT);
		Dimension isize = getIconSize();
		Dimension tsize = getTextSize(width - isize.width - ICON_TEXT_GAP);
		Dimension bsize = getButtonsSize();
		height -= bsize.height + (h * 2);
		height = Math.max(height, Math.max(isize.height, tsize.height));
		if (icon != null) {
			Dimension d = icon.preferredSize();
			Rectangle r = getPosition(new Rectangle(insl, inst, isize.width, height), d);
			icon.move(r.x, r.y);
			icon.resize(r.width, r.height);
		}
		if (text != null) {
			int addIc = isize.width;
			if (isize.width > 0) {
				addIc += ICON_TEXT_GAP;
			}
			if ((width - tsize.width) > (3 * addIc)) {
				addIc = 0;
			}
			else {
				addIc -= (width - tsize.width - addIc) / 2;
			}
			Rectangle r = getPosition(new Rectangle(insl + addIc, inst, width - addIc, height), tsize);
			text.move(r.x, r.y);
			text.resize(r.width, r.height);
		}
		int x, y;
		y = height + inst + h;
		x = ((width - ((bsize.width + w) * buttons.size()) - w) / 2) + w + insl;
		for (int i = 0; i < buttons.size(); i++) {
			getButton(i).move(x, y);
			getButton(i).resize(bsize);
			x += bsize.width + w;
		}
	}

	@Override
	public boolean handleEvent(Event e) {
		if (e.id == Event.WINDOW_DESTROY) {
			hide();
			return true;
		}
		return super.handleEvent(e);
	}

	public Button getPressedButton() {
		return pressedButton;
	}

	public void init() {
	}

	/*public void setModal( boolean b )
	{
	}*/

	@Override
	public boolean keyDown(Event e, int key) {
		if ((e.target instanceof Button) && (key == Event.ENTER)) {
			Component p = this;
			while ((p != null) && !(p instanceof Window)) {
				p = p.getParent();
			}
			if (p == null) {
				return false;
			}
			p.action(e, null);
			return true;
		}
		return false;
	}
}
