/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner;

/**
 * Токен, соответствующий сущности
 * Токен сущности
 */
public class ReferentToken extends MetaToken {

    public ReferentToken(Referent entity, Token begin, Token end, com.pullenti.ner.core.AnalysisKit _kit) {
        super(begin, end, _kit);
        referent = entity;
        if (getMorph() == null) 
            setMorph(new MorphCollection(null));
    }

    /**
     * Ссылка на сущность
     */
    public Referent referent;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder((referent == null ? "Null" : referent.toString()));
        if (getMorph() != null) 
            res.append(" ").append(this.getMorph().toString());
        return res.toString();
    }

    @Override
    public Referent getReferent() {
        return referent;
    }

    @Override
    public java.util.ArrayList<Referent> getReferents() {
        java.util.ArrayList<Referent> res = new java.util.ArrayList<Referent>();
        if (referent != null) 
            res.add(referent);
        java.util.ArrayList<Referent> ri = super.getReferents();
        if (ri != null) 
            com.pullenti.unisharp.Utils.addToArrayList(res, ri);
        return res;
    }

    public void saveToLocalOntology() {
        if (data == null) 
            return;
        if (kit.level > 5) 
            return;
        kit.level++;
        Referent r = data.registerReferent(referent);
        kit.level--;
        data = null;
        if (r != null) {
            referent = r;
            TextAnnotation anno = new TextAnnotation(null, null, null);
            anno.sofa = kit.getSofa();
            anno.setOccurenceOf(referent);
            anno.beginChar = this.getBeginChar();
            anno.endChar = this.getEndChar();
            referent.addOccurence(anno);
        }
    }

    public void setDefaultLocalOnto(Processor proc) {
        if (referent == null || kit == null || proc == null) 
            return;
        for (Analyzer a : proc.getAnalyzers()) {
            if (a.createReferent(referent.getTypeName()) != null) {
                data = kit.getAnalyzerData(a);
                break;
            }
        }
    }

    public com.pullenti.ner.core.AnalyzerData data;

    /**
     * Используется произвольным образом
     */
    public int miscAttrs;

    public void replaceReferent(Referent oldReferent, Referent newReferent) {
        if (referent == oldReferent) 
            referent = newReferent;
        if (getEndToken() == null) 
            return;
        for (Token t = getBeginToken(); t != null; t = t.getNext()) {
            if (t.getEndChar() > getEndChar()) 
                break;
            if (t instanceof ReferentToken) 
                ((ReferentToken)com.pullenti.unisharp.Utils.cast(t, ReferentToken.class)).replaceReferent(oldReferent, newReferent);
            if (t == getEndToken()) 
                break;
        }
    }

    @Override
    public void serialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        super.serialize(stream);
        int id = 0;
        if (referent != null && (referent.tag instanceof Integer)) 
            id = (int)referent.tag;
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, id);
    }

    @Override
    public void deserialize(com.pullenti.unisharp.Stream stream, com.pullenti.ner.core.AnalysisKit _kit, int vers) throws java.io.IOException {
        super.deserialize(stream, _kit, vers);
        int id = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        if (id > 0) 
            referent = _kit.getEntities().get(id - 1);
    }

    public static ReferentToken _new786(Referent _arg1, Token _arg2, Token _arg3, MorphCollection _arg4) {
        ReferentToken res = new ReferentToken(_arg1, _arg2, _arg3, null);
        res.setMorph(_arg4);
        return res;
    }

    public static ReferentToken _new788(Referent _arg1, Token _arg2, Token _arg3, Object _arg4) {
        ReferentToken res = new ReferentToken(_arg1, _arg2, _arg3, null);
        res.tag = _arg4;
        return res;
    }

    public static ReferentToken _new1424(Referent _arg1, Token _arg2, Token _arg3, MorphCollection _arg4, com.pullenti.ner.core.AnalyzerData _arg5) {
        ReferentToken res = new ReferentToken(_arg1, _arg2, _arg3, null);
        res.setMorph(_arg4);
        res.data = _arg5;
        return res;
    }

    public static ReferentToken _new1425(Referent _arg1, Token _arg2, Token _arg3, com.pullenti.ner.core.AnalyzerData _arg4) {
        ReferentToken res = new ReferentToken(_arg1, _arg2, _arg3, null);
        res.data = _arg4;
        return res;
    }

    public static ReferentToken _new2684(Referent _arg1, Token _arg2, Token _arg3, MorphCollection _arg4, int _arg5) {
        ReferentToken res = new ReferentToken(_arg1, _arg2, _arg3, null);
        res.setMorph(_arg4);
        res.miscAttrs = _arg5;
        return res;
    }

    public static ReferentToken _new2791(Referent _arg1, Token _arg2, Token _arg3, int _arg4) {
        ReferentToken res = new ReferentToken(_arg1, _arg2, _arg3, null);
        res.miscAttrs = _arg4;
        return res;
    }

    public static ReferentToken _new2801(Referent _arg1, Token _arg2, Token _arg3, MorphCollection _arg4, Object _arg5) {
        ReferentToken res = new ReferentToken(_arg1, _arg2, _arg3, null);
        res.setMorph(_arg4);
        res.tag = _arg5;
        return res;
    }
    public ReferentToken() {
        super();
    }
}
