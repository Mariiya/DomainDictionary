/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.instrument.internal;

public class MetaInstrumentBlock extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        GLOBALMETA = new MetaInstrumentBlock();
        GLOBALMETA.kindFeature = GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_KIND, "Класс", 0, 1);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.UNDEFINED.toString(), "Неизвестный фрагмент", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.DOCUMENT.toString(), "Документ", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.INTERNALDOCUMENT.toString(), "Внутренний документ", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.APPENDIX.toString(), "Приложение", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.CONTENT.toString(), "Содержимое", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.HEAD.toString(), "Заголовочная часть", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.TAIL.toString(), "Хвостовая часть", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.NAME.toString(), "Название", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.NUMBER.toString(), "Номер", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.CASENUMBER.toString(), "Номер дела", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.CASEINFO.toString(), "Информация дела", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.EDITIONS.toString(), "Редакции", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.APPROVED.toString(), "Одобрен", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.ORGANIZATION.toString(), "Организация", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.DOCPART.toString(), "Часть документа", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.PLACE.toString(), "Место", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.SIGNER.toString(), "Подписант", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.SUBITEM.toString(), "Подпункт", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.INDENTION.toString(), "Абзац", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.CHAPTER.toString(), "Глава", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.PARAGRAPH.toString(), "Параграф", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.SUBPARAGRAPH.toString(), "Подпараграф", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.LISTHEAD.toString(), "Заголовок списка", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.LISTITEM.toString(), "Элемент списка", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.NOTICE.toString(), "Примечание", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.TYP.toString(), "Тип", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.SECTION.toString(), "Раздел", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.SUBSECTION.toString(), "Подраздел", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.CLAUSE.toString(), "Статья", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.CLAUSEPART.toString(), "Часть", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.DATE.toString(), "Дата", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.DIRECTIVE.toString(), "Директива", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.INDEX.toString(), "Оглавление", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.INDEXITEM.toString(), "Элемент оглавления", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.DOCREFERENCE.toString(), "Ссылка на документ", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.INITIATOR.toString(), "Инициатор", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.PREAMBLE.toString(), "Преамбула", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.ITEM.toString(), "Пункт", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.KEYWORD.toString(), "Ключевое слово", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.COMMENT.toString(), "Комментарий", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.QUESTION.toString(), "Вопрос", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.CITATION.toString(), "Цитата", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.CONTACT.toString(), "Контакт", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.TABLE.toString(), "Таблица", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.TABLEROW.toString(), "Строка таблицы", null, null);
        GLOBALMETA.kindFeature.addValue(com.pullenti.ner.instrument.InstrumentKind.TABLECELL.toString(), "Ячейка таблицы", null, null);
        com.pullenti.ner.metadata.Feature fi2 = GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_KIND2, "Класс (доп.)", 0, 1);
        for (int i = 0; i < GLOBALMETA.kindFeature.innerValues.size(); i++) {
            fi2.addValue(GLOBALMETA.kindFeature.innerValues.get(i), GLOBALMETA.kindFeature.outerValues.get(i), null, null);
        }
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_CHILD, "Внутренний элемент", 0, 0).setShowAsParent(true);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_NAME, "Наименование", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_NUMBER, "Номер", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_MINNUMBER, "Минимальный номер", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_SUBNUMBER, "Подномер", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_SUB2NUMBER, "Подномер второй", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_SUB3NUMBER, "Подномер третий", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_VALUE, "Значение", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_REF, "Ссылка на объект", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.instrument.InstrumentBlockReferent.ATTR_EXPIRED, "Утратил силу", 0, 1);
    }

    public com.pullenti.ner.metadata.Feature kindFeature;

    @Override
    public String getName() {
        return com.pullenti.ner.instrument.InstrumentBlockReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Блок документа";
    }


    public static String DOCIMAGEID = "decree";

    public static String PARTIMAGEID = "part";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return PARTIMAGEID;
    }

    public static MetaInstrumentBlock GLOBALMETA;
    public MetaInstrumentBlock() {
        super();
    }
}
