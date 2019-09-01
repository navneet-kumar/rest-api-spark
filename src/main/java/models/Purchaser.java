package models;

import java.sql.Timestamp;

public class Purchaser {
    private String id;
    private String name;
    private Timestamp created;

    public Purchaser() {
        this.created = new Timestamp(System.currentTimeMillis());
    }


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

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
}
