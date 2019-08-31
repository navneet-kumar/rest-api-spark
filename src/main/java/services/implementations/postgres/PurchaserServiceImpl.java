package services.implementations.postgres;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import models.Product;
import models.Purchaser;
import services.PurchaserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PurchaserServiceImpl implements PurchaserService {

  @Override
  public boolean addUser(Purchaser user) {
    {
      System.out.println("Purchaser Added - ");
      System.out.println(new Gson().toJson(user));
      return true;
    }
  }

  @Override
  public Collection<Purchaser> getUsers() {
    List<Purchaser> purchasers = new ArrayList<>();

    Purchaser p = new Purchaser();
    p.setId("001");
    p.setName("user1");
    p.setCreated(new Date());
    purchasers.add(p);
    p = new Purchaser();
    p.setId("002");
    p.setName("user2");
    p.setCreated(new Date());
    purchasers.add(p);
    p = new Purchaser();
    p.setId("003");
    p.setName("user3");
    p.setCreated(new Date());
    purchasers.add(p);
    p = new Purchaser();
    p.setId("004");
    p.setName("user4");
    p.setCreated(new Date());
    purchasers.add(p);

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
    System.out.println("purchaser - ");
    System.out.println(new Gson().toJson(purchaser));
    System.out.println("bought - ");
    System.out.println(new Gson().toJson(product));
    return true;
  }

  @Override
  public JsonElement getPurchaseHistory(Purchaser purchaser, Map<String, String[]> filters) {
    return null;
  }
}
