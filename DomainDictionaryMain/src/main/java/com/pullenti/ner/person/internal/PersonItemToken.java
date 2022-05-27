/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.person.internal;

public class PersonItemToken extends com.pullenti.ner.MetaToken {

    private PersonItemToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
        if(_globalInstance == null) return;
        typ = ItemType.VALUE;
    }

    public static void initialize() {
        MorphPersonItem.initialize();
    }

    public ItemType typ = ItemType.VALUE;

    public String value;

    public boolean isInDictionary;

    public boolean isHiphenBefore;

    public boolean isHiphenAfter;

    public boolean isAsianItem(boolean last) {
        if (value == null || typ != ItemType.VALUE) 
            return false;
        if (chars.isAllLower()) 
            return false;
        if (chars.isAllUpper() && getLengthChar() > 1) 
            return false;
        int sogl = 0;
        int gl = 0;
        boolean prevGlas = false;
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (!com.pullenti.morph.LanguageHelper.isCyrillicChar(ch)) 
                return false;
            else if (com.pullenti.morph.LanguageHelper.isCyrillicVowel(ch)) {
                if (!prevGlas) {
                    if (gl > 0) {
                        if (!last) 
                            return false;
                        if (i == (value.length() - 1) && ((ch == 'А' || ch == 'У' || ch == 'Е'))) 
                            break;
                        else if (i == (value.length() - 2) && ch == 'О' && value.charAt(i + 1) == 'М') 
                            break;
                    }
                    gl++;
                }
                prevGlas = true;
            }
            else {
                sogl++;
                prevGlas = false;
            }
        }
        if (gl != 1) {
            if (last && gl == 2) {
            }
            else 
                return false;
        }
        if (sogl > 4) 
            return false;
        if (value.length() == 1) {
            if (!chars.isAllUpper()) 
                return false;
        }
        else if (!chars.isCapitalUpper()) 
            return false;
        if (value.length() > 5 && getBeginToken() == getEndToken() && !last) {
            com.pullenti.morph.MorphClass mc = getBeginToken().getMorphClassInDictionary();
            if (!mc.isUndefined()) 
                return false;
        }
        return true;
    }

    public MorphPersonItem firstname;

    public MorphPersonItem lastname;

    public MorphPersonItem middlename;

    public com.pullenti.ner.person.PersonReferent referent;

    public static class MorphPersonItemVariant extends com.pullenti.morph.MorphBaseInfo {
    
        public MorphPersonItemVariant(String v, com.pullenti.morph.MorphBaseInfo bi, boolean _lastname) {
            super();
            value = v;
            if (bi != null) 
                this.copyFrom(bi);
        }
    
        public String value;
    
        public String shortValue;
    
        @Override
        public String toString() {
            return (((value != null ? value : "?")) + ": " + super.toString());
        }
    
        public static MorphPersonItemVariant _new2783(String _arg1, com.pullenti.morph.MorphBaseInfo _arg2, boolean _arg3, String _arg4) {
            MorphPersonItemVariant res = new MorphPersonItemVariant(_arg1, _arg2, _arg3);
            res.shortValue = _arg4;
            return res;
        }
        public MorphPersonItemVariant() {
            super();
        }
    }


    public static class MorphPersonItem {
    
        public com.pullenti.ner.MorphCollection getMorph() {
            if (m_Morph != null && m_Morph.getItemsCount() != vars.size()) 
                m_Morph = null;
            if (m_Morph == null) {
                m_Morph = new com.pullenti.ner.MorphCollection(null);
                for (com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant v : vars) {
                    m_Morph.addItem(v);
                }
            }
            return m_Morph;
        }
    
    
        private com.pullenti.ner.MorphCollection m_Morph;
    
        public java.util.ArrayList<com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant> vars = new java.util.ArrayList<com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant>();
    
        public String term;
    
        public boolean isInDictionary;
    
        public boolean isInOntology;
    
        public boolean isLastnameHasStdTail;
    
        public boolean isLastnameHasHiphen;
    
        public boolean isHasStdPostfix;
    
        public boolean isChinaSurname() {
            String _term = term;
            if (_term == null && vars.size() > 0) 
                _term = vars.get(0).value;
            if (_term == null) 
                return false;
            if (m_LastnameAsian.indexOf(_term) >= 0) 
                return true;
            String tr = com.pullenti.ner.person.PersonReferent._DelSurnameEnd(_term);
            if (m_LastnameAsian.indexOf(tr) >= 0) 
                return true;
            if (m_LastnameAsian.indexOf(_term + "Ь") >= 0) 
                return true;
            if (_term.charAt(_term.length() - 1) == 'Ь') {
                if (m_LastnameAsian.indexOf(_term.substring(0, 0 + _term.length() - 1)) >= 0) 
                    return true;
            }
            return false;
        }
    
    
        @Override
        public String toString() {
            StringBuilder res = new StringBuilder();
            if (term != null) 
                res.append(term);
            for (com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant v : vars) {
                res.append("; ").append(v.toString());
            }
            if (isInDictionary) 
                res.append(" - InDictionary");
            if (isInOntology) 
                res.append(" - InOntology");
            if (isLastnameHasStdTail) 
                res.append(" - IsLastnameHasStdTail");
            if (isHasStdPostfix) 
                res.append(" - IsHasStdPostfix");
            if (isChinaSurname()) 
                res.append(" - IsChinaSurname");
            return res.toString();
        }
    
        public void mergeHiphen(MorphPersonItem second) {
            java.util.ArrayList<com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant> addvars = new java.util.ArrayList<com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant>();
            for (com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant v : vars) {
                int ok = 0;
                for (com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant vv : second.vars) {
                    if (((short)((vv.getGender().value()) & (v.getGender().value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                        v.value = (v.value + "-" + vv.value);
                        ok++;
                        break;
                    }
                }
                if (ok > 0) 
                    continue;
                if (v.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                    for (com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant vv : second.vars) {
                        if (vv.getGender() == com.pullenti.morph.MorphGender.UNDEFINED) {
                            v.value = (v.value + "-" + vv.value);
                            ok++;
                            break;
                        }
                    }
                    if (ok > 0) 
                        continue;
                }
                else {
                    String val0 = v.value;
                    for (com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant vv : second.vars) {
                        if (vv.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                            if (ok == 0) {
                                v.value = (val0 + "-" + vv.value);
                                v.copyFrom(vv);
                            }
                            else 
                                addvars.add(new com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant((val0 + "-" + vv.value), vv, false));
                            ok++;
                        }
                    }
                    if (ok > 0) 
                        continue;
                }
                if (second.vars.size() == 0) 
                    continue;
                v.value = (v.value + "-" + second.vars.get(0).value);
            }
            com.pullenti.unisharp.Utils.addToArrayList(vars, addvars);
        }
    
        public void addPrefix(String val) {
            if (term != null) 
                term = val + term;
            for (com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant v : vars) {
                if (v.value != null) 
                    v.value = val + v.value;
            }
        }
    
        public void addPostfix(String val, com.pullenti.morph.MorphGender gen) {
            if (term != null) 
                term = (term + "-" + val);
            for (com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant v : vars) {
                if (v.value != null) {
                    v.value = (v.value + "-" + val);
                    if (gen != com.pullenti.morph.MorphGender.UNDEFINED) 
                        v.setGender(gen);
                }
            }
            isHasStdPostfix = true;
            isInDictionary = false;
        }
    
        public void mergeWithByHiphen(MorphPersonItem pi) {
            term = (((term != null ? term : "")) + "-" + ((pi.term != null ? pi.term : "")));
            if (pi.isInDictionary) 
                isInDictionary = true;
            if (pi.isHasStdPostfix) 
                isHasStdPostfix = true;
            isLastnameHasHiphen = true;
            if (pi.vars.size() == 0) {
                if (pi.term != null) 
                    this.addPostfix(pi.term, com.pullenti.morph.MorphGender.UNDEFINED);
                return;
            }
            if (vars.size() == 0) {
                if (term != null) 
                    pi.addPrefix(term + "-");
                vars = pi.vars;
                return;
            }
            java.util.ArrayList<com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant> res = new java.util.ArrayList<com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant>();
            for (com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant v : vars) {
                for (com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant vv : pi.vars) {
                    com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant vvv = new com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant((v.value + "-" + vv.value), v, false);
                    res.add(vvv);
                }
            }
            vars = res;
        }
    
        public void correctLastnameVariants() {
            isLastnameHasStdTail = false;
            boolean strongStd = false;
            for (com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant v : vars) {
                if (v.value != null) {
                    if (endsWithStdSurname(v.value) || com.pullenti.morph.LanguageHelper.endsWith(v.value, "АЯ") || com.pullenti.morph.LanguageHelper.endsWith(v.value, "ОЙ")) {
                        isLastnameHasStdTail = true;
                        strongStd = true;
                        break;
                    }
                    else if (com.pullenti.morph.LanguageHelper.endsWith(v.value, "КИЙ") || com.pullenti.morph.LanguageHelper.endsWith(v.value, "ЫЙ")) 
                        isLastnameHasStdTail = true;
                }
            }
            if (isLastnameHasStdTail) {
                for (int i = vars.size() - 1; i >= 0; i--) {
                    if ((((vars.get(i).value != null && !endsWithStdSurname(vars.get(i).value) && !com.pullenti.morph.LanguageHelper.endsWith(vars.get(i).value, "АЯ")) && !com.pullenti.morph.LanguageHelper.endsWith(vars.get(i).value, "ОЙ") && !com.pullenti.morph.LanguageHelper.endsWith(vars.get(i).value, "КИЙ")) && !com.pullenti.morph.LanguageHelper.endsWith(vars.get(i).value, "ЫЙ") && !com.pullenti.morph.LanguageHelper.endsWith(vars.get(i).value, "ИХ")) && !com.pullenti.morph.LanguageHelper.endsWith(vars.get(i).value, "ЫХ")) {
                        if (!vars.get(i)._getClass().isProperSurname() || strongStd) {
                            vars.remove(i);
                            continue;
                        }
                    }
                    if (vars.get(i).getGender() == com.pullenti.morph.MorphGender.UNDEFINED) {
                        boolean del = false;
                        for (int j = 0; j < vars.size(); j++) {
                            if (j != i && com.pullenti.unisharp.Utils.stringsEq(vars.get(j).value, vars.get(i).value) && vars.get(j).getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                                del = true;
                                break;
                            }
                        }
                        if (del) {
                            vars.remove(i);
                            continue;
                        }
                        com.pullenti.ner.person.internal.PersonItemToken.SurnameTail t = findTail(vars.get(i).value);
                        if (t != null) {
                            if (t.gender != com.pullenti.morph.MorphGender.UNDEFINED) 
                                vars.get(i).setGender(t.gender);
                        }
                        else if (com.pullenti.morph.LanguageHelper.endsWithEx(vars.get(i).value, "А", "Я", null, null)) 
                            vars.get(i).setGender(com.pullenti.morph.MorphGender.FEMINIE);
                        else 
                            vars.get(i).setGender(com.pullenti.morph.MorphGender.MASCULINE);
                    }
                }
            }
        }
    
        public void removeNotGenitive() {
            boolean hasGen = false;
            for (com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant v : vars) {
                if (v.getCase().isGenitive()) 
                    hasGen = true;
            }
            if (hasGen) {
                for (int i = vars.size() - 1; i >= 0; i--) {
                    if (!vars.get(i).getCase().isGenitive()) 
                        vars.remove(i);
                }
            }
        }
    
        public static void initialize() {
            m_LastnameStdTails = new java.util.ArrayList<com.pullenti.ner.person.internal.PersonItemToken.SurnameTail>();
            m_LastnameStdTails.add(new com.pullenti.ner.person.internal.PersonItemToken.SurnameTail("ОВ", com.pullenti.morph.MorphGender.MASCULINE));
            m_LastnameStdTails.add(new com.pullenti.ner.person.internal.PersonItemToken.SurnameTail("ОВА", com.pullenti.morph.MorphGender.FEMINIE));
            m_LastnameStdTails.add(new com.pullenti.ner.person.internal.PersonItemToken.SurnameTail("ЕВ", com.pullenti.morph.MorphGender.MASCULINE));
            m_LastnameStdTails.add(new com.pullenti.ner.person.internal.PersonItemToken.SurnameTail("ЕВА", com.pullenti.morph.MorphGender.FEMINIE));
            m_LastnameStdTails.add(new com.pullenti.ner.person.internal.PersonItemToken.SurnameTail("ЄВ", com.pullenti.morph.MorphGender.MASCULINE));
            m_LastnameStdTails.add(new com.pullenti.ner.person.internal.PersonItemToken.SurnameTail("ЄВА", com.pullenti.morph.MorphGender.FEMINIE));
            m_LastnameStdTails.add(new com.pullenti.ner.person.internal.PersonItemToken.SurnameTail("ИН", com.pullenti.morph.MorphGender.MASCULINE));
            m_LastnameStdTails.add(new com.pullenti.ner.person.internal.PersonItemToken.SurnameTail("ИНА", com.pullenti.morph.MorphGender.FEMINIE));
            m_LastnameStdTails.add(new com.pullenti.ner.person.internal.PersonItemToken.SurnameTail("ІН", com.pullenti.morph.MorphGender.MASCULINE));
            m_LastnameStdTails.add(new com.pullenti.ner.person.internal.PersonItemToken.SurnameTail("ІНА", com.pullenti.morph.MorphGender.FEMINIE));
            for (String s : new String[] {"ЕР", "РН", "ДЗЕ", "ВИЛИ", "ЯН", "УК", "ЮК", "КО", "МАН", "АНН", "ЙН", "УН", "СКУ", "СКИ", "СЬКІ", "ИЛО", "ІЛО", "АЛО", "ИК", "СОН", "РА", "НДА", "НДО", "ЕС", "АС", "АВА", "ЛС", "ЛЮС", "ЛЬС", "ЙЗ", "ЕРГ", "ИНГ", "OR", "ER", "OV", "IN", "ERG"}) {
                m_LastnameStdTails.add(new com.pullenti.ner.person.internal.PersonItemToken.SurnameTail(s, com.pullenti.morph.MorphGender.UNDEFINED));
            }
            m_LatsnameSexStdTails = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"ОВ", "ОВА", "ЕВ", "ЄВ", "ЕВА", "ЄВA", "ИН", "ИНА", "ІН", "ІНА", "КИЙ", "КАЯ"}));
            m_LastnameAsian = new java.util.ArrayList<String>();
            for (String s : com.pullenti.unisharp.Utils.split(com.pullenti.ner.person.internal.ResourceHelper.getString("chinasurnames.txt"), String.valueOf('\n'), false)) {
                String ss = s.trim().toUpperCase().replace("Ё", "Е");
                if (!com.pullenti.unisharp.Utils.isNullOrEmpty(ss)) 
                    m_LastnameAsian.add(ss);
            }
            java.util.ArrayList<String> m_ChinaSurs = new java.util.ArrayList<String>(java.util.Arrays.asList(com.pullenti.unisharp.Utils.split("Чон Чжао Цянь Сунь Ли Чжоу У Чжэн Ван Фэн Чэнь Чу Вэй Цзян Шэнь Хань Ян Чжу Цинь Ю Сюй Хэ Люй Ши Чжан Кун Цао Янь Хуа Цзинь Тао Ци Се Цзоу Юй Бай Шуй Доу Чжан Юнь Су Пань Гэ Си Фань Пэн Лан Лу Чан Ма Мяо Фан Жэнь Юань Лю Бао Ши Тан Фэй Лянь Цэнь Сюэ Лэй Хэ Ни Тэн Инь Ло Би Хао Ань Чан Лэ Фу Пи Бянь Кан Бу Гу Мэн Пин Хуан Му Сяо Яо Шао Чжань Мао Ди Ми Бэй Мин Ху Хван", String.valueOf(' '), false)));
            for (String s : m_ChinaSurs) {
                String ss = s.trim().toUpperCase().replace("Ё", "Е");
                if (!com.pullenti.unisharp.Utils.isNullOrEmpty(ss)) {
                    if (!m_LastnameAsian.contains(ss)) 
                        m_LastnameAsian.add(ss);
                }
            }
            java.util.Collections.sort(m_LastnameAsian);
        }
    
        private static java.util.ArrayList<com.pullenti.ner.person.internal.PersonItemToken.SurnameTail> m_LastnameStdTails;
    
        private static java.util.ArrayList<String> m_LatsnameSexStdTails;
    
        private static java.util.ArrayList<String> m_LastnameAsian;
    
        private static com.pullenti.ner.person.internal.PersonItemToken.SurnameTail findTail(String val) {
            if (val == null) 
                return null;
            for (int i = 0; i < m_LastnameStdTails.size(); i++) {
                if (com.pullenti.morph.LanguageHelper.endsWith(val, m_LastnameStdTails.get(i).tail)) 
                    return m_LastnameStdTails.get(i);
            }
            return null;
        }
    
        public static boolean endsWithStdSurname(String val) {
            return findTail(val) != null;
        }
    
        public static MorphPersonItem _new2730(boolean _arg1) {
            MorphPersonItem res = new MorphPersonItem();
            res.isHasStdPostfix = _arg1;
            return res;
        }
    
        public static MorphPersonItem _new2738(String _arg1) {
            MorphPersonItem res = new MorphPersonItem();
            res.term = _arg1;
            return res;
        }
    
        public static MorphPersonItem _new2741(String _arg1, boolean _arg2) {
            MorphPersonItem res = new MorphPersonItem();
            res.term = _arg1;
            res.isInOntology = _arg2;
            return res;
        }
    
        public static MorphPersonItem _new2773(boolean _arg1) {
            MorphPersonItem res = new MorphPersonItem();
            res.isInOntology = _arg1;
            return res;
        }
        public MorphPersonItem() {
        }
    }


    public static class SurnameTail {
    
        public SurnameTail(String t, com.pullenti.morph.MorphGender g) {
            tail = t;
            gender = g;
        }
    
        public String tail;
    
        public com.pullenti.morph.MorphGender gender = com.pullenti.morph.MorphGender.UNDEFINED;
        public SurnameTail() {
        }
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(typ.toString()).append(" ").append(((value != null ? value : "")));
        if (firstname != null) 
            res.append(" (First: ").append(firstname.toString()).append(")");
        if (middlename != null) 
            res.append(" (Middle: ").append(middlename.toString()).append(")");
        if (lastname != null) 
            res.append(" (Last: ").append(lastname.toString()).append(")");
        if (referent != null) 
            res.append(" Ref: ").append(referent);
        return res.toString();
    }

    private void addPostfixInfo(String postfix, com.pullenti.morph.MorphGender gen) {
        if (value != null) 
            value = (value + "-" + postfix);
        if (lastname != null) 
            lastname.addPostfix(postfix, gen);
        if (firstname != null) 
            firstname.addPostfix(postfix, gen);
        else if (lastname != null) 
            firstname = lastname;
        else {
            firstname = MorphPersonItem._new2730(true);
            firstname.vars.add(new MorphPersonItemVariant(value, com.pullenti.morph.MorphBaseInfo._new2731(gen), false));
            if (lastname == null) 
                lastname = firstname;
        }
        if (middlename != null) 
            middlename.addPostfix(postfix, gen);
        else if (firstname != null && !chars.isLatinLetter()) 
            middlename = firstname;
        isInDictionary = false;
    }

    public void mergeWithByHiphen(PersonItemToken pi) {
        setEndToken(pi.getEndToken());
        value = (value + "-" + pi.value);
        if (lastname != null) {
            if (pi.lastname == null || pi.lastname.vars.size() == 0) 
                lastname.addPostfix(pi.value, com.pullenti.morph.MorphGender.UNDEFINED);
            else 
                lastname.mergeWithByHiphen(pi.lastname);
        }
        else if (pi.lastname != null) {
            pi.lastname.addPrefix(value + "-");
            lastname = pi.lastname;
        }
        if (firstname != null) {
            if (pi.firstname == null || pi.firstname.vars.size() == 0) 
                firstname.addPostfix(pi.value, com.pullenti.morph.MorphGender.UNDEFINED);
            else 
                firstname.mergeWithByHiphen(pi.firstname);
        }
        else if (pi.firstname != null) {
            pi.firstname.addPrefix(value + "-");
            firstname = pi.firstname;
        }
        if (middlename != null) {
            if (pi.middlename == null || pi.middlename.vars.size() == 0) 
                middlename.addPostfix(pi.value, com.pullenti.morph.MorphGender.UNDEFINED);
            else 
                middlename.mergeWithByHiphen(pi.middlename);
        }
        else if (pi.middlename != null) {
            pi.middlename.addPrefix(value + "-");
            middlename = pi.middlename;
        }
    }

    public void removeNotGenitive() {
        if (lastname != null) 
            lastname.removeNotGenitive();
        if (firstname != null) 
            firstname.removeNotGenitive();
        if (middlename != null) 
            middlename.removeNotGenitive();
    }

    public static PersonItemToken tryAttachLatin(com.pullenti.ner.Token t) {
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) {
            com.pullenti.ner.MetaToken mt = (com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class);
            if (mt != null && mt.getBeginToken() == mt.getEndToken()) {
                PersonItemToken res00 = tryAttachLatin(mt.getBeginToken());
                if (res00 != null) {
                    res00.setBeginToken(res00.setEndToken(t));
                    return res00;
                }
            }
            return null;
        }
        if (!tt.chars.isLetter()) 
            return null;
        if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "THE")) 
            return null;
        if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "JR") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "JNR") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "JUNIOR")) {
            com.pullenti.ner.Token t1 = tt;
            if (tt.getNext() != null && tt.getNext().isChar('.')) 
                t1 = tt.getNext();
            return _new2732(tt, t1, ItemType.SUFFIX, "JUNIOR");
        }
        if ((com.pullenti.unisharp.Utils.stringsEq(tt.term, "SR") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "SNR") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "SENIOR")) || com.pullenti.unisharp.Utils.stringsEq(tt.term, "FITZ") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "FILS")) {
            com.pullenti.ner.Token t1 = tt;
            if (tt.getNext() != null && tt.getNext().isChar('.')) 
                t1 = tt.getNext();
            return _new2732(tt, t1, ItemType.SUFFIX, "SENIOR");
        }
        boolean initials = (com.pullenti.unisharp.Utils.stringsEq(tt.term, "YU") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "YA") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "CH")) || com.pullenti.unisharp.Utils.stringsEq(tt.term, "SH");
        if (!initials && tt.term.length() == 2 && tt.chars.isCapitalUpper()) {
            if (!com.pullenti.morph.LanguageHelper.isLatinVowel(tt.term.charAt(0)) && !com.pullenti.morph.LanguageHelper.isLatinVowel(tt.term.charAt(1))) 
                initials = true;
        }
        if (initials) {
            PersonItemToken rii = _new2734(tt, tt, ItemType.INITIAL, tt.term, tt.chars);
            if (tt.getNext() != null && tt.getNext().isChar('.')) 
                rii.setEndToken(tt.getNext());
            return rii;
        }
        if (tt.chars.isAllLower()) {
            if (!m_SurPrefixesLat.contains(tt.term)) 
                return null;
        }
        if (tt.chars.isCyrillicLetter()) 
            return null;
        if (tt.getLengthChar() == 1) {
            if (tt.getNext() == null) 
                return null;
            if (tt.getNext().isChar('.')) 
                return _new2734(tt, tt.getNext(), ItemType.INITIAL, tt.term, tt.chars);
            if (!tt.getNext().isWhitespaceAfter() && !tt.isWhitespaceAfter() && ((com.pullenti.unisharp.Utils.stringsEq(tt.term, "D") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "O") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "M")))) {
                if (com.pullenti.ner.core.BracketHelper.isBracket(tt.getNext(), false) && (tt.getNext().getNext() instanceof com.pullenti.ner.TextToken)) {
                    if (tt.getNext().getNext().chars.isLatinLetter()) {
                        PersonItemToken pit0 = tryAttachLatin(tt.getNext().getNext());
                        if (pit0 != null && pit0.typ == ItemType.VALUE) {
                            pit0.setBeginToken(tt);
                            String val = tt.term;
                            if (pit0.value != null) {
                                if (com.pullenti.unisharp.Utils.stringsEq(val, "M") && pit0.value.startsWith("C")) {
                                    pit0.value = "MA" + pit0.value;
                                    val = "MA";
                                }
                                else 
                                    pit0.value = val + pit0.value;
                            }
                            if (pit0.lastname != null) {
                                pit0.lastname.addPrefix(val);
                                pit0.lastname.isInDictionary = true;
                            }
                            else if (pit0.firstname != null) {
                                pit0.lastname = pit0.firstname;
                                pit0.lastname.addPrefix(val);
                                pit0.lastname.isInDictionary = true;
                            }
                            pit0.firstname = (pit0.middlename = null);
                            if (!pit0.chars.isAllUpper() && !pit0.chars.isCapitalUpper()) 
                                pit0.chars.setCapitalUpper(true);
                            return pit0;
                        }
                    }
                }
            }
            if (!com.pullenti.morph.LanguageHelper.isLatinVowel(tt.term.charAt(0)) || tt.getWhitespacesAfterCount() != 1) {
                PersonItemToken nex = tryAttachLatin(tt.getNext());
                if (nex != null && nex.typ == ItemType.VALUE) 
                    return _new2734(tt, tt, ItemType.INITIAL, tt.term, tt.chars);
                return null;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "I")) 
                return null;
            return _new2734(tt, tt, ItemType.VALUE, tt.term, tt.chars);
        }
        if (!com.pullenti.ner.core.MiscHelper.hasVowel(tt)) 
            return null;
        PersonItemToken res;
        if (m_SurPrefixesLat.contains(tt.term)) {
            com.pullenti.ner.Token te = tt.getNext();
            if (te != null && te.isHiphen()) 
                te = te.getNext();
            res = tryAttachLatin(te);
            if (res != null) {
                res.value = (tt.term + "-" + res.value);
                res.setBeginToken(tt);
                res.lastname = new MorphPersonItem();
                res.lastname.vars.add(new MorphPersonItemVariant(res.value, new com.pullenti.morph.MorphBaseInfo(), true));
                res.lastname.isLastnameHasHiphen = true;
                return res;
            }
        }
        if (com.pullenti.ner.mail.internal.MailLine.isKeyword(tt)) 
            return null;
        res = new PersonItemToken(tt, tt);
        res.value = tt.term;
        com.pullenti.morph.MorphClass cla = tt.getMorphClassInDictionary();
        if (cla.isProperName() || ((cla.isProper() && ((tt.getMorph().getGender() == com.pullenti.morph.MorphGender.MASCULINE || tt.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE))))) {
            res.firstname = MorphPersonItem._new2738(res.value);
            for (com.pullenti.morph.MorphBaseInfo wf : tt.getMorph().getItems()) {
                if (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class)).isInDictionary()) {
                    if (wf._getClass().isProperName()) 
                        res.firstname.vars.add(new MorphPersonItemVariant(res.value, wf, false));
                }
            }
            if (res.firstname.vars.size() == 0) 
                res.firstname.vars.add(new MorphPersonItemVariant(res.value, null, false));
            res.firstname.isInDictionary = true;
        }
        if (cla.isProperSurname()) {
            res.lastname = MorphPersonItem._new2738(res.value);
            for (com.pullenti.morph.MorphBaseInfo wf : tt.getMorph().getItems()) {
                if (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class)).isInDictionary()) {
                    if (wf._getClass().isProperSurname()) 
                        res.lastname.vars.add(new MorphPersonItemVariant(res.value, wf, false));
                }
            }
            if (res.lastname.vars.size() == 0) 
                res.lastname.vars.add(new MorphPersonItemVariant(res.value, null, false));
            res.lastname.isInDictionary = true;
        }
        if ((!cla.isProperName() && !cla.isProper() && !cla.isProperSurname()) && !cla.isUndefined()) 
            res.isInDictionary = true;
        res.setMorph(tt.getMorph());
        java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> ots = null;
        if (t != null && t.kit.ontology != null && ots == null) 
            ots = t.kit.ontology.attachToken(com.pullenti.ner.person.PersonReferent.OBJ_TYPENAME, t);
        if (ots != null) {
            if (ots.get(0).termin.ignoreTermsOrder) 
                return _new2740(ots.get(0).getBeginToken(), ots.get(0).getEndToken(), ItemType.REFERENT, (com.pullenti.ner.person.PersonReferent)com.pullenti.unisharp.Utils.cast(ots.get(0).item.tag, com.pullenti.ner.person.PersonReferent.class), ots.get(0).getMorph());
            res.lastname = MorphPersonItem._new2741(ots.get(0).termin.getCanonicText(), true);
            for (com.pullenti.ner.core.IntOntologyToken ot : ots) {
                if (ot.termin != null) {
                    com.pullenti.morph.MorphBaseInfo mi = ot.getMorph();
                    if (ot.termin.getGender() == com.pullenti.morph.MorphGender.MASCULINE || ot.termin.getGender() == com.pullenti.morph.MorphGender.FEMINIE) 
                        mi = com.pullenti.morph.MorphBaseInfo._new2731(ot.termin.getGender());
                    res.lastname.vars.add(new MorphPersonItemVariant(ot.termin.getCanonicText(), mi, true));
                }
            }
        }
        if (res.value.startsWith("MC")) 
            res.value = "MAC" + res.value.substring(2);
        if (res.value.startsWith("MAC")) {
            res.firstname = (res.middlename = null);
            res.lastname = MorphPersonItem._new2730(true);
            res.lastname.vars.add(new MorphPersonItemVariant(res.value, new com.pullenti.morph.MorphBaseInfo(), true));
        }
        return res;
    }

    public static PersonItemToken tryAttach(com.pullenti.ner.Token t, ParseAttr attrs, java.util.ArrayList<PersonItemToken> prevList) {
        if (t == null) 
            return null;
        if (t instanceof com.pullenti.ner.TextToken) {
            com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
            if (mc.isPreposition() || mc.isConjunction() || mc.isMisc()) {
                if (t.getNext() != null && (t.getNext() instanceof com.pullenti.ner.ReferentToken)) {
                    if ((((attrs.value()) & (ParseAttr.MUSTBEITEMALWAYS.value()))) != (ParseAttr.NO.value()) && !t.chars.isAllLower()) {
                    }
                    else if (t.getLengthChar() == 1 && t.chars.isAllUpper()) {
                    }
                    else 
                        return null;
                }
            }
        }
        if (t instanceof com.pullenti.ner.NumberToken) {
            com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
            if (nt.getBeginToken() == nt.getEndToken() && nt.typ == com.pullenti.ner.NumberSpellingType.WORDS && ((!nt.getBeginToken().chars.isAllLower() || (((attrs.value()) & (ParseAttr.MUSTBEITEMALWAYS.value()))) != (ParseAttr.NO.value())))) {
                PersonItemToken res00 = tryAttach(nt.getBeginToken(), attrs, prevList);
                if (res00 != null) {
                    res00.setBeginToken(res00.setEndToken(t));
                    return res00;
                }
            }
        }
        if (t instanceof com.pullenti.ner.ReferentToken) {
            com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
            if (rt.getBeginToken() == rt.getEndToken() && rt.getBeginToken().chars.isCapitalUpper()) {
                PersonItemToken res00 = tryAttach(rt.getBeginToken(), attrs, prevList);
                if (res00 != null) {
                    PersonItemToken res01 = tryAttach(t.getNext(), attrs, prevList);
                    if ((res01 != null && res01.lastname != null && res01.firstname == null) && res01.middlename == null) 
                        return null;
                    res00.setBeginToken(res00.setEndToken(t));
                    res00.isInDictionary = true;
                    return res00;
                }
            }
        }
        if ((((t instanceof com.pullenti.ner.TextToken) && t.getLengthChar() == 2 && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "JI")) && t.chars.isAllUpper() && !t.isWhitespaceAfter()) && t.getNext() != null && t.getNext().isChar('.')) {
            PersonItemToken re1 = _new2732(t, t.getNext(), ItemType.INITIAL, "Л");
            re1.chars.setCyrillicLetter(true);
            re1.chars.setAllUpper(true);
            return re1;
        }
        if ((((((t instanceof com.pullenti.ner.TextToken) && t.getLengthChar() == 1 && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "J")) && t.chars.isAllUpper() && !t.isWhitespaceAfter()) && (t.getNext() instanceof com.pullenti.ner.NumberToken) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getValue(), "1")) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.DIGIT && t.getNext().getNext() != null) && t.getNext().getNext().isChar('.')) {
            PersonItemToken re1 = _new2732(t, t.getNext().getNext(), ItemType.INITIAL, "Л");
            re1.chars.setCyrillicLetter(true);
            re1.chars.setAllUpper(true);
            return re1;
        }
        if ((((((t instanceof com.pullenti.ner.TextToken) && t.getLengthChar() == 1 && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "I")) && t.chars.isAllUpper() && !t.isWhitespaceAfter()) && (t.getNext() instanceof com.pullenti.ner.NumberToken) && com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getValue(), "1")) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).typ == com.pullenti.ner.NumberSpellingType.DIGIT && t.getNext().getNext() != null) && t.getNext().getNext().isChar('.')) {
            if (prevList != null && prevList.get(0).chars.isCyrillicLetter()) {
                PersonItemToken re1 = _new2732(t, t.getNext().getNext(), ItemType.INITIAL, "П");
                re1.chars.setCyrillicLetter(true);
                re1.chars.setAllUpper(true);
                return re1;
            }
        }
        PersonItemToken res = _tryAttach(t, attrs, prevList);
        if (res != null) 
            return res;
        if (t.chars.isLatinLetter() && (((attrs.value()) & (ParseAttr.CANBELATIN.value()))) != (ParseAttr.NO.value())) {
            java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> ots = null;
            PersonAnalyzerData ad = com.pullenti.ner.person.PersonAnalyzer.getData(t);
            if (ad != null) 
                ots = ad.localOntology.tryAttach(t, com.pullenti.ner.person.PersonReferent.OBJ_TYPENAME, false);
            if (t != null && t.kit.ontology != null && ots == null) 
                ots = t.kit.ontology.attachToken(com.pullenti.ner.person.PersonReferent.OBJ_TYPENAME, t);
            if (ots != null && (t instanceof com.pullenti.ner.TextToken)) {
                if (ots.get(0).termin.ignoreTermsOrder) 
                    return _new2740(ots.get(0).getBeginToken(), ots.get(0).getEndToken(), ItemType.REFERENT, (com.pullenti.ner.person.PersonReferent)com.pullenti.unisharp.Utils.cast(ots.get(0).item.tag, com.pullenti.ner.person.PersonReferent.class), ots.get(0).getMorph());
                res = _new2748(ots.get(0).getBeginToken(), ots.get(0).getEndToken(), ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, ots.get(0).chars);
                res.lastname = MorphPersonItem._new2741(ots.get(0).termin.getCanonicText(), true);
                for (com.pullenti.ner.core.IntOntologyToken ot : ots) {
                    if (ot.termin != null) {
                        com.pullenti.morph.MorphBaseInfo mi = ot.getMorph();
                        if (ot.termin.getGender() == com.pullenti.morph.MorphGender.MASCULINE || ot.termin.getGender() == com.pullenti.morph.MorphGender.FEMINIE) 
                            mi = com.pullenti.morph.MorphBaseInfo._new2731(ot.termin.getGender());
                        res.lastname.vars.add(new MorphPersonItemVariant(ot.termin.getCanonicText(), mi, true));
                    }
                }
                return res;
            }
            res = tryAttachLatin(t);
            if (res != null) 
                return res;
        }
        if (((t instanceof com.pullenti.ner.NumberToken) && t.getLengthChar() == 1 && (((attrs.value()) & (ParseAttr.CANINITIALBEDIGIT.value()))) != (ParseAttr.NO.value())) && t.getNext() != null && t.getNext().isCharOf(".„")) {
            if (com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue(), "1")) 
                return _new2734(t, t.getNext(), ItemType.INITIAL, "І", com.pullenti.morph.CharsInfo._new2751(true));
            if (com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue(), "0")) 
                return _new2734(t, t.getNext(), ItemType.INITIAL, "О", com.pullenti.morph.CharsInfo._new2751(true));
            if (com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue(), "3")) 
                return _new2734(t, t.getNext(), ItemType.INITIAL, "З", com.pullenti.morph.CharsInfo._new2751(true));
        }
        if ((((t instanceof com.pullenti.ner.NumberToken) && t.getLengthChar() == 1 && (((attrs.value()) & (ParseAttr.CANINITIALBEDIGIT.value()))) != (ParseAttr.NO.value())) && t.getNext() != null && t.getNext().chars.isAllLower()) && !t.isWhitespaceAfter() && t.getNext().getLengthChar() > 2) {
            String num = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue();
            if (com.pullenti.unisharp.Utils.stringsEq(num, "3") && t.getNext().chars.isCyrillicLetter()) 
                return _new2734(t, t.getNext(), ItemType.VALUE, "З" + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class)).term, com.pullenti.morph.CharsInfo._new2757(true, true));
            if (com.pullenti.unisharp.Utils.stringsEq(num, "0") && t.getNext().chars.isCyrillicLetter()) 
                return _new2734(t, t.getNext(), ItemType.VALUE, "О" + ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class)).term, com.pullenti.morph.CharsInfo._new2757(true, true));
        }
        if (((((t instanceof com.pullenti.ner.TextToken) && t.getLengthChar() == 1 && t.chars.isLetter()) && t.chars.isAllUpper() && (t.getWhitespacesAfterCount() < 2)) && (t.getNext() instanceof com.pullenti.ner.TextToken) && t.getNext().getLengthChar() == 1) && t.getNext().chars.isAllLower()) {
            int cou = 0;
            com.pullenti.ner.Token t1 = null;
            int lat = 0;
            int cyr = 0;
            char ch = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).getSourceText().charAt(0);
            if (t.chars.isCyrillicLetter()) {
                cyr++;
                if (((int)com.pullenti.morph.LanguageHelper.getLatForCyr(ch)) != 0) 
                    lat++;
            }
            else {
                lat++;
                if (((int)com.pullenti.morph.LanguageHelper.getCyrForLat(ch)) != 0) 
                    cyr++;
            }
            for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
                if (tt.getWhitespacesBeforeCount() > 1) 
                    break;
                if (!(tt instanceof com.pullenti.ner.TextToken) || tt.getLengthChar() != 1 || !tt.chars.isAllLower()) 
                    break;
                t1 = tt;
                cou++;
                ch = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).getSourceText().charAt(0);
                if (tt.chars.isCyrillicLetter()) {
                    cyr++;
                    if (((int)com.pullenti.morph.LanguageHelper.getLatForCyr(ch)) != 0) 
                        lat++;
                }
                else {
                    lat++;
                    if (((int)com.pullenti.morph.LanguageHelper.getCyrForLat(ch)) != 0) 
                        cyr++;
                }
            }
            if (cou < 2) 
                return null;
            if (cou < 5) {
                if (prevList != null && prevList.size() > 0 && prevList.get(prevList.size() - 1).typ == ItemType.INITIAL) {
                }
                else {
                    PersonItemToken ne = tryAttach(t1.getNext(), attrs, null);
                    if (ne == null || ne.typ != ItemType.INITIAL) 
                        return null;
                }
            }
            boolean isCyr = cyr >= lat;
            if (cyr == lat && t.chars.isLatinLetter()) 
                isCyr = false;
            StringBuilder val = new StringBuilder();
            for (com.pullenti.ner.Token tt = t; tt != null && tt.getEndChar() <= t1.getEndChar(); tt = tt.getNext()) {
                ch = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).getSourceText().charAt(0);
                if (isCyr && com.pullenti.morph.LanguageHelper.isLatinChar(ch)) {
                    char chh = com.pullenti.morph.LanguageHelper.getCyrForLat(ch);
                    if (((int)chh) != 0) 
                        ch = chh;
                }
                else if (!isCyr && com.pullenti.morph.LanguageHelper.isCyrillicChar(ch)) {
                    char chh = com.pullenti.morph.LanguageHelper.getLatForCyr(ch);
                    if (((int)chh) != 0) 
                        ch = chh;
                }
                val.append(Character.toUpperCase(ch));
            }
            res = _new2732(t, t1, ItemType.VALUE, val.toString());
            res.chars = com.pullenti.morph.CharsInfo._new2762(true, isCyr, !isCyr, true);
            return res;
        }
        if ((((attrs.value()) & (ParseAttr.MUSTBEITEMALWAYS.value()))) != (ParseAttr.NO.value()) && (t instanceof com.pullenti.ner.TextToken) && !t.chars.isAllLower()) {
            res = _new2763(t, t, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term);
            return res;
        }
        if (((t.chars.isAllUpper() && (t instanceof com.pullenti.ner.TextToken) && t.getLengthChar() == 1) && prevList != null && prevList.size() > 0) && (t.getWhitespacesBeforeCount() < 2) && prevList.get(0).chars.isCapitalUpper()) {
            PersonItemToken last = prevList.get(prevList.size() - 1);
            boolean ok = false;
            if ((last.typ == ItemType.VALUE && last.lastname != null && last.lastname.isInDictionary) && prevList.size() == 1) 
                ok = true;
            else if (prevList.size() == 2 && last.typ == ItemType.INITIAL && prevList.get(0).lastname != null) 
                ok = true;
            if (ok) 
                return _new2764(t, t, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, ItemType.INITIAL);
        }
        return null;
    }

    private static PersonItemToken _tryAttach(com.pullenti.ner.Token t, ParseAttr attrs, java.util.ArrayList<PersonItemToken> prevList) {
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) {
            if (t.chars.isLetter() && t.chars.isCapitalUpper() && (t instanceof com.pullenti.ner.ReferentToken)) {
                com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
                if (rt.getBeginToken() == rt.getEndToken() && !(rt.referent instanceof com.pullenti.ner.person.PersonReferent)) {
                    PersonItemToken res0 = _tryAttach(rt.getBeginToken(), attrs, null);
                    if (res0 == null) {
                        res0 = _new2765(rt, rt, rt.referent.toStringEx(true, t.kit.baseLanguage, 0).toUpperCase(), rt.chars, rt.getMorph());
                        res0.lastname = MorphPersonItem._new2738(res0.value);
                    }
                    else 
                        res0.setBeginToken(res0.setEndToken(rt));
                    if ((t.getNext() != null && t.getNext().isHiphen() && (t.getNext().getNext() instanceof com.pullenti.ner.TextToken)) && t.getNext().getNext().getMorphClassInDictionary().isProperSecname()) {
                        PersonItemToken res1 = tryAttach(t.getNext().getNext(), ParseAttr.NO, null);
                        if (res1 != null && res1.middlename != null) {
                            res1.middlename.addPrefix(res0.value + "-");
                            res1.firstname = res1.middlename;
                            res1.setBeginToken(t);
                            return res1;
                        }
                    }
                    return res0;
                }
            }
            return null;
        }
        if (!tt.chars.isLetter()) 
            return null;
        boolean canBeAllLower = false;
        if (tt.chars.isAllLower() && (((attrs.value()) & (ParseAttr.CANBELOWER.value()))) == (ParseAttr.NO.value())) {
            if (!M_SURPREFIXES.contains(tt.term)) {
                com.pullenti.morph.MorphClass mc0 = tt.getMorphClassInDictionary();
                if (((com.pullenti.unisharp.Utils.stringsEq(tt.term, "Д") && !tt.isWhitespaceAfter() && com.pullenti.ner.core.BracketHelper.isBracket(tt.getNext(), true)) && !tt.getNext().isWhitespaceAfter() && (tt.getNext().getNext() instanceof com.pullenti.ner.TextToken)) && tt.getNext().getNext().chars.isCapitalUpper()) {
                }
                else if (mc0.isProperSurname() && !mc0.isNoun()) {
                    if (tt.getNext() != null && (tt.getWhitespacesAfterCount() < 3)) {
                        com.pullenti.morph.MorphClass mc1 = tt.getNext().getMorphClassInDictionary();
                        if (mc1.isProperName()) 
                            canBeAllLower = true;
                    }
                    if (tt.getPrevious() != null && (tt.getWhitespacesBeforeCount() < 3)) {
                        com.pullenti.morph.MorphClass mc1 = tt.getPrevious().getMorphClassInDictionary();
                        if (mc1.isProperName()) 
                            canBeAllLower = true;
                    }
                    if (!canBeAllLower) 
                        return null;
                }
                else if (mc0.isProperSecname() && !mc0.isNoun()) {
                    if (tt.getPrevious() != null && (tt.getWhitespacesBeforeCount() < 3)) {
                        com.pullenti.morph.MorphClass mc1 = tt.getPrevious().getMorphClassInDictionary();
                        if (mc1.isProperName()) 
                            canBeAllLower = true;
                    }
                    if (!canBeAllLower) 
                        return null;
                }
                else if (mc0.isProperName() && !mc0.isNoun()) {
                    if (tt.getNext() != null && (tt.getWhitespacesAfterCount() < 3)) {
                        com.pullenti.morph.MorphClass mc1 = tt.getNext().getMorphClassInDictionary();
                        if (mc1.isProperSurname() || mc1.isProperSecname()) 
                            canBeAllLower = true;
                    }
                    if (tt.getPrevious() != null && (tt.getWhitespacesBeforeCount() < 3)) {
                        com.pullenti.morph.MorphClass mc1 = tt.getPrevious().getMorphClassInDictionary();
                        if (mc1.isProperSurname()) 
                            canBeAllLower = true;
                    }
                    if (!canBeAllLower) 
                        return null;
                }
                else 
                    return null;
            }
        }
        if (tt.getLengthChar() == 1 || com.pullenti.unisharp.Utils.stringsEq(tt.term, "ДЖ")) {
            if (tt.getNext() == null) 
                return null;
            String ini = tt.term;
            com.pullenti.morph.CharsInfo ci = com.pullenti.morph.CharsInfo._new2767(tt.chars.value);
            if (!tt.chars.isCyrillicLetter()) {
                char cyr = com.pullenti.morph.LanguageHelper.getCyrForLat(ini.charAt(0));
                if (cyr == ((char)0)) 
                    return null;
                ini = (String.valueOf(cyr));
                ci.setLatinLetter(false);
                ci.setCyrillicLetter(true);
            }
            if (tt.getNext().isChar('.')) 
                return _new2734(tt, tt.getNext(), ItemType.INITIAL, ini, ci);
            if ((tt.getNext().isCharOf(",;„") && prevList != null && prevList.size() > 0) && prevList.get(prevList.size() - 1).typ == ItemType.INITIAL) 
                return _new2734(tt, tt, ItemType.INITIAL, ini, ci);
            if ((tt.getNext().getWhitespacesAfterCount() < 2) && (tt.getWhitespacesAfterCount() < 2) && ((com.pullenti.unisharp.Utils.stringsEq(tt.term, "Д") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "О") || com.pullenti.unisharp.Utils.stringsEq(tt.term, "Н")))) {
                if (com.pullenti.ner.core.BracketHelper.isBracket(tt.getNext(), false) && (tt.getNext().getNext() instanceof com.pullenti.ner.TextToken)) {
                    if (tt.getNext().getNext().chars.isCyrillicLetter()) {
                        PersonItemToken pit0 = tryAttach(tt.getNext().getNext(), ParseAttr.of((attrs.value()) | (ParseAttr.CANBELOWER.value())), prevList);
                        if (pit0 != null) {
                            pit0.setBeginToken(tt);
                            if (pit0.value != null) 
                                pit0.value = ini + pit0.value;
                            if (pit0.lastname != null) {
                                pit0.lastname.addPrefix(ini);
                                pit0.lastname.isInDictionary = true;
                            }
                            else if (pit0.firstname != null) {
                                pit0.lastname = pit0.firstname;
                                pit0.lastname.addPrefix(ini);
                                pit0.lastname.isInDictionary = true;
                            }
                            pit0.firstname = (pit0.middlename = null);
                            if (!pit0.chars.isAllUpper() && !pit0.chars.isCapitalUpper()) 
                                pit0.chars.setCapitalUpper(true);
                            return pit0;
                        }
                    }
                }
            }
            if (!com.pullenti.morph.LanguageHelper.isCyrillicVowel(tt.term.charAt(0))) 
                return null;
            if (tt.getWhitespacesAfterCount() != 1) {
                if (tt.getNext() == null) {
                }
                else if ((!tt.isWhitespaceAfter() && (tt.getNext() instanceof com.pullenti.ner.TextToken) && !tt.getNext().isChar('.')) && !tt.getNext().chars.isLetter()) {
                }
                else 
                    return null;
            }
            return _new2734(tt, tt, ItemType.VALUE, tt.term, tt.chars);
        }
        if (!tt.chars.isCyrillicLetter()) 
            return null;
        if (!com.pullenti.ner.core.MiscHelper.hasVowel(tt)) 
            return null;
        java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> ots = null;
        PersonAnalyzerData ad = com.pullenti.ner.person.PersonAnalyzer.getData(t);
        if (ad.localOntology.getItems().size() < 1000) {
            ots = ad.localOntology.tryAttach(t, com.pullenti.ner.person.PersonReferent.OBJ_TYPENAME, false);
            if (ots != null && ots.get(0).getBeginToken() != ots.get(0).getEndToken() && !ots.get(0).getBeginToken().getNext().isHiphen()) {
                if (PersonAttrToken.tryAttachWord(ots.get(0).getBeginToken(), false) != null) 
                    ots = null;
            }
        }
        if (t != null && t.kit.ontology != null && ots == null) 
            ots = t.kit.ontology.attachToken(com.pullenti.ner.person.PersonReferent.OBJ_TYPENAME, t);
        String surPrefix = null;
        PersonItemToken res = null;
        if (ots != null) {
            if (ots.get(0).termin.ignoreTermsOrder) 
                return _new2740(ots.get(0).getBeginToken(), ots.get(0).getEndToken(), ItemType.REFERENT, (com.pullenti.ner.person.PersonReferent)com.pullenti.unisharp.Utils.cast(ots.get(0).item.tag, com.pullenti.ner.person.PersonReferent.class), ots.get(0).getMorph());
            com.pullenti.morph.MorphClass mc = ots.get(0).getBeginToken().getMorphClassInDictionary();
            if (ots.get(0).getBeginToken() == ots.get(0).getEndToken() && mc.isProperName() && !mc.isProperSurname()) 
                ots = null;
        }
        if (ots != null) {
            res = _new2748(ots.get(0).getBeginToken(), ots.get(0).getEndToken(), tt.term, ots.get(0).chars);
            res.lastname = MorphPersonItem._new2773(true);
            res.lastname.term = ots.get(0).termin.getCanonicText().replace(" - ", "-");
            res.value = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(ots.get(0), com.pullenti.ner.core.GetTextAttr.NO);
            for (com.pullenti.ner.core.IntOntologyToken ot : ots) {
                if (ot.termin != null) {
                    com.pullenti.morph.MorphBaseInfo mi = ot.getMorph();
                    if (ot.termin.getGender() == com.pullenti.morph.MorphGender.MASCULINE) {
                        if (((short)((t.getMorph().getGender().value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                            continue;
                        mi = com.pullenti.morph.MorphBaseInfo._new2731(ot.termin.getGender());
                    }
                    else if (ot.termin.getGender() == com.pullenti.morph.MorphGender.FEMINIE) {
                        if (((short)((t.getMorph().getGender().value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                            continue;
                        mi = com.pullenti.morph.MorphBaseInfo._new2731(ot.termin.getGender());
                    }
                    else 
                        continue;
                    res.lastname.vars.add(new MorphPersonItemVariant(ot.termin.getCanonicText(), mi, true));
                }
            }
            if ((ots.get(0).termin.getCanonicText().indexOf("-") >= 0)) 
                return res;
        }
        else {
            res = _new2765(t, t, tt.term, tt.chars, tt.getMorph());
            if (M_SURPREFIXES.contains(tt.term)) {
                if (((tt.isValue("БЕН", null) || tt.isValue("ВАН", null))) && (((attrs.value()) & (ParseAttr.ALTVAR.value()))) != (ParseAttr.NO.value()) && ((tt.getNext() == null || !tt.getNext().isHiphen()))) {
                }
                else {
                    if (tt.getNext() != null) {
                        com.pullenti.ner.Token t1 = tt.getNext();
                        if ((t1 instanceof com.pullenti.ner.TextToken) && ((com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class)).term, "Л") || com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class)).term, "ЛЬ") || M_SURPREFIXES.contains(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class)).term)))) {
                            res.setEndToken(t1);
                            res.value += ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class)).term;
                            t1 = t1.getNext();
                        }
                        else if ((t1.isHiphen() && (t1.getNext() instanceof com.pullenti.ner.TextToken) && t1.getNext().getNext() != null) && ((com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.TextToken.class)).term, "Л") || com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.TextToken.class)).term, "ЛЬ") || M_SURPREFIXES.contains(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.TextToken.class)).term)))) {
                            res.setEndToken(t1.getNext());
                            res.value += ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.TextToken.class)).term;
                            t1 = t1.getNext().getNext();
                        }
                        if (t1 != null && t1.isHiphen()) 
                            tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.TextToken.class);
                        else if ((((attrs.value()) & (ParseAttr.SURNAMEPREFIXNOTMERGE.value()))) != (ParseAttr.NO.value()) && t1 != null && t1.chars.isAllLower()) 
                            tt = null;
                        else 
                            tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class);
                        if ((tt == null || tt.isNewlineBefore() || tt.chars.isAllLower()) || !tt.chars.isCyrillicLetter() || (tt.getLengthChar() < 3)) {
                        }
                        else {
                            surPrefix = res.value;
                            res.value = (res.value + "-" + tt.term);
                            res.setMorph(tt.getMorph());
                            res.chars = tt.chars;
                            res.setEndToken(tt);
                        }
                    }
                    if (surPrefix == null) {
                        if (t.chars.isCapitalUpper() || t.chars.isAllUpper()) 
                            return res;
                        return null;
                    }
                }
            }
        }
        if (tt.isValue("ФАМИЛИЯ", "ПРІЗВИЩЕ") || tt.isValue("ИМЯ", "ІМЯ") || tt.isValue("ОТЧЕСТВО", "БАТЬКОВІ")) 
            return null;
        if (tt.getMorph()._getClass().isPreposition() || tt.getMorph()._getClass().isConjunction()) {
            if (tt.getMorphClassInDictionary().isProperName()) {
            }
            else if (tt.getNext() == null || !tt.getNext().isChar('.')) {
                if (tt.getLengthChar() > 1 && tt.chars.isCapitalUpper() && !com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt)) {
                }
                else 
                    return null;
            }
        }
        if ((((attrs.value()) & (ParseAttr.MUSTBEITEMALWAYS.value()))) != (ParseAttr.NO.value())) {
        }
        else {
            if (tt.term.length() > 6 && tt.term.startsWith("ЗД")) {
                if (com.pullenti.ner.core.MiscHelper.isNotMoreThanOneError("ЗДРАВСТВУЙТЕ", tt)) 
                    return null;
                if (com.pullenti.ner.core.MiscHelper.isNotMoreThanOneError("ЗДРАВСТВУЙ", tt)) 
                    return null;
            }
            if (tt.getLengthChar() > 6 && tt.term.startsWith("ПР")) {
                if (com.pullenti.ner.core.MiscHelper.isNotMoreThanOneError("ПРИВЕТСТВУЮ", tt)) 
                    return null;
            }
            if (tt.getLengthChar() > 6 && tt.term.startsWith("УВ")) {
                if (tt.isValue("УВАЖАЕМЫЙ", null)) 
                    return null;
            }
            if (tt.getLengthChar() > 6 && tt.term.startsWith("ДО")) {
                if (tt.isValue("ДОРОГОЙ", null)) 
                    return null;
            }
        }
        if (!tt.chars.isAllUpper() && !tt.chars.isCapitalUpper() && !canBeAllLower) {
            if ((((attrs.value()) & (ParseAttr.CANINITIALBEDIGIT.value()))) != (ParseAttr.NO.value()) && !tt.chars.isAllLower()) {
            }
            else if ((((attrs.value()) & (ParseAttr.CANBELOWER.value()))) == (ParseAttr.NO.value())) 
                return null;
        }
        com.pullenti.morph.MorphWordForm adj = null;
        for (com.pullenti.morph.MorphBaseInfo wff : tt.getMorph().getItems()) {
            com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wff, com.pullenti.morph.MorphWordForm.class);
            if (wf == null) 
                continue;
            if (wf._getClass().isAdjective() && wf.containsAttr("к.ф.", null)) {
                if (wf.isInDictionary()) {
                    if (com.pullenti.morph.LanguageHelper.endsWith(tt.term, "НО") || ((tt.getNext() != null && tt.getNext().isHiphen()))) 
                        res.isInDictionary = true;
                }
                continue;
            }
            else if ((wf._getClass().isAdjective() && adj == null && !(((wf.normalFull != null ? wf.normalFull : wf.normalCase))).endsWith("ОВ")) && !(((wf.normalFull != null ? wf.normalFull : wf.normalCase))).endsWith("ИН") && (((wf.isInDictionary() || wf.normalCase.endsWith("ЫЙ") || wf.normalCase.endsWith("КИЙ")) || wf.normalCase.endsWith("АЯ") || wf.normalCase.endsWith("ЯЯ")))) 
                adj = wf;
            if (wf._getClass().isVerb()) {
                if (wf.isInDictionary()) 
                    res.isInDictionary = true;
                continue;
            }
            if (wf.isInDictionary()) {
                if ((wf._getClass().isAdverb() || wf._getClass().isPreposition() || wf._getClass().isConjunction()) || wf._getClass().isPronoun() || wf._getClass().isPersonalPronoun()) 
                    res.isInDictionary = true;
            }
            if (wf._getClass().isProperSurname() || surPrefix != null) {
                if (res.lastname == null) 
                    res.lastname = MorphPersonItem._new2738(tt.term);
                if (adj != null) {
                    if (!wf.isInDictionary() && adj.getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) {
                        String val = adj.normalCase;
                        res.lastname.vars.add(new MorphPersonItemVariant(val, adj, true));
                        if (com.pullenti.unisharp.Utils.stringsEq(val, tt.term)) 
                            break;
                    }
                    adj = null;
                }
                if ((((attrs.value()) & (ParseAttr.NOMINATIVECASE.value()))) != (ParseAttr.NO.value())) {
                    if (!wf.getCase().isUndefined() && !wf.getCase().isNominative()) 
                        continue;
                }
                MorphPersonItemVariant v = new MorphPersonItemVariant(wf.normalCase, wf, true);
                if (com.pullenti.unisharp.Utils.stringsNe(wf.normalCase, tt.term) && com.pullenti.morph.LanguageHelper.endsWith(tt.term, "ОВ")) {
                    v.value = tt.term;
                    v.setGender(com.pullenti.morph.MorphGender.MASCULINE);
                }
                else if ((wf.getNumber() == com.pullenti.morph.MorphNumber.PLURAL && wf.normalFull != null && com.pullenti.unisharp.Utils.stringsNe(wf.normalFull, wf.normalCase)) && wf.normalFull.length() > 1) {
                    v.value = wf.normalFull;
                    v.setNumber(com.pullenti.morph.MorphNumber.SINGULAR);
                    if (wf.normalCase.length() > tt.term.length()) 
                        v.value = tt.term;
                }
                res.lastname.vars.add(v);
                if (wf.isInDictionary() && v.getGender() == com.pullenti.morph.MorphGender.UNDEFINED && wf.getGender() == com.pullenti.morph.MorphGender.UNDEFINED) {
                    v.setGender(com.pullenti.morph.MorphGender.MASCULINE);
                    MorphPersonItemVariant vv = new MorphPersonItemVariant(wf.normalCase, wf, true);
                    vv.value = v.value;
                    vv.shortValue = v.shortValue;
                    vv.setGender(com.pullenti.morph.MorphGender.FEMINIE);
                    res.lastname.vars.add(vv);
                }
                if (wf.isInDictionary()) 
                    res.lastname.isInDictionary = true;
                if (tt.term.endsWith("ИХ") || tt.term.endsWith("ЫХ")) {
                    if (com.pullenti.unisharp.Utils.stringsNe(res.lastname.vars.get(0).value, tt.term)) 
                        res.lastname.vars.add(0, new MorphPersonItemVariant(tt.term, com.pullenti.morph.MorphBaseInfo._new2779(com.pullenti.morph.MorphCase.ALLCASES, com.pullenti.morph.MorphGender.of((short)((com.pullenti.morph.MorphGender.MASCULINE.value()) | (com.pullenti.morph.MorphGender.FEMINIE.value()))), com.pullenti.morph.MorphClass._new2778(true)), true));
                }
            }
            if (surPrefix != null) 
                continue;
            if (wf._getClass().isProperName() && wf.getNumber() != com.pullenti.morph.MorphNumber.PLURAL) {
                boolean ok = true;
                if (t.getMorph().getLanguage().isUa()) {
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(wf.normalCase, "ЯКОВ") || com.pullenti.unisharp.Utils.stringsEq(wf.normalCase, "ИОВ") || com.pullenti.unisharp.Utils.stringsEq(wf.normalCase, "ИАКОВ")) {
                }
                else if (wf.normalCase != null && (wf.normalCase.length() < 5)) {
                }
                else {
                    ok = !com.pullenti.morph.LanguageHelper.endsWith(wf.normalCase, "ОВ") && com.pullenti.unisharp.Utils.stringsNe(wf.normalCase, "АЛЛ");
                    if (ok) {
                        if (tt.chars.isAllUpper() && (tt.getLengthChar() < 4)) 
                            ok = false;
                    }
                }
                if (ok) {
                    if (res.firstname == null) 
                        res.firstname = MorphPersonItem._new2738(tt.term);
                    res.firstname.vars.add(new MorphPersonItemVariant(wf.normalCase, wf, false));
                    if (wf.isInDictionary()) {
                        if (!tt.chars.isAllUpper() || tt.getLengthChar() > 4) 
                            res.firstname.isInDictionary = true;
                    }
                }
            }
            if (!MorphPersonItem.endsWithStdSurname(tt.term)) {
                if (wf._getClass().isProperSecname()) {
                    if (res.middlename == null) 
                        res.middlename = MorphPersonItem._new2738(tt.term);
                    else if (wf.misc.getForm() == com.pullenti.morph.MorphForm.SYNONYM) 
                        continue;
                    MorphPersonItemVariant iii = new MorphPersonItemVariant(wf.normalCase, wf, false);
                    if (com.pullenti.unisharp.Utils.stringsEq(iii.value, tt.term)) 
                        res.middlename.vars.add(0, iii);
                    else 
                        res.middlename.vars.add(iii);
                    if (wf.isInDictionary()) 
                        res.middlename.isInDictionary = true;
                }
                if (!wf._getClass().isProper() && wf.isInDictionary()) 
                    res.isInDictionary = true;
            }
            else if (wf.isInDictionary() && !wf._getClass().isProper() && com.pullenti.morph.LanguageHelper.endsWith(tt.term, "КО")) 
                res.isInDictionary = true;
        }
        if (res.lastname != null) {
            for (MorphPersonItemVariant v : res.lastname.vars) {
                if (MorphPersonItem.endsWithStdSurname(v.value)) {
                    res.lastname.isLastnameHasStdTail = true;
                    break;
                }
            }
            if (!res.lastname.isInDictionary) {
                if (((!res.lastname.isInDictionary && !res.lastname.isLastnameHasStdTail)) || MorphPersonItem.endsWithStdSurname(tt.term)) {
                    MorphPersonItemVariant v = new MorphPersonItemVariant(tt.term, null, true);
                    if (com.pullenti.morph.LanguageHelper.endsWithEx(tt.term, "ВА", "НА", null, null)) 
                        res.lastname.vars.add(0, v);
                    else 
                        res.lastname.vars.add(v);
                    if (MorphPersonItem.endsWithStdSurname(v.value) && !res.lastname.isInDictionary) 
                        res.lastname.isLastnameHasStdTail = true;
                }
            }
            res.lastname.correctLastnameVariants();
            if (surPrefix != null) {
                res.lastname.isLastnameHasHiphen = true;
                res.lastname.term = (surPrefix + "-" + res.lastname.term);
                for (MorphPersonItemVariant v : res.lastname.vars) {
                    v.value = (surPrefix + "-" + v.value);
                }
            }
            if (tt.getMorph()._getClass().isAdjective() && !res.lastname.isInOntology) {
                boolean stdEnd = false;
                for (MorphPersonItemVariant v : res.lastname.vars) {
                    if (MorphPersonItem.endsWithStdSurname(v.value)) {
                        stdEnd = true;
                        break;
                    }
                }
                if (!stdEnd && (tt.getWhitespacesAfterCount() < 2)) {
                    com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                    if (npt != null && npt.getEndToken() != npt.getBeginToken()) {
                        if ((prevList != null && prevList.size() == 1 && prevList.get(0).firstname != null) && prevList.get(0).firstname.isInDictionary && tt.getWhitespacesBeforeCount() == 1) {
                        }
                        else {
                            PersonItemToken nex = _tryAttach(npt.getEndToken(), attrs, null);
                            if (nex != null && nex.firstname != null) {
                            }
                            else 
                                res.lastname = null;
                        }
                    }
                }
            }
        }
        else if (tt.getLengthChar() > 2) {
            res.lastname = new MorphPersonItem();
            for (com.pullenti.morph.MorphBaseInfo wf : tt.getMorph().getItems()) {
                if (!wf._getClass().isVerb()) {
                    if (wf.containsAttr("к.ф.", null)) 
                        continue;
                    res.lastname.vars.add(new MorphPersonItemVariant(((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class)).normalCase, wf, true));
                    if (!res.lastname.isLastnameHasStdTail) 
                        res.lastname.isLastnameHasStdTail = MorphPersonItem.endsWithStdSurname(((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class)).normalCase);
                }
            }
            res.lastname.vars.add(new MorphPersonItemVariant(tt.term, null, true));
            if (!res.lastname.isLastnameHasStdTail) 
                res.lastname.isLastnameHasStdTail = MorphPersonItem.endsWithStdSurname(tt.term);
            if (surPrefix != null) {
                res.lastname.addPrefix(surPrefix + "-");
                res.lastname.isLastnameHasHiphen = true;
            }
        }
        if (res.getBeginToken() == res.getEndToken()) {
            if (res.getBeginToken().getMorphClassInDictionary().isVerb() && res.lastname != null) {
                if (!res.lastname.isLastnameHasStdTail && !res.lastname.isInDictionary) {
                    if (res.isNewlineBefore()) {
                    }
                    else if (res.getBeginToken().chars.isCapitalUpper() && !com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(res.getBeginToken())) {
                    }
                    else 
                        res.lastname = null;
                }
            }
            if (res.lastname != null && res.getBeginToken().isValue("ЗАМ", null)) 
                return null;
            if (res.firstname != null && (res.getBeginToken() instanceof com.pullenti.ner.TextToken)) {
                if (com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(res.getBeginToken(), com.pullenti.ner.TextToken.class)).term, "ЛЮБОЙ")) 
                    res.firstname = null;
            }
            if (res.getBeginToken().getMorphClassInDictionary().isAdjective() && res.lastname != null) {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(res.getBeginToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
                if (npt != null) {
                    if (npt.getBeginToken() != npt.getEndToken()) {
                        if (!res.lastname.isInOntology && !res.lastname.isInDictionary) 
                            res.lastname = null;
                    }
                }
            }
        }
        if (res.firstname != null) {
            for (int i = 0; i < res.firstname.vars.size(); i++) {
                String val = res.firstname.vars.get(i).value;
                java.util.ArrayList<ShortNameHelper.ShortnameVar> di = ShortNameHelper.getNamesForShortname(val);
                if (di == null) 
                    continue;
                com.pullenti.morph.MorphGender g = res.firstname.vars.get(i).getGender();
                if (g != com.pullenti.morph.MorphGender.MASCULINE && g != com.pullenti.morph.MorphGender.FEMINIE) {
                    boolean fi = true;
                    for (ShortNameHelper.ShortnameVar kp : di) {
                        if (fi) {
                            res.firstname.vars.get(i).shortValue = val;
                            res.firstname.vars.get(i).value = kp.name;
                            res.firstname.vars.get(i).setGender(kp.gender);
                            fi = false;
                        }
                        else {
                            com.pullenti.morph.MorphBaseInfo mi = com.pullenti.morph.MorphBaseInfo._new2731(kp.gender);
                            res.firstname.vars.add(MorphPersonItemVariant._new2783(kp.name, mi, false, val));
                        }
                    }
                }
                else {
                    int cou = 0;
                    for (ShortNameHelper.ShortnameVar kp : di) {
                        if (kp.gender == g) {
                            if ((++cou) < 2) {
                                res.firstname.vars.get(i).value = kp.name;
                                res.firstname.vars.get(i).shortValue = val;
                            }
                            else 
                                res.firstname.vars.add(i + 1, MorphPersonItemVariant._new2783(kp.name, res.firstname.vars.get(i), false, val));
                        }
                    }
                }
            }
        }
        if ((res != null && res.isInDictionary && res.firstname == null) && (((attrs.value()) & (ParseAttr.MUSTBEITEMALWAYS.value()))) == (ParseAttr.NO.value())) {
            com.pullenti.ner.core.StatisticWordInfo wi = res.kit.statistics.getWordInfo(res.getBeginToken());
            if (wi != null && wi.lowerCount > 0) {
                if (((t.getMorph()._getClass().isPreposition() || t.getMorph()._getClass().isConjunction() || t.getMorph()._getClass().isPronoun())) && !com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) {
                }
                else 
                    return null;
            }
        }
        if (res.getEndToken().getNext() != null && res.getEndToken().getNext().isHiphen() && (res.getEndToken().getNext().getNext() instanceof com.pullenti.ner.TextToken)) {
            String ter = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(res.getEndToken().getNext().getNext(), com.pullenti.ner.TextToken.class)).term;
            if (M_ARABPOSTFIX.contains(ter) || M_ARABPOSTFIXFEM.contains(ter)) {
                res.setEndToken(res.getEndToken().getNext().getNext());
                res.addPostfixInfo(ter, (M_ARABPOSTFIXFEM.contains(ter) ? com.pullenti.morph.MorphGender.FEMINIE : com.pullenti.morph.MorphGender.MASCULINE));
                if ((((com.pullenti.unisharp.Utils.stringsEq(ter, "ОГЛЫ") || com.pullenti.unisharp.Utils.stringsEq(ter, "ОГЛИ") || com.pullenti.unisharp.Utils.stringsEq(ter, "КЫЗЫ")) || com.pullenti.unisharp.Utils.stringsEq(ter, "ГЫЗЫ") || com.pullenti.unisharp.Utils.stringsEq(ter, "УГЛИ")) || com.pullenti.unisharp.Utils.stringsEq(ter, "КЗЫ") || com.pullenti.unisharp.Utils.stringsEq(ter, "УЛЫ")) || com.pullenti.unisharp.Utils.stringsEq(ter, "УУЛУ")) {
                    if (res.middlename != null) {
                        res.firstname = null;
                        res.lastname = null;
                    }
                }
            }
            else if ((!res.isWhitespaceAfter() && !res.getEndToken().getNext().isWhitespaceAfter() && res.getEndToken().getNext().getNext().chars.equals(res.getBeginToken().chars)) && res.getBeginToken() == res.getEndToken()) {
                PersonItemToken res1 = tryAttach(res.getEndToken().getNext().getNext(), ParseAttr.NO, null);
                if (res1 != null && res1.getBeginToken() == res1.getEndToken()) {
                    if (res1.lastname != null && res.lastname != null && ((((res1.lastname.isHasStdPostfix || res1.lastname.isInDictionary || res1.lastname.isInOntology) || res.lastname.isHasStdPostfix || res.lastname.isInDictionary) || res.lastname.isInOntology))) {
                        res.lastname.mergeHiphen(res1.lastname);
                        if (res.value != null && res1.value != null) 
                            res.value = (res.value + "-" + res1.value);
                        res.firstname = null;
                        res.middlename = null;
                        res.setEndToken(res1.getEndToken());
                    }
                    else if (res.firstname != null && ((res.firstname.isInDictionary || res.firstname.isInOntology))) {
                        if (res1.firstname != null) {
                            if (res.value != null && res1.value != null) 
                                res.value = (res.value + "-" + res1.value);
                            res.firstname.mergeHiphen(res1.firstname);
                            res.lastname = null;
                            res.middlename = null;
                            res.setEndToken(res1.getEndToken());
                        }
                        else if (res1.middlename != null) {
                            if (res.value != null && res1.value != null) 
                                res.value = (res.value + "-" + res1.value);
                            res.setEndToken(res1.getEndToken());
                            if (res.middlename != null) 
                                res.middlename.mergeHiphen(res1.middlename);
                            if (res.firstname != null) {
                                res.firstname.mergeHiphen(res1.middlename);
                                if (res.middlename == null) 
                                    res.middlename = res.firstname;
                            }
                            if (res.lastname != null) {
                                res.lastname.mergeHiphen(res1.middlename);
                                if (res.middlename == null) 
                                    res.middlename = res.firstname;
                            }
                        }
                        else if (res1.lastname != null && !res1.lastname.isInDictionary && !res1.lastname.isInOntology) {
                            if (res.value != null && res1.value != null) 
                                res.value = (res.value + "-" + res1.value);
                            res.firstname.mergeHiphen(res1.lastname);
                            res.lastname = null;
                            res.middlename = null;
                            res.setEndToken(res1.getEndToken());
                        }
                    }
                    else if ((res.firstname == null && res.middlename == null && res.lastname != null) && !res.lastname.isInOntology && !res.lastname.isInDictionary) {
                        if (res.value != null && res1.value != null) 
                            res.value = (res.value + "-" + res1.value);
                        res.setEndToken(res1.getEndToken());
                        if (res1.firstname != null) {
                            res.lastname.mergeHiphen(res1.firstname);
                            res.firstname = res.lastname;
                            res.lastname = (res.middlename = null);
                        }
                        else if (res1.middlename != null) {
                            res.lastname.mergeHiphen(res1.middlename);
                            res.middlename = res.lastname;
                            res.firstname = null;
                        }
                        else if (res1.lastname != null) 
                            res.lastname.mergeHiphen(res1.lastname);
                        else if (res1.value != null) {
                            for (MorphPersonItemVariant v : res.lastname.vars) {
                                v.value = (v.value + "-" + res1.value);
                            }
                        }
                    }
                    else if (((res.firstname == null && res.lastname == null && res.middlename == null) && res1.lastname != null && res.value != null) && res1.value != null) {
                        res.lastname = res1.lastname;
                        res.lastname.addPrefix(res.value + "-");
                        res.value = (res.value + "-" + res1.value);
                        res.firstname = null;
                        res.middlename = null;
                        res.setEndToken(res1.getEndToken());
                    }
                    else if (((res.firstname == null && res.lastname != null && res.middlename == null) && res1.lastname == null && res.value != null) && res1.value != null) {
                        res.lastname.addPostfix("-" + res1.value, com.pullenti.morph.MorphGender.UNDEFINED);
                        res.value = (res.value + "-" + res1.value);
                        res.firstname = null;
                        res.middlename = null;
                        res.setEndToken(res1.getEndToken());
                    }
                }
            }
        }
        while ((res.getEndToken().getWhitespacesAfterCount() < 3) && (res.getEndToken().getNext() instanceof com.pullenti.ner.TextToken)) {
            String ter = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(res.getEndToken().getNext(), com.pullenti.ner.TextToken.class)).term;
            if (((com.pullenti.unisharp.Utils.stringsNe(ter, "АЛИ") && com.pullenti.unisharp.Utils.stringsNe(ter, "ПАША"))) || res.getEndToken().getNext().chars.isAllLower()) {
                if (M_ARABPOSTFIX.contains(ter) || M_ARABPOSTFIXFEM.contains(ter)) {
                    if (res.getEndToken().getNext().getNext() != null && res.getEndToken().getNext().getNext().isHiphen()) {
                    }
                    else {
                        res.setEndToken(res.getEndToken().getNext());
                        res.addPostfixInfo(ter, (M_ARABPOSTFIXFEM.contains(ter) ? com.pullenti.morph.MorphGender.FEMINIE : com.pullenti.morph.MorphGender.MASCULINE));
                        if ((((com.pullenti.unisharp.Utils.stringsEq(ter, "ОГЛЫ") || com.pullenti.unisharp.Utils.stringsEq(ter, "ОГЛИ") || com.pullenti.unisharp.Utils.stringsEq(ter, "КЫЗЫ")) || com.pullenti.unisharp.Utils.stringsEq(ter, "ГЫЗЫ") || com.pullenti.unisharp.Utils.stringsEq(ter, "УГЛИ")) || com.pullenti.unisharp.Utils.stringsEq(ter, "КЗЫ") || com.pullenti.unisharp.Utils.stringsEq(ter, "УЛЫ")) || com.pullenti.unisharp.Utils.stringsEq(ter, "УУЛУ")) {
                            if (res.middlename != null) {
                                res.firstname = null;
                                res.lastname = null;
                            }
                        }
                        continue;
                    }
                }
            }
            break;
        }
        return res;
    }

    public static java.util.ArrayList<String> M_SURPREFIXES;

    private static java.util.ArrayList<String> m_SurPrefixesLat;

    public static java.util.ArrayList<String> M_ARABPOSTFIX;

    public static java.util.ArrayList<String> M_ARABPOSTFIXFEM;

    public static java.util.ArrayList<PersonItemToken> tryAttachList(com.pullenti.ner.Token t, ParseAttr attrs, int maxCount) {
        if (t == null) 
            return null;
        if (((!(t instanceof com.pullenti.ner.TextToken) || !t.chars.isLetter())) && (((attrs.value()) & (ParseAttr.CANINITIALBEDIGIT.value()))) == (ParseAttr.NO.value())) {
            if ((t instanceof com.pullenti.ner.ReferentToken) && (((t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) || com.pullenti.unisharp.Utils.stringsEq(t.getReferent().getTypeName(), "ORGANIZATION") || com.pullenti.unisharp.Utils.stringsEq(t.getReferent().getTypeName(), "TRANSPORT")))) {
                if (((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class)).getBeginToken() == ((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class)).getEndToken()) {
                }
                else 
                    return null;
            }
            else if (t instanceof com.pullenti.ner.NumberToken) {
                com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
                if (nt.getBeginToken() == nt.getEndToken() && nt.typ == com.pullenti.ner.NumberSpellingType.WORDS && !nt.getBeginToken().chars.isAllLower()) {
                }
                else 
                    return null;
            }
            else 
                return null;
        }
        PersonItemToken pit = tryAttach(t, attrs, null);
        if (pit == null && t.chars.isLatinLetter()) {
        }
        if (pit == null) 
            return null;
        java.util.ArrayList<PersonItemToken> res = new java.util.ArrayList<PersonItemToken>();
        res.add(pit);
        t = pit.getEndToken().getNext();
        if ((t != null && t.isChar('.') && pit.typ == ItemType.VALUE) && pit.getLengthChar() > 3) {
            String str = pit.getSourceText();
            if (Character.isUpperCase(str.charAt(0)) && Character.isUpperCase(str.charAt(str.length() - 1))) {
                boolean ok = true;
                for (int i = 1; i < (str.length() - 1); i++) {
                    if (!Character.isLowerCase(str.charAt(i))) 
                        ok = false;
                }
                if (ok) {
                    pit.value = pit.value.substring(0, 0 + pit.value.length() - 1);
                    pit.firstname = (pit.middlename = (pit.lastname = null));
                    PersonItemToken pit2 = _new2732(t, t, ItemType.INITIAL, str.substring(str.length() - 1));
                    res.add(pit2);
                    t = t.getNext();
                }
            }
        }
        boolean zap = false;
        for (; t != null; t = (t == null ? null : t.getNext())) {
            if (t.getWhitespacesBeforeCount() > 15) 
                break;
            com.pullenti.ner.Token tt = t;
            if (tt.isHiphen() && tt.getNext() != null) {
                if (!tt.isWhitespaceAfter() && !tt.isWhitespaceBefore()) 
                    tt = t.getNext();
                else if (tt.getPrevious().chars.equals(tt.getNext().chars) && !tt.isNewlineAfter()) 
                    tt = tt.getNext();
            }
            else if ((tt.isChar(',') && (tt.getWhitespacesAfterCount() < 2) && tt.getNext() != null) && res.size() == 1) {
                zap = true;
                tt = tt.getNext();
            }
            else if ((tt.isChar('(') && (tt.getNext() instanceof com.pullenti.ner.TextToken) && tt.getNext().chars.equals(tt.getPrevious().chars)) && tt.getNext().getNext() != null && tt.getNext().getNext().isChar(')')) {
                PersonItemToken pit0 = res.get(res.size() - 1);
                PersonItemToken pit11 = tryAttach(tt.getNext(), attrs, null);
                if (pit0.firstname != null && pit11 != null && pit11.firstname != null) {
                    com.pullenti.unisharp.Utils.addToArrayList(pit0.firstname.vars, pit11.firstname.vars);
                    tt = tt.getNext().getNext();
                    pit0.setEndToken(tt);
                    tt = tt.getNext();
                }
                else if (pit0.firstname == null && pit0.lastname != null && ((pit0.lastname.isInDictionary || pit0.lastname.isLastnameHasStdTail || pit0.lastname.isHasStdPostfix))) {
                    if (pit11 != null && pit11.lastname != null) {
                        boolean ok = false;
                        if ((pit11.lastname.isInDictionary || pit11.lastname.isLastnameHasStdTail || pit11.lastname.isHasStdPostfix)) 
                            ok = true;
                        else if (res.size() == 1) {
                            PersonItemToken pit22 = tryAttach(tt.getNext().getNext().getNext(), attrs, null);
                            if (pit22 != null) {
                                if (pit22.firstname != null) 
                                    ok = true;
                            }
                        }
                        if (ok) {
                            com.pullenti.unisharp.Utils.addToArrayList(pit0.lastname.vars, pit11.lastname.vars);
                            tt = tt.getNext().getNext();
                            pit0.setEndToken(tt);
                            tt = tt.getNext();
                        }
                    }
                }
            }
            PersonItemToken pit1 = tryAttach(tt, attrs, res);
            if (pit1 == null) 
                break;
            if (pit1.chars.isCyrillicLetter() != pit.chars.isCyrillicLetter()) {
                boolean ok = false;
                if (pit1.typ == ItemType.INITIAL) {
                    if (pit1.chars.isCyrillicLetter()) {
                        char v = com.pullenti.morph.LanguageHelper.getLatForCyr(pit1.value.charAt(0));
                        if (v != ((char)0)) {
                            pit1.value = (String.valueOf(v));
                            ok = true;
                            pit1.chars = com.pullenti.morph.CharsInfo._new2786(true);
                        }
                        else if (pit.typ == ItemType.INITIAL) {
                            v = com.pullenti.morph.LanguageHelper.getCyrForLat(pit.value.charAt(0));
                            if (v != ((char)0)) {
                                pit.value = (String.valueOf(v));
                                ok = true;
                                pit.chars = com.pullenti.morph.CharsInfo._new2751(true);
                                pit = pit1;
                            }
                        }
                    }
                    else {
                        char v = com.pullenti.morph.LanguageHelper.getCyrForLat(pit1.value.charAt(0));
                        if (v != ((char)0)) {
                            pit1.value = (String.valueOf(v));
                            ok = true;
                            pit1.chars = com.pullenti.morph.CharsInfo._new2751(true);
                        }
                        else if (pit.typ == ItemType.INITIAL) {
                            v = com.pullenti.morph.LanguageHelper.getLatForCyr(pit.value.charAt(0));
                            if (v != ((char)0)) {
                                pit.value = (String.valueOf(v));
                                ok = true;
                                pit.chars = com.pullenti.morph.CharsInfo._new2786(true);
                                pit = pit1;
                            }
                        }
                    }
                }
                else if (pit.typ == ItemType.INITIAL) {
                    if (pit.chars.isCyrillicLetter()) {
                        char v = com.pullenti.morph.LanguageHelper.getLatForCyr(pit.value.charAt(0));
                        if (v != ((char)0)) {
                            pit.value = (String.valueOf(v));
                            ok = true;
                        }
                        else if (pit1.typ == ItemType.INITIAL) {
                            v = com.pullenti.morph.LanguageHelper.getCyrForLat(pit1.value.charAt(0));
                            if (v != ((char)0)) {
                                pit1.value = (String.valueOf(v));
                                ok = true;
                                pit = pit1;
                            }
                        }
                    }
                    else {
                        char v = com.pullenti.morph.LanguageHelper.getCyrForLat(pit.value.charAt(0));
                        if (v != ((char)0)) {
                            pit.value = (String.valueOf(v));
                            ok = true;
                        }
                        else if (pit1.typ == ItemType.INITIAL) {
                            v = com.pullenti.morph.LanguageHelper.getLatForCyr(pit1.value.charAt(0));
                            if (v != ((char)0)) {
                                pit.value = (String.valueOf(v));
                                ok = true;
                                pit = pit1;
                            }
                        }
                    }
                }
                if (!ok) 
                    break;
            }
            if (pit1.typ == ItemType.VALUE || ((pit1.typ == ItemType.SUFFIX && pit1.isNewlineBefore()))) {
                if ((((attrs.value()) & (ParseAttr.IGNOREATTRS.value()))) == (ParseAttr.NO.value())) {
                    PersonAttrToken pat = PersonAttrToken.tryAttach(pit1.getBeginToken(), PersonAttrToken.PersonAttrAttachAttrs.NO);
                    if (pat != null) {
                        if (pit1.isNewlineBefore()) 
                            break;
                        if (pit1.lastname == null || !pit1.lastname.isLastnameHasStdTail) {
                            com.pullenti.morph.MorphClass ty = pit1.getBeginToken().getMorphClassInDictionary();
                            if (ty.isNoun()) {
                                if (pit1.getWhitespacesBeforeCount() > 1) 
                                    break;
                                if (pat.chars.isCapitalUpper() && pat.getBeginToken() == pat.getEndToken()) {
                                }
                                else 
                                    break;
                            }
                        }
                    }
                }
            }
            if (tt != t) {
                pit1.isHiphenBefore = true;
                res.get(res.size() - 1).isHiphenAfter = true;
            }
            res.add(pit1);
            t = pit1.getEndToken();
            if (res.size() > 15) 
                break;
            if (maxCount > 0 && res.size() >= maxCount) 
                break;
        }
        if (res.get(0).isAsianItem(false) && res.get(0).value.length() == 1) {
            if ((((attrs.value()) & (ParseAttr.MUSTBEITEMALWAYS.value()))) == (ParseAttr.NO.value())) {
                if (res.size() < 2) 
                    return null;
                if (!res.get(1).isAsianItem(false) || res.get(1).value.length() == 1) 
                    return null;
            }
        }
        if (zap && res.size() > 1) {
            boolean ok = false;
            if (res.get(0).lastname != null && res.size() == 3) {
                if (res.get(1).typ == ItemType.INITIAL || res.get(1).firstname != null) {
                    if (res.get(2).typ == ItemType.INITIAL || res.get(2).middlename != null) 
                        ok = true;
                }
            }
            else if ((((attrs.value()) & (ParseAttr.CANINITIALBEDIGIT.value()))) != (ParseAttr.NO.value()) && res.get(0).typ == ItemType.VALUE && res.get(1).typ == ItemType.INITIAL) {
                if (res.size() == 2) 
                    ok = true;
                else if (res.size() == 3 && res.get(2).typ == ItemType.INITIAL) 
                    ok = true;
                else if (res.size() == 3 && res.get(2).isInDictionary) 
                    ok = true;
            }
            if (!ok) 
                for(int jjj = 1 + res.size() - 1 - 1, mmm = 1; jjj >= mmm; jjj--) res.remove(jjj);
        }
        if (res.size() == 1 && res.get(0).isNewlineBefore() && res.get(0).isNewlineAfter()) {
            if (res.get(0).lastname != null && ((res.get(0).lastname.isHasStdPostfix || res.get(0).lastname.isInDictionary || res.get(0).lastname.isLastnameHasStdTail))) {
                java.util.ArrayList<PersonItemToken> res1 = tryAttachList(res.get(0).getEndToken().getNext(), ParseAttr.CANBELATIN, maxCount);
                if (res1 != null && res1.size() > 0) {
                    if (res1.size() == 2 && ((res1.get(0).firstname != null || res1.get(1).middlename != null)) && res1.get(1).isNewlineAfter()) 
                        com.pullenti.unisharp.Utils.addToArrayList(res, res1);
                    else if (res1.size() == 1 && res1.get(0).isNewlineAfter()) {
                        java.util.ArrayList<PersonItemToken> res2 = tryAttachList(res1.get(0).getEndToken().getNext(), ParseAttr.CANBELATIN, maxCount);
                        if (res2 != null && res2.size() == 1 && res2.get(0).isNewlineAfter()) {
                            if (res1.get(0).firstname != null || res2.get(0).middlename != null) {
                                res.add(res1.get(0));
                                res.add(res2.get(0));
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < res.size(); i++) {
            if (res.get(i).firstname != null && res.get(i).getBeginToken().isValue("СВЕТА", null)) {
                if (i > 0 && res.get(i - 1).lastname != null) {
                }
                else if (((i + 1) < res.size()) && ((res.get(i + 1).lastname != null || res.get(i + 1).middlename != null))) {
                }
                else 
                    continue;
                res.get(i).firstname.vars.get(0).value = "СВЕТЛАНА";
            }
            else if (res.get(i).typ == ItemType.VALUE && ((i + 1) < res.size()) && res.get(i + 1).typ == ItemType.SUFFIX) {
                res.get(i).addPostfixInfo(res.get(i + 1).value, com.pullenti.morph.MorphGender.UNDEFINED);
                res.get(i).setEndToken(res.get(i + 1).getEndToken());
                if (res.get(i).lastname == null) {
                    res.get(i).lastname = MorphPersonItem._new2730(true);
                    res.get(i).lastname.vars.add(new MorphPersonItemVariant(res.get(i).value, new com.pullenti.morph.MorphBaseInfo(), true));
                    res.get(i).firstname = null;
                }
                res.remove(i + 1);
            }
        }
        if (res.size() > 1 && res.get(0).isInDictionary && (((attrs.value()) & (((ParseAttr.MUSTBEITEMALWAYS.value()) | (ParseAttr.AFTERATTRIBUTE.value()))))) == (ParseAttr.NO.value())) {
            com.pullenti.morph.MorphClass mc = res.get(0).getBeginToken().getMorphClassInDictionary();
            if (mc.isPronoun() || mc.isPersonalPronoun()) {
                if (res.get(0).getBeginToken().isValue("ТОМ", null)) {
                }
                else 
                    return null;
            }
        }
        for (int i = 0; i < (res.size() - 1); i++) {
            if (res.get(i).typ == ItemType.VALUE && res.get(i + 1).typ == ItemType.VALUE && res.get(i).getEndToken().getNext().isHiphen()) {
                boolean ok = false;
                if (i > 0 && res.get(i - 1).typ == ItemType.INITIAL && (i + 2) == res.size()) 
                    ok = true;
                else if (i == 0 && ((i + 2) < res.size()) && res.get(i + 2).typ == ItemType.INITIAL) 
                    ok = true;
                if (!ok) 
                    continue;
                res.get(i).setEndToken(res.get(i + 1).getEndToken());
                res.get(i).value = (res.get(i).value + "-" + res.get(i + 1).value);
                res.get(i).firstname = (res.get(i).lastname = (res.get(i).middlename = null));
                res.get(i).isInDictionary = false;
                res.remove(i + 1);
                break;
            }
        }
        if (res.size() == 1 && res.get(0).getLengthChar() == 1) 
            return null;
        return res;
    }

    public static com.pullenti.ner.ReferentToken tryParsePerson(com.pullenti.ner.Token t, FioTemplateType prevPersTemplate) {
        if (t == null) 
            return null;
        if (t.getReferent() instanceof com.pullenti.ner.person.PersonReferent) {
            com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
            if (rt.getBeginToken() == rt.getEndToken()) {
                com.pullenti.ner.Token tt1 = t.getNext();
                if (tt1 != null && tt1.isComma()) 
                    tt1 = tt1.getNext();
                if (tt1 != null && (tt1.getWhitespacesBeforeCount() < 2)) {
                    java.util.ArrayList<PersonItemToken> pits0 = PersonItemToken.tryAttachList(tt1, ParseAttr.CANINITIALBEDIGIT, 10);
                    if (pits0 != null && pits0.get(0).typ == ItemType.INITIAL) {
                        String str = rt.referent.getStringValue(com.pullenti.ner.person.PersonReferent.ATTR_FIRSTNAME);
                        if (str != null && str.startsWith(pits0.get(0).value)) {
                            com.pullenti.ner.ReferentToken res = com.pullenti.ner.ReferentToken._new2791(rt.referent, t, pits0.get(0).getEndToken(), FioTemplateType.SURNAMEI.value());
                            if (pits0.size() > 1 && pits0.get(1).typ == ItemType.INITIAL) {
                                str = rt.referent.getStringValue(com.pullenti.ner.person.PersonReferent.ATTR_MIDDLENAME);
                                if (str != null && str.startsWith(pits0.get(1).value)) {
                                    res.setEndToken(pits0.get(1).getEndToken());
                                    res.miscAttrs = FioTemplateType.SURNAMEII.value();
                                }
                            }
                            return res;
                        }
                    }
                    if (((((tt1 instanceof com.pullenti.ner.TextToken) && tt1.getLengthChar() == 1 && tt1.chars.isAllUpper()) && tt1.chars.isCyrillicLetter() && (tt1.getNext() instanceof com.pullenti.ner.TextToken)) && (tt1.getWhitespacesAfterCount() < 2) && tt1.getNext().getLengthChar() == 1) && tt1.getNext().chars.isAllUpper() && tt1.getNext().chars.isCyrillicLetter()) {
                        String str = rt.referent.getStringValue(com.pullenti.ner.person.PersonReferent.ATTR_FIRSTNAME);
                        if (str != null && str.startsWith(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt1, com.pullenti.ner.TextToken.class)).term)) {
                            String str2 = rt.referent.getStringValue(com.pullenti.ner.person.PersonReferent.ATTR_MIDDLENAME);
                            if (str2 == null || str2.startsWith(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt1.getNext(), com.pullenti.ner.TextToken.class)).term)) {
                                com.pullenti.ner.ReferentToken res = com.pullenti.ner.ReferentToken._new2791(rt.referent, t, tt1.getNext(), FioTemplateType.NAMEISURNAME.value());
                                if (str2 == null) 
                                    rt.referent.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_MIDDLENAME, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt1.getNext(), com.pullenti.ner.TextToken.class)).term, false, 0);
                                if (res.getEndToken().getNext() != null && res.getEndToken().getNext().isChar('.')) 
                                    res.setEndToken(res.getEndToken().getNext());
                                return res;
                            }
                        }
                    }
                }
            }
            return rt;
        }
        if (t.getReferent() != null && com.pullenti.unisharp.Utils.stringsEq(t.getReferent().getTypeName(), "ORGANIZATION")) {
            com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
            com.pullenti.ner.ReferentToken ppp = tryParsePerson(rt.getBeginToken(), FioTemplateType.UNDEFINED);
            if (ppp != null && ppp.getEndChar() == rt.getEndChar()) {
                ppp.setBeginToken(ppp.setEndToken(rt));
                return ppp;
            }
        }
        java.util.ArrayList<PersonItemToken> pits = PersonItemToken.tryAttachList(t, ParseAttr.of((ParseAttr.CANINITIALBEDIGIT.value()) | (ParseAttr.CANBELATIN.value())), 10);
        if ((pits == null && (t instanceof com.pullenti.ner.TextToken) && t.chars.isAllLower()) && t.getLengthChar() > 3) {
            PersonItemToken pi = PersonItemToken.tryAttach(t, ParseAttr.of((ParseAttr.CANINITIALBEDIGIT.value()) | (ParseAttr.CANBELATIN.value()) | (ParseAttr.CANBELOWER.value())), null);
            if (pi != null && pi.lastname != null && ((pi.lastname.isInDictionary || pi.lastname.isLastnameHasStdTail))) {
                pits = PersonItemToken.tryAttachList(pi.getEndToken().getNext(), ParseAttr.of((ParseAttr.CANINITIALBEDIGIT.value()) | (ParseAttr.CANBELATIN.value())), 10);
                if (pits != null && pits.get(0).typ == ItemType.INITIAL && pits.get(0).chars.isLatinLetter() == pi.chars.isLatinLetter()) 
                    pits.add(0, pi);
                else 
                    pits = null;
            }
        }
        if (pits != null && prevPersTemplate != FioTemplateType.UNDEFINED && pits.get(0).typ == ItemType.VALUE) {
            com.pullenti.ner.Token tt1 = null;
            if (pits.size() == 1 && prevPersTemplate == FioTemplateType.SURNAMEI) 
                tt1 = pits.get(0).getEndToken().getNext();
            if (tt1 != null && tt1.isComma()) 
                tt1 = tt1.getNext();
            if (((tt1 instanceof com.pullenti.ner.TextToken) && tt1.chars.isLetter() && tt1.chars.isAllUpper()) && tt1.getLengthChar() == 1 && (tt1.getWhitespacesBeforeCount() < 2)) {
                PersonItemToken ii = _new2734(tt1, tt1, ItemType.INITIAL, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt1, com.pullenti.ner.TextToken.class)).term, tt1.chars);
                pits.add(ii);
            }
            if (pits.size() == 1 && pits.get(0).isNewlineAfter() && ((prevPersTemplate == FioTemplateType.SURNAMEI || prevPersTemplate == FioTemplateType.SURNAMEII))) {
                java.util.ArrayList<PersonItemToken> ppp = PersonItemToken.tryAttachList(pits.get(0).getEndToken().getNext(), ParseAttr.CANBELATIN, 10);
                if (ppp != null && ppp.get(0).typ == ItemType.INITIAL) {
                    pits.add(ppp.get(0));
                    if (ppp.size() > 1 && ppp.get(1).typ == ItemType.INITIAL) 
                        pits.add(ppp.get(1));
                }
            }
        }
        if (pits != null && pits.size() > 1) {
            FioTemplateType tmpls = FioTemplateType.UNDEFINED;
            PersonItemToken first = null;
            PersonItemToken middl = null;
            PersonItemToken last = null;
            if (pits.get(0).typ == ItemType.VALUE && pits.get(1).typ == ItemType.INITIAL) {
                if (((t.isValue("ГЛАВА", null) || t.isValue("СТАТЬЯ", "СТАТТЯ") || t.isValue("РАЗДЕЛ", "РОЗДІЛ")) || t.isValue("ПОДРАЗДЕЛ", "ПІДРОЗДІЛ") || t.isValue("ЧАСТЬ", "ЧАСТИНА")) || t.isValue("ПРИЛОЖЕНИЕ", "ДОКЛАДАННЯ")) 
                    return null;
                if (((t.isValue("CHAPTER", null) || t.isValue("CLAUSE", null) || t.isValue("SECTION", null)) || t.isValue("SUBSECTION", null) || t.isValue("PART", null)) || t.isValue("LIST", null) || t.isValue("APPENDIX", null)) 
                    return null;
                first = pits.get(1);
                last = pits.get(0);
                tmpls = FioTemplateType.SURNAMEI;
                if (pits.size() > 2 && pits.get(2).typ == ItemType.INITIAL) {
                    middl = pits.get(2);
                    tmpls = FioTemplateType.SURNAMEII;
                }
            }
            else if (pits.get(0).typ == ItemType.INITIAL && pits.get(1).typ == ItemType.VALUE) {
                first = pits.get(0);
                last = pits.get(1);
                tmpls = FioTemplateType.ISURNAME;
            }
            else if ((pits.size() > 2 && pits.get(0).typ == ItemType.INITIAL && pits.get(1).typ == ItemType.INITIAL) && pits.get(2).typ == ItemType.VALUE) {
                first = pits.get(0);
                middl = pits.get(1);
                last = pits.get(2);
                tmpls = FioTemplateType.IISURNAME;
            }
            if (pits.size() == 2 && pits.get(0).typ == ItemType.VALUE && pits.get(1).typ == ItemType.VALUE) {
                if (pits.get(0).chars.isLatinLetter() && ((!pits.get(0).isInDictionary || !pits.get(1).isInDictionary))) {
                    if (!com.pullenti.ner.core.MiscHelper.isEngArticle(pits.get(0).getBeginToken())) {
                        first = pits.get(0);
                        last = pits.get(1);
                        tmpls = FioTemplateType.NAMESURNAME;
                    }
                }
            }
            if (last != null) {
                com.pullenti.ner.person.PersonReferent pers = new com.pullenti.ner.person.PersonReferent();
                pers.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_LASTNAME, last.value, false, 0);
                pers.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_FIRSTNAME, first.value, false, 0);
                if (middl != null) 
                    pers.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_MIDDLENAME, middl.value, false, 0);
                com.pullenti.ner.ReferentToken res = new com.pullenti.ner.ReferentToken(pers, t, last.getEndToken(), null);
                if (first.getEndChar() > last.getEndChar()) 
                    res.setEndToken(first.getEndToken());
                if (middl != null && middl.getEndChar() > res.getEndChar()) 
                    res.setEndToken(middl.getEndToken());
                res.data = t.kit.getAnalyzerDataByAnalyzerName(com.pullenti.ner.person.PersonAnalyzer.ANALYZER_NAME);
                res.miscAttrs = tmpls.value();
                if ((res.getEndToken().getWhitespacesAfterCount() < 2) && (res.getEndToken().getNext() instanceof com.pullenti.ner.NumberToken)) {
                    com.pullenti.ner.NumberToken num = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(res.getEndToken().getNext(), com.pullenti.ner.NumberToken.class);
                    if (com.pullenti.unisharp.Utils.stringsEq(num.getValue(), "2") || com.pullenti.unisharp.Utils.stringsEq(num.getValue(), "3")) {
                        if (num.getMorph()._getClass().isAdjective()) {
                            pers.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_NICKNAME, num.getValue().toString(), false, 0);
                            res.setEndToken(res.getEndToken().getNext());
                        }
                    }
                }
                return res;
            }
        }
        if (pits != null && pits.size() == 1 && pits.get(0).typ == ItemType.VALUE) {
            com.pullenti.ner.Token tt = pits.get(0).getEndToken().getNext();
            boolean comma = false;
            if (tt != null && ((tt.isComma() || tt.isChar('.')))) {
                tt = tt.getNext();
                comma = true;
            }
            if (((tt instanceof com.pullenti.ner.TextToken) && tt.getLengthChar() == 2 && tt.chars.isAllUpper()) && tt.chars.isCyrillicLetter()) {
                com.pullenti.ner.person.PersonReferent pers = new com.pullenti.ner.person.PersonReferent();
                pers.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_LASTNAME, pits.get(0).value, false, 0);
                pers.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_FIRSTNAME, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term.charAt(0), false, 0);
                pers.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_MIDDLENAME, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term.charAt(1), false, 0);
                com.pullenti.ner.ReferentToken res = com.pullenti.ner.ReferentToken._new2791(pers, t, tt, FioTemplateType.SURNAMEII.value());
                if (tt.getNext() != null && tt.getNext().isChar('.')) 
                    res.setEndToken((tt = tt.getNext()));
                res.data = t.kit.getAnalyzerDataByAnalyzerName(com.pullenti.ner.person.PersonAnalyzer.ANALYZER_NAME);
                return res;
            }
            if ((((((tt instanceof com.pullenti.ner.TextToken) && (tt.getWhitespacesBeforeCount() < 2) && tt.getLengthChar() == 1) && tt.chars.isAllUpper() && tt.chars.isCyrillicLetter()) && (tt.getNext() instanceof com.pullenti.ner.TextToken) && (tt.getWhitespacesAfterCount() < 2)) && tt.getNext().getLengthChar() == 1 && tt.getNext().chars.isAllUpper()) && tt.getNext().chars.isCyrillicLetter()) {
                com.pullenti.ner.person.PersonReferent pers = new com.pullenti.ner.person.PersonReferent();
                pers.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_LASTNAME, pits.get(0).value, false, 0);
                pers.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_FIRSTNAME, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term, false, 0);
                pers.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_MIDDLENAME, ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt.getNext(), com.pullenti.ner.TextToken.class)).term, false, 0);
                com.pullenti.ner.ReferentToken res = com.pullenti.ner.ReferentToken._new2791(pers, t, tt.getNext(), FioTemplateType.SURNAMEII.value());
                if (tt.getNext().getNext() != null && tt.getNext().getNext().isChar('.')) 
                    res.setEndToken(tt.getNext().getNext());
                res.data = t.kit.getAnalyzerDataByAnalyzerName(com.pullenti.ner.person.PersonAnalyzer.ANALYZER_NAME);
                return res;
            }
            if (comma && tt != null && (tt.getWhitespacesBeforeCount() < 2)) {
                java.util.ArrayList<PersonItemToken> pits1 = PersonItemToken.tryAttachList(tt, ParseAttr.of((ParseAttr.CANINITIALBEDIGIT.value()) | (ParseAttr.CANBELATIN.value())), 10);
                if (pits1 != null && pits1.size() > 0 && pits1.get(0).typ == ItemType.INITIAL) {
                    if (prevPersTemplate != FioTemplateType.UNDEFINED) {
                        if (prevPersTemplate != FioTemplateType.SURNAMEI && prevPersTemplate != FioTemplateType.SURNAMEII) 
                            return null;
                    }
                    com.pullenti.ner.person.PersonReferent pers = new com.pullenti.ner.person.PersonReferent();
                    pers.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_LASTNAME, pits.get(0).value, false, 0);
                    String nam = pits1.get(0).value;
                    if (pits1.get(0).chars.isCyrillicLetter() != pits.get(0).chars.isCyrillicLetter()) {
                        char ch;
                        if (pits.get(0).chars.isCyrillicLetter()) 
                            ch = com.pullenti.morph.LanguageHelper.getCyrForLat(nam.charAt(0));
                        else 
                            ch = com.pullenti.morph.LanguageHelper.getLatForCyr(nam.charAt(0));
                        if (ch != ((char)0)) 
                            nam = (String.valueOf(ch));
                    }
                    pers.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_FIRSTNAME, nam, false, 0);
                    com.pullenti.ner.ReferentToken res = com.pullenti.ner.ReferentToken._new2791(pers, t, pits1.get(0).getEndToken(), FioTemplateType.SURNAMEI.value());
                    if (pits1.size() > 1 && pits1.get(1).typ == ItemType.INITIAL) {
                        String mid = pits1.get(1).value;
                        if (pits1.get(1).chars.isCyrillicLetter() != pits.get(0).chars.isCyrillicLetter()) {
                            char ch;
                            if (pits.get(0).chars.isCyrillicLetter()) 
                                ch = com.pullenti.morph.LanguageHelper.getCyrForLat(mid.charAt(0));
                            else 
                                ch = com.pullenti.morph.LanguageHelper.getLatForCyr(mid.charAt(0));
                            if (ch != ((char)0)) 
                                mid = (String.valueOf(ch));
                        }
                        pers.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_MIDDLENAME, mid, false, 0);
                        res.setEndToken(pits1.get(1).getEndToken());
                        res.miscAttrs = FioTemplateType.SURNAMEII.value();
                    }
                    res.data = t.kit.getAnalyzerDataByAnalyzerName(com.pullenti.ner.person.PersonAnalyzer.ANALYZER_NAME);
                    return res;
                }
            }
        }
        return null;
    }

    public static PersonItemToken _new2732(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, String _arg4) {
        PersonItemToken res = new PersonItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        return res;
    }

    public static PersonItemToken _new2734(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, String _arg4, com.pullenti.morph.CharsInfo _arg5) {
        PersonItemToken res = new PersonItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        res.chars = _arg5;
        return res;
    }

    public static PersonItemToken _new2740(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, com.pullenti.ner.person.PersonReferent _arg4, com.pullenti.ner.MorphCollection _arg5) {
        PersonItemToken res = new PersonItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.referent = _arg4;
        res.setMorph(_arg5);
        return res;
    }

    public static PersonItemToken _new2748(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, com.pullenti.morph.CharsInfo _arg4) {
        PersonItemToken res = new PersonItemToken(_arg1, _arg2);
        res.value = _arg3;
        res.chars = _arg4;
        return res;
    }

    public static PersonItemToken _new2763(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3) {
        PersonItemToken res = new PersonItemToken(_arg1, _arg2);
        res.value = _arg3;
        return res;
    }

    public static PersonItemToken _new2764(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, ItemType _arg4) {
        PersonItemToken res = new PersonItemToken(_arg1, _arg2);
        res.value = _arg3;
        res.typ = _arg4;
        return res;
    }

    public static PersonItemToken _new2765(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, com.pullenti.morph.CharsInfo _arg4, com.pullenti.ner.MorphCollection _arg5) {
        PersonItemToken res = new PersonItemToken(_arg1, _arg2);
        res.value = _arg3;
        res.chars = _arg4;
        res.setMorph(_arg5);
        return res;
    }

    public static class ItemType implements Comparable<ItemType> {
    
        public static final ItemType VALUE; // 0
    
        public static final ItemType INITIAL; // 1
    
        public static final ItemType REFERENT; // 2
    
        public static final ItemType SUFFIX; // 3
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private ItemType(int val, String str) { m_val = val; m_str = str; }
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
        public int compareTo(ItemType v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, ItemType> mapIntToEnum; 
        private static java.util.HashMap<String, ItemType> mapStringToEnum; 
        private static ItemType[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static ItemType of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            ItemType item = new ItemType(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static ItemType of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static ItemType[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, ItemType>();
            mapStringToEnum = new java.util.HashMap<String, ItemType>();
            VALUE = new ItemType(0, "VALUE");
            mapIntToEnum.put(VALUE.value(), VALUE);
            mapStringToEnum.put(VALUE.m_str.toUpperCase(), VALUE);
            INITIAL = new ItemType(1, "INITIAL");
            mapIntToEnum.put(INITIAL.value(), INITIAL);
            mapStringToEnum.put(INITIAL.m_str.toUpperCase(), INITIAL);
            REFERENT = new ItemType(2, "REFERENT");
            mapIntToEnum.put(REFERENT.value(), REFERENT);
            mapStringToEnum.put(REFERENT.m_str.toUpperCase(), REFERENT);
            SUFFIX = new ItemType(3, "SUFFIX");
            mapIntToEnum.put(SUFFIX.value(), SUFFIX);
            mapStringToEnum.put(SUFFIX.m_str.toUpperCase(), SUFFIX);
            java.util.Collection<ItemType> col = mapIntToEnum.values();
            m_Values = new ItemType[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }


    public static class ParseAttr implements Comparable<ParseAttr> {
    
        public static final ParseAttr NO; // 0
    
        public static final ParseAttr ALTVAR; // 1
    
        public static final ParseAttr CANBELATIN; // 2
    
        public static final ParseAttr CANINITIALBEDIGIT; // 4
    
        public static final ParseAttr CANBELOWER; // 8
    
        public static final ParseAttr MUSTBEITEMALWAYS; // 0x10
    
        public static final ParseAttr IGNOREATTRS; // 0x20
    
        public static final ParseAttr NOMINATIVECASE; // 0x40
    
        public static final ParseAttr SURNAMEPREFIXNOTMERGE; // 0x80
    
        public static final ParseAttr AFTERATTRIBUTE; // 0x100
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private ParseAttr(int val, String str) { m_val = val; m_str = str; }
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
        public int compareTo(ParseAttr v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, ParseAttr> mapIntToEnum; 
        private static java.util.HashMap<String, ParseAttr> mapStringToEnum; 
        private static ParseAttr[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static ParseAttr of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            ParseAttr item = new ParseAttr(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static ParseAttr of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static ParseAttr[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, ParseAttr>();
            mapStringToEnum = new java.util.HashMap<String, ParseAttr>();
            NO = new ParseAttr(0, "NO");
            mapIntToEnum.put(NO.value(), NO);
            mapStringToEnum.put(NO.m_str.toUpperCase(), NO);
            ALTVAR = new ParseAttr(1, "ALTVAR");
            mapIntToEnum.put(ALTVAR.value(), ALTVAR);
            mapStringToEnum.put(ALTVAR.m_str.toUpperCase(), ALTVAR);
            CANBELATIN = new ParseAttr(2, "CANBELATIN");
            mapIntToEnum.put(CANBELATIN.value(), CANBELATIN);
            mapStringToEnum.put(CANBELATIN.m_str.toUpperCase(), CANBELATIN);
            CANINITIALBEDIGIT = new ParseAttr(4, "CANINITIALBEDIGIT");
            mapIntToEnum.put(CANINITIALBEDIGIT.value(), CANINITIALBEDIGIT);
            mapStringToEnum.put(CANINITIALBEDIGIT.m_str.toUpperCase(), CANINITIALBEDIGIT);
            CANBELOWER = new ParseAttr(8, "CANBELOWER");
            mapIntToEnum.put(CANBELOWER.value(), CANBELOWER);
            mapStringToEnum.put(CANBELOWER.m_str.toUpperCase(), CANBELOWER);
            MUSTBEITEMALWAYS = new ParseAttr(0x10, "MUSTBEITEMALWAYS");
            mapIntToEnum.put(MUSTBEITEMALWAYS.value(), MUSTBEITEMALWAYS);
            mapStringToEnum.put(MUSTBEITEMALWAYS.m_str.toUpperCase(), MUSTBEITEMALWAYS);
            IGNOREATTRS = new ParseAttr(0x20, "IGNOREATTRS");
            mapIntToEnum.put(IGNOREATTRS.value(), IGNOREATTRS);
            mapStringToEnum.put(IGNOREATTRS.m_str.toUpperCase(), IGNOREATTRS);
            NOMINATIVECASE = new ParseAttr(0x40, "NOMINATIVECASE");
            mapIntToEnum.put(NOMINATIVECASE.value(), NOMINATIVECASE);
            mapStringToEnum.put(NOMINATIVECASE.m_str.toUpperCase(), NOMINATIVECASE);
            SURNAMEPREFIXNOTMERGE = new ParseAttr(0x80, "SURNAMEPREFIXNOTMERGE");
            mapIntToEnum.put(SURNAMEPREFIXNOTMERGE.value(), SURNAMEPREFIXNOTMERGE);
            mapStringToEnum.put(SURNAMEPREFIXNOTMERGE.m_str.toUpperCase(), SURNAMEPREFIXNOTMERGE);
            AFTERATTRIBUTE = new ParseAttr(0x100, "AFTERATTRIBUTE");
            mapIntToEnum.put(AFTERATTRIBUTE.value(), AFTERATTRIBUTE);
            mapStringToEnum.put(AFTERATTRIBUTE.m_str.toUpperCase(), AFTERATTRIBUTE);
            java.util.Collection<ParseAttr> col = mapIntToEnum.values();
            m_Values = new ParseAttr[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }


    public PersonItemToken() {
        super();
        if(_globalInstance == null) return;
        typ = ItemType.VALUE;
    }
    public static PersonItemToken _globalInstance;
    
    static {
        try { _globalInstance = new PersonItemToken(); } 
        catch(Exception e) { }
        M_SURPREFIXES = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"АБД", "АБУ", "АБУЛЬ", "АБДУ", "АБДЕЛЬ", "УММ", "АЛ", "АЛЬ", "АН", "АТ", "АР", "АС", "АД", "БИН", "БЕН", "ИБН", "УЛЬД", "БИНТ", "ФОН", "ВАН", "ДЕ", "ДИ", "ДА", "ЛА", "ЛЕ", "ЛЯ", "ЭЛЬ", "УЛЬ"}));
        m_SurPrefixesLat = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"ABD", "AL", "BEN", "IBN", "VON", "VAN", "DE", "DI", "LA", "LE", "DA", "DE"}));
        M_ARABPOSTFIX = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"АГА", "АЛИ", "АР", "АС", "АШ", "БЕЙ", "БЕК", "ЗАДЕ", "ОГЛЫ", "ОГЛИ", "УГЛИ", "ОЛЬ", "ООЛ", "ПАША", "БАША", "УЛЬ", "УЛЫ", "УУЛУ", "ХАН", "ХАДЖИ", "ШАХ", "ЭД", "ЭЛЬ"}));
        M_ARABPOSTFIXFEM = new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"АСУ", "АЗУ", "ГЫЗЫ", "ЗУЛЬ", "КЫЗЫ", "КЫС", "КЗЫ"}));
    }
}
