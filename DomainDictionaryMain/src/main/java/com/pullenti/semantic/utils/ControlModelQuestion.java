/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.utils;

/**
 * Вопрос модели управления
 */
public class ControlModelQuestion {

    /**
     * Тип вопроса
     */
    public QuestionType question = QuestionType.UNDEFINED;

    /**
     * Предлог (если есть)
     */
    public String preposition;

    /**
     * Падеж
     */
    public com.pullenti.morph.MorphCase _case;

    /**
     * Краткое написание вопроса
     */
    public String spelling;

    /**
     * Расширенное написание вопроса
     */
    public String spellingEx;

    public int id;

    /**
     * Признак вопроса базовой части модели
     */
    public boolean isBase;

    /**
     * Это абстрактные вопросы где, куда, откуда, когда
     */
    public boolean isAbstract;

    @Override
    public String toString() {
        return spelling;
    }

    /**
     * Проверить на соответствие вопросу предлога с падежом
     * @param prep предлог
     * @param cas падеж
     * @return да-нет
     */
    public boolean check(String prep, com.pullenti.morph.MorphCase cas) {
        if (isAbstract) {
            for (ControlModelQuestion it : ITEMS) {
                if (!it.isAbstract && it.question == this.question) {
                    if (it.check(prep, cas)) 
                        return true;
                }
            }
            return false;
        }
        if (((com.pullenti.morph.MorphCase.ooBitand(cas, _case))).isUndefined()) {
            if (com.pullenti.unisharp.Utils.stringsEq(preposition, "В") && com.pullenti.unisharp.Utils.stringsEq(prep, preposition)) {
                if (_case.isAccusative()) {
                    if (cas.isUndefined() || cas.isNominative()) 
                        return true;
                }
            }
            return false;
        }
        if (prep != null && preposition != null) {
            if (com.pullenti.unisharp.Utils.stringsEq(prep, preposition)) 
                return true;
            if (com.pullenti.unisharp.Utils.stringsEq(preposition, "ОТ") && com.pullenti.unisharp.Utils.stringsEq(prep, "ОТ ИМЕНИ")) 
                return true;
        }
        return com.pullenti.unisharp.Utils.isNullOrEmpty(prep) && com.pullenti.unisharp.Utils.isNullOrEmpty(preposition);
    }

    public ControlModelQuestion checkAbstract(String prep, com.pullenti.morph.MorphCase cas) {
        for (ControlModelQuestion it : ITEMS) {
            if (!it.isAbstract && it.question == this.question) {
                if (it.check(prep, cas)) 
                    return it;
            }
        }
        return null;
    }

    private ControlModelQuestion(String prep, com.pullenti.morph.MorphCase cas, QuestionType typ) {
        preposition = prep;
        _case = cas;
        question = typ;
        if (prep != null) {
            if (cas.isGenitive()) 
                spelling = (prep.toLowerCase() + " чего");
            else if (cas.isDative()) 
                spelling = (prep.toLowerCase() + " чему");
            else if (cas.isAccusative()) 
                spelling = (prep.toLowerCase() + " что");
            else if (cas.isInstrumental()) 
                spelling = (prep.toLowerCase() + " чем");
            else if (cas.isPrepositional()) 
                spelling = (prep.toLowerCase() + " чём");
            spellingEx = spelling;
            if (typ == QuestionType.WHEN) 
                spellingEx = (spelling + "/когда");
            else if (typ == QuestionType.WHERE) 
                spellingEx = (spelling + "/где");
            else if (typ == QuestionType.WHEREFROM) 
                spellingEx = (spelling + "/откуда");
            else if (typ == QuestionType.WHERETO) 
                spellingEx = (spelling + "/куда");
        }
        else if (cas != null) {
            if (cas.isNominative()) {
                spelling = "кто";
                spellingEx = "кто/что";
            }
            else if (cas.isGenitive()) {
                spelling = "чего";
                spellingEx = "кого/чего";
            }
            else if (cas.isDative()) {
                spelling = "чему";
                spellingEx = "кому/чему";
            }
            else if (cas.isAccusative()) {
                spelling = "что";
                spellingEx = "кого/что";
            }
            else if (cas.isInstrumental()) {
                spelling = "чем";
                spellingEx = "кем/чем";
            }
        }
        else if (typ == QuestionType.WHATTODO) {
            spelling = "что делать";
            spellingEx = "что делать";
        }
        else if (typ == QuestionType.WHEN) {
            spelling = "когда";
            spellingEx = "когда";
        }
        else if (typ == QuestionType.WHERE) {
            spelling = "где";
            spellingEx = "где";
        }
        else if (typ == QuestionType.WHEREFROM) {
            spelling = "откуда";
            spellingEx = "откуда";
        }
        else if (typ == QuestionType.WHERETO) {
            spelling = "куда";
            spellingEx = "куда";
        }
    }

    public static ControlModelQuestion getBaseNominative() {
        return ITEMS.get(m_BaseNominativeInd);
    }


    private static int m_BaseNominativeInd;

    public static ControlModelQuestion getBaseGenetive() {
        return ITEMS.get(m_BaseGenetiveInd);
    }


