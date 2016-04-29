package me.lty.plugin;

import me.lty.redis.Operator;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 */
public class RedisSamplerGui extends AbstractSamplerGui implements ItemListener{

	private static final long serialVersionUID = 1L;
	public RedisGui redisGui;

    public RedisSamplerGui(){
        super();
        init();
    }

    private void init() {
        setLayout(new BorderLayout(0,5));
        setBorder(makeBorder());
        add(makeTitlePanel(),BorderLayout.NORTH);

        redisGui = new RedisGui();
        add(redisGui,BorderLayout.CENTER);
    }

    public String getLabelResource() {
        return this.getClass().getSimpleName();
    }

    public TestElement createTestElement() {
        return new RedisSamplerBase();
    }

    public void modifyTestElement(TestElement testElement) {
        super.configureTestElement(testElement);
        if(testElement instanceof  RedisSamplerBase){
            RedisSamplerBase base = (RedisSamplerBase) testElement;
            base.setIp(redisGui.urlText.getText().trim());
            base.setPassword(new String(redisGui.passwordText.getPassword()));
            if(redisGui.seconds.getText().trim().equals("")){

            }else{
                int seconds = Integer.valueOf(redisGui.seconds.getText().trim());
                base.setSeconds(seconds);
            }
            if(redisGui.portText.getText().trim().equals("")){

            }else{
                int port = Integer.valueOf(redisGui.portText.getText().trim());
                base.setPort(port);
            }
            base.setKey(redisGui.keyText.getText().trim());
            base.setValue(redisGui.valueText.getText().trim());
            base.setField(redisGui.fieldText.getText().trim());
            if(redisGui.indexText.getText().trim().equals("")){

            }else{
                int index = Integer.valueOf(redisGui.indexText.getText().trim());
                base.setIndex(index);
            }
            base.setType(redisGui.typeBox.getSelectedItem().toString());
            Operator operator = (Operator) redisGui.operatorBox.getSelectedItem();
            if(operator !=null){
                base.setOperatorCode(operator.getCode());
            }else{
                JOptionPane.showMessageDialog(null,"Operator","error",JOptionPane.ERROR_MESSAGE);
            }
            base.setOperatorIndex(redisGui.operatorBox.getSelectedIndex());
        }
    }

    @Override
    public void configure(TestElement element) {
        super.configure(element);
        if(element instanceof RedisSamplerBase){
            RedisSamplerBase base = (RedisSamplerBase) element;
            redisGui.typeBox.setSelectedItem(base.getType());
            redisGui.operatorBox.setSelectedIndex(base.getOperatorIndex());
            redisGui.keyText.setText(base.getKey());
            redisGui.valueText.setText(base.getValue());
            redisGui.fieldText.setText(base.getField());
            redisGui.urlText.setText(base.getIP());
            redisGui.passwordText.setText(base.getPASSWORD());
            if(base.getSeconds() == 0){
                redisGui.seconds.setText("");
            }else{
                redisGui.seconds.setText(base.getSeconds()+"");
            }
            if(base.getPORT() == 0){
                redisGui.portText.setText("");
            }else{
                redisGui.portText.setText(base.getPORT()+"");
            }
            if(base.getIndex() == 0){
                redisGui.indexText.setText("");
            }else{
                redisGui.indexText.setText(base.getIndex()+"");
            }
        }
    }

    public void itemStateChanged(ItemEvent e) {

    }

    @Override
    public String getStaticLabel() {
        return "RedisSampler";
    }
}
