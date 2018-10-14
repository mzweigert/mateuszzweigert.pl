# mateuszzweigert.pl

### Software usage:

* Java: `1.8`
* Maven: `3.3.x`
* PostgreSQL `9.x`

#### Configure PostgreSQL:

To install postgresql:
`sudo apt-get install postgresql`

To set password to postgres user:
`sudo -u postgres psql postgres`

To create new user with password:
`sudo -u postgres createuser --interactive --password <username>`

To create database owned by previous created user:
`sudo -u postgres createdb mateuszzweigertdb -O <username>`

To start, restart or stop postgresql service:
`sudo service postgresql start/restart/stop`

To login as user to database:
`psql -U <username> -d mateuszzweigertdb -W`

To check postgreSQL server status:
`/etc/init.d/postgresql status`

#### Run from command line:
`mvn spring-boot:run`

#### Run in develop environment:
`chmod 700 runDev.sh`

`./runDev.sh`

#### Run in production environment:
`chmod 700 runProd.sh`

`./runProd.sh`