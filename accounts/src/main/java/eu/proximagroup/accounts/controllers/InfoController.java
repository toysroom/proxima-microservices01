package eu.proximagroup.accounts.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InfoController {

//	@Autowired
//    private Environment environment;
//	
//	@Value("${build.version}")
//    private String buildVersion;
	
	@GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity
	        .status(HttpStatus.OK)
	        .build();
    }
	
	
	
//    @GetMapping("/build-info")
//    public ResponseEntity<String> getBuildInfo() {
//        return ResponseEntity
//	        .status(HttpStatus.OK)
//	        .body(buildVersion);
//    }
//    
//    @GetMapping("/build-info2")
//    public ResponseEntity<String> getBuildInfo2() {
//        return ResponseEntity
//	        .status(HttpStatus.OK)
//	        .body(environment.getProperty("build.version"));
//    }
//    
//    @GetMapping("/java-version")
//    public ResponseEntity<String> getJavaVersion() {
//        return ResponseEntity
//	        .status(HttpStatus.OK)
//	        .body(environment.getProperty("java.version"));
//    }
//    
//    @GetMapping("/system-info")
//    public String getSystemInfo() {
//        String osName = environment.getProperty("os.name");
//        String osVersion = environment.getProperty("os.version");
//        String osArch = environment.getProperty("os.arch");
//        String javaVersion = environment.getProperty("java.version");
//        
//        return String.format("OS: %s, Version: %s, Arch: %s, Java: %s", 
//                              osName, osVersion, osArch, javaVersion);
//    }
//    
//    @GetMapping("/env")
//    public ResponseEntity<String> env() {
//        String ambiente = environment.getProperty("ambiente");
//        return ResponseEntity
//    	        .status(HttpStatus.OK)
//    	        .body(ambiente);
//    }
}
