import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class View extends JFrame {
	private static final long serialVersionUID = 1502202796906896029L;
	private JTextField i_simulationTime = new JTextField(5);
	private JTextField i_minServiceTime = new JTextField(5);
	private JTextField i_maxServiceTime = new JTextField(5);
	private JTextField i_minArrivalTime = new JTextField(5);
	private JTextField i_maxArrivalTime = new JTextField(5);
	private JTextField i_nrOfQueues = new JTextField(5);
	private JTextField i_nrOfClients = new JTextField(5);
	
	private JTextArea o_queueEvolutionT = new JTextArea(20, 60);
	private JTextArea o_info = new JTextArea(10, 40);
	
    private JButton i_start = new JButton("Start");
    private JButton i_clear = new JButton("Clear");
    
    View() {     
        JLabel simTime = new JLabel("Simulation time");
        simTime.setFont(new Font("TimesRoman", Font.BOLD, 20));
        JLabel minSTime = new JLabel("Minimum service time");
        minSTime.setFont(new Font("TimesRoman", Font.BOLD, 20));
        JLabel maxSTime = new JLabel("Maximum service time");
        maxSTime.setFont(new Font("TimesRoman", Font.BOLD, 20));
        JLabel minATime = new JLabel("Minimum arrival time");
        minATime.setFont(new Font("TimesRoman", Font.BOLD, 20));
        JLabel maxATime = new JLabel("Maximum arrival time");
        maxATime.setFont(new Font("TimesRoman", Font.BOLD, 20));
        JLabel nrQ = new JLabel("Number of queues");
        nrQ.setFont(new Font("TimesRoman", Font.BOLD, 20));
        JLabel nrC = new JLabel("Number of clients");
        nrC.setFont(new Font("TimesRoman", Font.BOLD, 20));
        
        JPanel content1 = new JPanel();
        content1.setLayout(new BoxLayout(content1, BoxLayout.Y_AXIS));
        content1.add(simTime);
        content1.add(i_simulationTime);
        content1.add(minSTime);
        content1.add(i_minServiceTime);
        content1.add(maxSTime);
        content1.add(i_maxServiceTime);
        content1.add(minATime);
        content1.add(i_minArrivalTime);
        content1.add(maxATime);
        content1.add(i_maxArrivalTime);
        content1.add(nrQ);
        content1.add(i_nrOfQueues);
        content1.add(nrC);
        content1.add(i_nrOfClients);
        content1.add(Box.createRigidArea(new Dimension(0, 20)));
        content1.add(i_start);
        content1.add(Box.createRigidArea(new Dimension(0, 20)));
        content1.add(i_clear);
        content1.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JPanel content2 = new JPanel();
        content2.setLayout(new BoxLayout(content2, BoxLayout.Y_AXIS));
        
        o_queueEvolutionT.setEditable(false);
        o_info.setEditable(false);
        JScrollPane o_queueEvolution = new JScrollPane(o_queueEvolutionT);
        o_queueEvolution.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        o_queueEvolution.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        JLabel qEvol = new JLabel("Evolution of queues");
        qEvol.setFont(new Font("TimesRoman", Font.BOLD, 20));
        JLabel qInfo = new JLabel("Information");
        qInfo.setFont(new Font("TimesRoman", Font.BOLD, 20));
        content2.add(qEvol);
        content2.add(o_queueEvolution);
        content2.add(Box.createRigidArea(new Dimension(0, 20)));
        content2.add(qInfo);
        content2.add(o_info);
        content2.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        content.add(content1);
        content.add(Box.createRigidArea(new Dimension(40, 0)));
        content.add(content2);
        
        this.reset();
        
        this.setContentPane(content);
        this.pack();
        this.setSize(1200, 800);
        this.setTitle("Queue simulation");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    void reset() {
    	o_queueEvolutionT.setText("");
    	o_info.setText("");
    }
    
    String getSimTime() {
    		return i_simulationTime.getText();
    }
    
    String getMinSTime() {
    		return i_minServiceTime.getText();
    }
    
    String getMaxSTime() {
    	return i_maxServiceTime.getText();
    }
    
    String getMinATime() {
    	return i_minArrivalTime.getText();
    }
    
    String getMaxATime() {
    	return i_maxArrivalTime.getText();
    }
    
    String getNrQ() {
    	return i_nrOfQueues.getText();
    }
    
    String getNrC() {
    	return i_nrOfClients.getText();
    }
    
    void setQueues(String currQueues) {
        o_queueEvolutionT.append(currQueues);
    }
    
    void setInfo(String info) {
        o_info.append(info);
    }

    void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }
    
    void addStartListener(ActionListener al) {
        i_start.addActionListener(al);
    }
    
    void addClearListener(ActionListener al) {
        i_clear.addActionListener(al);
    }
}

