package com.bridgelab.addressbooksystemusingcollections;

import com.google.gson.Gson;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/************************************************************************
 * @author mihir
 *
 * @since 14-July-2021
 * This class holds the logical part of the project.
 * This class has all the necessary methods that are required to maintain,
 * address book and records in it.
 ************************************************************************/

public class AddressBookService
{
    public enum IOService {CONSOLE_IO, FILE_IO, DB_IO, REST_IO}
    public static String addressBookFile = "AddressBookFile.txt";

    public static final String FILE_PATH = "C:\\Users\\mihir\\IdeaProjects\\AddressBookRefactored\\src\\Resources";
    public static final String CSV_FILE = "/AddressBook.csv";

    public static final String JSON_FILE="/AddressBook.json";

    //To scan the input.
    Scanner scanner;
    //To store contacts.
    ArrayList<PersonDetails> contactList;
    //To store multiple addressBook.
    Map<String,ArrayList<PersonDetails>> addressBook;
    List<PersonDetails> contactListDB = new ArrayList<>();

    //instantiating scanner and ArrayList in constructor.
    public AddressBookService()
    {
        scanner = new Scanner(System.in);
        contactList = new ArrayList<>();
        addressBook = new HashMap<>();
    }

    /**
     * Name : addNewContact
     *
     * Description : Adding new contact to AddressBook.
     *
     * Algorithm : In this method accepting all necessary contact details from user,
     * and adding those all details to store in AddressBook as a new contact.
     *
     * Modification : Final commit 16-July-2021.
     */
    public void addNewContact()
    {
        System.out.print("Enter how many contacts you want to store at a time : ");
        int enterCount = scanner.nextInt();
        for (int i = 0; i < enterCount; i++)
        {
            PersonDetails contactDetails = new PersonDetails();
            System.out.print("Enter First Name : ");
            String firstName = scanner.next();
            contactDetails.setFirstName(firstName);

            System.out.print("Enter Last Name : ");
            contactDetails.setLastName(scanner.next());

            System.out.print("Enter Address : ");
            contactDetails.setAddress(scanner.next());

            System.out.print("Enter City : ");
            contactDetails.setCity(scanner.next());

            System.out.print("Enter State : ");
            contactDetails.setState(scanner.next());

            System.out.print("Enter ZipCode : ");
            contactDetails.setZipCode(scanner.nextInt());

            System.out.print("Enter Phone-Number : ");
            contactDetails.setPhoneNumber(scanner.nextLong());

            System.out.print("Enter Email-Id : ");
            contactDetails.setEmailId(scanner.next());

            System.out.print("Enter Book name to which you have to add contact : ");
            String bookName  = scanner.next();
            if(addressBook.containsKey(bookName))
            {
                ArrayList<PersonDetails> contactList = addressBook.get(bookName);
                //checking for duplicate contact
                contactList.stream().filter(personDetails -> personDetails.getFirstName().equals(firstName)).forEach(personDetails -> {
                    System.out.println("Sorry can not allow duplicate contact :");
                    addNewContact();
                });
                contactList.add(contactDetails);
                addressBook.put(bookName,contactList);
                System.out.println("New Contact Added Successfully");
            }
            else
            {
                contactList.stream().filter(personDetails -> personDetails.getFirstName().equals(firstName)).forEach(personDetails -> {
                    System.out.println("Sorry can not allow duplicate contact :");
                    addNewContact();
                });
                contactList.add(contactDetails);
                addressBook.put(bookName,contactList);
                System.out.println("New Address-Book created and added Contact Added Successfully");
            }
        }
    }

