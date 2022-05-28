/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.decree;

/**
 * Модель изменения структурной части НПА
 */
public class DecreeChangeReferent extends com.pullenti.ner.Referent {

    public DecreeChangeReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.decree.internal.MetaDecreeChange.GLOBALMETA);
    }

    /**
     * Имя типа сущности TypeName ("DECREECHANGE")
     */
    public static final String OBJ_TYPENAME = "DECREECHANGE";

    /**
     * Имя атрибута - Структурный элемент, в который вносится изменение (м.б. несколько), 
     * DecreeReferent или DecreePartReferent.
     */
    public static final String ATTR_OWNER = "OWNER";

    /**
     * Имя атрибута - тип изменения (DecreeChangeKind)
     */
    public static final String ATTR_KIND = "KIND";

    /**
     * Имя атрибута - внутренние изменения (DecreeChangeReferent)
     */
    public static final String ATTR_CHILD = "CHILD";

    /**
     * Имя атрибута - само изменение (DecreeChangeValueReferent)
     */
    public static final String ATTR_VALUE = "VALUE";

    /**
     * Имя атрибута - дополнительный параметр DecreeChangeValueReferent (для типа Exchange - что заменяется, для Append - после чего)
     */
    public static final String ATTR_PARAM = "PARAM";

    /**
     * Имя атрибута - разное
     */
    public static final String ATTR_MISC = "MISC";

    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        if (getKind() != DecreeChangeKind.UNDEFINED) 
            res.append(com.pullenti.ner.decree.internal.MetaDecreeChange.KINDFEATURE.convertInnerValueToOuterValue(this.getKind().toString(), lang)).append(" ");
        if (isOwnerNameAndText()) 
            res.append("наименование и текст ");
        else if (isOwnerName()) 
            res.append("наименование ");
        else if (isOnlyText()) 
            res.append("текст ");
        for (com.pullenti.ner.Referent o : getOwners()) {
            res.append("'").append(o.toStringEx(true, lang, 0)).append("' ");
        }
        if (getValue() != null) 
            res.append(this.getValue().toStringEx(true, lang, 0)).append(" ");
        if (getParam() != null) {
            if (getKind() == DecreeChangeKind.APPEND) 
                res.append("после ");
            else if (getKind() == DecreeChangeKind.EXCHANGE) 
                res.append("вместо ");
            res.append(this.getParam().toStringEx(true, lang, 0));
        }
        return res.toString().trim();
    }

    @Override
    public com.pullenti.ner.Referent getParentReferent() {
        return (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_OWNER), com.pullenti.ner.Referent.class);
    }


    public DecreeChangeKind getKind() {
        String s = this.getStringValue(ATTR_KIND);
        if (s == null) 
            return DecreeChangeKind.UNDEFINED;
        try {
            if (com.pullenti.unisharp.Utils.stringsEq(s, "Add")) 
                return DecreeChangeKind.APPEND;
            Object res = DecreeChangeKind.of(s);
            if (res instanceof DecreeChangeKind) 
                return (DecreeChangeKind)res;
        } catch (Exception ex1184) {
        }
        return DecreeChangeKind.UNDEFINED;
    }

    public DecreeChangeKind setKind(DecreeChangeKind _value) {
        if (_value != DecreeChangeKind.UNDEFINED) 
            this.addSlot(ATTR_KIND, _value.toString(), true, 0);
        return _value;
    }


    public java.util.ArrayList<com.pullenti.ner.Referent> getOwners() {
        java.util.ArrayList<com.pullenti.ner.Referent> res = new java.util.ArrayList<com.pullenti.ner.Referent>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_OWNER) && (s.getValue() instanceof com.pullenti.ner.Referent)) 
                res.add((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class));
        }
        return res;
    }


    public java.util.ArrayList<DecreeChangeReferent> getChildren() {
        java.util.ArrayList<DecreeChangeReferent> res = new java.util.ArrayList<DecreeChangeReferent>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_CHILD) && (s.getValue() instanceof DecreeChangeReferent)) 
                res.add((DecreeChangeReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), DecreeChangeReferent.class));
        }
        return res;
    }


    public DecreeChangeValueReferent getValue() {
        return (DecreeChangeValueReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_VALUE), DecreeChangeValueReferent.class);
    }

    public DecreeChangeValueReferent setValue(DecreeChangeValueReferent _value) {
        this.addSlot(ATTR_VALUE, _value, true, 0);
        return _value;
    }


    public DecreeChangeValueReferent getParam() {
        return (DecreeChangeValueReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_PARAM), DecreeChangeValueReferent.class);
    }

    public DecreeChangeValueReferent setParam(DecreeChangeValueReferent _value) {
        this.addSlot(ATTR_PARAM, _value, true, 0);
        return _value;
    }


    public boolean isOwnerName() {
        return this.findSlot(ATTR_MISC, "NAME", true) != null;
    }

    public boolean setOwnerName(boolean _value) {
        if (_value) 
            this.addSlot(ATTR_MISC, "NAME", false, 0);
        return _value;
    }


    public boolean isOnlyText() {
        return this.findSlot(ATTR_MISC, "TEXT", true) != null;
    }

    public boolean setOnlyText(boolean _value) {
        if (_value) 
            this.addSlot(ATTR_MISC, "TEXT", false, 0);
        return _value;
    }


    public boolean isOwnerNameAndText() {
        return this.findSlot(ATTR_MISC, "NAMETEXT", true) != null;
    }

    public boolean setOwnerNameAndText(boolean _value) {
        if (_value) 
            this.addSlot(ATTR_MISC, "NAMETEXT", false, 0);
        return _value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType typ) {
        return obj == this;
    }

    public boolean checkCorrect() {
        if (getKind() == DecreeChangeKind.UNDEFINED) 
            return false;
        if (getKind() == DecreeChangeKind.EXPIRE || getKind() == DecreeChangeKind.REMOVE) 
            return true;
        if (getValue() == null) 
            return false;
        if (getKind() == DecreeChangeKind.EXCHANGE) {
            if (getParam() == null) {
                if (getOwners().size() > 0 && this.getOwners().get(0).findSlot(DecreePartReferent.ATTR_INDENTION, null, true) != null) 
                    setKind(DecreeChangeKind.NEW);
                else 
                    return false;
            }
        }
        return true;
    }

    public static DecreeChangeReferent _new1171(DecreeChangeKind _arg1) {
        DecreeChangeReferent res = new DecreeChangeReferent();
        res.setKind(_arg1);
        return res;
    }
}
