package br.com.faculdade.fotosensor;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

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
		// captura imagem no tamanho VGA
		webcam.setViewSize(WebcamResolution.VGA.getSize());

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
		botaoCapturar.addActionListener((evento) -> { // lambda do java8
			// trata o evento
			try {
				capturaImagem();
				gravaTextoImagem();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ParseException e1) {
				e1.printStackTrace();
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

	// cria a tarja
	public static Graphics2D criaTarja(int x, String velocidade, String dataEHora) {
		BufferedImage bi = new BufferedImage(x, 30, BufferedImage.TYPE_INT_RGB);

		Graphics2D g = bi.createGraphics();
		g.fillRect(0, 0, bi.getWidth(), 40);
		return g;
	}

	// escreve na imagem
	private void gravaTextoImagem() throws IOException, ParseException {

		// Hora atual
		LocalDateTime agora = LocalDateTime.now();
		String dataEHora = agora.format(DateTimeFormatter.ofPattern("hh:mm:ss"));

		// Velocidade atual
		double max = Math.random() * 100;
		long i = Math.round(max);
		String velocidade = String.valueOf(i);

		BufferedImage imagem = webcam.getImage();

		BufferedImage bi = new BufferedImage(imagem.getWidth(), 30, BufferedImage.TYPE_INT_RGB);

		Graphics2D g = bi.createGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, bi.getWidth(), 40);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 12));
		g.drawString("Rua: " + "Avenida Mister Hull", bi.getWidth() - 625, 30);
		g.drawString("Velocidade: " + velocidade + " Km/h", bi.getWidth() - 625, 15);
		g.drawString("Hora: " + dataEHora, bi.getWidth() - 125, 20);

		BufferedImage bufferTotal = new BufferedImage(imagem.getWidth(), imagem.getHeight() + 40,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D grapTotal = bufferTotal.createGraphics();
		grapTotal.drawImage(imagem, 0, 0, null);
		grapTotal.drawImage(bi, 0, imagem.getHeight(), null);

		ImageIO.write(bufferTotal, "PNG", new File("foto.png"));
	}

	// captura a imagem
	private void capturaImagem() throws IOException {
		BufferedImage imagem = webcam.getImage();
		ImageIO.write(imagem, "PNG", new File("foto.png"));
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
