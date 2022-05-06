/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner;

/**
 * Базовый класс для всех токенов. Наследные классы - TextToken (конечная словоформа) и MetaToken (связный фрагмент других токенов).
 * 
 * Токен
 */
public class Token {

    public Token(com.pullenti.ner.core.AnalysisKit _kit, int begin, int end) {
        kit = _kit;
        m_BeginChar = begin;
        m_EndChar = end;
    }

    /**
     * Аналитический контейнер
     */
    public com.pullenti.ner.core.AnalysisKit kit;

    public int getBeginChar() {
        return m_BeginChar;
    }


    private int m_BeginChar;

    public int getEndChar() {
        return m_EndChar;
    }


    private int m_EndChar;

    public int getLengthChar() {
        return (getEndChar() - getBeginChar()) + 1;
    }


    public boolean isIgnored() {
        if (kit.getSofa().ignoredEndChar > 0) {
            if (getBeginChar() >= kit.getSofa().ignoredBeginChar && getEndChar() <= kit.getSofa().ignoredEndChar) 
                return true;
        }
        return false;
    }


    /**
     * Используется произвольным образом
     */
    public Object tag;

    public Token getPrevious() {
        return m_Previous;
    }

    public Token setPrevious(Token value) {
        m_Previous = value;
        if (value != null) 
            value.m_Next = this;
        m_Attrs = (short)0;
        return value;
    }


    public Token m_Previous;

    public Token getNext() {
        return m_Next;
    }

    public Token setNext(Token value) {
        m_Next = value;
        if (value != null) 
            value.m_Previous = this;
        m_Attrs = (short)0;
        return value;
    }


    public Token m_Next;

    public MorphCollection getMorph() {
        if (m_Morph == null) 
            m_Morph = new MorphCollection(null);
        return m_Morph;
    }

    public MorphCollection setMorph(MorphCollection value) {
        m_Morph = value;
        return value;
    }


    private MorphCollection m_Morph;

    /**
     * Информация о символах
     * 
     */
    public com.pullenti.morph.CharsInfo chars;

    @Override
    public String toString() {
        return kit.getSofa().getText().substring(this.getBeginChar(), this.getBeginChar() + (this.getEndChar() + 1) - this.getBeginChar());
    }

    private short m_Attrs;

    private boolean getAttr(int i) {
        char ch;
        if (((((int)m_Attrs) & 1)) == 0) {
            m_Attrs = (short)1;
            if (m_Previous == null) {
                this.setAttr(1, true);
                this.setAttr(3, true);
            }
            else 
                for (int j = m_Previous.getEndChar() + 1; j < getBeginChar(); j++) {
                    if (com.pullenti.unisharp.Utils.isWhitespace(((ch = kit.getSofa().getText().charAt(j))))) {
                        this.setAttr(1, true);
                        if ((((int)ch) == 0xD || ((int)ch) == 0xA || ch == '\f') || ((int)ch) == 0x2028) 
                            this.setAttr(3, true);
                    }
                }
            if (m_Next == null) {
                this.setAttr(2, true);
                this.setAttr(4, true);
            }
            else 
                for (int j = getEndChar() + 1; j < m_Next.getBeginChar(); j++) {
                    if (com.pullenti.unisharp.Utils.isWhitespace((ch = kit.getSofa().getText().charAt(j)))) {
                        this.setAttr(2, true);
                        if ((((int)ch) == 0xD || ((int)ch) == 0xA || ch == '\f') || ((int)ch) == 0x2028) 
                            this.setAttr(4, true);
                    }
                }
        }
        return ((((((int)m_Attrs) >> i)) & 1)) != 0;
    }

    protected void setAttr(int i, boolean val) {
        if (val) 
            m_Attrs |= ((short)(1 << i));
        else 
            m_Attrs &= ((short)(~(1 << i)));
    }

    public boolean isWhitespaceBefore() {
        return this.getAttr(1);
    }

    public boolean setWhitespaceBefore(boolean value) {
        this.setAttr(1, value);
        return value;
    }


