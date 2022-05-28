/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.semantic.internal;

public class AnalyzeHelper {

    public static com.pullenti.semantic.SemDocument process(com.pullenti.ner.AnalysisResult ar, com.pullenti.semantic.SemProcessParams pars) {
        com.pullenti.semantic.SemDocument txt = new com.pullenti.semantic.SemDocument();
        for (com.pullenti.ner.Token t = ar.firstToken; t != null; t = t.getNext()) {
            t.tag = null;
        }
        if (pars.progress != null) 
            pars.progress.call(null, new com.pullenti.unisharp.ProgressEventArgs(0, null));
        int pers0 = 0;
        for (com.pullenti.ner.Token t = ar.firstToken; t != null; t = t.getNext()) {
            if (pars.progress != null) {
                int p = t.getBeginChar();
                if (ar.getSofa().getText().length() < 100000) 
                    p = (p * 100) / ar.getSofa().getText().length();
                else 
                    p /= ((ar.getSofa().getText().length() / 100));
                if (p != pers0) {
                    pers0 = p;
                    pars.progress.call(null, new com.pullenti.unisharp.ProgressEventArgs(p, null));
                }
            }
            com.pullenti.ner.Token t1 = t;
            for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
                if (tt.isNewlineBefore()) {
                    if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt)) 
                        break;
                }
                t1 = tt;
            }
            try {
                _processBlock(txt, ar, t, t1);
            } catch (Exception ex) {
            }
            t = t1;
            if (pars.maxChar > 0 && t.getEndChar() > pars.maxChar) 
                break;
        }
        OptimizerHelper.optimize(txt, pars);
        if (pars.progress != null) 
            pars.progress.call(null, new com.pullenti.unisharp.ProgressEventArgs(100, null));
        return txt;
    }

    private static void _processBlock(com.pullenti.semantic.SemDocument res, com.pullenti.ner.AnalysisResult ar, com.pullenti.ner.Token t0, com.pullenti.ner.Token t1) {
        com.pullenti.semantic.SemBlock blk = new com.pullenti.semantic.SemBlock(res);
        for (com.pullenti.ner.Token t = t0; t != null && t.getEndChar() <= t1.getEndChar(); t = t.getNext()) {
            com.pullenti.ner.Token te = t;
            for (com.pullenti.ner.Token tt = t.getNext(); tt != null && tt.getEndChar() <= t1.getEndChar(); tt = tt.getNext()) {
                if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt)) 
                    break;
                else 
                    te = tt;
            }
            _processSentence(blk, ar, t, te);
            t = te;
        }
        if (blk.fragments.size() > 0) 
            res.blocks.add(blk);
    }

    private static void _processSentence(com.pullenti.semantic.SemBlock blk, com.pullenti.ner.AnalysisResult ar, com.pullenti.ner.Token t0, com.pullenti.ner.Token t1) {
        int cou = 0;
        for (com.pullenti.ner.Token t = t0; t != null && (t.getEndChar() < t1.getEndChar()); t = t.getNext(),cou++) {
        }
        if (cou > 70) {
            int cou2 = 0;
            for (com.pullenti.ner.Token t = t0; t != null && (t.getEndChar() < t1.getEndChar()); t = t.getNext(),cou2++) {
                if (cou2 >= 70) {
                    t1 = t;
                    break;
                }
            }
        }
        java.util.ArrayList<Sentence> sents = Sentence.parseVariants(t0, t1, 0, 100, SentItemType.UNDEFINED);
        if (sents == null) 
            return;
        double max = -1.0;
        Sentence best = null;
        Sentence alt = null;
        for (Sentence s : sents) {
            if ((t1 instanceof com.pullenti.ner.TextToken) && !t1.chars.isLetter()) 
                s.lastChar = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class);
            s.calcCoef(false);
            if (s.coef > max) {
                max = s.coef;
                best = s;
                alt = null;
            }
            else if (s.coef == max && max > 0) 
                alt = s;
        }
        if (best != null && best.resBlock != null) 
            best.addToBlock(blk, null);
    }
    public AnalyzeHelper() {
    }
}
