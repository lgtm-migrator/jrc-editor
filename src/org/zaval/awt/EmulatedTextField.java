/**
 *     Caption: Zaval Java Resource Editor
 *     $Revision: 0.37 $
 *     $Date: 2002/03/28 9:24:42 $
 *
 *     @author:     Victor Krapivin
 *     @version:    2.0
 *
 * Zaval JRC Editor is a visual editor which allows you to manipulate 
 * localization strings for all Java based software with appropriate 
 * support embedded.
 * 
 * For more info on this product read Zaval Java Resource Editor User's Guide
 * (It comes within this package).
 * The latest product version is always available from the product's homepage:
 * http://www.zaval.org/products/jrc-editor/
 * and from the SourceForge:
 * http://sourceforge.net/projects/zaval0002/
 *
 * Contacts:
 *   Support : support@zaval.org
 *   Change Requests : change-request@zaval.org
 *   Feedback : feedback@zaval.org
 *   Other : info@zaval.org
 * 
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
 * 
 */
package org.zaval.awt;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class EmulatedTextField
extends Canvas
implements KeyListener, MouseListener, FocusListener, ActionListener
{
  protected static EmulatedTextField cursorOwner = null;

  public static final int LEFT  = 1;
  public static final int RIGHT = 2;

  protected StringBuffer buffer         = new StringBuffer("");
  Insets       insets         = new Insets (2,5,2,5);
  Point        textLocation   = new Point (0,0);
  Point        cursorLocation = new Point (0,0);
  Dimension    textSize       = new Dimension(0,0);
  Dimension    cursorSize     = new Dimension(0,0);
  Point        shift          = new Point(0,0);
  Color        cursorColor    = Color.black;
  int          cursorPos      = 0;
  int          align          = LEFT;
  boolean      is3D           = true;
  protected int          selPos = 0, selWidth = 0, startSel = 0;
  private PopupMenu menu;
  private int minSize = 0;

  public EmulatedTextField() {
    this(null);
  }

  public EmulatedTextField(int size) {
    this(null);
    minSize = size;
  }

  public void actionPerformed(ActionEvent e)
  {
    if(e.getActionCommand().equals("Cut")){
        blCopy();
        removeBlock();
    }
    else if(e.getActionCommand().equals("Copy")) blCopy();
    else if(e.getActionCommand().equals("Paste")) blPaste();
  }

  public EmulatedTextField(String s) {
    if (s != null) setText(s);
    enableInputMethods(true);
    addKeyListener(this);
    addMouseListener(this);
    addFocusListener(this);

    MenuItem mi1;
    menu = new PopupMenu();
    add(menu);
    menu.add(mi1 = new MenuItem("Cut"));
    mi1.addActionListener(this);
    mi1.setActionCommand("Cut");
    menu.add(mi1 = new MenuItem("Copy"));
    mi1.addActionListener(this);
    mi1.setActionCommand("Copy");
    menu.add(mi1 = new MenuItem("Paste"));
    mi1.addActionListener(this);
    mi1.setActionCommand("Paste");
    menu.addActionListener(this);
  }

  public void setText(String s) {
    buffer = new StringBuffer(s);
    setPos(0);
  }

  public String getText() {
    return buffer.toString();
  }

  public void setAlign(int a) {
    align = a;
  }

  public void getAlign(int a) {
    align = a;
    repaint();
  }

  public void setCursorColor(Color c) {
    cursorColor = c;
    if (hasFocus()) repaint();
  }

  public int getCursorPos() {
    return cursorPos;
  }

  public boolean hasFocus() {
    return (this == cursorOwner);
  }

  public void setInsets(Insets i)
  {
    insets.top    = i.top;
    insets.left   = i.left;
    insets.right  = i.right;
    insets.bottom = i.bottom;
    repaint();
  }

  public void set3D(boolean b)
  {
    if (b == is3D) return;
    is3D = b;
    repaint();
  }

  public boolean is3D() {
    return is3D;
  }

  public void keyTyped(KeyEvent ke)
  {
    int key = ke.getKeyCode();
    boolean isCtrl = (ke.getModifiers()&ke.CTRL_MASK) !=0;
    boolean isShift = (ke.getModifiers()&ke.SHIFT_MASK) !=0;
    if (controlKey(key, isShift)) return;

    if (ke.isActionKey()) return;
    char c = ke.getKeyChar();
    if(c==ke.CHAR_UNDEFINED) return;

    if(c==0x7F || c == 0x1B) return;
    if(ke.isControlDown()) return;
    if(c!='\b' && c!='\t'){
        removeBlock();
        if (write(c)) seek (1, false);
    }
  }

  public void keyPressed(KeyEvent ke)
  {
    int key = ke.getKeyCode();
    boolean isCtrl = (ke.getModifiers()&ke.CTRL_MASK) !=0;
    boolean isShift = (ke.getModifiers()&ke.SHIFT_MASK) !=0;
    if (blockKey(key, isCtrl, isShift)) return;
    if (controlKey(key, isShift)) return;
    if (key==ke.VK_F12) menu.show(this, 0, 0);
  }

  public void keyReleased(KeyEvent ke)
  {
  }
  
  protected boolean blockKey(int key, boolean isCtrlDown, boolean isShiftDown)
  {
    boolean shift = isShiftDown;
    if (isCtrlDown && (key == KeyEvent.VK_INSERT || key == KeyEvent.VK_C)) blCopy();
    else if ((isShiftDown && key == KeyEvent.VK_INSERT) || (isCtrlDown && key == KeyEvent.VK_V)) blPaste();
    else if ((isShiftDown && key == KeyEvent.VK_DELETE) || (isCtrlDown && key == KeyEvent.VK_X)) blDelete();
    else if (isCtrlDown && key==KeyEvent.VK_RIGHT) seek(nextSpace() - cursorPos, shift);
    else if (isCtrlDown && key==KeyEvent.VK_LEFT)  seek(prevSpace() - cursorPos, shift);
    else if (!isCtrlDown) return false;
    return true;
  }

  protected int prevSpace()
  {
    int j = cursorPos;
    if(j>=buffer.length()) j = buffer.length() - 1;
    for(;j>0 &&  Character.isSpaceChar(buffer.charAt(j));--j); 
    for(;j>0 && !Character.isSpaceChar(buffer.charAt(j));--j); 
    return j;
  }

  protected int nextSpace()
  {
    int j = cursorPos;
    int k = buffer.length();
    if(j>=k) return cursorPos;
    for(;j<k &&  Character.isSpaceChar(buffer.charAt(j));++j); 
    for(;j<k && !Character.isSpaceChar(buffer.charAt(j));++j); 
    return j;
  }

  protected boolean controlKey(int key, boolean shift)
  {
    boolean b = true;
    switch (key){
    case KeyEvent.VK_DOWN  :
    case KeyEvent.VK_RIGHT : 
      seek(1, shift);  
      break;
    case KeyEvent.VK_UP    :
    case KeyEvent.VK_LEFT  : 
      seek(-1, shift); 
      break;
    case KeyEvent.VK_END   : 
      seek2end(shift); 
      break;
    case KeyEvent.VK_HOME  : 
      seek2beg(shift); 
      break;
    case KeyEvent.VK_DELETE: 
      if (!isSelected()) remove(cursorPos, 1);
      else removeBlock();
      break;
    case KeyEvent.VK_BACK_SPACE : 
      if (!isSelected()){
        if (cursorPos > 0){
          seek(-1, shift);
          remove(cursorPos, 1);
        }
      }
      else removeBlock();
      break;
    case KeyEvent.VK_ENTER :
    case KeyEvent.VK_PAGE_UP   :
    case KeyEvent.VK_PAGE_DOWN : 
      break;
    case KeyEvent.VK_INSERT :   
      return false;
    case KeyEvent.VK_TAB : 
      return true;
    default    : 
      return false;
    }
    if (!shift) clear();
    return b;
  }

  public void blPaste()
  {
    String s = readFromClipboard();
    if (s != null){
      s = filterSymbols( s );
      removeBlock();
      insert(cursorPos, s);
      setPos(cursorPos + s.length());
    }
  }

  protected String filterSymbols( String s )
  {
     return s;
  }

  public String getSelectedText()
  {
    return buffer.toString().substring(selPos, selPos + selWidth);
  }

  public void blCopy() {
    if (!isSelected()) return ;
    writeToClipboard(buffer.toString().substring(selPos, selPos + selWidth));
  }

  public void blDelete()
  {
    if (!isSelected()) return;
    writeToClipboard(buffer.toString().substring(selPos, selPos + selWidth));
    removeBlock();
  }

  protected boolean inputKey(int key)
  {
    removeBlock();
    char ch = (char)key;
    if (write(key)) seek (1, false);
    return true;
  }

  protected void removeBlock()
  {
    if (isSelected()){
      remove(selPos, selWidth);
      setPos(selPos);
      clear();
    }
  }

  public void paint(Graphics g)
  {
    recalc();
    drawBorder(g);
    drawCursor(g);
    drawText  (g);
    drawBlock (g);
  }

  public Insets insets() {
    return insets;
  }

  protected void drawBlock(Graphics g)
  {
    int len = buffer.length();
    if (!isSelected()) return;
    String s = buffer.toString();
    FontMetrics fm = getFontMetrics(getFont());
    int beg = fm.stringWidth(s.substring(0, selPos));
    int end = fm.stringWidth(s.substring(0, selPos + selWidth));
    g.setColor(Color.blue);
    g.fillRect(textLocation.x + shift.x + beg,
               cursorLocation.y + shift.y,
               end - beg,
               textSize.height);
    g.setColor(Color.white);
    g.drawString (s.substring(selPos, selPos + selWidth), textLocation.x + shift.x + beg, textLocation.y + shift.y);
  }

  protected void drawText(Graphics g)
  {
    Dimension d = size();
    g.clipRect   (insets.left, insets.top, d.width-insets.left-insets.right,  d.height-insets.top-insets.bottom);
    g.setColor   (getForeground());
    g.drawString (buffer.toString(), textLocation.x + shift.x, textLocation.y + shift.y);
  }

  protected void drawCursor(Graphics g)
  {
    if (cursorOwner != this) return;
    g.setColor(cursorColor);
    g.fillRect(cursorLocation.x + shift.x, cursorLocation.y + shift.y, cursorSize.width, cursorSize.height);
  }

  public Rectangle getCursorShape()
  {
    return new Rectangle(cursorLocation.x + shift.x, cursorLocation.y + shift.y, 
        cursorSize.width, 
        cursorSize.height);
  }

  protected void drawBorder(Graphics g)
  {
    Dimension d = size();
    if (is3D)
    {
      g.setColor(Color.gray);
      g.drawLine(0, 0, d.width-1, 0);
      g.drawLine(0, 0, 0, d.height-1);
      g.setColor(Color.black);
      g.drawLine(1, 1, d.width-3, 1);
      g.drawLine(1, 1, 1, d.height-3);
      g.setColor(Color.white);
      g.drawLine(0, d.height-1, d.width-1, d.height-1);
      g.drawLine(d.width-1, 0, d.width-1, d.height-1);
    }
    else
    {
      g.setColor(Color.black);
      g.drawRect(0, 0, d.width-1, d.height-1);
    }
  }

  protected boolean seek(int shift, boolean b)
  {
    int len  = buffer.length();
    int npos = getValidPos(shift);

    if (npos > len || npos < 0) return false;

    if (!isSelected() && b)
      startSel = cursorPos;

    setPos(npos);

    if (b)
    {
      if (cursorPos < startSel) select(cursorPos, startSel - cursorPos);
      else                      select(startSel , cursorPos - startSel);
    }

    return true;
  }

  protected int getValidPos(int shift) {
    return cursorPos + shift;
  }

  protected boolean seek2end(boolean b) {
    seek(buffer.length() - cursorPos, b);
    return true;
  }

  protected boolean seek2beg(boolean b) {
    seek(-cursorPos, b);
    return true;
  }

  protected boolean write(int key) {
    buffer.insert(cursorPos, (char)key);
    return true;
  }

  protected boolean write(char key) {
    buffer.insert(cursorPos, key);
    return true;
  }

  protected void remove(int pos, int size)
  {
    if (pos > buffer.length() || pos < 0) return;
    if (pos + size > buffer.length()) size = buffer.length() - pos;
    String s = buffer.toString();
    s = s.substring(0, pos) + s.substring(pos+size);
    buffer = new StringBuffer(s);
    repaintPart ();
  }

  protected int getShift(int x, Dimension d, Insets i)
  {
    if (x < i.left)
      return (i.left - x);

    int w = d.width - i.right;
    if (x > w) return (w - x);

    return 0;
  }

  public void insert(int pos, String str)
  {
    if (pos > buffer.length() || pos < 0) return;
    String s = buffer.toString();
    s = s.substring(0, pos) + str + s.substring(pos);
    buffer = new StringBuffer(s);
    repaintPart ();
  }

    private long clickTime = 0;

    public void mouseClicked(MouseEvent e)
    {
    }

    public void mousePressed(MouseEvent e)
    {
    }

    public void selectAll()
    {
        select(0, buffer.length());
    }

    public void mouseReleased(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();

        if (cursorOwner != this) requestFocus();
        int pos = calcTextPos(x, y);
        if (pos >= 0 && pos != cursorPos) setPos(pos);

        if(e.isPopupTrigger() || e.isShiftDown()){
            menu.show(this, x, y);
            return;
        }
        else if (isSelected() && !e.isShiftDown()) clear();

        long t = System.currentTimeMillis();
        if ((t - clickTime) < 300) select(0, buffer.length());
        clickTime = t;
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

  protected int calcTextPos(int x, int y)
  {
     if (buffer.length() == 0) return 0;

     if (x > (shift.x + textSize.width + textLocation.x))
       return buffer.length();

     if ((shift.x + textLocation.x) > x)
       return 0;

     int w = x - shift.x;
     int p = (w * 100)/textSize.width;
     int l = buffer.length();
     int s = (l * p)/100;

     FontMetrics fm = getFontMetrics(getFont());
     String      ss = buffer.toString();
     for (int i = s, j = s + 1, k = i; i>=0 || j<l;)
     {
       if (k>=0 && k<l)
       {
         char   ch = buffer.charAt(k);
         String sx = ss.substring(0, k);
         int    sl = fm.stringWidth(sx) + shift.x + textLocation.x;
         int    cl = fm.charWidth  (ch);
         if (x >= sl && x < sl + cl)
         {
           if (x > (sl + cl/2)) return k+1;
           else                 return k;
           //return 1;
         }
       }

       if (k == j)
       {
         i--;
         j++;
         k = i;
       }
       else k = j;
     }

     return -1;
  }

  protected void setPos(int p) {
    cursorPos = p;
    repaintPart ();
  }

  protected Dimension calcSize()
  {
    Font f = getFont();
    if (f == null) return new Dimension (0,25);
    FontMetrics m = getFontMetrics(f);
    if(m==null){
       Toolkit k = Toolkit.getDefaultToolkit();
       m = k.getFontMetrics(f);
       if(m==null) return new Dimension (0,25);
    }
    Insets i = insets();
    String t = buffer.toString();
    return new Dimension (
        i.left + i.right + Math.max(minSize* m.stringWidth("W"), m.stringWidth(t)), 
        i.top + i.bottom + Math.max(m.getHeight(), 17));
  }

  protected boolean recalc()
  {
    Dimension d = size();
    if (d.width == 0 || d.height == 0) return false;

    Insets      i   = insets();
    FontMetrics m   = getFontMetrics(getFont());
    if (m == null) return false;

    String      s   = buffer.toString();
    String      sub = s.substring(0, cursorPos);
    int         sl  = m.stringWidth(sub);
    int         rh  = d.height - i.top - i.bottom;

    textSize.height = m.getHeight();
    textSize.width  = m.stringWidth(s);
    textLocation.y  = i.top + (rh + textSize.height)/2 - m.getDescent();

    cursorLocation.x = sl + i.left;
    cursorLocation.y = textLocation.y - textSize.height + m.getDescent();
    if (cursorLocation.y < i.top) cursorLocation.y = i.top;

    cursorSize.width  = 1;
    cursorSize.height = textSize.height;

    if ((cursorLocation.y + cursorSize.height) >= d.height - i.bottom)
      cursorSize.height = d.height - cursorLocation.y - i.bottom;

    switch (align)
    {
      case LEFT  :
      {
        textLocation.x   = i.left;
        cursorLocation.x = sl + i.left;
      } break;
      case RIGHT :
      {
        textLocation.x   = d.width - i.right - textSize.width;
        cursorLocation.x = sl +  textLocation.x;
      } break;
    }

    if ((cursorLocation.x + shift.x) < i.left)
      shift.x = i.left - cursorLocation.x;
    else
    {
      int w = d.width - i.right;
      if ((cursorLocation.x + shift.x) > w) shift.x = w - cursorLocation.x;
    }

    return true;
  }

  public void resize(int w, int h) {
    shift.x = 0;
    super.resize(w, h);
  }

  protected void otdaiFocusTvojuMat() {
    cursorOwner = null;
    repaint();
  }

    public void focusGained(FocusEvent e)
    {
        if (cursorOwner != null) cursorOwner.otdaiFocusTvojuMat();
        cursorOwner = this;
        if (buffer != null){
           setPos(buffer.length());
           select(0, buffer.length());
        }
        repaint();
    }

    public void focusLost(FocusEvent e)
    {
        if (cursorOwner == this){
          cursorOwner = null;
          clear();
          repaint();
        }
    }

  protected void repaintPart ()
  {
    Insets    i = insets();
    Dimension d = size  ();
    repaint(i.left, i.top, d.width - i.right - i.left + 1, d.height - i.bottom - i.top + 1);
  }

  public Dimension preferredSize() {
    return calcSize();
  }

  public void select(int pos, int w)
  {
    if (selPos == pos && w == selWidth) return;
    selPos   = pos;
    selWidth = w;
    repaintPart();
  }

  public boolean isSelected ()
  {
    int len = buffer.length();
    if (selPos <  0 || selPos >= len || (selPos + selWidth) > len || selWidth == 0) return false;
    return true;
  }

  protected void clear ()
  {
    selWidth = 0;
    repaintPart();
  }

  public boolean mouseDrag(Event e, int x, int y)
  {
    int pos = calcTextPos(x, y);
    if (pos >= 0)
    {
      if (pos < cursorPos)
        select(pos, cursorPos - pos);
      else
        select(cursorPos, pos - cursorPos);
    }
    return super.mouseDrag(e, x, y);
  }

  private static String clipboard;

/**
 *
 * Contributed by <a href="mailto:morten@bilpriser.dk">Morten Raahede Knudsen</a>.
 */
  
  protected static synchronized void writeToClipboard(String s)
  {
    java.awt.datatransfer.Clipboard c = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
    java.awt.datatransfer.StringSelection s2 = new java.awt.datatransfer.StringSelection(s);
    c.setContents(s2,s2);
  }

  protected static synchronized String readFromClipboard()
  {
    java.awt.datatransfer.Clipboard c = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
    java.awt.datatransfer.Transferable t = c.getContents("e");

    if(t.isDataFlavorSupported(java.awt.datatransfer.DataFlavor.stringFlavor))
      try{
        return (String)t.getTransferData(java.awt.datatransfer.DataFlavor.stringFlavor);
      }
      catch(Exception ex){
      }
    return "";
  }
}