    public boolean isWhitespaceAfter() {
        return this.getAttr(2);
    }

    public boolean setWhitespaceAfter(boolean value) {
        this.setAttr(2, value);
        return value;
    }


    public boolean isNewlineBefore() {
        return this.getAttr(3);
    }

    public boolean setNewlineBefore(boolean value) {
        this.setAttr(3, value);
        return value;
    }


    public boolean isNewlineAfter() {
        return this.getAttr(4);
    }

    public boolean setNewlineAfter(boolean value) {
        this.setAttr(4, value);
        return value;
    }


    public boolean getInnerBool() {
        return this.getAttr(5);
    }

    public boolean setInnerBool(boolean value) {
        this.setAttr(5, value);
        return value;
    }


    public boolean getNotNounPhrase() {
        return this.getAttr(6);
    }

    public boolean setNotNounPhrase(boolean value) {
        this.setAttr(6, value);
        return value;
    }


    public int getWhitespacesBeforeCount() {
        if (getPrevious() == null) 
            return 100;
        if ((getPrevious().getEndChar() + 1) == getBeginChar()) 
            return 0;
        return this.calcWhitespaces(this.getPrevious().getEndChar() + 1, this.getBeginChar() - 1);
    }


    public int getNewlinesBeforeCount() {
        char ch0 = (char)0;
        int res = 0;
        String txt = kit.getSofa().getText();
        for (int p = getBeginChar() - 1; p >= 0; p--) {
            char ch = txt.charAt(p);
            if (((int)ch) == 0xA || ((int)ch) == 0x2028) 
                res++;
            else if (((int)ch) == 0xD && ((int)ch0) != 0xA) 
                res++;
            else if (ch == '\f') 
                res += 10;
            else if (!com.pullenti.unisharp.Utils.isWhitespace(ch)) 
                break;
            ch0 = ch;
        }
        return res;
    }


    public int getNewlinesAfterCount() {
        char ch0 = (char)0;
        int res = 0;
        String txt = kit.getSofa().getText();
        for (int p = getEndChar() + 1; p < txt.length(); p++) {
            char ch = txt.charAt(p);
            if (((int)ch) == 0xD || ((int)ch) == 0x2028) 
                res++;
            else if (((int)ch) == 0xA && ((int)ch0) != 0xD) 
                res++;
            else if (ch == '\f') 
                res += 10;
            else if (!com.pullenti.unisharp.Utils.isWhitespace(ch)) 
                break;
            ch0 = ch;
        }
        return res;
    }


    public int getWhitespacesAfterCount() {
        if (getNext() == null) 
            return 100;
        if ((getEndChar() + 1) == getNext().getBeginChar()) 
            return 0;
        return this.calcWhitespaces(this.getEndChar() + 1, this.getNext().getBeginChar() - 1);
    }


    private int calcWhitespaces(int p0, int p1) {
        if ((p0 < 0) || p0 > p1 || p1 >= kit.getSofa().getText().length()) 
            return -1;
        int res = 0;
        for (int i = p0; i <= p1; i++) {
            char ch = kit.getTextCharacter(i);
            if (ch == '\r' || ch == '\n' || ((int)ch) == 0x2028) {
                res += 10;
                char ch1 = kit.getTextCharacter(i + 1);
                if (ch != ch1 && ((ch1 == '\r' || ch1 == '\n'))) 
                    i++;
            }
            else if (ch == '\t') 
                res += 5;
            else if (ch == '\u0007') 
                res += 100;
            else if (ch == '\f') 
                res += 100;
            else 
                res++;
        }
        return res;
    }

    public boolean isHiphen() {
        char ch = kit.getSofa().getText().charAt(this.getBeginChar());
        return com.pullenti.morph.LanguageHelper.isHiphen(ch);
    }


    public boolean isTableControlChar() {
        char ch = kit.getSofa().getText().charAt(this.getBeginChar());
        return ((int)ch) == 7 || ((int)ch) == 0x1F || ((int)ch) == 0x1E;
    }


