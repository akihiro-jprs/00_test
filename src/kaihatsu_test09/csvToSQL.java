package kaihatsu_test09;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

public class csvToSQL {

  public static void main(String[] args) throws SQLException, IOException {
    // これ、作成したデータベースからCSVを作る方法です。いりません。

    // データベース接続情報を格納する変数

    // JDBCドライバの読み込み
    try {
      // postgresSQLのJDBCドライバを読みこみ
      Class.forName("org.postgresql.Driver");
    } catch(ClassNotFoundException e) {
      // JDBCドライバが見つからない場合
      e.printStackTrace();
    }

 // 接続文字列の設定
    String url = "jdbc:postgresql://localhost:5432/sampledb" ;
    String user = "postgres" ;
    String password = "password" ;

    Connection conn = null;
    conn = DriverManager.getConnection(url, user, password);

    // PostgreSQLコマンド実行クラス
    // copyManager準備
    FileOutputStream file = new FileOutputStream("C:\\pleiades\\workspace\\kaihatsu_test09\\information3.csv");
    CopyManager copyManager = new CopyManager((BaseConnection) conn);
    Writer writer = new OutputStreamWriter(file, "UTF8");

    // PostgreSQLコマンド呼び出し実行
    String command = "COPY information TO STDOUT WITH (FORMAT csv ,HEADER ,NULL 'null' ,DELIMITER E'\\t');";

    // PostgreSQLコマンド実行
    copyManager.copyOut(command,writer);
    writer.close();

    System.out.println("OutputDao 完了");


    // SELECT文の作成・実行
    String select = "SELECT * from information" ;

    // PostgreSQLに接続
    try (Connection con = DriverManager.getConnection ( url, user, password );
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery ( select ); ) {

        // 実行結果の取得
        while ( result.next() ) {
            String col1 = result.getString ( 1 ) ;
            String col2 = result.getString ( 2 ) ;
            String col3 = result.getString ( 3 ) ;
            String col4 = result.getString ( 4 ) ;
            String col5 = result.getString ( 5 ) ;
            System.out.println ( col1 + " " + col2 + " " + col3 + " " + col4 + " " + col5) ;
        }
    } catch ( SQLException e ) {
      e.printStackTrace() ;
    }

  }

}