/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.goods.internal;

public class AttrMeta extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        GLOBALMETA = new AttrMeta();
        GLOBALMETA.typAttr = GLOBALMETA.addFeature(com.pullenti.ner.goods.GoodAttributeReferent.ATTR_TYPE, "Тип", 0, 1);
        GLOBALMETA.typAttr.addValue(com.pullenti.ner.goods.GoodAttrType.KEYWORD.toString(), "Ключевое слово", null, null);
        GLOBALMETA.typAttr.addValue(com.pullenti.ner.goods.GoodAttrType.CHARACTER.toString(), "Качеств.свойство", null, null);
        GLOBALMETA.typAttr.addValue(com.pullenti.ner.goods.GoodAttrType.MODEL.toString(), "Модель", null, null);
        GLOBALMETA.typAttr.addValue(com.pullenti.ner.goods.GoodAttrType.NUMERIC.toString(), "Колич.свойство", null, null);
        GLOBALMETA.typAttr.addValue(com.pullenti.ner.goods.GoodAttrType.PROPER.toString(), "Имя собственное", null, null);
        GLOBALMETA.typAttr.addValue(com.pullenti.ner.goods.GoodAttrType.REFERENT.toString(), "Ссылка", null, null);
        GLOBALMETA.addFeature(com.pullenti.ner.goods.GoodAttributeReferent.ATTR_VALUE, "Значение", 1, 0);
        GLOBALMETA.addFeature(com.pullenti.ner.goods.GoodAttributeReferent.ATTR_ALTVALUE, "Значание (альт.)", 0, 0);
        GLOBALMETA.addFeature(com.pullenti.ner.goods.GoodAttributeReferent.ATTR_UNIT, "Единица измерения", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.goods.GoodAttributeReferent.ATTR_NAME, "Название", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.goods.GoodAttributeReferent.ATTR_REF, "Ссылка", 0, 1);
    }

    public com.pullenti.ner.metadata.Feature typAttr;

    @Override
    public String getName() {
        return com.pullenti.ner.goods.GoodAttributeReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Атрибут товара";
    }


    public static String ATTRIMAGEID = "attr";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return ATTRIMAGEID;
    }

    public static AttrMeta GLOBALMETA;
    public AttrMeta() {
        super();
    }
}
