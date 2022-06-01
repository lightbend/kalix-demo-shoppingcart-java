# Shopping Cart DEMO - Java - Event Sourced
Not supported by Lightbend in any conceivable way, not open for contributions.
## Prerequisite
- Java 11 or later<br>
- Apache Maven 3.6 or higher<br>
- Kalix:
    - Register account: [Register](https://console.kalix.io/register)
    - `kalix` tool installed: [Kalix CLI](https://docs.kalix.io/kalix/install-kalix.html)
    - `kalix` login
    -  project `demo` created and set for `kalix`
- Docker 20.10.8 or higher (engine and client)<br>
- Docker Hub account (configured with Docker)<br>
  Access to the `gcr.io/kalix-public` container registry<br>
  cURL<br>
  IDE / editor<br>

## Generate Java project (terminal)

```
mvn archetype:generate \
-DarchetypeGroupId=io.kalix \
-DarchetypeArtifactId=kalix-maven-archetype \
-DarchetypeVersion=LATEST
```

```
Define value for property 'groupId': com.example
Define value for property 'artifactId': shoppingcart
Define value for property 'version' 1.0-SNAPSHOT: :
Define value for property 'package' com.example: : com.example.shoppingcart
```

## Import in IDE

## Cleanup (IDE)

Delete:<br>
`src/main/proto/com/example/shoppingcart/counter_api.proto`<br>
`src/main/proto/com/example/shoppingcart/domain/counter_domain.proto`

## API descriptor - endpoints (IDE)

Note: For code snippet insertion use command+J (MAC)<br>

1. Create file `shoppingcart_api.proto` in `src/main/proto/com/example/shoppingcart` folder.<br>
2. Edit `src/main/proto/com/example/shoppingcart/shoppingcart_api.proto` in IDE <br>
3. Insert header snippet: `aheader`
4. Insert commands snippet: `acmd`
5. Insert state snippet: `astate`
6. Insert service snippet: `asrv`
7. Add functions to service snippet (place cursor inside brackets `service ShoppingCartService { }`): `afunc`

## API descriptor - domain (IDE)

1. Create file `shoppingcart_domain.proto` in `src/main/proto/com/example/shoppingcart/domain` folder.<br>
2. Edit `src/main/proto/com/example/shoppingcart/domain/shoppingcart_domain.proto` in IDE <br>
3. Insert header snippet: `dheader`
4. Insert events snippet: `devts`
5. Insert state snippet: `dstate`

## API descriptor - codegen annotations (IDE)

1. Edit `src/main/proto/com/example/shoppingcart/shoppingcart_api.proto`
2. Insert codegen annotations (place cursor under `service ShoppingCartService {` ): `acodegen`

## Codegen

1. Code generation (terminal):
```
mvn compile
```
2. Refresh project (IDE)
3. Trigger Maven sync (IDE)


## Business logic implementation (IDE)

1. Edit `src/main/java/com/example/shoppingcart/domain/ShoppingCart` class
2. Delete class body
3. Insert help methods snippet: `ehelp`
4. Implement `addItem` command handler method
    1. Delete method implementation
    2. Insert snippet: `ecadd`
5. Implement `removeItem` command handler method
    1. Delete method implementation
    2. Insert snippet: `ecremove`
6. Implement `removeItem` command handler method
    1. Delete method implementation
    2. Insert snippet: `ecremove`
7. Implement `itemAdded` event handler method
    1. Delete method implementation
    2. Insert snippet: `eeadded`
8. Implement `itemRemoved` event handler method
    1. Delete method implementation
    2. Insert snippet: `eeremoved`

## Implement unit test
1. Edit `src/test/java/com/example/shoppingcart/domain/ShoppingCartTest` class
2. Delete class body
3. Insert code snippet: `ut`

## Run unit test (terminal)
```
mvn test
```

## Implement integration test (IDE)
1. Edit `src/it/java/com/example/shoppingcart/ShoppingCartTest` class
2. Delete everything under the constructor
3. Insert code snippet: `it`

## Run integration test (terminal)
```
mvn -Pit verify
```
## Run locally
??

## Package
1. Edit `pom.xml` and update `my-docker-repo` in `<dockerImage>my-docker-repo/${project.artifactId}</dockerImage>`
2. Execute in terminal:
```
mvn package docker:push
```

## Deploy to Kalix
1. Deploy project:
```
kalix service deploy shoppingcart aklikic/shoppingcart:1.0-SNAPSHOT
```
Note: replace `aklikic` as in Package
2. Expose service:
```
kalix services expose shoppingcart
```
3. List routes:
```
kalix routes list                 
```
```
NAME           HOSTNAME                                           PATHS             CORS ENABLED   STATUS          
shoppingcart   black-hill-1471.us-east1.kalix.app                  /->shoppingcart   false          NotConfigured   

```
Note: HOSTNAME is dedicated hostname for this service and is publicly accessible on Internet

## Test service in production
1. Add item to cart
```
curl -XPOST -d '{
  "product_id": "idA",
  "name": "apples",
  "quantity": "1"
}' https://black-hill-1471.us-east1.kalix.app/cart/1/items/add -H "Content-Type: application/json"
```
Note: For `dev` environment use host: `black-hill-1471.us-east1.kalix.app`
2. Get cart
```
curl -XGET https://black-hill-1471.us-east1.kalix.app/carts/1 -H "Content-Type: application/json"
```

## Eventing (optional)

1. Create file `shoppingcart_topic.proto` in `src/main/proto/com/example/shoppingcart` folder.<br>
2. Edit `src/main/proto/com/example/shoppingcart/shoppingcart_topic.proto` in IDE <br>
3. Insert header snippet: `dheader`
4. Insert events snippet: `devts`
5. Insert service snippet: `dsrv`
6. Insert functions snippet (place cursor inside brackets `service ShoppingCartToTopic { }`): `dfunc`
7. Insert codegen snippet (place cursor inside brackets `service ShoppingCartToTopic { }`): `dcodegen`


8. Code generation (terminal):
```
mvn compile
```
9. Refresh project (IDE)
10. Trigger Maven sync (IDE)
11. Edit `src/main/java/com/example/shoppingcart/ShoppingCartToTopicAction` class
12. Delete class body
13. Insert code snippet: `hall`


## Copy-paste list
```
mvn archetype:generate \
-DarchetypeGroupId=io.kalix \
-DarchetypeArtifactId=kalix-maven-archetype \
-DarchetypeVersion=LATEST
```
```
com.example
```
```
shoppingcart
```
```
com.example.shoppingcart
```
```
shoppingcart_api.proto
```
```
shoppingcart_domain.proto
```
```
mvn compile
```
```
mvn test
```
```
mvn -Pit verify
```
```
mvn deploy
```
```
curl -XPOST -d '{
  "product_id": "idA",
  "name": "apples",
  "quantity": "1"
}' https://black-hill-1471.us-east1.kalix.app/cart/1/items/add -H "Content-Type: application/json"
```
```
curl -XGET https://black-hill-1471.us-east1.kalix.app/carts/1 -H "Content-Type: application/json"
```
```
shoppingcart_topic.proto
```
