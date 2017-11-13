Follow below steps

Pull the CentOS image-
docker pull centos

Build the dockerfile-
docker build -t centos6 .
docker build -t kirillf/centos-tomcat .

How to use-
Put your war under the /opt/tomcat/webapps directory and run the following command-

docker run -v /opt/tomcat/webapps:/opt/tomcat/webapps -v /opt/tomcat/logs:/opt/tomcat/logs -p 8080:7080 -i -t --name centos-tomcat centos6
