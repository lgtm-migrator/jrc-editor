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
package org.zaval.tools.i18n.translator;

import java.io.*;
import java.awt.*;
import java.util.*;
import org.zaval.util.SafeResourceBundle;

public class Main
{
   public static void main( String arg[] )
   throws Exception
   {
      String altDir = System.getProperty("my.root.dir");
      File f = new File(altDir!=null ? altDir : ".");
      String path = f.getAbsolutePath();
      if(path.endsWith(".")) path = path.substring( 0, path.length() - 1 );
      path += "/images/";

      Translator t = null;
      if(arg.length>0){
          t = new Translator( 
             path, 
             new SafeResourceBundle("jrc-editor", Locale.getDefault()),
             arg[0] );
      }
      else
          t = new Translator( 
             path, 
             new SafeResourceBundle("jrc-editor", Locale.getDefault()) );

      Dimension gdz = Toolkit.getDefaultToolkit().getScreenSize();
      int optimalX = gdz.width / 4 * 3;
      int optimalY = gdz.height / 4 * 3;
      t.move( (gdz.width - optimalX) / 2, (gdz.height - optimalY) / 2 );
      t.resize( optimalX, optimalY );
      t.show();
   }
}
