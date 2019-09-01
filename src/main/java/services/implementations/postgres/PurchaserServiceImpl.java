package services.implementations.postgres;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import models.Product;
import models.Purchaser;
import services.PurchaserService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        return null;
    }
}
