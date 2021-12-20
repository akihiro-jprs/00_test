package kaihatsu_test09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLtocsv {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/sampledb" ;
        String user = "postgres" ;
        String password = "password" ;

        String csvFilePath = "information2.csv";

        int batchSize = 20;

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO information (number,name,before_stock_value,before_value,before_hold_value,new_stock_value,new_value,after_stock_value,after_value,after_hold_value) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            int count = 0;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                String number = data[0];
                String name = data[1];
                String before_stock_value = data[2];
                String before_value = data[3];
                String before_host_value = data[4];
                String new_stock_value = data[5];
                String new_value = data[6];
                String after_stock_value = data[7];
                String after_value = data[8];
                String after_host_value = data[9];

                int inumber = Integer.parseInt(number);
                statement.setInt(1, inumber);
                statement.setString(2, name);

                Float fbefore_stock_value = Float.parseFloat(before_stock_value);
                statement.setFloat(3, fbefore_stock_value);

                int ibv = Integer.parseInt(before_value);
                statement.setInt(4, ibv);
                int ibhv = Integer.parseInt(before_host_value);
                statement.setInt(5, ibhv);

                Float fnew_stock_value = Float.parseFloat(new_stock_value);
                statement.setFloat(6, fnew_stock_value);

                int inv = Integer.parseInt(new_value);
                statement.setInt(7, inv);


                Float fafter_stock_value = Float.parseFloat(after_stock_value);
                statement.setFloat(8, fafter_stock_value);

                int iav = Integer.parseInt(after_value);
                statement.setInt(9, iav);
                int iahv = Integer.parseInt(after_host_value);
                statement.setInt(10, iahv);

                statement.addBatch();

                if (count % batchSize == 0) {
                    statement.executeBatch();
                }
            }

            lineReader.close();

            // execute the remaining queries
            statement.executeBatch();

            connection.commit();
            connection.close();

        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
