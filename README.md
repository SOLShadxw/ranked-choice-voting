# ranked-choice-voting
<div align="center">
This repository will contain code implementing a ranked-choice voting system.

**What is ranked choice voting?** 

The ranked choice voting scheme is where voters “rank” different candidates based on personal preference, and is meant to ensure that the winner receives a variety of support from the voting population. The way this system works is in the following steps. In the first step, a voter orders the candidates by preference such as 1st, 2nd, 3rd etc choice. The key note on this, however, is that if a voter only selects one candidate, the vote is still counted. Next is the counting of votes process, where if a candidate receives over 50% of the 1st choice votes, then they win. However, if there is no majority, the candidate with the least votes is eliminated from the selection, and their votes are then distributed to the next candidate. To clear this up, the distribution goes to whoever was selected as the 2nd choice by the voter if they picked the eliminated candidate as their 1st pick. This process repeats until a candidate gets a majority of the votes. Advantages of using this over other systems include the following: encouraging positive campaigning and ensuring majority support instead of a simple case of “plurality”. 

**Here is the initial UML Diagram that was theorized** 
</div>
<p align="center">
  <img src="https://github.com/SOLShadxw/ranked-choice-voting/blob/main/initUML.png" alt="Basic UML" />
</p>
