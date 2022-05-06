/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph.internal;

// Сделан специально для Питона - а то стандартым способом через Memory Stream
// жутко тормозит, придётся делать самим
public class ByteArrayWrapper {

    public byte[] m_Array;

    private int m_Len;

    public ByteArrayWrapper(byte[] arr) {
        m_Array = arr;
        m_Len = m_Array.length;
    }

    public boolean isEOF(int pos) {
        return pos >= m_Len;
    }

    public byte deserializeByte(com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        if (pos.value >= m_Len) 
            return (byte)0;
        return m_Array[pos.value++];
    }

    public int deserializeShort(com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        if ((pos.value + 1) >= m_Len) 
            return 0;
        byte b0 = m_Array[pos.value++];
        byte b1 = m_Array[pos.value++];
        int res = Byte.toUnsignedInt(b1);
        res <<= 8;
        return (res | (Byte.toUnsignedInt(b0)));
    }

    public int deserializeInt(com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        if ((pos.value + 1) >= m_Len) 
            return 0;
        byte b0 = m_Array[pos.value++];
        byte b1 = m_Array[pos.value++];
        byte b2 = m_Array[pos.value++];
        byte b3 = m_Array[pos.value++];
        int res = Byte.toUnsignedInt(b3);
        res <<= 8;
        res |= (Byte.toUnsignedInt(b2));
        res <<= 8;
        res |= (Byte.toUnsignedInt(b1));
        res <<= 8;
        return (res | (Byte.toUnsignedInt(b0)));
    }

    public String deserializeString(com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        if (pos.value >= m_Len) 
            return null;
        byte len = m_Array[pos.value++];
        if (len == ((byte)0xFF)) 
            return null;
        if (len == ((byte)0)) 
            return "";
        if ((pos.value + (Byte.toUnsignedInt(len))) > m_Len) 
            return null;
        String res = com.pullenti.unisharp.Utils.decodeCharset(java.nio.charset.Charset.forName("UTF-8"), m_Array, pos.value, Byte.toUnsignedInt(len));
        pos.value += (Byte.toUnsignedInt(len));
        return res;
    }

    public String deserializeStringEx(com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        if (pos.value >= m_Len) 
            return null;
        int len = this.deserializeShort(pos);
        if (len == 0xFFFF || (len < 0)) 
            return null;
        if (len == 0) 
            return "";
        if ((pos.value + len) > m_Len) 
            return null;
        String res = com.pullenti.unisharp.Utils.decodeCharset(java.nio.charset.Charset.forName("UTF-8"), m_Array, pos.value, len);
        pos.value += len;
        return res;
    }
    public ByteArrayWrapper() {
    }
}
