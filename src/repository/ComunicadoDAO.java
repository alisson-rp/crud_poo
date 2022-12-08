package repository;

import modal.Comunicado;
import modal.TipoComunicado;
import modal.TipoNoticiaUrgencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComunicadoDAO extends Conexao implements IGenericDAO<Comunicado> {

    static List<Comunicado> comunicados = new ArrayList<>();


    @Override
    public void salvar(Comunicado comunicado) {
        ComunicadoDAO comunicadoRepository = new ComunicadoDAO();
        try {
            if (comunicado.getId() != null) {
                comunicadoRepository.update(comunicado);
            } else {
                comunicadoRepository.insere(comunicado);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remover(Comunicado comunicado) throws SQLException, ClassNotFoundException {
        ComunicadoDAO comunicadoRepository = new ComunicadoDAO();
        comunicadoRepository.delete(comunicado);
    }

    @Override
    public List<Comunicado> buscarTodos() {
        System.out.println(comunicados);

        ComunicadoDAO comunicadoRepository = new ComunicadoDAO();
        try {
            comunicados = comunicadoRepository.busca();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return comunicados;
    }

    @Override
    public List<Comunicado> buscarPorNome(String nome) {
        List<Comunicado> comunicadoFiltradas = new ArrayList<>();
        for (Comunicado comunicado : comunicados) {
            if (comunicado.getTitulo().contains(nome)) {
                comunicadoFiltradas.add(comunicado);
            }
        }
        return comunicadoFiltradas;
    }

    public void insere(Comunicado comunicado) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("insert into comunicados (dt_cadastro,cd_setor,cd_tipoUrgencia,cd_tipoComunicado," +
                "cd_responsavel,titulo,descricao,curtidas) value(?,?,?,?,?,?,?,?)");
        stmt.setString(1, comunicado.getDataCadastro().toString());
        stmt.setInt(2, comunicado.getSetor().getId().intValue());
        stmt.setInt(3, comunicado.getTipoUrgencia().ordinal());
        stmt.setInt(4, comunicado.getTipoComunicado().ordinal());
        stmt.setInt(5, comunicado.getResponsavel().getId().intValue());
        stmt.setString(6, comunicado.getTitulo());
        stmt.setString(7, comunicado.getDescricao());
        stmt.setInt(8, comunicado.getQtdCurtidas());

        int i = stmt.executeUpdate();
        System.out.println(i + " linhas inseridas");
        connection.close();
    }

    public List<Comunicado> busca() throws SQLException, ClassNotFoundException {
        List<Comunicado> comunicados = new ArrayList<>();
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM comunicados");
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            Comunicado comunicado = new Comunicado();
            comunicado.setId(resultSet.getLong(1));
            comunicado.setDataCadastro(resultSet.getDate(2).toLocalDate());
            long idSetor = resultSet.getLong(3);
            comunicado.setSetor(SetorDAO.buscaPorId(idSetor));
            comunicado.setTipoUrgencia(TipoNoticiaUrgencia.getTipoById(resultSet.getInt(4)));
            comunicado.setTipoComunicado(TipoComunicado.getTipoById(resultSet.getInt(5)));
            long idResponsavel = resultSet.getLong(6);
            comunicado.setResponsavel(UsuarioDAO.buscaPorId(idResponsavel));
            comunicado.setTitulo(resultSet.getString(7));
            comunicado.setDescricao(resultSet.getString(8));
            comunicado.setQtdCurtidas(resultSet.getInt(9));
            comunicados.add(comunicado);
        }
        connection.close();
        return comunicados;
    }

    public static Comunicado buscaPorId(Long id) throws SQLException, ClassNotFoundException {
        List<Comunicado> comunicados = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("select * from comunicados WHERE cd_comunicado = ?");
        stmt.setLong(1, id);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            Comunicado comunicado = new Comunicado();
            comunicado.setId(resultSet.getLong(1));
        }
        connection.close();
        return comunicados.get(0);
    }

    public void update(Comunicado comunicado) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "UPDATE comunicados SET dt_cadastro = ?, cd_setor = ?, cd_tipoUrgencia = ? " +
                        "cd_tipoComunicado = ?, cd_responsavel = ?, titulo = ?, " +
                        "descricao = ?, curtidas = ?  WHERE cd_comunicado = ?");
        stmt.setString(1, comunicado.getDataCadastro().toString());
        stmt.setInt(2, comunicado.getSetor().getId().intValue());
        stmt.setInt(3, comunicado.getTipoUrgencia().ordinal());
        stmt.setInt(4, comunicado.getTipoComunicado().ordinal());
        stmt.setInt(5, comunicado.getResponsavel().getId().intValue());
        stmt.setString(6, comunicado.getTitulo());
        stmt.setString(7, comunicado.getDescricao());
        stmt.setInt(8, comunicado.getQtdCurtidas());

        int i = stmt.executeUpdate();
        System.out.println(i + "linhas atualizadas");
        connection.close();
    }

    public void delete(Comunicado comunicado) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE from comunicados WHERE cd_comunicado = ?");
        stmt.setInt(1, comunicado.getId().intValue());
        stmt.executeUpdate();
        connection.close();
    }

    public Object[] findComunicadoInArray() {
        List<Comunicado> comunicados = buscarTodos();
        List<String> comunicadosNomes = new ArrayList<>();

        for (Comunicado comunicado : comunicados) {
            comunicadosNomes.add(comunicado.getTitulo());
        }

        return comunicadosNomes.toArray();
    }
}
