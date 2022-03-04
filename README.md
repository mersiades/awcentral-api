[![<mersiades>](https://circleci.com/gh/mersiades/awcentral-api.svg?style=svg&circle-token=a03c2f7515d94f5c89541091557b734860340ddc)](https://app.circleci.com/pipelines/github/mersiades/awcentral-api)

# awcentral
A web interface to play Apocalypse World online

## Structure

This app is a Maven multi-module project, with three modules

1. `awc-web`: Does all the graphql and server stuff
2. `awc-data`: Does all the database stuff: repositories, models, services
3. `awc-content`: Provides all the hard-coded content from the Apocalypse World book

## Development

### Running the app locally

#### For regular development

1. `scripts/run-docker-dev.sh` to run the app in a Docker container.
2. Create an IDE run configuration. 
   - VM options: `-Djdk.tls.client.protocols=TLSv1.2`
   - Use the classpath of the `awc-web` module
   - Environment variables can be copied from `scripts/run-docker-dev.sh`

#### For end-to-end testing

1. `scripts/run-docker-e2e.sh` to run the app in a Docker container.
2. Create an IDE run configuration.
   - VM options: `-Djdk.tls.client.protocols=TLSv1.2`
   - Use the classpath of the `awc-web` module
   - Environment variables can be copied from `scripts/run-docker-e2e.sh`
    
## Testing

TODO: Flesh out
- run `mvn clean install`

## Deployment

### On Digital Ocean, with Docker

#### References

- https://beppecatanese.medium.com/1-2-3-springboot-docker-nginx-digitalocean-1152ad7143ba
- https://docs.docker.com/compose/env-file/
- https://stackoverflow.com/a/50201232/8035620
- https://www.digitalocean.com/community/tutorials/how-to-set-up-a-firewall-with-ufw-on-ubuntu-20-04
- https://eff-certbot.readthedocs.io/en/stable/using.html#certbot-command-line-options

#### Initial deployment

1. Created droplet via DO web portal. 
   - Marketplace > Docker on Ubuntu
2. Added Dockerfile, built Docker image
   - `docker build -t qdfn3af9be/awc-web:latest .`
3. Pushed Docker image to DockerHub
   - `docker push qdfn3af9be/awc-web`
4. Created folder on droplet for app
   - `ssh-add PATH/TO/DIGITAL_OCEAN/SSH_KEY`
   - `ssh root@<DROPLET IP ADDRESS>`
   - `cd ..`
   - `mkdir awc-web`
5. Added `nginx.conf`, `docker-compose.yml`, `.env`
   - `scp docker-compose.yml root@<DROPLET IP ADDRESS>:/awc-web/docker-compose.yml
     docker-compose.yml`
   - `scp .env root@137.184.38.28:/awc-web/.env`
   - `scp nginx.conf root@137.184.38.28:/awc-web/nginx.conf`
6. At Route53, assigned the droplet IP address to graphql.aw-central.com
7. Opened firewall port for Certbot
   - `ufw allow 80`
8. Installed and used Certbot
   - `ssh root@<DROPLET IP ADDRESS>`
   - `cd ../awc-web`
   - `sudo apt install certbot`
   - `certbot certonly --force-renewal --dry-run -d graphql.aw-central.com` (dry run) (Chose option 1)
   - `certbot certonly --force-renewal -d graphql.aw-central.com` (Chose option 1)
9. Start the app
   - `/awc-web/docker compose up`

#### Deploying an updated app
1. Build the app
   - Checkout the `master` branch
   - run `./mvnw clean release:prepare`
     - this gets the maven-release-plugin to update the release versions, and commits the changes
   - run `git fetch` and make sure local and remote are up-to-date.
   - run `./mvnw release:clean` to remove all the temporary files `release:prepare` generates
   - check out the newly created `tag`. (eg, aw-central-0.0.6)
   - run `mvn clean package`
     - if you change your mind after running `./mvnw clean release:perform`, you can run `./mvnw release:rollback`.
       - You may need to manually delete the release tag from Github
2. Build and push the Docker image
   - `docker build -t qdfn3af9be/awc-web:latest .`
   - `docker push qdfn3af9be/awc-web`
3. If `.env`, `nginx.conf` or `docker-compose.yml` have changed, upload them again using `scp` (see above)
4. SSH into the droplet
  - `ssh-add PATH/TO/DIGITAL_OCEAN/SSH_KEY`
  - `ssh root@<DROPLET IP ADDRESS>`
  - `cd ../awc-web`
5. Stop the containers
  - (should probably spin up another droplet to avoid downtime)
  - `docker-compose down`
6. Restart the containers, pulling in latest version of the images
   - `docker-compose up --build`

#### Renewing SSL certificates
1. SSH into the droplet
   - `ssh-add PATH/TO/DIGITAL_OCEAN/SSH_KEY`
   - `ssh root@<DROPLET IP ADDRESS>`
   - `cd ../awc-web`
2. Stop the containers
   - (should probably spin up another droplet to avoid downtime)
   - `docker-compose down`
3. Renew certs 
   - `certbot certonly --force-renewal -d graphql.aw-central.com` (choose option 1)
4. Restart containers
   - `docker-compose up`

### (OLD) Manual deploy on Digital Ocean droplet 
#### Create an SSH key and add it to Digital Ocean project
#### Create Droplet, attaching SSH key
#### Set up Java

- SSH into Droplet: `ssh root@<DROPLET IPv4>`
- Update packages in Droplet: `apt update`
- Install Java: `apt install openjdk-11-jre-headless`
- Test Java: `java --version`

#### Make a systemd .system file
https://clouding.io/hc/en-us/articles/360010806999-How-to-Deploy-Spring-Boot-Application-with-Nginx-on-Ubuntu-18-04
- See `/digital-ocean/awcentral-staging.service` for an example

#### Upload files (`scp` commands run from different terminal)

- Make a dir for the jar file: `mkdir artifact`
- Upload the jar to the artifact directory: `scp workspace/awcentral/awcentral-api/awc-web/target/awc-web-0.0.17-SNAPSHOT.jar root@<DROPLET IPv4>:/root/artifact`
- Upload the .system file to the systemd directory: `scp workspace/awcentral/awcentral-api/digital-ocean/awcentral-staging.service  root@<DROPLET IPv4>:/etc/systemd/system`

#### Start the app

- Restart the system: `systemctl daemon-reload`
- Start the app: `systemctl start awcentral-staging.service`
- Enable start on boot: `systemctl enable awcentral-staging.service`
- Check status: `systemctl status awcentral-staging.service`

#### Install nginx for reverse proxy
https://clouding.io/hc/en-us/articles/360010806999-How-to-Deploy-Spring-Boot-Application-with-Nginx-on-Ubuntu-18-04

- Install nginx: `apt-get install nginx -y`
- Create .conf file with url(s) as server_name. See `/digital-ocean/graphql-staging.aw-central.com.conf`
- Upload .conf file: `scp workspace/awcentral/awcentral-api/digital-ocean/graphql-staging.aw-central.com.conf  root@144.126.221.37:/etc/nginx/conf.d`
- Restart nginx to pick up new config: `systemctl restart nginx`
- Test from Postman using `POST` to `http://<DROPLET IPv4>:8080/graphql`

#### Point subdomain at Droplet

- At Route53, change the A record for the subdomain so that it points at the Droplet's IPv4.
- Check propagation at `www.whatsmydns.net`

#### Create an SSL certificate
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