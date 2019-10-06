
import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JFormattedTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;


public class Ventana extends JFrame{
	JTextField text_cuentaAExtraer;
	JTextField text_montoAExtraer;
	JTextField text_montoADepositar;
	JTextField text_cuentaADepositar;
	
	private float montoAExtraer = 0;
	private float montoADepositar = 0;
	private int cuentaADepositar = 0;
	private int cuentaAExtraer = 0;
	private Cliente cliente;
	
	
	public Ventana(Cliente c) {
		this.cliente = c;
		this.setSize(500, 500);
		
		JLabel label1 = new JLabel();
		label1.setText("Importe: ");
		
		JLabel label2 = new JLabel();
		label2.setText("N° de Cuenta");
		
		JLabel label3 = new JLabel();
		label3.setText("N° de Cuenta");
		
		
		text_cuentaAExtraer = new JTextField();
		text_cuentaAExtraer.getDocument().addDocumentListener((DocumentListener) new DocumentListener() {
			@Override
		  public void changedUpdate(DocumentEvent e) {
		    warn();
		  }

			@Override
			public void removeUpdate(DocumentEvent e) {
				//warn();
			}
			@Override
		  public void insertUpdate(DocumentEvent e) {
		    warn();
		  }

		  public void warn() {
			 try {
				  if (Integer.parseInt(text_cuentaAExtraer.getText())<=0){
					  JOptionPane.showMessageDialog(null,"Error: Ingrese una cuenta mayor a 0", "Error Message",JOptionPane.ERROR_MESSAGE);
				  }
				  else {
					  cuentaAExtraer = Integer.parseInt(text_cuentaAExtraer.getText());
				  }
				
			} catch (Exception e) {
			       JOptionPane.showMessageDialog(null,"Error de formato: Debe ingresar un valor decimal.", "Error Message",JOptionPane.ERROR_MESSAGE);
			}
		  }			
		});
		
		
		
		text_montoAExtraer = new JTextField();
		text_montoAExtraer.getDocument().addDocumentListener((DocumentListener) new DocumentListener() {
			@Override
			  public void changedUpdate(DocumentEvent e) {
			    warn();
			  }

				@Override
				public void removeUpdate(DocumentEvent e) {
					//warn();
				}
				@Override
			  public void insertUpdate(DocumentEvent e) {
					warn();
			  }

			  public void warn() {
				 try {
					  if (Integer.parseInt(text_montoAExtraer.getText())<=0){
					       JOptionPane.showMessageDialog(null,"Error: Ingrese un monto mayor a 0", "Error Message",JOptionPane.ERROR_MESSAGE);
					  }
					  else {
						  montoAExtraer = Float.parseFloat(text_montoAExtraer.getText());
					  }
					
				} catch (Exception e) {
				       JOptionPane.showMessageDialog(null,"Error de formato: Debe ingresar un valor decimal.", "Error Message",JOptionPane.ERROR_MESSAGE);
				}
			  }			
			});
		
		
		text_montoADepositar = new JTextField();
		text_montoADepositar.getDocument().addDocumentListener((DocumentListener) new DocumentListener() {
			@Override
			  public void changedUpdate(DocumentEvent e) {
			    warn();
			  }

				@Override
				public void removeUpdate(DocumentEvent e) {
					//warn();
				}
				@Override
			  public void insertUpdate(DocumentEvent e) {
			    warn();
			  }

			  public void warn() {
				 try {
					  if (Integer.parseInt(text_montoADepositar.getText())<=0){
					       JOptionPane.showMessageDialog(null,"Error: Ingrese un monto mayor a 0", "Error Message",JOptionPane.ERROR_MESSAGE);
					  }
					  else {
						  montoADepositar = Float.parseFloat(text_montoADepositar.getText());
					  }
					
				} catch (Exception e) {
				       JOptionPane.showMessageDialog(null,"Error de formato: Debe ingresar un valor decimal.", "Error Message",JOptionPane.ERROR_MESSAGE);
				}
			  }			
			});
		
		
		text_cuentaADepositar = new JTextField();
		text_cuentaADepositar.getDocument().addDocumentListener((DocumentListener) new DocumentListener() {
			@Override
			  public void changedUpdate(DocumentEvent e) {
			    warn();
			  }

				@Override
				public void removeUpdate(DocumentEvent e) {
					//warn();
				}
				@Override
			  public void insertUpdate(DocumentEvent e) {
			    warn();
			  }

			  public void warn() {
				 try {
					  if (Integer.parseInt(text_cuentaADepositar.getText())<=0){
					       JOptionPane.showMessageDialog(null,"Error: Ingrese un monto mayor a 0", "Error Message",JOptionPane.ERROR_MESSAGE);
					  }
					  else {
						  cuentaADepositar = Integer.parseInt(text_cuentaADepositar.getText());
					  }
					
				} catch (Exception e) {
				       JOptionPane.showMessageDialog(null,"Error de formato: Debe ingresar un valor decimal.", "Error Message",JOptionPane.ERROR_MESSAGE);
				}
			  }			
			});
		
		
		
		
		
		JButton botonTransaccion = new JButton();
		botonTransaccion.setText("Realizar Transacción");
		botonTransaccion.addActionListener(cliente);
		
		
		
		
		
		JLabel lblDeposito = DefaultComponentFactory.getInstance().createTitle("Deposito");
		lblDeposito.setForeground(Color.GRAY);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setToolTipText("sadasd");
		
		JLabel label = new JLabel();
		label.setText("Importe: ");
		
		JLabel lblNewLabel = new JLabel("Extraccion");
		lblNewLabel.setForeground(Color.GRAY);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setToolTipText("sadasd");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(38)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, 387, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(17)
									.addComponent(label, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
									.addGap(4)
									.addComponent(text_montoADepositar, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(10)
									.addComponent(label1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
									.addGap(30)
									.addComponent(text_montoAExtraer, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)))
							.addGap(16)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(12)
									.addComponent(label2, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(text_cuentaAExtraer, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(10)
									.addComponent(label3, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
									.addGap(4)
									.addComponent(text_cuentaADepositar, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))))
						.addComponent(lblDeposito)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
							.addComponent(botonTransaccion, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
							.addComponent(separator_4, GroupLayout.PREFERRED_SIZE, 403, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(59, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(30)
					.addComponent(lblNewLabel)
					.addGap(7)
					.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(43)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(label1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(text_montoAExtraer, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(label2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(text_cuentaAExtraer, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(72)
					.addComponent(lblDeposito, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(43)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(text_montoADepositar, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(label3, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(text_cuentaADepositar, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(111)
					.addComponent(botonTransaccion, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(50))
		);
		getContentPane().setLayout(groupLayout);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	public float getMontoAExtraer() {
		return this.montoAExtraer;
	}
	
	public int getCuentaAExtraer() {
		return this.cuentaAExtraer;
	}
	
	public int getCuentaADepositar() {
		return this.cuentaADepositar;
	}
	
	public float getMontoADepositar() {
		return this.montoADepositar;
	}
	
	public boolean estanLosCamposLlenos() {
		return !(text_montoADepositar.getText().isEmpty() || text_montoAExtraer.getText().isEmpty() ||  text_cuentaADepositar.getText().isEmpty() || text_cuentaAExtraer.getText().isEmpty());
	}
	
	public void resetValores() {
		this.montoADepositar = 0;
		this.montoAExtraer = 0;
		this.cuentaADepositar = 0;
		this.cuentaAExtraer = 0;
	}
}
