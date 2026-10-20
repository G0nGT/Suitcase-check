# Suitcase Check

This project is Suitcase Check. It is a command line program that helps college students like us plan for a trip without missing important items. It works by the user inputting how many days they are going for a trip, the location, and once they think they have everything they can use the check function to verify everything is packed, and if something isnt it throws alerts.

Commands:
- `trip <destination> <days>` This starts the trip with the destination and the number of days 1-30.
- `pack <item>` packs an item and puts it inside of the suitcase
- `unpack <item>` unpacks item and removes it from suitcase
- `list` lists the items inside
- `check` the verification check that either says it is all set or missing items
- `quit` exits

Some items are always required like a passport, charger, toothbrush, and at least one tshirt per the day.
Some locations are prone to rain year round so if the location is Seattle, London, or Vancouver then an umbrella is necessary

## Compile and Run

### How to compile and run the program

From the project folder run these commands:

```bash
mkdir bin
javac -cp lib/student.jar -d bin src/*.java test/*.java
java -cp bin Main
```

### How to run the JUnit tests

Once it compiles on windows run this: 

java -cp "bin;lib/student.jar" org.junit.runner.JUnitCore AlertTest CommandTest ItemTest LinkedChainTest MainTest PackingManagerTest PackingRulesTest TripTest

To run the test folders click the test folder and chooose Run as > JUnit Test

make sure to use Java 17 because some of our test cases fail when a new version is implemented
## Reflection

### What changed from the specification, and why?

the main design pretty much stayed the same, with main starting the program and commmand handling the input, while trip, packaging manager, and packingRules do the work of the program.


Command 
- **Command methods.** The spec had these ones to start the command (`startTrip`,
  `packing`, `unpacking`, and so on). To make it simpler we just wrote one `processCommand` method that checks which
  command was typed, since they all needed the same input parsing so it just sped up the process.
- **PackingManager.** We added `countItem` and `getItemCount`. `check()` has to know how many copies of the same item are in the backpack, and the tests need to check the suitcase while not reading the list text

- **PackingRules.** The spec left `check(...)` vague and it wasnt detailed . It is now `check(Trip, PackingManager)`
  and returns a `LinkedChain<Alert>`. `getRequired(Trip)` returns a `LinkedChain<Item>`.
- **Where validation lives.** The spec said invalid input would re-prompt inside the
  constructors. We changed it so that instead, `Trip`, `Item`, and `Alert` throw an `IllegalArgumentException`, and
  `Command` prints the message the exception throughs. The user can just type the next command. We did this for testing purposes because it is easier to check
- **Item names.** Checking the item name moved from `PackingManager.packItem()` into the `Item` 
  constructor. the names have to be 1-30 chars, and onlyl letters spaces and hyphens
- **Days.** The spec table only said negative or 0. We used the scope's range of 1 to 30.
  Text for the days prints "Days must be 1 to 30."
- **Tests.** We added test files for `Item`, `Alert`, `Trip`, and `Main`, since we moved
  validation into those classes. 
  
  Where the test plan said `packItem("")` should leave the
  suitcase alone, the test now expects the exception and ensures that the count stays the same

Smaller things we decided while coding: a new `trip` replaces the old one but keeps the
suitcase, item names are not case sensitive, and `quit` is handled in `Main`. Also, `list`
prints the newest item first because `LinkedChain.add()` puts new entries at the front.

These came from talking it over as a group while we implemented.

### What changed in LinkedChain, and why?

Nothing, we used it as given. We only call the public methods: add, remove, contains, count, size, isEmpty, and toArray.  The same class holds `Item` objects in the suitcase
and the required list, and `Alert` objects for the check results, so copies arent neccesary. For `remove` and `count` to work on items, we had to write `equals` and `hashCode`
in `Item`.

Big-O of our methods compared to the spec:

- `packItem` is O(1), same as the spec
- `unpackItem` is O(n), same as the spec
- `PackingRules.check is O(n²) in the worst case because it repeatedly calls contains and count, which each scan a chain. The 1–30 day limit bounds the required list, but it does not limit how many items the user can pack. 
- `processCommand depends on the command being processed. list and unpack are O(n), while check can be O(n²), so its overall worst-case complexity is O(n²).
### Which unknowns were resolved, and which remain open?

