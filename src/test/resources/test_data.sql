INSERT INTO haltestelle (EVA_NR, DS100, IFOPT, NAME, Verkehr, Laenge, Breite, Betreiber_Name, Betreiber_Nr, Status)
SELECT EVA_NR,
       DS100,
       IFOPT,
       NAME,
       Verkehr,
       REPLACE(Laenge, ',', '.'),
       REPLACE(Breite, ',', '.'),
       Betreiber_Name,
       Betreiber_Nr,
       Status
FROM CSVREAD('src/test/resources/Test_Bahnhoefe.csv', NULL, 'fieldSeparator=;')
where Verkehr = 'FV';
