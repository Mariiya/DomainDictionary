/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.instrument;

/**
 * Для судебных решений формализованная резолюция (пока).
 */
public class InstrumentArtefactReferent extends com.pullenti.ner.Referent {

    public InstrumentArtefactReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.instrument.internal.InstrumentArtefactMeta.GLOBALMETA);
    }

    /**
     * Имя типа сущности TypeName ("INSTRARTEFACT")
     */
    public static final String OBJ_TYPENAME = "INSTRARTEFACT";

    /**
     * Имя атрибута - тип артефакта
     */
    public static final String ATTR_TYPE = "TYPE";

    /**
     * Имя атрибута - значение артефакта
     */
    public static final String ATTR_VALUE = "VALUE";

    /**
     * Имя атрибута - ссылка на сущность (если есть)
     */
    public static final String ATTR_REF = "REF";

    public String getTyp() {
        return this.getStringValue(ATTR_TYPE);
    }

    public String setTyp(String _value) {
        this.addSlot(ATTR_TYPE, (_value == null ? null : _value.toUpperCase()), true, 0);
        return _value;
    }


    public Object getValue() {
        return this.getSlotValue(ATTR_VALUE);
    }

    public Object setValue(Object _value) {
        this.addSlot(ATTR_VALUE, _value, false, 0);
        return _value;
    }


    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        res.append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower((String)com.pullenti.unisharp.Utils.notnull(this.getTyp(), "?")));
        Object val = getValue();
        if (val != null) 
            res.append(": ").append(val);
        if (!shortVariant && (lev < 30)) {
            com.pullenti.ner.Referent re = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_REF), com.pullenti.ner.Referent.class);
            if (re != null) 
                res.append(" (").append(re.toStringEx(shortVariant, lang, lev + 1)).append(")");
        }
        return res.toString();
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType _typ) {
        InstrumentArtefactReferent p = (InstrumentArtefactReferent)com.pullenti.unisharp.Utils.cast(obj, InstrumentArtefactReferent.class);
        if (p == null) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(getTyp(), p.getTyp())) 
            return false;
        if (getValue() != p.getValue()) 
            return false;
        return true;
    }

    public static InstrumentArtefactReferent _new1556(String _arg1) {
        InstrumentArtefactReferent res = new InstrumentArtefactReferent();
        res.setTyp(_arg1);
        return res;
    }
}
