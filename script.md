# Spring demo
- Daniel Garnier-Moiroux
- Pivotal
- XP, UCD, Lean & Agile
- Product-centered approach
- Help clients change the way they work and build great products

SLIDE
# A brief word on Labs
...

SLIDE
# A word of warning
- This might go a bit fast, but there are great resources
- Not remember everything that I typed ; but remember what is possible

SLIDE
# Agenda
- History of Spring
- Intro to Spring (time ?)
    - Show you how to start using Spring
    - Show you the resources and the points to get started
- Let's take a break
- Spring and RabbitMQ : cloud patterns
- A few cool things

SLIDE
# Wildcards
...

SLIDE
# Intro to Spring
## The story behind Spring
- Created in 2003
- Mostly a competitor to Java Entreprise Edition (J2EE) / Entreprise Java Beans
- Framework to build apps
- Revolves around a few key concepts :
    - Dependency injection
    - Dependency discovery
    - An abstraction layer to integrate modules
    - A curated list of modules that work well together
- Open source !
- Used to be a complex machine where you wrote XML files to describe your components
    - "An XML debugging program"
- Now you can create your app in code - much better
- Still hard to configure ; also people started asking for an embedded web server
    - So in 2014, created Spring Boot !
    - Convention over configuration (all is configured with sensible defaults)
    - Great for building web stuff (Netflix, Alibaba, Amazon, eBay, Visa ...)

Appendices :
- Company bought by VMWare in 2009
- Goes into the Pivotal Cloud Mix

SLIDE
## Let's do it !
## Objectives
- Our goal is to build a small web API to perform CRUD operations against an in-memory DB, and explore how to run it in dev and production mode.
- Along the way, we will see simple stuff to do with Spring
- We will also go through the docs, so you can do it on your own :)

STOP THE SLIDES
## It all starts somewhere
- /!\ start.spring.io
- My second favorite place on the Internet
    - My favorite place is production
    - I. Love. Production.
    - You. Should love. Production.
    - You should go to production as often as possible.
    - Bring the kids, bring the family ! The weather's nice, you can have a picnic, it's the happiest place on earth
    - It's better than Disneyland !
- It's a website to bootstrap your project
    - But also an API -> see IntelliJ's integration
- Name the app "traffic-service"
- Can choose basic config stuff (language, build system, etc), and add dependencies
    - /!\ Add Web
- Also, can configure more and dive into an ocean of check-boxes
    - Java 6 or 7 if you like end-of-lifed, unsupported products
    - War, if you live in the past, with the dinosaurs, far from modern help

## A very (very) simple app
- /!\ Remove .gitignore, .mvn-stuff
- Add a testing dependency :
        <dependency>
            <groupId>org.assertj</groupId>
            <artifactId>assertj-core</artifactId>
        </dependency>
- We can see one class, that's our app
    - See, it's decorated with @SpringBootApplication
    - *clic*
    - It's a config class (can contain beans)
    - It also has a ComponentScan, which is how we tell Spring to inspect classes loaded in your classpath and find all anotated stuff. It's automatic dependency discovery.
    - It's also an auto-config class ! *clic*
    - That's where the magic happens ... show *ServerProperties*
- So anyway. Let's create a first controller.
    - Show the GUIDE !
    - RestController -> GreetingsController
    - @GetMapping sayHi, with test first
    - assertJ is an assertion library, not specific to Spring
    - I like it better than JUnit
- Let's run it
    - mvn spring-boot:run
    - curl http://localhost:8080/hello
    - Deploying to prod with mvn clean package then java -jar
    - Also : mvn spring-boot run when running from source (no packaging required)
- Config :
    - Spring is pre-configured with lots of default values (e.g. port 8080)
    - You can override some of this configuration in multiple ways
    - Example : application.yml
    - Example : environment variables
    - Example : command line
- Integration testing :
    - SpringBootTest
        - RestTemplate
        - With default port first
        - Then @LocalServerPort is an int
        - TestRestTemplate
    - WebMvcTest
Recap :
    - We have created an app (yay !)
    - We have created a Controller
    - We have learned how to build it for deployment
    - We have learned how to configure it
    - We have learnt how to write Spring-specific tests
        - And discovered the cool assertJ library

## Let's add some *real* features now
- Go back to GreetingsController.
    - Add a request param to the query (name), fix the test.
    - This looks like Jersey, right ?
