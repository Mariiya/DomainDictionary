package com.pullenti.unisharp;

public class Utils {

    public static <T> T cast(Object o, Class<T> clazz) { 
        if(clazz == null || o == null) return null;
		if(clazz.isAssignableFrom(o.getClass())) 
            return (T) o;
        return null;
    }
    public static boolean referenceEquals(Object o1, Object o2) { 
        if(o1 == null || o2 == null) return o1 == null && o2 == null;
        return o1.equals(o2);
    }
    public static Object notnull(Object o1, Object o2) { 
        if(o1 == null) return o2; 
        return o1; 
    }
    public static String notnullstr(String o1, String o2) { 
        if(o1 == null) return o2; 
        return o1; 
    }
    public static boolean isNullOrEmpty(String s) { 
        return s == null || s.length() == 0; 
    }
    public static boolean isNullOrWhiteSpace(String s) { 
        if(s == null || s.length() == 0) return true; 
        for(int i = s.length() - 1; i >=0; i--) 
            if(!isWhitespace(s.charAt(i))) return false;
		return true;
    }
    public static boolean stringsEq(String s1, String s2) { 
        if(s1 == null || s2 == null) return s1 == null && s2 == null; 
        return s1.equals(s2); 
    }
    public static boolean stringsNe(String s1, String s2) { 
        if(s1 == null || s2 == null) return s1 != null || s2 != null; 
        return !s1.equals(s2); 
    }
    public static int stringsCompare(String s1, String s2, boolean ignoreCase) { 
        if(s1 == null || s2 == null) return 0; 
        if(ignoreCase) return s1.compareToIgnoreCase(s2); 
        return s1.compareTo(s2); 
    }
    public static String createString(char ch, int count) { 
        StringBuilder tmp = new StringBuilder();
        for(int i = 0; i < count; i++) tmp.append(ch);
        return tmp.toString();
    }
    static java.util.ArrayList<Integer> m_Whitespaces = new java.util.ArrayList<Integer>(java.util.Arrays.asList(0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x20, 0x85, 0xA0, 0x1680, 0x2000, 0x2001, 0x2002, 0x2003, 0x2004, 0x2005, 0x2006, 0x2007, 0x2008, 0x2009, 0x200A, 0x2028, 0x2029, 0x202F, 0x205F, 0x3000 ));
    public static boolean isWhitespace(char ch) {
    	if(m_Whitespaces.contains((Integer)(int)ch)) return true;
        return false;
    }
    static java.util.ArrayList<Integer> m_Puncts = new java.util.ArrayList<Integer>(java.util.Arrays.asList(0x21, 0x22, 0x23, 0x25, 0x26, 0x27, 0x28, 0x29, 0x2A, 0x2C, 0x2D, 0x2E, 0x2F, 0x3A, 0x3B, 0x3F, 0x40, 0x5B, 0x5C, 0x5D, 0x5F, 0x7B, 0x7D, 0xA1, 0xAB, 0xAD, 0xB7, 0xBB, 0xBF, 0x37E, 0x387, 0x55A, 0x55B, 0x55C, 0x55D, 0x55E, 0x55F, 0x589, 0x58A, 0x5BE, 0x5C0, 0x5C3, 0x5C6, 0x5F3, 0x5F4, 0x609, 0x60A, 0x60C, 0x60D, 0x61B, 0x61E, 0x61F, 0x66A, 0x66B, 0x66C, 0x66D, 0x6D4, 0x700, 0x701, 0x702, 0x703, 0x704, 0x705, 0x706, 0x707, 0x708, 0x709, 0x70A, 0x70B, 0x70C, 0x70D, 0x7F7, 0x7F8, 0x7F9, 0x830, 0x831, 0x832, 0x833, 0x834, 0x835, 0x836, 0x837, 0x838, 0x839, 0x83A, 0x83B, 0x83C, 0x83D, 0x83E, 0x85E, 0x964, 0x965, 0x970, 0xAF0, 0xDF4, 0xE4F, 0xE5A, 0xE5B, 0xF04, 0xF05, 0xF06, 0xF07, 0xF08, 0xF09, 0xF0A, 0xF0B, 0xF0C, 0xF0D, 0xF0E, 0xF0F, 0xF10, 0xF11, 0xF12, 0xF14, 0xF3A, 0xF3B, 0xF3C, 0xF3D, 0xF85, 0xFD0, 0xFD1, 0xFD2, 0xFD3, 0xFD4, 0xFD9, 0xFDA, 0x104A, 0x104B, 0x104C, 0x104D, 0x104E, 0x104F, 0x10FB, 0x1360, 0x1361, 0x1362, 0x1363, 0x1364, 0x1365, 0x1366, 0x1367, 0x1368, 0x1400, 0x166D, 0x166E, 0x169B, 0x169C, 0x16EB, 0x16EC, 0x16ED, 0x1735, 0x1736, 0x17D4, 0x17D5, 0x17D6, 0x17D8, 0x17D9, 0x17DA, 0x1800, 0x1801, 0x1802, 0x1803, 0x1804, 0x1805, 0x1806, 0x1807, 0x1808, 0x1809, 0x180A, 0x1944, 0x1945, 0x1A1E, 0x1A1F, 0x1AA0, 0x1AA1, 0x1AA2, 0x1AA3, 0x1AA4, 0x1AA5, 0x1AA6, 0x1AA8, 0x1AA9, 0x1AAA, 0x1AAB, 0x1AAC, 0x1AAD, 0x1B5A, 0x1B5B, 0x1B5C, 0x1B5D, 0x1B5E, 0x1B5F, 0x1B60, 0x1BFC, 0x1BFD, 0x1BFE, 0x1BFF, 0x1C3B, 0x1C3C, 0x1C3D, 0x1C3E, 0x1C3F, 0x1C7E, 0x1C7F, 0x1CC0, 0x1CC1, 0x1CC2, 0x1CC3, 0x1CC4, 0x1CC5, 0x1CC6, 0x1CC7, 0x1CD3, 0x2010, 0x2011, 0x2012, 0x2013, 0x2014, 0x2015, 0x2016, 0x2017, 0x2018, 0x2019, 0x201A, 0x201B, 0x201C, 0x201D, 0x201E, 0x201F, 0x2020, 0x2021, 0x2022, 0x2023, 0x2024, 0x2025, 0x2026, 0x2027, 0x2030, 0x2031, 0x2032, 0x2033, 0x2034, 0x2035, 0x2036, 0x2037, 0x2038, 0x2039, 0x203A, 0x203B, 0x203C, 0x203D, 0x203E, 0x203F, 0x2040, 0x2041, 0x2042, 0x2043, 0x2045, 0x2046, 0x2047, 0x2048, 0x2049, 0x204A, 0x204B, 0x204C, 0x204D, 0x204E, 0x204F, 0x2050, 0x2051, 0x2053, 0x2054, 0x2055, 0x2056, 0x2057, 0x2058, 0x2059, 0x205A, 0x205B, 0x205C, 0x205D, 0x205E, 0x207D, 0x207E, 0x208D, 0x208E, 0x2308, 0x2309, 0x230A, 0x230B, 0x2329, 0x232A, 0x2768, 0x2769, 0x276A, 0x276B, 0x276C, 0x276D, 0x276E, 0x276F, 0x2770, 0x2771, 0x2772, 0x2773, 0x2774, 0x2775, 0x27C5, 0x27C6, 0x27E6, 0x27E7, 0x27E8, 0x27E9, 0x27EA, 0x27EB, 0x27EC, 0x27ED, 0x27EE, 0x27EF, 0x2983, 0x2984, 0x2985, 0x2986, 0x2987, 0x2988, 0x2989, 0x298A, 0x298B, 0x298C, 0x298D, 0x298E, 0x298F, 0x2990, 0x2991, 0x2992, 0x2993, 0x2994, 0x2995, 0x2996, 0x2997, 0x2998, 0x29D8, 0x29D9, 0x29DA, 0x29DB, 0x29FC, 0x29FD, 0x2CF9, 0x2CFA, 0x2CFB, 0x2CFC, 0x2CFE, 0x2CFF, 0x2D70, 0x2E00, 0x2E01, 0x2E02, 0x2E03, 0x2E04, 0x2E05, 0x2E06, 0x2E07, 0x2E08, 0x2E09, 0x2E0A, 0x2E0B, 0x2E0C, 0x2E0D, 0x2E0E, 0x2E0F, 0x2E10, 0x2E11, 0x2E12, 0x2E13, 0x2E14, 0x2E15, 0x2E16, 0x2E17, 0x2E18, 0x2E19, 0x2E1A, 0x2E1B, 0x2E1C, 0x2E1D, 0x2E1E, 0x2E1F, 0x2E20, 0x2E21, 0x2E22, 0x2E23, 0x2E24, 0x2E25, 0x2E26, 0x2E27, 0x2E28, 0x2E29, 0x2E2A, 0x2E2B, 0x2E2C, 0x2E2D, 0x2E2E, 0x2E30, 0x2E31, 0x2E32, 0x2E33, 0x2E34, 0x2E35, 0x2E36, 0x2E37, 0x2E38, 0x2E39, 0x2E3A, 0x2E3B, 0x2E3C, 0x2E3D, 0x2E3E, 0x2E3F, 0x2E40, 0x2E41, 0x2E42, 0x3001, 0x3002, 0x3003, 0x3008, 0x3009, 0x300A, 0x300B, 0x300C, 0x300D, 0x300E, 0x300F, 0x3010, 0x3011, 0x3014, 0x3015, 0x3016, 0x3017, 0x3018, 0x3019, 0x301A, 0x301B, 0x301C, 0x301D, 0x301E, 0x301F, 0x3030, 0x303D, 0x30A0, 0x30FB, 0xA4FE, 0xA4FF, 0xA60D, 0xA60E, 0xA60F, 0xA673, 0xA67E, 0xA6F2, 0xA6F3, 0xA6F4, 0xA6F5, 0xA6F6, 0xA6F7, 0xA874, 0xA875, 0xA876, 0xA877, 0xA8CE, 0xA8CF, 0xA8F8, 0xA8F9, 0xA8FA, 0xA8FC, 0xA92E, 0xA92F, 0xA95F, 0xA9C1, 0xA9C2, 0xA9C3, 0xA9C4, 0xA9C5, 0xA9C6, 0xA9C7, 0xA9C8, 0xA9C9, 0xA9CA, 0xA9CB, 0xA9CC, 0xA9CD, 0xA9DE, 0xA9DF, 0xAA5C, 0xAA5D, 0xAA5E, 0xAA5F, 0xAADE, 0xAADF, 0xAAF0, 0xAAF1, 0xABEB, 0xFD3E, 0xFD3F, 0xFE10, 0xFE11, 0xFE12, 0xFE13, 0xFE14, 0xFE15, 0xFE16, 0xFE17, 0xFE18, 0xFE19, 0xFE30, 0xFE31, 0xFE32, 0xFE33, 0xFE34, 0xFE35, 0xFE36, 0xFE37, 0xFE38, 0xFE39, 0xFE3A, 0xFE3B, 0xFE3C, 0xFE3D, 0xFE3E, 0xFE3F, 0xFE40, 0xFE41, 0xFE42, 0xFE43, 0xFE44, 0xFE45, 0xFE46, 0xFE47, 0xFE48, 0xFE49, 0xFE4A, 0xFE4B, 0xFE4C, 0xFE4D, 0xFE4E, 0xFE4F, 0xFE50, 0xFE51, 0xFE52, 0xFE54, 0xFE55, 0xFE56, 0xFE57, 0xFE58, 0xFE59, 0xFE5A, 0xFE5B, 0xFE5C, 0xFE5D, 0xFE5E, 0xFE5F, 0xFE60, 0xFE61, 0xFE63, 0xFE68, 0xFE6A, 0xFE6B, 0xFF01, 0xFF02, 0xFF03, 0xFF05, 0xFF06, 0xFF07, 0xFF08, 0xFF09, 0xFF0A, 0xFF0C, 0xFF0D, 0xFF0E, 0xFF0F, 0xFF1A, 0xFF1B, 0xFF1F, 0xFF20, 0xFF3B, 0xFF3C, 0xFF3D, 0xFF3F, 0xFF5B, 0xFF5D, 0xFF5F, 0xFF60, 0xFF61, 0xFF62, 0xFF63, 0xFF64, 0xFF65 ));
    public static boolean isPunctuation(char ch) {
    	if(m_Puncts.contains((Integer)(int)ch)) return true;
        return false;
    }
    public static String trimStart(String s) { 
        for(int i = 0; i < s.length(); i++) 
            if(!isWhitespace(s.charAt(i))) {
                if(i == 0) return s;
                return s.substring(i);
            }
        return "";
    }
    public static String trimEnd(String s) { 
        for(int i = s.length() - 1; i >=0; i--) 
            if(!isWhitespace(s.charAt(i))) {
                if(i == s.length() - 1) return s;
                return s.substring(0, i + 1);
            }
        return "";
    }

