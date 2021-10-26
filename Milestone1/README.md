# Monopoly Game
```
SYSC 3110 | Software Designing Project (SDK 17 working)
```
# Board
<div align-item="center"><img src="Images/Board.png" height="100%" width="100%"></div>

# Description:
The Monopoly Game is implemented following the <strong>MVC</strong> pattern and written in <strong>Java<strong>
<br>
Monopoly is a multiplayer economics-themed board game. In the game, players roll two dice to move around the game board, buying and selling properties, and developing them with houses and hotels. Players collect rent from their opponents, with the goal being to drive them into bankruptcy. Players receive a stipend every time they pass "Go", and can end up in jail, from which they cannot move until 3 turns have passed.

# Files required to run this program:
* Bank.java,
* BankProperty.java,
* Board.java,
* Business.java,
* ColorGroup.java,
* Dice.java,
* MonopolyController.java,
* MonopolyView.java,
* Player.java,
* PrivateProperty.java,
* PropertyAPI.java,
* Rail.java,
* RoleAPI.java,
* Square.java

# Usage:
```
Run the main method inside the MonopolyView.java file and follow the textual instruction.
```

# Contributors:
* Ngo Huu Gia Bao:                       huugiabaongo@cmail.carleton.ca,       
* Gabriel Benni Kelley Evensen:          bennievensen@cmail.carleton.ca,
* Zakaria Ismail:                        zakariaismail@cmail.carleton.ca,               
* Yuguo Liu:                             patrickliu@cmail.carleton.ca,

# Roadmap:
* Milestone 1:
An UI allows to play the Monopoly Game
  
* Milestone 2:
Be able to run the game in a graphical user interface

* Milestone 3:
Add additional features such as having the ability to buy houses and hotel on private properties square
Have the ability to add “AI” players into the game

* Milestone 4:
Be able to save/load game progresses,
Have an international version of the game in which players can have customized property names, values and currencies

# Known Issues:
When the prompt asks the player to enter an integer, entering an out of bound integer, a letter or any other symbols will cause the program to crash. Try Catch statements will be implemented later.
If a player does not own any properties, entering invalid property index will cause the program to crash
