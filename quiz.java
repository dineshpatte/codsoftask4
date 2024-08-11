import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class quiz {

    // Inner class representing a quiz question
    static class Question {
        String prompt;
        String[] choices;
        String correctOption;

        Question(String prompt, String[] choices, String correctOption) {
            this.prompt = prompt;
            this.choices = choices;
            this.correctOption = correctOption;
        }
    }

    // Array containing all football quiz questions
    private static final Question[] questions = {
            new Question("Which country won the FIFA World Cup in 2018?",
                    new String[]{"1. Germany", "2. Brazil", "3. France", "4. Argentina"},
                    "3"),
            new Question("Who holds the record for the most goals in a calendar year?",
                    new String[]{"1. Lionel Messi", "2. Cristiano Ronaldo", "3. Pelé", "4. Gerd Müller"},
                    "1"),
            new Question("Which club has won the most UEFA Champions League titles?",
                    new String[]{"1. AC Milan", "2. FC Barcelona", "3. Liverpool", "4. Real Madrid"},
                    "4"),
            new Question("Who is the all-time top scorer in the English Premier League?",
                    new String[]{"1. Alan Shearer", "2. Wayne Rooney", "3. Sergio Agüero", "4. Thierry Henry"},
                    "1"),
            new Question("Which national team is known as 'La Roja'?",
                    new String[]{"1. Italy", "2. Spain", "3. Mexico", "4. Portugal"},
                    "2")
    };

    // Score tracking
    private static int totalScore = 0;
    private static int currentQuestionIndex = 0;
    private static final int TIME_LIMIT = 10; // seconds for each question
    private static boolean isAnswered = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (currentQuestionIndex < questions.length) {
            presentQuestion(questions[currentQuestionIndex], scanner);
            currentQuestionIndex++;
        }

        showFinalResults();
        scanner.close();
    }

    private static void presentQuestion(Question question, Scanner scanner) {
        System.out.println(question.prompt);
        for (String choice : question.choices) {
            System.out.println(choice);
        }

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int remainingTime = TIME_LIMIT;

            @Override
            public void run() {
                if (isAnswered) {
                    timer.cancel();
                } else if (remainingTime > 0) {
                    System.out.println("Time remaining: " + remainingTime + " seconds");
                    remainingTime--;
                } else {
                    System.out.println("Time's up!");
                    timer.cancel();
                    evaluateAnswer(null, question.correctOption); // Null indicates no answer was given
                }
            }
        };

        timer.scheduleAtFixedRate(task, 0, 1000);

        String userAnswer = scanner.nextLine();
        isAnswered = true;
        evaluateAnswer(userAnswer, question.correctOption);
    }

    private static void evaluateAnswer(String userAnswer, String correctOption) {
        if (userAnswer != null && userAnswer.equals(correctOption)) {
            totalScore++;
            System.out.println("That's correct!");
        } else {
            System.out.println("Wrong answer! The correct option was " + correctOption);
        }
        isAnswered = false;
        System.out.println();
    }

    private static void showFinalResults() {
        System.out.println("Quiz Finished!");
        System.out.println("You scored: " + totalScore + " out of " + questions.length);

        for (int i = 0; i < questions.length; i++) {
            System.out.println("Question " + (i + 1) + ": " + questions[i].prompt);
            System.out.println("Correct Answer: " + questions[i].correctOption);
        }
    }
}