    public static String removeString(String str, int pos, int len) {
    	String tail = null;
		if(len <= 0) len = str.length() - pos;
    	if(pos == 0 && len == str.length()) return "";
    	if(pos + len < str.length()) {
    		tail = str.substring(pos + len);
    		if(pos == 0) return tail;
    	}
    	String res = str.substring(0, pos);
    	if(tail != null) res += tail;
    	return res;
    }
    public static int indexOfAny(String str, Character[] chars, int pos, int cou) {
    	for(int i = pos; i < str.length(); i++) {
	        if(cou > 0 && i - pos > cou) break;
    		char ch = str.charAt(i);
    		for(int j = 0; j < chars.length; j++)
    			if(chars[j] == ch) return i;
    	}
    	return -1;
    }
    public static int indexOfAny(String str, char[] chars, int pos, int cou) {
    	for(int i = pos; i < str.length(); i++) {
	        if(cou > 0 && i - pos > cou) break;
    		char ch = str.charAt(i);
    		for(int j = 0; j < chars.length; j++)
    			if(chars[j] == ch) return i;
    	}
    	return -1;
    }

    public static String[] split(String str, String dels, boolean ignoreEmpty) {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        int i0 = 0, i = 0;
		if(str != null)
        for(i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if(dels.indexOf(ch) >= 0) {
                if(i > i0) res.add(str.substring(i0, i));
                else if(!ignoreEmpty && i > 0) res.add("");
                i0 = i + 1;
            }
        }
        if(i > i0)
            res.add(str.substring(i0, i));
        String[] arr = new String[res.size()];
        arr = res.toArray(arr);
        return arr;
    }

