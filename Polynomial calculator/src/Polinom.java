import java.util.*;
import java.util.regex.*;

public class Polinom {
	//cheia va reprezenta exponentul, iar valoarea, monomul corespunzator exponentului
	HashMap<Integer, Monom> polinom = new HashMap<Integer, Monom>();
	Pattern p = Pattern.compile( "([+-]?\\d*)x(\\^(\\d+))?|([+-]?\\d+)" );

	public Polinom(String input) {
		double c = 0;
		int e = 0;
		//eliminam spatiile din input
		input = input.replaceAll(" ", "");
		Matcher m = p.matcher(input);
		//grupul 1 reprezinta coeficientul monomului
		//grupul 2 reprezinta structura "^exponent"
		//grupul 3 reprezinta exponentul monomului
		//grupul 4 apartine cazurilor in care avem un monom la puterea 0(coeficientii liberi: 1, -8, 9 etc)
		while (m.find()) {
			//cazul in care avem un monom cu exponentul diferit de 0
			if (m.group(4) == null) {
				//cazul in care coeficientul monomului este -1(exemplu secventa monom: "-x")
				if (m.group(1).equals("-")) 
					c = -1;	
				//cazul in care coeficientul monomului este 1(exemplu secventa monom: "x")
				else if (m.group(1).equals(""))
					c = 1;
				//cazul in care coeficientul monomului este 1(exemplu secventa monom: "+x^4")
				else if (m.group(1).equals("+"))
					c = 1;
				else
				//cazul in care coeficientul monomului nu se regaseste in celelalte cazuri(exemplu secventa monom: "+2x^7"; "-8x^5")
					c = Double.parseDouble(m.group(1));	
				//cazul in care exponentul monomului este 1(exemplu secventa monom: "x")
				if (m.group(3) == null) 
					e = 1;	
				else 
				//cazul in care exponentul monomului este diferit de 0(exemplu secventa monom: "x^7")
					e = Integer.parseInt(m.group(3));			
			}
			//cazul in care avem un monom cu exponentul 0
			else if (m.group(4) != null) {
				//coeficientul este intocmai grupul 4, iar exponentul este 0
				c = Double.parseDouble(m.group(4));
				e = 0;
			}
			//daca la cheia e(care reprezinta exponentul) exista deja o valoare, se va adauga acesteia noua valoare citita(exemplu: "1+2" => "3")
			if (polinom.containsKey(e))
				polinom.put(e, polinom.get(e).add(new Monom(c, e)));
			else
			//altfel, marcam legatura in structura polinomului(punem la cheia e(->exponentul) valoarea Monom(c, e)(->monomul corespunzator exponentului)
				polinom.put(e, new Monom(c, e));
		}
	}

	public Polinom getPolinom() {
		return this;
	}

	//returneaza monomul cu exponent maxim din polinom
	public Monom getDominantMonom() {
		Monom m = new Monom (0, 0);
		for (Monom m1 : this.polinom.values())
			m = m1;
		return m;
	}
	
	//returneaza mononul cu exponent minim din polinom
	public int getMinimumMonomExponent() {
		for (Monom m1 : this.polinom.values())
			if (m1.getCoef() != 0)
				return m1.getExp();
		return Integer.MAX_VALUE;
	}
	
	//inverseaza semnul tuturor coeficientilor din polinom
	public void inv() {
		for (Monom m : this.polinom.values())
			m.inv();
	}
	
	//realizeaza o copie structurii polinom
	public HashMap<Integer, Monom> copy(Polinom p){
		HashMap<Integer, Monom> newHashMap = new HashMap<Integer, Monom>();
		for (Integer i : p.polinom.keySet())
			newHashMap.put(i, p.polinom.get(i));
		return newHashMap;
	}

