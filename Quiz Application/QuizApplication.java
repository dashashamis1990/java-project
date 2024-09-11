import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

class Question {
    private final String questionText;
    private final String optionA;
    private final String optionB;
    private final String optionC;
    private final String optionD;
    private final String correctAnswer;

    public Question(String questionText, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public boolean isCorrectAnswer(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

public class QuizApplication {
    private static final String QUESTIONS_FILE = "questions.txt";
    private static final int SCORE_PER_QUESTION = 10;
    private static final Logger logger = Logger.getLogger(QuizApplication.class.getName());

    private List<Question> questions;
    private Scanner scanner;

    public void loadQuestions() throws FileNotFoundException {
        File file = new File(QUESTIONS_FILE);
        scanner = new Scanner(file);

        questions = new ArrayList<>();

        int lineCount = 1;
        try {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                lineCount++;

                if (line.startsWith("Question:")) {
                    String questionText = extractContent(line);
                    String optionAText = extractContent(scanner.nextLine());
                    String optionBText = extractContent(scanner.nextLine());
                    String optionCText = extractContent(scanner.nextLine());
                    String optionDText = extractContent(scanner.nextLine());
                    String correctAnswer = extractContent(scanner.nextLine());

                    Question question = new Question(questionText, optionAText, optionBText, optionCText, optionDText, correctAnswer);
                    questions.add(question);
                } else {
                    logger.warning("Invalid question format at line " + lineCount);
                }
            }
        } finally {
            scanner.close();
        }
    }

    private String extractContent(String line) {
        int colonIndex = line.indexOf(":");
        if (colonIndex != -1) {
            return line.substring(colonIndex + 1).trim();
        } else {
            logger.warning("Invalid format in line: " + line);
            return "";
        }
    }

    public void startQuiz() {
        int score = 0;

        logger.info("Welcome to the Quiz Application!\n");

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String questionLog = "Question " + (i + 1) + ": " + question.getQuestionText() + "\n" +
                    "A. " + question.getOptionA() + "\n" +
                    "B. " + question.getOptionB() + "\n" +
                    "C. " + question.getOptionC() + "\n" +
                    "D. " + question.getOptionD() + "\n";

            logger.info(questionLog);

            String userAnswer = getUserAnswer();

            if (question.isCorrectAnswer(userAnswer)) {
                logger.info("Correct!\n");
                score += SCORE_PER_QUESTION;
            } else {
                logger.info("Incorrect!\n");
            }
        }

        int totalQuestions = questions.size();
        int correctAnswers = score / SCORE_PER_QUESTION;
        int incorrectAnswers = totalQuestions - correctAnswers;
        double percentageScore = (double) score / (totalQuestions * SCORE_PER_QUESTION) * 100;

        String summaryLog = "Quiz Summary:\n" +
                "Total Questions: " + totalQuestions + "\n" +
                "Correct Answers: " + correctAnswers + "\n" +
                "Incorrect Answers: " + incorrectAnswers + "\n" +
                "Score: " + percentageScore + "%\n" +
                "Thank you for playing!";

        logger.info(summaryLog);
    }

    private String getUserAnswer() {
        logger.info("Your Answer: ");
        return scanner.nextLine().toUpperCase();
    }

    public static void main(String[] args) {
        QuizApplication quiz = new QuizApplication();

        try {
            quiz.loadQuestions();
            quiz.startQuiz();
        } catch (FileNotFoundException e) {
            logger.severe("Failed to load questions from file: " + QUESTIONS_FILE);
        } finally {
            if (quiz.scanner != null) {
                quiz.scanner.close();
            }
        }
    }
}