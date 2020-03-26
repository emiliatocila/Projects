import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class CalculatorView extends JFrame {
	private static final long serialVersionUID = 1502202796906896029L;
	private JTextField m_fxInput = new JTextField(30);
    private JTextField m_gxInput = new JTextField(30);
    private JTextField m_result = new JTextField(30);
    private JButton    m_addBtn = new JButton("Add");
    private JButton    m_subBtn = new JButton("Subtract");
    private JButton    m_multiplyBtn = new JButton("Multiply");
    private JButton    m_divideBtn = new JButton("Divide");
    private JButton    m_differentiateBtn = new JButton("Differentiate");
    private JButton    m_integrateBtn = new JButton("Integrate");
    private JButton    m_clearBtn    = new JButton("Clear");
    
    CalculatorView() {     
        JLabel fx = new JLabel("f(x)");
        fx.setFont(new Font("TimesRoman", Font.BOLD, 40));
        JLabel gx = new JLabel("g(x)");
        gx.setFont(new Font("TimesRoman", Font.BOLD, 40));
        JLabel result = new JLabel("result");
        result.setFont(new Font("TimesRoman", Font.BOLD, 40));
        m_fxInput.setPreferredSize(new Dimension(3, 100));
        m_fxInput.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        m_gxInput.setPreferredSize(new Dimension(3, 100));
        m_gxInput.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        m_result.setPreferredSize(new Dimension(3, 100));
        m_result.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        m_result.setEditable(false);
        m_addBtn.setFont(new Font("TimesRoman", Font.BOLD, 20));
        m_subBtn.setFont(new Font("TimesRoman", Font.BOLD, 20));
        m_multiplyBtn.setFont(new Font("TimesRoman", Font.BOLD, 20));
        m_divideBtn.setFont(new Font("TimesRoman", Font.BOLD, 20));
        m_differentiateBtn.setFont(new Font("TimesRoman", Font.BOLD, 20));
        m_integrateBtn.setFont(new Font("TimesRoman", Font.BOLD, 20));
        m_clearBtn.setFont(new Font("TimesRoman", Font.BOLD, 20));
        
        JPanel content1 = new JPanel();
        content1.setLayout(new BoxLayout(content1, BoxLayout.Y_AXIS));
        content1.add(fx);
        content1.add(m_fxInput);
        content1.add(gx);
        content1.add(m_gxInput);
        content1.add(result);
        content1.add(m_result);
        JPanel content2 = new JPanel();
        content2.setLayout(new BoxLayout(content2, BoxLayout.Y_AXIS));
        
        content2.add(m_addBtn);
        content2.add(Box.createRigidArea(new Dimension(0, 20)));
        content2.add(m_subBtn);
        content2.add(Box.createRigidArea(new Dimension(0, 20)));
        content2.add(m_multiplyBtn);
        content2.add(Box.createRigidArea(new Dimension(0, 20)));
        content2.add(m_divideBtn);
        content2.add(Box.createRigidArea(new Dimension(0, 20)));
        content2.add(m_differentiateBtn); 
        content2.add(Box.createRigidArea(new Dimension(0, 20)));
        content2.add(m_integrateBtn);
        content2.add(Box.createRigidArea(new Dimension(0, 20)));
        content2.add(m_clearBtn);
        
        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        content.add(content1);
        content.add(Box.createRigidArea(new Dimension(40, 0)));
        content.add(content2);
        
        this.reset();
        
        this.setContentPane(content);
        this.pack();
        this.setSize(800, 800);
        this.setTitle("Polynomial calculator");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    void reset() {
    	m_fxInput.setText("");
    	m_gxInput.setText("");
        m_result.setText("0");
    }
    
    String getfxInput() {
    		return m_fxInput.getText();
    }
    
    String getgxInput() {
    		return m_gxInput.getText();
    }
    
    String getResult() {
    	return m_result.getText();
    }
    
    void setResult(String newTotal) {
        m_result.setText(newTotal);
    }
    
    void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }
    
    void addAddListener(ActionListener al) {
        m_addBtn.addActionListener(al);
    }
    
    void addSubListener(ActionListener al) {
        m_subBtn.addActionListener(al);
    }
    
    void addMultiplyListener(ActionListener al) {
        m_multiplyBtn.addActionListener(al);
    }
    
    void addDivideListener(ActionListener al) {
        m_divideBtn.addActionListener(al);
    }
    
    void addDifferentiateListener(ActionListener al) {
        m_differentiateBtn.addActionListener(al);
    }
    
    void addIntegrateListener(ActionListener al) {
        m_integrateBtn.addActionListener(al);
    }
    
    void addClearListener(ActionListener al) {
        m_clearBtn.addActionListener(al);
    }
}

