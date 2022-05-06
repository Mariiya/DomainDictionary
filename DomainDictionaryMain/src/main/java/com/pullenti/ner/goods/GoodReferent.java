/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.goods;

/**
 * Товар
 * 
 */
public class GoodReferent extends com.pullenti.ner.Referent {

    public GoodReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.goods.internal.GoodMeta.GLOBALMETA);
    }

    /**
     * Имя типа сущности TypeName ("GOOD")
     */
    public static final String OBJ_TYPENAME = "GOOD";

    /**
     * Имя атрибута - атрибут (характеристика) товара (GoodAttributeReferent)
     */
    public static final String ATTR_ATTR = "ATTR";

    public java.util.Collection<GoodAttributeReferent> getAttrs() {
        java.util.ArrayList<GoodAttributeReferent> res = new java.util.ArrayList<GoodAttributeReferent>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (s.getValue() instanceof GoodAttributeReferent) 
                res.add((GoodAttributeReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), GoodAttributeReferent.class));
        }
        return res;
    }


    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        for (GoodAttributeReferent a : getAttrs()) {
            res.append(a.toStringEx(true, lang, lev)).append(" ");
        }
        return res.toString().trim();
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType typ) {
        return this == obj;
    }

    @Override
    public com.pullenti.ner.core.IntOntologyItem createOntologyItem() {
        com.pullenti.ner.core.IntOntologyItem re = new com.pullenti.ner.core.IntOntologyItem(this);
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_ATTR)) 
                re.termins.add(new com.pullenti.ner.core.Termin(s.getValue().toString(), null, false));
        }
        return re;
    }
}
