package br.com.faculdade.fotosensor;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.bridj.ann.Alignment;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

/**
 * @author welington sousa
 */
public class FramePrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	private static Webcam webcam = Webcam.getDefault();
	private WebcamPanel panel = new WebcamPanel(webcam);

	private JPanel painelPrincipal;

	/**
	 * Inicia a aplicação
	 */
	public static void main(String... args) {

		// ensina como a Thread deve executar
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					FramePrincipal frame = new FramePrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Cria o frame
	 */
	public FramePrincipal() {
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 163, 70);
		painelPrincipal = new JPanel();
		painelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelPrincipal);

		JButton botaoCapturar = new JButton("Capturar");
		botaoCapturar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// trata o evento
				try {
					capturaImagem();
					gravaTextoImagem();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		});

		// agrupa nosso layout
		GroupLayout contentLayout = new GroupLayout(painelPrincipal);
		contentLayout
				.setHorizontalGroup(contentLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
						contentLayout.createSequentialGroup()
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(botaoCapturar,
										GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		contentLayout.setVerticalGroup(
				contentLayout.createParallelGroup(Alignment.LEADING).addGroup(contentLayout.createSequentialGroup()
						.addComponent(botaoCapturar).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		painelPrincipal.setLayout(contentLayout);

		iniciaWebCam();
	}

	/**
	 * Inicia a webcam
	 */
	private void iniciaWebCam() {
		JFrame janela = new JFrame("Camera");
		janela.getContentPane().add(panel);
		janela.setResizable(false);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.pack();
		janela.setSize(540, 540);
		janela.setVisible(true);
	}

}
