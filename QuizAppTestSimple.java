/**
 * Simple module-based testing for QuizApp without external dependencies
 * Can be compiled and run with basic javac/java commands
 */
public class QuizAppTestSimple {
    
    // Test counter
    private static int totalTests = 0;
    private static int passedTests = 0;
    
    // Simple assertion methods
    private static void assertEquals(Object expected, Object actual, String testName) {
        totalTests++;
        if (expected == null && actual == null) {
            System.out.println("✓ PASS: " + testName);
            passedTests++;
        } else if (expected != null && expected.equals(actual)) {
            System.out.println("✓ PASS: " + testName);
            passedTests++;
        } else {
            System.out.println("✗ FAIL: " + testName + " - Expected: " + expected + ", Got: " + actual);
        }
    }
    
    private static void assertTrue(boolean condition, String testName) {
        totalTests++;
        if (condition) {
            System.out.println("✓ PASS: " + testName);
            passedTests++;
        } else {
            System.out.println("✗ FAIL: " + testName + " - Expected true, got false");
        }
    }
    
    private static void assertFalse(boolean condition, String testName) {
        totalTests++;
        if (!condition) {
            System.out.println("✓ PASS: " + testName);
            passedTests++;
        } else {
            System.out.println("✗ FAIL: " + testName + " - Expected false, got true");
        }
    }
    
    private static void assertNotNull(Object object, String testName) {
        totalTests++;
        if (object != null) {
            System.out.println("✓ PASS: " + testName);
            passedTests++;
        } else {
            System.out.println("✗ FAIL: " + testName + " - Expected non-null object");
        }
    }
    
    // ===========================================
    // Question Class Tests
    // ===========================================
    public static void testQuestionClass() {
        System.out.println("\n=== Testing Question Class ===");
        
        // Test 1: Create question with correct properties
        String[] options = {"Option A", "Option B", "Option C", "Option D"};
        Question question = new Question("What is Java?", options, 2);
        
        assertEquals("What is Java?", question.question, "Question text should be set correctly");
        assertEquals(4, question.options.length, "Should have 4 options");
        assertEquals("Option A", question.options[0], "First option should be correct");
        assertEquals(2, question.answer, "Answer should be set correctly");
        
        // Test 2: Handle empty question text
        Question emptyQuestion = new Question("", options, 1);
        assertEquals("", emptyQuestion.question, "Empty question text should be handled");
        assertEquals(1, emptyQuestion.answer, "Answer should still be set");
        
        // Test 3: Handle null options
        Question nullOptionsQuestion = new Question("Test?", null, 1);
        assertEquals(null, nullOptionsQuestion.options, "Null options should be handled");
    }
    
    // ===========================================
    // QuestionDataSet Class Tests
    // ===========================================
    public static void testQuestionDataSetClass() {
        System.out.println("\n=== Testing QuestionDataSet Class ===");
        
        QuestionDataSet dataSet = new QuestionDataSet();
        
        // Test 1: Set and get level
        dataSet.setLevel("level1");
        assertEquals("level1", dataSet.getCurrentLevel(), "Level should be set correctly");
        
        // Test 2: Get level1 questions
        dataSet.setLevel("level1");
        Question[] questions = dataSet.getQuestionData();
        assertNotNull(questions, "Level1 questions should not be null");
        assertEquals(5, questions.length, "Level1 should have 5 questions");
        assertTrue(questions[0].question.contains("HTML"), "First question should contain HTML");
        
        // Test 3: Get level2 questions
        dataSet.setLevel("level2");
        questions = dataSet.getQuestionData();
        assertNotNull(questions, "Level2 questions should not be null");
        assertEquals(5, questions.length, "Level2 should have 5 questions");
        assertTrue(questions[0].question.contains("x++"), "First question should contain x++");
        
        // Test 4: Default to level3 for invalid level
        dataSet.setLevel("invalid_level");
        questions = dataSet.getQuestionData();
        assertNotNull(questions, "Invalid level should return level3 questions");
        assertEquals(5, questions.length, "Should still have 5 questions");
        
        // Test 5: Next level logic
        dataSet.setLevel("level1");
        assertEquals("level2", dataSet.nextLevel(), "Next level after level1 should be level2");
        
        dataSet.setLevel("level2");
        assertEquals("level3", dataSet.nextLevel(), "Next level after level2 should be level3");
        
        dataSet.setLevel("level3");
        assertEquals("end", dataSet.nextLevel(), "Next level after level3 should be end");
        
        // Test 6: Validate question structure
        dataSet.setLevel("level1");
        questions = dataSet.getQuestionData();
        for (int i = 0; i < questions.length; i++) {
            assertNotNull(questions[i].question, "Question " + i + " text should not be null");
            assertNotNull(questions[i].options, "Question " + i + " options should not be null");
            assertEquals(4, questions[i].options.length, "Question " + i + " should have 4 options");
            assertTrue(questions[i].answer >= 1 && questions[i].answer <= 4, 
                      "Question " + i + " answer should be between 1-4");
        }
    }
    
