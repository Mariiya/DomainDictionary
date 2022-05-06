/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.mail.internal;

public class MailLine extends com.pullenti.ner.MetaToken {

    private MailLine(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
        if(_globalInstance == null) return;
        typ = Types.UNDEFINED;
    }

    public int getCharsCount() {
        int cou = 0;
        for (com.pullenti.ner.Token t = getBeginToken(); t != null; t = t.getNext()) {
            cou += t.getLengthChar();
            if (t == getEndToken()) 
                break;
        }
        return cou;
    }


    public int getWords() {
        int cou = 0;
        for (com.pullenti.ner.Token t = getBeginToken(); t != null && t.getEndChar() <= getEndChar(); t = t.getNext()) {
            if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter() && t.getLengthChar() > 2) {
                if (t.tag == null) 
                    cou++;
            }
        }
        return cou;
    }


    public boolean isPureEn() {
        int en = 0;
        int ru = 0;
        for (com.pullenti.ner.Token t = getBeginToken(); t != null && t.getEndChar() <= getEndChar(); t = t.getNext()) {
            if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter()) {
                if (t.chars.isCyrillicLetter()) 
                    ru++;
                else if (t.chars.isLatinLetter()) 
                    en++;
            }
        }
        if (en > 0 && ru == 0) 
            return true;
        return false;
    }


    public boolean isPureRu() {
        int en = 0;
        int ru = 0;
        for (com.pullenti.ner.Token t = getBeginToken(); t != null && t.getEndChar() <= getEndChar(); t = t.getNext()) {
            if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter()) {
                if (t.chars.isCyrillicLetter()) 
                    ru++;
                else if (t.chars.isLatinLetter()) 
                    en++;
            }
        }
        if (ru > 0 && en == 0) 
            return true;
        return false;
    }


    public int lev = 0;

    public Types typ = Types.UNDEFINED;

    public java.util.ArrayList<com.pullenti.ner.Referent> refs = new java.util.ArrayList<com.pullenti.ner.Referent>();

    public boolean mustBeFirstLine = false;

    public com.pullenti.ner.Referent getMailAddr() {
        for (com.pullenti.ner.Token t = getBeginToken(); t != null && t.getEndChar() <= getEndChar(); t = t.getNext()) {
            if (t.getReferent() != null && com.pullenti.unisharp.Utils.stringsEq(t.getReferent().getTypeName(), "URI")) {
                if (com.pullenti.unisharp.Utils.stringsEq(t.getReferent().getStringValue("SCHEME"), "mailto")) 
                    return t.getReferent();
            }
        }
        return null;
    }


    public boolean isRealFrom() {
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(getBeginToken(), com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return false;
        return com.pullenti.unisharp.Utils.stringsEq(tt.term, "FROM") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "ОТ");
    }


    @Override
    public String toString() {
        return ((mustBeFirstLine ? "(1) " : "") + lev + " " + typ.toString() + ": " + this.getSourceText());
    }

    public static MailLine parse(com.pullenti.ner.Token t0, int _lev, int maxCount) {
        if (t0 == null) 
            return null;
        MailLine res = new MailLine(t0, t0);
        boolean pr = true;
        int cou = 0;
        for (com.pullenti.ner.Token t = t0; t != null; t = t.getNext(),cou++) {
            if (t.isNewlineBefore() && t0 != t) 
                break;
            if (maxCount > 0 && cou > maxCount) 
                break;
            res.setEndToken(t);
            if (t.isTableControlChar() || t.isHiphen()) 
                continue;
            if (pr) {
                if ((t instanceof com.pullenti.ner.TextToken) && t.isCharOf(">|")) 
                    res.lev++;
                else {
                    pr = false;
                    com.pullenti.ner.core.TerminToken tok = m_FromWords.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
                    if (tok != null && tok.getEndToken().getNext() != null && tok.getEndToken().getNext().isChar(':')) {
                        res.typ = Types.FROM;
                        t = tok.getEndToken().getNext();
                        continue;
                    }
                }
            }
            if (t instanceof com.pullenti.ner.ReferentToken) {
                com.pullenti.ner.Referent r = t.getReferent();
                if (r != null) {
                    if ((((r instanceof com.pullenti.ner.person.PersonReferent) || (r instanceof com.pullenti.ner.geo.GeoReferent) || (r instanceof com.pullenti.ner.address.AddressReferent)) || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "PHONE") || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "URI")) || (r instanceof com.pullenti.ner.person.PersonPropertyReferent) || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "ORGANIZATION")) 
                        res.refs.add(r);
                }
            }
        }
        if (res.typ == Types.UNDEFINED) {
            com.pullenti.ner.Token t = t0;
            for (; t != null && (t.getEndChar() < res.getEndChar()); t = t.getNext()) {
                if (!t.isHiphen() && t.chars.isLetter()) 
                    break;
            }
            int ok = 0;
            int nams = 0;
            int oth = 0;
            com.pullenti.ner.Token lastComma = null;
            for (; t != null && (t.getEndChar() < res.getEndChar()); t = t.getNext()) {
                if (t.getReferent() instanceof com.pullenti.ner.person.PersonReferent) {
                    nams++;
                    continue;
                }
                if (t instanceof com.pullenti.ner.TextToken) {
                    if (!t.chars.isLetter()) {
                        lastComma = t;
                        continue;
                    }
                    com.pullenti.ner.core.TerminToken tok = m_HelloWords.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
                    if (tok != null && (t instanceof com.pullenti.ner.TextToken) && t.isValue("ДОРОГОЙ", null)) {
                        if (com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "ДОРОГОЙ") || com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "ДОРОГАЯ") || com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "ДОРОГИЕ")) {
                        }
                        else 
                            tok = null;
                    }
                    if (tok != null) {
                        ok++;
                        t = tok.getEndToken();
                        continue;
                    }
                    if (t.isValue("ВСЕ", null) || t.isValue("ALL", null) || t.isValue("TEAM", null)) {
                        nams++;
                        continue;
                    }
                    com.pullenti.ner.person.internal.PersonItemToken pit = com.pullenti.ner.person.internal.PersonItemToken.tryAttach(t, com.pullenti.ner.person.internal.PersonItemToken.ParseAttr.NO, null);
                    if (pit != null) {
                        nams++;
                        t = pit.getEndToken();
                        continue;
                    }
                }
                if ((++oth) > 3) {
                    if (ok > 0 && lastComma != null) {
                        res.setEndToken(lastComma);
                        oth = 0;
                    }
                    break;
                }
            }
            if ((oth < 3) && ok > 0) 
                res.typ = Types.HELLO;
        }
        if (res.typ == Types.UNDEFINED) {
            int okWords = 0;
            if (t0.isValue("HAVE", null)) {
            }
            for (com.pullenti.ner.Token t = t0; t != null && t.getEndChar() <= res.getEndChar(); t = (t == null ? null : t.getNext())) {
                if (!(t instanceof com.pullenti.ner.TextToken)) 
                    continue;
                if (t.isChar('<')) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null) {
                        t = br.getEndToken();
                        continue;
                    }
                }
                if (!t.isLetters() || t.isTableControlChar()) 
                    continue;
                com.pullenti.ner.core.TerminToken tok = m_RegardWords.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok != null) {
                    okWords++;
                    for (; t != null && t.getEndChar() <= tok.getEndChar(); t = t.getNext()) {
                        t.tag = tok.termin;
                    }
                    t = tok.getEndToken();
                    if ((t.getNext() instanceof com.pullenti.ner.TextToken) && t.getNext().getMorph().getCase().isGenitive()) {
                        for (t = t.getNext(); t != null && t.getEndChar() <= res.getEndChar(); t = t.getNext()) {
                            if (t.getMorph()._getClass().isConjunction()) 
                                continue;
                            com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                            if (npt1 == null) 
                                break;
                            if (!npt1.getMorph().getCase().isGenitive()) 
                                break;
                            for (; t.getEndChar() < npt1.getEndChar(); t = t.getNext()) {
                                t.tag = t;
                            }
                            t.tag = t;
                        }
                    }
                    continue;
                }
                if ((t.getMorph()._getClass().isPreposition() || t.getMorph()._getClass().isConjunction() || t.getMorph()._getClass().isMisc()) || t.isValue("C", null)) 
                    continue;
                if ((okWords > 0 && t.getPrevious() != null && t.getPrevious().isComma()) && t.getPrevious().getBeginChar() > t0.getBeginChar() && !t.chars.isAllLower()) {
                    res.setEndToken(t.getPrevious());
                    break;
                }
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt == null) {
                    if ((res.getEndChar() - t.getEndChar()) > 10) 
                        okWords = 0;
                    break;
                }
                tok = m_RegardWords.tryParse(npt.getEndToken(), com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok != null && (npt.getEndToken() instanceof com.pullenti.ner.TextToken)) {
                    String term = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(npt.getEndToken(), com.pullenti.ner.TextToken.class)).term;
                    if (com.pullenti.unisharp.Utils.stringsEq(term, "ДЕЛ")) 
                        tok = null;
                }
                if (tok == null) {
                    if (npt.noun.isValue("НАДЕЖДА", null)) 
                        t.tag = t;
                    else if (okWords > 0 && t.isValue("NICE", null) && ((res.getEndChar() - npt.getEndChar()) < 13)) 
                        t.tag = t;
                    else 
                        okWords = 0;
                    break;
                }
                okWords++;
                for (; t != null && t.getEndChar() <= tok.getEndChar(); t = t.getNext()) {
                    t.tag = tok.termin;
                }
                t = tok.getEndToken();
            }
            if (okWords > 0) 
                res.typ = Types.BESTREGARDS;
        }
        if (res.typ == Types.UNDEFINED) {
            com.pullenti.ner.Token t = t0;
            for (; t != null && (t.getEndChar() < res.getEndChar()); t = t.getNext()) {
                if (!(t instanceof com.pullenti.ner.TextToken)) 
                    break;
                else if (!t.isHiphen() && t.chars.isLetter()) 
                    break;
            }
            if (t != null) {
                if (t != t0) {
                }
                if (((t.isValue("ПЕРЕСЫЛАЕМОЕ", null) || t.isValue("ПЕРЕАДРЕСОВАННОЕ", null))) && t.getNext() != null && t.getNext().isValue("СООБЩЕНИЕ", null)) {
                    res.typ = Types.FROM;
                    res.mustBeFirstLine = true;
                }
                else if ((t.isValue("НАЧАЛО", null) && t.getNext() != null && ((t.getNext().isValue("ПЕРЕСЫЛАЕМОЕ", null) || t.getNext().isValue("ПЕРЕАДРЕСОВАННОЕ", null)))) && t.getNext().getNext() != null && t.getNext().getNext().isValue("СООБЩЕНИЕ", null)) {
                    res.typ = Types.FROM;
                    res.mustBeFirstLine = true;
                }
                else if (t.isValue("ORIGINAL", null) && t.getNext() != null && ((t.getNext().isValue("MESSAGE", null) || t.getNext().isValue("APPOINTMENT", null)))) {
                    res.typ = Types.FROM;
                    res.mustBeFirstLine = true;
                }
                else if (t.isValue("ПЕРЕСЛАНО", null) && t.getNext() != null && t.getNext().isValue("ПОЛЬЗОВАТЕЛЕМ", null)) {
                    res.typ = Types.FROM;
                    res.mustBeFirstLine = true;
                }
                else if (((t.getReferent() != null && com.pullenti.unisharp.Utils.stringsEq(t.getReferent().getTypeName(), "DATE"))) || ((t.isValue("IL", null) && t.getNext() != null && t.getNext().isValue("GIORNO", null))) || ((t.isValue("ON", null) && (t.getNext() instanceof com.pullenti.ner.ReferentToken) && com.pullenti.unisharp.Utils.stringsEq(t.getNext().getReferent().getTypeName(), "DATE")))) {
                    boolean hasFrom = false;
                    boolean hasDate = t.getReferent() != null && com.pullenti.unisharp.Utils.stringsEq(t.getReferent().getTypeName(), "DATE");
                    if (t.isNewlineAfter() && (_lev < 5)) {
                        MailLine res1 = parse(t.getNext(), _lev + 1, 0);
                        if (res1 != null && res1.typ == Types.HELLO) 
                            res.typ = Types.FROM;
                    }
                    MailLine _next = parse(res.getEndToken().getNext(), _lev + 1, 0);
                    if (_next != null) {
                        if (_next.typ != Types.UNDEFINED) 
                            _next = null;
                    }
                    int tmax = res.getEndChar();
                    if (_next != null) 
                        tmax = _next.getEndChar();
                    com.pullenti.ner.core.BracketSequenceToken br1 = null;
                    for (; t != null && t.getEndChar() <= tmax; t = (t == null ? null : t.getNext())) {
                        if (t.isValue("ОТ", null) || t.isValue("FROM", null)) 
                            hasFrom = true;
                        else if (t.getReferent() != null && ((com.pullenti.unisharp.Utils.stringsEq(t.getReferent().getTypeName(), "URI") || (t.getReferent() instanceof com.pullenti.ner.person.PersonReferent)))) {
                            if (com.pullenti.unisharp.Utils.stringsEq(t.getReferent().getTypeName(), "URI") && hasDate) {
                                if (br1 != null) {
                                    hasFrom = true;
                                    _next = null;
                                }
                                if (t.getPrevious().isChar('<') && t.getNext() != null && t.getNext().isChar('>')) {
                                    t = t.getNext();
                                    if (t.getNext() != null && t.getNext().isChar(':')) 
                                        t = t.getNext();
                                    if (t.isNewlineAfter()) {
                                        hasFrom = true;
                                        _next = null;
                                    }
                                }
                            }
                            for (t = t.getNext(); t != null && t.getEndChar() <= res.getEndChar(); t = t.getNext()) {
                                if (t.isValue("HA", null) && t.getNext() != null && t.getNext().isValue("SCRITTO", null)) {
                                    hasFrom = true;
                                    break;
                                }
                                else if (((t.isValue("НАПИСАТЬ", null) || t.isValue("WROTE", null))) && ((res.getEndChar() - t.getEndChar()) < 10)) {
                                    hasFrom = true;
                                    break;
                                }
                            }
                            if (t == null) 
                                continue;
                            if (hasFrom) {
                                res.typ = Types.FROM;
                                if (_next != null && t.getEndChar() >= _next.getBeginChar()) 
                                    res.setEndToken(_next.getEndToken());
                            }
                            break;
                        }
                        else if (br1 == null && !t.isChar('<') && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) {
                            br1 = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                            if (br1 != null) 
                                t = br1.getEndToken();
                        }
                    }
                }
                else {
                    boolean hasUri = false;
                    for (; t != null && (t.getEndChar() < res.getEndChar()); t = t.getNext()) {
                        if (t.getReferent() != null && ((com.pullenti.unisharp.Utils.stringsEq(t.getReferent().getTypeName(), "URI") || (t.getReferent() instanceof com.pullenti.ner.person.PersonReferent)))) 
                            hasUri = true;
                        else if (t.isValue("ПИСАТЬ", null) && hasUri) {
                            if (t.getNext() != null && t.getNext().isChar('(')) {
                                if (hasUri) 
                                    res.typ = Types.FROM;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return res;
    }

    private static com.pullenti.ner.core.TerminCollection m_RegardWords;

    private static com.pullenti.ner.core.TerminCollection m_FromWords;

    private static com.pullenti.ner.core.TerminCollection m_HelloWords;

    public static boolean isKeyword(com.pullenti.ner.Token t) {
        if (t == null) 
            return false;
        if (m_RegardWords.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO) != null) 
            return true;
        if (m_FromWords.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO) != null) 
            return true;
        if (m_HelloWords.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO) != null) 
            return true;
        return false;
    }

    public static void initialize() {
        if (m_RegardWords != null) 
            return;
        m_RegardWords = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"УВАЖЕНИЕ", "ПОЧТЕНИЕ", "С УВАЖЕНИЕМ", "ПОЖЕЛАНИE", "ДЕНЬ", "ХОРОШЕГО ДНЯ", "ИСКРЕННЕ ВАШ", "УДАЧА", "СПАСИБО", "ЦЕЛОВАТЬ", "ПОВАГА", "З ПОВАГОЮ", "ПОБАЖАННЯ", "ДЕНЬ", "ЩИРО ВАШ", "ДЯКУЮ", "ЦІЛУВАТИ", "BEST REGARDS", "REGARDS", "BEST WISHES", "KIND REGARDS", "GOOD BYE", "BYE", "THANKS", "THANK YOU", "MANY THANKS", "DAY", "VERY MUCH", "HAVE", "LUCK", "Yours sincerely", "sincerely Yours", "Looking forward", "Ar cieņu"}) {
            m_RegardWords.add(new com.pullenti.ner.core.Termin(s.toUpperCase(), null, false));
        }
        m_FromWords = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"FROM", "TO", "CC", "SENT", "SUBJECT", "SENDER", "TIME", "ОТ КОГО", "КОМУ", "ДАТА", "ТЕМА", "КОПИЯ", "ОТ", "ОТПРАВЛЕНО", "WHEN", "WHERE"}) {
            m_FromWords.add(new com.pullenti.ner.core.Termin(s, null, false));
        }
        m_HelloWords = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"HI", "HELLO", "DEAR", "GOOD MORNING", "GOOD DAY", "GOOD EVENING", "GOOD NIGHT", "ЗДРАВСТВУЙ", "ЗДРАВСТВУЙТЕ", "ПРИВЕТСТВУЮ", "ПРИВЕТ", "ПРИВЕТИК", "УВАЖАЕМЫЙ", "ДОРОГОЙ", "ЛЮБЕЗНЫЙ", "ДОБРОЕ УТРО", "ДОБРЫЙ ДЕНЬ", "ДОБРЫЙ ВЕЧЕР", "ДОБРОЙ НОЧИ", "ЗДРАСТУЙ", "ЗДРАСТУЙТЕ", "ВІТАЮ", "ПРИВІТ", "ПРИВІТ", "ШАНОВНИЙ", "ДОРОГИЙ", "ЛЮБИЙ", "ДОБРОГО РАНКУ", "ДОБРИЙ ДЕНЬ", "ДОБРИЙ ВЕЧІР", "ДОБРОЇ НОЧІ"}) {
            m_HelloWords.add(new com.pullenti.ner.core.Termin(s, null, false));
        }
    }

    public static class Types implements Comparable<Types> {
    
        public static final Types UNDEFINED; // 0
    
        public static final Types HELLO; // 1
    
        public static final Types BESTREGARDS; // 2
    
        public static final Types FROM; // 3
    
    
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
            HELLO = new Types(1, "HELLO");
            mapIntToEnum.put(HELLO.value(), HELLO);
            mapStringToEnum.put(HELLO.m_str.toUpperCase(), HELLO);
            BESTREGARDS = new Types(2, "BESTREGARDS");
            mapIntToEnum.put(BESTREGARDS.value(), BESTREGARDS);
            mapStringToEnum.put(BESTREGARDS.m_str.toUpperCase(), BESTREGARDS);
            FROM = new Types(3, "FROM");
            mapIntToEnum.put(FROM.value(), FROM);
            mapStringToEnum.put(FROM.m_str.toUpperCase(), FROM);
            java.util.Collection<Types> col = mapIntToEnum.values();
            m_Values = new Types[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }

    public MailLine() {
        super();
        if(_globalInstance == null) return;
        typ = Types.UNDEFINED;
    }
    public static MailLine _globalInstance;
    
    static {
        try { _globalInstance = new MailLine(); } 
        catch(Exception e) { }
    }
}
