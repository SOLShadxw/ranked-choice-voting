import java.io.*;
import java.util.*;

public class RankedChoiceVoting {

    // Candidate class to store the name and vote count of a candidate
    static class Candidate {
        String name;
        int voteCount;

        Candidate(String name) {
            this.name = name;
            this.voteCount = 0;
        }
    }

    public static void main(String[] args) {
        // File input and output error handling
        File inputFile = new File("ballots.txt");
        if (!inputFile.exists() || !inputFile.isFile()) {
            System.out.println("Error: The input file 'ballots.txt' does not exist or is not a valid file.");
            return; // Exit the program
        }

        // Read ballots from the file
        List<List<String>> ballots = new ArrayList<>();
        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    List<String> ballot = new ArrayList<>(Arrays.asList(line.split(",")));
                    ballots.add(ballot);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: The input file 'ballots.txt' could not be found.");
            return; // Exit the program
        }

        // Set up the output file and error check
        File outputFile = new File("results.txt");
        PrintWriter out = null;
        try {
            out = new PrintWriter(outputFile);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not create or open the output file 'results.txt' for writing.");
            return; // Exit the program
        }

        // Print message about the output file location
        System.out.println("The election results will be saved to results.txt");

        // Run the election process
        String winner = electionProcess(ballots, out);
        out.println("Winner: " + winner);

        // Close the output stream
        out.close();
    }

    // Main election process
    public static String electionProcess(List<List<String>> ballots, PrintWriter out) {
        Set<String> candidateNames = new HashSet<>();
        for (List<String> ballot : ballots) {
            candidateNames.addAll(ballot);
        }

        Map<String, Candidate> candidates = new HashMap<>();
        for (String name : candidateNames) {
            candidates.put(name, new Candidate(name));
        }

        Set<String> eliminated = new HashSet<>();
        int totalVotes;
        double majorityNeeded;
        int round = 1;

        // Display candidates before the first round
        displayCandidates(candidateNames, out);

        while (true) {
            out.println("---- Round " + round + " ----");

            // Reset vote counts for each candidate
            resetVoteCounts(candidates);

            // Count first valid choice votes
            countVotes(ballots, candidates, eliminated);

            // Calculate total votes
            totalVotes = calculateTotalVotes(candidates, eliminated);

            // Majority needed to win
            majorityNeeded = totalVotes / 2.0;

            // Display vote counts
            displayVoteCounts(candidates, eliminated, out);

            // Check if any candidate has more than the majority
            String winner = checkForWinner(candidates, eliminated, majorityNeeded, out);
            if (winner != null) {
                return winner;
            }

            // Find candidates to eliminate
            List<String> toEliminate = findCandidatesToEliminate(candidates, eliminated);
            
            // Eliminate candidates with the lowest votes
            eliminateCandidates(toEliminate, eliminated, out);

            round++;
            out.println();
        }
    }

    // Display the candidates at the start of the election
    public static void displayCandidates(Set<String> candidateNames, PrintWriter out) {
        out.println("Candidates: ");
        for (String name : candidateNames) {
            out.println(name);
        }
        out.println();
    }

    // Reset vote counts before each round
    public static void resetVoteCounts(Map<String, Candidate> candidates) {
        for (Candidate c : candidates.values()) {
            c.voteCount = 0;
        }
    }

    // Count votes based on the ballots
    public static void countVotes(List<List<String>> ballots, Map<String, Candidate> candidates, Set<String> eliminated) {
        for (List<String> ballot : ballots) {
            // Remove empty or invalid candidates from the ballot
            List<String> cleanedBallot = new ArrayList<>();
            Set<String> seen = new HashSet<>();

            for (String choice : ballot) {
                String trimmed = choice.trim();
                if (!trimmed.isEmpty() && !seen.contains(trimmed)) {
                    cleanedBallot.add(trimmed);
                    seen.add(trimmed);
                }
            }

            // Count the first valid choice
            for (String choice : cleanedBallot) {
                if (!eliminated.contains(choice)) {
                    candidates.get(choice).voteCount++;
                    break;
                }
            }
        }
    }

    // Calculate total votes by counting non-eliminated candidates' votes
    public static int calculateTotalVotes(Map<String, Candidate> candidates, Set<String> eliminated) {
        int totalVotes = 0;
        for (Candidate c : candidates.values()) {
            if (!eliminated.contains(c.name)) {
                totalVotes += c.voteCount;
            }
        }
        return totalVotes;
    }

    // Display the vote counts for each candidate
    public static void displayVoteCounts(Map<String, Candidate> candidates, Set<String> eliminated, PrintWriter out) {
        for (Candidate c : candidates.values()) {
            if (!eliminated.contains(c.name)) {
                out.println(c.name + ": " + c.voteCount + " votes");
            }
        }
    }

    // Check if there is a winner
    public static String checkForWinner(Map<String, Candidate> candidates, Set<String> eliminated, double majorityNeeded, PrintWriter out) {
        for (Candidate c : candidates.values()) {
            if (!eliminated.contains(c.name) && c.voteCount > majorityNeeded) {
                out.println("\nWinner: " + c.name + " with " + c.voteCount + " votes.");
                return c.name;
            }
        }
        return null;
    }

    // Find the candidate(s) with the minimum votes to eliminate
    public static List<String> findCandidatesToEliminate(Map<String, Candidate> candidates, Set<String> eliminated) {
        int minVotes = Integer.MAX_VALUE;
        for (Candidate c : candidates.values()) {
            if (!eliminated.contains(c.name)) {
                minVotes = Math.min(minVotes, c.voteCount);
            }
        }

        List<String> toEliminate = new ArrayList<>();
        for (Candidate c : candidates.values()) {
            if (!eliminated.contains(c.name) && c.voteCount == minVotes) {
                toEliminate.add(c.name);
            }
        }
        return toEliminate;
    }

    // Eliminate candidates with the minimum votes
    public static void eliminateCandidates(List<String> toEliminate, Set<String> eliminated, PrintWriter out) {
        for (String elim : toEliminate) {
            out.println("Eliminated: " + elim);
            eliminated.add(elim);
        }
    }
}
