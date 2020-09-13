package socket;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class FormClient extends JFrame {

	private JPanel contentPane;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormClient frame = new FormClient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FormClient() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 88, 403, 163);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		JButton btnPenduraServidor = new JButton("Inicia Comunicacao com servidor");
		btnPenduraServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new cliente().start();
			}
		});
		btnPenduraServidor.setBounds(22, 28, 286, 25);
		contentPane.add(btnPenduraServidor);
	}

	class cliente extends Thread {
		public void run() {
			try {
				textArea.append("Iniciando conexao com o servidor..\n");
				Socket socket = new Socket("192.168.0.4", 2345);
				
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				objectOutputStream.flush();
				objectOutputStream.writeObject("Conectando ao servidor");
				objectOutputStream.writeObject("Os dados dessa conexao " + socket.toString());
				objectOutputStream.writeObject("FIM");
				
				textArea.append("Comunicacao feita com sucesso\n");
				textArea.append("Servidor Aceitoo:" + socket.getInetAddress().toString());
				textArea.append("Recebendo mensagens vindas do servidor\n");
				ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
				String textoServidor = "";
				while (!textoServidor.equals("FIM")) {
					textoServidor = (String) objectInputStream.readObject();
					textArea.append(textoServidor + "\n");
				}
				textArea.append("FIM");
				socket.close();
			} catch (Exception erro) {
				JOptionPane.showMessageDialog(null, erro);
			}
		}
	}
}