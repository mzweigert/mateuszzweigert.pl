#!/usr/bin/env bash


while true; do
    read -p "Do you wish to debug app y - yes/ n - no? : " yn

    case ${yn} in
        [Yy]* )
            echo "Maven running..."
            mvn clean install -Dmaven.test.skip=true
            port=8080
            echo "Please connect to the port $port"
            nohup java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=${port},suspend=y -jar target/mateuszzweigert.pl.war &
        break;;
        [Nn]* )
            echo "Maven running..."
            mvn clean install -Dmaven.test.skip=true
            nohup java -jar target/mateuszzweigert.pl.war &
        break;;
        * ) echo "Please answer yes or no.";;
    esac
done
