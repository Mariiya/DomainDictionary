/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.internal;

public class AlgoParam {

    public String name;

    public double value;

    public double min;

    public double max;

    public double delta;

    public int getCount() {
        return ((int)((((max - min)) / delta))) + 1;
    }


    @Override
    public String toString() {
        return (name + "=" + value + " [" + min + " .. " + max + "] by " + delta);
    }

    public static AlgoParam _new3101(String _arg1, double _arg2, double _arg3, double _arg4) {
        AlgoParam res = new AlgoParam();
        res.name = _arg1;
        res.min = _arg2;
        res.max = _arg3;
        res.delta = _arg4;
        return res;
    }
    public AlgoParam() {
    }
}
