package applications.simpleApp;


import com.mca.client.annotation.enablement.EnableMcaClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.thepavel.icomponent.InterfaceComponentScan;

@EnableMcaClient
@SpringBootApplication
@InterfaceComponentScan
public class SimpleApp {

    public static void main(String[] args) {
        SpringApplication.run(SimpleApp.class);
    }
}
