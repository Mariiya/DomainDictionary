/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.internal;

public class AlgoParams {

    public double transitiveCoef = 1.0;

    public double nextModel = 1.0;

    public double ngLink = 1.0;

    public double list = 2.0;

    public double verbPlural = 2.0;

    public double caseAccord = 1.0;

    public double morphAccord = 1.0;

    public void copyFrom(AlgoParams src) {
        transitiveCoef = src.transitiveCoef;
        nextModel = src.nextModel;
        ngLink = src.ngLink;
        list = src.list;
        verbPlural = src.verbPlural;
        caseAccord = src.caseAccord;
        morphAccord = src.morphAccord;
    }

    public void copyFromParams() {
        for (AlgoParam p : PARAMS) {
            if (com.pullenti.unisharp.Utils.stringsEq(p.name, "TransitiveCoef")) 
                transitiveCoef = p.value;
            else if (com.pullenti.unisharp.Utils.stringsEq(p.name, "NextModel")) 
                nextModel = p.value;
            else if (com.pullenti.unisharp.Utils.stringsEq(p.name, "NgLink")) 
                ngLink = p.value;
            else if (com.pullenti.unisharp.Utils.stringsEq(p.name, "List")) 
                list = p.value;
            else if (com.pullenti.unisharp.Utils.stringsEq(p.name, "VerbPlural")) 
                verbPlural = p.value;
            else if (com.pullenti.unisharp.Utils.stringsEq(p.name, "CaseAccord")) 
                caseAccord = p.value;
            else if (com.pullenti.unisharp.Utils.stringsEq(p.name, "MorphAccord")) 
                morphAccord = p.value;
        }
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append("TransitiveCoef = ").append(transitiveCoef).append(" \r\n");
        tmp.append("NextModel = ").append(nextModel).append(" \r\n");
        tmp.append("NgLink = ").append(ngLink).append(" \r\n");
        tmp.append("List = ").append(list).append(" \r\n");
        tmp.append("VerbPlural = ").append(verbPlural).append(" \r\n");
        tmp.append("CaseAccord = ").append(caseAccord).append(" \r\n");
        tmp.append("MorphAccord = ").append(morphAccord).append(" \r\n");
        return tmp.toString();
    }

    public static java.util.ArrayList<AlgoParam> PARAMS;

    public AlgoParams() {
    }
    
    static {
        PARAMS = new java.util.ArrayList<AlgoParam>();
        PARAMS.add(AlgoParam._new3101("TransitiveCoef", 1.0, 4.0, 1.0));
        PARAMS.add(AlgoParam._new3101("NextModel", 1.0, 4.0, 1.0));
        PARAMS.add(AlgoParam._new3101("NgLink", 1.0, 3.0, 1.0));
        PARAMS.add(AlgoParam._new3101("List", 1.0, 4.0, 1.0));
        PARAMS.add(AlgoParam._new3101("VerbPlural", 1.0, 4.0, 1.0));
        PARAMS.add(AlgoParam._new3101("CaseAccord", 1.0, 3.0, 1.0));
        PARAMS.add(AlgoParam._new3101("MorphAccord", 1.0, 3.0, 1.0));
    }
}
