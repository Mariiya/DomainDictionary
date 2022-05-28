/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.uri.internal;

public class UriItemToken extends com.pullenti.ner.MetaToken {

    private UriItemToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    public String value;

    public static UriItemToken attachUriContent(com.pullenti.ner.Token t0, boolean afterHttp) {
        UriItemToken res = _AttachUriContent(t0, ".;:-_=+&%#@/\\?[]()!~", afterHttp);
        if (res == null) 
            return null;
        if (res.getEndToken().isCharOf(".;-:") && res.getEndChar() > 3) {
            res.setEndToken(res.getEndToken().getPrevious());
            res.value = res.value.substring(0, 0 + res.value.length() - 1);
        }
        if (res.value.endsWith("/")) 
            res.value = res.value.substring(0, 0 + res.value.length() - 1);
        if (res.value.endsWith("\\")) 
            res.value = res.value.substring(0, 0 + res.value.length() - 1);
        if (res.value.indexOf('\\') > 0) 
            res.value = res.value.replace('\\', '/');
        return res;
    }

    public static UriItemToken attachISOContent(com.pullenti.ner.Token t0, String specChars) {
        com.pullenti.ner.Token t = t0;
        while (true) {
            if (t == null) 
                return null;
            if (t.isCharOf(":/\\") || t.isHiphen() || t.isValue("IEC", null)) {
                t = t.getNext();
                continue;
            }
            break;
        }
        if (!(t instanceof com.pullenti.ner.NumberToken)) 
            return null;
        com.pullenti.ner.Token t1 = t;
        char delim = (char)0;
        StringBuilder txt = new StringBuilder();
        for (; t != null; t = t.getNext()) {
            if (t.isWhitespaceBefore() && t != t1) 
                break;
            if (t instanceof com.pullenti.ner.NumberToken) {
                if (delim != ((char)0)) 
                    txt.append(delim);
                delim = (char)0;
                t1 = t;
                txt.append(t.getSourceText());
                continue;
            }
            if (!(t instanceof com.pullenti.ner.TextToken)) 
                break;
            if (!t.isCharOf(specChars)) 
                break;
            delim = t.getSourceText().charAt(0);
        }
        if (txt.length() == 0) 
            return null;
        return _new2913(t0, t1, txt.toString());
    }

