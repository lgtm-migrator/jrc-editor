package org.zaval.awt;

import java.awt.*;
import java.util.*;

public abstract class BaseCheckbox extends Canvas {
	public final static int LEFT = 1;
	public final static int RIGHT = 2;

	private Dimension sqSize = new Dimension(12, 12);
	private int textInset = 3;

	boolean state = false;
	boolean hasFocus = false;
	boolean isCalc = false;
	boolean mouse_down;

	private int align = LEFT;
	int posY = 0, posX = 0;
	Rectangle textArea = new Rectangle();
	Rectangle eventBox = new Rectangle();
	private TextAlignArea alignArea = new TextAlignArea();

	public String getLabel() {
		return alignArea.getText();
	}

	public void disable() {
		super.disable();
		repaint();
	}

	public void enable() {
		super.enable();
		repaint();
	}

	public void setLabel(String label) {
		alignArea.setText(label);
		if (alignArea.isValid())
			return;
		invalidate();
		repaint();
	}

	public void setAlign(int a) {
		if (a == align)
			return;
		align = a;
		if (alignArea.isValid())
			return;
		invalidate();
		repaint();
	}

	public int getAlign() {
		return align;
	}

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
		repaint();
	}

	public boolean gotFocus(Event e, Object o) {
		hasFocus = true;
		repaint();
		return super.gotFocus(e, o);
	}

	public boolean lostFocus(Event e, Object o) {
		hasFocus = false;
		repaint();
		return super.lostFocus(e, o);
	}

	private void calc() {
		FontMetrics fm = getFontMetrics(getFont());
		if (fm == null)
			return;

		Dimension d = size();
		posY = d.height / 2 - sqSize.height / 2;
		posX = 0;

		if (align == RIGHT)
			posX = d.width - sqSize.width - 1;

		eventBox.x = 0;
		eventBox.y = 0;
		eventBox.width = d.width;
		eventBox.height = d.height;

		alignArea.setSize(d);
		if (align == LEFT)
			alignArea.setInsets(new Insets(0, textInset + sqSize.width + 1, 0, 0));
		else
			alignArea.setInsets(new Insets(0, 1, 0, 0));

		alignArea.setFontMetrics(fm);

		textArea = alignArea.getAlignRectangle();
		if ((textArea.x + textArea.width) >= d.width)
			textArea.width = (d.width - textArea.x - 1);

		if ((textArea.y + textArea.height) >= d.height)
			textArea.height = (d.height - textArea.y - 1);

		if (align == RIGHT && (textArea.x + textArea.width) > posX)
			textArea.width = (posX - textArea.x - 1);

		isCalc = true;
	}

	public void invalidate() {
		isCalc = false;
		super.invalidate();
	}

	public void paint(Graphics g) {
		super.paint(g);
		if (!isCalc)
			calc();
		String lab = getLabel();

		if (isEnabled())
			g.setColor(Color.black);
		else
			g.setColor(Color.gray);

		int yy = posY + sqSize.height;
		if (lab != null && lab.length() > 0) {
			if (!isEnabled())
				alignArea.draw(g, 0, 1, Color.white);

			alignArea.draw(g, getForeground());
		}

		if (hasFocus) {
			g.setColor(Color.black);
			if (lab != null && lab.length() > 0)
				drawRect(g, textArea.x - 1, textArea.y, textArea.width, textArea.height);
		}

		paint(g, posX, posY, sqSize.width, sqSize.height);
	}

	protected boolean insideBox(int x, int y) {
		return eventBox.inside(x, y);
	}

	public synchronized boolean mouseDown(Event ev, int x, int y) {
		if (isEnabled()) {
			if (insideBox(x, y)) {
				mouse_down = true;
				requestFocus();
				if (condition())
					stateChanged();
			}
		}
		return super.mouseDown(ev, x, y);
	}

	public synchronized boolean mouseUp(Event ev, int x, int y) {
		if (isEnabled())
			if (condition() && insideBox(x, y)) {
				mouse_down = false;

				if (state)
					state = false;
				else
					state = true;
				repaint();

				Event e = new Event(this, Event.ACTION_EVENT, "1");
				getParent().postEvent(e);
				return true;
			}
		return super.mouseDown(ev, x, y);
	}

	public boolean keyDown(Event ev, int key) {
		if (!hasFocus || key != ' ' || (!condition()))
			return super.keyDown(ev, key);

		setState(!getState());
		Event e = new Event(this, Event.ACTION_EVENT, "1");
		getParent().postEvent(e);
		return true;
	}

	public synchronized boolean mouseDrag(Event ev, int x, int y) {
		if (isEnabled()) {
			if (insideBox(x, y)) {
				mouse_down = false;
				repaint();
			}
		}
		return super.mouseDrag(ev, x, y);
	}

	public Dimension preferredSize() {
		FontMetrics fm = getFontMetrics(getFont());
		if (fm == null)
			return super.preferredSize();

		int w = textInset + sqSize.width + fm.stringWidth(alignArea.getText()) + 1;
		int h = Math.max(fm.getHeight(), sqSize.height);
		return new Dimension(w, h);
	}

	public void stateChanged() {
	}

	protected boolean condition() {
		return true;
	}

	public TextAlignArea getAlignArea() {
		return alignArea;
	}

	public boolean mouseMove(Event e, int x, int y) {
		return true;
	}

	public boolean mouseExit(Event e, int x, int y) {
		return true;
	}

	public boolean mouseEnter(Event e, int x, int y) {
		return true;
	}

	public abstract void paint(Graphics g, int x, int y, int width, int height);

	public void drawRect(Graphics gr, int x, int y, int w, int h) {
		drawVLine(gr, y, y + h, x);
		drawVLine(gr, y, y + h, x + w);
		drawHLine(gr, x, x + w, y);
		drawHLine(gr, x, x + w, y + h);
	}

	public void drawHLine(Graphics gr, int x1, int x2, int y1) {
		int dx = x2 - x1;
		int count = dx / 2 + dx % 2;
		for (int i = 0; i < count; i++) {
			gr.drawLine(x1, y1, x1, y1);
			x1 += 2;
		}
		gr.drawLine(x2, y1, x2, y1);
	}

	public void drawVLine(Graphics gr, int y1, int y2, int x1) {
		int dy = y2 - y1;
		int count = dy / 2 + dy % 2;
		;
		for (int i = 0; i < count; i++) {
			gr.drawLine(x1, y1, x1, y1);
			y1 += 2;
		}
		gr.drawLine(x1, y2, x1, y2);
	}
}
