# BackEnd

## Pre-Requistes:
* [Java JDK8](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html) installed on the system.
* JAVA_HOME is set.
* PATH variable has JDK path.
* [Maven](https://maven.apache.org/download.cgi) is installed.


## Setup Instructions for local machine.
* If project is cloned/imported on Eclipse IDE:
    * You can choose to simply right click on `JobsApplication.java`, click `Run As` > `Java Application`.
    * The application will start-up on `http://localhost:8080`.
* Alternatively, 
    * You can open Terminal/Command Prompt at the project root folder, run `mvn clean install`.
    * This will create a jar file upon successful build.
    * Run `java -jar target\swe-0.0.1-SNAPSHOT.jar`.
    * This should boot up the application.
    * To test you can go to `http://localhost:8080` on the web browser.
