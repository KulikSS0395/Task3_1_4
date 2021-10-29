package ru.jm.ds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.jm.ds.entity.User;

import java.util.List;

@Component
public class Communication {

    private String sessionID;

    @Autowired
    private RestTemplate restTemplate;

    private final String URL = "http://91.241.64.178:7081/api/users";

    public List<User> getUsers() {


        ResponseEntity<List<User>> responseEntity =
                restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                });
        List<User> allUsers = responseEntity.getBody();
        String cookie = responseEntity.getHeaders().get("set-cookie").get(0);
        sessionID = cookie;
        System.out.println(sessionID);
        return allUsers;
    }



    public User getUser(int id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", sessionID);

        User user = restTemplate.getForObject(URL + "/" + id, User.class, httpHeaders);

        return user;
    }


    public void saveUser(User user) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", sessionID);

        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, new HttpEntity<>(user, httpHeaders), String.class);
        System.out.println(responseEntity.getBody());
        System.out.println("user add");

    }

    public void updateUser(User user) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", sessionID);

        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>(user, httpHeaders), String.class);

        System.out.println(responseEntity.getBody());
        System.out.println("user update");
    }

    public void deleteUser(int id, User user) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", sessionID);
        String URLDelete = URL + "/" + id;

        ResponseEntity<String> responseEntity = restTemplate.exchange(URLDelete, HttpMethod.DELETE, new HttpEntity<>(null, httpHeaders), String.class);
        System.out.println(responseEntity.getBody());
    }

}
