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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zaval.awt.peer.TreeNode;

public class LevelTree {
	// constants for insertion
	public static final int CHILD = 0;
	private static final int NEXT = CHILD + 1;
	private static final int LAST = CHILD + 2;

	List<TreeNode> e = new ArrayList<>(); // e is vector of existing nodes
	List<TreeNode> v = new ArrayList<>(); // v is vector of viewable nodes
	private TreeNode rootNode; // root node of tree
	private ImageResolver imgres; // To autosetup

	private int count; // Number of nodes in the tree
	private int viewCount;// Number of viewable nodes in the tree (A node is viewable if all of its parents are expanded.)

	private final String delim = ".";

	private final Map<String, TreeNode> nameCache = new HashMap<>();

	public LevelTree() {
		count = 0;
	}

	public int getViewCount() {
		return viewCount;
	}

	// Insert a new node relative to a node in the tree.
	// position = CHILD inserts the new node as a child of the node
	// position = NEXT inserts the new node as the next sibling
	// position = PREVIOUS inserts the new node as the previous sibling
	void insert(TreeNode newNode, TreeNode relativeNode, int position) {
		if ((newNode == null) || (relativeNode == null)) {
			return;
		}
		if (!exists(relativeNode)) {
			return;
		}
		switch (position) {
			case CHILD:
				addChild(newNode, relativeNode);
				break;

			case NEXT:
				addSibling(newNode, relativeNode);
				break;

			case LAST:
				addSibling(newNode, relativeNode);
				break;

			default:
				// invalid position
				return;
		}
		setResolver(rootNode, imgres);
		nameCache.put(newNode.text, newNode);
	}

	public TreeNode getRootNode() {
		return rootNode;
	}

	private boolean exists(TreeNode node) {
		if (nameCache.get(node.text) != null) {
			return true;
		}
		for (int i = 0; i < count; i++) {
			if (node == e.get(i)) {
				return true;
			}
		}
		return false;
	}

// This functions will be added on caf
	TreeNode getNode(String name) {
		if (name == null) {
			return null;
		}
		if (nameCache.get(name) != null) {
			return nameCache.get(name);
		}
		for (int i = 0; i < count; i++) {
			TreeNode tn = e.get(i);
			if (name.equals(tn.text)) {
				return tn;
			}
		}
		return null;
	}

	private void insertChild(String name, String addname) {
		if ((name == null) || (addname == null)) {
			return;
		}
		TreeNode tn = getNode(name);
		if (tn == null) {
			return;
		}
		insert(new TreeNode(addname, null, null), tn, LevelTree.CHILD);
	}

	void insertRoot(String addname) {
		if (addname == null) {
			return;
		}
		if (getRootNode() == null) {
			append(new TreeNode("root"));
		}
		insertChild(rootNode.text, addname);
	}

	// end add

	// add new node to level 0
	private void append(TreeNode newNode) {
		if (rootNode == null) {
			rootNode = newNode;
			rootNode.setDepth(0);
			rootNode.setStringProperty("PATH", "");
			e.add(rootNode);
			count = 1;
		}
		else {
			addSibling(newNode, rootNode);
		}
		setResolver(newNode, imgres);
	}

	private void addChild(TreeNode newNode, TreeNode relativeNode) {
		if (relativeNode.child == null) {
			relativeNode.child = newNode;
			newNode.parent = relativeNode;
			newNode.setDepth(relativeNode.getDepth() + 1);
			String prop = relativeNode.getStringProperty("PATH");
			if (prop.length() > 0) {
				prop += delim;
			}
			newNode.setStringProperty("PATH", prop + newNode.text);
			e.add(newNode);
			count++;
		}
		else {
			addSibling(newNode, relativeNode.child);
		}

		++relativeNode.numberOfChildren;
		setResolver(newNode, imgres);
	}

	private void addSibling(TreeNode newNode, TreeNode siblingNode) {
		TreeNode tempNode;
		tempNode = siblingNode;

		String s = siblingNode.getStringProperty("PATH");
		int index = s.lastIndexOf(delim);
		if (index >= 0) {
			newNode.setStringProperty("PATH", s.substring(0, index) + delim + newNode.text);
		}
		else {
			newNode.setStringProperty("PATH", newNode.text);
		}
		while (tempNode.sibling != null) {
			tempNode = tempNode.sibling;
		}
		tempNode.sibling = newNode;
		newNode.parent = tempNode.parent;
		newNode.setDepth(tempNode.getDepth());
		e.add(newNode);
		count++;
		setResolver(newNode, imgres);
	}

