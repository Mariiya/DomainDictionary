/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner;

/**
 * Элемент внешней онтологии
 */
public class ExtOntologyItem {

    public ExtOntologyItem(String caption) {
        m_Caption = caption;
    }

    /**
     * Внешний идентификатор (ссылка на что угодно)
     */
    public Object extId;

    /**
     * Имя типа сущности
     */
    public String typeName;

    /**
     * Ссылка на сущность
     */
    public Referent referent;

    /**
     * Используйте произвольным образом
     */
    public Object tag;

    // Используется внутренним образом
    public java.util.ArrayList<Referent> refs = null;

    private String m_Caption;

    @Override
    public String toString() {
        if (m_Caption != null) 
            return m_Caption;
        else if (referent == null) 
            return (((typeName != null ? typeName : "?")) + ": ?");
        else {
            String res = referent.toString();
            if (referent.getParentReferent() != null) {
                String str1 = referent.getParentReferent().toString();
                if (!(res.indexOf(str1) >= 0)) 
                    res = res + "; " + str1;
            }
            return res;
        }
    }

    public void serialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, (extId == null ? null : extId.toString()));
        com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, m_Caption);
        if (refs == null) 
            com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, 0);
        else {
            com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, refs.size());
            int id = 1;
            for (Referent r : refs) {
                r.tag = id++;
            }
            for (Referent r : refs) {
                r.getOccurrence().clear();
                com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, r.getTypeName());
                r.serialize(stream, false);
            }
        }
        referent.getOccurrence().clear();
        com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, typeName);
        referent.serialize(stream, false);
    }

    public void deserialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        extId = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
        m_Caption = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
        int cou = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        if (cou > 0) {
            refs = new java.util.ArrayList<Referent>();
            for (; cou > 0; cou--) {
                String typ = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
                Referent r = ProcessorService.createReferent(typ);
                r.deserialize(stream, refs, null, false);
                refs.add(r);
            }
        }
        typeName = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
        referent = ProcessorService.createReferent(typeName);
        referent.deserialize(stream, refs, null, false);
    }

    public static ExtOntologyItem _new3015(Object _arg1, Referent _arg2, String _arg3) {
        ExtOntologyItem res = new ExtOntologyItem(null);
        res.extId = _arg1;
        res.referent = _arg2;
        res.typeName = _arg3;
        return res;
    }
    public ExtOntologyItem() {
        this(null);
    }
}
