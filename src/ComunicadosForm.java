import modal.Comunicado;
import relatorio.ComunicadosTable;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class ComunicadosForm extends JPanel {

    public static final long serialVersionUID = 1L;

    public static final String[] nomeColunas =
            {"Codigo", "Titulo", "Data", "Setor", "Urgencia", "Tipo", "Responsavel", "Curtidas", ""};

    protected JTable table;
    protected JScrollPane scroller;
    protected ComunicadosTable tabela;

    public ComunicadosForm(Vector<Comunicado> vetorDados) {
        iniciarComponentes(vetorDados);
    }

    public void iniciarComponentes(Vector<Comunicado> vetorDados) {
        tabela = new ComunicadosTable(nomeColunas, vetorDados);
        table = new JTable();
        table.setModel(tabela);
        table.setSurrendersFocusOnKeystroke(true);
        scroller = new javax.swing.JScrollPane(table);
        table.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));

        TableColumn colunaEscondida = table.getColumnModel().getColumn(ComunicadosTable.INDEX_ESCONDIDO);
        colunaEscondida.setMinWidth(2);
        colunaEscondida.setPreferredWidth(2);
        colunaEscondida.setMaxWidth(2);
        setLayout(new BorderLayout());
        add(scroller, BorderLayout.CENTER);
    }

    public static void emitirRelatorioComunicados(List<Comunicado> comunicados) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            JFrame frame = new JFrame("Comunicados");

            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) {
                    frame.setVisible(false);
                    try {
                        AppMain.ChamarMenuPrincipal();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            Vector<Comunicado> vetorDados = new Vector<Comunicado>();
            for (Comunicado noticia : comunicados) {
                vetorDados.add(noticia);
            }

            frame.getContentPane().add(new ComunicadosForm(vetorDados));
            frame.pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
