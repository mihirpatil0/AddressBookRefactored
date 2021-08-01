package com.bridgelab.addressbooktest;

import com.bridgelab.addressbooksystemusingcollections.AddressBookService;
import com.bridgelab.addressbooksystemusingcollections.PersonDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AddressBookTesting
{
    @Test
    public void givenReadFromDB_ShouldReturnListOfContacts()
    {
        AddressBookService addressBookService = new AddressBookService();
        List<PersonDetails> contactList  = addressBookService.readFromDataBase();
        Assertions.assertEquals(4, contactList.size());
    }
}
