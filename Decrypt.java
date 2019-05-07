import java.util.*;
import java.io.File;
import java.io.PrintStream;

public class Decrypt {

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
  private static String current_part;

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

    // Make the input string all lowercase
    input = input.toLowerCase();
    // Split the input into sentences
    sentences = splitString( input );
    // Iterate through the sentences array and decode each element
    for(int i = 0; i < sentences.size(); i++) {
      // Assign current part to variable for easy reading
      current_part = sentences.get(i);
      // Remove the space added for visual effect by the encrypt module, but not on first element
      if(i != 0) {
        current_part = current_part.substring(1);
      }
      // Decode current_part and append it to the output_build
      output += formatSentence( decodePart( current_part ) );
      // Add whitespace after all sentences that isn't the last
      if(i != sentences.size()-1) {
        output += " ";
      }
    }
    // Print the formated output
    System.out.println( output );
    print_to_file.println( output );
    print_to_file.flush();
  }

  /*
  Function to split the array at all full stops.
  Returns an array of Strings
  */
  public static List<String> splitString(String input_str) {
    // Initlize temporary List to return
    List<String> temp_sentence = new ArrayList<String>();
    // Split the string at all full stops
    temp_sentence = Arrays.asList( input_str.split("\\.") );
    return temp_sentence;
  }

  /*
  Function that goes through the array of strings and decodes it based on the encryption model
  */
  public static String decodePart(String input_elem) {
    // Initlize a StringBuilder used for each element
    StringBuilder temp_builder = new StringBuilder();
    // Initlize two temporary counters
    int even = 0;
    int uneven = 0;
    // Iterate through the input array and decode each element
    for(int i = 0; i < input_elem.length(); i++) {
      if( input_elem.length() % 2 != 0 ) {
        if( (i % 2) == 0 ) { /* Every other letter we put at the end of the StringBuilder.. */
          temp_builder.insert( 0, input_elem.charAt( input_elem.length()-1 - even ) );
          even++;
        } else { /* and everyother we put at the beginning of the StringBuilder */
          temp_builder.insert( 0, input_elem.charAt( uneven ) );
          uneven++;
        }
      } else {
        if( (i % 2) != 0 ) { /* Every other letter we put at the end of the StringBuilder.. */
          temp_builder.insert( 0, input_elem.charAt( input_elem.length()-1 - even ) );
          even++;
        } else { /* and everyother we put at the beginning of the StringBuilder */
          temp_builder.insert( 0, input_elem.charAt( uneven ) );
          uneven++;
        }
      }
    }
    // Convert the builder to a string and return it
    return temp_builder.toString();
  }

  /*
  Function that formates the decoded string (Uppercade and full stop)
  */
  public static String formatSentence(String decoded_part) {
    // Initlize a string to containg the formated part
    String formated_sentence;
    // Temporary index variable
    int char_index = 0;
    // Check if the first char of the string is whitespace
    if(Character.isWhitespace( decoded_part.charAt(0) )) {
      char_index = 1;
    }
    // Make the first char of the part uppercase
    formated_sentence = decoded_part.substring(char_index, char_index+1).toUpperCase() + decoded_part.substring(char_index+1);
    // Add full stop at the end
    formated_sentence += ".";
    // Return a formated sentence
    return formated_sentence;
  }

}
