package service;

import pl.lebiedzinski.Rastaman;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RastamanManager {

    public void addRastaman(Rastaman rastaman);

    void dropDatatable() throws SQLException;

    public Connection getConnection();

    public void setConnection(Connection connection) throws SQLException;

    public List<Rastaman> getAll();

    Rastaman getById(int id) throws SQLException;

    public void deleteRastaman(Rastaman rastaman);

    void updateRastaman(int oldId, Rastaman newRastaman) throws SQLException;
}
