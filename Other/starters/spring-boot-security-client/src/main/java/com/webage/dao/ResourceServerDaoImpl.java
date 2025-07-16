package com.webage.dao;

import com.webage.domain.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.Optional;

@Service
public class ResourceServerDaoImpl implements ResourceServerDao {

    //  PART 15a -
    //  Define two String member variables for uri and port.
    //  Annotate each with the @Value annotation.
    //  Use the appropriate placeholder to populate them with the resource server's uri and port:


    //  PART 15b -
    //  Add the @Autowired annotation to this member variable:
    RestClient client;

	private String serverUri() {
        //  PART 15c -
        //  Use the String.format() method inject the port into the URI String.  
        //  Return the result (remove the existing line that returns null)
		//return null;    //  <<====  Replace this line
        return null;
	}


    @Override
    public Iterable<Purchase> findAllPurchases(String jwt) {
        ResponseEntity<Purchase[]> responseEntity = client
                .get()
                .uri(serverUri() + "/purchases")

                //  PART 15d -
                //  Use the .header() method to define the "Authorization" header.
                //  The value for the header should be the word "Bearer " concatenated with the jwt.
                //  The JWT is passed to this method (above) as a parameter.
                //  Be sure there is a space between "Bearer " and your JWT value.


                //  PART 15e -
                //  Use the .accept() method to define the "Accept" header.
                //  The method takes one parameter of type "MediaType".
                //  Select APPLICATION_JSON as the desired MediaType.

                .retrieve()
                .toEntity(Purchase[].class);
        return Arrays.asList(responseEntity.getBody());
    }


    @Override
    public Optional<Purchase> findPurchaseById(String jwt, long id) {
        ResponseEntity<Purchase> responseEntity = client
                .get()
                .uri(serverUri() + "/purchases/{id}",id)

                //  PART 15f - 
                //  Compare the .header() and .accept() lines below to 
                //  your implementation above.  Did you get it right?

                .header("Authorization", "Bearer " + jwt)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Purchase.class);
        return Optional.of(responseEntity.getBody());
    }
}
