/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner;

/**
 * Базовый класс для всех именованных сущностей
 * 
 * Именованная сущность
 */
public class Referent {

    public Referent(String typ) {
        m_ObjectType = typ;
    }

    private String m_ObjectType;

    public String getTypeName() {
        return m_ObjectType;
    }


    @Override
    public String toString() {
        return this.toStringEx(false, com.pullenti.morph.MorphLang.UNKNOWN, 0);
    }

    /**
     * Специализированное строковое представление сущности
     * @param shortVariant Сокращённый вариант
     * @param lang Язык
     * @return 
     */
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        return getTypeName();
    }

    // По этой строке можно осуществлять сортировку среди сущностей одного типа
    public String toSortString() {
        return this.toStringEx(false, com.pullenti.morph.MorphLang.UNKNOWN, 0);
    }

    private com.pullenti.ner.metadata.ReferentClass _instanceof;

    public com.pullenti.ner.metadata.ReferentClass getInstanceOf() {
        return _instanceof;
    }

    public com.pullenti.ner.metadata.ReferentClass setInstanceOf(com.pullenti.ner.metadata.ReferentClass value) {
        _instanceof = value;
        return _instanceof;
    }


    /**
     * Привязка к элементам внешней онтологии, если таковые были заданы - 
     * когда в Process(...) класса Processor был передан словарь "внешней онтологии" ExtOntology. 
     * В принципе, может привязаться к нескольким элементам "онтологии".
     */
    public java.util.ArrayList<ExtOntologyItem> ontologyItems;

    public java.util.ArrayList<Slot> getSlots() {
        return m_Slots;
    }


    private java.util.ArrayList<Slot> m_Slots = new java.util.ArrayList<Slot>();

    /**
     * Добавить значение атрибута
     * @param attrName имя
     * @param attrValue значение
     * @param clearOldValue если true и слот существует, то значение перезапишется
     * @return слот(атрибут)
     */
    public Slot addSlot(String attrName, Object attrValue, boolean clearOldValue, int statCount) {
        if (com.pullenti.unisharp.Utils.stringsEq((String)com.pullenti.unisharp.Utils.cast(attrValue, String.class), "СЕН")) {
        }
        if (clearOldValue) {
            for (int i = getSlots().size() - 1; i >= 0; i--) {
                if (com.pullenti.unisharp.Utils.stringsEq(this.getSlots().get(i).getTypeName(), attrName)) 
                    getSlots().remove(i);
            }
        }
        if (attrValue == null) 
            return null;
        for (Slot r : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), attrName)) {
                if (this.compareValues(r.getValue(), attrValue, true)) {
                    r.setCount(r.getCount() + statCount);
                    return r;
                }
            }
        }
        Slot res = new Slot();
        res.setOwner(this);
        res.setValue(attrValue);
        res.setTypeName(attrName);
        res.setCount(statCount);
        getSlots().add(res);
        return res;
    }

    public void uploadSlot(Slot slot, Object newVal) {
        if (slot != null) 
            slot.setValue(newVal);
    }

    private int m_Level;

    /**
     * Найти слот (атрибут)
     * @param attrName имя атрибута
     * @param val возможное значение
     * @param useCanBeEqualsForReferents для значений-сущностей использовать метод CanBeEquals для сравнения
     * @return подходящий слот или null
     * 
     */
    public Slot findSlot(String attrName, Object val, boolean useCanBeEqualsForReferents) {
        if (m_Level > 10) 
            return null;
        if (attrName == null) {
            if (val == null) 
                return null;
            m_Level++;
            for (Slot r : getSlots()) {
                if (this.compareValues(val, r.getValue(), useCanBeEqualsForReferents)) {
                    m_Level--;
                    return r;
                }
            }
            m_Level--;
            return null;
        }
        for (Slot r : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), attrName)) {
                if (val == null) 
                    return r;
                m_Level++;
                if (this.compareValues(val, r.getValue(), useCanBeEqualsForReferents)) {
                    m_Level--;
                    return r;
                }
                m_Level--;
            }
        }
        return null;
    }

    private boolean compareValues(Object val1, Object val2, boolean useCanBeEqualsForReferents) {
        if (val1 == null) 
            return val2 == null;
        if (val2 == null) 
            return val1 == null;
        if (val1 == val2) 
            return true;
        if ((val1 instanceof Referent) && (val2 instanceof Referent)) {
            if (useCanBeEqualsForReferents) 
                return ((Referent)com.pullenti.unisharp.Utils.cast(val1, Referent.class)).canBeEquals((Referent)com.pullenti.unisharp.Utils.cast(val2, Referent.class), com.pullenti.ner.core.ReferentsEqualType.DIFFERENTTEXTS);
            else 
                return false;
        }
        if (val1 instanceof String) {
            if (!(val2 instanceof String)) 
                return false;
            String s1 = val1.toString();
            String s2 = val2.toString();
            int i = com.pullenti.unisharp.Utils.stringsCompare(s1, s2, true);
            return i == 0;
        }
        return val1 == val2;
    }

    /**
     * Получить значение слота-атрибута (если их несколько, то вернёт первое)
     * @param attrName имя слота
     * @return значение (поле Value)
     * 
     */
    public Object getSlotValue(String attrName) {
        for (Slot v : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(v.getTypeName(), attrName)) 
                return v.getValue();
        }
        return null;
    }

    /**
     * Получить строковое значение (если их несколько, то вернёт первое)
     * @param attrName имя атрибута
     * @return значение или null
     * 
     */
    public String getStringValue(String attrName) {
        for (Slot v : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(v.getTypeName(), attrName)) 
                return (v.getValue() == null ? null : v.getValue().toString());
        }
        return null;
    }

    /**
     * Получить все строовые значения заданного атрибута
     * @param attrName имя атрибута
     * @return список значений string
     * 
     */
    public java.util.ArrayList<String> getStringValues(String attrName) {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        for (Slot v : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(v.getTypeName(), attrName) && v.getValue() != null) {
                if (v.getValue() instanceof String) 
                    res.add((String)com.pullenti.unisharp.Utils.cast(v.getValue(), String.class));
                else 
                    res.add(v.toString());
            }
        }
        return res;
    }

    /**
     * Получить числовое значение (если их несколько, то вернёт первое)
     * @param attrName имя атрибута
     * @param defValue дефолтовое значение, если не найдено
     * @return число
     */
    public int getIntValue(String attrName, int defValue) {
        String str = this.getStringValue(attrName);
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(str)) 
            return defValue;
        int res;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapres3058 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres3059 = com.pullenti.unisharp.Utils.parseInteger(str, 0, null, wrapres3058);
        res = (wrapres3058.value != null ? wrapres3058.value : 0);
        if (!inoutres3059) 
            return defValue;
        return res;
    }

    private java.util.ArrayList<TextAnnotation> m_Occurrence;

    public java.util.ArrayList<TextAnnotation> getOccurrence() {
        if (m_Occurrence == null) 
            m_Occurrence = new java.util.ArrayList<TextAnnotation>();
        return m_Occurrence;
    }


    /**
     * Найти ближайшую к токену аннотацию
     * @param t токен
     * @return ближайшая аннотация
     */
    public TextAnnotation findNearOccurence(Token t) {
        int min = -1;
        TextAnnotation res = null;
        for (TextAnnotation oc : getOccurrence()) {
            if (oc.sofa == t.kit.getSofa()) {
                int len = oc.beginChar - t.getBeginChar();
                if (len < 0) 
                    len = -len;
                if ((min < 0) || (len < min)) {
                    min = len;
                    res = oc;
                }
            }
        }
        return res;
    }

    public void addOccurenceOfRefTok(ReferentToken rt) {
        this.addOccurence(TextAnnotation._new765(rt.kit.getSofa(), rt.getBeginChar(), rt.getEndChar(), rt.referent));
    }

    /**
     * Добавить аннотацию
     * @param anno аннотация
     */
    public void addOccurence(TextAnnotation anno) {
        for (TextAnnotation l : getOccurrence()) {
            com.pullenti.ner.core.internal.TextsCompareType typ = l.compareWith(anno);
            if (typ == com.pullenti.ner.core.internal.TextsCompareType.NONCOMPARABLE) 
                continue;
            if (typ == com.pullenti.ner.core.internal.TextsCompareType.EQUIVALENT || typ == com.pullenti.ner.core.internal.TextsCompareType.CONTAINS) 
                return;
            if (typ == com.pullenti.ner.core.internal.TextsCompareType.IN || typ == com.pullenti.ner.core.internal.TextsCompareType.INTERSECT) {
                l.merge(anno);
                return;
            }
        }
        if (anno.getOccurenceOf() != this && anno.getOccurenceOf() != null) 
            anno = TextAnnotation._new3061(anno.beginChar, anno.endChar, anno.sofa);
        if (m_Occurrence == null) 
            m_Occurrence = new java.util.ArrayList<TextAnnotation>();
        anno.setOccurenceOf(this);
        if (m_Occurrence.size() == 0) {
            anno.essentialForOccurence = true;
            m_Occurrence.add(anno);
            return;
        }
        if (anno.beginChar < m_Occurrence.get(0).beginChar) {
            m_Occurrence.add(0, anno);
            return;
        }
        if (anno.beginChar >= m_Occurrence.get(m_Occurrence.size() - 1).beginChar) {
            m_Occurrence.add(anno);
            return;
        }
        for (int i = 0; i < (m_Occurrence.size() - 1); i++) {
            if (anno.beginChar >= m_Occurrence.get(i).beginChar && anno.beginChar <= m_Occurrence.get(i + 1).beginChar) {
                m_Occurrence.add(i + 1, anno);
                return;
            }
        }
        m_Occurrence.add(anno);
    }

    /**
     * Проверка, что ссылки на элемент имеются на заданном участке текста
     * @param beginChar начальная позиция
     * @param endChar конечная позиция
     * @return да или нет
     */
    public boolean checkOccurence(int beginChar, int endChar) {
        for (TextAnnotation loc : getOccurrence()) {
            com.pullenti.ner.core.internal.TextsCompareType cmp = loc.compare(beginChar, endChar);
            if (cmp != com.pullenti.ner.core.internal.TextsCompareType.EARLY && cmp != com.pullenti.ner.core.internal.TextsCompareType.LATER && cmp != com.pullenti.ner.core.internal.TextsCompareType.NONCOMPARABLE) 
                return true;
        }
        return false;
    }

    /**
     * Используется произвольным образом
     */
    public Object tag;

    public Referent clone() {
        Referent res = ProcessorService.createReferent(this.getTypeName());
        if (res == null) 
            res = new Referent(getTypeName());
        com.pullenti.unisharp.Utils.addToArrayList(res.getOccurrence(), this.getOccurrence());
        res.ontologyItems = ontologyItems;
        for (Slot r : getSlots()) {
            Slot rr = Slot._new3062(r.getTypeName(), r.getValue(), r.getCount());
            rr.setOwner(res);
            res.getSlots().add(rr);
        }
        return res;
    }

    /**
     * Проверка возможной тождественности сущностей
     * @param obj другая сущность
     * @param typ тип сравнения
     * @return результат
     * 
     */
    public boolean canBeEquals(Referent obj, com.pullenti.ner.core.ReferentsEqualType typ) {
        if (obj == null || com.pullenti.unisharp.Utils.stringsNe(obj.getTypeName(), getTypeName())) 
            return false;
        for (Slot r : getSlots()) {
            if (r.getValue() != null && obj.findSlot(r.getTypeName(), r.getValue(), false) == null) 
                return false;
        }
        for (Slot r : obj.getSlots()) {
            if (r.getValue() != null && this.findSlot(r.getTypeName(), r.getValue(), true) == null) 
                return false;
        }
        return true;
    }

    /**
     * Объединение значений атрибутов со значениями атрибутов другой сущности
     * @param obj Другая сущшность, считающаяся эквивалентной
     * @param mergeStatistic Объединять ли вместе со статистикой
     */
    public void mergeSlots(Referent obj, boolean mergeStatistic) {
        if (obj == null) 
            return;
        for (Slot r : obj.getSlots()) {
            Slot s = this.findSlot(r.getTypeName(), r.getValue(), true);
            if (s == null && r.getValue() != null) 
                s = this.addSlot(r.getTypeName(), r.getValue(), false, 0);
            if (s != null && mergeStatistic) 
                s.setCount(s.getCount() + r.getCount());
        }
        this._mergeExtReferents(obj);
    }

    public Referent getParentReferent() {
        return null;
    }


    /**
     * Получить идентификатор иконки. Саму иконку ImageWrapper можно получить через функцию 
     * GetImageById(imageId) статического класса ProcessorService.
     * @return идентификатор иконки
     */
    public String getImageId() {
        if (getInstanceOf() == null) 
            return null;
        return getInstanceOf().getImageId(this);
    }

    public static final String ATTR_GENERAL = "GENERAL";

    /**
     * Проверка, может ли текущая сущность быть обобщением для другой сущности
     * @param obj более частная сущность
     * @return да-нет
     */
    public boolean canBeGeneralFor(Referent obj) {
        return false;
    }

    public Referent getGeneralReferent() {
        Referent res = (Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_GENERAL), Referent.class);
        if (res == null || res == this) 
            return null;
        return res;
    }

    public Referent setGeneralReferent(Referent value) {
        if (value == getGeneralReferent()) 
            return value;
        if (value == this) 
            return value;
        this.addSlot(ATTR_GENERAL, value, true, 0);
        return value;
    }


    // Создать элемент онтологии
    public com.pullenti.ner.core.IntOntologyItem createOntologyItem() {
        return null;
    }

    // Используется внутренним образом (напрямую не устанавливать!)
    public com.pullenti.ner.core.IntOntologyItem intOntologyItem;

    // Используется внутренним образом
    public java.util.ArrayList<String> getCompareStrings() {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        res.add(this.toString());
        String s = this.toStringEx(true, com.pullenti.morph.MorphLang.UNKNOWN, 0);
        if (com.pullenti.unisharp.Utils.stringsNe(s, res.get(0))) 
            res.add(s);
        return res;
    }

    public java.util.ArrayList<ReferentToken> m_ExtReferents;

    public void addExtReferent(ReferentToken rt) {
        if (rt == null) 
            return;
        if (m_ExtReferents == null) 
            m_ExtReferents = new java.util.ArrayList<ReferentToken>();
        if (!m_ExtReferents.contains(rt)) 
            m_ExtReferents.add(rt);
        if (m_ExtReferents.size() > 100) {
        }
    }

    public void moveExtReferent(Referent target, Referent r) {
        if (m_ExtReferents != null) {
            for (ReferentToken rt : m_ExtReferents) {
                if (rt.referent == r || r == null) {
                    target.addExtReferent(rt);
                    m_ExtReferents.remove(rt);
                    break;
                }
            }
        }
    }

    protected void _mergeExtReferents(Referent obj) {
        if (obj.m_ExtReferents != null) {
            for (ReferentToken rt : obj.m_ExtReferents) {
                this.addExtReferent(rt);
            }
        }
    }

    public void serialize(com.pullenti.unisharp.Stream stream, boolean newFormat) throws java.io.IOException {
        com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, this.getTypeName());
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, m_Slots.size());
        for (Slot s : m_Slots) {
            com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, s.getTypeName());
            com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, s.getCount());
            if ((s.getValue() instanceof Referent) && (((Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), Referent.class)).tag instanceof Integer)) {
                if (newFormat) {
                    stream.write((byte)2);
                    com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, (int)((Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), Referent.class)).tag);
                }
                else 
                    com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, -((int)((Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), Referent.class)).tag));
            }
            else if (s.getValue() instanceof String) {
                if (newFormat) 
                    stream.write((byte)1);
                com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, (String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class));
            }
            else if (s.getValue() == null) {
                if (newFormat) 
                    stream.write((byte)0);
                else 
                    com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, 0);
            }
            else {
                if (newFormat) 
                    stream.write((byte)1);
                com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, s.getValue().toString());
            }
        }
        if (m_Occurrence == null) 
            com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, 0);
        else {
            com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, m_Occurrence.size());
            for (TextAnnotation o : m_Occurrence) {
                com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, o.beginChar);
                com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, o.endChar);
                int attr = 0;
                if (o.essentialForOccurence) 
                    attr = 1;
                com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, attr);
            }
        }
    }

    public void deserialize(com.pullenti.unisharp.Stream stream, java.util.ArrayList<Referent> all, SourceOfAnalysis sofa, boolean newFormat) throws java.io.IOException {
        String typ = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
        int cou = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        for (int i = 0; i < cou; i++) {
            typ = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
            int c = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
            Object val = null;
            if (newFormat) {
                byte bb = (byte)stream.read();
                if (bb == ((byte)2)) {
                    int id1 = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
                    if (id1 > 0 && id1 <= all.size()) 
                        val = all.get(id1 - 1);
                }
                else if (bb == ((byte)1)) 
                    val = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
            }
            else {
                int id = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
                if ((id < 0) && all != null) {
                    int id1 = (-id) - 1;
                    if (id1 < all.size()) 
                        val = all.get(id1);
                }
                else if (id > 0) {
                    stream.setPosition(stream.getPosition() - (4L));
                    val = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
                }
            }
            this.addSlot(typ, val, false, c);
        }
        cou = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        m_Occurrence = new java.util.ArrayList<TextAnnotation>();
        for (int i = 0; i < cou; i++) {
            TextAnnotation a = TextAnnotation._new3063(sofa, this);
            m_Occurrence.add(a);
            a.beginChar = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
            a.endChar = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
            int attr = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
            if (((attr & 1)) != 0) 
                a.essentialForOccurence = true;
        }
    }
    public Referent() {
    }
}
