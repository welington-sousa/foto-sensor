package br.com.faculdade.fotosensor;

import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * @author welington sousa
 */
public class FramePrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

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

}
