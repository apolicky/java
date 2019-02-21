package cz.cuni.mff.java.semestr3.domaciukoly.brainfuck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class matej {


        static class interpret
        {
            public int go=0;
            int max;
            int pointer=0;
            int[] data;
            public void interpret(int size)
            {
                max=size;
                data=new int[size];
                pointer=0;
            }

            void decrement()
            {
                data[pointer]--;
            }

            void left()
            {
                pointer--;
            }

            void right()
            {
                pointer++;
            }

            void increment()
            {
                data[pointer]++;
            }

            void read()
            {
                try {
                    data[pointer]=System.in.read();
                }
                catch (Exception e)
                {
                    System.out.print("ERROR OF READ");
                }

            }

            void write()
            {
                System.out.print((char) (data[pointer]));
            }

            void jumpR()
            {
                if(data[pointer]==0)
                    go++;
            }

            void jumpL()
            {
                if(data[pointer]!=0)
                    go--;
            }

            public boolean DO(char com)
            {
                switch(com)
                {
                    case '+':
                    {
                        this.increment();
                    }break;

                    case '-':
                    {
                        this.decrement();
                    }break;

                    case '>':
                    {
                        this.right();
                    }break;

                    case '<':
                    {
                        this.left();
                    }break;

                    case '.':
                    {
                        this.write();
                    }break;

                    case ',':
                    {
                        this.read();
                    }break;

                    case ']':
                    {
                        this.jumpL();
                    }break;

                    case '[':
                    {
                        this.jumpR();
                    }break;

                    default:{return false;}
                }
                if(pointer<0){
                    System.out.print("Memory underrun");
                    return false;
                }
                if(pointer>=this.max){
                    System.out.print("Memory overrun");
                    return false;
                }
                return true;
            }



        }


        static class  program
        {
            public int ePointer =0;
            public int lenght=0;
            List prg=new ArrayList();


            public char step()
            {
                ePointer++;
                return (char)(prg.get(ePointer-1));
            }

            public void goTOR(int x)
            {
                ePointer--;
                while(x!=0)
                {
                    ePointer++;
                    if((char)prg.get(ePointer)==']')
                    {
                        x--;

                    }
                    if((char)prg.get(ePointer)=='[')
                    {
                        x++;

                    }
                }
                ePointer++;
            }

            public void goTOL(int x)
            {
                ePointer--;
                while(x < 0){
                    ePointer--;
                    if((char)prg.get(ePointer)==']')
                    {
                        x--;

                    }
                    if((char)prg.get(ePointer)=='[')
                    {
                        x++;

                    }

                }

            }

            public void goTO(int x)
            {
                if(x>0)
                    this.goTOR(x);
                else
                    this.goTOL(x);
            }









            public boolean tryProgram(String file)  //PART FOR TESTING OF CORRECTNOST PROGRAM
            {
                try {

                    BufferedReader br = new BufferedReader(new FileReader(new File(file)));
                    int x=0;

                    List opened=new ArrayList();
                    String line=br.readLine();

                    while (line  != null) {
                        for (int i = 0; i < line.length(); i++) {
                            if(line.charAt(i)=='[')opened.add((x+1)+" character "+(i+1));
                            if(line.charAt(i)==']'){
                                if(opened.size()<1){
                                    System.out.print("Unopened cycle - line "+(x+1)+" character "+(i+1));
                                    return false;
                                }
                                opened.remove(opened.size()-1);
                            }


                        }
                        line=br.readLine();
                        x++;
                    }


                    if(opened.size()>0){
                        System.out.print("Unclosed cycle - line "+opened.get(opened.size()-1));
                        return false;
                    }
                }
                catch(Exception e){
                    System.out.print("ERROR OF READING PROGRAM");
                    return false;
                }


                return true;
            }







            public boolean readProgram(String file)
            {
                try {

                    BufferedReader br = new BufferedReader(new FileReader(new File(file)));

                    String st=br.readLine();

                    while (st  != null) {
                        for (int i = 0; i < st.length(); i++) {
                            if (st.charAt(i) == '+' || st.charAt(i) == '-' || st.charAt(i) == '.' || st.charAt(i) == ',' || st.charAt(i) == '<' || st.charAt(i) == '>' || st.charAt(i) == ']' || st.charAt(i) == '[') {
                                prg.add(st.charAt(i));

                            }
                        }
                        st=br.readLine();
                    }
                }
                catch(Exception e){
                    System.out.print("ERROR OF READING PROGRAM");
                    return false;
                }
                this.lenght=prg.size();
                //for(int i=0;i<prg.size();i++){System.out.print(prg.get(i));}  //write out program
                return true;
            }

            public boolean loopTest()
            {
                int count=0;
                for (int i = 0; i < prg.size(); i++)
                {
                    if(prg.get(i)=="[")count++;
                    if(prg.get(i)=="]")count--;
                    if(count<0)return false;

                }
                if(count>0)return false;
                return true;
            }
        }

        public static void main(String[] args){

            BufferedReader buff=new BufferedReader(new InputStreamReader(System.in));
            int size=30000;
            String file="default.txt";
            try {
                if (args.length == 0) {
                    size = 30000;
                    file = buff.readLine();
                } else if (args.length == 1) {
                    size = 30000;
                    file = args[0];
                } else {
                    size = Integer.parseInt(args[1]);
                    file = args[0];
                }
            }
            catch(Exception e){
                System.out.print("Something go wrong!");
            }



            program prg=new program();
            prg.readProgram(file);
            interpret interp= new interpret();
            interp.interpret(size);
            if(prg.tryProgram(file))
                while(prg.ePointer<prg.lenght)
                {
                    if(!interp.DO(prg.step()))break;
                    if(interp.go!=0)prg.goTO(interp.go);
                    interp.go=0;

                }

        }
    }

