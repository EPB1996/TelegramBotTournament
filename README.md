# TelegramBotTournament
#### By EPB1996 

## What it does:
This bot lets you create and organize custom tournaments. (Originally thought to be used for BeerPong)

## How does it work:
1) Someone starts of by creating a Tournament. This creates a initial tournament file on the server. 
2) Player can enter a tournament by adding his team to the tournament. You always enter with a team consisting of 1-2 members.
For an other player to join a specific team a password (which is set by the creator of any given team) is required. 
3) As soon as there are enough teams availible the creator of a tournament will be able to start the tournament, denying all further application 
to the it. 
4) The bot then applies the logic of a tournament (there are different types available, SuddenDeath or Normal) and puts teams against each other. 
The results of such an encounter needs to be sent to the bot over telegram (with validation) and the winning team may proceed to the next round whereas
the looser is deleted from the tournament. 
5) The result of each round is gonna be broadcasted to all the different teams via the bot. 

Remark: Steps 1-3 is already implemented and running. The rest needs to follow. 

## Features of the bot:
Since I made this bot for private use with my friends I've already implemented some kind of trackable bonussystem. Each participation will give you 
points. Each ranking in tournaments gives you points whereas better rankings gain more points. These points are collected and saved to the according
chatID of each user (in a bigger scale this is annonymous, right now though I know which chatID belongs to whom^^). This allows me to do statistic graphs
and have even more fun. 
Furthermore there is a statistics about wins, draws and losses for each team/player.

To the more technical features: </br>
#### Tournament Creation:
 Everyone may start a new tournament in different modes: 
 - Sudden Death (SD), which eliminates everyone who lost a match
 - Normal , which is basically a tournament with groupphase and stuff. 
 (The logic behind these are not yet implemented) 
 
 
 #### Join Tournament
 To join simply select which tournament you would like to participate and chose between 
 - join an existing team, which will ask for a team code (should be provided by team creater)
 - create team, which will ask you for a name and password for your team
 (Password is used to join a team if there are more then one teammembers allowed)
 
 #### Result reporting  
 To report the winner of a match just press "Match Result". This will give you a list of all ongoing tournaments. After selecting one a message will be sent
 to the participant of the ongoing match. Both of them need to report the winner (validation) which will get the points.
 
 
 ## What is there to do:
 - Implementation of the logic of the tournaments & some points of the reporting.
 - Player Profile with access to actual points, wins, draws, losses and "global" ranking
 - Adding more control over tournament creation/alteration. 
 
 
 
 
 
 
 
 
 
 
 
