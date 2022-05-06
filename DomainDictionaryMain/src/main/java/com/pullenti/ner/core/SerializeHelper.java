/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner.core;

/**
 * Сериализация сущностей
 */
public class SerializeHelper {

    /**
     * Сериализация в строку XML списка сущностей. Сущности могут быть взаимосвязаны, 
     * то есть значениями атрибутов могут выступать другие сущности (то есть сериализуется по сути граф).
     * @param refs список сериализуемых сущностей
     * @param rootTagName имя корневого узла
     * @param outOccurences выводить ли вхождения в текст
     * @return строка с XML
     */
    public static String serializeReferentsToXmlString(java.util.ArrayList<com.pullenti.ner.Referent> refs, String rootTagName, boolean outOccurences) throws Exception, javax.xml.stream.XMLStreamException {
        int id = 1;
        for (com.pullenti.ner.Referent r : refs) {
            r.tag = id;
            id++;
        }
        StringBuilder res = new StringBuilder();
        try (com.pullenti.unisharp.XmlWriterWrapper xml = new com.pullenti.unisharp.XmlWriterWrapper(res, null)) {
            xml.wr.writeStartElement(rootTagName);
            for (com.pullenti.ner.Referent r : refs) {
                serializeReferentToXml(r, xml, outOccurences, false);
            }
            xml.wr.writeEndElement();
        }
        _corrXmlFile(res);
        for (com.pullenti.ner.Referent r : refs) {
            r.tag = null;
        }
        return res.toString();
    }

    /**
     * Прямая сериализация сущности в строку XML.
     * @param r сериализуемая сущность
     * @param outOccurences выводить ли вхождения в текст
     */
    public static String serializeReferentToXmlString(com.pullenti.ner.Referent r, boolean outOccurences) throws Exception, javax.xml.stream.XMLStreamException {
        StringBuilder res = new StringBuilder();
        try (com.pullenti.unisharp.XmlWriterWrapper xml = new com.pullenti.unisharp.XmlWriterWrapper(res, null)) {
            serializeReferentToXml(r, xml, outOccurences, true);
        }
        _corrXmlFile(res);
        return res.toString();
    }

    /**
     * Прямая сериализация сущности в XML.
     * @param r сериализуемая сущность
     * @param xml куда сериализовать
     * @param outOccurences выводить ли вхождения в текст
     * @param convertSlotRefsToString преобразовывать ли ссылки в слотах на сущноси в строковые значения
     */
    public static void serializeReferentToXml(com.pullenti.ner.Referent r, com.pullenti.unisharp.XmlWriterWrapper xml, boolean outOccurences, boolean convertSlotRefsToString) throws javax.xml.stream.XMLStreamException {
        xml.wr.writeStartElement("referent");
        if (r.tag instanceof Integer) 
            xml.wr.writeAttribute("id", r.tag.toString());
        xml.wr.writeAttribute("typ", r.getTypeName());
        xml.wr.writeAttribute("spel", _corrXmlValue(r.toString()));
        for (com.pullenti.ner.Slot s : r.getSlots()) {
            if (s.getValue() != null) {
                String nam = s.getTypeName();
                xml.wr.writeStartElement("slot");
                xml.wr.writeAttribute("typ", s.getTypeName());
                if ((s.getValue() instanceof com.pullenti.ner.Referent) && (((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class)).tag instanceof Integer)) 
                    xml.wr.writeAttribute("ref", ((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class)).tag.toString());
                if (s.getValue() != null) 
                    xml.wr.writeAttribute("val", _corrXmlValue(s.getValue().toString()));
                if (s.getCount() > 0) 
                    xml.wr.writeAttribute("count", ((Integer)s.getCount()).toString());
                xml.wr.writeEndElement();
            }
        }
        if (outOccurences) {
            for (com.pullenti.ner.TextAnnotation o : r.getOccurrence()) {
                xml.wr.writeStartElement("occ");
                xml.wr.writeAttribute("begin", ((Integer)o.beginChar).toString());
                xml.wr.writeAttribute("end", ((Integer)o.endChar).toString());
                xml.wr.writeAttribute("text", _corrXmlValue(o.getText()));
                xml.wr.writeEndElement();
            }
        }
        xml.wr.writeEndElement();
    }

    private static void _corrXmlFile(StringBuilder res) {
        int i = res.toString().indexOf('>');
        if (i > 10 && res.charAt(1) == '?') 
            res.delete(0, 0 + i + 1);
        for (i = 0; i < res.length(); i++) {
            char ch = res.charAt(i);
            int cod = (int)ch;
            if ((cod < 0x80) && cod >= 0x20) 
                continue;
            if (com.pullenti.morph.LanguageHelper.isCyrillicChar(ch)) 
                continue;
            res.delete(i, i + 1);
            res.insert(i, ("&#x" + String.format("%04X", cod) + ";"));
        }
    }

