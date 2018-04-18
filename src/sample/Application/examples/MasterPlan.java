package sample.Application.examples;

public class MasterPlan {
}
/*
Gem private keysene på en dat fil du gemmer i client mappen
Hent disse private keys når du skal dekryptere. Disse forbliver dermed den samme instans
af private keys ligegyldig om programmet er slukket eller ej.
https://stackoverflow.com/questions/2411096/how-to-recover-a-rsa-public-key-from-a-byte-array

I første omgang gør det samme med public keys, men public keys´ene vil ellers være konstante
over hvert program ligesom public keys´ene ved at være gemt i en database.

I morgen fortsætter jeg det her.
En anden kan se på hvordan man laver en query over vores database 


JournalMakerController skal have alle textfieldsene/variablerne som lægen skal indtaste
og disse skal hænge sammen med xlm filen. //KRISTOFFER
Variablerne skal sendes til serveren krypteret. Dette sker med en public key som hentes fra databasen efter CPR nummer.
(optional) en pdf laves med variablerne med det samme som lægen kan bruge til at se filen som et preview.

Variablerne modtages fra serveren. De dekrypteres med en software-baseret private key og danner en pdf fra variablerne.
(ide: download PDF skal default sættes til overførsler eller det skal være optional hvor den downloades fra GUI´en)
*/
