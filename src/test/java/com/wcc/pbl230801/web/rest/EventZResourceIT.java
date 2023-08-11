package com.wcc.pbl230801.web.rest;

import static com.wcc.pbl230801.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.wcc.pbl230801.IntegrationTest;
import com.wcc.pbl230801.domain.EventZ;
import com.wcc.pbl230801.repository.EventZRepository;
import com.wcc.pbl230801.service.criteria.EventZCriteria;
import com.wcc.pbl230801.service.dto.EventZDTO;
import com.wcc.pbl230801.service.mapper.EventZMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EventZResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventZResourceIT {

    private static final String DEFAULT_EVNT_NM = "AAAAAAAAAA";
    private static final String UPDATED_EVNT_NM = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EVNT_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EVNT_DT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_EVNT_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_VENUE = "AAAAAAAAAA";
    private static final String UPDATED_VENUE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EVENT_BEG_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EVENT_BEG_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_EVENT_BEG_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_EVENT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EVENT_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_EVENT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_LST_MTN_USR = "AAAAAAAAAA";
    private static final String UPDATED_LST_MTN_USR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LST_MTN_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LST_MTN_DT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LST_MTN_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/event-zs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventZRepository eventZRepository;

    @Autowired
    private EventZMapper eventZMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventZMockMvc;

    private EventZ eventZ;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventZ createEntity(EntityManager em) {
        EventZ eventZ = new EventZ()
            .evntNm(DEFAULT_EVNT_NM)
            .evntDt(DEFAULT_EVNT_DT)
            .venue(DEFAULT_VENUE)
            .eventBegTime(DEFAULT_EVENT_BEG_TIME)
            .eventEndTime(DEFAULT_EVENT_END_TIME)
            .lstMtnUsr(DEFAULT_LST_MTN_USR)
            .lstMtnDt(DEFAULT_LST_MTN_DT);
        return eventZ;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventZ createUpdatedEntity(EntityManager em) {
        EventZ eventZ = new EventZ()
            .evntNm(UPDATED_EVNT_NM)
            .evntDt(UPDATED_EVNT_DT)
            .venue(UPDATED_VENUE)
            .eventBegTime(UPDATED_EVENT_BEG_TIME)
            .eventEndTime(UPDATED_EVENT_END_TIME)
            .lstMtnUsr(UPDATED_LST_MTN_USR)
            .lstMtnDt(UPDATED_LST_MTN_DT);
        return eventZ;
    }

    @BeforeEach
    public void initTest() {
        eventZ = createEntity(em);
    }

    @Test
    @Transactional
    void createEventZ() throws Exception {
        int databaseSizeBeforeCreate = eventZRepository.findAll().size();
        // Create the EventZ
        EventZDTO eventZDTO = eventZMapper.toDto(eventZ);
        restEventZMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventZDTO)))
            .andExpect(status().isCreated());

        // Validate the EventZ in the database
        List<EventZ> eventZList = eventZRepository.findAll();
        assertThat(eventZList).hasSize(databaseSizeBeforeCreate + 1);
        EventZ testEventZ = eventZList.get(eventZList.size() - 1);
        assertThat(testEventZ.getEvntNm()).isEqualTo(DEFAULT_EVNT_NM);
        assertThat(testEventZ.getEvntDt()).isEqualTo(DEFAULT_EVNT_DT);
        assertThat(testEventZ.getVenue()).isEqualTo(DEFAULT_VENUE);
        assertThat(testEventZ.getEventBegTime()).isEqualTo(DEFAULT_EVENT_BEG_TIME);
        assertThat(testEventZ.getEventEndTime()).isEqualTo(DEFAULT_EVENT_END_TIME);
        assertThat(testEventZ.getLstMtnUsr()).isEqualTo(DEFAULT_LST_MTN_USR);
        assertThat(testEventZ.getLstMtnDt()).isEqualTo(DEFAULT_LST_MTN_DT);
    }

    @Test
    @Transactional
    void createEventZWithExistingId() throws Exception {
        // Create the EventZ with an existing ID
        eventZ.setId(1L);
        EventZDTO eventZDTO = eventZMapper.toDto(eventZ);

        int databaseSizeBeforeCreate = eventZRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventZMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventZDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventZ in the database
        List<EventZ> eventZList = eventZRepository.findAll();
        assertThat(eventZList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEventZS() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList
        restEventZMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventZ.getId().intValue())))
            .andExpect(jsonPath("$.[*].evntNm").value(hasItem(DEFAULT_EVNT_NM)))
            .andExpect(jsonPath("$.[*].evntDt").value(hasItem(sameInstant(DEFAULT_EVNT_DT))))
            .andExpect(jsonPath("$.[*].venue").value(hasItem(DEFAULT_VENUE)))
            .andExpect(jsonPath("$.[*].eventBegTime").value(hasItem(sameInstant(DEFAULT_EVENT_BEG_TIME))))
            .andExpect(jsonPath("$.[*].eventEndTime").value(hasItem(sameInstant(DEFAULT_EVENT_END_TIME))))
            .andExpect(jsonPath("$.[*].lstMtnUsr").value(hasItem(DEFAULT_LST_MTN_USR)))
            .andExpect(jsonPath("$.[*].lstMtnDt").value(hasItem(sameInstant(DEFAULT_LST_MTN_DT))));
    }

    @Test
    @Transactional
    void getEventZ() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get the eventZ
        restEventZMockMvc
            .perform(get(ENTITY_API_URL_ID, eventZ.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventZ.getId().intValue()))
            .andExpect(jsonPath("$.evntNm").value(DEFAULT_EVNT_NM))
            .andExpect(jsonPath("$.evntDt").value(sameInstant(DEFAULT_EVNT_DT)))
            .andExpect(jsonPath("$.venue").value(DEFAULT_VENUE))
            .andExpect(jsonPath("$.eventBegTime").value(sameInstant(DEFAULT_EVENT_BEG_TIME)))
            .andExpect(jsonPath("$.eventEndTime").value(sameInstant(DEFAULT_EVENT_END_TIME)))
            .andExpect(jsonPath("$.lstMtnUsr").value(DEFAULT_LST_MTN_USR))
            .andExpect(jsonPath("$.lstMtnDt").value(sameInstant(DEFAULT_LST_MTN_DT)));
    }

    @Test
    @Transactional
    void getEventZSByIdFiltering() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        Long id = eventZ.getId();

        defaultEventZShouldBeFound("id.equals=" + id);
        defaultEventZShouldNotBeFound("id.notEquals=" + id);

        defaultEventZShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEventZShouldNotBeFound("id.greaterThan=" + id);

        defaultEventZShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEventZShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEventZSByEvntNmIsEqualToSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where evntNm equals to DEFAULT_EVNT_NM
        defaultEventZShouldBeFound("evntNm.equals=" + DEFAULT_EVNT_NM);

        // Get all the eventZList where evntNm equals to UPDATED_EVNT_NM
        defaultEventZShouldNotBeFound("evntNm.equals=" + UPDATED_EVNT_NM);
    }

    @Test
    @Transactional
    void getAllEventZSByEvntNmIsInShouldWork() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where evntNm in DEFAULT_EVNT_NM or UPDATED_EVNT_NM
        defaultEventZShouldBeFound("evntNm.in=" + DEFAULT_EVNT_NM + "," + UPDATED_EVNT_NM);

        // Get all the eventZList where evntNm equals to UPDATED_EVNT_NM
        defaultEventZShouldNotBeFound("evntNm.in=" + UPDATED_EVNT_NM);
    }

    @Test
    @Transactional
    void getAllEventZSByEvntNmIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where evntNm is not null
        defaultEventZShouldBeFound("evntNm.specified=true");

        // Get all the eventZList where evntNm is null
        defaultEventZShouldNotBeFound("evntNm.specified=false");
    }

    @Test
    @Transactional
    void getAllEventZSByEvntNmContainsSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where evntNm contains DEFAULT_EVNT_NM
        defaultEventZShouldBeFound("evntNm.contains=" + DEFAULT_EVNT_NM);

        // Get all the eventZList where evntNm contains UPDATED_EVNT_NM
        defaultEventZShouldNotBeFound("evntNm.contains=" + UPDATED_EVNT_NM);
    }

    @Test
    @Transactional
    void getAllEventZSByEvntNmNotContainsSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where evntNm does not contain DEFAULT_EVNT_NM
        defaultEventZShouldNotBeFound("evntNm.doesNotContain=" + DEFAULT_EVNT_NM);

        // Get all the eventZList where evntNm does not contain UPDATED_EVNT_NM
        defaultEventZShouldBeFound("evntNm.doesNotContain=" + UPDATED_EVNT_NM);
    }

    @Test
    @Transactional
    void getAllEventZSByEvntDtIsEqualToSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where evntDt equals to DEFAULT_EVNT_DT
        defaultEventZShouldBeFound("evntDt.equals=" + DEFAULT_EVNT_DT);

        // Get all the eventZList where evntDt equals to UPDATED_EVNT_DT
        defaultEventZShouldNotBeFound("evntDt.equals=" + UPDATED_EVNT_DT);
    }

    @Test
    @Transactional
    void getAllEventZSByEvntDtIsInShouldWork() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where evntDt in DEFAULT_EVNT_DT or UPDATED_EVNT_DT
        defaultEventZShouldBeFound("evntDt.in=" + DEFAULT_EVNT_DT + "," + UPDATED_EVNT_DT);

        // Get all the eventZList where evntDt equals to UPDATED_EVNT_DT
        defaultEventZShouldNotBeFound("evntDt.in=" + UPDATED_EVNT_DT);
    }

    @Test
    @Transactional
    void getAllEventZSByEvntDtIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where evntDt is not null
        defaultEventZShouldBeFound("evntDt.specified=true");

        // Get all the eventZList where evntDt is null
        defaultEventZShouldNotBeFound("evntDt.specified=false");
    }

    @Test
    @Transactional
    void getAllEventZSByEvntDtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where evntDt is greater than or equal to DEFAULT_EVNT_DT
        defaultEventZShouldBeFound("evntDt.greaterThanOrEqual=" + DEFAULT_EVNT_DT);

        // Get all the eventZList where evntDt is greater than or equal to UPDATED_EVNT_DT
        defaultEventZShouldNotBeFound("evntDt.greaterThanOrEqual=" + UPDATED_EVNT_DT);
    }

    @Test
    @Transactional
    void getAllEventZSByEvntDtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where evntDt is less than or equal to DEFAULT_EVNT_DT
        defaultEventZShouldBeFound("evntDt.lessThanOrEqual=" + DEFAULT_EVNT_DT);

        // Get all the eventZList where evntDt is less than or equal to SMALLER_EVNT_DT
        defaultEventZShouldNotBeFound("evntDt.lessThanOrEqual=" + SMALLER_EVNT_DT);
    }

    @Test
    @Transactional
    void getAllEventZSByEvntDtIsLessThanSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where evntDt is less than DEFAULT_EVNT_DT
        defaultEventZShouldNotBeFound("evntDt.lessThan=" + DEFAULT_EVNT_DT);

        // Get all the eventZList where evntDt is less than UPDATED_EVNT_DT
        defaultEventZShouldBeFound("evntDt.lessThan=" + UPDATED_EVNT_DT);
    }

    @Test
    @Transactional
    void getAllEventZSByEvntDtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where evntDt is greater than DEFAULT_EVNT_DT
        defaultEventZShouldNotBeFound("evntDt.greaterThan=" + DEFAULT_EVNT_DT);

        // Get all the eventZList where evntDt is greater than SMALLER_EVNT_DT
        defaultEventZShouldBeFound("evntDt.greaterThan=" + SMALLER_EVNT_DT);
    }

    @Test
    @Transactional
    void getAllEventZSByVenueIsEqualToSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where venue equals to DEFAULT_VENUE
        defaultEventZShouldBeFound("venue.equals=" + DEFAULT_VENUE);

        // Get all the eventZList where venue equals to UPDATED_VENUE
        defaultEventZShouldNotBeFound("venue.equals=" + UPDATED_VENUE);
    }

    @Test
    @Transactional
    void getAllEventZSByVenueIsInShouldWork() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where venue in DEFAULT_VENUE or UPDATED_VENUE
        defaultEventZShouldBeFound("venue.in=" + DEFAULT_VENUE + "," + UPDATED_VENUE);

        // Get all the eventZList where venue equals to UPDATED_VENUE
        defaultEventZShouldNotBeFound("venue.in=" + UPDATED_VENUE);
    }

    @Test
    @Transactional
    void getAllEventZSByVenueIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where venue is not null
        defaultEventZShouldBeFound("venue.specified=true");

        // Get all the eventZList where venue is null
        defaultEventZShouldNotBeFound("venue.specified=false");
    }

    @Test
    @Transactional
    void getAllEventZSByVenueContainsSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where venue contains DEFAULT_VENUE
        defaultEventZShouldBeFound("venue.contains=" + DEFAULT_VENUE);

        // Get all the eventZList where venue contains UPDATED_VENUE
        defaultEventZShouldNotBeFound("venue.contains=" + UPDATED_VENUE);
    }

    @Test
    @Transactional
    void getAllEventZSByVenueNotContainsSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where venue does not contain DEFAULT_VENUE
        defaultEventZShouldNotBeFound("venue.doesNotContain=" + DEFAULT_VENUE);

        // Get all the eventZList where venue does not contain UPDATED_VENUE
        defaultEventZShouldBeFound("venue.doesNotContain=" + UPDATED_VENUE);
    }

    @Test
    @Transactional
    void getAllEventZSByEventBegTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where eventBegTime equals to DEFAULT_EVENT_BEG_TIME
        defaultEventZShouldBeFound("eventBegTime.equals=" + DEFAULT_EVENT_BEG_TIME);

        // Get all the eventZList where eventBegTime equals to UPDATED_EVENT_BEG_TIME
        defaultEventZShouldNotBeFound("eventBegTime.equals=" + UPDATED_EVENT_BEG_TIME);
    }

    @Test
    @Transactional
    void getAllEventZSByEventBegTimeIsInShouldWork() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where eventBegTime in DEFAULT_EVENT_BEG_TIME or UPDATED_EVENT_BEG_TIME
        defaultEventZShouldBeFound("eventBegTime.in=" + DEFAULT_EVENT_BEG_TIME + "," + UPDATED_EVENT_BEG_TIME);

        // Get all the eventZList where eventBegTime equals to UPDATED_EVENT_BEG_TIME
        defaultEventZShouldNotBeFound("eventBegTime.in=" + UPDATED_EVENT_BEG_TIME);
    }

    @Test
    @Transactional
    void getAllEventZSByEventBegTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where eventBegTime is not null
        defaultEventZShouldBeFound("eventBegTime.specified=true");

        // Get all the eventZList where eventBegTime is null
        defaultEventZShouldNotBeFound("eventBegTime.specified=false");
    }

    @Test
    @Transactional
    void getAllEventZSByEventBegTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where eventBegTime is greater than or equal to DEFAULT_EVENT_BEG_TIME
        defaultEventZShouldBeFound("eventBegTime.greaterThanOrEqual=" + DEFAULT_EVENT_BEG_TIME);

        // Get all the eventZList where eventBegTime is greater than or equal to UPDATED_EVENT_BEG_TIME
        defaultEventZShouldNotBeFound("eventBegTime.greaterThanOrEqual=" + UPDATED_EVENT_BEG_TIME);
    }

    @Test
    @Transactional
    void getAllEventZSByEventBegTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where eventBegTime is less than or equal to DEFAULT_EVENT_BEG_TIME
        defaultEventZShouldBeFound("eventBegTime.lessThanOrEqual=" + DEFAULT_EVENT_BEG_TIME);

        // Get all the eventZList where eventBegTime is less than or equal to SMALLER_EVENT_BEG_TIME
        defaultEventZShouldNotBeFound("eventBegTime.lessThanOrEqual=" + SMALLER_EVENT_BEG_TIME);
    }

    @Test
    @Transactional
    void getAllEventZSByEventBegTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where eventBegTime is less than DEFAULT_EVENT_BEG_TIME
        defaultEventZShouldNotBeFound("eventBegTime.lessThan=" + DEFAULT_EVENT_BEG_TIME);

        // Get all the eventZList where eventBegTime is less than UPDATED_EVENT_BEG_TIME
        defaultEventZShouldBeFound("eventBegTime.lessThan=" + UPDATED_EVENT_BEG_TIME);
    }

    @Test
    @Transactional
    void getAllEventZSByEventBegTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where eventBegTime is greater than DEFAULT_EVENT_BEG_TIME
        defaultEventZShouldNotBeFound("eventBegTime.greaterThan=" + DEFAULT_EVENT_BEG_TIME);

        // Get all the eventZList where eventBegTime is greater than SMALLER_EVENT_BEG_TIME
        defaultEventZShouldBeFound("eventBegTime.greaterThan=" + SMALLER_EVENT_BEG_TIME);
    }

    @Test
    @Transactional
    void getAllEventZSByEventEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where eventEndTime equals to DEFAULT_EVENT_END_TIME
        defaultEventZShouldBeFound("eventEndTime.equals=" + DEFAULT_EVENT_END_TIME);

        // Get all the eventZList where eventEndTime equals to UPDATED_EVENT_END_TIME
        defaultEventZShouldNotBeFound("eventEndTime.equals=" + UPDATED_EVENT_END_TIME);
    }

    @Test
    @Transactional
    void getAllEventZSByEventEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where eventEndTime in DEFAULT_EVENT_END_TIME or UPDATED_EVENT_END_TIME
        defaultEventZShouldBeFound("eventEndTime.in=" + DEFAULT_EVENT_END_TIME + "," + UPDATED_EVENT_END_TIME);

        // Get all the eventZList where eventEndTime equals to UPDATED_EVENT_END_TIME
        defaultEventZShouldNotBeFound("eventEndTime.in=" + UPDATED_EVENT_END_TIME);
    }

    @Test
    @Transactional
    void getAllEventZSByEventEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where eventEndTime is not null
        defaultEventZShouldBeFound("eventEndTime.specified=true");

        // Get all the eventZList where eventEndTime is null
        defaultEventZShouldNotBeFound("eventEndTime.specified=false");
    }

    @Test
    @Transactional
    void getAllEventZSByEventEndTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where eventEndTime is greater than or equal to DEFAULT_EVENT_END_TIME
        defaultEventZShouldBeFound("eventEndTime.greaterThanOrEqual=" + DEFAULT_EVENT_END_TIME);

        // Get all the eventZList where eventEndTime is greater than or equal to UPDATED_EVENT_END_TIME
        defaultEventZShouldNotBeFound("eventEndTime.greaterThanOrEqual=" + UPDATED_EVENT_END_TIME);
    }

    @Test
    @Transactional
    void getAllEventZSByEventEndTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where eventEndTime is less than or equal to DEFAULT_EVENT_END_TIME
        defaultEventZShouldBeFound("eventEndTime.lessThanOrEqual=" + DEFAULT_EVENT_END_TIME);

        // Get all the eventZList where eventEndTime is less than or equal to SMALLER_EVENT_END_TIME
        defaultEventZShouldNotBeFound("eventEndTime.lessThanOrEqual=" + SMALLER_EVENT_END_TIME);
    }

    @Test
    @Transactional
    void getAllEventZSByEventEndTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where eventEndTime is less than DEFAULT_EVENT_END_TIME
        defaultEventZShouldNotBeFound("eventEndTime.lessThan=" + DEFAULT_EVENT_END_TIME);

        // Get all the eventZList where eventEndTime is less than UPDATED_EVENT_END_TIME
        defaultEventZShouldBeFound("eventEndTime.lessThan=" + UPDATED_EVENT_END_TIME);
    }

    @Test
    @Transactional
    void getAllEventZSByEventEndTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where eventEndTime is greater than DEFAULT_EVENT_END_TIME
        defaultEventZShouldNotBeFound("eventEndTime.greaterThan=" + DEFAULT_EVENT_END_TIME);

        // Get all the eventZList where eventEndTime is greater than SMALLER_EVENT_END_TIME
        defaultEventZShouldBeFound("eventEndTime.greaterThan=" + SMALLER_EVENT_END_TIME);
    }

    @Test
    @Transactional
    void getAllEventZSByLstMtnUsrIsEqualToSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where lstMtnUsr equals to DEFAULT_LST_MTN_USR
        defaultEventZShouldBeFound("lstMtnUsr.equals=" + DEFAULT_LST_MTN_USR);

        // Get all the eventZList where lstMtnUsr equals to UPDATED_LST_MTN_USR
        defaultEventZShouldNotBeFound("lstMtnUsr.equals=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllEventZSByLstMtnUsrIsInShouldWork() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where lstMtnUsr in DEFAULT_LST_MTN_USR or UPDATED_LST_MTN_USR
        defaultEventZShouldBeFound("lstMtnUsr.in=" + DEFAULT_LST_MTN_USR + "," + UPDATED_LST_MTN_USR);

        // Get all the eventZList where lstMtnUsr equals to UPDATED_LST_MTN_USR
        defaultEventZShouldNotBeFound("lstMtnUsr.in=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllEventZSByLstMtnUsrIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where lstMtnUsr is not null
        defaultEventZShouldBeFound("lstMtnUsr.specified=true");

        // Get all the eventZList where lstMtnUsr is null
        defaultEventZShouldNotBeFound("lstMtnUsr.specified=false");
    }

    @Test
    @Transactional
    void getAllEventZSByLstMtnUsrContainsSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where lstMtnUsr contains DEFAULT_LST_MTN_USR
        defaultEventZShouldBeFound("lstMtnUsr.contains=" + DEFAULT_LST_MTN_USR);

        // Get all the eventZList where lstMtnUsr contains UPDATED_LST_MTN_USR
        defaultEventZShouldNotBeFound("lstMtnUsr.contains=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllEventZSByLstMtnUsrNotContainsSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where lstMtnUsr does not contain DEFAULT_LST_MTN_USR
        defaultEventZShouldNotBeFound("lstMtnUsr.doesNotContain=" + DEFAULT_LST_MTN_USR);

        // Get all the eventZList where lstMtnUsr does not contain UPDATED_LST_MTN_USR
        defaultEventZShouldBeFound("lstMtnUsr.doesNotContain=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllEventZSByLstMtnDtIsEqualToSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where lstMtnDt equals to DEFAULT_LST_MTN_DT
        defaultEventZShouldBeFound("lstMtnDt.equals=" + DEFAULT_LST_MTN_DT);

        // Get all the eventZList where lstMtnDt equals to UPDATED_LST_MTN_DT
        defaultEventZShouldNotBeFound("lstMtnDt.equals=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllEventZSByLstMtnDtIsInShouldWork() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where lstMtnDt in DEFAULT_LST_MTN_DT or UPDATED_LST_MTN_DT
        defaultEventZShouldBeFound("lstMtnDt.in=" + DEFAULT_LST_MTN_DT + "," + UPDATED_LST_MTN_DT);

        // Get all the eventZList where lstMtnDt equals to UPDATED_LST_MTN_DT
        defaultEventZShouldNotBeFound("lstMtnDt.in=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllEventZSByLstMtnDtIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where lstMtnDt is not null
        defaultEventZShouldBeFound("lstMtnDt.specified=true");

        // Get all the eventZList where lstMtnDt is null
        defaultEventZShouldNotBeFound("lstMtnDt.specified=false");
    }

    @Test
    @Transactional
    void getAllEventZSByLstMtnDtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where lstMtnDt is greater than or equal to DEFAULT_LST_MTN_DT
        defaultEventZShouldBeFound("lstMtnDt.greaterThanOrEqual=" + DEFAULT_LST_MTN_DT);

        // Get all the eventZList where lstMtnDt is greater than or equal to UPDATED_LST_MTN_DT
        defaultEventZShouldNotBeFound("lstMtnDt.greaterThanOrEqual=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllEventZSByLstMtnDtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where lstMtnDt is less than or equal to DEFAULT_LST_MTN_DT
        defaultEventZShouldBeFound("lstMtnDt.lessThanOrEqual=" + DEFAULT_LST_MTN_DT);

        // Get all the eventZList where lstMtnDt is less than or equal to SMALLER_LST_MTN_DT
        defaultEventZShouldNotBeFound("lstMtnDt.lessThanOrEqual=" + SMALLER_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllEventZSByLstMtnDtIsLessThanSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where lstMtnDt is less than DEFAULT_LST_MTN_DT
        defaultEventZShouldNotBeFound("lstMtnDt.lessThan=" + DEFAULT_LST_MTN_DT);

        // Get all the eventZList where lstMtnDt is less than UPDATED_LST_MTN_DT
        defaultEventZShouldBeFound("lstMtnDt.lessThan=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllEventZSByLstMtnDtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        // Get all the eventZList where lstMtnDt is greater than DEFAULT_LST_MTN_DT
        defaultEventZShouldNotBeFound("lstMtnDt.greaterThan=" + DEFAULT_LST_MTN_DT);

        // Get all the eventZList where lstMtnDt is greater than SMALLER_LST_MTN_DT
        defaultEventZShouldBeFound("lstMtnDt.greaterThan=" + SMALLER_LST_MTN_DT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEventZShouldBeFound(String filter) throws Exception {
        restEventZMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventZ.getId().intValue())))
            .andExpect(jsonPath("$.[*].evntNm").value(hasItem(DEFAULT_EVNT_NM)))
            .andExpect(jsonPath("$.[*].evntDt").value(hasItem(sameInstant(DEFAULT_EVNT_DT))))
            .andExpect(jsonPath("$.[*].venue").value(hasItem(DEFAULT_VENUE)))
            .andExpect(jsonPath("$.[*].eventBegTime").value(hasItem(sameInstant(DEFAULT_EVENT_BEG_TIME))))
            .andExpect(jsonPath("$.[*].eventEndTime").value(hasItem(sameInstant(DEFAULT_EVENT_END_TIME))))
            .andExpect(jsonPath("$.[*].lstMtnUsr").value(hasItem(DEFAULT_LST_MTN_USR)))
            .andExpect(jsonPath("$.[*].lstMtnDt").value(hasItem(sameInstant(DEFAULT_LST_MTN_DT))));

        // Check, that the count call also returns 1
        restEventZMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEventZShouldNotBeFound(String filter) throws Exception {
        restEventZMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEventZMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEventZ() throws Exception {
        // Get the eventZ
        restEventZMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEventZ() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        int databaseSizeBeforeUpdate = eventZRepository.findAll().size();

        // Update the eventZ
        EventZ updatedEventZ = eventZRepository.findById(eventZ.getId()).get();
        // Disconnect from session so that the updates on updatedEventZ are not directly saved in db
        em.detach(updatedEventZ);
        updatedEventZ
            .evntNm(UPDATED_EVNT_NM)
            .evntDt(UPDATED_EVNT_DT)
            .venue(UPDATED_VENUE)
            .eventBegTime(UPDATED_EVENT_BEG_TIME)
            .eventEndTime(UPDATED_EVENT_END_TIME)
            .lstMtnUsr(UPDATED_LST_MTN_USR)
            .lstMtnDt(UPDATED_LST_MTN_DT);
        EventZDTO eventZDTO = eventZMapper.toDto(updatedEventZ);

        restEventZMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventZDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventZDTO))
            )
            .andExpect(status().isOk());

        // Validate the EventZ in the database
        List<EventZ> eventZList = eventZRepository.findAll();
        assertThat(eventZList).hasSize(databaseSizeBeforeUpdate);
        EventZ testEventZ = eventZList.get(eventZList.size() - 1);
        assertThat(testEventZ.getEvntNm()).isEqualTo(UPDATED_EVNT_NM);
        assertThat(testEventZ.getEvntDt()).isEqualTo(UPDATED_EVNT_DT);
        assertThat(testEventZ.getVenue()).isEqualTo(UPDATED_VENUE);
        assertThat(testEventZ.getEventBegTime()).isEqualTo(UPDATED_EVENT_BEG_TIME);
        assertThat(testEventZ.getEventEndTime()).isEqualTo(UPDATED_EVENT_END_TIME);
        assertThat(testEventZ.getLstMtnUsr()).isEqualTo(UPDATED_LST_MTN_USR);
        assertThat(testEventZ.getLstMtnDt()).isEqualTo(UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void putNonExistingEventZ() throws Exception {
        int databaseSizeBeforeUpdate = eventZRepository.findAll().size();
        eventZ.setId(count.incrementAndGet());

        // Create the EventZ
        EventZDTO eventZDTO = eventZMapper.toDto(eventZ);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventZMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventZDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventZDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventZ in the database
        List<EventZ> eventZList = eventZRepository.findAll();
        assertThat(eventZList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEventZ() throws Exception {
        int databaseSizeBeforeUpdate = eventZRepository.findAll().size();
        eventZ.setId(count.incrementAndGet());

        // Create the EventZ
        EventZDTO eventZDTO = eventZMapper.toDto(eventZ);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventZMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventZDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventZ in the database
        List<EventZ> eventZList = eventZRepository.findAll();
        assertThat(eventZList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEventZ() throws Exception {
        int databaseSizeBeforeUpdate = eventZRepository.findAll().size();
        eventZ.setId(count.incrementAndGet());

        // Create the EventZ
        EventZDTO eventZDTO = eventZMapper.toDto(eventZ);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventZMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventZDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventZ in the database
        List<EventZ> eventZList = eventZRepository.findAll();
        assertThat(eventZList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventZWithPatch() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        int databaseSizeBeforeUpdate = eventZRepository.findAll().size();

        // Update the eventZ using partial update
        EventZ partialUpdatedEventZ = new EventZ();
        partialUpdatedEventZ.setId(eventZ.getId());

        partialUpdatedEventZ.evntDt(UPDATED_EVNT_DT).venue(UPDATED_VENUE).lstMtnUsr(UPDATED_LST_MTN_USR);

        restEventZMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventZ.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventZ))
            )
            .andExpect(status().isOk());

        // Validate the EventZ in the database
        List<EventZ> eventZList = eventZRepository.findAll();
        assertThat(eventZList).hasSize(databaseSizeBeforeUpdate);
        EventZ testEventZ = eventZList.get(eventZList.size() - 1);
        assertThat(testEventZ.getEvntNm()).isEqualTo(DEFAULT_EVNT_NM);
        assertThat(testEventZ.getEvntDt()).isEqualTo(UPDATED_EVNT_DT);
        assertThat(testEventZ.getVenue()).isEqualTo(UPDATED_VENUE);
        assertThat(testEventZ.getEventBegTime()).isEqualTo(DEFAULT_EVENT_BEG_TIME);
        assertThat(testEventZ.getEventEndTime()).isEqualTo(DEFAULT_EVENT_END_TIME);
        assertThat(testEventZ.getLstMtnUsr()).isEqualTo(UPDATED_LST_MTN_USR);
        assertThat(testEventZ.getLstMtnDt()).isEqualTo(DEFAULT_LST_MTN_DT);
    }

    @Test
    @Transactional
    void fullUpdateEventZWithPatch() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        int databaseSizeBeforeUpdate = eventZRepository.findAll().size();

        // Update the eventZ using partial update
        EventZ partialUpdatedEventZ = new EventZ();
        partialUpdatedEventZ.setId(eventZ.getId());

        partialUpdatedEventZ
            .evntNm(UPDATED_EVNT_NM)
            .evntDt(UPDATED_EVNT_DT)
            .venue(UPDATED_VENUE)
            .eventBegTime(UPDATED_EVENT_BEG_TIME)
            .eventEndTime(UPDATED_EVENT_END_TIME)
            .lstMtnUsr(UPDATED_LST_MTN_USR)
            .lstMtnDt(UPDATED_LST_MTN_DT);

        restEventZMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventZ.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventZ))
            )
            .andExpect(status().isOk());

        // Validate the EventZ in the database
        List<EventZ> eventZList = eventZRepository.findAll();
        assertThat(eventZList).hasSize(databaseSizeBeforeUpdate);
        EventZ testEventZ = eventZList.get(eventZList.size() - 1);
        assertThat(testEventZ.getEvntNm()).isEqualTo(UPDATED_EVNT_NM);
        assertThat(testEventZ.getEvntDt()).isEqualTo(UPDATED_EVNT_DT);
        assertThat(testEventZ.getVenue()).isEqualTo(UPDATED_VENUE);
        assertThat(testEventZ.getEventBegTime()).isEqualTo(UPDATED_EVENT_BEG_TIME);
        assertThat(testEventZ.getEventEndTime()).isEqualTo(UPDATED_EVENT_END_TIME);
        assertThat(testEventZ.getLstMtnUsr()).isEqualTo(UPDATED_LST_MTN_USR);
        assertThat(testEventZ.getLstMtnDt()).isEqualTo(UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void patchNonExistingEventZ() throws Exception {
        int databaseSizeBeforeUpdate = eventZRepository.findAll().size();
        eventZ.setId(count.incrementAndGet());

        // Create the EventZ
        EventZDTO eventZDTO = eventZMapper.toDto(eventZ);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventZMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventZDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventZDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventZ in the database
        List<EventZ> eventZList = eventZRepository.findAll();
        assertThat(eventZList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEventZ() throws Exception {
        int databaseSizeBeforeUpdate = eventZRepository.findAll().size();
        eventZ.setId(count.incrementAndGet());

        // Create the EventZ
        EventZDTO eventZDTO = eventZMapper.toDto(eventZ);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventZMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventZDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventZ in the database
        List<EventZ> eventZList = eventZRepository.findAll();
        assertThat(eventZList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEventZ() throws Exception {
        int databaseSizeBeforeUpdate = eventZRepository.findAll().size();
        eventZ.setId(count.incrementAndGet());

        // Create the EventZ
        EventZDTO eventZDTO = eventZMapper.toDto(eventZ);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventZMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eventZDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventZ in the database
        List<EventZ> eventZList = eventZRepository.findAll();
        assertThat(eventZList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEventZ() throws Exception {
        // Initialize the database
        eventZRepository.saveAndFlush(eventZ);

        int databaseSizeBeforeDelete = eventZRepository.findAll().size();

        // Delete the eventZ
        restEventZMockMvc
            .perform(delete(ENTITY_API_URL_ID, eventZ.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventZ> eventZList = eventZRepository.findAll();
        assertThat(eventZList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
