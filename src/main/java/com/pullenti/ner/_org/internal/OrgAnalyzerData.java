/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner._org.internal;

public class OrgAnalyzerData extends com.pullenti.ner.core.AnalyzerDataWithOntology {

    @Override
    public com.pullenti.ner.Referent registerReferent(com.pullenti.ner.Referent referent) {
        if (referent instanceof com.pullenti.ner._org.OrganizationReferent) 
            ((com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(referent, com.pullenti.ner._org.OrganizationReferent.class)).finalCorrection();
        int slots = referent.getSlots().size();
        com.pullenti.ner.Referent res = super.registerReferent(referent);
        if (!largeTextRegim && (res instanceof com.pullenti.ner._org.OrganizationReferent) && (res == referent)) {
            com.pullenti.ner.core.IntOntologyItem ioi = ((com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(res, com.pullenti.ner._org.OrganizationReferent.class)).createOntologyItemEx(2, true, false);
            if (ioi != null) 
                locOrgs.addItem(ioi);
            java.util.ArrayList<String> names = ((com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(res, com.pullenti.ner._org.OrganizationReferent.class))._getPureNames();
            if (names != null) {
                for (String n : names) {
                    orgPureNames.add(new com.pullenti.ner.core.Termin(n, null, false));
                }
            }
        }
        return res;
    }

    public com.pullenti.ner.core.IntOntologyCollection locOrgs = new com.pullenti.ner.core.IntOntologyCollection();

    public com.pullenti.ner.core.TerminCollection orgPureNames = new com.pullenti.ner.core.TerminCollection();

    public com.pullenti.ner.core.TerminCollection aliases = new com.pullenti.ner.core.TerminCollection();

    public boolean largeTextRegim = false;

    public boolean tRegime = false;

    public int tLevel;
    public OrgAnalyzerData() {
        super();
    }
}
