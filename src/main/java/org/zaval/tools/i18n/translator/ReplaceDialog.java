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

import java.awt.Component;
import java.awt.Event;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;

import org.zaval.awt.BorderedPanel;
import org.zaval.awt.EmulatedTextField;
import org.zaval.awt.IECheckbox;
import org.zaval.awt.IELabel;
import org.zaval.awt.IERadioButton;
import org.zaval.awt.dialog.EditDialog;

class ReplaceDialog extends EditDialog {
	private final IERadioButton regex;
	private final IERadioButton exact;
	private final IERadioButton cg2[];

	private final IECheckbox cases;
	private final IECheckbox prompt;
	private final IECheckbox all;
	private final EmulatedTextField replaceTo;
	private final IELabel label;

	public void setReplaceLabel(String s) {
		label.setText(s);
	}

	public void setRMGroupLabels(String s1, String s3) {
		regex.setLabel(s1);
		exact.setLabel(s3);
	}

	public void setCPALabels(String s1, String s2, String s3) {
		cases.setLabel(s1);
		prompt.setLabel(s2);
		all.setLabel(s3);
	}

	public boolean isRegexMatching() {
		return regex.getState();
	}

	public boolean isCaseSensitive() {
		return cases.getState();
	}

	public boolean isPromptRequired() {
		return prompt.getState();
	}

	public boolean isReplaceAll() {
		return all.getState();
	}

	public String getReplaceTo() {
		return replaceTo.getText();
	}

	public ReplaceDialog(Frame f, String s, boolean b, Component l) {
		super(f, s, b, l);

		cg2 = new IERadioButton[2];
		cg2[0] = regex = new IERadioButton("", false);
		cg2[1] = exact = new IERadioButton("", true);
		cases = new IECheckbox("");
		cases.setState(true);
		prompt = new IECheckbox("");
		prompt.setState(true);
		all = new IECheckbox("");
		all.setState(false);

		label = new IELabel("To:");
		replaceTo = new EmulatedTextField(20);
		constrain(this, label, 0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0.0, 0.0, 0, 5, 5, 5);
		constrain(this, replaceTo, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST, 1.0, 0.0, 0, 5, 5, 0);

		Panel p = new Panel();
		p.setLayout(new GridBagLayout());

		Panel p2 = new BorderedPanel();
		p2.setLayout(new GridBagLayout());
		constrain(p2, exact, 1, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 0.0, 0.0, 0, 5, 5, 0);
		constrain(p2, regex, 1, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 0.0, 0.0, 0, 5, 5, 0);
		constrain(p, p2, 0, 1, 2, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST, 1.0, 1.0, 5, 0, 0, 0);

		constrain(p, cases, 0, 2, 2, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 0.0, 0.0, 5, 5, 5, 0);
		constrain(p, prompt, 0, 3, 2, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 0.0, 0.0, 0, 5, 5, 0);
		constrain(p, all, 0, 4, 2, 1, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 0.0, 0.0, 0, 5, 5, 5);

		constrain(this, p, 0, 2, 2, 1, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST, 1.0, 1.0, 5, 5, 5, 5);
	}

	private void applyTo(IERadioButton[] group, IERadioButton b) {
		int j;
		for (j = 0; j < group.length; ++j) {
			if (group[j] == b) {
				break;
			}
		}
		if (j >= group.length) {
			return;
		}
		for (j = 0; j < group.length; ++j) {
			if (group[j] != b) {
				group[j].setState(false);
			}
		}
	}

	@Override
	public boolean action(Event evt, Object what) {
		if (evt.target instanceof IERadioButton) {
			applyTo(cg2, (IERadioButton) evt.target);
			return true;
		}
		return super.action(evt, what);
	}
}
