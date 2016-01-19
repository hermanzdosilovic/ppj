# Scripts

U vršnom direktoriju projekta postoji nekoliko skripti koje se koriste za pokretanje i testiranje pojedinih rješenja/dijelova laboratorijskih vježbi kao i cjelovitog rješenja svih laboratorijskih vježbi - prevoditelj *ppjc*.

## 1. laboratorijska vježba

### `GLA`

* pokreće generator leksičkog analizatora
* generatoru leksičkog analizatora predaje se opis procesa leksičkog analizatora jezika *ppjC* iz datoteke `io/lab-3/ppjC.lan`
* parametar `--build` prevodi kôd generatora leksičkog analizatora prije pokretanja
* parametar `--build-only` prevodi kôd generatora leksičkog analizatora i __ne__ pokreće generator leksičkog analizatora
* stvara datoteku `analyzer_definition.ser` i razred `hr.fer.zemris.ppj.lab1.analyzer.AnalyzerAction`
* primjer pokretanja: `./GLA --build`

### `LA`

* pokreće leksički analizator
* leksički analizator na `stdin` očekuje programski kod jezika *ppjC*
* parametar `--build` prevodi kôd leksičkog analizatora prije pokretanja
* parametar `--build-only` prevodi kôd leksičkog analizatora i __ne__ pokreće leksički analizator
* na `stdout` ispisuje uniformne znakove
* primjer pokretanja: `./LA --build source.c`

## 2. laboratorijska vježba

### `GSA`

* pokreće generator sintaksnog analizatora
* generatoru sintaksnog analizatora predaje se opis procesa sintaksnog analizatora jezika *ppjC* iz datoteke `io/lab-3/ppjC.san`
* parametar `--build` prevodi kôd generator sintaksnog analizatora prije pokretanja
* parametar `--build-only` prevodi kôd generatora sintaksnog analizatora i __ne__ pokreće generator sintaksnog analizatora
* stvara datoteke: `action_table.ser`, `new_state_table.ser`, `syn_strings.ser` i `start_state.ser`
* primjer pokretanja: `./GSA --build-only`


### `SA`

* pokreće sintaksni analizator
* sintaksni analizator na `stdin` očekuje niz uniformnih znakova
* parametar `--build` prevodi kôd sintaksnog analizatora prije pokretanja
* parametar `--build-only` prevodi kôd sintaksnog analizatora i __ne__ pokreće sintaksni analizator
* na `stdout` ispisuje generativno stablo
* primjer pokretanja: `./SA < uniformni_znakovi.txt`

## 3. laboratorijska vježba

### `SEM`

* pokreće semantički analizator napisan za programski jezik *ppjC*
* semantički analizator na `stdin` očekuje generativno stablo
* parametar `--build` prevodi kôd semantičkog analizatora prije pokretanja
* parametar `--build-only` prevodi kôd semantičkog analizatora i __ne__ pokreće semantički analizator
* na `stdout` ispisuje produkciju u kojoj se dogodila semantička pogreška
    * napomena: ispisuje prvu produkciju u kojoj se dogodila semantička pogreška i završava s radom
* primjer pokretanja: `./SEM < generativno_stablo.txt`

### `lab3.tester.py`

* pokreće tester semantičkog analizatora
* ulazni testni primjeri nalaze se u direktoriju `io/lab-3/test`, a imena im završavaju ekstenzijom `.in`
* izlazni testni primjeri nalaze se u direktoriju `io/lab-3/test`, a imena im završavaju ekstenzijom `.out`
* parametar `--build` prevodi kôd semantičkog analizatora prije pokretanja testera
* primjer pokretanja: `./lab3.tester.py --build`

## 4. laboratorijska vježba

### `GEN`

* pokreće generator kôda za procesor *FRISC*
* generator kôda na `stdin` očekuje generativno stablo
* parametar `--build` prevodi kôd generatora prije pokretanja
* parametar `--build-only` prevodi kôd generatora i __ne__ pokreće generator
* primjer pokretanja: `./GEN --build < generativno_stablo.txt`

### `frisc.sh`

* prevodi i pokreće mnemonički program za procesor *FRISC*
* očekuje ime datoteke kao prvi parametar
* primjer pokretanja: `./frisc.sh source.frisc`

### `lab4.tester.py`

* pokreće tester generatora kôda
* ulazni testni primjeri nalaze se u direktoriju `io/lab-4`, a imena im završavaju ekstenzijom `.c`
* izlazni testni primjeri nalaze se u direktoriju `io/lab-4`, a imena im završavaju ekstenzijom `.out`
* tester generatora kôda pokreće cjeloviti prevoditelj jezika *ppjC*: skriptu `ppjc`
* parametar `--build` prevodi kôd semantičkog analizatora prije pokretanja testera
* primjer pokretanja: `./lab4.tester.py`

## Prevoditelj `ppjc`

* prevodi programski kôd napisan u jeziku *ppjC* u mnemonički jezik za procesor *FRISC*
* očekuje ime datoteke kao prvi parametar
* parametar `--build` prevodi i pokreće sve *komponente* prevoditelja prije pokretanja
    * napomena: pokreće samo `GLA` i `GSA` koji stvaraju strukture potrebne za `LA` i `SA`
* parametar `--build-only` prevodi i pokreće sve *komponente* prevoditelja i __ne__ pokreće prevoditelj
    * napomena: pokreće samo `GLA` i `GSA` koji stvaraju strukture potrebne za `LA` i `SA`
* prevoditelj uvijek stvara datoteku `a.frisc` u kojoj se nalazi rezultat prevođenja
* prevoditelj uvijek na `stderr` ispisuje rezultat prevođenja
* primjer pokretanja: `./ppjc source.c`

## Napomene

* parametri `--build` i `--build-only` nisu obavezni ni u jednoj skripti

