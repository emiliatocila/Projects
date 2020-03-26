
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JTextField;

public class Controller {
	private View  view;

	Controller(View view) {
		this.view  = view;
		view.addStartListener(new StartListener());
		view.addClearListener(new ClearListener());
	}

	class StartListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {			
		    int simTime = 0;
		    int minSTime = 0;
		    int maxSTime = 0;
		    int minATime = 0;
		    int maxATime = 0;
		    int nrQ = 0;
		    int nrC = 0;
			simTime = Integer.parseInt(view.getSimTime());
			minSTime = Integer.parseInt(view.getMinSTime());
			maxSTime = Integer.parseInt(view.getMaxSTime());
			minATime = Integer.parseInt(view.getMinATime());
			maxATime = Integer.parseInt(view.getMaxATime());
			nrQ = Integer.parseInt(view.getNrQ());
			nrC = Integer.parseInt(view.getNrC());
			SimulationManager sim = new SimulationManager(simTime, minSTime, maxSTime, minATime, maxATime, nrQ, nrC, view);
			Thread t = new Thread(sim);
			t.start();
		}
	}

	class ClearListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.reset();
		}
	}
}