package service;

import pl.lebiedzinski.Rastaman;
import repository.RastamanRepositoryFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RastamanManagerImpl extends RastamanRepositoryFactory implements RastamanManager {

    private Connection connection;

    private PreparedStatement addRastamanStatement;
    private PreparedStatement getAllRastamanStatement;
    private PreparedStatement deleteRastamanStatement;
    private PreparedStatement updateRastamanStatement;
    private PreparedStatement getByIdRastamanStatement;

    public RastamanManagerImpl(Connection connection) throws SQLException {
        this.connection = connection;
        if (!isDatabaseReady()) {
            createTables();
        }
        setConnection(connection);
    }

    public RastamanManagerImpl() {

    }

    public void createTables() throws SQLException {
        connection.createStatement().executeUpdate(
                "CREATE TABLE "
                        + "Rastaman(id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, " +
                        "name varchar(20) NOT NULL, " + "grams integer NOT NULL," + "type varchar(20) NOT NULL");
    }

    public boolean isDatabaseReady() {
        try {
            ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
            boolean tableExists = false;
            while (rs.next()) {
                if ("RastamanList".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
                    tableExists = true;
                    break;
                }
            }
            return tableExists;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void addRastaman(Rastaman rastaman) {
        try {
            addRastamanStatement.setString(1, rastaman.getName());
            addRastamanStatement.setInt(1, rastaman.getGrams());
            addRastamanStatement.setString(1, rastaman.getType());
            addRastamanStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
    }

    /*@Override
    public void deleteRastaman(Rastaman rastaman) {
        try {
            deleteRastamanStatement.executeQuery();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
    }*/


    public Connection getConnection() {
        return connection;
    }

    /**
     * @param connection the connection to set
     */
    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;
        addRastamanStatement = connection.prepareStatement("INSERT INTO Rastaman (name) VALUES (?)");
        getAllRastamanStatement = connection.prepareStatement("SELECT * FROM Rastaman");
        getByIdRastamanStatement = connection.prepareStatement("SELECT * FROM Rastaman WHERE id = ?");
        deleteRastamanStatement = connection.prepareStatement("DELETE FROM Rastaman");
        updateRastamanStatement = connection.prepareStatement("UPDATE Rastaman SET name = ? WHERE id = ?");
    }


    @Override
    public List<Rastaman> getAll() {
        List<Rastaman> RastamanList = new LinkedList<>();
        try {
            ResultSet rs = getAllRastamanStatement.executeQuery();
            while (rs.next()) {
                Rastaman r = new Rastaman();
                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                r.getGrams();
                r.getType();
                RastamanList.add(r);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        return RastamanList;
    }

    @Override
    public Rastaman getById(int id) throws SQLException {
        getByIdRastamanStatement.setInt(1, id);
        ResultSet rs = getByIdRastamanStatement.executeQuery();
        if (rs.next()) {
            Rastaman rastaman = new Rastaman();
            rastaman.setId(rs.getInt("id"));
            rastaman.setName(rs.getString("name"));
            rastaman.setGrams(rs.getInt("grams"));
            rastaman.setType(rs.getString("type"));
            return rastaman;
        } else {
            return null;
        }
    }

    @Override
    public void deleteRastaman(Rastaman rastaman) {}

    @Override
    public void updateRastaman(int oldId, Rastaman newRastaman) throws SQLException{
        updateRastamanStatement.setInt(2, oldId);
        updateRastamanStatement.setString(2, newRastaman.getName());
        updateRastamanStatement.setInt(2, newRastaman.getGrams());
        updateRastamanStatement.setString(2, newRastaman.getType());
        updateRastamanStatement.executeUpdate();
    }

    @Override
    public void dropDatatable() throws SQLException{
        deleteRastamanStatement.executeUpdate();
    }

}