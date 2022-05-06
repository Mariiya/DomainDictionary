/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.geo.internal;

public class GeoTokenData {

    public GeoTokenData(com.pullenti.ner.Token t) {
        tok = t;
        t.tag = this;
    }

    public com.pullenti.ner.Token tok;

    public com.pullenti.ner.core.NounPhraseToken npt;

    public TerrItemToken terr;

    public CityItemToken cit;

    public OrgTypToken orgTyp;

    public OrgItemToken _org;

    public com.pullenti.ner.address.internal.StreetItemToken street;

    public com.pullenti.ner.address.internal.AddressItemToken addr;

    public boolean noGeo = false;

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append(tok.toString());
        if (npt != null) 
            tmp.append(" \r\nNpt: ").append(npt.toString());
        if (terr != null) 
            tmp.append(" \r\nTerr: ").append(terr.toString());
        if (cit != null) 
            tmp.append(" \r\nCit: ").append(cit.toString());
        if (_org != null) 
            tmp.append(" \r\nOrg: ").append(_org.toString());
        if (orgTyp != null) 
            tmp.append(" \r\nOrgTyp: ").append(orgTyp.toString());
        if (street != null) 
            tmp.append(" \r\nStreet: ").append(street.toString());
        if (addr != null) 
            tmp.append(" \r\nAddr: ").append(addr.toString());
        if (noGeo) 
            tmp.append(" \r\nNO GEO!!!");
        return tmp.toString();
    }
    public GeoTokenData() {
    }
}
