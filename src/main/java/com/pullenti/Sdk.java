/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti;

/**
 * Инициализация SDK Pullenti
 * 
 */
public class Sdk {

    public static String getVersion() {
        return com.pullenti.ner.ProcessorService.getVersion();
    }


    public static String getVersionDate() {
        return com.pullenti.ner.ProcessorService.getVersionDate();
    }


    /**
     * Инициализация всего SDK и на всех поддержанных языках. 
     * Вызывать в самом начале работы. Инициализируется морфология (MorphologyService), 
     * служба процессоров (ProcessorService), все доступные анализаторы сущностей и 
     * семантический анализ (SemanticService). Так что больше ничего инициализировать не нужно.
     * Полная инициализация
     */
    public static void initializeAll() throws Exception, java.io.IOException {
        initialize(com.pullenti.morph.MorphLang.ooBitor(com.pullenti.morph.MorphLang.RU, com.pullenti.morph.MorphLang.ooBitor(com.pullenti.morph.MorphLang.UA, com.pullenti.morph.MorphLang.EN)));
    }

    /**
     * Инициализация SDK. 
     * Вызывать в самом начале работы. Инициализируется морфология (MorphologyService), 
     * служба процессоров (ProcessorService), все доступные анализаторы сущностей и 
     * семантический анализ (SemanticService). Так что больше ничего инициализировать не нужно.
     * @param lang по умолчанию, русский и английский
     * Инициализация конкретных языков
     */
    public static void initialize(com.pullenti.morph.MorphLang lang) throws Exception, java.io.IOException {
        // сначала инициализация всего сервиса
        com.pullenti.ner.ProcessorService.initialize(lang);
        // а затем конкретные анализаторы (какие нужно, в данном случае - все)
        com.pullenti.ner.money.MoneyAnalyzer.initialize();
        com.pullenti.ner.uri.UriAnalyzer.initialize();
        com.pullenti.ner.phone.PhoneAnalyzer.initialize();
        com.pullenti.ner.date.DateAnalyzer.initialize();
        com.pullenti.ner.keyword.KeywordAnalyzer.initialize();
        com.pullenti.ner.definition.DefinitionAnalyzer.initialize();
        com.pullenti.ner.denomination.DenominationAnalyzer.initialize();
        com.pullenti.ner.measure.MeasureAnalyzer.initialize();
        com.pullenti.ner.bank.BankAnalyzer.initialize();
        com.pullenti.ner.geo.GeoAnalyzer.initialize();
        com.pullenti.ner.address.AddressAnalyzer.initialize();
        com.pullenti.ner._org.OrganizationAnalyzer.initialize();
        com.pullenti.ner.person.PersonAnalyzer.initialize();
        com.pullenti.ner.mail.MailAnalyzer.initialize();
        com.pullenti.ner.transport.TransportAnalyzer.initialize();
        com.pullenti.ner.decree.DecreeAnalyzer.initialize();
        com.pullenti.ner.instrument.InstrumentAnalyzer.initialize();
        com.pullenti.ner.titlepage.TitlePageAnalyzer.initialize();
        com.pullenti.ner.booklink.BookLinkAnalyzer.initialize();
        com.pullenti.ner.goods.GoodsAnalyzer.initialize();
        com.pullenti.ner.named.NamedEntityAnalyzer.initialize();
        com.pullenti.ner.weapon.WeaponAnalyzer.initialize();
        // ещё инициализируем семантическую обработки (в принципе, она не используется для задачи NER)
        com.pullenti.semantic.SemanticService.initialize();
    }
    public Sdk() {
    }
}
