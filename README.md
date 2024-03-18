# Stacks
 - Java 17 ( Spring Boot, Java TOTP, H2 database )
 - Typescript ( React, SCSS, Jest )

### Requirements

Belong all the stacks said in the previous topic, the user will need a cellphone to check the MFA. I integrated a OTP system in the project, so use a Google Authentication or anything similar to scan the QR code. 

# How to run code

## Backend

There's 2 ways to run the code:
1. Open the login project in your IDE, enter in *src/main/java/devx/challenge/login/LoginApplication.java* and run it direct.
2. Run the .jar file in XXXXXXXXXXXXXX

## Frontend

Run the following in project root command to start frontend page
1. npm start

## Access contents
To access the contents available in backend you could check the list of endpoints availables in swagger
 - http://localhost:8080/swagger-ui/index.html

To access the frontend page just go to the following link
 - http://localhost:4200

## Important considerations

To facilitate all the process of running the project, it was created using an in memory database (H2), obviously in a real situation the best practice would be using another db like mariaDB, OracleDb, SQL Server, etc. But in this situation it will be pretty easy to run all the project.

I faced one issue while trying to run my frontend project using *npm start* it seems that the nx command was invalid so I updated it to *nx serve ui --watch*

**I used a library that do not provide the OTP code generated, to debug you can send the secret code, obviously it has more than 6 chars, but the main thing here is that the OTP works property, this is just to debug proccess. If you want to scan the QR code and check the OTP code it works too**

The logged page has the goal to show that the JWT is valid, if you refresh the page it will show that you're unauthorized, that is expected.

# Obstacles overcome

During develop proccess i faced some problems, in this section I'll explain my thoughts to pass through all these hindrances.

### Security filter chain

To be honest, I've never configure the security layer in Spring boot by my own, so all these where new to be (I undestand the workflows but never configured this in practice).

I think the security filter chain was the hardest part to be, I believe this bc all this stuff is really confuse in the beggining, and additionally I had the JWT token to setup property, so I faced A LOT of bugs related to give the property permission and check if the given token is really valid to see the private content.

The proccess to resolve all those bugs were pretty similar, I love to use "evaluation expression" in IntelliJ, this helps me a lot to check the current state of all the variables presents in the context and check all the methods available to use.

To get some insights of the erros I also use StackOverflow and a lot of other forums available to check if someone has some solution to all the problems I got during the develop of this project.

### Create a nice UI

I confess to you that I'm not an artistic guy, I can replicate screen in Figma and also looking in images all over the internet (if the CSS is not THAT complex). I wanted to create a nice UI to you use while testing the project. To resolve this, I copied the template from one code I found on codepen.io and edit it to be more within the reality of the test. I did this because I think the main goal here is to show integrations between FE/BE, so I spent more time putting features to the project and less time styling the page itself.

# Future improves

In this section I'll tell in my opinion what I'ld suggest if we're working in a real project. This part is just to demonstrate critical analysis about the projects I'm working on

1. I would suggest to add a remember-me button, so the user do not need to insert the password everytime
2. I would suggest to add a "Forget my password" button, this feature would bring the flexibility to the user recovery its account if needed.
3. Add a recovery token feature. In case the user lose the cellphone, he/she can gain access again using recovery token provided while registered.
4. Put the backend application into a Docker container to better scalling.

# Conclusion

As a conclusion, I like the challenge, it was very fun do all those features even if I had no idea how everything works, thank you very much for this opportunity. :D