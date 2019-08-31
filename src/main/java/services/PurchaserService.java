package services;

import com.google.gson.JsonElement;
import models.Product;
import models.Purchaser;

import java.util.Collection;
import java.util.Map;

public interface PurchaserService {
  public boolean addUser(Purchaser user);

  public Collection<Purchaser> getUsers();

  public Purchaser getUser(String id);

  public Purchaser editUser(Purchaser user) throws Exception;

  public boolean deleteUser(String id);

  public boolean userExist(String id);

  public boolean buyProduct(Purchaser purchaser, Product product);

  public JsonElement getPurchaseHistory(Purchaser purchaser, Map<String, String[]> filters);
}