    private static String _corrXmlValue(String txt) {
        if (txt == null) 
            return "";
        for (char c : txt.toCharArray()) {
            if (((((int)c) < 0x20) && c != '\r' && c != '\n') && c != '\t') {
                StringBuilder tmp = new StringBuilder(txt);
                for (int i = 0; i < tmp.length(); i++) {
                    char ch = tmp.charAt(i);
                    if (((((int)ch) < 0x20) && ch != '\r' && ch != '\n') && ch != '\t') 
                        tmp.setCharAt(i, ' ');
                }
                return tmp.toString();
            }
        }
        return txt;
    }

    /**
     * Десериализация списка взаимосвязанных сущностей из строки
     * @param xmlString результат сериализации функцией SerializeReferentsToXmlString()
     * @return Список экземпляров сущностей
     */
    public static java.util.ArrayList<com.pullenti.ner.Referent> deserializeReferentsFromXmlString(String xmlString) {
        java.util.ArrayList<com.pullenti.ner.Referent> res = new java.util.ArrayList<com.pullenti.ner.Referent>();
        java.util.HashMap<Integer, com.pullenti.ner.Referent> map = new java.util.HashMap<Integer, com.pullenti.ner.Referent>();
        try {
            com.pullenti.unisharp.XmlDocumentWrapper xml = new com.pullenti.unisharp.XmlDocumentWrapper();
            xml.doc = xml.db.parse(new org.xml.sax.InputSource(new java.io.StringReader(xmlString)));
            for (org.w3c.dom.Node x : (new com.pullenti.unisharp.XmlNodeListWrapper(xml.doc.getDocumentElement().getChildNodes())).arr) {
                if (com.pullenti.unisharp.Utils.stringsEq(com.pullenti.unisharp.Utils.getXmlLocalName(x), "referent")) {
                    com.pullenti.ner.Referent r = _deserializeReferent(x);
                    if (r == null) 
                        continue;
                    res.add(r);
                    if (r.tag instanceof Integer) {
                        if (!map.containsKey((int)r.tag)) 
                            map.put((int)r.tag, r);
                    }
                }
            }
        } catch (Exception ex) {
            return null;
        }
        // восстанавливаем ссылки
        for (com.pullenti.ner.Referent r : res) {
            r.tag = null;
            for (com.pullenti.ner.Slot s : r.getSlots()) {
                if (s.getTag() instanceof Integer) {
                    com.pullenti.ner.Referent rr = null;
                    com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.Referent> wraprr559 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.Referent>();
                    com.pullenti.unisharp.Utils.tryGetValue(map, (int)s.getTag(), wraprr559);
                    rr = wraprr559.value;
                    if (rr != null) 
                        s.setValue(rr);
                    s.setTag(null);
                }
            }
        }
        return res;
    }

    /**
     * Десериализация сущности из строки XML
     * @param xmlString результат сериализации функцией SerializeReferentToXmlString()
     * @return Экземпляр сущностей
     */
    public static com.pullenti.ner.Referent deserializeReferentFromXmlString(String xmlString) {
        try {
            com.pullenti.unisharp.XmlDocumentWrapper xml = new com.pullenti.unisharp.XmlDocumentWrapper();
            xml.doc = xml.db.parse(new org.xml.sax.InputSource(new java.io.StringReader(xmlString)));
            return _deserializeReferent(xml.doc.getDocumentElement());
        } catch (Exception ex) {
        }
        return null;
    }

    private static com.pullenti.ner.Referent _deserializeReferent(org.w3c.dom.Node xml) throws NumberFormatException {
        String typ = null;
        int id = 0;
        if (xml.getAttributes() != null) {
            for (org.w3c.dom.Node a : (new com.pullenti.unisharp.XmlAttrListWrapper(xml.getAttributes())).arr) {
                if (com.pullenti.unisharp.Utils.stringsEq(com.pullenti.unisharp.Utils.getXmlLocalName(a), "id")) 
                    id = com.pullenti.unisharp.Utils.parseInteger(a.getNodeValue(), 0, null);
                else if (com.pullenti.unisharp.Utils.stringsEq(com.pullenti.unisharp.Utils.getXmlLocalName(a), "typ")) 
                    typ = a.getNodeValue();
            }
        }
        if (typ == null) 
            return null;
        com.pullenti.ner.Referent res = com.pullenti.ner.ProcessorService.createReferent(typ);
        if (res == null) 
            return null;
        res.tag = id;
        for (org.w3c.dom.Node x : (new com.pullenti.unisharp.XmlNodeListWrapper(xml.getChildNodes())).arr) {
            if (com.pullenti.unisharp.Utils.stringsNe(com.pullenti.unisharp.Utils.getXmlLocalName(x), "slot")) 
                continue;
            String nam = null;
            String val = null;
            int cou = 0;
            int refid = 0;
            if (x.getAttributes() != null) {
                for (org.w3c.dom.Node a : (new com.pullenti.unisharp.XmlAttrListWrapper(x.getAttributes())).arr) {
                    if (com.pullenti.unisharp.Utils.stringsEq(com.pullenti.unisharp.Utils.getXmlLocalName(a), "typ")) 
                        nam = a.getNodeValue();
                    else if (com.pullenti.unisharp.Utils.stringsEq(com.pullenti.unisharp.Utils.getXmlLocalName(a), "count")) 
                        cou = com.pullenti.unisharp.Utils.parseInteger(a.getNodeValue(), 0, null);
                    else if (com.pullenti.unisharp.Utils.stringsEq(com.pullenti.unisharp.Utils.getXmlLocalName(a), "ref")) 
                        refid = com.pullenti.unisharp.Utils.parseInteger(a.getNodeValue(), 0, null);
                    else if (com.pullenti.unisharp.Utils.stringsEq(com.pullenti.unisharp.Utils.getXmlLocalName(a), "val")) 
                        val = a.getNodeValue();
                }
            }
            if (nam == null) 
                continue;
            com.pullenti.ner.Slot slot = res.addSlot(nam, val, false, 0);
            slot.setCount(cou);
            if (refid > 0) 
                slot.setTag(refid);
        }
        return res;
    }

