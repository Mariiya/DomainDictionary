/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner._org.internal;

public class OrgItemTypeTermin extends com.pullenti.ner.core.Termin {

    public OrgItemTypeTermin(String s, com.pullenti.morph.MorphLang _lang, com.pullenti.ner._org.OrgProfile p1, com.pullenti.ner._org.OrgProfile p2) {
        super(s, _lang, false);
        if (p1 != com.pullenti.ner._org.OrgProfile.UNDEFINED) 
            profiles.add(p1);
        if (p2 != com.pullenti.ner._org.OrgProfile.UNDEFINED) 
            profiles.add(p2);
    }

    public OrgItemTypeTyp getTyp() {
        if (isPurePrefix) 
            return OrgItemTypeTyp.PREFIX;
        return m_Typ;
    }

    public OrgItemTypeTyp setTyp(OrgItemTypeTyp value) {
        if (value == OrgItemTypeTyp.PREFIX) {
            isPurePrefix = true;
            m_Typ = OrgItemTypeTyp.ORG;
        }
        else {
            m_Typ = value;
            if (m_Typ == OrgItemTypeTyp.DEP || m_Typ == OrgItemTypeTyp.DEPADD) {
                if (!profiles.contains(com.pullenti.ner._org.OrgProfile.UNIT)) 
                    profiles.add(com.pullenti.ner._org.OrgProfile.UNIT);
            }
        }
        return value;
    }


    private OrgItemTypeTyp m_Typ = OrgItemTypeTyp.UNDEFINED;

    public boolean mustBePartofName = false;

    public boolean isPurePrefix;

    public boolean canBeNormalDep;

    public boolean canHasNumber;

    public boolean canHasSingleName;

    public boolean canHasLatinName;

    public boolean mustHasCapitalName;

    public boolean isTop;

    public boolean canBeSingleGeo;

    public boolean isDoubtWord;

    public float coeff;

    public java.util.ArrayList<com.pullenti.ner._org.OrgProfile> profiles = new java.util.ArrayList<com.pullenti.ner._org.OrgProfile>();

    public com.pullenti.ner._org.OrgProfile getProfile() {
        return com.pullenti.ner._org.OrgProfile.UNDEFINED;
    }

    public com.pullenti.ner._org.OrgProfile setProfile(com.pullenti.ner._org.OrgProfile value) {
        profiles.add(value);
        return value;
    }


    private void copyFrom(OrgItemTypeTermin it) {
        com.pullenti.unisharp.Utils.addToArrayList(profiles, it.profiles);
        isPurePrefix = it.isPurePrefix;
        canBeNormalDep = it.canBeNormalDep;
        canHasNumber = it.canHasNumber;
        canHasSingleName = it.canHasSingleName;
        canHasLatinName = it.canHasLatinName;
        mustBePartofName = it.mustBePartofName;
        mustHasCapitalName = it.mustHasCapitalName;
        isTop = it.isTop;
        canBeNormalDep = it.canBeNormalDep;
        canBeSingleGeo = it.canBeSingleGeo;
        isDoubtWord = it.isDoubtWord;
        coeff = it.coeff;
    }

