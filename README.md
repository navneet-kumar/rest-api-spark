4 endpoints implemented: 
 
POST /purchaser 
- sample body: "{ "name": "Bob" }"   
POST /product 
- sample body: "{ "name": "Tomato" }" 
 
POST /purchaser-product (should create relationship between purchaser and product) 
- sample body: "{ "purchaser_id": 1,  "product_id": 5, 
"purchase_timestamp": 1566265701 }" 
 
GET /purchaser/{$purchaser_id}/product  ?start_date={$start_date}&end_date={$end_date}  (specifications for this on the next slide)
 
Specifications for the GET /purchaser/{$purchaser_id}/product?start_date={$start_date}&end_date={$end_date} endpoint  
 
Sample response: 
```JSON
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