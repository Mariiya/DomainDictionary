/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner;

/**
 * Коллекция морфологических вариантов
 * Морфология токена
 */
public class MorphCollection extends com.pullenti.morph.MorphBaseInfo {

    public MorphCollection(MorphCollection source) {
        super();
        if (source == null) 
            return;
        for (com.pullenti.morph.MorphBaseInfo it : source.getItems()) {
            com.pullenti.morph.MorphBaseInfo mi = null;
            if (it instanceof com.pullenti.morph.MorphWordForm) {
                com.pullenti.morph.MorphWordForm wf = new com.pullenti.morph.MorphWordForm();
                wf.copyFromWordForm((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(it, com.pullenti.morph.MorphWordForm.class));
                mi = wf;
            }
            else {
                mi = new com.pullenti.morph.MorphBaseInfo();
                mi.copyFrom(it);
            }
            if (m_Items == null) 
                m_Items = new java.util.ArrayList<com.pullenti.morph.MorphBaseInfo>();
            m_Items.add(mi);
        }
        m_Class = com.pullenti.morph.MorphClass._new53(source.m_Class.value);
        m_Gender = source.m_Gender;
        m_Case = com.pullenti.morph.MorphCase._new29(source.m_Case.value);
        m_Number = source.m_Number;
        m_Language = com.pullenti.morph.MorphLang._new56(source.m_Language.value);
        m_Voice = source.m_Voice;
        m_NeedRecalc = false;
    }

    @Override
    public String toString() {
        String res = super.toString();
        if (getVoice() != com.pullenti.morph.MorphVoice.UNDEFINED) {
            if (getVoice() == com.pullenti.morph.MorphVoice.ACTIVE) 
                res += " действ.з.";
            else if (getVoice() == com.pullenti.morph.MorphVoice.PASSIVE) 
                res += " страд.з.";
            else if (getVoice() == com.pullenti.morph.MorphVoice.MIDDLE) 
                res += " сред. з.";
        }
        return res;
    }

    private com.pullenti.morph.MorphClass m_Class = new com.pullenti.morph.MorphClass();

    private com.pullenti.morph.MorphGender m_Gender = com.pullenti.morph.MorphGender.UNDEFINED;

    private com.pullenti.morph.MorphNumber m_Number = com.pullenti.morph.MorphNumber.UNDEFINED;

    private com.pullenti.morph.MorphCase m_Case = new com.pullenti.morph.MorphCase();

    private com.pullenti.morph.MorphLang m_Language = new com.pullenti.morph.MorphLang();

    private com.pullenti.morph.MorphVoice m_Voice = com.pullenti.morph.MorphVoice.UNDEFINED;

    private boolean m_NeedRecalc = true;

    /**
     * Создать копию
     * @return 
     */
    public MorphCollection clone() {
        MorphCollection res = new MorphCollection(null);
        if (m_Items != null) {
            res.m_Items = new java.util.ArrayList<com.pullenti.morph.MorphBaseInfo>();
            try {
                com.pullenti.unisharp.Utils.addToArrayList(res.m_Items, m_Items);
            } catch (Exception ex) {
            }
        }
        if (!m_NeedRecalc) {
            res.m_Class = com.pullenti.morph.MorphClass._new53(m_Class.value);
            res.m_Gender = m_Gender;
            res.m_Case = com.pullenti.morph.MorphCase._new29(m_Case.value);
            res.m_Number = m_Number;
            res.m_Language = com.pullenti.morph.MorphLang._new56(m_Language.value);
            res.m_NeedRecalc = false;
            res.m_Voice = m_Voice;
        }
        return res;
    }

    public int getItemsCount() {
        return (m_Items == null ? 0 : m_Items.size());
    }


    public com.pullenti.morph.MorphBaseInfo getIndexerItem(int ind) {
        if (m_Items == null || (ind < 0) || ind >= m_Items.size()) 
            return null;
        else 
            return m_Items.get(ind);
    }


    private static java.util.ArrayList<com.pullenti.morph.MorphBaseInfo> m_EmptyItems;

    public java.util.Collection<com.pullenti.morph.MorphBaseInfo> getItems() {
        return (m_Items != null ? m_Items : m_EmptyItems);
    }


    private java.util.ArrayList<com.pullenti.morph.MorphBaseInfo> m_Items = null;

    public void addItem(com.pullenti.morph.MorphBaseInfo item) {
        if (m_Items == null) 
            m_Items = new java.util.ArrayList<com.pullenti.morph.MorphBaseInfo>();
        m_Items.add(item);
        m_NeedRecalc = true;
    }

    public void insertItem(int ind, com.pullenti.morph.MorphBaseInfo item) {
        if (m_Items == null) 
            m_Items = new java.util.ArrayList<com.pullenti.morph.MorphBaseInfo>();
        m_Items.add(ind, item);
        m_NeedRecalc = true;
    }

    public void removeItem(int i) {
        if (m_Items != null && i >= 0 && (i < m_Items.size())) {
            m_Items.remove(i);
            m_NeedRecalc = true;
        }
    }

    public void removeItem(com.pullenti.morph.MorphBaseInfo item) {
        if (m_Items != null && m_Items.contains(item)) {
            m_Items.remove(item);
            m_NeedRecalc = true;
        }
    }

    private void _recalc() {
        m_NeedRecalc = false;
        if (m_Items == null || m_Items.size() == 0) 
            return;
        m_Class = new com.pullenti.morph.MorphClass();
        m_Gender = com.pullenti.morph.MorphGender.UNDEFINED;
        boolean g = m_Gender == com.pullenti.morph.MorphGender.UNDEFINED;
        m_Number = com.pullenti.morph.MorphNumber.UNDEFINED;
        boolean n = m_Number == com.pullenti.morph.MorphNumber.UNDEFINED;
        m_Case = new com.pullenti.morph.MorphCase();
        boolean ca = m_Case.isUndefined();
        boolean la = m_Language == null || m_Language.isUndefined();
        m_Voice = com.pullenti.morph.MorphVoice.UNDEFINED;
        boolean verbHasUndef = false;
        if (m_Items != null) {
            for (com.pullenti.morph.MorphBaseInfo it : m_Items) {
                m_Class.value |= it._getClass().value;
                if (g) 
                    m_Gender = com.pullenti.morph.MorphGender.of((short)((m_Gender.value()) | (it.getGender().value())));
                if (ca) 
                    m_Case = com.pullenti.morph.MorphCase.ooBitor(m_Case, it.getCase());
                if (n) 
                    m_Number = com.pullenti.morph.MorphNumber.of((short)((m_Number.value()) | (it.getNumber().value())));
                if (la) 
                    m_Language.value |= it.getLanguage().value;
                if (it._getClass().isVerb()) {
                    if (it instanceof com.pullenti.morph.MorphWordForm) {
                        com.pullenti.morph.MorphVoice v = ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(it, com.pullenti.morph.MorphWordForm.class)).misc.getVoice();
                        if (v == com.pullenti.morph.MorphVoice.UNDEFINED) 
                            verbHasUndef = true;
                        else 
                            m_Voice = com.pullenti.morph.MorphVoice.of((short)((m_Voice.value()) | (v.value())));
                    }
                }
            }
        }
        if (verbHasUndef) 
            m_Voice = com.pullenti.morph.MorphVoice.UNDEFINED;
    }

