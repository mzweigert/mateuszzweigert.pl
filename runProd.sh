#!/usr/bin/env bash

validate_input() {
    local input=""
    if [[ $2="no_crypt" ]]
    then
	    read -p "$1" input
    else
	    read -s -p "$1" input
	    echo $"" > /dev/stderr
    fi;

    check_input=${#input}
    while [[ check_input -le 0 ]]; do
        echo "Write $1" > /dev/stderr
        if [[ $2 = "no_crypt" ]] ; then
	        read -p "$1" input
        else
            read -s -p "$1" input
            echo $"" > /dev/stderr
        fi;
	    check_input=${#input}
    done
    echo ${input}
}



security_username=$(validate_input "security username: " "no_crypt")
security_pass=$(validate_input "security password: ")
email_pass=$(validate_input "email password: ")

echo "Maven running..."
#mvn clean install
java -jar -Dspring.profiles.active=prod target/mateuszzweigert.pl.war --spring.security.user.name="${security_username}" \
--spring.security.user.password="${security_pass}" --email.password="${email_pass}"
