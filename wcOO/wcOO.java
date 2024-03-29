package wcOO;

import wcOO.administrator.Administrator;
import wcOO.counters.*;
import wcOO.fileManager.*;
import wcOO.options.*;

import java.io.*;
import java.util.*;
import java.net.*;

//Main program file used to print number of characters, words, lines or copy contents to destination file
public class wcOO {

    private static ICounter counter;
    private static IOption option;
    private static CounterList counterList;
    private static ArrayList<IFileManager> file;
    private static ArrayList<String> data;
    private static Administrator admin;

    public static void main(String [] args){
        args = new String[]{"wc","-v", "wcOO/test.txt"};
        admin = new Administrator();
        admin.parse(args);
        wcOO.init();
        wcOO.execute();
    }

    //Initalizes the program with lines from file, counters and options given user input
    private static void init(){
        //Initializing Objects
        data = new ArrayList<>();
        counter = admin.getCounter();
        option = admin.getOption();
        file = admin.getFileList();
        counterList = admin.getCounterList();

    }

    //Interpret the file contents and call count method on all counter objects from counterList
    private static void execute(){
        System.out.println("\n================ wcOO Program ================");
        try {
            //Counters Processing
            if (!counter.getClass().getSimpleName().equals("CopyCounter")) {
                for (IFileManager iFileManager : file) {
                    iFileManager.openInputStream();
                    BufferedReader fileReader = new BufferedReader(new FileReader(iFileManager.getFile().getPath()));
                    while (fileReader.ready()) {
                        data.add(fileReader.readLine());
                    }
                    iFileManager.canReadFile();
                    counterList.setOpt(option);
                    counterList.count(data);
                    data.clear();
                    System.out.println("\n --------------------------------------------------");
                }
            }

            //Copy Program
            if (counter.getClass().getSimpleName().equals("CopyCounter")) {
                counterList.setOpt(option);
                counterList.setFiles(file);
                counterList.count(data);
            }

            //Closing All Files
            for (IFileManager iFileManager : file)
                iFileManager.close();

       }catch(URISyntaxException e){

            System.out.println("File not found at program source folder");
            System.out.println(e.getMessage());

        }catch(IOException e){

            System.out.println(e.getMessage());

        }catch(NullPointerException e){

            if((e.getMessage().contains("filePathSystem")) && (e.getMessage().contains("is null"))){
                for (IFileManager iFileManager : file) {
                    if (!iFileManager.isOpen()) {
                        System.out.println(iFileManager.getFileName() + " not found at program source folder");
                        System.out.println(e.getMessage());
                        return;
                    }
                }
            }
        }
    }
}
