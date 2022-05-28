/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.uri;

/**
 * Сущность URI - всё, что укладывается в СХЕМА:ЗНАЧЕНИЕ (www, email, ISBN, УДК, ББК, ICQ и пр.)
 * 
 */
public class UriReferent extends com.pullenti.ner.Referent {

    public UriReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.uri.internal.MetaUri.globalMeta);
    }

    /**
     * Имя типа сущности TypeName ("URI")
     */
    public static final String OBJ_TYPENAME = "URI";

    /**
     * Имя атрибута - значение (без схемы)
     */
    public static final String ATTR_VALUE = "VALUE";

    /**
     * Имя атрибута - детализация
     */
    public static final String ATTR_DETAIL = "DETAIL";

    /**
     * Имя атрибута - схема
     */
    public static final String ATTR_SCHEME = "SCHEME";

    @Override
    public String toStringEx(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        if (getScheme() != null) {
            String split = ":";
            if (com.pullenti.unisharp.Utils.stringsEq(getScheme(), "ISBN") || com.pullenti.unisharp.Utils.stringsEq(getScheme(), "ББК") || com.pullenti.unisharp.Utils.stringsEq(getScheme(), "УДК")) 
                split = " ";
            else if (com.pullenti.unisharp.Utils.stringsEq(getScheme(), "http") || com.pullenti.unisharp.Utils.stringsEq(getScheme(), "ftp") || com.pullenti.unisharp.Utils.stringsEq(getScheme(), "https")) 
                split = "://";
            return (this.getScheme() + split + ((String)com.pullenti.unisharp.Utils.notnull(this.getValue(), "?")));
        }
        else 
            return getValue();
    }

    public String getValue() {
        return this.getStringValue(ATTR_VALUE);
    }

    public String setValue(String _value) {
        String val = _value;
        this.addSlot(ATTR_VALUE, val, true, 0);
        return _value;
    }


    public String getScheme() {
        return this.getStringValue(ATTR_SCHEME);
    }

    public String setScheme(String _value) {
        this.addSlot(ATTR_SCHEME, _value, true, 0);
        return _value;
    }


    public String getDetail() {
        return this.getStringValue(ATTR_DETAIL);
    }

    public String setDetail(String _value) {
        this.addSlot(ATTR_DETAIL, _value, true, 0);
        return _value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.core.ReferentsEqualType typ) {
        UriReferent _uri = (UriReferent)com.pullenti.unisharp.Utils.cast(obj, UriReferent.class);
        if (_uri == null) 
            return false;
        return com.pullenti.unisharp.Utils.stringsCompare(this.getValue(), _uri.getValue(), true) == 0;
    }

    public static UriReferent _new2921(String _arg1, String _arg2) {
        UriReferent res = new UriReferent();
        res.setScheme(_arg1);
        res.setValue(_arg2);
        return res;
    }

    public static UriReferent _new2924(String _arg1, String _arg2) {
        UriReferent res = new UriReferent();
        res.setValue(_arg1);
        res.setScheme(_arg2);
        return res;
    }
}
