package java8.news;

public interface PersonFactory <P extends Person>{

	P create(String firstName, String lastName);
	
}
