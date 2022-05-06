/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.denomination;

/**
 * Сущность, моделирующая буквенно-цифровые комбинации (например, Си++, СС-300)
 * 
 */
public class DenominationReferent extends com.pullenti.ner.Referent {

    public DenominationReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.denomination.internal.MetaDenom.globalMeta);
    }

    /**
     * Имя типа сущности TypeName ("DENOMINATION")
     */
    public static final String OBJ_TYPENAME = "DENOMINATION";

    /**
     * Имя атрибута - значение
     */
    public static final String ATTR_VALUE = "VALUE";

    public String getValue() {
        return this.getStringValue(ATTR_VALUE);
    }


    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        return (String)com.pullenti.unisharp.Utils.notnull(getValue(), "?");
    }

    public void addValue(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        StringBuilder tmp = new StringBuilder();
        for (com.pullenti.ner.Token t = begin; t != null && t.getPrevious() != end; t = t.getNext()) {
            if (t instanceof com.pullenti.ner.NumberToken) {
                tmp.append(t.getSourceText());
                continue;
            }
            if (t instanceof com.pullenti.ner.TextToken) {
                String s = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term;
                if (t.isCharOf("-\\/")) 
                    s = "-";
                tmp.append(s);
            }
        }
        for (int i = 0; i < tmp.length(); i++) {
            if (tmp.charAt(i) == '-' && i > 0 && ((i + 1) < tmp.length())) {
                char ch0 = tmp.charAt(i - 1);
                char ch1 = tmp.charAt(i + 1);
                if (Character.isLetterOrDigit(ch0) && Character.isLetterOrDigit(ch1)) {
                    if (Character.isDigit(ch0) && !Character.isDigit(ch1)) 
                        tmp.delete(i, i + 1);
                    else if (!Character.isDigit(ch0) && Character.isDigit(ch1)) 
                        tmp.delete(i, i + 1);
                }
            }
        }
        this.addSlot(ATTR_VALUE, tmp.toString(), false, 0);
        m_Names = null;
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType typ) {
        DenominationReferent dr = (DenominationReferent)com.pullenti.unisharp.Utils.cast(obj, DenominationReferent.class);
        if (dr == null) 
            return false;
        for (String n : getNameVars()) {
            if (dr.getNameVars().contains(n)) 
                return true;
        }
        return false;
    }

    private java.util.ArrayList<String> m_Names;

    private java.util.ArrayList<String> getNameVars() {
        if (m_Names != null) 
            return m_Names;
        m_Names = new java.util.ArrayList<String>();
        String nam = getValue();
        if (nam == null) 
            return m_Names;
        m_Names.add(nam);
        java.util.ArrayList<java.util.ArrayList<String>> items = new java.util.ArrayList<java.util.ArrayList<String>>();
        int i;
        int ty0 = 0;
        int i0 = 0;
        for (i = 0; i <= nam.length(); i++) {
            int ty = 0;
            if (i < nam.length()) {
                if (Character.isDigit(nam.charAt(i))) 
                    ty = 1;
                else if (Character.isLetter(nam.charAt(i))) 
                    ty = 2;
                else 
                    ty = 3;
            }
            if (ty != ty0 || ty == 3) {
                if (i > i0) {
                    java.util.ArrayList<String> vars = new java.util.ArrayList<String>();
                    String p = nam.substring(i0, i0 + i - i0);
                    addVars(p, vars);
                    items.add(vars);
                    if (ty == 1 && ty0 == 2) {
                        vars = new java.util.ArrayList<String>();
                        vars.add("");
                        vars.add("-");
                        items.add(vars);
                    }
                }
                i0 = i;
                ty0 = ty;
            }
        }
        int[] inds = new int[items.size()];
        for (i = 0; i < inds.length; i++) {
            inds[i] = 0;
        }
        StringBuilder tmp = new StringBuilder();
        while (true) {
            tmp.setLength(0);
            for (i = 0; i < items.size(); i++) {
                tmp.append(items.get(i).get(inds[i]));
            }
            String v = tmp.toString();
            if (!m_Names.contains(v)) 
                m_Names.add(v);
            if (m_Names.size() > 20) 
                break;
            for (i = inds.length - 1; i >= 0; i--) {
                inds[i]++;
                if (inds[i] < items.get(i).size()) 
                    break;
            }
            if (i < 0) 
                break;
            for (++i; i < inds.length; i++) {
                inds[i] = 0;
            }
        }
        return m_Names;
    }


    private static void addVars(String str, java.util.ArrayList<String> vars) {
        vars.add(str);
        for (int k = 0; k < 2; k++) {
            int i;
            StringBuilder tmp = new StringBuilder();
            for (i = 0; i < str.length(); i++) {
                String v;
                com.pullenti.unisharp.Outargwrapper<String> wrapv1196 = new com.pullenti.unisharp.Outargwrapper<String>();
                boolean inoutres1197 = com.pullenti.unisharp.Utils.tryGetValue(m_VarChars, str.charAt(i), wrapv1196);
                v = wrapv1196.value;
                if (!inoutres1197) 
                    break;
                if ((v.length() < 2) || v.charAt(k) == '-') 
                    break;
                tmp.append(v.charAt(k));
            }
            if (i >= str.length()) {
                String v = tmp.toString();
                if (!vars.contains(v)) 
                    vars.add(v);
            }
        }
    }

    private static java.util.HashMap<Character, String> m_VarChars;


    @Override
    public com.pullenti.ner.core.IntOntologyItem createOntologyItem() {
        com.pullenti.ner.core.IntOntologyItem oi = new com.pullenti.ner.core.IntOntologyItem(this);
        for (String v : getNameVars()) {
            oi.termins.add(new com.pullenti.ner.core.Termin(v, null, false));
        }
        return oi;
    }
    
    static {
        m_VarChars = new java.util.HashMap<Character, String>();
        m_VarChars.put('A', "АА");
        m_VarChars.put('B', "БВ");
        m_VarChars.put('C', "ЦС");
        m_VarChars.put('D', "ДД");
        m_VarChars.put('E', "ЕЕ");
        m_VarChars.put('F', "Ф-");
        m_VarChars.put('G', "Г-");
        m_VarChars.put('H', "ХН");
        m_VarChars.put('I', "И-");
        m_VarChars.put('J', "Ж-");
        m_VarChars.put('K', "КК");
        m_VarChars.put('L', "Л-");
        m_VarChars.put('M', "ММ");
        m_VarChars.put('N', "Н-");
        m_VarChars.put('O', "ОО");
        m_VarChars.put('P', "ПР");
        m_VarChars.put('Q', "--");
        m_VarChars.put('R', "Р-");
        m_VarChars.put('S', "С-");
        m_VarChars.put('T', "ТТ");
        m_VarChars.put('U', "У-");
        m_VarChars.put('V', "В-");
        m_VarChars.put('W', "В-");
        m_VarChars.put('X', "ХХ");
        m_VarChars.put('Y', "УУ");
        m_VarChars.put('Z', "З-");
        m_VarChars.put('А', "AA");
        m_VarChars.put('Б', "B-");
        m_VarChars.put('В', "VB");
        m_VarChars.put('Г', "G-");
        m_VarChars.put('Д', "D-");
        m_VarChars.put('Е', "EE");
        m_VarChars.put('Ж', "J-");
        m_VarChars.put('З', "Z-");
        m_VarChars.put('И', "I-");
        m_VarChars.put('Й', "Y-");
        m_VarChars.put('К', "KK");
        m_VarChars.put('Л', "L-");
        m_VarChars.put('М', "MM");
        m_VarChars.put('Н', "NH");
        m_VarChars.put('О', "OO");
        m_VarChars.put('П', "P-");
        m_VarChars.put('Р', "RP");
        m_VarChars.put('С', "SC");
        m_VarChars.put('Т', "TT");
        m_VarChars.put('У', "UY");
        m_VarChars.put('Ф', "F-");
        m_VarChars.put('Х', "HX");
        m_VarChars.put('Ц', "C-");
        m_VarChars.put('Ч', "--");
        m_VarChars.put('Ш', "--");
        m_VarChars.put('Щ', "--");
        m_VarChars.put('Ы', "--");
        m_VarChars.put('Э', "A-");
        m_VarChars.put('Ю', "U-");
        m_VarChars.put('Я', "--");
    }
}
