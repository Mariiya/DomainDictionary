/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.utils;

/**
 * Модель управления
 */
public class ControlModel {

    /**
     * Элементы модели
     */
    public java.util.ArrayList<ControlModelItem> items = new java.util.ArrayList<ControlModelItem>();

    /**
     * Типовые пациенты (устойчивые словосочетания)
     */
    public java.util.ArrayList<String> pacients = new java.util.ArrayList<String>();

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (ControlModelItem it : items) {
            if (it.ignorable) 
                continue;
            if (res.length() > 0) 
                res.append("; ");
            if (it.typ == ControlModelItemType.WORD) 
                res.append(it.word).append(" = ").append(it.links.size());
            else 
                res.append(it.typ.toString()).append(" = ").append(it.links.size());
        }
        for (String p : pacients) {
            res.append(" (").append(p).append(")");
        }
        return res.toString();
    }

    public ControlModelItem findItemByTyp(ControlModelItemType typ) {
        for (ControlModelItem it : items) {
            if (it.typ == typ) 
                return it;
        }
        return null;
    }

    public void deserialize(com.pullenti.morph.internal.ByteArrayWrapper str, com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        int cou = str.deserializeShort(pos);
        for (; cou > 0; cou--) {
            ControlModelItem it = new ControlModelItem();
            byte b = str.deserializeByte(pos);
            if ((((Byte.toUnsignedInt(b)) & 0x80)) != 0) 
                it.nominativeCanBeAgentAndPacient = true;
            it.typ = ControlModelItemType.of(((Byte.toUnsignedInt(b)) & 0x7F));
            if (it.typ == ControlModelItemType.WORD) 
                it.word = str.deserializeString(pos);
            int licou = str.deserializeShort(pos);
            for (; licou > 0; licou--) {
                byte bi = str.deserializeByte(pos);
                int i = Byte.toUnsignedInt(bi);
                b = str.deserializeByte(pos);
                if (i >= 0 && (i < ControlModelQuestion.ITEMS.size())) 
                    it.links.put(ControlModelQuestion.ITEMS.get(i), com.pullenti.semantic.core.SemanticRole.of(Byte.toUnsignedInt(b)));
            }
            items.add(it);
        }
        cou = str.deserializeShort(pos);
        for (; cou > 0; cou--) {
            String p = str.deserializeString(pos);
            if (p != null) 
                pacients.add(p);
        }
    }
    public ControlModel() {
    }
}
