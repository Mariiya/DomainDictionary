/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.titlepage.internal;

public class MetaTitleInfo extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        globalMeta = new MetaTitleInfo();
        globalMeta.addFeature(com.pullenti.ner.titlepage.TitlePageReferent.ATTR_NAME, "Название", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.titlepage.TitlePageReferent.ATTR_TYPE, "Тип", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.titlepage.TitlePageReferent.ATTR_AUTHOR, "Автор", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.titlepage.TitlePageReferent.ATTR_SUPERVISOR, "Руководитель", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.titlepage.TitlePageReferent.ATTR_EDITOR, "Редактор", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.titlepage.TitlePageReferent.ATTR_CONSULTANT, "Консультант", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.titlepage.TitlePageReferent.ATTR_OPPONENT, "Оппонент", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.titlepage.TitlePageReferent.ATTR_AFFIRMANT, "Утверждающий", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.titlepage.TitlePageReferent.ATTR_TRANSLATOR, "Переводчик", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.titlepage.TitlePageReferent.ATTR_ORG, "Организация", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.titlepage.TitlePageReferent.ATTR_STUDENTYEAR, "Номер курса", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.titlepage.TitlePageReferent.ATTR_DATE, "Дата", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.titlepage.TitlePageReferent.ATTR_CITY, "Город", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.titlepage.TitlePageReferent.ATTR_SPECIALITY, "Специальность", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.titlepage.TitlePageReferent.ATTR_ATTR, "Атрибут", 0, 0);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.titlepage.TitlePageReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Заголовок";
    }


    public static String TITLEINFOIMAGEID = "titleinfo";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return TITLEINFOIMAGEID;
    }

    public static MetaTitleInfo globalMeta;
    public MetaTitleInfo() {
        super();
    }
}
