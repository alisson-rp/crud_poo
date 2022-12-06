package repository;

import modal.Comunicado;
import modal.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComunicadoDAO implements IGenericDAO<Comunicado>{

    static List<Comunicado> Comunicados = new ArrayList<>();


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
    public void remover(Comunicado objeto) throws SQLException, ClassNotFoundException {

    }

    @Override
    public List<Comunicado> buscarTodos() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<Comunicado> buscarPorNome(String nome) {
        return null;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/seguradora"; // trocar nome do banco
        Connection connection = DriverManager.getConnection(url, "root", "");

        return connection;
    }

    public void insere(Comunicado comunicado) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("insert into usuarios value(?,?,?,?)");
        stmt.setInt(1, comunicado.getId().intValue());
        stmt.setString(2, comunicado.getDataCadastro().toString());
        stmt.setInt(3, comunicado.getSetor().getId().intValue());
        stmt.setInt(4, comunicado.getUsuario().getId().intValue());
        stmt.setString(2, usuario.getUsuario());
        stmt.setString(3, usuario.getEmail());
        stmt.setInt(4, usuario.getSetor().getId().intValue());

        int i = stmt.executeUpdate();
        System.out.println(i + " linhas inseridas");
        connection.close();
    }
}
