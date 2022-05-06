/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.mail;

/**
 * Сущность - блок письма
 * 
 */
public class MailReferent extends com.pullenti.ner.Referent {

    public MailReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.mail.internal.MetaLetter.globalMeta);
    }

    /**
     * Имя типа сущности TypeName ("MAIL")
     */
    public static final String OBJ_TYPENAME = "MAIL";

    /**
     * Имя атрибута - тип блока (MailKind)
     */
    public static final String ATTR_KIND = "TYPE";

    /**
     * Имя атрибута - текст блока
     */
    public static final String ATTR_TEXT = "TEXT";

    /**
     * Имя атрибута - ссылка на сущность
     */
    public static final String ATTR_REF = "REF";

    public MailKind getKind() {
        String val = this.getStringValue(ATTR_KIND);
        try {
            if (val != null) 
                return (MailKind)MailKind.of(val);
        } catch (Exception ex1763) {
        }
        return MailKind.UNDEFINED;
    }

    public MailKind setKind(MailKind value) {
        this.addSlot(ATTR_KIND, value.toString().toUpperCase(), true, 0);
        return value;
    }


    public String getText() {
        return this.getStringValue(ATTR_TEXT);
    }

    public String setText(String value) {
        this.addSlot(ATTR_TEXT, value, true, 0);
        return value;
    }


    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        res.append(this.getKind().toString()).append(": ");
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_REF) && (s.getValue() instanceof com.pullenti.ner.Referent)) 
                res.append(((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class)).toStringEx(true, lang, lev + 1)).append(", ");
        }
        if (res.length() < 100) {
            String str = (String)com.pullenti.unisharp.Utils.notnull(getText(), "");
            str = str.replace('\r', ' ').replace('\n', ' ');
            if (str.length() > 100) 
                str = str.substring(0, 0 + 100) + "...";
            res.append(str);
        }
        return res.toString();
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType typ) {
        return obj == this;
    }

    public void addRef(com.pullenti.ner.Referent r, int lev) {
        if (r == null || lev > 4) 
            return;
        if ((((r instanceof com.pullenti.ner.person.PersonReferent) || (r instanceof com.pullenti.ner.person.PersonPropertyReferent) || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "ORGANIZATION")) || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "PHONE") || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "URI")) || (r instanceof com.pullenti.ner.geo.GeoReferent) || (r instanceof com.pullenti.ner.address.AddressReferent)) 
            this.addSlot(ATTR_REF, r, false, 0);
        for (com.pullenti.ner.Slot s : r.getSlots()) {
            if (s.getValue() instanceof com.pullenti.ner.Referent) 
                this.addRef((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class), lev + 1);
        }
    }

    public static MailReferent _new1759(MailKind _arg1) {
        MailReferent res = new MailReferent();
        res.setKind(_arg1);
        return res;
    }
}
