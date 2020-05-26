# e9s-s6r (enwikibooks-seacher)

[![Build Status](https://travis-ci.com/hvvka/wielki-sniffer.svg?token=AtJu5RATvaNahLGCYye5&branch=master)](https://travis-ci.com/hvvka/wielki-sniffer)

## Run

```bash
$ docker-compose up -d
```

_Note: will be faster if [enwikibooks-20200301-pages-articles.xml](https://ftp.acc.umu.se/mirror/wikimedia.org/dumps/enwikibooks/20200301/enwikibooks-20200301-pages-articles.xml.bz2) file 
is present in [datasource](./datasource) directory_ 

## Check ELK

Perform HTTP Request (check your elasticsearch IP!)
```
POST http://192.168.99.106:9200/enwikibooks/_search?pretty
Content-Type: application/json

{"query":{"bool":{"filter":[{"bool":{"filter":[{"bool":{"minimum_should_match":1,"should":[{"match_phrase":{"title":"Cover"}}]}},{"bool":{"minimum_should_match":1,"should":[{"match_phrase":{"categories":"Organic Chemistry"}}]}}]}},{"range":{"@timestamp":{"format":"strict_date_optional_time","gte":"2005-04-29T15:38:52.839Z","lte":"2020-04-29T15:38:52.839Z"}}}],"must":[],"must_not":[],"should":[]}}}
```

curl:
```bash
$ curl 'http://192.168.99.106:9200/enwikibooks/_search?pretty' -H 'Content-Type: application/json' --data '{"query":{"bool":{"filter":[{"bool":{"filter":[{"bool":{"minimum_should_match":1,"should":[{"match_phrase":{"title":"Cover"}}]}},{"bool":{"minimum_should_match":1,"should":[{"match_phrase":{"categories":"Organic Chemistry"}}]}}]}},{"range":{"@timestamp":{"format":"strict_date_optional_time","gte":"2005-04-29T15:38:52.839Z","lte":"2020-04-29T15:38:52.839Z"}}}],"must":[],"must_not":[],"should":[]}}}'
```

should return:
```
...
"hits": [{
      "_index": "enwikibooks",
      "_type": "_doc",
      "_id": "5",
      "_score": 1.0,
      "_source": {
        "first_image": "Ethane3D.png",
        "id": "5",
        "@timestamp": "2015-03-30T05:55:04.000Z",
        "categories": ["Organic Chemistry"],
        "text": [" Welcome to the world s foremost open content Organic Chemistry Textbook on the web!  <br> cellpadding \" 5 \"  <br> - <br> Organic Chemistry Go nbsp ; to nbsp ; contents nbsp ; 
...
```

## Frontend icons

```
    <v-icon>insert_emoticon</v-icon> : https://material.io/resources/icons
    <v-icon>mdi-account-lock</v-icon> : https://materialdesignicons.com/
```

## Swagger
Available at port `8081` eg. `http://localhost:8081`