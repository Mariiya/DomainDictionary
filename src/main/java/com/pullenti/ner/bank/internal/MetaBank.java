/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.bank.internal;

public class MetaBank extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        globalMeta = new MetaBank();
        globalMeta.addFeature(com.pullenti.ner.bank.BankDataReferent.ATTR_ITEM, "Элемент", 0, 0).setShowAsParent(true);
        globalMeta.addFeature(com.pullenti.ner.bank.BankDataReferent.ATTR_BANK, "Банк", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.bank.BankDataReferent.ATTR_CORBANK, "Банк К/С", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.bank.BankDataReferent.ATTR_MISC, "Разное", 0, 0);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.bank.BankDataReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Банковские реквизиты";
    }


    public static String IMAGEID = "bankreq";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return IMAGEID;
    }

    public static MetaBank globalMeta;
    public MetaBank() {
        super();
    }
}
