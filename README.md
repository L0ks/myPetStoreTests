# myPetStoreTests
This repository contains automated tests for the store section of https://petstore.swagger.io/.
These tests are created for the purpose of skill demonstration.

## Prerequisites
- **Java 8+**: Ensure you have Java 8 or higher installed.
- **Apache Maven**: Maven is used for project management and build automation.

## Installation

Clone the repository:

```bash
git clone https://github.com/L0ks/myPetStoreTests.git
cd myPetStoreTests
```

Install dependencies:

```bash
mvn install
```

## Running Tests

To execute all tests:

```bash
mvn test
```

To execute tests tagged with `store`:

```bash
mvn test -Dtags=store
```

To execute happy path tests:

```bash
mvn test -Dtags=happy
```

To execute negative path tests:

```bash
mvn test -Dtags=negative
```

### Running tests under specific environments

Add the `-Denv` flag with one of the following options: `dev`, `test`, `demo`, `live`.

For example, to run tests in the `test` environment:

```bash
mvn test -Denv=test
```

If no environment is provided explicitly, tests will run under the `dev` environment by default:

```bash
mvn test -Denv=dev
```

