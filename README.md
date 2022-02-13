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
- run `git fetch` and make sure local and remote are up-to-date.
- run `./mvnw release:clean` to remove all the temporary files `release:prepare` generates
- check out the newly created `tag`. (eg, aw-central-0.0.6)
- run `mvn clean package`
- manually deploy JAR to AWS Elastic Beanstalk environment



- if you change your mind after running `./mvnw clean release:perform`, you can run `./mvnw release:rollback`.
  - You may need to manually delete the release tag from Github
    
### Testing the app

- run `mvn clean install`


### Manual deploy on Digital Ocean droplet
## Create an SSH key and add it to Digital Ocean project
## Create Droplet, attaching SSH key
## Set up Java

  - SSH into Droplet: `ssh root@<DROPLET IPv4>`
  - Update packages in Droplet: `apt update`
  - Install Java: `apt install openjdk-11-jre-headless`
  - Test Java: `java --version`

## Make a systemd .system file
https://clouding.io/hc/en-us/articles/360010806999-How-to-Deploy-Spring-Boot-Application-with-Nginx-on-Ubuntu-18-04
  - See `/digital-ocean/awcentral-staging.service` for an example

## Upload files (`scp` commands run from different terminal)

  - Make a dir for the jar file: `mkdir artifact`
  - Upload the jar to the artifact directory: `scp workspace/awcentral/awcentral-api/awc-web/target/awc-web-0.0.17-SNAPSHOT.jar root@<DROPLET IPv4>:/root/artifact`
  - Upload the .system file to the systemd directory: `scp workspace/awcentral/awcentral-api/digital-ocean/awcentral-staging.service  root@<DROPLET IPv4>:/etc/systemd/system`

## Start the app

  - Restart the system: `systemctl daemon-reload`
  - Start the app: `systemctl start awcentral-staging.service`
  - Enable start on boot: `systemctl enable awcentral-staging.service`
  - Check status: `systemctl status awcentral-staging.service`

## Install nginx for reverse proxy
https://clouding.io/hc/en-us/articles/360010806999-How-to-Deploy-Spring-Boot-Application-with-Nginx-on-Ubuntu-18-04

  - Install nginx: `apt-get install nginx -y`
  - Create .conf file with url(s) as server_name. See `/digital-ocean/graphql-staging.aw-central.com.conf`
  - Upload .conf file: `scp workspace/awcentral/awcentral-api/digital-ocean/graphql-staging.aw-central.com.conf  root@144.126.221.37:/etc/nginx/conf.d`
  - Restart nginx to pick up new config: `systemctl restart nginx`
  - Test from Postman using `POST` to `http://<DROPLET IPv4>:8080/graphql`

## Point subdomain at Droplet

  - At Route53, change the A record for the subdomain so that it points at the Droplet's IPv4.
  - Check propagation at `www.whatsmydns.net`

## Create an SSL certificate
https://www.nginx.com/blog/using-free-ssltls-certificates-from-lets-encrypt-with-nginx/

  - SSH into Droplet: `ssh root@<DROPLET IPv4>`
  - Install the Let's Encrypt certbot client: `apt-get install certbot`
  - Install the certbot-nginx plugin: `apt-get install python3-certbot-nginx`
  - Test the conf file syntax and reload nginx: `nginx -t && nginx -s reload`
  - Generate SSL certificates: `certbot --nginx -d graphql-staging.aw-central.com`
  - Enter any email (I used michael@neonkingkong.com)
  - Accept ToS
  - I saw these warnings, but there didn't seem to be a real problem:

```
  nginx: [warn] conflicting server name "graphql-staging.aw-central.com" on [::]:443, ignored
  nginx: [warn] conflicting server name "graphql-staging.aw-central.com" on 0.0.0.0:443, ignored
```

  - Choose #2 (redirect to https)
