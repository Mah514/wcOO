package wcOO.counters;

import wcOO.fileManager.IFileManager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

//Returns the number of characters, words and lines in the file.
class WcCounter extends Counter{
    ICounter counter;
    CounterFactory counterFactory= new CounterFactory();

    @Override
    public void count(ArrayList<String> line) {
    	try {
                //Character Counter
                counter = counterFactory.createCounter("charcount");
                counter.setOpt(opt);
                counter.count(line);
                resetCount();

                //Word Counter
                counter = counterFactory.createCounter("wordcount");
                counter.setOpt(opt);
                counter.count(line);
                resetCount();

                //Line Counter
                counter = counterFactory.createCounter("linecount");
                counter.setOpt(opt);
                counter.count(line);
                resetCount();

    	}
    	catch (IOException e) {
    		System.out.println("Error in reading files");
    	}
    	catch (URISyntaxException e2) {
    		System.out.println("URI Syntax error");
    	}
    }

    @Override
    public void setFiles(ArrayList<IFileManager> file) {

    }
}
