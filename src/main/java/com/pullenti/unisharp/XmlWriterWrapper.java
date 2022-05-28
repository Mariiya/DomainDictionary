package com.pullenti.unisharp;

public class XmlWriterWrapper implements AutoCloseable {

    public javax.xml.stream.XMLStreamWriter wr;
    private java.io.FileOutputStream str;
    private java.io.CharArrayWriter saw;
    private StringBuilder sbres;
    private FileStream fstr;
    public String encoding = "utf-8";

    public XmlWriterWrapper(String fname, XmlWriterSettings s) throws java.io.FileNotFoundException, javax.xml.stream.XMLStreamException { 
        str = new java.io.FileOutputStream(fname); 
        javax.xml.stream.XMLOutputFactory fact = javax.xml.stream.XMLOutputFactory.newInstance();
        if(s != null) {
        	//if(s.getIndent())
        		//fact.setProperty("indent", "true");
        	if(s.getEncoding() != null)
        		encoding = s.getEncoding().name();
        }
        wr = fact.createXMLStreamWriter(str, encoding); 
    }
    public XmlWriterWrapper(StringBuilder res, XmlWriterSettings s) throws javax.xml.stream.XMLStreamException {
        saw = new java.io.CharArrayWriter();
        javax.xml.stream.XMLOutputFactory fact = javax.xml.stream.XMLOutputFactory.newInstance(); 
        if(s != null) {
        	//if(s.getIndent())
        		//fact.setProperty("indent", "true");
        	if(s.getEncoding() != null)
        		encoding = s.getEncoding().name();
        }
        wr = fact.createXMLStreamWriter(saw);
        sbres = res;
    }
    public XmlWriterWrapper(FileStream fs, XmlWriterSettings s) throws java.io.FileNotFoundException, javax.xml.stream.XMLStreamException { 
        fstr = fs; 
        saw = new java.io.CharArrayWriter();
        javax.xml.stream.XMLOutputFactory fact = javax.xml.stream.XMLOutputFactory.newInstance(); 
        if(s != null) {
        	//if(s.getIndent())
        		//fact.setProperty("indent", "true");
        	if(s.getEncoding() != null)
        		encoding = s.getEncoding().name();
        }
        wr = fact.createXMLStreamWriter(saw); 
    }
    @Override
    public void close() {
        try { 
            if(wr != null) wr.close(); wr = null; 
            if(str != null) str.close(); str = null; 
            if(sbres != null) {
                if(saw != null) {
                    sbres.append(saw);
                    saw.close(); saw = null;
				}
            }
            if(fstr != null) {
            	if(saw != null) {
            		String txt = saw.toString(); 
            		saw.close(); saw = null;
            		byte[] arr = Utils.encodeCharset(Utils.getCharsetByName(encoding), txt);
            		fstr.write(arr, 0, arr.length);
            	}
            }
        } catch(Exception ee) { 
		    //System.out.println(ee);
		} 
    }

    public void writeElementString(String tag, String text) throws javax.xml.stream.XMLStreamException {
        wr.writeStartElement(tag);
        wr.writeCharacters(text);
        wr.writeEndElement();
    }
}