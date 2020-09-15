# Lecture 1 (2020-08-31)

Failt, error, failure
Functional and structural testing (unit, integration, system)
Finding bugs:
    * Testing
    * Fault classification
    * Fault isolation

Static analysis:
    * Test abstract properties without running (unintialized/unused variables, empty/unspecified cases, antipattern)

Model checking:
    * Test the state-space for formally specified properties
    855556987+

Whitebox testing and blackbox testing
Specification: System testing
Detailed design: Functional testing
Implementation code: unit test

# Lecture 2 2020-09-10 (Guest lecture)
Testing: Quality assurance not just quality assessment

## S.M.A.R.T practice automatic tests
* Self checking
* Maintainable
* Act as documentation (Understand the test before understanding the code. Gives a hint about the previous developer)
* Repeatable and robust
* To the point - provide "defect triangulation"

## Test and build automation (CL or CD)
* Continuous Integration 
* Continuous Delivery
* Continuous Deployment

Smoke tests: Tests a set of logical units

Developer testing vs Acceptance testing (Developers for developers vs what customers want)
Acceptance tests: Accept legal stuff with customer that the software is working correctly.

* Find all function requirements that describes what the product should be able to do
* Non-functional relates to performance and so on..

Class AccountImplTest {function testWidthraw()..}
Files: main -> java -> MyUnit.java -> MyUnitTest and test -> java -> MyUnitTest.java -> testCalculate();

DTO (Data transfer object) -> Does not need to be tested if no function is included (excluding get and set functions)

## Execute running JUnit tests
From within Eclipse, command line: gradle, automation: jenkins

## Setup for JUnit
* Arrange - Instantiate Unit under test and setup test data
* Act - Run methods
* Assert - Assert response from methods

* Pragmatic unit testing
* Exhaustive testing -> test EVERYTHING

## TDD
* add test
* Run test to see new failure (it should fail since no code)
* Write code to fix (Just enough for it to work)

* Good book: Clean code -> Robert C. Martin
* Every single line of code has to be supported by a failing test!

* Something about Spy?
* Replace real classes with Mock


