package relatorio;

import modal.Comunicado;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class ComunicadosTable extends AbstractTableModel {
    private static final long serialVersionUID = 1L;

    public static final int INDEX_CODIGO = 0;
    public static final int INDEX_TITULO = 1;
    public static final int INDEX_DATA = 2;
    public static final int INDEX_SETOR = 3;
    public static final int INDEX_TPURGENCIA = 4;
    public static final int INDEX_TPCOMUNICADO = 5;
    public static final int INDEX_RESPONSAVEL = 6;
    public static final int INDEX_CURTIDAS = 7;
    public static final int INDEX_ESCONDIDO = 8;

    protected String[] nomeColunas;
    protected Vector<Comunicado> vetorDados;

    public ComunicadosTable(String[] columnNames, Vector<Comunicado> vetorDados) {
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
        Comunicado registroComment = (Comunicado) vetorDados.get(linha);
        switch (coluna) {
            case INDEX_CODIGO:
                return registroComment.getId();
            case INDEX_TITULO:
                return registroComment.getTitulo();
            case INDEX_DATA:
                return registroComment.getDataCadastro();
            case INDEX_SETOR:
                return registroComment.getSetor().getNome();
            case INDEX_TPURGENCIA:
                return registroComment.getTipoUrgencia();
            case INDEX_TPCOMUNICADO:
                return registroComment.getTipoComunicado();
            case INDEX_RESPONSAVEL:
                return registroComment.getResponsavel().getUsuario();
            case INDEX_CURTIDAS:
                return registroComment.getQtdCurtidas();
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