	//realizeaza adunarea a 2 polinoame
	public Polinom add(Polinom p2) {
		//in p3 va fi rezultatul
		Polinom p3 = new Polinom("");
		//in p4 va fi copia HashMapului polinomului rezultat
		HashMap<Integer, Monom> p4 = new HashMap<Integer, Monom>();
		//pentru fiecare pereche de monoame,
		for (Monom m1 : this.polinom.values())
			for (Monom m2 : p2.polinom.values()) 
				//daca monoamele au acelasi exponent, se aduna, si se pune la cheia ce reprezinta exponentul valoarea(monomul) rezultata din adunare
				if (m1.getExp() == m2.getExp()) 
					p3.polinom.put(m1.getExp(), m1.add(m2));
				else {
					//altfel, daca nu exista un monom cu exponentul monomului din primul, respectiv al doilea polinom, se va pune in polinomul rezultat
					if (!p3.polinom.containsKey(m1.getExp()))
						p3.polinom.put(m1.getExp(), m1);
					if (!p3.polinom.containsKey(m2.getExp()))
						p3.polinom.put(m2.getExp(), m2);
				}
		//se face copia polinomului rezultat
		p4 = p3.copy(p3);
		//daca exista monoame cu coeficientul 0 in polinom, se va elimina acel monom din polinomul rezultat
		for (Monom m : p4.values())
			if (m.getCoef() == 0)
				p3.polinom.remove(m.getExp());
		return p3;
	}
	
	//realizeaza scaderea a 2 polinoame
	public Polinom sub(Polinom p2) {
		Polinom p3 = new Polinom("");
		HashMap<Integer, Monom> p4 = new HashMap<Integer, Monom>();
		for (Monom m1 : this.polinom.values())
			for (Monom m2 : p2.polinom.values()) 
				//in cazul in care perechea de monoame are acelasi exponent, iar in polinomul rezultat nu exista un monom cu acest exponent, se va pune in polinom diferenta monoamelor
				if (m1.getExp() == m2.getExp() && !p3.polinom.containsKey(m1.getExp())) 
					p3.polinom.put(m1.getExp(), m1.sub(m2));
				//in cazul in care perechea de monoame are acelasi exponent, iar in polinomul rezultat exista un monom cu acest exponent, se va pune in polinom suma monoamelor sau diferenta in functie de care element a fost adaugat primul
				else if (m1.getExp() == m2.getExp() && p3.polinom.containsKey(m1.getExp())) {
					if (this.getMinimumMonomExponent() <= p2.getMinimumMonomExponent())
						p3.polinom.put(m1.getExp(), m1.add(p3.polinom.get(m1.getExp())));
					else if (this.getMinimumMonomExponent() > p2.getMinimumMonomExponent()) {
						p3.polinom.put(m1.getExp(), p3.polinom.get(m1.getExp()).sub(m2));
					}
				}					
				else {
					//in cazul in care nu exista primul monom din pereche in rezultat, se va pune
					if (!p3.polinom.containsKey(m1.getExp()))
						p3.polinom.put(m1.getExp(), m1);
					//in cazul in care nu exista al doilea monom din pereche in rezultat, acesta se inverseaza si se trece in rezultat
					if (!p3.polinom.containsKey(m2.getExp()))
						p3.polinom.put(m2.getExp(), m2.inv());
				}
		p4 = p3.copy(p3);
		for (Monom m : p4.values())
			if (m.getCoef() == 0)
				p3.polinom.remove(m.getExp());
		return p3;
	}

	//realizeaza inmultirea a 2 polinoame
	public Polinom multiply(Polinom p2) {
		Polinom p3 = new Polinom("");
		HashMap<Integer, Monom> p4 = new HashMap<Integer, Monom>();
		Monom m = new Monom(0, 0);
		//se inmulteste fiecare monom cu fiecare monom din cele 2 polinoame
		for (Monom m1 : this.polinom.values())
			for (Monom m2 : p2.polinom.values()) {
				m = m1.multiply(m2);
				//daca polinomul rezultat nu contine niciun monom cu exponentul corespunzator produsului, se va pune produsul
				if (!p3.polinom.containsKey(m.getExp())) 
					p3.polinom.put(m.getExp(), m);	
				else
					//altfel, daca exista un monom in polinomul rezultat cu exponentul respectiv
					p3.polinom.put(m.getExp(), m.add(p3.polinom.get(m.getExp())));
			}	
		p4 = p3.copy(p3);
		for (Monom mon : p4.values())
			if (mon.getCoef() == 0)
				p3.polinom.remove(mon.getExp());
		return p3;
	}

