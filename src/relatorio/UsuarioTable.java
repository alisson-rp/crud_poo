package relatorio;

import modal.Usuario;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class UsuarioTable extends AbstractTableModel {
    private static final long serialVersionUID = 1L;

    public static final int INDEX_CODIGO = 0;
    public static final int INDEX_USUARIO = 1;
    public static final int INDEX_EMAIL  = 2;
    public static final int INDEX_SETOR  = 3;
    public static final int INDEX_ESCONDIDO = 4;

    protected String[] nomeColunas;
    protected Vector<Usuario> vetorDados;

    public UsuarioTable(String[] columnNames, Vector<Usuario> vetorDados) {
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
        Usuario registroComment = (Usuario) vetorDados.get(linha);
        switch (coluna) {
            case INDEX_CODIGO:
                return registroComment.getId();
            case INDEX_USUARIO:
                return registroComment.getUsuario();
            case INDEX_EMAIL:
                return registroComment.getEmail();
            case INDEX_SETOR:
                return registroComment.getSetor().getNome();
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
