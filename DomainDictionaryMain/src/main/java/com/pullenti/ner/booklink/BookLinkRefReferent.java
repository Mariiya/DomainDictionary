/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.booklink;

/**
 * Ссылка на ССЫЛКУ (BookLinkReferent или DecreeReferent)
 * 
 */
public class BookLinkRefReferent extends com.pullenti.ner.Referent {

    /**
     * Имя типа сущности TypeName ("BOOKLINKREF")
     */
    public static final String OBJ_TYPENAME = "BOOKLINKREF";

    /**
     * Имя атрибута - источник (BookLinkReferent или DecreeReferent)
     */
    public static final String ATTR_BOOK = "BOOK";

    /**
     * Имя атрибута - тип (BookLinkRefType)
     */
    public static final String ATTR_TYPE = "TYPE";

    /**
     * Имя атрибута - ссылка на страницу или диапазон страниц
     */
    public static final String ATTR_PAGES = "PAGES";

    /**
     * Имя атрибута - порядковый номер в списке
     */
    public static final String ATTR_NUMBER = "NUMBER";

    /**
     * Имя атрибута - разное
     */
    public static final String ATTR_MISC = "MISC";

    public BookLinkRefReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.booklink.internal.MetaBookLinkRef.globalMeta);
    }

    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        if (getNumber() != null) 
            res.append("[").append(this.getNumber()).append("] ");
        if (getPages() != null) 
            res.append((lang != null && lang.isEn() ? "pages" : "стр.")).append(" ").append(this.getPages()).append("; ");
        com.pullenti.ner.Referent _book = getBook();
        if (_book == null) 
            res.append("?");
        else 
            res.append(_book.toStringEx(shortVariant, lang, lev));
        return res.toString();
    }

    @Override
    public com.pullenti.ner.Referent getParentReferent() {
        return (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_BOOK), com.pullenti.ner.Referent.class);
    }


    public BookLinkRefType getTyp() {
        String val = this.getStringValue(ATTR_TYPE);
        if (val == null) 
            return BookLinkRefType.UNDEFINED;
        try {
            return (BookLinkRefType)BookLinkRefType.of(val);
        } catch (Exception ex395) {
        }
        return BookLinkRefType.UNDEFINED;
    }

    public BookLinkRefType setTyp(BookLinkRefType value) {
        this.addSlot(ATTR_TYPE, value.toString(), true, 0);
        return value;
    }


    public com.pullenti.ner.Referent getBook() {
        return (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_BOOK), com.pullenti.ner.Referent.class);
    }

    public com.pullenti.ner.Referent setBook(com.pullenti.ner.Referent value) {
        this.addSlot(ATTR_BOOK, value, true, 0);
        return value;
    }


    public String getNumber() {
        return this.getStringValue(ATTR_NUMBER);
    }

    public String setNumber(String value) {
        String num = value;
        if (num != null && num.indexOf('-') > 0) 
            num = num.replace(" - ", "-");
        this.addSlot(ATTR_NUMBER, num, true, 0);
        return value;
    }


    public String getPages() {
        return this.getStringValue(ATTR_PAGES);
    }

    public String setPages(String value) {
        this.addSlot(ATTR_PAGES, value, true, 0);
        return value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType _typ) {
        BookLinkRefReferent r = (BookLinkRefReferent)com.pullenti.unisharp.Utils.cast(obj, BookLinkRefReferent.class);
        if (r == null) 
            return false;
        if (getBook() != r.getBook()) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(getNumber(), r.getNumber())) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(getPages(), r.getPages())) 
            return false;
        if ((this.getTyp() == BookLinkRefType.INLINE) != (r.getTyp() == BookLinkRefType.INLINE)) 
            return false;
        return true;
    }

    /**
     * Возвращает разницу номеров r2 - r1, иначе null, если номеров нет
     * @param r1 первая ссылка
     * @param r2 вторая ссылка
     * @return 
     */
    public static Integer getNumberDiff(com.pullenti.ner.Referent r1, com.pullenti.ner.Referent r2) {
        String num1 = r1.getStringValue(ATTR_NUMBER);
        String num2 = r2.getStringValue(ATTR_NUMBER);
        if (num1 == null || num2 == null) 
            return null;
        int n1;
        int n2;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapn1396 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres397 = com.pullenti.unisharp.Utils.parseInteger(num1, 0, null, wrapn1396);
        com.pullenti.unisharp.Outargwrapper<Integer> wrapn2398 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres399 = com.pullenti.unisharp.Utils.parseInteger(num2, 0, null, wrapn2398);
        n1 = (wrapn1396.value != null ? wrapn1396.value : 0);
        n2 = (wrapn2398.value != null ? wrapn2398.value : 0);
        if (!inoutres397 || !inoutres399) 
            return null;
        return n2 - n1;
    }

    public static BookLinkRefReferent _new387(com.pullenti.ner.Referent _arg1) {
        BookLinkRefReferent res = new BookLinkRefReferent();
        res.setBook(_arg1);
        return res;
    }
}
