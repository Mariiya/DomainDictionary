/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.metadata;

/**
 * Приходится работать через обёртку, так как ориентируемся на все платформы и языки
 */
public class ImageWrapper {

    /**
     * Уникальный идентификатор
     */
    public String id;

    /**
     * Байтовый поток иконки
     */
    public byte[] content;

    /**
     * А здесь Bitmap вы уж сами формируйте, если нужно
     */
    public Object image;

    public static ImageWrapper _new3053(String _arg1, byte[] _arg2) {
        ImageWrapper res = new ImageWrapper();
        res.id = _arg1;
        res.content = _arg2;
        return res;
    }
    public ImageWrapper() {
    }
}
