/*
 * Copyright (C) 2001-2002  Zaval Creative Engineering Group (http://www.zaval.org)
 * Copyright (C) 2019 Christoph Obexer <cobexer@gmail.com>
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

import static org.zaval.ui.UiUtils.constrain;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.zaval.ui.UiUtils;

@SuppressWarnings("serial")
public class EditDialog extends JDialog {
	private final JLabel label;
	private final JTextField edit;
	private final JButton ok;
	private final JButton cancel;
	private boolean isApply;

	public EditDialog(JFrame owner, String title, boolean modal) {
		super(owner, title, modal);
		setLayout(new GridBagLayout());

		label = new JLabel("Name");

		constrain(this, label, 0, 0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, 0.0, 0.0, 5, 5, 5, 5);
		edit = new JTextField(20);
		edit.addActionListener(this::onPerform);
		constrain(this, edit, 1, 0, 4, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 1.0, 0.0, 5, 5, 5, 5);

		edit.requestFocusInWindow();
		ok = new JButton("Ok");
		cancel = new JButton("Cancel");
	}

	protected void renderDialogFooter() {
		ok.addActionListener(this::onPerform);
		cancel.addActionListener(this::onCancel);

		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 2, 2, 0));
		p.add(ok);
		p.add(cancel);

		constrain(this, p, 0, 10, 2, 1, GridBagConstraints.EAST, GridBagConstraints.NONE, 1.0, 0.0, 5, 5, 5, 5);
	}

	public void setText(String t) {
		edit.setText(null == t ? "" : t);
	}

	public String getText() {
		return edit.getText();
	}

	public void setButtonsCaption(String o, String c) {
		ok.setText(o);
		cancel.setText(c);
	}

	public void setLabelCaption(String l) {
		label.setText(l);
	}

	public boolean isApply() {
		return isApply;
	}

	public void doModal() {
		pack();
		UiUtils.toCenter(this);
		edit.requestFocusInWindow();
		setVisible(true);
	}

	protected void onPerform(ActionEvent e) {
		isApply = true;
		dispose();
	}

	private void onCancel(ActionEvent e) {
		isApply = false;
		dispose();
	}
}
