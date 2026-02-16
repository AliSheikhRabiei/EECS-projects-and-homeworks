package tests;
import static org.junit.Assert.*;
import org.junit.Test;
import model.*;

public class StarterTests {

	/*
	 * You are required to solve a weather station problem:
	 * 	A weather station has zero or more connected apps (each of which being a sensor app or a dummy app).
	 *  Symmetrically, each sensor app or dummy app has zero or more connected weather stations.    
	 */

	@Test
	public void test_weather_station_01() {
		/* 
		 * Create a weather station with the specified name.
		 */
		WeatherStation station = new WeatherStation("Station@NorthYork");
		assertEquals("Station@NorthYork has no connected apps.", station.toString());
	}

	@Test
	public void test_weather_app_01() {
		/*
		 * Create a weather sensor app with the specified name and maximum 20 connected weather stations.
		 * No error handling is needed when a preset maximum is exceeded: let the ArrayIndexOutOfBoundsException occur.
		 */
		
		WeatherApp app = new SensorApp("Sensor1234", 20);
		assertEquals("Weather Sensor App Sensor1234 is connected to no stations.", app.toString());
	}

	@Test
	public void test_weather_app_02() {
		/*
		 * Create a weather dummy app with the specified name and maximum 20 connected weather stations.
		 * The dummy app is a still in the making so it doesn't do anything besides have a toString. 
		 * No error handling is needed when a preset maximum is exceeded: let the ArrayIndexOutOfBoundsException occur.
		 */
		WeatherApp app = new DummyApp("DummyApp5678", 20);
		assertEquals("Weather Dummy App DummyApp5678 is connected to no stations.",app.toString());

	}

	@Test 
	public void test_weatehrStation_getAppsMethods() {
		WeatherStation station1 = new WeatherStation("Station@NorthYork");

		/* 
		 * Each WeatherStation has an array of WeatherApps.
		 * The maxCapacity a weather station can initially have is 2.
		 * We will look at later what happens when we want to add more.
		 * 
		 */

		assertEquals(2, station1.getInternalAppsArray().length);
		assertEquals(0, station1.getApps().length);
		assertEquals("Station@NorthYork has no connected apps.", station1.toString());		
	}

	@Test 
	public void test_weatehrStation_connect() {
		WeatherStation station1 = new WeatherStation("Station@NorthYork");

		WeatherApp app1 = new DummyApp("Dummy5678", 60); 
		WeatherApp app2 = new SensorApp("Sensor1234", 20);

		/* 
		 * Connect `app1` to `station1` (which symmetrically also connects `station1` to `app1`). 		 * 
		 * You can assume that a weather app, once added to a weather station, will not be added to that station again.
		 * 
		 * For simplicity, in the return value of the toString method,
		 * 	spell `1 apps`, `2 apps`, `1 stations`, `2 stations`, and etc.
		 */

		station1.connect(app1);
		assertEquals(2, station1.getInternalAppsArray().length);
		assertEquals(1, station1.getApps().length);

		assertEquals("Station@NorthYork is connected by 1 apps: <Weather Dummy App Dummy5678>.", station1.toString());
		assertEquals("Weather Dummy App Dummy5678 is connected to 1 stations: <Station@NorthYork>.", app1.toString());
		assertEquals("Weather Sensor App Sensor1234 is connected to no stations.", app2.toString());		


		station1.connect(app2);
		assertEquals(2, station1.getInternalAppsArray().length);
		assertEquals(2, station1.getApps().length);

		assertEquals("Station@NorthYork is connected by 2 apps: <Weather Dummy App Dummy5678, Weather Sensor App Sensor1234>.", station1.toString());
		assertEquals("Weather Dummy App Dummy5678 is connected to 1 stations: <Station@NorthYork>.", app1.toString());
		assertEquals("Weather Sensor App Sensor1234 is connected to 1 stations: <Station@NorthYork>.", app2.toString());		
	}
	
	@Test 
	public void test_weatehrStation_getDummyApps() {
		WeatherStation station1 = new WeatherStation("Station@NorthYork");

		WeatherApp app1 = new SensorApp("Sensor1234", 20);
		WeatherApp app2 = new DummyApp("Dummy5678", 60); 
		
		/*
		 * Get all the apps that are dynamically dummy apps
		 * that are connected to the station context object.
		 * If there are no dummyApps connected, return an empty array.
		 */
		DummyApp[] dummyApps1 = station1.getDummyApps();
		assertEquals(0, dummyApps1.length);
		assertNotNull(dummyApps1);

		/*
		 * Connect app1 which IS NOT dynamically a dummy app.
		 */
		station1.connect(app1);
		DummyApp[] dummyApps2 = station1.getDummyApps();
		assertEquals(0, dummyApps2.length);
		assertNotNull(dummyApps2);
		
		/*
		 * Connect app2 which IS a dynamically a dummy app.
		 */
		station1.connect(app2);
		DummyApp[] dummyApps3 = station1.getDummyApps();
		assertEquals(1, dummyApps3.length);
		assertEquals(app2, dummyApps3[0]);
	}

