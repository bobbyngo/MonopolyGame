# Monopoly Game
```
SYSC 3110 | Software Designing Project (SDK 17 working)
```
# Game.Board
<div align-item="center"><img src="Images/Board.png" height="100%" width="100%"></div>

# Description:
The Monopoly Game is implemented following the <strong>MVC</strong> pattern and written in <strong>Java</strong>
<br>
Monopoly is a multiplayer economics-themed board game. In the game, players roll two dice to move around the game board, buying and selling properties, and developing them with houses and hotels. Players collect rent from their opponents, with the goal being to drive them into bankruptcy. Players receive a stipend every time they pass "Go", and can end up in jail, from which they cannot move until 3 turns have passed.

# Files required to run this program:
* DiceImg package
* Game.Bank.java,
* Game.BankProperty.java,
* Game.Board.java,
* Game.Business.java,
* Game.BuyHouseHotelDialog.java
* Game.Dice.java,
* Game.MonopolyController.java,
* Game.MonopolyGUIView.java,
* Game.MonopolyModel.java
* Game.Player.java,
* PrivatePropertyListHouseModel.java,
* PrivatePropertyListModel.java,
* Game.PrivateProperty.java,
* Game.PropertyAPI.java,
* Game.Rail.java,
* Game.RoleAPI.java,
* Game.SellPlayerPropertyDialog.java,
* Game.Square.java

# Usage:
```
Run the main method inside the MonopolyGUIView.java file and follow the user interface.
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

# Changes From Previous Milestone

The players can now have an international version of the game by importing an XML file which alters the name and prices of the squares
The players are now able to save and load game progresses

Added files:
* Game.RentableAPI.java: Interface for Business and Rail class
* Game.MonopolyModel.java: Class which contains all the game data and methods to alter/manipulate them

Removed file:
Game.MonopolyView.java: Class previously used for textual based game play, with the implementation of the GUI, this class is no longer required
  
# Known Issue
* Game.Player label is updated whenever the player rolls the dice, however, if a player landed on a square that is already occupied, the previous player's label will be erased. This is only a visual issue and does not have any impact on the player's actual position in the system.
* If programme is not in fullscreen then board may glitch