    @Override
    public com.pullenti.morph.MorphClass _getClass() {
        if (m_NeedRecalc) 
            this._recalc();
        return m_Class;
    }

    @Override
    public com.pullenti.morph.MorphClass _setClass(com.pullenti.morph.MorphClass value) {
        m_Class = value;
        return value;
    }


    @Override
    public com.pullenti.morph.MorphCase getCase() {
        if (m_NeedRecalc) 
            this._recalc();
        return m_Case;
    }

    @Override
    public com.pullenti.morph.MorphCase setCase(com.pullenti.morph.MorphCase value) {
        m_Case = value;
        return value;
    }


    @Override
    public com.pullenti.morph.MorphGender getGender() {
        if (m_NeedRecalc) 
            this._recalc();
        return m_Gender;
    }

    @Override
    public com.pullenti.morph.MorphGender setGender(com.pullenti.morph.MorphGender value) {
        m_Gender = value;
        return value;
    }


    @Override
    public com.pullenti.morph.MorphNumber getNumber() {
        if (m_NeedRecalc) 
            this._recalc();
        return m_Number;
    }

    @Override
    public com.pullenti.morph.MorphNumber setNumber(com.pullenti.morph.MorphNumber value) {
        m_Number = value;
        return value;
    }


    @Override
    public com.pullenti.morph.MorphLang getLanguage() {
        if (m_NeedRecalc) 
            this._recalc();
        return m_Language;
    }

