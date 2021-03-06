package pl.lebiedzinski;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import repository.RastamanRepositoryFactory;
import service.RastamanManager;
import service.RastamanManagerImpl;

import java.sql.DriverManager;
import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@Ignore
@RunWith(JUnit4.class)
public class RastamanManagerTest {

    RastamanManager rastamanManager;

    public RastamanManagerTest() throws SQLException {
        String url = "jdbc:hsqldb:hsql://localhost/workdb";
        rastamanManager = new RastamanManagerImpl(DriverManager.getConnection(url));
    }

    @Test
    public void checkingAddRastaman() throws SQLException {
        Rastaman rastaman = new Rastaman();
        rastaman.setName("Soja");
        rastaman.setGrams(15);
        rastaman.setType("Stoned");
        rastamanManager.addRastaman(rastaman);
        assertNotNull(rastamanManager.getById(rastamanManager.getAll().size()-1));
    }

    @Test
    public void checkingDeleteRastaman() throws SQLException {
        Rastaman marley = rastamanManager.getById(1);
        rastamanManager.deleteRastaman(marley);

        if (rastamanManager.getAll().size() > 0) {
            assertNotNull(rastamanManager.getAll());
            /*assertNull(rastamanManager.getById(marley.getId()));*/

        }
    }

    @Test
    public void checkingUpdateRastaman() throws SQLException {
        Rastaman marley = new Rastaman();
        marley.setName("Marley");
        marley.setGrams(4);
        marley.setType("Stoned");
        int marleyToUpdate = rastamanManager.getAll().size()-1;
        rastamanManager.updateRastaman(marleyToUpdate, marley);
        assertEquals(rastamanManager.getById(marleyToUpdate).getType(), marley.getType());

        assertEquals(1, rastamanManager.updateRastaman(marleyToUpdate, marley));
    }

    @Test
    public void checkingGetAll() {
        assertNotNull(rastamanManager.getAll());
    }

    @Before
    public void checkingInitRepository() {
        rastamanManager = (RastamanManager) RastamanRepositoryFactory.getInstance();
        Rastaman marley = new Rastaman();
        Rastaman gentleman = new Rastaman();
        Rastaman chronixx = new Rastaman();
        Rastaman albarosie = new Rastaman();

        marley.setName("Marley");
        marley.setGrams(8);
        marley.setType("AF");

        gentleman.setName("Gentleman");
        gentleman.setGrams(3);
        gentleman.setType("AF");

        chronixx.setName("Chronixx");
        chronixx.setGrams(6);
        chronixx.setType("Stoned");

        albarosie.setName("Albarosie");
        albarosie.setGrams(2);
        albarosie.setType("Plain");

        rastamanManager.addRastaman(marley);
        rastamanManager.addRastaman(gentleman);
        rastamanManager.addRastaman(chronixx);
        rastamanManager.addRastaman(albarosie);
    }

}