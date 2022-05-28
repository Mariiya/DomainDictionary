/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.person.internal;

public class PersonMorphCollection {

    public String head;

    public static class PersonMorphVariant {
    
        public String value;
    
        public String shortValue;
    
        public com.pullenti.morph.MorphGender gender = com.pullenti.morph.MorphGender.UNDEFINED;
    
        @Override
        public String toString() {
            StringBuilder res = new StringBuilder();
            res.append(value);
            if (shortValue != null) 
                res.append(" (").append(shortValue).append(")");
            if (gender != com.pullenti.morph.MorphGender.UNDEFINED) 
                res.append(" ").append(gender.toString());
            return res.toString();
        }
    
        public static PersonMorphVariant _new2694(String _arg1) {
            PersonMorphVariant res = new PersonMorphVariant();
            res.value = _arg1;
            return res;
        }
    
        public static PersonMorphVariant _new2797(String _arg1, com.pullenti.morph.MorphGender _arg2, String _arg3) {
            PersonMorphVariant res = new PersonMorphVariant();
            res.value = _arg1;
            res.gender = _arg2;
            res.shortValue = _arg3;
            return res;
        }
        public PersonMorphVariant() {
        }
    }


    public java.util.ArrayList<PersonMorphVariant> items = new java.util.ArrayList<PersonMorphVariant>();

    public int number;

    public boolean checkLatinVariant(String latin) {
        for (PersonMorphVariant it : items) {
            if (com.pullenti.ner.core.MiscHelper.canBeEqualCyrAndLatSS(latin, it.value)) 
                return true;
        }
        return false;
    }

    public void correct() {
        for (PersonMorphVariant it : items) {
            if (it.value.indexOf(' ') > 0) 
                it.value = it.value.replace(" ", "");
        }
        for (int i = 0; i < (items.size() - 1); i++) {
            for (int k = 0; k < (items.size() - 1); k++) {
                if (m_Comparer.compare(items.get(k), items.get(k + 1)) > 0) {
                    PersonMorphVariant it = items.get(k + 1);
                    com.pullenti.unisharp.Utils.putArrayValue(items, k + 1, items.get(k));
                    com.pullenti.unisharp.Utils.putArrayValue(items, k, it);
                }
            }
        }
    }

    public static class SortComparer implements java.util.Comparator<com.pullenti.ner.person.internal.PersonMorphCollection.PersonMorphVariant> {
    
        @Override
        public int compare(com.pullenti.ner.person.internal.PersonMorphCollection.PersonMorphVariant x, com.pullenti.ner.person.internal.PersonMorphCollection.PersonMorphVariant y) {
            if (x.value.indexOf('-') > 0) {
                if ((y.value.indexOf('-') < 0) && (y.value.length() < (x.value.length() - 1))) 
                    return -1;
            }
            else if (y.value.indexOf('-') > 0 && (y.value.length() - 1) > x.value.length()) 
                return 1;
            if (x.value.length() < y.value.length()) 
                return -1;
            if (x.value.length() > y.value.length()) 
                return 1;
            return 0;
        }
        public SortComparer() {
        }
    }


    private static SortComparer m_Comparer;

    public boolean getHasLastnameStandardTail() {
        for (PersonMorphVariant it : items) {
            if (PersonItemToken.MorphPersonItem.endsWithStdSurname(it.value)) 
                return true;
        }
        return false;
    }


    public void add(String val, String shortval, com.pullenti.morph.MorphGender gen, boolean addOtherGenderVar) {
        if (val == null) 
            return;
        if (head == null) {
            if (val.length() > 3) 
                head = val.substring(0, 0 + 3);
            else 
                head = val;
        }
        if (gen == com.pullenti.morph.MorphGender.MASCULINE || gen == com.pullenti.morph.MorphGender.FEMINIE) {
            for (PersonMorphVariant it : items) {
                if (com.pullenti.unisharp.Utils.stringsEq(it.value, val) && it.gender == gen) 
                    return;
            }
            items.add(PersonMorphVariant._new2797(val, gen, shortval));
            if (addOtherGenderVar) {
                com.pullenti.morph.MorphGender g0 = (gen == com.pullenti.morph.MorphGender.FEMINIE ? com.pullenti.morph.MorphGender.MASCULINE : com.pullenti.morph.MorphGender.FEMINIE);
                String v = com.pullenti.morph.MorphologyService.getWordform(val, com.pullenti.morph.MorphBaseInfo._new171(com.pullenti.morph.MorphClass._new2778(true), g0));
                if (v != null) 
                    items.add(PersonMorphVariant._new2797(v, g0, shortval));
            }
        }
        else {
            this.add(val, shortval, com.pullenti.morph.MorphGender.MASCULINE, false);
            this.add(val, shortval, com.pullenti.morph.MorphGender.FEMINIE, false);
        }
    }

    public boolean remove(String val, com.pullenti.morph.MorphGender gen) {
        boolean ret = false;
        for (int i = items.size() - 1; i >= 0; i--) {
            if (val != null && com.pullenti.unisharp.Utils.stringsNe(items.get(i).value, val)) 
                continue;
            if (gen != com.pullenti.morph.MorphGender.UNDEFINED && items.get(i).gender != gen) 
                continue;
            items.remove(i);
            ret = true;
        }
        return ret;
    }

