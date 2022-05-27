package com.pullenti.domaindictionary.dao;

public class Constants {

    public static String GET_ELECTRONIC_DICTIONARY = "select * from electronic_dictionary  ed left join resource_bank rb\n" +
            "on ed.electronic_dictionary_id=rb.resource_id\n" +
            "left join rule r on r.rule_id = rb.rule_id where ed.electronic_dictionary_id =?";

    public static String CREATE_ELECTRONIC_DICTIONARY = "insert into electronic_dictionary values (nextval('resource_seq'), ?,?,?) ON CONFLICT DO NOTHING";
    public static String CREATE_DOMAIN_DICTIONARY = "insert into domain_dictionary values (nextval('resource_seq'), ?,?,?) ON CONFLICT DO NOTHING";
    public static String CREATE_RULE = "INSERT INTO RULE VALUES (nextval('rule_seq'),?,?,?,?) ON CONFLICT DO NOTHING";
    public static String ADD_TO_RESOURCE_BANK = "insert into resource_bank values " +
            "((SELECT last_value FROM resource_seq),?,?,(SELECT last_value FROM rule_seq)) ON CONFLICT DO NOTHING";

    public static String GET_DOMAIN_DICTIONARY = "select domain_dictionary_id from domain_dictionary where user_id = ? and path_to_file = ?";

    public static final String GET_RESOURCES = "select RESOURCE_ID, ir.name, type, SUBTYPE\n" +
            "from RESOURCE_BANK db\n" +
            "         left join INTERNET_RESOURCE ir on (db.RESOURCE_ID = ir.INTERNET_RESOURCE_ID)\n" +
            "union all\n" +
            "select RESOURCE_ID, ed.name, type, SUBTYPE\n" +
            "from RESOURCE_BANK db\n" +
            "         left join ELECTRONIC_DICTIONARY ED on ed.ELECTRONIC_DICTIONARY_ID = db.RESOURCE_ID";

    public static final String GET_USER_BY_NAME_OR_EMAIL = "select user_id id, name, email, role, password\n" +
            "from USERS \n" +
            "where EMAIL = ?\n" +
            "   or name = ?";

    public static final String IS_USER_BY_NAME = "select count(user_id)\n" +
            "from  USERS  \n" +
            "where name = ?";

    public static final String GET_NUMBER_OF_DD_BY_USER = "SELECT COUNT(*) FROM domain_dictionary WHERE user_id = ?";

    public static final String IS_USER_BY_EMAIL = "select count(user_id)\n" +
            "from  USERS\n" +
            "where EMAIL = ?\n";

    public static final String GET_USER_BY_ID = "select user_id id, name, email, role, password\n" +
            "from USERS \n" +
            "where USER_ID = ?\n";

    public static final String CREATE_USER = "INSERT INTO  USERS (USER_ID, NAME, EMAIL, PASSWORD, ROLE)\n" +
            "                VALUES (DEFAULT, ?, ?, ?, ?)";
}
