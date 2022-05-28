/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner._org.internal;

public class OrgOwnershipHelper {

    public static boolean canBeHigher(com.pullenti.ner._org.OrganizationReferent higher, com.pullenti.ner._org.OrganizationReferent lower, boolean robust) {
        if (higher == null || lower == null || higher == lower) 
            return false;
        if (lower.getOwner() != null) 
            return false;
        com.pullenti.ner._org.OrganizationKind hk = higher.getKind();
        com.pullenti.ner._org.OrganizationKind lk = lower.getKind();
        if (higher.canBeEquals(lower, com.pullenti.ner.core.ReferentsEqualType.WITHINONETEXT)) 
            return false;
        if (lower.getHigher() == null && lower.findSlot(com.pullenti.ner._org.OrganizationReferent.ATTR_HIGHER, null, true) != null) 
            return false;
        java.util.Collection<String> hTyps = higher.getTypes();
        java.util.Collection<String> lTyps = lower.getTypes();
        if (hk != com.pullenti.ner._org.OrganizationKind.BANK) {
            for (String v : hTyps) {
                if (lTyps.contains(v)) 
                    return false;
            }
        }
        if (hk != com.pullenti.ner._org.OrganizationKind.DEPARTMENT && lk == com.pullenti.ner._org.OrganizationKind.DEPARTMENT) {
            if (_Contains(lTyps, "курс", null) || _Contains(lTyps, "группа", "група")) 
                return hk == com.pullenti.ner._org.OrganizationKind.STUDY || _Contains(hTyps, "институт", "інститут");
            if (_Contains(lTyps, "епархия", "єпархія") || _Contains(lTyps, "патриархия", "патріархія")) 
                return hk == com.pullenti.ner._org.OrganizationKind.CHURCH;
            if (hk == com.pullenti.ner._org.OrganizationKind.UNDEFINED) {
                if (_Contains(hTyps, "управление", "управління")) 
                    return false;
            }
            return true;
        }
        if (lower.containsProfile(com.pullenti.ner._org.OrgProfile.UNIT) || _Contains(lTyps, "department", null)) {
            if (!higher.containsProfile(com.pullenti.ner._org.OrgProfile.UNIT) && lk != com.pullenti.ner._org.OrganizationKind.DEPARTMENT) 
                return true;
        }
        if (_Contains(hTyps, "правительство", "уряд")) {
            if (lk == com.pullenti.ner._org.OrganizationKind.GOVENMENT) 
                return (((lTyps.contains("агентство") || lTyps.contains("федеральная служба") || lTyps.contains("федеральна служба")) || lTyps.contains("департамент") || lTyps.contains("комиссия")) || lTyps.contains("комитет") || lTyps.contains("комісія")) || lTyps.contains("комітет");
        }
        if (hk == com.pullenti.ner._org.OrganizationKind.GOVENMENT) {
            if (lk == com.pullenti.ner._org.OrganizationKind.GOVENMENT) {
                if (_Contains(lTyps, "комиссия", "комісія") || _Contains(lTyps, "инспекция", "інспекція") || _Contains(lTyps, "комитет", "комітет")) {
                    if ((!_Contains(hTyps, "комиссия", "комісія") && !_Contains(hTyps, "инспекция", "інспекція") && !_Contains(lTyps, "государственный комитет", null)) && !_Contains(hTyps, "комитет", "комітет") && ((!_Contains(hTyps, "совет", "рада") || (higher.toString().indexOf("Верховн") >= 0)))) 
                        return true;
                }
                if (higher.findSlot(com.pullenti.ner._org.OrganizationReferent.ATTR_NAME, "ФЕДЕРАЛЬНОЕ СОБРАНИЕ", true) != null || hTyps.contains("конгресс") || hTyps.contains("парламент")) {
                    if ((lower.findSlot(com.pullenti.ner._org.OrganizationReferent.ATTR_NAME, "СОВЕТ ФЕДЕРАЦИИ", true) != null || lower.findSlot(com.pullenti.ner._org.OrganizationReferent.ATTR_NAME, "ГОСУДАРСТВЕННАЯ ДУМА", true) != null || lower.findSlot(com.pullenti.ner._org.OrganizationReferent.ATTR_NAME, "ВЕРХОВНА РАДА", true) != null) || _Contains(lTyps, "палата", null) || _Contains(lTyps, "совет", null)) 
                        return true;
                }
                if (higher.findSlot(com.pullenti.ner._org.OrganizationReferent.ATTR_NAME, "ФСБ", true) != null) {
                    if (lower.findSlot(com.pullenti.ner._org.OrganizationReferent.ATTR_NAME, "ФПС", true) != null) 
                        return true;
                }
                if (_Contains(hTyps, "государственный комитет", null)) {
                    if ((_Contains(lTyps, "комиссия", "комісія") || _Contains(lTyps, "инспекция", "інспекція") || _Contains(lTyps, "комитет", "комітет")) || _Contains(lTyps, "департамент", null)) 
                        return true;
                }
            }
            else if (lk == com.pullenti.ner._org.OrganizationKind.UNDEFINED) {
                if ((_Contains(lTyps, "комиссия", "комісія") || _Contains(lTyps, "инспекция", "інспекція") || _Contains(lTyps, "комитет", "комітет")) || _Contains(lTyps, "управление", "управління") || _Contains(lTyps, "служба", null)) 
                    return true;
            }
            else if (lk == com.pullenti.ner._org.OrganizationKind.BANK) {
            }
        }
        if (_Contains(hTyps, "министерство", "міністерство")) {
            if ((((((_Contains(lTyps, "институт", "інститут") || _Contains(lTyps, "университет", "університет") || _Contains(lTyps, "училище", null)) || _Contains(lTyps, "школа", null) || _Contains(lTyps, "лицей", "ліцей")) || _Contains(lTyps, "НИИ", "НДІ") || _Contains(lTyps, "Ф", null)) || _Contains(lTyps, "департамент", null) || _Contains(lTyps, "управление", "управління")) || _Contains(lTyps, "комитет", "комітет") || _Contains(lTyps, "комиссия", "комісія")) || _Contains(lTyps, "инспекция", "інспекція") || _Contains(lTyps, "центр", null)) 
                return true;
            if (_Contains(lTyps, "академия", "академія")) {
            }
            if (_Contains(lTyps, "служба", null) && !_Contains(lTyps, "федеральная служба", "федеральна служба")) 
                return true;
            if (lk == com.pullenti.ner._org.OrganizationKind.CULTURE || lk == com.pullenti.ner._org.OrganizationKind.MEDICAL) 
                return true;
        }
        if (_Contains(hTyps, "академия", "академія")) {
            if (_Contains(lTyps, "институт", "інститут") || _Contains(lTyps, "научн", "науков") || _Contains(lTyps, "НИИ", "НДІ")) 
                return true;
        }
        if (_Contains(hTyps, "факультет", null)) {
            if (_Contains(lTyps, "курс", null) || _Contains(lTyps, "кафедра", null)) 
                return true;
        }
        if (_Contains(hTyps, "university", null)) {
            if (_Contains(lTyps, "school", null) || _Contains(lTyps, "college", null)) 
                return true;
        }
        int hr = _militaryRank(hTyps);
        int lr = _militaryRank(lTyps);
        if (hr > 0) {
            if (lr > 0) 
                return hr < lr;
            else if (hr == 3 && ((lTyps.contains("войсковая часть") || lTyps.contains("військова частина")))) 
                return true;
        }
        else if (hTyps.contains("войсковая часть") || hTyps.contains("військова частина")) {
            if (lr >= 6) 
                return true;
        }
        if (lr >= 6) {
            if (higher.containsProfile(com.pullenti.ner._org.OrgProfile.POLICY) || higher.containsProfile(com.pullenti.ner._org.OrgProfile.UNION)) 
                return true;
        }
        if (hk == com.pullenti.ner._org.OrganizationKind.STUDY || _Contains(hTyps, "институт", "інститут") || _Contains(hTyps, "академия", "академія")) {
            if (((_Contains(lTyps, "магистратура", "магістратура") || _Contains(lTyps, "аспирантура", "аспірантура") || _Contains(lTyps, "докторантура", null)) || _Contains(lTyps, "факультет", null) || _Contains(lTyps, "кафедра", null)) || _Contains(lTyps, "курс", null)) 
                return true;
        }
        if (hk != com.pullenti.ner._org.OrganizationKind.DEPARTMENT) {
            if (((((_Contains(lTyps, "департамент", null) || _Contains(lTyps, "центр", null))) && hk != com.pullenti.ner._org.OrganizationKind.MEDICAL && hk != com.pullenti.ner._org.OrganizationKind.SCIENCE) && !_Contains(hTyps, "центр", null) && !_Contains(hTyps, "департамент", null)) && !_Contains(hTyps, "управление", "управління")) 
                return true;
            if (_Contains(hTyps, "департамент", null) || robust) {
                if (_Contains(lTyps, "центр", null)) 
                    return true;
                if (lk == com.pullenti.ner._org.OrganizationKind.STUDY) 
                    return true;
            }
            if (_Contains(hTyps, "служба", null) || _Contains(hTyps, "штаб", null)) {
                if (_Contains(lTyps, "управление", "управління")) 
                    return true;
            }
            if (hk == com.pullenti.ner._org.OrganizationKind.BANK) {
                if (_Contains(lTyps, "управление", "управління") || _Contains(lTyps, "департамент", null)) 
                    return true;
            }
            if (hk == com.pullenti.ner._org.OrganizationKind.PARTY || hk == com.pullenti.ner._org.OrganizationKind.FEDERATION) {
                if (_Contains(lTyps, "комитет", "комітет")) 
                    return true;
            }
            if ((lk == com.pullenti.ner._org.OrganizationKind.FEDERATION && hk != com.pullenti.ner._org.OrganizationKind.FEDERATION && hk != com.pullenti.ner._org.OrganizationKind.GOVENMENT) && hk != com.pullenti.ner._org.OrganizationKind.PARTY) {
                if (!_Contains(hTyps, "фонд", null) && hk != com.pullenti.ner._org.OrganizationKind.UNDEFINED) 
                    return true;
            }
        }
        else if (_Contains(hTyps, "управление", "управління") || _Contains(hTyps, "департамент", null)) {
            if (!_Contains(lTyps, "управление", "управління") && !_Contains(lTyps, "департамент", null) && lk == com.pullenti.ner._org.OrganizationKind.DEPARTMENT) 
                return true;
            if (_Contains(hTyps, "главное", "головне") && _Contains(hTyps, "управление", "управління")) {
                if (_Contains(lTyps, "департамент", null)) 
                    return true;
                if (_Contains(lTyps, "управление", "управління")) {
                    if (!lTyps.contains("главное управление") && !lTyps.contains("головне управління") && !lTyps.contains("пограничное управление")) 
                        return true;
                }
            }
            if (_Contains(hTyps, "управление", "управління") && _Contains(lTyps, "центр", null)) 
                return true;
            if (_Contains(hTyps, "департамент", null) && _Contains(lTyps, "управление", "управління")) 
                return true;
        }
        else if ((lk == com.pullenti.ner._org.OrganizationKind.GOVENMENT && _Contains(lTyps, "служба", null) && higher.getHigher() != null) && higher.getHigher().getKind() == com.pullenti.ner._org.OrganizationKind.GOVENMENT) 
            return true;
        else if (_Contains(hTyps, "отдел", "відділ") && lk == com.pullenti.ner._org.OrganizationKind.DEPARTMENT && ((_Contains(lTyps, "стол", "стіл") || _Contains(lTyps, "направление", "напрямок") || _Contains(lTyps, "отделение", "відділ")))) 
            return true;
        if (hk == com.pullenti.ner._org.OrganizationKind.BANK) {
            if (higher.getNames().contains("СБЕРЕГАТЕЛЬНЫЙ БАНК")) {
                if (lk == com.pullenti.ner._org.OrganizationKind.BANK && !lower.getNames().contains("СБЕРЕГАТЕЛЬНЫЙ БАНК")) 
                    return true;
            }
        }
        if (lk == com.pullenti.ner._org.OrganizationKind.MEDICAL) {
            if (hTyps.contains("департамент")) 
                return true;
        }
        if (lk == com.pullenti.ner._org.OrganizationKind.DEPARTMENT) {
            if (hk == com.pullenti.ner._org.OrganizationKind.DEPARTMENT && higher.getHigher() != null && hTyps.size() == 0) {
                if (canBeHigher(higher.getHigher(), lower, false)) {
                    if (_Contains(lTyps, "управление", "управління") || _Contains(lTyps, "отдел", "відділ")) 
                        return true;
                }
            }
            if (_Contains(lTyps, "офис", "офіс")) {
                if (_Contains(hTyps, "филиал", "філіал") || _Contains(hTyps, "отделение", "відділення")) 
                    return true;
            }
        }
        if (_Contains(lTyps, "управление", "управління") || _Contains(lTyps, "отдел", "відділ")) {
            String str = higher.toStringEx(true, null, 0);
            if (com.pullenti.unisharp.Utils.startsWithString(str, "ГУ", true)) 
                return true;
        }
        return false;
    }

