package services;

import models.Purchaser;

import java.util.Collection;

public interface PurchaserService {
  public boolean addUser(Purchaser user);

  public Collection<Purchaser> getUsers();

  public Purchaser getUser(String id);

  public Purchaser editUser(Purchaser user) throws Exception;

  public boolean deleteUser(String id);

  public boolean userExist(String id);
}
