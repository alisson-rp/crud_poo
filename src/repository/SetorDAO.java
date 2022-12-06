package repository;

import modal.Setor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static repository.Conexao.getConnection;

public class SetorDAO implements IGenericDAO<Setor>{

    static List<Setor> setores = new ArrayList<>();

    @Override
    public void salvar(Setor setor) {
        SetorDAO setorRepository = new SetorDAO();
        try {
            if(setor.getId() != null) {
                setorRepository.update(setor);
            } else {
                setor.setId(setorRepository.proximoId().longValue());
                setorRepository.insere(setor);
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        setores.add(setor);
    }

    @Override
    public void remover(Setor setor) throws SQLException, ClassNotFoundException {
        SetorDAO setorRepository = new SetorDAO();
        setorRepository.delete(setor);
    }

    @Override
    public List<Setor> buscarTodos() {
        System.out.println(setores);
        SetorDAO setorRepository = new SetorDAO();
        try {
            setores = setorRepository.busca();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return setores;
    }

    @Override
    public List<Setor> buscarPorNome(String nome) {
        List<Setor> setoresFiltradas = new ArrayList<>();
        for (Setor setor : setores) {
            if (setor.getNome().contains(nome)) {
                setoresFiltradas.add(setor);
            }
        }
        return setoresFiltradas;
    }


    public void insere(Setor setor) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("insert into setores value(?,?)");
        stmt.setInt(1,setor.getId().intValue());
        stmt.setString(2,setor.getNome());

        int i = stmt.executeUpdate();
        System.out.println(i + " linhas inseridas");
        connection.close();
    }

    public List<Setor> busca()  throws SQLException, ClassNotFoundException {
        List<Setor> setores = new ArrayList<>();
        Connection connection = getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM setores");
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            Setor setor = new Setor();
            setor.setId(resultSet.getLong(1));
            setor.setNome(resultSet.getString(2));
            setores.add(setor);
        }
        connection.close();
        return setores;
    }

    public static Setor buscaPorId(Long id) throws SQLException, ClassNotFoundException {
        List<Setor> setores = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("select * from setores WHERE id = ?");
        stmt.setLong(1,id);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            Setor setor = new Setor();
            setor.setId(resultSet.getLong(1));
            setor.setNome(resultSet.getString(2));
            setores.add(setor);
        }
        connection.close();
        return setores.get(0);
    }

    public void update(Setor setor) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("UPDATE setores SET nome = ? WHERE id = ?");
        stmt.setString(1, setor.getNome());
        stmt.setInt(2,setor.getId().intValue());



        int i = stmt.executeUpdate();
        System.out.println(i + "linhas atualizadas");
        connection.close();
    }

    public  Integer proximoId() throws SQLException , ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT max(id) from setores");
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            return resultSet.getInt(1) +1;
        }
        return 1;
    }

    public void delete(Setor setor) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE from setores WHERE id = ?");
        stmt.setInt(1, setor.getId().intValue());
        stmt.executeUpdate();
        connection.close();
    }

    public Object[] findSetoresInArray() {
        List<Setor> setores = buscarTodos();
        List<String> setoresNomes = new ArrayList<>();

        for (Setor setor : setores) {
            setoresNomes.add(setor.getNome());
        }

        return setoresNomes.toArray();
    }
}
