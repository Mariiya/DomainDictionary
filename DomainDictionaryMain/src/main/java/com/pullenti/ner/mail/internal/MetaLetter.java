/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.mail.internal;

public class MetaLetter extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        globalMeta = new MetaLetter();
        globalMeta.addFeature(com.pullenti.ner.mail.MailReferent.ATTR_KIND, "Тип блока", 1, 1);
        globalMeta.addFeature(com.pullenti.ner.mail.MailReferent.ATTR_TEXT, "Текст блока", 1, 1);
        globalMeta.addFeature(com.pullenti.ner.mail.MailReferent.ATTR_REF, "Ссылка на объект", 0, 0);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.mail.MailReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Блок письма";
    }


    public static String IMAGEID = "letter";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return IMAGEID;
    }

    public static MetaLetter globalMeta;
    public MetaLetter() {
        super();
    }
}
