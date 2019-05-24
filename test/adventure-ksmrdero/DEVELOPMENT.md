# Developing Adventure 2

## Code design changes from Adventure Time:
* Added more vertical whitespace in code
* Made naming variables and classes more specific
* Broke up long methods
* Made class and methods non-static
* Avoided network requests in testing

## Plan for extending Adventure game:
* Build new adventure world revolving around solving a murder mystery
* Users can pick up items that can help advance case
* Game ends when users have solved the case and finds the criminal
* Add hidden implementation to hide certain locations unless user has the right item in hand

### Issues and challenges during development:
* Hidden room implementation made it difficult to correctly determine what to display on screen
* Having users pickup items was difficult to implement due to the many changes of state