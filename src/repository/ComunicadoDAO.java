package repository;

import modal.Comunicado;
import modal.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static repository.Conexao.getConnection;

public class ComunicadoDAO implements IGenericDAO<Comunicado>{

    static List<Comunicado> comunicados = new ArrayList<>();


    @Override
    public void salvar(Comunicado comunicado) {
        ComunicadoDAO comunicadoRepository = new ComunicadoDAO();
        try {
            if (comunicado.getId() == null) {
                comunicadoRepository.update(comunicado);
            } else {
                comunicado.setId(comunicadoRepository.proximoId().longValue());
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
    /// ver como faz a interface para a conexão..
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/seguradora"; // trocar nome do banco
        Connection connection = DriverManager.getConnection(url, "root", "");

        return connection;
    }

    public void insere(Comunicado comunicado) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("insert into comunicados value(?,?,?,?)");
        stmt.setInt(1, comunicado.getId().intValue());
        stmt.setString(2, comunicado.getDataCadastro().toString());
        stmt.setInt(3, comunicado.getSetor().getId().intValue());
        stmt.setInt(4, comunicado.getResponsavel().getId().intValue());
        stmt.setString(5, comunicado.getTitulo());
        stmt.setString(6, comunicado.getDescricao());
        stmt.setInt(7, comunicado.getQtdCurtidas());
        /// comentar com o bruno como trazer o numero do enum ...
        stmt.setString(8, comunicado.getTipoUrgencia().toString());
        stmt.setString(9, comunicado.getTipoComunicado().toString());

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
            long idResponsavel = resultSet.getLong(4);
            comunicado.setResponsavel(UsuarioDAO.buscaPorId(idResponsavel));
            comunicado.setTitulo(resultSet.getString(5));
            comunicado.setDescricao(resultSet.getString(6));
            comunicado.setQtdCurtidas(resultSet.getInt(7));
            //resolver questão do enum ...
            comunicado.setTipoUrgencia(resultSet.getInt(8));
            comunicado.setTipoComunicado(resultSet.getInt(9));
        }
        connection.close();
        return comunicados;
    }

    public Comunicado buscaPorId(Long id) throws SQLException, ClassNotFoundException {
        List<Comunicado> comunicados = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("select * from comunicados WHERE id = ?");
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
                        "descricao = ?, curtidas = ?  WHERE id = ?");
        stmt.setString(1, comunicado.getDataCadastro().toString());
        stmt.setInt(2, comunicado.getSetor().getId().intValue());
        /// comentar com o bruno como trazer o numero do enum ...
        stmt.setString(3, comunicado.getTipoUrgencia().toString());
        stmt.setString(4, comunicado.getTipoComunicado().toString());
        stmt.setInt(5, comunicado.getResponsavel().getId().intValue());
        stmt.setString(6, comunicado.getTitulo());
        stmt.setString(7, comunicado.getDescricao());
        stmt.setInt(8, comunicado.getQtdCurtidas());

        int i = stmt.executeUpdate();
        System.out.println(i + "linhas atualizadas");
        connection.close();
    }

    public Integer proximoId() throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT max(id) from comunicados");
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            return resultSet.getInt(1) + 1;
        }
        return 1;
    }

    public void delete(Comunicado comunicado) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE from comunicados WHERE id = ?");
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