    @Override
    public com.pullenti.morph.MorphLang setLanguage(com.pullenti.morph.MorphLang value) {
        m_Language = value;
        return value;
    }


    public com.pullenti.morph.MorphVoice getVoice() {
        if (m_NeedRecalc) 
            this._recalc();
        return m_Voice;
    }

    public com.pullenti.morph.MorphVoice setVoice(com.pullenti.morph.MorphVoice value) {
        if (m_NeedRecalc) 
            this._recalc();
        m_Voice = value;
        return value;
    }


    @Override
    public boolean containsAttr(String attrValue, com.pullenti.morph.MorphClass cla) {
        for (com.pullenti.morph.MorphBaseInfo it : getItems()) {
            if (cla != null && cla.value != ((short)0) && ((((int)it._getClass().value) & ((int)cla.value))) == 0) 
                continue;
            if (it.containsAttr(attrValue, cla)) 
                return true;
        }
        return false;
    }

    @Override
    public boolean checkAccord(com.pullenti.morph.MorphBaseInfo v, boolean ignoreGender, boolean ignoreNumber) {
        for (com.pullenti.morph.MorphBaseInfo it : getItems()) {
            if (v instanceof MorphCollection) {
                if (v.checkAccord(it, ignoreGender, ignoreNumber)) 
                    return true;
            }
            else if (it.checkAccord(v, ignoreGender, ignoreNumber)) 
                return true;
        }
        if (getItems().size() > 0) 
            return false;
        return super.checkAccord(v, ignoreGender, ignoreNumber);
    }

    public boolean check(com.pullenti.morph.MorphClass cl) {
        return ((((int)this._getClass().value) & ((int)cl.value))) != 0;
    }