- Spring has the ability to help you write apps without thinking too much about "how" to wire things together, through Dependency Injection
- Say, you have an HTTP connection to a back-end system, that gives you the number of KM of traffic jam
    - You can model this with a "Service"
    - That you then inject in your controller, or any other Spring "component"
    - That's the core of what Spring does
- Let's start with a fake service, for a start :
    - TrafficJamService, with getTotalKilometers: int
        - Test it
    - Bouchons endpoint
        - Test it
        - Inject the thing
        - Mock it !
            - Mockito is a cool mocking library
            - And it's bundled with the spring framework
            - Talk about verification as well
- Now it's nice and can be configured, through "values"
    - Add a test
    - Add a message for the total number of KMs in the /bouchon endpoint, as a Value, e.g. "Bouchons en cours :" (no quotes)
    - Talk about default values
    - Talk about using a property to define another property with ${}
    - Change the value in the properties and/or command-line
- Okay, this is all and well, but how about we get a real service instead ?
    - Refactor this so it uses an interface (ITrafficJamService)
    - Change TrafficJamService to FakeTrafficJamService
    - http://www.sytadin.fr/refreshed/cumul_bouchon.jsp.html
    - Make SytadinService. Pattern : "alt=\"(\\d+) km\"". Take group 1.
        - Beware : RestTemplate must be injected through RestTemplateBuilder or through beans.
- But I don't want to run my integration tests with this service, or depending on some config, I might not want to do that.
    - One way we could do it, is with beans !
    - E.g. : we want a fake service if the SytadinUrl is not defined.
    - Add @Value traffic.sytadin.url to Alertes Application
    - Add a @Bean returning ITrafficJamService, conditionally returning the Fake / Sytadin service.
    - Demo it !
Recap :
    - We used dependency injection with some fakes
    - We configured our app with config files
    - We changed our fakes to a real client
    - And we used beans to configure it at run time !


## Automagic Spring stuff
- Spring also scans the class path for libraries it knows

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>     
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
        </dependency>

- Example with spring-data-rest
    - Create model QTV - Localisation / debit / temps / vitesse / date(getter) / ID
        -> Think about @NotNull resources
    - Create QTV repository (JUST REPOSITORY) 
    - How do I make a filter query ? (e.g. SELECT * WHERE ...)
    - Show the ref doc for Spring Data Jpa
        - Look up "where"
        - Explain "queryMethods"
    - Create findAllByLocalisationContaining(@Param("localisation") String localisation)

- Default values with CommandLineRunner :
    - Update the QTV model to have a nice constructor
    - Update the app test so it wants default values
    - Implement CommandLineRunner in TrafficServiceApplication
    - Save a few examples    

- Switch the database to SQLITE :
    - Show the SQL support on spring initializr
    - Explain why SQLite is not supported
        - It's generally a bad idea
        - And it's NOT a cloud DB, it's just a file
        - Not supported by hibernate because it's a C implementation and there's no Java implementation
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
    <dependency>
        <groupId>org.xerial</groupId>
        <artifactId>sqlite-jdbc</artifactId>
        <version>3.16.1</version>
    </dependency>
    <dependency>
        <groupId>com.github.gwenn</groupId>
        <artifactId>sqlite-dialect</artifactId>
        <version>master</version>
    </dependency>
    <properties>
        <hibernate.version>5.2.10.Final</hibernate.version>
    </properties>

    ```
    spring:
      datasource:
        url: "jdbc:sqlite:/home/daniel/test.db"
        driver-class-name: org.sqlite.JDBC

      jpa:
        properties.hibernate.dialect: org.hibernate.dialect.SQLiteDialect
        hibernate:
          ddl-auto: update
    ```
    
    - Don't forget to re-configure the tests so that they do not use SQLite

- Spring data rest HAL-browser
    - Transform the QTV repository in (@RepositoryRestResource, path = qtv)
    - Demonstrate some post and get
    - Then add the HAL browser
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-rest</artifactId>
    </dependency>   
    <dependency>
        <groupId>org.springframework.data</groupId>
        <artifactId>spring-data-rest-hal-browser</artifactId>
    </dependency>


Recap :
    - We used a very heavyweight Spring project, that helps you do lots of stuff
    - We looked up the reference doc, the best place to find all things
    - We did non-normal out-of-the-box integrations
    - Spring is automagic woohoo !
    - ... But maybe a bit too magic ?


