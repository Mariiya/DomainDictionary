/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.date.internal;

public class MetaDate extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        GLOBALMETA = new MetaDate();
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_ISRELATIVE, "Относительность", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_CENTURY, "Век", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_DECADE, "Декада", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_YEAR, "Год", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_HALFYEAR, "Полугодие", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_QUARTAL, "Квартал", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_SEASON, "Сезон", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_MONTH, "Месяц", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_WEEK, "Неделя", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_DAY, "День", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_HOUR, "Час", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_MINUTE, "Минут", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_SECOND, "Секунд", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_DAYOFWEEK, "День недели", 0, 1);
        POINTER = GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_POINTER, "Указатель", 0, 1);
        POINTER.addValue(com.pullenti.ner.date.DatePointerType.BEGIN.toString(), "в начале", "на початку", "in the beginning");
        POINTER.addValue(com.pullenti.ner.date.DatePointerType.CENTER.toString(), "в середине", "в середині", "in the middle");
        POINTER.addValue(com.pullenti.ner.date.DatePointerType.END.toString(), "в конце", "в кінці", "in the end");
        POINTER.addValue(com.pullenti.ner.date.DatePointerType.TODAY.toString(), "настоящее время", "теперішній час", "today");
        POINTER.addValue(com.pullenti.ner.date.DatePointerType.WINTER.toString(), "зимой", "взимку", "winter");
        POINTER.addValue(com.pullenti.ner.date.DatePointerType.SPRING.toString(), "весной", "навесні", "spring");
        POINTER.addValue(com.pullenti.ner.date.DatePointerType.SUMMER.toString(), "летом", "влітку", "summer");
        POINTER.addValue(com.pullenti.ner.date.DatePointerType.AUTUMN.toString(), "осенью", "восени", "autumn");
        POINTER.addValue(com.pullenti.ner.date.DatePointerType.ABOUT.toString(), "около", "біля", "about");
        POINTER.addValue(com.pullenti.ner.date.DatePointerType.UNDEFINED.toString(), "Не определена", null, null);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_NEWSTYLE, "Дата нового стиля", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_HIGHER, "Вышестоящая дата", 0, 1);
    }

    public static com.pullenti.ner.metadata.Feature POINTER;

    @Override
    public String getName() {
        return com.pullenti.ner.date.DateReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Дата";
    }


    public static String DATEFULLIMAGEID = "datefull";

    public static String DATERELIMAGEID = "daterel";

    public static String DATEIMAGEID = "date";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        com.pullenti.ner.date.DateReferent dat = (com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(obj, com.pullenti.ner.date.DateReferent.class);
        if (dat != null && dat.isRelative()) 
            return DATERELIMAGEID;
        if (dat != null && dat.getHour() >= 0) 
            return DATEIMAGEID;
        else 
            return DATEFULLIMAGEID;
    }

    public static MetaDate GLOBALMETA;
    public MetaDate() {
        super();
    }
}
