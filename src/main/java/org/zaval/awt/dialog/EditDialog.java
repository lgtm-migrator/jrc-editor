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

import java.awt.AWTEvent;
import java.awt.Button;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import org.zaval.awt.EmulatedTextField;
import org.zaval.awt.IELabel;

public class EditDialog extends Dialog implements java.awt.event.AWTEventListener {
	private final EmulatedTextField edit;
	private final Button ok, cancel;
	private boolean isApply;
	private final Component listener;
	private final IELabel label;

	public EditDialog(Frame f, String s, boolean b, Component l) {
		super(f, s, b);
		setLayout(new GridBagLayout());

		ok = new Button("Ok");
		cancel = new Button("Cancel");
		label = new IELabel("Name");

		Panel p = new Panel();
		p.setLayout(new GridLayout(1, 2, 2, 0));
		p.add(ok);
		p.add(cancel);

		constrain(this, label, 0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0.0, 0.0, 5, 5, 5, 5);
		edit = new EmulatedTextField(20);
		constrain(this, edit, 1, 0, 4, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST, 1.0, 0.0, 5, 5, 5, 5);
		constrain(this, p, 0, 10, 2, 1, GridBagConstraints.NONE, GridBagConstraints.EAST, 1.0, 0.0, 5, 5, 5, 5);

		listener = l;
		edit.requestFocus();
	}

	public void setText(String t) {
		if (t == null) {
			edit.setText("");
		}
		else {
			edit.setText(t);
		}
	}

	public String getText() {
		return edit.getText();
	}

	public void setButtonsCaption(String o, String c) {
		ok.setLabel(o);
		cancel.setLabel(c);
	}

	public void setLabelCaption(String l) {
		label.setText(l);
	}

	@Override
	public boolean handleEvent(Event e) {
		if ((e.id == Event.WINDOW_DESTROY) || ((e.target == cancel) && (e.id == Event.ACTION_EVENT))) {
			isApply = false;
			dispose();
		}

		if ((e.target == ok) && (e.id == Event.ACTION_EVENT)) {
			isApply = true;
			listener.postEvent(new Event(this, Event.ACTION_EVENT, edit.getText()));
			dispose();
		}

		return super.handleEvent(e);
	}

	public boolean isApply() {
		return isApply;
	}

	private void toCenter() {
		Dimension s = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension d = getSize();
		move((s.width - d.width) / 2, (s.height - d.height) / 2);
	}

	protected static void constrain(Container c, Component p, int x, int y, int w, int h, int f, int a, double wx, double wy, int t, int l,
		int r, int b) {
		GridBagConstraints cc = new GridBagConstraints();

		cc.gridx = x;
		cc.gridy = y;
		cc.gridwidth = w;
		cc.gridheight = h;

		cc.fill = f;
		cc.anchor = a;
		cc.weightx = wx;
		cc.weighty = wy;

		if ((t + b + l + r) > 0) {
			cc.insets = new Insets(t, l, b, r);
		}
		LayoutManager lm = c.getLayout();
		if (lm instanceof GridBagLayout) {
			GridBagLayout gbl = (GridBagLayout) lm;
			gbl.setConstraints(p, cc);
		}
		c.add(p);
	}

	@Override
	public boolean keyDown(Event e, int key) {
		if (key == '\t') {
			return moveFocus();
		}
		else if (((e.target == ok) && (key == Event.ENTER)) || ((e.target == edit) && (key == Event.ENTER))) {
			isApply = true;
			listener.postEvent(new Event(this, Event.ACTION_EVENT, edit.getText()));
			dispose();
			return true;
		}
		else if ((e.target == cancel) && (key == Event.ENTER)) {
			isApply = false;
			dispose();
			return true;
		}
		else {
			return false;
		}
	}

	public void doModal() {
		Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.KEY_EVENT_MASK);
		pack();
		toCenter();
		edit.requestFocus();
		show();
		Toolkit.getDefaultToolkit().removeAWTEventListener(this);
	}

	private boolean moveFocus() {
		Component owner = getFocusOwner();
		return moveFocus(owner);
	}

	private void optimize(List<Component> v) {
		int j, k = v.size();
		for (j = 0; j < k; ++j) {
			Component c = v.get(j);
			if (c instanceof Button) {
				v.remove(j);
				v.add(c);
				--k;
				--j;
			}
		}
	}

	private boolean moveFocus(Component c) {
		int j, k;
		boolean ask = false;
		List<Component> v = new ArrayList<>();

		linearize(this, v);
		optimize(v);
		for (k = v.size(), j = 0; j < k; ++j) {
			if (c == v.get(j)) {
				break;
			}
		}
		if (j >= k) {
			j = -1;
		}
		if (j == (k - 1)) {
			ask = true;
			j = -1; // see below +1
		}
		v.get(j + 1).requestFocus();
		return ask;
	}

	private void linearize(Container cc, List<Component> v) {
		int j, k = cc.getComponentCount();
		for (j = 0; j < k; ++j) {
			Component c = cc.getComponent(j);
			if (c instanceof Label) {
				// ignore
			}
			else if (c instanceof IELabel) {
				// ignore
			}
			else if (c instanceof Container) {
				linearize((Container) c, v);
			}
			else {
				v.add(c);
			}
		}
	}

	@Override
	public void eventDispatched(AWTEvent event) {
		if (event.getID() != java.awt.event.KeyEvent.KEY_TYPED) {
			return;
		}
		if (!(event instanceof java.awt.event.KeyEvent)) {
			return;
		}
		java.awt.event.KeyEvent ke = (java.awt.event.KeyEvent) event;
		if (ke.getKeyChar() != '\t') {
			return;
		}
		moveFocus();
	}
}
