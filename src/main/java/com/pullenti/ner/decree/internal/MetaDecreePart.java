/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.decree.internal;

public class MetaDecreePart extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        GLOBALMETA = new MetaDecreePart();
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_NAME, "Наименование", 0, 0);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_OWNER, "Владелец", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_LOCALTYP, "Локальный тип", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_SECTION, "Раздел", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_SUBSECTION, "Подраздел", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_APPENDIX, "Приложение", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_CHAPTER, "Глава", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_PREAMBLE, "Преамбула", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_CLAUSE, "Статья", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_PART, "Часть", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_DOCPART, "Часть документа", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_PARAGRAPH, "Параграф", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_SUBPARAGRAPH, "Подпараграф", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_ITEM, "Пункт", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_SUBITEM, "Подпункт", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_INDENTION, "Абзац", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_SUBINDENTION, "Подабзац", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_SUBPROGRAM, "Подпрограмма", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_ADDAGREE, "Допсоглашение", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.decree.DecreePartReferent.ATTR_NOTICE, "Примечание", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.decree.DecreeReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Ссылка на часть НПА";
    }


    public static String PARTIMAGEID = "part";

    public static String PARTLOCIMAGEID = "partloc";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        com.pullenti.ner.decree.DecreePartReferent dpr = (com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(obj, com.pullenti.ner.decree.DecreePartReferent.class);
        if (dpr != null) {
            if (dpr.getOwner() == null) 
                return PARTLOCIMAGEID;
        }
        return PARTIMAGEID;
    }

    public static MetaDecreePart GLOBALMETA;
    public MetaDecreePart() {
        super();
    }
}
