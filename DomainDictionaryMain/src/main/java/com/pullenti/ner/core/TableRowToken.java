/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

// Токен - строка таблицы из текста
public class TableRowToken extends com.pullenti.ner.MetaToken {

    public TableRowToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public java.util.ArrayList<TableCellToken> cells = new java.util.ArrayList<TableCellToken>();

    public boolean eor = false;

    public boolean lastRow = false;

    @Override
    public String toString() {
        return ("ROW (" + cells.size() + " cells) : " + this.getSourceText());
    }
    public TableRowToken() {
        super();
    }
}
