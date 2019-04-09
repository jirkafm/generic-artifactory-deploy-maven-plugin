# generic-artifactory-deploy-maven-plugin

Welcome to the generic-artifactory-deploy-maven-plugin plugin for Apache Maven 3.

This plugin is meant to provide an easy way to upload files into generic
package type of artifactory repository.

This plugin is based on work of another plugin [rest-maven-plugin](https://github.com/cjnygard/rest-maven-plugin). 
Credit to author of that plugin [Carl Nygard](https://github.com/cjnygard).

For Java 8 and newer.

## Available goals

 * generic-artifactory-deploy:deploy


## Getting started

To use this plugin and start uploading filse into artifactory,
declare the plugin and add a dependency on generic-artifactory-deploy-maven-plugin:

    <packaging>jar</packaging>
    ....
    <build>
      <plugins>
        <plugin>
          <groupId>com.github.jirkafm</groupId>
          <artifactId>generic-artifactory-deploy-maven-plugin</artifactId>
          <version>1.0.1</version>
        </plugin>
      </plugins>
    </build>
    ....
    <dependencies>
      <dependency>
        <groupId>com.github.jirkafm</groupId>
        <artifactId>generic-artifactory-deploy-maven-plugin</artifactId>
        <version>1.0.1</version>
      </dependency>
    </dependencies>

Check out example configuration [here](src/test/resources/unit/deploy-project/pom.xml)

### Adding source directories

To specify the input *fileset* you can add the following
configurations.  To use a single fileset:

    <configuration>
      <fileset>
        <directory>${project.resources[0].directory}</directory>
        <includes>
          <include>*.md</include>
        </includes>
      </fileset>
    </configuration>

To use multiple filesets, just wrap the single fileset in a *filesets*
list wrapper:

    <configuration>
      <filesets>
        <fileset>
          <directory>${project.resources[0].directory}</directory>
          <includes>
            <include>*.md</include>
          </includes>
        </fileset>
        <fileset>
          <....>
        </fileset>
      </filesets>
    </configuration>

### Artifactory URL

The Artifactory URL endpoint is specified via two parameters, the *url*
and the *repository*.  The components are separated so that mapping
separate execution configurations to different *repository* extensions
can still share the same base *url* setting.

    <configuration>
      <url>http://localhost:8081/artifactory/</url>
      <repository>generic-local</repository>
    </configuration>

Please note that if you only need to upload to one repository you
can omit *repository* parameter and set it in *url*.

    <configuration>
      <url>http://localhost:8081/artifactory/generic-local</url>
    </configuration>

### Authentication

Authentication is done through authentication header. User has several options to setup authentication header.
Artifactory wiki provides mechanism to authenticate please check this [link](https://www.jfrog.com/confluence/display/RTF/Artifactory+REST+API#ArtifactoryRESTAPI-Authentication) for details. 

Supported authentication mechanisms:
* Basic authentication 
* API Key
* Access token

Basic authentication:

    <configuration>
		<username>admin</username>
		<password>password</password>
    </configuration>

API Key:	
	
    <configuration>
		<apiKey>your_api_key</apiKey>
    </configuration>

Access token:	
	
    <configuration>
		<accessToken>your_access_token</accessToken>
    </configuration>

### Checksum-Based Storage

Validation of integrity of uploaded files is done through checksum headers. User can choose what kind of checksum headers will be 
generated for uploaded file: 

* SHA1
* SHA256
* MD5

There is a *fileChecksums* option in configuration to define checksum algorithms. Here is an example of configuration:

    <configuration>
		<fileChecksums>
			<param>SHA1</param>
			<param>SHA256</param>
			<param>MD5</param>
		</fileChecksums>
    </configuration>

## Optional REST configuration parameters

### Header Parameters

The Artifactory request URL can be further modified by adding header
parameters to the request.  These header parameters can be configured
via the *headers* tag, which is a map of key/value pairs.

    <configuration>
      <headers>
        <Content-Type>application/json</Content-Type>
      </headers>
    </configuration>
    
Please note that plugin itself creates several headers to succesfully
deploy files into artifactory. 

Following headers are usually created:

1. Authentication header
1. Checksum headers

### REST Method

Please note this option was taken from [rest-maven-plugin](https://github.com/cjnygard/rest-maven-plugin) you don't need
to configure this parameters. 

The REST request method can be configured via the *method* tag.
Currently only the default setting PUT is fully tested and supported.
Other HTTP methods can be set however they don't make much sense.

    <configuration>
      <method>PUT</method>
    </configuration>

### REST Query Parameters

Please note this option was taken from [rest-maven-plugin](https://github.com/cjnygard/rest-maven-plugin) you don't need
to configure this parameters. 

The REST request URL can be further modified by adding query
parameters to the request.  These query parameters can be configured
via the *queryParams* tag, which is a map of key/value pairs.

For example, to add the properties n=3 and addRequired=1 to the REST
request URL, the following configuration can be used:

    <configuration>
      <queryParams>
        <n>3</n>
        <addRequired>1</addRequired>
      </queryParams>
    </configuration>
    
### REST Request/Response types

Please note this option was taken from [rest-maven-plugin](https://github.com/cjnygard/rest-maven-plugin) you don't need
to configure this parameters. 

The REST request and response types can be configured via the
*requestType* and *responseType* tags.  Defaults for request and
response types are 'text/plain' and 'application/octet-stream'
respectively.

The request and response type parameters use the *MediaType* datatype
and consequently can be configured using the tags of the *MediaType*
object.  For example:

    <configuration>
      <requestType>
        <type>application</type>
        <subtype>json</subtype>
      </requestType>
      <responseType>
        <type>application</type>
        <subtype>pdf</subtype>
      </responseType>
    </configuration>

