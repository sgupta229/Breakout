package game;

import java.io.*;

public class HighScoreUpdater {

    private int currentScore;
    private int highscore;
    private File scoreFile = new File(HighScoreUpdater.class.getClassLoader().getResource("scores.txt").getPath());

    public int getHighscore() {
        return this.highscore;
    }

    public int getCurrentScore() {
        return this.currentScore;
    }

    public void setCurrentScore(int newScore) {
        this.currentScore = newScore;
    }

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
