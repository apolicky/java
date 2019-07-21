package cz.cuni.mff.java.semestr4.prednaska.jdbc.jdbcxml;

import java.sql.*;

public class JDBCTest {

  public static void main(String[] args) throws Exception {
    Class.forName("com.mysql.jdbc.Driver");
    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/first_try_schema", "pokus","heslopokusu");
    
    Statement stmt = con.createStatement();
    
    ResultSet rs = stmt.executeQuery("SELECT * FROM fruits");
    
    int i = 1;
    while (rs.next()) {
      System.out.println("Row " + i);
      System.out.println("a = " + rs.getString("id") + ", b = " + rs.getString("name") + ", c= " + rs.getDouble("price"));
      i++;
    }

    stmt.close();
    con.close();
  }
}
