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

package org.zaval.awt;

import java.awt.Event;
import java.awt.Graphics;
import java.awt.MenuItem;

// ====================================================================
import org.zaval.awt.peer.TreeNode;

// =================================================================

public class GraphTree extends SymTree {
	ContextMenuBar menubar = null;

// =================================================================

	public GraphTree() {
		super();
	}

	// =================================================================

	public GraphTree(TreeNode tn) {
		super();
	}

	// =================================================================

	public GraphTree(ContextMenuBar a_menubar) {
		this();
		menubar = a_menubar;
	}

// =================================================================

	public void setMenuBar(ContextMenuBar mb) {
		menubar = mb;
		menubar.setParent(this);
	}

// =================================================================

	public ContextMenuBar getMenuBar() {
		return menubar;
	}

// =================================================================

	public void setContextMenu(String name, int index) {
		TreeNode tn = getNode(name);
		if ((tn == null) || (menubar.get(index) == null)) {
			return;
		}
		tn.setContextMenu(index);
	}

// =================================================================

	public void setContextMenu(String name, ContextMenu cm) {
		TreeNode tn = getNode(name);
		if (tn == null) {
			return;
		}
		for (int i = 0; i < menubar.countMenus(); i++) {
			if (menubar.get(i) == cm) {
				tn.setContextMenu(i);
				return;
			}
		}
		addMenu(cm);
		setContextMenu(name, menubar.countMenus() - 1);
	}

	// =================================================================

	public ContextMenu getContextMenu(String name) {
		TreeNode tn = getNode(name);
		if ((menubar == null) || (tn == null) || (tn.getContextMenu() < 0)) {
			return null;
		}
		return menubar.get(tn.getContextMenu());
	}

// =================================================================

	public int addMenu(ContextMenu menu) {
		if (menu == null) {
			return -1;
		}
		menubar.add(menu);
		return menubar.countMenus() - 1;
	}

// =================================================================

	@Override
	public void update(Graphics gr) {
		paint(gr);
	}

	@Override
	public void paint(Graphics gr) {
		if ((menubar != null) && menubar.isActive()) {
			menubar.paint(gr);
		}
		else {
			super.paint(gr);
		}
	}

// =================================================================

	int isRightKey = 0;

	@Override
	public boolean mouseDown(Event evt, int x, int y) {
		if (evt.modifiers == 4) {
			isRightKey = 1;
		}
		else {
			isRightKey = 0;
			return super.mouseDown(evt, x, y);
		}
		return true;
	}

// =================================================================

	@Override
	public boolean mouseUp(Event evt, int x, int y) {
		if (isRightKey == 1) {
			isRightKey = 0;
			changeSelection(evt, evt.x, evt.y, false, new boolean[1]);
			TreeNode tn = getSelectedNode();
			if ((noChoice) || (tn == null) || (tn.getContextMenu() < 0)) {
				tn = getRootNode();
			}
			if ((tn != null) && (tn.getContextMenu() >= 0)) {
				menubar.init(tn.getContextMenu(), x, y);
				repaint();
			}
		}
		return super.mouseUp(evt, x, y);
	}

// =================================================================

	@Override
	public boolean action(Event evt, Object what) {
		if (evt.target instanceof MenuItem) {
			repaint();
			return false;
		}
		else {
			return super.action(evt, what);
		}
	}

// =================================================================

	@Override
	public boolean handleEvent(Event evt) {
		if (evt.key == '\t') {
			return false;
		}
		if ((menubar != null) && menubar.handleEvent(evt)) {
			return true;
		}
		return super.handleEvent(evt);
	}

// =================================================================

	@Override
	public boolean postEvent(Event evt) {
		if (evt.target instanceof MenuItem) {
			if (noChoice) {
				evt.arg = null;
			}
			else {
				evt.arg = getSelectedNode().text;
			}
		}
		return super.postEvent(evt);
	}

	public void disableAll() {
		if (menubar == null) {
			return;
		}
		int count = menubar.countMenus();
		for (int i = 0; i < count; i++) {
			menubar.getMenu(i).disable();
		}
	}

	// =================================================================

	public void enableAll() {
		if (menubar == null) {
			return;
		}
		int count = menubar.countMenus();
		for (int i = 0; i < count; i++) {
			menubar.getMenu(i).enable();
		}
	}

	// =================================================================

	public void setContextMenu(TreeNode tn, int index) {
		if ((tn == null) || (menubar.get(index) == null)) {
			return;
		}
		tn.setContextMenu(index);
	}

	// =================================================================

	public ContextMenu getContextMenu(TreeNode tn) {
		if (tn == null) {
			return null;
		}
		return menubar.get(tn.getContextMenu());
	}
}
