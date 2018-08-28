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

import java.util.*;

class BundleSet
implements TranslatorConstants
{
    private Vector items;
    private Vector lng;
    private Hashtable nameCache;
    
    BundleSet()
    {
        items = new Vector();
        lng = new Vector();
        nameCache = new Hashtable();
    }

    void addLanguage(String slng, String desc)
    {
        if(getLanguage(slng)!=null) return;
        LangItem newl = new LangItem(slng, desc);
        lng.addElement(newl);
        correctFileName(newl);
    }

    int getLangCount()
    {
        return lng.size();
    }

    LangItem getLanguage(int idx)
    {
        return (LangItem)lng.elementAt(idx);
    }

    LangItem getLanguage(String lng)
    {
        int j = getLangIndex(lng);
        return j < 0 ? null : getLanguage(j);
    }

    int getLangIndex(String lng)
    {
        int j, k = getLangCount();
        for(j=0;j<k;++j){
            LangItem lx = getLanguage(j);
            if(lx.getLangId().equals(lng)) return j;
        }
        return -1;
    }

    LangItem[] getLanguageByDescription(String lng)
    {
        int j, k = getLangCount();
        Vector ask = new Vector();
        for(j=0;j<k;++j){
            LangItem lx = getLanguage(j);
            if(lx.getLangDescription().equals(lng)) ask.addElement(lx);
        }
        if(ask.size()==0) return null;
        LangItem[] li = new LangItem[k=ask.size()];
        for(j=0;j<k;++j) li[j] = (LangItem)ask.elementAt(j);
        return li;
    }

    int getItemCount()
    {
        return items.size();
    }

    BundleItem getItem(int idx)
    {
        return (BundleItem)items.elementAt(idx);
    }

    BundleItem getItem(String key)
    {
    //  int j = getItemIndex(key);
    //  if(j<0) return null;
    //  return getItem(j);
        return (BundleItem)nameCache.get(key);
    }

    int getItemIndex(String key)
    {
        int j, k = getItemCount();
        for(j=k-1;j>=0;--j){
            BundleItem bi = getItem(j);
            if(bi.getId().equals(key)) return j;
        }
        return -1;
    }

    BundleItem addKey(String key)
    {
        int j, k = getItemCount(), q;
        BundleItem ask = (BundleItem)getItem(key);
    /*  for(j=0;j<k;++j){
            BundleItem bi = getItem(j);
            q = bi.getId().compareTo(key);
            if(q==0) return bi;
            else if(q>0){
                items.insertElementAt(ask = new BundleItem(key), j);
                return ask;
            }
        } */
        if(ask==null){ 
            items.addElement(ask = new BundleItem(key));
            nameCache.put(key, ask);
        }
        return ask;
    }

    void removeKey(String key)
    {
        int j = getItemIndex(key);
        if(j>=0) items.removeElementAt(j);
        nameCache.remove(key);
    }

    Enumeration getKeysBeginningWith(String key)
    {
        Vector v = new Vector();
        for(int j=0;j<getItemCount();++j){
            BundleItem bi = getItem(j);
            if(bi.getId().startsWith(key)) v.addElement(bi);
        }
        return v.elements();
    }

    void removeKeysBeginningWith(String key)
    {
        for(int j=0;j<getItemCount();++j){
            BundleItem bi = getItem(j);
            if(bi.getId().startsWith(key)){
                removeKey(bi.getId());
                --j;
            }
        }
    }

    void updateValue(String key, String lang, String value)
    {
        BundleItem bi = getItem(key);
        if(bi!=null) bi.setTranslation(lang, value);
    }
 
    private Locale parseLanguage( String suffix )
    {
       if ( suffix == null || suffix.length() == 0 ) return null;
       int undInd = suffix.indexOf( '_' );
       String sl = suffix;
       String sc = "";
       if ( undInd > 0 ){
          sl = suffix.substring( 0, undInd );
          sc = suffix.substring( undInd + 1 );
       }
       return new Locale( sl, sc );
    }
 
    void addLanguage(String lng)
    {
        Locale loc = parseLanguage( lng );
        if ( loc != null ){
            String desc = loc.getDisplayLanguage();
            String sCountry = loc.getDisplayCountry();
            if ( sCountry != null && sCountry.length() > 0 )
                desc += " (" + sCountry + ")";
            addLanguage(lng, desc);
        }
    }

    private String makeLine(String key, String val)
    {
        if(val==null) return null;
        int capacity = key.length() + val.length() + 2;
        StringBuffer sb = new StringBuffer(capacity);
        sb.append(key);
        sb.append('=');
        sb.append(val);
        return sb.toString();
    }

    Vector store(String lng)
    {
        LangItem lang = getLanguage(lng);
        Vector lines = new Vector();
        lines.addElement("# Java Resource Bundle");
        lines.addElement("# Modified by Zaval JRC Editor (C) Zaval CE Group");
        lines.addElement("# http://www.zaval.org/products/jrc-editor/");
      //lines.addElement(makeLine(DEFAULT_LANG_KEY, lang.getLangDescription()));
        lines.addElement("#");
        lines.addElement("");
 
        for(int j=0;j<getItemCount();++j){
           BundleItem bi = getItem(j);
           if(bi.getComment()!=null) lines.addElement("#" + bi.getComment());
           if(bi.getTranslation(lng)==null) continue;
           lines.addElement(makeLine(bi.getId(), bi.getTranslation(lng)));
        }
        return lines;
    }

    private void correctFileName(LangItem lang)
    {
        if(getLangCount() < 1) return;
        LangItem lan0 = getLanguage(0);
        if(lan0 == lang) return;
        if(lang.getLangFile()!=null) return;
        if(lan0.getLangFile()==null) return;
        String base = lan0.getLangFile();
        int j = base.lastIndexOf('.');
        if(j<0) return;
        base = base.substring(0, j) + "_" + lang.getLangId() + base.substring(j);
        lang.setLangFile(base);
    }

    public void resort()
    {
        Collections.sort(items, new Comparator(){
            public int compare(Object o1, Object o2){
                return ((BundleItem)o1).getId().compareTo(((BundleItem)o2).getId());
            }
            public boolean equals(Object obj){
                return this == obj;
            }
        });
    }
}
