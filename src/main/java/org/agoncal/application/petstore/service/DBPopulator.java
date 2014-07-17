package org.agoncal.application.petstore.service;

import java.util.ArrayList;
import java.util.List;

import org.agoncal.application.petstore.domain.*;
import org.agoncal.application.petstore.util.Loggable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
//everything is in this class
@Singleton

//this bean is started on startup
@Startup

//make logs 
@Loggable

//set the login details for the database
@DataSourceDefinition(
        className = "org.apache.derby.jdbc.EmbeddedDataSource",
        name = "java:global/jdbc/applicationPetstoreDS",
        user = "app",
        password = "app",
        databaseName = "applicationPetstoreDB",
        properties = {"connectionAttributes=;create=true"}
)
public class DBPopulator {

    // ======================================
    // =             Attributes             =
    // ======================================
//there are all our catagories
    private Category fish;
    private Category dog;
    private Category reptile;
    private Category cat;
    private Category bird;
    private Customer marc;
    private Customer bill;
    private Customer steve;
    private Customer user;
    private Customer admin;

    @Inject
    private CatalogService catalogService;

    @Inject
    private CustomerService customerService;

    // ======================================
    // =          Lifecycle Methods         =
    // ======================================

    //after making the bean load all our products and categories to the database
    @PostConstruct
    private void populateDB() {
        initCatalog();
        initCustomers();
    }

    //before the bean is destroyed delete all the products and catagories so that we dont have duplicates when we start it again
    @PreDestroy
    private void clearDB() {
        catalogService.removeCategory(fish);
        catalogService.removeCategory(dog);
        catalogService.removeCategory(reptile);
        catalogService.removeCategory(cat);
        catalogService.removeCategory(bird);
        customerService.removeCustomer(marc);
        customerService.removeCustomer(bill);
        customerService.removeCustomer(steve);
        customerService.removeCustomer(user);
        customerService.removeCustomer(admin);
    }

