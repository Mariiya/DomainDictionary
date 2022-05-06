/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.instrument;

/**
 * Представление фрагмента документа. Фрагменты образуют дерево с вершиной в InstrumentReferent.
 * 
 */
public class InstrumentBlockReferent extends com.pullenti.ner.Referent {

    public InstrumentBlockReferent(String typename) {
        super((typename != null ? typename : OBJ_TYPENAME));
        setInstanceOf(com.pullenti.ner.instrument.internal.MetaInstrumentBlock.GLOBALMETA);
    }

    /**
     * Имя типа сущности TypeName ("INSTRBLOCK")
     */
    public static final String OBJ_TYPENAME = "INSTRBLOCK";

    /**
     * Имя атрибута - тип фрагмента (InstrumentKind)
     */
    public static final String ATTR_KIND = "KIND";

    public static final String ATTR_KIND2 = "KIND_SEC";

    /**
     * Имя атрибута - ссылки на дочерние фрагменты (InstrumentBlockReferent)
     */
    public static final String ATTR_CHILD = "CHILD";

    /**
     * Имя атрибута - значение (например, текст)
     */
    public static final String ATTR_VALUE = "VALUE";

    /**
     * Имя атрибута - ссылка на сущность (если есть)
     */
    public static final String ATTR_REF = "REF";

    /**
     * Имя атрибута - признак утраты силы
     */
    public static final String ATTR_EXPIRED = "EXPIRED";

    /**
     * Имя атрибута - наименование фрагмента
     */
    public static final String ATTR_NAME = "NAME";

    /**
     * Имя атрибута - номер фрагмента (для диапазона - максимальный номер)
     */
    public static final String ATTR_NUMBER = "NUMBER";

    /**
     * Имя атрибута - для диапазона - минимальный номер
     */
    public static final String ATTR_MINNUMBER = "MINNUMBER";

    /**
     * Имя атрибута - подномер
     */
    public static final String ATTR_SUBNUMBER = "ADDNUMBER";

    /**
     * Имя атрибута - второй подномер
     */
    public static final String ATTR_SUB2NUMBER = "ADDSECNUMBER";