- **How to make a generic LinkedChain work with multiple object types.** One
  `LinkedChain<T>` is used for `Item` and `Alert` and it depends on `equals`.
- **How to make packing recommendations.**  We used a small fixed set of
  rules (basics, a tshirt per day, an umbrella for three cities). Still open is a nicer way to
  organize rules so we can add more without a long chain of if statements, and getting real
  weather data if we have the time to do so.
- **Odd item names.** The scope said we would think about this later. We picked 30 characters
  and letters, spaces, and hyphens only, but we have not decided if numbers or apostrophes
  should be allowed.

### What does not work yet?

- None of the stretch goals (weather, saving trips, templates) are done.
- The required list is fixed, and only Seattle, London, and Vancouver get an umbrella, we can figure out more rainy places
- Item names with numbers or apostrophes are rejected
- `quit` only works from `Main`. If you call `processCommand("quit")` it says unknown command.
- There is no loop that keeps asking until the input is valid. It prints the error and waits for
  the next command. We have to fix this
- No test changes the trip between two `check` commands.

## Next Steps

### What is left to finish the MVP, and in what order?

1. Run all the tests with the real `student.TestCase` on Java 17 and fix anything that breaks, or maybe make student test case compatible with all java versions.
2. Decide the item name rules (numbers, apostrophes) and update `Item` and `ItemTest`.
3. Clean up `PackingRules.check` so it is faster, or fix the Big-O estimate in the spec.
4. Move `quit` into `Command` so all commands are handled in one place.
5. Put the required items and rainy cities into named constants so they are easy to change, and add more rainy cities!
6. Final pass on comments and the README.

### Which stretch goals will be attempted or dropped?
- Rainy places should get an umbrella while places that are not rainy should not include one. 
- Reusable packing templates (like "Beach Trip"): we plan to attempt this one.
- Saving and reloading trips: dropped, the project is about the chain and not files.
- Weather API: dropped, it adds network code that does not test the chain. Also due to time restrictions we dont think we can pull this off.

### What new tests are needed?

- `Item`: names with numbers and apostrophes, and exactly 30 and 31 characters.
- `PackingRules.check`: a suitcase with lots of duplicates, and a trip changed between checks.
- `Command`: `quit` through `processCommand`, and extra spaces in commands.
- Templates: applying a template adds the right items, an unknown template name is rejected
  and does not change the suitcase, and applying one twice.

### Who does what?

| Remaining task | Team member |
| --- | --- |
| Run tests on Java 17 and fix failures | Nie |
| Item name rules and `ItemTest` | Bagu |
| `PackingRules.check` cleanup and tests | Luc |
| Move `quit` into `Command`, update `CommandTest` and `MainTest` | Bagu |
| Constants for required items | Shresta |
| Packing templates and their tests | Shresta, Nie |
| Comments and final README | Luc |

## Work Attribution

| Class or test file | Team member(s) | Contribution |
| --- | --- | --- |
| Main.java | Nie | Reads input in a loop and handles `quit` |
| Command.java | Bagu | Parses commands and prints error messages |
| Trip.java | Luc | Stores destination and days, validates them |
| PackingRules.java | Nie | Required items and `check()` |
| PackingManager.java | Nie | Pack, unpack, and list using `LinkedChain<Item>` |
| Item.java | Bagu | Item name, validation, `equals` and `hashCode` |
| Alert.java | Bagu | Holds one "Missing: ..." message |
| LinkedChain.java | CS 2114 Staff | Provided, we did not change it |
| MainTest.java | Bagu | Runs `Main` with fake input |
| CommandTest.java | Luc | Tests commands and bad input |
| TripTest.java | Nie | Tests valid and invalid trips |
| PackingRulesTest.java | Shresta | Tests required items and `check()` |
| PackingManagerTest.java | Luc | Tests pack, unpack, list, count |
| ItemTest.java | Shresta | Tests names and equality |
| AlertTest.java | Shresta | Tests messages |
| LinkedChainTest.java | Shresta | Tests the chain, including using it for `Alert` |

## GenAI Use Note

We used GenAI to help format the test plan and figure out what test cases we needed. We also used it to give guidance on the project when we stuck on debugging, on some logic errors, and when we could not figure out why our program was not working properly 

GenAI was used on the source and test files to help with logical errors and debugging problems

