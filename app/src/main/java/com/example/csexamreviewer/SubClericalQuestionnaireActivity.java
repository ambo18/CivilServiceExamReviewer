package com.example.csexamreviewer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
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

public class SubClericalQuestionnaireActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    // Views
    private List<TextView> questionTextViews;
    private List<RadioGroup> choiceRadioGroups;
    private List<ImageView> textToSpeechButtons;
    private Button submitButton, nextButton;

    // Questions
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private final int QUESTIONS_PER_PAGE = 20;
    private final int TOTAL_PAGES = 5;

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

        // Show initial page
        showPage(0);
    }

    private void initializeViews() {
        questionTextViews = new ArrayList<>();
        choiceRadioGroups = new ArrayList<>();
        textToSpeechButtons = new ArrayList<>();
    
        // Initialize TextViews, RadioGroups, and ImageViews
        for (int i = 1; i <= 100; i++) {
            int textViewResID = getResources().getIdentifier("questionTextView" + i, "id", getPackageName());
            TextView textView = findViewById(textViewResID);
            if (textView != null) {
                questionTextViews.add(textView);
            } else {
                Log.e("SubClericalQuestionnaireActivity", "TextView with ID questionTextView" + i + " not found.");
            }
    
            int radioGroupResID = getResources().getIdentifier("choiceRadioGroup" + i, "id", getPackageName());
            RadioGroup radioGroup = findViewById(radioGroupResID);
            if (radioGroup != null) {
                choiceRadioGroups.add(radioGroup);
            } else {
                Log.e("SubClericalQuestionnaireActivity", "RadioGroup with ID choiceRadioGroup" + i + " not found.");
            }
    
            int textToSpeechButtonResID = getResources().getIdentifier("textToSpeechButton" + i, "id", getPackageName());
            ImageView textToSpeechButton = findViewById(textToSpeechButtonResID);
            if (textToSpeechButton != null) {
                textToSpeechButtons.add(textToSpeechButton);
            } else {
                Log.e("SubClericalQuestionnaireActivity", "ImageView with ID textToSpeechButton" + i + " not found.");
            }
        }
    
        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);
    }

    private void initializeQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("Which of the following is the correct spelling?", createChoices("A) Accommodate", "B) Accomodate", "C) Acommodate", "D) Accomodate"), 0));
        questions.add(new Question("Which punctuation mark is used to indicate a question?", createChoices("A) Period", "B) Comma", "C) Question Mark", "D) Exclamation Point"), 2));
        questions.add(new Question("How many spaces should follow a period in a typed document?", createChoices("A) None", "B) One", "C) Two", "D) Three"), 1));
        questions.add(new Question("What is the correct format for a date in a formal letter?", createChoices("A) 12/31/2024", "B) 31-12-2024", "C) December 31, 2024", "D) 2024/12/31"), 2));
        questions.add(new Question("How should a letter be filed in alphabetical order?", createChoices("A) By the sender's name", "B) By the recipient's name", "C) By the date", "D) By the subject"), 1));
        questions.add(new Question("Which of the following is an example of a commonly used office supply?", createChoices("A) Stapler", "B) Calculator", "C) Printer", "D) All of the above"), 3));
        questions.add(new Question("What is the purpose of a cover letter in job applications?", createChoices("A) To introduce yourself", "B) To list references", "C) To provide a resume", "D) To detail your salary expectations"), 0));
        questions.add(new Question("What does a 'blind carbon copy' (BCC) in an email do?", createChoices("A) Sends the email to all recipients", "B) Hides the email addresses of recipients from each other", "C) Forwards the email to additional recipients", "D) Provides a copy to the sender"), 1));
        questions.add(new Question("Which of the following best describes 'proofreading'?", createChoices("A) Checking spelling and grammar errors", "B) Formatting a document", "C) Creating a presentation", "D) Organizing files"), 0));
        questions.add(new Question("How should a file be labeled for easy identification?", createChoices("A) With a unique number", "B) With a brief description", "C) With the file creation date", "D) With the file creator's name"), 1));
        questions.add(new Question("What is the correct way to address a formal letter to an individual whose name you do not know?", createChoices("A) To Whom It May Concern", "B) Dear Sir/Madam", "C) Hello", "D) Greetings"), 1));
        questions.add(new Question("Which of the following is a common typing error?", createChoices("A) Transposing letters", "B) Using correct punctuation", "C) Proper spacing", "D) Using correct spelling"), 0));
        questions.add(new Question("What is the purpose of a memo in an office setting?", createChoices("A) To communicate internally", "B) To make official announcements", "C) To schedule meetings", "D) To record financial transactions"), 0));
        questions.add(new Question("How should documents be organized in a filing cabinet?", createChoices("A) By date", "B) By subject", "C) By department", "D) All of the above"), 3));
        questions.add(new Question("Which of the following is considered a basic office procedure?", createChoices("A) Filing documents", "B) Managing company finances", "C) Conducting interviews", "D) Creating marketing strategies"), 0));
        questions.add(new Question("What does 'CC' stand for in an email?", createChoices("A) Confidential Copy", "B) Carbon Copy", "C) Certified Copy", "D) Creative Copy"), 1));
        questions.add(new Question("What is the correct way to handle a phone call from a client?", createChoices("A) Ignore the call", "B) Transfer the call to a colleague", "C) Take a message and provide contact information", "D) Hang up after a brief greeting"), 2));
        questions.add(new Question("Which key is used to capitalize letters while typing?", createChoices("A) Shift", "B) Control", "C) Alt", "D) Enter"), 0));
        questions.add(new Question("What is the best practice for managing multiple tasks in an office?", createChoices("A) Do them all at once", "B) Prioritize tasks and manage time effectively", "C) Delegate all tasks", "D) Complete tasks in random order"), 1));
        questions.add(new Question("What does the acronym 'PTO' stand for in office terminology?", createChoices("A) Paid Time Off", "B) Personal Time Off", "C) Public Time Off", "D) Professional Time Off"), 0));
        questions.add(new Question("What is the purpose of a 'file folder'?", createChoices("A) To store files", "B) To organize files", "C) To protect files", "D) All of the above"), 3));
        questions.add(new Question("How should a typist correct a mistake in a document?", createChoices("A) Erase and retype", "B) Use correction fluid", "C) Strike through and type the correction", "D) Ignore it"), 2));
        questions.add(new Question("What is the role of a 'keypunch' machine?", createChoices("A) To type text", "B) To enter data into a computer", "C) To print documents", "D) To scan barcodes"), 1));
        questions.add(new Question("What should be done if a confidential document is accidentally shared?", createChoices("A) Inform your supervisor immediately", "B) Ignore it", "C) Destroy the document", "D) Ask for a new copy"), 0));
        questions.add(new Question("Which of the following is a common mistake in filing documents?", createChoices("A) Mislabeling", "B) Filing in chronological order", "C) Using proper labels", "D) Storing documents in secure folders"), 0));
        questions.add(new Question("What is the proper format for a telephone number in a document?", createChoices("A) (123) 456-7890", "B) 123-456-7890", "C) 1234567890", "D) 123.456.7890"), 0));
        questions.add(new Question("What does the term 'filing' refer to?", createChoices("A) Organizing documents", "B) Creating documents", "C) Sending documents", "D) Destroying documents"), 0));
        questions.add(new Question("How should a letter be signed?", createChoices("A) With a typed name", "B) With a handwritten signature", "C) With a digital signature", "D) With a printed name"), 1));
        questions.add(new Question("Which of the following best describes 'data entry'?", createChoices("A) Inputting information into a computer", "B) Analyzing data", "C) Creating reports", "D) Filing documents"), 0));
        questions.add(new Question("What is a 'requisition form' used for?", createChoices("A) To request supplies", "B) To submit invoices", "C) To apply for a job", "D) To report problems"), 0));
        questions.add(new Question("What is the standard font size for a formal document?", createChoices("A) 10 pt", "B) 12 pt", "C) 14 pt", "D) 16 pt"), 1));
        questions.add(new Question("What is a 'fax machine' used for?", createChoices("A) Sending and receiving documents", "B) Typing documents", "C) Printing documents", "D) Scanning documents"), 0));
        questions.add(new Question("Which key is used to delete the character to the right of the cursor?", createChoices("A) Backspace", "B) Delete", "C) Enter", "D) Shift"), 1));
        questions.add(new Question("What is the correct method to file a document in a drawer?", createChoices("A) Alphabetically by title", "B) By date", "C) By importance", "D) By sender"), 0));
        questions.add(new Question("How should a document be formatted for a business report?", createChoices("A) With a cover page and headings", "B) In bullet points only", "C) With a casual tone", "D) Without any formatting"), 0));
        questions.add(new Question("What does 'confidential' mean in office terminology?", createChoices("A) Private and not to be shared", "B) Open for public viewing", "C) Available to all staff", "D) For external distribution"), 0));
        questions.add(new Question("What is the best practice for handling incoming mail?", createChoices("A) Sort and distribute promptly", "B) Leave it in the mailroom", "C) Open and read all mail", "D) Discard unopened"), 0));
        questions.add(new Question("What is the purpose of a 'routing slip'?", createChoices("A) To track the movement of documents", "B) To request office supplies", "C) To schedule meetings", "D) To record telephone calls"), 0));
        questions.add(new Question("What is the purpose of using 'headers' in a document?", createChoices("A) To organize content", "B) To make the document look colorful", "C) To reduce the length of the document", "D) To hide information"), 0));
        questions.add(new Question("What is the best way to handle a typo in a printed document?", createChoices("A) Use correction tape", "B) Cross it out and write the correction", "C) Reprint the document", "D) Ignore it"), 2));
        questions.add(new Question("Which document format is most suitable for sharing resumes?", createChoices("A) PDF", "B) Word", "C) Excel", "D) PowerPoint"), 0));
        questions.add(new Question("How should you file documents that need to be accessed frequently?", createChoices("A) By using a label system and placing them in an accessible drawer", "B) By storing them in a locked cabinet", "C) By placing them in a folder on the desk", "D) By keeping them in a binder"), 0));
        questions.add(new Question("What does 'alphabetizing' a file mean?", createChoices("A) Arranging files in alphabetical order", "B) Sorting files by date", "C) Grouping files by size", "D) Organizing files by importance"), 0));
        questions.add(new Question("Which of the following is an example of a 'standard operating procedure' (SOP)?", createChoices("A) A document outlining steps for handling customer complaints", "B) A monthly sales report", "C) A company directory", "D) A promotional flyer"), 0));
        questions.add(new Question("What is the purpose of 'pagination' in a document?", createChoices("A) To number the pages for easy reference", "B) To add graphics to each page", "C) To increase the font size", "D) To format text in bold"), 0));
        questions.add(new Question("Which punctuation mark is used to separate items in a list?", createChoices("A) Colon", "B) Semicolon", "C) Comma", "D) Period"), 2));
        questions.add(new Question("How should a formal letter be signed off?", createChoices("A) Sincerely", "B) Cheers", "C) Regards", "D) Best wishes"), 0));
        questions.add(new Question("What is the role of a 'data entry clerk'?", createChoices("A) To input data into computer systems", "B) To design websites", "C) To manage office supplies", "D) To handle customer service calls"), 0));
        questions.add(new Question("What is the function of a 'paperclip'?", createChoices("A) To hold papers together", "B) To mark a page", "C) To clean the screen", "D) To record information"), 0));
        questions.add(new Question("What does 'proofreading' involve?", createChoices("A) Reviewing a document for errors before finalizing", "B) Printing multiple copies of a document", "C) Creating a document outline", "D) Organizing files into folders"), 0));
        questions.add(new Question("What is the most appropriate action if you find a document with sensitive information?", createChoices("A) Secure it in a confidential file", "B) Leave it on your desk", "C) Share it with colleagues", "D) Dispose of it in a recycling bin"), 0));
        questions.add(new Question("Which is the best practice for handling confidential documents?", createChoices("A) Use secure storage and limit access", "B) Keep them in an unlocked drawer", "C) Share them with all team members", "D) Place them in an open filing cabinet"), 0));
        questions.add(new Question("How should a typist handle a document that needs to be corrected?", createChoices("A) Strike through the error and type the correction", "B) Use a different document", "C) Erase the mistake and retype", "D) Highlight the error"), 0));
        questions.add(new Question("What does 'filing' in an office refer to?", createChoices("A) Storing documents in an organized manner", "B) Creating documents", "C) Sending documents to clients", "D) Printing documents"), 0));
        questions.add(new Question("What is the purpose of a 'telephone log'?", createChoices("A) To record phone calls and their details", "B) To list contact numbers", "C) To track office supplies", "D) To note meeting dates"), 0));
        questions.add(new Question("What is the recommended way to format a resume?", createChoices("A) Use bullet points and a clear layout", "B) Write it in long paragraphs", "C) Include only personal information", "D) Use a casual tone"), 0));
        questions.add(new Question("How should you handle a situation where you receive a document with missing pages?", createChoices("A) Report the issue and request the complete document", "B) Proceed with the incomplete document", "C) Ignore the missing pages", "D) Guess the missing information"), 0));
        questions.add(new Question("What is the correct format for writing a professional email subject line?", createChoices("A) Brief and descriptive", "B) Long and detailed", "C) Generic and vague", "D) In all capital letters"), 0));
        questions.add(new Question("What is the best way to organize a physical filing cabinet?", createChoices("A) Use labeled folders and categorize documents", "B) Stack documents randomly", "C) Place all documents in one drawer", "D) Store documents without labels"), 0));
        questions.add(new Question("Which method is commonly used for document backup?", createChoices("A) Using cloud storage", "B) Storing documents on a single hard drive", "C) Keeping physical copies only", "D) Ignoring backup procedures"), 0));
        questions.add(new Question("What is the purpose of a 'desk organizer'?", createChoices("A) To keep office supplies and documents organized", "B) To decorate the desk", "C) To hold books", "D) To increase desk space"), 0));
        questions.add(new Question("What does 'data entry' typically involve?", createChoices("A) Inputting information into a computer system", "B) Creating marketing materials", "C) Scheduling appointments", "D) Conducting interviews"), 0));
        questions.add(new Question("What is the best way to ensure accuracy in data entry?", createChoices("A) Double-check the entered information", "B) Rely on the computer system to correct errors", "C) Enter data quickly without checking", "D) Ignore any inconsistencies"), 0));
        questions.add(new Question("How should a typist address a misalignment in text on a document?", createChoices("A) Adjust the margins and formatting", "B) Ignore it", "C) Use a different document format", "D) Reprint the document"), 0));
        questions.add(new Question("What is the purpose of using 'template documents'?", createChoices("A) To standardize and speed up document creation", "B) To create unique documents", "C) To ensure documents are always handwritten", "D) To store personal notes"), 0));
        questions.add(new Question("What is the best practice for handling incoming office mail?", createChoices("A) Sort and prioritize it immediately", "B) Leave it unattended", "C) Open and read all mail", "D) Store it in a drawer without sorting"), 0));
        questions.add(new Question("What is the recommended method for storing confidential files digitally?", createChoices("A) Use encryption and access controls", "B) Store on a public cloud", "C) Keep them in an open folder", "D) Share them with all staff"), 0));
        questions.add(new Question("Which punctuation mark is used to show possession?", createChoices("A) Comma", "B) Apostrophe", "C) Colon", "D) Semicolon"), 1));
        questions.add(new Question("How should a document be formatted for a presentation?", createChoices("A) Use bullet points and a clear layout", "B) Write in long paragraphs", "C) Include complex charts", "D) Use multiple font sizes"), 0));
        questions.add(new Question("What is the correct way to handle multiple versions of a document?", createChoices("A) Save each version with a different name or date", "B) Overwrite the previous version", "C) Delete old versions", "D) Keep all versions in one file"), 0));
        questions.add(new Question("What does 'double-spacing' in a document refer to?", createChoices("A) Leaving a full line of space between lines of text", "B) Reducing line spacing", "C) Using two fonts", "D) Increasing font size"), 0));
        questions.add(new Question("What is the purpose of a 'table of contents'?", createChoices("A) To provide an overview and page numbers for sections", "B) To include a summary of the document", "C) To list the authors", "D) To provide a cover page"), 0));
        questions.add(new Question("How should an error in a typed document be corrected before printing?", createChoices("A) Use a computer's editing tools to fix the error", "B) Manually cross out and rewrite the correct information", "C) Print the document and correct it by hand", "D) Ignore the error"), 0));
        questions.add(new Question("What does the term 'confidential' mean when handling documents?", createChoices("A) Restricted access to authorized individuals only", "B) Open for public viewing", "C) Available for all employees", "D) Shared with external partners"), 0));
        questions.add(new Question("What is the correct action when receiving a document with an incorrect address?", createChoices("A) Return it to the sender with the correct address", "B) Ignore the mistake", "C) Open the document anyway", "D) Dispose of it"), 0));
        questions.add(new Question("What is the primary function of a 'paper shredder' in an office?", createChoices("A) To destroy sensitive documents", "B) To organize papers", "C) To clean the desk", "D) To create paper clips"), 0));
        questions.add(new Question("What is the best way to organize files in a digital filing system?", createChoices("A) Use clear and consistent folder names", "B) Save all files in one folder", "C) Store files by date only", "D) Use a different naming convention for each file"), 0));
        questions.add(new Question("When creating a spreadsheet, what is the purpose of 'formulas'?", createChoices("A) To perform calculations automatically", "B) To format text", "C) To design graphics", "D) To sort data alphabetically"), 0));
        questions.add(new Question("What does 'document versioning' refer to?", createChoices("A) Keeping track of different iterations of a document", "B) Changing the document's font", "C) Adding graphics to the document", "D) Printing multiple copies"), 0));
        questions.add(new Question("What is an appropriate way to handle a large volume of incoming paperwork?", createChoices("A) Use a systematic sorting and filing process", "B) Stack all papers on the desk", "C) Discard unimportant documents immediately", "D) File all documents randomly"), 0));
        questions.add(new Question("How should a clerical worker handle confidential information?", createChoices("A) Store it in a secure location and limit access", "B) Share it with colleagues", "C) Keep it in an unlocked drawer", "D) Send it via unencrypted email"), 0));
        questions.add(new Question("What is the correct way to format a formal business letter?", createChoices("A) Use a professional tone, proper formatting, and correct punctuation", "B) Write casually and omit salutations", "C) Use a personal email style", "D) Avoid any formalities"), 0));
        questions.add(new Question("What is the purpose of a 'file index' in a filing system?", createChoices("A) To quickly locate and access files", "B) To create additional copies of files", "C) To decorate the filing cabinet", "D) To categorize documents by color"), 0));
        questions.add(new Question("What does 'spell check' do in a word processing program?", createChoices("A) Identifies and suggests corrections for spelling errors", "B) Formats the text", "C) Creates graphics", "D) Organizes files"), 0));
        questions.add(new Question("What is the role of 'metadata' in a document?", createChoices("A) To provide additional information about the document's content", "B) To format the document's text", "C) To add watermarks", "D) To change the document's color scheme"), 0));
        questions.add(new Question("How should you handle a situation where you need to prioritize tasks?", createChoices("A) Create a to-do list and tackle the most urgent tasks first", "B) Work on tasks randomly", "C) Complete tasks in alphabetical order", "D) Focus on less important tasks"), 0));
        questions.add(new Question("What does 'data validation' ensure in a database?", createChoices("A) That the data entered meets certain criteria and is accurate", "B) That the database is visually appealing", "C) That the database is empty", "D) That data is encrypted"), 0));
        questions.add(new Question("What is the purpose of a 'contact list' in office management?", createChoices("A) To keep track of important contacts and their information", "B) To list office supplies", "C) To create a calendar", "D) To file documents"), 0));
        questions.add(new Question("How should errors in a typed document be corrected during the editing process?", createChoices("A) Use editing tools to make corrections", "B) Reprint the document and correct manually", "C) Ignore the errors", "D) Cross out mistakes and write over them"), 0));
        questions.add(new Question("What does 'database backup' mean?", createChoices("A) Creating a copy of the database to prevent data loss", "B) Updating the database's design", "C) Reducing the database's size", "D) Increasing database speed"), 0));
        questions.add(new Question("What is the best way to format a report for clarity?", createChoices("A) Use headings, bullet points, and clear sections", "B) Write in long paragraphs", "C) Include excessive details", "D) Use varied fonts and colors"), 0));
        questions.add(new Question("What is a 'memo' used for in a business setting?", createChoices("A) To communicate brief messages internally", "B) To prepare detailed reports", "C) To create presentations", "D) To handle customer complaints"), 0));
        questions.add(new Question("How should a clerical worker handle a document with mixed formats?", createChoices("A) Standardize the format for consistency", "B) Leave it as is", "C) Print it in multiple formats", "D) Convert all text to one font"), 0));
        questions.add(new Question("What is the purpose of 'document collaboration' tools?", createChoices("A) To allow multiple users to work on a document simultaneously", "B) To create static documents", "C) To print multiple copies", "D) To encrypt documents"), 0));
        questions.add(new Question("What is an 'address book' used for in office settings?", createChoices("A) To keep track of contact information", "B) To record office supply purchases", "C) To store meeting notes", "D) To manage task lists"), 0));
        questions.add(new Question("How should you organize a digital folder structure for a project?", createChoices("A) Use a clear hierarchy and descriptive folder names", "B) Store all files in a single folder", "C) Use random names for folders", "D) Avoid creating subfolders"), 0));
        questions.add(new Question("What is the purpose of a 'calendar' in office management?", createChoices("A) To schedule and track meetings and deadlines", "B) To file documents", "C) To design marketing materials", "D) To track inventory"), 0));
        questions.add(new Question("What does 'document redaction' involve?", createChoices("A) Removing or obscuring sensitive information from a document", "B) Adding highlights to text", "C) Formatting the document's layout", "D) Printing additional copies"), 0));
        questions.add(new Question("How should you handle the sorting of physical files in an office?", createChoices("A) Use a consistent and logical filing system", "B) Stack files randomly", "C) Ignore sorting", "D) Change file locations frequently"), 0));
        questions.add(new Question("What is the best practice for handling office emails?", createChoices("A) Read and respond promptly, and organize by priority", "B) Ignore non-urgent emails", "C) Delete all emails immediately", "D) Keep all emails in one folder"), 0));
    }    

    private void setOnClickListeners() {
        nextButton.setOnClickListener(view -> {
            if (areAllQuestionsAnswered()) {
                if (currentQuestionIndex < TOTAL_PAGES - 1) {
                    currentQuestionIndex++;
                    showPage(currentQuestionIndex);
                }
            } else {
                showUnansweredQuestionsAlert();
            }
        });
    
        for (int i = 0; i < textToSpeechButtons.size(); i++) {
            int finalI = i; // To access in lambda expression
            ImageView button = textToSpeechButtons.get(finalI);
            if (button != null) {
                button.setOnClickListener(view -> speakText(questions.get(finalI).getQuestionText() + ". " + getChoicesText(finalI)));
            } else {
                Log.e("SubClericalQuestionnaireActivity", "ImageView in textToSpeechButtons list is null at index " + finalI);
            }
        }
    
        submitButton.setOnClickListener(view -> {
            // Save current page's answers before showing score
            saveCurrentPageAnswers();
            showScoreDialog();
        });
    }
    
    private boolean areAllQuestionsAnswered() {
        int start = currentQuestionIndex * QUESTIONS_PER_PAGE;
        int end = Math.min(start + QUESTIONS_PER_PAGE, questions.size());
    
        for (int i = start; i < end; i++) {
            int selectedId = choiceRadioGroups.get(i - start).getCheckedRadioButtonId();
            if (selectedId == -1) {
                return false; // Found an unanswered question
            }
        }
        return true; // All questions are answered
    }
    
    private void showUnansweredQuestionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please answer all questions before proceeding.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Save current page's answers before navigating
                        saveCurrentPageAnswers();
                        
                        // Find the first unanswered question and show it
                        int unansweredQuestionIndex = findFirstUnansweredQuestionIndex();
                        if (unansweredQuestionIndex != -1) {
                            showQuestion(unansweredQuestionIndex);
                        }
                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }    
    
    private int findFirstUnansweredQuestionIndex() {
        int start = currentQuestionIndex * QUESTIONS_PER_PAGE;
        int end = Math.min(start + QUESTIONS_PER_PAGE, questions.size());
    
        for (int i = start; i < end; i++) {
            int selectedId = choiceRadioGroups.get(i - start).getCheckedRadioButtonId();
            if (selectedId == -1) {
                return i; // Return the index of the first unanswered question
            }
        }
        return -1; // All questions are answered
    }
    
    private void showQuestion(int index) {
        // Calculate the page number of the question
        int pageIndex = index / QUESTIONS_PER_PAGE;
        int questionIndexOnPage = index % QUESTIONS_PER_PAGE;
    
        // Update the current page index
        currentQuestionIndex = pageIndex;
    
        // Show the corresponding page
        showPage(pageIndex);
    
        // Scroll to the specific question if needed
        scrollToQuestion(questionIndexOnPage);
    }
    
    private void scrollToQuestion(int questionIndexOnPage) {
        // Find the view for the specific question
        if (questionIndexOnPage < questionTextViews.size()) {
            // Set focus to the TextView of the unanswered question
            questionTextViews.get(questionIndexOnPage).requestFocus();
        } else {
            Log.e("SubClericalQuestionnaireActivity", "Invalid question index for scrolling: " + questionIndexOnPage);
        }
    }                

    private void showPage(int pageIndex) {
        int start = pageIndex * QUESTIONS_PER_PAGE;
        int end = Math.min(start + QUESTIONS_PER_PAGE, questions.size());
        
        // Hide all question views first
        for (int i = 0; i < questionTextViews.size(); i++) {
            questionTextViews.get(i).setVisibility(View.GONE);
            choiceRadioGroups.get(i).setVisibility(View.GONE);
            textToSpeechButtons.get(i).setVisibility(View.GONE);
        }
        
        // Show only the views for the current page
        for (int i = start; i < end; i++) {
            Question question = questions.get(i);
            TextView textView = questionTextViews.get(i - start);
            RadioGroup radioGroup = choiceRadioGroups.get(i - start);
            ImageView textToSpeechButton = textToSpeechButtons.get(i - start);
        
            textView.setText(question.getQuestionText());
            textView.setVisibility(View.VISIBLE);
            radioGroup.setVisibility(View.VISIBLE);
            textToSpeechButton.setVisibility(View.VISIBLE);
        
            List<String> choices = question.getChoices();
            for (int j = 0; j < choices.size(); j++) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(j);
                radioButton.setText(choices.get(j));
            }
        
            // Restore the previously selected answer
            if (question.getSelectedAnswerIndex() != -1) {
                ((RadioButton) radioGroup.getChildAt(question.getSelectedAnswerIndex())).setChecked(true);
            } else {
                radioGroup.clearCheck(); // Ensure no selection if nothing was previously selected
            }
        }
        
        // Show or hide the next and submit buttons
        nextButton.setVisibility(pageIndex < TOTAL_PAGES - 1 ? View.VISIBLE : View.INVISIBLE);
        submitButton.setVisibility(pageIndex == TOTAL_PAGES - 1 ? View.VISIBLE : View.INVISIBLE);
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

    private void saveCurrentPageAnswers() {
        int start = currentQuestionIndex * QUESTIONS_PER_PAGE;
        int end = Math.min(start + QUESTIONS_PER_PAGE, questions.size());
    
        for (int i = start; i < end; i++) {
            int selectedId = choiceRadioGroups.get(i - start).getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton selectedRadioButton = findViewById(selectedId);
                int selectedAnswerIndex = choiceRadioGroups.get(i - start).indexOfChild(selectedRadioButton);
                questions.get(i).setSelectedAnswerIndex(selectedAnswerIndex);
            }
        }
    }    

    private void showScoreDialog() {
        int correctAnswers = 0;
        StringBuilder correctAnswersText = new StringBuilder();
        
        // Calculate the score and correct answers
        for (int i = 0; i < questions.size(); i++) {
            if (checkAnswer(i)) {
                correctAnswers++;
                correctAnswersText.append(i + 1).append(". Correct\n");
            } else {
                correctAnswersText.append(i + 1).append(". (Correct Answer: ")
                        .append(questions.get(i).getChoices().get(questions.get(i).getCorrectAnswerIndex())).append(")\n");
            }
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
        int selectedId = choiceRadioGroups.get(index % QUESTIONS_PER_PAGE).getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        int selectedAnswerIndex = choiceRadioGroups.get(index % QUESTIONS_PER_PAGE).indexOfChild(selectedRadioButton);
        questions.get(index).setSelectedAnswerIndex(selectedAnswerIndex); // Save the selected answer
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