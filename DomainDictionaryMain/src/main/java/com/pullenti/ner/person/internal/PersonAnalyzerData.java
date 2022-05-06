/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.person.internal;

public class PersonAnalyzerData extends com.pullenti.ner.core.AnalyzerDataWithOntology {

    public boolean nominativeCaseAlways = false;

    public boolean textStartsWithLastnameFirstnameMiddlename = false;

    public boolean needSecondStep = false;

    @Override
    public com.pullenti.ner.Referent registerReferent(com.pullenti.ner.Referent referent) {
        if (referent instanceof com.pullenti.ner.person.PersonReferent) {
            java.util.ArrayList<com.pullenti.ner.person.PersonPropertyReferent> existProps = null;
            for (int i = 0; i < referent.getSlots().size(); i++) {
                com.pullenti.ner.Slot a = referent.getSlots().get(i);
                if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), com.pullenti.ner.person.PersonReferent.ATTR_ATTR)) {
                    PersonAttrToken pat = (PersonAttrToken)com.pullenti.unisharp.Utils.cast(a.getValue(), PersonAttrToken.class);
                    if (pat == null || pat.getPropRef() == null) {
                        if (a.getValue() instanceof com.pullenti.ner.person.PersonPropertyReferent) {
                            if (existProps == null) 
                                existProps = new java.util.ArrayList<com.pullenti.ner.person.PersonPropertyReferent>();
                            existProps.add((com.pullenti.ner.person.PersonPropertyReferent)com.pullenti.unisharp.Utils.cast(a.getValue(), com.pullenti.ner.person.PersonPropertyReferent.class));
                        }
                        continue;
                    }
                    if (pat.getPropRef() != null) {
                        for (com.pullenti.ner.Slot ss : pat.getPropRef().getSlots()) {
                            if (com.pullenti.unisharp.Utils.stringsEq(ss.getTypeName(), com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF)) {
                                if (ss.getValue() instanceof com.pullenti.ner.ReferentToken) {
                                    if (((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(ss.getValue(), com.pullenti.ner.ReferentToken.class)).referent == referent) {
                                        pat.getPropRef().getSlots().remove(ss);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (existProps != null) {
                        for (com.pullenti.ner.person.PersonPropertyReferent pp : existProps) {
                            if (pp.canBeEquals(pat.getPropRef(), com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) {
                                if (pat.getPropRef().canBeGeneralFor(pp)) {
                                    pat.getPropRef().mergeSlots(pp, true);
                                    break;
                                }
                            }
                        }
                    }
                    pat.data = this;
                    pat.saveToLocalOntology();
                    if (pat.getPropRef() != null) {
                        if (referent.findSlot(a.getTypeName(), pat.getPropRef(), true) != null) {
                            if (i >= 0 && (i < referent.getSlots().size())) {
                                referent.getSlots().remove(i);
                                i--;
                            }
                        }
                        else 
                            referent.uploadSlot(a, pat.referent);
                    }
                }
            }
        }
        if (referent instanceof com.pullenti.ner.person.PersonPropertyReferent) {
            for (int i = 0; i < referent.getSlots().size(); i++) {
                com.pullenti.ner.Slot a = referent.getSlots().get(i);
                if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF) || com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), com.pullenti.ner.person.PersonPropertyReferent.ATTR_HIGHER)) {
                    com.pullenti.ner.ReferentToken pat = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(a.getValue(), com.pullenti.ner.ReferentToken.class);
                    if (pat != null) {
                        pat.data = this;
                        pat.saveToLocalOntology();
                        if (pat.referent != null) 
                            referent.uploadSlot(a, pat.referent);
                    }
                    else if (a.getValue() instanceof com.pullenti.ner.person.PersonPropertyReferent) {
                        if (a.getValue() == referent) {
                            referent.getSlots().remove(i);
                            i--;
                            continue;
                        }
                        referent.uploadSlot(a, this.registerReferent((com.pullenti.ner.person.PersonPropertyReferent)com.pullenti.unisharp.Utils.cast(a.getValue(), com.pullenti.ner.person.PersonPropertyReferent.class)));
                    }
                }
            }
        }
        com.pullenti.ner.Referent res = super.registerReferent(referent);
        return res;
    }

    public java.util.HashMap<Integer, Boolean> canBePersonPropBeginChars = new java.util.HashMap<Integer, Boolean>();

    public boolean aRegime = false;
    public PersonAnalyzerData() {
        super();
    }
}
