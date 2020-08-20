# cetsevni


## Requirements

1. Find the highest common factor (https://en.wikipedia.org/wiki/Greatest_common_divisor) for a given array of integers
   ```
    public int highestCommonFactor(int[] numbers) {}
    ``` 
2. Given the attached addresses.json file which contains an array of address, do the following:

- [x] Write a function to return a pretty print version of an address in the format:\
      ```
      Type: Line details - city - province/state - postal code – country
      ``` 
- [x] Write a function to pretty print all the addresses in the attached file
- [ ] Write a function to print an address of a certain type (postal, physical, business)
- [x] Write a function to validate an address
    - A valid address must consist of a numeric postal code, a country, and at least one address line that is not blank or null. 
    - If the country is ZA, then a province is required as well. 
- [x] For each address in the attached file, determine if it is valid or not, and if not give an indication of what is invalid in a message format of your choice.
       
    ```
    // example Java function definition
    public String prettyPrintAddress(Address address) {
        // your code here
    }
    ```

## Running the samples

The following are pre-requisites to running samples in this project:
- [Maven](https://nodejs.org/) 3.x
- [Java 8](https://nodejs.org/)

> Depending on your IDE, you might have to enable Lombok annotation processing

#### There are two branches within this repo, and each branch represents a question in the assessment.

### Question 1
 
 Check out and build question 1 branch
```sh
$ git checkout -b investec-q1
$ mvn clean install
```
This should build and run pre configured unit tests. You can also run the app using custom arguments.
```sh
$ mvn exec:java \
    -Dexec.mainClass=com.investec.App \
    -Dexec.args="23 25"
```

### Question 2

While running the mvn command you might see exception stack trace on the console. This is a test verifying the exception throw and it is expected :)

Check out and build question 2 branch
```sh
$ git checkout -b investec-q2
$ mvn clean install 
```
Run the main app using:
```sh
$ mvn exec:java -Dexec.mainClass=com.investec.App 
```

The command will run the application from the main method and execute the functions as per requirements above.
