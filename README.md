# Enemies-Tracker
Track events or elements

The program works by setting a stream of data with the twitter API and performing searches.

The searches are very customitzable, you can choose the topic, the location of the search and the radius of the action. First you are suposed to move around a worldwide map which will be generated using google maps API, when you arrive to the position that you want to perform the search on, you just need to write the parameters on the GUI.
After this, the coordinates, the country and province and other data will be extracted through APIs and some calculus, and then this information will be sent you the twitter search algorithm.

https://github.com/GuillemGarciaSabate/Enemies-Tracker/blob/master/1.PNG
https://github.com/GuillemGarciaSabate/Enemies-Tracker/blob/master/2.PNG

The twitter search algorithm will send a request with the paramters you have choosen and to bypass the tweets limit set by twitter will gather the maximum allowed by time and save the last tweet ID obtained.
Next a nested recursive search will be thrown obtaining the maximum tweets by search and saving the last ID and so on until 48h have been passed from the first search.

https://github.com/GuillemGarciaSabate/Enemies-Tracker/blob/master/3.PNG

The program is meant to provide data to coordinate fast-action responses in a militar-scenario.
There is a bug, if the place you are exploring does NOT have tweets like the topic you are looking for it WILL crash! excuse my retardedness! :)
