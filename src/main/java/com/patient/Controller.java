//package com.patient;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class Controller {
//
//  public static void main(String[] args) {
//
//    String url = "jdbc:postgresql://localhost:5432/medicalApp";
//    String user = "postgres";
//    String password = "postgres";
//
//    try {
//      Connection con = DriverManager.getConnection(url, user, password);
//      Statement st = con.createStatement();
//
//
//      ResultSet cancerResult = st.executeQuery("SELECT  (id)  from cancer");
//
//      ArrayList<String> cancers = new ArrayList<>();
//      while (cancerResult.next()) {
//        cancers.add(cancerResult.getInt(1) + "");
//      }
//
//      for (String cancer: cancers) {
//
//        System.out.println("Reading Regimens for cancers : " + cancer);
//        ResultSet rs = st.executeQuery("SELECT  (pk)  from regimen_detail where subcancer_type3_id = " + cancer);
//        String regimens = "";
//        while (rs.next()) {
//          regimens = regimens.concat(regimens.equals("") ? "" :  ",") + String.valueOf(rs.getInt(1));
//        }
//
//        System.out.println("Regimes for Cancer : " + cancer + " are: " + regimens);
//
//        String query = "UPDATE cancer set regimen = ? where id = ?";
//        PreparedStatement pst = con.prepareStatement(query);
//        pst.setString(1, regimens);
//        pst.setInt(2, Integer.parseInt(cancer));
//        pst.executeUpdate();
//      }
//
//
//    } catch (SQLException ex) {
//      System.out.println(ex.getMessage());
//    }
//  }
//}