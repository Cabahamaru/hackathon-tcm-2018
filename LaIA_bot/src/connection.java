import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

public class connection {

	public static boolean sendPOST(String tipus, String descripcio, String location, String imgURL) {
		HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 
		JSONObject json = new JSONObject();
		json.put("tipus", tipus);
		json.put("descripcio", descripcio);
		json.put("location", location);
		json.put("imgURL", imgURL);
		
		try {
	
		    HttpPost request = new HttpPost("http://127.0.0.1:5000/new");
		    StringEntity params =new StringEntity(json.toString());
            params.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));		    
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
		    System.out.println("hola");
		    return true;
	
		}catch (Exception ex) {
			System.out.println("error: " + ex.getMessage());
			return false;
	
		} finally {
		}
	}
}
