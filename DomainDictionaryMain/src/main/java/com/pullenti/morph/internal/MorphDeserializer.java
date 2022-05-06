/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.morph.internal;

public class MorphDeserializer {

    public static void deflateGzip(com.pullenti.unisharp.Stream str, com.pullenti.unisharp.Stream res) throws Exception, java.io.IOException {
        try (com.pullenti.unisharp.GZipStream deflate = new com.pullenti.unisharp.GZipStream(str, false)) {
            byte[] buf = new byte[100000];
            int len = buf.length;
            while (true) {
                int i = -1;
                try {
                    for (int ii = 0; ii < len; ii++) {
                        buf[ii] = (byte)0;
                    }
                    i = deflate.read(buf, 0, len);
                } catch (Exception ex) {
                    for (i = len - 1; i >= 0; i--) {
                        if (buf[i] != ((byte)0)) {
                            res.write(buf, 0, i + 1);
                            break;
                        }
                    }
                    break;
                }
                if (i < 1) 
                    break;
                res.write(buf, 0, i);
            }
        }
    }
    public MorphDeserializer() {
    }
}
