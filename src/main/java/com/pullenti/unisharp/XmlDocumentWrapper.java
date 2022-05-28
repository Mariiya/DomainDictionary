package com.pullenti.unisharp;

public class XmlDocumentWrapper {

    public javax.xml.parsers.DocumentBuilder db;
    public org.w3c.dom.Document doc;
    public XmlDocumentWrapper() { 
        javax.xml.parsers.DocumentBuilderFactory fact = javax.xml.parsers.DocumentBuilderFactory.newInstance(); 
        try { db = fact.newDocumentBuilder(); } catch(Exception e) { }
    }
    public void load(Stream str) throws org.xml.sax.SAXException, java.io.IOException {
        if(str instanceof MemoryStream) {
            doc = db.parse(((MemoryStream)str).toInputStream());
            return;
        }
        byte[] buf = new byte[10000];
        MemoryStream mem = new MemoryStream();
        while(true) {
        	int i = str.read(buf, 0, buf.length);
        	if(i <= 0) break;
        	if(i > 0) mem.write(buf, 0, i);
        }
        doc = db.parse(mem.toInputStream());
        mem.close();
    }

    public boolean getPreserveWhitespace() {
    	return true;
    }
    public boolean setPreserveWhitespace(boolean v) {
    	return true;
    }

}