    public static boolean startsWithString(String str, String sub, boolean caseIgnore) {
    	if(!caseIgnore) return str.startsWith(sub);
    	for(int i = 0; i < str.length(); i++) {
    		if(i >= sub.length()) return true;
    		char ch = Character.toUpperCase(str.charAt(i));
    		char ch1 = Character.toUpperCase(sub.charAt(i));
    		if(ch != ch1) return false;
    	}
    	return true;
    }
    public static boolean endsWithString(String str, String sub, boolean caseIgnore) {
    	if(!caseIgnore) return str.endsWith(sub);
    	int j = sub.length() - 1;
    	for(int i = str.length() - 1; i >=0; i--, j--) {
    		if(j < 0) return true;
    		char ch = Character.toUpperCase(str.charAt(i));
    		char ch1 = Character.toUpperCase(sub.charAt(j));
    		if(ch != ch1) return false;
    	}
    	return true;
    }


    public static StringBuilder append(StringBuilder sb, char ch, int count) {
        for(int i = 0; i < count; i++) sb.append(ch);
        return sb;
    }

    public static StringBuilder replace(StringBuilder sb, char oldChar, char newChar) {
        for (int i = sb.length() - 1; i >= 0; i--) {
            if(sb.charAt(i) == oldChar)
                sb.setCharAt(i, newChar);
        }
        return sb;
    }
    
    public static StringBuilder replace(StringBuilder sb, String oldStr, String newStr) {
        for (int i = sb.length() - oldStr.length(); i >= 0; i--) {
            int j;
            for (j = 0; j < oldStr.length(); j++) {
                if (sb.charAt(i + j) != oldStr.charAt(j)) {
                    break;
                }
            }
            if (j == oldStr.length()) {
                sb.replace(i, i + j, newStr);
            }
        }
        return sb;
    }

    public static Stopwatch startNewStopwatch() {
        Stopwatch sw = new Stopwatch();
        sw.start();
        return sw;
    }

    public static double mathTruncate(double val) {
        if (val < 0) {
            return Math.ceil(val);
        } else {
            return Math.floor(val);
        }
    }

    public static double mathRound(double val, int prec) {
        for (int i = 0; i < prec; i++) {
            val *= 10;
        }
        val = Math.round(val);
        for (int i = 0; i < prec; i++) {
            val /= 10;
        }
        return val;
    }
    
    public static String setDecimalSeparator(java.text.DecimalFormat nf, String sep) {
    	java.text.DecimalFormatSymbols sym = nf.getDecimalFormatSymbols();
    	if(sep.length() > 0)
    		sym.setDecimalSeparator(sep.charAt(0));
    	nf.setDecimalFormatSymbols(sym);
    	return String.valueOf(nf.getDecimalFormatSymbols().getDecimalSeparator());
    }

    public static boolean parseByte(String str, Outargwrapper<Byte> res) {
        try {
            res.value = Byte.parseByte(str);
            return true;
        } catch (Exception ex) {
            res.value = (byte) 0;
            return false;
        }
    }

    public static byte parseByte(String str) throws NumberFormatException {
        return Byte.parseByte(str);
    }

    public static boolean parseShort(String str, int ty, java.text.DecimalFormat frm,  Outargwrapper<Short> res) {
        try {
			if(frm != null)
				res.value = (short)((long)frm.parse(str));
			else if((ty & 512) != 0)
				res.value = Short.decode("0x" + str);
			else
				res.value = Short.parseShort(str);
            return true;
        } catch (Exception ex) {
            res.value = (short) 0;
            return false;
        }
    }

    public static short parseShort(String str, int ty, java.text.DecimalFormat frm) throws NumberFormatException {
		try {
			if(frm != null)
				return (short)((long)frm.parse(str));
			else if((ty & 512) != 0)
				return Short.decode("0x" + str);
			else
				return Short.parseShort(str);
		}
		catch (Exception ex) {
			throw new NumberFormatException(ex.getMessage());
        }
    }

