package com.pullenti.unisharp;

public class XmlWriterSettings {
	
	boolean indent;
	public boolean getIndent() { return indent; }
	public boolean setIndent(boolean b) { return indent = b; }
	
	String indentChars;
	public String getIndentChars() { return indentChars; }
	public String setIndentChars(String v) { return indentChars = v; }
	
	java.nio.charset.Charset encoding;
	public java.nio.charset.Charset getEncoding() { return encoding; }
	public java.nio.charset.Charset setEncoding(java.nio.charset.Charset v) { return encoding = v; }
	
}