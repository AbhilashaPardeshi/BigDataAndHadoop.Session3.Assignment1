package Assignment1;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
/**
 * 
 * @author abhilasha : 19/05/2017 
 * Session3.Assignment1
 *
 */
public class MainClass {

	public static void main(String[] args) throws Exception 
	{
		
		//Instantiate configuration object and populate it with configuration files
		Configuration conf = new Configuration();
		conf.addResource(new Path("/usr/local/hadoop-2.6.0/etc/hadoop/core-site.xml"));
		conf.addResource(new Path("/usr/local/hadoop-2.6.0/etc/hadoop/hdfs-site.xml"));
		
		//Instantiate FileSystem object
		FileSystem fs = FileSystem.get(conf);
		
		Scanner scanner = new Scanner(System.in);

		//Take directory, who listing is to be performed, as input
		System.out.println("Enter the directory to perform list on : ");
		String strPath = scanner.next();
	
		//Instantiate the path variable using the input directory 
		Path path = new Path(strPath);
		
		if(!fs.isDirectory(path))
		{
			System.out.println("Input path is not that of a directory");
			throw new Exception("Provided path is that of a file when it was expecting that of a directory");
		}

	
		//************Task1 ******************
		//List all sub-directories and files in the input path 
		System.out.println("*********** Task1 : Listing of directories/Files ************");
		listFilesAndDirectories(fs,path);
		
		//************ Task2 ****************
		//List all sub-directories and files in the input path recursively
		System.out.println("\n*********** Task2 : Recursive listing of directories/Files ************");
		listFilesAndDirectoriesRecursively(fs,path);
		
		
		//************ Task3 ****************
		//List all sub-directories and files in all the input paths recursively
		System.out.println("\n*********** Task3 : Recursive listing of directories/Files for multiple paths************");
		listMultipleDirectories(fs,scanner);
				
		fs.close();
		
		scanner.close();
	}


	private static void listMultipleDirectories(FileSystem fs, Scanner scanner) throws IOException, Exception 
	{
		//Take directory, who listing is to be performed, as input
		System.out.println("Enter the directories (separated by space) to perform list : ");
		scanner.nextLine();
		String strPaths = scanner.nextLine();
	
		String[] aSplit = strPaths.split("\\s+");
		System.out.println("Number of paths : "+aSplit.length);
		
		for(String strPath:aSplit)
		{	
			//Instantiate the path variable using the input directory 
			Path path = new Path(strPath);
		
			if(!fs.isDirectory(path))
			{
				System.out.println("Input path is not that of a directory");
				throw new Exception("Provided path is that of a file when it was expecting that of a directory");
			}
			
			System.out.println("Listing for directory "+strPath+" is as follows : ");
			listFilesAndDirectoriesRecursively(fs, path);
			System.out.println("---------------------------------------");
		}	

	
	}


	private static void listFilesAndDirectoriesRecursively(FileSystem fs, Path path) throws IOException, Exception 
	{
		FileStatus[] aFileStatuses=fs.listStatus(path);
		for(FileStatus filesStatus:aFileStatuses)
		{
			System.out.println("Path : "+filesStatus.getPath());
			System.out.println("Modification Time : "+filesStatus.getOwner());
			System.out.println("Is Directory : "+filesStatus.isDirectory());
			System.out.println("Length : "+filesStatus.getLen());
			System.out.println("Permissions : "+filesStatus.getPermission().toString());
			System.out.println();
			
			if(filesStatus.isDirectory())
			{
				listFilesAndDirectories(fs, filesStatus.getPath());
			}
		}	

	}


	private static void listFilesAndDirectories(FileSystem fs, Path path) throws Exception 
	{
		FileStatus[] aFileStatuses=fs.listStatus(path);
		for(FileStatus filesStatus:aFileStatuses)
		{
			System.out.println("Path : "+filesStatus.getPath());
			System.out.println("Modification Time : "+filesStatus.getOwner());
			System.out.println("Is Directory : "+filesStatus.isDirectory());
			System.out.println("Length : "+filesStatus.getLen());
			System.out.println("Permissions : "+filesStatus.getPermission().toString());
			System.out.println();
		}
	}


	
}

