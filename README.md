# awc-bom
This is a BOM for the AW Central project

## Deploying the BOM

Checkout the `main` branch.

Run `./mvnw clean release:prepare`. This will:
- Increment the release version on `main` branch to the next SNAPSHOT version
- Other preparations

Run `./mvnw clean release:perform`. This will:
- Install the BOM to your local `.m2` repository with the current, non-SNAPSHOT version.
- Deploy the BOM to the private Maven repository at respy.io with the current, non-SNAPSHOT version.
- Commit and push changes to Github

Run `./mvnw release:clean` to remove all the temporary files `release:perform` generates

### Release rollback
If you change your mind after running `./mvnw clean release:perform`, you can run `./mvnw release:rollback`. 
You may need to manually delete the release tag from Github.