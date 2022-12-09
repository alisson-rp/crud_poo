package relatorio;

import modal.Setor;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class SetorTable extends AbstractTableModel {
    private static final long serialVersionUID = 1L;

    public static final int INDEX_CODIGO = 0;
    public static final int INDEX_SETOR = 1;
    public static final int INDEX_ESCONDIDO = 2;

    protected String[] nomeColunas;
    protected Vector<Setor> vetorDados;

    public SetorTable(String[] columnNames, Vector<Setor> vetorDados) {
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
        Setor registroComment = (Setor) vetorDados.get(linha);
        switch (coluna) {
            case INDEX_CODIGO:
                return registroComment.getId();
            case INDEX_SETOR:
                return registroComment.getNome();
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
