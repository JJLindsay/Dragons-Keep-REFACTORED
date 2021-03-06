package junitTestCases;

import model.Database;
import controller.AccountDB;
import controller.room.RoomsDB;
import controller.actors.ActorDB;
import controller.inventory.InventoryDB;
import controller.itemsAndPuzzle.ItemDB;
import controller.itemsAndPuzzle.PuzzleDB;

import org.junit.*;

import static org.junit.Assert.*;

public class LoadEntityTest
{
    private static Database tdb = new Database();
    private static int playerID;

    @BeforeClass
    public static void  setUp() throws Exception
    {
        playerID = new AccountDB().createAccount("JUnitTestName");
        new ActorDB().saveHeroData(playerID + "|JUnitTestName|1|77|61");

        String rooms = playerID + "|" + 14;
        for (int i = 1; i <= 50;  i++)
        {
            rooms += "|" + (i%2);
        }
        new RoomsDB().saveRoomState(rooms);

        String[][] tempInventory = new String[3][2];
        tempInventory[0][0] = "dagger";
        tempInventory[0][1] = "w";
        tempInventory[1][0] = "Standard Armor";
        tempInventory[1][1] = "a";
        tempInventory[2][0] = "Red Elixir";
        tempInventory[2][1] = "e";

        new InventoryDB().saveHeroInventory(playerID, tempInventory);
    }

    @AfterClass
    public static void tearDown() throws Exception
    {
        tdb.modData("DELETE FROM playerFile WHERE name = \'JUnitTestName\'");
        tdb.modData("DELETE FROM playerRooms WHERE playerID = " + playerID);
        tdb.modData("DELETE FROM savedInventory WHERE playerID = " + playerID);
    }

    @Test
    public void testLoadSavedRooms() throws Exception
    {
        String rooms = playerID + "|" + 14;
        for (int i = 1; i <= 50;  i++)
        {
            rooms += "|" + (i%2);
        }
        assertEquals(rooms, new RoomsDB().loadSavedRooms(playerID));
    }

    @Test
    public void testLoadHero() throws Exception
    {
        String expected = playerID + "|JUnitTestName|1|77|61|";

        assertEquals(expected, new ActorDB().loadHero("JUnitTestName"));
    }

    @Test
    public void testLoadHeroInventory() throws Exception
    {
        String expected = "1|0|0|0|1|0|0|0|3|";

        assertEquals(expected, new InventoryDB().loadHeroInventory(playerID));
    }

    @Test
    public void testRetrieveAllRooms() throws Exception
    {
        String[] actual = new RoomsDB().retrieveAllRooms().split("[|]]");

        for(int i = 0; i < actual.length-11; i = i+12)
        {
            assertTrue(Integer.parseInt(actual[i]) >= 0);
            assertTrue(Integer.parseInt(actual[i + 1]) >= 0);
            assertTrue(Integer.parseInt(actual[i + 2]) >= 0);
            assertTrue(Integer.parseInt(actual[i + 3]) >= 0);
            assertTrue(Integer.parseInt(actual[i + 4]) >= 0);

            assertTrue(actual[i + 5] != null);

            assertTrue(Integer.parseInt(actual[i + 6]) >= 0);
            assertTrue(Integer.parseInt(actual[i + 7]) >= 0);
            assertTrue(Integer.parseInt(actual[i + 8]) >= 0);
            assertTrue(Integer.parseInt(actual[i + 9]) >= 0);
            assertTrue(Integer.parseInt(actual[i + 10]) >= 0);
            assertTrue(Integer.parseInt(actual[i + 11]) >= 0);
        }
    }

    @Test
    public void testRetrieveMonster() throws Exception
    {
        String expected = "Blue Dragon|31|51";

        assertEquals(expected, new ActorDB().retrieveMonster(5));
    }

    @Test
    public void testRetrieveArmor() throws Exception
    {
        String expected = "Dragon Scales|48";

        assertEquals(expected, new ItemDB().retrieveArmor(3));
    }

    @Test
    public void testRetrieveElixir() throws Exception
    {
        String expected = "Blue Elixir|10";

        assertEquals(expected, new ItemDB().retrieveElixir(1));
    }

    @Test
    public void testRetrieveWeapon() throws Exception
    {
        String expected = "Axe|8";

        assertEquals(expected, new ItemDB().retrieveWeapon(6));
    }

    @Test
    public void testRetrievePuzzle() throws Exception
    {
        StringBuilder expected = new StringBuilder("its completely dark. Along the wall is a flashing red light. It " +
                "seems\nto indicate something used to be in the empty candle holder");
        expected.append("|use roman candle sticks");
        expected.append("|The room is now lit and you can see, shining across the room some armor");
        expected.append("|nothing happened");
        expected.append("|0");

        assertEquals(expected.toString(), new PuzzleDB().retrievePuzzle(2));
    }
}