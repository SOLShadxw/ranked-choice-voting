# ranked-choice-voting

This repository will contain code implementing a ranked-choice voting system.

**What is ranked choice voting?** 

The ranked choice voting scheme is where voters “rank” different candidates based on personal preference, and is meant to ensure that the winner receives a variety of support from the voting population. The way this system works is in the following steps. In the first step, a voter orders the candidates by preference such as 1st, 2nd, 3rd etc choice. The key note on this, however, is that if a voter only selects one candidate, the vote is still counted. Next is the counting of votes process, where if a candidate receives over 50% of the 1st choice votes, then they win. However, if there is no majority, the candidate with the least votes is eliminated from the selection, and their votes are then distributed to the next candidate. To clear this up, the distribution goes to whoever was selected as the 2nd choice by the voter if they picked the eliminated candidate as their 1st pick. This process repeats until a candidate gets a majority of the votes. Advantages of using this over other systems include the following: encouraging positive campaigning and ensuring majority support instead of a simple case of “plurality”. 

**Here is the initial UML Diagram that was theorized** 

![Basic UML](https://github.com/SOLShadxw/ranked-choice-voting/blob/main/initUML.png)

**Overview of RankedChoiceVoting.java** 

**Summary of Workflow**

1. Read Input: Load ballots from a file called ballots.txt
2. Initialize Candidates: Ensure unique candidates are saved from the ballots.
3. Count Votes: Votes are counted depending on voter preferences. If only one (or two) candidate is selected, ensure that interaction is accounted for.
4. Elimiante Candidates: If there is no winner, the candidate with the least amount of votes is eliminated.
5. Repeat: Continue steps 3 and 4 until a winner is found.
6. Output Results: Results of each round and the overall winner is output as results.txt

**File Handling and Input Validation**

In the attached file, the program checks for the input file, ensuring it exists and is valid for use. if the file does not exist or is unabe to be read, there is an implementation to send an error output. This ensures that the program doesnt crash and that the ballot can in fact be read correctly. 

** Setup for Candidates**

The setup for the candidate class is performed by dynamically creating a list of candidates by scanning through the ballot file, and extracting the unique candidate names. Doing so allows us to identify the candidates and then allowing the porgram to handle an (initially) unknown number of candidates in a way that is flexible. 

**Vote Counting Process**

After initialization, the program counts the votes for each round. Votes are tallied based on the first-choice preference in each ballot. A mojoirty threshold is then caluclated to determine if and when a candidate wins. This is the main approach to the ranked choice voting system (as explained earlier) 

**Elimination Process**

If no candidate has receied enough votes to win in a round, the candidate (or candidates) with the fewest votes is eliminated. After a candidate is eliminated, ballots are "redistributed" so that votes are counted for the next choice on a voters ballot. This process is repeated until there is only one candidate or a winner is determined. Again, this system was explained through our inital exploration of the ranked choice voting scheme. 

**Error Handling**

The program also includes error checks , to make sure that an input file exists and is readable, as well as giving us a way to check that an output file can be created and written to. If either of these errors are generated, then the program stops and a error message is generated in the console. 

**Results Output** 

The program outputs the results in a file called results.txt in a specific way to ensure it is easy to read. In this output, each round's vote count, candidate eliminations, and if applicable, the winner, is logged to this file. This allows for a review of the round to round process. 

**So, Why Chose this Approach?**

*Flexibility*: This approach can read an arbitrary amount of candidates and voters. This approach also doesnt require a predefined list of candidates, allowing us to adapt based on the input file. 

*Transparency*: Outputting the details of each round and elimination allows for anyone to follow the election process step by step, so that an unexpected output could be traced. 
