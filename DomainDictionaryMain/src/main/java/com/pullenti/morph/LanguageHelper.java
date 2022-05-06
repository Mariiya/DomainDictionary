/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph;

// Служба подержки языков.
// В качестве универсальных идентификаторов языков выступает 2-х символьный идентификатор ISO 639-1.
// Также содержит некоторые полезные функции.
public class LanguageHelper {

    public static String getLanguageForText(String text) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(text)) 
            return null;
        int i;
        int j;
        int ruChars = 0;
        int enChars = 0;
        for (i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (!Character.isLetter(ch)) 
                continue;
            j = (int)ch;
            if (j >= 0x400 && (j < 0x500)) 
                ruChars++;
            else if (j < 0x80) 
                enChars++;
        }
        if ((ruChars > (enChars * 2)) && ruChars > 10) 
            return "ru";
        if (ruChars > 0 && enChars == 0) 
            return "ru";
        if (enChars > 0) 
            return "en";
        return null;
    }

    public static MorphLang getWordLang(String word) {
        int cyr = 0;
        int lat = 0;
        int undef = 0;
        for (char ch : word.toCharArray()) {
            com.pullenti.morph.internal.UnicodeInfo ui = com.pullenti.morph.internal.UnicodeInfo.ALLCHARS.get((int)ch);
            if (ui.isLetter()) {
                if (ui.isCyrillic()) 
                    cyr++;
                else if (ui.isLatin()) 
                    lat++;
                else 
                    undef++;
            }
        }
        if (undef > 0) 
            return MorphLang.UNKNOWN;
        if (cyr == 0 && lat == 0) 
            return MorphLang.UNKNOWN;
        if (cyr == 0) 
            return MorphLang.EN;
        if (lat > 0) 
            return MorphLang.UNKNOWN;
        MorphLang lang = MorphLang.ooBitor(MorphLang.ooBitor(MorphLang.UA, MorphLang.ooBitor(MorphLang.RU, MorphLang.BY)), MorphLang.KZ);
        for (char ch : word.toCharArray()) {
            com.pullenti.morph.internal.UnicodeInfo ui = com.pullenti.morph.internal.UnicodeInfo.ALLCHARS.get((int)ch);
            if (ui.isLetter()) {
                if (ch == 'Ґ' || ch == 'Є' || ch == 'Ї') {
                    lang.setRu(false);
                    lang.setBy(false);
                }
                else if (ch == 'І') 
                    lang.setRu(false);
                else if (ch == 'Ё' || ch == 'Э') {
                    lang.setUa(false);
                    lang.setKz(false);
                }
                else if (ch == 'Ы') 
                    lang.setUa(false);
                else if (ch == 'Ў') {
                    lang.setRu(false);
                    lang.setUa(false);
                }
                else if (ch == 'Щ') 
                    lang.setBy(false);
                else if (ch == 'Ъ') {
                    lang.setBy(false);
                    lang.setUa(false);
                    lang.setKz(false);
                }
                else if ((((ch == 'Ә' || ch == 'Ғ' || ch == 'Қ') || ch == 'Ң' || ch == 'Ө') || ((ch == 'Ұ' && word.length() > 1)) || ch == 'Ү') || ch == 'Һ') {
                    lang.setBy(false);
                    lang.setUa(false);
                    lang.setRu(false);
                }
                else if ((ch == 'В' || ch == 'Ф' || ch == 'Ц') || ch == 'Ч' || ch == 'Ь') 
                    lang.setKz(false);
            }
        }
        return lang;
    }

    public static boolean isLatinChar(char ch) {
        com.pullenti.morph.internal.UnicodeInfo ui = com.pullenti.morph.internal.UnicodeInfo.ALLCHARS.get((int)ch);
        return ui.isLatin();
    }

    public static boolean isLatin(String str) {
        if (str == null) 
            return false;
        for (int i = 0; i < str.length(); i++) {
            if (!isLatinChar(str.charAt(i))) {
                if (!com.pullenti.unisharp.Utils.isWhitespace(str.charAt(i)) && str.charAt(i) != '-') 
                    return false;
            }
        }
        return true;
    }

    public static boolean isCyrillicChar(char ch) {
        com.pullenti.morph.internal.UnicodeInfo ui = com.pullenti.morph.internal.UnicodeInfo.ALLCHARS.get((int)ch);
        return ui.isCyrillic();
    }

    public static boolean isCyrillic(String str) {
        if (str == null) 
            return false;
        for (int i = 0; i < str.length(); i++) {
            if (!isCyrillicChar(str.charAt(i))) {
                if (!com.pullenti.unisharp.Utils.isWhitespace(str.charAt(i)) && str.charAt(i) != '-') 
                    return false;
            }
        }
        return true;
    }

    public static boolean isHiphen(char ch) {
        com.pullenti.morph.internal.UnicodeInfo ui = com.pullenti.morph.internal.UnicodeInfo.ALLCHARS.get((int)ch);
        return ui.isHiphen();
    }

    public static boolean isCyrillicVowel(char ch) {
        com.pullenti.morph.internal.UnicodeInfo ui = com.pullenti.morph.internal.UnicodeInfo.ALLCHARS.get((int)ch);
        return ui.isCyrillic() && ui.isVowel();
    }

    public static boolean isLatinVowel(char ch) {
        com.pullenti.morph.internal.UnicodeInfo ui = com.pullenti.morph.internal.UnicodeInfo.ALLCHARS.get((int)ch);
        return ui.isLatin() && ui.isVowel();
    }

    public static char getCyrForLat(char lat) {
        int i = m_LatChars.indexOf(lat);
        if (i >= 0 && (i < m_CyrChars.length())) 
            return m_CyrChars.charAt(i);
        i = m_GreekChars.indexOf(lat);
        if (i >= 0 && (i < m_CyrGreekChars.length())) 
            return m_CyrGreekChars.charAt(i);
        return (char)0;
    }

    public static char getLatForCyr(char cyr) {
        int i = m_CyrChars.indexOf(cyr);
        if ((i < 0) || i >= m_LatChars.length()) 
            return (char)0;
        else 
            return m_LatChars.charAt(i);
    }

    public static String transliteralCorrection(String value, String prevValue, boolean always) {
        int pureCyr = 0;
        int pureLat = 0;
        int quesCyr = 0;
        int quesLat = 0;
        int udarCyr = 0;
        boolean y = false;
        boolean udaren = false;
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            com.pullenti.morph.internal.UnicodeInfo ui = com.pullenti.morph.internal.UnicodeInfo.ALLCHARS.get((int)ch);
            if (!ui.isLetter()) {
                if (ui.isUdaren()) {
                    udaren = true;
                    continue;
                }
                if (ui.isApos() && value.length() > 2) 
                    return transliteralCorrection(value.replace((String.valueOf(ch)), ""), prevValue, false);
                return value;
            }
            if (ui.isCyrillic()) {
                if (m_CyrChars.indexOf(ch) >= 0) 
                    quesCyr++;
                else 
                    pureCyr++;
            }
            else if (ui.isLatin()) {
                if (m_LatChars.indexOf(ch) >= 0) 
                    quesLat++;
                else 
                    pureLat++;
            }
            else if (m_UdarChars.indexOf(ch) >= 0) 
                udarCyr++;
            else 
                return value;
            if (ch == 'Ь' && ((i + 1) < value.length()) && value.charAt(i + 1) == 'I') 
                y = true;
        }
        boolean toRus = false;
        boolean toLat = false;
        if (pureLat > 0 && pureCyr > 0) 
            return value;
        if (((pureLat > 0 || always)) && quesCyr > 0) 
            toLat = true;
        else if (((pureCyr > 0 || always)) && quesLat > 0) 
            toRus = true;
        else if (pureCyr == 0 && pureLat == 0) {
            if (quesCyr > 0 && quesLat > 0) {
                if (!com.pullenti.unisharp.Utils.isNullOrEmpty(prevValue)) {
                    if (isCyrillicChar(prevValue.charAt(0))) 
                        toRus = true;
                    else if (isLatinChar(prevValue.charAt(0))) 
                        toLat = true;
                }
                if (!toLat && !toRus) {
                    if (quesCyr > quesLat) 
                        toRus = true;
                    else if (quesCyr < quesLat) 
                        toLat = true;
                }
            }
        }
        if (!toRus && !toLat) {
            if (!y && !udaren && udarCyr == 0) 
                return value;
        }
        StringBuilder tmp = new StringBuilder(value);
        for (int i = 0; i < tmp.length(); i++) {
            if (tmp.charAt(i) == 'Ь' && ((i + 1) < tmp.length()) && tmp.charAt(i + 1) == 'I') {
                tmp.setCharAt(i, 'Ы');
                tmp.delete(i + 1, i + 1 + 1);
                continue;
            }
            int cod = (int)tmp.charAt(i);
            if (cod >= 0x300 && (cod < 0x370)) {
                tmp.delete(i, i + 1);
                continue;
            }
            if (toRus) {
                int ii = m_LatChars.indexOf(tmp.charAt(i));
                if (ii >= 0) 
                    tmp.setCharAt(i, m_CyrChars.charAt(ii));
                else if ((((ii = m_UdarChars.indexOf(tmp.charAt(i))))) >= 0) 
                    tmp.setCharAt(i, m_UdarCyrChars.charAt(ii));
            }
            else if (toLat) {
                int ii = m_CyrChars.indexOf(tmp.charAt(i));
                if (ii >= 0) 
                    tmp.setCharAt(i, m_LatChars.charAt(ii));
            }
            else {
                int ii = m_UdarChars.indexOf(tmp.charAt(i));
                if (ii >= 0) 
                    tmp.setCharAt(i, m_UdarCyrChars.charAt(ii));
            }
        }
        return tmp.toString();
    }

    public static String m_LatChars = "ABEKMHOPCTYXIaekmopctyxi";

    public static String m_CyrChars = "АВЕКМНОРСТУХІаекморстухі";

    public static String m_GreekChars = "ΑΒΓΕΗΙΚΛΜΟΠΡΤΥΦΧ";

    public static String m_CyrGreekChars = "АВГЕНІКЛМОПРТУФХ";

    private static String m_UdarChars = "ÀÁÈÉËÒÓàáèéëýÝòóЀѐЍѝỲỳ";

    private static String m_UdarCyrChars = "ААЕЕЕООааеееуУооЕеИиУу";

    public static boolean isQuote(char ch) {
        com.pullenti.morph.internal.UnicodeInfo ui = com.pullenti.morph.internal.UnicodeInfo.ALLCHARS.get((int)ch);
        return ui.isQuot();
    }

    public static boolean isApos(char ch) {
        com.pullenti.morph.internal.UnicodeInfo ui = com.pullenti.morph.internal.UnicodeInfo.ALLCHARS.get((int)ch);
        return ui.isApos();
    }

    private static String[] m_Preps;

    private static MorphCase[] m_Cases;

    private static java.util.HashMap<String, MorphCase> m_PrepCases;

    public static MorphCase getCaseAfterPreposition(String prep) {
        MorphCase mc;
        com.pullenti.unisharp.Outargwrapper<MorphCase> wrapmc25 = new com.pullenti.unisharp.Outargwrapper<MorphCase>();
        boolean inoutres26 = com.pullenti.unisharp.Utils.tryGetValue(m_PrepCases, prep, wrapmc25);
        mc = wrapmc25.value;
        if (inoutres26) 
            return mc;
        else 
            return MorphCase.UNDEFINED;
    }

    private static String[] m_PrepNormsSrc;

    private static java.util.HashMap<String, String> m_PrepNorms;

    public static String normalizePreposition(String prep) {
        String res;
        com.pullenti.unisharp.Outargwrapper<String> wrapres27 = new com.pullenti.unisharp.Outargwrapper<String>();
        boolean inoutres28 = com.pullenti.unisharp.Utils.tryGetValue(m_PrepNorms, prep, wrapres27);
        res = wrapres27.value;
        if (inoutres28) 
            return res;
        else 
            return prep;
    }

    public static boolean endsWith(String str, String substr) {
        if (str == null || substr == null) 
            return false;
        int i = str.length() - 1;
        int j = substr.length() - 1;
        if (j > i || (j < 0)) 
            return false;
        for (; j >= 0; j--,i--) {
            if (str.charAt(i) != substr.charAt(j)) 
                return false;
        }
        return true;
    }

    public static boolean endsWithEx(String str, String substr, String substr2, String substr3, String substr4) {
        if (str == null) 
            return false;
        for (int k = 0; k < 4; k++) {
            String s = substr;
            if (k == 1) 
                s = substr2;
            else if (k == 2) 
                s = substr3;
            else if (k == 3) 
                s = substr4;
            if (s == null) 
                continue;
            int i = str.length() - 1;
            int j = s.length() - 1;
            if (j > i || (j < 0)) 
                continue;
            for (; j >= 0; j--,i--) {
                if (str.charAt(i) != s.charAt(j)) 
                    break;
            }
            if (j < 0) 
                return true;
        }
        return false;
    }

    public static String toStringMorphTense(MorphTense tense) {
        StringBuilder res = new StringBuilder();
        if (((short)((tense.value()) & (MorphTense.PAST.value()))) != (MorphTense.UNDEFINED.value())) 
            res.append("прошедшее|");
        if (((short)((tense.value()) & (MorphTense.PRESENT.value()))) != (MorphTense.UNDEFINED.value())) 
            res.append("настоящее|");
        if (((short)((tense.value()) & (MorphTense.FUTURE.value()))) != (MorphTense.UNDEFINED.value())) 
            res.append("будущее|");
        if (res.length() > 0) 
            res.setLength(res.length() - 1);
        return res.toString();
    }

    public static String toStringMorphPerson(MorphPerson person) {
        StringBuilder res = new StringBuilder();
        if (((short)((person.value()) & (MorphPerson.FIRST.value()))) != (MorphPerson.UNDEFINED.value())) 
            res.append("1лицо|");
        if (((short)((person.value()) & (MorphPerson.SECOND.value()))) != (MorphPerson.UNDEFINED.value())) 
            res.append("2лицо|");
        if (((short)((person.value()) & (MorphPerson.THIRD.value()))) != (MorphPerson.UNDEFINED.value())) 
            res.append("3лицо|");
        if (res.length() > 0) 
            res.setLength(res.length() - 1);
        return res.toString();
    }

    public static String toStringMorphGender(MorphGender gender) {
        StringBuilder res = new StringBuilder();
        if (((short)((gender.value()) & (MorphGender.MASCULINE.value()))) != (MorphGender.UNDEFINED.value())) 
            res.append("муж.|");
        if (((short)((gender.value()) & (MorphGender.FEMINIE.value()))) != (MorphGender.UNDEFINED.value())) 
            res.append("жен.|");
        if (((short)((gender.value()) & (MorphGender.NEUTER.value()))) != (MorphGender.UNDEFINED.value())) 
            res.append("средн.|");
        if (res.length() > 0) 
            res.setLength(res.length() - 1);
        return res.toString();
    }

    public static String toStringMorphNumber(MorphNumber number) {
        StringBuilder res = new StringBuilder();
        if (((short)((number.value()) & (MorphNumber.SINGULAR.value()))) != (MorphNumber.UNDEFINED.value())) 
            res.append("единств.|");
        if (((short)((number.value()) & (MorphNumber.PLURAL.value()))) != (MorphNumber.UNDEFINED.value())) 
            res.append("множеств.|");
        if (res.length() > 0) 
            res.setLength(res.length() - 1);
        return res.toString();
    }

    public static String toStringMorphVoice(MorphVoice voice) {
        StringBuilder res = new StringBuilder();
        if (((short)((voice.value()) & (MorphVoice.ACTIVE.value()))) != (MorphVoice.UNDEFINED.value())) 
            res.append("действит.|");
        if (((short)((voice.value()) & (MorphVoice.PASSIVE.value()))) != (MorphVoice.UNDEFINED.value())) 
            res.append("страдат.|");
        if (((short)((voice.value()) & (MorphVoice.MIDDLE.value()))) != (MorphVoice.UNDEFINED.value())) 
            res.append("средн.|");
        if (res.length() > 0) 
            res.setLength(res.length() - 1);
        return res.toString();
    }

    public static String toStringMorphMood(MorphMood mood) {
        StringBuilder res = new StringBuilder();
        if (((short)((mood.value()) & (MorphMood.INDICATIVE.value()))) != (MorphMood.UNDEFINED.value())) 
            res.append("изъявит.|");
        if (((short)((mood.value()) & (MorphMood.IMPERATIVE.value()))) != (MorphMood.UNDEFINED.value())) 
            res.append("повелит.|");
        if (((short)((mood.value()) & (MorphMood.SUBJUNCTIVE.value()))) != (MorphMood.UNDEFINED.value())) 
            res.append("условн.|");
        if (res.length() > 0) 
            res.setLength(res.length() - 1);
        return res.toString();
    }

    public static String toStringMorphAspect(MorphAspect aspect) {
        StringBuilder res = new StringBuilder();
        if (((short)((aspect.value()) & (MorphAspect.IMPERFECTIVE.value()))) != (MorphAspect.UNDEFINED.value())) 
            res.append("несоверш.|");
        if (((short)((aspect.value()) & (MorphAspect.PERFECTIVE.value()))) != (MorphAspect.UNDEFINED.value())) 
            res.append("соверш.|");
        if (res.length() > 0) 
            res.setLength(res.length() - 1);
        return res.toString();
    }

    public static String toStringMorphFinite(MorphFinite finit) {
        StringBuilder res = new StringBuilder();
        if (((short)((finit.value()) & (MorphFinite.FINITE.value()))) != (MorphFinite.UNDEFINED.value())) 
            res.append("finite|");
        if (((short)((finit.value()) & (MorphFinite.GERUND.value()))) != (MorphFinite.UNDEFINED.value())) 
            res.append("gerund|");
        if (((short)((finit.value()) & (MorphFinite.INFINITIVE.value()))) != (MorphFinite.UNDEFINED.value())) 
            res.append("инфинитив|");
        if (((short)((finit.value()) & (MorphFinite.PARTICIPLE.value()))) != (MorphFinite.UNDEFINED.value())) 
            res.append("participle|");
        if (res.length() > 0) 
            res.setLength(res.length() - 1);
        return res.toString();
    }

    public static String toStringMorphForm(MorphForm form) {
        StringBuilder res = new StringBuilder();
        if (((short)((form.value()) & (MorphForm.SHORT.value()))) != (MorphForm.UNDEFINED.value())) 
            res.append("кратк.|");
        if (((short)((form.value()) & (MorphForm.SYNONYM.value()))) != (MorphForm.UNDEFINED.value())) 
            res.append("синонимич.|");
        if (res.length() > 0) 
            res.setLength(res.length() - 1);
        return res.toString();
    }

    private static String m_Rus0 = "–ЁѐЀЍѝЎўӢӣ";

    private static String m_Rus1 = "-ЕЕЕИИУУЙЙ";

    public static String correctWord(String w) {
        if (w == null) 
            return null;
        String res = w.toUpperCase();
        for (char ch : res.toCharArray()) {
            if (m_Rus0.indexOf(ch) >= 0) {
                StringBuilder tmp = new StringBuilder();
                tmp.append(res);
                for (int i = 0; i < tmp.length(); i++) {
                    int j = m_Rus0.indexOf(tmp.charAt(i));
                    if (j >= 0) 
                        tmp.setCharAt(i, m_Rus1.charAt(j));
                }
                res = tmp.toString();
                break;
            }
        }
        if (res.indexOf((char)0x00AD) >= 0) 
            res = res.replace((char)0x00AD, '-');
        if (res.startsWith("АГЕНС")) 
            res = "АГЕНТС" + res.substring(5);
        return res;
    }

    public LanguageHelper() {
    }
    
    static {
        m_Preps = new String[] {("БЕЗ;ДО;ИЗ;ИЗЗА;ОТ;У;ДЛЯ;РАДИ;ВОЗЛЕ;ПОЗАДИ;ВПЕРЕДИ;БЛИЗ;ВБЛИЗИ;ВГЛУБЬ;ВВИДУ;ВДОЛЬ;ВЗАМЕН;ВКРУГ;ВМЕСТО;" + "ВНЕ;ВНИЗУ;ВНУТРИ;ВНУТРЬ;ВОКРУГ;ВРОДЕ;ВСЛЕД;ВСЛЕДСТВИЕ;ЗАМЕСТО;ИЗНУТРИ;КАСАТЕЛЬНО;КРОМЕ;" + "МИМО;НАВРОДЕ;НАЗАД;НАКАНУНЕ;НАПОДОБИЕ;НАПРОТИВ;НАСЧЕТ;ОКОЛО;ОТНОСИТЕЛЬНО;") + "ПОВЕРХ;ПОДЛЕ;ПОМИМО;ПОПЕРЕК;ПОРЯДКА;ПОСЕРЕДИНЕ;ПОСРЕДИ;ПОСЛЕ;ПРЕВЫШЕ;ПРЕЖДЕ;ПРОТИВ;СВЕРХ;" + "СВЫШЕ;СНАРУЖИ;СРЕДИ;СУПРОТИВ;ПУТЕМ;ПОСРЕДСТВОМ", "К;БЛАГОДАРЯ;ВОПРЕКИ;НАВСТРЕЧУ;СОГЛАСНО;СООБРАЗНО;ПАРАЛЛЕЛЬНО;ПОДОБНО;СООТВЕТСТВЕННО;СОРАЗМЕРНО", "ПРО;ЧЕРЕЗ;СКВОЗЬ;СПУСТЯ", "НАД;ПЕРЕД;ПРЕД", "ПРИ", "В;НА;О;ВКЛЮЧАЯ", "МЕЖДУ", "ЗА;ПОД", "ПО", "С"};
        m_Cases = new MorphCase[] {MorphCase.GENITIVE, MorphCase.DATIVE, MorphCase.ACCUSATIVE, MorphCase.INSTRUMENTAL, MorphCase.PREPOSITIONAL, MorphCase.ooBitor(MorphCase.ACCUSATIVE, MorphCase.PREPOSITIONAL), MorphCase.ooBitor(MorphCase.GENITIVE, MorphCase.INSTRUMENTAL), MorphCase.ooBitor(MorphCase.ACCUSATIVE, MorphCase.INSTRUMENTAL), MorphCase.ooBitor(MorphCase.DATIVE, MorphCase.ooBitor(MorphCase.ACCUSATIVE, MorphCase.PREPOSITIONAL)), MorphCase.ooBitor(MorphCase.GENITIVE, MorphCase.ooBitor(MorphCase.ACCUSATIVE, MorphCase.INSTRUMENTAL))};
        m_PrepNormsSrc = new String[] {"БЕЗ;БЕЗО", "ВБЛИЗИ;БЛИЗ", "В;ВО", "ВОКРУГ;ВКРУГ", "ВНУТРИ;ВНУТРЬ;ВОВНУТРЬ", "ВПЕРЕДИ;ВПЕРЕД", "ВСЛЕД;ВОСЛЕД", "ВМЕСТО;ЗАМЕСТО", "ИЗ;ИЗО", "К;КО", "МЕЖДУ;МЕЖ;ПРОМЕЖДУ;ПРОМЕЖ", "НАД;НАДО", "О;ОБ;ОБО", "ОТ;ОТО", "ПЕРЕД;ПРЕД;ПРЕДО;ПЕРЕДО", "ПОД;ПОДО", "ПОСЕРЕДИНЕ;ПОСРЕДИ;ПОСЕРЕДЬ", "С;СО", "СРЕДИ;СРЕДЬ;СЕРЕДЬ", "ЧЕРЕЗ;ЧРЕЗ"};
        m_PrepCases = new java.util.HashMap<String, MorphCase>();
        for (int i = 0; i < m_Preps.length; i++) {
            for (String v : com.pullenti.unisharp.Utils.split(m_Preps[i], String.valueOf(';'), false)) {
                m_PrepCases.put(v, m_Cases[i]);
            }
        }
        m_PrepNorms = new java.util.HashMap<String, String>();
        for (String s : m_PrepNormsSrc) {
            String[] vars = com.pullenti.unisharp.Utils.split(s, String.valueOf(';'), false);
            for (int i = 1; i < vars.length; i++) {
                m_PrepNorms.put(vars[i], vars[0]);
            }
        }
    }
}