    // ===========================================
    // QuizLogic Class Tests
    // ===========================================
    public static void testQuizLogicClass() {
        System.out.println("\n=== Testing QuizLogic Class ===");
        
        QuestionDataSet dataSet = new QuestionDataSet();
        QuizLogic quizLogic = new QuizLogic(dataSet);
        
        // Test 1: Initialize with correct level
        quizLogic.setLevel("level1");
        assertTrue(quizLogic.hasNextQuestion(), "Should have questions after setting level");
        
        // Test 2: Get next question
        Question question = quizLogic.getNextQuestion();
        assertNotNull(question, "Should return a valid question");
        
        // Test 3: Check correct answer
        int correctAnswer = question.answer;
        assertTrue(quizLogic.checkAnswer(correctAnswer), "Correct answer should return true");
        
        // Test 4: Check wrong answer
        quizLogic.setLevel("level1"); // Reset
        question = quizLogic.getNextQuestion();
        int wrongAnswer = (question.answer == 1) ? 2 : 1;
        assertFalse(quizLogic.checkAnswer(wrongAnswer), "Wrong answer should return false");
        
        // Test 5: Complete all questions
        quizLogic.setLevel("level1");
        int questionCount = 0;
        while (quizLogic.hasNextQuestion() && questionCount < 10) { // Prevent infinite loop
            quizLogic.getNextQuestion();
            quizLogic.checkAnswer(1);
            questionCount++;
        }
        assertEquals(5, questionCount, "Should have exactly 5 questions");
        assertFalse(quizLogic.hasNextQuestion(), "Should have no more questions after completing all");
        
        // Test 6: Reset after completion
        assertTrue(quizLogic.hasNextQuestion(), "Should reset and have questions again");
    }
    
    // ===========================================
    // Integration Tests
    // ===========================================
    public static void testIntegration() {
        System.out.println("\n=== Testing Integration ===");
        
        // Test QuestionDataSet and QuizLogic integration
        QuestionDataSet dataSet = new QuestionDataSet();
        QuizLogic logic = new QuizLogic(dataSet);
        
        logic.setLevel("level1");
        assertEquals("level1", dataSet.getCurrentLevel(), "DataSet should reflect level change");
        
        // Test progression through levels
        String currentLevel = "level1";
        for (int level = 1; level <= 3; level++) {
            dataSet.setLevel(currentLevel);
            logic.setLevel(currentLevel);
            
            // Complete the level
            int questionsAnswered = 0;
            while (logic.hasNextQuestion() && questionsAnswered < 5) {
                logic.getNextQuestion();
                logic.checkAnswer(1);
                questionsAnswered++;
            }
            
            assertEquals(5, questionsAnswered, "Level " + level + " should have 5 questions");
            currentLevel = dataSet.nextLevel();
        }
        
        assertEquals("end", currentLevel, "Should reach 'end' after level3");
    }
    
    // ===========================================
    // Edge Case Tests
    // ===========================================
    public static void testEdgeCases() {
        System.out.println("\n=== Testing Edge Cases ===");
        
        QuestionDataSet dataSet = new QuestionDataSet();
        QuizLogic logic = new QuizLogic(dataSet);
        
        // Test null level handling
        dataSet.setLevel(null);
        assertEquals(null, dataSet.getCurrentLevel(), "Should handle null level");
        
        // Test invalid answer ranges
        logic.setLevel("level1");
        Question question = logic.getNextQuestion();
        assertFalse(logic.checkAnswer(0), "Answer 0 should be invalid");
        
        logic.setLevel("level1");
        question = logic.getNextQuestion();
        assertFalse(logic.checkAnswer(10), "Answer 10 should be invalid");
        
        // Test string comparison issue (the bug we found earlier)
        // This test will fail with the current implementation
        dataSet.setLevel("level1");
        Question[] questions1 = dataSet.getQuestionData();
        
        dataSet.setLevel("level1");
        Question[] questions2 = dataSet.getQuestionData();
        
        // This should be the same but might fail due to == vs .equals()
        assertEquals(questions1.length, questions2.length, "Same level should return same number of questions");
    }
    
    // ===========================================
    // Main Test Runner
    // ===========================================
    public static void main(String[] args) {
        System.out.println("🚀 Running QuizApp Module Tests");
        System.out.println("================================");
        
        // Run all test suites
        testQuestionClass();
        testQuestionDataSetClass();
        testQuizLogicClass();
        testIntegration();
        testEdgeCases();
        
        // Print summary
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📊 TEST SUMMARY");
        System.out.println("=".repeat(50));
        System.out.println("Total Tests: " + totalTests);
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + (totalTests - passedTests));
        System.out.println("Success Rate: " + (passedTests * 100.0 / totalTests) + "%");
        
        if (passedTests == totalTests) {
            System.out.println("🎉 ALL TESTS PASSED!");
        } else {
            System.out.println("❌ Some tests failed. Check the output above.");
        }
        
        System.out.println("\n💡 Tips:");
        System.out.println("- Fix the string comparison issue by using .equals() instead of ==");
        System.out.println("- Add input validation for better error handling");
        System.out.println("- Consider using enums for level management");
    }
}