    private static UriItemToken _AttachUriContent(com.pullenti.ner.Token t0, String _chars, boolean canBeWhitespaces) {
        StringBuilder txt = new StringBuilder();
        com.pullenti.ner.Token t1 = t0;
        UriItemToken dom = attachDomainName(t0, true, canBeWhitespaces);
        if (dom != null) {
            if (dom.value.length() < 3) 
                return null;
        }
        char openChar = (char)0;
        com.pullenti.ner.Token t = t0;
        if (dom != null) 
            t = dom.getEndToken().getNext();
        for (; t != null; t = t.getNext()) {
            if (t != t0 && t.isWhitespaceBefore()) {
                if (t.isNewlineBefore() || !canBeWhitespaces) 
                    break;
                if (dom == null) 
                    break;
                if (t.getPrevious().isHiphen()) {
                }
                else if (t.getPrevious().isCharOf(",;")) 
                    break;
                else if (t.getPrevious().isChar('.') && t.chars.isLetter() && t.getLengthChar() == 2) {
                }
                else {
                    boolean ok = false;
                    com.pullenti.ner.Token tt1 = t;
                    if (t.isCharOf("\\/")) 
                        tt1 = t.getNext();
                    com.pullenti.ner.Token tt0 = tt1;
                    for (; tt1 != null; tt1 = tt1.getNext()) {
                        if (tt1 != tt0 && tt1.isWhitespaceBefore()) 
                            break;
                        if (tt1 instanceof com.pullenti.ner.NumberToken) 
                            continue;
                        if (!(tt1 instanceof com.pullenti.ner.TextToken)) 
                            break;
                        String term1 = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt1, com.pullenti.ner.TextToken.class)).term;
                        if (((com.pullenti.unisharp.Utils.stringsEq(term1, "HTM") || com.pullenti.unisharp.Utils.stringsEq(term1, "HTML") || com.pullenti.unisharp.Utils.stringsEq(term1, "SHTML")) || com.pullenti.unisharp.Utils.stringsEq(term1, "ASP") || com.pullenti.unisharp.Utils.stringsEq(term1, "ASPX")) || com.pullenti.unisharp.Utils.stringsEq(term1, "JSP")) {
                            ok = true;
                            break;
                        }
                        if (!tt1.chars.isLetter()) {
                            if (tt1.isCharOf("\\/")) {
                                ok = true;
                                break;
                            }
                            if (!tt1.isCharOf(_chars)) 
                                break;
                        }
                        else if (!tt1.chars.isLatinLetter()) 
                            break;
                    }
                    if (!ok) 
                        break;
                }
            }
            if (t instanceof com.pullenti.ner.NumberToken) {
                com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
                txt.append(nt.getSourceText());
                t1 = t;
                continue;
            }
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
            if (tt == null) {
                com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
                if (rt != null && rt.getBeginToken().isValue("РФ", null)) {
                    if (txt.length() > 0 && txt.charAt(txt.length() - 1) == '.') {
                        txt.append(rt.getBeginToken().getSourceText());
                        t1 = t;
                        continue;
                    }
                }
                if (rt != null && rt.chars.isLatinLetter() && rt.getBeginToken() == rt.getEndToken()) {
                    txt.append(rt.getBeginToken().getSourceText());
                    t1 = t;
                    continue;
                }
                break;
            }
            String src = tt.getSourceText();
            char ch = src.charAt(0);
            if (!Character.isLetter(ch)) {
                if (_chars.indexOf(ch) < 0) 
                    break;
                if (ch == '(' || ch == '[') 
                    openChar = ch;
                else if (ch == ')') {
                    if (openChar != '(') 
                        break;
                    openChar = (char)0;
                }
                else if (ch == ']') {
                    if (openChar != '[') 
                        break;
                    openChar = (char)0;
                }
            }
            txt.append(src);
            t1 = t;
        }
        if (txt.length() == 0) 
            return dom;
        int i;
        for (i = 0; i < txt.length(); i++) {
            if (Character.isLetterOrDigit(txt.charAt(i))) 
                break;
        }
        if (i >= txt.length()) 
            return dom;
        if (txt.charAt(txt.length() - 1) == '.' || txt.charAt(txt.length() - 1) == '/') {
            txt.setLength(txt.length() - 1);
            t1 = t1.getPrevious();
        }
        if (dom != null) 
            txt.insert(0, dom.value);
        String tmp = txt.toString();
        if (tmp.startsWith("\\\\")) {
            com.pullenti.unisharp.Utils.replace(txt, "\\\\", "//");
            tmp = txt.toString();
        }
        if (tmp.startsWith("//")) 
            tmp = tmp.substring(2);
        if (com.pullenti.unisharp.Utils.stringsCompare(tmp, "WWW", true) == 0) 
            return null;
        UriItemToken res = _new2913(t0, t1, txt.toString());
        return res;
    }

    public static UriItemToken attachDomainName(com.pullenti.ner.Token t0, boolean _check, boolean canBeWhitspaces) {
        StringBuilder txt = new StringBuilder();
        com.pullenti.ner.Token t1 = t0;
        int ipCount = 0;
        boolean isIp = true;
        for (com.pullenti.ner.Token t = t0; t != null; t = t.getNext()) {
            if (t.isWhitespaceBefore() && t != t0) {
                boolean ok = false;
                if (!t.isNewlineBefore() && canBeWhitspaces) {
                    for (com.pullenti.ner.Token tt1 = t; tt1 != null; tt1 = tt1.getNext()) {
                        if (tt1.isChar('.') || tt1.isHiphen()) 
                            continue;
                        if (tt1.isWhitespaceBefore()) {
                            if (tt1.isNewlineBefore()) 
                                break;
                            if (tt1.getPrevious() != null && ((tt1.getPrevious().isChar('.') || tt1.getPrevious().isHiphen()))) {
                            }
                            else 
                                break;
                        }
                        if (!(tt1 instanceof com.pullenti.ner.TextToken)) 
                            break;
                        if (m_StdGroups.tryParse(tt1, com.pullenti.ner.core.TerminParseAttr.NO) != null) {
                            ok = true;
                            break;
                        }
                        if (!tt1.chars.isLatinLetter()) 
                            break;
                    }
                }
                if (!ok) 
                    break;
            }
            if (t instanceof com.pullenti.ner.NumberToken) {
                com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
                if (nt.getIntValue() == null) 
                    break;
                txt.append(nt.getSourceText());
                t1 = t;
                if (nt.typ == com.pullenti.ner.NumberSpellingType.DIGIT && nt.getIntValue() >= 0 && (nt.getIntValue() < 256)) 
                    ipCount++;
                else 
                    isIp = false;
                continue;
            }
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
            if (tt == null) 
                break;
            String src = ((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class)).term;
            char ch = src.charAt(0);
            if (!Character.isLetter(ch)) {
                if (".-_".indexOf(ch) < 0) 
                    break;
                if (ch != '.') 
                    isIp = false;
                if (ch == '-') {
                    if (com.pullenti.unisharp.Utils.stringsCompare(txt.toString(), "vk.com", true) == 0) 
                        return _new2913(t0, t1, txt.toString().toLowerCase());
                }
            }
            else 
                isIp = false;
            txt.append(src.toLowerCase());
            t1 = t;
        }
        if (txt.length() == 0) 
            return null;
        if (ipCount != 4) 
            isIp = false;
        int i;
        int points = 0;
        for (i = 0; i < txt.length(); i++) {
            if (txt.charAt(i) == '.') {
                if (i == 0) 
                    return null;
                if (i >= (txt.length() - 1)) {
                    txt.setLength(txt.length() - 1);
                    t1 = t1.getPrevious();
                    break;
                }
                if (txt.charAt(i - 1) == '.' || txt.charAt(i + 1) == '.') 
                    return null;
                points++;
            }
        }
        if (points == 0) 
            return null;
        String _uri = txt.toString();
        if (_check) {
            boolean ok = isIp;
            if (!isIp) {
                if (com.pullenti.unisharp.Utils.stringsEq(txt.toString(), "localhost")) 
                    ok = true;
            }
            if (!ok && t1.getPrevious() != null && t1.getPrevious().isChar('.')) {
                if (m_StdGroups.tryParse(t1, com.pullenti.ner.core.TerminParseAttr.NO) != null) 
                    ok = true;
            }
            if (!ok) 
                return null;
        }
        return _new2913(t0, t1, txt.toString().toLowerCase());
    }

    public static java.util.ArrayList<UriItemToken> attachMailUsers(com.pullenti.ner.Token t1) {
        if (t1 == null) 
            return null;
        if (t1.isChar('}')) {
            java.util.ArrayList<UriItemToken> res0 = attachMailUsers(t1.getPrevious());
            if (res0 == null) 
                return null;
            t1 = res0.get(0).getBeginToken().getPrevious();
            for (; t1 != null; t1 = t1.getPrevious()) {
                if (t1.isChar('{')) {
                    res0.get(0).setBeginToken(t1);
                    return res0;
                }
                if (t1.isCharOf(";,")) 
                    continue;
                java.util.ArrayList<UriItemToken> res1 = attachMailUsers(t1);
                if (res1 == null) 
                    return null;
                res0.add(0, res1.get(0));
                t1 = res1.get(0).getBeginToken();
            }
            return null;
        }
        StringBuilder txt = new StringBuilder();
        com.pullenti.ner.Token t0 = t1;
        for (com.pullenti.ner.Token t = t1; t != null; t = t.getPrevious()) {
            if (t.isWhitespaceAfter()) 
                break;
            if (t instanceof com.pullenti.ner.NumberToken) {
                com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
                txt.insert(0, nt.getSourceText());
                t0 = t;
                continue;
            }
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
            if (tt == null) 
                break;
            String src = tt.getSourceText();
            char ch = src.charAt(0);
            if (!Character.isLetter(ch)) {
                if (".-_".indexOf(ch) < 0) 
                    break;
            }
            txt.insert(0, src);
            t0 = t;
        }
        if (txt.length() == 0) 
            return null;
        java.util.ArrayList<UriItemToken> res = new java.util.ArrayList<UriItemToken>();
        res.add(_new2913(t0, t1, txt.toString().toLowerCase()));
        return res;
    }

    public static UriItemToken attachUrl(com.pullenti.ner.Token t0) {
        UriItemToken srv = attachDomainName(t0, true, false);
        if (srv == null) 
            return null;
        StringBuilder txt = new StringBuilder(srv.value);
        com.pullenti.ner.Token t1 = srv.getEndToken();
        if (t1.getNext() != null && t1.getNext().isChar(':') && (t1.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
            t1 = t1.getNext().getNext();
            txt.append(":").append(((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class)).getValue());
        }
        else if ((com.pullenti.unisharp.Utils.stringsEq(srv.value, "vk.com") && t1.getNext() != null && t1.getNext().isHiphen()) && t1.getNext().getNext() != null) {
            t1 = t1.getNext().getNext();
            UriItemToken dat = _AttachUriContent(t1, ".-_+%", false);
            if (dat != null) {
                t1 = dat.getEndToken();
                txt.append("/").append(dat.value);
            }
        }
        for (com.pullenti.ner.Token t = t1.getNext(); t != null; t = t.getNext()) {
            if (t.isWhitespaceBefore()) 
                break;
            if (!t.isChar('/')) 
                break;
            if (t.isWhitespaceAfter()) {
                t1 = t;
                break;
            }
            UriItemToken dat = _AttachUriContent(t.getNext(), ".-_+%", false);
            if (dat == null) {
                t1 = t;
                break;
            }
            t = (t1 = dat.getEndToken());
            txt.append("/").append(dat.value);
        }
        if ((t1.getNext() != null && t1.getNext().isChar('?') && !t1.getNext().isWhitespaceAfter()) && !t1.isWhitespaceAfter()) {
            UriItemToken dat = _AttachUriContent(t1.getNext().getNext(), ".-_+%=&", false);
            if (dat != null) {
                t1 = dat.getEndToken();
                txt.append("?").append(dat.value);
            }
        }
        if ((t1.getNext() != null && t1.getNext().isChar('#') && !t1.getNext().isWhitespaceAfter()) && !t1.isWhitespaceAfter()) {
            UriItemToken dat = _AttachUriContent(t1.getNext().getNext(), ".-_+%", false);
            if (dat != null) {
                t1 = dat.getEndToken();
                txt.append("#").append(dat.value);
            }
        }
        int i;
        for (i = 0; i < txt.length(); i++) {
            if (Character.isLetter(txt.charAt(i))) 
                break;
        }
        if (i >= txt.length()) 
            return null;
        return _new2913(t0, t1, txt.toString());
    }

    public static UriItemToken attachISBN(com.pullenti.ner.Token t0) {
        StringBuilder txt = new StringBuilder();
        com.pullenti.ner.Token t1 = t0;
        int digs = 0;
        for (com.pullenti.ner.Token t = t0; t != null; t = t.getNext()) {
            if (t.isTableControlChar()) 
                break;
            if (t.isNewlineBefore() && t != t0) {
                if (t.getPrevious() != null && t.getPrevious().isHiphen()) {
                }
                else 
                    break;
            }
            if (t instanceof com.pullenti.ner.NumberToken) {
                com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
                if (nt.typ != com.pullenti.ner.NumberSpellingType.DIGIT || !nt.getMorph()._getClass().isUndefined()) 
                    break;
                String d = nt.getSourceText();
                txt.append(d);
                digs += d.length();
                t1 = t;
                if (digs > 13) 
                    break;
                continue;
            }
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
            if (tt == null) 
                break;
            String s = tt.term;
            if (com.pullenti.unisharp.Utils.stringsNe(s, "-") && com.pullenti.unisharp.Utils.stringsNe(s, "Х") && com.pullenti.unisharp.Utils.stringsNe(s, "X")) 
                break;
            if (com.pullenti.unisharp.Utils.stringsEq(s, "Х")) 
                s = "X";
            txt.append(s);
            t1 = t;
            if (com.pullenti.unisharp.Utils.stringsNe(s, "-")) 
                break;
        }
        int i;
        int dig = 0;
        for (i = 0; i < txt.length(); i++) {
            if (Character.isDigit(txt.charAt(i))) 
                dig++;
        }
        if (dig < 7) 
            return null;
        return _new2913(t0, t1, txt.toString());
    }

    public static UriItemToken attachBBK(com.pullenti.ner.Token t0) {
        StringBuilder txt = new StringBuilder();
        com.pullenti.ner.Token t1 = t0;
        int digs = 0;
        for (com.pullenti.ner.Token t = t0; t != null; t = t.getNext()) {
            if (t.isNewlineBefore() && t != t0) 
                break;
            if (t.isTableControlChar()) 
                break;
            if (t instanceof com.pullenti.ner.NumberToken) {
                com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
                if (nt.typ != com.pullenti.ner.NumberSpellingType.DIGIT || !nt.getMorph()._getClass().isUndefined()) 
                    break;
                String d = nt.getSourceText();
                txt.append(d);
                digs += d.length();
                t1 = t;
                continue;
            }
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
            if (tt == null) 
                break;
            if (tt.isChar(',')) 
                break;
            if (tt.isChar('(')) {
                if (!(tt.getNext() instanceof com.pullenti.ner.NumberToken)) 
                    break;
            }
            String s = tt.getSourceText();
            if (Character.isLetter(s.charAt(0))) {
                if (tt.isWhitespaceBefore()) 
                    break;
            }
            txt.append(s);
            t1 = t;
        }
        if ((txt.length() < 3) || (digs < 2)) 
            return null;
        if (txt.charAt(txt.length() - 1) == '.') {
            txt.setLength(txt.length() - 1);
            t1 = t1.getPrevious();
        }
        return _new2913(t0, t1, txt.toString());
    }

    public static UriItemToken attachSkype(com.pullenti.ner.Token t0) {
        if (t0.chars.isCyrillicLetter()) 
            return null;
        UriItemToken res = _AttachUriContent(t0, "._", false);
        if (res == null) 
            return null;
        if (res.value.length() < 5) 
            return null;
        return res;
    }

    public static UriItemToken attachIcqContent(com.pullenti.ner.Token t0) {
        if (!(t0 instanceof com.pullenti.ner.NumberToken)) 
            return null;
        UriItemToken res = attachISBN(t0);
        if (res == null) 
            return null;
        if ((res.value.indexOf("-") >= 0)) 
            res.value = res.value.replace("-", "");
        for (char ch : res.value.toCharArray()) {
            if (!Character.isDigit(ch)) 
                return null;
        }
        if ((res.value.length() < 6) || res.value.length() > 10) 
            return null;
        return res;
    }

    private static com.pullenti.ner.core.TerminCollection m_StdGroups;

    public static void initialize() {
        if (m_StdGroups != null) 
            return;
        m_StdGroups = new com.pullenti.ner.core.TerminCollection();
        String[] domainGroups = new String[] {"com;net;org;inf;biz;name;aero;arpa;edu;int;gov;mil;coop;museum;mobi;travel", "ac;ad;ae;af;ag;ai;al;am;an;ao;aq;ar;as;at;au;aw;az", "ba;bb;bd;be;bf;bg;bh;bi;bj;bm;bn;bo;br;bs;bt;bv;bw;by;bz", "ca;cc;cd;cf;cg;ch;ci;ck;cl;cm;cn;co;cr;cu;cv;cx;cy;cz", "de;dj;dk;dm;do;dz", "ec;ee;eg;eh;er;es;et;eu", "fi;fj;fk;fm;fo;fr", "ga;gd;ge;gf;gg;gh;gi;gl;gm;gn;gp;gq;gr;gs;gt;gu;gw;gy", "hk;hm;hn;hr;ht;hu", "id;ie;il;im;in;io;iq;ir;is;it", "je;jm;jo;jp", "ke;kg;kh;ki;km;kn;kp;kr;kw;ky;kz", "la;lb;lc;li;lk;lr;ls;lt;lu;lv;ly", "ma;mc;md;mg;mh;mk;ml;mm;mn;mo;mp;mq;mr;ms;mt;mu;mv;mw;mx;my;mz", "na;nc;ne;nf;ng;ni;nl;no;np;nr;nu;nz", "om", "pa;pe;pf;pg;ph;pk;pl;pm;pn;pr;ps;pt;pw;py", "qa", "re;ro;ru;rw", "sa;sb;sc;sd;se;sg;sh;si;sj;sk;sl;sm;sn;so;sr;st;su;sv;sy;sz", "tc;td;tf;tg;th;tj;tk;tm;tn;to;tp;tr;tt;tv;tw;tz", "ua;ug;uk;um;us;uy;uz", "va;vc;ve;vg;vi;vn;vu", "wf;ws", "ye;yt;yu", "za;zm;zw"};
        char[] separator = new char[] {';'};
        for (String domainGroup : domainGroups) {
            for (String domain : com.pullenti.unisharp.Utils.split(domainGroup.toUpperCase(), new String(separator), true)) {
                m_StdGroups.add(new com.pullenti.ner.core.Termin(domain, com.pullenti.morph.MorphLang.UNKNOWN, true));
            }
        }
    }

    public static UriItemToken _new2913(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3) {
        UriItemToken res = new UriItemToken(_arg1, _arg2);
        res.value = _arg3;
        return res;
    }
    public UriItemToken() {
        super();
    }
}
