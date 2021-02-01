package com.test;

import com.patient.models.responses.Regimen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static com.rometools.utils.Strings.isBlank;
import static com.rometools.utils.Strings.trim;

public class DataBaseTest {
  public static void main(String[] args) {
    generateRegimenReferenceWithLinks();
//    convertOldBrandRegimenLinkToDrugRegimenLink();
  }

  public static void createCancerRegimenLinkTable () {
    try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/medicalApp", "postgres", "postgres")) {

      System.out.println("Java JDBC PostgreSQL Example");
      // When this class first attempts to establish a connection, it automatically loads any JDBC 4.0 drivers found within
      // the class path. Note that your application must manually load any JDBC drivers prior to version 4.0.
//          Class.forName("org.postgresql.Driver");

      System.out.println("Connected to PostgreSQL database!");
      Statement statement = connection.createStatement();
      System.out.println("Reading car records...");
      System.out.printf("%-30.30s  %-30.30s%n", "Model", "Price");
      ResultSet resultSet = statement.executeQuery("SELECT * FROM public.cancer");
      int counter = 0;
      while (resultSet.next()) {

        String regimenString = resultSet.getString("regimen");

        if (null != regimenString && regimenString.split(",").length > 0)
        {
          for (String regimenId: regimenString.split(",")) {
            if (!StringUtils.isEmpty(regimenId)) {
              System.out.printf("insert into cancer_regimen_link (id, cancer_id, regimen_id) values (\"%d\", \"%d\", \"%d\")%n", counter, resultSet.getInt("id"),regimenId);
            }
          }
        }
      }

    } /*catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver not found.");
            e.printStackTrace();
        }*/ catch (SQLException e) {
      System.out.println("Connection failure.");
      e.printStackTrace();
    }
  }

  public static void generateRegimenReference () {
    try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/medicalApp", "postgres", "postgres")) {

      System.out.println("Java JDBC PostgreSQL Example");
      // When this class first attempts to establish a connection, it automatically loads any JDBC 4.0 drivers found within
      // the class path. Note that your application must manually load any JDBC drivers prior to version 4.0.
//          Class.forName("org.postgresql.Driver");

      System.out.println("Connected to PostgreSQL database!");
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM previous.regimen_detail");
      int columnId = 1;
      while (resultSet.next()) {
        String referenceString = resultSet.getString("reference");

        if (null != referenceString && referenceString.split("\\$").length > 0)
        {
          for (String reference: referenceString.split("\\$")) {
            if (!isBlank(reference)) {
              System.out.printf("insert into regimen_reference (id, regimen_detail_pk, reference) values (%d, %d, \"%s\");%n", columnId, resultSet.getInt("pk"), trim(reference));
              columnId++;
            }
          }
        }
      }

    } /*catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver not found.");
            e.printStackTrace();
        }*/ catch (SQLException e) {
      System.out.println("Connection failure.");
      e.printStackTrace();
    }
  }

  public static void generateRegimenReferenceWithLinks () {
    try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/medicalApp", "postgres", "postgres")) {

      System.out.println("Java JDBC PostgreSQL Example");
      // When this class first attempts to establish a connection, it automatically loads any JDBC 4.0 drivers found within
      // the class path. Note that your application must manually load any JDBC drivers prior to version 4.0.
//          Class.forName("org.postgresql.Driver");
      Map<Integer, List<RegimenReference>> mapOfReferencesByRegimen = new HashMap<>();
      System.out.println("Connected to PostgreSQL database!");
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM public.regimen_reference");
      int columnId = 1;
      while (resultSet.next()) {
        Integer regimenId = resultSet.getInt("regimen_detail_id");

        if (null != regimenId)
        {
          mapOfReferencesByRegimen.computeIfAbsent(regimenId, k -> new ArrayList<>());
          mapOfReferencesByRegimen.get(regimenId).add(new RegimenReference(0, resultSet.getString("reference"), null, regimenId));
        }
      }

      Map<Integer, List<RegimenReference>> updatedMapOfReferencesByPopulatingLinks = new HashMap<>();

      for(Map.Entry<Integer, List<RegimenReference>> entry: mapOfReferencesByRegimen.entrySet()) {
        mapOfReferencesByRegimen.computeIfAbsent(entry.getKey(), k -> new ArrayList<>());
        List<RegimenReference> regimenReferences = entry.getValue();
        List<RegimenReference> referencesWithCorrectLinks = new ArrayList<>();
        Collections.sort(regimenReferences);
//        System.out.format("Processing reference for Regimen  %d, found %d regimen; %n", entry.getKey(), regimenReferences.size());

        for (int i=0; i < regimenReferences.size(); i = i + 2) {
          RegimenReference regimenReference = new RegimenReference();
          regimenReference.reference = regimenReferences.get(i).reference;
          if (i + 1 < regimenReferences.size()) {
            regimenReference.referenceLink = regimenReferences.get(i + 1).reference;
          }
          regimenReference.regimenId = regimenReferences.get(i).regimenId;
//          System.out.format("i: %d, referenceSize: %d; Regimen Created: %s; %n", i, regimenReferences.size(), regimenReference.toString());
          referencesWithCorrectLinks.add(regimenReference);
        }

        updatedMapOfReferencesByPopulatingLinks.put(entry.getKey(), referencesWithCorrectLinks);
      }

      System.out.println("Number of regimen Captured: " + updatedMapOfReferencesByPopulatingLinks.size());

      // this loop is supposed to be used to create the  query
      int counter = 1;
      for(Map.Entry<Integer, List<RegimenReference>> entry: updatedMapOfReferencesByPopulatingLinks.entrySet()) {
        List<RegimenReference> regimenReferences = entry.getValue();
        for(RegimenReference r: regimenReferences) {
          String reference = StringUtils.replace(r.reference, "\n", " ");
          reference = StringUtils.replace(reference, "'", "''");
          System.out.printf("insert into regimen_reference (id, regimen_detail_id, reference, link) values (%d, %d, '%s', '%s');%n",
                  counter, r.regimenId, reference, r.referenceLink);
          counter++;
        }

      }

    } /*catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver not found.");
            e.printStackTrace();
        }*/ catch (SQLException e) {
      System.out.println("Connection failure.");
      e.printStackTrace();
    }
  }

  public static void splitBrandNameAndCreateNewTable () {
    try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/medicalApp", "postgres", "postgres")) {

      System.out.println("Java JDBC PostgreSQL Example");
      // When this class first attempts to establish a connection, it automatically loads any JDBC 4.0 drivers found within
      // the class path. Note that your application must manually load any JDBC drivers prior to version 4.0.
//          Class.forName("org.postgresql.Driver");

      System.out.println("Connected to PostgreSQL database!");
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM previous.regimen_detail");
      int columnId = 1;
      HashMap<String, String> brandNameGenericNameMap = new HashMap<String, String>();
      while (resultSet.next()) {
        String brandNames = resultSet.getString("brand_names");

        if (null != brandNames && brandNames.split("<br>").length > 0) {
          for (String brandName : brandNames.split("<br>")) {
            if (!isBlank(brandName) && brandName.split(":").length > 0) {
              String brand = brandName.split(":")[0];

              if (!brandNameGenericNameMap.containsKey(brand)) {
                brandNameGenericNameMap.put(brand, "");
              }

              String generic = brandName.split(":").length == 1 ? "" : brandName.split(":")[1];
//              for (String eachGenericNameUnderBrand : generic.split(",")) {
                brandNameGenericNameMap.put(brand, generic);
//              }
            }
          }
        }
      }

      for (String key : brandNameGenericNameMap.keySet()) {
        columnId++;
        System.out.printf("insert into regimen_brand_names (id, brand_name, generic_name, manufacturer) values (%d, \"%s\", \"%s\", \"%s\");%n", columnId, key, trim(brandNameGenericNameMap.get(key)), "");
      }

//      Iterator<String> j = brandNameGenericNameMap.keySet().iterator();
//      int branNameColumn = 1;
//      while (j.hasNext()) {
//        String brandName = j.next();
//        System.out.printf("insert into brand (id, brand_name) values (%d, \"%s\");%n", branNameColumn, trim(brandName));
//        branNameColumn++;
//      }
//
//      for (String key: brandNameGenericNameMap.keySet()) {
//        if (brandNameGenericNameMap.get(key).size() == 0) {
//          brandNameGenericNameMap.get(key).add("");
//        }
//
//        Iterator<String> i = brandNameGenericNameMap.get(key).iterator();
//        while (i.hasNext()) {
//          String genericName = i.next();
//          if (!(isBlank(trim(key))&& isBlank(trim(genericName)))) {
////            System.out.printf("insert into brand (id, brand_name, generic_name, manufacturer) values (%d, \"%s\", \"%s\", \"%s\");%n", columnId, trim(key), trim(genericName), "");
//            columnId++;
//          }
//        }
//      }

    } /*catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver not found.");
            e.printStackTrace();
        }*/ catch (SQLException e) {
      System.out.println("Connection failure.");
      e.printStackTrace();
    }
  }

  public static void getRegimenLinkedToThisBrand() {
    try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/medicalApp", "postgres", "postgres")) {

      System.out.println("Java JDBC PostgreSQL Example");
      // When this class first attempts to establish a connection, it automatically loads any JDBC 4.0 drivers found within
      // the class path. Note that your application must manually load any JDBC drivers prior to version 4.0.
//          Class.forName("org.postgresql.Driver");

      System.out.println("Connected to PostgreSQL database!");
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM previous.brand");
      Map brandMap = new HashMap<String, String>();
      int columnId = 1;
      while (resultSet.next()) {
        String brandName = resultSet.getString("brand_name");
        int id = resultSet.getInt("id");

        brandMap.put(StringUtils.capitalize(trim(brandName)), String.valueOf(id));

      }

      resultSet = statement.executeQuery("SELECT * FROM previous.regimen_detail");
      int counter  = 0;
      while (resultSet.next()) {
        String brandName = resultSet.getString("brand_names");
        if (!StringUtils.isEmpty(brandName)) {
          String[] brands = brandName.split("<br>");

          for (String brand : brands) {
            if (brand.split(":").length > 0 &&
                    brandMap.containsKey(StringUtils.capitalize(trim(brand.split(":")[0])))) {
              System.out.printf("insert into brand_regimen_link (id, brand_id, regimen_id) values (%d, %s, %d);\n", counter,
                      brandMap.get(trim(StringUtils.capitalize(brand.split(":")[0]))), resultSet.getInt("pk"));
              counter++;
            }
          }
        }
      }

    } /*catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver not found.");
            e.printStackTrace();
        }*/ catch (SQLException e) {
      System.out.println("Connection failure.");
      e.printStackTrace();
    }
  }

  public static void createRegimenLevelLink() {
    try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/medicalApp", "postgres", "postgres")) {

      System.out.println("Java JDBC PostgreSQL Example");
      // When this class first attempts to establish a connection, it automatically loads any JDBC 4.0 drivers found within
      // the class path. Note that your application must manually load any JDBC drivers prior to version 4.0.
//          Class.forName("org.postgresql.Driver");

      System.out.println("Connected to PostgreSQL database!");
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM previous.regimen_detail");
      Map levelTypeMap = new HashMap<String, Integer>();

      levelTypeMap.put("ADJUVANT", 1);
      levelTypeMap.put( "NEOADJUVANT", 2);
      levelTypeMap.put("METASTATIC", 3);
      levelTypeMap.put( "LOCALLY ADVANCED", 6);
      levelTypeMap.put( "HEPATIC ARTERY INFUSION", 7);

      int counter  = 1;
      while (resultSet.next()) {
        String regimenType = resultSet.getString("regimen_type");
        if (!StringUtils.isEmpty(regimenType) && levelTypeMap.containsKey(StringUtils.capitalize(trim(regimenType))))
        {
            System.out.printf("insert into regimen_level_link (id, level_id, regimen_id) values (%d, %s, %d);\n", counter,
                    levelTypeMap.get(trim(StringUtils.capitalize(regimenType))), resultSet.getInt("pk"));
            counter++;
          }
        }

    } /*catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver not found.");
            e.printStackTrace();
        }*/ catch (SQLException e) {
      System.out.println("Connection failure.");
      e.printStackTrace();
    }
  }

  public static void createAdditionalRegimenCancerLinkFromCancerIdInRegimen() {
    try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/medicalApp", "postgres", "postgres")) {

      System.out.println("Java JDBC PostgreSQL Example");
      // When this class first attempts to establish a connection, it automatically loads any JDBC 4.0 drivers found within
      // the class path. Note that your application must manually load any JDBC drivers prior to version 4.0.
//          Class.forName("org.postgresql.Driver");

      System.out.println("Connected to PostgreSQL database!");
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM public.regimen_detail");
      int counter = 332;
      while (resultSet.next()) {

        String regimenString = resultSet.getString("subcancer_type3_id");

        if (null != regimenString && regimenString.split(",").length > 0)
        {
          for (String regimenId: regimenString.split(",")) {
            if (!StringUtils.isEmpty(regimenId)) {
              System.out.printf("insert into cancer_regimen_link (id, cancer_id, regimen_id) values (%d, %s, %d)%n", counter, regimenId, resultSet.getInt("id"));
              counter++;
            }
          }
        }
      }

    } /*catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver not found.");
            e.printStackTrace();
        }*/ catch (SQLException e) {
      System.out.println("Connection failure.");
      e.printStackTrace();
    }
  }


  public static void testRegimenLevelLinkData() {
    try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/medicalApp", "postgres", "postgres")) {

      System.out.println("Connected to DB!");
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM previous.regimen_detail");

      while (resultSet.next()) {
        if (!StringUtils.isEmpty(resultSet.getString("regimen_type"))) {
          ResultSet regimenLevelLinkResultSetCount = statement.executeQuery("SELECT COUNT(*) as total FROM previous.regimen_level_link where regimen_id=" + resultSet.getInt("pk"));
          while (regimenLevelLinkResultSetCount.next()) {
            if (regimenLevelLinkResultSetCount.getInt("total") > 1) {
              throw new SQLException("level type hits more than expected, expected 1 hit but got " + regimenLevelLinkResultSetCount.getInt("total"));
            }

            ResultSet regimenLevelLinkResultSet = statement.executeQuery("SELECT * FROM previous.regimen_level_link where regimen_id=" + resultSet.getInt("pk"));
            while (regimenLevelLinkResultSet.next()) {

              ResultSet levelTypeResultSet = statement.executeQuery("SELECT * FROM previous.level_type where id=" + regimenLevelLinkResultSet.getInt("id"));
              while (levelTypeResultSet.next()) {
                if (!resultSet.getString("regimen_type").equals(levelTypeResultSet.getString("level"))) {
                  throw new SQLException("level type didn't match between the one in regimen" + "[" + resultSet.getString("regimen_type") + "]" + " to the one in level type[" + levelTypeResultSet.getInt("level") + "]");
                }
              }
            }
          }
        }
      }

    } /*catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver not found.");
            e.printStackTrace();
        }*/ catch (SQLException e) {
      System.out.println("Connection failure.");
      e.printStackTrace();
    }
  }

  public static void breakBrandToDrugAndDrugBrand() {
    try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/medicalApp", "postgres", "postgres")) {

      System.out.println("Connected to DB!");
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM public.brand");
      Map<String, Integer> genericNameMap = new HashMap<>();
      Map<String, Integer> brandNameMap = new HashMap<>();
      Map<String, Integer> brandGenericLink = new HashMap<>();
      int genericNameCounter = 1;
      int brandNameCounter = 1;
      int brand_drug_counter = 1;
      while (resultSet.next()) {
        String genericName = trim(resultSet.getString("generic_name"));
        String brandName = trim(resultSet.getString("brand_name"));
        if (!StringUtils.isEmpty(genericName) && null == genericNameMap.get(genericName)) {
          genericNameMap.put(genericName, genericNameCounter);
          genericNameCounter++;
        }

        if (null == brandNameMap.get(brandName) && !StringUtils.isEmpty(brandName)) {
          brandNameMap.put(brandName, brandNameCounter);
          brandNameCounter++;

          if (null == brandGenericLink.get(genericNameMap.get(genericName)+","+brandNameMap.get(brandName))) {
            brandGenericLink.put(genericNameMap.get(genericName)+","+brandNameMap.get(brandName), brand_drug_counter);
            brand_drug_counter++;
          }

        }
      }

      genericNameMap.forEach((k, v) -> System.out.printf("insert into drug (id, drug_name) values (%d, \"%s\");%n", v, k));
      brandNameMap.forEach((k, v) -> System.out.printf("insert into drug_brand (id, brand_name, manufacturer) values (%d, \"%s\", \"\");%n", v, k));
      brandGenericLink.forEach((k, v) -> System.out.printf("insert into drug_brand_link (id, drug_id, drug_brand_id) values (%d, %s, %s);%n", v, k.split(",")[0], k.split(",")[1]));


    } /*catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver not found.");
            e.printStackTrace();
        }*/ catch (SQLException e) {
      System.out.println("Connection failure.");
      e.printStackTrace();
    }
  }

  public static void convertOldBrandRegimenLinkToDrugRegimenLink() {
    try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/medicalApp", "postgres", "postgres")) {

      System.out.println("Connected to DB!");
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM public.drug");
      Map<String, Integer> drugMap = new HashMap<>();
      Map<String, Integer> brandMap = new HashMap<>();
      Map<Integer, Integer> brandToDrugMap = new HashMap<>();
      while (resultSet.next()) {
        drugMap.put(resultSet.getString("generic_name"), resultSet.getInt("id"));
      }
      resultSet = statement.executeQuery("SELECT * FROM public.brand");
      while (resultSet.next()) {
        if (null != drugMap.get(trim(resultSet.getString("generic_name")))) {
          brandToDrugMap.put(resultSet.getInt("id"), drugMap.get(trim(resultSet.getString("generic_name"))));
        }
      }
      resultSet = statement.executeQuery("SELECT * FROM public.brand_regimen_link");

      while (resultSet.next()) {
        if (null != brandToDrugMap.get(resultSet.getInt("brand_id"))) {
          System.out.printf("insert into drug_regimen_link (id, drug_id, regimen_id) values (%d, %d, %d);%n", resultSet.getInt("id"), brandToDrugMap.get(resultSet.getInt("brand_id")), resultSet.getInt("regimen_id"));
        }
      }

    } /*catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver not found.");
            e.printStackTrace();
        }*/ catch (SQLException e) {
      System.out.println("Connection failure.");
      e.printStackTrace();
    }
  }
}

//                  System.out.printf("insert into regimen_brand_names (id, regimen_id, brand_name, generic_name, manufacturer) values (%d, %d, \"%s\", \"%s\", \"%s\");%n", columnId, resultSet.getInt("pk"), trim(brand), trim(eachGenericNameUnderBrand), "");
//                  columnId++;


@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
 class RegimenReference implements Comparable<RegimenReference> {
  int id;
  String reference;
  String referenceLink;
  int regimenId;

  @Override
  public int compareTo(RegimenReference o) {
    if(id==o.id)
      return 0;
    else if(id > o.id )
      return 1;
    else
      return -1;
  }

  @Override
  public String toString() {
    return String.format("id: %d; reference: %s; link: %s; regimenId: %d", id, reference, referenceLink, regimenId);
  }
}