    /**
     * Сериализация в строку JSON списка сущностей. Сущности могут быть взаимосвязаны, 
     * то есть значениями атрибутов могут выступать другие сущности (то есть сериализуется по сути граф).
     * @param refs список сериализуемых сущностей
     * @param rootTagName имя корневого узла
     * @param outOccurences выводить ли вхождения в текст
     * @return строка с JSON (массив [...])
     */
    public static String serializeReferentsToJsonString(java.util.ArrayList<com.pullenti.ner.Referent> refs, boolean outOccurences) {
        int id = 1;
        for (com.pullenti.ner.Referent r : refs) {
            r.tag = id;
            id++;
        }
        StringBuilder res = new StringBuilder();
        res.append("[");
        for (com.pullenti.ner.Referent r : refs) {
            String json = serializeReferentToJsonString(r, outOccurences);
            res.append("\r\n");
            res.append(json);
            if (r != refs.get(refs.size() - 1)) 
                res.append(", ");
        }
        res.append("\r\n]");
        for (com.pullenti.ner.Referent r : refs) {
            r.tag = null;
        }
        return res.toString();
    }

    /**
     * Сериализация сущности в JSON (словарь {...}).
     * @param r сериализуемая сущность
     * @param outOccurences выводить ли вхождения в текст
     * @return строка со словарём JSON
     */
    public static String serializeReferentToJsonString(com.pullenti.ner.Referent r, boolean outOccurences) {
        StringBuilder res = new StringBuilder();
        res.append("{");
        if (r.tag instanceof Integer) 
            res.append("\r\n  \"id\" : ").append(r.tag).append(",");
        res.append("\r\n  \"typ\" : \"").append(r.getTypeName()).append("\", ");
        res.append("\r\n  \"spel\" : \"");
        _corrJsonValue(r.toString(), res);
        res.append("\", ");
        res.append("\r\n  \"slots\" : [");
        for (int i = 0; i < r.getSlots().size(); i++) {
            com.pullenti.ner.Slot s = r.getSlots().get(i);
            res.append("\r\n      ").append('{').append(" \"typ\" : \"").append(s.getTypeName()).append("\", ");
            if (s.getValue() instanceof com.pullenti.ner.Referent) 
                res.append("\"ref\" : ").append(((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class)).tag.toString()).append(", ");
            if (s.getValue() != null) 
                res.append("\"val\" : \"");
            _corrJsonValue(s.getValue().toString(), res);
            res.append("\"");
            if (s.getCount() > 0) 
                res.append(", \"count\" : ").append(((Integer)s.getCount()).toString());
            res.append(" }");
            if ((i + 1) < r.getSlots().size()) 
                res.append(",");
        }
        res.append(" ]");
        if (outOccurences) {
            res.append(",\r\n  \"occs\" : [");
            for (int i = 0; i < r.getOccurrence().size(); i++) {
                com.pullenti.ner.TextAnnotation o = r.getOccurrence().get(i);
                res.append("\r\n      ").append('{').append(" \"begin\" : ").append(o.beginChar).append(", \"end\" : ").append(o.endChar).append(", \"text\" : \"");
                _corrJsonValue(o.getText(), res);
                res.append("\" }");
                if ((i + 1) < r.getOccurrence().size()) 
                    res.append(",");
            }
            res.append(" ]");
        }
        res.append("\r\n}");
        return res.toString();
    }

    private static void _corrJsonValue(String txt, StringBuilder res) {
        for (char ch : txt.toCharArray()) {
            if (ch == '"') 
                res.append("\\\"");
            else if (ch == '\\') 
                res.append("\\\\");
            else if (ch == '/') 
                res.append("\\/");
            else if (((int)ch) == 0xD) 
                res.append("\\r");
            else if (((int)ch) == 0xA) 
                res.append("\\n");
            else if (ch == '\t') 
                res.append("\\t");
            else if (((int)ch) < 0x20) 
                res.append(' ');
            else 
                res.append(ch);
        }
    }
    public SerializeHelper() {
    }
}
