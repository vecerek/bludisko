bludisko_bazdmeg
================
Projekt do IJA.

Zadání: Navrhněte a implementujte aplikaci Bludiště. 

Specifikace bludiště:

- bludiště je rozděleno na políčka
- každé políčko má stejný rozměr
- velikost bludiště se udává v počtu políček ve formátu RxC, kde R je počet řádků a C je počet políček v řádku (např.     30x25 znamená 30 řádků a 25 sloupců)
- minimální rozměr je 20x20, maximální rozměr 50x50
- v bludišti jsou umístěny objekty, každý objekt je vždy umístěn na jedno políčko; objekty se dělí do kategorií:
  - stavební objekty bludiště
  - hráč
  - hlídač

- v bludišti jsou tyto základní typy stavebních objektů:
  zeď - hráč nemůže projít
  brána - brána může být otevřená nebo zavřená; hráč může projít otevřenou bránou; zavřenou bránu může hráč otevřít       klíčem, po použití klíč mizí a brána zůstává otevřená
  klíč - hráč může zvednout a schovat, při pokusu o průchod zavřenou bránou tuto bránu otevírá
  cíl - je pouze jeden v bludišti; hráč musí dosáhnout cílového políčka

Hráč:

- objekt pohybující se po bludišti; při startu je umístěn na některé z volných polí (můžete vyzkoušet různé strategie     umisťování hráčů)
- je ovládán uživatelem, pohyb je řízen textovými příkazy:
  step - hráč udělá jeden krok dopředu (pokud je to možné); příkaz go je pak tvořen sekvencí příkazů step
  go - hráč jde rovně dokud nenarazí na jiný objekt (brána, zeď, klíč, jiný hráč)
  stop - zastaví pohyb hráče (pokud již stojí, neprovede nic)
  left - hráč se otočí doleva
  right - hráč se otočí doprava
  take - hráč vezme klíč (pokud je na políčku před ním)
  open - hráč otevře bránu klíčem (pokud stojí před bránou a má klíč)
  keys - vypíše počet klíčů aktuálně vlastněných hráčem

Hlídač:

- objekt, který se samostatně pohybuje po bludišti
- pohyb je generován náhodně, hlídač se může na několik okamžiků zastavit
- pokud hlídač narazí na hráče, zabije ho
- hlídačů může být víc

Specifikace požadavků:

- aplikace je typu klient - server

Klient:

- implementuje rozhraní k hernímu serveru, umožňuje zadávat příkazy a zobrazuje aktuální stav hry
- jeden klient umožňuje pracovat s jedním uživatelem (hráčem)
- klient obsahuje grafické uživatelské rozhraní (GUI)
- GUI obsahuje zejména menu, hrací desku a prostor pro zápis příkazů a zobrazení informací
- graficky musí být jasně odlišitelná pole, stavební objekty, hráči a hlídači

Server:

- obsahuje úložiště bludišť; bludiště je uloženo v souboru v textové podobě a je identifikováno svým názvem
- obsahuje herní logiku, zpracovává události od klientů a rozesílá změny stavu hry připojeným klientům

Hra:

- klient se připojuje na server, kde jsou uložena bludiště
- server nabízí seznam uložených bludišť a seznam rozehraných her
- klient buď zakládá novou hru (výběrem bludiště) nebo se může připojit k již rozehrané hře
- autentizaci a autorizaci uživatelů není třeba řešit - při prvním kontaktu klienta se serverem se mu přidělí unikátní - - název/kód/číslo, kterým se poté klient identifikuje
- klient vždy ovládá pouze svého hráče, stav bludiště a ostatních hráčů se mu pouze zobrazuje
- při vytvoření hry nebo připojení k existující hře se hráči přidělí unikátní barva (v rámci hry) a umístí se na volné    pole (můžete vyzkoušet různé strategie umisťování hráčů)
- počet hráčů v jedné hře je omezen na 4
- souvislý pohyb hráče (po příkazu go) je omezen časovou prodlevou mezi kroky
- při každém kroku obsadí volné políčko před sebou a vyčká na uplynutí prodlevy
- prodlevu je možné nastavovat v rozmezí 0,5 s až 5 s, nastavuje vždy zakladatel hry
- stejná prodleva se uplatní i při souvislém pohybu hlídačů
- aplikace poskytuje zpětnou vazbu (v podobě textové informace) o sledovaných událostech; sledovaná událost je
- výsledek požadované operace (provedena/nelze provést); týká se pouze vlastního hráče
- kolize hráče s jiným objektem s následným zabitím hráče (týká se všech)
- hra končí dosažením cíle jedním z hráčů nebo zabitím všech hráčů
- po ukončení hry zobrazuje klient stav hry
- najetím myši na hráče je možné zobrazit informace o počtu kroků hráče (kolika políčky hráč prošel), času stráveném ve   hře a celkovém času hry
- informace s průběhem hry (úspěšnost provedení příkazů a zabití hráčů) zůstávají zobrazeny
- můžete zvážit zabíjení hráčů navzájem (podobně jako hlídač)

Součást odevzdání:
připravte alespoň tři bludiště uložená na straně serveru

Minimum pro získání zápočtu:
návrh a implementace všech základních požadavků

Doporučení:
zamyslete se nad použitím vhodných návrhových vzorů
