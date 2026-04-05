

````markdown id="u1w8kj"
# DevOps Flow - String Processor Service

This project implements:

- Convert string to uppercase
- Count vowels
- Check palindrome

DevOps pipeline:

GitHub → Maven → Docker → Jenkins → Kubernetes

---

## Repository Structure

devops-string-service/
│
├── pom.xml
├── Dockerfile
├── Jenkinsfile
├── k8s/
│   └── deployment.yaml
│
└── src/
    ├── main/
    │   └── java/
    │       └── com/devops/
    │           └── StringProcessor.java
    │
    └── test/
        └── java/
            └── com/devops/
                └── StringProcessorTest.java

---

## Phase 1: Clone Project

```bash
git clone https://github.com/Tharun-coder-hash/maven_app.git
cd maven_app
code .
````

---

## Phase 2: Java Application

### StringProcessor.java

```java
package com.devops;

public class StringProcessor {

    public static String toUpperCase(String input) {
        if (input == null) return null;
        return input.toUpperCase();
    }

    public static int countVowels(String input) {
        if (input == null) return 0;
        int count = 0;
        input = input.toLowerCase();

        for (char c : input.toCharArray()) {
            if ("aeiou".indexOf(c) != -1) {
                count++;
            }
        }
        return count;
    }

    public static boolean isPalindrome(String input) {
        if (input == null) return false;
        String reversed = new StringBuilder(input).reverse().toString();
        return input.equalsIgnoreCase(reversed);
    }

    public static void main(String[] args) {
        System.out.println("String Processor Service Running...");
    }
}
```

---

## Phase 3: JUnit Testing

### StringProcessorTest.java

```java
package com.devops;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class StringProcessorTest {

    @Test
    void testUpperCase() {
        assertEquals("HELLO", StringProcessor.toUpperCase("hello"));
    }

    @Test
    void testVowelCount() {
        assertEquals(2, StringProcessor.countVowels("hello"));
    }

    @Test
    void testPalindrome() {
        assertTrue(StringProcessor.isPalindrome("madam"));
        assertFalse(StringProcessor.isPalindrome("java"));
    }
}
```

---

## Phase 4: Maven Configuration (pom.xml)

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.devops</groupId>
    <artifactId>string-processor-service</artifactId>
    <version>1.0</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.10.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>com.devops.StringProcessor</mainClass>
                        </manifest>
                    </archive>
                </configuration>
            </plugin>

        </plugins>
    </build>
</project>
```

---

## Phase 5: Dockerfile (Single Stage)

```dockerfile
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## Phase 6: Jenkinsfile

```groovy
pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "string-processor:latest"
    }

    stages {

        stage('Checkout') {
            steps {
                git 'https://github.com/Tharun-coder-hash/maven_app.git'
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn clean test package'
            }
        }

        stage('Docker Build') {
            steps {
                bat "docker build -t %DOCKER_IMAGE% ."
            }
        }

        stage('Kubernetes Deploy') {
            steps {
                bat "kubectl apply -f k8s/deployment.yaml"
            }
        }
    }
}
```

---

## Phase 7: Kubernetes Deployment

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: string-processor

spec:
  replicas: 2

  selector:
    matchLabels:
      app: string-processor

  template:
    metadata:
      labels:
        app: string-processor

    spec:
      containers:
      - name: string-container
        image: string-processor:latest
        imagePullPolicy: IfNotPresent
        ports:
        - containerPort: 8080
```

---

## Phase 8: Commands

```bash
mvn clean test
mvn clean package

docker build -t string-processor:latest .

kubectl apply -f k8s/deployment.yaml

kubectl get pods
```

---

## Final Flow

GitHub → Maven → Docker → Jenkins → Kubernetes (2 Pods)

---

## Important Notes

* Class names are application-specific
* Tests validate logic correctness
* Docker uses compiled jar
* Jenkins automates pipeline
* Kubernetes ensures scalability (2 replicas)

```
