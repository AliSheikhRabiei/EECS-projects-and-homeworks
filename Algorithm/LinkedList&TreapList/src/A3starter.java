import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class A3starter {

	public static void main(String[] args) {
		FileOutputStream fos;
		ObjectOutputStream oos;
		FileInputStream fis;
		ObjectInputStream ois;
		Random r = new Random();
		final int NUMBERS_TO_GENERATE = 1_000_000;
		final String FILE_NAME = "t.tmp";

		//write random numbers into a specified file
		try {
			fos = new FileOutputStream(FILE_NAME);
			oos = new ObjectOutputStream(fos);

			for (long i = 0; i < NUMBERS_TO_GENERATE; i++)
				oos.writeInt(r.nextInt());

			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//read the numbers from the file
		try {
			fis = new FileInputStream(FILE_NAME);
			ois = new ObjectInputStream(fis);

			System.out.println("File Size: " + fis.getChannel().size() + " bytes");
			int count = 0;
			while(ois.available() > 0) {
				//System.out.println(ois.readInt());
				count++;
				ois.readInt();
			}
			System.out.println(count);

			//reset to the beginning and read numbers at index 0 and at index 2 (skip one number = 4 bytes)
			fis.getChannel().position(0);
			ois = new ObjectInputStream(fis);

			System.out.println(ois.readInt());
			ois.skipNBytes(4);
			System.out.println(ois.readInt());

			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Delete a file 
//		try {
//			Files.deleteIfExists(new File (FILE_NAME).toPath());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
	}

}
