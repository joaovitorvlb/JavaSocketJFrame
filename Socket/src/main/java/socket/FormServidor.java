package socket;

import java.awt.EventQueue;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class FormServidor extends JFrame {

	private JPanel contentPane;
	JTextArea textArea;
	private InputStream inBS;
	private BufferedInputStream inputS;

	byte[] messageByte = new byte[1000];
	boolean end = false;
	String dataString = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormServidor frame = new FormServidor();
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
	public FormServidor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 756, 428);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 188, 732, 203);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		JButton btntartServer = new JButton("Iniciar servidor");
		btntartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new servidor().start();
			}
		});
		btntartServer.setBounds(23, 12, 158, 25);
		contentPane.add(btntartServer);
	}

	class servidor extends Thread {
		public void run() {
			textArea.append("Criando o servidor socket...\n");
			try {
				@SuppressWarnings("resource")
				ServerSocket serverSocket = new ServerSocket(9765);
				textArea.append("Servidor rodando na porta 2345\n");
				while (true) {
					//textArea.append("Aguardando conexao de cliente ..\n");
					Socket socket = serverSocket.accept();
					//textArea.append("Conexao esta aberto com cliente.: " + socket.getInetAddress().toString() + "\n");
					//textArea.append("Enviando dados para o cliente..\n");

					DataInputStream is = new DataInputStream(socket.getInputStream());
					String responseLine;
					responseLine = is.readLine();
					//System.out.println("echo: " + responseLine);
					textArea.append(responseLine + "\n");

				}
			} catch (IOException ex) {
				// JOptionPane.showMessageDialog(null, "Erro " + ex);
				ex.printStackTrace();
			}
		}
	}
}