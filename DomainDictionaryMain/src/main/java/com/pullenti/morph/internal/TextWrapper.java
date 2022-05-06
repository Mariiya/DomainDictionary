/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph.internal;

// Введено для ускорения Питона!
public class TextWrapper {

    public TextWrapper(String txt, boolean toUpper) {
        if(_globalInstance == null) return;
        if (toUpper && txt != null) 
            text = txt.toUpperCase();
        else 
            text = txt;
        length = (txt == null ? 0 : txt.length());
        chars = new CharsList(txt);
    }

    @Override
    public String toString() {
        return text.toString();
    }

    public static class CharsList {
    
        public CharsList(String txt) {
            text = txt;
        }
    
        public String text;
    
        public UnicodeInfo getIndexerItem(int ind) {
            return UnicodeInfo.ALLCHARS.get((int)text.charAt(ind));
        }
    
        public CharsList() {
        }
    }


    public CharsList chars;

    public String text;

    public int length;
    public TextWrapper() {
    }
    public static TextWrapper _globalInstance;
    
    static {
        try { _globalInstance = new TextWrapper(); } 
        catch(Exception e) { }
    }
}
