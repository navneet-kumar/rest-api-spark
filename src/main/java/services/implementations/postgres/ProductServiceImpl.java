package services.implementations.postgres;

import com.google.gson.Gson;
import models.Product;
import services.ProductException;
import services.ProductService;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
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
    boolean status;
    try {
      PreparedStatement preparedStatement =
          psql.getConnection()
              .prepareStatement("insert into products(product_name,product_created) values(?,?)");
      preparedStatement.setString(1, product.getName());
      preparedStatement.setTime(2, product.getCreated());
      status = preparedStatement.execute();
    } catch (SQLException ex) {
      System.out.println(ex.getErrorCode() + " " + ex.getLocalizedMessage());
      status = false;
    }
    return status;
  }

  @Override
  public Collection<Product> getProducts() {
    List<Product> products = new ArrayList<>();

    Product p = new Product();
    p.setId("10001");
    p.setName("orange");
    p.setCreated(new Time(System.currentTimeMillis()));
    products.add(p);
    p = new Product();
    p.setId("10002");
    p.setName("Apple");
    p.setCreated(new Time(System.currentTimeMillis()));
    products.add(p);
    p = new Product();
    p.setId("10003");
    p.setName("Banana");
    p.setCreated(new Time(System.currentTimeMillis()));
    products.add(p);
    p = new Product();
    p.setId("10004");
    p.setName("pine apple");
    p.setCreated(new Time(System.currentTimeMillis()));
    products.add(p);

    return products;
  }

  @Override
  public Product getProduct(String id) {
    System.out.println("Fetching Product - " + id);
    return new Product();
  }

  @Override
  public Product editProduct(Product product) throws ProductException {
    System.out.println("Editing Product - " + product.getId());
    System.out.println(new Gson().toJson(product));
    return product;
  }

  @Override
  public void deleteProduct(String id) {}

  @Override
  public boolean productExist(String id) {
    return false;
  }
}
