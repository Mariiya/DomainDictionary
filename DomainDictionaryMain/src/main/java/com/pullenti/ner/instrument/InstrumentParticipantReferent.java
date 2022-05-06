/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.instrument;

/**
 * Участник НПА (для договора: продавец, агент, исполнитель и т.п.)
 */
public class InstrumentParticipantReferent extends com.pullenti.ner.Referent {

    public InstrumentParticipantReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.instrument.internal.InstrumentParticipantMeta.GLOBALMETA);
    }

    /**
     * Имя типа сущности TypeName ("INSTRPARTICIPANT")
     */
    public static final String OBJ_TYPENAME = "INSTRPARTICIPANT";

    /**
     * Имя атрибута - тип участника (например, продавец, арендатор, ответчик...)
     */
    public static final String ATTR_TYPE = "TYPE";

    /**
     * Имя атрибута - ссылка на сущность (PersonReferent или OrganizationReferent)
     */
    public static final String ATTR_REF = "REF";

    /**
     * Имя атрибута - представитель участника (PersonReferent)
     */
    public static final String ATTR_DELEGATE = "DELEGATE";

    /**
     * Имя атрибута - основание (на основании чего действует)
     */
    public static final String ATTR_GROUND = "GROUND";

    public String getTyp() {
        return this.getStringValue(ATTR_TYPE);
    }

    public String setTyp(String value) {
        this.addSlot(ATTR_TYPE, (value == null ? null : value.toUpperCase()), true, 0);
        return value;
    }


    public Object getGround() {
        return this.getSlotValue(ATTR_GROUND);
    }

    public Object setGround(Object value) {
        this.addSlot(ATTR_GROUND, value, false, 0);
        return value;
    }


    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        res.append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower((String)com.pullenti.unisharp.Utils.notnull(this.getTyp(), "?")));
        com.pullenti.ner.Referent _org = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_REF), com.pullenti.ner.Referent.class);
        com.pullenti.ner.Referent del = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_DELEGATE), com.pullenti.ner.Referent.class);
        if (_org != null) {
            res.append(": ").append(_org.toStringEx(shortVariant, lang, 0));
            if (!shortVariant && del != null) 
                res.append(" (в лице ").append(del.toStringEx(true, lang, lev + 1)).append(")");
        }
        else if (del != null) 
            res.append(": в лице ").append(del.toStringEx(shortVariant, lang, lev + 1));
        return res.toString();
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType _typ) {
        InstrumentParticipantReferent p = (InstrumentParticipantReferent)com.pullenti.unisharp.Utils.cast(obj, InstrumentParticipantReferent.class);
        if (p == null) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(getTyp(), p.getTyp())) 
            return false;
        com.pullenti.ner.Referent re1 = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_REF), com.pullenti.ner.Referent.class);
        com.pullenti.ner.Referent re2 = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(obj.getSlotValue(ATTR_REF), com.pullenti.ner.Referent.class);
        if (re1 != null && re2 != null) {
            if (!re1.canBeEquals(re2, _typ)) 
                return false;
        }
        return true;
    }

    public boolean containsRef(com.pullenti.ner.Referent r) {
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (((com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_REF) || com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_DELEGATE))) && (s.getValue() instanceof com.pullenti.ner.Referent)) {
                if (r == s.getValue() || r.canBeEquals((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class), com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) 
                    return true;
            }
        }
        return false;
    }

    public static InstrumentParticipantReferent _new1542(String _arg1) {
        InstrumentParticipantReferent res = new InstrumentParticipantReferent();
        res.setTyp(_arg1);
        return res;
    }
}
