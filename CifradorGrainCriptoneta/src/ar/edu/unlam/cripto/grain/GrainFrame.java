package ar.edu.unlam.cripto.grain;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GrainFrame extends JFrame {

	private static final long serialVersionUID = 3874484081279429158L;
	private JPanel contentPane;
	private JTextField txtFieldKey;
	private JTextField txtFieldSeed;
	private BufferedImage initialImage = null;
	private BufferedImage finalImage = null;
	private byte[] imageByteArray;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GrainFrame frame = new GrainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GrainFrame() {
		setFont(new Font("Dialog", Font.BOLD, 12));
		setTitle("GRAIN V1 - 80 bits");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 584, 604);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(this);
		setResizable(false);

		JLabel lblAlumnos = new JLabel(
				"<html><B>Alumnos:</B><BR><BR>Miño, Maximiliano Alexander<BR>Vernet, Federico<BR>Oria, Matias<BR>Corcione, Luciano Ismael<BR>Lo Giudice, Diego Andrés</html>");
		lblAlumnos.setVerticalAlignment(SwingConstants.TOP);
		lblAlumnos.setToolTipText("");
		lblAlumnos.setFont(new Font("DialogInput", Font.BOLD, 16));
		lblAlumnos.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlumnos.setBounds(274, 10, 296, 161);
		contentPane.add(lblAlumnos);

		JPanel panelImagenInicial = new JPanel();
		panelImagenInicial.setBorder(new LineBorder(Color.DARK_GRAY));
		panelImagenInicial.setBounds(20, 10, 250, 250);
		panelImagenInicial.setBackground(Color.WHITE);
		contentPane.add(panelImagenInicial);
		panelImagenInicial.setLayout(null);

		final JLabel lblImagenInicial = new JLabel("");
		lblImagenInicial.setBounds(0, 0, 250, 250);
		panelImagenInicial.add(lblImagenInicial);
		panelImagenInicial.revalidate();
		panelImagenInicial.repaint();

		JPanel panelImagenFinal = new JPanel();
		panelImagenFinal.setBorder(new LineBorder(Color.DARK_GRAY));
		panelImagenFinal.setBounds(20, 290, 250, 250);
		panelImagenFinal.setBackground(Color.WHITE);
		contentPane.add(panelImagenFinal);
		panelImagenFinal.setLayout(null);

		final JLabel lblImagenFinal = new JLabel("");
		lblImagenFinal.setBounds(0, 0, 250, 250);
		panelImagenFinal.add(lblImagenFinal);
		panelImagenFinal.revalidate();
		panelImagenFinal.repaint();

		JLabel lblClave = new JLabel("Clave");
		lblClave.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblClave.setHorizontalAlignment(SwingConstants.CENTER);
		lblClave.setBounds(274, 424, 83, 14);
		contentPane.add(lblClave);

		JLabel lblNewLabel = new JLabel("(10 caracteres)");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.ITALIC, 9));
		lblNewLabel.setBounds(471, 426, 99, 14);
		contentPane.add(lblNewLabel);

		txtFieldKey = new JTextField();
		txtFieldKey.setText("1234567890");
		txtFieldKey.setToolTipText("");
		txtFieldKey.setBounds(370, 422, 99, 20);
		contentPane.add(txtFieldKey);
		txtFieldKey.setColumns(10);

		JLabel lblSemilla = new JLabel("Semilla");
		lblSemilla.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSemilla.setHorizontalAlignment(SwingConstants.CENTER);
		lblSemilla.setBounds(270, 452, 99, 14);
		contentPane.add(lblSemilla);

		JLabel lblCaracteres = new JLabel("(8 caracteres)");
		lblCaracteres.setHorizontalAlignment(SwingConstants.CENTER);
		lblCaracteres.setFont(new Font("Tahoma", Font.ITALIC, 9));
		lblCaracteres.setBounds(471, 454, 99, 14);
		contentPane.add(lblCaracteres);

		txtFieldSeed = new JTextField();
		txtFieldSeed.setText("12345678");
		txtFieldSeed.setBounds(370, 450, 99, 20);
		contentPane.add(txtFieldSeed);
		txtFieldSeed.setColumns(10);

		JButton btnAbrirImagen = new JButton("Abrir imagen");
		btnAbrirImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("Archivos de imagen",
						"jpg", "jpeg", "png", "bmp", "gif");
				fileChooser.setFileFilter(fileNameExtensionFilter);

				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					String fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1).toLowerCase();
					if (!Arrays.asList("jpg", "jpeg", "png", "bmp", "gif").contains(fileExtension)) {
						JOptionPane.showMessageDialog(null,
								"Debe seleccionar un archivo de imagen (jpg, jpeg, png, bmp, gif)", "Archivo inválido",
								JOptionPane.WARNING_MESSAGE);
						return;
					}

					try {
						initialImage = ImageIO.read(file);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(GrainFrame.this, "Error al abrir la imagen para cifrar", "Error",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}

					ImageIcon imageIcon = new ImageIcon(initialImage.getScaledInstance(250, 250, Image.SCALE_SMOOTH));
					lblImagenInicial.setIcon(imageIcon);
					lblImagenFinal.setIcon(null);
					finalImage = null;
					imageByteArray = null;
				}
			}
		});
		btnAbrirImagen.setBounds(20, 263, 250, 23);
		contentPane.add(btnAbrirImagen);

		JButton btnGuardarImagen = new JButton("Guardar imagen");
		btnGuardarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (finalImage == null) {
					JOptionPane.showMessageDialog(null, "Debe cifrar una imagen para poder guardarla",
							"Imagen inválida", JOptionPane.WARNING_MESSAGE);
					return;
				}

				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showSaveDialog(null);
				File file = null;

				if (result == JFileChooser.APPROVE_OPTION) {
					file = new File(fileChooser.getSelectedFile().getAbsolutePath() + ".bmp");
				}

				try {
					ImageIO.write(finalImage, "bmp", file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnGuardarImagen.setBounds(20, 542, 250, 23);
		contentPane.add(btnGuardarImagen);

		JButton btnCifrarDescifrar = new JButton("Cifrar / Descifrar");
		btnCifrarDescifrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (txtFieldKey.getDocument().getLength() != 10) {
					JOptionPane.showMessageDialog(null, "La clave debe ser de 10 caracteres", "Clave inválida",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (txtFieldSeed.getDocument().getLength() != 8) {
					JOptionPane.showMessageDialog(null, "La semilla debe ser de 8 caracteres", "Semilla inválida",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (initialImage == null) {
					JOptionPane.showMessageDialog(null, "Debe cargar una imagen para cifrar", "Imagen inválida",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				try {
					imageByteArray = ImageConverter.imageToByteArray(initialImage);
					Grain grain = new Grain(txtFieldKey.getText().getBytes(), txtFieldSeed.getText().getBytes(),
							imageByteArray);
					finalImage = ImageConverter.byteArrayToImage(grain.xor());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				ImageIcon imageIcon = new ImageIcon(finalImage.getScaledInstance(250, 250, Image.SCALE_SMOOTH));
				lblImagenFinal.setIcon(imageIcon);
			}
		});
		btnCifrarDescifrar.setBounds(288, 542, 181, 23);
		contentPane.add(btnCifrarDescifrar);

		JButton btnClear = new JButton("Borrar todo");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtFieldKey.setText("");
				txtFieldSeed.setText("");
				lblImagenInicial.setIcon(null);
				lblImagenFinal.setIcon(null);
				initialImage = null;
				finalImage = null;
				imageByteArray = null;
			}
		});
		btnClear.setBounds(288, 482, 118, 23);
		contentPane.add(btnClear);

		JButton btnDefaultValues = new JButton("Valores iniciales");
		btnDefaultValues.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtFieldKey.setText("1234567890");
				txtFieldSeed.setText("12345678");
			}
		});
		btnDefaultValues.setBounds(285, 506, 155, 23);
		contentPane.add(btnDefaultValues);
		
		JLabel lblInstrucciones = new JLabel("<html><B>Modo de uso:</B><BR><BR>Miño, Maximiliano Alexander<BR>Vernet, Federico<BR>Oria, Matias<BR>Corcione, Luciano Ismael<BR>Lo Giudice, Diego Andrés</html>");
		lblInstrucciones.setVerticalAlignment(SwingConstants.TOP);
		lblInstrucciones.setToolTipText("");
		lblInstrucciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblInstrucciones.setFont(new Font("DialogInput", Font.BOLD, 16));
		lblInstrucciones.setBounds(274, 183, 296, 161);
		contentPane.add(lblInstrucciones);

	}
}