    private static int _militaryRank(java.util.Collection<String> li) {
        if (_Contains(li, "фронт", null)) 
            return 1;
        if (_Contains(li, "группа армий", "група армій")) 
            return 2;
        if (_Contains(li, "армия", "армія")) 
            return 3;
        if (_Contains(li, "корпус", null)) 
            return 4;
        if (_Contains(li, "округ", null)) 
            return 5;
        if (_Contains(li, "дивизия", "дивізія")) 
            return 6;
        if (_Contains(li, "бригада", null)) 
            return 7;
        if (_Contains(li, "полк", null)) 
            return 8;
        if (_Contains(li, "батальон", "батальйон") || _Contains(li, "дивизион", "дивізіон")) 
            return 9;
        if (_Contains(li, "рота", null) || _Contains(li, "батарея", null) || _Contains(li, "эскадрон", "ескадрон")) 
            return 10;
        if (_Contains(li, "взвод", null) || _Contains(li, "отряд", "загін")) 
            return 11;
        return -1;
    }

    private static boolean _Contains(java.util.Collection<String> li, String v, String v2) {
        for (String l : li) {
            if ((l.indexOf(v) >= 0)) 
                return true;
        }
        if (v2 != null) {
            for (String l : li) {
                if ((l.indexOf(v2) >= 0)) 
                    return true;
            }
        }
        return false;
    }
    public OrgOwnershipHelper() {
    }
}
