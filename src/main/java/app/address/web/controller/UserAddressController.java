package app.address.web.controller;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.net.URL;

/**
 * This class if for cover CRUD operations on mocked data from json file
 */
@RestController
@RequestMapping("/api/v1/address")
public class UserAddressController {
    /**
     * This method is for get all data from mocked json file
     *
     * @return mocked data from json file
     * @throws Exception if file not found
     */
    @GetMapping
    public ResponseEntity<Object> getUserAddress() throws Exception {
        URL file = getClass().getClassLoader().getResource("json/addressData.json");
        if (file != null) {
            FileReader fileReader = new FileReader(ResourceUtils.getFile(file));
            JSONParser jsonParser = new JSONParser(fileReader);
            return ResponseEntity.ok(jsonParser.list());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
