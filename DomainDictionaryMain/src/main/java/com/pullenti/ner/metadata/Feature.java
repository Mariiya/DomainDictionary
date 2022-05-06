/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.metadata;

/**
 * Атрибут класса сущностей
 */
public class Feature {

    private String _name;

    public String getName() {
        return _name;
    }

    public String setName(String value) {
        _name = value;
        return _name;
    }


    private String _caption;

    public String getCaption() {
        return _caption;
    }

    public String setCaption(String value) {
        _caption = value;
        return _caption;
    }


    private int _lowerbound;

    public int getLowerBound() {
        return _lowerbound;
    }

    public int setLowerBound(int value) {
        _lowerbound = value;
        return _lowerbound;
    }


    private int _upperbound;

    public int getUpperBound() {
        return _upperbound;
    }

    public int setUpperBound(int value) {
        _upperbound = value;
        return _upperbound;
    }


    private boolean _showasparent;

    public boolean getShowAsParent() {
        return _showasparent;
    }

    public boolean setShowAsParent(boolean value) {
        _showasparent = value;
        return _showasparent;
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder((String)com.pullenti.unisharp.Utils.notnull(getCaption(), getName()));
        if (getUpperBound() > 0 || getLowerBound() > 0) {
            if (getUpperBound() == 0) 
                res.append("[").append(this.getLowerBound()).append("..*]");
            else if (getUpperBound() == getLowerBound()) 
                res.append("[").append(this.getUpperBound()).append("]");
            else 
                res.append("[").append(this.getLowerBound()).append("..").append(this.getUpperBound()).append("]");
        }
        return res.toString();
    }

    public java.util.ArrayList<String> innerValues = new java.util.ArrayList<String>();

    public java.util.ArrayList<String> outerValues = new java.util.ArrayList<String>();

    public java.util.ArrayList<String> outerValuesEN = new java.util.ArrayList<String>();

    public java.util.ArrayList<String> outerValuesUA = new java.util.ArrayList<String>();

    public String convertInnerValueToOuterValue(String innerValue, com.pullenti.morph.MorphLang lang) {
        if (innerValue == null) 
            return null;
        String val = innerValue.toString();
        for (int i = 0; i < innerValues.size(); i++) {
            if (com.pullenti.unisharp.Utils.stringsCompare(innerValues.get(i), val, true) == 0 && (i < outerValues.size())) {
                if (lang != null) {
                    if (lang.isUa() && (i < outerValuesUA.size()) && outerValuesUA.get(i) != null) 
                        return outerValuesUA.get(i);
                    if (lang.isEn() && (i < outerValuesEN.size()) && outerValuesEN.get(i) != null) 
                        return outerValuesEN.get(i);
                }
                return outerValues.get(i);
            }
        }
        return innerValue;
    }

    public String convertOuterValueToInnerValue(String outerValue) {
        if (outerValue == null) 
            return null;
        for (int i = 0; i < outerValues.size(); i++) {
            if (com.pullenti.unisharp.Utils.stringsCompare(outerValues.get(i), outerValue, true) == 0 && (i < innerValues.size())) 
                return innerValues.get(i);
            else if ((i < outerValuesUA.size()) && com.pullenti.unisharp.Utils.stringsEq(outerValuesUA.get(i), outerValue)) 
                return innerValues.get(i);
        }
        return outerValue;
    }

    public void addValue(String intVal, String extVal, String extValUa, String extValEng) {
        innerValues.add(intVal);
        outerValues.add(extVal);
        outerValuesUA.add(extValUa);
        outerValuesEN.add(extValEng);
    }

    public static Feature _new1909(String _arg1, String _arg2, int _arg3, int _arg4) {
        Feature res = new Feature();
        res.setName(_arg1);
        res.setCaption(_arg2);
        res.setLowerBound(_arg3);
        res.setUpperBound(_arg4);
        return res;
    }
    public Feature() {
    }
}
