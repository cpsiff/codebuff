// Contributed by Taylor Mehmen
public class Ages {

  public static void main(String[] args) {
    int johnAge=5;
    int mattAge=10;
    int sarahAge=14;
    int emilyAge=6;

    System.out.println(
        "Ages of John "
           +johnAge
           +", Matt "
           +mattAge
           +", Sarah "
           +sarahAge
           +", Emily "
           +emilyAge);
    double avg=getAverageAge(johnAge, mattAge, sarahAge, emilyAge);
    System.out.println("Average age "+avg);

    // call the getNextAge method to calculate the age of each person, in 1 year
    johnAge=getNextAge(johnAge);
    mattAge=getNextAge(mattAge);
    sarahAge=getNextAge(sarahAge);
    emilyAge=getNextAge(emilyAge);

    System.out.println(
        "Ages next year of John "
           +johnAge
           +", Matt "
           +mattAge
           +", Sarah "
           +sarahAge
           +", Emily "
           +emilyAge);
    avg=getAverageAge(johnAge, mattAge, sarahAge, emilyAge);
    System.out.println("Average age "+avg);
  }

  public static int getNextAge(int age) {
    int nextAge=age+1;
    return nextAge;
  }

  public static double getAverageAge(int age1, int age2, int age3, int age4) {
    double averageAge=(age1+age2+age3+age4)/4.0;
    return averageAge;
  }
}
