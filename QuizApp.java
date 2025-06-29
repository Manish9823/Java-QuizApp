
import java.util.Scanner;



class Question{
    String question;
    String[] options;
    int answer;

    public Question(String question, String[] options, int answer){
        this.question= question;
        this.options = options;
        this.answer = answer;
    }
}

class QuestionDataSet{

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

    public void setLevel(String levelInput){
        level=levelInput;
    }
    public Question[] getQuestionData(){
        if(level =="level1"){
            return level1;
        }
        if(level=="level2"){
            return level2;
        }
            return level3;
        
    }

    public String nextLevel(){
         if(level =="level1"){
            return "level2";
        }
        if(level=="level2"){
            return "level3";
        }
            return "end";
    }
     
}

class QuizSystem{
    String level;
    Scanner scanner= new Scanner(System.in);
    QuestionDataSet questionDataSet = new QuestionDataSet();
    Question[] questionsData;
    int marks;
    int totalMarks;
    int status; // 0: hold, 1: continue

    public QuizSystem(){
        this.level="level1";
        this.questionDataSet.setLevel("level1");
        this.totalMarks=0;
        this.status = 1;
    }

    public void startQuiz(){
        int questionIndex=1;
        marks = 0;
        questionsData = questionDataSet.getQuestionData();

        for(Question question: questionsData){
            System.out.println(questionIndex + ") " + question.question);
            for(int i=0;i<question.options.length;i++){
                System.out.println(i+1 + ". " + question.options[i]);
            }
            int userAnswer = scanner.nextInt();

            if(userAnswer == question.answer){
                marks++;
            }
            System.out.println("");
            questionIndex++;
        }
        if(marks>4){
            System.out.println("Congrats you cleared the " + level + " and you got "+ marks+" out of 5");
            level = questionDataSet.nextLevel();
            if(level!="end"){
                System.out.println("Do you want to next level? (Enter 'y' for yes)");
                String userInput = scanner.next();
                if(userInput.trim().equalsIgnoreCase("y")){
                    questionDataSet.setLevel(level);
                    totalMarks = totalMarks + marks;
                    status = 1;
                    return;
                }
            }
        }else{
            System.out.println("Sorry you didn't passed the level" + " and you got "+ marks+" out of 5" + " , Try again? (Enter 'y' for yes)");
            String userInput = scanner.next();
            if(userInput.trim().equalsIgnoreCase("y")){
                questionDataSet.setLevel(level);
                status = 1;
                return;
            }
        }
        status = 0;
    }
    
    public Boolean hasNextLevel(){
        if(level!="end"){
            return true;
        }
        return false;
    }
    public void updateStatus(int newStatus){
        status = newStatus;
    }
}

public class QuizApp{
    public static void main(String[] args){
    Scanner scanner= new Scanner(System.in);
    QuizSystem quizSystem= new QuizSystem();

    while(quizSystem.hasNextLevel()){
            quizSystem.startQuiz();
            while(quizSystem.status == 0 && quizSystem.hasNextLevel()){
                System.out.println("Press 'y' to continue with the quiz.");
                String userInput = scanner.next();
                if(userInput.trim().equalsIgnoreCase("y")){
                    quizSystem.updateStatus(1);
                }
            }
       }
    }
}
 