    /**
     * Имя атрибута - третий подномер
     */
    public static final String ATTR_SUB3NUMBER = "ADDTHIRDNUMBER";

    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        InstrumentKind ki = getKind();
        String str;
        str = (String)com.pullenti.unisharp.Utils.cast(com.pullenti.ner.instrument.internal.MetaInstrumentBlock.GLOBALMETA.kindFeature.convertInnerValueToOuterValue(ki.toString(), lang), String.class);
        if (str != null) {
            res.append(str);
            if (getKind2() != InstrumentKind.UNDEFINED) {
                str = (String)com.pullenti.unisharp.Utils.cast(com.pullenti.ner.instrument.internal.MetaInstrumentBlock.GLOBALMETA.kindFeature.convertInnerValueToOuterValue(this.getKind2().toString(), lang), String.class);
                if (str != null) 
                    res.append(" (").append(str).append(")");
            }
        }
        if (getNumber() > 0) {
            if (ki == InstrumentKind.TABLE) 
                res.append(" ").append(this.getChildren().size()).append(" строк, ").append(this.getNumber()).append(" столбцов");
            else {
                res.append(" №").append(this.getNumber());
                if (getSubNumber() > 0) {
                    res.append(".").append(this.getSubNumber());
                    if (getSubNumber2() > 0) {
                        res.append(".").append(this.getSubNumber2());
                        if (getSubNumber3() > 0) 
                            res.append(".").append(this.getSubNumber3());
                    }
                }
                if (getMinNumber() > 0) {
                    for (int i = res.length() - 1; i >= 0; i--) {
                        if (res.charAt(i) == ' ' || res.charAt(i) == '.') {
                            res.insert(i + 1, (((Integer)this.getMinNumber()).toString() + "-"));
                            break;
                        }
                    }
                }
            }
        }
        boolean ignoreRef = false;
        if (isExpired()) {
            res.append(" (утратить силу)");
            ignoreRef = true;
        }
        else if (ki != InstrumentKind.EDITIONS && ki != InstrumentKind.APPROVED && (getRef() instanceof com.pullenti.ner.decree.DecreeReferent)) {
            res.append(" (*)");
            ignoreRef = true;
        }
        if ((((str = this.getStringValue(ATTR_NAME)))) == null) 
            str = this.getStringValue(ATTR_VALUE);
        if (str != null) {
            if (str.length() > 100) 
                str = str.substring(0, 0 + 100) + "...";
            res.append(" \"").append(str).append("\"");
        }
        else if (!ignoreRef && (getRef() instanceof com.pullenti.ner.Referent) && (lev < 30)) 
            res.append(" \"").append(this.getRef().toStringEx(shortVariant, lang, lev + 1)).append("\"");
        return res.toString().trim();
    }

    public InstrumentKind getKind() {
        String s = this.getStringValue(ATTR_KIND);
        if (s == null) 
            return InstrumentKind.UNDEFINED;
        try {
            if (com.pullenti.unisharp.Utils.stringsEq(s, "Part") || com.pullenti.unisharp.Utils.stringsEq(s, "Base") || com.pullenti.unisharp.Utils.stringsEq(s, "Special")) 
                return InstrumentKind.UNDEFINED;
            Object res = InstrumentKind.of(s);
            if (res instanceof InstrumentKind) 
                return (InstrumentKind)res;
        } catch (Exception ex1736) {
        }
        return InstrumentKind.UNDEFINED;
    }

    public InstrumentKind setKind(InstrumentKind _value) {
        if (_value != InstrumentKind.UNDEFINED) 
            this.addSlot(ATTR_KIND, _value.toString().toUpperCase(), true, 0);
        return _value;
    }


    public InstrumentKind getKind2() {
        String s = this.getStringValue(ATTR_KIND2);
        if (s == null) 
            return InstrumentKind.UNDEFINED;
        try {
            Object res = InstrumentKind.of(s);
            if (res instanceof InstrumentKind) 
                return (InstrumentKind)res;
        } catch (Exception ex1737) {
        }
        return InstrumentKind.UNDEFINED;
    }

    public InstrumentKind setKind2(InstrumentKind _value) {
        if (_value != InstrumentKind.UNDEFINED) 
            this.addSlot(ATTR_KIND2, _value.toString().toUpperCase(), true, 0);
        return _value;
    }


    public String getValue() {
        return this.getStringValue(ATTR_VALUE);
    }

    public String setValue(String _value) {
        this.addSlot(ATTR_VALUE, _value, true, 0);
        return _value;
    }


    public com.pullenti.ner.Referent getRef() {
        return (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_REF), com.pullenti.ner.Referent.class);
    }


    public boolean isExpired() {
        return com.pullenti.unisharp.Utils.stringsEq(this.getStringValue(ATTR_EXPIRED), "true");
    }

    public boolean setExpired(boolean _value) {
        this.addSlot(ATTR_EXPIRED, (_value ? "true" : null), true, 0);
        return _value;
    }


    public int getNumber() {
        String str = this.getStringValue(ATTR_NUMBER);
        if (str == null) 
            return 0;
        int i;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapi1738 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres1739 = com.pullenti.unisharp.Utils.parseInteger(str, 0, null, wrapi1738);
        i = (wrapi1738.value != null ? wrapi1738.value : 0);
        if (inoutres1739) 
            return i;
        return 0;
    }

    public int setNumber(int _value) {
        if (_value >= 0) 
            this.addSlot(ATTR_NUMBER, ((Integer)_value).toString(), true, 0);
        return _value;
    }


    public int getSubNumber() {
        String str = this.getStringValue(ATTR_SUBNUMBER);
        if (str == null) 
            return 0;
        int i;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapi1740 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres1741 = com.pullenti.unisharp.Utils.parseInteger(str, 0, null, wrapi1740);
        i = (wrapi1740.value != null ? wrapi1740.value : 0);
        if (inoutres1741) 
            return i;
        return 0;
    }

    public int setSubNumber(int _value) {
        if (_value > 0) 
            this.addSlot(ATTR_SUBNUMBER, ((Integer)_value).toString(), true, 0);
        return _value;
    }


    public int getSubNumber2() {
        String str = this.getStringValue(ATTR_SUB2NUMBER);
        if (str == null) 
            return 0;
        int i;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapi1742 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres1743 = com.pullenti.unisharp.Utils.parseInteger(str, 0, null, wrapi1742);
        i = (wrapi1742.value != null ? wrapi1742.value : 0);
        if (inoutres1743) 
            return i;
        return 0;
    }

    public int setSubNumber2(int _value) {
        if (_value > 0) 
            this.addSlot(ATTR_SUB2NUMBER, ((Integer)_value).toString(), true, 0);
        return _value;
    }


    public int getSubNumber3() {
        String str = this.getStringValue(ATTR_SUB3NUMBER);
        if (str == null) 
            return 0;
        int i;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapi1744 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres1745 = com.pullenti.unisharp.Utils.parseInteger(str, 0, null, wrapi1744);
        i = (wrapi1744.value != null ? wrapi1744.value : 0);
        if (inoutres1745) 
            return i;
        return 0;
    }

    public int setSubNumber3(int _value) {
        if (_value > 0) 
            this.addSlot(ATTR_SUB3NUMBER, ((Integer)_value).toString(), true, 0);
        return _value;
    }


    public int getMinNumber() {
        String str = this.getStringValue(ATTR_MINNUMBER);
        if (str == null) 
            return 0;
        int i;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapi1746 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres1747 = com.pullenti.unisharp.Utils.parseInteger(str, 0, null, wrapi1746);
        i = (wrapi1746.value != null ? wrapi1746.value : 0);
        if (inoutres1747) 
            return i;
        return 0;
    }

    public int setMinNumber(int _value) {
        this.addSlot(ATTR_MINNUMBER, ((Integer)_value).toString(), true, 0);
        return _value;
    }


    public String getName() {
        return this.getStringValue(ATTR_NAME);
    }

    public String setName(String _value) {
        this.addSlot(ATTR_NAME, _value, true, 0);
        return _value;
    }


    public java.util.ArrayList<InstrumentBlockReferent> getChildren() {
        if (m_Children == null) {
            m_Children = new java.util.ArrayList<InstrumentBlockReferent>();
            for (com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_CHILD)) {
                    if (s.getValue() instanceof InstrumentBlockReferent) 
                        m_Children.add((InstrumentBlockReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), InstrumentBlockReferent.class));
                }
            }
        }
        return m_Children;
    }


    private java.util.ArrayList<InstrumentBlockReferent> m_Children;

    @Override
    public com.pullenti.ner.Slot addSlot(String attrName, Object attrValue, boolean clearOldValue, int statCount) {
        m_Children = null;
        return super.addSlot(attrName, attrValue, clearOldValue, statCount);
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType typ) {
        return obj == this;
    }

    /**
     * Представить тип строкой русского языка.
     * @param typ тип
     * @param shortVal сокращённый или полный (например, ст. или статья)
     * @return слово
     */
    public static String kindToRusString(InstrumentKind typ, boolean shortVal) {
        if (typ == InstrumentKind.APPENDIX) 
            return (shortVal ? "прил." : "Приложение");
        if (typ == InstrumentKind.CLAUSE) 
            return (shortVal ? "ст." : "Статья");
        if (typ == InstrumentKind.CHAPTER) 
            return (shortVal ? "гл." : "Глава");
        if (typ == InstrumentKind.ITEM) 
            return (shortVal ? "п." : "Пункт");
        if (typ == InstrumentKind.PARAGRAPH) 
            return (shortVal ? "§" : "Параграф");
        if (typ == InstrumentKind.SUBPARAGRAPH) 
            return (shortVal ? "подпарагр." : "Подпараграф");
        if (typ == InstrumentKind.DOCPART) 
            return (shortVal ? "ч." : "Часть");
        if (typ == InstrumentKind.SECTION) 
            return (shortVal ? "раздел" : "Раздел");
        if (typ == InstrumentKind.INTERNALDOCUMENT) 
            return "Документ";
        if (typ == InstrumentKind.SUBITEM) 
            return (shortVal ? "пп." : "Подпункт");
        if (typ == InstrumentKind.SUBSECTION) 
            return (shortVal ? "подразд." : "Подраздел");
        if (typ == InstrumentKind.CLAUSEPART) 
            return (shortVal ? "ч." : "Часть");
        if (typ == InstrumentKind.INDENTION) 
            return (shortVal ? "абз." : "Абзац");
        if (typ == InstrumentKind.PREAMBLE) 
            return (shortVal ? "преамб." : "Преамбула");
        return null;
    }
    public InstrumentBlockReferent() {
        this(null);
    }
}
