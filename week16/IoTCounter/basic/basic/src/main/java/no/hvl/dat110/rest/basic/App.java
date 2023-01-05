package no.hvl.dat110.rest.basic;

import spark.Spark;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main(String[] args){
		
		if (args.length > 0) {
			Spark.port(Integer.parseInt(args[0]));
		} else {
			Spark.port(8080);
		}
		  
        Spark.get("/hello", (request, response) -> "Hello World!");
    }
}
