package repository;

import modal.Setor;
import modal.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UsuarioDAO extends Conexao implements IGenericDAO<Usuario> {

    static List<Usuario> usuarios = new ArrayList<>();

    @Override
    public void salvar(Usuario usuario) {
        UsuarioDAO usuarioRepository = new UsuarioDAO();
        try {
            if (usuario.getId() != null) {
                usuarioRepository.update(usuario);
            } else {
                usuarioRepository.insere(usuario);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remover(Usuario usuario) throws SQLException, ClassNotFoundException {
        UsuarioDAO usuarioRepository = new UsuarioDAO();
        usuarioRepository.delete(usuario);
    }

    @Override
    public List<Usuario> buscarTodos() {
        System.out.println(usuarios);

        UsuarioDAO usuarioRepository = new UsuarioDAO();
        try {
            usuarios = usuarioRepository.busca();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return usuarios;
    }

    @Override
    public List<Usuario> buscarPorNome(String nome) {
        List<Usuario> pessoasFiltradas = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getUsuario().contains(nome)) {
                pessoasFiltradas.add(usuario);
            }
        }
        return pessoasFiltradas;
    }

    public void insere(Usuario usuario) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("insert into usuarios (lg_usuario,email,cd_setor) value(?,?,?)");
        stmt.setString(1, usuario.getUsuario());
        stmt.setString(2, usuario.getEmail());
        stmt.setInt(3, usuario.getSetor().getId().intValue());

        int i = stmt.executeUpdate();
        System.out.println(i + " linhas inseridas");
        connection.close();
    }

    public List<Usuario> busca() throws SQLException, ClassNotFoundException {
        List<Usuario> usuarios = new ArrayList<>();
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT cd_usuario, lg_usuario, email, cd_setor FROM usuarios");
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            Usuario usuario = new Usuario();
            usuario.setId(resultSet.getLong(1));
            usuario.setUsuario(resultSet.getString(2));
            usuario.setEmail(resultSet.getString(3));
            long idSetor = resultSet.getLong(4);
            usuario.setSetor(SetorDAO.buscaPorId(idSetor));
            usuarios.add(usuario);
        }
        connection.close();
        return usuarios;
    }

    public static Usuario buscaPorId(Long id) throws SQLException, ClassNotFoundException {
        List<Usuario> usuarios = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("select * from usuarios WHERE cd_usuario = ?");
        stmt.setLong(1, id);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            Usuario usuario = new Usuario();
            usuario.setId(resultSet.getLong(1));
            usuario.setUsuario(resultSet.getString(2));
            usuario.setEmail(resultSet.getString(3));
            long idSetor = resultSet.getLong(4);
            usuario.setSetor(SetorDAO.buscaPorId(idSetor));
            usuarios.add(usuario);
        }
        connection.close();
        return usuarios.get(0);
    }

    public void update(Usuario usuario) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("UPDATE usuarios SET lg_usuario = ?, email = ?, cd_setor = ? WHERE cd_usuario = ?");
        stmt.setString(1, usuario.getUsuario());
        stmt.setString(2, usuario.getEmail());
        stmt.setInt(3, usuario.getSetor().getId().intValue());
        stmt.setInt(4, usuario.getId().intValue());

        int i = stmt.executeUpdate();
        System.out.println(i + "linhas atualizadas");
        connection.close();
    }

    public void delete(Usuario usuario) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE from usuarios WHERE cd_usuario = ?");
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

    public Usuario findUsuarioByNome(String busca) {
        List<Usuario> usuarios = buscarTodos();
        for (Usuario usuario : usuarios) {
            if (busca.equals(usuario.getUsuario())) {
                return usuario;
            }
        }
        return null;
    }
}
