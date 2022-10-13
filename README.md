
# Reifenhotel

Dieses Programm dient der Verwaltung aller eingelagerten Kundenräder.

Die Idee dieses Programm zu entwickeln bestand daraus, dass wir bisher eine Excel Tabelle benutzt haben,
um die Kundenräder zu verwalten. Über die Jahre wurde die Liste immer unübersichtlicher und auch Perfomancetechnisch
langsamer.

In dem Screenshot ein Einblick auf die alte Exceltabelle:
![App Screenshot](https://github.com/DennisSchwefelbauer/Reifenhotel/blob/main/README/Reifenhotel.png)

Daher habe ich angefangen ein Programm auf Basis einer SQLite Datenbank zu entwickeln. Das Programm ist akutell schon im Einsatz.
Es ist noch nicht vollständig fertig und wird nach wie vor weiterentwickelt.


## Zur Benutzung
* Beim ersten Gebrauch sollte unter "Bearbeiten -> Mechaniker bearbeiten" ein Benutzer hinzugefügt werden.

* Unter "Bearbeiten -> Schäden bearbeiten" können nach Bedarf Reifenschäden hinzugefügt werden, die bei der Einlagerung hinterlegt werden.

* Mit einem Doppelklick auf den jeweiligen Eintrag in der Tabelle kommt man eine Stufe weiter  
    "Kunde -> hinterlegte Fahrzeuge -> für das Fahrzeug hinterlegte Einlagerungen". 

    Ausserdem wird jeweils der Bereich für eine Neuanlage und das löschen des Datensatz aktiviert.
* Mit einem Rechsklick auf den jeweiligen Datensatz in der Tabelle wird dieser geöffnet und kann bearbeitet werden.

* Bei der Druckfunktion wird erst ein Fenster mit dem Einlagerungsprotokoll generiert. Wenn das Fenster geschlossen wird, werden die Printerjobs aktiviert.
    
    Der erste Job ist das Protokoll in zweifacher Ausführung (einmal für den Kunden und einmal für den Backupordner). 
    
    Bei einem eingetragenen Schaden wird noch eine dritte Ausfertigung für den Lageristen gedruckt, um zur nächsten Saison einen Kostenvoranschlag zu erstellen.

    Der zweite Printjob ist das Label, das zweimal gefaltet und an den Reifensatz angebracht wird.

* Die Exportfunktion ist noch nicht fertig und quasi noch ohne Funktion.

* Bei der Suchfunktion ist eine Suchbedingung auszuwählen

* Die Funktion "Datei -> Filtere nach Profilteiefe" ist für unseren Lageristen, um noch einmal alle Radsätze nach zu geringer Profiltiefe zu filtern und dafür Kostenvoranschläge zu erstellen.

