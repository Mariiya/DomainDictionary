/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.address.internal;

public class AddressDefineHelper {

    public static com.pullenti.ner.Token tryDefine(java.util.ArrayList<AddressItemToken> li, com.pullenti.ner.Token t, com.pullenti.ner.core.AnalyzerData ad) {
        if (li == null || li.size() == 0) 
            return null;
        boolean empty = true;
        boolean notEmpty = false;
        boolean badOrg = false;
        for (AddressItemToken v : li) {
            if (v.getTyp() == AddressItemType.NUMBER || v.getTyp() == AddressItemType.ZIP || v.getTyp() == AddressItemType.DETAIL) {
            }
            else if (v.getTyp() != AddressItemType.STREET) {
                empty = false;
                if (v.getTyp() != AddressItemType.CITY && v.getTyp() != AddressItemType.COUNTRY && v.getTyp() != AddressItemType.REGION) 
                    notEmpty = true;
            }
            else if (v.referent instanceof com.pullenti.ner.address.StreetReferent) {
                com.pullenti.ner.address.StreetReferent s = (com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(v.referent, com.pullenti.ner.address.StreetReferent.class);
                if (s.getKind() == com.pullenti.ner.address.StreetKind.RAILWAY && s.getNumber() == null) {
                }
                else if (s.getKind() == com.pullenti.ner.address.StreetKind.ORG) {
                    if (v.refToken != null && !v.refTokenIsGsk) 
                        badOrg = true;
                    if (badOrg) {
                        if (v == li.get(0)) 
                            return null;
                        else if (li.get(0).getTyp() == AddressItemType.PREFIX && v == li.get(1)) 
                            return null;
                    }
                }
                else {
                    empty = false;
                    notEmpty = true;
                }
            }
        }
        if (empty) 
            return null;
        if (!notEmpty) {
            for (AddressItemToken v : li) {
                if (v != li.get(0) && v.isNewlineBefore()) 
                    return null;
            }
            if (badOrg) 
                return null;
            if (li.get(0).getTyp() == AddressItemType.STREET && ((com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(li.get(0).referent, com.pullenti.ner.address.StreetReferent.class)).getKind() == com.pullenti.ner.address.StreetKind.ORG) 
                return null;
            if (li.size() == 1 && li.get(0).getTyp() != AddressItemType.STREET && li.get(0).detailMeters == 0) 
                return null;
        }
        if ((li.size() > 3 && li.get(0).getTyp() == AddressItemType.CITY && li.get(1).getTyp() == AddressItemType.STREET) && li.get(2).getTyp() == AddressItemType.CITY && li.get(3).getTyp() == AddressItemType.STREET) {
            if (((com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(li.get(1).referent, com.pullenti.ner.address.StreetReferent.class)).getKind() == com.pullenti.ner.address.StreetKind.RAILWAY || ((com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(li.get(1).referent, com.pullenti.ner.address.StreetReferent.class)).getKind() == com.pullenti.ner.address.StreetKind.ROAD) {
                com.pullenti.ner.geo.GeoReferent geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(2).referent, com.pullenti.ner.geo.GeoReferent.class);
                if (geo != null && geo.getHigher() == null && com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigher((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(0).referent, com.pullenti.ner.geo.GeoReferent.class), geo, null, null)) {
                    geo.setHigher((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(0).referent, com.pullenti.ner.geo.GeoReferent.class));
                    com.pullenti.unisharp.Utils.putArrayValue(li, 2, li.get(2).clone());
                    li.get(2).setBeginToken(li.get(0).getBeginToken());
                    for(int jjj = 0 + 2 - 1, mmm = 0; jjj >= mmm; jjj--) li.remove(jjj);
                }
            }
        }
        if (li.size() >= 2 && li.get(0).getTyp() == AddressItemType.BLOCK && li.get(1).getTyp() == AddressItemType.STREET) 
            return null;
        if (li.get(0).getTyp() == AddressItemType.STREET) {
            if (li.get(0).refToken != null) {
                if (!li.get(0).refTokenIsGsk || ((com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(li.get(0).referent, com.pullenti.ner.address.StreetReferent.class)).getKind() == com.pullenti.ner.address.StreetKind.AREA) 
                    return null;
            }
        }
        com.pullenti.ner.address.AddressReferent addr = new com.pullenti.ner.address.AddressReferent();
        java.util.ArrayList<AddressItemToken> streets = new java.util.ArrayList<AddressItemToken>();
        int i;
        int j;
        AddressItemToken metro = null;
        java.util.ArrayList<AddressItemToken> details = new java.util.ArrayList<AddressItemToken>();
        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> geos = null;
        boolean err = false;
        boolean cross = false;
        for (i = 0; i < li.size(); i++) {
            if ((li.get(i).getTyp() == AddressItemType.DETAIL && li.get(i).detailType == com.pullenti.ner.address.AddressDetailType.CROSS && ((i + 2) < li.size())) && li.get(i + 1).getTyp() == AddressItemType.STREET && li.get(i + 2).getTyp() == AddressItemType.STREET) {
                cross = true;
                streets.add(li.get(i + 1));
                streets.add(li.get(i + 2));
                li.get(i + 1).setEndToken(li.get(i + 2).getEndToken());
                li.get(i).tag = addr;
                li.get(i + 1).tag = addr;
                li.remove(i + 2);
                break;
            }
            else if (li.get(i).getTyp() == AddressItemType.STREET) {
                if (((li.get(i).refToken != null && !li.get(i).refTokenIsGsk)) && streets.size() == 0) {
                    if (i > 0 && li.get(i).isNewlineBefore()) {
                        err = true;
                        for(int jjj = i + li.size() - i - 1, mmm = i; jjj >= mmm; jjj--) li.remove(jjj);
                        break;
                    }
                    else if ((i + 1) == li.size()) 
                        err = details.size() == 0;
                    else if (((i + 1) < li.size()) && li.get(i + 1).getTyp() == AddressItemType.NUMBER) 
                        err = true;
                    if (err && geos != null) {
                        for (int ii = i - 1; ii >= 0; ii--) {
                            if (li.get(ii).getTyp() == AddressItemType.ZIP || li.get(ii).getTyp() == AddressItemType.PREFIX) 
                                err = false;
                        }
                    }
                    if (err) 
                        break;
                }
                li.get(i).tag = addr;
                streets.add(li.get(i));
                if (((i + 1) < li.size()) && li.get(i + 1).getTyp() == AddressItemType.STREET) {
                }
                else 
                    break;
            }
            else if (li.get(i).getTyp() == AddressItemType.CITY || li.get(i).getTyp() == AddressItemType.REGION) {
                if (geos == null) 
                    geos = new java.util.ArrayList<com.pullenti.ner.geo.GeoReferent>();
                com.pullenti.ner.geo.GeoReferent geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(i).referent, com.pullenti.ner.geo.GeoReferent.class);
                if (li.get(i).detailType != com.pullenti.ner.address.AddressDetailType.UNDEFINED) {
                    details.add(li.get(i));
                    if (geos.size() == 0) {
                        if (geo.getHigher() != null) 
                            geos.add(geo.getHigher());
                        else 
                            geos.add(geo);
                    }
                }
                else 
                    geos.add(0, geo);
                li.get(i).tag = addr;
            }
            else if (li.get(i).getTyp() == AddressItemType.DETAIL) {
                details.add(li.get(i));
                li.get(i).tag = addr;
            }
        }
        if ((i >= li.size() && metro == null && details.size() == 0) && !cross) {
            for (i = 0; i < li.size(); i++) {
                boolean cit = false;
                if (li.get(i).getTyp() == AddressItemType.CITY) 
                    cit = true;
                else if (li.get(i).getTyp() == AddressItemType.REGION) {
                    for (com.pullenti.ner.Slot s : li.get(i).referent.getSlots()) {
                        if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.geo.GeoReferent.ATTR_TYPE)) {
                            String ss = (String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class);
                            if ((ss.indexOf("посел") >= 0) || (ss.indexOf("сельск") >= 0) || (ss.indexOf("почтовое отделение") >= 0)) 
                                cit = true;
                        }
                    }
                }
                if (cit) {
                    if (((i + 1) < li.size()) && (((((li.get(i + 1).getTyp() == AddressItemType.HOUSE || li.get(i + 1).getTyp() == AddressItemType.BLOCK || li.get(i + 1).getTyp() == AddressItemType.PLOT) || li.get(i + 1).getTyp() == AddressItemType.FIELD || li.get(i + 1).getTyp() == AddressItemType.BUILDING) || li.get(i + 1).getTyp() == AddressItemType.CORPUS || li.get(i + 1).getTyp() == AddressItemType.POSTOFFICEBOX) || li.get(i + 1).getTyp() == AddressItemType.CSP))) 
                        break;
                    if (((i + 1) < li.size()) && li.get(i + 1).getTyp() == AddressItemType.NUMBER) {
                        if (li.get(i).getEndToken().getNext().isComma()) {
                            if ((li.get(i).referent instanceof com.pullenti.ner.geo.GeoReferent) && !((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(i).referent, com.pullenti.ner.geo.GeoReferent.class)).isBigCity() && ((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(i).referent, com.pullenti.ner.geo.GeoReferent.class)).isCity()) {
                                li.get(i + 1).setTyp(AddressItemType.HOUSE);
                                li.get(i + 1).isDoubt = true;
                                break;
                            }
                        }
                    }
                    if (li.get(0).getTyp() == AddressItemType.ZIP || li.get(0).getTyp() == AddressItemType.PREFIX) 
                        break;
                    continue;
                }
                if (li.get(i).getTyp() == AddressItemType.REGION) {
                    if ((li.get(i).referent instanceof com.pullenti.ner.geo.GeoReferent) && ((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(i).referent, com.pullenti.ner.geo.GeoReferent.class)).getHigher() != null && ((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(i).referent, com.pullenti.ner.geo.GeoReferent.class)).getHigher().isCity()) {
                        if (((i + 1) < li.size()) && li.get(i + 1).getTyp() == AddressItemType.HOUSE) 
                            break;
                    }
                }
            }
            if (i >= li.size()) 
                return null;
        }
        if (err) 
            return null;
        int i0 = i;
        if (i > 0 && li.get(i - 1).getTyp() == AddressItemType.HOUSE && li.get(i - 1).isDigit()) {
            addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_HOUSE, li.get(i - 1).value, false, 0).setTag(li.get(i - 1));
            li.get(i - 1).tag = addr;
        }
        else if ((i > 0 && li.get(i - 1).getTyp() == AddressItemType.KILOMETER && li.get(i - 1).isDigit()) && (i < li.size()) && li.get(i).isStreetRoad()) {
            addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_KILOMETER, li.get(i - 1).value, false, 0).setTag(li.get(i - 1));
            li.get(i - 1).tag = addr;
        }
        else {
            if (i >= li.size()) 
                i = -1;
            for (i = 0; i < li.size(); i++) {
                if (li.get(i).tag != null) 
                    continue;
                if (li.get(i).getTyp() == AddressItemType.HOUSE) {
                    if (addr.getHouse() != null) 
                        break;
                    if (li.get(i).value != null) {
                        String attr = com.pullenti.ner.address.AddressReferent.ATTR_HOUSE;
                        if (li.get(i).isDoubt) {
                            attr = com.pullenti.ner.address.AddressReferent.ATTR_HOUSEORPLOT;
                            if (((i + 1) < li.size()) && (((li.get(i + 1).getTyp() == AddressItemType.FLAT || li.get(i + 1).getTyp() == AddressItemType.POTCH || li.get(i + 1).getTyp() == AddressItemType.FLOOR) || li.get(i + 1).getTyp() == AddressItemType.NUMBER))) 
                                attr = com.pullenti.ner.address.AddressReferent.ATTR_HOUSE;
                        }
                        addr.addSlot(attr, li.get(i).value, false, 0).setTag(li.get(i));
                        if (li.get(i).houseType != com.pullenti.ner.address.AddressHouseType.UNDEFINED) 
                            addr.setHouseType(li.get(i).houseType);
                    }
                    li.get(i).tag = addr;
                }
                else if (li.get(i).getTyp() == AddressItemType.KILOMETER && li.get(i).isDigit() && (((i0 < li.size()) && li.get(i0).isStreetRoad()))) {
                    if (addr.getKilometer() != null) 
                        break;
                    com.pullenti.ner.Slot s = addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_KILOMETER, li.get(i).value, false, 0);
                    if (s != null) 
                        s.setTag(li.get(i));
                    li.get(i).tag = addr;
                }
                else if (li.get(i).getTyp() == AddressItemType.PLOT) {
                    if (addr.getPlot() != null) 
                        break;
                    com.pullenti.ner.Slot s = addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_PLOT, li.get(i).value, false, 0);
                    if (s != null) 
                        s.setTag(li.get(i));
                    li.get(i).tag = addr;
                }
                else if (li.get(i).getTyp() == AddressItemType.FIELD) {
                    if (addr.getField() != null) 
                        break;
                    com.pullenti.ner.Slot s = addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_FIELD, li.get(i).value, false, 0);
                    if (s != null) 
                        s.setTag(li.get(i));
                    li.get(i).tag = addr;
                }
                else if (li.get(i).getTyp() == AddressItemType.BOX && li.get(i).isDigit()) {
                    if (addr.getBox() != null) 
                        break;
                    com.pullenti.ner.Slot s = addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_BOX, li.get(i).value, false, 0);
                    if (s != null) 
                        s.setTag(li.get(i));
                    li.get(i).tag = addr;
                }
                else if (li.get(i).getTyp() == AddressItemType.BLOCK && li.get(i).isDigit()) {
                    if (addr.getBlock() != null) 
                        break;
                    com.pullenti.ner.Slot s = addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_BLOCK, li.get(i).value, false, 0);
                    if (s != null) 
                        s.setTag(li.get(i));
                    li.get(i).tag = addr;
                }
                else if (li.get(i).getTyp() == AddressItemType.CORPUS) {
                    if (addr.getCorpus() != null) 
                        break;
                    if (li.get(i).value != null) {
                        com.pullenti.ner.Slot s = addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_CORPUS, li.get(i).value, false, 0);
                        if (s != null) 
                            s.setTag(li.get(i));
                    }
                    li.get(i).tag = addr;
                }
                else if (li.get(i).getTyp() == AddressItemType.BUILDING) {
                    if (addr.getBuilding() != null) 
                        break;
                    if (li.get(i).value != null) {
                        com.pullenti.ner.Slot s = addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_BUILDING, li.get(i).value, false, 0);
                        if (s != null) 
                            s.setTag(li.get(i));
                        if (li.get(i).buildingType != com.pullenti.ner.address.AddressBuildingType.UNDEFINED) 
                            addr.setBuildingType(li.get(i).buildingType);
                    }
                    li.get(i).tag = addr;
                }
                else if (li.get(i).getTyp() == AddressItemType.FLOOR && li.get(i).isDigit()) {
                    if (addr.getFloor() != null) 
                        break;
                    com.pullenti.ner.Slot s = addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_FLOOR, li.get(i).value, false, 0);
                    if (s != null) 
                        s.setTag(li.get(i));
                    li.get(i).tag = addr;
                }
                else if (li.get(i).getTyp() == AddressItemType.POTCH && li.get(i).isDigit()) {
                    if (addr.getPotch() != null) 
                        break;
                    com.pullenti.ner.Slot s = addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_PORCH, li.get(i).value, false, 0);
                    if (s != null) 
                        s.setTag(li.get(i));
                    li.get(i).tag = addr;
                }
                else if (li.get(i).getTyp() == AddressItemType.FLAT) {
                    if (addr.getFlat() != null) 
                        break;
                    if (li.get(i).value != null) 
                        addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_FLAT, li.get(i).value, false, 0).setTag(li.get(i));
                    li.get(i).tag = addr;
                }
                else if (li.get(i).getTyp() == AddressItemType.PAVILION) {
                    if (addr.getPavilion() != null) 
                        break;
                    if (li.get(i).value != null) 
                        addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_PAVILION, li.get(i).value, false, 0).setTag(li.get(i));
                    li.get(i).tag = addr;
                }
                else if (li.get(i).getTyp() == AddressItemType.OFFICE && li.get(i).isDigit()) {
                    if (addr.getOffice() != null) 
                        break;
                    com.pullenti.ner.Slot s = addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_OFFICE, li.get(i).value, false, 0);
                    if (s != null) 
                        s.setTag(li.get(i));
                    li.get(i).tag = addr;
                }
                else if (li.get(i).getTyp() == AddressItemType.CORPUSORFLAT && ((li.get(i).isDigit() || li.get(i).value == null))) {
                    for (j = i + 1; j < li.size(); j++) {
                        if (li.get(j).isDigit()) {
                            if ((((li.get(j).getTyp() == AddressItemType.FLAT || li.get(j).getTyp() == AddressItemType.CORPUSORFLAT || li.get(j).getTyp() == AddressItemType.OFFICE) || li.get(j).getTyp() == AddressItemType.FLOOR || li.get(j).getTyp() == AddressItemType.POTCH) || li.get(j).getTyp() == AddressItemType.POSTOFFICEBOX || li.get(j).getTyp() == AddressItemType.BUILDING) || li.get(j).getTyp() == AddressItemType.PAVILION) 
                                break;
                        }
                    }
                    if (li.get(i).value != null) {
                        if ((j < li.size()) && addr.getCorpus() == null) 
                            addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_CORPUS, li.get(i).value, false, 0).setTag(li.get(i));
                        else if (addr.getCorpus() != null) 
                            addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_FLAT, li.get(i).value, false, 0).setTag(li.get(i));
                        else 
                            addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_CORPUSORFLAT, li.get(i).value, false, 0).setTag(li.get(i));
                    }
                    li.get(i).tag = addr;
                }
                else if ((!li.get(i).isNewlineBefore() && li.get(i).getTyp() == AddressItemType.NUMBER && li.get(i).isDigit()) && li.get(i - 1).getTyp() == AddressItemType.STREET) {
                    int v = 0;
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapv70 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    boolean inoutres71 = com.pullenti.unisharp.Utils.parseInteger(li.get(i).value, 0, null, wrapv70);
                    v = (wrapv70.value != null ? wrapv70.value : 0);
                    if (!inoutres71) {
                        com.pullenti.unisharp.Outargwrapper<Integer> wrapv64 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                        boolean inoutres65 = com.pullenti.unisharp.Utils.parseInteger(li.get(i).value.substring(0, 0 + li.get(i).value.length() - 1), 0, null, wrapv64);
                        v = (wrapv64.value != null ? wrapv64.value : 0);
                        if (!inoutres65) {
                            if (!(li.get(i).value.indexOf("/") >= 0)) 
                                break;
                        }
                    }
                    if (v > 500) 
                        break;
                    String attr = com.pullenti.ner.address.AddressReferent.ATTR_HOUSEORPLOT;
                    if (((i + 1) < li.size()) && (((li.get(i + 1).getTyp() == AddressItemType.FLAT || li.get(i + 1).getTyp() == AddressItemType.POTCH || li.get(i + 1).getTyp() == AddressItemType.FLOOR) || li.get(i + 1).getTyp() == AddressItemType.NUMBER || ((li.get(i + 1).getTyp() == AddressItemType.STREET && li.get(i + 1).refTokenIsGsk))))) 
                        attr = com.pullenti.ner.address.AddressReferent.ATTR_HOUSE;
                    addr.addSlot(attr, li.get(i).value, false, 0).setTag(li.get(i));
                    li.get(i).tag = addr;
                    if (((i + 1) < li.size()) && ((li.get(i + 1).getTyp() == AddressItemType.NUMBER || li.get(i + 1).getTyp() == AddressItemType.FLAT)) && !li.get(i + 1).isNewlineBefore()) {
                        com.pullenti.unisharp.Outargwrapper<Integer> wrapv68 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                        boolean inoutres69 = com.pullenti.unisharp.Utils.parseInteger(li.get(i + 1).value, 0, null, wrapv68);
                        v = (wrapv68.value != null ? wrapv68.value : 0);
                        if (!inoutres69) 
                            break;
                        if (v > 500) 
                            break;
                        i++;
                        if ((((i + 1) < li.size()) && li.get(i + 1).getTyp() == AddressItemType.NUMBER && !li.get(i + 1).isNewlineBefore()) && (v < 5)) {
                            com.pullenti.unisharp.Outargwrapper<Integer> wrapv66 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                            boolean inoutres67 = com.pullenti.unisharp.Utils.parseInteger(li.get(i + 1).value, 0, null, wrapv66);
                            v = (wrapv66.value != null ? wrapv66.value : 0);
                            if (inoutres67) {
                                if (v < 500) {
                                    addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_CORPUS, li.get(i).value, false, 0).setTag(li.get(i));
                                    li.get(i).tag = addr;
                                    i++;
                                }
                            }
                        }
                        addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_FLAT, li.get(i).value, false, 0).setTag(li.get(i));
                        li.get(i).tag = addr;
                    }
                }
                else if (li.get(i).getTyp() == AddressItemType.CITY) {
                    if (geos == null) 
                        geos = new java.util.ArrayList<com.pullenti.ner.geo.GeoReferent>();
                    if (li.get(i).isNewlineBefore()) {
                        if (geos.size() > 0) {
                            if ((i > 0 && li.get(i - 1).getTyp() != AddressItemType.CITY && li.get(i - 1).getTyp() != AddressItemType.REGION) && li.get(i - 1).getTyp() != AddressItemType.ZIP && li.get(i - 1).getTyp() != AddressItemType.PREFIX) 
                                break;
                        }
                        if (((i + 1) < li.size()) && li.get(i + 1).getTyp() == AddressItemType.STREET && i > i0) 
                            break;
                    }
                    if (li.get(i).detailType != com.pullenti.ner.address.AddressDetailType.UNDEFINED) {
                        details.add(li.get(i));
                        li.get(i).tag = addr;
                        if (geos.size() > 0) 
                            continue;
                    }
                    int ii;
                    for (ii = 0; ii < geos.size(); ii++) {
                        if (geos.get(ii).isCity()) 
                            break;
                    }
                    if (ii >= geos.size()) 
                        geos.add((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(i).referent, com.pullenti.ner.geo.GeoReferent.class));
                    else if (i > 0 && li.get(i).isNewlineBefore() && i > i0) {
                        int jj;
                        for (jj = 0; jj < i; jj++) {
                            if ((li.get(jj).getTyp() != AddressItemType.PREFIX && li.get(jj).getTyp() != AddressItemType.ZIP && li.get(jj).getTyp() != AddressItemType.REGION) && li.get(jj).getTyp() != AddressItemType.COUNTRY && li.get(jj).getTyp() != AddressItemType.CITY) 
                                break;
                        }
                        if (jj < i) 
                            break;
                    }
                    li.get(i).tag = addr;
                }
                else if (li.get(i).getTyp() == AddressItemType.POSTOFFICEBOX) {
                    if (addr.getPostOfficeBox() != null) 
                        break;
                    addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_POSTOFFICEBOX, (li.get(i).value != null ? li.get(i).value : ""), false, 0).setTag(li.get(i));
                    li.get(i).tag = addr;
                }
                else if (li.get(i).getTyp() == AddressItemType.CSP) {
                    if (addr.getCSP() != null) 
                        break;
                    addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_CSP, li.get(i).value, false, 0).setTag(li.get(i));
                    li.get(i).tag = addr;
                }
                else if (li.get(i).getTyp() == AddressItemType.STREET) {
                    if (streets.size() > 1) 
                        break;
                    if (streets.size() > 0) {
                        if (li.get(i).isNewlineBefore()) 
                            break;
                        if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(li.get(i).getBeginToken())) 
                            break;
                    }
                    if (li.get(i).refToken == null && i > 0 && li.get(i - 1).getTyp() != AddressItemType.STREET) 
                        break;
                    if (streets.size() > 0) {
                        com.pullenti.ner.address.StreetReferent ss = (com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(li.get(i).referent, com.pullenti.ner.address.StreetReferent.class);
                        if (ss.getKind() == com.pullenti.ner.address.StreetKind.ORG && ((com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(streets.get(streets.size() - 1).referent, com.pullenti.ner.address.StreetReferent.class)).getKind() == com.pullenti.ner.address.StreetKind.UNDEFINED) {
                            details.add(li.get(i));
                            li.get(i).tag = addr;
                            continue;
                        }
                    }
                    streets.add(li.get(i));
                    li.get(i).tag = addr;
                }
                else if (li.get(i).getTyp() == AddressItemType.DETAIL) {
                    if ((i + 1) == li.size() && li.get(i).detailType == com.pullenti.ner.address.AddressDetailType.NEAR) 
                        break;
                    if (li.get(i).detailType == com.pullenti.ner.address.AddressDetailType.NEAR && ((i + 1) < li.size()) && li.get(i + 1).getTyp() == AddressItemType.CITY) {
                        details.add(li.get(i));
                        li.get(i).tag = addr;
                        i++;
                    }
                    details.add(li.get(i));
                    li.get(i).tag = addr;
                }
                else if (i > i0) 
                    break;
            }
        }
        java.util.ArrayList<String> typs = new java.util.ArrayList<String>();
        for (com.pullenti.ner.Slot s : addr.getSlots()) {
            if (!typs.contains(s.getTypeName())) 
                typs.add(s.getTypeName());
        }
        if (streets.size() == 1 && !streets.get(0).isDoubt && streets.get(0).refToken == null) {
        }
        else if (li.size() > 2 && li.get(0).getTyp() == AddressItemType.ZIP && ((li.get(1).getTyp() == AddressItemType.COUNTRY || li.get(1).getTyp() == AddressItemType.REGION))) {
        }
        else if ((typs.size() + streets.size()) < 2) {
            if (typs.size() > 0) {
                if (((((com.pullenti.unisharp.Utils.stringsNe(typs.get(0), com.pullenti.ner.address.AddressReferent.ATTR_STREET) && com.pullenti.unisharp.Utils.stringsNe(typs.get(0), com.pullenti.ner.address.AddressReferent.ATTR_POSTOFFICEBOX) && metro == null) && com.pullenti.unisharp.Utils.stringsNe(typs.get(0), com.pullenti.ner.address.AddressReferent.ATTR_HOUSE) && com.pullenti.unisharp.Utils.stringsNe(typs.get(0), com.pullenti.ner.address.AddressReferent.ATTR_HOUSEORPLOT)) && com.pullenti.unisharp.Utils.stringsNe(typs.get(0), com.pullenti.ner.address.AddressReferent.ATTR_CORPUS) && com.pullenti.unisharp.Utils.stringsNe(typs.get(0), com.pullenti.ner.address.AddressReferent.ATTR_BUILDING)) && com.pullenti.unisharp.Utils.stringsNe(typs.get(0), com.pullenti.ner.address.AddressReferent.ATTR_PLOT) && com.pullenti.unisharp.Utils.stringsNe(typs.get(0), com.pullenti.ner.address.AddressReferent.ATTR_DETAIL)) && details.size() == 0 && !cross) 
                    return null;
            }
            else if (streets.size() == 0 && details.size() == 0 && !cross) {
                if (li.get(i - 1).getTyp() == AddressItemType.CITY && i > 2 && li.get(i - 2).getTyp() == AddressItemType.ZIP) {
                }
                else 
                    return null;
            }
            else if ((i == li.size() && streets.size() == 1 && (streets.get(0).referent instanceof com.pullenti.ner.address.StreetReferent)) && streets.get(0).referent.findSlot(com.pullenti.ner.address.StreetReferent.ATTR_TYP, "квартал", true) != null) 
                return null;
            if (geos == null) {
                boolean hasGeo = false;
                for (com.pullenti.ner.Token tt = li.get(0).getBeginToken().getPrevious(); tt != null; tt = tt.getPrevious()) {
                    if (tt.getMorph()._getClass().isPreposition() || tt.isComma()) 
                        continue;
                    com.pullenti.ner.Referent r = tt.getReferent();
                    if (r == null) 
                        break;
                    if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "DATE") || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "DATERANGE")) 
                        continue;
                    if (r instanceof com.pullenti.ner.geo.GeoReferent) {
                        if (!((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.geo.GeoReferent.class)).isState()) {
                            if (geos == null) 
                                geos = new java.util.ArrayList<com.pullenti.ner.geo.GeoReferent>();
                            geos.add((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.geo.GeoReferent.class));
                            hasGeo = true;
                        }
                    }
                    break;
                }
                if (!hasGeo) {
                    if (streets.size() > 0 && streets.get(0).refTokenIsGsk && streets.get(0).refToken != null) {
                    }
                    else 
                        return null;
                }
            }
        }
        for (i = 0; i < li.size(); i++) {
            if (li.get(i).getTyp() == AddressItemType.PREFIX) 
                li.get(i).tag = addr;
            else if (li.get(i).tag == null) {
                if (li.get(i).isNewlineBefore() && i > i0) {
                    boolean stop = false;
                    for (j = i + 1; j < li.size(); j++) {
                        if (li.get(j).getTyp() == AddressItemType.STREET) {
                            stop = true;
                            break;
                        }
                    }
                    if (stop) 
                        break;
                }
                if (li.get(i).getTyp() == AddressItemType.COUNTRY || li.get(i).getTyp() == AddressItemType.REGION || li.get(i).getTyp() == AddressItemType.CITY) {
                    if (geos == null) 
                        geos = new java.util.ArrayList<com.pullenti.ner.geo.GeoReferent>();
                    if (!geos.contains((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(i).referent, com.pullenti.ner.geo.GeoReferent.class))) 
                        geos.add((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(i).referent, com.pullenti.ner.geo.GeoReferent.class));
                    if (li.get(i).getTyp() != AddressItemType.COUNTRY) {
                        if (li.get(i).detailType != com.pullenti.ner.address.AddressDetailType.UNDEFINED && addr.getDetail() == com.pullenti.ner.address.AddressDetailType.UNDEFINED) {
                            addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_DETAIL, li.get(i).detailType.toString().toUpperCase(), false, 0).setTag(li.get(i));
                            if (li.get(i).detailMeters > 0) 
                                addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_DETAILPARAM, (((Integer)li.get(i).detailMeters).toString() + "м"), false, 0);
                        }
                    }
                    li.get(i).tag = addr;
                }
                else if (li.get(i).getTyp() == AddressItemType.ZIP) {
                    if (addr.getZip() != null) 
                        break;
                    addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_ZIP, li.get(i).value, false, 0).setTag(li.get(i));
                    li.get(i).tag = addr;
                }
                else if (li.get(i).getTyp() == AddressItemType.POSTOFFICEBOX) {
                    if (addr.getPostOfficeBox() != null) 
                        break;
                    addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_POSTOFFICEBOX, li.get(i).value, false, 0).setTag(li.get(i));
                    li.get(i).tag = addr;
                }
                else if (li.get(i).getTyp() == AddressItemType.CSP) {
                    if (addr.getCSP() != null) 
                        break;
                    addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_CSP, li.get(i).value, false, 0).setTag(li.get(i));
                    li.get(i).tag = addr;
                }
                else if (li.get(i).getTyp() == AddressItemType.NUMBER && li.get(i).isDigit() && li.get(i).value.length() == 6) {
                    if (((i + 1) < li.size()) && li.get(i + 1).getTyp() == AddressItemType.CITY) {
                        if (addr.getZip() != null) 
                            break;
                        addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_ZIP, li.get(i).value, false, 0).setTag(li.get(i));
                        li.get(i).tag = addr;
                    }
                }
                else 
                    break;
            }
        }
        com.pullenti.ner.Token t0 = null;
        com.pullenti.ner.Token t1 = null;
        for (i = 0; i < li.size(); i++) {
            if (li.get(i).tag != null) {
                t0 = li.get(i).getBeginToken();
                break;
            }
        }
        for (i = li.size() - 1; i >= 0; i--) {
            if (li.get(i).tag != null) {
                t1 = li.get(i).getEndToken();
                break;
            }
        }
        if (t0 == null || t1 == null) 
            return null;
        if (addr.getSlots().size() == 0) {
            int pureStreets = 0;
            int gsks = 0;
            for (AddressItemToken s : streets) {
                if (s.refToken != null && s.refTokenIsGsk) 
                    gsks++;
                else if (s.refToken == null) 
                    pureStreets++;
            }
            if ((pureStreets + gsks) == 0 && streets.size() > 0) {
                if (((details.size() > 0 || cross)) && geos != null) {
                }
                else 
                    addr = null;
            }
            else if (streets.size() < 2) {
                if ((streets.size() == 1 && geos != null && geos.size() > 0) && ((streets.get(0).refToken == null || streets.get(0).refTokenIsGsk))) {
                }
                else if (details.size() > 0 && geos != null && streets.size() == 0) {
                }
                else 
                    addr = null;
            }
        }
        if (addr != null) {
            if (cross) 
                addr.setDetail(com.pullenti.ner.address.AddressDetailType.CROSS);
            else if (details.size() > 0) {
                com.pullenti.ner.address.AddressDetailType ty = com.pullenti.ner.address.AddressDetailType.UNDEFINED;
                String par = null;
                for (AddressItemToken v : details) {
                    if ((v.referent instanceof com.pullenti.ner.address.StreetReferent) && ((com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(v.referent, com.pullenti.ner.address.StreetReferent.class)).getKind() == com.pullenti.ner.address.StreetKind.ORG) {
                        com.pullenti.ner.Referent _org = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(v.referent.getSlotValue(com.pullenti.ner.address.StreetReferent.ATTR_REF), com.pullenti.ner.Referent.class);
                        if (_org != null && com.pullenti.unisharp.Utils.stringsEq(_org.getTypeName(), "ORGANIZATION")) {
                            addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_DETAILREF, _org, false, 0);
                            v.referent.moveExtReferent(addr, _org);
                        }
                    }
                    else if (v.referent != null) {
                        addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_DETAILREF, v.referent, false, 0);
                        if (v.refToken != null) 
                            addr.addExtReferent(v.refToken);
                        com.pullenti.ner.geo.GeoReferent gg = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(v.referent, com.pullenti.ner.geo.GeoReferent.class);
                        if (gg != null && gg.getHigher() == null) {
                            if (geos.size() > 0 && com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigher(geos.get(0), gg, null, null)) 
                                gg.setHigher(geos.get(0));
                        }
                    }
                    if (ty == com.pullenti.ner.address.AddressDetailType.UNDEFINED || v.detailMeters > 0) {
                        if (v.detailMeters > 0) 
                            par = (((Integer)v.detailMeters).toString() + "м");
                        ty = v.detailType;
                    }
                }
                if (ty != com.pullenti.ner.address.AddressDetailType.UNDEFINED) 
                    addr.setDetail(ty);
                if (par != null) 
                    addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_DETAILPARAM, par, false, 0);
                else 
                    for (AddressItemToken v : li) {
                        if (v.tag != null && v.detailMeters > 0) {
                            addr.addSlot(com.pullenti.ner.address.AddressReferent.ATTR_DETAILPARAM, (((Integer)v.detailMeters).toString() + "м"), false, 0);
                            break;
                        }
                    }
            }
        }
        if (geos == null && streets.size() > 0 && !streets.get(0).isStreetRoad()) {
            int cou = 0;
            for (com.pullenti.ner.Token tt = t0.getPrevious(); tt != null && (cou < 200); tt = tt.getPrevious(),cou++) {
                if (tt.isNewlineAfter()) 
                    cou += 10;
                com.pullenti.ner.Referent r = tt.getReferent();
                if ((r instanceof com.pullenti.ner.geo.GeoReferent) && !((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.geo.GeoReferent.class)).isState()) {
                    geos = new java.util.ArrayList<com.pullenti.ner.geo.GeoReferent>();
                    geos.add((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.geo.GeoReferent.class));
                    break;
                }
                if (r instanceof com.pullenti.ner.address.StreetReferent) {
                    java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> ggg = ((com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.address.StreetReferent.class)).getGeos();
                    if (ggg.size() > 0) {
                        geos = new java.util.ArrayList<com.pullenti.ner.geo.GeoReferent>(ggg);
                        break;
                    }
                }
                if (r instanceof com.pullenti.ner.address.AddressReferent) {
                    java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> ggg = ((com.pullenti.ner.address.AddressReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.address.AddressReferent.class)).getGeos();
                    if (ggg.size() > 0) {
                        geos = new java.util.ArrayList<com.pullenti.ner.geo.GeoReferent>(ggg);
                        break;
                    }
                }
            }
        }
        com.pullenti.ner.ReferentToken rt;
        com.pullenti.ner.address.StreetReferent sr0 = null;
        for (int ii = 0; ii < streets.size(); ii++) {
            AddressItemToken s = streets.get(ii);
            com.pullenti.ner.address.StreetReferent sr = (com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(s.referent, com.pullenti.ner.address.StreetReferent.class);
            if (geos != null && sr != null && sr.getGeos().size() == 0) {
                for (com.pullenti.ner.geo.GeoReferent gr : geos) {
                    if (gr.isCity() || ((gr.getHigher() != null && gr.getHigher().isCity())) || ((gr.isRegion() && sr.getKind() != com.pullenti.ner.address.StreetKind.UNDEFINED))) {
                        sr.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_GEO, gr, true, 0);
                        if (li.get(0).referent == gr) 
                            streets.get(0).setBeginToken(li.get(0).getBeginToken());
                        for (int jj = ii + 1; jj < streets.size(); jj++) {
                            if (streets.get(jj).referent instanceof com.pullenti.ner.address.StreetReferent) 
                                streets.get(jj).referent.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_GEO, gr, false, 0);
                        }
                        geos.remove(gr);
                        break;
                    }
                    else if (gr.isRegion()) {
                        boolean ok = false;
                        if ((sr.getKind() == com.pullenti.ner.address.StreetKind.RAILWAY || sr.getKind() == com.pullenti.ner.address.StreetKind.ROAD || sr.getKind() == com.pullenti.ner.address.StreetKind.AREA) || sr.getKind() == com.pullenti.ner.address.StreetKind.SPEC) 
                            ok = true;
                        else 
                            for (String v : gr.getTyps()) {
                                if (com.pullenti.unisharp.Utils.stringsEq(v, "муниципальный округ") || com.pullenti.unisharp.Utils.stringsEq(v, "городской округ")) 
                                    ok = true;
                            }
                        if (ok) {
                            if (li.get(0).referent == gr) 
                                streets.get(0).setBeginToken(li.get(0).getBeginToken());
                            sr.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_GEO, gr, true, 0);
                            geos.remove(gr);
                            break;
                        }
                    }
                }
            }
            if (sr != null && sr.getGeos().size() == 0) {
                if (sr0 != null) {
                    for (com.pullenti.ner.geo.GeoReferent g : sr0.getGeos()) {
                        sr.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_GEO, g, false, 0);
                    }
                }
                sr0 = sr;
            }
            if (s.referent != null && s.referent.findSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, "НЕТ", true) != null) {
                for (com.pullenti.ner.Slot ss : s.referent.getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(ss.getTypeName(), com.pullenti.ner.address.StreetReferent.ATTR_GEO)) 
                        addr.addReferent((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(ss.getValue(), com.pullenti.ner.Referent.class));
                }
            }
            else {
                if (sr != null && ii > 0 && (streets.get(ii - 1).referent instanceof com.pullenti.ner.address.StreetReferent)) {
                    com.pullenti.ner.address.StreetKind ki = ((com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(streets.get(ii - 1).referent, com.pullenti.ner.address.StreetReferent.class)).getKind();
                    if (ki != sr.getKind() || ki == com.pullenti.ner.address.StreetKind.AREA || ki == com.pullenti.ner.address.StreetKind.ORG) {
                        if ((sr.getKind() == com.pullenti.ner.address.StreetKind.AREA || ki == com.pullenti.ner.address.StreetKind.AREA || ki == com.pullenti.ner.address.StreetKind.RAILWAY) || ki == com.pullenti.ner.address.StreetKind.ROAD || ((ki == com.pullenti.ner.address.StreetKind.ORG && sr.getKind() == com.pullenti.ner.address.StreetKind.UNDEFINED))) {
                            sr.setHigher((com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(streets.get(ii - 1).referent, com.pullenti.ner.address.StreetReferent.class));
                            sr.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_GEO, null, true, 0);
                            if (addr != null) {
                                com.pullenti.ner.Slot slo = addr.findSlot(com.pullenti.ner.address.AddressReferent.ATTR_STREET, null, true);
                                if (slo != null) 
                                    addr.getSlots().remove(slo);
                            }
                            s.setBeginToken(t0);
                        }
                    }
                }
                if (addr != null) 
                    addr.moveExtReferent(s.referent, null);
                s.referent = ad.registerReferent(s.referent);
                if (addr != null) 
                    addr.addReferent(s.referent);
                for (com.pullenti.ner.Token tt = s.getBeginToken().getPrevious(); tt != null && tt.getBeginChar() >= t0.getBeginChar(); tt = tt.getPrevious()) {
                    com.pullenti.ner.geo.GeoReferent g = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                    if (g == null || sr == null) 
                        continue;
                    for (com.pullenti.ner.geo.GeoReferent gg : sr.getGeos()) {
                        if (gg.getTopHigher() == g.getTopHigher()) 
                            s.setBeginToken(tt);
                    }
                }
                t = (rt = new com.pullenti.ner.ReferentToken(s.referent, s.getBeginToken(), s.getEndToken(), null));
                t.kit.embedToken(rt);
                if (s.getBeginChar() == t0.getBeginChar()) 
                    t0 = rt;
                if (s.getEndChar() == t1.getEndChar()) 
                    t1 = rt;
            }
        }
        if (addr != null) {
            boolean ok = false;
            for (com.pullenti.ner.Slot s : addr.getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsNe(s.getTypeName(), com.pullenti.ner.address.AddressReferent.ATTR_DETAIL)) 
                    ok = true;
            }
            if (!ok) 
                addr = null;
        }
        if (addr == null) 
            return t;
        if (geos != null && geos.size() > 0) {
            if ((geos.size() == 1 && geos.get(0).isRegion() && streets.size() == 1) && streets.get(0).refToken != null) {
            }
            if (streets.size() == 1 && streets.get(0).referent != null) {
                for (com.pullenti.ner.Slot s : streets.get(0).referent.getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.address.StreetReferent.ATTR_GEO) && (s.getValue() instanceof com.pullenti.ner.geo.GeoReferent)) {
                        int k = 0;
                        for (com.pullenti.ner.geo.GeoReferent gg = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.geo.GeoReferent.class); gg != null && (k < 5); gg = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(gg.getParentReferent(), com.pullenti.ner.geo.GeoReferent.class),k++) {
                            for (int ii = geos.size() - 1; ii >= 0; ii--) {
                                if (geos.get(ii) == gg) {
                                    geos.remove(ii);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            while (geos.size() >= 2) {
                if (geos.get(1).getHigher() == null && com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigher(geos.get(0), geos.get(1), null, null)) {
                    geos.get(1).setHigher(geos.get(0));
                    geos.remove(0);
                }
                else if (geos.get(0).getHigher() == null && com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigher(geos.get(1), geos.get(0), null, null)) {
                    geos.get(0).setHigher(geos.get(1));
                    geos.remove(1);
                }
                else if (geos.get(1).getHigher() != null && geos.get(1).getHigher().getHigher() == null && com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigher(geos.get(0), geos.get(1).getHigher(), null, null)) {
                    geos.get(1).getHigher().setHigher(geos.get(0));
                    geos.remove(0);
                }
                else if (geos.get(0).getHigher() != null && geos.get(0).getHigher().getHigher() == null && com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigher(geos.get(1), geos.get(0).getHigher(), null, null)) {
                    geos.get(0).getHigher().setHigher(geos.get(1));
                    geos.remove(1);
                }
                else 
                    break;
            }
            for (com.pullenti.ner.geo.GeoReferent g : geos) {
                addr.addReferent(g);
            }
        }
        boolean ok1 = false;
        for (com.pullenti.ner.Slot s : addr.getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsNe(s.getTypeName(), com.pullenti.ner.address.AddressReferent.ATTR_STREET)) {
                ok1 = true;
                break;
            }
        }
        if (!ok1) 
            return t;
        if (addr.getHouse() != null && addr.getCorpus() == null && addr.findSlot(com.pullenti.ner.address.AddressReferent.ATTR_STREET, null, true) == null) {
            if (geos != null && geos.size() > 0 && geos.get(0).findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_NAME, "ЗЕЛЕНОГРАД", true) != null) {
                addr.setCorpus(addr.getHouse());
                addr.setHouse(null);
            }
        }
        rt = new com.pullenti.ner.ReferentToken(ad.registerReferent(addr), t0, t1, null);
        t.kit.embedToken(rt);
        t = rt;
        if ((t.getNext() != null && ((t.getNext().isComma() || t.getNext().isChar(';'))) && (t.getNext().getWhitespacesAfterCount() < 2)) && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
            AddressItemToken last = null;
            for (AddressItemToken ll : li) {
                if (ll.tag != null) 
                    last = ll;
            }
            String attrName = null;
            if (last == null) 
                return t;
            if (last.getTyp() == AddressItemType.HOUSE) 
                attrName = com.pullenti.ner.address.AddressReferent.ATTR_HOUSE;
            else if (last.getTyp() == AddressItemType.CORPUS) 
                attrName = com.pullenti.ner.address.AddressReferent.ATTR_CORPUS;
            else if (last.getTyp() == AddressItemType.BUILDING) 
                attrName = com.pullenti.ner.address.AddressReferent.ATTR_BUILDING;
            else if (last.getTyp() == AddressItemType.FLAT) 
                attrName = com.pullenti.ner.address.AddressReferent.ATTR_FLAT;
            else if (last.getTyp() == AddressItemType.PAVILION) 
                attrName = com.pullenti.ner.address.AddressReferent.ATTR_PAVILION;
            else if (last.getTyp() == AddressItemType.PLOT) 
                attrName = com.pullenti.ner.address.AddressReferent.ATTR_PLOT;
            else if (last.getTyp() == AddressItemType.FIELD) 
                attrName = com.pullenti.ner.address.AddressReferent.ATTR_FIELD;
            else if (last.getTyp() == AddressItemType.BOX) 
                attrName = com.pullenti.ner.address.AddressReferent.ATTR_BOX;
            else if (last.getTyp() == AddressItemType.POTCH) 
                attrName = com.pullenti.ner.address.AddressReferent.ATTR_PORCH;
            else if (last.getTyp() == AddressItemType.BLOCK) 
                attrName = com.pullenti.ner.address.AddressReferent.ATTR_BLOCK;
            else if (last.getTyp() == AddressItemType.OFFICE) 
                attrName = com.pullenti.ner.address.AddressReferent.ATTR_OFFICE;
            if (attrName != null) {
                for (t = t.getNext().getNext(); t != null; t = t.getNext()) {
                    if (!(t instanceof com.pullenti.ner.NumberToken)) 
                        break;
                    com.pullenti.ner.address.AddressReferent addr1 = (com.pullenti.ner.address.AddressReferent)com.pullenti.unisharp.Utils.cast(addr.clone(), com.pullenti.ner.address.AddressReferent.class);
                    addr1.getOccurrence().clear();
                    addr1.addSlot(attrName, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getValue().toString(), true, 0);
                    rt = new com.pullenti.ner.ReferentToken(ad.registerReferent(addr1), t, t, null);
                    t.kit.embedToken(rt);
                    t = rt;
                    if ((t.getNext() != null && ((t.getNext().isComma() || t.getNext().isChar(';'))) && (t.getNext().getWhitespacesAfterCount() < 2)) && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                    }
                    else 
                        break;
                }
            }
        }
        return t;
    }
    public AddressDefineHelper() {
    }
}
