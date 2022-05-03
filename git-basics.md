# Git Basics

Git ist ein Versioning-System, welches Änderungen in textbasierten Dateien tracken kann. Beispiel: Ich füge meine Lebenslauf als `.txt` hinzu, lasse ihn von Git tracken. Dann mache ich in 2 Wochen eine Änderung und möchte zu vorher vergleichen. Dabei fällt mir auf das es vorher doch besser war, ich hab aber die Datei schon abgespeichert - ja doof. Oder? Nein, denn hier liegt die Stärke von Git, man kann einfach Dateien auf den Stand von jeglichem "commit" zurücksetzen der diese Datei beinhaltet hat.

## Begriffserklärungen

`commit`: Sozusagen eine Speicherung. Man nimmt Änderungen vor, wählt aus was davon wirklich gespeichert werden soll und gibt dann noch eine kurze "Commit-Message" an um zu erklären was man geändert hat
`remote`: Der Server auf dem die Git Repository gespeichert ist und von den alle ihren Code zusammenschmeißen, in unserem Fall Github.
`branch`: Ein "Zweig" (no shit sherlock) der Repository. Man stelle sich den Code wie einen Baum vor, der `master` Branch ist der Stamm in der Mitte, der immer weiter nach oben wächst, und alle anderen Branches sind Zweige die mehr oder weniger weit sich nach außen bewegen und irgendwann, wenn auf ihnen nicht mehr aktiv progammiert wird, werden diese auch mal abgesägt - sprich gelöscht
`merge`: Eins der wichtigsten Features von Git, bei einem Merge werden die Dateien von zwei Branches "zusammengeworfen" und nur die Änderungen gespeichert. Wenn auf beiden Branches in der gleichen Datei eine Änderung vorgenommen wurde, dann kommt es zu einem Merge Conflict - FUN! Dann muss man durch alle Dateien manuell durchgehen in denen es Konflikte gibt und selbst auswählen welche Änderung übernommen werden soll.

Hier bitte mehr Erklärungen einfügen

## Repositories

Anfangs brauchen wir ja erstma eine von Git getrackte Repo. Macht Sinn oder? Also ab in den Ordner unserer Wahl und `git init` ausführen. Boom, schon hast du eine Repo. Wenn du schon Code auf irgendeinem Server liegen hast, ist der nächste logische Schritt die Remote zu verbinden.

```bash
git remote add origin https://remoteLink.git
```

Danach könnt ihr den `master` Branch pullen:

```bash
git pull origin master
```

## Branches und so

Wenn man an einem neuen Feature/Aufgabe/whatever anfängt zu arbeiten, will man ja logischerweise das auf dem neuesten Code basierend tuen. Also erstmal auf den `master` branch.

```bash
git checkout master
```

Danach die neuesten Änderungen "pullen".

```bash
git pull origin master
```

Hierbei ist `origin` der Name des Remote-Servers, in den meisten Fällen eben `origin` genannt. `master` ist der Branch den man pulled.
Danach erstellt man einen neuen Branch und gibt ihm einen Namen.

```bash
git branch deinBranchName
```

Um einen Branch zu erstellen und direkt darauf zu wechseln:

```bash
git checkout -b deinBranchName
```

Dann programmiert man fröhlich vor sich und macht sein Ding und commited in der Weltgeschichte herum. Hold up, wie geht ein Commit, Henrik? Exzellente Frage.

## Commiting

Wenn man nun also auf seinem Branch ist und ganz toll programmiert hat und seinen Bug gefixed hat oder so, will man den natürlich allen zur Verfügung stellen. Also commiten wir doch mal. Um eine Liste von veränderten Dateien zu sehen, führe `git status` aus. Der Output könnte in etwas so aussehen:

```
On branch master
Your branch is up to date with 'origin/master'.

Changes to be committed:
  (use "git reset HEAD <file>..." to unstage)

        modified:   "\303\234bung 3/Struktogram.java"

Untracked files:
  (use "git add <file>..." to include in what will be committed)

        README.md
```

Coolio. Wir haben also eine neue Datei `README.md` die wir tracken möchten. Also fügen wir diese zur Git Versioncontrol hinzu:

```bash
git add README.md
```

Wenn man alle Dateien hinzugefügt hat von denen man die Änderungen tracken möchte, ist man für den Commit bereit (Oh Gott Commitment-Ängste). Jetzt noch schnell eine superkreative Nachricht einfallen lassen und dann ab dafür:

```bash
git commit -m "It works, don't touch it"
```

Super, jetzt haben wir also unsere Änderungen in Git verewigt. Wie kriegen unsere anderen Coder nun aber diesen Code? Erstmal sollten wir den Branch pushen:

```bash
git push origin deinBranchName
```

Der nun folgende Part ist ein wenig tricky, aber sehr wichtig um effizient zusammenzuarbeiten. Solange du auf deinem eigenen Branch bist, kannste ja machen was du willst, du machst ja niemand Anderem die Arbeit kaputt. Aber `master` ist wo alle coolen Kids abhängen (also alle) und deswegen muss dieser mit Samthandschuhen angefasst werden.
Der von Github selbst empfohlene Flow um einen Branch in `master` zu mergen, ist wie folgt:

```bash
git checkout master
git pull origin master
git checkout deinBranchName
git merge master --no-ff
git checkout master
git merge deinBranchName --squash
```

Was tuen wir hier? Die ersten 3 Schritte sollten klar sein. Zeile 4 ist da wo es interessant wird. Wir mergen `master` in unseren eigenen Branch, hä? Der Grund hierfür ist dass wir dadurch eventuelle Merge Konflikte schon selber lokal beheben können und nicht aus versehen `master` für alle kaputt machen. Wenn also alles paletti ist, gehts zurück auf den `master` und dann mergen wir unseren eigenen Branch, hierbei benutzen wir die `--squash` flag um alle commits die wir auf unserem Branch gemacht haben in einen zu stopfen, damit im "Logbuch" von `master` nicht 300.000 Commits von nur einem Branch sind. Und damit sind wir fertig, jetzt können wir noch unseren Branch lokal und auf dem Server löschen:

```bash
// lokal
git branch -D deinBranchName
// remote
git push origin --delete deinBranchName
```

Happy coding!
