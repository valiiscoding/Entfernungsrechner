## Entfernungsrechner für Fernverkehrsbahnhöfe

### Kurzdokumentation

Die API berechnet die Entfernung (in Kilometern) zwischen zwei Fernverkehrsbahnhöfen in Deutschland anhand ihrer
DS100-Kürzel.

Die Architektur der API orientiert sich grob an einer Onion-Architektur (nicht sehr streng eingehalten).

Ich habe entschieden, die CSV-Dateien nicht periodisch und nicht online abzurufen, da die Dateien einen Zwischenstand
darstellen und höchstwahrscheinlich nicht aktualisiert werden unter selbigem Link. Zudem lade ich in die H2-Datenbank
nur die Fernverkehrsbahnhöfe für eine bessere Query-Performance, da es weniger Einträge gibt (Nachteil: ungenauere
Fehlermeldungen).

Die Tests arbeiten mit einer Kopie der CSV-Datei aus 2020. (Es wäre nicht sinnvoll die Tests an eine Datei zu koppeln,
die sich verändern kann.)

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

Antwort bei GET-Request mit ungültigem DS-100 Code (z.B. 'KLN')

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

Antwort bei GET-Request zu undefiniertem Endpunkt:

```
GET /api/v1/distance/BLS
```

Antwort:

```
{
"timestamp": "2023-01-23T19:05:31.237+00:00",
"status": 404,
"error": "Not Found",
"message": "No message available",
"path": "/api/v1/distance/BLS"
}
```
