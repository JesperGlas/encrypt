import java.util.*;
import java.io.File;
import java.io.PrintStream;


public class Encrypt {

  /*
  Variables
  ##############################################################################
  */
  private static String input_file_path = "input.txt";
  private static String input = "";

  private static String output_file_path = "output.txt";
  private static String output = "";
  private static PrintStream print_to_file;

  private static List<String> sentences = new ArrayList<String>();
  private static String current_sentence;



  /*
  Main function of the program
  */
  public static void main(String[] args) {
    // Find file for input and output
    try {
        File input_file = new File( input_file_path );
        Scanner input_data = new Scanner( input_file );
        print_to_file = new PrintStream( output_file_path );

        while (input_data.hasNextLine()) {
            input = input + input_data.nextLine();
        }
        input_data.close();

    } catch (Exception ex) {
        ex.printStackTrace();
    }

    // Make the input all lowercase
    input = input.toLowerCase();

    // Split the input string at all full stops and put it in an array
    sentences = splitString( input );
    
    // Iterate through the sentences array and encrypt each element
    for(int i = 0; i < sentences.size(); i++) {
      // Assign current part to variable for easy reading
      current_sentence = sentences.get(i);
      // Decode current_sentence and append it to the output_build
      output += formatSentence( encryptWord( current_sentence ) );
      // Add whitespace after all sentences that isn't the last
      if(i != sentences.size()-1) {
        output += " ";
      }
    }
    // Format the final output with added spaces
    //output = formatOutput( output );
    // Print the formatted output
    System.out.println( output );
    print_to_file.println( output );
    print_to_file.flush();
  }

  /*
  Functions
  ##############################################################################
  */

  /*
  Function to split the array at all full stops.
  Returns an array of Strings
  */
  public static List<String> splitString(String input_str) {
    // Initlize a temporary List to return
    List<String> temp_sentence = new ArrayList<String>();
    // Split the string at all full stops
    temp_sentence = Arrays.asList( input_str.split( "\\." ) );
    return temp_sentence;
  }

  /*
  Function to split up each word of a sentence
  */
  public static List<String> splitSentence(String input_sentence) {
    // Initlize an array with all the words of a sentence
    List<String> words = new ArrayList<String>();
    // Split the sentence to words
    words = Arrays.asList( input_sentence.split( "\\s+" ) );
    return words;
  }

  /*
  Function to encrypt a sentence
  */
  public static List<String> encryptSentence(List<String> input_sentence) {
    // Initlize temporary String array to store the encrypted sentence
    List<String> temp_sentence = new ArrayList<String>();
    // Iterate through the array of words and encrypt each word
    for(int i = 0; i < input_sentence.size(); i++) {
      // Encrypt each word and add it to the return array at the correct index i
      temp_sentence.add( encryptWord( input_sentence.get(i) ) );
    }
    return temp_sentence;
  }

  /*
  Function that encrypts each part
  */
  public static String encryptWord(String input_elem) {
    // Initlize a StringBuilder used for each element
    StringBuilder temp_builder = new StringBuilder();
    // We then iterate through each letter of the string and shuffle them around
    for(int n = 0; n < input_elem.length(); n++ ) {
      if( (n % 2) == 0 ) { /* Every other letter we put at the end of the StringBuilder.. */
        temp_builder.append( Character.toString( input_elem.charAt(n)) );
      } else { /* and everyother we put at the beginning of the StringBuilder */
        temp_builder.insert( 0, Character.toString( input_elem.charAt(n) ) );
      }

    }
    // Add a whitespace at the end of each word.
    temp_builder.append( " " );
    return temp_builder.toString();
  }

  /*
  Function to format each sentence to look decent
  */
  public static String formatSentence(String encrypted_elem) {
    // Initlize a string to containg the formatted part
    String formatted_sentence;
    // Temporary index variable
    int char_index = 0;
    // Check if the first char of the string is whitespace
    if(Character.isWhitespace( encrypted_elem.charAt(0) )) {
      char_index = 1;
    }
    // Make the first char of the part uppercase
    formatted_sentence = encrypted_elem.substring(char_index, char_index+1).toUpperCase() + encrypted_elem.substring(char_index+1);
    // Remove the whitespace after the last word of the sentence
    formatted_sentence = formatted_sentence.substring( 0, formatted_sentence.length()-1 );
    // Add full stop at the end
    formatted_sentence += ".";
    // Return a formatted sentence
    return formatted_sentence;
  }

}
