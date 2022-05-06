/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.address.internal;

public class MetaAddress extends com.pullenti.ner.metadata.ReferentClass {

    public static void initialize() {
        globalMeta = new MetaAddress();
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_STREET, "Улица", 0, 2);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_HOUSE, "Дом", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_HOUSEORPLOT, "Дом или участок", 0, 1);
        globalMeta.houseTypeFeature = globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_HOUSETYPE, "Тип дома", 0, 1);
        globalMeta.houseTypeFeature.addValue(com.pullenti.ner.address.AddressHouseType.ESTATE.toString(), "Владение", null, null);
        globalMeta.houseTypeFeature.addValue(com.pullenti.ner.address.AddressHouseType.HOUSE.toString(), "Дом", null, null);
        globalMeta.houseTypeFeature.addValue(com.pullenti.ner.address.AddressHouseType.HOUSEESTATE.toString(), "Домовладение", null, null);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_BUILDING, "Строение", 0, 1);
        globalMeta.buildingTypeFeature = globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_BUILDINGTYPE, "Тип строения", 0, 1);
        globalMeta.buildingTypeFeature.addValue(com.pullenti.ner.address.AddressBuildingType.BUILDING.toString(), "Строение", null, null);
        globalMeta.buildingTypeFeature.addValue(com.pullenti.ner.address.AddressBuildingType.CONSTRUCTION.toString(), "Сооружение", null, null);
        globalMeta.buildingTypeFeature.addValue(com.pullenti.ner.address.AddressBuildingType.LITER.toString(), "Литера", null, null);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_CORPUS, "Корпус", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_PORCH, "Подъезд", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_FLOOR, "Этаж", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_FLAT, "Квартира", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_CORPUSORFLAT, "Корпус или квартира", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_OFFICE, "Офис", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_PAVILION, "Павильон", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_PLOT, "Участок", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_FIELD, "Поле", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_BLOCK, "Блок", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_BOX, "Бокс", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_KILOMETER, "Километр", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_GEO, "Город\\Регион\\Страна", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_ZIP, "Индекс", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_POSTOFFICEBOX, "Абоненский ящик", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_CSP, "ГСП", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_METRO, "Метро", 0, 1);
        com.pullenti.ner.metadata.Feature detail = globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_DETAIL, "Дополнительный указатель", 0, 1);
        globalMeta.detailFeature = detail;
        detail.addValue(com.pullenti.ner.address.AddressDetailType.CROSS.toString(), "На пересечении", null, null);
        detail.addValue(com.pullenti.ner.address.AddressDetailType.NEAR.toString(), "Вблизи", null, null);
        detail.addValue(com.pullenti.ner.address.AddressDetailType.HOSTEL.toString(), "Общежитие", null, null);
        detail.addValue(com.pullenti.ner.address.AddressDetailType.NORTH.toString(), "Севернее", null, null);
        detail.addValue(com.pullenti.ner.address.AddressDetailType.SOUTH.toString(), "Южнее", null, null);
        detail.addValue(com.pullenti.ner.address.AddressDetailType.EAST.toString(), "Восточнее", null, null);
        detail.addValue(com.pullenti.ner.address.AddressDetailType.WEST.toString(), "Западнее", null, null);
        detail.addValue(com.pullenti.ner.address.AddressDetailType.NORTHEAST.toString(), "Северо-восточнее", null, null);
        detail.addValue(com.pullenti.ner.address.AddressDetailType.NORTHWEST.toString(), "Северо-западнее", null, null);
        detail.addValue(com.pullenti.ner.address.AddressDetailType.SOUTHEAST.toString(), "Юго-восточнее", null, null);
        detail.addValue(com.pullenti.ner.address.AddressDetailType.SOUTHWEST.toString(), "Юго-западнее", null, null);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_MISC, "Разное", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_DETAILPARAM, "Параметр детализации", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_DETAILREF, "Объект детализации", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_FIAS, "Объект ФИАС", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.address.AddressReferent.ATTR_BTI, "Объект БТИ", 0, 1);
    }

    public com.pullenti.ner.metadata.Feature detailFeature;

    public com.pullenti.ner.metadata.Feature houseTypeFeature;

    public com.pullenti.ner.metadata.Feature buildingTypeFeature;

    @Override
    public String getName() {
        return com.pullenti.ner.address.AddressReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Адрес";
    }


    public static String ADDRESSIMAGEID = "address";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return ADDRESSIMAGEID;
    }

    public static MetaAddress globalMeta;
    public MetaAddress() {
        super();
    }
}
