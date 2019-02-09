import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class connection {

	public static boolean sendPOST() {
		HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 
	
		try {
	
		    HttpPost request = new HttpPost("http://192.168.40.45");
		    StringEntity params =new StringEntity("details={\"nom\":\"PEPE\",\"cognom\":\"AGUERRIDO\",\"age\":\"20\",} ");
		    request.addHeader("content-type", "application/x-www-form-urlencoded");
		    request.setEntity(params);
		    HttpResponse response = httpClient.execute(request);
		    System.out.println("hola");
		    return true;
		    //handle response here...
	
		}catch (Exception ex) {
			System.out.println("error: " + ex.getMessage());
			return false;
		    //handle exception here
	
		}
	}
}