    public static java.util.ArrayList<OrgItemTypeTermin> deserializeSrc(org.w3c.dom.Node xml, OrgItemTypeTermin set) throws Exception, NumberFormatException {
        java.util.ArrayList<OrgItemTypeTermin> res = new java.util.ArrayList<OrgItemTypeTermin>();
        boolean isSet = com.pullenti.unisharp.Utils.stringsEq(com.pullenti.unisharp.Utils.getXmlLocalName(xml), "set");
        if (isSet) 
            res.add((set = new OrgItemTypeTermin(null, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED)));
        if (xml.getAttributes() == null) 
            return res;
        for (org.w3c.dom.Node a : (new com.pullenti.unisharp.XmlAttrListWrapper(xml.getAttributes())).arr) {
            String nam = com.pullenti.unisharp.Utils.getXmlLocalName(a);
            if (!nam.startsWith("name")) 
                continue;
            com.pullenti.morph.MorphLang _lang = com.pullenti.morph.MorphLang.RU;
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "nameUa")) 
                _lang = com.pullenti.morph.MorphLang.UA;
            else if (com.pullenti.unisharp.Utils.stringsEq(nam, "nameEn")) 
                _lang = com.pullenti.morph.MorphLang.EN;
            OrgItemTypeTermin it = null;
            for (String s : com.pullenti.unisharp.Utils.split(a.getNodeValue(), String.valueOf(';'), false)) {
                if (!com.pullenti.unisharp.Utils.isNullOrEmpty(s)) {
                    if (it == null) {
                        res.add((it = new OrgItemTypeTermin(s, _lang, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED)));
                        if (set != null) 
                            it.copyFrom(set);
                    }
                    else 
                        it.addVariant(s, false);
                }
            }
        }
        for (org.w3c.dom.Node a : (new com.pullenti.unisharp.XmlAttrListWrapper(xml.getAttributes())).arr) {
            String nam = com.pullenti.unisharp.Utils.getXmlLocalName(a);
            if (nam.startsWith("name")) 
                continue;
            if (nam.startsWith("abbr")) {
                com.pullenti.morph.MorphLang _lang = com.pullenti.morph.MorphLang.RU;
                if (com.pullenti.unisharp.Utils.stringsEq(nam, "abbrUa")) 
                    _lang = com.pullenti.morph.MorphLang.UA;
                else if (com.pullenti.unisharp.Utils.stringsEq(nam, "abbrEn")) 
                    _lang = com.pullenti.morph.MorphLang.EN;
                for (OrgItemTypeTermin r : res) {
                    if (r.lang.equals(_lang)) 
                        r.acronym = a.getNodeValue();
                }
                continue;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "profile")) {
                java.util.ArrayList<com.pullenti.ner._org.OrgProfile> li = new java.util.ArrayList<com.pullenti.ner._org.OrgProfile>();
                for (String s : com.pullenti.unisharp.Utils.split(a.getNodeValue(), String.valueOf(';'), false)) {
                    try {
                        com.pullenti.ner._org.OrgProfile p = (com.pullenti.ner._org.OrgProfile)com.pullenti.ner._org.OrgProfile.of(s);
                        if (p != com.pullenti.ner._org.OrgProfile.UNDEFINED) 
                            li.add(p);
                    } catch (Exception ex) {
                    }
                }
                for (OrgItemTypeTermin r : res) {
                    r.profiles = li;
                }
                continue;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "coef")) {
                float v = com.pullenti.unisharp.Utils.parseFloat(a.getNodeValue(), null);
                for (OrgItemTypeTermin r : res) {
                    r.coeff = v;
                }
                continue;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "partofname")) {
                for (OrgItemTypeTermin r : res) {
                    r.mustBePartofName = com.pullenti.unisharp.Utils.stringsEq(a.getNodeValue(), "true");
                }
                continue;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "top")) {
                for (OrgItemTypeTermin r : res) {
                    r.isTop = com.pullenti.unisharp.Utils.stringsEq(a.getNodeValue(), "true");
                }
                continue;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "geo")) {
                for (OrgItemTypeTermin r : res) {
                    r.canBeSingleGeo = com.pullenti.unisharp.Utils.stringsEq(a.getNodeValue(), "true");
                }
                continue;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "purepref")) {
                for (OrgItemTypeTermin r : res) {
                    r.isPurePrefix = com.pullenti.unisharp.Utils.stringsEq(a.getNodeValue(), "true");
                }
                continue;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "number")) {
                for (OrgItemTypeTermin r : res) {
                    r.canHasNumber = com.pullenti.unisharp.Utils.stringsEq(a.getNodeValue(), "true");
                }
                continue;
            }
            throw new Exception("Unknown Org Type Tag: " + a.getNodeName());
        }
        return res;
    }

    public static OrgItemTypeTermin _new1996(String _arg1, com.pullenti.morph.MorphLang _arg2, com.pullenti.ner._org.OrgProfile _arg3, boolean _arg4, float _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, _arg3, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.canHasLatinName = _arg4;
        res.coeff = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2001(String _arg1, boolean _arg2, float _arg3) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.canHasLatinName = _arg2;
        res.coeff = _arg3;
        return res;
    }

    public static OrgItemTypeTermin _new2005(String _arg1, OrgItemTypeTyp _arg2, float _arg3, boolean _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.coeff = _arg3;
        res.canHasLatinName = _arg4;
        return res;
    }

    public static OrgItemTypeTermin _new2019(String _arg1, com.pullenti.morph.MorphLang _arg2, com.pullenti.ner._org.OrgProfile _arg3, float _arg4, OrgItemTypeTyp _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, _arg3, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg4;
        res.setTyp(_arg5);
        res.isTop = _arg6;
        res.canBeSingleGeo = _arg7;
        return res;
    }

    public static OrgItemTypeTermin _new2022(String _arg1, OrgItemTypeTyp _arg2, com.pullenti.ner._org.OrgProfile _arg3, float _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.setProfile(_arg3);
        res.coeff = _arg4;
        return res;
    }

    public static OrgItemTypeTermin _new2023(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, com.pullenti.ner._org.OrgProfile _arg4, float _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.coeff = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2024(String _arg1, OrgItemTypeTyp _arg2, com.pullenti.ner._org.OrgProfile _arg3, float _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.setProfile(_arg3);
        res.coeff = _arg4;
        res.canBeSingleGeo = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2027(String _arg1, float _arg2, OrgItemTypeTyp _arg3, com.pullenti.ner._org.OrgProfile _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        return res;
    }

    public static OrgItemTypeTermin _new2028(String _arg1, float _arg2, OrgItemTypeTyp _arg3, com.pullenti.ner._org.OrgProfile _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.canBeNormalDep = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2029(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTypeTermin _new2030(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canBeSingleGeo = _arg4;
        return res;
    }

    public static OrgItemTypeTermin _new2031(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canBeSingleGeo = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2035(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.isTop = _arg4;
        res.canBeSingleGeo = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2037(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.isTop = _arg5;
        res.canBeSingleGeo = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2038(String _arg1, float _arg2, OrgItemTypeTyp _arg3) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        return res;
    }

    public static OrgItemTypeTermin _new2040(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        return res;
    }

    public static OrgItemTypeTermin _new2043(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTypeTermin _new2045(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canBeSingleGeo = _arg4;
        res.canBeNormalDep = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTypeTermin _new2047(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canBeSingleGeo = _arg4;
        res.canHasNumber = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTypeTermin _new2048(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canBeSingleGeo = _arg4;
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTypeTermin _new2049(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canBeSingleGeo = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTypeTermin _new2051(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5, boolean _arg6, com.pullenti.ner._org.OrgProfile _arg7) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canBeSingleGeo = _arg5;
        res.canHasNumber = _arg6;
        res.setProfile(_arg7);
        return res;
    }

    public static OrgItemTypeTermin _new2058(String _arg1, float _arg2, String _arg3, OrgItemTypeTyp _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.acronym = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTypeTermin _new2059(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2060(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTypeTermin _new2063(String _arg1, float _arg2, com.pullenti.morph.MorphLang _arg3, OrgItemTypeTyp _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.lang = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTypeTermin _new2072(String _arg1, float _arg2, OrgItemTypeTyp _arg3, com.pullenti.ner._org.OrgProfile _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.canBeSingleGeo = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2073(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.isDoubtWord = _arg4;
        return res;
    }

    public static OrgItemTypeTermin _new2074(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.isDoubtWord = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2077(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        return res;
    }

    public static OrgItemTypeTermin _new2082(String _arg1, OrgItemTypeTyp _arg2, String _arg3, com.pullenti.ner._org.OrgProfile _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.setProfile(_arg4);
        res.canBeSingleGeo = _arg5;
        res.canHasNumber = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2085(String _arg1, float _arg2, OrgItemTypeTyp _arg3, com.pullenti.ner._org.OrgProfile _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.canHasNumber = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2086(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, com.pullenti.ner._org.OrgProfile _arg4, OrgItemTypeTyp _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setProfile(_arg4);
        res.setTyp(_arg5);
        res.canHasNumber = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2089(String _arg1, float _arg2, OrgItemTypeTyp _arg3, com.pullenti.ner._org.OrgProfile _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.canHasNumber = _arg5;
        res.canHasLatinName = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2095(String _arg1, float _arg2, String _arg3, OrgItemTypeTyp _arg4, com.pullenti.ner._org.OrgProfile _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.acronym = _arg3;
        res.setTyp(_arg4);
        res.setProfile(_arg5);
        res.canBeSingleGeo = _arg6;
        res.canHasNumber = _arg7;
        return res;
    }

    public static OrgItemTypeTermin _new2096(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5, boolean _arg6, boolean _arg7, com.pullenti.ner._org.OrgProfile _arg8) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canBeNormalDep = _arg4;
        res.canBeSingleGeo = _arg5;
        res.canHasSingleName = _arg6;
        res.canHasLatinName = _arg7;
        res.setProfile(_arg8);
        return res;
    }

    public static OrgItemTypeTermin _new2102(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTypeTermin _new2114(String _arg1, float _arg2, String _arg3, OrgItemTypeTyp _arg4, boolean _arg5, boolean _arg6, com.pullenti.ner._org.OrgProfile _arg7) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.acronym = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.canBeSingleGeo = _arg6;
        res.setProfile(_arg7);
        return res;
    }

    public static OrgItemTypeTermin _new2116(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, com.pullenti.ner._org.OrgProfile _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.setProfile(_arg5);
        res.canHasLatinName = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2121(String _arg1, OrgItemTypeTyp _arg2, boolean _arg3) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.isDoubtWord = _arg3;
        return res;
    }

    public static OrgItemTypeTermin _new2124(String _arg1, float _arg2, OrgItemTypeTyp _arg3, String _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6, boolean _arg7) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasNumber = _arg5;
        res.setProfile(_arg6);
        res.canHasLatinName = _arg7;
        return res;
    }

    public static OrgItemTypeTermin _new2125(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, String _arg5, boolean _arg6, com.pullenti.ner._org.OrgProfile _arg7, boolean _arg8) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.acronym = _arg5;
        res.canHasNumber = _arg6;
        res.setProfile(_arg7);
        res.canHasLatinName = _arg8;
        return res;
    }

    public static OrgItemTypeTermin _new2126(String _arg1, float _arg2, OrgItemTypeTyp _arg3, String _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasNumber = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTypeTermin _new2138(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, String _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.acronym = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTypeTermin _new2139(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5, String _arg6, com.pullenti.ner._org.OrgProfile _arg7) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.acronym = _arg6;
        res.setProfile(_arg7);
        return res;
    }

    public static OrgItemTypeTermin _new2140(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        return res;
    }

    public static OrgItemTypeTermin _new2150(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5, boolean _arg6, com.pullenti.ner._org.OrgProfile _arg7) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.setProfile(_arg7);
        return res;
    }

    public static OrgItemTypeTermin _new2151(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5, boolean _arg6, boolean _arg7, com.pullenti.ner._org.OrgProfile _arg8) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.canHasLatinName = _arg6;
        res.canHasSingleName = _arg7;
        res.setProfile(_arg8);
        return res;
    }

    public static OrgItemTypeTermin _new2154(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5, boolean _arg6, boolean _arg7, com.pullenti.ner._org.OrgProfile _arg8) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.isTop = _arg4;
        res.canHasSingleName = _arg5;
        res.canHasLatinName = _arg6;
        res.canBeSingleGeo = _arg7;
        res.setProfile(_arg8);
        return res;
    }

    public static OrgItemTypeTermin _new2155(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5, boolean _arg6, boolean _arg7, boolean _arg8) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.isTop = _arg5;
        res.canHasSingleName = _arg6;
        res.canHasLatinName = _arg7;
        res.canBeSingleGeo = _arg8;
        return res;
    }

    public static OrgItemTypeTermin _new2159(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2160(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasNumber = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2161(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasNumber = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2162(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTypeTermin _new2163(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5, boolean _arg6, com.pullenti.ner._org.OrgProfile _arg7) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.setProfile(_arg7);
        return res;
    }

    public static OrgItemTypeTermin _new2164(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.mustBePartofName = _arg4;
        return res;
    }

    public static OrgItemTypeTermin _new2165(String _arg1, float _arg2, OrgItemTypeTyp _arg3, String _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setCanonicText(_arg4);
        return res;
    }

    public static OrgItemTypeTermin _new2167(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.mustBePartofName = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2168(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, String _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.setCanonicText(_arg5);
        return res;
    }

    public static OrgItemTypeTermin _new2174(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2175(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.canHasLatinName = _arg6;
        res.canHasSingleName = _arg7;
        return res;
    }

    public static OrgItemTypeTermin _new2178(String _arg1, float _arg2, OrgItemTypeTyp _arg3, String _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasNumber = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2180(String _arg1, OrgItemTypeTyp _arg2, float _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.coeff = _arg3;
        res.canBeSingleGeo = _arg4;
        res.canHasSingleName = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2181(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, float _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.setTyp(_arg3);
        res.coeff = _arg4;
        res.canBeSingleGeo = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2182(String _arg1, float _arg2, OrgItemTypeTyp _arg3, String _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.acronym = _arg4;
        return res;
    }

    public static OrgItemTypeTermin _new2183(String _arg1, float _arg2, OrgItemTypeTyp _arg3, String _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTypeTermin _new2185(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.isDoubtWord = _arg4;
        res.canHasNumber = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2186(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.isDoubtWord = _arg5;
        res.canHasNumber = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2187(String _arg1, float _arg2, String _arg3, OrgItemTypeTyp _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.acronym = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2188(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, String _arg4, OrgItemTypeTyp _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.acronym = _arg4;
        res.setTyp(_arg5);
        res.canHasNumber = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2193(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2200(String _arg1, OrgItemTypeTyp _arg2) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        return res;
    }

    public static OrgItemTypeTermin _new2201(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        return res;
    }

    public static OrgItemTypeTermin _new2203(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, boolean _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.isDoubtWord = _arg4;
        return res;
    }

    public static OrgItemTypeTermin _new2208(String _arg1, OrgItemTypeTyp _arg2, boolean _arg3, boolean _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.isDoubtWord = _arg3;
        res.canHasNumber = _arg4;
        return res;
    }

    public static OrgItemTypeTermin _new2209(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.isDoubtWord = _arg4;
        res.canHasNumber = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2210(String _arg1, OrgItemTypeTyp _arg2, float _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.coeff = _arg3;
        res.canHasNumber = _arg4;
        res.canHasSingleName = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2212(String _arg1, String _arg2, OrgItemTypeTyp _arg3, float _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.acronym = _arg2;
        res.setTyp(_arg3);
        res.coeff = _arg4;
        res.canHasNumber = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2213(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, float _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.coeff = _arg4;
        res.canHasNumber = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2214(String _arg1, String _arg2, OrgItemTypeTyp _arg3, boolean _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.acronym = _arg2;
        res.setTyp(_arg3);
        res.canBeNormalDep = _arg4;
        return res;
    }

    public static OrgItemTypeTermin _new2216(String _arg1, com.pullenti.morph.MorphLang _arg2, String _arg3, OrgItemTypeTyp _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.acronym = _arg3;
        res.setTyp(_arg4);
        res.canBeNormalDep = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2219(String _arg1, OrgItemTypeTyp _arg2, boolean _arg3) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canBeNormalDep = _arg3;
        return res;
    }

    public static OrgItemTypeTermin _new2220(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, boolean _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canBeNormalDep = _arg4;
        return res;
    }

    public static OrgItemTypeTermin _new2232(String _arg1, OrgItemTypeTyp _arg2, boolean _arg3, com.pullenti.ner._org.OrgProfile _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canBeNormalDep = _arg3;
        res.setProfile(_arg4);
        return res;
    }

    public static OrgItemTypeTermin _new2233(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, boolean _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canBeNormalDep = _arg4;
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTypeTermin _new2237(String _arg1, OrgItemTypeTyp _arg2, boolean _arg3, boolean _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasNumber = _arg3;
        res.isDoubtWord = _arg4;
        return res;
    }

    public static OrgItemTypeTermin _new2238(String _arg1, OrgItemTypeTyp _arg2, boolean _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasNumber = _arg3;
        res.isDoubtWord = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2239(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.isDoubtWord = _arg5;
        res.canHasLatinName = _arg6;
        res.canHasSingleName = _arg7;
        return res;
    }

    public static OrgItemTypeTermin _new2246(String _arg1, OrgItemTypeTyp _arg2, boolean _arg3) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasNumber = _arg3;
        return res;
    }

    public static OrgItemTypeTermin _new2247(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, boolean _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        return res;
    }

    public static OrgItemTypeTermin _new2248(String _arg1, OrgItemTypeTyp _arg2, com.pullenti.ner._org.OrgProfile _arg3, String _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.setProfile(_arg3);
        res.acronym = _arg4;
        return res;
    }

    public static OrgItemTypeTermin _new2249(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, com.pullenti.ner._org.OrgProfile _arg4, String _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.acronym = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2254(String _arg1, OrgItemTypeTyp _arg2, String _arg3, com.pullenti.ner._org.OrgProfile _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.setProfile(_arg4);
        return res;
    }

    public static OrgItemTypeTermin _new2255(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, String _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTypeTermin _new2259(String _arg1, OrgItemTypeTyp _arg2, com.pullenti.ner._org.OrgProfile _arg3) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.setProfile(_arg3);
        return res;
    }

    public static OrgItemTypeTermin _new2276(String _arg1, OrgItemTypeTyp _arg2, String _arg3) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        return res;
    }

    public static OrgItemTypeTermin _new2278(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, String _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.acronym = _arg4;
        return res;
    }

    public static OrgItemTypeTermin _new2368(String _arg1, OrgItemTypeTyp _arg2, String _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.acronymCanBeLower = _arg4;
        res.canBeSingleGeo = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2369(String _arg1, OrgItemTypeTyp _arg2, String _arg3, boolean _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.canHasLatinName = _arg4;
        return res;
    }

    public static OrgItemTypeTermin _new2372(String _arg1, OrgItemTypeTyp _arg2, boolean _arg3, String _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasLatinName = _arg3;
        res.acronym = _arg4;
        return res;
    }

    public static OrgItemTypeTermin _new2373(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, boolean _arg4, String _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.acronym = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2376(String _arg1, OrgItemTypeTyp _arg2, boolean _arg3) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasLatinName = _arg3;
        return res;
    }

    public static OrgItemTypeTermin _new2379(String _arg1, OrgItemTypeTyp _arg2, boolean _arg3, String _arg4, String _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasLatinName = _arg3;
        res.acronym = _arg4;
        res.acronymSmart = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2390(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, boolean _arg4, String _arg5, String _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.acronym = _arg5;
        res.acronymSmart = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2408(String _arg1, OrgItemTypeTyp _arg2, String _arg3, String _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.acronymSmart = _arg4;
        return res;
    }

    public static OrgItemTypeTermin _new2411(String _arg1, OrgItemTypeTyp _arg2, boolean _arg3, String _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasLatinName = _arg3;
        res.acronym = _arg4;
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTypeTermin _new2412(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, boolean _arg4, String _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.acronym = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTypeTermin _new2415(String _arg1, OrgItemTypeTyp _arg2, boolean _arg3, com.pullenti.ner._org.OrgProfile _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasLatinName = _arg3;
        res.setProfile(_arg4);
        return res;
    }

    public static OrgItemTypeTermin _new2416(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, boolean _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTypeTermin _new2418(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, boolean _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        return res;
    }

    public static OrgItemTypeTermin _new2422(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, String _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasLatinName = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2425(String _arg1, OrgItemTypeTyp _arg2, boolean _arg3, boolean _arg4, String _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasLatinName = _arg3;
        res.canHasNumber = _arg4;
        res.acronym = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2426(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5, String _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasNumber = _arg5;
        res.acronym = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2431(String _arg1, OrgItemTypeTyp _arg2, String _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.canHasLatinName = _arg4;
        res.canHasNumber = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2445(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, String _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasNumber = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2446(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, String _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2447(String _arg1, OrgItemTypeTyp _arg2, com.pullenti.ner._org.OrgProfile _arg3, boolean _arg4, float _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.setProfile(_arg3);
        res.canHasLatinName = _arg4;
        res.coeff = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2448(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasSingleName = _arg4;
        res.canHasLatinName = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2449(String _arg1, float _arg2, OrgItemTypeTyp _arg3, com.pullenti.ner._org.OrgProfile _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.canHasSingleName = _arg5;
        res.canHasLatinName = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2450(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, com.pullenti.ner._org.OrgProfile _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.setProfile(_arg5);
        res.canHasSingleName = _arg6;
        res.canHasLatinName = _arg7;
        return res;
    }

    public static OrgItemTypeTermin _new2451(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasSingleName = _arg5;
        res.canHasLatinName = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2452(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasSingleName = _arg4;
        res.canHasLatinName = _arg5;
        res.mustHasCapitalName = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2453(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasSingleName = _arg5;
        res.canHasLatinName = _arg6;
        res.mustHasCapitalName = _arg7;
        return res;
    }

    public static OrgItemTypeTermin _new2456(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canBeNormalDep = _arg4;
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTypeTermin _new2458(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canBeNormalDep = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTypeTermin _new2459(String _arg1, OrgItemTypeTyp _arg2, boolean _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasSingleName = _arg3;
        res.canHasLatinName = _arg4;
        res.isDoubtWord = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2461(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5, boolean _arg6, com.pullenti.ner._org.OrgProfile _arg7) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasSingleName = _arg4;
        res.canHasLatinName = _arg5;
        res.isDoubtWord = _arg6;
        res.setProfile(_arg7);
        return res;
    }

    public static OrgItemTypeTermin _new2462(String _arg1, OrgItemTypeTyp _arg2, boolean _arg3, boolean _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasSingleName = _arg3;
        res.canHasLatinName = _arg4;
        res.isDoubtWord = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTypeTermin _new2463(String _arg1, OrgItemTypeTyp _arg2, boolean _arg3, boolean _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasSingleName = _arg3;
        res.canHasLatinName = _arg4;
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTypeTermin _new2464(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasSingleName = _arg4;
        res.canHasLatinName = _arg5;
        res.isDoubtWord = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2465(String _arg1, OrgItemTypeTyp _arg2, float _arg3, boolean _arg4) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.coeff = _arg3;
        res.canHasSingleName = _arg4;
        return res;
    }

    public static OrgItemTypeTermin _new2466(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, float _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.coeff = _arg4;
        res.canHasSingleName = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2476(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2477(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2478(String _arg1, float _arg2, OrgItemTypeTyp _arg3, String _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.canBeSingleGeo = _arg7;
        return res;
    }

    public static OrgItemTypeTermin _new2479(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, String _arg5, boolean _arg6, boolean _arg7, boolean _arg8) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.acronym = _arg5;
        res.canHasLatinName = _arg6;
        res.canHasSingleName = _arg7;
        res.canBeSingleGeo = _arg8;
        return res;
    }

    public static OrgItemTypeTermin _new2486(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.mustHasCapitalName = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2487(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.mustHasCapitalName = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2488(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, float _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.coeff = _arg4;
        res.canHasLatinName = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2489(String _arg1, com.pullenti.morph.MorphLang _arg2, com.pullenti.ner._org.OrgProfile _arg3, OrgItemTypeTyp _arg4, float _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, _arg3, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg4);
        res.coeff = _arg5;
        res.canHasLatinName = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2497(String _arg1, com.pullenti.morph.MorphLang _arg2, com.pullenti.ner._org.OrgProfile _arg3, OrgItemTypeTyp _arg4, float _arg5, boolean _arg6, String _arg7) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, _arg3, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg4);
        res.coeff = _arg5;
        res.canHasLatinName = _arg6;
        res.acronym = _arg7;
        return res;
    }

    public static OrgItemTypeTermin _new2498(String _arg1, OrgItemTypeTyp _arg2, boolean _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasLatinName = _arg3;
        res.canHasSingleName = _arg4;
        res.mustHasCapitalName = _arg5;
        res.canHasNumber = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2499(String _arg1, com.pullenti.morph.MorphLang _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.mustHasCapitalName = _arg6;
        res.canHasNumber = _arg7;
        return res;
    }

    public static OrgItemTypeTermin _new2500(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5, boolean _arg6, com.pullenti.ner._org.OrgProfile _arg7) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.mustHasCapitalName = _arg6;
        res.setProfile(_arg7);
        return res;
    }

    public static OrgItemTypeTermin _new2501(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5, boolean _arg6, boolean _arg7, com.pullenti.ner._org.OrgProfile _arg8) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.mustHasCapitalName = _arg7;
        res.setProfile(_arg8);
        return res;
    }

    public static OrgItemTypeTermin _new2505(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.mustHasCapitalName = _arg7;
        return res;
    }

    public static OrgItemTypeTermin _new2506(String _arg1, float _arg2, OrgItemTypeTyp _arg3, String _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.mustHasCapitalName = _arg7;
        return res;
    }

    public static OrgItemTypeTermin _new2508(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.mustHasCapitalName = _arg6;
        res.canHasNumber = _arg7;
        return res;
    }

    public static OrgItemTypeTermin _new2510(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5, boolean _arg6, boolean _arg7, boolean _arg8) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.mustHasCapitalName = _arg7;
        res.canHasNumber = _arg8;
        return res;
    }

    public static OrgItemTypeTermin _new2513(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.canBeSingleGeo = _arg6;
        return res;
    }

    public static OrgItemTypeTermin _new2514(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.canBeSingleGeo = _arg7;
        return res;
    }

    public static OrgItemTypeTermin _new2522(String _arg1, float _arg2, String _arg3, OrgItemTypeTyp _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.acronym = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.canHasNumber = _arg7;
        return res;
    }

    public static OrgItemTypeTermin _new2523(String _arg1, String _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5, boolean _arg6, boolean _arg7, boolean _arg8) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.acronym = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.mustHasCapitalName = _arg7;
        res.canHasNumber = _arg8;
        return res;
    }

    public static OrgItemTypeTermin _new2528(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, OrgItemTypeTyp _arg4, boolean _arg5) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        return res;
    }

    public static OrgItemTypeTermin _new2529(String _arg1, float _arg2, OrgItemTypeTyp _arg3, boolean _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasNumber = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTypeTermin _new2533(String _arg1, float _arg2, boolean _arg3, OrgItemTypeTyp _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTypeTermin res = new OrgItemTypeTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.canBeNormalDep = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.canBeSingleGeo = _arg6;
        return res;
    }
    public OrgItemTypeTermin() {
        super();
    }
}