    public void addPrefixStr(String prefix) {
        head = (prefix + head);
        for (PersonMorphVariant it : items) {
            it.value = (prefix + it.value);
            if (it.shortValue != null) 
                it.value = (prefix + it.shortValue);
        }
    }

    public static PersonMorphCollection addPrefix(PersonMorphCollection prefix, PersonMorphCollection body) {
        PersonMorphCollection res = new PersonMorphCollection();
        res.head = (prefix.head + "-" + body.head);
        for (PersonMorphVariant pv : prefix.items) {
            for (PersonMorphVariant bv : body.items) {
                com.pullenti.morph.MorphGender g = bv.gender;
                if (g == com.pullenti.morph.MorphGender.UNDEFINED) 
                    g = pv.gender;
                else if (pv.gender != com.pullenti.morph.MorphGender.UNDEFINED && pv.gender != g) 
                    g = com.pullenti.morph.MorphGender.UNDEFINED;
                res.add((pv.value + "-" + bv.value), null, g, false);
            }
        }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (number > 0) 
            res.append("Num=").append(number).append(";");
        for (PersonMorphVariant it : items) {
            res.append(it.toString()).append("; ");
        }
        return res.toString();
    }

    public java.util.ArrayList<String> getValues() {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        for (PersonMorphVariant it : items) {
            if (!res.contains(it.value)) 
                res.add(it.value);
            if (it.shortValue != null && !res.contains(it.shortValue)) 
                res.add(it.shortValue);
        }
        return res;
    }


    public com.pullenti.morph.MorphGender getGender() {
        com.pullenti.morph.MorphGender res = com.pullenti.morph.MorphGender.UNDEFINED;
        for (PersonMorphVariant it : items) {
            res = com.pullenti.morph.MorphGender.of((short)((res.value()) | (it.gender.value())));
        }
        if (res == com.pullenti.morph.MorphGender.FEMINIE || res == com.pullenti.morph.MorphGender.MASCULINE) 
            return res;
        else 
            return com.pullenti.morph.MorphGender.UNDEFINED;
    }


    private boolean containsItem(String v, com.pullenti.morph.MorphGender g) {
        for (PersonMorphVariant it : items) {
            if (com.pullenti.unisharp.Utils.stringsEq(it.value, v) && it.gender == g) 
                return true;
        }
        return false;
    }

    public static boolean isEquals(PersonMorphCollection col1, PersonMorphCollection col2) {
        if (com.pullenti.unisharp.Utils.stringsNe(col1.head, col2.head)) 
            return false;
        for (PersonMorphVariant v : col1.items) {
            if (!col2.containsItem(v.value, v.gender)) 
                return false;
        }
        for (PersonMorphVariant v : col2.items) {
            if (!col1.containsItem(v.value, v.gender)) 
                return false;
        }
        return true;
    }

    private static boolean intersect2(PersonMorphCollection col1, PersonMorphCollection col2) {
        if (com.pullenti.unisharp.Utils.stringsNe(col1.head, col2.head)) 
            return false;
        boolean ret = false;
        java.util.ArrayList<String> vals1 = col1.getValues();
        java.util.ArrayList<String> vals2 = col2.getValues();
        java.util.ArrayList<String> uni = new java.util.ArrayList<String>();
        for (String v : vals1) {
            if (vals2.contains(v)) {
                uni.add(v);
                continue;
            }
        }
        for (String v : vals1) {
            if (!uni.contains(v)) {
                col1.remove(v, com.pullenti.morph.MorphGender.UNDEFINED);
                ret = true;
            }
        }
        for (String v : vals2) {
            if (!uni.contains(v)) {
                col2.remove(v, com.pullenti.morph.MorphGender.UNDEFINED);
                ret = true;
            }
        }
        if (col1.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
            if (col2.remove(null, (col1.getGender() == com.pullenti.morph.MorphGender.FEMINIE ? com.pullenti.morph.MorphGender.MASCULINE : com.pullenti.morph.MorphGender.FEMINIE))) 
                ret = true;
        }
        if (col2.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
            if (col1.remove(null, (col2.getGender() == com.pullenti.morph.MorphGender.FEMINIE ? com.pullenti.morph.MorphGender.MASCULINE : com.pullenti.morph.MorphGender.FEMINIE))) 
                ret = true;
        }
        return ret;
    }

    public static boolean intersect(java.util.ArrayList<PersonMorphCollection> list) {
        boolean ret = false;
        while (true) {
            boolean ch = false;
            for (int i = 0; i < (list.size() - 1); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    if (PersonMorphCollection.intersect2(list.get(i), list.get(j))) 
                        ch = true;
                    if (PersonMorphCollection.isEquals(list.get(i), list.get(j))) {
                        list.remove(j);
                        j--;
                        ch = true;
                    }
                }
            }
            if (ch) 
                ret = true;
            else 
                break;
        }
        return ret;
    }

    public static void setGender(java.util.ArrayList<PersonMorphCollection> list, com.pullenti.morph.MorphGender gen) {
        for (PersonMorphCollection li : list) {
            li.remove(null, (gen == com.pullenti.morph.MorphGender.MASCULINE ? com.pullenti.morph.MorphGender.FEMINIE : com.pullenti.morph.MorphGender.MASCULINE));
        }
    }
    public PersonMorphCollection() {
    }
    public static PersonMorphCollection _globalInstance;
    
    static {
        try { _globalInstance = new PersonMorphCollection(); } 
        catch(Exception e) { }
        m_Comparer = new SortComparer();
    }
}