## Stuff that I could be showing :
- ConfigProperties annotation
- DB migrations (e.g. flyway)
- H2 console activated with DevTools
- Also, IntelliJ stuff : e.g. new file from template
- mvn spring-boot:build-info
- Setup logging levels with Hibernate


# Cloud patterns, RabbitMQ & (maybe) Circuit Breaker (...)
## What we'll build 
- The goal is to demonstrate how to loosely couple services without too much hassle
    - So that when a service fails, it's no big deal
    - We'll leverage RabbitMQ
    - And Hystrix "circuit breakers"
- So we'll build :
    - A client, that emulates an edge client sending data
    - The client also exposes its status through HTTP (e.g. codeur video)
- We'll modify our service so that :
    - It receives QTVs from the clients
    - It can query the clients for their status, and expose those

## Building the client
- Demonstrate the possibility of Scheduled things (fixedRate = 1000)
    - First by logging things in the console
    - sysout is enough
    - Can use the logger though, it's an interesting thing to show.
    
- Create a service to talk to the traffic service, through rest-template
    - small test ?
    - Attention : how do you create QTVs ? You could copy it, and decouple the two projects, or create "domain" package, that contain your models (and, if need be, interfaces).
    - Replace the scheduled logging by the service call

- Demonstrate it live, it posts things to the database

- But what happens in the server is down ?
    - Shut down the server 
    - Loosing all your data :(

## Building the server
- Show the Rabbit MQ guide on the docs.spring.io site ... It's complex !
    - This does a lot of boilerplate configuration for you (e.g. create a "transient queue")
- Can be simpler !
    - create qtv receiver
    - Bind with @RabbitListener(queues = {"rabbitmq-demo"})
    - Buuuut might fail because deserializing stuff
    - So we will configure the queue to deserialize json messages
    - We do this with beans ...
    @Bean
    SimpleRabbitListenerContainerFactory factory(ConnectionFactory connectionFactory,
                                                 SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }
    - And we update @RabbitListener with containerFactory = "factory"
- Good, now we are listening to RabbitMQ


## Update the client with the sender
- Update QTVService client
    @Autowired private RabbitTemplate rabbitTemplate;
    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    rabbitTemplate.convertAndSend("rabbitmq-demo", qtv);


## Demonstrate !
- Navigate to localhost:15672 ; guest/guest
- Start the server
- Start the client
- See all the things happen !
- Stop the server
- Messages still arrive
- And noooow restart the server
    - Tadaaaaa.

## Recap
- We've seen a cloud pattern, which is about fault tolerance and network partition
    - Helps relieve and smooth out the load on systems (I will take this message into account when I have time to do it)
    - And better distribute work across workers (the architecture does the distribution for you)
    - We get into "eventual consistency" : your system will be tolerant to data-loss BUT with a delay
- Rabbit MQ has very sane defaults and for a basic use-case it's great
    - But there are edge cases !
    - What if you run out of ram ?
    - Do you want to persist your messages to disk ?
    - What about reliability ? Show the guide : https://www.rabbitmq.com/reliability.html, talk about "at-least-once" delivery and "at-most-once" delivery.
- No silver bullet and no free lunch (pas de solution magique, et tout a un coût)
- What are the main problems in using RabbitMQ ?
    - Another dependency !
    - Another point of failure !
    - Another system to monitor !
    - Another step in the message passing : the system becomes harder to reason about for devs


# PCF
## What is it ?
- PCF is a PaaS, Platform as a Service
- Abstracts the infrastructure so devs can easily deploy apps with minimum config
    - Infrastructure being abstracted, only config is left
    - And config is code
    - Yay reproducible deploys !
    - Yay automation !
- It's based on Community CF, but with lots of proprietary enhancements
- Huge clients use it :
    - Orange
    - JP Morgan Chase
    - GE
    - Boeing
    - ...

## How does it work ?
- Show : https://apps.local.pcfdev.io
- Deploy a staticfile build pack
    - index.html
    - Staticfile
    - manifest.yml
        applications:
        - name: static-site 
          memory: 64M
          instances: 1
          buildpack: staticfile_buildpack
          routes:
          - route: hello.local.pcfdev.io

- Easy peasy, exactly the same for apps
