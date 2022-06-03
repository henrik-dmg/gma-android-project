# Metronom Pro

[![Build Status](https://app.bitrise.io/app/ea70fa64fbda2537/status.svg?token=Hxyrd6Kvk22GcvXxibhSCg&branch=main)](https://app.bitrise.io/app/ea70fa64fbda2537)

Semesterprojekt von Bastian Unterbörsch (573228) und Henrik Panhans (573550) für das Modul "Grundlagen Mobiler Anwendungen"

## Grundidee

Wir bauen eine Metronom-App, die zusätzlich zur grundlegenden Takt-Vorgabe-Funktion noch die Möglichkeit bietet, komplexe Muster und Abfolgen von verschiedenen Takten und Tempi zu erstellen und zu speichern.
Diese Taktmuster können außerdem als QR-Code geteilt werden. Der Fokus liegt hierbei auf einer sauberen Trennung der Komponenten und möglischt präziser Zeit-Sychronisation des Metronom-Takts.

## Specs

### Views

Die App hat eine Navigation-Bar am unteren Bildschirmrand, über die die folgenden Views errreicht werden können.

#### Basic-Metronom

Diese View bietet einige Buttons zum Einstellen des Takts, Tempo und Betonung/Auslassung. Sobald der "Start"-Button gedrückt wird, spielt das Gerät einen sich wiederholenden Takt ab. Der Button wandelt sich daraufhin in einen Stop-Button um, der das Gegenteil bewirkt. Metronom halt.

#### Pro-Metronom

Das Pro-Metronom bietet die gleichen Funktionen wie das Basic-Metronom, mit dem Unterschied, dass hier nun mehrere, aufeinander folgende Takte und Beat-Muster definiert und angeordnet werden können.
Man kann diese Muster in der Reihenfolge verschieben, duplizieren, löschen oder neu hinzufügen. Es kann außerdem zu jedem Muster eine Wiederholungsanzahl angegeben und angepasst werden.
Über einen Button am oberen Bildschirmrand kann ein Menü angezeigt werden, über welches man entweder das aktuelle Beat-Muster als QR-Code teilen oder es abspeichern kann.

#### Beat-Library

Falls ein Beat-Muster vom Pro-Metronom aus abgespeichert wird, wird es daraufhin in der Beat-Library angezeigt. Von dort aus, kann man Muster antippen, die dann eine nicht-interaktive Vorschau anzeigen.
In dieser kann dann entweder auf "Abbrechen" oder "Laden" gedrückt werden, was zur Folge hat, dass das Pro-Metronom mit dem ausgewählten Muster überschrieben wird.

#### Mockups

![Mockup of Application](assets/Mockup.jpg "Mockup")

## Komponenten

Unsere App ist in die folgenden Komponenten unterteilt:

![Component Diagram](assets/packages.svg "Components")

### MetronomeKit

Hier lebt die Logik unserer App. Genauer gesagt liegt der gesamte Code, der nicht direkt mit UI zu tun, das heißt:

#### Audio Package

Das `Audio` Package stellt einige Interfaces bereit, die benötigt werden um mit Audio zu arbeiten und es zu steuern

- `AudioControllable` (interface)
- `AudioWriteable` (interface)
- `StatelessAudioControllable` (interface)

Da dieses Package keinerleit Implementation hat, wird dieses nicht getestet.

#### Beat Package

Im Package `Beat` ist die Logik und die Datenstrukturen für die Beatmuster zu finden. Dies beinhaltet simple `data class` Objekte, die reine Model-Objekte sind und keine Logik beinhalten (außer Validierung). Außerdem ist hier der `BeatManager` zu finden, welches das Singleton-Objekt ist, welches dafür zuständig ist, den nächsten Ton auszusuchen (betont, normal oder muted).

- `Beat` (data class)
- `BeatManager` (object class)
- `BeatManagerException` (exception, die von `BeatManager` geworfen wird, falls ein Beat-Muster fehlformartiert ist)
- `BeatPattern` (data class)
- `Tone` (enum)

In diesem Package wird vor Allem die doch eher komplexe Logik des `BeatManager` getestet. Bereits vorhandene Tests sind zB:

- simpler 4-Schlag Beat wird einmal wiederholt
- 4-Schlag Beat mit abwechselnd betont und nicht betonten Noten wird einmal wiederholt
- zwei unterschiedliche Beats werden jeweils einmal wiederholt
- simpler 3-Schlag Beat wird zwei Mal wiederholt
- 4-Schlag Beat wird unendlich lang wiederholt
- ungültiger Beat wird erkannt und Exception geworfen

Tests die für die `Beat` Klasse noch geschrieben werden:

- Verifizierung ist korrekt
  - Tempo ist > 0 und liegt im erlaubten Bereich
  - Notenanzahl is > 0 und liegt im erlaubten Bereich
  - Wiederholungen sind entweder `null` oder > 0
  - Betonte und gemutete Noten haben nur Indizes, die auch im Beat existieren können (anhand von Notenanzahl)
- `Tone`-Array wird korrekt ausgegeben
  - richtige Anzahl an Tönen wird zurückgegeben
  - betonte und gemutete Töne sind am richtigen Index zu finden

## Exploration

### Metronom

Im Laufe der bisherigen Entwicklung, gab es vor Allem eine große Frage. Wie kriegen wir es hin, dass das Metronom genau im Takt schlägt? Durch eine Google-Suche ergaben sich schnell 2 potentielle Möglichkeiten, die hier verglichen werden.

#### Thread.sleep

Diese Variante ist die deutlich simplere. Kurz gesagt, muss man den Abstand zwischen zwei Schlägen des Metronoms errechnen und dann `Thread.sleep(timeDelta)` aufrufen, um danach wieder zum Anfang der Schleife zu gelangen.
Der entscheidende Nachteil, der schlussendlich auch dazu geführt hat, dass wir uns gegen diese Methode entschieden haben, ist, dasss `Thread.sleep` nicht garantiert, dass der Thread nach _genau_ dem gewünschten Zeitinterval fortfährt. Das heißt, das potentiel der Beat ein paar Millisekunden länger ist als gewollt, was auf lange Sicht natürlich zu immer gröseren Ungenauigkeiten führt.

#### AudioTrack

Die Verwendung von `AudioTrack` ist deutlich komplexer als `Thread.sleep`. Allerdings bietet sie auch deutlich höhere Präzision, was für ein Metronom natürlich das ausschlaggebende Argument schlechthin ist.
Bei dieser Methode wird eine Instanz von `AudioTrack` konfiguriert und dann kann in diesen sozusagen "live" der Buffer des Lautsprechers gefüllt werden. `AudioTrack` bietet außerdem einen Streaming-Modus, wo die Method `audioTrack.write(byte[] samples)` erst dann returned, wenn die geschriebenen Samples abgespielt wurden.
Das führt dazu, dass wir die Zeitsynchronisation zwischen den Takten quasi umsonst bekommen.

### Persistenz

Bei der Frage wie wir die erstellten Beat-Muster speichern wollen, sind wir uns nach wie vor nicht 100% sicher. Unsere erste Idee war das Ganze einfach in JSON zu encoden und dann in eine Datei auf dem Gerät zu schreiben, und andersrum natürlich wieder zu laden. Dies würde allerdings einiges an manueller Arbeit mit ByteStreams usw. erfordern, was fehleranfällig ist.
Einfacher (und vermutlich auch deutlich sicherer) wäre es eine lokale NoSQL Datenbank wie zum Beispiel MongoDB aufzuziehen, was wir wahrscheinlich schlussendlich auch tun werden. Hier nimmt uns die Datenbank einiges an Arbeit, wie zum Beispiel `fetchAll`, `delete` und so weiter.

Die gespeicherten Beat-Muster könnten in JSON ungefähr wie folgt aussehen:

```jsonc
{
  "patternName": "Some Beat Pattern", // name of pattern
  "createdAt": "2022-05-08 15:26:01+1000", // date of creation
  "patterns": [
    {
      "tempo": 120, // range(10-300)
      "noteCount": 3, // range(1-12)
      "repetitions": 3, // range(1-10)
      "emphasisedNotes": [0, 1] // first and second note emphasised; optional
    },
    {
      "tempo": 140,
      "noteCount": 4,
      "repetitions": 2,
      "emphasisedNotes": [1, 3], // second and fourth note emphasised
      "mutedNotes": [2] // 3 note muted; optional
    }
  ]
}
```
