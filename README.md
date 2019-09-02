**REST API** <br>
 
 1.1 Add a user <br>
```
POST http://127.0.0.1:4567/purchaser
{ "name": "Bob" }
```

 1.2 Get all users <br>
```
GET http://127.0.0.1:4567/purchaser
```

2.1 Add a product <br>
```
POST http://127.0.0.1:4567/product
{ "name": "Tomato" }
```
2.2 Get all products <br>
```
GET http://127.0.0.1:4567/product
{ "name": "Tomato" }
```

2.3 Purchase a product (creates relationship between purchaser and product) 
```
POST http://127.0.0.1:4567/purchaser-product 
{ "purchaser_id": 1,"product_id": 5,"purchase_timestamp": 1566265701 }
```

2.4  Purchase history
```
GET http://127.0.0.1:4567/purchaser/{$purchaser_id}/product?start_date={$start_date}&end_date={$end_date} 
{
  "purchases": {
    "2019-05-10": [
      {
        "product": "Birthday cake"
      },
      {
        "product": "Trumpet"
      }
    ],
    "2019-04-01": [
      {
        "product": "Tomato"
      }
    ],
    "2019-03-21": [
      {
        "product": "Trumpet"
      },
      {
        "product": "Diamond"
      }
    ]
  }
}
```
