package kaihatsu_test09;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class javatoxml {
     public static void main(String[] args) {

          DocumentBuilder builder = null;

          try {
              builder = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder();
         } catch (ParserConfigurationException e) {
              e.printStackTrace();
         }

          Document doc = builder.newDocument();
          Element stocks = doc.createElement("stocks");
          doc.appendChild(stocks);

          String memo = "";

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

            // SELECT文の作成・実行
            String select = "SELECT * from user_list" ;

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
                    memo = col1;
                }

         System.out.println(memo);
          Element date = doc.createElement("date");
          // date.setAttribute("id", "P001");
          date.appendChild(doc.createTextNode(memo));
          stocks.appendChild(date);

          Element companyname = doc.createElement("companyname");
          // date.setAttribute("id", "P001");
          companyname.appendChild(doc.createTextNode("日本レジストリサービス持株会"));
          stocks.appendChild(companyname);

          Element author = doc.createElement("author");
          // date.setAttribute("id", "P001");
          author.appendChild(doc.createTextNode("事務局"));
          stocks.appendChild(date);

          Element number = doc.createElement("number");
          // date.setAttribute("id", "P001");
          number.appendChild(doc.createTextNode("社員番号"));
          stocks.appendChild(number);

          Element name = doc.createElement("name");
          // date.setAttribute("id", "P001");
          name.appendChild(doc.createTextNode("あああ"));
          stocks.appendChild(name);


          File file = new File("C:\\pleiades\\workspace\\kaihatsu_test09\\src\\kaihatsu_test09\\Sample.xml");
          writexml(file, doc);

     } catch ( SQLException e ) {
         e.printStackTrace() ;
     }
     }

     public static boolean writexml(File file, Document doc) {

          Transformer tf = null;

          try {
               TransformerFactory factory = TransformerFactory
                         .newInstance();
               tf = factory.newTransformer();
          } catch (TransformerConfigurationException e) {
               e.printStackTrace();
               return false;
          }

          tf.setOutputProperty("indent", "yes");
          tf.setOutputProperty("encoding", "UTF-8");

          try {
               tf.transform(new DOMSource(doc), new StreamResult(
                         file));
          } catch (TransformerException e) {
               e.printStackTrace();
               return false;
          }

          return true;
     }
}
