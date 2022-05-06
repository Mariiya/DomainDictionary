/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.date.internal;

public class DateRelHelper {

    public static java.util.ArrayList<com.pullenti.ner.ReferentToken> createReferents(DateExToken et) {
        if (!et.isDiap || et.itemsTo.size() == 0) {
            java.util.ArrayList<com.pullenti.ner.ReferentToken> li = _createRefs(et.itemsFrom);
            if (li == null || li.size() == 0) 
                return null;
            return li;
        }
        java.util.ArrayList<com.pullenti.ner.ReferentToken> liFr = _createRefs(et.itemsFrom);
        java.util.ArrayList<com.pullenti.ner.ReferentToken> liTo = _createRefs(et.itemsTo);
        com.pullenti.ner.date.DateRangeReferent ra = new com.pullenti.ner.date.DateRangeReferent();
        if (liFr.size() > 0) 
            ra.setDateFrom((com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(liFr.get(0).tag, com.pullenti.ner.date.DateReferent.class));
        if (liTo.size() > 0) 
            ra.setDateTo((com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(liTo.get(0).tag, com.pullenti.ner.date.DateReferent.class));
        java.util.ArrayList<com.pullenti.ner.ReferentToken> res = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
        com.pullenti.unisharp.Utils.addToArrayList(res, liFr);
        com.pullenti.unisharp.Utils.addToArrayList(res, liTo);
        res.add(new com.pullenti.ner.ReferentToken(ra, et.getBeginToken(), et.getEndToken(), null));
        if (res.size() == 0) 
            return null;
        res.get(0).tag = ra;
        return res;
    }

    private static java.util.ArrayList<com.pullenti.ner.ReferentToken> _createRefs(java.util.ArrayList<DateExToken.DateExItemToken> its) {
        java.util.ArrayList<com.pullenti.ner.ReferentToken> res = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
        com.pullenti.ner.date.DateReferent own = null;
        for (int i = 0; i < its.size(); i++) {
            DateExToken.DateExItemToken it = its.get(i);
            com.pullenti.ner.date.DateReferent d = new com.pullenti.ner.date.DateReferent();
            if (it.isValueRelate) 
                d.setRelative(true);
            if (own != null) 
                d.setHigher(own);
            if (it.typ == DateExToken.DateExItemTokenType.DAY) {
                d.setDay(it.value);
                if (it.isLast && ((it.value == 0 || it.value == -1)) && i > 0) {
                    DateExToken.DateExItemToken it0 = its.get(i - 1);
                    int day = 0;
                    if (it0.typ == DateExToken.DateExItemTokenType.MONTH && !it0.isValueRelate) {
                        int m = d.getMonth();
                        if (((m == 1 || m == 3 || m == 5) || m == 7 || m == 8) || m == 10 || m == 12) 
                            day = 31;
                        else if (m == 2) 
                            day = 28;
                        else if (m > 0) 
                            day = 30;
                    }
                    else if (it0.typ == DateExToken.DateExItemTokenType.QUARTAL && !it0.isValueRelate) {
                        int m = 1 + (((it0.value - 1)) * 4);
                        com.pullenti.ner.date.DateReferent dm = new com.pullenti.ner.date.DateReferent();
                        dm.setMonth(m);
                        if (own != null) 
                            dm.setHigher(own);
                        res.add(new com.pullenti.ner.ReferentToken(dm, it.getBeginToken(), it.getEndToken(), null));
                        own = d.setHigher(dm);
                        if (((m == 1 || m == 3 || m == 5) || m == 7 || m == 8) || m == 10 || m == 12) 
                            day = 31;
                        else if (m == 2) 
                            day = 28;
                        else if (m > 0) 
                            day = 30;
                    }
                    else if (it0.typ == DateExToken.DateExItemTokenType.YEAR) {
                        com.pullenti.ner.date.DateReferent dm = new com.pullenti.ner.date.DateReferent();
                        dm.setMonth(12);
                        if (own != null) 
                            dm.setHigher(own);
                        res.add(new com.pullenti.ner.ReferentToken(dm, it.getBeginToken(), it.getEndToken(), null));
                        own = d.setHigher(dm);
                        day = 31;
                    }
                    else if (it0.typ == DateExToken.DateExItemTokenType.CENTURY) {
                        com.pullenti.ner.date.DateReferent dy = new com.pullenti.ner.date.DateReferent();
                        dy.setYear(99);
                        dy.setRelative(true);
                        if (own != null) 
                            dy.setHigher(own);
                        res.add(new com.pullenti.ner.ReferentToken(dy, it.getBeginToken(), it.getEndToken(), null));
                        own = dy;
                        com.pullenti.ner.date.DateReferent dm = new com.pullenti.ner.date.DateReferent();
                        dm.setMonth(12);
                        dm.setHigher(own);
                        res.add(new com.pullenti.ner.ReferentToken(dm, it.getBeginToken(), it.getEndToken(), null));
                        own = d.setHigher(dm);
                        day = 31;
                    }
                    if ((day + it.value) > 0) {
                        d.setRelative(false);
                        d.setDay(day + it.value);
                    }
                }
            }
            else if (it.typ == DateExToken.DateExItemTokenType.DAYOFWEEK) 
                d.setDayOfWeek(it.value);
            else if (it.typ == DateExToken.DateExItemTokenType.HOUR) {
                d.setHour(it.value);
                if (((i + 1) < its.size()) && its.get(i + 1).typ == DateExToken.DateExItemTokenType.MINUTE && !its.get(i + 1).isValueRelate) {
                    d.setMinute(its.get(i + 1).value);
                    i++;
                }
            }
            else if (it.typ == DateExToken.DateExItemTokenType.MINUTE) 
                d.setMinute(it.value);
            else if (it.typ == DateExToken.DateExItemTokenType.MONTH) {
                d.setMonth(it.value);
                if (it.isLast && ((it.value == 0 || it.value == -1)) && i > 0) {
                    DateExToken.DateExItemToken it0 = its.get(i - 1);
                    int m = 0;
                    if (it0.typ == DateExToken.DateExItemTokenType.QUARTAL && !it0.isValueRelate) 
                        m = 1 + (((it0.value - 1)) * 4) + it.value;
                    else if (it0.typ == DateExToken.DateExItemTokenType.YEAR || it0.typ == DateExToken.DateExItemTokenType.DECADE || it0.typ == DateExToken.DateExItemTokenType.CENTURY) 
                        m = 12 + it.value;
                    if (m > 0) {
                        d.setRelative(false);
                        d.setMonth(m);
                    }
                }
            }
            else if (it.typ == DateExToken.DateExItemTokenType.QUARTAL) 
                d.setQuartal(it.value);
            else if (it.typ == DateExToken.DateExItemTokenType.SEASON) 
                d.setSeason(it.value);
            else if (it.typ == DateExToken.DateExItemTokenType.WEEK) 
                d.setWeek(it.value);
            else if (it.typ == DateExToken.DateExItemTokenType.HALFYEAR) 
                d.setHalfyear((it.isLast ? 2 : it.value));
            else if (it.typ == DateExToken.DateExItemTokenType.YEAR) 
                d.setYear(it.value);
            else if (it.typ == DateExToken.DateExItemTokenType.CENTURY) 
                d.setCentury(it.value);
            else if (it.typ == DateExToken.DateExItemTokenType.DECADE) 
                d.setDecade(it.value);
            else 
                continue;
            res.add(new com.pullenti.ner.ReferentToken(d, it.getBeginToken(), it.getEndToken(), null));
            own = d;
            it.src = d;
        }
        if (res.size() > 0) 
            res.get(0).tag = own;
        return res;
    }

    private static java.util.ArrayList<DateExToken.DateExItemToken> _createDateEx(com.pullenti.ner.date.DateReferent dr) {
        java.util.ArrayList<DateExToken.DateExItemToken> res = new java.util.ArrayList<DateExToken.DateExItemToken>();
        for (; dr != null; dr = dr.getHigher()) {
            int n;
            for (com.pullenti.ner.Slot s : dr.getSlots()) {
                DateExToken.DateExItemToken it = DateExToken.DateExItemToken._new721(null, null, DateExToken.DateExItemTokenType.UNDEFINED);
                if (com.pullenti.unisharp.Utils.stringsEq(dr.getStringValue(com.pullenti.ner.date.DateReferent.ATTR_ISRELATIVE), "true")) 
                    it.isValueRelate = true;
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.date.DateReferent.ATTR_YEAR)) {
                    it.typ = DateExToken.DateExItemTokenType.YEAR;
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapn722 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    boolean inoutres723 = com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapn722);
                    n = (wrapn722.value != null ? wrapn722.value : 0);
                    if (inoutres723) 
                        it.value = n;
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.date.DateReferent.ATTR_DECADE)) {
                    it.typ = DateExToken.DateExItemTokenType.DECADE;
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapn724 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    boolean inoutres725 = com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapn724);
                    n = (wrapn724.value != null ? wrapn724.value : 0);
                    if (inoutres725) 
                        it.value = n;
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.date.DateReferent.ATTR_CENTURY)) {
                    it.typ = DateExToken.DateExItemTokenType.CENTURY;
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapn726 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    boolean inoutres727 = com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapn726);
                    n = (wrapn726.value != null ? wrapn726.value : 0);
                    if (inoutres727) 
                        it.value = n;
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.date.DateReferent.ATTR_HALFYEAR)) {
                    it.typ = DateExToken.DateExItemTokenType.HALFYEAR;
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapn728 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    boolean inoutres729 = com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapn728);
                    n = (wrapn728.value != null ? wrapn728.value : 0);
                    if (inoutres729) 
                        it.value = n;
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.date.DateReferent.ATTR_QUARTAL)) {
                    it.typ = DateExToken.DateExItemTokenType.QUARTAL;
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapn730 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    boolean inoutres731 = com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapn730);
                    n = (wrapn730.value != null ? wrapn730.value : 0);
                    if (inoutres731) 
                        it.value = n;
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.date.DateReferent.ATTR_SEASON)) {
                    it.typ = DateExToken.DateExItemTokenType.SEASON;
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapn732 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    boolean inoutres733 = com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapn732);
                    n = (wrapn732.value != null ? wrapn732.value : 0);
                    if (inoutres733) 
                        it.value = n;
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.date.DateReferent.ATTR_MONTH)) {
                    it.typ = DateExToken.DateExItemTokenType.MONTH;
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapn734 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    boolean inoutres735 = com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapn734);
                    n = (wrapn734.value != null ? wrapn734.value : 0);
                    if (inoutres735) 
                        it.value = n;
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.date.DateReferent.ATTR_WEEK)) {
                    it.typ = DateExToken.DateExItemTokenType.WEEK;
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapn736 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    boolean inoutres737 = com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapn736);
                    n = (wrapn736.value != null ? wrapn736.value : 0);
                    if (inoutres737) 
                        it.value = n;
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.date.DateReferent.ATTR_DAYOFWEEK)) {
                    it.typ = DateExToken.DateExItemTokenType.DAYOFWEEK;
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapn738 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    boolean inoutres739 = com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapn738);
                    n = (wrapn738.value != null ? wrapn738.value : 0);
                    if (inoutres739) 
                        it.value = n;
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.date.DateReferent.ATTR_DAY)) {
                    it.typ = DateExToken.DateExItemTokenType.DAY;
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapn740 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    boolean inoutres741 = com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapn740);
                    n = (wrapn740.value != null ? wrapn740.value : 0);
                    if (inoutres741) 
                        it.value = n;
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.date.DateReferent.ATTR_HOUR)) {
                    it.typ = DateExToken.DateExItemTokenType.HOUR;
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapn742 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    boolean inoutres743 = com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapn742);
                    n = (wrapn742.value != null ? wrapn742.value : 0);
                    if (inoutres743) 
                        it.value = n;
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.date.DateReferent.ATTR_MINUTE)) {
                    it.typ = DateExToken.DateExItemTokenType.MINUTE;
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapn744 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    boolean inoutres745 = com.pullenti.unisharp.Utils.parseInteger((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), 0, null, wrapn744);
                    n = (wrapn744.value != null ? wrapn744.value : 0);
                    if (inoutres745) 
                        it.value = n;
                }
                if (it.typ != DateExToken.DateExItemTokenType.UNDEFINED) 
                    res.add(0, it);
            }
        }
        // PYTHON: sort(key=attrgetter('typ'))
        java.util.Collections.sort(res);
        return res;
    }

    public static java.time.LocalDateTime calculateDate(com.pullenti.ner.date.DateReferent dr, java.time.LocalDateTime now, int tense) {
        if (dr.getPointer() == com.pullenti.ner.date.DatePointerType.TODAY) 
            return now;
        if (!dr.isRelative() && dr.getDt() != null) 
            return dr.getDt();
        DateExToken det = new DateExToken(null, null);
        det.itemsFrom = _createDateEx(dr);
        return det.getDate(now, tense);
    }

    public static boolean calculateDateRange(com.pullenti.ner.date.DateReferent dr, java.time.LocalDateTime now, com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> from, com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> to, int tense) {
        if (dr.getPointer() == com.pullenti.ner.date.DatePointerType.TODAY) {
            from.value = now;
            to.value = now;
            return true;
        }
        if (!dr.isRelative() && dr.getDt() != null) {
            from.value = (to.value = dr.getDt());
            return true;
        }
        DateExToken det = new DateExToken(null, null);
        det.itemsFrom = _createDateEx(dr);
        boolean inoutres746 = det.getDates(now, from, to, tense);
        return inoutres746;
    }

    public static boolean calculateDateRange2(com.pullenti.ner.date.DateRangeReferent dr, java.time.LocalDateTime now, com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> from, com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> to, int tense) {
        from.value = java.time.LocalDateTime.MIN;
        to.value = java.time.LocalDateTime.MAX;
        java.time.LocalDateTime dt0;
        java.time.LocalDateTime dt1;
        if (dr.getDateFrom() == null) {
            if (dr.getDateTo() == null) 
                return false;
            com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> wrapdt0747 = new com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime>();
            com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> wrapdt1748 = new com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime>();
            boolean inoutres749 = calculateDateRange(dr.getDateTo(), now, wrapdt0747, wrapdt1748, tense);
            dt0 = wrapdt0747.value;
            dt1 = wrapdt1748.value;
            if (!inoutres749) 
                return false;
            to.value = dt1;
            return true;
        }
        else if (dr.getDateTo() == null) {
            com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> wrapdt0750 = new com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime>();
            com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> wrapdt1751 = new com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime>();
            boolean inoutres752 = calculateDateRange(dr.getDateFrom(), now, wrapdt0750, wrapdt1751, tense);
            dt0 = wrapdt0750.value;
            dt1 = wrapdt1751.value;
            if (!inoutres752) 
                return false;
            from.value = dt0;
            return true;
        }
        com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> wrapdt0756 = new com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime>();
        com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> wrapdt1757 = new com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime>();
        boolean inoutres758 = calculateDateRange(dr.getDateFrom(), now, wrapdt0756, wrapdt1757, tense);
        dt0 = wrapdt0756.value;
        dt1 = wrapdt1757.value;
        if (!inoutres758) 
            return false;
        from.value = dt0;
        java.time.LocalDateTime dt2;
        java.time.LocalDateTime dt3;
        com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> wrapdt2753 = new com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime>();
        com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> wrapdt3754 = new com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime>();
        boolean inoutres755 = calculateDateRange(dr.getDateTo(), now, wrapdt2753, wrapdt3754, tense);
        dt2 = wrapdt2753.value;
        dt3 = wrapdt3754.value;
        if (!inoutres755) 
            return false;
        to.value = dt3;
        return true;
    }

    public static void appendToString(com.pullenti.ner.date.DateReferent dr, StringBuilder res) {
        java.time.LocalDateTime dt0;
        java.time.LocalDateTime dt1;
        java.time.LocalDateTime cur = (com.pullenti.ner.ProcessorService.DEBUGCURRENTDATETIME == null ? java.time.LocalDateTime.now() : com.pullenti.ner.ProcessorService.DEBUGCURRENTDATETIME);
        com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> wrapdt0759 = new com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime>();
        com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> wrapdt1760 = new com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime>();
        boolean inoutres761 = DateRelHelper.calculateDateRange(dr, cur, wrapdt0759, wrapdt1760, 0);
        dt0 = wrapdt0759.value;
        dt1 = wrapdt1760.value;
        if (!inoutres761) 
            return;
        _appendDates(cur, dt0, dt1, res);
    }

    public static void appendToString2(com.pullenti.ner.date.DateRangeReferent dr, StringBuilder res) {
        java.time.LocalDateTime dt0;
        java.time.LocalDateTime dt1;
        java.time.LocalDateTime cur = (com.pullenti.ner.ProcessorService.DEBUGCURRENTDATETIME == null ? java.time.LocalDateTime.now() : com.pullenti.ner.ProcessorService.DEBUGCURRENTDATETIME);
        com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> wrapdt0762 = new com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime>();
        com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> wrapdt1763 = new com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime>();
        boolean inoutres764 = DateRelHelper.calculateDateRange2(dr, cur, wrapdt0762, wrapdt1763, 0);
        dt0 = wrapdt0762.value;
        dt1 = wrapdt1763.value;
        if (!inoutres764) 
            return;
        _appendDates(cur, dt0, dt1, res);
    }

    private static void _appendDates(java.time.LocalDateTime cur, java.time.LocalDateTime dt0, java.time.LocalDateTime dt1, StringBuilder res) {
        int mon0 = dt0.getMonthValue();
        res.append(" (").append(dt0.getYear()).append(".").append(String.format("%02d", mon0)).append(".").append(String.format("%02d", dt0.getDayOfMonth()));
        if (dt0.getHour() > 0 || dt0.getMinute() > 0) 
            res.append(" ").append(String.format("%02d", dt0.getHour())).append(":").append(String.format("%02d", dt0.getMinute()));
        if (dt0.compareTo(dt1) != 0) {
            int mon1 = dt1.getMonthValue();
            res.append("-").append(dt1.getYear()).append(".").append(String.format("%02d", mon1)).append(".").append(String.format("%02d", dt1.getDayOfMonth()));
            if (dt1.getHour() > 0 || dt1.getMinute() > 0) 
                res.append(" ").append(String.format("%02d", dt1.getHour())).append(":").append(String.format("%02d", dt1.getMinute()));
        }
        int monc = cur.getMonthValue();
        res.append(" отн. ").append(cur.getYear()).append(".").append(String.format("%02d", monc)).append(".").append(String.format("%02d", cur.getDayOfMonth()));
        if (cur.getHour() > 0 || cur.getMinute() > 0) 
            res.append(" ").append(String.format("%02d", cur.getHour())).append(":").append(String.format("%02d", cur.getMinute()));
        res.append(")");
    }
    public DateRelHelper() {
    }
}
