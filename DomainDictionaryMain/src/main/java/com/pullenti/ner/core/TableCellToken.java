/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

// Токен - ячейка таблицы
public class TableCellToken extends com.pullenti.ner.MetaToken {

    public TableCellToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public int colSpan = 1;

    public int rowSpan = 1;

    public boolean eoc = false;

    public java.util.ArrayList<TableCellToken> getLines() {
        java.util.ArrayList<TableCellToken> res = new java.util.ArrayList<TableCellToken>();
        for (com.pullenti.ner.Token t = getBeginToken(); t != null && t.getEndChar() <= getEndChar(); t = t.getNext()) {
            com.pullenti.ner.Token t0 = t;
            com.pullenti.ner.Token t1 = t;
            for (; t != null && t.getEndChar() <= getEndChar(); t = t.getNext()) {
                t1 = t;
                if (t.isNewlineAfter()) {
                    if ((t.getNext() != null && t.getNext().getEndChar() <= getEndChar() && t.getNext().chars.isLetter()) && t.getNext().chars.isAllLower() && !t0.chars.isAllLower()) 
                        continue;
                    break;
                }
            }
            res.add(new TableCellToken(t0, t1));
            t = t1;
        }
        return res;
    }


    public static TableCellToken _new582(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, int _arg3, int _arg4) {
        TableCellToken res = new TableCellToken(_arg1, _arg2);
        res.rowSpan = _arg3;
        res.colSpan = _arg4;
        return res;
    }
    public TableCellToken() {
        super();
    }
}
