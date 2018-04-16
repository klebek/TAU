package service;

import pl.lebiedzinski.Rastaman;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RastamanManager {

    public Connection getConnection();
    public List<Rastaman> getAll();
    public Rastaman getById(int id) throws SQLException;
    public int deleteRastaman(Rastaman rastaman) throws SQLException;
    public int updateRastaman(int oldId, Rastaman newRastaman) throws SQLException;
    public void dropDatatable() throws SQLException;
    public void setConnection(Connection connection) throws SQLException;
    public int addRastaman(Rastaman rastaman);
    public int deleteByIdStatement(Rastaman rastaman) throws SQLException;
}
