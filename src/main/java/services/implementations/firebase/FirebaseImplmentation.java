package services.implementations.firebase;

import com.google.gson.Gson;
import models.Product;
import services.ProductException;
import services.ProductService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FirebaseImplmentation implements ProductService {
  @Override
  public boolean addProduct(Product product) {
    System.out.println(new Gson().toJson(product));
    return true;
  }

  @Override
  public Collection<Product> getProducts() {
    List<Product> products = new ArrayList<>();

    return products;
  }

  @Override
  public Product getProduct(String id) {
    return null;
  }

  @Override
  public Product editProduct(Product user) throws ProductException {
    return null;
  }

  @Override
  public void deleteProduct(String id) {}

  @Override
  public boolean productExist(String id) {
    return false;
  }
}
