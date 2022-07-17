package com.relay42.sensor.integration;

import com.relay42.sensor.SensorApplication;
import com.relay42.sensor.domain.EvenMessage;
import com.relay42.sensor.repository.EvenMessageRepository;
import com.relay42.sensor.service.dto.SensorResult;
import com.relay42.sensor.service.enumeration.Operation;
import com.relay42.sensor.service.mapper.EvenMessageMapper;
import com.relay42.sensor.util.AbstractCassandraTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the {@link EvenMessageResourceIntegrationTest} REST controller.
 */

@SpringBootTest(classes = SensorApplication.class)
@ImportAutoConfiguration(exclude = ReactiveSecurityAutoConfiguration.class)
@AutoConfigureWebTestClient
@WithMockUser
class EvenMessageResourceIntegrationTest extends AbstractCassandraTest {

    private static final String INSTANCEONE_UID = "AAAAAAAAAA";
    private static final String INSTANCETWO_UID = "BBBBBBBBBB";

    private static final BigDecimal INSTANCEONE_VALUE = new BigDecimal(1);
    private static final BigDecimal INSTANCETWO_VALUE = new BigDecimal(2);

    private static final Date INSTANCEONE_TIMESTAMP_DATE = new Date();
    private static final Date INSTANCETWO_TIMESTAMP_DATE = new Date();

    private static final String INSTANCEONE_TYPE = "AAAAAAAAAA";
    private static final String INSTANCETWO_TYPE = "BBBBBBBBBB";

    private static final String INSTANCEONE_NAME = "AAAAAAAAAA";
    private static final String INSTANCETWO_NAME = "BBBBBBBBBB";

    private static final Long INSTANCEONE_CLUSTER_ID = 1L;
    private static final Long INSTANCETWO_CLUSTER_ID = 2L;

    private static final Long INSTANCEONE_OFFSET = 1L;
    private static final Long INSTANCETWO_OFFSET = 2L;

    private static final Long INSTANCEONE_PARTITION_ID = 1L;
    private static final Long INSTANCETWO_PARTITION_ID = 2L;

    private static final String INSTANCEONE_TOPIC = "AAAAAAAAAA";
    private static final String INSTANCETWO_TOPIC = "BBBBBBBBBB";

    private static final String INSTANCEONE_GROUP_ID = "AAAAAAAAAA";
    private static final String INSTANCETWO_GROUP_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/consumer-api/api/even-messages";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EvenMessageRepository evenMessageRepository;

    @Autowired
    private EvenMessageMapper evenMessageMapper;

    @Autowired
    private WebTestClient webTestClient;

    private EvenMessage evenFirstMessage;
    private EvenMessage evenSecondMessage;

