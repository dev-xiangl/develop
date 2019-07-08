package jp.isols.tool.dbunit;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;

public class DataDumpExport {

    /** データベース名 */
	public static String DATABASE_NAME = "__isols__";

    /** データベース：ユーザー名 */
	public static String DATABASE_USER = "__isols__";

    /** データベース：パスワード */
	public static String DATABASE_PASS = "__isols__";


    /**
     * テーブルのダンプをXMLファイルに出力する。
     *
     * @param args 使用しない
     * @throws Exception データベースもしくはファイル操作で例外が発生したとき
     */
    public static void main(String[] args) throws Exception {
    	Class.forName("org.postgresql.Driver");
    	Connection connection =
		    DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/" + DATABASE_NAME,
                DATABASE_USER,
                DATABASE_PASS
            );

    	IDatabaseConnection databaseConnection= new DatabaseConnection(connection, DATABASE_NAME.toUpperCase());
		DatabaseConfig dbConfig = databaseConnection.getConfig();
	    dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
        dbConfig.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
		dbConfig.setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, false);
        dbConfig.setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, true);

        IDataSet dataSet = databaseConnection.createDataSet();
        FileOutputStream stream = new FileOutputStream("build/resources/dbDump.xml");
        FlatXmlDataSet.write(dataSet, stream);

        System.out.println("Done.");
    }
}
