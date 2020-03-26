
import java.awt.event.*;

public class CalculatorController {
	private CalculatorView  m_view;

	CalculatorController(CalculatorView view) {
		m_view  = view;
		view.addAddListener(new AddListener());
		view.addSubListener(new SubListener());
		view.addMultiplyListener(new MultiplyListener());
		view.addDivideListener(new DivideListener());
		view.addDifferentiateListener(new DifferentiateListener());
		view.addIntegrateListener(new IntegrateListener());
		view.addClearListener(new ClearListener());
	}

	class AddListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String userfx = "0";
			String usergx = "0";
			userfx = m_view.getfxInput();
			usergx = m_view.getgxInput();
			Polinom p1 = new Polinom(userfx);
			Polinom p2 = new Polinom(usergx);
			Polinom p3;
			if (usergx.equals(""))
				p3 = p1;
			else if (userfx.equals(""))
				p3 = p2;
			else
				p3 = p1.add(p2);
			m_view.setResult(p3.getPolinom().toString());
		}
	}

	class SubListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String userfx = "0";
			String usergx = "0";
			userfx = m_view.getfxInput();
			usergx = m_view.getgxInput();
			Polinom p1 = new Polinom(userfx);
			Polinom p2 = new Polinom(usergx);
			Polinom p3;
			if (usergx.equals(""))
				p3 = p1;
			else if (userfx.equals("")) {
				for (Monom m : p2.polinom.values())
					m.inv();
					p3 = p2;
			}
			else
			p3 = p1.sub(p2);
			m_view.setResult(p3.getPolinom().toString());
		}
	}
	class MultiplyListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String userfx = "0";
			String usergx = "0";
			userfx = m_view.getfxInput();
			usergx = m_view.getgxInput();
			Polinom p1 = new Polinom(userfx);
			Polinom p2 = new Polinom(usergx);
			Polinom p3 = p1.multiply(p2);
			m_view.setResult(p3.getPolinom().toString());
		}
	}

	class DivideListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String userfx = "0";
			String usergx = "0";
			userfx = m_view.getfxInput();
			usergx = m_view.getgxInput();
			if (usergx.equals("0") || usergx.equals(""))
				m_view.setResult("Division by 0!");
			else {
				Polinom p1 = new Polinom(userfx);
				Polinom p2 = new Polinom(usergx);
				Polinom []p3 = p1.divide(p2);
				m_view.setResult("Quotient: " + p3[0].getPolinom().toString() + " Remainder: " + p3[1].getPolinom().toString());
			}
		}
	}
	class DifferentiateListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String userfx = "0";
			userfx = m_view.getfxInput();
			Polinom p1 = new Polinom(userfx);
			Polinom p2 = p1.getPolinom().differentiate();
			m_view.setResult(p2.getPolinom().toString());
		}
	}

	class IntegrateListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String userfx = "0";
			userfx = m_view.getfxInput();
			Polinom p1 = new Polinom(userfx);
			Polinom p2 = p1.getPolinom().integrate();
			m_view.setResult(p2.getPolinom().toString());
		}
	}

	class ClearListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			m_view.reset();
		}
	}
}