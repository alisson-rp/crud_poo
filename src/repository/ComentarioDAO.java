package repository;

import modal.Comentario;
import modal.Setor;
import modal.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static repository.Conexao.getConnection;

public class ComentarioDAO implements IGenericDAO<Comentario>{

    static List<Comentario> comentarios = new ArrayList<>();

    @Override
    public void salvar(Comentario comentario) {
        ComentarioDAO comentRepository = new ComentarioDAO();
        try {
            if(comentario.getId() != null) {
                comentRepository.update(comentario);
            } else {
                comentario.setId(comentRepository.proximoId().longValue());
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        comentarios.add(comentario);
    }

    @Override
    public void remover(Comentario comentario) throws SQLException, ClassNotFoundException {
        ComentarioDAO commentRepository = new ComentarioDAO();
        commentRepository.delete(comentario);
    }

    @Override
    public List<Comentario> buscarTodos() throws SQLException, ClassNotFoundException {

        ComentarioDAO commentRepository = new ComentarioDAO();
        try {
            comentarios = commentRepository.busca();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return comentarios;
    }

    @Override
    public List<Comentario> buscarPorNome(String nome) {
        return null;
    }

    public void update(Comentario comentario) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("UPDATE setores SET comentario = ?, dt_comentario ? WHERE id = ?");
        stmt.setString(1, comentario.getComentario());
        //stmt.setDate(2, comentario.getDataComentario());
        stmt.setInt(3,comentario.getId().intValue());



        int i = stmt.executeUpdate();
        System.out.println(i + "linhas atualizadas");
        connection.close();
    }

    public void delete(Comentario comment) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE from comentarios WHERE id = ?");
        stmt.setInt(1, comment.getId().intValue());
        stmt.executeUpdate();
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
            Usuario usuario = UsuarioDAO.buscarPorID(resultSet.getLong(3));
            commnet.setUsuario(usuario);
            //commnet.setDataComentario(resultSet.getDate(3));
            comments.add(commnet);
        }
        connection.close();
        return comments;
    }

    public  Integer proximoId() throws SQLException , ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT max(item) from comentarios");
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            return resultSet.getInt(1) +1;
        }
        return 1;
    }
}