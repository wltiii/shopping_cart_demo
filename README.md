## Instructions for the assignment
1. Clone this repository on your machine.
1. Use your IDE of choice to complete the assignment.
1. When you are done with the solution and have pushed it to the repo, [you can submit the assignment here](https://app.snapcode.review/submission_links/633cada6-2e6f-424e-bd7d-fc3aa2e00eaa).
1. Once you submitted your solution, your access to the repository will be revoked. So make sure that before you do submit the solution, that you have completed the problem and pushed all your changes.

## Before you start
### Why complete this task?

We want to make the interview process as simple and stress-free as possible. That’s why we ask you to complete the first stage of the process from the comfort of your own home.

Your submission will help us to learn about your skills and approach. If we think you’re a good fit for our network, we’ll use your submission in the next interview stages too.

### About the task

We’ll be using a simple shopping cart, similar to those used by e-commerce websites, as the domain for this problem.

There’s no time limit for this task. We find most people complete the exercise in around 90 minutes.

### Tips on what we’re looking for

1. **Test coverage**

Your solution should:

- be developed ‘test-first’
- have good unit tests
- cover common paths
- be self-contained and not rely on external systems when running tests
- integration tests should run against stubs using something like [WireMock](https://wiremock.org/), [MockServer](https://www.mock-server.com/) or similar, not the real API

2. **Simplicity**

We value simplicity as an architectural virtue and a development practice. Solutions should reflect the difficulty of the assigned task, and shouldn’t be overly complex. We prefer simple, well tested solutions over clever solutions.

Please avoid:

- layers of abstraction
- patterns
- custom test frameworks
- architectural features that aren’t called for
- a web, desktop, command line or any other kind of app

3. **Self-explanatory code**

The solution you produce must speak for itself. Multiple paragraphs explaining the solution is a sign that the code isn’t straightforward enough to understand on its own.

4. **Dealing with ambiguity**

If there’s any ambiguity, please add this in a section at the bottom of the README. You should also make a choice to resolve the ambiguity.

## Begin the task

Create a shopping cart package that facilitates 2 basic capabilities.

1. Add multiple products to the cart. A product has a name and price. You should be able to specify how many of each product is being added to the cart, and provide a means to observe the resulting state.

2. Calculate the totals:
   1. Cart subtotal (sum of price for all items)
   2. Tax payable (charged at 12.5% on the subtotal)
   3. Total payable (subtotal + tax)
   
Pricing data for each product should be retrieved via an HTTP call. You can find example pricing data for a set of sample products at the URL’s below. Prices should be rounded up where required.

You should assume that the product name (lowercase) matches the file name. Use whatever libraries you like to fetch and parse the JSON.

## Valid Product Information URLs
- https://equalexperts.github.io/backend-take-home-test-data/cheerios.json
- https://equalexperts.github.io/backend-take-home-test-data/cornflakes.json
- https://equalexperts.github.io/backend-take-home-test-data/frosties.json
- https://equalexperts.github.io/backend-take-home-test-data/shreddies.json
- https://equalexperts.github.io/backend-take-home-test-data/weetabix.json

## Sample based on the data
The below is a sample with the correct values you can use to confirm your calculations
```
  Add 1 × cornflakes @2.52 each
  Add another 1 x cornflakes @2.52 each
  Add 1 × weetabix @9.98 each
  
  Then: 
  
  Cart contains 2 x conflakes
  Cart contains 1 x weetabix
  Subtotal = 15.02
  Tax = 1.88
  Total = 16.90
```

## Comments

### Ambiguities
Real world implementation would mean discussing requirements and getting resolution that way. I have made choices that I feel were in keeping with the overall requirements of this test, but may or may not be acceptable in a production application - possibly acceptable in a POC or MVP, however.

1. The statement "You should be able to specify how many of each product is being added to the cart, and provide a means to observe the resulting state." is the most ambiguous, in my opinion. It does not state how the resulting state should be observed. Providing methods to get total counts and counts by product seemed the most straight-forward.
2. There is no requirement specified for not found conditions or other exceptions when retrieving data. I resolved this by returning an empty Optional.
3. There is no requirement specified for the quantity of any specific product, nor for total product quantity. I ignored this for the purpose of this exercise. This could have serious consequences in a production application.
4. There is no requirement specified for the total number of different products that can be added to the cart. I ignored this for the purpose of this exercise. This could have serious consequences in a production application.
5. There is no requirement specified for the max price (nor min) of any specific product. I ignored this for the purpose of this exercise, though do not allow negative pricing.
6. There is no requirement of the handling of removing more of an item than is currently in cart. I felt setting the value to 0, which then caused it to be removed, was acceptable.
7. You called out that the code should speak for itself, that comments should not be necessary to explain the code. I agree with that, and have tried to practice that for years. The only real exception to that would be Javadoc (or similar in other languages). Documenting the public API is handy for developers who wish to use it. For the purpose of this exercise, however, I felt that was not something upon which I would be judged. Too, some teams adamantly forego such documentation. I do understand the reasons why that may be a choice as well.

I feel that covers most of the assumptions made.

A couple of closing thoughts... 

I have not worked with Java for a few years, so there may be features of which I am unaware and have not taken advantage of. I don't feel that is the most important thing here. Anyone that has been around a long time quickly realizes that C-based languages have overlap. The actual details of the language matter much less than how one thinks about development, overall approaches, understanding of practices and trade-offs, etc. My lack of recent experience, I don't feel, is a great barrier. Most of what I need to know I will understand within just a few days. And, of course, one is rarely working in a vacuum without existing code in the client code base for reference and nor not having colleagues of whom questions can be asked. 

When asked to do tests such as this, developers are kind of stuck in that it is unclear how rigorous they should be. On one hand, they want to put their best foot forward, have a well-developed application, and hopefully one that demonstrates some of their thought process. On  the other, that can lead to overly complex applications for the purpose of the test. Should it be closer to a POC? Should it be closer to production ready? Hard to say. That said, I have incorporated some practices to a greater or lesser extent, such as organizing the code along a DDD architecture style and developing with the concept of "making invalid state unrepresentable", which is a mantra of the Type Driven Design movement. I have not fully embraced either of these concepts fully herein, but would be happy to discuss. I did adhere to strong testing, however. I feel, unless truly just a POC or possibly an MVC, good testing is important.

Thank you for this opportunity!