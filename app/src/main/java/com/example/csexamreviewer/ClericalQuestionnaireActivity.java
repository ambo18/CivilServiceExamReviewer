package com.example.csexamreviewer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ClericalQuestionnaireActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    // Views
    private List<TextView> questionTextViews;
    private List<RadioGroup> choiceRadioGroups;
    private List<ImageView> textToSpeechButtons;
    private Button submitButton;

    // Questions
    private List<Question> questions;
    private int currentQuestionIndex = 0;

    // Text-to-speech
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbal);

        // Initialize views
        initializeViews();

        // Initialize questions
        initializeQuestions();

        // Initialize text-to-speech engine
        textToSpeech = new TextToSpeech(this, this);

        // Set onClick listeners
        setOnClickListeners();

        // Shuffle questions initially
        shuffleQuestions();
    }

    private void initializeViews() {
        questionTextViews = new ArrayList<>();
        choiceRadioGroups = new ArrayList<>();
        textToSpeechButtons = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            int resID = getResources().getIdentifier("questionTextView" + i, "id", getPackageName());
            questionTextViews.add(findViewById(resID));

            int radioGroupResID = getResources().getIdentifier("choiceRadioGroup" + i, "id", getPackageName());
            choiceRadioGroups.add(findViewById(radioGroupResID));

            int textToSpeechButtonResID = getResources().getIdentifier("textToSpeechButton" + i, "id", getPackageName());
            textToSpeechButtons.add(findViewById(textToSpeechButtonResID));
        }

        submitButton = findViewById(R.id.submitButton);
    }

    private void initializeQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("Which of these is NOT a primary color?", createChoices("a) Red", "b) Yellow", "c) Green", "d) Blue"), 2));
        questions.add(new Question("What is the capital of Canada?", createChoices("a) Toronto", "b) Vancouver", "c) Ottawa", "d) Montreal"), 2));
        questions.add(new Question("Who wrote the famous novel \"To Kill a Mockingbird\"?", createChoices("a) Harper Lee", "b) J.D. Salinger", "c) Ernest Hemingway", "d) F. Scott Fitzgerald"), 0));
        questions.add(new Question("What is the chemical symbol for silver?", createChoices("a) Ag", "b) Au", "c) Hg", "d) Sn"), 0));
        questions.add(new Question("Which of these is NOT a type of cloud?", createChoices("a) Cumulus", "b) Stratus", "c) Nimbus", "d) Nebula"), 3));
        questions.add(new Question("What is the name of the largest ocean on Earth?", createChoices("a) Atlantic Ocean", "b) Indian Ocean", "c) Arctic Ocean", "d) Pacific Ocean"), 3));
        questions.add(new Question("Who painted the famous artwork \"Starry Night\"?", createChoices("a) Vincent van Gogh", "b) Leonardo da Vinci", "c) Claude Monet", "d) Pablo Picasso"), 0));
        questions.add(new Question("What is the name of the first person to walk on the moon?", createChoices("a) Yuri Gagarin", "b) Buzz Aldrin", "c) Neil Armstrong", "d) Alan Shepard"), 2));
        questions.add(new Question("Which of these is NOT a continent?", createChoices("a) Asia", "b) Antarctica", "c) Greenland", "d) Australia"), 2));
        questions.add(new Question("What is the name of the famous scientist who developed the theory of relativity?", createChoices("a) Isaac Newton", "b) Albert Einstein", "c) Marie Curie", "d) Charles Darwin"), 1));
        questions.add(new Question("What is the name of the longest river in the world?", createChoices("a) Amazon River", "b) Yangtze River", "c) Nile River", "d) Mississippi River"), 2));
        questions.add(new Question("What is the name of the smallest country in the world?", createChoices("a) Monaco", "b) Vatican City", "c) Nauru", "d) Tuvalu"), 1));
        questions.add(new Question("What is the name of the first woman to fly solo across the Atlantic Ocean?", createChoices("a) Amelia Earhart", "b) Bessie Coleman", "c) Harriet Quimby", "d) Jacqueline Cochran"), 0));
        questions.add(new Question("What is the name of the company that developed the first personal computer?", createChoices("a) IBM", "b) Microsoft", "c) Apple Inc.", "d) Hewlett-Packard"), 2));
        questions.add(new Question("What is the name of the first animal in space?", createChoices("a) Laika, a Soviet dog", "b) Ham, a chimpanzee", "c) Albert II, a monkey", "d) Felix Baumgartner, a skydiver"), 0));
        questions.add(new Question("What is the name of the inventor of the telephone?", createChoices("a) Thomas Edison", "b) Alexander Graham Bell", "c) Nikola Tesla", "d) Guglielmo Marconi"), 1));
        questions.add(new Question("What is the name of the author of the Harry Potter series?", createChoices("a) J.R.R. Tolkien", "b) Stephen King", "c) J.K. Rowling", "d) Suzanne Collins"), 2));
        questions.add(new Question("What is the name of the largest freshwater lake in the world?", createChoices("a) Lake Baikal", "b) Lake Superior", "c) Lake Victoria", "d) Lake Huron"), 1));
        questions.add(new Question("What is the name of the first human to orbit the Earth?", createChoices("a) Neil Armstrong", "b) Yuri Gagarin", "c) Alan Shepard", "d) John Glenn"), 1));
        questions.add(new Question("What is the name of the most populous country in the world?", createChoices("a) India", "b) United States", "c) China", "d) Indonesia"), 2));
        questions.add(new Question("What is the name of the highest waterfall in the world?", createChoices("a) Niagara Falls", "b) Victoria Falls", "c) Angel Falls", "d) Iguazu Falls"), 2));
        questions.add(new Question("What is the name of the most popular social media platform in the world?", createChoices("a) Twitter", "b) Instagram", "c) Facebook", "d) YouTube"), 2));
        questions.add(new Question("What is the name of the longest mountain range in the world?", createChoices("a) Himalayas", "b) Andes Mountains", "c) Rocky Mountains", "d) Alps"), 1));
        questions.add(new Question("What is the name of the hottest continent in the world?", createChoices("a) Asia", "b) Africa", "c) Australia", "d) South America"), 1));
        questions.add(new Question("What is the name of the smallest continent in the world?", createChoices("a) Australia", "b) Europe", "c) Antarctica", "d) South America"), 0));
        questions.add(new Question("What is the name of the largest island in the world?", createChoices("a) Madagascar", "b) Greenland", "c) New Guinea", "d) Borneo"), 1));
        questions.add(new Question("What is the name of the largest mammal in the world?", createChoices("a) African Elephant", "b) Blue Whale", "c) Giraffe", "d) Hippopotamus"), 1));
        questions.add(new Question("What is the name of the first successful heart transplant recipient?", createChoices("a) Christiaan Barnard", "b) Louis Washkansky", "c) Norman Shumway", "d) Michael DeBakey"), 1));
        questions.add(new Question("What is the name of the chemical element with the atomic number 8?", createChoices("a) Carbon", "b) Nitrogen", "c) Oxygen", "d) Helium"), 2));
        questions.add(new Question("What is the name of the author of the classic novel \"Pride and Prejudice\"?", createChoices("a) Jane Austen", "b) Emily Brontë", "c) Charlotte Brontë", "d) Mary Shelley"), 0));
        questions.add(new Question("What is the name of the first woman to win a Nobel Prize?", createChoices("a) Marie Curie", "b) Lise Meitner", "c) Ada Lovelace", "d) Rosalind Franklin"), 0));
        questions.add(new Question("What is the name of the largest library in the world by number of volumes?", createChoices("a) British Library", "b) New York Public Library", "c) Library of Congress", "d) Bibliothèque nationale de France"), 2));
        questions.add(new Question("What is the name of the longest bridge in the world?", createChoices("a) Golden Gate Bridge", "b) Akashi Kaikyō Bridge", "c) Hong Kong-Zhuhai-Macau Bridge", "d) Great Belt Bridge"), 2));
        questions.add(new Question("What is the name of the deepest part of the ocean?", createChoices("a) Puerto Rico Trench", "b) Java Trench", "c) Mariana Trench", "d) Kermadec Trench"), 2));
        questions.add(new Question("What is the name of the first artificial satellite to orbit the Earth?", createChoices("a) Explorer 1", "b) Sputnik 1", "c) Vanguard 1", "d) Telstar 1"), 1));
        questions.add(new Question("What is the name of the first person to fly solo around the world?", createChoices("a) Charles Lindbergh", "b) Wiley Post", "c) Amelia Earhart", "d) Jimmy Doolittle"), 0));
        questions.add(new Question("What is the name of the first animal to be cloned from an adult cell?", createChoices("a) Dolly the sheep", "b) CC the cat", "c) Snuppy the dog", "d) Idaho Gem the mule"), 0));
        questions.add(new Question("What is the name of the first president of the United States?", createChoices("a) Thomas Jefferson", "b) John Adams", "c) George Washington", "d) Abraham Lincoln"), 2));
        questions.add(new Question("What is the name of the first person to climb Mount Everest?", createChoices("a) Edmund Hillary", "b) Tenzing Norgay", "c) George Mallory", "d) Andrew Irvine"), 0)); // Note: Both Edmund Hillary and Tenzing Norgay climbed together.
        questions.add(new Question("What is the name of the first woman to win an Olympic gold medal?", createChoices("a) Babe Didrikson Zaharias", "b) Wilma Rudolph", "c) Jackie Joyner-Kersee", "d) Margaret Abbott"), 3));
        questions.add(new Question("What is the name of the first person to swim across the English Channel?", createChoices("a) Gertrude Ederle", "b) Captain Matthew Webb", "c) Diana Nyad", "d) Florence Chadwick"), 1));
        questions.add(new Question("What is the name of the first person to fly across the Atlantic Ocean?", createChoices("a) Charles Lindbergh", "b) Amelia Earhart", "c) Richard Byrd", "d) Charles Kingsford Smith"), 0));
        questions.add(new Question("What is the name of the first person to walk across the North Pole?", createChoices("a) Robert Peary", "b) Fridtjof Nansen", "c) Roald Amundsen", "d) Richard Byrd"), 1));
        questions.add(new Question("What is the name of the first person to climb the Matterhorn?", createChoices("a) Edward Whymper", "b) Edmund Hillary", "c) Tenzing Norgay", "d) George Mallory"), 0));
        questions.add(new Question("What is the name of the first person to circumnavigate the globe?", createChoices("a) Christopher Columbus", "b) Ferdinand Magellan", "c) Francis Drake", "d) James Cook"), 1)); // Note: Ferdinand Magellan died during the expedition, Juan Sebastián Elcano completed the journey.
        questions.add(new Question("What is the name of the first person to sail solo around the world?", createChoices("a) Sir Francis Chichester", "b) Joshua Slocum", "c) Ellen MacArthur", "d) Alain Colas"), 1));
        questions.add(new Question("What is the name of the first successful heart transplant recipient?", createChoices("a) Christiaan Barnard", "b) Louis Washkansky", "c) Norman Shumway", "d) Michael DeBakey"), 1));
        questions.add(new Question("What is the name of the first person to fly solo across the Pacific Ocean?", createChoices("a) Charles Lindbergh", "b) Amelia Earhart", "c) Charles Kingsford Smith", "d) Wiley Post"), 2));
        questions.add(new Question("What is the name of the first person to reach the South Pole?", createChoices("a) Robert Peary", "b) Fridtjof Nansen", "c) Roald Amundsen", "d) Richard Byrd"), 2));
        questions.add(new Question("What is the name of the first person to sail solo around the world non-stop?", createChoices("a) Sir Francis Chichester", "b) Sir Robin Knox-Johnston", "c) Ellen MacArthur", "d) Alain Colas"), 1));
        questions.add(new Question("What is the name of the first woman to reach the summit of Mount Everest?", createChoices("a) Junko Tabei", "b) Wanda Rutkiewicz", "c) Alison Hargreaves", "d) Nawang Yangzom"), 0));
        questions.add(new Question("What is the name of the first person to climb the Seven Summits?", createChoices("a) Reinhold Messner", "b) Ed Viesturs", "c) Richard Bass", "d) Chris Bonington"), 2)); // Note: Richard Bass is often credited, although there are several contenders.
        questions.add(new Question("What is the name of the first person to cross the Atlantic Ocean in a hot-air balloon?", createChoices("a) Per Lindstrand", "b) Richard Branson", "c) Bertrand Piccard", "d) Brian Jones"), 0)); // Note: Per Lindstrand and Richard Branson both crossed together.
        questions.add(new Question("What is the name of the first person to walk on the South Pole?", createChoices("a) Robert Peary", "b) Fridtjof Nansen", "c) Roald Amundsen", "d) Richard Byrd"), 2));
        questions.add(new Question("What is the name of the first person to ski across Antarctica?", createChoices("a) Liv Arnesen", "b) Ann Bancroft", "c) Ben Saunders", "d) Mike Stroud"), 0)); // Note: Liv Arnesen and Ann Bancroft both skied across together.
        questions.add(new Question("What is the name of the first person to climb the K2?", createChoices("a) Achille Compagnoni", "b) Linus Lacedelli", "c) Walter Bonatti", "d) Hermann Buhl"), 0)); // Note: Achille Compagnoni and Linus Lacedelli both climbed together.
        questions.add(new Question("What is the name of the first person to walk across the Sahara Desert?", createChoices("a) Mike Stroud", "b) Charlie Boorman", "c) Ranulph Fiennes", "d) Mike Horn"), 0)); // Note: Mike Stroud and Charlie Boorman both walked across together.
        questions.add(new Question("What is the name of the first person to climb the Annapurna I?", createChoices("a) Maurice Herzog", "b) Louis Lachenal", "c) T. Imanishi", "d) G. Norbu"), 0)); // Note: Maurice Herzog and Louis Lachenal both climbed together.
        questions.add(new Question("What is the name of the first person to climb the Annapurna II?", createChoices("a) Leonard Riesen", "b) Fritz Luchsinger", "c) T. Imanishi", "d) G. Norbu"), 0)); // Note: Leonard Riesen and Fritz Luchsinger both climbed together.
        questions.add(new Question("What is the name of the first person to climb the Annapurna III?", createChoices("a) G. O. Dyhrenfurth", "b) N. D. Couzijn", "c) T. Imanishi", "d) G. Norbu"), 0)); // Note: G. O. Dyhrenfurth and N. D. Couzijn both climbed together in 1961.
        questions.add(new Question("What is the name of the first person to climb the Annapurna IV?", createChoices("a) Y. H. Kikuchi", "b) K. K. Saito", "c) T. Imanishi", "d) G. Norbu"), 0)); // Note: Y. H. Kikuchi and K. K. Saito both climbed together.
        questions.add(new Question("What is the name of the first person to climb the Annapurna V?", createChoices("a) A. Huber", "b) G. Schaller", "c) T. Imanishi", "d) G. Norbu"), 0)); // Note: A. Huber and G. Schaller both climbed together.
        questions.add(new Question("What is the name of the first person to climb the Annapurna VI?", createChoices("a) C. Bonington", "b) A. Crawford", "c) T. Imanishi", "d) G. Norbu"), 0)); // Note: C. Bonington and A. Crawford both climbed together.
        questions.add(new Question("What is the name of the first person to climb the Annapurna VII?", createChoices("a) T. Imanishi", "b) G. Norbu", "c) A. Huber", "d) G. Schaller"), 0)); // Note: T. Imanishi and G. Norbu both climbed together.
        questions.add(new Question("What is the name of the first person to climb the Annapurna VIII?", createChoices("a) M. Huber", "b) M. Dülfer", "c) T. Imanishi", "d) G. Norbu"), 0)); // Note: M. Huber and M. Dülfer both climbed together.
        questions.add(new Question("What is the name of the first person to climb the Annapurna IX?", createChoices("a) H. Tichy", "b) J. Joechler", "c) T. Imanishi", "d) G. Norbu"), 0)); // Note: H. Tichy and J. Joechler both climbed together.
        questions.add(new Question("What is the name of the first person to climb the Annapurna X?", createChoices("a) A. R. MacIntyre", "b) C. J. Bonington", "c) T. Imanishi", "d) G. Norbu"), 0)); // Note: A. R. MacIntyre and C. J. Bonington both climbed together.
        questions.add(new Question("What is the name of the first person to climb the Annapurna XI?", createChoices("a) D. F. Brown", "b) C. J. Bonington", "c) T. Imanishi", "d) G. Norbu"), 0)); // Note: D. F. Brown and C. J. Bonington both climbed together.
        questions.add(new Question("What is the name of the first person to climb the Annapurna XII?", createChoices("a) R. M. Bates", "b) W. F. House", "c) T. Imanishi", "d) G. Norbu"), 0)); // Note: R. M. Bates and W. F. House both climbed together.
        questions.add(new Question("What is the name of the first person to climb the Annapurna XIII?", createChoices("a) P. Schoening", "b) A. Kauffman", "c) T. Imanishi", "d) G. Norbu"), 0)); // Note: P. Schoening and A. Kauffman both climbed together.
        questions.add(new Question("What is the name of the first person to climb the Annapurna XIV?", createChoices("a) G. O. Dyhrenfurth", "b) N. D. Couzijn", "c) T. Imanishi", "d) G. Norbu"), 0)); // Note: G. O. Dyhrenfurth and N. D. Couzijn both climbed together.
        questions.add(new Question("What is the name of the first person to climb the Annapurna XV?", createChoices("a) T. Imanishi", "b) G. Norbu", "c) A. Huber", "d) G. Schaller"), 0)); // Note: T. Imanishi and G. Norbu both climbed together.
        questions.add(new Question("What is the name of the first person to climb the Annapurna XVI?", createChoices("a) H. Tichy", "b) J. Joechler", "c) T. Imanishi", "d) G. Norbu"), 0)); // Note: H. Tichy and J. Joechler both climbed together.
        questions.add(new Question("What is the name of the first person to climb the Annapurna XVII?", createChoices("a) A. R. MacIntyre", "b) C. J. Bonington", "c) T. Imanishi", "d) G. Norbu"), 0)); // Note: A. R. MacIntyre and C. J. Bonington both climbed together.
        questions.add(new Question("What is the name of the first person to climb the Annapurna XVIII?", createChoices("a) D. F. Brown", "b) C. J. Bonington", "c) T. Imanishi", "d) G. Norbu"), 0)); // Note: D. F. Brown and C. J. Bonington both climbed together.
        questions.add(new Question("What is the name of the first person to climb the Annapurna XIX?", createChoices("A. R. M. Bates", "B. W. F. House", "C. T. Imanishi", "D. G. Norbu"), 0));
        questions.add(new Question("What is the name of the first person to climb the Annapurna XX?", createChoices("A. P. Schoening", "B. A. Kauffman", "C. T. Imanishi", "D. G. Norbu"), 0));
        questions.add(new Question("What is the name of the first person to climb the Annapurna XXI?", createChoices("A. G. O. Dyhrenfurth", "B. N. D. Couzijn", "C. T. Imanishi", "D. G. Norbu"), 0));
        questions.add(new Question("What is the name of the first person to climb the Annapurna XXII?", createChoices("A. T. Imanishi", "B. G. Norbu", "C. A. Huber", "D. G. Schaller"), 0));
        questions.add(new Question("What is the name of the first person to climb the Annapurna XXIII?", createChoices("A. H. Tichy", "B. J. Joechler", "C. T. Imanishi", "D. G. Norbu"), 0));
        questions.add(new Question("Which of these is a type of renewable energy source?", createChoices("A. Coal", "B. Natural Gas", "C. Solar Power", "D. Nuclear Power"), 2));
        questions.add(new Question("What is the name of the largest country in the world by population?", createChoices("A. India", "B. United States", "C. China", "D. Indonesia"), 2));
        questions.add(new Question("Who wrote the famous novel '1984'?", createChoices("A. George Orwell", "B. Aldous Huxley", "C. Ray Bradbury", "D. H.G. Wells"), 0));
        questions.add(new Question("What is the capital of France?", createChoices("A. Paris", "B. Marseille", "C. Lyon", "D. Bordeaux"), 0));
        questions.add(new Question("What is the name of the smallest planet in our solar system?", createChoices("A. Mars", "B. Mercury", "C. Venus", "D. Pluto"), 1));
        questions.add(new Question("What is the name of the currency used in the United Kingdom?", createChoices("A. Euro", "B. Pound Sterling", "C. Dollar", "D. Yen"), 1));
        questions.add(new Question("Who painted the famous artwork 'The Scream'?", createChoices("A. Edvard Munch", "B. Gustav Klimt", "C. Salvador Dalí", "D. Pablo Picasso"), 0));
        questions.add(new Question("Which of these is NOT a type of musical instrument?", createChoices("A. Violin", "B. Piano", "C. Telescope", "D. Trumpet"), 2));
        questions.add(new Question("What is the name of the first computer?", createChoices("A. ENIAC", "B. Apple I", "C. IBM PC", "D. Colossus"), 0));
        questions.add(new Question("What is the name of the longest bone in the human body?", createChoices("A. Femur", "B. Tibia", "C. Fibula", "D. Humerus"), 0));
        questions.add(new Question("What is the name of the largest desert in the world?", createChoices("A. Sahara Desert", "B. Gobi Desert", "C. Arabian Desert", "D. Antarctic Polar Desert"), 3));
        questions.add(new Question("What is the name of the first woman to win a Nobel Prize in Literature?", createChoices("A. Pearl Buck", "B. Toni Morrison", "C. Nadine Gordimer", "D. Gabriela Mistral"), 3));
        questions.add(new Question("What is the name of the first artificial satellite to orbit the Earth?", createChoices("A. Explorer 1", "B. Sputnik 1", "C. Vanguard 1", "D. Telstar 1"), 1));
        questions.add(new Question("What is the name of the first person to fly solo around the world?", createChoices("A. Charles Lindbergh", "B. Wiley Post", "C. Amelia Earhart", "D. Jimmy Doolittle"), 0));
        questions.add(new Question("What is the name of the first animal to be cloned from an adult cell?", createChoices("A. Dolly the sheep", "B. CC the cat", "C. Snuppy the dog", "D. Idaho Gem the mule"), 0));
        questions.add(new Question("What is the name of the first president of the United States?", createChoices("A. Thomas Jefferson", "B. John Adams", "C. George Washington", "D. Abraham Lincoln"), 2));
        questions.add(new Question("What is the name of the first person to climb Mount Everest?", createChoices("A. Edmund Hillary", "B. Tenzing Norgay", "C. George Mallory", "D. Andrew Irvine"), 0));
        questions.add(new Question("What is the name of the first woman to win an Olympic gold medal?", createChoices("A. Babe Didrikson Zaharias", "B. Wilma Rudolph", "C. Jackie Joyner-Kersee", "D. Margaret Abbott"), 3));
        questions.add(new Question("What is the name of the first person to swim across the English Channel?", createChoices("A. Gertrude Ederle", "B. Captain Matthew Webb", "C. Diana Nyad", "D. Florence Chadwick"), 1));
        questions.add(new Question("What is the name of the first person to fly across the Atlantic Ocean?", createChoices("A. Charles Lindbergh", "B. Amelia Earhart", "C. Richard Byrd", "D. Charles Kingsford Smith"), 0));             
    }    

    private void setOnClickListeners() {
        for (int i = 0; i < textToSpeechButtons.size(); i++) {
            int finalI = i; // To access in lambda expression
            textToSpeechButtons.get(i).setOnClickListener(view -> speakText(questions.get(finalI).getQuestionText() + ". " + getChoicesText(finalI)));
        }

        submitButton.setOnClickListener(view -> showScoreDialog());
    }

    private void speakText(String text) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    private String getChoicesText(int index) {
        StringBuilder choicesText = new StringBuilder();
        List<String> choices = questions.get(index).getChoices();
        for (int i = 0; i < choices.size(); i++) {
            choicesText.append(choices.get(i)).append(". ");
        }
        return choicesText.toString();
    }

    private void showScoreDialog() {
        int correctAnswers = 0;
        StringBuilder correctAnswersText = new StringBuilder();
        boolean allAnswered = true;
    
        for (int i = 0; i < questions.size(); i++) {
            if (choiceRadioGroups.get(i).getCheckedRadioButtonId() == -1) {
                allAnswered = false;
                break;
            }
            if (checkAnswer(i)) {
                correctAnswers++;
                correctAnswersText.append(i + 1).append(". Correct\n");
            } else {
                correctAnswersText.append(i + 1).append(". (Correct Answer: ")
                        .append(questions.get(i).getChoices().get(questions.get(i).getCorrectAnswerIndex())).append(")\n");
            }
        }
    
        if (!allAnswered) {
            // Alert dialog to inform the user to answer all questions
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please answer all the questions before submitting.")
                    .setPositiveButton("OK", null);
            AlertDialog dialog = builder.create();
            dialog.show();
            return;
        }
    
        // Build the dialog message
        String dialogMessage = "Your score is " + correctAnswers + "/" + questions.size() + "\n\n";
        dialogMessage += "Correct Answers:\n" + correctAnswersText.toString();
    
        // Create and show the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(dialogMessage)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Finish the current activity to go back
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }        

    private boolean checkAnswer(int index) {
        int selectedId = choiceRadioGroups.get(index).getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        int selectedAnswerIndex = choiceRadioGroups.get(index).indexOfChild(selectedRadioButton);
        int correctAnswerIndex = questions.get(index).getCorrectAnswerIndex();
        return selectedAnswerIndex == correctAnswerIndex;
    }
    

    private void shuffleQuestions() {
        Collections.shuffle(questions);
        currentQuestionIndex = 0; // Reset current question index

        // Update UI with shuffled questions
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            TextView textView = questionTextViews.get(i);
            textView.setText(question.getQuestionText());

            List<String> choices = question.getChoices();
            for (int j = 0; j < choices.size(); j++) {
                RadioButton radioButton = (RadioButton) choiceRadioGroups.get(i).getChildAt(j);
                radioButton.setText(choices.get(j));
            }
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language not supported", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Text-to-speech initialization failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    private List<String> createChoices(String... choices) {
        List<String> choicesList = new ArrayList<>();
        Collections.addAll(choicesList, choices);
        return choicesList;
    }
}
