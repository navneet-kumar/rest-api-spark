package response;

import com.google.gson.JsonElement;

public class Response {

  private Status status;
  private String message;
  private JsonElement data;

  /* We can use Lombok to avoid below methods */

  public Response() {
    this.status = Status.SUCCESS;
  }

  public Response(Status status) {
    this.status = status;
  }

  public Response(Status status, String message) {
    this.status = status;
    this.message = message;
  }

  public Response(Status status, JsonElement data) {
    this.status = status;
    this.data = data;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public JsonElement getData() {
    return data;
  }

  public void setData(JsonElement data) {
    this.data = data;
  }
}
