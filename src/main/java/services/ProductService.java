package services;

import models.Product;

import java.util.Collection;

public interface ProductService {
  public boolean addProduct(Product product);

  public Collection<Product> getProducts();

  public Product getProduct(String id);

  public Product editProduct(Product user) throws ProductException;

  public void deleteProduct(String id);

  public boolean productExist(String id);
}
