import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Stack;

class Bracket {
    Bracket(char type, int position) {
        this.type = type;
        this.position = position;
    }

    boolean Match(char c) {
        if (this.type == '[' && c == ']')
            return true;
        if (this.type == '{' && c == '}')
            return true;
        if (this.type == '(' && c == ')')
            return true;
        return false;
    }

    char type;
    int position;
}

class check_brackets {
    public static void main(String[] args) throws IOException {
        InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
        String text = reader.readLine();

        Stack<Bracket> opening_brackets_stack = new Stack<Bracket>();
        for (int position = 0; position < text.length(); ++position) {
            char next = text.charAt(position);

            // create a new bracket, populate bracket type and bracket position
            Bracket bracket = new Bracket(next, position + 1);

            if (next == '(' || next == '[' || next == '{') {
                // Process opening bracket, write your code here

                // push a new bracket into a stack
                opening_brackets_stack.push(bracket);
            }

            if (next == ')' || next == ']' || next == '}') {
                // Process closing bracket, write your code here

                if (opening_brackets_stack.empty()) {
                    // This is bad, because the closing bracket cannot occur before the opening bracket.
                    // So we push a new bracket into a stack, and exit the loop.

                    // push a new bracket into a stack
                    opening_brackets_stack.push(bracket);

                    break;
                } else {
                    Bracket peek_bracket = opening_brackets_stack.peek();

                    if (peek_bracket.Match(next)) {
                        opening_brackets_stack.pop();
                    } else {
                        // This is bad, because the new bracket doesn't match the previous bracket.
                        // So we push a new bracket into a stack, and exit the loop.
                        opening_brackets_stack.push(bracket);

                        break;
                    }
                }
            }
        }

        // Printing answer, write your code here
        if (opening_brackets_stack.empty()) {
            // The stack is empty, which means all the brackets in the text matched correctly.
            System.out.println("Success");
        } else {
            // The stack is not empty, which means there were unmatched brackets.
            // The first unmatched bracket is the one at the top of the stack.
            Bracket bracket = opening_brackets_stack.peek();
            System.out.println(bracket.position);
        }
    }
}
