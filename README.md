# Entfernungsrechner für Fernverkehrsbahnhöfe

### Reference Documentation

Die API umfasst einen GET-Request:

```
GET /api/v1/distance/FF/BLS
```

Antwort:

```
{
"from": "Frankfurt(Main)Hbf",
"to": "Berlin Hbf",
"distance": 423,
"unit": "km"
}
```

Antwort bei GET-Request mit ungültigem Bahnhof

```
GET /api/v1/distance/BLS/KLN
```

Antwort:

```
{
    "timestamp": "2023-01-22T22:51:23.243+00:00",
    "status": 400,
    "error": "Bad Request",
    "message": "Haltestelle DS100='KLN' unbekannt oder kein Fernverkehrsbahnhof.",
    "path": "/api/v1/distance/BLS/KLN"
}
```