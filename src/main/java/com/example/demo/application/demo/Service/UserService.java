package com.example.demo.application.demo.Service;

import com.example.demo.application.demo.Model.AuthRequest;
import com.example.demo.application.demo.Model.User;
import com.example.demo.application.demo.Repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class UserService {

    public final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    public User saveUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public  User getUser(String id){
    return userRepository.findById(id).get();
    }

    public void deleteUser(String id){
        userRepository.deleteById(id);
    }

    public List<User> filterItems(String roleName) {
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream()
                .filter(user -> user.getRoles() != null && user.getRoles().contains(roleName))
//                .map(User::getName)
                .collect(Collectors.toList());
    }


    public User getUserDetail(String userName){
        Optional<User> user=userRepository.findByUsername(userName);
        List<String>userNames=new ArrayList<>();

        userNames.add(userName);
        System.out.println("userNames" + userNames);
        userNames.removeIf(name -> name.equals("Santosh2"));
        System.out.println("userNames" + userNames);
        int length=userNames.size();
        System.out.println("Size of userNames " + length);
        userNames.clear();
        System.out.println("userNames" + userNames);
        List<User>users=userRepository.findAll();
      List<User>filteredUser=  users.stream().filter(user1->user1.getUsername().equals("Santosh1")).collect(Collectors.toList());
        System.out.println("users" + filteredUser);
        List<User>filteredUser1=  users.stream().filter(user1->user1.getUsername().equals("Santosh1")).toList();
        log.info("To List User Data is {} ",filteredUser1);

        String test="test";
        String now="Testing";


        List<String>list=new ArrayList<>();
        List<String>list1=new ArrayList<>();
        List<String>list2=new ArrayList<>();
        list2.add("hhdh");
        list.add("apple");
        list.add("banana");
        list.add("ora@nge");
        list.add("kiwi");
        list.add("mango");
        list.add("pineapple");
        list.add("grapes");
        list.add("watermelon");
        list.add("cherry");
        list.add("strawberry");
        list.add("peach");
        list.stream().filter(s->s.equals("apple")).findFirst().orElse(String.valueOf(list1.add("Not Found")));
        System.out.println("list1"+list1);


        list.stream().filter(f->f.equals("apprffele")).findFirst().orElseGet(()->{
            list2.add("apple");
            System.out.println("list2"+list2);
            return list2.toString();
        });


      List<String> filtered=  list.stream().filter(f->f.equals("apple")).toList();
        System.out.println("filtered"+filtered);

        String fruitName=list.stream().filter(f->f.contains("@")).findFirst().orElse("Not Found");
        System.out.println("fruitName"+fruitName);


        boolean newFruitName=list.stream().anyMatch(p->p.equals("strawberry"));

        if(newFruitName){
            System.out.println("New Fruit Name is Found");
        }
        else {
            System.out.println("New Fruit Name is not Found");
        }

//        List<String>newList=list.stream().forEach(item->item.toUpperCase());


        return user.orElse(null);


    }

    public String verifyUser(AuthRequest authRequest) {
        String username = authRequest.getUsername();
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username Not Found");
        }

        User user1 = user.get();

        if (passwordEncoder.matches(authRequest.getPassword(), user1.getPassword())) {
            String identifier = user1.getEmail() != null ? user1.getEmail() : user1.getUserNumber();
            String token=generateToken(user1.getUsername());
            log.info("Token is {} ",token);
            return "Success";
        }

        return "Fail";
    }


    public String generateToken(String username) {
        String SECRET_KEY = "secretsecretsecretsecretsecretsecretsecretsecretsecret";

        Key key = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());

        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(key)
                .compact();
    }

}


//Lambda functions are short functions and unnamed functions that can be passed to simplify code

//orElse and orElseGet
//these will work when the method is optional

//orElse
//it will always create a default value whether optional value is there or not it doesn't care

//orElseGet
//it will create value if the optional value is not there then only it will create the value.and more on
//it will expect a lambda value in the inside of orElseGet, and it also expects a return value
//we have two types of orElse Get
// 1ST one is .orElseGet(()->method_name(params))
//2Nd one is .orElseGet(()->{ code }) this type must need return value



//equals
//it is used to compare to strings

//add
//used to add item to a list

//toList()
//creates an immutable list

//collect(Collectors.toList()
//creates an mutable list

//filter
//user to filter the list
//usage
//.filter(name->name.code.....)

//.stream
//used to convert the list to stream which helps us to do sorting, filtering, etc...

//map
//used to convert a list to a particular type of list like .map(User::getName) so this will create a list with only names


//Optional.ofNullable()
//This is used when the value is null, so with using this method, it will also accept null values


//findFirst()
//It is used to return the first element in the stream that matches a condition (if filtered) or simply the first element of the stream if no filter is applied.
//this func is an optional function



//builder
//is a method that creates a builder object

//contains
//The .contains method is used to check if a collection, string, or another container contains a specific element or substring.



//anyMatch
//it will work on condition (predicate must be there to work this)
//this is a boolean type, so it will work with true or false values


//distinct
//used to find distinct elements
//Removes duplicates

//foreach
//it returns void so it won't return any list...
//it is used to iterate over the list loop
//it will work on condition (predicate must be there to work this)


//ifPresent
//it will work on condition (predicate must be there to work this)
//is used to check whether it is present or not if present we will follow the predicate condition




