    public static boolean parseInteger(String str, int ty, java.text.DecimalFormat frm, Outargwrapper<Integer> res) {
        try {
			if(frm != null)
				res.value = (int)((long)frm.parse(str));
			else if((ty & 512) != 0)
				res.value = Integer.decode("0x" + str);
			else
				res.value = Integer.parseInt(str);
            return true;
        } catch (Exception ex) {
            res.value = (int) 0;
            return false;
        }
    }

    public static int parseInteger(String str, int ty, java.text.DecimalFormat frm) throws NumberFormatException {
		try {
			if(frm != null)
				return (int)((long)frm.parse(str));
			else if((ty & 512) != 0)
				return Integer.decode("0x" + str);
			else
				return Integer.parseInt(str);
		}
		catch (Exception ex) {
			throw new NumberFormatException(ex.getMessage());
        }
    }

    public static boolean parseLong(String str, int ty, java.text.DecimalFormat frm, Outargwrapper<Long> res) {
        try {
			if(frm != null)
				res.value = (long)frm.parse(str);
			else if((ty & 512) != 0)
				res.value = Long.decode("0x" + str);
			else
				res.value = Long.parseLong(str);
            return true;
        } catch (Exception ex) {
            res.value = (long) 0;
            return false;
        }
    }

    public static long parseLong(String str, int ty, java.text.DecimalFormat frm) throws NumberFormatException {
		try {
			if(frm != null)
				return (long)frm.parse(str);
			else if((ty & 512) != 0)
				return Long.decode("0x" + str);
			else
				return Long.parseLong(str);
		}
		catch (Exception ex) {
			throw new NumberFormatException(ex.getMessage());
        }
    }

    public static boolean parseFloat(String str, java.text.DecimalFormat frm, Outargwrapper<Float> res) {
        try {
			if(frm == null)
				res.value = Float.parseFloat(str);
			else {
				Object o = frm.parse(str);
				if(o instanceof Double)
					res.value = (float)((double)frm.parse(str));
				else if(o instanceof Long)
					res.value = (float)((long)frm.parse(str));
				else 
					throw new Exception("Bad number");
			}
            return true;
        } catch (Exception ex) {
            res.value = (float) 0;
            return false;
        }
    }

    public static float parseFloat(String str, java.text.DecimalFormat frm) throws NumberFormatException {
		if(frm == null)
			return Float.parseFloat(str);
		try {
			Object o = frm.parse(str);
			if(o instanceof Double)
				return (float)((double)frm.parse(str));
			else if(o instanceof Long)
				return (float)((long)frm.parse(str));
			else 
				throw new Exception("Bad number");
		}
		catch (Exception ex) {
			throw new NumberFormatException(ex.getMessage());
        }
    }

    public static boolean parseDouble(String str, java.text.DecimalFormat frm, Outargwrapper<Double> res) {
        try {
			if(frm == null)
				res.value = Double.parseDouble(str);
			else {
				Object o = frm.parse(str);
				if(o instanceof Double)
					res.value = (double)frm.parse(str);
				else if(o instanceof Long)
					res.value = (double)((long)frm.parse(str));
				else 
					throw new Exception("Bad number");
			}
            return true;
        } catch (Exception ex) {
            res.value = (double) 0;
            return false;
        }
    }

    public static double parseDouble(String str, java.text.DecimalFormat frm) throws NumberFormatException {
		if(frm == null)
			return Double.parseDouble(str);
		try {
			Object o = frm.parse(str);
			if(o instanceof Double)
				return (double)frm.parse(str);
			else if(o instanceof Long)
				return (double)((long)frm.parse(str));
			else 
				throw new Exception("Bad number");
		}
		catch (Exception ex) {
			throw new NumberFormatException(ex.getMessage());
        }
    }

    public static boolean parseUri(String str, Outargwrapper<java.net.URI> res) {
        try {
            res.value = java.net.URI.create(str);
            return true;
        }
        catch(Exception ex) {
            res.value = null;
            return false;
        }
    }
    public static String encodeUri(String str) {
        StringBuilder sb = new StringBuilder(str);
        for(int i = 0; i < sb.length(); i++) {
            char ch = sb.charAt(i);
            if(ch != ' ' && ((int)ch) < 0x100) continue;
            try {
                String ee = java.net.URLEncoder.encode(String.valueOf(ch), "ASCII");
                sb.replace(i, i + 1, ee);
            }
            catch(Exception ex) {
                continue;
            }
        }
        return sb.toString();
    }

