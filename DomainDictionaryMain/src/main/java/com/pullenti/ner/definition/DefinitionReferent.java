/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.definition;

/**
 * Сущность, моделирующая тезис (утверждение, определения)
 * 
 */
public class DefinitionReferent extends com.pullenti.ner.Referent {

    public DefinitionReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.definition.internal.MetaDefin.globalMeta);
    }

    /**
     * Имя типа сущности TypeName ("THESIS")
     */
    public static final String OBJ_TYPENAME = "THESIS";

    /**
     * Имя атрибута - определяемый термин
     */
    public static final String ATTR_TERMIN = "TERMIN";

    /**
     * Имя атрибута - дополнительный атрибут термина
     */
    public static final String ATTR_TERMIN_ADD = "TERMINADD";

    /**
     * Имя атрибута - основной текст
     */
    public static final String ATTR_VALUE = "VALUE";

    /**
     * Имя атрибута - разное
     */
    public static final String ATTR_MISC = "MISC";

    /**
     * Имя атрибута - тип тезиса (DefinitionKind)
     */
    public static final String ATTR_KIND = "KIND";

    /**
     * Имя атрибута - ссылка на НПА (DecreeReferent или DecreePartReferent)
     */
    public static final String ATTR_DECREE = "DECREE";

    public String getTermin() {
        return this.getStringValue(ATTR_TERMIN);
    }


    public String getTerminAdd() {
        return this.getStringValue(ATTR_TERMIN_ADD);
    }


    public String getValue() {
        return this.getStringValue(ATTR_VALUE);
    }


    public DefinitionKind getKind() {
        String s = this.getStringValue(ATTR_KIND);
        if (s == null) 
            return DefinitionKind.UNDEFINED;
        try {
            Object res = DefinitionKind.of(s);
            if (res instanceof DefinitionKind) 
                return (DefinitionKind)res;
        } catch (Exception ex1195) {
        }
        return DefinitionKind.UNDEFINED;
    }

    public DefinitionKind setKind(DefinitionKind _value) {
        this.addSlot(ATTR_KIND, _value.toString(), true, 0);
        return _value;
    }


    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        String misc = this.getStringValue(ATTR_TERMIN_ADD);
        if (misc == null) 
            misc = this.getStringValue(ATTR_MISC);
        return ("[" + this.getKind().toString() + "] " + ((String)com.pullenti.unisharp.Utils.notnull(this.getTermin(), "?")) + (misc == null ? "" : (" (" + misc + ")")) + " = " + ((String)com.pullenti.unisharp.Utils.notnull(this.getValue(), "?")));
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType typ) {
        DefinitionReferent dr = (DefinitionReferent)com.pullenti.unisharp.Utils.cast(obj, DefinitionReferent.class);
        if (dr == null) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(getTermin(), dr.getTermin())) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(getValue(), dr.getValue())) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(getTerminAdd(), dr.getTerminAdd())) 
            return false;
        return true;
    }

    public static DefinitionReferent _new1191(DefinitionKind _arg1) {
        DefinitionReferent res = new DefinitionReferent();
        res.setKind(_arg1);
        return res;
    }
}
