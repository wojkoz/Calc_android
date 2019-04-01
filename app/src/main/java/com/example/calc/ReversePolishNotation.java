package com.example.calc;

import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;

public class ReversePolishNotation {

    private Stack<Double> stack = new Stack<>();

    private String[] arr;

    private String onp = "", result="";


    private void calculate() throws ArithmeticException{
        Double la,lb;

        for(int i=0;i<arr.length;i++) {

            if (arr[i].charAt(0) == '+') {

                la = stack.pop();
                lb = stack.pop();
                stack.push(lb + la);
            } else {
                if (arr[i].charAt(0) =='-') {

                    la = stack.pop();
                    lb = stack.pop();
                    stack.push(lb - la);
                } else {
                    if (arr[i].charAt(0) =='*') {

                        la = stack.pop();
                        lb = stack.pop();
                        stack.push(lb * la);
                    } else {
                        if (arr[i].charAt(0) =='/') {

                            la = stack.pop();
                            lb = stack.pop();
                            if(la == 0)
                                throw new ArithmeticException();
                            stack.push(lb / la);
                        } else {
                            if (arr[i].equals(" ")) {

                            } else {

                                try {
                                    stack.push(Double.parseDouble(arr[i]));

                                } catch (NumberFormatException e) {

                                }
                            }

                        }

                    }

                }


            }
        }
        takeFromStack();
    }
    private void takeFromStack(){ result = Double.toString(stack.pop());}


    private String makeOnp(String s) throws EmptyStackException {
        if (s == null)
            return null;
        String res = "";
        int len = s.length();
        Stack<Character> operator = new Stack<Character>();
        Stack<String> reversePolish = new Stack<String>();

        //avoid checking empty
        operator.push('#');
        Scanner scanner = new Scanner(s);
        while (scanner.hasNext()) {

            // if the next is a Double,
            // print found and the Double
            if (scanner.hasNextDouble()) {
                reversePolish.push(String.valueOf(scanner.nextDouble()));
            } else {
                char[] a = scanner.next().toCharArray();

                switch (a[0]) {
                    case '-':
                    case '+':

                        while (operator.peek() != '#')
                            reversePolish.push(Character.toString(operator.pop()));
                        operator.push(a[0]);
                        break;
                    case '*':
                    case '/':
                        while (operator.peek() != '#' && operator.peek() != '+' &&
                                operator.peek() != '-')
                            reversePolish.push(Character.toString(operator.pop()));
                        operator.push(a[0]);
                        break;
                }

            }

        }
        while (operator.peek() != '#')
            reversePolish.push(Character.toString(operator.pop()));
        while (!reversePolish.isEmpty())
            res = res.length() == 0? reversePolish.pop() + res: reversePolish.pop() + " " + res;
        return res;
    }

    public String convertToOnp(String s) throws EmptyStackException, ArithmeticException{

        if(s!=null){
            onp = makeOnp(s);

            arr = onp.split(" ");

            calculate();

            return result;

        }
        else{
            return "Error";
        }

    }
}
