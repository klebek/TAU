package service;

import pl.lebiedzinski.Rastaman;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RastamanManager {

    public Connection getConnection();
    public List<Rastaman> getAll();

    public void deleteRastaman(Rastaman rastaman);
    public void updateRastaman(int oldId, Rastaman newRastaman) throws SQLException;
    public void dropDatatable() throws SQLException;
    public void setConnection(Connection connection) throws SQLException;
    public void addRastaman(Rastaman rastaman);

    Rastaman getById(int id) throws SQLException;
}