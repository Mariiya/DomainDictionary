/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.decree;

/**
 * Значение изменения структурного элемента НПА
 */
public class DecreeChangeValueReferent extends com.pullenti.ner.Referent {

    public DecreeChangeValueReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.decree.internal.MetaDecreeChangeValue.GLOBALMETA);
    }

    /**
     * Имя типа сущности TypeName ("DECREECHANGEVALUE")
     */
    public static final String OBJ_TYPENAME = "DECREECHANGEVALUE";

    /**
     * Имя атрибута - тип (DecreeChangeValueKind)
     */
    public static final String ATTR_KIND = "KIND";

    /**
     * Имя атрибута - значение
     */
    public static final String ATTR_VALUE = "VALUE";

    /**
     * Имя атрибута - номер
     */
    public static final String ATTR_NUMBER = "NUMBER";

    /**
     * Имя атрибута - новый структурный элемент
     */
    public static final String ATTR_NEWITEM = "NEWITEM";

    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        java.util.ArrayList<String> nws = getNewItems();
        if (nws.size() > 0) {
            for (String p : nws) {
                DecreePartReferent dpr = new DecreePartReferent();
                int ii = p.indexOf(' ');
                if (ii < 0) 
                    dpr.addSlot(p, "", false, 0);
                else 
                    dpr.addSlot(p.substring(0, 0 + ii), p.substring(ii + 1), false, 0);
                res.append(" новый '").append(dpr.toStringEx(true, null, 0)).append("'");
            }
        }
        if (getKind() != DecreeChangeValueKind.UNDEFINED) 
            res.append(" ").append(com.pullenti.ner.decree.internal.MetaDecreeChangeValue.KINDFEATURE.convertInnerValueToOuterValue(this.getKind().toString(), lang).toString().toLowerCase());
        if (getNumber() != null) 
            res.append(" ").append(this.getNumber());
        String val = getValue();
        if (val != null) {
            if (val.length() > 100) 
                val = val.substring(0, 0 + 100) + "...";
            res.append(" '").append(val).append("'");
            com.pullenti.unisharp.Utils.replace(res, '\n', ' ');
            com.pullenti.unisharp.Utils.replace(res, '\r', ' ');
        }
        return res.toString().trim();
    }

    public DecreeChangeValueKind getKind() {
        String s = this.getStringValue(ATTR_KIND);
        if (s == null) 
            return DecreeChangeValueKind.UNDEFINED;
        try {
            Object res = DecreeChangeValueKind.of(s);
            if (res instanceof DecreeChangeValueKind) 
                return (DecreeChangeValueKind)res;
        } catch (Exception ex1185) {
        }
        return DecreeChangeValueKind.UNDEFINED;
    }

    public DecreeChangeValueKind setKind(DecreeChangeValueKind _value) {
        if (_value != DecreeChangeValueKind.UNDEFINED) 
            this.addSlot(ATTR_KIND, _value.toString(), true, 0);
        return _value;
    }


    public String getValue() {
        return this.getStringValue(ATTR_VALUE);
    }

    public String setValue(String _value) {
        this.addSlot(ATTR_VALUE, _value, true, 0);
        return _value;
    }


    public String getNumber() {
        return this.getStringValue(ATTR_NUMBER);
    }

    public String setNumber(String _value) {
        this.addSlot(ATTR_NUMBER, _value, true, 0);
        return _value;
    }


    public java.util.ArrayList<String> getNewItems() {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NEWITEM) && (s.getValue() instanceof String)) 
                res.add((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class));
        }
        return res;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType typ) {
        return obj == this;
    }

    public static DecreeChangeValueReferent _new865(DecreeChangeValueKind _arg1) {
        DecreeChangeValueReferent res = new DecreeChangeValueReferent();
        res.setKind(_arg1);
        return res;
    }
}
