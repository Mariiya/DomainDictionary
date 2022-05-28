/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.booklink.internal;

public class MetaBookLinkRef extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        globalMeta = new MetaBookLinkRef();
        globalMeta.addFeature(com.pullenti.ner.booklink.BookLinkRefReferent.ATTR_BOOK, "Источник", 1, 1);
        globalMeta.addFeature(com.pullenti.ner.booklink.BookLinkRefReferent.ATTR_TYPE, "Тип", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.booklink.BookLinkRefReferent.ATTR_PAGES, "Страницы", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.booklink.BookLinkRefReferent.ATTR_NUMBER, "Номер", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.booklink.BookLinkRefReferent.ATTR_MISC, "Разное", 0, 0);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.booklink.BookLinkRefReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Ссылка на внешний источник в тексте";
    }


    public static String IMAGEID = "booklinkref";

    public static String IMAGEIDINLINE = "booklinkrefinline";

    public static String IMAGEIDLAST = "booklinkreflast";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        com.pullenti.ner.booklink.BookLinkRefReferent rr = (com.pullenti.ner.booklink.BookLinkRefReferent)com.pullenti.unisharp.Utils.cast(obj, com.pullenti.ner.booklink.BookLinkRefReferent.class);
        if (rr != null) {
            if (rr.getTyp() == com.pullenti.ner.booklink.BookLinkRefType.INLINE) 
                return IMAGEIDINLINE;
        }
        return IMAGEID;
    }

    public static MetaBookLinkRef globalMeta;
    public MetaBookLinkRef() {
        super();
    }
}
