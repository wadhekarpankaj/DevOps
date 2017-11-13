How to use using-pssh.sh 

1- chmod +X using-pssh.sh
2- using-pssh.sh ${host-names/IP's} ${command}
eg.. using-pssh.sh 192.168.122.92:22,192.168.122.143:22 uptime

Note- If you dont have PSSH installed use below command
$ sudo apt install pssh