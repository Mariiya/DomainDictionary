/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner;

/**
 * Значение атрибута в конкретном экземпляре сущности
 * 
 * Атрибут сущности
 */
public class Slot {

    private String _typename;

    public String getTypeName() {
        return _typename;
    }

    public String setTypeName(String _value) {
        _typename = _value;
        return _typename;
    }


    public boolean isInternal() {
        return getTypeName() != null && this.getTypeName().charAt(0) == '@';
    }


    private Referent _owner;

    public Referent getOwner() {
        return _owner;
    }

    public Referent setOwner(Referent _value) {
        _owner = _value;
        return _owner;
    }


    private Object m_Value;

    public Object getValue() {
        return m_Value;
    }

    public Object setValue(Object _value) {
        m_Value = _value;
        if (m_Value != null) {
            if (m_Value instanceof Referent) {
            }
            else if (m_Value instanceof Token) {
            }
            else if (m_Value instanceof String) {
            }
            else 
                m_Value = m_Value.toString();
        }
        else {
        }
        return _value;
    }


    private int _count;

    public int getCount() {
        return _count;
    }

    public int setCount(int _value) {
        _count = _value;
        return _count;
    }


    public com.pullenti.ner.metadata.Feature getDefiningFeature() {
        if (getOwner() == null) 
            return null;
        if (getOwner().getInstanceOf() == null) 
            return null;
        return getOwner().getInstanceOf().findFeature(this.getTypeName());
    }


    @Override
    public String toString() {
        return this.toStringEx(com.pullenti.morph.MorphLang.UNKNOWN);
    }

    public String toStringEx(com.pullenti.morph.MorphLang lang) {
        StringBuilder res = new StringBuilder();
        com.pullenti.ner.metadata.Feature attr = getDefiningFeature();
        if (attr != null) {
            if (getCount() > 0) 
                res.append(attr.getCaption()).append(" (").append(this.getCount()).append("): ");
            else 
                res.append(attr.getCaption()).append(": ");
        }
        else 
            res.append(this.getTypeName()).append(": ");
        if (getValue() != null) {
            if (getValue() instanceof Referent) 
                res.append(((Referent)com.pullenti.unisharp.Utils.cast(this.getValue(), Referent.class)).toStringEx(false, lang, 0));
            else if (attr == null) 
                res.append(this.getValue().toString());
            else 
                res.append(attr.convertInnerValueToOuterValue(this.getValue().toString(), null));
        }
        return res.toString();
    }

    /**
     * Преобразовать внутреннее значение в строку указанного языка
     * @param lang язык
     * @return значение
     */
    public String convertValueToString(com.pullenti.morph.MorphLang lang) {
        if (getValue() == null) 
            return null;
        com.pullenti.ner.metadata.Feature attr = getDefiningFeature();
        if (attr == null) 
            return getValue().toString();
        Object v = attr.convertInnerValueToOuterValue(this.getValue().toString(), lang);
        if (v == null) 
            return null;
        if (v instanceof String) 
            return (String)com.pullenti.unisharp.Utils.cast(v, String.class);
        else 
            return v.toString();
    }

    private Object _tag;

    public Object getTag() {
        return _tag;
    }

    public Object setTag(Object _value) {
        _tag = _value;
        return _tag;
    }


    public static Slot _new1180(String _arg1, Object _arg2, int _arg3) {
        Slot res = new Slot();
        res.setTypeName(_arg1);
        res.setTag(_arg2);
        res.setCount(_arg3);
        return res;
    }

    public static Slot _new3062(String _arg1, Object _arg2, int _arg3) {
        Slot res = new Slot();
        res.setTypeName(_arg1);
        res.setValue(_arg2);
        res.setCount(_arg3);
        return res;
    }
    public Slot() {
    }
}
