package me.lty.plugin;

import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import me.lty.redis.Data;

import org.apache.jmeter.gui.util.VerticalPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisGui extends JPanel {

	private static final long serialVersionUID = 1L;
	public JTextField urlText ;
    public JTextField portText;
    public JPasswordField passwordText;

    public JButton connection;

    public JComboBox typeBox ;
    public JComboBox operatorBox;

    public JTextArea keyText;
    public JTextArea valueText;
    public JTextField seconds;
    public JTextArea fieldText;
    public JTextField indexText;
    private Logger log=LoggerFactory.getLogger(getClass());


    public JPanel valuePanel,secondsPanel,fieldPanel,indexPanel;

    public RedisGui(){
        init();
    }

    private void init() {
        setLayout(new BorderLayout(2,5));
        VerticalPanel mainPanel = new VerticalPanel();
        initTextField();
        initButton();
        initComboBox();
        initTextArea();
        mainPanel.add(createPanel(urlText,"Redis IP   :"));
        mainPanel.add(createPanel(portText,"Redis Port:"));
        mainPanel.add(createPanel(passwordText,"Password: "));
        mainPanel.add(connection);
        mainPanel.add(createComboBoxPanel());
        mainPanel.add(createTextAreaPanel(keyText,"Key    "));
        mainPanel.add(valuePanel = createTextAreaPanel(valueText,"Value "));
        mainPanel.add(secondsPanel = createPanel(seconds,"Seconds "));
        mainPanel.add(fieldPanel = createTextAreaPanel(fieldText,"Field  "));
        mainPanel.add(indexPanel = createPanel(indexText,"index"));
        add(mainPanel,BorderLayout.CENTER);
    }

    private void initTextArea() {
        keyText = new JTextArea(5,10);
        valueText = new JTextArea(5,10);
        fieldText = new JTextArea(5,10);
    }

    private JPanel createPanel(JTextField jTextField,String name){
        JLabel label = new JLabel(name);
        label.setLabelFor(jTextField);
        JPanel panel = new JPanel(new BorderLayout(2,5));
        panel.add(label,BorderLayout.WEST);
        panel.add(jTextField,BorderLayout.CENTER);
        return panel;
    }

    private JPanel createComboBoxPanel(){
        JLabel label = new JLabel("Type:");
        label.setLabelFor(typeBox);
        JPanel typePanel = new JPanel(new BorderLayout(0,5));
        typePanel.add(label,BorderLayout.WEST);
        typePanel.add(typeBox,BorderLayout.CENTER);

        JLabel jLabel = new JLabel("Operator:");
        jLabel.setLabelFor(operatorBox);
        JPanel operatorPanel = new JPanel(new BorderLayout(0,5));
        operatorPanel.add(jLabel,BorderLayout.WEST);
        operatorPanel.add(operatorBox,BorderLayout.CENTER);

        JPanel panel = new JPanel(new BorderLayout(0,5));
        panel.add(typePanel,BorderLayout.WEST);
        panel.add(operatorPanel,BorderLayout.CENTER);
        return panel;
    }
    private void initComboBox() {
        typeBox = new JComboBox();
        operatorBox = new JComboBox();
        Data.TYPE_OPERATOR.entrySet().forEach((x)->typeBox.addItem(x.getKey()));
        operatorBox.setModel(new DefaultComboBoxModel(Data.TYPE_OPERATOR.get(typeBox.getSelectedItem()).toArray()));
        typeBox.addActionListener(e -> {
            operatorBox.removeAllItems();
            String type = (String) typeBox.getSelectedItem();
            operatorBox.setModel(new DefaultComboBoxModel(Data.TYPE_OPERATOR.get(type).toArray()));
            switch (type){
                case "String":
                    valuePanel.setVisible(true);
                    secondsPanel.setVisible(false);
                    fieldPanel.setVisible(false);
                    indexPanel.setVisible(false);
                    break;
            }
        });
    }

    private void initButton() {
		connection = new JButton();
		connection.setText("Connection");
		connection.addActionListener(e -> {
			String url = urlText.getText().trim();
			if ("".equals(url)) {
				JOptionPane.showMessageDialog(null, "Redis IP is null",
						"error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String port = portText.getText().trim();
			if ("".equals(port)) {
				JOptionPane.showMessageDialog(null, "port is empty", "error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				int p = Integer.valueOf(port);
				Jedis jedis = new Jedis(url, p);
				log.info("connected");
				jedis.close();
				JOptionPane.showMessageDialog(null, "connected", "notice",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (NumberFormatException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "port is wrong", "错误",
						JOptionPane.ERROR_MESSAGE);
			} catch (JedisConnectionException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null,
						"connect error ,please check.", "错误",
						JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
	}

    private JPanel createTextAreaPanel(JTextArea jTextArea,String name){
        JLabel label = new JLabel(name);
        label.setLabelFor(jTextArea);
        JPanel panel = new JPanel(new BorderLayout(0,5));
        panel.add(label,BorderLayout.WEST);
        panel.add(jTextArea,BorderLayout.CENTER);
        return panel;
    }

    private void initTextField() {
        urlText = new JTextField(15);
        portText = new JTextField(15);
        passwordText = new JPasswordField(15);
        indexText = new JTextField(15);
        seconds = new JTextField(15);
    }
}