    /**
     * Удалить элементы, не соответствующие падежу
     * @param cas 
     */
    public void removeItems(com.pullenti.morph.MorphCase cas) {
        if (m_Items == null) 
            return;
        if (m_Items.size() == 0) 
            m_Case = com.pullenti.morph.MorphCase.ooBitand(m_Case, cas);
        for (int i = m_Items.size() - 1; i >= 0; i--) {
            if (((com.pullenti.morph.MorphCase.ooBitand(m_Items.get(i).getCase(), cas))).isUndefined()) {
                m_Items.remove(i);
                m_NeedRecalc = true;
            }
            else if (!((com.pullenti.morph.MorphCase.ooBitand(m_Items.get(i).getCase(), cas))).equals(m_Items.get(i).getCase())) {
                if (m_Items.get(i) instanceof com.pullenti.morph.MorphWordForm) {
                    com.pullenti.morph.MorphWordForm wf = new com.pullenti.morph.MorphWordForm();
                    wf.copyFromWordForm((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(m_Items.get(i), com.pullenti.morph.MorphWordForm.class));
                    wf.setCase(com.pullenti.morph.MorphCase.ooBitand(wf.getCase(), cas));
                    com.pullenti.unisharp.Utils.putArrayValue(m_Items, i, wf);
                }
                else {
                    com.pullenti.morph.MorphBaseInfo bi = new com.pullenti.morph.MorphBaseInfo();
                    bi.copyFrom(m_Items.get(i));
                    bi.setCase(com.pullenti.morph.MorphCase.ooBitand(bi.getCase(), cas));
                    com.pullenti.unisharp.Utils.putArrayValue(m_Items, i, bi);
                }
                m_NeedRecalc = true;
            }
        }
        m_NeedRecalc = true;
    }

    /**
     * Удалить элементы, не соответствующие классу
     * @param cl 
     */
    public void removeItems(com.pullenti.morph.MorphClass cl, boolean eq) {
        if (m_Items == null) 
            return;
        for (int i = m_Items.size() - 1; i >= 0; i--) {
            boolean ok = false;
            if (((((int)m_Items.get(i)._getClass().value) & ((int)cl.value))) == 0) 
                ok = true;
            else if (eq && m_Items.get(i)._getClass().value != cl.value) 
                ok = true;
            if (ok) {
                m_Items.remove(i);
                m_NeedRecalc = true;
            }
        }
        m_NeedRecalc = true;
    }

    /**
     * Удалить элементы, не соответствующие параметрам
     * @param inf 
     */
    public void removeItems(com.pullenti.morph.MorphBaseInfo inf) {
        if (m_Items == null) 
            return;
        if (m_Items.size() == 0) {
            if (inf.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) 
                m_Gender = com.pullenti.morph.MorphGender.of((short)((m_Gender.value()) & (inf.getGender().value())));
            if (inf.getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED) 
                m_Number = com.pullenti.morph.MorphNumber.of((short)((m_Number.value()) & (inf.getNumber().value())));
            if (!inf.getCase().isUndefined()) 
                m_Case = com.pullenti.morph.MorphCase.ooBitand(m_Case, inf.getCase());
            return;
        }
        for (int i = m_Items.size() - 1; i >= 0; i--) {
            boolean ok = true;
            com.pullenti.morph.MorphBaseInfo it = m_Items.get(i);
            if (inf.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                if (((short)((it.getGender().value()) & (inf.getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                    ok = false;
            }
            boolean chNum = false;
            if (inf.getNumber() != com.pullenti.morph.MorphNumber.PLURAL && inf.getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED) {
                if (((short)((it.getNumber().value()) & (inf.getNumber().value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                    ok = false;
                chNum = true;
            }
            if (!inf._getClass().isUndefined()) {
                if (((com.pullenti.morph.MorphClass.ooBitand(inf._getClass(), it._getClass()))).isUndefined()) 
                    ok = false;
            }
            if (!inf.getCase().isUndefined()) {
                if (((com.pullenti.morph.MorphCase.ooBitand(inf.getCase(), it.getCase()))).isUndefined()) 
                    ok = false;
            }
            if (!ok) {
                m_Items.remove(i);
                m_NeedRecalc = true;
            }
            else {
                if (!inf.getCase().isUndefined()) {
                    if (!it.getCase().equals(com.pullenti.morph.MorphCase.ooBitand(inf.getCase(), it.getCase()))) {
                        it.setCase((com.pullenti.morph.MorphCase.ooBitand(inf.getCase(), it.getCase())));
                        m_NeedRecalc = true;
                    }
                }
                if (inf.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                    if ((it.getGender().value()) != ((short)((inf.getGender().value()) & (it.getGender().value())))) {
                        it.setGender(com.pullenti.morph.MorphGender.of((short)((inf.getGender().value()) & (it.getGender().value()))));
                        m_NeedRecalc = true;
                    }
                }
                if (chNum) {
                    if ((it.getNumber().value()) != ((short)((inf.getNumber().value()) & (it.getNumber().value())))) {
                        it.setNumber(com.pullenti.morph.MorphNumber.of((short)((inf.getNumber().value()) & (it.getNumber().value()))));
                        m_NeedRecalc = true;
                    }
                }
            }
        }
    }

    /**
     * Убрать элементы, не соответствующие по падежу предлогу
     * @param prep 
     */
    public void removeItemsByPreposition(Token prep) {
        if (!(prep instanceof TextToken)) 
            return;
        com.pullenti.morph.MorphCase mc = com.pullenti.morph.LanguageHelper.getCaseAfterPreposition(((TextToken)com.pullenti.unisharp.Utils.cast(prep, TextToken.class)).lemma);
        if (((com.pullenti.morph.MorphCase.ooBitand(mc, this.getCase()))).isUndefined()) 
            return;
        this.removeItems(mc);
    }

    /**
     * Удалить элементы не из словаря (если все не из словаря, то ничего не удаляется). 
     * То есть оставить только словарный вариант.
     */
    public void removeNotInDictionaryItems() {
        if (m_Items == null) 
            return;
        boolean hasInDict = false;
        for (int i = m_Items.size() - 1; i >= 0; i--) {
            if ((m_Items.get(i) instanceof com.pullenti.morph.MorphWordForm) && ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(m_Items.get(i), com.pullenti.morph.MorphWordForm.class)).isInDictionary()) {
                hasInDict = true;
                break;
            }
        }
        if (hasInDict) {
            for (int i = m_Items.size() - 1; i >= 0; i--) {
                if ((m_Items.get(i) instanceof com.pullenti.morph.MorphWordForm) && !((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(m_Items.get(i), com.pullenti.morph.MorphWordForm.class)).isInDictionary()) {
                    m_Items.remove(i);
                    m_NeedRecalc = true;
                }
            }
        }
    }

    public void removeProperItems() {
        if (m_Items == null) 
            return;
        for (int i = m_Items.size() - 1; i >= 0; i--) {
            if (m_Items.get(i)._getClass().isProper()) {
                m_Items.remove(i);
                m_NeedRecalc = true;
            }
        }
    }

    public void removeItems(com.pullenti.morph.MorphNumber num) {
        if (m_Items == null) 
            return;
        for (int i = m_Items.size() - 1; i >= 0; i--) {
            if (((short)((m_Items.get(i).getNumber().value()) & (num.value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) {
                m_Items.remove(i);
                m_NeedRecalc = true;
            }
        }
    }

    public void removeItems(com.pullenti.morph.MorphGender gen) {
        if (m_Items == null) 
            return;
        for (int i = m_Items.size() - 1; i >= 0; i--) {
            if (((short)((m_Items.get(i).getGender().value()) & (gen.value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                m_Items.remove(i);
                m_NeedRecalc = true;
            }
        }
    }

    /**
     * Удалить элементы, не соответствующие заданным параметрам
     * @param bis 
     * @param cla 
     */
    public void removeItemsListCla(java.util.Collection<com.pullenti.morph.MorphBaseInfo> bis, com.pullenti.morph.MorphClass cla) {
        if (m_Items == null) 
            return;
        for (int i = m_Items.size() - 1; i >= 0; i--) {
            if (cla != null && !cla.isUndefined()) {
                if (((((int)m_Items.get(i)._getClass().value) & ((int)cla.value))) == 0) {
                    if (((m_Items.get(i)._getClass().isProper() || m_Items.get(i)._getClass().isNoun())) && ((cla.isProper() || cla.isNoun()))) {
                    }
                    else {
                        m_Items.remove(i);
                        m_NeedRecalc = true;
                        continue;
                    }
                }
            }
            boolean ok = false;
            for (com.pullenti.morph.MorphBaseInfo it : bis) {
                if (!it.getCase().isUndefined() && !m_Items.get(i).getCase().isUndefined()) {
                    if (((com.pullenti.morph.MorphCase.ooBitand(m_Items.get(i).getCase(), it.getCase()))).isUndefined()) 
                        continue;
                }
                if (it.getGender() != com.pullenti.morph.MorphGender.UNDEFINED && m_Items.get(i).getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                    if (((short)((it.getGender().value()) & (m_Items.get(i).getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                        continue;
                }
                if (it.getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED && m_Items.get(i).getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED) {
                    if (((short)((it.getNumber().value()) & (m_Items.get(i).getNumber().value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                        continue;
                }
                ok = true;
                break;
            }
            if (!ok) {
                m_Items.remove(i);
                m_NeedRecalc = true;
            }
        }
    }

    /**
     * Удалить элементы, не соответствующие другой морфологической коллекции
     * @param col 
     */
    public void removeItemsEx(MorphCollection col, com.pullenti.morph.MorphClass cla) {
        this.removeItemsListCla(col.getItems(), cla);
    }

    public com.pullenti.morph.MorphBaseInfo findItem(com.pullenti.morph.MorphCase cas, com.pullenti.morph.MorphNumber num, com.pullenti.morph.MorphGender gen) {
        if (m_Items == null) 
            return null;
        com.pullenti.morph.MorphBaseInfo res = null;
        int maxCoef = 0;
        for (com.pullenti.morph.MorphBaseInfo it : m_Items) {
            if (!cas.isUndefined()) {
                if (((com.pullenti.morph.MorphCase.ooBitand(it.getCase(), cas))).isUndefined()) 
                    continue;
            }
            if (num != com.pullenti.morph.MorphNumber.UNDEFINED) {
                if (((short)((num.value()) & (it.getNumber().value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                    continue;
            }
            if (gen != com.pullenti.morph.MorphGender.UNDEFINED) {
                if (((short)((gen.value()) & (it.getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                    continue;
            }
            com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(it, com.pullenti.morph.MorphWordForm.class);
            if (wf != null && wf.undefCoef > ((short)0)) {
                if (wf.undefCoef > maxCoef) {
                    maxCoef = (int)wf.undefCoef;
                    res = it;
                }
                continue;
            }
            return it;
        }
        return res;
    }

    public void serialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, m_Class.value);
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, m_Case.value);
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, m_Gender.value());
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, m_Number.value());
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, m_Voice.value());
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, m_Language.value);
        if (m_Items == null) 
            m_Items = new java.util.ArrayList<com.pullenti.morph.MorphBaseInfo>();
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, m_Items.size());
        for (com.pullenti.morph.MorphBaseInfo it : m_Items) {
            this.serializeItem(stream, it);
        }
    }

    public void deserialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        m_Class = com.pullenti.morph.MorphClass._new53(com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream));
        m_Case = com.pullenti.morph.MorphCase._new29(com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream));
        m_Gender = com.pullenti.morph.MorphGender.of(com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream));
        m_Number = com.pullenti.morph.MorphNumber.of(com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream));
        m_Voice = com.pullenti.morph.MorphVoice.of(com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream));
        m_Language = com.pullenti.morph.MorphLang._new56(com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream));
        int cou = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        m_Items = new java.util.ArrayList<com.pullenti.morph.MorphBaseInfo>();
        for (int i = 0; i < cou; i++) {
            com.pullenti.morph.MorphBaseInfo it = this.deserializeItem(stream);
            if (it != null) 
                m_Items.add(it);
        }
        m_NeedRecalc = false;
    }

    private void serializeItem(com.pullenti.unisharp.Stream stream, com.pullenti.morph.MorphBaseInfo bi) throws java.io.IOException {
        byte ty = (byte)0;
        if (bi instanceof com.pullenti.morph.MorphWordForm) 
            ty = (byte)1;
        stream.write(ty);
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, bi._getClass().value);
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, bi.getCase().value);
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, bi.getGender().value());
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, bi.getNumber().value());
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, bi.getLanguage().value);
        com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(bi, com.pullenti.morph.MorphWordForm.class);
        if (wf == null) 
            return;
        com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, wf.normalCase);
        com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, wf.normalFull);
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, wf.undefCoef);
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, (wf.misc == null ? 0 : wf.misc.getAttrs().size()));
        if (wf.misc != null) {
            for (String a : wf.misc.getAttrs()) {
                com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, a);
            }
        }
    }

    private com.pullenti.morph.MorphBaseInfo deserializeItem(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        int ty = stream.read();
        com.pullenti.morph.MorphBaseInfo res = (ty == 0 ? new com.pullenti.morph.MorphBaseInfo() : new com.pullenti.morph.MorphWordForm());
        res._setClass(com.pullenti.morph.MorphClass._new53(com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream)));
        res.setCase(com.pullenti.morph.MorphCase._new29(com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream)));
        res.setGender(com.pullenti.morph.MorphGender.of(com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream)));
        res.setNumber(com.pullenti.morph.MorphNumber.of(com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream)));
        res.setLanguage(com.pullenti.morph.MorphLang._new56(com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream)));
        if (ty == 0) 
            return res;
        com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(res, com.pullenti.morph.MorphWordForm.class);
        wf.normalCase = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
        wf.normalFull = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
        wf.undefCoef = com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream);
        int cou = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        for (int i = 0; i < cou; i++) {
            if (wf.misc == null) 
                wf.misc = new com.pullenti.morph.MorphMiscInfo();
            wf.misc.getAttrs().add(com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream));
        }
        return res;
    }

    public static MorphCollection _new531(com.pullenti.morph.MorphClass _arg1) {
        MorphCollection res = new MorphCollection(null);
        res._setClass(_arg1);
        return res;
    }

    public static MorphCollection _new2551(com.pullenti.morph.MorphGender _arg1) {
        MorphCollection res = new MorphCollection(null);
        res.setGender(_arg1);
        return res;
    }

    public static MorphCollection _new2650(com.pullenti.morph.MorphCase _arg1) {
        MorphCollection res = new MorphCollection(null);
        res.setCase(_arg1);
        return res;
    }

    public MorphCollection() {
        this(null);
    }
    
    static {
        m_EmptyItems = new java.util.ArrayList<com.pullenti.morph.MorphBaseInfo>();
    }
}
