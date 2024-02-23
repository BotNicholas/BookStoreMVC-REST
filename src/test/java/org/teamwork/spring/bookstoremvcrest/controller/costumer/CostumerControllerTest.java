package org.teamwork.spring.bookstoremvcrest.controller.costumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.teamwork.spring.bookstoremvcrest.controller.CostumerController;
import org.teamwork.spring.bookstoremvcrest.model.Costumer;
import org.teamwork.spring.bookstoremvcrest.model.dto.CostumerDTO;
import org.teamwork.spring.bookstoremvcrest.security.model.BookStoreUser;
import org.teamwork.spring.bookstoremvcrest.service.impl.CostumerServiceImpl;
import org.teamwork.spring.bookstoremvcrest.utils.NotFoundError;
import org.teamwork.spring.bookstoremvcrest.utils.UsedIdError;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.teamwork.spring.bookstoremvcrest.controller.costumer.CostumerControllerTestConstants.URL;

//@AutoConfigureMockMvc //it configures and adds MockMvc to test context, making it available to autowire

//IF you will use @SpringBootTest, you will have to specify real username and password
//IF you will use @WebMvcTest, then false one will be enough

@DisplayName("Unauthorized Costumer Controller Test")
@AutoConfigureMockMvc(addFilters = false)//Configure mockmvc && disable Spring security filters so we do not need @WithMockUser with credentials
//Thus I say, that I do not need authentication

//if it set to true, default security filters will be activated. It is useful, when you want to provide Authentication in SecurityContext throw MockMvc request (it won't pass any Authentication filter, but will pass FilterSecurityInterceptor,
// that ensures Authentication is available in SecurityContext (we pass it with MockMvc))

//@SpringBootTest //Will raise whole Spring context with all the beans
//Also will create bean for roles validation, so if you use it you have to pass @WithMockUser(roles = "ROLE").
//Roles must be the same, as in controller (Real)
//Why? Because full Spring Context starts, and all Security beans and configurations also start. Thus, despite you've
//disabled Security filters, all your spring config will be created and 1)All roles wil be checked correctly (false some won't work)
//        (Authentication will be needed (you will have to be authorized. I.e. authorised access from HttpSecurity))


//@WebMvcTest //If we do not need to raise whole the Spring context we can use this annotation
//It creates contexts only for web layer of the application (Controllers, Advices, etc.)
//If you add Services mocks (annotated with @MockBean), they also will be created (added) in context
//(in @SpringBootTest all the beans will be created, replacing beans that you've annotated in the test with
// @MockBean)
//@ExtendWith(MockitoExtension.class) //is used if you will be using @Mock, @Spy and the others
//from default Mockito. It can be replaced with @BeforeEach method with MockitoAnnotations.initMocks(this)
//By default will put in context all the controllers that my app has.
@WebMvcTest(CostumerController.class)//Here I say, that I need only CostumerController.
//Thus it will raise only web layer of application.
//IF you use @AutoConfigureMockMvc with filters included, you will also need @WithMockUser, but data shan't be real
//HERE @WithMockUser with role is not mandatory, but if needed you can specify any (even false), because it is not SpringBootTest, and it won't create
//all the needed beans for Security and Method validation...

public class CostumerControllerTest {

    @Autowired
    MockMvc mockMvc; //needed to perform MockMvc http requests
    @Autowired
    private ObjectMapper objectMapper; //needed to transform objects into JSON String and vice versa

    @MockBean
    private CostumerServiceImpl costumerService; //creating mock bean for costumer's service

    private CostumerDTO costumerDTO1 = new CostumerDTO("1000000000000", "test costumer1", "test address1", "+37310000000", "example1@gmail.com", List.of(1, 2, 3));
    private CostumerDTO costumerDTO2 = new CostumerDTO("2000000000000", "test costumer2", "test address2", "+37320000000", "example2@gmail.com", List.of(4, 5, 6));
    private CostumerDTO costumerDTO3 = new CostumerDTO("3000000000000", "test costumer3", "test address3", "+37330000000", "example3@gmail.com", List.of(7, 8, 9));


    Costumer costumer = new Costumer("1000000000000", "test costumer1", "test address1", "+37310000000", "example1@gmail.com");
    BookStoreUser user = new BookStoreUser("test", "test", "ROLE_USER", costumer);
    @Test
    @DisplayName("find all costumers test")
//    @WithMockUser(roles = "ADMIN") //Or mockMvc.perform(get("/**").with(user("admin").roles("USER","ADMIN")))... instead
//    @WithMockUser(username = "test", password = "test") // For @AutoConfigureMockMvc
    public void findAllCostumersTest() throws Exception {
        List<CostumerDTO> costumerDTOS = List.of(costumerDTO1, costumerDTO2);
//        Mockito.when()...
        when(costumerService.findAll()).thenReturn(costumerDTOS);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(URL)); //named as ResultActions, because we will perform some actions on the result

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(costumerDTOS)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("find 1'st costumer test")
    public void findCostumerByIdTest() throws Exception {
        Mockito.when(costumerService.findByKey(1)).thenReturn(costumerDTO1);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(URL + "/1"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(costumerDTO1)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("find non-existent costumer test")
    public void findNonExistentCostumer() throws Exception {
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(URL + "/777"));

        actions.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(new NotFoundError("Such object was not found!"))))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("save new costumer without id test")
    public void saveCostumer() throws Exception {
        Mockito.when(costumerService.save(costumerDTO3)).thenReturn(costumerDTO3);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8) //just needed to specify the request body encoding. Helps to avoid problems with encoding
                .content(objectMapper.writeValueAsString(costumerDTO3)));

        result.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Save success!"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("save new costumer with id test")
    public void saveCostumerWithId() throws Exception {
        costumerDTO3.setId(1);
        Mockito.when(costumerService.save(costumerDTO3)).thenReturn(costumerDTO3);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(costumerDTO3)));

        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(new UsedIdError("Id is unexpected here!!!"))))
                .andDo(MockMvcResultHandlers.print());

        costumerDTO3.setId(null);
    }

    @Test
    @DisplayName("update 1'st costumer test")
    public void updateCostumer() throws Exception {
        Mockito.when(costumerService.update(1, costumerDTO1)).thenReturn(costumerDTO1);
        //in service we change object's id, but here we test just controllers, and changing id logic is specified in service.
        //so we do not need to test service's logic. Thus we do not expect object with changes
        //Thus we will make sure that controller works correct (took users's input, called update method from service, and returned response)

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch(URL+"/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(costumerDTO1)));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Update successful!"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("delete 1'st costumer test")
    public void deleteCostumer() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete(URL+"/1"));

        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().string("Delete successful!"))
                .andDo(MockMvcResultHandlers.print());
    }
}
