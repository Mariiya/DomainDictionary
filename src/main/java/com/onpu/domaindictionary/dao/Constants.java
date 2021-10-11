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
    public static String CREATE_DICTIONARY_ENTRY = "MERGE INTO DICTIONARY_ENTRY old USING (SELECT seq_next() DICTIONARY_ENTRY_ID,\n" +
            "                  ?          TERM,\n" +
            "                  TO_CLOB(?)          DEFINITION,\n" +
            "                  NULL        DOMAIN_DICTIONARY_ID,\n" +
            "                  1          ELECTRONIC_DICTIONARY_ID\n" +
            "           FROM DUAL) new\n" +
            "    ON (old.DICTIONARY_ENTRY_ID = new.DICTIONARY_ENTRY_ID)\n" +
            "    WHEN MATCHED THEN\n" +
            "        UPDATE\n" +
            "        SET old.TERM                     = new.TERM,\n" +
            "            old.DEFINITION               = new.DEFINITION,\n" +
            "            old.ELECTRONIC_DICTIONARY_ID = new.ELECTRONIC_DICTIONARY_ID\n" +
            "        WHERE old.TERM <> new.TERM\n" +
            "           OR old.ELECTRONIC_DICTIONARY_ID <> new.ELECTRONIC_DICTIONARY_ID\n" +
            "    WHEN NOT MATCHED THEN\n" +
            "        INSERT (old.DICTIONARY_ENTRY_ID, old.TERM, old.DEFINITION, old.DOMAIN_DICTIONARY_ID,old.ELECTRONIC_DICTIONARY_ID)\n" +
            "        VALUES (seq_curr(), new.TERM, TO_CLOB(new.DEFINITION),null, new.ELECTRONIC_DICTIONARY_ID)";

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

    public static final String CREATE_USER = "MERGE INTO DD_USER old\n" +
            "            USING (SELECT  seq_next()  USER_ID,\n" +
            "                          ?            NAME,\n" +
            "                          ?            EMAIL,\n" +
            "                          ?            PASSWORD,\n" +
            "                          ?            ROLE\n" +
            "                   FROM DUAL) new\n" +
            "            ON (old.USER_ID = new.USER_ID\n" +
            "                OR old.EMAIL = new.EMAIL)\n" +
            "            WHEN MATCHED THEN\n" +
            "                UPDATE\n" +
            "                SET old.NAME      = new.NAME,\n" +
            "                    old.PASSWORD = new.PASSWORD,\n" +
            "                    old.ROLE     = new.ROLE\n" +
            "                WHERE old.NAME     <> new.NAME\n" +
            "                  OR  old.PASSWORD <> new.PASSWORD\n" +
            "                  OR  old.ROLE     <> new.ROLE\n" +
            "                  OR  old.EMAIL    <> new.EMAIL\n" +
            "                  OR  old.USER_ID  <> new.USER_ID\n" +
            "            WHEN NOT MATCHED THEN\n" +
            "                INSERT (old.USER_ID, old.NAME, old.EMAIL, old.PASSWORD,old.ROLE)\n" +
            "                VALUES (SEQ_CURR(), new.NAME, new.EMAIL, new.PASSWORD, new.ROLE)";
}
