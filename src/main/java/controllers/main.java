package controllers;

import com.google.gson.Gson;
import models.Product;
import response.Response;
import response.Status;
import services.ProductService;
import services.implementations.FirebaseImplmentation;
import spark.utils.StringUtils;

import static spark.Spark.get;
import static spark.Spark.post;

public class main {
  public static void main(String[] args) {
    final ProductService productService = new FirebaseImplmentation();
    final Gson JSON = new Gson();
    get(
        "/welcome/:name",
        (req, res) -> {
          String user = req.params(":name");
          return "Hello " + (StringUtils.isEmpty(user) ? "User" : user);
        });

    /* Product related end points */
	  // Add a product
    post(
        "/product",
        (req, res) -> {
          Product product = new Gson().fromJson(req.body(), Product.class);
          boolean status = productService.addProduct(product);
          return JSON.toJson(new Response((status) ? Status.SUCCESS : Status.ERROR));
        });

	  // get all products
    get(
        "/products",
        (req, res) -> {
          res.type("application/json");
          return JSON.toJson(
              new Response(Status.SUCCESS, JSON.toJsonTree(productService.getProducts())));
        });

  }
}
