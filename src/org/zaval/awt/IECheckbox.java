package org.zaval.awt;

import java.awt.*;
//import java.io.Serializable;

public class IECheckbox
extends BaseCheckbox
{
  public IECheckbox(){}

  public IECheckbox(String label) {
    this(label, false);
  }

  public IECheckbox(String label, boolean state) {
    this(label, LEFT, state);
  }

  public IECheckbox(String label, int align, boolean state)
  {
    setLabel(label);
    setState(state);
    setAlign(align);
  }

  public void paint(Graphics g, int x, int y, int width, int height)
  {
    int yy = posY + height;
    int xx = posX + width;

    if(isEnabled())
    {
      if(mouse_down)
        g.setColor(Color.lightGray);
      else
        g.setColor(Color.white);
    }
    else
      g.setColor(Color.lightGray);

    g.fillRect(x, y, width, height);

    g.setColor(Color.gray);
    g.drawLine(x, y, x, yy);
    g.drawLine(x, y, xx, y);
    g.setColor(Color.white);
    g.drawLine(xx, y, xx, yy);
    g.drawLine(xx, yy, x, yy);
    g.setColor(Color.black);
    g.drawLine(x+1, y + 1, x+1, yy-2);
    g.drawLine(x+1, y + 1, xx-2, y + 1);
    g.setColor(Color.lightGray);
    g.drawLine(x+1, yy - 1, xx-1, yy - 1);
    g.drawLine(xx-1, y + 1, xx-1, yy - 1);


    if(state){
      if(isEnabled())
        g.setColor(Color.black);
      else
        g.setColor(Color.gray);

      g.drawLine(x+3, y+5, x+3, y+7);
      g.drawLine(x+4, y+6, x+4, y+8);
      g.drawLine(x+5, y+7, x+5, y+9);
      g.drawLine(x+6, y+6, x+6, y+8);
      g.drawLine(x+7, y+5, x+7, y+7);
      g.drawLine(x+8, y+4, x+8, y+6);
      g.drawLine(x+9, y+3, x+9, y+5);
    }
   }
}
