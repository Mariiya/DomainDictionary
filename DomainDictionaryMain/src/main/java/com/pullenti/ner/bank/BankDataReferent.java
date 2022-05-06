/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.bank;

/**
 * Банковские данные (реквизиты)
 * 
 */
public class BankDataReferent extends com.pullenti.ner.Referent {

    public BankDataReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.bank.internal.MetaBank.globalMeta);
    }

    /**
     * Имя типа сущности TypeName ("BANKDATA")
     */
    public static final String OBJ_TYPENAME = "BANKDATA";

    /**
     * Имя атрибута - реквизит (обычно UriReferent)
     */
    public static final String ATTR_ITEM = "ITEM";

    /**
     * Имя атрибута - банк (обычно OrganizationReferent)
     */
    public static final String ATTR_BANK = "BANK";

    /**
     * Имя атрибута - банк К/С
     */
    public static final String ATTR_CORBANK = "CORBANK";

    /**
     * Имя атрибута - разное
     */
    public static final String ATTR_MISC = "MISC";

    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (s.getValue() instanceof com.pullenti.ner.uri.UriReferent) {
                if (com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.uri.UriReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.uri.UriReferent.class)).getScheme(), "Р/С")) {
                    res.append(s.getValue().toString());
                    break;
                }
            }
        }
        if (res.length() == 0) 
            res.append((String)com.pullenti.unisharp.Utils.notnull(this.getStringValue(ATTR_ITEM), "?"));
        if (getParentReferent() != null && !shortVariant && (lev < 20)) 
            res.append(", ").append(this.getParentReferent().toStringEx(true, lang, lev + 1));
        return res.toString();
    }

    @Override
    public com.pullenti.ner.Referent getParentReferent() {
        return (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_BANK), com.pullenti.ner.Referent.class);
    }


    public String findValue(String schema) {
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (s.getValue() instanceof com.pullenti.ner.uri.UriReferent) {
                com.pullenti.ner.uri.UriReferent ur = (com.pullenti.ner.uri.UriReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.uri.UriReferent.class);
                if (com.pullenti.unisharp.Utils.stringsEq(ur.getScheme(), schema)) 
                    return ur.getValue();
            }
        }
        return null;
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType typ) {
        BankDataReferent bd = (BankDataReferent)com.pullenti.unisharp.Utils.cast(obj, BankDataReferent.class);
        if (bd == null) 
            return false;
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_ITEM)) {
                com.pullenti.ner.uri.UriReferent ur = (com.pullenti.ner.uri.UriReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.uri.UriReferent.class);
                String val = bd.findValue(ur.getScheme());
                if (val != null) {
                    if (com.pullenti.unisharp.Utils.stringsNe(val, ur.getValue())) 
                        return false;
                }
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_BANK)) {
                com.pullenti.ner.Referent b1 = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class);
                com.pullenti.ner.Referent b2 = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(bd.getSlotValue(ATTR_BANK), com.pullenti.ner.Referent.class);
                if (b2 != null) {
                    if (b1 != b2 && !b1.canBeEquals(b2, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) 
                        return false;
                }
            }
        }
        return true;
    }
}
