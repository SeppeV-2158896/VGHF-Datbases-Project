# Database ontwerp voor VGHF
Het ontwerpen van een database voor het VGHF (Video Game Heritage Foundation) om de inventarisatie van alle spellen en hun locaties bij te houden, is een belangrijke stap om ervoor te zorgen dat de collectie efficiënt en effectief beheerd kan worden. Hieronder bespreken we de verschillende aspecten en argumenten die bijdragen aan het ontwerp van deze database.

## 1. Doel van de database:

De voornaamste focus van deze database is het bijhouden van de huidige locatie van alle spellen in de collectie van het VGHF. Dit stelt klanten in staat om gemakkelijk te achterhalen waar ze een specifiek spel kunnen bewonderen of uitlenen, en vrijwilligers kunnen snel toegang krijgen tot de locaties van de spellen die ze beheren. Dit dient meerdere doelen:

- Verbeterde toegankelijkheid voor klanten: Klanten kunnen via de database snel informatie vinden over de beschikbaarheid van specifieke spellen en hun locaties. Hierdoor kunnen klanten opzoeken waar ze bepaalde spellen kunnen bewonderen/uitlenen.

- Vrijwilligersbeheer: Vrijwilligers die verantwoordelijk zijn voor specifieke spellen of locaties kunnen gemakkelijk toegang krijgen tot relevante informatie en hun taken effectief uitvoeren.

- Uitleenregistratie: Het bijhouden van uitleengegevens, inclusief de status van eerdere uitleningen en boetes, maakt een georganiseerd uitleenproces mogelijk en helpt bij het handhaven van de regels voor uitleenbaarheid.

## 2. Datastructuur en relaties:


![image](https://github.com/SeppeV-2158896/databases-20232024/assets/125284904/80c7e28f-9ae4-49f9-a905-0cc3d632498f)


De database bevat verschillende tabellen met onderlinge relaties om de vereiste gegevens op een georganiseerde manier te bewaren.

- Games Tabel: Dit is het kernonderdeel van de database, waarin elk spel wordt geregistreerd. De tabel bevat informatie over de naam van het spel, de eigenaar, de huidige locatie en details zoals compatibele consoles, ontwikkelaars en genres. Omdat spellen meerdere consoles, ontwikkelaars en genres kunnen hebben en vice versa, worden veel-op-veel relaties met brugtabellen gebruikt om deze complexe relaties te beheren.

- Console Tabel: Deze tabel bevat informatie over de verschillende spelconsoles en is gebaseerd op een externe dataset om eenvoudig nieuwe consoles toe te voegen. Dit helpt bij het identificeren van de platforms waarop spellen kunnen worden gespeeld.

- Developing Company Tabel: Deze tabel bevat informatie over de ontwikkelaars van de spellen. Het biedt klanten de mogelijkheid om informatie over de producenten van de spellen op te zoeken.

- Genre Tabel: De genre tabel bevat een lijst met genres van spellen. Dit stelt klanten in staat om spellen te sorteren op basis van het genre dat ze interessant vinden.

- Locaties Tabel: Deze tabel registreert de verschillende locaties waar spellen kunnen worden bewaard of uitgeleend. Het bevat details zoals adres, eigenaar en het type locatie. Sommige locaties zijn openbaar toegankelijk, terwijl andere alleen beschikbaar zijn voor specifieke doeleinden, zoals uitlenen.

## 3. Gebruikersrollen en permissies:

Een belangrijk aspect van de database is het beheer van gebruikersrollen en permissies. Er zijn twee belangrijke gebruikersrollen:

- Klanten: Klanten hebben beperkte rechten en kunnen de database gebruiken om spellen op te zoeken en te lenen. Ze hebben geen toegang tot vrijwilligersfuncties.

- Vrijwilligers: Vrijwilligers hebben uitgebreidere rechten, waaronder het beheer van spellen en locaties. Ze kunnen spellen toewijzen aan locaties, uitleentransacties registreren en boetes berekenen.

Dit systeem maakt het ook mogelijk om in de toekomst extra gebruikersrollen toe te voegen, zoals beheerders, om de beheermogelijkheden verder uit te breiden.
Elke gebruiker kan ook inloggen op het platform en heeft daarvoor zijn e-mail nodig maar ook een wachtwoord, maar voor de nodige beveiliging zal dit wachtwoord gehashed worden voordat het wordt opgeslagen.

## 4. Uitleenregistratie:

Het bijhouden van uitleeninformatie is van groot belang voor het behoud van de collectie en het voorkomen van verlies of schade aan de spellen. Elke uitleentransactie wordt geregistreerd in de 'loan_receipts' tabel, waarbij elk ontvangstbewijs wordt gekoppeld aan het uitgeleende spel en de klant die het spel heeft geleend. Hierdoor kan de status van een spel en eventuele boetes worden gevolgd.

## 5. Boetebeheer:

Het systeem houdt ook bij of een spel te lang is uitgeleend, en dit kan leiden tot boetes voor de klant. De boetegegevens worden opgeslagen in de database en kunnen worden opgevraagd door vrijwilligers op basis van het klant-ID. Dit maakt het mogelijk om boetes effectief te innen en draagt bij aan de financiële integriteit van de organisatie.

## 6. Veel-op-veel-relaties en brugtabellen:

Om de complexe relaties tussen spellen, consoles, ontwikkelaars en genres te beheren, worden brugtabellen gebruikt. Deze brugtabellen stellen de database in staat om efficiënt en flexibel om te gaan met de vele-op-veel relaties tussen deze entiteiten. Hierdoor kunnen spellen met meerdere consoles en ontwikkelaars worden geregistreerd zonder de structuur van de database te compliceren.

## 7. Efficiëntie en schaalbaarheid:

Het ontwerp van de database is schaalbaar en efficiënt. Het maakt gebruik van brugtabellen om de veel-op-veel relaties te beheren en maakt het mogelijk om snel nieuwe consoles, ontwikkelaars en genres toe te voegen. Dit zorgt ervoor dat de database met de groeiende collectie van het VGHF kan meegroeien zonder grote aanpassingen aan de structuur.

In conclusie biedt dit databaseontwerp een solide basis voor het beheer van de spellencollectie van het VGHF. Het voorziet in de behoeften van klanten, vrijwilligers en organisatiebeheerders, en stelt hen in staat om de spellen efficiënt te beheren, te lenen en te volgen. Door een flexibele structuur te hanteren, is de database klaar voor toekomstige uitbreidingen en veranderingen in de behoeften van de organisatie.

## 8. Onderhoud en Toekomstige Uitbreidingen

De hoeveelheid aan tabellen en gedetailleerde omschrijving van elk component laat toe om verschillende functies later toe te voegen. Zo kan er na enige tijd, data analyse worden uitgevoerd. Om bijvoorbeeld de populariteit van een genre bij de leners te bekijken. Of de evolutie van gepresenteerde developing companies. 

## 9. Conclusie

Dit uitgebreide databaseontwerp voor de Video Game Heritage Foundation biedt niet alleen een oplossing voor het bijhouden van spellen en hun locaties, maar opent ook deuren naar gegevensanalyse, efficiënt klantenbeheer en gegevensintegriteit. Door te voorzien in de behoeften van klanten, vrijwilligers en organisatiebeheerders, zal deze database bijdragen aan het behoud en de toegankelijkheid van de spellencollectie van het VGHF voor toekomstige generaties.
