package pl.lebiedzinski;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import service.RastamanManager;
import service.RastamanManagerImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RastamanManagerMockedTest {

    RastamanManager rastamanManager;

    @Mock
    Connection connectionMock;

    @Mock
    PreparedStatement insertStatementMock;

    @Mock
    PreparedStatement getAllStatementMock;

    @Mock
    PreparedStatement deleteStatementMock;

    @Mock
    PreparedStatement updateStatementMock;

    @Before
    public void setupDatabase() throws SQLException {
        when(connectionMock.prepareStatement(
                "INSERT INTO Rastaman (name, grams, type) VALUES (?, ?, ?)"))
                .thenReturn(insertStatementMock);
        when(connectionMock.prepareStatement(
                "SELECT * FROM Rastaman"))
                .thenReturn(getAllStatementMock);
        when(connectionMock.prepareStatement(
                "UPDATE Rastaman SET name = ? WHERE id = ?"))
                .thenReturn(updateStatementMock);
        when(connectionMock.prepareStatement("DELETE FROM Rastaman WHERE id = ?"))
                .thenReturn(deleteStatementMock);

        rastamanManager = new RastamanManagerImpl();
        rastamanManager.setConnection(connectionMock);

        verify(connectionMock).prepareStatement(
                "INSERT INTO Rastaman (name, grams, type) VALUES (?, ?, ?)"
        );
        verify(connectionMock).prepareStatement(
                "SELECT * FROM Rastaman"
        );
        verify(connectionMock).prepareStatement(
                "DELETE FROM Rastaman WHERE id = ?"
        );
        verify(connectionMock).prepareStatement(
                "UPDATE Rastaman SET name = ? WHERE id = ?"
        );
    }

    @Test
    public void checkingAddRastaman() throws Exception {
        when(insertStatementMock.executeUpdate()).thenReturn(1);

        Rastaman rastaman = new Rastaman();
        rastaman.setName("Soja");
        rastaman.setGrams(15);
        rastaman.setType("Stoned");

        assertEquals(1, rastamanManager.addRastaman(rastaman));
        verify(insertStatementMock, times(1)).setString(1, "Soja");
        verify(insertStatementMock, times(1)).setInt(2, 15);
        verify(insertStatementMock, times(1)).setString(3, "Stoned");
        verify(insertStatementMock).executeUpdate();
    }

    @Test
    public void checkingAddRastamanInOrder() throws Exception {
        InOrder inorder = inOrder(insertStatementMock);
        when(insertStatementMock.executeUpdate()).thenReturn(1);

        Rastaman rastaman = new Rastaman();
        rastaman.setName("Soja");
        rastaman.setGrams(15);
        rastaman.setType("Stoned");

        assertEquals(1, rastamanManager.addRastaman(rastaman));

        inorder.verify(insertStatementMock, times(1)).setString(1, "Soja");
        inorder.verify(insertStatementMock, times(1)).setInt(2, 15);
        inorder.verify(insertStatementMock, times(1)).setString(3, "Stoned");
        verify(insertStatementMock).executeUpdate();
    }

    @Test
    public void getAll() throws SQLException{

        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getInt("id")).thenCallRealMethod();
        when(mockedResultSet.getString("name")).thenCallRealMethod();
        when(getAllStatementMock.executeQuery()).thenReturn(mockedResultSet);

        assertEquals(1, rastamanManager.getAll().size());

        verify(getAllStatementMock, times(1)).executeQuery();
        verify(mockedResultSet, times(1)).getInt("id");
        verify(mockedResultSet, times(1)).getString("name");
        verify(mockedResultSet, times(2)).next();

    }

    abstract class AbstractResultSet implements ResultSet {
        int i = 0;

        @Override
        public boolean next() throws SQLException {
            if (i == 1)
                return false;
            i++;
            return true;
        }

        @Override
        public int getInt(String s) throws SQLException {
            return 1;
        }

        @Override
        public String getString(String columnLabel) throws SQLException {
            return "AF";
        }
    }

    @Test
    public void checkingAddRastamanInCaseAddNull() throws Exception {
        when(insertStatementMock.executeUpdate()).thenReturn(1);

        Rastaman rastaman = new Rastaman();
        rastaman.setName("Soja");
        rastaman.setGrams(15);
        rastaman.setType("Stoned");

        assertEquals(1, rastamanManager.addRastaman(rastaman));

    }


    @Test
    public void deleteRastaman() throws SQLException {

        when(deleteStatementMock.executeUpdate()).thenReturn(1);
        Rastaman rastaman = new Rastaman();
        rastaman.setId(1);
        rastaman.setName("Marley");
        rastaman.setGrams(8);
        rastaman.setType("AF");
        assertEquals(1, rastamanManager.deleteRastaman(rastaman));
        verify(deleteStatementMock, times(1)).setInt(1, rastaman.getId());
        verify(deleteStatementMock).executeUpdate();

    }


    @Test
    public void updateUndead() throws SQLException {

        when(updateStatementMock.executeUpdate()).thenReturn(1);
        Rastaman rastaman = new Rastaman();

        rastaman.setId(1);
        rastaman.setName("Gentleman");
        rastaman.setGrams(15);
        rastaman.setType("High AF");

        Rastaman updatedRastaman = new Rastaman();
        updatedRastaman.setId(rastaman.getId());
        updatedRastaman.setName("Updated Gentleman");
        updatedRastaman.setGrams(12);
        updatedRastaman.setType("Plain");

        assertEquals(1, rastamanManager.updateRastaman(rastaman.getId(), updatedRastaman));
        verify(updateStatementMock).executeUpdate();


    }

}
/*
@Ignore
@RunWith(JUnit4.class)
public class RastamanManagerMockedTest {

    RastamanManager rastamanManager;

    public RastamanManagerMockedTest() throws SQLException {
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
        assertNotNull(rastamanManager.getById(170));
    }


    @Ignore
    @Test
    public void checkingDeleteRastaman() throws SQLException {
        Rastaman marley = rastamanManager.getById(1);
        rastamanManager.deleteRastaman(marley);
        if (rastamanManager.getAll().size() > 0) {
            assertNotNull(rastamanManager.getAll());
        } else {
            assertNull(rastamanManager.getById(marley.getId()));
        }
    }

    @Test
    public void checkingUpdateRastaman() throws SQLException {
        Rastaman marley = new Rastaman();
        marley.setName("Marley");
        marley.setGrams(444444);
        marley.setType("Stoned");
        int marleyToUpdate = 170;
        rastamanManager.updateRastaman(marleyToUpdate, marley);
        assertEquals(rastamanManager.getById(marleyToUpdate).getType(), marley.getType());
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
*/