    /**
     * Create first entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvenMessage createFirstEntity() {
        EvenMessage evenMessage = EvenMessage.builder()
                .uid(INSTANCEONE_UID)
                .value(INSTANCEONE_VALUE)
                .timestampDate(INSTANCEONE_TIMESTAMP_DATE)
                .type(INSTANCEONE_TYPE)
                .name(INSTANCEONE_NAME)
                .clusterId(INSTANCEONE_CLUSTER_ID)
                .offset(INSTANCEONE_OFFSET)
                .partitionId(INSTANCEONE_PARTITION_ID)
                .topic(INSTANCEONE_TOPIC)
                .groupId(INSTANCEONE_GROUP_ID).build();
        return evenMessage;
    }

    /**
     * Create second entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvenMessage createSecondEntity() {
        EvenMessage evenMessage = EvenMessage.builder()
                .uid(INSTANCETWO_UID)
                .value(INSTANCETWO_VALUE)
                .timestampDate(INSTANCETWO_TIMESTAMP_DATE)
                .type(INSTANCETWO_TYPE)
                .name(INSTANCETWO_NAME)
                .clusterId(INSTANCETWO_CLUSTER_ID)
                .offset(INSTANCETWO_OFFSET)
                .partitionId(INSTANCETWO_PARTITION_ID)
                .topic(INSTANCETWO_TOPIC)
                .groupId(INSTANCETWO_GROUP_ID).build();
        return evenMessage;
    }

    @BeforeEach
    public void initTest() {
        evenMessageRepository.deleteAll().block();
        evenFirstMessage = createFirstEntity();
        evenSecondMessage = createSecondEntity();
    }

    @Test
    void get_Min_value_from_event_controller() {
        // Initialize the database
        evenMessageRepository.save(evenFirstMessage).block();
        evenMessageRepository.save(evenSecondMessage).block();

        List<SensorResult> evenMessageList = webTestClient
                .get()
                .uri(ENTITY_API_URL + "/" + Operation.MIN + "?fromDate=" + LocalDate.now().minusDays(1).toString() + "&toDate=" + LocalDate.now().plusDays(1).toString())
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .returnResult(SensorResult.class)
                .getResponseBody()
                .collectList()
                .block(Duration.ofSeconds(5));
        assertThat(evenMessageList).isNotNull();
        assertThat(evenMessageList).hasSize(1);
        SensorResult testEvenMessage = evenMessageList.get(0);
        assertThat(testEvenMessage.getOperation()).isEqualTo(Operation.MIN);
        assertThat(testEvenMessage.getResultCount()).isEqualByComparingTo(2);
        assertThat(testEvenMessage.getResult()).isEqualTo(INSTANCEONE_VALUE);
    }

    @Test
    void get_Max_value_from_event_controller() {
        // Initialize the database
        evenMessageRepository.save(evenFirstMessage).block();
        evenMessageRepository.save(evenSecondMessage).block();

        List<SensorResult> evenMessageList = webTestClient
                .get()
                .uri(ENTITY_API_URL + "/" +
                        Operation.MAX +
                        "?fromDate=" +
                        LocalDate.now().minusDays(1).toString() +
                        "&toDate=" + LocalDate.now().plusDays(1).toString())
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .returnResult(SensorResult.class)
                .getResponseBody()
                .collectList()
                .block(Duration.ofSeconds(5));
        assertThat(evenMessageList).isNotNull();
        assertThat(evenMessageList).hasSize(1);
        SensorResult testEvenMessage = evenMessageList.get(0);
        assertThat(testEvenMessage.getOperation()).isEqualTo(Operation.MAX);
        assertThat(testEvenMessage.getResultCount()).isEqualByComparingTo(2);
        assertThat(testEvenMessage.getResult()).isEqualTo(INSTANCETWO_VALUE);
    }

    @Test
    void get_Average_value_from_event_controller() {
        // Initialize the database
        evenMessageRepository.save(evenFirstMessage).block();
        evenMessageRepository.save(evenSecondMessage).block();

        List<SensorResult> evenMessageList = webTestClient
                .get()
                .uri(ENTITY_API_URL + "/" + Operation.AVERAGE + "?fromDate=" + LocalDate.now().minusDays(1).toString() + "&toDate=" + LocalDate.now().plusDays(1).toString())
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .returnResult(SensorResult.class)
                .getResponseBody()
                .collectList()
                .block(Duration.ofSeconds(5));
        assertThat(evenMessageList).isNotNull();
        assertThat(evenMessageList).hasSize(1);
        SensorResult testEvenMessage = evenMessageList.get(0);
        assertThat(testEvenMessage.getOperation()).isEqualTo(Operation.AVERAGE);
        assertThat(testEvenMessage.getResultCount()).isEqualByComparingTo(2);
        assertThat(testEvenMessage.getResult()).isEqualTo(new BigDecimal(1.5));
    }

    @Test
    void get_Median_value_from_event_controller() {
        // Initialize the database
        evenMessageRepository.save(evenFirstMessage).block();
        evenMessageRepository.save(evenSecondMessage).block();

        List<SensorResult> evenMessageList = webTestClient
                .get()
                .uri(ENTITY_API_URL + "/" +
                        Operation.MEDIAN +
                        "?fromDate=" +
                        LocalDate.now().minusDays(1).toString() +
                        "&toDate=" + LocalDate.now().plusDays(1).toString())
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .returnResult(SensorResult.class)
                .getResponseBody()
                .collectList()
                .block(Duration.ofSeconds(5));
        assertThat(evenMessageList).isNotNull();
        assertThat(evenMessageList).hasSize(1);
        SensorResult testEvenMessage = evenMessageList.get(0);
        assertThat(testEvenMessage.getOperation()).isEqualTo(Operation.MEDIAN);
        assertThat(testEvenMessage.getResultCount()).isEqualByComparingTo(2);
        assertThat(testEvenMessage.getResult()).isEqualTo(new BigDecimal(2.5));
    }
}
