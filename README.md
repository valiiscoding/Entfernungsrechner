# Entfernungsrechner für Fernverkehrsbahnhöfe

### Kurzdokumentation

Die API berechnet die Entfernung (in KM) zwischen zwei Fernverkehrsbahnhöfen in Deutschland anhand ihrer DS100-Kürzel.

Die Architektur des Projekts orientiert sich grob an einer Onion-Architektur (nicht sehr streng eingehalten). Ich habe
entschieden, die CSV-Dateien nicht periodisch und nicht online abzurufen, da die Dateien einen Zwischenstand darstellen
und höchstwahrscheinlich nicht aktualisiert werden unter selbigem Link. Zudem lade ich in die H2-Datenbank nur die
Fernverkehrsbahnhöfe für eine bessere Query-Performance, da es weniger Einträge gibt (Nachteil: ungenauere
Fehlermeldungen).

Die Tests und das Projekt nutzen eine Kopie der CSV-Datei
mit [Haltestellendaten](https://data.deutschebahn.com/dataset/data-haltestellen.html#) der DB Station&Service AG von
2020.

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

    * Fehlerhafter Request (Antwort bei GET-Request mit ungültigem DS-100 Code (z.B. 'KLN'))

    ```
    GET /api/v1/distance/BLS/KLN
    ```

  Antwort (kann sich unterscheiden, wenn von Browser aufgerufen):

  ```
  {
      "timestamp": "2023-01-25T14:38:19.054+00:00",
      "status": 400,
      "error": "Bad Request",
      "message": "Station DS100='KLN' unknown or no long-distance train station.",
      "path": "/api/v1/distance/BLS/KLN"
  }
  ```

    * GET-Request zu undefiniertem Endpunkt:

  ```
  GET /api/v1/distance/BLS
  ```

  Antwort (kann sich unterscheiden, wenn von Browser aufgerufen):

  ```
  {
      "timestamp": "2023-01-23T19:05:31.237+00:00",
      "status": 404,
      "error": "Not Found",
      "message": "No message available",
      "path": "/api/v1/distance/BLS"
  }
  ```
    