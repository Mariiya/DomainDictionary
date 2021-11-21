package com.onpu.domaindictionary.dao;

public class Constants {

    public static String GET_ELECTRONIC_DICTIONARY = "select * from electronic_dictionary";
    public static String CREATE_ELECTRONIC_DICTIONARY = "MERGE INTO ELECTRONIC_DICTIONARY old\n" +
            "USING (SELECT r_seq_next() ELECTRONIC_DICTIONARY_ID,\n" +
            "              ?            NAME,\n" +
            "              ?            AUTHOR,\n" +
            "              ?            PATH_TO_FILE\n" +
            "       FROM DUAL) new\n" +
            "ON (old.PATH_TO_FILE = new.PATH_TO_FILE)\n" +
            "WHEN NOT MATCHED THEN\n" +
            "    INSERT VALUES (r_seq_next(), new.NAME, new.AUTHOR, new.PATH_TO_FILE)\n";
    public static String CREATE_RULE = "";
    public static String SEARCH_TERM = "select distinct to_char(DEFINITION) from DICTIONARY_ENTRY where (TERM like ? || '_' OR TERM like ?)  and ELECTRONIC_DICTIONARY_Id = ?";
    public static String CREATE_DICTIONARY_ENTRY = "INSERT  into dictionary_entry (DICTIONARY_ENTRY_ID, TERM, DEFINITION, DOMAIN_DICTIONARY_ID,ELECTRONIC_DICTIONARY_ID)\n" +
            "        VALUES (RSEQ.nextval, ?, TO_CLOB(?),null, ?)";

    public static final String GET_RESOURCES = "select RESOURCE_ID,ir.name,type,SUBTYPE\n" +
            "from DICTIONARY_BANK db\n" +
            "    left join INTERNET_RESOURCE ir on(db.RESOURCE_ID = ir.INTERNET_RESOURCE_ID)\n" +
            "    union all\n" +
            "select RESOURCE_ID,ed.name,type,SUBTYPE\n" +
            "from DICTIONARY_BANK db\n" +
            "left join ELECTRONIC_DICTIONARY ED on ed.ELECTRONIC_DICTIONARY_ID = db.RESOURCE_ID";

    public static final String GET_USER_BY_NAME_OR_EMAIL = "select user_id id, name, email, role, password\n" +
            "from DD_USER \n" +
            "where EMAIL = ?\n" +
            "   or name = ?";

    public static final String IS_USER_BY_NAME = "select count(user_id)\n" +
            "from DD_USER  \n" +
            "where name = ?";

    public static final String IS_USER_BY_EMAIL = "select count(user_id)\n" +
            "from DD_USER\n" +
            "where EMAIL = ?\n";

    public static final String CREATE_USER = "INSERT INTO DD_USER (USER_ID, NAME, EMAIL, PASSWORD, ROLE)\n" +
            "                VALUES (SEQ.nextval, ?, ?, ?, ?)";
}
