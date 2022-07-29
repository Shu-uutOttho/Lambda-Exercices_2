package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Product;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter full file path: ");
		String path = sc.nextLine();

		try (BufferedReader BR = new BufferedReader(new FileReader(path))) {
			List<Product> list = new ArrayList<Product>();
			String line = BR.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Product(fields[0], Double.parseDouble(fields[1])));
				line = BR.readLine();
			}
			Double avg = list.stream().map(x -> x.getPrice()).reduce(0.0, (x, y) -> x + y) / list.size();

			System.out.println("Average price + " + String.format("%.2f", avg));

			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
			List<String> names = list.stream().filter(x -> x.getPrice() < avg).map(x -> x.getName())
					.sorted(comp.reversed()).collect(Collectors.toList());
			names.forEach(System.out::println);

		}

		catch (FileNotFoundException e) {
			System.out.println("File not found" + e.getMessage());
			e.printStackTrace();
		}

		catch (IOException e) {
			System.out.println("Error :" + e.getMessage());
			e.printStackTrace();
		}
		sc.close();
	}

}