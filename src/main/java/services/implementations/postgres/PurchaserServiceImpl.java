package services.implementations.postgres;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.Product;
import models.Purchaser;
import org.postgresql.jdbc.TimestampUtils;
import services.PurchaserService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
                                .prepareStatement("insert into purchaser(purchaser_name,purchaser_created) values(?,?)");
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
                            .prepareStatement("select purchaser_id,purchaser_name,purchaser_created from purchaser");
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
        System.out.println("Getting purchaser - " + id);
        return new Purchaser();
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
        JsonObject object = new JsonObject();
        try {
            PreparedStatement preparedStatement =
                    psql.getConnection()
                            .prepareStatement(
                                    "select product_name, txn_created from transactions inner join products on products.product_id = transactions.product_id where txn_created between ? and ? ");
//TODO get timestamp from date
            LocalDate date = LocalDate.parse(filters.get("start_date")[0],DateTimeFormatter.ofPattern("yyyy-m-d"));
            Instant instant = Instant.parse(date.toString());
            System.out.printf(Timestamp.from(instant).toString());
            preparedStatement.setTimestamp(1, Timestamp.valueOf(filters.get("start_date")[0]));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(filters.get("end_date")[0]));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                JsonObject obj = new JsonObject();
                obj.add("product", new JsonParser().parse(resultSet.getString("product_name")));
                object.add(resultSet.getString("txn_created"), obj);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getErrorCode() + " " + ex.getLocalizedMessage());
        }
        return object;
    }
}
