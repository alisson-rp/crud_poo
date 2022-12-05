package repository;

import modal.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements IGenericDAO<Usuario>{

    static List<Usuario> usuarios = new ArrayList<>();

    @Override
    public void salvar(Usuario usuario) {
        UsuarioDAO usuarioRepository = new UsuarioDAO();
        try {
            if(usuario.getId() == null) {
                usuarioRepository.update(usuario);
            } else {
                usuario.setId(usuarioRepository.proximoId().longValue());
                usuarioRepository.insere(usuario);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remover(Usuario usuario) throws SQLException, ClassNotFoundException {
        UsuarioDAO usuarioRepository = new UsuarioDAO();
        UsuarioDAO.delete(usuario);
    }

    @Override
    public List<Usuario> buscarTodos() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<Usuario> buscarPorNome(String nome) {
        return null;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/seguradora"; // trocar nome do banco
        Connection connection = DriverManager.getConnection(url, "root","");

        return connection;
    }

    public void insere(Usuario usuario) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("insert into usuarios value(?,?,?,?)");
        stmt.setInt(1, usuario.getId().intValue());
        stmt.setString(2, usuario.getUsuario());
        stmt.setString(3, usuario.getEmail());
        stmt.setInt(4, usuario.getSetor().getId().intValue());

        int i = stmt.executeUpdate();
        System.out.println(i + " linhas inseridas");
        connection.close();
    }

    public List<Usuario> busca() throws SQLException, ClassNotFoundException {
        List<Usuario> usuarios = new ArrayList<>();
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM usuarios");
        ResultSet resultSet = stmt.executeQuery();

        while(resultSet.next()){
            Usuario usuario = new Usuario();
            usuario.setId(resultSet.getLong(1));
            usuario.setUsuario(resultSet.getString(2));
            usuario.setEmail(resultSet.getString(3));
            long idSetor = resultSet.getLong(4);
            usuario.setSetor(SetorDAO.buscaPorId(idSetor));
        }
        connection.close();
        return usuarios;
    }

    public  List<Usuario> buscaPorId(Long id) throws SQLException, ClassNotFoundException {
        List<Usuario> usuarios = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("select * from usuarios WHERE id = ?");
        stmt.setLong(1,id);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {

            Usuario usuario = new Usuario();
            usuario.setId(resultSet.getLong(1));
            usuario.setUsuario(resultSet.getString(2));
            usuario.setEmail(resultSet.getString(3));
            long idSetor = resultSet.getLong(4);
            usuario.setSetor(SetorDAO.buscaPorId(idSetor));
        }
        connection.close();
        return usuarios;
    }

    public void update(Usuario usuario) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("UPDATE usuarios SET lg_usuario = ?, email = ?, cd_setor = ? WHERE id = ?");
        stmt.setString(1, usuario.getUsuario());
        stmt.setString(2, usuario.getEmail());
        stmt.setInt(3, usuario.getSetor().getId().intValue());
        stmt.setInt(4, usuario.getId().intValue());

        int i = stmt.executeUpdate();
        System.out.println(i + "linhas atualizadas");
        connection.close();
    }

    public  Integer proximoId() throws SQLException , ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT max(id) from usuarios");
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            return resultSet.getInt(1) +1;
        }
        return 1;
    }

    public void delete(Usuario usuario) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE from pessoas WHERE id = ?");
        stmt.setInt(1, usuario.getId().intValue());
        stmt.executeUpdate();
        connection.close();
    }

    public Object[] findUsuariosInArray() {
        List<Usuario> usuarios = buscarTodos();
        List<String> usuariosNomes = new ArrayList<>();

        for (Usuario pessoa : usuarios) {
            usuariosNomes.add(pessoa.getUsuario());
        }

        return usuariosNomes.toArray();
    }
}