    public static boolean parseDateTime(String str, Outargwrapper<java.time.LocalDateTime> res) {
        res.value = java.time.LocalDateTime.MIN;
        if (str == null) {
            return false;
        }
        try {
            res.value = java.time.LocalDateTime.parse(str);
            return true;
        } catch (Exception ex) {
        }
        java.util.ArrayList<Integer> ints = new java.util.ArrayList<Integer>();
        boolean isT = false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                int v = Character.getNumericValue(str.charAt(i));
                for (++i; i < str.length(); i++) {
                    if (!Character.isDigit(str.charAt(i))) {
                        break;
                    } else {
                        v = (v * 10) + (Character.getNumericValue(str.charAt(i)));
                    }
                }
                ints.add(v);
                if (ints.size() == 3 && (i < str.length()) && str.charAt(i) == 'T') {
                    isT = true;
                }
            } else if (ints.size() == 3 && str.charAt(i) == 'T') {
                isT = true;
            }
        }
        try {
            if (ints.size() == 3) {
                res.value = java.time.LocalDateTime.of(ints.get(0), ints.get(1), ints.get(2), 0, 0);
                return true;
            }
            if (ints.size() == 6 || ((ints.size() >= 6 && isT))) {
                res.value = java.time.LocalDateTime.of(ints.get(0), ints.get(1), ints.get(2), ints.get(3), ints.get(4), ints.get(5));
                return true;
            }
        } catch (Exception ex) {
        }
        return false;
	}

    public static byte[] toBytesArray(Byte[] arr) {
        byte[] res = new byte[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static byte[] toBytesArray(byte[] arr) {
        return arr;
    }
    public static Byte[] toBytesArrayObj(byte[] arr) {
        Byte[] res = new Byte[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static Byte[] toBytesArrayObj(Byte[] arr) {
        return arr;
    }
    public static char[] toCharactersArray(Character[] arr) {
        char[] res = new char[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static char[] toCharactersArray(char[] arr) {
        return arr;
    }
    public static Character[] toCharactersArrayObj(char[] arr) {
        Character[] res = new Character[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static Character[] toCharactersArrayObj(Character[] arr) {
        return arr;
    }
    public static short[] toShortsArray(Short[] arr) {
        short[] res = new short[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static short[] toShortsArray(short[] arr) {
        return arr;
    }
    public static Short[] toShortsArrayObj(short[] arr) {
        Short[] res = new Short[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static Short[] toShortsArrayObj(Short[] arr) {
        return arr;
    }
    public static int[] toIntegersArray(Integer[] arr) {
        int[] res = new int[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static int[] toIntegersArray(int[] arr) {
        return arr;
    }
    public static Integer[] toIntegersArrayObj(int[] arr) {
        Integer[] res = new Integer[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static Integer[] toIntegersArrayObj(Integer[] arr) {
        return arr;
    }
    public static long[] toLongsArray(Long[] arr) {
        long[] res = new long[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static long[] toLongsArray(long[] arr) {
        return arr;
    }
    public static Long[] toLongsArrayObj(long[] arr) {
        Long[] res = new Long[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static Long[] toLongsArrayObj(Long[] arr) {
        return arr;
    }
    public static float[] toFloatsArray(Float[] arr) {
        float[] res = new float[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static float[] toFloatsArray(float[] arr) {
        return arr;
    }
    public static Float[] toFloatsArrayObj(float[] arr) {
        Float[] res = new Float[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static Float[] toFloatsArrayObj(Float[] arr) {
        return arr;
    }
    public static double[] toDoublesArray(Double[] arr) {
        double[] res = new double[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static double[] toDoublesArray(double[] arr) {
        return arr;
    }
    public static Double[] toDoublesArrayObj(double[] arr) {
        Double[] res = new Double[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    public static Double[] toDoublesArrayObj(Double[] arr) {
        return arr;
    }

    public static <T> void addToArrayList(java.util.ArrayList<T> res, Iterable<T> src) {
        for(T i : src) res.add(i);
    }
    public static <T> void insertToArrayList(java.util.ArrayList<T> res, Iterable<T> src, int pos) {
        for(T i : src) {
        	res.add(pos, i); pos++;
        }
    }

    public static <T> java.util.ArrayList<T> toArrayList(Iterable<T> src) {
        if(src == null) return null;
        java.util.ArrayList<T> res = new java.util.ArrayList<T>();
        for(T i : src) res.add(i);
        return res;
    }
    public static <T> Iterable<T> concatIterable(Iterable<T> src, Iterable<T> src2) {
        if(src == null) return null;
        java.util.ArrayList<T> res = new java.util.ArrayList<T>();
        for(T i : src) res.add(i);
        for(T i : src2) res.add(i);
        return res;
    }
    public static <T> Iterable<T> reverseIterable(Iterable<T> src) {
        if(src == null) return null;
        java.util.ArrayList<T> res = new java.util.ArrayList<T>();
        for(T i : src) res.add(0, i);
        return res;
    }
    public static <T> Iterable<T> whereIterable(Iterable<T> src, Predicate<T> cond) {
        java.util.ArrayList<T> res = new java.util.ArrayList<T>();
    	for(T i : src) {
  			if(cond.call(i))
  				res.add(i);
    	}
    	return res;
    }
    public static <T, TResult> Iterable<TResult> selectIterable(Iterable<T> src, Func<T, TResult> func) {
        java.util.ArrayList<TResult> res = new java.util.ArrayList<TResult>();
    	for(T i : src) res.add(func.call(i));
    	return res;
    }
    public static <T> int countIterable(Iterable<T> src, Predicate<T> cond) {
    	int cou = 0;
    	for(T i : src) {
    		if(cond != null)
    			if(!cond.call(i)) continue; 
    		cou++;
    	}
    	return cou;
    }
    public static <T> boolean anyIterable(Iterable<T> src, Predicate<T> cond) {
    	for(T i : src) {
    		if(cond != null)
    			if(!cond.call(i)) continue; 
    		return true;
    	}
    	return false;
    }
    public static <T> boolean allIterable(Iterable<T> src, Predicate<T> cond) {
    	for(T i : src) {
    		if(!cond.call(i)) return false; 
    	}
    	return true;
    }
    public static <T> T firstIterable(Iterable<T> src, Predicate<T> cond) throws Exception {
    	for(T i : src) {
    		if(cond != null)
    			if(!cond.call(i)) continue; 
    		return i;
    	}
    	throw new Exception("No items in iterable");
    }
    public static <T> T firstOrDefaultIterable(Iterable<T> src, Object nullVal, Predicate<T> cond) {
    	for(T i : src) {
    		if(cond != null)
    			if(!cond.call(i)) continue; 
    		return i;
    	}
    	return (T)nullVal;
    }
    public static <T> T elementAtIterable(Iterable<T> src, int index) throws Exception {
    	for(T i : src) {
    		if(index == 0) return i;
    		index--;
    	}
    	throw new Exception("No items in iterable");
    }
    public static <T> T elementAtOrDefaultIterable(Iterable<T> src, int index, Object nullVal)  {
    	for(T i : src) {
    		if(index == 0) return i;
    		index--;
    	}
    	return (T)nullVal;
    }
    public static <T> T lastIterable(Iterable<T> src, Predicate<T> cond) throws Exception {
    	T ret = null;
    	boolean has = false;
    	for(T i : src) {
    		if(cond != null)
    			if(!cond.call(i)) continue; 
    		ret = i; has = true; 
    	}
    	if(has) return ret;
    	throw new Exception("No items in iterable");
    }
    public static <T> T lastOrDefaultIterable(Iterable<T> src, Object nullVal, Predicate<T> cond) {
    	T ret = null;
    	boolean has = false;
    	for(T i : src) { 
    		if(cond != null)
    			if(!cond.call(i)) continue; 
    		ret = i; has = true; 
    	}
    	if(has) return ret;
    	return (T)nullVal;
    }

    public static <T> T putArrayValue(java.util.ArrayList<T> arr, int index, T value) {
        if (index < arr.size()) 
            arr.set(index, value);
        else
        	arr.add(index, value);
        return value;
    }

    public static <T> void forEachList(java.util.ArrayList<T> arr, Action<T> act) {
        for(T i : arr) 
        	act.call(i);
    }
    public static <T> boolean trueForAllList(java.util.ArrayList<T> arr, Predicate<T> cond) {
        for(T i : arr) 
        	if(!cond.call(i))
        		return false;
        return true;
    }
    public static <T> java.util.ArrayList<T> findAllList(java.util.ArrayList<T> arr, Predicate<T> cond ) {
        java.util.ArrayList<T> res = new java.util.ArrayList<T>();
        for(T i : arr) 
        	if(cond.call(i))
        		res.add(i);
        return res;
    }
    public static <T> T findList(java.util.ArrayList<T> arr, Predicate<T> cond ) {
        for(T i : arr) 
        	if(cond.call(i))
        		return i;
        return null;
    }
    public static <T> T findLastList(java.util.ArrayList<T> arr, Predicate<T> cond ) {
    	T res = null;
        for(T i : arr) 
        	if(cond.call(i))
        		res = i;
        return res;
    }
    public static <T> int findIndexList(java.util.ArrayList<T> arr, Predicate<T> cond, int start, int cou) {
    	if(cou == 0) cou = arr.size() - start;
    	for(int i = start; i < start + cou; i++) 
    		if(cond.call(arr.get(i)))
    			return i;
        return -1;
    }
    public static <T> int findLastIndexList(java.util.ArrayList<T> arr, Predicate<T> cond, int start, int cou) {
    	if(start == 0) start = arr.size() - 1;
    	if(cou == 0) cou = arr.size() - start;
        for(int i = start; i >= start - cou && i >= 0; i--) 
         	if(cond.call(arr.get(i)))
           		return i;
        return -1;
    }
    public static <T> boolean existsList(java.util.ArrayList<T> arr, Predicate<T> cond ) {
        for(T i : arr) 
        	if(cond.call(i))
        		return true;
        return false;
    }



    public static <T> int indexOf(T[] arr, T value, int fromIndex) {
        for(int i = fromIndex; i < arr.length; i++)
            if(arr[i] == value) return i;
        return -1;
    }
    public static int indexOfBytes(byte[] arr, byte value, int fromIndex) {
        for(int i = fromIndex; i < arr.length; i++)
            if(arr[i] == value) return i;
        return -1;
    }
    public static int indexOfChars(char[] arr, char value, int fromIndex) {
        for(int i = fromIndex; i < arr.length; i++)
            if(arr[i] == value) return i;
        return -1;
    }
    public static int indexOfInts(int[] arr, int value, int fromIndex) {
        for(int i = fromIndex; i < arr.length; i++)
            if(arr[i] == value) return i;
        return -1;
    }
    public static <T> int indexOf(java.util.ArrayList<T> arr, T value, int fromIndex) {
        for(int i = fromIndex; i < arr.size(); i++)
            if(arr.get(i) == value) return i;
        return -1;
    }
    public static <T> int lastIndexOf(java.util.ArrayList<T> arr, T value, int fromIndex) {
        for(int i = fromIndex; i >= 0; i--)
            if(arr.get(i) == value) return i;
        return -1;
    }
    public static int getCapacity(java.util.ArrayList<?> l) {
        try {
        java.lang.reflect.Field dataField = java.util.ArrayList.class.getDeclaredField("elementData");
        dataField.setAccessible(true);
        return ((Object[]) dataField.get(l)).length;
        }
        catch(Exception ex) {
            return l.size();
        }
    }

    public static int daysInMonth(int year, int month) {
        switch (month) {
            case 2:
                return (((year & 3) == 0) && ((year % 100) != 0 || (year % 400) == 0) ? 29 : 28);
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 31;
        }
    }

    public static <K, V> boolean tryGetValue(java.util.Map<K, V> dic, K key, Outargwrapper<V> res) {
        if (dic == null || key == null || !dic.containsKey(key)) {
            return false;
        }
        res.value = dic.get(key);
        return true;
    }
    public static String getFileExt(String s) {
	    if(s == null) return null;
        for (int i = s.length() - 1; i > 0; i--) {
            if (s.charAt(i) == '.') {
                return s.substring(i);
            } else if (s.charAt(i) == '\\' || s.charAt(i) == '/' || s.charAt(i) == ':') {
                break;
            }
        }
        return ".";
    }
    public static String getFileWithoutExt(String s) {
	    if(s == null) return null;
        for (int i = s.length() - 1; i > 0; i--) {
            if (s.charAt(i) == '.') {
                return s.substring(0, i);
            } else if (s.charAt(i) == '\\' || s.charAt(i) == '/' || s.charAt(i) == ':') {
                break;
            }
        }
        return s;
    }
    public static String changeFileExt(String s, String ext) {
	    if(s == null) return null;
        String res = getFileWithoutExt(s);
        if(ext != null && !ext.isEmpty()) {
            if(ext.charAt(0) == '.') res += ext;
            else res += "." + ext;
        }
        return res;
    }
	public static String getRootPath(String fileName) {
    	if(fileName == null) return null;
    	if(fileName.length() < 4) return "";
    	if(fileName.charAt(1) == ':') {
    		for(int i = 2; i < fileName.length(); i++) {
    			char ch = fileName.charAt(i);
				if(ch != '\\' && ch != '/')
					return fileName.substring(0, i);
    		}
    	}
    	return "";
    }
	public static boolean isPathRooted(String path) {
		return !isNullOrEmpty(getRootPath(path));
	}

    public static java.time.LocalDateTime getDateTimeFromFileTime(long ftime) {
        return java.time.LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(ftime),
                java.util.TimeZone.getDefault().toZoneId());
    }

    public static java.time.LocalDateTime getFileCreationTime(java.io.File f) {
    	try {
    	java.nio.file.attribute.BasicFileAttributes attr = java.nio.file.Files.readAttributes(f.toPath(), java.nio.file.attribute.BasicFileAttributes.class);
    	java.nio.file.attribute.FileTime ft = attr.creationTime();
        long lo = ft.toMillis();
        return java.time.LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(lo),
                java.util.TimeZone.getDefault().toZoneId());
    	}
    	catch(Exception ee) {
    		return java.time.LocalDateTime.MIN;
    	}
    }

    public static java.time.LocalDateTime getFileModifiedTime(java.io.File f) {
        long lo = f.lastModified();
        return java.time.LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(lo),
                java.util.TimeZone.getDefault().toZoneId());
    }

   static boolean checkPattern(String fname, int j, String pattern) {
        if(pattern.equals("*") || pattern.equals("*.*")) return true;
        int i;
        for(i = 0; i < pattern.length(); i++) {
            char ch = pattern.charAt(i);
            if(ch == '?') { j++; continue; }
            if(ch != '*') {
                if(j >= fname.length()) return false;
                char ch2 = fname.charAt(j);
                if(Character.toUpperCase(ch) != Character.toUpperCase(ch2)) return false;
                j++; continue; 
            }
            if(j >= fname.length() || i == pattern.length() - 1) { 
            	i++; 
            	j = fname.length();
            	break; 
            }
            String spat = pattern.substring(i + 1);
            for(int jj = j; jj < fname.length() - 1; jj++)
                if(checkPattern(fname, jj, spat)) 
                    return true;
            return false;
        }
        if(i < pattern.length()) return false;
        if(j < fname.length()) return false;
        return true;
    }

   public static boolean deleteDirectory(java.io.File path) throws java.io.FileNotFoundException{
       if (!path.exists()) throw new java.io.FileNotFoundException(path.getAbsolutePath());
       boolean ret = true;
       if (path.isDirectory()) {
           for (java.io.File f : path.listFiles()){
               ret = ret && Utils.deleteDirectory(f);
           }
       }
       return ret && path.delete();
   }   

   public static String[] getDirectoryItems(String path, String pattern, boolean dirs) {
        java.io.File file = new java.io.File(path);
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        
        java.io.File[] files = file.listFiles();
        if(files != null) 
            for(int i = 0; i < files.length; i++)
            {
                if(files[i].isDirectory()) { if(!dirs) continue; }
                else if(files[i].isFile()) { if(dirs) continue; }
                if(pattern != null)
                    if(!checkPattern(files[i].getName(), 0, pattern))
						continue;
                res.add(files[i].getAbsolutePath());
            }
        
        String[] arr = new String[res.size()];
        arr = res.toArray(arr);
        return arr;
    }

    public static boolean setCurrentDirectory(String directory_name) {
        boolean result = false;  
        java.io.File directory = new java.io.File(directory_name).getAbsoluteFile();
        if (directory.exists() || directory.mkdirs()) {
            result = (System.setProperty("user.dir", directory.getAbsolutePath()) != null);
        }
        return result;
    }
    public static String getFullPath(String path) {
        java.io.File f = new java.io.File(path);
        try {
            return f.getCanonicalPath();
        }
        catch(Exception e) {
        }
        return f.getAbsolutePath();
    }
    public static String getTempFileName() {
    	java.io.File baseDir = new java.io.File(System.getProperty("java.io.tmpdir"));
    	for(int val = (int)System.currentTimeMillis(); ; val++) {
    		java.io.File res = new java.io.File(baseDir, "tmp" + val + ".tmp");
    		if(!res.exists()) return res.getAbsolutePath();
    	}
    }
    public static void copyFile(String fromFile, String toFile) throws java.io.IOException {
        java.nio.file.Files.copy(java.nio.file.Paths.get(fromFile), java.nio.file.Paths.get(toFile), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    }
    public static void moveFile(String fromFile, String toFile) throws java.io.IOException {
        java.nio.file.Files.move(java.nio.file.Paths.get(fromFile), java.nio.file.Paths.get(toFile), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    }

    public static byte[] encodeCharset(java.nio.charset.Charset cs, String str) {             
	    java.nio.ByteBuffer bb = cs.encode(str + "!");             
		byte[] res = bb.array();             
		for(int i = res.length - 1; i >= 0; i--)                  
		    if(res[i] == (byte)33) {                      
		        res = java.util.Arrays.copyOf(res, i); break;                  
		    }            
	    return res;     
	}     
	public static String decodeCharset(java.nio.charset.Charset cs, byte[] dat, int offset, int length) {
	    if(length < 0) length = dat.length; 
		if(length > 2 && dat[offset] == (byte)0xEF && dat[offset + 1] == (byte)0xBB && dat[offset + 2] == (byte)0xBF) {
		    offset += 3; length -= 3;
		}
        java.nio.CharBuffer cb = cs.decode(java.nio.ByteBuffer.wrap(dat, offset, length)); 
        String res = cb.toString(); 
        return res; 
    }
	public static char[] decodeCharsetArr(java.nio.charset.Charset cs, byte[] dat, int offset, int length) {
	    if(length < 0) length = dat.length; 
		if(length > 2 && dat[0] == (byte)0xEF && dat[1] == (byte)0xBB && dat[2] == (byte)0xBF) {
		    offset += 3; length -= 3;
		}
        java.nio.CharBuffer cb = cs.decode(java.nio.ByteBuffer.wrap(dat, offset, length)); 
        return cb.array(); 
    }
    public static byte[] preambleCharset(java.nio.charset.Charset cs) {             
        if(cs.name().equalsIgnoreCase("UTF-8"))
            return new byte[] { (byte)0xEF, (byte)0xBB, (byte)0xBF };
        if(cs.name().equalsIgnoreCase("UTF-16LE"))
            return new byte[] { (byte)0xFF, (byte)0xFE };
        if(cs.name().equalsIgnoreCase("UTF-16BE"))
            return new byte[] { (byte)0xFE, (byte)0xFF };
        return new byte[0];
    }
    public static java.nio.charset.Charset getCharsetByName(String name) throws IllegalArgumentException {
        if(name.equalsIgnoreCase("UTF-16")) name = "UTF-16LE";
        else if(name.equalsIgnoreCase("UNICODEFFFE")) name = "UTF-16BE";
        else if(name.equalsIgnoreCase("US-ASCII")) name = "ASCII";
        else if(name.toUpperCase().startsWith("WINDOWS-")) {
            name = "cp" + name.substring(8);
        }
        try {
            return java.nio.charset.Charset.forName(name);
        }
        catch(Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static byte[] readAllBytes(String fname) throws java.io.FileNotFoundException, java.io.IOException { 
        try(java.io.RandomAccessFile f = new java.io.RandomAccessFile(fname, "r")) { 
            byte[] res = new byte[(int)f.length()];
            f.read(res, 0, res.length); 
            return res; 
        } 
    }

    public static String getXmlLocalName(org.w3c.dom.Node nod) {
    	if(nod == null) return null;
    	String res = nod.getNodeName(); if(res == null) return null;
    	for(int i = res.length() - 1; i >=0; i--)
    		if(res.charAt(i) == ':') 
    			return res.substring(i + 1);
    	return res;
    }

    public static org.w3c.dom.Attr getXmlAttrByName(org.w3c.dom.NamedNodeMap map, String name) { 
        //org.w3c.dom.NamedNodeMap map = node.getAttributes();
		if(map == null) return null;
		org.w3c.dom.Node res = map.getNamedItem(name);
		if(res instanceof org.w3c.dom.Attr) return (org.w3c.dom.Attr)res;
		return null;
    }

    public static void writeAllBytes(String fname, byte[] data) throws java.io.FileNotFoundException, java.io.IOException { 
        try(java.io.RandomAccessFile f = new java.io.RandomAccessFile(fname, "rw")) { 
            f.setLength(0); 
            f.write(data, 0, data.length); 
        } 
    }
    public static String readAllText(String fname, java.nio.charset.Charset cs) throws java.io.FileNotFoundException, java.io.IOException { 
        byte[] dat = readAllBytes(fname);
		if(dat == null || dat.length == 0) return null;
		if(cs == null) cs = java.nio.charset.StandardCharsets.UTF_8;
        return decodeCharset(cs, dat, 0, dat.length); 
    }
    public static void writeAllText(String fname, String txt, java.nio.charset.Charset cs) throws java.io.FileNotFoundException, java.io.IOException { 
		if(cs == null) cs = java.nio.charset.StandardCharsets.UTF_8;
	    byte[] dat = encodeCharset(cs, txt);
	    if(cs == java.nio.charset.StandardCharsets.UTF_8) {
	    	byte[] dat2 = new byte[dat.length + 3];
	    	for(int i = 0; i < dat.length; i++) dat2[i + 3] = dat[i];
	    	dat2[0] = (byte)0xEF; dat2[1] = (byte)0xBB; dat2[2] = (byte)0xBF;
	    	dat = dat2;
	    }
		writeAllBytes(fname, dat);
    }

    public static byte[] readAllBytes(java.io.InputStream istr) throws java.io.IOException {
        byte[] buffer = new byte[10240];
        int len;
        java.io.ByteArrayOutputStream  ba = new java.io.ByteArrayOutputStream ();
        while ((len = istr.read(buffer, 0, buffer.length)) != -1) {
            if(len > 0) ba.write(buffer, 0, len);
        }
        ba.flush();
        return ba.toByteArray();
    }

    public static byte[] readAllBytes(Stream istr) throws java.io.IOException {
		if(istr instanceof MemoryStream)
			return ((MemoryStream)istr).toByteArray();
        byte[] buffer = new byte[10240];
        int len;
        java.io.ByteArrayOutputStream  ba = new java.io.ByteArrayOutputStream ();
        while ((len = istr.read(buffer, 0, buffer.length)) != -1) {
            if(len > 0) ba.write(buffer, 0, len);
        }
        ba.flush();
        return ba.toByteArray();
    }

    public static long getTotalMemory(boolean collect) {
        if(collect) System.gc();
        return Runtime.getRuntime().totalMemory();
    }

    public static String getMachineName() {
    	try {
    		return java.net.InetAddress.getLocalHost().getHostName();
    	}
        catch(Exception e) {
        }
    	return null;
    }

    public static RegexMatchWrapper createMatchRegex(java.util.regex.Pattern p, String txt, int from) {
    	return new RegexMatchWrapper(p, txt, from);
    }
	public static java.util.ArrayList<RegexMatchWrapper> matchesRegex(java.util.regex.Pattern p, String txt, int from) {
		java.util.ArrayList<RegexMatchWrapper> res = new java.util.ArrayList<RegexMatchWrapper>();
		RegexMatchWrapper wr = new RegexMatchWrapper(p, txt, from); 
		for(; wr != null && wr.success;) {
			res.add(wr);
			wr = wr.nextMatch();
		}
		return res;
	}
	public static boolean isMatchRegex(java.util.regex.Pattern p, String txt) {
		RegexMatchWrapper wr = new RegexMatchWrapper(p, txt, 0); 
		return wr.success;
	}

	public static String consoleReadLine() {
		try(java.util.Scanner s = new java.util.Scanner(System.in)) {
			return s.nextLine();
		}
	}


    public static final Object EMPTYEVENTARGS = new Object();

    public static final java.util.UUID EMPTYUUID = new java.util.UUID(0, 0);

    public static byte[] getBytesFromUUID(java.util.UUID uuid) {
        java.nio.ByteBuffer bb = java.nio.ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        byte[] res = bb.array();
        byte b;
        b = res[0]; res[0] = res[3]; res[3] = b;
        b = res[1]; res[1] = res[2]; res[2] = b;
        b = res[4]; res[4] = res[5]; res[5] = b;
        b = res[6]; res[6] = res[7]; res[7] = b;
        return res;
    }

    public static java.util.UUID getUUIDFromBytes(byte[] bytes) {
    	byte[] res = new byte[bytes.length];
    	for(int i = 0; i < res.length; i++) res[i] = bytes[i];
        byte b;
        b = res[0]; res[0] = res[3]; res[3] = b;
        b = res[1]; res[1] = res[2]; res[2] = b;
        b = res[4]; res[4] = res[5]; res[5] = b;
        b = res[6]; res[6] = res[7]; res[7] = b;
    	java.nio.ByteBuffer byteBuffer = java.nio.ByteBuffer.wrap(res);
        Long high = byteBuffer.getLong();
        Long low = byteBuffer.getLong();
        return new java.util.UUID(high, low);
    }    
}
