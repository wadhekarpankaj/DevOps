#!/bin/sh

hosts=$1 #Stores the list of hosts seperated by comma
commands=$2 #Stores the command the be executed on multiple hosts

touch nodes.txt

echo "${hosts}" | cut -d',' --output-delimiter=$'\n' -f1- >> nodes.txt #Stores host names in nodes.txt file

cat nodes.txt

pssh -h nodes.txt -l root -A -i "${commands}" #PSSH connection allows us to execute ${commands} to be executed on multiple hosts

rm nodes.txt
