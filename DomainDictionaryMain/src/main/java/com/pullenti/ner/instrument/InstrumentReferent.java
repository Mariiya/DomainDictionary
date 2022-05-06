/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.instrument;

/**
 * Представление всего документа
 * 
 */
public class InstrumentReferent extends InstrumentBlockReferent {

    public InstrumentReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.instrument.internal.MetaInstrument.GLOBALMETA);
    }

    /**
     * Имя типа сущности TypeName ("INSTRUMENT")
     */
    public static final String OBJ_TYPENAME = "INSTRUMENT";

    /**
     * Имя атрибута - тип документа
     */
    public static final String ATTR_TYPE = "TYPE";

    /**
     * Имя атрибута - регистрационный номер
     */
    public static final String ATTR_REGNUMBER = "NUMBER";

    /**
     * Имя атрибута - номер судебного дела
     */
    public static final String ATTR_CASENUMBER = "CASENUMBER";

    /**
     * Имя атрибута - дата
     */
    public static final String ATTR_DATE = "DATE";

    /**
     * Имя атрибута - подписант
     */
    public static final String ATTR_SIGNER = "SIGNER";

    /**
     * Имя атрибута - публикующий орган
     */
    public static final String ATTR_SOURCE = "SOURCE";

    /**
     * Имя атрибута - географический объект
     */
    public static final String ATTR_GEO = "GEO";

    /**
     * Имя атрибута - номер части (если это часть другого документа)
     */
    public static final String ATTR_PART = "PART";

    /**
     * Имя атрибута - номер приложения (если это приложение)
     */
    public static final String ATTR_APPENDIX = "APPENDIX";

    /**
     * Имя атрибута - участник (InstrumentParticipant)
     */
    public static final String ATTR_PARTICIPANT = "PARTICIPANT";

    /**
     * Имя атрибута - артефакт (InstrumentArtefact)
     */
    public static final String ATTR_ARTEFACT = "ARTEFACT";

    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        String str;
        if ((((str = this.getStringValue(ATTR_APPENDIX)))) != null) {
            java.util.ArrayList<String> strs = this.getStringValues(ATTR_APPENDIX);
            if (strs.size() == 1) 
                res.append("Приложение").append((str.length() == 0 ? "" : " ")).append(str).append("; ");
            else {
                res.append("Приложения ");
                for (int i = 0; i < strs.size(); i++) {
                    if (i > 0) 
                        res.append(",");
                    res.append(strs.get(i));
                }
                res.append("; ");
            }
        }
        if ((((str = this.getStringValue(ATTR_PART)))) != null) 
            res.append("Часть ").append(str).append("; ");
        if (getTyp() != null) 
            res.append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(this.getTyp()));
        else 
            res.append("Документ");
        if (getRegNumber() != null) {
            res.append(" №").append(this.getRegNumber());
            for (com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_REGNUMBER) && com.pullenti.unisharp.Utils.stringsNe(s.getValue().toString(), getRegNumber())) 
                    res.append("/").append(s.getValue());
            }
        }
        if (getCaseNumber() != null) 
            res.append(" дело №").append(this.getCaseNumber());
        String dt = this.getStringValue(ATTR_DATE);
        if (dt != null) 
            res.append(" от ").append(dt);
        if ((((str = this.getStringValue(InstrumentBlockReferent.ATTR_NAME)))) != null) {
            if (str.length() > 100) 
                str = str.substring(0, 0 + 100) + "...";
            res.append(" \"").append(str).append("\"");
        }
        if ((((str = this.getStringValue(ATTR_GEO)))) != null) 
            res.append(" (").append(str).append(")");
        return res.toString().trim();
    }

    public String getTyp() {
        return this.getStringValue(ATTR_TYPE);
    }

    public String setTyp(String _value) {
        this.addSlot(ATTR_TYPE, _value, true, 0);
        return _value;
    }


    public String getRegNumber() {
        return this.getStringValue(ATTR_REGNUMBER);
    }

    public String setRegNumber(String _value) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(_value)) {
            this.addSlot(ATTR_REGNUMBER, null, true, 0);
            return _value;
        }
        if (".,".indexOf(_value.charAt(_value.length() - 1)) >= 0) 
            _value = _value.substring(0, 0 + _value.length() - 1);
        this.addSlot(ATTR_REGNUMBER, _value, true, 0);
        return _value;
    }


    public String getCaseNumber() {
        return this.getStringValue(ATTR_CASENUMBER);
    }

    public String setCaseNumber(String _value) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(_value)) 
            return _value;
        if (".,".indexOf(_value.charAt(_value.length() - 1)) >= 0) 
            _value = _value.substring(0, 0 + _value.length() - 1);
        this.addSlot(ATTR_CASENUMBER, _value, true, 0);
        return _value;
    }


    public java.time.LocalDateTime getDate() {
        String s = this.getStringValue(ATTR_DATE);
        if (s == null) 
            return null;
        return com.pullenti.ner.decree.internal.DecreeHelper.parseDateTime(s);
    }


    public boolean addDate(Object dt) {
        if (dt == null) 
            return false;
        if (dt instanceof com.pullenti.ner.decree.internal.DecreeToken) {
            if (((com.pullenti.ner.decree.internal.DecreeToken)com.pullenti.unisharp.Utils.cast(dt, com.pullenti.ner.decree.internal.DecreeToken.class)).ref instanceof com.pullenti.ner.ReferentToken) 
                return this.addDate(((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(((com.pullenti.ner.decree.internal.DecreeToken)com.pullenti.unisharp.Utils.cast(dt, com.pullenti.ner.decree.internal.DecreeToken.class)).ref, com.pullenti.ner.ReferentToken.class)).referent);
            if (((com.pullenti.ner.decree.internal.DecreeToken)com.pullenti.unisharp.Utils.cast(dt, com.pullenti.ner.decree.internal.DecreeToken.class)).value != null) {
                this.addSlot(ATTR_DATE, ((com.pullenti.ner.decree.internal.DecreeToken)com.pullenti.unisharp.Utils.cast(dt, com.pullenti.ner.decree.internal.DecreeToken.class)).value, true, 0);
                return true;
            }
            return false;
        }
        if (dt instanceof com.pullenti.ner.ReferentToken) 
            return this.addDate(((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(dt, com.pullenti.ner.ReferentToken.class)).referent);
        if (dt instanceof com.pullenti.ner.date.DateReferent) {
            com.pullenti.ner.date.DateReferent dr = (com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(dt, com.pullenti.ner.date.DateReferent.class);
            int year = dr.getYear();
            int mon = dr.getMonth();
            int day = dr.getDay();
            if (year == 0) 
                return dr.getPointer() == com.pullenti.ner.date.DatePointerType.UNDEFINED;
            java.time.LocalDateTime exDate = getDate();
            if (exDate != null && exDate.getYear() == year) {
                if (mon == 0 && exDate.getMonthValue() > 0) 
                    return false;
                if (day == 0 && exDate.getDayOfMonth() > 0) 
                    return false;
                boolean delExist = false;
                if (mon > 0 && exDate.getMonthValue() == 0) 
                    delExist = true;
                if (delExist) {
                    for (com.pullenti.ner.Slot s : getSlots()) {
                        if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_DATE)) {
                            getSlots().remove(s);
                            break;
                        }
                    }
                }
            }
            StringBuilder tmp = new StringBuilder();
            tmp.append(year);
            if (mon > 0) 
                tmp.append(".").append(String.format("%02d", mon));
            if (day > 0) 
                tmp.append(".").append(String.format("%02d", day));
            this.addSlot(com.pullenti.ner.decree.DecreeReferent.ATTR_DATE, tmp.toString(), false, 0);
            return true;
        }
        if (dt instanceof String) {
            this.addSlot(ATTR_DATE, (String)com.pullenti.unisharp.Utils.cast(dt, String.class), true, 0);
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType _typ) {
        return obj == this;
    }
}
