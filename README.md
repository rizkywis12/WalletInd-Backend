# WalletIND

_WalletIND is an online transaction application that is user friendly, can be accessed anywhere and anytime._

### Devtools

1. Java
2. MySQL
3. Postman

### Final Deployment

See [WalletIND Frontend](https://wallet-ind.netlify.app/)

### How to run project?

1. Open your terminal and then type:

```
$ git clone {the url to the GitHub repo}
```
2. Setup your database in resources > templates > application.properties
3. Open your XAMPP Control Panel and Start running the Apache and MySQL
4. Create a database with the name walletind
5. Run Java Program by opening WalletindApplication.java (@SpringBootApplication) and run the program or you can open terminal and type:

```
$ mvn spring-boot:run
```

6. Open your postman and create new request. Enter your request URL. You can find the request URL via controller folder. Find the required request body for your request URL via model folder > dto > request. Example:
   You want to register an account. Open the controller folder > UserController.java. The code looks as follows:

```
 @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDto request) throws Exception {
        responseData = userService.register(request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }
```

From the code, we do post mapping (choose post method in your Postman app). After that, we need RegisterDto as request body for register request URL. So, open the model folder > dto > request > RegisterDto.java

From RegisterDto, we have to write down our email, password, userName and role (optionally). In your postman, open Body, choose raw, and JSON. Input your required Dto (email, password, and userName) as your request body in your Postman.

For more detailed example, open our Postman documentation below!

See:
[Walletind Documentation](https://mvnrepository.com/)

7. Without postman, you can use the frontend that we have created. Access our code:
[Walletind Frontend](https://github.com/rizkywis12/WalletIND)
When you run the frontend, make sure your port is match with the backend crossorigin setup (check in the controller folder).

### Dependency

1. Spring Starter Mail
2. Spring data JPA
3. Spring Dev-tools
4. Mysql Connector
5. Spring Starter Test
6. Lombok
7. Spring Validation
8. Spring Security
9. JWT
10. Thymeleaf

Find dependency:
[Maven Repository](https://mvnrepository.com/)

Created by: Mujoriwi Team (Rizky Wisesar, Joenathan Sirait, Muhand Dany, dan Windy Nathalie Ong) (2022)
