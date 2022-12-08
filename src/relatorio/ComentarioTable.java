package relatorio;

import modal.Comentario;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class ComentarioTable extends AbstractTableModel {
    private static final long serialVersionUID = 1L;

    public static final int INDEX_COMENTARIO = 0;
    public static final int INDEX_USUARIO = 1;
    public static final int INDEX_DATA  = 2;
    public static final int INDEX_ESCONDIDO = 3;

    protected String[] nomeColunas;
    protected Vector<Comentario> vetorDados;

    public ComentarioTable(String[] columnNames, Vector<Comentario> vetorDados) {
        this.nomeColunas = columnNames;
        this.vetorDados = vetorDados;
    }

    @Override
    public String getColumnName(int column) {
        return nomeColunas[column];
    }

    @Override
    public boolean isCellEditable(int linha, int coluna) {
        if (coluna == INDEX_ESCONDIDO) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        Comentario registroComment = (Comentario) vetorDados.get(linha);
        switch (coluna) {
            case INDEX_COMENTARIO:
                return registroComment.getComentario();
            case INDEX_USUARIO:
                return registroComment.getUsuario().getUsuario();
            case INDEX_DATA:
                return registroComment.getDataComentario();
            default:
                return new Object();
        }
    }

    @Override
    public int getRowCount() {
        return vetorDados.size();
    }

    @Override
    public int getColumnCount() {
        return nomeColunas.length;
    }
}