	//realizeaza impartirea a 2 polinoame
	public Polinom[] divide(Polinom p2) {
		//dominant2 va reprezenta monomul dominant al impartitorului
		Monom dominant2 = new Monom (0, 0);
		//in []p3 se va stoca catul si restul
		Polinom [] p3 = new Polinom[2];
		//p0 va fi deimpartitul care se va actualiza dupa fiecare iteratie a do-while-ului
		Polinom p0 = this;
		//p1 va fi polinomul cu coeficienti inversati obtinut din inmultirea impartitorului cu noul monom adaugat in polinomul corespunzator catului
		Polinom p1 = new Polinom("");
		//dominantQPol va fi polinomul care va contine noul monom adaugat in polinomul corespunzator catului
		Polinom dominantQPol = new Polinom("");
		//in q se va afla catul
		Polinom q = new Polinom("");
		//in r se va afla restul
		Polinom r = new Polinom("");
		dominant2 = p2.getDominantMonom();
		//in dominantP0 se va afla monomul dominant al polinomului care se imparte (p0)
		Monom dominantP0 = p0.getDominantMonom();
		//daca gradul deimpartitului este mai mare decat cel al impartitorului,
		if (dominantP0.getExp() >= dominant2.getExp()) {
		do {
			//in monom div1 se va afla monomul rezultat din impartirea monoamelor dominante din p0 si p2(p2 fiind impartitorul)
			Monom div1 = dominantP0.divide(dominant2);
			//in polinomul care reprezinta catul se va pune div1
			q.polinom.put(div1.getExp(), div1);
			dominantQPol.polinom.clear();
			//dominantQPol primeste div1
			dominantQPol.polinom.put(div1.getExp(), div1);
			//se inmulteste monomul div1 cu impartitorul si se inverseaza semnele coeficientilor
			p1 = dominantQPol.multiply(p2);
			p1.inv();
			//se face adunarea intre p0 si p1 si se actualizeaza p0("noul deimpartit")
			p0 = p0.add(p1);
			//dominantP0 este actualizat
			dominantP0 = p0.getDominantMonom();
			//conditia de oprire este: cand gradul deimpartitului este mai mic sau egal cu cel al impartitorului, sau cand cele doua au acelasi grad, iar gradul este diferit de 0, sau cand au gradele egale cu 0 si p1(sau restul) este diferit de 0
		} while (dominantP0.getExp() > dominant2.getExp() || (dominantP0.getExp() == dominant2.getExp() && dominantP0.getExp() != 0) || (dominantP0.getExp() == dominant2.getExp() && dominantP0.getExp() == 0) && p1.getDominantMonom().getExp() != 0);
		r = p0;
		}
		else {
			q = new Polinom("0");
			r = this;
		}
		p3[0] = q;
		p3[1] = r;
		return p3;
	}

	//realizeaza derivata unui polinom
	public Polinom differentiate() {
		Polinom p2 = new Polinom("");
		HashMap<Integer, Monom> p3 = new HashMap<Integer, Monom>();
		Monom m = new Monom(0, 0);
		for (Monom m1 : this.polinom.values()) {
			m = m1.differentiate();
			p2.polinom.put(m.getExp(), m);
		}
		p3 = p2.copy(p2);
		for (Monom mon : p3.values())
			if (mon.getCoef() == 0)
				p2.polinom.remove(mon.getExp());
		return p2;
	}

	//realizeaza integrarea unui polinom
	public Polinom integrate() {
		Polinom p2 = new Polinom("");
		HashMap<Integer, Monom> p3 = new HashMap<Integer, Monom>();
		Monom m = new Monom(0, 0);
		for (Monom m1 : this.polinom.values()) {
			m = m1.integrate();
			p2.polinom.put(m.getExp(), m);
		}
		p3 = p2.copy(p2);
		for (Monom mon : p3.values())
			if (mon.getCoef() == 0)
				p2.polinom.remove(mon.getExp());
		return p2;
	}

	//afisarea polinomului
	public String toString() {
		//lowestExp va contine exponentul monomului cu gradul cel mai mic din polinom, iar coefToLowestExp, coeficientul
		int lowestExp = Integer.MAX_VALUE;
		double coefToLowestExp = Double.MAX_VALUE;
		String s = "";
		for (Monom m : polinom.values()) { 
			if (m.getExp() < lowestExp) {
				lowestExp = m.getExp();
				coefToLowestExp = m.getCoef();
			}
			//daca coeficientul unui monom este 0, nu va fi afisat
			if (m.getCoef() != 0)
				s += m.toString();
		}
		//daca monomul cu gradul cel mai mic din polinom are gradul mai mare ca 0, iar coeficientul este pozitiv, se va elimina semnul plus(exemplu: "+4x^2-5x^6" => "4x^2-5x^6")
		if (lowestExp > 0 && coefToLowestExp > 0 && lowestExp != Integer.MAX_VALUE)
			s = s.substring(1);
		//daca polinomul este nul, se va afisa "0"
		if (s.equals(""))
			s = "0";
		return s;
	}
}