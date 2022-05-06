/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.keyword;

/**
 * Ключевая комбинация
 * 
 */
public class KeywordReferent extends com.pullenti.ner.Referent {

    public KeywordReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.keyword.internal.KeywordMeta.GLOBALMETA);
    }

    /**
     * Имя типа сущности TypeName ("KEYWORD")
     */
    public static final String OBJ_TYPENAME = "KEYWORD";

    /**
     * Имя атрибута - тип (KeywordType)
     */
    public static final String ATTR_TYPE = "TYPE";

    /**
     * Имя атрибута - значение
     */
    public static final String ATTR_VALUE = "VALUE";

    /**
     * Имя атрибута - нормализованное значение
     */
    public static final String ATTR_NORMAL = "NORMAL";

    /**
     * Имя атрибута - ссылка на сущность, если это она
     */
    public static final String ATTR_REF = "REF";

    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        if (lev > 10) 
            return "?";
        double _rank = rank;
        String val = this.getStringValue(ATTR_VALUE);
        if (val == null) {
            com.pullenti.ner.Referent r = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_REF), com.pullenti.ner.Referent.class);
            if (r != null) 
                val = r.toStringEx(true, lang, lev + 1);
            else 
                val = this.getStringValue(ATTR_NORMAL);
        }
        if (shortVariant) 
            return (val != null ? val : "?");
        String norm = this.getStringValue(ATTR_NORMAL);
        if (norm == null) 
            return (val != null ? val : "?");
        else 
            return (((val != null ? val : "?")) + " [" + norm + "]");
    }

    /**
     * Вычисляемый ранг (в атрибутах не сохраняется - просто поле!)
     */
    public double rank;

    public KeywordType getTyp() {
        String str = this.getStringValue(ATTR_TYPE);
        if (str == null) 
            return KeywordType.UNDEFINED;
        try {
            return (KeywordType)KeywordType.of(str);
        } catch (Exception ex) {
            return KeywordType.UNDEFINED;
        }
    }

    public KeywordType setTyp(KeywordType _value) {
        this.addSlot(ATTR_TYPE, _value.toString(), true, 0);
        return _value;
    }


    public String getValue() {
        return this.getStringValue(ATTR_VALUE);
    }

    public String setValue(String _value) {
        this.addSlot(ATTR_VALUE, _value, false, 0);
        return _value;
    }


    public String getNormalValue() {
        return this.getStringValue(ATTR_NORMAL);
    }

    public String setNormalValue(String _value) {
        this.addSlot(ATTR_NORMAL, _value, false, 0);
        return _value;
    }


    public int getChildWords() {
        return this._getChildWords(this, 0);
    }


    private int _getChildWords(KeywordReferent root, int lev) {
        if (lev > 5) 
            return 0;
        int res = 0;
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_REF) && (s.getValue() instanceof KeywordReferent)) {
                if (s.getValue() == root) 
                    return 0;
                res += ((KeywordReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), KeywordReferent.class))._getChildWords(root, lev + 1);
            }
        }
        if (res == 0) 
            res = 1;
        return res;
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType _typ) {
        KeywordReferent kw = (KeywordReferent)com.pullenti.unisharp.Utils.cast(obj, KeywordReferent.class);
        if (kw == null) 
            return false;
        KeywordType ki = getTyp();
        if (ki != kw.getTyp()) 
            return false;
        if (ki == KeywordType.REFERENT) {
            com.pullenti.ner.Referent re = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_REF), com.pullenti.ner.Referent.class);
            if (re == null) 
                return false;
            com.pullenti.ner.Referent re2 = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(kw.getSlotValue(ATTR_REF), com.pullenti.ner.Referent.class);
            if (re2 == null) 
                return false;
            if (re.canBeEquals(re2, _typ)) 
                return true;
        }
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NORMAL) || com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_VALUE)) {
                if (kw.findSlot(ATTR_NORMAL, s.getValue(), true) != null) 
                    return true;
                if (kw.findSlot(ATTR_VALUE, s.getValue(), true) != null) 
                    return true;
            }
        }
        return false;
    }

    @Override
    public void mergeSlots(com.pullenti.ner.Referent obj, boolean mergeStatistic) {
        double r1 = rank + ((KeywordReferent)com.pullenti.unisharp.Utils.cast(obj, KeywordReferent.class)).rank;
        super.mergeSlots(obj, mergeStatistic);
        if (getSlots().size() > 50) {
        }
        rank = r1;
    }

    public void union(KeywordReferent kw1, KeywordReferent kw2, String word2) {
        setTyp(kw1.getTyp());
        java.util.ArrayList<String> tmp = new java.util.ArrayList<String>();
        StringBuilder tmp2 = new StringBuilder();
        for (String v : kw1.getStringValues(ATTR_VALUE)) {
            this.addSlot(ATTR_VALUE, (v + " " + word2), false, 0);
        }
        java.util.ArrayList<String> norms1 = kw1.getStringValues(ATTR_NORMAL);
        if (norms1.size() == 0 && kw1.getChildWords() == 1) 
            norms1 = kw1.getStringValues(ATTR_VALUE);
        java.util.ArrayList<String> norms2 = kw2.getStringValues(ATTR_NORMAL);
        if (norms2.size() == 0 && kw2.getChildWords() == 1) 
            norms2 = kw2.getStringValues(ATTR_VALUE);
        for (String n1 : norms1) {
            for (String n2 : norms2) {
                tmp.clear();
                com.pullenti.unisharp.Utils.addToArrayList(tmp, java.util.Arrays.asList(com.pullenti.unisharp.Utils.split(n1, String.valueOf(' '), false)));
                for (String n : com.pullenti.unisharp.Utils.split(n2, String.valueOf(' '), false)) {
                    if (!tmp.contains(n)) 
                        tmp.add(n);
                }
                java.util.Collections.sort(tmp);
                tmp2.setLength(0);
                for (int i = 0; i < tmp.size(); i++) {
                    if (i > 0) 
                        tmp2.append(' ');
                    tmp2.append(tmp.get(i));
                }
                this.addSlot(ATTR_NORMAL, tmp2.toString(), false, 0);
            }
        }
        this.addSlot(ATTR_REF, kw1, false, 0);
        this.addSlot(ATTR_REF, kw2, false, 0);
    }

    @Override
    public com.pullenti.ner.core.IntOntologyItem createOntologyItem() {
        com.pullenti.ner.core.IntOntologyItem res = new com.pullenti.ner.core.IntOntologyItem(this);
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NORMAL) || com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_VALUE)) 
                res.termins.add(new com.pullenti.ner.core.Termin(s.getValue().toString(), null, false));
        }
        return res;
    }

    public static KeywordReferent _new1749(KeywordType _arg1) {
        KeywordReferent res = new KeywordReferent();
        res.setTyp(_arg1);
        return res;
    }
}
