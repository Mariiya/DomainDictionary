/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.titlepage.internal;

public class PersonRelations {

    public java.util.ArrayList<PersonRelation> rels = new java.util.ArrayList<PersonRelation>();

    public void add(com.pullenti.ner.person.PersonReferent pers, TitleItemToken.Types typ, float coef) {
        PersonRelation r = null;
        for (PersonRelation rr : rels) {
            if (rr.person == pers) {
                r = rr;
                break;
            }
        }
        if (r == null) 
            rels.add((r = PersonRelation._new2861(pers)));
        if (!r.coefs.containsKey(typ)) 
            r.coefs.put(typ, coef);
        else 
            r.coefs.put(typ, r.coefs.get(typ) + coef);
    }

    public java.util.ArrayList<com.pullenti.ner.person.PersonReferent> getPersons(TitleItemToken.Types typ) {
        java.util.ArrayList<com.pullenti.ner.person.PersonReferent> res = new java.util.ArrayList<com.pullenti.ner.person.PersonReferent>();
        for (PersonRelation v : rels) {
            if (v.getBest() == typ) 
                res.add(v.person);
        }
        return res;
    }

    public java.util.ArrayList<TitleItemToken.Types> getRelTypes() {
        java.util.ArrayList<TitleItemToken.Types> res = new java.util.ArrayList<TitleItemToken.Types>();
        res.add(TitleItemToken.Types.WORKER);
        res.add(TitleItemToken.Types.BOSS);
        res.add(TitleItemToken.Types.EDITOR);
        res.add(TitleItemToken.Types.OPPONENT);
        res.add(TitleItemToken.Types.CONSULTANT);
        res.add(TitleItemToken.Types.ADOPT);
        res.add(TitleItemToken.Types.TRANSLATE);
        return res;
    }


    public String getAttrNameForType(TitleItemToken.Types typ) {
        if (typ == TitleItemToken.Types.WORKER) 
            return com.pullenti.ner.titlepage.TitlePageReferent.ATTR_AUTHOR;
        if (typ == TitleItemToken.Types.BOSS) 
            return com.pullenti.ner.titlepage.TitlePageReferent.ATTR_SUPERVISOR;
        if (typ == TitleItemToken.Types.EDITOR) 
            return com.pullenti.ner.titlepage.TitlePageReferent.ATTR_EDITOR;
        if (typ == TitleItemToken.Types.OPPONENT) 
            return com.pullenti.ner.titlepage.TitlePageReferent.ATTR_OPPONENT;
        if (typ == TitleItemToken.Types.CONSULTANT) 
            return com.pullenti.ner.titlepage.TitlePageReferent.ATTR_CONSULTANT;
        if (typ == TitleItemToken.Types.ADOPT) 
            return com.pullenti.ner.titlepage.TitlePageReferent.ATTR_AFFIRMANT;
        if (typ == TitleItemToken.Types.TRANSLATE) 
            return com.pullenti.ner.titlepage.TitlePageReferent.ATTR_TRANSLATOR;
        return null;
    }

    public TitleItemToken.Types calcTypFromAttrs(com.pullenti.ner.person.PersonReferent pers) {
        for (com.pullenti.ner.Slot a : pers.getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), com.pullenti.ner.person.PersonReferent.ATTR_ATTR)) {
                String s = a.getValue().toString();
                if ((s.indexOf("руководител") >= 0)) 
                    return TitleItemToken.Types.BOSS;
                if ((s.indexOf("студент") >= 0) || (s.indexOf("слушател") >= 0)) 
                    return TitleItemToken.Types.WORKER;
                if ((s.indexOf("редактор") >= 0) || (s.indexOf("рецензент") >= 0)) 
                    return TitleItemToken.Types.EDITOR;
                if ((s.indexOf("консультант") >= 0)) 
                    return TitleItemToken.Types.CONSULTANT;
                if ((s.indexOf("исполнитель") >= 0)) 
                    return TitleItemToken.Types.WORKER;
            }
        }
        return TitleItemToken.Types.UNDEFINED;
    }
    public PersonRelations() {
    }
}