    private static int m_BaseGenetiveInd;

    public static ControlModelQuestion getBaseAccusative() {
        return ITEMS.get(m_BaseAccusativeInd);
    }


    private static int m_BaseAccusativeInd;

    public static ControlModelQuestion getBaseInstrumental() {
        return ITEMS.get(m_BaseInstrumentalInd);
    }


    private static int m_BaseInstrumentalInd;

    public static ControlModelQuestion getBaseDative() {
        return ITEMS.get(m_BaseDativeInd);
    }


    private static int m_BaseDativeInd;

    public static ControlModelQuestion getToDo() {
        return ITEMS.get(m_BaseToDoInd);
    }


    private static int m_BaseToDoInd;

    /**
     * Список всех вопросов ControlModelQuestion
     */
    public static java.util.ArrayList<ControlModelQuestion> ITEMS;

    private static java.util.HashMap<String, Integer> m_HashBySpel;

    public static void initialize() {
        if (ITEMS != null) 
            return;
        ITEMS = new java.util.ArrayList<ControlModelQuestion>();
        for (String s : new String[] {"ИЗ", "ОТ", "С", "ИЗНУТРИ"}) {
            ITEMS.add(new ControlModelQuestion(s, com.pullenti.morph.MorphCase.GENITIVE, QuestionType.WHEREFROM));
        }
        ITEMS.add(new ControlModelQuestion("В", com.pullenti.morph.MorphCase.ACCUSATIVE, QuestionType.WHERETO));
        ITEMS.add(new ControlModelQuestion("НА", com.pullenti.morph.MorphCase.ACCUSATIVE, QuestionType.WHERETO));
        ITEMS.add(new ControlModelQuestion("ПО", com.pullenti.morph.MorphCase.ACCUSATIVE, QuestionType.WHERETO));
        ITEMS.add(new ControlModelQuestion("К", com.pullenti.morph.MorphCase.DATIVE, QuestionType.WHERETO));
        ITEMS.add(new ControlModelQuestion("НАВСТРЕЧУ", com.pullenti.morph.MorphCase.DATIVE, QuestionType.WHERETO));
        ITEMS.add(new ControlModelQuestion("ДО", com.pullenti.morph.MorphCase.GENITIVE, QuestionType.WHERETO));
        for (String s : new String[] {"У", "ОКОЛО", "ВОКРУГ", "ВОЗЛЕ", "ВБЛИЗИ", "МИМО", "ПОЗАДИ", "ВПЕРЕДИ", "ВГЛУБЬ", "ВДОЛЬ", "ВНЕ", "КРОМЕ", "МЕЖДУ", "НАПРОТИВ", "ПОВЕРХ", "ПОДЛЕ", "ПОПЕРЕК", "ПОСЕРЕДИНЕ", "СВЕРХ", "СРЕДИ", "СНАРУЖИ", "ВНУТРИ"}) {
            ITEMS.add(new ControlModelQuestion(s, com.pullenti.morph.MorphCase.GENITIVE, QuestionType.WHERE));
        }
        for (String s : new String[] {"ПАРАЛЛЕЛЬНО"}) {
            ITEMS.add(new ControlModelQuestion(s, com.pullenti.morph.MorphCase.DATIVE, QuestionType.WHERE));
        }
        for (String s : new String[] {"СКВОЗЬ", "ЧЕРЕЗ", "ПОД"}) {
            ITEMS.add(new ControlModelQuestion(s, com.pullenti.morph.MorphCase.ACCUSATIVE, QuestionType.WHERE));
        }
        for (String s : new String[] {"МЕЖДУ", "НАД", "ПОД", "ПЕРЕД", "ЗА"}) {
            ITEMS.add(new ControlModelQuestion(s, com.pullenti.morph.MorphCase.INSTRUMENTAL, QuestionType.WHERE));
        }
        for (String s : new String[] {"В", "НА", "ПРИ"}) {
            ITEMS.add(new ControlModelQuestion(s, com.pullenti.morph.MorphCase.PREPOSITIONAL, QuestionType.WHERE));
        }
        ITEMS.add(new ControlModelQuestion("ПРЕЖДЕ", com.pullenti.morph.MorphCase.GENITIVE, QuestionType.WHEN));
        ITEMS.add(new ControlModelQuestion("ПОСЛЕ", com.pullenti.morph.MorphCase.GENITIVE, QuestionType.WHEN));
        ITEMS.add(new ControlModelQuestion("НАКАНУНЕ", com.pullenti.morph.MorphCase.GENITIVE, QuestionType.WHEN));
        ITEMS.add(new ControlModelQuestion("СПУСТЯ", com.pullenti.morph.MorphCase.ACCUSATIVE, QuestionType.WHEN));
        for (String s : new String[] {"БЕЗ", "ДЛЯ", "РАДИ", "ИЗЗА", "ВВИДУ", "ВЗАМЕН", "ВМЕСТО", "ПРОТИВ", "СВЫШЕ", "ВСЛЕДСТВИЕ", "ПОМИМО", "ПОСРЕДСТВОМ", "ПУТЕМ"}) {
            ITEMS.add(new ControlModelQuestion(s, com.pullenti.morph.MorphCase.GENITIVE, QuestionType.UNDEFINED));
        }
        for (String s : new String[] {"ПО", "ПОДОБНО", "СОГЛАСНО", "СООТВЕТСТВЕННО", "СОРАЗМЕРНО", "ВОПРЕКИ"}) {
            ITEMS.add(new ControlModelQuestion(s, com.pullenti.morph.MorphCase.DATIVE, QuestionType.UNDEFINED));
        }
        for (String s : new String[] {"ПРО", "О", "ЗА", "ВКЛЮЧАЯ", "С"}) {
            ITEMS.add(new ControlModelQuestion(s, com.pullenti.morph.MorphCase.ACCUSATIVE, QuestionType.UNDEFINED));
        }
        for (String s : new String[] {"С"}) {
            ITEMS.add(new ControlModelQuestion(s, com.pullenti.morph.MorphCase.INSTRUMENTAL, QuestionType.UNDEFINED));
        }
        for (String s : new String[] {"О", "ПО"}) {
            ITEMS.add(new ControlModelQuestion(s, com.pullenti.morph.MorphCase.PREPOSITIONAL, QuestionType.UNDEFINED));
        }
        for (int i = 0; i < ITEMS.size(); i++) {
            for (int j = 0; j < (ITEMS.size() - 1); j++) {
                if (ITEMS.get(j).compareTo(ITEMS.get(j + 1)) > 0) {
                    ControlModelQuestion it = ITEMS.get(j);
                    com.pullenti.unisharp.Utils.putArrayValue(ITEMS, j, ITEMS.get(j + 1));
                    com.pullenti.unisharp.Utils.putArrayValue(ITEMS, j + 1, it);
                }
            }
        }
        ITEMS.add((m_BaseNominativeInd = 0), _new3157(null, com.pullenti.morph.MorphCase.NOMINATIVE, true));
        ITEMS.add((m_BaseGenetiveInd = 1), _new3157(null, com.pullenti.morph.MorphCase.GENITIVE, true));
        ITEMS.add((m_BaseAccusativeInd = 2), _new3157(null, com.pullenti.morph.MorphCase.ACCUSATIVE, true));
        ITEMS.add((m_BaseInstrumentalInd = 3), _new3157(null, com.pullenti.morph.MorphCase.INSTRUMENTAL, true));
        ITEMS.add((m_BaseDativeInd = 4), _new3157(null, com.pullenti.morph.MorphCase.DATIVE, true));
        ITEMS.add((m_BaseToDoInd = 5), new ControlModelQuestion(null, null, QuestionType.WHATTODO));
        ITEMS.add(6, _new3162(null, null, QuestionType.WHERE, true));
        ITEMS.add(7, _new3162(null, null, QuestionType.WHERETO, true));
        ITEMS.add(8, _new3162(null, null, QuestionType.WHEREFROM, true));
        ITEMS.add(9, _new3162(null, null, QuestionType.WHEN, true));
        m_HashBySpel = new java.util.HashMap<String, Integer>();
        for (int i = 0; i < ITEMS.size(); i++) {
            ControlModelQuestion it = ITEMS.get(i);
            it.id = i + 1;
            m_HashBySpel.put(it.spelling, i);
        }
    }

