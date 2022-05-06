package com.pullenti.unisharp;

public class RegexMatchWrapper {

	public java.util.regex.Matcher matcher;
	public String text;
	public boolean success;
	public int index;
	public int length;
	public String value;
	public java.util.ArrayList<RegexGroupWrapper> groups;

	public RegexMatchWrapper(java.util.regex.Pattern p, String txt, int from) {
		this(p.matcher(txt), txt, from);
	}

	public RegexMatchWrapper(java.util.regex.Matcher m, String txt, int from) {
		matcher = m;
		if(from > 0)
			success = matcher.find(from);
		else 
			success = matcher.find();
		if(!success) return;
		index = matcher.start();
		length = matcher.end() - matcher.start();
		text = txt;
		value = txt.substring(index, index + length);
		groups = new java.util.ArrayList<RegexGroupWrapper>();
		for(int i = 0; i < matcher.groupCount(); i++) {
			RegexGroupWrapper g = new RegexGroupWrapper();
			g.index = matcher.start(i);
			g.length = matcher.end(i) - g.index;
			g.value = txt.substring(g.index, g.index + g.length);
			groups.add(g);
		}
	}
	public RegexMatchWrapper nextMatch( ) {
		RegexMatchWrapper res = new RegexMatchWrapper(matcher, text, -1);
		return res;
	}

	@Override
	public String toString() {
		return value;
	}
}
