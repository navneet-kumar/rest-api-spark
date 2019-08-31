package models;

import java.sql.Time;

public class Product {
  private String id;
  private String name;
  private Time created;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Time getCreated() {
    return created;
  }

  public void setCreated(Time created) {
    this.created = created;
  }
}