    private int compareTo(ControlModelQuestion other) {
        int i = com.pullenti.unisharp.Utils.stringsCompare(preposition, other.preposition, false);
        if (i != 0) 
            return i;
        if (this._casRank() < other._casRank()) 
            return -1;
        if (this._casRank() > other._casRank()) 
            return 1;
        return 0;
    }

    private int _casRank() {
        if (_case.isGenitive()) 
            return 1;
        if (_case.isDative()) 
            return 2;
        if (_case.isAccusative()) 
            return 3;
        if (_case.isInstrumental()) 
            return 4;
        if (_case.isPrepositional()) 
            return 5;
        return 0;
    }

    public static ControlModelQuestion getById(int _id) {
        if (_id >= 1 && _id <= ITEMS.size()) 
            return ITEMS.get(_id - 1);
        return null;
    }

    public static ControlModelQuestion findBySpel(String spel) {
        int ind;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapind3166 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres3167 = com.pullenti.unisharp.Utils.tryGetValue(m_HashBySpel, spel, wrapind3166);
        ind = (wrapind3166.value != null ? wrapind3166.value : 0);
        if (!inoutres3167) 
            return null;
        return ITEMS.get(ind);
    }

    public static ControlModelQuestion _new3157(String _arg1, com.pullenti.morph.MorphCase _arg2, boolean _arg3) {
        ControlModelQuestion res = new ControlModelQuestion(_arg1, _arg2, QuestionType.UNDEFINED);
        res.isBase = _arg3;
        return res;
    }

    public static ControlModelQuestion _new3162(String _arg1, com.pullenti.morph.MorphCase _arg2, QuestionType _arg3, boolean _arg4) {
        ControlModelQuestion res = new ControlModelQuestion(_arg1, _arg2, _arg3);
        res.isAbstract = _arg4;
        return res;
    }
    public ControlModelQuestion() {
    }
}
