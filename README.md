[![<mersiades>](https://circleci.com/gh/mersiades/awcentral.svg?style=svg&circle-token=a03c2f7515d94f5c89541091557b734860340ddc)](https://app.circleci.com/pipelines/github/mersiades/awcentral)

## awcentral
A web interface to play Apocalypse World online

### Running the app

#### Local

- start the db with `brew com.mersiades.awccontent.services start mongodb/brew/mongodb-community`
- run `AWCWebApplication` in IDE

### Deploying the app 

- Checkout the `master` branch
- run `./mvnw clean release:prepare` 
  - this get the maven-release-plugin to update the release versions, and commits the changes
- push the 2 new commits to the remote repo
- run `./mvnw release:clean` to remove all the temporary files `release:prepare` generates
- check out the newly created `tag`. (eg, aw-central-0.0.6)
- run `mvn clean package`
- manually deploy JAR to AWS Elastic Beanstalk environment



- if you change your mind after running `./mvnw clean release:perform`, you can run `./mvnw release:rollback`.
  - You may need to manually delete the release tag from Github
    
### Testing the app

- run `mvn clean install`