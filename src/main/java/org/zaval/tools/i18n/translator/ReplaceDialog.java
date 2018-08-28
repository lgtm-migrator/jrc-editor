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
 * Copyright (C) 2001-2004  Zaval Creative Engineering Group (http://www.zaval.org)
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
package org.zaval.tools.i18n.translator;

import org.zaval.awt.*;
import org.zaval.awt.dialog.*;

import java.io.File;
import java.awt.*;
import java.util.*;

public class ReplaceDialog
extends EditDialog
{
   IERadioButton regex;
   IERadioButton exact;
   IERadioButton cg2[];

   IECheckbox cases;
   IECheckbox prompt;
   IECheckbox all;
   EmulatedTextField replaceTo;
   IELabel label;

   public void setReplaceLabel(String s)
   {
       label.setText(s);
   }

   public void setRMGroupLabels(String s1, String s3)
   {
       regex.setLabel(s1);
       exact.setLabel(s3);
   }

   public void setCPALabels(String s1, String s2, String s3)
   {
       cases.setLabel(s1);
       prompt.setLabel(s2);
       all.setLabel(s3);
   }

   public boolean isExactMatching()
   {
      return exact.getState();
   }

   public boolean isRegexMatching()
   {
      return regex.getState();
   }

   public boolean isCaseSensitive()
   {
      return cases.getState();
   }

   public boolean isPromptRequired()
   {
      return prompt.getState();
   }

   public boolean isReplaceAll()
   {
      return all.getState();
   }

   public String getReplaceTo()
   {
      return replaceTo.getText();
   }

   public ReplaceDialog(Frame f, String s, boolean b, Component l)
   {
      super(f, s, b, l);

      cg2 = new IERadioButton[2];
      cg2[0] = regex = new IERadioButton("", false);
      cg2[1] = exact = new IERadioButton("", true);
      cases = new IECheckbox("");
      cases.setState(true);
      prompt= new IECheckbox("");
      prompt.setState(true);
      all   = new IECheckbox("");
      all.setState(false);

      label = new IELabel("To:");
      replaceTo = new EmulatedTextField(20);
      constrain(this, label, 0,1,1,1,
         GridBagConstraints.NONE, GridBagConstraints.WEST,
         0.0,0.0,0,5,5,5);
      constrain(this, replaceTo, 1,1,1,1,
         GridBagConstraints.HORIZONTAL,
         GridBagConstraints.WEST,
         1.0,0.0,0,5,5,0);

      Panel p = new Panel();
      p.setLayout(new GridBagLayout());

      Panel p2 = new BorderedPanel();
      p2.setLayout(new GridBagLayout());
      constrain(p2, exact, 1,0,1,1,
         GridBagConstraints.NONE,GridBagConstraints.NORTHWEST,
         0.0,0.0,0,5,5,0);
      constrain(p2, regex, 1,1,1,1,
         GridBagConstraints.NONE,GridBagConstraints.NORTHWEST,
         0.0,0.0,0,5,5,0);
      constrain(p, p2, 0,1,2,1,
         GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,
         1.0,1.0,5,0,0,0);

      constrain(p, cases, 0,2,2,1,
         GridBagConstraints.NONE,GridBagConstraints.NORTHWEST,
         0.0,0.0,5,5,5,0);
      constrain(p, prompt, 0,3,2,1,
         GridBagConstraints.NONE,GridBagConstraints.NORTHWEST,
         0.0,0.0,0,5,5,0);
      constrain(p, all, 0,4,2,1,
         GridBagConstraints.NONE,GridBagConstraints.NORTHWEST,
         0.0,0.0,0,5,5,5);

      constrain(this, p, 0,2,2,1,
         GridBagConstraints.BOTH,GridBagConstraints.NORTHWEST,
         1.0,1.0,5,5,5,5);
   }

   private void applyTo(IERadioButton[] group, IERadioButton b)
   {
      int j;
      for(j=0;j<group.length;++j) if(group[j]==b) break;
      if(j>=group.length) return;
      for(j=0;j<group.length;++j) if(group[j]!=b) group[j].setState(false);
   }

   public boolean action(Event evt, Object what)
   {
     if(evt.target instanceof IERadioButton){
         applyTo(cg2, (IERadioButton)evt.target);
         return true;
     }
     return super.action(evt, what);
   }
}
