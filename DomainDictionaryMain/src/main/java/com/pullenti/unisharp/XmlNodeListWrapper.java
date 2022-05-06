package com.pullenti.unisharp;

public class XmlNodeListWrapper {
    public java.util.ArrayList<org.w3c.dom.Node> arr;
    public org.w3c.dom.NodeList nl;
    public XmlNodeListWrapper(org.w3c.dom.NodeList src) { 
        nl = src; 
        arr = new java.util.ArrayList<org.w3c.dom.Node>(); 
        for(int i = 0; i < nl.getLength(); i++) arr.add(nl.item(i)); 
    }
}