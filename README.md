# EnergySystem Descriere 

Rețeaua de curent electric a unei țări este un sistem complex, format din producători, distribuitori,
consumatori (casnici sau industriali) și instituții ale statului care reglementează și supraveghează 
buna funcționare a sistemului.

**Consumatori:
Doresc o sursă de energie; Au un buget inițial + venit lunar; Aleg contractul cu cea mai mică rată lunară; Plătesc rată stabilită la început pentru o perioadă stabilită la 
alegerea contractului.

**Distribuitori:
Distribuitorii vor reprezenta companiile responsabile cu oferirea surselor de energie. Fiecare astfel de companie va fi vizitata în fiecare luna de consumatori dornici sa afle
ce oferta pot obține. Un distribuitor poate schimba caracteristicile noilor contracte (costurile de întreținere și durata) în fiecare luna, noile valori regasindu-se in input.
Distribuitorii vor avea și ei un buget de început, la care lunar se va adăuga profitul obtinut. Întrucât aceștia au de achitat preturi atat pentru infrastructură cât și pentru
producție, formula prețului contractului diferă în funcție de numărul de clienți.

**Distribuitori:
Interacțiunea cu producătorii: Distribuitorii au nevoie de o cantitate de energie lunară. Aceasta este constantă și dată ca input.
În prima rundă distribuitorii își aleg unul sau mai mulți producători care să le ofere cantitatea de energie necesară alegerea se face pe baza unor strategii -
fiecare distribuitor are o strategie primită ca input.
Dacă a apărut o schimbare a cantității de energie oferită de un producător, atunci la începutul următoarei luni, distribuitorii care iau energie de la acel producător își
vor aplica din nou strategia de alegere producători. Schimbări lunare date de simulare: costul pentru infrastructură.

**Producători:
Proprietățile unui producător: tip de energie produsă, care poate fi regenerabilă (wind, solar, hydro) sau nu (coal, nuclear); tipurile sunt definite într-un enum 
EnergyType dat în schelet; preț pe KWh; cantitatea lunară de energie oferită fiecărui distribuitor - oferă aceeași cantitate de energie fiecăruia; numărul maxim de distribuitori 
către care poate oferi energie. Schimbări lunare date de simulare: energia oferită fiecărui distribuitor.
Strategii de alegere producători->
Green Strategy - un distribuitor își alege producătorii prioritizându-i pe cei cu renewable energy întâi, apoi după preț, apoi după cantitate; 
Price Strategy - un distribuitor își alege producătorii prioritizând doar după preț, apoi după cantitate; 
Quantity Strategy - un distribuitor își alege producătorii prioritizând după cantitatea de energie oferită per distribuitor.


*****Simularea este bazată pe runde reprezentate de luni. La finalul fiecărei luni, toți consumatorii înregistrați trebuie să aibă o sursa de electricitate, altfel se consideră că aceștia nu își pot permite și sunt dați afara din joc. La fel se întâmplă și cu distribuitorii ce rămân fără bani, cu precizarea ca în momentul falimentarii, toți consumatorii ce au contract cu acesta, vor trebui să își formeze alt contract incepand cu luna următoare.
La începutul fiecărei luni distribuitorii vor stabili noile prețuri, urmând ca după ce aceștia termină, să vina rândul consumatorilor, care vor alege un contract (dacă nu au deja unul), urmând ca la finalul lunii să plătească rata curentă. Simularea începe cu o runda inițială, unde sunt folosite datele primite la început, apoi sunt rulate numberOfTurns luni, care se folosesc de noile preturi primite la începutul fiecărei luni. Astfel, simularea se termină când au fost rulate numberOfTurns + 1 runde și se afișează starea curentă a simulării. În cazul în care toți distribuitorii dau faliment, jocul se va încheia.*****


