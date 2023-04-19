import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class parkingtest {

	private JFrame frame;
	private Estacionamento estacionamento;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					parkingtest window = new parkingtest();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public parkingtest() {
		initialize();
		try {
	        estacionamento = new Estacionamento(10);	//10 vagas
	        estacionamento.lerDados();
	    }
	    catch(Exception e) {
	        System.out.println(e.getMessage());
	    }
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 760, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel(" Sistema de Controle de Estacionamento ");
		label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label.setBounds(250, 50, 350, 15);
		frame.getContentPane().add(label);
		
		JButton button = new JButton("Entrar carro");
		button.setBounds(304, 90, 149, 21);
		frame.getContentPane().add(button);
		
		JButton button_1_1 = new JButton("Sair carro");
		button_1_1.setBounds(304, 140, 149, 21);
		frame.getContentPane().add(button_1_1);
		
		JButton button_1_2_1 = new JButton("Consultar placa");
		button_1_2_1.setBounds(304, 190, 149, 21);
		frame.getContentPane().add(button_1_2_1);
		
		JButton button_1_2 = new JButton("Transferir placa");
		button_1_2.setBounds(304, 240, 149, 21);
		frame.getContentPane().add(button_1_2);
		
		JButton button_1_3 = new JButton("Listar geral");
		button_1_3.setBounds(304, 290, 149, 21);
		frame.getContentPane().add(button_1_3);
		
		JButton button_1 = new JButton("Listar vagas livres");
		button_1.setBounds(304, 340, 149, 21);
		frame.getContentPane().add(button_1);
		
		JButton button_1_4 = new JButton("Gravar Dados");
		button_1_4.setBounds(304, 390, 149, 21);
		frame.getContentPane().add(button_1_4);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String placa = JOptionPane.showInputDialog(null, "Qual a placa para entrar?");
				int vaga = Integer.parseInt(JOptionPane.showInputDialog(null, "Qual a vaga para entrar?"));
				if(estacionamento != null) {
					try {
						estacionamento.entrar(placa, vaga);
						JOptionPane.showMessageDialog(null, "Carro " + placa + " inserido com sucesso na vaga " + vaga, "Estacionamento realizado!", JOptionPane.PLAIN_MESSAGE);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro na inserção da vaga", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}					
				}
			}
		});
		
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Integer> vagasLivres = estacionamento.listarLivres();
				Integer[] vagasArray = vagasLivres.toArray(new Integer[vagasLivres.size()]);
				JOptionPane.showMessageDialog(null, vagasArray, "Lista de vagas livres", JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		button_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int vaga = Integer.parseInt(JOptionPane.showInputDialog(null, "Qual a vaga para sair?"));
				try {
					estacionamento.sair(vaga);
					JOptionPane.showMessageDialog(null, "O carro que estava na vaga " + vaga + " saiu com sucesso.", "Sair do estacionamento", JOptionPane.PLAIN_MESSAGE);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro ao sair!", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		
		button_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int vaga = Integer.parseInt(JOptionPane.showInputDialog(null, "Qual a vaga de origem?"));
				int vagaDestino = Integer.parseInt(JOptionPane.showInputDialog(null, "Qual a vaga de destino?"));
				try {
					estacionamento.transferir(vaga, vagaDestino);
					JOptionPane.showMessageDialog(null, "A vaga " + vaga + " foi transferida com sucesso para a vaga " + vagaDestino, "Transferência de vaga", JOptionPane.PLAIN_MESSAGE);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro na transferência", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		
		button_1_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String placa = JOptionPane.showInputDialog(null, "Qual a placa para consultar?");
				int vaga = estacionamento.consultarPlaca(placa);
				JOptionPane.showMessageDialog(null, "A vaga da placa " + placa + " é " + vaga, "Consulta de placa", JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		button_1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] vagasArray = estacionamento.listarGeral();
				JOptionPane.showMessageDialog(null, vagasArray, "Lista geral de vagas", JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		button_1_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					estacionamento.gravarDados();
					frame.dispose();
					JOptionPane.showMessageDialog(null, "Dados Salvos com sucesso!", "Sair do programa", JOptionPane.PLAIN_MESSAGE);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro ao gravar dados!", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
	}

}
