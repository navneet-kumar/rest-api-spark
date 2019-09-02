package services.implementations.postgres;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import models.Product;
import models.Purchaser;
import services.PurchaserService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class PurchaserServiceImpl implements PurchaserService {
  private PostgresSQLConnection psql;

  public PurchaserServiceImpl() {
    psql = PostgresSQLConnection.getInstance();
  }

  @Override
  public boolean addUser(Purchaser user) {
    {
      boolean status = false;
      try {
        PreparedStatement preparedStatement =
            psql.getConnection()
                .prepareStatement(
                    "insert into purchaser(purchaser_name,purchaser_created) values(?,?)");
        preparedStatement.setString(1, user.getName());
        preparedStatement.setTimestamp(2, user.getCreated());
        int response = preparedStatement.executeUpdate();
        if (response > 0) {
          status = true;
        }
      } catch (SQLException ex) {
        System.out.println(ex.getErrorCode() + " " + ex.getLocalizedMessage());
      }
      return status;
    }
  }

  @Override
  public Collection<Purchaser> getUsers() {
    List<Purchaser> purchasers = new ArrayList<>();
    try {
      PreparedStatement preparedStatement =
          psql.getConnection()
              .prepareStatement(
                  "select purchaser_id,purchaser_name,purchaser_created from purchaser");
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Purchaser purchaser = new Purchaser();
        purchaser.setId(resultSet.getString("purchaser_id"));
        purchaser.setName(resultSet.getString("purchaser_name"));
        purchaser.setCreated(resultSet.getTimestamp("purchaser_created"));
        purchasers.add(purchaser);
      }
    } catch (SQLException ex) {
      System.out.println(ex.getErrorCode() + " " + ex.getLocalizedMessage());
    }
    return purchasers;
  }

  @Override
  public Purchaser getUser(String id) {
    Purchaser purchaser = null;
    try {
      PreparedStatement preparedStatement =
          psql.getConnection()
              .prepareStatement(
                  "select purchaser_id,purchaser_name,purchaser_created from purchaser where purchaser_id = ?");
      preparedStatement.setInt(1, Integer.parseInt(id));
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        purchaser = new Purchaser();
        purchaser.setId(resultSet.getString("purchaser_id"));
        purchaser.setName(resultSet.getString("purchaser_name"));
        purchaser.setCreated(resultSet.getTimestamp("purchaser_created"));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getErrorCode() + " " + ex.getLocalizedMessage());
    }
    return purchaser;
  }

  @Override
  public Purchaser editUser(Purchaser user) throws Exception {
    System.out.println("Editing purchaser - ");
    System.out.println(new Gson().toJson(user));
    return user;
  }

  @Override
  public boolean deleteUser(String id) {
    return false;
  }

  @Override
  public boolean userExist(String id) {
    return false;
  }

  @Override
  public boolean buyProduct(Purchaser purchaser, Product product) {
    boolean status = false;
    try {
      PreparedStatement preparedStatement =
          psql.getConnection()
              .prepareStatement("insert into transactions(purchaser_id,product_id) values(?,?)");
      preparedStatement.setInt(1, Integer.parseInt(purchaser.getId()));
      preparedStatement.setInt(2, Integer.parseInt(product.getId()));
      int response = preparedStatement.executeUpdate();
      if (response > 0) {
        status = true;
      }
    } catch (SQLException ex) {
      System.out.println(ex.getErrorCode() + " " + ex.getLocalizedMessage());
    }
    return status;
  }

  @Override
  public JsonElement getPurchaseHistory(Purchaser purchaser, Map<String, String[]> filters) {
    JsonObject jsonObject = new JsonObject();
    try {
      PreparedStatement preparedStatement =
          psql.getConnection()
              .prepareStatement(
                  "select product_name, txn_created from transactions inner join products on products.product_id = transactions.product_id where purchaser_id = ? and txn_created between ? and ? ");

      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

      preparedStatement.setInt(1, Integer.parseInt(purchaser.getId()));
      preparedStatement.setTimestamp(
          2, new Timestamp(dateFormat.parse(filters.get("start_date")[0]).getTime()));
      preparedStatement.setTimestamp(
          3, new Timestamp(dateFormat.parse(filters.get("end_date")[0]).getTime()));

      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        String key = resultSet.getString("txn_created").split(" ")[0];
        JsonElement value = jsonObject.get(key);
        if (value == null) {
          JsonObject v = new JsonObject();
          v.addProperty("product", resultSet.getString("product_name"));
          jsonObject.add(key, v);
        } else {
          JsonObject v = new JsonObject();
          v.addProperty("product", resultSet.getString("product_name"));

          JsonArray array = new JsonArray();
          array.add(value.getAsJsonObject());
          array.add(v);
          jsonObject.add(key, array);
        }
      }
    } catch (SQLException | ParseException ex) {
      System.out.println(ex.getLocalizedMessage());
    }
    return jsonObject;
  }
}
