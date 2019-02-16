package game;

import java.io.*;

/**
 * This class handles the feature of having a high score counter across different games.
 */

public class HighScoreUpdater {

    /**
     * The class keeps track of the current score and the previous high score. This class also has an instance variable
     * for the file that stores the previous scores of games.
     */

    private int currentScore;
    private int highscore;
    private File scoreFile = new File(HighScoreUpdater.class.getClassLoader().getResource("scores.txt").getPath());

    /**
     * retrieves the current high score.
     * @return the high score
     */

    public int getHighscore() {
        return this.highscore;
    }

    /**
     * retrieves the current score of the game.
     * @return the current score.
     */

    public int getCurrentScore() {
        return this.currentScore;
    }

    /**
     * Sets the current score of the game
     * @param newScore what the current score is
     */

    public void setCurrentScore(int newScore) {
        this.currentScore = newScore;
    }

    /**
     * This method updates the high score. This method loops through the text file with all the scores and
     * keeps track of which score was the highest. It then sets the instance variable highScore to the high score it
     * found.
     */

    public void updateHighScore() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(scoreFile));
            String line = reader.readLine();
            while (line != null) {
                try {
                    int score = Integer.parseInt(line.trim());
                    if (score > highscore) {
                        highscore = score;
                    }
                }
                catch (NumberFormatException e1) {
                    System.err.println("ignoring invalid score: " + line);
                }
                line = reader.readLine();
            }
            reader.close();
        }
        catch (IOException e) {
            System.err.println("Error reading scores from file");
        }
    }

    /**
     * This method adds the new score of the game to the text file.
     */

    public void addNewScoreToFile() {
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(scoreFile, true));
            output.newLine();
            output.append("" + currentScore);
            output.close();

        } catch (IOException e) {
            System.out.printf("error adding new score to file");
        }
    }
}
