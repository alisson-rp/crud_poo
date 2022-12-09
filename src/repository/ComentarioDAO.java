package repository;

import modal.Comentario;
import modal.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDAO extends Conexao implements IGenericDAO<Comentario> {

    static List<Comentario> comentarios = new ArrayList<>();

    @Override
    public void salvar(Comentario comentario) {
        ComentarioDAO comentRepository = new ComentarioDAO();
        try {
            if (comentario.getId() != null) {
                comentRepository.update(comentario);
            } else {
                comentRepository.insere(comentario);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remover(Comentario comentario) throws SQLException, ClassNotFoundException {
        ComentarioDAO commentRepository = new ComentarioDAO();
        commentRepository.delete(comentario);
    }

    @Override
    public List<Comentario> buscarTodos() throws SQLException, ClassNotFoundException {

        ComentarioDAO comentRepository = new ComentarioDAO();
        try {
            comentarios = comentRepository.busca();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return comentarios;
    }

    @Override
    public List<Comentario> buscarPorNome(String nome) {
        List<Comentario> comentariosFiltradas = new ArrayList<>();
        for (Comentario coment : comentarios) {
            if (coment.getComentario().contains(nome)) {
                comentariosFiltradas.add(coment);
            }
        }
        return comentariosFiltradas;
    }

    public void insere(Comentario comentario) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("insert into comentarios (cd_comunicado, comentario, cd_usuario, dt_comentario)" +
                "value(?,?,?,?)");
        stmt.setInt(1, comentario.getComunicado().getId().intValue());
        stmt.setString(2, comentario.getComentario());
        stmt.setInt(3, comentario.getUsuario().getId().intValue());
        stmt.setString(4, comentario.getDataComentario().toString());
        int i = stmt.executeUpdate();
        System.out.println(i + " linhas inseridas");
        connection.close();
    }

    public List<Comentario> busca() throws SQLException, ClassNotFoundException {
        List<Comentario> comments = new ArrayList<>();
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM comentarios");
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            Comentario commnet = new Comentario();
            commnet.setId(resultSet.getLong(1));
            commnet.setComentario(resultSet.getString(2));
            Usuario usuario = UsuarioDAO.buscaPorId(resultSet.getLong(3));
            commnet.setUsuario(usuario);
            comments.add(commnet);
        }
        connection.close();
        return comments;
    }

    public static List<Comentario> buscaPorId(Long id) throws SQLException, ClassNotFoundException {
        List<Comentario> comments = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("select * from comentarios WHERE cd_comunicado  = ?");
        stmt.setLong(1, id);
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            Comentario comentario = new Comentario();
            comentario.setId(resultSet.getLong(1));
            comentario.setComunicado(ComunicadoDAO.buscaPorId(resultSet.getLong(2)));
            comentario.setComentario(resultSet.getString(3));
            comentario.setUsuario(UsuarioDAO.buscaPorId(resultSet.getLong(4)));
            comentario.setDataComentario(resultSet.getDate(5).toLocalDate());
            comments.add(comentario);
        }
        connection.close();
        return comments;
    }

    public static Comentario buscaPorIdED(Long id) throws SQLException, ClassNotFoundException {
        List<Comentario> comments = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("select * from comentarios WHERE item = ?");
        stmt.setLong(1, id);
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            Comentario comentario = new Comentario();
            comentario.setId(resultSet.getLong(1));
            comentario.setComentario(resultSet.getString(3));

            comments.add(comentario);
        }
        connection.close();
        return comments.get(0);
    }

    public void update(Comentario comentario) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("UPDATE comentarios SET comentario = ? WHERE item = ?");
        stmt.setString(1, comentario.getComentario());
        stmt.setInt(2, comentario.getId().intValue());

        int i = stmt.executeUpdate();
        System.out.println(i + "linhas atualizadas");
        connection.close();
    }

    public void delete(Comentario comment) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE from comentarios WHERE item = ?");
        stmt.setInt(1, comment.getId().intValue());
        stmt.executeUpdate();
        connection.close();
    }

}
