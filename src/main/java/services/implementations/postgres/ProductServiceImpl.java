package services.implementations.postgres;

import com.google.gson.Gson;
import models.Product;
import services.ProductException;
import services.ProductService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private PostgresSQLConnection psql;

    public ProductServiceImpl() {
        psql = PostgresSQLConnection.getInstance();
    }

    @Override
    public boolean addProduct(Product product) {
        boolean status = false;
        try {
            PreparedStatement preparedStatement =
                    psql.getConnection()
                            .prepareStatement("insert into products(product_name,product_created) values(?,?)");
            preparedStatement.setString(1, product.getName());
            preparedStatement.setTimestamp(2, product.getCreated());
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
    public Collection<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement preparedStatement =
                    psql.getConnection()
                            .prepareStatement("select product_id,product_name,product_created from products");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getString("product_id"));
                product.setName(resultSet.getString("product_name"));
                product.setCreated(resultSet.getTimestamp("product_created"));
                products.add(product);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getErrorCode() + " " + ex.getLocalizedMessage());
        }
        return products;
    }

    @Override
    public Product getProduct(String id) {
        Product product = null;
        try {
            PreparedStatement preparedStatement =
                    psql.getConnection()
                            .prepareStatement("select product_id,product_name,product_created from products where product_id = ?");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                product = new Product();
                product.setId(resultSet.getString("product_id"));
                product.setName(resultSet.getString("product_name"));
                product.setCreated(resultSet.getTimestamp("product_created"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getErrorCode() + " " + ex.getLocalizedMessage());
        }
        return product;
    }

    @Override
    public Product editProduct(Product product) throws ProductException {
        System.out.println("Editing Product - " + product.getId());
        System.out.println(new Gson().toJson(product));
        return product;
    }

    @Override
    public void deleteProduct(String id) {
    }

    @Override
    public boolean productExist(String id) {
        return false;
    }
}
