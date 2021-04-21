# awc-bom
This is a BOM for the AW Central project

## Deploying the BOM

### Pre-deploy

Because of the way `maven-release-plugin` is configured, by default the Maven version on the `main` branch
will be a snapshot version.

Before you deploy, you'll need to make sure you are on a release branch, which are created by `maven-release-plugin`.

If there is no appropriate release branch, you'll need to create one. To do that:

-  Checkout the `main` branch 
-  run `./mvnw clean release:prepare`. This will:
   - Increment the release version on `main` branch to the next SNAPSHOT version
   - Create a release branch with the current, non-SNAPSHOT version
   - Commit and push changes to Github
- run `./mvnw release:clean` to remove all the temporary files `release:perform` generates

### Deploy

To deploy the BOM to your local `.m2` repository, run `mvn clean install`.

To deploy to the private Maven repository at repsy.io, run `mvn clean deploy`.

### Release rollback
If you change your mind after running `./mvnw clean release:perform`, you can run `./mvnw release:rollback`. 
You may need to manually delete the release tag from Github.