    public boolean isAnd() {
        return false;
    }


    public boolean isOr() {
        return false;
    }


    public boolean isComma() {
        return this.isChar(',');
    }


    public boolean isCommaAnd() {
        return isComma() || isAnd();
    }


    /**
     * Токен состоит из конкретного символа
     * @param ch проверяемый символ
     * @return 
     */
    public boolean isChar(char ch) {
        if (getBeginChar() != getEndChar()) 
            return false;
        return kit.getSofa().getText().charAt(this.getBeginChar()) == ch;
    }

    /**
     * Токен состоит из одного символа, который есть в указанной строке
     * @param _chars строка возможных символов
     * @return 
     */
    public boolean isCharOf(String _chars) {
        if (getBeginChar() != getEndChar()) 
            return false;
        return _chars.indexOf(kit.getSofa().getText().charAt(this.getBeginChar())) >= 0;
    }

    /**
     * Проверка конкретного значения слова (с учётом морф.вариантов)
     * @param term слово (проверяется значение TextToken.Term и все морфварианты)
     * @param termUA слово для проверки на украинском языке
     * @return да-нет
     */
    public boolean isValue(String term, String termUA) {
        return false;
    }

    /**
     * Проверка двух подряд идущих слов (с учётом морф.вариантов)
     * @param term слово (проверяется значение TextToken.Term и все морфварианты)
     * @param nextTerm слово в следующем токене
     * @return 
     */
    public boolean isValue2(String term, String nextTerm) {
        if (getNext() == null) 
            return false;
        if (!this.isValue(term, null)) 
            return false;
        return getNext().isValue(nextTerm, null);
    }

    public boolean isLetters() {
        return false;
    }


    /**
     * Получить ссылку на сущность (не null только для ReferentToken)
     * 
     */
    public Referent getReferent() {
        return null;
    }

    /**
     * Получить список ссылок на все сущности, скрывающиеся под элементом. 
     * Дело в том, что одни сущности могут накрывать другие (например, адрес накроет город).
     * @return 
     */
    public java.util.ArrayList<Referent> getReferents() {
        return null;
    }

    /**
     * Получить связанный с токеном текст в именительном падеже
     * @param mc желательная часть речи
     * @param num желательное число
     * @param gender желательный пол
     * @param keepChars сохранять регистр символов (по умолчанию, всё в верхний)
     * @return строка текста
     */
    public String getNormalCaseText(com.pullenti.morph.MorphClass mc, com.pullenti.morph.MorphNumber num, com.pullenti.morph.MorphGender gender, boolean keepChars) {
        return this.toString();
    }

    /**
     * Получить фрагмент исходного текста, связанный с токеном
     * @return фрагмент исходного текста
     */
    public String getSourceText() {
        int len = (getEndChar() + 1) - getBeginChar();
        if ((len < 1) || (getBeginChar() < 0)) 
            return null;
        if ((getBeginChar() + len) > kit.getSofa().getText().length()) 
            return null;
        return kit.getSofa().getText().substring(this.getBeginChar(), this.getBeginChar() + len);
    }

    /**
     * Проверка, что слово есть в словаре соответствующего языка
     * @return части речи, если не из словаря, то IsUndefined
     */
    public com.pullenti.morph.MorphClass getMorphClassInDictionary() {
        return getMorph()._getClass();
    }

    public void serialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, this.getBeginChar());
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, this.getEndChar());
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, (int)m_Attrs);
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, (int)chars.value);
        if (m_Morph == null) 
            m_Morph = new MorphCollection(null);
        m_Morph.serialize(stream);
    }

    public void deserialize(com.pullenti.unisharp.Stream stream, com.pullenti.ner.core.AnalysisKit _kit, int vers) throws java.io.IOException {
        kit = _kit;
        m_BeginChar = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        m_EndChar = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        m_Attrs = (short)com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        chars = com.pullenti.morph.CharsInfo._new2767((short)com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream));
        m_Morph = new MorphCollection(null);
        m_Morph.deserialize(stream);
    }
    public Token() {
    }
}
