package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import models.Product;
import models.Purchaser;
import response.Response;
import response.Status;
import services.ProductService;
import services.PurchaserService;
import services.implementations.postgres.ProductServiceImpl;
import services.implementations.postgres.PurchaserServiceImpl;
import spark.utils.StringUtils;

import java.util.Map;

import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;

public class main {
  public static void main(String[] args) {

    final ProductService productService = new ProductServiceImpl();
    final PurchaserService purchaserService = new PurchaserServiceImpl();
    final Gson JSON = new GsonBuilder().setDateFormat("YYYY-MM-DD").create();

    get(
        "/welcome/:name",
        (req, res) -> {
          String user = req.params(":name");
          return "Hello " + (StringUtils.isEmpty(user) ? "User" : user);
        });

    /* Product related end points */
    path(
        "/product",
        () -> {
          // Add a product
          post(
              "",
              (req, res) -> {
                Product product = JSON.fromJson(req.body(), Product.class);
                boolean status = productService.addProduct(product);
                return JSON.toJson(new Response((status) ? Status.SUCCESS : Status.ERROR));
              });

          // get all products
          get(
              "",
              (req, res) -> {
                res.type("application/json");
                return JSON.toJson(
                    new Response(Status.SUCCESS, JSON.toJsonTree(productService.getProducts())));
              });
        });

    /* Users related end points */
    path(
        "/purchaser",
        () -> {
          get(
              "",
              (req, res) -> {
                res.type("application/json");
                return JSON.toJson(
                    new Response(Status.SUCCESS, JSON.toJsonTree(purchaserService.getUsers())));
              });
          post(
              "",
              (req, res) -> {
                Purchaser purchaser = JSON.fromJson(req.body(), Purchaser.class);
                boolean status = purchaserService.addUser(purchaser);
                return JSON.toJson(new Response((status) ? Status.SUCCESS : Status.ERROR));
              });
          post(
              "-product",
              (req, res) -> {
                JsonElement reqJson = new JsonParser().parse(req.body());
                Product product =
                    JSON.fromJson(reqJson.getAsJsonObject().get("product"), Product.class);
                Purchaser purchaser =
                    JSON.fromJson(reqJson.getAsJsonObject().get("purchaser"), Purchaser.class);
                return JSON.toJson(
                    new Response(
                        purchaserService.buyProduct(purchaser, product)
                            ? Status.SUCCESS
                            : Status.ERROR));
              });
          get(
              "/:purchaserId/product",
              (req, res) -> {
                res.type("application/json");
                String purchaserId = req.params(":purchaserId");
                Map<String, String[]> filters = req.queryMap().toMap();
                JsonElement response =
                    purchaserService.getPurchaseHistory(
                        purchaserService.getUser(purchaserId), filters);
                return JSON.toJson(new Response(Status.SUCCESS, response));
              });
        });

    exception(
        Exception.class,
        (e, req, res) -> {
          res.type("application/json");
          res.status(500);
          res.body(JSON.toJson(new Response(Status.ERROR, e.getLocalizedMessage())));
        });
  }
}
