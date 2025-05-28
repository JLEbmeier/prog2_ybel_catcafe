# Analyse der Binärbaum-Modellierung

## Funktionsweise der Modellierung
- **Struktur**:
    - `Tree<T>` ist ein Interface für einen sortierten Binärsuchbaum, parametrisiert mit `T extends Comparable<T>`.
    - `Empty<T>` repräsentiert einen leeren Knoten (Blatt).
    - `Node<T>` repräsentiert einen Knoten mit Daten (`data`), linkem (`leftChild`) und rechtem (`rightChild`) Teilbaum.
- **Sortierung**: Der Baum ist nach dem `Comparable`-Vergleich sortiert. Für `FelineOverLord` wird nach Gewicht (`weight`) sortiert (`compareTo` vergleicht `weight`).
- **Operationen**:
    - `addData(T data)`: Fügt ein neues Element ein, indem es rekursiv den richtigen Platz im Baum findet. Bei gleichem Gewicht wird das Element ignoriert.
    - `isEmpty()`: Gibt `true` für `Empty`, `false` für `Node` zurück.
    - `accept(TreeVisitor)`: Ermöglicht das Visitor-Pattern für flexible Traversierungen.

## Vorteile der Modellierung
1. **Immutabilität**: `Empty` und `Node` sind Records, was Thread-Sicherheit und Vorhersagbarkeit gewährleistet.
2. **Typensicherheit**: Der parametrische Typ `T extends Comparable<T>` sorgt für Compile-Time-Sicherheit.
3. **Iterable/Stream-Unterstützung**: Ermöglicht moderne Java-Features wie Schleifen und Streams.
4. **Flexibilität**: Das Visitor-Pattern erlaubt verschiedene Traversierungsarten ohne Änderung der Baumstruktur.
5. **Sauberes Design**: Die Trennung in `Empty` und `Node` erleichtert rekursive Algorithmen.

## Nachteile der Modellierung
1. **Speicherverbrauch**: Neue Knoten werden bei jeder Änderung (`addData`) erstellt, was speicherintensiv ist.
2. **Keine Balancierung**: Der Baum ist nicht selbstbalancierend, was zu O(n) Worst-Case-Zeitkomplexität führen kann.
3. **Leere Knoten**: `Empty`-Objekte verbrauchen Speicher, im Gegensatz zu `null`-Referenzen.
4. **Ineffiziente Namenssuche**: Da der Baum nach Gewicht sortiert ist, ist `getCatByName` immer O(n).
5. **Vergleichsproblem**: `FelineOverLord#compareTo` (`weight - o.weight`) kann bei großen Gewichtsunterschieden Überläufe verursachen.

## Schleifen und Streams
**Was musste getan werden?**
- `Tree<T>` erweitert `Iterable<T>` und definiert `iterator()`.
- `Empty` und `Node` implementieren `iterator()`, indem sie einen `TreeIterator<T>` zurückgeben.
- `TreeIterator` traversiert den Baum in Inorder-Reihenfolge (links → Wurzel → rechts) mit einem Stack.
- `stream()` in `Tree` nutzt `StreamSupport.stream(spliterator(), false)`, wobei `spliterator()` einen `Spliterator` aus dem `TreeIterator` erstellt.
- `Empty` und `Node` implementieren `spliterator()` und `forEach`, um Schleifen und Streams zu unterstützen.

## Funktionsweise des TreeIterator
- **Konstruktor**: Initialisiert einen Stack und ruft `pushAllLeftNodes(root)` auf, um alle linken Knoten des Baums auf den Stack zu legen.
- **pushAllLeftNodes**: Traversiert rekursiv den linken Teilbaum und schiebt nicht-leere Knoten auf den Stack.
- **hasNext**: Prüft, ob der Stack leer ist.
- **next**: Entfernt den obersten Knoten, gibt dessen Daten zurück und fügt den rechten Teilbaum hinzu.
- **Reihenfolge**: Inorder (links → Wurzel → rechts), was für `FelineOverLord` eine Ausgabe in aufsteigender Gewichtsreihenfolge bedeutet (z. B. Little Tapsy (1), Miss Chief Sooky (2), Gwenapurr Esmeralda (3), Morticia (3), Fitzby Darnsworth (5)).
