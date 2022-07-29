[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
![Build & Test](https://github.com/infor-cloud/acme-corp-extensions/workflows/Java%20CI/badge.svg?event=push)

# EDYCEM XtendM3 Extensions
Repository for EDYCEM XtendM3 Extensions

## Setup
The project is a standard Maven project using Mockito and JUnit 4. The source directory structure is similiar to any other Maven directory structure except for the Groovy source roots like below

```
.
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── groovy
    │   │   ├── xxx.groovy
    │   │   └── yyy.groovy
    │   ├── java
    │   └── resources
    │       └── metadata.yaml
    └── test
        ├── groovy
        │   ├── xxxTest.groovy
        │   └── xxxTest.groovy
        └── java
```

### Project Structure Descriptions

| File/directory name  | Description                                                                                    |
|:---------------------|:-----------------------------------------------------------------------------------------------|
| `mvnw`               | Maven wrapper executive for *nix environments                                                  |
| `mvnw.cmd`           | Maven wrapper executive for windows environments                                               |
| `pom.xml`            | Maven project definition file                                                                  |
| `README.md`          | Readme file for project documentation                                                          |
| `src/main/groovy`    | Groovy Extensions source directory                                                             |
| `xxx.groovy`         | Groovy Extension                                                                               |
| `src/main/java`      | Java source directory, **must  always be empty**                                               |
| `src/main/resources` | Resource directory                                                                             |
| `metadata.yaml`      | Extension metadata file, used for trigger definition, **must always be named `metadata.yaml`** |
| `src/test/groovy`    | Groovy Extensions unit test source directory                                                   |
| `xxxTest.groovy`     | Groovy Extension unit test, **name must always follow format `<extension name>Test.groovy`**   |
| `src/test/java`      | Java unit test source directory, **must always be empty**                                      |

