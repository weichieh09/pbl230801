package com.wcc.pbl230801.web.rest;

import static com.wcc.pbl230801.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.wcc.pbl230801.IntegrationTest;
import com.wcc.pbl230801.domain.EventPlayer;
import com.wcc.pbl230801.repository.EventPlayerRepository;
import com.wcc.pbl230801.service.criteria.EventPlayerCriteria;
import com.wcc.pbl230801.service.dto.EventPlayerDTO;
import com.wcc.pbl230801.service.mapper.EventPlayerMapper;
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
 * Integration tests for the {@link EventPlayerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventPlayerResourceIT {

    private static final Long DEFAULT_E_ID = 1L;
    private static final Long UPDATED_E_ID = 2L;
    private static final Long SMALLER_E_ID = 1L - 1L;

    private static final Long DEFAULT_P_ID = 1L;
    private static final Long UPDATED_P_ID = 2L;
    private static final Long SMALLER_P_ID = 1L - 1L;

    private static final String DEFAULT_CHK_FG = "AAAAAAAAAA";
    private static final String UPDATED_CHK_FG = "BBBBBBBBBB";

    private static final String DEFAULT_LST_MTN_USR = "AAAAAAAAAA";
    private static final String UPDATED_LST_MTN_USR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LST_MTN_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LST_MTN_DT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LST_MTN_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/event-players";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventPlayerRepository eventPlayerRepository;

    @Autowired
    private EventPlayerMapper eventPlayerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventPlayerMockMvc;

    private EventPlayer eventPlayer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventPlayer createEntity(EntityManager em) {
        EventPlayer eventPlayer = new EventPlayer()
            .eId(DEFAULT_E_ID)
            .pId(DEFAULT_P_ID)
            .chkFg(DEFAULT_CHK_FG)
            .lstMtnUsr(DEFAULT_LST_MTN_USR)
            .lstMtnDt(DEFAULT_LST_MTN_DT);
        return eventPlayer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventPlayer createUpdatedEntity(EntityManager em) {
        EventPlayer eventPlayer = new EventPlayer()
            .eId(UPDATED_E_ID)
            .pId(UPDATED_P_ID)
            .chkFg(UPDATED_CHK_FG)
            .lstMtnUsr(UPDATED_LST_MTN_USR)
            .lstMtnDt(UPDATED_LST_MTN_DT);
        return eventPlayer;
    }

    @BeforeEach
    public void initTest() {
        eventPlayer = createEntity(em);
    }

    @Test
    @Transactional
    void createEventPlayer() throws Exception {
        int databaseSizeBeforeCreate = eventPlayerRepository.findAll().size();
        // Create the EventPlayer
        EventPlayerDTO eventPlayerDTO = eventPlayerMapper.toDto(eventPlayer);
        restEventPlayerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventPlayerDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EventPlayer in the database
        List<EventPlayer> eventPlayerList = eventPlayerRepository.findAll();
        assertThat(eventPlayerList).hasSize(databaseSizeBeforeCreate + 1);
        EventPlayer testEventPlayer = eventPlayerList.get(eventPlayerList.size() - 1);
        assertThat(testEventPlayer.geteId()).isEqualTo(DEFAULT_E_ID);
        assertThat(testEventPlayer.getpId()).isEqualTo(DEFAULT_P_ID);
        assertThat(testEventPlayer.getChkFg()).isEqualTo(DEFAULT_CHK_FG);
        assertThat(testEventPlayer.getLstMtnUsr()).isEqualTo(DEFAULT_LST_MTN_USR);
        assertThat(testEventPlayer.getLstMtnDt()).isEqualTo(DEFAULT_LST_MTN_DT);
    }

    @Test
    @Transactional
    void createEventPlayerWithExistingId() throws Exception {
        // Create the EventPlayer with an existing ID
        eventPlayer.setId(1L);
        EventPlayerDTO eventPlayerDTO = eventPlayerMapper.toDto(eventPlayer);

        int databaseSizeBeforeCreate = eventPlayerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventPlayerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventPlayerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventPlayer in the database
        List<EventPlayer> eventPlayerList = eventPlayerRepository.findAll();
        assertThat(eventPlayerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEventPlayers() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList
        restEventPlayerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventPlayer.getId().intValue())))
            .andExpect(jsonPath("$.[*].eId").value(hasItem(DEFAULT_E_ID.intValue())))
            .andExpect(jsonPath("$.[*].pId").value(hasItem(DEFAULT_P_ID.intValue())))
            .andExpect(jsonPath("$.[*].chkFg").value(hasItem(DEFAULT_CHK_FG)))
            .andExpect(jsonPath("$.[*].lstMtnUsr").value(hasItem(DEFAULT_LST_MTN_USR)))
            .andExpect(jsonPath("$.[*].lstMtnDt").value(hasItem(sameInstant(DEFAULT_LST_MTN_DT))));
    }

    @Test
    @Transactional
    void getEventPlayer() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get the eventPlayer
        restEventPlayerMockMvc
            .perform(get(ENTITY_API_URL_ID, eventPlayer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventPlayer.getId().intValue()))
            .andExpect(jsonPath("$.eId").value(DEFAULT_E_ID.intValue()))
            .andExpect(jsonPath("$.pId").value(DEFAULT_P_ID.intValue()))
            .andExpect(jsonPath("$.chkFg").value(DEFAULT_CHK_FG))
            .andExpect(jsonPath("$.lstMtnUsr").value(DEFAULT_LST_MTN_USR))
            .andExpect(jsonPath("$.lstMtnDt").value(sameInstant(DEFAULT_LST_MTN_DT)));
    }

    @Test
    @Transactional
    void getEventPlayersByIdFiltering() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        Long id = eventPlayer.getId();

        defaultEventPlayerShouldBeFound("id.equals=" + id);
        defaultEventPlayerShouldNotBeFound("id.notEquals=" + id);

        defaultEventPlayerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEventPlayerShouldNotBeFound("id.greaterThan=" + id);

        defaultEventPlayerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEventPlayerShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEventPlayersByeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where eId equals to DEFAULT_E_ID
        defaultEventPlayerShouldBeFound("eId.equals=" + DEFAULT_E_ID);

        // Get all the eventPlayerList where eId equals to UPDATED_E_ID
        defaultEventPlayerShouldNotBeFound("eId.equals=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllEventPlayersByeIdIsInShouldWork() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where eId in DEFAULT_E_ID or UPDATED_E_ID
        defaultEventPlayerShouldBeFound("eId.in=" + DEFAULT_E_ID + "," + UPDATED_E_ID);

        // Get all the eventPlayerList where eId equals to UPDATED_E_ID
        defaultEventPlayerShouldNotBeFound("eId.in=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllEventPlayersByeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where eId is not null
        defaultEventPlayerShouldBeFound("eId.specified=true");

        // Get all the eventPlayerList where eId is null
        defaultEventPlayerShouldNotBeFound("eId.specified=false");
    }

    @Test
    @Transactional
    void getAllEventPlayersByeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where eId is greater than or equal to DEFAULT_E_ID
        defaultEventPlayerShouldBeFound("eId.greaterThanOrEqual=" + DEFAULT_E_ID);

        // Get all the eventPlayerList where eId is greater than or equal to UPDATED_E_ID
        defaultEventPlayerShouldNotBeFound("eId.greaterThanOrEqual=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllEventPlayersByeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where eId is less than or equal to DEFAULT_E_ID
        defaultEventPlayerShouldBeFound("eId.lessThanOrEqual=" + DEFAULT_E_ID);

        // Get all the eventPlayerList where eId is less than or equal to SMALLER_E_ID
        defaultEventPlayerShouldNotBeFound("eId.lessThanOrEqual=" + SMALLER_E_ID);
    }

    @Test
    @Transactional
    void getAllEventPlayersByeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where eId is less than DEFAULT_E_ID
        defaultEventPlayerShouldNotBeFound("eId.lessThan=" + DEFAULT_E_ID);

        // Get all the eventPlayerList where eId is less than UPDATED_E_ID
        defaultEventPlayerShouldBeFound("eId.lessThan=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllEventPlayersByeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where eId is greater than DEFAULT_E_ID
        defaultEventPlayerShouldNotBeFound("eId.greaterThan=" + DEFAULT_E_ID);

        // Get all the eventPlayerList where eId is greater than SMALLER_E_ID
        defaultEventPlayerShouldBeFound("eId.greaterThan=" + SMALLER_E_ID);
    }

    @Test
    @Transactional
    void getAllEventPlayersBypIdIsEqualToSomething() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where pId equals to DEFAULT_P_ID
        defaultEventPlayerShouldBeFound("pId.equals=" + DEFAULT_P_ID);

        // Get all the eventPlayerList where pId equals to UPDATED_P_ID
        defaultEventPlayerShouldNotBeFound("pId.equals=" + UPDATED_P_ID);
    }

    @Test
    @Transactional
    void getAllEventPlayersBypIdIsInShouldWork() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where pId in DEFAULT_P_ID or UPDATED_P_ID
        defaultEventPlayerShouldBeFound("pId.in=" + DEFAULT_P_ID + "," + UPDATED_P_ID);

        // Get all the eventPlayerList where pId equals to UPDATED_P_ID
        defaultEventPlayerShouldNotBeFound("pId.in=" + UPDATED_P_ID);
    }

    @Test
    @Transactional
    void getAllEventPlayersBypIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where pId is not null
        defaultEventPlayerShouldBeFound("pId.specified=true");

        // Get all the eventPlayerList where pId is null
        defaultEventPlayerShouldNotBeFound("pId.specified=false");
    }

    @Test
    @Transactional
    void getAllEventPlayersBypIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where pId is greater than or equal to DEFAULT_P_ID
        defaultEventPlayerShouldBeFound("pId.greaterThanOrEqual=" + DEFAULT_P_ID);

        // Get all the eventPlayerList where pId is greater than or equal to UPDATED_P_ID
        defaultEventPlayerShouldNotBeFound("pId.greaterThanOrEqual=" + UPDATED_P_ID);
    }

    @Test
    @Transactional
    void getAllEventPlayersBypIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where pId is less than or equal to DEFAULT_P_ID
        defaultEventPlayerShouldBeFound("pId.lessThanOrEqual=" + DEFAULT_P_ID);

        // Get all the eventPlayerList where pId is less than or equal to SMALLER_P_ID
        defaultEventPlayerShouldNotBeFound("pId.lessThanOrEqual=" + SMALLER_P_ID);
    }

    @Test
    @Transactional
    void getAllEventPlayersBypIdIsLessThanSomething() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where pId is less than DEFAULT_P_ID
        defaultEventPlayerShouldNotBeFound("pId.lessThan=" + DEFAULT_P_ID);

        // Get all the eventPlayerList where pId is less than UPDATED_P_ID
        defaultEventPlayerShouldBeFound("pId.lessThan=" + UPDATED_P_ID);
    }

    @Test
    @Transactional
    void getAllEventPlayersBypIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where pId is greater than DEFAULT_P_ID
        defaultEventPlayerShouldNotBeFound("pId.greaterThan=" + DEFAULT_P_ID);

        // Get all the eventPlayerList where pId is greater than SMALLER_P_ID
        defaultEventPlayerShouldBeFound("pId.greaterThan=" + SMALLER_P_ID);
    }

    @Test
    @Transactional
    void getAllEventPlayersByChkFgIsEqualToSomething() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where chkFg equals to DEFAULT_CHK_FG
        defaultEventPlayerShouldBeFound("chkFg.equals=" + DEFAULT_CHK_FG);

        // Get all the eventPlayerList where chkFg equals to UPDATED_CHK_FG
        defaultEventPlayerShouldNotBeFound("chkFg.equals=" + UPDATED_CHK_FG);
    }

    @Test
    @Transactional
    void getAllEventPlayersByChkFgIsInShouldWork() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where chkFg in DEFAULT_CHK_FG or UPDATED_CHK_FG
        defaultEventPlayerShouldBeFound("chkFg.in=" + DEFAULT_CHK_FG + "," + UPDATED_CHK_FG);

        // Get all the eventPlayerList where chkFg equals to UPDATED_CHK_FG
        defaultEventPlayerShouldNotBeFound("chkFg.in=" + UPDATED_CHK_FG);
    }

    @Test
    @Transactional
    void getAllEventPlayersByChkFgIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where chkFg is not null
        defaultEventPlayerShouldBeFound("chkFg.specified=true");

        // Get all the eventPlayerList where chkFg is null
        defaultEventPlayerShouldNotBeFound("chkFg.specified=false");
    }

    @Test
    @Transactional
    void getAllEventPlayersByChkFgContainsSomething() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where chkFg contains DEFAULT_CHK_FG
        defaultEventPlayerShouldBeFound("chkFg.contains=" + DEFAULT_CHK_FG);

        // Get all the eventPlayerList where chkFg contains UPDATED_CHK_FG
        defaultEventPlayerShouldNotBeFound("chkFg.contains=" + UPDATED_CHK_FG);
    }

    @Test
    @Transactional
    void getAllEventPlayersByChkFgNotContainsSomething() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where chkFg does not contain DEFAULT_CHK_FG
        defaultEventPlayerShouldNotBeFound("chkFg.doesNotContain=" + DEFAULT_CHK_FG);

        // Get all the eventPlayerList where chkFg does not contain UPDATED_CHK_FG
        defaultEventPlayerShouldBeFound("chkFg.doesNotContain=" + UPDATED_CHK_FG);
    }

    @Test
    @Transactional
    void getAllEventPlayersByLstMtnUsrIsEqualToSomething() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where lstMtnUsr equals to DEFAULT_LST_MTN_USR
        defaultEventPlayerShouldBeFound("lstMtnUsr.equals=" + DEFAULT_LST_MTN_USR);

        // Get all the eventPlayerList where lstMtnUsr equals to UPDATED_LST_MTN_USR
        defaultEventPlayerShouldNotBeFound("lstMtnUsr.equals=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllEventPlayersByLstMtnUsrIsInShouldWork() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where lstMtnUsr in DEFAULT_LST_MTN_USR or UPDATED_LST_MTN_USR
        defaultEventPlayerShouldBeFound("lstMtnUsr.in=" + DEFAULT_LST_MTN_USR + "," + UPDATED_LST_MTN_USR);

        // Get all the eventPlayerList where lstMtnUsr equals to UPDATED_LST_MTN_USR
        defaultEventPlayerShouldNotBeFound("lstMtnUsr.in=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllEventPlayersByLstMtnUsrIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where lstMtnUsr is not null
        defaultEventPlayerShouldBeFound("lstMtnUsr.specified=true");

        // Get all the eventPlayerList where lstMtnUsr is null
        defaultEventPlayerShouldNotBeFound("lstMtnUsr.specified=false");
    }

    @Test
    @Transactional
    void getAllEventPlayersByLstMtnUsrContainsSomething() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where lstMtnUsr contains DEFAULT_LST_MTN_USR
        defaultEventPlayerShouldBeFound("lstMtnUsr.contains=" + DEFAULT_LST_MTN_USR);

        // Get all the eventPlayerList where lstMtnUsr contains UPDATED_LST_MTN_USR
        defaultEventPlayerShouldNotBeFound("lstMtnUsr.contains=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllEventPlayersByLstMtnUsrNotContainsSomething() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where lstMtnUsr does not contain DEFAULT_LST_MTN_USR
        defaultEventPlayerShouldNotBeFound("lstMtnUsr.doesNotContain=" + DEFAULT_LST_MTN_USR);

        // Get all the eventPlayerList where lstMtnUsr does not contain UPDATED_LST_MTN_USR
        defaultEventPlayerShouldBeFound("lstMtnUsr.doesNotContain=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllEventPlayersByLstMtnDtIsEqualToSomething() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where lstMtnDt equals to DEFAULT_LST_MTN_DT
        defaultEventPlayerShouldBeFound("lstMtnDt.equals=" + DEFAULT_LST_MTN_DT);

        // Get all the eventPlayerList where lstMtnDt equals to UPDATED_LST_MTN_DT
        defaultEventPlayerShouldNotBeFound("lstMtnDt.equals=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllEventPlayersByLstMtnDtIsInShouldWork() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where lstMtnDt in DEFAULT_LST_MTN_DT or UPDATED_LST_MTN_DT
        defaultEventPlayerShouldBeFound("lstMtnDt.in=" + DEFAULT_LST_MTN_DT + "," + UPDATED_LST_MTN_DT);

        // Get all the eventPlayerList where lstMtnDt equals to UPDATED_LST_MTN_DT
        defaultEventPlayerShouldNotBeFound("lstMtnDt.in=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllEventPlayersByLstMtnDtIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where lstMtnDt is not null
        defaultEventPlayerShouldBeFound("lstMtnDt.specified=true");

        // Get all the eventPlayerList where lstMtnDt is null
        defaultEventPlayerShouldNotBeFound("lstMtnDt.specified=false");
    }

    @Test
    @Transactional
    void getAllEventPlayersByLstMtnDtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where lstMtnDt is greater than or equal to DEFAULT_LST_MTN_DT
        defaultEventPlayerShouldBeFound("lstMtnDt.greaterThanOrEqual=" + DEFAULT_LST_MTN_DT);

        // Get all the eventPlayerList where lstMtnDt is greater than or equal to UPDATED_LST_MTN_DT
        defaultEventPlayerShouldNotBeFound("lstMtnDt.greaterThanOrEqual=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllEventPlayersByLstMtnDtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where lstMtnDt is less than or equal to DEFAULT_LST_MTN_DT
        defaultEventPlayerShouldBeFound("lstMtnDt.lessThanOrEqual=" + DEFAULT_LST_MTN_DT);

        // Get all the eventPlayerList where lstMtnDt is less than or equal to SMALLER_LST_MTN_DT
        defaultEventPlayerShouldNotBeFound("lstMtnDt.lessThanOrEqual=" + SMALLER_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllEventPlayersByLstMtnDtIsLessThanSomething() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where lstMtnDt is less than DEFAULT_LST_MTN_DT
        defaultEventPlayerShouldNotBeFound("lstMtnDt.lessThan=" + DEFAULT_LST_MTN_DT);

        // Get all the eventPlayerList where lstMtnDt is less than UPDATED_LST_MTN_DT
        defaultEventPlayerShouldBeFound("lstMtnDt.lessThan=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllEventPlayersByLstMtnDtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        // Get all the eventPlayerList where lstMtnDt is greater than DEFAULT_LST_MTN_DT
        defaultEventPlayerShouldNotBeFound("lstMtnDt.greaterThan=" + DEFAULT_LST_MTN_DT);

        // Get all the eventPlayerList where lstMtnDt is greater than SMALLER_LST_MTN_DT
        defaultEventPlayerShouldBeFound("lstMtnDt.greaterThan=" + SMALLER_LST_MTN_DT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEventPlayerShouldBeFound(String filter) throws Exception {
        restEventPlayerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventPlayer.getId().intValue())))
            .andExpect(jsonPath("$.[*].eId").value(hasItem(DEFAULT_E_ID.intValue())))
            .andExpect(jsonPath("$.[*].pId").value(hasItem(DEFAULT_P_ID.intValue())))
            .andExpect(jsonPath("$.[*].chkFg").value(hasItem(DEFAULT_CHK_FG)))
            .andExpect(jsonPath("$.[*].lstMtnUsr").value(hasItem(DEFAULT_LST_MTN_USR)))
            .andExpect(jsonPath("$.[*].lstMtnDt").value(hasItem(sameInstant(DEFAULT_LST_MTN_DT))));

        // Check, that the count call also returns 1
        restEventPlayerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEventPlayerShouldNotBeFound(String filter) throws Exception {
        restEventPlayerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEventPlayerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEventPlayer() throws Exception {
        // Get the eventPlayer
        restEventPlayerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEventPlayer() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        int databaseSizeBeforeUpdate = eventPlayerRepository.findAll().size();

        // Update the eventPlayer
        EventPlayer updatedEventPlayer = eventPlayerRepository.findById(eventPlayer.getId()).get();
        // Disconnect from session so that the updates on updatedEventPlayer are not directly saved in db
        em.detach(updatedEventPlayer);
        updatedEventPlayer
            .eId(UPDATED_E_ID)
            .pId(UPDATED_P_ID)
            .chkFg(UPDATED_CHK_FG)
            .lstMtnUsr(UPDATED_LST_MTN_USR)
            .lstMtnDt(UPDATED_LST_MTN_DT);
        EventPlayerDTO eventPlayerDTO = eventPlayerMapper.toDto(updatedEventPlayer);

        restEventPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventPlayerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventPlayerDTO))
            )
            .andExpect(status().isOk());

        // Validate the EventPlayer in the database
        List<EventPlayer> eventPlayerList = eventPlayerRepository.findAll();
        assertThat(eventPlayerList).hasSize(databaseSizeBeforeUpdate);
        EventPlayer testEventPlayer = eventPlayerList.get(eventPlayerList.size() - 1);
        assertThat(testEventPlayer.geteId()).isEqualTo(UPDATED_E_ID);
        assertThat(testEventPlayer.getpId()).isEqualTo(UPDATED_P_ID);
        assertThat(testEventPlayer.getChkFg()).isEqualTo(UPDATED_CHK_FG);
        assertThat(testEventPlayer.getLstMtnUsr()).isEqualTo(UPDATED_LST_MTN_USR);
        assertThat(testEventPlayer.getLstMtnDt()).isEqualTo(UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void putNonExistingEventPlayer() throws Exception {
        int databaseSizeBeforeUpdate = eventPlayerRepository.findAll().size();
        eventPlayer.setId(count.incrementAndGet());

        // Create the EventPlayer
        EventPlayerDTO eventPlayerDTO = eventPlayerMapper.toDto(eventPlayer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventPlayerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventPlayerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventPlayer in the database
        List<EventPlayer> eventPlayerList = eventPlayerRepository.findAll();
        assertThat(eventPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEventPlayer() throws Exception {
        int databaseSizeBeforeUpdate = eventPlayerRepository.findAll().size();
        eventPlayer.setId(count.incrementAndGet());

        // Create the EventPlayer
        EventPlayerDTO eventPlayerDTO = eventPlayerMapper.toDto(eventPlayer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventPlayerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventPlayer in the database
        List<EventPlayer> eventPlayerList = eventPlayerRepository.findAll();
        assertThat(eventPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEventPlayer() throws Exception {
        int databaseSizeBeforeUpdate = eventPlayerRepository.findAll().size();
        eventPlayer.setId(count.incrementAndGet());

        // Create the EventPlayer
        EventPlayerDTO eventPlayerDTO = eventPlayerMapper.toDto(eventPlayer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventPlayerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventPlayerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventPlayer in the database
        List<EventPlayer> eventPlayerList = eventPlayerRepository.findAll();
        assertThat(eventPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventPlayerWithPatch() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        int databaseSizeBeforeUpdate = eventPlayerRepository.findAll().size();

        // Update the eventPlayer using partial update
        EventPlayer partialUpdatedEventPlayer = new EventPlayer();
        partialUpdatedEventPlayer.setId(eventPlayer.getId());

        partialUpdatedEventPlayer.eId(UPDATED_E_ID).pId(UPDATED_P_ID).chkFg(UPDATED_CHK_FG).lstMtnDt(UPDATED_LST_MTN_DT);

        restEventPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventPlayer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventPlayer))
            )
            .andExpect(status().isOk());

        // Validate the EventPlayer in the database
        List<EventPlayer> eventPlayerList = eventPlayerRepository.findAll();
        assertThat(eventPlayerList).hasSize(databaseSizeBeforeUpdate);
        EventPlayer testEventPlayer = eventPlayerList.get(eventPlayerList.size() - 1);
        assertThat(testEventPlayer.geteId()).isEqualTo(UPDATED_E_ID);
        assertThat(testEventPlayer.getpId()).isEqualTo(UPDATED_P_ID);
        assertThat(testEventPlayer.getChkFg()).isEqualTo(UPDATED_CHK_FG);
        assertThat(testEventPlayer.getLstMtnUsr()).isEqualTo(DEFAULT_LST_MTN_USR);
        assertThat(testEventPlayer.getLstMtnDt()).isEqualTo(UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void fullUpdateEventPlayerWithPatch() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        int databaseSizeBeforeUpdate = eventPlayerRepository.findAll().size();

        // Update the eventPlayer using partial update
        EventPlayer partialUpdatedEventPlayer = new EventPlayer();
        partialUpdatedEventPlayer.setId(eventPlayer.getId());

        partialUpdatedEventPlayer
            .eId(UPDATED_E_ID)
            .pId(UPDATED_P_ID)
            .chkFg(UPDATED_CHK_FG)
            .lstMtnUsr(UPDATED_LST_MTN_USR)
            .lstMtnDt(UPDATED_LST_MTN_DT);

        restEventPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventPlayer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventPlayer))
            )
            .andExpect(status().isOk());

        // Validate the EventPlayer in the database
        List<EventPlayer> eventPlayerList = eventPlayerRepository.findAll();
        assertThat(eventPlayerList).hasSize(databaseSizeBeforeUpdate);
        EventPlayer testEventPlayer = eventPlayerList.get(eventPlayerList.size() - 1);
        assertThat(testEventPlayer.geteId()).isEqualTo(UPDATED_E_ID);
        assertThat(testEventPlayer.getpId()).isEqualTo(UPDATED_P_ID);
        assertThat(testEventPlayer.getChkFg()).isEqualTo(UPDATED_CHK_FG);
        assertThat(testEventPlayer.getLstMtnUsr()).isEqualTo(UPDATED_LST_MTN_USR);
        assertThat(testEventPlayer.getLstMtnDt()).isEqualTo(UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void patchNonExistingEventPlayer() throws Exception {
        int databaseSizeBeforeUpdate = eventPlayerRepository.findAll().size();
        eventPlayer.setId(count.incrementAndGet());

        // Create the EventPlayer
        EventPlayerDTO eventPlayerDTO = eventPlayerMapper.toDto(eventPlayer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventPlayerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventPlayerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventPlayer in the database
        List<EventPlayer> eventPlayerList = eventPlayerRepository.findAll();
        assertThat(eventPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEventPlayer() throws Exception {
        int databaseSizeBeforeUpdate = eventPlayerRepository.findAll().size();
        eventPlayer.setId(count.incrementAndGet());

        // Create the EventPlayer
        EventPlayerDTO eventPlayerDTO = eventPlayerMapper.toDto(eventPlayer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventPlayerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventPlayer in the database
        List<EventPlayer> eventPlayerList = eventPlayerRepository.findAll();
        assertThat(eventPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEventPlayer() throws Exception {
        int databaseSizeBeforeUpdate = eventPlayerRepository.findAll().size();
        eventPlayer.setId(count.incrementAndGet());

        // Create the EventPlayer
        EventPlayerDTO eventPlayerDTO = eventPlayerMapper.toDto(eventPlayer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eventPlayerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventPlayer in the database
        List<EventPlayer> eventPlayerList = eventPlayerRepository.findAll();
        assertThat(eventPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEventPlayer() throws Exception {
        // Initialize the database
        eventPlayerRepository.saveAndFlush(eventPlayer);

        int databaseSizeBeforeDelete = eventPlayerRepository.findAll().size();

        // Delete the eventPlayer
        restEventPlayerMockMvc
            .perform(delete(ENTITY_API_URL_ID, eventPlayer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventPlayer> eventPlayerList = eventPlayerRepository.findAll();
        assertThat(eventPlayerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
