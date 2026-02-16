package Lab5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {


	@Test
	void testRole1() {
		Role role = Role.getInstance(); 
		boolean expected = true;
		boolean actual = (role.role == ' ') && (role.role == role.getRole());
		assertEquals(actual, expected, "The role is not initialized correctly by the default constructor of the Roll class..");
	}

	@Test
	void testRole2() {
		Role role = Role.getInstance('C'); 
		boolean expected = true;
		boolean actual = (role.role == 'C') && (role.role == role.getRole());
		assertEquals(actual, expected, "The role is not initialized correctly by the overloaded constructor of the Roll class..");
	}
	@Test
	void testMap1() {
		Map map = Map.getInstance(); 
		boolean expected = true;
		boolean actual = (map.theSkeld.compareTo("") == 0) && (map.miraHq.compareTo("") == 0) 
				&& (map.polus.compareTo("") == 0) && (map.airShip.compareTo("") == 0);
		assertEquals(actual, expected, "The map is not initialized correctly by the default constructor of the Map class.");
	}
	@Test
	void testMap2() {
		Map map = Map.getInstance("theSkeld", "miraHq", "polus", "airShip"); 
		boolean expected = true;
		boolean actual = (map.theSkeld.compareTo("theSkeld") == 0) && (map.miraHq.compareTo("miraHq") == 0) && 
				(map.polus.compareTo("polus") == 0) && (map.airShip.compareTo("airShip") == 0);
		assertEquals(actual, expected, "The map is not initialized correctly by the overloaded constructor of the Map class.");
	}

	@Test
	void testPlayer1() {
		Role role = Role.getInstance('I'); 
		Player player = Player.getInstance("Jane", role); 
		boolean expected = true;
		boolean actual = (role == player.getRole());
		assertEquals(actual, expected, "The aggregation relationship is not correcly implemented for the Player class [wrong getter method].");
	}
	
	@Test
	void testPlayer5() {
		Role role = Role.getInstance('I'); 
		Player player1 = Player.getInstance(new String("Jane"), role); 
		Player player2 = Player.getInstance("Jane", role);
		boolean expected = true;
		boolean actual = (player1.getName() != player2.getName());
		assertEquals(actual, expected, "The aggregation relationship is not correctly implemented for name in Player class.");
	}
	@Test
	void testGame1() {
		Role role1 = Role.getInstance('C'); 
		Player player1 = Player.getInstance("Jane", role1); 
		Role role2 = Role.getInstance('I'); 
		Player player2 = Player.getInstance("John", role2); 
		Player [] player = new Player[2]; 
		player[0] = player1; 
		player[1] = player2;
		Map map = Map.getInstance(); 
		
		Game game = Game.getInstance(player, map); 
		boolean expected = true;
		boolean actual = (game.getPlayer() != player);
		assertEquals(actual, expected, "The Composition relationship is not correctly implemented for the player list in Game class [getPlayer].");
	}
	@Test
	void testGame3() {
		Role role1 = Role.getInstance('C'); 
		Player player1 =  Player.getInstance("Jane", role1); 
		Role role2 = Role.getInstance('I'); 
		Player player2 = Player.getInstance("John", role2); 
		Player [] player = new Player[2]; 
		player[0] = player1; 
		player[1] = player2;
		Map map = Map.getInstance("theSkeld", "miraHq", "polus", "airShip"); 
		
		Game game = Game.getInstance();
		game.setMap(map);
		game.setPlayer(player);
		boolean expected = true;
		boolean actual = (game.getMap() != map) && (game.getPlayer() != player);
		assertEquals(actual, expected, "The Composition relationship is not correctly implemented for map in Game class [setMap and setPlayer].");
	}

	@Test
	void testGame6() {
		Role role1 = Role.getInstance('C'); 
		Player player1 = Player.getInstance("Jane", role1); 
		Role role2 = Role.getInstance('I'); 
		Player player2 = Player.getInstance("John", role2); 
		Player [] player = new Player[2]; 
		player[0] = player1; 
		player[1] = player2;
		Map map = Map.getInstance("theSkeld", "miraHq", "polus", "airShip"); 
		
		Game game1 = Game.getInstance(player, map);
		Game game2 = Game.getInstance(player, map);
		boolean expected = true;
		boolean actual = (game1.getPlayer() != game2.getPlayer());
		assertEquals(actual, expected, "The Composition relationship is not correctly implemented for player array in Game class [copy constructor or overloaded constructor].");
	}
}