    /**
     * Name : writeToFile
     *
     * Description : writing data to file.
     *
     * Modification : First commit 20-July-2021
     */
    public void writeToFile()
    {
        StringBuffer addressBuffer = new StringBuffer();
        contactList.forEach(address -> { String addressDataString = address.toString().concat("\n");addressBuffer.append(addressDataString);});
        try
        {
            Files.write(Paths.get(addressBookFile),addressBuffer.toString().getBytes(StandardCharsets.UTF_8));
            System.out.println("Data successfully written to file.");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Name : readDataFromFile
     *
     * Description : Reading data from file and printing to console.
     *
     * Modification : 20-July-2021.
     */
    public void readDataFromFile()
    {
        try
        {
            System.out.println("Reading Data From File :");
            Files.lines(new File(addressBookFile).toPath()).map(line -> line.trim()).forEach(line -> System.out.println(line));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Name : editFullContact
     *
     * Description : Asking user to enter mobile number associated contact will be edited.
     *
     * Algorithm : Taking input from user mobile number because that will be unique,
     * fetching associated record to that mobile number.
     * asking user to choose which field from contact you want to edit.
     * accordingly user will enter new value that is to be set to respective field of contact.
     *
     * Modification : Final Commit 16-July-2021
     */
    public void editFullContact()
    {
        System.out.print("Enter the address-book name from which you want to edit the contact : ");
        String searchBookName = scanner.next();
        if (addressBook.containsKey(searchBookName))
        {
            System.out.print("Enter mobile number to select the contact to be edited : ");
            int searchMNumber = scanner.nextInt();
            System.out.println("Select filed number that you want to edit :\n1.Edit First-Name,\n2.Edit Last-Name,\n3.Edit Address,\n4.Edit City.\n5.Edit State,\n6.Edit ZipCode,\n7.Edit Phone-Number,\n8.Edit EmailId.\n");
            int fieldNumber = scanner.nextInt();
            switch (fieldNumber)
            {
                case 1:
                    //Edit Fist-Name
                    System.out.print("What new first name you want to set : ");
                    String editFistName = scanner.next();
                    for (int i = 0; i < contactList.size(); i++)
                    {
                        if (contactList.get(i).getPhoneNumber() == searchMNumber)
                        {
                            contactList.get(i).setFirstName(editFistName);
                            System.out.println("\nFirst-Name of respective contact has been successfully edited.\n");
                        }
                    }
                    break;
                case 2:
                    //Edit Last-Name
                    System.out.print("What new last name you want to set : ");
                    String editLastName = scanner.next();
                    for (int i = 0; i < contactList.size(); i++)
                    {
                        if (contactList.get(i).getPhoneNumber() == searchMNumber)
                        {
                            contactList.get(i).setLastName(editLastName);
                            System.out.println("\nLast-Name of respective contact has been successfully edited.\n");
                        }
                    }
                    break;
                case 3:
                    //Edit Address
                    System.out.print("What new address you want to set : ");
                    String editAddress = scanner.next();
                    for (int i = 0; i < contactList.size(); i++)
                    {
                        if (contactList.get(i).getPhoneNumber() == searchMNumber)
                        {
                            contactList.get(i).setAddress(editAddress);
                            System.out.println("\nAddress of respective contact has been successfully edited.\n");
                        }
                    }
                    break;
                case 4:
                    //Edit City
                    System.out.print("What new city you want to set : ");
                    String editCity = scanner.next();
                    for (int i = 0; i < contactList.size(); i++)
                    {
                        if (contactList.get(i).getPhoneNumber() == searchMNumber)
                        {
                            contactList.get(i).setCity(editCity);
                            System.out.println("\nCity of respective contact has been successfully edited.\n");
                        }
                    }
                    break;
                case 5:
                    //Edit State
                    System.out.print("What new state you want to set : ");
                    String editState = scanner.next();
                    for (int i = 0; i < contactList.size(); i++)
                    {
                        if (contactList.get(i).getPhoneNumber() == searchMNumber)
                        {
                            contactList.get(i).setState(editState);
                            System.out.println("\nState of respective contact has been successfully edited.\n");
                        }
                    }
                    break;
                case 6:
                    //Edit ZipCode
                    System.out.print("What new zipcode you want to set : ");
                    int editZipCode = scanner.nextInt();
                    for (int i = 0; i < contactList.size(); i++)
                    {
                        if (contactList.get(i).getPhoneNumber() == searchMNumber)
                        {
                            contactList.get(i).setZipCode(editZipCode);
                            System.out.println("\nZip-Code of respective contact has been successfully edited.\n");
                        }
                    }
                    break;
                case 7:
                    //Edit Mobile-Number
                    System.out.print("What new mobile number you want to set : ");
                    long editPhoneNumber = scanner.nextLong();
                    for (int i = 0; i < contactList.size(); i++)
                    {
                        if (contactList.get(i).getPhoneNumber() == searchMNumber)
                        {
                            contactList.get(i).setPhoneNumber(editPhoneNumber);
                            System.out.println("\nMobile-Number of respective contact has been successfully edited.\n");
                        }
                    }
                    break;
                case 8:
                    //Edit Email-id
                    System.out.print("What new email-id you want to set : ");
                    String editEmailId = scanner.next();
                    for (int i = 0; i < contactList.size(); i++)
                    {
                        if (contactList.get(i).getPhoneNumber() == searchMNumber)
                        {
                            contactList.get(i).setEmailId(editEmailId);
                            System.out.println("\nEmail-Id of respective contact has been successfully edited.\n");
                        }
                    }
                    break;
                default:
                    System.out.println("Incorrect Entry !");
            }
        }
    }

    /**
     * Name : deleteContact
     *
     * Description : Deleting contact as per user input.
     *
     * Algorithm : Taking mobile number because it will be unique to delete associated contact.
     * Iterating over array to get the exact match record once we get it making that record as null.
     * Which is considered to be deleted.
     *
     * Modification : First commit 14-July-2021
     */
    public void deleteContact()
    {
        System.out.print("\nGive a mobile number to deleted associated contact : ");
        long searchNumber = scanner.nextLong();
        for(int i = 0; i < contactList.size(); i++)
        {
            if(contactList.get(i).getPhoneNumber() == searchNumber)
            {
                contactList.remove(i);
                System.out.println("\nContact successfully deleted\n");
                break;
            }
            else
            {
                System.out.println("Contact associated with the given mobile number does not exist." +
                        "Enter correct mobile number.");
                deleteContact();
            }
        }
    }

    /**
     * Name : searchPersonInACityOrState
     *
     * Description : Searching person by city or state.
     *
     * Algorithm : Printing Person contact who belongs to given city or state.
     *
     * Modification : Final commit 16-July-2021
     */
    public void searchPersonInACityOrState()
    {
        System.out.print("Enter City Name Or State Name To Search Contact : ");
        String searchCityState = scanner.next();
        System.out.println("\nFollowing are the persons who belongs to : " + searchCityState);
        contactList.stream().filter(details -> details.getCity().equals(searchCityState) || details.getState().equals(searchCityState)).map(PersonDetails::getFirstName).forEach(System.out::println);
    }

    /**
     * Name : getNumberOfContacts
     *
     * Description : Printing total count of contacts present in respective address book.
     *
     * Modification : First commit 14-July-2021
     */
    public void getNumberOfContacts()
    {
        System.out.print("Enter City Name Or State Name To Get The Count Of Contacts : ");
        String countContacts = scanner.next();
        int totalCount = 0;
        contactList.stream().filter(details -> details.getCity().equals(countContacts) || details.getState().equals(countContacts)).map(PersonDetails::getFirstName).forEach(System.out::println);totalCount++;
        System.out.println("\nTotal number of contacts present in " + countContacts + " is : " + totalCount);
    }

    /**
     * Name : sortContactByFirstName
     *
     * Description : Sorting existing contacts in address book by their first name.
     *
     * Modification : First commit 16-July-2021
     */
    public void sortContactByFirstName()
    {
        addressBook.keySet().forEach((String name) -> {
            addressBook.get(name).stream().sorted(Comparator.comparing(PersonDetails::getFirstName))
                    .collect(Collectors.toList()).forEach(person -> System.out.println(person.toString()));
        });
    }

    /**
     * Name : sortByZipCode
     *
     * Description : Sorting existing contact using zipcode.
     *
     * Modification : First commit 16-July-2021
     */
    public void sortByZipCode()
    {
        addressBook.keySet().forEach((String key) -> {
            addressBook.get(key).stream()
                    .sorted(Comparator.comparing(PersonDetails::getZipCode))
                    .collect(Collectors.toList())
                    .forEach(person -> System.out.println(person.toString()));
        });
    }

    /**
     * Name : writeToCsv
     *
     * Description : Person details storing inside csv file.
     * nothing but writing to csv file.
     *
     * Modification : First commit 26-July-2021
     */
    public void writeToCsv()
    {
        try
        {
            Writer writer = Files.newBufferedWriter(Paths.get(FILE_PATH+CSV_FILE));
            StatefulBeanToCsv<PersonDetails> beanToCsv = new StatefulBeanToCsvBuilder<PersonDetails>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            List<PersonDetails> ContactList = new ArrayList<>();
            addressBook.entrySet().stream()
                    .map(books->books.getKey())
                    .map(bookNames->{
                        return addressBook.get(bookNames);
                    }).forEach(contacts ->{
                ContactList.addAll(contacts);
            });
            beanToCsv.write(ContactList);
            writer.close();
        }
        catch (CsvDataTypeMismatchException e)
        {
            e.printStackTrace();
        }
        catch (CsvRequiredFieldEmptyException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Name : readFromCsvFile
     *
     * Description : Reading data from csv file.
     *
     * Modification : First commit 26-July-2021
     */
    public void readFromCsvFile()
    {
        Reader reader;
        try {
            reader = Files.newBufferedReader(Paths.get(FILE_PATH+CSV_FILE));
            CsvToBean<PersonDetails> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(PersonDetails.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            List<PersonDetails> contacts = csvToBean.parse();

            for(PersonDetails contact: contacts) {
                System.out.println("Name : " + contact.getFirstName()+" "+contact.getLastName());
                System.out.println("Email : " + contact.getEmailId());
                System.out.println("PhoneNo : " + contact.getPhoneNumber());
                System.out.println("Address : " + contact.getAddress());
                System.out.println("State : " + contact.getState());
                System.out.println("City : " + contact.getCity());
                System.out.println("Zip : " + contact.getZipCode());
                System.out.println("==========================");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Name : writeToJson
     *
     * Description : Writing data to json file.
     *
     * Modification : First commit 26-July-2021
     */
    public void writeToJson()
    {
        List<PersonDetails> contacts = getContentOfCsv();
        Gson gson = new Gson();
        String json = gson.toJson(contacts);
        try
        {
            FileWriter writer = new FileWriter(FILE_PATH+JSON_FILE);
            writer.write(json);
            writer.close();
            System.out.println("Written sucessfully");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Name : readFromJson
     *
     * Description : Reading data from.
     *
     * Modification : First commit 26-July-2021
     */
    public void readFromJson()
    {
        try
        {
            Gson gson = new Gson();
            BufferedReader br = new BufferedReader(new FileReader(FILE_PATH+JSON_FILE));
            PersonDetails[] contacts = gson.fromJson(br,PersonDetails[].class);
            List<PersonDetails> contactsList = Arrays.asList(contacts);
            for(PersonDetails contact: contactsList) {
                System.out.println("Name : " + contact.getFirstName()+" "+contact.getLastName());
                System.out.println("Email : " + contact.getEmailId());
                System.out.println("PhoneNo : " + contact.getPhoneNumber());
                System.out.println("Address : " + contact.getAddress());
                System.out.println("State : " + contact.getState());
                System.out.println("City : " + contact.getCity());
                System.out.println("Zip : " + contact.getZipCode());
                System.out.println("==========================");
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private List<PersonDetails> getContentOfCsv()
    {
        try
        {
            Reader reader = Files.newBufferedReader(Paths.get(FILE_PATH+CSV_FILE));
            CsvToBean<PersonDetails> csvToBean = new CsvToBeanBuilder<PersonDetails>(reader)
                    .withType(PersonDetails.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Establishing connection with database.
     * @return
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException
    {
        String jdbcURL = "jdbc:mysql://localhost:3306/addressbook_service?useSSL=false";
        String username = "root";
        String password = "Mihir@08";
        System.out.println("Connecting to database"+jdbcURL);
        Connection connection = null;
        connection = DriverManager.getConnection(jdbcURL,username,password);
        System.out.println("Connection successful" + connection);
        return connection;
    }

    /**
     * Reading data from database.
     * @return
     */
    public List<PersonDetails> readFromDataBase()
    {
        String query = "select * from address_book;";
        contactListDB = getQueryResult(query);
        return contactListDB;
    }

    private List<PersonDetails> getQueryResult(String query)
    {
        List<PersonDetails> contacts = new ArrayList<>();
        try(Connection connection = this.getConnection())
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("FirstName");
                String lastname = resultSet.getString("LastName");
                String address = resultSet.getString("Address");
                String city = resultSet.getString("City") ;
                String state= resultSet.getString("State");
                String zip= resultSet.getString("Zip");
                int phoneNumber= resultSet.getInt("Phone");
                String email = resultSet.getString("Email");
                contacts.add(new PersonDetails(id,firstName, lastname, address, city, state, zip, phoneNumber, email));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return contacts;
    }

    public void updateContactInDataBase(String name,int phoneNumber)
    {
        String sql = "update address_book set Phone = "+phoneNumber+" where FirstName = '"+name+"';";
        try(Connection connection = this.getConnection())
        {
            Statement statement = connection.createStatement();
            int rowChanged = statement.executeUpdate(sql);
            if (rowChanged == 1)
            {
                PersonDetails contact = getContactFormList(name);
                contact.setPhoneNumber(phoneNumber);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public boolean checkSyncWithDB(String name)
    {
        return getContactFromDatabase(name).get(0).equals(getContactFormList(name));
    }

    private List<PersonDetails> getContactFromDatabase(String name)
    {
        String sql = "select * from address_book where first = '"+name+"'; ";
        return getQueryResult(sql);
    }

    //search a particular contact in list
    private PersonDetails getContactFormList(String name)
    {
        return contactListDB.stream().filter(contacts->contacts.getFirstName().equals(name)).findFirst().orElse(null);
    }

    public List<PersonDetails> getContatctsAddedAfterdate(String date)
    {
        String query = "Select  * from address_book  Where Date_Added Between cast('"+date+"' as date) and date(now()) ;";
        return getQueryResult(query);
    }

    public HashMap<String, Integer> getContactHaveSameCity()
    {
        HashMap<String, Integer> matches = new HashMap<String, Integer>();
        try(Connection connection = this.getConnection())
        {
            String sql = " select City,count(City) from address_book group by City; ";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next())
            {
                matches.put(resultSet.getString(1),resultSet.getInt(2));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return matches;
    }

    /**
     * Name : displayContacts
     *
     * Description : Displaying all the contact to user.
     *
     * Algorithm : Using for each loop to traverse through ArrayList.
     * and printing records rows one by one.
     *
     * Modification : Fist Commit 14-July-2021
     */
    public void displayContacts()
    {
        for(PersonDetails element : contactList)
        {
            if(element != null)
            {
                System.out.println(element);
            }
        }
    }
}