	@Test
	public void test_weather_station_03() {
		WeatherStation station1 = new WeatherStation("Station@NorthYork");

		WeatherApp app1 = new DummyApp("Dummy5678", 60); 
		WeatherApp app2 = new SensorApp("Sensor1234", 20);

		/*
		 * Recall: The default size of a station WeatherApp array is 2.
		 * If another WeatherApp wants to be connected when the capacity is full, 
		 * you should grow the array size to to hold 3 more weather apps, and then add the app.
		 * E.g. 2 --> 5 --> 8 --> 11 etc...
		 */
		assertTrue(station1.getInternalAppsArray().length==2);
		assertTrue(station1.getApps().length==0);

		/*
		 * Connect the first app
		 */
		station1.connect(app1);
		assertTrue(station1.getInternalAppsArray().length==2);
		assertTrue(station1.getApps().length==1);
		assertTrue(station1.getInternalAppsArray()[0]==app1);
		assertTrue(station1.getInternalAppsArray()[1]==null);

		/*
		 * Connect the second app (Now full)
		 */
		station1.connect(app2);
		assertTrue(station1.getInternalAppsArray().length==2);
		assertTrue(station1.getApps().length==2);
		assertTrue(station1.getInternalAppsArray()[0]==app1);
		assertTrue(station1.getInternalAppsArray()[1]==app2);


		/*
		 * Adding one more app now will make the getInternalAppsArray length be 5
		 *  since we are increasing its size by 3.
		 */
		WeatherApp app3 = new SensorApp("Sensor1234", 20);
		station1.connect(app3);
		assertTrue(station1.getInternalAppsArray().length==5);
		assertTrue(station1.getApps().length==3);
		assertTrue(station1.getInternalAppsArray()[0]==app1);
		assertTrue(station1.getInternalAppsArray()[1]==app2);
		assertTrue(station1.getInternalAppsArray()[2]==app3);
	}
	
		@Test
		public void testGetConnectedDummysOf() {
			WeatherStation station = new WeatherStation("StationA");
	
			SensorApp sensor = new SensorApp("SensorX", 3);
			DummyApp dummy1 = new DummyApp("DummyA", 2);
			DummyApp dummy2 = new DummyApp("DummyB", 2);
	
	
			/*
			 * For the weather station that is connected to the context sensor object, 
			 * retrieve the list of dummy apps that are connected to that weather station.
			 * The order of the dummy apps should correspond to the order they are in the WeatherStation object.
			 * If there are no connected dummy apps, return an empty array.
			 */
			
			/*
			 * Currently there are no apps connected.
			 */
			DummyApp[] connected1 = sensor.getConnectedDummysOf("StationA");
			assertNotNull(connected1);
			assertEquals(0, connected1.length);
			
			/*
			 * Now connect apps and check again.
			 */
			station.connect(sensor);
			station.connect(dummy1);
			station.connect(dummy2);
			
			DummyApp[] connected2 = sensor.getConnectedDummysOf("StationA");
	
			assertEquals(2, connected2.length);
			assertTrue(connected2[0].getName().equals("DummyA"));
			assertTrue(connected2[1].getName().equals("DummyB"));
		}
	
		@Test
		public void testGetConnectedDummysOf_invalidStation() {
			SensorApp sensor = new SensorApp("SensorY", 2);
			DummyApp[] result = sensor.getConnectedDummysOf("NonexistentStation");
	
			assertNotNull(result);
			assertEquals(0, result.length);
		}
	
		@Test
		public void testWeatherPerson() {
			/*
			 * Create a WeatherPerson and set their name.
			 * Assume the maximum number of WeatherApps a WeatherPerson can have is 100, 
			 * Assume: more than 100 will never be added. No exception handling required.
			 */
			WeatherPerson.resetWeatherPerson();
			WeatherPerson wp1 = WeatherPerson.getInstance();
			WeatherPerson wp2 = WeatherPerson.getInstance();
			
			assertEquals(null,wp1.getName());
			assertEquals(null,wp2.getName());
			wp1.setName("Yuna");
	
			/*
			 * Check that setting the name for one instance, will set it for all.
			 */
			assertEquals("Yuna",wp1.getName());
			assertEquals("Yuna",wp2.getName());
	
			SensorApp sensor = new SensorApp("YorkSensor", 2);
			DummyApp dummy = new DummyApp("Dummy123", 2);
	
			//Add apps and check that it sets it for all
			wp1.addApp(sensor);
			wp1.addApp(dummy);
	
			assertEquals(sensor, wp1.getWeahterApps()[0]);
			assertEquals(dummy, wp1.getWeahterApps()[1]);
	
			assertEquals(sensor, wp2.getWeahterApps()[0]);
			assertEquals(dummy, wp2.getWeahterApps()[1]);
	
			assertEquals("Yuna has  1 sensor apps and 1 dummy apps.", wp1.toString());
		}
}



