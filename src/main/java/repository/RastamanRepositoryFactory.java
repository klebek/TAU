package repository;

import service.RastamanManagerImpl;

import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class RastamanRepositoryFactory {

    public static RastamanRepositoryFactory getInstance() {
        try {
            String url = "jdbc:hsqldb:hsql://localhost/workdb";
            return new RastamanManagerImpl(DriverManager.getConnection(url));
        }
        catch(SQLException e){
            return null;
        }
    }
}

