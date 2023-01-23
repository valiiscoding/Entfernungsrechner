# Entfernungsrechner für Fernverkehrsbahnhöfe

### Reference Documentation

Die Web-Anwendung ist größtenteils in der Onion-Architektur implementiert. Natürlich ist dies für eine so kleine
Web-Anwendung noch nicht notwendig, verbessert aber z.B. Wartbarkeit und Erweiterbarkeit.

Die API umfasst einen GET-Request, bei dem zwei Fernverkehrsbahnhöfe mit DS100 Kürzel angegeben werden müssen:

### Endpunkte

Berechnen der Entfernung zwischen zwei Fernverkehrsbahnhöfen:

* URL: `/api/v1/distance/{from}/{to}`
* Methode: GET
* Parameter:
    * `from`: DS100-Kürzel eines FV-Bahnhofs (erforderlich)
    * `to`: DS100-Kürzel eines FV-Bahnhofs (erforderlich)

* Rückgabewerte:
    * Erfolgreicher Request:

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

* Fehlerhafter Request

Antwort bei GET-Request mit ungültigem Bahnhof ('KLN')

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