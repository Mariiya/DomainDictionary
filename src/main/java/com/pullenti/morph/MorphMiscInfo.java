/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph;

/**
 * Дополнительная морфологическая информация
 * Дополнительная морф.информация
 */
public class MorphMiscInfo {

    public java.util.Collection<String> getAttrs() {
        return m_Attrs;
    }


    private java.util.ArrayList<String> m_Attrs = new java.util.ArrayList<String>();

    public void addAttr(String a) {
        if (!m_Attrs.contains(a)) 
            m_Attrs.add(a);
    }

    public short value;

    private boolean getBoolValue(int i) {
        return ((((((int)value) >> i)) & 1)) != 0;
    }

    private void setBoolValue(int i, boolean val) {
        if (val) 
            value |= ((short)(1 << i));
        else 
            value &= ((short)(~(1 << i)));
    }

    public void copyFrom(MorphMiscInfo src) {
        value = src.value;
        for (String a : src.getAttrs()) {
            m_Attrs.add(a);
        }
    }

    public MorphPerson getPerson() {
        MorphPerson res = MorphPerson.UNDEFINED;
        if (m_Attrs.contains("1 л.")) 
            res = MorphPerson.of((short)((res.value()) | (MorphPerson.FIRST.value())));
        if (m_Attrs.contains("2 л.")) 
            res = MorphPerson.of((short)((res.value()) | (MorphPerson.SECOND.value())));
        if (m_Attrs.contains("3 л.")) 
            res = MorphPerson.of((short)((res.value()) | (MorphPerson.THIRD.value())));
        return res;
    }

    public MorphPerson setPerson(MorphPerson _value) {
        if (((short)((_value.value()) & (MorphPerson.FIRST.value()))) != (MorphPerson.UNDEFINED.value())) 
            this.addAttr("1 л.");
        if (((short)((_value.value()) & (MorphPerson.SECOND.value()))) != (MorphPerson.UNDEFINED.value())) 
            this.addAttr("2 л.");
        if (((short)((_value.value()) & (MorphPerson.THIRD.value()))) != (MorphPerson.UNDEFINED.value())) 
            this.addAttr("3 л.");
        return _value;
    }


    public MorphTense getTense() {
        if (m_Attrs.contains("п.вр.")) 
            return MorphTense.PAST;
        if (m_Attrs.contains("н.вр.")) 
            return MorphTense.PRESENT;
        if (m_Attrs.contains("б.вр.")) 
            return MorphTense.FUTURE;
        return MorphTense.UNDEFINED;
    }

    public MorphTense setTense(MorphTense _value) {
        if (_value == MorphTense.PAST) 
            this.addAttr("п.вр.");
        if (_value == MorphTense.PRESENT) 
            this.addAttr("н.вр.");
        if (_value == MorphTense.FUTURE) 
            this.addAttr("б.вр.");
        return _value;
    }


    public MorphAspect getAspect() {
        if (m_Attrs.contains("нес.в.")) 
            return MorphAspect.IMPERFECTIVE;
        if (m_Attrs.contains("сов.в.")) 
            return MorphAspect.PERFECTIVE;
        return MorphAspect.UNDEFINED;
    }

    public MorphAspect setAspect(MorphAspect _value) {
        if (_value == MorphAspect.IMPERFECTIVE) 
            this.addAttr("нес.в.");
        if (_value == MorphAspect.PERFECTIVE) 
            this.addAttr("сов.в.");
        return _value;
    }


    public MorphMood getMood() {
        if (m_Attrs.contains("пов.накл.")) 
            return MorphMood.IMPERATIVE;
        return MorphMood.UNDEFINED;
    }

    public MorphMood setMood(MorphMood _value) {
        if (_value == MorphMood.IMPERATIVE) 
            this.addAttr("пов.накл.");
        return _value;
    }


    public MorphVoice getVoice() {
        if (m_Attrs.contains("дейст.з.")) 
            return MorphVoice.ACTIVE;
        if (m_Attrs.contains("страд.з.")) 
            return MorphVoice.PASSIVE;
        return MorphVoice.UNDEFINED;
    }

    public MorphVoice setVoice(MorphVoice _value) {
        if (_value == MorphVoice.ACTIVE) 
            this.addAttr("дейст.з.");
        if (_value == MorphVoice.PASSIVE) 
            this.addAttr("страд.з.");
        return _value;
    }


    public MorphForm getForm() {
        if (m_Attrs.contains("к.ф.")) 
            return MorphForm.SHORT;
        if (m_Attrs.contains("синоним.форма")) 
            return MorphForm.SYNONYM;
        if (isSynonymForm()) 
            return MorphForm.SYNONYM;
        return MorphForm.UNDEFINED;
    }


    public boolean isSynonymForm() {
        return this.getBoolValue(0);
    }

    public boolean setSynonymForm(boolean _value) {
        this.setBoolValue(0, _value);
        return _value;
    }


    @Override
    public String toString() {
        if (m_Attrs.size() == 0 && value == ((short)0)) 
            return "";
        StringBuilder res = new StringBuilder();
        if (isSynonymForm()) 
            res.append("синоним.форма ");
        for (int i = 0; i < m_Attrs.size(); i++) {
            res.append(m_Attrs.get(i)).append(" ");
        }
        return com.pullenti.unisharp.Utils.trimEnd(res.toString());
    }

    public int id;

    public void deserialize(com.pullenti.morph.internal.ByteArrayWrapper str, com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        int sh = str.deserializeShort(pos);
        value = (short)sh;
        while (true) {
            String s = str.deserializeString(pos);
            if (com.pullenti.unisharp.Utils.isNullOrEmpty(s)) 
                break;
            if (!m_Attrs.contains(s)) 
                m_Attrs.add(s);
        }
    }
    public MorphMiscInfo() {
    }
}