	void remove(TreeNode node) {
		if (!exists(node)) {
			return;
		}
		nameCache.remove(node.text);

		// remove node and its decendents
		if (node.parent != null) {
			if (node.parent.child == node) {
				if (node.sibling != null) {
					node.parent.child = node.sibling;
				}
				else {
					node.parent.child = null;
					node.parent.collapse();
				}
			}
			else {
				TreeNode tn = node.parent.child;

				while (tn.sibling != node) {
					tn = tn.sibling;
				}

				if (node.sibling != null) {
					tn.sibling = node.sibling;
				}
				else {
					tn.sibling = null;
				}
			}
		}
		else {
			if (node == rootNode) {
				if (node.sibling == null) {
					rootNode = null;
				}
				else {
					rootNode = node.sibling;
				}
			}
			else {
				TreeNode tn = rootNode;

				while (tn.sibling != node) {
					tn = tn.sibling;
				}

				if (node.sibling != null) {
					tn.sibling = node.sibling;
				}
				else {
					tn.sibling = null;
				}
			}
		}

		recount();
	}

	private void recount() {
		count = 0;
		e = new ArrayList<>();
		nameCache.clear();

		if (rootNode != null) {
			rootNode.depth = 0;
			traverse(rootNode);
		}
	}

	private void traverse(TreeNode node) {
		count++;
		e.add(node);
		nameCache.put(node.text, node);

		if (node.child != null) {
			node.child.depth = node.depth + 1;
			traverse(node.child);
		}
		if (node.sibling != null) {
			node.sibling.depth = node.depth;
			traverse(node.sibling);
		}
	}

	void resetVector() {
		// Traverses tree to put nodes into vector v
		// for internal processing. Depths of nodes are set,
		// and viewCount and viewWidest is set.
		v = new ArrayList<>(count);

		if (count < 1) {
			viewCount = 0;
			return;
		}
		rootNode.depth = 0;
		vectorize(rootNode.child);
		viewCount = v.size();
	}

	private void vectorize(TreeNode node) {
		if (node == null) {
			return;
		}
		nameCache.put(node.text, node);

		if (!node.hidden) {
			v.add(node);
			if (node.isExpanded()) {
				if (node.child != null) {
					node.child.depth = node.depth + 1;
					vectorize(node.child);
				}
			}
		}

		if (node.sibling != null) {
			node.sibling.depth = node.depth;
			vectorize(node.sibling);
		}
	}

	public void setResolver(ImageResolver imgres) {
		this.imgres = imgres;
		TreeNode t = getRootNode();
		setResolver(t, imgres);
	}

	private void setResolver(TreeNode t, ImageResolver imgres) {
		if (t != null) {
			t.setResolver(imgres);
		}
		int i;
		for (i = 0; i < e.size(); ++i) {
			TreeNode c = e.get(i);
			c.setResolver(imgres);
		}
	}

	void expandAll() {
		recount();
		if (e == null) {
			return;
		}
		for (TreeNode tn : e) {
			tn.expand();
		}
	}

	void collapseAll() {
		recount();
		if (e == null) {
			return;
		}
		for (TreeNode tn : e) {
			tn.collapse();
		}
	}

	private int getNumChild(TreeNode parent) {
		recount();
		if (parent == null) {
			return -1;
		}
		TreeNode next = parent.child;
		int count = 0;
		while (next != null) {
			count++;
			next = next.sibling;
		}
		return count;
	}

	TreeNode[] enumChild(TreeNode tn) {
		recount();
		if ((tn == null) || (tn.child == null)) {
			return null;
		}
		int size = getNumChild(tn);
		TreeNode tns[] = new TreeNode[size];
		TreeNode next = tn.child;
		for (int i = 0; i < size; i++) {
			tns[i] = next;
			next = next.sibling;
		}
		return tns;
	}

}
