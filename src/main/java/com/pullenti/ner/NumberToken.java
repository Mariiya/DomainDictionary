/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner;

/**
 * Метатокен - число (числительное). Причём задаваемое не только цифрами, но и словами, возможно, римская запись и др. 
 * Для получения см. методы NumberHelper.
 * 
 * Числовой токен
 */
public class NumberToken extends MetaToken {

    public NumberToken(Token begin, Token end, String val, NumberSpellingType _typ, com.pullenti.ner.core.AnalysisKit _kit) {
        super(begin, end, _kit);
        setValue(val);
        typ = _typ;
    }

    public String getValue() {
        return m_Value;
    }

    public String setValue(String _value) {
        m_Value = (_value != null ? _value : "");
        if (m_Value.length() > 2 && m_Value.endsWith(".0")) 
            m_Value = m_Value.substring(0, 0 + m_Value.length() - 2);
        while (m_Value.length() > 1 && m_Value.charAt(0) == '0' && m_Value.charAt(1) != '.') {
            m_Value = m_Value.substring(1);
        }
        int n;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapn3043 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres3044 = com.pullenti.unisharp.Utils.parseInteger(m_Value, 0, null, wrapn3043);
        n = (wrapn3043.value != null ? wrapn3043.value : 0);
        if (inoutres3044) 
            m_IntVal = n;
        else 
            m_IntVal = null;
        Double d = com.pullenti.ner.core.NumberHelper.stringToDouble(m_Value);
        if (d == null) 
            m_RealVal = Double.NaN;
        else 
            m_RealVal = d;
        return _value;
    }


    private String m_Value;

    private Integer m_IntVal;

    private double m_RealVal;

    public Integer getIntValue() {
        return m_IntVal;
    }

    public Integer setIntValue(Integer _value) {
        setValue(_value.toString());
        return _value;
    }


    public double getRealValue() {
        return m_RealVal;
    }

    public double setRealValue(double _value) {
        setValue(com.pullenti.ner.core.NumberHelper.doubleToString(_value));
        return _value;
    }


    /**
     * Тип числительного
     */
    public NumberSpellingType typ = NumberSpellingType.DIGIT;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(this.getValue()).append(" ").append(typ.toString());
        if (getMorph() != null) 
            res.append(" ").append(this.getMorph().toString());
        return res.toString();
    }

    @Override
    public String getNormalCaseText(com.pullenti.morph.MorphClass mc, com.pullenti.morph.MorphNumber num, com.pullenti.morph.MorphGender gender, boolean keepChars) {
        return getValue().toString();
    }

    @Override
    public void serialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        super.serialize(stream);
        com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, m_Value);
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, typ.value());
    }

    @Override
    public void deserialize(com.pullenti.unisharp.Stream stream, com.pullenti.ner.core.AnalysisKit _kit, int vers) throws java.io.IOException {
        super.deserialize(stream, _kit, vers);
        if (vers == 0) {
            byte[] buf = new byte[8];
            stream.read(buf, 0, 8);
            long lo = java.nio.ByteBuffer.wrap(buf, 0, 8).order(java.nio.ByteOrder.LITTLE_ENDIAN).getLong();
            setValue(((Long)lo).toString());
        }
        else 
            setValue(com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream));
        typ = NumberSpellingType.of(com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream));
    }

    public void _corrDrob(double val) {
        Token t = getEndToken().getNext();
        if (t == null) 
            return;
        if (t.isValue("ДЕСЯТИ", null) && t.getNext() != null && t.getNext().isValue("ТЫСЯЧНЫЙ", "ТИСЯЧНИЙ")) {
            setEndToken(t.getNext());
            setRealValue((((val) / (10000.0))) + this.getRealValue());
            return;
        }
        if (t.isValue("ДЕСЯТИ", null) && t.getNext() != null && t.getNext().isValue("МИЛЛИОННЫЙ", "МІЛЬЙОННИЙ")) {
            setEndToken(t.getNext());
            setRealValue((((val) / (10000000.0))) + this.getRealValue());
            return;
        }
        if (t.isValue("ДЕСЯТЫЙ", "ДЕСЯТИЙ")) {
            setEndToken(t);
            setRealValue((((val) / (10.0))) + this.getRealValue());
            return;
        }
        if (t.isValue("СТО", null) && t.getNext() != null && t.getNext().isValue("ТЫСЯЧНЫЙ", "ТИСЯЧНИЙ")) {
            setEndToken(t.getNext());
            setRealValue((((val) / (100000.0))) + this.getRealValue());
            return;
        }
        if (t.isValue("СТО", null) && t.getNext() != null && t.getNext().isValue("МИЛЛИОННЫЙ", "МІЛЬЙОННИЙ")) {
            setEndToken(t.getNext());
            setRealValue((((val) / (100000000.0))) + this.getRealValue());
            return;
        }
        if (t.isValue("СОТЫЙ", "СОТИЙ")) {
            setEndToken(t);
            setRealValue((((val) / (100.0))) + this.getRealValue());
            return;
        }
        if (t.isValue("ТЫСЯЧНЫЙ", "ТИСЯЧНИЙ")) {
            setEndToken(t);
            setRealValue((((val) / (1000.0))) + this.getRealValue());
            return;
        }
        if (t.isValue("ДЕСЯТИТЫСЯЧНЫЙ", "ДЕСЯТИТИСЯЧНИЙ") || (((t instanceof NumberToken) && com.pullenti.unisharp.Utils.stringsEq(((NumberToken)com.pullenti.unisharp.Utils.cast(t, NumberToken.class)).getValue(), "10000")))) {
            setEndToken(t);
            setRealValue((((val) / (10000.0))) + this.getRealValue());
            return;
        }
        if (t.isValue("СТОТЫСЯЧНЫЙ", "СТОТИСЯЧНИЙ") || (((t instanceof NumberToken) && com.pullenti.unisharp.Utils.stringsEq(((NumberToken)com.pullenti.unisharp.Utils.cast(t, NumberToken.class)).getValue(), "100000")))) {
            setEndToken(t);
            setRealValue((((val) / (100000.0))) + this.getRealValue());
            return;
        }
        if (t.isValue("МИЛЛИОННЫЙ", "МІЛЬЙОННИЙ")) {
            setEndToken(t);
            setRealValue((((val) / (1000000.0))) + this.getRealValue());
            return;
        }
        if (t.isValue("ДЕСЯТИМИЛЛИОННЫЙ", "ДЕСЯТИМІЛЬЙОННИЙ") || (((t instanceof NumberToken) && com.pullenti.unisharp.Utils.stringsEq(((NumberToken)com.pullenti.unisharp.Utils.cast(t, NumberToken.class)).getValue(), "10000000")))) {
            setEndToken(t);
            setRealValue((((val) / (10000000.0))) + this.getRealValue());
            return;
        }
        if (t.isValue("СТОМИЛЛИОННЫЙ", "СТОМІЛЬЙОННИЙ") || (((t instanceof NumberToken) && com.pullenti.unisharp.Utils.stringsEq(((NumberToken)com.pullenti.unisharp.Utils.cast(t, NumberToken.class)).getValue(), "100000000")))) {
            setEndToken(t);
            setRealValue((((val) / (10000000.0))) + this.getRealValue());
            return;
        }
    }

    public static NumberToken _new519(Token _arg1, Token _arg2, String _arg3, NumberSpellingType _arg4, MorphCollection _arg5) {
        NumberToken res = new NumberToken(_arg1, _arg2, _arg3, _arg4, null);
        res.setMorph(_arg5);
        return res;
    }
    public NumberToken() {
        super();
    }
}
