public class Monom {
	private double coef;
	private int exp;

	//constructor
	public Monom(double c, int e) {
		coef = c;
		exp = e;
	}

	public double getCoef() {
		return coef;
	}

	public int getExp() {
		return exp;
	}

	public void setCoef(double c){
		coef = c;
	}

	public void setExp(int e) {
		exp = e;
	}

	//operatia de adunare pe monoame
	public Monom add(Monom m2) {
		Monom m3 = new Monom(0, exp);
		m3.coef = this.coef + m2.coef;
		return m3;
	}
	
	//inverseaza semnul coeficientului monomului
	public Monom inv() {
		coef *= -1;
		return this;
	}
	
	//operatia de scadere pe monoame
	public Monom sub(Monom m2) {
		Monom m3 = new Monom(0, exp);
		m3.coef = this.coef - m2.coef;
		return m3;
	}
	
	//operatia de inmultire pe monoame
	public Monom multiply(Monom m2) {
		Monom m3 = new Monom(0, 0);
		m3.coef = this.coef * m2.coef;
		m3.exp = this.exp + m2.exp;
		return m3;
	}

	////operatia de impartire pe monoame
	public Monom divide(Monom m2) {
		Monom m3 = new Monom(0, 0);
		m3.coef = this.coef / m2.coef;
		m3.exp = this.exp - m2.exp;
		return m3;
	}
	
	//operatia de derivare pe monoame
	public Monom differentiate() {
		if (exp > 0) {
			coef *= exp;
			exp--;
		}
		else if (exp == 0){
			coef = 0;
		}
		return this;
	}
	
	//operatia de integrare pe monoame
	public Monom integrate() {
		exp++;
		coef /= exp;
		return this;
	}

	//afisarea monomului
	public String toString() {
		String s = "";
		//in sCoef va fi stocat coeficientul
		String sCoef = "";
		//daca coeficientul este un numar intreg, se va stoca in sCoef, 
		if (coef == (int)coef) 
			sCoef += Integer.toString((int)coef);
		else
		//altfel, daca coeficientul este un Double(in cazul integrarii), in sCoef se stocheaza valoarea in Double
			sCoef += coef;
		//daca monomul este de genul 4x^3, Stringul returnat va fi "+4x^4"
		if (coef > 0 && coef != 1 && exp != 0)
			s += "+" + sCoef;
		//daca monomul este de genul 8(x^0), Stringul returnat va fi "8"
		else if (coef >= 1 && exp == 0)
			s += sCoef;
		//daca monomul este de genul x^6, Stringul returnat va fi "+x^6"
		else if (coef == 1)
			s += "+";
		//daca monomul este de genul -x^7, Stringul returnat va fi "-x^7"
		else if (coef == -1 && exp != 0)
			s += "-";
		//daca monomul este de -1, Stringul returnat va fi "-1"
		else if (coef == -1 && exp == 0) 
			return "-1";
		//altfel, daca monomul este negativ, avand coeficientul diferit de -1 si exponentul diferit de 0(de genul -5x^8), Stringul returnat va fi "-5x^8"
		else 
			s += sCoef;
		//daca monomul este la puterea 1, nu se va mai concatena "^1"
		if (exp == 1)
			s += "x";
		//altfel, se concateneaza "^" si exponentul corespunzator
		else if (exp > 1)
			s += "x^"+exp;
		return s;
	}
}