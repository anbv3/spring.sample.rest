package spring.sample.rest.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import spring.sample.rest.SpringSampleRestApplication;
import spring.sample.rest.configs.AppConfig;
import spring.sample.rest.domains.Room;
import spring.sample.rest.repositories.RoomRepository;

import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {SpringSampleRestApplication.class, AppConfig.class})
@WebAppConfiguration
@Transactional
public class RoomControllerTest {
    private static final Logger LOG = LoggerFactory.getLogger(RoomControllerTest.class);

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    private RoomRepository romRoomRepository;

    private MockMvc mockMvc;
    private Room room;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        this.room = Room.create(RandomStringUtils.randomAlphanumeric(10).toLowerCase());
        romRoomRepository.save(this.room);

        // check the json circular reference error
        LOG.debug("JSON: {}", new ObjectMapper().writeValueAsString(this.room));
    }

    @Test
    public void testFindAll() throws Exception {
        // when
        String url = "/api/v1/rooms";
        ResultActions result = this.mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON)
                                                            .contentType(MediaType.APPLICATION_JSON));


        // then
        result.andDo(print());
        result.andExpect(status().isOk());

        result.andExpect(jsonPath("$").exists());
        result.andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testFindById() throws Exception {
        // when
        String url = "/api/v1/rooms/" + this.room.getId();
        ResultActions result = this.mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON)
                                                            .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andDo(print());
        result.andExpect(status().isOk());

        result.andExpect(jsonPath("$").exists());
        result.andExpect(jsonPath("$.name", is(this.room.getName())));
    }

    @Test
    public void testCreate() throws Exception {
        // given
        Room room2 = Room.create(RandomStringUtils.randomAlphanumeric(10).toLowerCase());

        // when
        String url = "/api/v1/rooms";
        ResultActions result = this.mockMvc.perform(post(url).content(new ObjectMapper().writeValueAsString(room2))
                                                             .accept(MediaType.APPLICATION_JSON)
                                                             .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andDo(print());
        result.andExpect(status().isCreated());
    }

    @Test
    public void testUpdate() throws Exception {
        // given
        Room room2 = Room.create(RandomStringUtils.randomAlphanumeric(10).toLowerCase());

        // when
        String url = "/api/v1/rooms/" + this.room.getId();
        ResultActions result = this.mockMvc.perform(patch(url).content(new ObjectMapper().writeValueAsString(room2))
                                                              .accept(MediaType.APPLICATION_JSON)
                                                              .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andDo(print());
        result.andExpect(status().isNoContent());
    }

    @Test
    public void testDelete() throws Exception {
        // when
        String url = "/api/v1/rooms/" + this.room.getId();
        ResultActions result = this.mockMvc.perform(delete(url).accept(MediaType.APPLICATION_JSON)
                                                               .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andDo(print());
        result.andExpect(status().isNoContent());
    }

}