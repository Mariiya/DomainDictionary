/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.date.internal;

// ВСЁ, этот класс теперь используется внутренним робразом, а DateReferent поддерживает относительные даты-время
// Используется для нахождения в тексте абсолютных и относительных дат и диапазонов,
// например, "в прошлом году", "за первый квартал этого года", "два дня назад и т.п."
public class DateExToken extends com.pullenti.ner.MetaToken {

    public DateExToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
        if(_globalInstance == null) return;
    }

    public boolean isDiap = false;

    public java.util.ArrayList<DateExItemToken> itemsFrom = new java.util.ArrayList<DateExItemToken>();

    public java.util.ArrayList<DateExItemToken> itemsTo = new java.util.ArrayList<DateExItemToken>();

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        for (DateExItemToken it : itemsFrom) {
            tmp.append((isDiap ? "(fr)" : "")).append(it.toString()).append("; ");
        }
        for (DateExItemToken it : itemsTo) {
            tmp.append("(to)").append(it.toString()).append("; ");
        }
        return tmp.toString();
    }

    public java.time.LocalDateTime getDate(java.time.LocalDateTime now, int tense) {
        DateValues dvl = DateValues.tryCreate((itemsFrom.size() > 0 ? itemsFrom : itemsTo), now, tense);
        try {
            java.time.LocalDateTime dt = dvl.generateDate(now, false);
            if (dt.compareTo(java.time.LocalDateTime.MIN) == 0) 
                return null;
            dt = this._correctHours(dt, (itemsFrom.size() > 0 ? itemsFrom : itemsTo), now);
            return dt;
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean getDates(java.time.LocalDateTime now, com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> from, com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> to, int tense) {
        from.value = java.time.LocalDateTime.MIN;
        to.value = java.time.LocalDateTime.MIN;
        boolean hasHours = false;
        for (DateExItemToken it : itemsFrom) {
            if (it.typ == DateExItemTokenType.HOUR || it.typ == DateExItemTokenType.MINUTE) 
                hasHours = true;
        }
        for (DateExItemToken it : itemsTo) {
            if (it.typ == DateExItemTokenType.HOUR || it.typ == DateExItemTokenType.MINUTE) 
                hasHours = true;
        }
        java.util.ArrayList<DateExItemToken> li = new java.util.ArrayList<DateExItemToken>();
        if (hasHours) {
            for (DateExItemToken it : itemsFrom) {
                if (it.typ != DateExItemTokenType.HOUR && it.typ != DateExItemTokenType.MINUTE) 
                    li.add(it);
            }
            for (DateExItemToken it : itemsTo) {
                if (it.typ != DateExItemTokenType.HOUR && it.typ != DateExItemTokenType.MINUTE) {
                    boolean exi = false;
                    for (DateExItemToken itt : li) {
                        if (itt.typ == it.typ) {
                            exi = true;
                            break;
                        }
                    }
                    if (!exi) 
                        li.add(it);
                }
            }
            // PYTHON: sort(key=attrgetter('typ'))
            java.util.Collections.sort(li);
            DateValues dvl = DateValues.tryCreate(li, now, tense);
            if (dvl == null) 
                return false;
            try {
                from.value = dvl.generateDate(now, false);
                if (from.value.compareTo(java.time.LocalDateTime.MIN) == 0) 
                    return false;
            } catch (Exception ex) {
                return false;
            }
            to.value = from.value;
            from.value = this._correctHours(from.value, itemsFrom, now);
            to.value = this._correctHours(to.value, (itemsTo.size() == 0 ? itemsFrom : itemsTo), now);
            return true;
        }
        boolean grYear = false;
        for (DateExItemToken f : itemsFrom) {
            if (f.typ == DateExItemTokenType.CENTURY || f.typ == DateExItemTokenType.DECADE) 
                grYear = true;
        }
        if (itemsTo.size() == 0 && !grYear) {
            DateValues dvl = DateValues.tryCreate(itemsFrom, now, tense);
            if (dvl == null) 
                return false;
            try {
                from.value = dvl.generateDate(now, false);
                if (from.value.compareTo(java.time.LocalDateTime.MIN) == 0) 
                    return false;
            } catch (Exception ex) {
                return false;
            }
            try {
                to.value = dvl.generateDate(now, true);
                if (to.value.compareTo(java.time.LocalDateTime.MIN) == 0) 
                    to.value = from.value;
            } catch (Exception ex) {
                to.value = from.value;
            }
            return true;
        }
        li.clear();
        for (DateExItemToken it : itemsFrom) {
            li.add(it);
        }
        for (DateExItemToken it : itemsTo) {
            boolean exi = false;
            for (DateExItemToken itt : li) {
                if (itt.typ == it.typ) {
                    exi = true;
                    break;
                }
            }
            if (!exi) 
                li.add(it);
        }
        // PYTHON: sort(key=attrgetter('typ'))
        java.util.Collections.sort(li);
        DateValues dvl1 = DateValues.tryCreate(li, now, tense);
        li.clear();
        for (DateExItemToken it : itemsTo) {
            li.add(it);
        }
        for (DateExItemToken it : itemsFrom) {
            boolean exi = false;
            for (DateExItemToken itt : li) {
                if (itt.typ == it.typ) {
                    exi = true;
                    break;
                }
            }
            if (!exi) 
                li.add(it);
        }
        // PYTHON: sort(key=attrgetter('typ'))
        java.util.Collections.sort(li);
        DateValues dvl2 = DateValues.tryCreate(li, now, tense);
        try {
            from.value = dvl1.generateDate(now, false);
            if (from.value.compareTo(java.time.LocalDateTime.MIN) == 0) 
                return false;
        } catch (Exception ex) {
            return false;
        }
        try {
            to.value = dvl2.generateDate(now, true);
            if (to.value.compareTo(java.time.LocalDateTime.MIN) == 0) 
                return false;
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    private java.time.LocalDateTime _correctHours(java.time.LocalDateTime dt, java.util.ArrayList<DateExItemToken> li, java.time.LocalDateTime now) {
        boolean hasHour = false;
        for (DateExItemToken it : li) {
            if (it.typ == DateExItemTokenType.HOUR) {
                hasHour = true;
                if (it.isValueRelate) {
                    dt = java.time.LocalDateTime.of(dt.getYear(), dt.getMonthValue(), dt.getDayOfMonth(), now.getHour(), now.getMinute(), 0);
                    dt = dt.plusHours(it.value);
                }
                else if (it.value > 0 && (it.value < 24)) 
                    dt = java.time.LocalDateTime.of(dt.getYear(), dt.getMonthValue(), dt.getDayOfMonth(), it.value, 0, 0);
            }
            else if (it.typ == DateExItemTokenType.MINUTE) {
                if (!hasHour) 
                    dt = java.time.LocalDateTime.of(dt.getYear(), dt.getMonthValue(), dt.getDayOfMonth(), now.getHour(), 0, 0);
                if (it.isValueRelate) {
                    dt = java.time.LocalDateTime.of(dt.getYear(), dt.getMonthValue(), dt.getDayOfMonth(), dt.getHour(), 0, 0);
                    dt = dt.plusMinutes(it.value);
                    if (!hasHour) 
                        dt = dt.plusMinutes(now.getMinute());
                }
                else if (it.value > 0 && (it.value < 60)) 
                    dt = java.time.LocalDateTime.of(dt.getYear(), dt.getMonthValue(), dt.getDayOfMonth(), dt.getHour(), it.value, 0);
            }
        }
        return dt;
    }

    public static class DateValues {
    
        public int day1;
    
        public int day2;
    
        public int month1;
    
        public int month2;
    
        public int year1;
    
        public int year2;
    
        @Override
        public String toString() {
            StringBuilder tmp = new StringBuilder();
            if (year1 > 0) {
                tmp.append("Year:").append(year1);
                if (year2 > year1) 
                    tmp.append("..").append(year2);
            }
            if (month1 > 0) {
                tmp.append(" Month:").append(month1);
                if (month2 > month1) 
                    tmp.append("..").append(month2);
            }
            if (day1 > 0) {
                tmp.append(" Day:").append(day1);
                if (day2 > day1) 
                    tmp.append("..").append(day2);
            }
            return tmp.toString().trim();
        }
    
        public java.time.LocalDateTime generateDate(java.time.LocalDateTime today, boolean endOfDiap) {
            int year = year1;
            if (year == 0) 
                year = today.getYear();
            if (endOfDiap && year2 > year1) 
                year = year2;
            if (year < 0) 
                return java.time.LocalDateTime.MIN;
            int mon = month1;
            if (mon == 0) 
                mon = (endOfDiap ? 12 : 1);
            else if (endOfDiap && month2 > 0) 
                mon = month2;
            int day = day1;
            if (day == 0) {
                day = (endOfDiap ? 31 : 1);
                if (day > com.pullenti.unisharp.Utils.daysInMonth(year, mon)) 
                    day = com.pullenti.unisharp.Utils.daysInMonth(year, mon);
            }
            else if (day2 > 0 && endOfDiap) 
                day = day2;
            if (day > com.pullenti.unisharp.Utils.daysInMonth(year, mon)) 
                return java.time.LocalDateTime.MIN;
            return java.time.LocalDateTime.of(year, mon, day, 0, 0, 0);
        }
    
        public static DateValues tryCreate(java.util.ArrayList<com.pullenti.ner.date.internal.DateExToken.DateExItemToken> list, java.time.LocalDateTime today, int tense) {
            boolean oo = false;
            if (list != null) {
                for (com.pullenti.ner.date.internal.DateExToken.DateExItemToken v : list) {
                    if (v.typ != com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.HOUR && v.typ != com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.MINUTE) 
                        oo = true;
                }
            }
            if (!oo) 
                return _new633(today.getYear(), today.getMonthValue(), today.getDayOfMonth());
            if (list == null || list.size() == 0) 
                return null;
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAYOFWEEK) {
                    if (j > 0 && list.get(j - 1).typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.WEEK) 
                        break;
                    com.pullenti.ner.date.internal.DateExToken.DateExItemToken we = com.pullenti.ner.date.internal.DateExToken.DateExItemToken._new634(list.get(j).getBeginToken(), list.get(j).getEndToken(), com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.WEEK, true);
                    if (list.get(j).isValueRelate) {
                        list.get(j).isValueRelate = false;
                        if (list.get(j).value < 0) {
                            we.value = -1;
                            list.get(j).value = -list.get(j).value;
                        }
                    }
                    list.add(j, we);
                    break;
                }
            }
            DateValues res = new DateValues();
            com.pullenti.ner.date.internal.DateExToken.DateExItemToken it;
            int i = 0;
            boolean hasRel = false;
            if ((i < list.size()) && list.get(i).typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.CENTURY) {
                it = list.get(i);
                if (!it.isValueRelate) 
                    res.year1 = (((today.getYear() / 1000)) * 1000) + (it.value * 100);
                else 
                    res.year1 = (((today.getYear() / 100)) * 100) + (it.value * 100);
                res.year2 = res.year1 + 99;
                i++;
            }
            if ((i < list.size()) && list.get(i).typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DECADE) {
                it = list.get(i);
                if ((i > 0 && list.get(i - 1).typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.CENTURY && !it.isValueRelate) && (res.year1 + 99) == res.year2) {
                    res.year1 += (((it.value - 1)) * 10);
                    res.year2 = res.year1 + 9;
                }
                else if (!it.isValueRelate) 
                    res.year1 = (((today.getYear() / 100)) * 100) + (it.value * 10);
                else 
                    res.year1 = (((today.getYear() / 10)) * 10) + (it.value * 10);
                res.year2 = res.year1 + 9;
                return res;
            }
            if ((i < list.size()) && list.get(i).typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.YEAR) {
                it = list.get(i);
                if (!it.isValueRelate) 
                    res.year1 = it.value;
                else {
                    if (res.year1 > 0 && res.year2 > res.year1 && it.value >= 0) {
                        res.year1 += it.value;
                        res.year2 = res.year1;
                    }
                    else 
                        res.year1 = today.getYear() + it.value;
                    hasRel = true;
                }
                i++;
            }
            if ((i < list.size()) && list.get(i).typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.HALFYEAR) {
                it = list.get(i);
                if (!it.isValueRelate) {
                    if (it.isLast || it.value == 2) {
                        res.month1 = 7;
                        res.month2 = 12;
                    }
                    else {
                        res.month1 = 1;
                        res.month2 = 6;
                    }
                }
                else {
                    int v = (today.getMonthValue() > 6 ? 2 : 1);
                    v += it.value;
                    while (v > 2) {
                        res.year1 += 1;
                        v -= 2;
                    }
                    while (v < 1) {
                        res.year1 -= 1;
                        v += 2;
                    }
                    if (v == 1) {
                        res.month1 = 1;
                        res.month2 = 6;
                    }
                    else {
                        res.month1 = 7;
                        res.month2 = 12;
                    }
                    hasRel = true;
                }
                i++;
            }
            if ((i < list.size()) && list.get(i).typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.QUARTAL) {
                it = list.get(i);
                int v = 0;
                if (!it.isValueRelate) {
                    if (res.year1 == 0) {
                        int v0 = 1 + ((((today.getMonthValue() - 1)) / 3));
                        if (it.value > v0 && (tense < 0)) 
                            res.year1 = today.getYear() - 1;
                        else if ((it.value < v0) && tense > 0) 
                            res.year1 = today.getYear() + 1;
                        else 
                            res.year1 = today.getYear();
                    }
                    v = it.value;
                }
                else {
                    if (res.year1 == 0) 
                        res.year1 = today.getYear();
                    v = 1 + ((((today.getMonthValue() - 1)) / 3)) + it.value;
                }
                while (v > 3) {
                    v -= 3;
                    res.year1++;
                }
                while (v <= 0) {
                    v += 3;
                    res.year1--;
                }
                res.month1 = (((v - 1)) * 3) + 1;
                res.month2 = res.month1 + 2;
                return res;
            }
            if ((i < list.size()) && list.get(i).typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.SEASON) {
                it = list.get(i);
                int v = 0;
                if (!it.isValueRelate) {
                    if (res.year1 == 0) {
                        int v0 = 1 + ((((today.getMonthValue() - 1)) / 3));
                        if (it.value > v0 && (tense < 0)) 
                            res.year1 = today.getYear() - 1;
                        else if ((it.value < v0) && tense > 0) 
                            res.year1 = today.getYear() + 1;
                        else 
                            res.year1 = today.getYear();
                    }
                    v = it.value;
                }
                else {
                    if (res.year1 == 0) 
                        res.year1 = today.getYear();
                    v = it.value;
                }
                if (v == 1) {
                    res.month1 = 12;
                    res.year2 = res.year1;
                    res.year1--;
                    res.month2 = 2;
                }
                else if (v == 2) {
                    res.month1 = 3;
                    res.month2 = 5;
                }
                else if (v == 3) {
                    res.month1 = 6;
                    res.month2 = 8;
                }
                else if (v == 4) {
                    res.month1 = 9;
                    res.month2 = 11;
                }
                else 
                    return null;
                return res;
            }
            if ((i < list.size()) && list.get(i).typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.MONTH) {
                it = list.get(i);
                if (!it.isValueRelate) {
                    if (res.year1 == 0) {
                        if (it.value > today.getMonthValue() && (tense < 0)) 
                            res.year1 = today.getYear() - 1;
                        else if ((it.value < today.getMonthValue()) && tense > 0) 
                            res.year1 = today.getYear() + 1;
                        else 
                            res.year1 = today.getYear();
                    }
                    res.month1 = it.value;
                }
                else {
                    hasRel = true;
                    if (res.year1 == 0) 
                        res.year1 = today.getYear();
                    int v = today.getMonthValue() + it.value;
                    while (v > 12) {
                        v -= 12;
                        res.year1++;
                    }
                    while (v <= 0) {
                        v += 12;
                        res.year1--;
                    }
                    res.month1 = v;
                }
                i++;
            }
            if ((i < list.size()) && list.get(i).typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.WEEKEND && i == 0) {
                it = list.get(i);
                hasRel = true;
                if (res.year1 == 0) 
                    res.year1 = today.getYear();
                if (res.month1 == 0) 
                    res.month1 = today.getMonthValue();
                if (res.day1 == 0) 
                    res.day1 = today.getDayOfMonth();
                java.time.LocalDateTime dt0 = java.time.LocalDateTime.of(res.year1, res.month1, res.day1, 0, 0, 0);
                java.time.DayOfWeek dow = dt0.getDayOfWeek();
                if (dow == java.time.DayOfWeek.MONDAY) 
                    dt0 = dt0.plusDays(5);
                else if (dow == java.time.DayOfWeek.TUESDAY) 
                    dt0 = dt0.plusDays(4);
                else if (dow == java.time.DayOfWeek.WEDNESDAY) 
                    dt0 = dt0.plusDays(3);
                else if (dow == java.time.DayOfWeek.THURSDAY) 
                    dt0 = dt0.plusDays(2);
                else if (dow == java.time.DayOfWeek.FRIDAY) 
                    dt0 = dt0.plusDays(1);
                else if (dow == java.time.DayOfWeek.SATURDAY) 
                    dt0 = dt0.plusDays(-1);
                else if (dow == java.time.DayOfWeek.SUNDAY) {
                }
                if (it.value != 0) 
                    dt0 = dt0.plusDays(it.value * 7);
                res.year1 = dt0.getYear();
                res.month1 = dt0.getMonthValue();
                res.day1 = dt0.getDayOfMonth();
                dt0 = dt0.plusDays(1);
                res.year1 = dt0.getYear();
                res.month2 = dt0.getMonthValue();
                res.day2 = dt0.getDayOfMonth();
                i++;
            }
            if (((i < list.size()) && list.get(i).typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.WEEK && i == 0) && list.get(i).isValueRelate) {
                it = list.get(i);
                hasRel = true;
                if (res.year1 == 0) 
                    res.year1 = today.getYear();
                if (res.month1 == 0) 
                    res.month1 = today.getMonthValue();
                if (res.day1 == 0) 
                    res.day1 = today.getDayOfMonth();
                java.time.LocalDateTime dt0 = java.time.LocalDateTime.of(res.year1, res.month1, res.day1, 0, 0, 0);
                java.time.DayOfWeek dow = dt0.getDayOfWeek();
                if (dow == java.time.DayOfWeek.TUESDAY) 
                    dt0 = dt0.plusDays(-1);
                else if (dow == java.time.DayOfWeek.WEDNESDAY) 
                    dt0 = dt0.plusDays(-2);
                else if (dow == java.time.DayOfWeek.THURSDAY) 
                    dt0 = dt0.plusDays(-3);
                else if (dow == java.time.DayOfWeek.FRIDAY) 
                    dt0 = dt0.plusDays(-4);
                else if (dow == java.time.DayOfWeek.SATURDAY) 
                    dt0 = dt0.plusDays(-5);
                else if (dow == java.time.DayOfWeek.SUNDAY) 
                    dt0 = dt0.plusDays(-6);
                if (it.value != 0) 
                    dt0 = dt0.plusDays(it.value * 7);
                res.year1 = dt0.getYear();
                res.month1 = dt0.getMonthValue();
                res.day1 = dt0.getDayOfMonth();
                dt0 = dt0.plusDays(6);
                res.year1 = dt0.getYear();
                res.month2 = dt0.getMonthValue();
                res.day2 = dt0.getDayOfMonth();
                i++;
            }
            if ((i < list.size()) && list.get(i).typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY) {
                it = list.get(i);
                if (!it.isValueRelate) {
                    res.day1 = it.value;
                    if (res.month1 == 0) {
                        if (res.year1 == 0) 
                            res.year1 = today.getYear();
                        if (it.value > today.getDayOfMonth() && (tense < 0)) {
                            res.month1 = today.getMonthValue() - 1;
                            if (res.month1 <= 0) {
                                res.month1 = 12;
                                res.year1--;
                            }
                        }
                        else if ((it.value < today.getDayOfMonth()) && tense > 0) {
                            res.month1 = today.getMonthValue() + 1;
                            if (res.month1 > 12) {
                                res.month1 = 1;
                                res.year1++;
                            }
                        }
                        else 
                            res.month1 = today.getMonthValue();
                    }
                }
                else {
                    hasRel = true;
                    if (res.year1 == 0) 
                        res.year1 = today.getYear();
                    if (res.month1 == 0) 
                        res.month1 = today.getMonthValue();
                    int v = today.getDayOfMonth() + it.value;
                    while (v > com.pullenti.unisharp.Utils.daysInMonth(res.year1, res.month1)) {
                        v -= com.pullenti.unisharp.Utils.daysInMonth(res.year1, res.month1);
                        res.month1++;
                        if (res.month1 > 12) {
                            res.month1 = 1;
                            res.year1++;
                        }
                    }
                    while (v <= 0) {
                        res.month1--;
                        if (res.month1 <= 0) {
                            res.month1 = 12;
                            res.year1--;
                        }
                        v += com.pullenti.unisharp.Utils.daysInMonth(res.year1, res.month1);
                    }
                    res.day1 = v;
                }
                i++;
            }
            if ((i < list.size()) && list.get(i).typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAYOFWEEK) {
                it = list.get(i);
                if ((i > 0 && list.get(i - 1).typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.WEEK && it.value >= 1) && it.value <= 7) {
                    res.day1 = (res.day1 + it.value) - 1;
                    while (res.day1 > com.pullenti.unisharp.Utils.daysInMonth(res.year1, res.month1)) {
                        res.day1 -= com.pullenti.unisharp.Utils.daysInMonth(res.year1, res.month1);
                        res.month1++;
                        if (res.month1 > 12) {
                            res.month1 = 1;
                            res.year1++;
                        }
                    }
                    res.day2 = res.day1;
                    res.month2 = res.month1;
                    i++;
                }
            }
            return res;
        }
    
        public static DateValues _new633(int _arg1, int _arg2, int _arg3) {
            DateValues res = new DateValues();
            res.year1 = _arg1;
            res.month1 = _arg2;
            res.day1 = _arg3;
            return res;
        }
        public DateValues() {
        }
    }


    public static DateExToken tryParse(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        if (t.isValue("ЗА", null) && t.getNext() != null && t.getNext().isValue("ПЕРИОД", null)) {
            DateExToken ne = tryParse(t.getNext().getNext());
            if (ne != null && ne.isDiap) {
                ne.setBeginToken(t);
                return ne;
            }
        }
        DateExToken res = null;
        boolean toRegime = false;
        boolean fromRegime = false;
        com.pullenti.ner.Token t0 = null;
        for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
            com.pullenti.ner.date.DateRangeReferent drr = (com.pullenti.ner.date.DateRangeReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.date.DateRangeReferent.class);
            if (drr != null) {
                res = _new635(t, tt, true);
                com.pullenti.ner.date.DateReferent fr = drr.getDateFrom();
                if (fr != null) {
                    if (fr.getPointer() == com.pullenti.ner.date.DatePointerType.TODAY) 
                        return null;
                    _addItems(fr, res.itemsFrom, tt);
                }
                com.pullenti.ner.date.DateReferent to = drr.getDateTo();
                if (to != null) {
                    if (to.getPointer() == com.pullenti.ner.date.DatePointerType.TODAY) 
                        return null;
                    _addItems(to, res.itemsTo, tt);
                }
                boolean hasYear = false;
                if (res.itemsFrom.size() > 0 && res.itemsFrom.get(0).typ == DateExItemTokenType.YEAR) 
                    hasYear = true;
                else if (res.itemsTo.size() > 0 && res.itemsTo.get(0).typ == DateExItemTokenType.YEAR) 
                    hasYear = true;
                if (!hasYear && (tt.getWhitespacesAfterCount() < 3)) {
                    DateExItemToken dit = DateExItemToken.tryParse(tt.getNext(), (res.itemsTo.size() > 0 ? res.itemsTo : res.itemsFrom), 0, false);
                    if (dit != null && dit.typ == DateExItemTokenType.YEAR) {
                        if (res.itemsFrom.size() > 0) 
                            res.itemsFrom.add(0, dit);
                        if (res.itemsTo.size() > 0) 
                            res.itemsTo.add(0, dit);
                        res.setEndToken(dit.getEndToken());
                    }
                }
                return res;
            }
            com.pullenti.ner.date.DateReferent dr = (com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.date.DateReferent.class);
            if (dr != null) {
                if (dr.getPointer() == com.pullenti.ner.date.DatePointerType.TODAY) 
                    return null;
                if (res == null) 
                    res = new DateExToken(t, tt);
                java.util.ArrayList<DateExItemToken> li = new java.util.ArrayList<DateExItemToken>();
                _addItems(dr, li, tt);
                if (li.size() == 0) 
                    continue;
                if (toRegime) {
                    boolean ok = true;
                    for (DateExItemToken v : li) {
                        for (DateExItemToken vv : res.itemsTo) {
                            if (vv.typ == v.typ) 
                                ok = false;
                        }
                    }
                    if (!ok) 
                        break;
                    com.pullenti.unisharp.Utils.addToArrayList(res.itemsTo, li);
                    res.setEndToken(tt);
                }
                else {
                    boolean ok = true;
                    for (DateExItemToken v : li) {
                        for (DateExItemToken vv : res.itemsFrom) {
                            if (vv.typ == v.typ) 
                                ok = false;
                        }
                    }
                    if (!ok) 
                        break;
                    com.pullenti.unisharp.Utils.addToArrayList(res.itemsFrom, li);
                    res.setEndToken(tt);
                }
                boolean hasYear = false;
                if (res.itemsFrom.size() > 0 && res.itemsFrom.get(0).typ == DateExItemTokenType.YEAR) 
                    hasYear = true;
                else if (res.itemsTo.size() > 0 && res.itemsTo.get(0).typ == DateExItemTokenType.YEAR) 
                    hasYear = true;
                if (!hasYear && (tt.getWhitespacesAfterCount() < 3)) {
                    DateExItemToken dit = DateExItemToken.tryParse(tt.getNext(), null, 0, false);
                    if (dit != null && dit.typ == DateExItemTokenType.YEAR) {
                        if (res.itemsFrom.size() > 0) 
                            res.itemsFrom.add(0, dit);
                        if (res.itemsTo.size() > 0) 
                            res.itemsTo.add(0, dit);
                        tt = res.setEndToken(dit.getEndToken());
                    }
                }
                continue;
            }
            if (tt.getMorph()._getClass().isPreposition()) {
                if (tt.isValue("ПО", null) || tt.isValue("ДО", null)) {
                    toRegime = true;
                    if (t0 == null) 
                        t0 = tt;
                }
                else if (tt.isValue("С", null) || tt.isValue("ОТ", null)) {
                    fromRegime = true;
                    if (t0 == null) 
                        t0 = tt;
                }
                continue;
            }
            DateExItemToken it = DateExItemToken.tryParse(tt, (res == null ? null : (toRegime ? res.itemsTo : res.itemsFrom)), 0, false);
            if (it == null) 
                break;
            if (tt.isValue("ДЕНЬ", null) && tt.getNext() != null && tt.getNext().isValue("НЕДЕЛЯ", null)) 
                break;
            if (it.getEndToken() == tt && ((it.typ == DateExItemTokenType.HOUR || it.typ == DateExItemTokenType.MINUTE))) {
                if (tt.getPrevious() == null || !tt.getPrevious().getMorph()._getClass().isPreposition()) 
                    break;
            }
            if (res == null) {
                if ((it.typ == DateExItemTokenType.DAY || it.typ == DateExItemTokenType.MONTH || it.typ == DateExItemTokenType.WEEK) || it.typ == DateExItemTokenType.QUARTAL || it.typ == DateExItemTokenType.YEAR) {
                    if (it.getBeginToken() == it.getEndToken() && !it.isValueRelate && it.value == 0) 
                        return null;
                }
                res = new DateExToken(t, tt);
            }
            if (toRegime) 
                res.itemsTo.add(it);
            else {
                res.itemsFrom.add(it);
                if (it.isLast && it.value != 0 && it.value != -1) {
                    res.itemsTo.add(DateExItemToken._new634(it.getBeginToken(), it.getEndToken(), it.typ, true));
                    fromRegime = true;
                }
            }
            tt = it.getEndToken();
            res.setEndToken(tt);
        }
        if (res != null) {
            if (t0 != null && res.getBeginToken().getPrevious() == t0) 
                res.setBeginToken(t0);
            res.isDiap = fromRegime || toRegime;
            // PYTHON: sort(key=attrgetter('typ'))
            java.util.Collections.sort(res.itemsFrom);
            // PYTHON: sort(key=attrgetter('typ'))
            java.util.Collections.sort(res.itemsTo);
            if ((res.itemsFrom.size() == 1 && res.itemsTo.size() == 0 && res.itemsFrom.get(0).isLast) && res.itemsFrom.get(0).value == 0) 
                return null;
        }
        return res;
    }

    private static void _addItems(com.pullenti.ner.date.DateReferent fr, java.util.ArrayList<DateExItemToken> res, com.pullenti.ner.Token tt) {
        if (fr.getCentury() > 0) 
            res.add(DateExItemToken._new637(tt, tt, DateExItemTokenType.CENTURY, fr.getCentury(), fr));
        if (fr.getDecade() > 0) 
            res.add(DateExItemToken._new637(tt, tt, DateExItemTokenType.DECADE, fr.getDecade(), fr));
        if (fr.getYear() > 0) 
            res.add(DateExItemToken._new637(tt, tt, DateExItemTokenType.YEAR, fr.getYear(), fr));
        else if (fr.getPointer() == com.pullenti.ner.date.DatePointerType.TODAY) 
            res.add(DateExItemToken._new640(tt, tt, DateExItemTokenType.YEAR, 0, true));
        if (fr.getMonth() > 0) 
            res.add(DateExItemToken._new637(tt, tt, DateExItemTokenType.MONTH, fr.getMonth(), fr));
        else if (fr.getPointer() == com.pullenti.ner.date.DatePointerType.TODAY) 
            res.add(DateExItemToken._new640(tt, tt, DateExItemTokenType.MONTH, 0, true));
        if (fr.getDay() > 0) 
            res.add(DateExItemToken._new637(tt, tt, DateExItemTokenType.DAY, fr.getDay(), fr));
        else if (fr.getPointer() == com.pullenti.ner.date.DatePointerType.TODAY) 
            res.add(DateExItemToken._new640(tt, tt, DateExItemTokenType.DAY, 0, true));
        if (fr.findSlot(com.pullenti.ner.date.DateReferent.ATTR_HOUR, null, true) != null) 
            res.add(DateExItemToken._new637(tt, tt, DateExItemTokenType.HOUR, fr.getHour(), fr));
        else if (fr.getPointer() == com.pullenti.ner.date.DatePointerType.TODAY) 
            res.add(DateExItemToken._new640(tt, tt, DateExItemTokenType.HOUR, 0, true));
        if (fr.findSlot(com.pullenti.ner.date.DateReferent.ATTR_MINUTE, null, true) != null) 
            res.add(DateExItemToken._new637(tt, tt, DateExItemTokenType.MINUTE, fr.getMinute(), fr));
        else if (fr.getPointer() == com.pullenti.ner.date.DatePointerType.TODAY) 
            res.add(DateExItemToken._new640(tt, tt, DateExItemTokenType.MINUTE, 0, true));
    }

    public static class DateExItemToken extends com.pullenti.ner.MetaToken implements Comparable<DateExItemToken> {
    
        public DateExItemToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
            super(begin, end, null);
        }
    
        public com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType typ = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.UNDEFINED;
    
        public int value;
    
        public boolean isValueRelate;
    
        public boolean isLast;
    
        public boolean isValueNotstrict;
    
        public com.pullenti.ner.date.DateReferent src;
    
        @Override
        public String toString() {
            StringBuilder tmp = new StringBuilder();
            tmp.append(typ.toString()).append(" ");
            if (isValueNotstrict) 
                tmp.append("~");
            if (isValueRelate) 
                tmp.append((value < 0 ? "" : "+")).append(value).append((isLast ? " (last)" : ""));
            else 
                tmp.append(value);
            return tmp.toString();
        }
    
        public static DateExItemToken tryParse(com.pullenti.ner.Token t, java.util.ArrayList<DateExItemToken> prev, int level, boolean noCorrAfter) {
            if (t == null || level > 10) 
                return null;
            if (t.isValue("СЕГОДНЯ", "СЬОГОДНІ")) 
                return _new640(t, t, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY, 0, true);
            if (t.isValue("ЗАВТРА", null)) 
                return _new640(t, t, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY, 1, true);
            if (t.isValue("ЗАВТРАШНИЙ", "ЗАВТРАШНІЙ") && t.getNext() != null && t.getNext().isValue("ДЕНЬ", null)) 
                return _new640(t, t.getNext(), com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY, 1, true);
            if (t.isValue("ПОСЛЕЗАВТРА", "ПІСЛЯЗАВТРА")) 
                return _new640(t, t, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY, 2, true);
            if (t.isValue("ПОСЛЕЗАВТРАШНИЙ", "ПІСЛЯЗАВТРАШНІЙ") && t.getNext() != null && t.getNext().isValue("ДЕНЬ", null)) 
                return _new640(t, t.getNext(), com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY, 2, true);
            if (t.isValue("ВЧЕРА", "ВЧОРА")) 
                return _new640(t, t, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY, -1, true);
            if (t.isValue("ВЧЕРАШНИЙ", "ВЧОРАШНІЙ") && t.getNext() != null && t.getNext().isValue("ДЕНЬ", null)) 
                return _new640(t, t.getNext(), com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY, -1, true);
            if (t.isValue("ПОЗАВЧЕРА", "ПОЗАВЧОРА")) 
                return _new640(t, t, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY, -2, true);
            if (t.isValue("ПОЗАВЧЕРАШНИЙ", "ПОЗАВЧОРАШНІЙ") && t.getNext() != null && t.getNext().isValue("ДЕНЬ", null)) 
                return _new640(t, t.getNext(), com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY, -2, true);
            if (t.isValue("ПОЛЧАСА", "ПІВГОДИНИ")) 
                return _new640(t, t, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.MINUTE, 30, true);
            if (t.isValue("ЗИМА", null)) 
                return _new659(t, t, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.SEASON, 1);
            if (t.isValue("ВЕСНА", null)) 
                return _new659(t, t, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.SEASON, 2);
            if (t.isValue("ЛЕТО", "ЛІТО") && !t.isValue("ЛЕТ", null)) 
                return _new659(t, t, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.SEASON, 3);
            if (t.isValue("ОСЕНЬ", "ОСЕНІ")) 
                return _new659(t, t, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.SEASON, 4);
            if (prev != null && prev.size() > 0) {
                if (((t.isValue("Т", null) && t.getNext() != null && t.getNext().isChar('.')) && t.getNext().getNext() != null && t.getNext().getNext().isValue("Г", null)) && t.getNext().getNext().getNext() != null && t.getNext().getNext().getNext().isChar('.')) 
                    return _new634(t, t.getNext().getNext().getNext(), com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.YEAR, true);
            }
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.of((com.pullenti.ner.core.NounPhraseParseAttr.PARSENUMERICASADJECTIVE.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION.value())), 0, null);
            if (npt != null && npt.getBeginToken() == npt.getEndToken()) {
                if (npt.getEndToken().isValue("ПРОШЛЫЙ", "МИНУЛИЙ") || npt.getEndToken().isValue("БУДУЩИЙ", "МАЙБУТНІЙ")) 
                    npt = null;
            }
            if (npt == null) {
                if ((t instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue() != null) {
                    DateExItemToken res0 = tryParse(t.getNext(), prev, level + 1, true);
                    if (res0 != null && ((res0.value == 1 || res0.value == 0))) {
                        res0.setBeginToken(t);
                        res0.value = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue();
                        if (t.getPrevious() != null && ((t.getPrevious().isValue("ЧЕРЕЗ", null) || t.getPrevious().isValue("СПУСТЯ", null)))) 
                            res0.isValueRelate = true;
                        else if (res0.getEndToken().getNext() != null) {
                            if (res0.getEndToken().getNext().isValue("СПУСТЯ", null)) {
                                res0.isValueRelate = true;
                                res0.setEndToken(res0.getEndToken().getNext());
                            }
                            else if (res0.getEndToken().getNext().isValue("НАЗАД", null)) {
                                res0.isValueRelate = true;
                                res0.value = -res0.value;
                                res0.setEndToken(res0.getEndToken().getNext());
                            }
                            else if (res0.getEndToken().getNext().isValue("ТОМУ", null) && res0.getEndToken().getNext().getNext() != null && res0.getEndToken().getNext().getNext().isValue("НАЗАД", null)) {
                                res0.isValueRelate = true;
                                res0.value = -res0.value;
                                res0.setEndToken(res0.getEndToken().getNext().getNext());
                            }
                        }
                        return res0;
                    }
                    com.pullenti.ner.date.internal.DateItemToken dtt = com.pullenti.ner.date.internal.DateItemToken.tryParse(t, null, false);
                    if (dtt != null && dtt.typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.YEAR) 
                        return _new659(t, dtt.getEndToken(), com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.YEAR, dtt.intValue);
                    if (t.getNext() != null && t.getNext().isValue("ЧИСЛО", null)) {
                        DateExItemToken ne = tryParse(t.getNext().getNext(), prev, level + 1, false);
                        if (ne != null && ne.typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.MONTH) 
                            return _new659(t, t.getNext(), com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY, ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class)).getIntValue());
                    }
                }
                int delt = 0;
                boolean ok = true;
                boolean last = false;
                com.pullenti.ner.Token t1 = t;
                if (t.isValue("СЛЕДУЮЩИЙ", "НАСТУПНИЙ") || t.isValue("БУДУЩИЙ", "МАЙБУТНІЙ") || t.isValue("БЛИЖАЙШИЙ", "НАЙБЛИЖЧИЙ")) 
                    delt = 1;
                else if (t.isValue("ПРЕДЫДУЩИЙ", "ПОПЕРЕДНІЙ") || t.isValue("ПРОШЛЫЙ", "МИНУЛИЙ") || t.isValue("ПРОШЕДШИЙ", null)) 
                    delt = -1;
                else if (t.isValue("ПОЗАПРОШЛЫЙ", "ПОЗАМИНУЛИЙ")) 
                    delt = -2;
                else if (t.isValue("ЭТОТ", "ЦЕЙ") || t.isValue("ТЕКУЩИЙ", "ПОТОЧНИЙ")) {
                    if ((t instanceof com.pullenti.ner.TextToken) && ((com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "ЭТО") || com.pullenti.unisharp.Utils.stringsEq(((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)).term, "ЦЕ")))) 
                        ok = false;
                }
                else if (t.isValue("ПОСЛЕДНИЙ", "ОСТАННІЙ")) {
                    last = true;
                    if (t.getNext() instanceof com.pullenti.ner.NumberToken) {
                        delt = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class)).getIntValue();
                        t1 = t.getNext();
                        DateExItemToken _next = tryParse(t1.getNext(), null, 0, false);
                        if (_next != null && _next.value == 0) {
                            _next.setBeginToken(t);
                            _next.isLast = true;
                            _next.value = -delt;
                            _next.isValueRelate = true;
                            return _next;
                        }
                    }
                    else {
                        DateExItemToken _next = tryParse(t.getNext(), null, 0, false);
                        if (_next != null && _next.value == 0) {
                            _next.setBeginToken(t);
                            _next.isLast = true;
                            _next.isValueRelate = true;
                            if (_next.typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.HALFYEAR) {
                                _next.value = 2;
                                _next.isValueRelate = false;
                            }
                            return _next;
                        }
                    }
                }
                else 
                    ok = false;
                if (ok) {
                    for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null; tt = tt.getPrevious()) {
                        if (tt.isNewlineAfter()) 
                            break;
                        com.pullenti.ner.date.DateReferent dr = (com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.date.DateReferent.class);
                        if (dr != null && dr.isRelative()) {
                            com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType ty0 = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.UNDEFINED;
                            for (com.pullenti.ner.Slot s : dr.getSlots()) {
                                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.date.DateReferent.ATTR_MONTH)) 
                                    ty0 = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.MONTH;
                                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.date.DateReferent.ATTR_YEAR)) 
                                    ty0 = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.YEAR;
                                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.date.DateReferent.ATTR_DAY)) 
                                    ty0 = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY;
                                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.date.DateReferent.ATTR_WEEK)) 
                                    ty0 = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.WEEK;
                                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.date.DateReferent.ATTR_CENTURY)) 
                                    ty0 = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.CENTURY;
                                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.date.DateReferent.ATTR_QUARTAL)) 
                                    ty0 = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.QUARTAL;
                                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.date.DateReferent.ATTR_HALFYEAR)) 
                                    ty0 = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.HALFYEAR;
                                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.date.DateReferent.ATTR_DECADE)) 
                                    ty0 = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DECADE;
                            }
                            if (ty0 != com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.UNDEFINED) 
                                return _new640(t, t, ty0, delt, true);
                        }
                        if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt)) 
                            break;
                    }
                }
                return null;
            }
            com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.HOUR;
            int val = 0;
            if (npt.noun.isValue("ГОД", "РІК") || npt.noun.isValue("ГОДИК", null) || npt.noun.isValue("ЛЕТ", null)) 
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.YEAR;
            else if (npt.noun.isValue("ПОЛГОДА", "ПІВРОКУ") || npt.noun.isValue("ПОЛУГОДИЕ", "ПІВРІЧЧЯ")) 
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.HALFYEAR;
            else if (npt.noun.isValue("ВЕК", null) || npt.noun.isValue("СТОЛЕТИЕ", "СТОЛІТТЯ")) 
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.CENTURY;
            else if (npt.noun.isValue("КВАРТАЛ", null)) 
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.QUARTAL;
            else if (npt.noun.isValue("ДЕСЯТИЛЕТИЕ", "ДЕСЯТИЛІТТЯ") || npt.noun.isValue("ДЕКАДА", null)) 
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DECADE;
            else if (npt.noun.isValue("МЕСЯЦ", "МІСЯЦЬ")) 
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.MONTH;
            else if (npt.noun.isValue("ДЕНЬ", null) || npt.noun.isValue("ДЕНЕК", null)) {
                if (npt.getEndToken().getNext() != null && npt.getEndToken().getNext().isValue("НЕДЕЛЯ", "ТИЖДЕНЬ")) 
                    return null;
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY;
            }
            else if (npt.noun.isValue("ЧИСЛО", null) && npt.adjectives.size() > 0 && (npt.adjectives.get(0).getBeginToken() instanceof com.pullenti.ner.NumberToken)) 
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY;
            else if (npt.noun.isValue("НЕДЕЛЯ", "ТИЖДЕНЬ") || npt.noun.isValue("НЕДЕЛЬКА", null)) {
                if (t.getPrevious() != null && t.getPrevious().isValue("ДЕНЬ", null)) 
                    return null;
                if (t.getPrevious() != null && ((t.getPrevious().isValue("ЗА", null) || t.getPrevious().isValue("НА", null)))) 
                    ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.WEEK;
                else if (t.isValue("ЗА", null) || t.isValue("НА", null)) 
                    ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.WEEK;
                else 
                    ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.WEEK;
            }
            else if (npt.noun.isValue("ВЫХОДНОЙ", "ВИХІДНИЙ")) 
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.WEEKEND;
            else if (npt.noun.isValue("ЧАС", "ГОДИНА") || npt.noun.isValue("ЧАСИК", null) || npt.noun.isValue("ЧАСОК", null)) 
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.HOUR;
            else if (npt.noun.isValue("МИНУТА", "ХВИЛИНА") || npt.noun.isValue("МИНУТКА", null)) 
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.MINUTE;
            else if (npt.noun.isValue("ПОНЕДЕЛЬНИК", "ПОНЕДІЛОК")) {
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAYOFWEEK;
                val = 1;
            }
            else if (npt.noun.isValue("ВТОРНИК", "ВІВТОРОК")) {
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAYOFWEEK;
                val = 2;
            }
            else if (npt.noun.isValue("СРЕДА", "СЕРЕДА")) {
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAYOFWEEK;
                val = 3;
            }
            else if (npt.noun.isValue("ЧЕТВЕРГ", "ЧЕТВЕР")) {
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAYOFWEEK;
                val = 4;
            }
            else if (npt.noun.isValue("ПЯТНИЦЯ", null)) {
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAYOFWEEK;
                val = 5;
            }
            else if (npt.noun.isValue("СУББОТА", "СУБОТА")) {
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAYOFWEEK;
                val = 6;
            }
            else if (npt.noun.isValue("ВОСКРЕСЕНЬЕ", "НЕДІЛЯ") || npt.noun.isValue("ВОСКРЕСЕНИЕ", null)) {
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAYOFWEEK;
                val = 7;
            }
            else {
                com.pullenti.ner.date.internal.DateItemToken dti = com.pullenti.ner.date.internal.DateItemToken.tryParse(npt.getEndToken(), null, false);
                if (dti != null && dti.typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.MONTH) {
                    ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.MONTH;
                    val = dti.intValue;
                }
                else 
                    return null;
            }
            DateExItemToken res = _new659(t, npt.getEndToken(), ty, val);
            boolean heg = false;
            for (int i = 0; i < npt.adjectives.size(); i++) {
                com.pullenti.ner.MetaToken a = npt.adjectives.get(i);
                if (a.isValue("СЛЕДУЮЩИЙ", "НАСТУПНИЙ") || a.isValue("БУДУЩИЙ", "МАЙБУТНІЙ") || a.isValue("БЛИЖАЙШИЙ", "НАЙБЛИЖЧИЙ")) {
                    if (res.value == 0 && ty != com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.WEEKEND) 
                        res.value = 1;
                    res.isValueRelate = true;
                }
                else if (a.isValue("ПРЕДЫДУЩИЙ", "ПОПЕРЕДНІЙ") || a.isValue("ПРОШЛЫЙ", "МИНУЛИЙ") || a.isValue("ПРОШЕДШИЙ", null)) {
                    if (res.value == 0) 
                        res.value = 1;
                    res.isValueRelate = true;
                    heg = true;
                }
                else if (a.isValue("ПОЗАПРОШЛЫЙ", "ПОЗАМИНУЛИЙ")) {
                    if (res.value == 0) 
                        res.value = 2;
                    res.isValueRelate = true;
                    heg = true;
                }
                else if (a.getBeginToken() == a.getEndToken() && (a.getBeginToken() instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(a.getBeginToken(), com.pullenti.ner.NumberToken.class)).getIntValue() != null) {
                    if (res.typ != com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAYOFWEEK) 
                        res.value = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(a.getBeginToken(), com.pullenti.ner.NumberToken.class)).getIntValue();
                }
                else if (a.isValue("ЭТОТ", "ЦЕЙ") || a.isValue("ТЕКУЩИЙ", "ПОТОЧНИЙ")) 
                    res.isValueRelate = true;
                else if (a.isValue("ПЕРВЫЙ", "ПЕРШИЙ")) 
                    res.value = 1;
                else if (a.isValue("ПОСЛЕДНИЙ", "ОСТАННІЙ")) {
                    res.isValueRelate = true;
                    res.isLast = true;
                    if (((i + 1) < npt.adjectives.size()) && (npt.adjectives.get(i + 1).getBeginToken() instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(npt.adjectives.get(i + 1).getBeginToken(), com.pullenti.ner.NumberToken.class)).getIntValue() != null) {
                        i++;
                        res.value = -((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(npt.adjectives.get(i).getBeginToken(), com.pullenti.ner.NumberToken.class)).getIntValue();
                        res.isLast = true;
                    }
                    else if (i > 0 && (npt.adjectives.get(i - 1).getBeginToken() instanceof com.pullenti.ner.NumberToken) && ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(npt.adjectives.get(i - 1).getBeginToken(), com.pullenti.ner.NumberToken.class)).getIntValue() != null) {
                        res.value = -((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(npt.adjectives.get(i - 1).getBeginToken(), com.pullenti.ner.NumberToken.class)).getIntValue();
                        res.isLast = true;
                    }
                }
                else if (a.isValue("ПРЕДПОСЛЕДНИЙ", "ПЕРЕДОСТАННІЙ")) {
                    res.isValueRelate = true;
                    res.isLast = true;
                    res.value = -1;
                }
                else if (a.isValue("БЛИЖАЙШИЙ", "НАЙБЛИЖЧИЙ") && res.typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAYOFWEEK) {
                }
                else 
                    return null;
            }
            if (npt.anafor != null) {
                if (npt.anafor.isValue("ЭТОТ", "ЦЕЙ")) {
                    if (npt.getMorph().getNumber() != com.pullenti.morph.MorphNumber.SINGULAR) 
                        return null;
                    if (res.value == 0) 
                        res.isValueRelate = true;
                    if (prev == null || prev.size() == 0) {
                        if (t.getPrevious() != null && t.getPrevious().getMorphClassInDictionary().isPreposition()) {
                        }
                        else if (t.getMorphClassInDictionary().isPreposition()) {
                        }
                        else if (ty == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.YEAR || ty == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.MONTH || ty == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.WEEK) {
                        }
                        else 
                            return null;
                    }
                }
                else 
                    return null;
            }
            boolean ch = false;
            if (!noCorrAfter && res.getEndToken().getNext() != null) {
                com.pullenti.ner.Token tt = res.getEndToken().getNext();
                com.pullenti.ner.Token tt0 = res.getBeginToken();
                if (tt.isValue("СПУСТЯ", null) || tt0.isValue("СПУСТЯ", null) || tt0.isValue("ЧЕРЕЗ", null)) {
                    ch = true;
                    res.isValueRelate = true;
                    if (res.value == 0) 
                        res.value = 1;
                    res.setEndToken(tt);
                }
                else if (tt.isValue("НАЗАД", null)) {
                    ch = true;
                    res.isValueRelate = true;
                    if (res.value == 0) 
                        res.value = -1;
                    else 
                        res.value = -res.value;
                    res.setEndToken(tt);
                }
                else if (tt.isValue("ТОМУ", null) && tt.getNext() != null && tt.getNext().isValue("НАЗАД", null)) {
                    ch = true;
                    res.isValueRelate = true;
                    if (res.value == 0) 
                        res.value = -1;
                    else 
                        res.value = -res.value;
                    res.setEndToken(tt.getNext());
                }
            }
            if (heg) 
                res.value = -res.value;
            if (t.getPrevious() != null) {
                if (t.getPrevious().isValue("ЧЕРЕЗ", null) || t.getPrevious().isValue("СПУСТЯ", null)) {
                    res.isValueRelate = true;
                    if (res.value == 0) 
                        res.value = 1;
                    res.setBeginToken(t.getPrevious());
                    ch = true;
                }
                else if (t.getPrevious().isValue("ЗА", null) && res.value == 0) {
                    if (!npt.getMorph().getCase().isAccusative()) 
                        return null;
                    if (npt.getEndToken().getNext() != null && npt.getEndToken().getNext().isValue("ДО", null)) 
                        return null;
                    if (npt.getBeginToken() == npt.getEndToken()) 
                        return null;
                    if (!res.isLast) {
                        res.isValueRelate = true;
                        ch = true;
                    }
                }
            }
            if (res.getBeginToken() == res.getEndToken()) {
                if (t.getPrevious() != null && t.getPrevious().isValue("ПО", null)) 
                    return null;
            }
            if (ch && res.typ != com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY) {
                if (res.typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.WEEK) {
                    res.value *= 7;
                    res.typ = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY;
                }
                else if (res.typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.MONTH) {
                    res.value *= 30;
                    res.typ = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY;
                }
                else if (res.typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.QUARTAL) {
                    res.value *= 91;
                    res.typ = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY;
                }
                else if (res.typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.YEAR) {
                    res.value *= 365;
                    res.typ = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY;
                }
            }
            return res;
        }
    
        @Override
        public int compareTo(DateExItemToken other) {
            if ((typ.value()) < (other.typ.value())) 
                return -1;
            if ((typ.value()) > (other.typ.value())) 
                return 1;
            return 0;
        }
    
        public static DateExItemToken _new634(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType _arg3, boolean _arg4) {
            DateExItemToken res = new DateExItemToken(_arg1, _arg2);
            res.typ = _arg3;
            res.isValueRelate = _arg4;
            return res;
        }
    
        public static DateExItemToken _new637(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType _arg3, int _arg4, com.pullenti.ner.date.DateReferent _arg5) {
            DateExItemToken res = new DateExItemToken(_arg1, _arg2);
            res.typ = _arg3;
            res.value = _arg4;
            res.src = _arg5;
            return res;
        }
    
        public static DateExItemToken _new640(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType _arg3, int _arg4, boolean _arg5) {
            DateExItemToken res = new DateExItemToken(_arg1, _arg2);
            res.typ = _arg3;
            res.value = _arg4;
            res.isValueRelate = _arg5;
            return res;
        }
    
        public static DateExItemToken _new659(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType _arg3, int _arg4) {
            DateExItemToken res = new DateExItemToken(_arg1, _arg2);
            res.typ = _arg3;
            res.value = _arg4;
            return res;
        }
    
        public static DateExItemToken _new721(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType _arg3) {
            DateExItemToken res = new DateExItemToken(_arg1, _arg2);
            res.typ = _arg3;
            return res;
        }
        public DateExItemToken() {
            super();
        }
    }


    public static DateExToken _new635(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3) {
        DateExToken res = new DateExToken(_arg1, _arg2);
        res.isDiap = _arg3;
        return res;
    }

    public static class DateExItemTokenType implements Comparable<DateExItemTokenType> {
    
        public static final DateExItemTokenType UNDEFINED; // 0
    
        public static final DateExItemTokenType CENTURY; // 1
    
        public static final DateExItemTokenType DECADE; // 2
    
        public static final DateExItemTokenType YEAR; // 3
    
        public static final DateExItemTokenType HALFYEAR; // 4
    
        public static final DateExItemTokenType QUARTAL; // 5
    
        public static final DateExItemTokenType SEASON; // 6
    
        public static final DateExItemTokenType MONTH; // 7
    
        public static final DateExItemTokenType WEEK; // 8
    
        public static final DateExItemTokenType DAY; // 9
    
        public static final DateExItemTokenType DAYOFWEEK; // 10
    
        public static final DateExItemTokenType HOUR; // 11
    
        public static final DateExItemTokenType MINUTE; // 12
    
        public static final DateExItemTokenType WEEKEND; // 13
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private DateExItemTokenType(int val, String str) { m_val = val; m_str = str; }
        @Override
        public String toString() {
            if(m_str != null) return m_str;
            return ((Integer)m_val).toString();
        }
        @Override
        public int hashCode() {
            return (int)m_val;
        }
        @Override
        public int compareTo(DateExItemTokenType v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, DateExItemTokenType> mapIntToEnum; 
        private static java.util.HashMap<String, DateExItemTokenType> mapStringToEnum; 
        private static DateExItemTokenType[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static DateExItemTokenType of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            DateExItemTokenType item = new DateExItemTokenType(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static DateExItemTokenType of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static DateExItemTokenType[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, DateExItemTokenType>();
            mapStringToEnum = new java.util.HashMap<String, DateExItemTokenType>();
            UNDEFINED = new DateExItemTokenType(0, "UNDEFINED");
            mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
            mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
            CENTURY = new DateExItemTokenType(1, "CENTURY");
            mapIntToEnum.put(CENTURY.value(), CENTURY);
            mapStringToEnum.put(CENTURY.m_str.toUpperCase(), CENTURY);
            DECADE = new DateExItemTokenType(2, "DECADE");
            mapIntToEnum.put(DECADE.value(), DECADE);
            mapStringToEnum.put(DECADE.m_str.toUpperCase(), DECADE);
            YEAR = new DateExItemTokenType(3, "YEAR");
            mapIntToEnum.put(YEAR.value(), YEAR);
            mapStringToEnum.put(YEAR.m_str.toUpperCase(), YEAR);
            HALFYEAR = new DateExItemTokenType(4, "HALFYEAR");
            mapIntToEnum.put(HALFYEAR.value(), HALFYEAR);
            mapStringToEnum.put(HALFYEAR.m_str.toUpperCase(), HALFYEAR);
            QUARTAL = new DateExItemTokenType(5, "QUARTAL");
            mapIntToEnum.put(QUARTAL.value(), QUARTAL);
            mapStringToEnum.put(QUARTAL.m_str.toUpperCase(), QUARTAL);
            SEASON = new DateExItemTokenType(6, "SEASON");
            mapIntToEnum.put(SEASON.value(), SEASON);
            mapStringToEnum.put(SEASON.m_str.toUpperCase(), SEASON);
            MONTH = new DateExItemTokenType(7, "MONTH");
            mapIntToEnum.put(MONTH.value(), MONTH);
            mapStringToEnum.put(MONTH.m_str.toUpperCase(), MONTH);
            WEEK = new DateExItemTokenType(8, "WEEK");
            mapIntToEnum.put(WEEK.value(), WEEK);
            mapStringToEnum.put(WEEK.m_str.toUpperCase(), WEEK);
            DAY = new DateExItemTokenType(9, "DAY");
            mapIntToEnum.put(DAY.value(), DAY);
            mapStringToEnum.put(DAY.m_str.toUpperCase(), DAY);
            DAYOFWEEK = new DateExItemTokenType(10, "DAYOFWEEK");
            mapIntToEnum.put(DAYOFWEEK.value(), DAYOFWEEK);
            mapStringToEnum.put(DAYOFWEEK.m_str.toUpperCase(), DAYOFWEEK);
            HOUR = new DateExItemTokenType(11, "HOUR");
            mapIntToEnum.put(HOUR.value(), HOUR);
            mapStringToEnum.put(HOUR.m_str.toUpperCase(), HOUR);
            MINUTE = new DateExItemTokenType(12, "MINUTE");
            mapIntToEnum.put(MINUTE.value(), MINUTE);
            mapStringToEnum.put(MINUTE.m_str.toUpperCase(), MINUTE);
            WEEKEND = new DateExItemTokenType(13, "WEEKEND");
            mapIntToEnum.put(WEEKEND.value(), WEEKEND);
            mapStringToEnum.put(WEEKEND.m_str.toUpperCase(), WEEKEND);
            java.util.Collection<DateExItemTokenType> col = mapIntToEnum.values();
            m_Values = new DateExItemTokenType[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }

    public DateExToken() {
        super();
    }
    public static DateExToken _globalInstance;
    
    static {
        try { _globalInstance = new DateExToken(); } 
        catch(Exception e) { }
    }
}
