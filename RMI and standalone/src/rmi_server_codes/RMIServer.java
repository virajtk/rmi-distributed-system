package rmi_server_codes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.Timer;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import desktop_forms.SensorDetailComponent;

public class RMIServer extends UnicastRemoteObject implements RMIService {

	public static void main(String[] args) throws RemoteException, AlreadyBoundException, IOException {

		Registry registry = LocateRegistry.createRegistry(5099);
		registry.bind("AirSensorService", new RMIServer());

		System.out.println("Server is starting.....!");
		System.out.println("Run the Fire monitoring system....!");
		
		Timer time = new Timer(0, null);

		time.addActionListener(new ActionListener() {

			//periodically check the state
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				} catch (Exception e1) {

					e1.printStackTrace();
				}
			}
		});

		time.setRepeats(true);
		time.setDelay(15000); // periodic interval
		time.start();

	}

	protected RMIServer() throws RemoteException {
		super();
	}

	
	 //Retrieve the sensor details
	 
	@Override	// http request
	public String getAllSensorDetails() throws RemoteException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest
				.newBuilder(URI.create("https://fire-alert-solution.herokuapp.com/api/v1/sensors/")).build();
		return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
				.thenApply((responseBody) -> parse(responseBody)).join();
	}

	public static String parse(String responseBody) {
		return responseBody;
	}

	//validate the admin login details (email and password)
	@Override
	public String loginValidator(String email, String password) throws RemoteException {

		JSONObject json = new JSONObject();
		json.put("email", email);
		json.put("password", password);
		
		String adminUN = "admin";
		String adminPW = "admin321";

		String res = null;

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		try {
			if (email.equals(adminUN) && password.equals(adminPW)){
				res = "success";
			}else {
				res = "failed";
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return res;
	}

	//co2 and smoke levels are checked
	public static void checkStateRepeatedly() {
		
		HttpClient client = HttpClient.newHttpClient();
		//http request
		//its implemented in front end
	
	}
	

	private static String checkCo2andSmokeLevel(String responseBody) {

		JSONObject res = new JSONObject(responseBody);
		
		JSONObject data = res.getJSONObject("data");
		
		JSONArray sensors = data.getJSONArray("sensors");

		for (int i = 0; i < sensors.length(); i++) {

			JSONObject obj = sensors.getJSONObject(i);

			JSONObject lastReading = obj.getJSONObject("lastReading");

			int co2Level = lastReading.getInt("co2Level");
			
			int smokeLevel = lastReading.getInt("smokeLevel");
			
			String _id = obj.getString("_id");

			if (co2Level > 5 || smokeLevel > 5) {

				//JSON object to send with Email API
				JSONObject jsonReadingEmail = new JSONObject();
				jsonReadingEmail.put("smokeLevel", smokeLevel);
				jsonReadingEmail.put("co2Level", co2Level);

				JSONObject jsonEmail = new JSONObject();
				jsonEmail.put("to", "uldkavindigunasinghe@gmail.com");
				jsonEmail.put("sensor", _id);
				jsonEmail.put("reading", jsonReadingEmail);

				//JSON object to send with SMS API
				JSONObject jsonReadingSms = new JSONObject();
				jsonReadingSms.put("smokeLevel", smokeLevel);
				jsonReadingSms.put("co2Level", co2Level);

				JSONObject jsonSms = new JSONObject();
				jsonSms.put("to", "+w");
				jsonSms.put("sensor", _id);
				jsonSms.put("reading", jsonReadingSms);

				CloseableHttpClient httpClient = HttpClientBuilder.create().build();

				try {
					//http request
					HttpPost requestEmail = new HttpPost("https://fire-alert-solution.herokuapp.com/api/v1/email");
					StringEntity paramsEmail = new StringEntity(jsonEmail.toString());
					// add headers 
					requestEmail.addHeader("content-type", "application/json");
					requestEmail.addHeader("Authorization", "agfYjhdioJK5ghiH46dHr8gfg857yfrJYuit57vf");
					requestEmail.setEntity(paramsEmail);
					org.apache.http.HttpResponse responseEmail = httpClient.execute(requestEmail);

					//http request
					HttpPost requestSms = new HttpPost("https://fire-alert-solution.herokuapp.com/api/v1/sms");
					StringEntity paramsSms = new StringEntity(jsonSms.toString());
					// add headers to the request
					requestSms.addHeader("content-type", "application/json");
					requestSms.addHeader("Authorization", "agfYjhdioJK5ghiH46dHr8gfg857yfrJYuit57vf");
					requestSms.setEntity(paramsSms);
					org.apache.http.HttpResponse responseSms = httpClient.execute(requestSms);

					//responses are checked
					System.out.println(responseEmail.getStatusLine().toString().equalsIgnoreCase("HTTP/1.1 201 Created")
							? "Email has Sent"
							: "Error occured!!");
					System.out.println(responseSms.getStatusLine().toString().equalsIgnoreCase("HTTP/1.1 201 Created")
							? "Sms has Sent"
							: "Error occured!!");

				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					try {
						httpClient.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return null;
	}

	//to add sensors
	@Override
	public boolean addSensor(String id, int floor, String room) throws RemoteException {

		boolean res = false;

		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("floorNo", floor);
		json.put("roomNo", room);

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		try {
			// prepare a HTTP request to send to API
			HttpPost request = new HttpPost("http://localhost:8080/alarms/create");
			StringEntity params = new StringEntity(json.toString());
			// add headers to the request
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			org.apache.http.HttpResponse response = httpClient.execute(request);

			System.out.println(response.getStatusLine().toString().equalsIgnoreCase("Alarm Created"));

			// check the response
			res = response.getStatusLine().toString().equalsIgnoreCase("Alarm Created");

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	//edit sensor details
	@Override
	public boolean editSensor(String id, int floor, String room) throws RemoteException {

		boolean res = false;

		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("floor", floor);
		json.put("room", room);

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		try {
			// http request
			HttpPatch request = new HttpPatch("http://localhost:8080/alarms/edit" + id);
			StringEntity params = new StringEntity(json.toString());
			
			request.addHeader("content-type", "application/json");
			
			request.setEntity(params);
			org.apache.http.HttpResponse response = httpClient.execute(request);

			System.out.println(response.getStatusLine().toString().equalsIgnoreCase("Alarm Updated"));

			//responses are checked
			res = response.getStatusLine().toString().equalsIgnoreCase("Alarm Updated");

		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	//delete sensors
	@Override
	public boolean deleteSensor(String id) throws RemoteException {

		boolean res = false;

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		try {
			// http request
			HttpDelete request = new HttpDelete("http://localhost:8080/alarms/delete" + id);
			
			request.addHeader("content-type", "application/json");
			
			org.apache.http.HttpResponse response = httpClient.execute(request);

			// responses are checked
			res = response.getStatusLine().toString().equalsIgnoreCase("Alarm Deleted");

		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
}
