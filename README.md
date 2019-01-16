# microservices-rest-accounts
Microservices REST Accounts

mvn clean package

java -jar --add-modules java.xml.bind target/microservices-rest-accounts-1.1.0.RELEASE.jar registration

java -jar --add-modules java.xml.bind target/microservices-rest-accounts-1.1.0.RELEASE.jar accounts

java -jar --add-modules java.xml.bind target/microservices-rest-accounts-1.1.0.RELEASE.jar web

quick fix:
https://stackoverflow.com/questions/43574426/how-to-resolve-java-lang-noclassdeffounderror-javax-xml-bind-jaxbexception-in-j


Eureka Dashboard:
http://localhost:1111

Check applications registered: 
http://localhost:1111/eureka/apps/

Get Account Details
http://localhost:2222/accounts/owner/Keri

Find by Account id:
http://localhost:2222/accounts/123456789

Find by Account Name:
http://localhost:2222/accounts/owner/Keri
