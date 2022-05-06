/*
 * SDK Pullenti Lingvo, version 4.12, march 2022. Copyright (c) 2013, Pullenti. All rights reserved. 
 * Non-Commercial Freeware and Commercial Software.
 * This class is generated using the converter Unisharping (www.unisharping.ru) from Pullenti C# project. 
 * The latest version of the code is available on the site www.pullenti.ru
 */

package com.pullenti.ner;

/**
 * Поддержка проведения анализа текста на внешнем сервере
 */
public class ServerService {

    /**
     * Проверить работоспособность сервера Pullenti.Server. 
     * Отправляется GET-запрос на сервер, возвращает ASCII-строку с версией SDK.
     * @param _address адрес вместе с портом (если null, то это http://localhost:1111)
     * @return версия SDK на сервере или null, если недоступен
     */
    public static String getServerVersion(String _address) {
        try {
            com.pullenti.unisharp.Webclient web = new com.pullenti.unisharp.Webclient();
            byte[] res = web.downloadData((_address != null ? _address : "http://localhost:1111"));
            if (res == null || res.length == 0) 
                return null;
            return com.pullenti.unisharp.Utils.decodeCharset(java.nio.charset.Charset.forName("UTF-8"), res, 0, -1);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Подготовить данные для POST-отправки на сервер
     * @param proc процессор, настройки (анализаторы) которого должны быть воспроизведены на сервере
     * @param text обрабатывамый текст
     * @param lang язык (если не задан, то будет определён автоматически)
     * @return 
     */
    public static byte[] preparePostData(Processor proc, String text, com.pullenti.morph.MorphLang lang) throws Exception, java.io.IOException {
        byte[] dat = null;
        try (com.pullenti.unisharp.MemoryStream mem1 = new com.pullenti.unisharp.MemoryStream()) {
            StringBuilder tmp = new StringBuilder();
            tmp.append((lang == null ? 0 : (int)lang.value)).append(";");
            for (Analyzer a : proc.getAnalyzers()) {
                tmp.append((a.getIgnoreThisAnalyzer() ? "-" : "")).append(a.getName()).append(";");
            }
            com.pullenti.ner.core.internal.SerializerHelper.serializeInt(mem1, 1234);
            com.pullenti.ner.core.internal.SerializerHelper.serializeString(mem1, tmp.toString());
            com.pullenti.ner.core.internal.SerializerHelper.serializeString(mem1, text);
            dat = mem1.toByteArray();
        }
        return dat;
    }

    /**
     * Оформить результат из того, что вернул сервер
     * @param dat массив байт, возвращённый сервером
     * @return результат (но может быть Exception, если была на сервере ошибка)
     */
    public static AnalysisResult createResult(byte[] dat) throws Exception, java.io.IOException {
        if (dat == null || (dat.length < 1)) 
            throw new Exception("Empty result");
        AnalysisResult res = new AnalysisResult();
        com.pullenti.ner.core.AnalysisKit kit = new com.pullenti.ner.core.AnalysisKit(null, false, null, null);
        try (com.pullenti.unisharp.MemoryStream mem2 = new com.pullenti.unisharp.MemoryStream(dat)) {
            if (((char)dat[0]) == 'E' && ((char)dat[1]) == 'R') {
                String err = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(mem2);
                throw new Exception(err);
            }
            kit.deserialize(mem2);
            com.pullenti.unisharp.Utils.addToArrayList(res.getEntities(), kit.getEntities());
            res.firstToken = kit.firstToken;
            res.setSofa(kit.getSofa());
            int i = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(mem2);
            res.baseLanguage = com.pullenti.morph.MorphLang._new56((short)i);
            i = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(mem2);
            for (; i > 0; i--) {
                res.getLog().add(com.pullenti.ner.core.internal.SerializerHelper.deserializeString(mem2));
            }
        }
        return res;
    }

    /**
     * Обработать текст на сервере (если он запущен). 
     * Функция фактически оформляет данные для отправки на сервер через PreparePostData(...), 
     * затем делает POST-запрос по http, полученный массив байт через CreateResult(...) оформляет как результат. 
     * Внимание! Внешняя онтология не поддерживается, в отличие от локальной обработки через Process.
     * @param _address адрес вместе с портом (если null, то это http://localhost:1111)
     * @param proc процессор, настройки (анализаторы) которого должны быть воспроизведены на сервере
     * @param text обрабатывамый текст
     * @param lang язык (если не задан, то будет определён автоматически)
     * @return аналитический контейнер с результатом
     * Обработать текст на сервере
     */
    public static AnalysisResult processOnServer(String _address, Processor proc, String text, com.pullenti.morph.MorphLang lang) throws Exception, java.io.IOException {
        byte[] dat = preparePostData(proc, text, lang);
        com.pullenti.unisharp.Webclient web = new com.pullenti.unisharp.Webclient();
        byte[] res = web.uploadData((_address != null ? _address : "http://localhost:1111"), dat);
        return createResult(res);
    }

    // Для внутреннего использования
    public static byte[] internalProc(com.pullenti.unisharp.Stream stream) throws Exception, java.io.IOException {
        int tag = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        if (tag != 1234) 
            return null;
        String attrs = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(attrs)) 
            return null;
        String[] parts = com.pullenti.unisharp.Utils.split(attrs, String.valueOf(';'), false);
        if (parts.length < 1) 
            return null;
        com.pullenti.morph.MorphLang lang = null;
        if (com.pullenti.unisharp.Utils.stringsNe(parts[0], "0")) 
            lang = com.pullenti.morph.MorphLang._new56(com.pullenti.unisharp.Utils.parseShort(parts[0], 0, null));
        try (Processor proc = ProcessorService.createEmptyProcessor()) {
            for (int i = 1; i < parts.length; i++) {
                String nam = parts[i];
                if (nam.length() == 0) 
                    continue;
                boolean ign = false;
                if (nam.charAt(0) == '-') {
                    ign = true;
                    nam = nam.substring(1);
                }
                for (Analyzer a : ProcessorService.getAnalyzers()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(a.getName(), nam)) {
                        Analyzer aa = a.clone();
                        if (ign) 
                            a.setIgnoreThisAnalyzer(true);
                        proc.addAnalyzer(a);
                    }
                }
            }
            String txt = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
            AnalysisResult ar = proc.process(new SourceOfAnalysis(txt), null, lang);
            byte[] res = null;
            if (ar != null && (ar.tag instanceof com.pullenti.ner.core.AnalysisKit)) {
                try (com.pullenti.unisharp.MemoryStream mem = new com.pullenti.unisharp.MemoryStream()) {
                    com.pullenti.ner.core.AnalysisKit kit = (com.pullenti.ner.core.AnalysisKit)com.pullenti.unisharp.Utils.cast(ar.tag, com.pullenti.ner.core.AnalysisKit.class);
                    kit.getEntities().clear();
                    com.pullenti.unisharp.Utils.addToArrayList(kit.getEntities(), ar.getEntities());
                    kit.serialize(mem, true);
                    com.pullenti.ner.core.internal.SerializerHelper.serializeInt(mem, (ar.baseLanguage == null ? 0 : (int)ar.baseLanguage.value));
                    com.pullenti.ner.core.internal.SerializerHelper.serializeInt(mem, ar.getLog().size());
                    for (String s : ar.getLog()) {
                        com.pullenti.ner.core.internal.SerializerHelper.serializeString(mem, s);
                    }
                    res = mem.toByteArray();
                }
            }
            return res;
        }
    }
    public ServerService() {
    }
}
