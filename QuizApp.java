
import java.util.Scanner;

class Question {

    String question;
    String[] options;
    int answer;

    public Question(String question, String[] options, int answer) {
        this.question = question;
        this.options = options;
        this.answer = answer;
    }
}

// interface IQuestionDataSet { 
//      Question[] getQuestionData();
// }
class QuestionDataSet {

    String level;
    Question[] currentQuestionData;

    // Beginner-level programming questions
    Question[] level1 = {
        new Question("What does HTML stand for?", new String[]{"HyperText Machine Language", "HyperText Markup Language", "HighText Markdown Language", "None of the above"}, 2),
        new Question("Which language is primarily used for Android app development?", new String[]{"Python", "Swift", "Java", "Kotlin"}, 4),
        new Question("Which symbol is used to start a single-line comment in Java?", new String[]{"/*", "//", "#", "<!--"}, 2),
        new Question("Which of the following is a loop structure in Java?", new String[]{"if", "for", "switch", "case"}, 2),
        new Question("What is the extension of Java source files?", new String[]{".class", ".java", ".jar", ".js"}, 2)
    };

    // Intermediate-level programming questions
    Question[] level2 = {
        new Question("What will be the output of: int x = 5; System.out.println(x++);", new String[]{"5", "6", "Error", "Undefined"}, 1),
        new Question("Which data structure uses FIFO (First In First Out)?", new String[]{"Stack", "Queue", "Array", "Tree"}, 2),
        new Question("Which keyword is used to inherit a class in Java?", new String[]{"implement", "inherits", "extends", "instanceof"}, 3),
        new Question("What is the size of an int in Java?", new String[]{"4 bytes", "2 bytes", "8 bytes", "Depends on the system"}, 1),
        new Question("Which interface needs to be implemented to create a thread in Java?", new String[]{"Runnable", "Serializable", "Cloneable", "Threadable"}, 1)
    };

    // Advanced-level programming questions
    Question[] level3 = {
        new Question("Which design pattern ensures a class has only one instance and provides a global point of access?", new String[]{"Factory", "Singleton", "Observer", "Builder"}, 2),
        new Question("What is tail recursion?", new String[]{"A function that returns before recursion", "A recursion where the recursive call is the last operation", "A loop-based recursion", "None of the above"}, 2),
        new Question("Which of the following best describes polymorphism in OOP?", new String[]{"Objects of different types responding to the same method call", "Wrapping data and functions together", "Code reuse through inheritance", "Access control in objects"}, 1),
        new Question("In Java, what is the default value of an uninitialized boolean variable?", new String[]{"true", "false", "0", "null"}, 2),
        new Question("Which Java keyword prevents a method from being overridden?", new String[]{"private", "static", "final", "abstract"}, 3)
    };

    public void setLevel(String levelInput) {
        level = levelInput;
    }

    public Question[] getQuestionData() {
        if (level == "level1") {
            return level1;
        }
        if (level == "level2") {
            return level2;
        }
        return level3;
    }

    public String nextLevel() {
        if (level == "level1") {
            return "level2";
        }
        if (level == "level2") {
            return "level3";
        }
        return "end";
    }

    public String getCurrentLevel() {
        return level;
    }
}

class QuizLogic {

    Question[] questionsData;
    private QuestionDataSet _questionDataSet;
    private int currentQuestionIndex;

    QuizLogic(QuestionDataSet questionDataSet) {
        _questionDataSet = questionDataSet;
        currentQuestionIndex = 0;
    }

    public void setLevel(String level) {
        // Debug assert when questionDataSet is null
        // Debug assert when level is empty or null
        _questionDataSet.setLevel(level);
        questionsData = _questionDataSet.getQuestionData();
    }

    public Boolean hasNextQuestion() {
        if (currentQuestionIndex >= questionsData.length) {
            currentQuestionIndex = 0;
            return false;
        }
        return true;
    }

    public Question getNextQuestion() {
        return questionsData[currentQuestionIndex];
    }

    public Boolean checkAnswer(int userInput) {
        Boolean isValidAnswer = userInput == questionsData[currentQuestionIndex].answer;
        currentQuestionIndex++;
        return isValidAnswer;
    }
}

// Presentation Layer
class QuizUI {

    private Scanner _scanner;
    QuestionDataSet _questionDataSet = new QuestionDataSet();
    QuizLogic _quizLogic = new QuizLogic(_questionDataSet);

    int marks;
    int totalMarks;

    public QuizUI(Scanner scanner) {
        _scanner = scanner;
        _quizLogic.setLevel("level1");
        totalMarks = 0;
        marks = 0;
    }

    private void displayQuestion(Question question) {
        System.out.println(question.question);
        for (int i = 0; i < question.options.length; i++) {
            System.out.println(i + 1 + ". " + question.options[i]);
        }
    }

    private void displayResult() {
        System.out.println("=====================================================================================================");
        if (marks > 4) {
            System.out.println("Congrats you cleared the " + _questionDataSet.getCurrentLevel() + " and you got " + marks + " out of 5");
        } else {
            System.out.println("Sorry you didn't passed the " + _questionDataSet.getCurrentLevel() + " and you got " + marks + " out of 5");
        }
        System.out.println("=====================================================================================================");
    }

    private int getUserInput() {
        return _scanner.nextInt();
    }

    private void levelUpdate() {
        String level = _questionDataSet.nextLevel();
        _quizLogic.setLevel(level);
    }

    private void resetTheMark() {
        marks = 0;
    }

    private void incrementTheMark() {
        marks++;
    }

    private void validateAndUpdateLevel() {
        if (marks > 4) {
            levelUpdate();
        }
    }

    public void start() {

        while (hasNextLevel()) {
            while (_quizLogic.hasNextQuestion()) {
                Question question = _quizLogic.getNextQuestion();

                // Display Question
                displayQuestion(question);

                // Get User Input
                int userInput = getUserInput();

                // Check the Answer
                Boolean isValidAnswer = _quizLogic.checkAnswer(userInput);

                // If answer is valid then add the marks.
                if (isValidAnswer) {
                    incrementTheMark();
                }
            }


            displayResult();

            validateAndUpdateLevel();

            resetTheMark();
        }
    }

    private Boolean hasNextLevel() {
        if (_questionDataSet.getCurrentLevel() != "end") {
            return true;
        }
        return false;
    }
}

public class QuizApp {

    public static void main(String[] args) {
        QuizUI quizUI = new QuizUI(new Scanner(System.in));
        quizUI.start();
    }
}
