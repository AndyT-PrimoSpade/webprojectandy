package org.generation.webproject;

import org.generation.webproject.repository.ItemRepository;
import org.generation.webproject.repository.entity.Item;
import org.generation.webproject.service.ItemService;
import org.generation.webproject.service.ItemServiceMySQL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)     //enables us to ask JUnit to create only one instance of the test class and reuse it between tests.
public class ItemServiceMySQLTest {

    // Mocking is done when you invoke methods of a class that has external communication like database calls or rest calls
    @Mock
    ItemRepository itemRepository;

    ItemService itemService;
    Item itemMock;

    @BeforeAll
    public void setup()
    {
        itemService = new ItemServiceMySQL( itemRepository );
        itemMock = Mockito.mock(Item.class);
    }

    @Test
    public void saveCallsItemsRepositorySave()
    {
        Mockito.reset(itemRepository);
        itemService.save(itemMock);                     //To test
        Mockito.verify(itemRepository).save(itemMock);  //To validate
    }
}