    // ======================================
    // =           Private Methods          =
    // ======================================
//the first part of populateDB which ADDS ALL THE DEETS TO THE PRODUCT OBJECTS
    private void initCatalog() {

        // Categories
        fish = new Category("Fish", "Any of numerous cold-blooded aquatic vertebrates characteristically having fins, gills, and a streamlined body");
        dog = new Category("Dogs", "A domesticated carnivorous mammal related to the foxes and wolves and raised in a wide variety of breeds");
        reptile = new Category("Reptiles", "Any of various cold-blooded, usually egg-laying vertebrates, such as a snake, lizard, crocodile, turtle");
        cat = new Category("Cats", " Small carnivorous mammal domesticated since early times as a catcher of rats and mice and as a pet and existing in several distinctive breeds and varieties");
        bird = new Category("Birds", "Any of the class Aves of warm-blooded, egg-laying, feathered vertebrates with forelimbs modified to form wings");

        // Products
        Product angelfish = new Product("Angelfish", "Saltwater fish from Australia", fish);
        Product tigerShark = new Product("Tiger Shark", "Saltwater fish from Australia", fish);
        Product koi = new Product("Koi", "Freshwater fish from Japan", fish);
        Product goldfish = new Product("Goldfish", "Freshwater fish from China", fish);
        fish.addProduct(angelfish);
        fish.addProduct(tigerShark);
        fish.addProduct(koi);
        fish.addProduct(goldfish);

        Product bulldog = new Product("Bulldog", "Friendly dog from England", dog);
        Product poodle = new Product("Poodle", "Cute dog from France", dog);
        Product dalmation = new Product("Dalmation", "Great dog for a fire station", dog);
        Product goldenRetriever = new Product("Golden Retriever", "Great family dog", dog);
        Product labradorRetriever = new Product("Labrador Retriever", "Great hunting dog", dog);
        Product chihuahua = new Product("Chihuahua", "Great companion dog", dog);
        dog.addProduct(bulldog);
        dog.addProduct(poodle);
        dog.addProduct(dalmation);
        dog.addProduct(goldenRetriever);
        dog.addProduct(labradorRetriever);
        dog.addProduct(chihuahua);

        Product rattlesnake = new Product("Rattlesnake", "Doubles as a watch dog", reptile);
        Product iguana = new Product("Iguana", "Friendly green friend", reptile);
        reptile.addProduct(rattlesnake);
        reptile.addProduct(iguana);

        Product manx = new Product("Manx", "Great for reducing mouse populations", cat);
        Product persian = new Product("Persian", "Friendly house cat, doubles as a princess", cat);
        cat.addProduct(manx);
        cat.addProduct(persian);

        Product amazonParrot = new Product("Amazon Parrot", "Great companion for up to 75 years", bird);
        Product finch = new Product("Finch", "Great stress reliever", bird);
        bird.addProduct(amazonParrot);
        bird.addProduct(finch);
        
        //Stock
        List<Stock> stocks = new ArrayList<Stock>();//list to hold all the stock objects
        //collect categories together
        List<Category> categories = new ArrayList<Category>();
        categories.add(fish);categories.add(dog);categories.add(reptile);categories.add(cat);categories.add(bird);
        //assign some stock to each
        for(Category c: categories){
        	for(Product p: c.getProducts()){
        		for(Item i: p.getItems()){
        			Stock stock = new Stock(i,10,3);
        			stocks.add(stock);
        			
        		}
        	}
        }
        
        // Items
        Item largeAngelfish = new Item("Large", 10.00f, "fish1.jpg", angelfish, "Lorem ipsum ");
        Item thootlessAngelfish = new Item("Thootless", 10.00f, "fish1.jpg", angelfish, "Lorem ipsum ");
        angelfish.addItem(largeAngelfish);
        angelfish.addItem(thootlessAngelfish);
        Item spottedTigerShark = new Item("Spotted", 12.00f, "fish4.jpg", tigerShark, "Lorem ipsum ");
        Item spotlessTigerShark = new Item("Spotless", 12.00f, "fish4.jpg", tigerShark, "Lorem ipsum ");
        tigerShark.addItem(spottedTigerShark);
        tigerShark.addItem(spotlessTigerShark);
        Item maleKoi = new Item("Male Adult", 12.00f, "fish3.jpg", koi, "Lorem ipsum ");
        Item femaleKoi = new Item("Female Adult", 12.00f, "fish3.jpg", koi, "Lorem ipsum ");
        koi.addItem(maleKoi);
        koi.addItem(femaleKoi);
        Item maleGoldfish = new Item("Male Puppy", 12.00f, "fish2.jpg", goldfish, "Lorem ipsum ");
        Item femaleGoldfish = new Item("Female Puppy", 12.00f, "fish2.jpg", goldfish, "Lorem ipsum ");
        goldfish.addItem(maleGoldfish);
        goldfish.addItem(femaleGoldfish);

        Item maleBulldog = new Item("Spotless Male Puppy", 22.00f, "dog1.jpg", bulldog, "Lorem ipsum ");
        Item femaleBulldog = new Item("Spotless Female Puppy", 22.00f, "dog1.jpg", bulldog, "Lorem ipsum ");
        bulldog.addItem(maleBulldog);
        bulldog.addItem(femaleBulldog);
        Item malePoodle = new Item("Spotted Male Puppy", 32.00f, "dog2.jpg", poodle, "Lorem ipsum ");
        Item femalePoodle = new Item("Spotted Female Puppy", 32.00f, "dog2.jpg", poodle, "Lorem ipsum ");
        poodle.addItem(malePoodle);
        poodle.addItem(femalePoodle);
        Item tailedDalmation = new Item("Tailed", 62.00f, "dog3.jpg", dalmation, "Lorem ipsum ");
        Item tailessDalmation = new Item("Tailless", 62.00f, "dog3.jpg", dalmation, "Lorem ipsum ");
        dalmation.addItem(tailedDalmation);
        dalmation.addItem(tailessDalmation);
        Item tailedGoldenRetriever = new Item("Tailed", 82.00f, "dog4.jpg", goldenRetriever, "Lorem ipsum ");
        Item tailessGoldenRetriever = new Item("Tailless", 82.00f, "dog4.jpg", goldenRetriever, "Lorem ipsum ");
        goldenRetriever.addItem(tailedGoldenRetriever);
        goldenRetriever.addItem(tailessGoldenRetriever);
        Item tailedLabradorRetriever = new Item("Tailed", 100.00f, "dog5.jpg", labradorRetriever, "Lorem ipsum ");
        Item tailessLabradorRetriever = new Item("Tailless", 100.00f, "dog5.jpg", labradorRetriever, "Lorem ipsum");
        labradorRetriever.addItem(tailedLabradorRetriever);
        labradorRetriever.addItem(tailessLabradorRetriever);
        Item maleChihuahua = new Item("Male Adult", 100.00f, "dog6.jpg", chihuahua, "Lorem ipsum ");
        Item femaleChihuahua = new Item("Female Adult", 100.00f, "dog6.jpg", chihuahua, "Lorem ipsum ");
        chihuahua.addItem(maleChihuahua);
        chihuahua.addItem(femaleChihuahua);

        Item femaleRattlesnake = new Item("Female Adult", 20.00f, "reptile1.jpg", rattlesnake, "Lorem ipsum ");
        Item maleRattlesnake = new Item("Male Adult", 20.00f, "reptile1.jpg", rattlesnake, "Lorem ipsum ");
        rattlesnake.addItem(femaleRattlesnake);
        rattlesnake.addItem(maleRattlesnake);
        Item femaleIguana = new Item("Female Adult", 150.00f, "lizard1.jpg", iguana, "Lorem ipsum ");
        Item maleIguana = new Item("Male Adult", 160.00f, "lizard1.jpg", iguana, "Lorem ipsum ");
        iguana.addItem(femaleIguana);
        iguana.addItem(maleIguana);

        Item maleManx = new Item("Male Adult", 120.00f, "cat1.jpg", manx, "Lorem ipsum ");
        Item femaleManx = new Item("Female Adult", 120.00f, "cat1.jpg", manx, "Lorem ipsum ");
        manx.addItem(maleManx);
        manx.addItem(femaleManx);
        Item malePersian = new Item("Male Adult", 70.00f, "cat2.jpg", persian, "Lorem ipsum ");
        Item femalePersian = new Item("Female Adult", 90.00f, "cat2.jpg", persian, "Lorem ipsum ");
        persian.addItem(malePersian);
        persian.addItem(femalePersian);

        Item maleAmazonParrot = new Item("Male Adult", 120.00f, "bird2.jpg", amazonParrot, "Lorem ipsum ");
        Item femaleAmazonParrot = new Item("Female Adult", 120.00f, "bird2.jpg", amazonParrot, "Lorem ipsum ");
        amazonParrot.addItem(maleAmazonParrot);
        amazonParrot.addItem(femaleAmazonParrot);
        Item maleFinch = new Item("Male Adult", 75.00f, "bird1.jpg", finch, "Lorem ipsum ");
        Item femaleFinch = new Item("Female Adult", 80.00f, "bird1.jpg", finch, "Lorem ipsum ");
        finch.addItem(maleFinch);
        finch.addItem(femaleFinch);

        catalogService.createCategory(fish);
        catalogService.createCategory(dog);
        catalogService.createCategory(reptile);
        catalogService.createCategory(cat);
        catalogService.createCategory(bird);
    }
//this method adds all the default customers to the database so that someone can log in
    private void initCustomers() {
        marc = new Customer("Marc", "Fleury", "marc", "marc", "marc@jboss.org", new Address("65 Ritherdon Road", "Los Angeles", "56421", "USA"));
        bill = new Customer("Bill", "Gates", "bill", "bill", "bill.gates@microsoft.com", new Address("27 West Side", "Alhabama", "8401", "USA"));
        steve = new Customer("Steve", "Jobs", "jobs", "jobs", "steve.jobs@apple.com", new Address("154 Star Boulevard", "San Francisco", "5455", "USA"));
        user = new Customer("User", "User", "user", "user", "user@petstore.org", new Address("Petstore", "Land", "666", "Nowhere"));
        admin = new Customer("Admin", "Admin", "admin", "admin", "admin@petstore.org", new Address("Petstore", "Land", "666", "Nowhere"));

        customerService.createCustomer(marc);
        customerService.createCustomer(bill);
        customerService.createCustomer(steve);
        customerService.createCustomer(user);
        customerService.createCustomer(admin);
    }

}
