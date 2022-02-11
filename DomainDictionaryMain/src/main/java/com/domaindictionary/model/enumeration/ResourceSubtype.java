package com.domaindictionary.model.enumeration;

import java.util.EnumSet;

public enum ResourceSubtype {
    ENGLISH,
    RUSSIAN,
    UKRAINIAN,

    GEOLOGY,
    JURISPRUDENCE,
    MEDICINE,
    TECHNOLOGY,
    ENGINEERING,
    BIOLOGY,
    HISTORY,
    PHYSICS,
    CHEMISTRY,
    CYBER,
    COMPUTER,
    ETHNOGRAPHY,
    RELIGION,
    PSYCHOLOGY,
    OTHER;

    public static EnumSet<ResourceSubtype> language = EnumSet.of(ENGLISH, RUSSIAN, UKRAINIAN);
    public static EnumSet<ResourceSubtype> domain =
            EnumSet.of(GEOLOGY, JURISPRUDENCE, MEDICINE,
                    TECHNOLOGY, ENGINEERING, BIOLOGY, HISTORY,
                    PHYSICS, CHEMISTRY, CYBER, COMPUTER,
                    ETHNOGRAPHY, RELIGION, PSYCHOLOGY,OTHER);
}
