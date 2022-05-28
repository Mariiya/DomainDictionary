/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.booklink.internal;

public class TitleItemToken extends com.pullenti.ner.MetaToken {

    private TitleItemToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end, Types _typ) {
        super(begin, end, null);
        if(_globalInstance == null) return;
        typ = _typ;
    }

    public Types typ = Types.UNDEFINED;

    public String value;

    @Override
    public String toString() {
        return (typ.toString() + ": " + ((value != null ? value : "")));
    }

    public static TitleItemToken tryAttach(com.pullenti.ner.Token t) {
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt != null) {
            com.pullenti.ner.Token t1 = tt;
            if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "ТЕМА")) {
                TitleItemToken tit = tryAttach(tt.getNext());
                if (tit != null && tit.typ == Types.TYP) {
                    t1 = tit.getEndToken();
                    if (t1.getNext() != null && t1.getNext().isChar(':')) 
                        t1 = t1.getNext();
                    return _new367(t, t1, Types.TYPANDTHEME, tit.value);
                }
                if (tt.getNext() != null && tt.getNext().isChar(':')) 
                    t1 = tt.getNext();
                return new TitleItemToken(tt, t1, Types.THEME);
            }
            if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "ПО") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "НА")) {
                if (tt.getNext() != null && tt.getNext().isValue("ТЕМА", null)) {
                    t1 = tt.getNext();
                    if (t1.getNext() != null && t1.getNext().isChar(':')) 
                        t1 = t1.getNext();
                    return new TitleItemToken(tt, t1, Types.THEME);
                }
            }
            if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "ПЕРЕВОД") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "ПЕР")) {
                com.pullenti.ner.Token tt2 = tt.getNext();
                if (tt2 != null && tt2.isChar('.')) 
                    tt2 = tt2.getNext();
                if (tt2 instanceof com.pullenti.ner.TextToken) {
                    if (com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt2, com.pullenti.ner.TextToken.class)).term, "C") || com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt2, com.pullenti.ner.TextToken.class)).term, "С")) {
                        tt2 = tt2.getNext();
                        if (tt2 instanceof com.pullenti.ner.TextToken) 
                            return new TitleItemToken(t, tt2, Types.TRANSLATE);
                    }
                }
            }
            if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "СЕКЦИЯ") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "SECTION") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "СЕКЦІЯ")) {
                t1 = tt.getNext();
                if (t1 != null && t1.isChar(':')) 
                    t1 = t1.getNext();
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t1, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null) 
                    t1 = br.getEndToken();
                else if (t1 != tt.getNext()) {
                    for (; t1 != null; t1 = t1.getNext()) {
                        if (t1.isNewlineAfter()) 
                            break;
                    }
                    if (t1 == null) 
                        return null;
                }
                if (t1 != tt.getNext()) 
                    return new TitleItemToken(tt, t1, Types.DUST);
            }
            t1 = null;
            if (tt.isValue("СПЕЦИАЛЬНОСТЬ", "СПЕЦІАЛЬНІСТЬ")) 
                t1 = tt.getNext();
            else if (tt.getMorph()._getClass().isPreposition() && tt.getNext() != null && tt.getNext().isValue("СПЕЦИАЛЬНОСТЬ", "СПЕЦІАЛЬНІСТЬ")) 
                t1 = tt.getNext().getNext();
            else if (tt.isChar('/') && tt.isNewlineBefore()) 
                t1 = tt.getNext();
            if (t1 != null) {
                if (t1.isCharOf(":") || t1.isHiphen()) 
                    t1 = t1.getNext();
                TitleItemToken spec = tryAttachSpeciality(t1, true);
                if (spec != null) {
                    spec.setBeginToken(t);
                    return spec;
                }
            }
        }
        TitleItemToken sss = tryAttachSpeciality(t, false);
        if (sss != null) 
            return sss;
        if (t instanceof com.pullenti.ner.ReferentToken) 
            return null;
        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
        if (npt != null) {
            String s = npt.getNormalCaseText(null, com.pullenti.morph.MorphNumber.UNDEFINED, com.pullenti.morph.MorphGender.UNDEFINED, false);
            com.pullenti.ner.core.TerminToken tok = m_Termins.tryParse(npt.getEndToken(), com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok != null) {
                Types ty = (Types)tok.termin.tag;
                if (ty == Types.TYP) {
                    TitleItemToken tit = tryAttach(tok.getEndToken().getNext());
                    if (tit != null && tit.typ == Types.THEME) 
                        return _new367(npt.getBeginToken(), tit.getEndToken(), Types.TYPANDTHEME, s);
                    if (com.pullenti.unisharp.Utils.stringsEq(s, "РАБОТА") || com.pullenti.unisharp.Utils.stringsEq(s, "РОБОТА") || com.pullenti.unisharp.Utils.stringsEq(s, "ПРОЕКТ")) 
                        return null;
                    com.pullenti.ner.Token t1 = tok.getEndToken();
                    if (com.pullenti.unisharp.Utils.stringsEq(s, "ДИССЕРТАЦИЯ") || com.pullenti.unisharp.Utils.stringsEq(s, "ДИСЕРТАЦІЯ")) {
                        int err = 0;
                        for (com.pullenti.ner.Token ttt = t1.getNext(); ttt != null; ttt = ttt.getNext()) {
                            if (ttt.getMorph()._getClass().isPreposition()) 
                                continue;
                            if (ttt.isValue("СОИСКАНИЕ", "")) 
                                continue;
                            com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(ttt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                            if (npt1 != null && npt1.noun.isValue("СТЕПЕНЬ", "СТУПІНЬ")) {
                                t1 = (ttt = npt1.getEndToken());
                                continue;
                            }
                            com.pullenti.ner.ReferentToken rt = t1.kit.processReferent("PERSON", ttt, null);
                            if (rt != null && (rt.referent instanceof com.pullenti.ner.person.PersonPropertyReferent)) {
                                com.pullenti.ner.person.PersonPropertyReferent ppr = (com.pullenti.ner.person.PersonPropertyReferent)com.pullenti.unisharp.Utils.cast(rt.referent, com.pullenti.ner.person.PersonPropertyReferent.class);
                                if (com.pullenti.unisharp.Utils.stringsEq(ppr.getName(), "доктор наук")) {
                                    t1 = rt.getEndToken();
                                    s = "ДОКТОРСКАЯ ДИССЕРТАЦИЯ";
                                    break;
                                }
                                else if (com.pullenti.unisharp.Utils.stringsEq(ppr.getName(), "кандидат наук")) {
                                    t1 = rt.getEndToken();
                                    s = "КАНДИДАТСКАЯ ДИССЕРТАЦИЯ";
                                    break;
                                }
                                else if (com.pullenti.unisharp.Utils.stringsEq(ppr.getName(), "магистр")) {
                                    t1 = rt.getEndToken();
                                    s = "МАГИСТЕРСКАЯ ДИССЕРТАЦИЯ";
                                    break;
                                }
                            }
                            if (ttt.isValue("ДОКТОР", null) || ttt.isValue("КАНДИДАТ", null) || ttt.isValue("МАГИСТР", "МАГІСТР")) {
                                t1 = ttt;
                                npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(ttt.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                                if (npt1 != null && npt1.getEndToken().isValue("НАУК", null)) 
                                    t1 = npt1.getEndToken();
                                s = (ttt.isValue("МАГИСТР", "МАГІСТР") ? "МАГИСТЕРСКАЯ ДИССЕРТАЦИЯ" : (ttt.isValue("ДОКТОР", null) ? "ДОКТОРСКАЯ ДИССЕРТАЦИЯ" : "КАНДИДАТСКАЯ ДИССЕРТАЦИЯ"));
                                break;
                            }
                            if ((++err) > 3) 
                                break;
                        }
                    }
                    if (t1.getNext() != null && t1.getNext().isChar('.')) 
                        t1 = t1.getNext();
                    if (s.endsWith("ОТЧЕТ") && t1.getNext() != null && t1.getNext().isValue("О", null)) {
                        com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(t1.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION, 0, null);
                        if (npt1 != null && npt1.getMorph().getCase().isPrepositional()) 
                            t1 = npt1.getEndToken();
                    }
                    return _new367(npt.getBeginToken(), t1, ty, s);
                }
            }
        }
        com.pullenti.ner.core.TerminToken tok1 = m_Termins.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok1 != null) {
            com.pullenti.ner.Token t1 = tok1.getEndToken();
            TitleItemToken re = new TitleItemToken(tok1.getBeginToken(), t1, (Types)tok1.termin.tag);
            return re;
        }
        if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, false, false)) {
            tok1 = m_Termins.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok1 != null && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(tok1.getEndToken().getNext(), false, null, false)) {
                com.pullenti.ner.Token t1 = tok1.getEndToken().getNext();
                return new TitleItemToken(tok1.getBeginToken(), t1, (Types)tok1.termin.tag);
            }
        }
        return null;
    }

    private static TitleItemToken tryAttachSpeciality(com.pullenti.ner.Token t, boolean keyWordBefore) {
        if (t == null) 
            return null;
        boolean susp = false;
        if (!keyWordBefore) {
            if (!t.isNewlineBefore()) 
                susp = true;
        }
        StringBuilder val = null;
        com.pullenti.ner.Token t0 = t;
        int digCount = 0;
        for (int i = 0; i < 3; i++) {
            com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
            if (nt == null) 
                break;
            if (nt.typ != com.pullenti.ner.NumberSpellingType.DIGIT || nt.getMorph()._getClass().isAdjective()) 
                break;
            if (val == null) 
                val = new StringBuilder();
            if (susp && t.getLengthChar() != 2) 
                return null;
            String digs = nt.getSourceText();
            digCount += digs.length();
            val.append(digs);
            if (t.getNext() == null) 
                break;
            t = t.getNext();
            if (t.isCharOf(".,") || t.isHiphen()) {
                if (susp && (i < 2)) {
                    if (!t.isChar('.') || t.isWhitespaceAfter() || t.isWhitespaceBefore()) 
                        return null;
                }
                if (t.getNext() != null) 
                    t = t.getNext();
            }
        }
        if (val == null || (digCount < 5)) 
            return null;
        if (digCount != 6) {
            if (!keyWordBefore) 
                return null;
        }
        else {
            val.insert(4, '.');
            val.insert(2, '.');
        }
        for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
            if (tt.isNewlineBefore()) 
                break;
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null) {
                t = (tt = br.getEndToken());
                continue;
            }
            t = tt;
        }
        return _new367(t0, t, Types.SPECIALITY, val.toString());
    }

    private static com.pullenti.ner.core.TerminCollection m_Termins;

    public static void initialize() {
        if (m_Termins != null) 
            return;
        m_Termins = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"РАБОТА", "ДИССЕРТАЦИЯ", "ОТЧЕТ", "ОБЗОР", "ДИПЛОМ", "ПРОЕКТ", "СПРАВКА", "АВТОРЕФЕРАТ", "РЕФЕРАТ", "TECHNOLOGY ISSUES", "TECHNOLOGY COURSE", "УЧЕБНИК", "УЧЕБНОЕ ПОСОБИЕ"}) {
            m_Termins.add(com.pullenti.ner.core.Termin._new100(s, Types.TYP));
        }
        for (String s : new String[] {"РОБОТА", "ДИСЕРТАЦІЯ", "ЗВІТ", "ОГЛЯД", "ДИПЛОМ", "ПРОЕКТ", "ДОВІДКА", "АВТОРЕФЕРАТ", "РЕФЕРАТ"}) {
            m_Termins.add(com.pullenti.ner.core.Termin._new372(s, com.pullenti.morph.MorphLang.UA, Types.TYP));
        }
        for (String s : new String[] {"ДОПУСТИТЬ К ЗАЩИТА", "РЕКОМЕНДОВАТЬ К ЗАЩИТА", "ДОЛЖНОСТЬ", "ЦЕЛЬ РАБОТЫ", "НА ПРАВАХ РУКОПИСИ", "ПО ИЗДАНИЮ", "ПОЛУЧЕНО"}) {
            m_Termins.add(com.pullenti.ner.core.Termin._new100(s, Types.DUST));
        }
        for (String s : new String[] {"ДОПУСТИТИ ДО ЗАХИСТУ", "РЕКОМЕНДУВАТИ ДО ЗАХИСТ", "ПОСАДА", "МЕТА РОБОТИ", "НА ПРАВАХ РУКОПИСУ", "ПО ВИДАННЮ", "ОТРИМАНО"}) {
            m_Termins.add(com.pullenti.ner.core.Termin._new372(s, com.pullenti.morph.MorphLang.UA, Types.DUST));
        }
        for (String s : new String[] {"УТВЕРЖДАТЬ", "СОГЛАСЕН", "СТВЕРДЖУВАТИ", "ЗГОДЕН"}) {
            m_Termins.add(com.pullenti.ner.core.Termin._new100(s, Types.ADOPT));
        }
        for (String s : new String[] {"НАУЧНЫЙ РУКОВОДИТЕЛЬ", "РУКОВОДИТЕЛЬ РАБОТА", "НАУКОВИЙ КЕРІВНИК", "КЕРІВНИК РОБОТА"}) {
            m_Termins.add(com.pullenti.ner.core.Termin._new100(s, Types.BOSS));
        }
        for (String s : new String[] {"НАУЧНЫЙ КОНСУЛЬТАНТ", "КОНСУЛЬТАНТ", "НАУКОВИЙ КОНСУЛЬТАНТ"}) {
            m_Termins.add(com.pullenti.ner.core.Termin._new100(s, Types.CONSULTANT));
        }
        for (String s : new String[] {"РЕДАКТОР", "РЕДАКТОРСКАЯ ГРУППА", "РЕЦЕНЗЕНТ", "РЕДАКТОРСЬКА ГРУПА"}) {
            m_Termins.add(com.pullenti.ner.core.Termin._new100(s, Types.EDITOR));
        }
        for (String s : new String[] {"ОФИЦИАЛЬНЫЙ ОППОНЕНТ", "ОППОНЕНТ", "ОФІЦІЙНИЙ ОПОНЕНТ"}) {
            m_Termins.add(com.pullenti.ner.core.Termin._new100(s, Types.OPPONENT));
        }
        for (String s : new String[] {"ИСПОЛНИТЕЛЬ", "ОТВЕТСТВЕННЫЙ ИСПОЛНИТЕЛЬ", "АВТОР", "ДИПЛОМНИК", "КОЛЛЕКТТИВ ИСПОЛНИТЕЛЕЙ", "ВЫПОЛНИТЬ", "ИСПОЛНИТЬ"}) {
            m_Termins.add(com.pullenti.ner.core.Termin._new100(s, Types.WORKER));
        }
        for (String s : new String[] {"ВИКОНАВЕЦЬ", "ВІДПОВІДАЛЬНИЙ ВИКОНАВЕЦЬ", "АВТОР", "ДИПЛОМНИК", "КОЛЛЕКТТИВ ВИКОНАВЦІВ", "ВИКОНАТИ", "ВИКОНАТИ"}) {
            m_Termins.add(com.pullenti.ner.core.Termin._new372(s, com.pullenti.morph.MorphLang.UA, Types.WORKER));
        }
        for (String s : new String[] {"КЛЮЧЕВЫЕ СЛОВА", "KEYWORDS", "КЛЮЧОВІ СЛОВА"}) {
            m_Termins.add(com.pullenti.ner.core.Termin._new100(s, Types.KEYWORDS));
        }
    }

    public static TitleItemToken _new367(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Types _arg3, String _arg4) {
        TitleItemToken res = new TitleItemToken(_arg1, _arg2, _arg3);
        res.value = _arg4;
        return res;
    }

    public static class Types implements Comparable<Types> {
    
        public static final Types UNDEFINED; // 0
    
        public static final Types TYP; // 1
    
        public static final Types THEME; // 2
    
        public static final Types TYPANDTHEME; // 3
    
        public static final Types BOSS; // 4
    
        public static final Types WORKER; // 5
    
        public static final Types EDITOR; // 6
    
        public static final Types CONSULTANT; // 7
    
        public static final Types OPPONENT; // 8
    
        public static final Types OTHERROLE; // 9
    
        public static final Types TRANSLATE; // 10
    
        public static final Types ADOPT; // 11
    
        public static final Types DUST; // 12
    
        public static final Types SPECIALITY; // 13
    
        public static final Types KEYWORDS; // 14
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private Types(int val, String str) { m_val = val; m_str = str; }
        @Override
        public String toString() {
            if(m_str != null) return m_str;
            return ((Integer)m_val).toString();
        }
        @Override
        public int hashCode() {
            return (int)m_val;
        }
        @Override
        public int compareTo(Types v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, Types> mapIntToEnum; 
        private static java.util.HashMap<String, Types> mapStringToEnum; 
        private static Types[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static Types of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            Types item = new Types(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static Types of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static Types[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, Types>();
            mapStringToEnum = new java.util.HashMap<String, Types>();
            UNDEFINED = new Types(0, "UNDEFINED");
            mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
            mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
            TYP = new Types(1, "TYP");
            mapIntToEnum.put(TYP.value(), TYP);
            mapStringToEnum.put(TYP.m_str.toUpperCase(), TYP);
            THEME = new Types(2, "THEME");
            mapIntToEnum.put(THEME.value(), THEME);
            mapStringToEnum.put(THEME.m_str.toUpperCase(), THEME);
            TYPANDTHEME = new Types(3, "TYPANDTHEME");
            mapIntToEnum.put(TYPANDTHEME.value(), TYPANDTHEME);
            mapStringToEnum.put(TYPANDTHEME.m_str.toUpperCase(), TYPANDTHEME);
            BOSS = new Types(4, "BOSS");
            mapIntToEnum.put(BOSS.value(), BOSS);
            mapStringToEnum.put(BOSS.m_str.toUpperCase(), BOSS);
            WORKER = new Types(5, "WORKER");
            mapIntToEnum.put(WORKER.value(), WORKER);
            mapStringToEnum.put(WORKER.m_str.toUpperCase(), WORKER);
            EDITOR = new Types(6, "EDITOR");
            mapIntToEnum.put(EDITOR.value(), EDITOR);
            mapStringToEnum.put(EDITOR.m_str.toUpperCase(), EDITOR);
            CONSULTANT = new Types(7, "CONSULTANT");
            mapIntToEnum.put(CONSULTANT.value(), CONSULTANT);
            mapStringToEnum.put(CONSULTANT.m_str.toUpperCase(), CONSULTANT);
            OPPONENT = new Types(8, "OPPONENT");
            mapIntToEnum.put(OPPONENT.value(), OPPONENT);
            mapStringToEnum.put(OPPONENT.m_str.toUpperCase(), OPPONENT);
            OTHERROLE = new Types(9, "OTHERROLE");
            mapIntToEnum.put(OTHERROLE.value(), OTHERROLE);
            mapStringToEnum.put(OTHERROLE.m_str.toUpperCase(), OTHERROLE);
            TRANSLATE = new Types(10, "TRANSLATE");
            mapIntToEnum.put(TRANSLATE.value(), TRANSLATE);
            mapStringToEnum.put(TRANSLATE.m_str.toUpperCase(), TRANSLATE);
            ADOPT = new Types(11, "ADOPT");
            mapIntToEnum.put(ADOPT.value(), ADOPT);
            mapStringToEnum.put(ADOPT.m_str.toUpperCase(), ADOPT);
            DUST = new Types(12, "DUST");
            mapIntToEnum.put(DUST.value(), DUST);
            mapStringToEnum.put(DUST.m_str.toUpperCase(), DUST);
            SPECIALITY = new Types(13, "SPECIALITY");
            mapIntToEnum.put(SPECIALITY.value(), SPECIALITY);
            mapStringToEnum.put(SPECIALITY.m_str.toUpperCase(), SPECIALITY);
            KEYWORDS = new Types(14, "KEYWORDS");
            mapIntToEnum.put(KEYWORDS.value(), KEYWORDS);
            mapStringToEnum.put(KEYWORDS.m_str.toUpperCase(), KEYWORDS);
            java.util.Collection<Types> col = mapIntToEnum.values();
            m_Values = new Types[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }

    public TitleItemToken() {
        super();
    }
    public static TitleItemToken _globalInstance;
    
    static {
        try { _globalInstance = new TitleItemToken(); } 
        catch(Exception e) { }
    }
}
