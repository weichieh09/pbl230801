package com.wcc.pbl230801.web.rest;

import static com.wcc.pbl230801.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.wcc.pbl230801.IntegrationTest;
import com.wcc.pbl230801.domain.TeamEvent;
import com.wcc.pbl230801.repository.TeamEventRepository;
import com.wcc.pbl230801.service.criteria.TeamEventCriteria;
import com.wcc.pbl230801.service.dto.TeamEventDTO;
import com.wcc.pbl230801.service.mapper.TeamEventMapper;
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
 * Integration tests for the {@link TeamEventResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TeamEventResourceIT {

    private static final Long DEFAULT_T_ID = 1L;
    private static final Long UPDATED_T_ID = 2L;
    private static final Long SMALLER_T_ID = 1L - 1L;

    private static final Long DEFAULT_E_ID = 1L;
    private static final Long UPDATED_E_ID = 2L;
    private static final Long SMALLER_E_ID = 1L - 1L;

    private static final String DEFAULT_LST_MTN_USR = "AAAAAAAAAA";
    private static final String UPDATED_LST_MTN_USR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LST_MTN_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LST_MTN_DT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LST_MTN_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/team-events";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TeamEventRepository teamEventRepository;

    @Autowired
    private TeamEventMapper teamEventMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTeamEventMockMvc;

    private TeamEvent teamEvent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamEvent createEntity(EntityManager em) {
        TeamEvent teamEvent = new TeamEvent()
            .tId(DEFAULT_T_ID)
            .eId(DEFAULT_E_ID)
            .lstMtnUsr(DEFAULT_LST_MTN_USR)
            .lstMtnDt(DEFAULT_LST_MTN_DT);
        return teamEvent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamEvent createUpdatedEntity(EntityManager em) {
        TeamEvent teamEvent = new TeamEvent()
            .tId(UPDATED_T_ID)
            .eId(UPDATED_E_ID)
            .lstMtnUsr(UPDATED_LST_MTN_USR)
            .lstMtnDt(UPDATED_LST_MTN_DT);
        return teamEvent;
    }

    @BeforeEach
    public void initTest() {
        teamEvent = createEntity(em);
    }

    @Test
    @Transactional
    void createTeamEvent() throws Exception {
        int databaseSizeBeforeCreate = teamEventRepository.findAll().size();
        // Create the TeamEvent
        TeamEventDTO teamEventDTO = teamEventMapper.toDto(teamEvent);
        restTeamEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teamEventDTO)))
            .andExpect(status().isCreated());

        // Validate the TeamEvent in the database
        List<TeamEvent> teamEventList = teamEventRepository.findAll();
        assertThat(teamEventList).hasSize(databaseSizeBeforeCreate + 1);
        TeamEvent testTeamEvent = teamEventList.get(teamEventList.size() - 1);
        assertThat(testTeamEvent.gettId()).isEqualTo(DEFAULT_T_ID);
        assertThat(testTeamEvent.geteId()).isEqualTo(DEFAULT_E_ID);
        assertThat(testTeamEvent.getLstMtnUsr()).isEqualTo(DEFAULT_LST_MTN_USR);
        assertThat(testTeamEvent.getLstMtnDt()).isEqualTo(DEFAULT_LST_MTN_DT);
    }

    @Test
    @Transactional
    void createTeamEventWithExistingId() throws Exception {
        // Create the TeamEvent with an existing ID
        teamEvent.setId(1L);
        TeamEventDTO teamEventDTO = teamEventMapper.toDto(teamEvent);

        int databaseSizeBeforeCreate = teamEventRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teamEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TeamEvent in the database
        List<TeamEvent> teamEventList = teamEventRepository.findAll();
        assertThat(teamEventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTeamEvents() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList
        restTeamEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].tId").value(hasItem(DEFAULT_T_ID.intValue())))
            .andExpect(jsonPath("$.[*].eId").value(hasItem(DEFAULT_E_ID.intValue())))
            .andExpect(jsonPath("$.[*].lstMtnUsr").value(hasItem(DEFAULT_LST_MTN_USR)))
            .andExpect(jsonPath("$.[*].lstMtnDt").value(hasItem(sameInstant(DEFAULT_LST_MTN_DT))));
    }

    @Test
    @Transactional
    void getTeamEvent() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get the teamEvent
        restTeamEventMockMvc
            .perform(get(ENTITY_API_URL_ID, teamEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(teamEvent.getId().intValue()))
            .andExpect(jsonPath("$.tId").value(DEFAULT_T_ID.intValue()))
            .andExpect(jsonPath("$.eId").value(DEFAULT_E_ID.intValue()))
            .andExpect(jsonPath("$.lstMtnUsr").value(DEFAULT_LST_MTN_USR))
            .andExpect(jsonPath("$.lstMtnDt").value(sameInstant(DEFAULT_LST_MTN_DT)));
    }

    @Test
    @Transactional
    void getTeamEventsByIdFiltering() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        Long id = teamEvent.getId();

        defaultTeamEventShouldBeFound("id.equals=" + id);
        defaultTeamEventShouldNotBeFound("id.notEquals=" + id);

        defaultTeamEventShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTeamEventShouldNotBeFound("id.greaterThan=" + id);

        defaultTeamEventShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTeamEventShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTeamEventsBytIdIsEqualToSomething() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where tId equals to DEFAULT_T_ID
        defaultTeamEventShouldBeFound("tId.equals=" + DEFAULT_T_ID);

        // Get all the teamEventList where tId equals to UPDATED_T_ID
        defaultTeamEventShouldNotBeFound("tId.equals=" + UPDATED_T_ID);
    }

    @Test
    @Transactional
    void getAllTeamEventsBytIdIsInShouldWork() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where tId in DEFAULT_T_ID or UPDATED_T_ID
        defaultTeamEventShouldBeFound("tId.in=" + DEFAULT_T_ID + "," + UPDATED_T_ID);

        // Get all the teamEventList where tId equals to UPDATED_T_ID
        defaultTeamEventShouldNotBeFound("tId.in=" + UPDATED_T_ID);
    }

    @Test
    @Transactional
    void getAllTeamEventsBytIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where tId is not null
        defaultTeamEventShouldBeFound("tId.specified=true");

        // Get all the teamEventList where tId is null
        defaultTeamEventShouldNotBeFound("tId.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamEventsBytIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where tId is greater than or equal to DEFAULT_T_ID
        defaultTeamEventShouldBeFound("tId.greaterThanOrEqual=" + DEFAULT_T_ID);

        // Get all the teamEventList where tId is greater than or equal to UPDATED_T_ID
        defaultTeamEventShouldNotBeFound("tId.greaterThanOrEqual=" + UPDATED_T_ID);
    }

    @Test
    @Transactional
    void getAllTeamEventsBytIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where tId is less than or equal to DEFAULT_T_ID
        defaultTeamEventShouldBeFound("tId.lessThanOrEqual=" + DEFAULT_T_ID);

        // Get all the teamEventList where tId is less than or equal to SMALLER_T_ID
        defaultTeamEventShouldNotBeFound("tId.lessThanOrEqual=" + SMALLER_T_ID);
    }

    @Test
    @Transactional
    void getAllTeamEventsBytIdIsLessThanSomething() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where tId is less than DEFAULT_T_ID
        defaultTeamEventShouldNotBeFound("tId.lessThan=" + DEFAULT_T_ID);

        // Get all the teamEventList where tId is less than UPDATED_T_ID
        defaultTeamEventShouldBeFound("tId.lessThan=" + UPDATED_T_ID);
    }

    @Test
    @Transactional
    void getAllTeamEventsBytIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where tId is greater than DEFAULT_T_ID
        defaultTeamEventShouldNotBeFound("tId.greaterThan=" + DEFAULT_T_ID);

        // Get all the teamEventList where tId is greater than SMALLER_T_ID
        defaultTeamEventShouldBeFound("tId.greaterThan=" + SMALLER_T_ID);
    }

    @Test
    @Transactional
    void getAllTeamEventsByeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where eId equals to DEFAULT_E_ID
        defaultTeamEventShouldBeFound("eId.equals=" + DEFAULT_E_ID);

        // Get all the teamEventList where eId equals to UPDATED_E_ID
        defaultTeamEventShouldNotBeFound("eId.equals=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllTeamEventsByeIdIsInShouldWork() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where eId in DEFAULT_E_ID or UPDATED_E_ID
        defaultTeamEventShouldBeFound("eId.in=" + DEFAULT_E_ID + "," + UPDATED_E_ID);

        // Get all the teamEventList where eId equals to UPDATED_E_ID
        defaultTeamEventShouldNotBeFound("eId.in=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllTeamEventsByeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where eId is not null
        defaultTeamEventShouldBeFound("eId.specified=true");

        // Get all the teamEventList where eId is null
        defaultTeamEventShouldNotBeFound("eId.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamEventsByeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where eId is greater than or equal to DEFAULT_E_ID
        defaultTeamEventShouldBeFound("eId.greaterThanOrEqual=" + DEFAULT_E_ID);

        // Get all the teamEventList where eId is greater than or equal to UPDATED_E_ID
        defaultTeamEventShouldNotBeFound("eId.greaterThanOrEqual=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllTeamEventsByeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where eId is less than or equal to DEFAULT_E_ID
        defaultTeamEventShouldBeFound("eId.lessThanOrEqual=" + DEFAULT_E_ID);

        // Get all the teamEventList where eId is less than or equal to SMALLER_E_ID
        defaultTeamEventShouldNotBeFound("eId.lessThanOrEqual=" + SMALLER_E_ID);
    }

    @Test
    @Transactional
    void getAllTeamEventsByeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where eId is less than DEFAULT_E_ID
        defaultTeamEventShouldNotBeFound("eId.lessThan=" + DEFAULT_E_ID);

        // Get all the teamEventList where eId is less than UPDATED_E_ID
        defaultTeamEventShouldBeFound("eId.lessThan=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllTeamEventsByeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where eId is greater than DEFAULT_E_ID
        defaultTeamEventShouldNotBeFound("eId.greaterThan=" + DEFAULT_E_ID);

        // Get all the teamEventList where eId is greater than SMALLER_E_ID
        defaultTeamEventShouldBeFound("eId.greaterThan=" + SMALLER_E_ID);
    }

    @Test
    @Transactional
    void getAllTeamEventsByLstMtnUsrIsEqualToSomething() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where lstMtnUsr equals to DEFAULT_LST_MTN_USR
        defaultTeamEventShouldBeFound("lstMtnUsr.equals=" + DEFAULT_LST_MTN_USR);

        // Get all the teamEventList where lstMtnUsr equals to UPDATED_LST_MTN_USR
        defaultTeamEventShouldNotBeFound("lstMtnUsr.equals=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllTeamEventsByLstMtnUsrIsInShouldWork() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where lstMtnUsr in DEFAULT_LST_MTN_USR or UPDATED_LST_MTN_USR
        defaultTeamEventShouldBeFound("lstMtnUsr.in=" + DEFAULT_LST_MTN_USR + "," + UPDATED_LST_MTN_USR);

        // Get all the teamEventList where lstMtnUsr equals to UPDATED_LST_MTN_USR
        defaultTeamEventShouldNotBeFound("lstMtnUsr.in=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllTeamEventsByLstMtnUsrIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where lstMtnUsr is not null
        defaultTeamEventShouldBeFound("lstMtnUsr.specified=true");

        // Get all the teamEventList where lstMtnUsr is null
        defaultTeamEventShouldNotBeFound("lstMtnUsr.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamEventsByLstMtnUsrContainsSomething() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where lstMtnUsr contains DEFAULT_LST_MTN_USR
        defaultTeamEventShouldBeFound("lstMtnUsr.contains=" + DEFAULT_LST_MTN_USR);

        // Get all the teamEventList where lstMtnUsr contains UPDATED_LST_MTN_USR
        defaultTeamEventShouldNotBeFound("lstMtnUsr.contains=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllTeamEventsByLstMtnUsrNotContainsSomething() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where lstMtnUsr does not contain DEFAULT_LST_MTN_USR
        defaultTeamEventShouldNotBeFound("lstMtnUsr.doesNotContain=" + DEFAULT_LST_MTN_USR);

        // Get all the teamEventList where lstMtnUsr does not contain UPDATED_LST_MTN_USR
        defaultTeamEventShouldBeFound("lstMtnUsr.doesNotContain=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllTeamEventsByLstMtnDtIsEqualToSomething() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where lstMtnDt equals to DEFAULT_LST_MTN_DT
        defaultTeamEventShouldBeFound("lstMtnDt.equals=" + DEFAULT_LST_MTN_DT);

        // Get all the teamEventList where lstMtnDt equals to UPDATED_LST_MTN_DT
        defaultTeamEventShouldNotBeFound("lstMtnDt.equals=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllTeamEventsByLstMtnDtIsInShouldWork() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where lstMtnDt in DEFAULT_LST_MTN_DT or UPDATED_LST_MTN_DT
        defaultTeamEventShouldBeFound("lstMtnDt.in=" + DEFAULT_LST_MTN_DT + "," + UPDATED_LST_MTN_DT);

        // Get all the teamEventList where lstMtnDt equals to UPDATED_LST_MTN_DT
        defaultTeamEventShouldNotBeFound("lstMtnDt.in=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllTeamEventsByLstMtnDtIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where lstMtnDt is not null
        defaultTeamEventShouldBeFound("lstMtnDt.specified=true");

        // Get all the teamEventList where lstMtnDt is null
        defaultTeamEventShouldNotBeFound("lstMtnDt.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamEventsByLstMtnDtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where lstMtnDt is greater than or equal to DEFAULT_LST_MTN_DT
        defaultTeamEventShouldBeFound("lstMtnDt.greaterThanOrEqual=" + DEFAULT_LST_MTN_DT);

        // Get all the teamEventList where lstMtnDt is greater than or equal to UPDATED_LST_MTN_DT
        defaultTeamEventShouldNotBeFound("lstMtnDt.greaterThanOrEqual=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllTeamEventsByLstMtnDtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where lstMtnDt is less than or equal to DEFAULT_LST_MTN_DT
        defaultTeamEventShouldBeFound("lstMtnDt.lessThanOrEqual=" + DEFAULT_LST_MTN_DT);

        // Get all the teamEventList where lstMtnDt is less than or equal to SMALLER_LST_MTN_DT
        defaultTeamEventShouldNotBeFound("lstMtnDt.lessThanOrEqual=" + SMALLER_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllTeamEventsByLstMtnDtIsLessThanSomething() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where lstMtnDt is less than DEFAULT_LST_MTN_DT
        defaultTeamEventShouldNotBeFound("lstMtnDt.lessThan=" + DEFAULT_LST_MTN_DT);

        // Get all the teamEventList where lstMtnDt is less than UPDATED_LST_MTN_DT
        defaultTeamEventShouldBeFound("lstMtnDt.lessThan=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllTeamEventsByLstMtnDtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        // Get all the teamEventList where lstMtnDt is greater than DEFAULT_LST_MTN_DT
        defaultTeamEventShouldNotBeFound("lstMtnDt.greaterThan=" + DEFAULT_LST_MTN_DT);

        // Get all the teamEventList where lstMtnDt is greater than SMALLER_LST_MTN_DT
        defaultTeamEventShouldBeFound("lstMtnDt.greaterThan=" + SMALLER_LST_MTN_DT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTeamEventShouldBeFound(String filter) throws Exception {
        restTeamEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].tId").value(hasItem(DEFAULT_T_ID.intValue())))
            .andExpect(jsonPath("$.[*].eId").value(hasItem(DEFAULT_E_ID.intValue())))
            .andExpect(jsonPath("$.[*].lstMtnUsr").value(hasItem(DEFAULT_LST_MTN_USR)))
            .andExpect(jsonPath("$.[*].lstMtnDt").value(hasItem(sameInstant(DEFAULT_LST_MTN_DT))));

        // Check, that the count call also returns 1
        restTeamEventMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTeamEventShouldNotBeFound(String filter) throws Exception {
        restTeamEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTeamEventMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTeamEvent() throws Exception {
        // Get the teamEvent
        restTeamEventMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTeamEvent() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        int databaseSizeBeforeUpdate = teamEventRepository.findAll().size();

        // Update the teamEvent
        TeamEvent updatedTeamEvent = teamEventRepository.findById(teamEvent.getId()).get();
        // Disconnect from session so that the updates on updatedTeamEvent are not directly saved in db
        em.detach(updatedTeamEvent);
        updatedTeamEvent.tId(UPDATED_T_ID).eId(UPDATED_E_ID).lstMtnUsr(UPDATED_LST_MTN_USR).lstMtnDt(UPDATED_LST_MTN_DT);
        TeamEventDTO teamEventDTO = teamEventMapper.toDto(updatedTeamEvent);

        restTeamEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teamEventDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamEventDTO))
            )
            .andExpect(status().isOk());

        // Validate the TeamEvent in the database
        List<TeamEvent> teamEventList = teamEventRepository.findAll();
        assertThat(teamEventList).hasSize(databaseSizeBeforeUpdate);
        TeamEvent testTeamEvent = teamEventList.get(teamEventList.size() - 1);
        assertThat(testTeamEvent.gettId()).isEqualTo(UPDATED_T_ID);
        assertThat(testTeamEvent.geteId()).isEqualTo(UPDATED_E_ID);
        assertThat(testTeamEvent.getLstMtnUsr()).isEqualTo(UPDATED_LST_MTN_USR);
        assertThat(testTeamEvent.getLstMtnDt()).isEqualTo(UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void putNonExistingTeamEvent() throws Exception {
        int databaseSizeBeforeUpdate = teamEventRepository.findAll().size();
        teamEvent.setId(count.incrementAndGet());

        // Create the TeamEvent
        TeamEventDTO teamEventDTO = teamEventMapper.toDto(teamEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teamEventDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamEventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamEvent in the database
        List<TeamEvent> teamEventList = teamEventRepository.findAll();
        assertThat(teamEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTeamEvent() throws Exception {
        int databaseSizeBeforeUpdate = teamEventRepository.findAll().size();
        teamEvent.setId(count.incrementAndGet());

        // Create the TeamEvent
        TeamEventDTO teamEventDTO = teamEventMapper.toDto(teamEvent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamEventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamEvent in the database
        List<TeamEvent> teamEventList = teamEventRepository.findAll();
        assertThat(teamEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTeamEvent() throws Exception {
        int databaseSizeBeforeUpdate = teamEventRepository.findAll().size();
        teamEvent.setId(count.incrementAndGet());

        // Create the TeamEvent
        TeamEventDTO teamEventDTO = teamEventMapper.toDto(teamEvent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamEventMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teamEventDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TeamEvent in the database
        List<TeamEvent> teamEventList = teamEventRepository.findAll();
        assertThat(teamEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTeamEventWithPatch() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        int databaseSizeBeforeUpdate = teamEventRepository.findAll().size();

        // Update the teamEvent using partial update
        TeamEvent partialUpdatedTeamEvent = new TeamEvent();
        partialUpdatedTeamEvent.setId(teamEvent.getId());

        restTeamEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeamEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeamEvent))
            )
            .andExpect(status().isOk());

        // Validate the TeamEvent in the database
        List<TeamEvent> teamEventList = teamEventRepository.findAll();
        assertThat(teamEventList).hasSize(databaseSizeBeforeUpdate);
        TeamEvent testTeamEvent = teamEventList.get(teamEventList.size() - 1);
        assertThat(testTeamEvent.gettId()).isEqualTo(DEFAULT_T_ID);
        assertThat(testTeamEvent.geteId()).isEqualTo(DEFAULT_E_ID);
        assertThat(testTeamEvent.getLstMtnUsr()).isEqualTo(DEFAULT_LST_MTN_USR);
        assertThat(testTeamEvent.getLstMtnDt()).isEqualTo(DEFAULT_LST_MTN_DT);
    }

    @Test
    @Transactional
    void fullUpdateTeamEventWithPatch() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        int databaseSizeBeforeUpdate = teamEventRepository.findAll().size();

        // Update the teamEvent using partial update
        TeamEvent partialUpdatedTeamEvent = new TeamEvent();
        partialUpdatedTeamEvent.setId(teamEvent.getId());

        partialUpdatedTeamEvent.tId(UPDATED_T_ID).eId(UPDATED_E_ID).lstMtnUsr(UPDATED_LST_MTN_USR).lstMtnDt(UPDATED_LST_MTN_DT);

        restTeamEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeamEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeamEvent))
            )
            .andExpect(status().isOk());

        // Validate the TeamEvent in the database
        List<TeamEvent> teamEventList = teamEventRepository.findAll();
        assertThat(teamEventList).hasSize(databaseSizeBeforeUpdate);
        TeamEvent testTeamEvent = teamEventList.get(teamEventList.size() - 1);
        assertThat(testTeamEvent.gettId()).isEqualTo(UPDATED_T_ID);
        assertThat(testTeamEvent.geteId()).isEqualTo(UPDATED_E_ID);
        assertThat(testTeamEvent.getLstMtnUsr()).isEqualTo(UPDATED_LST_MTN_USR);
        assertThat(testTeamEvent.getLstMtnDt()).isEqualTo(UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void patchNonExistingTeamEvent() throws Exception {
        int databaseSizeBeforeUpdate = teamEventRepository.findAll().size();
        teamEvent.setId(count.incrementAndGet());

        // Create the TeamEvent
        TeamEventDTO teamEventDTO = teamEventMapper.toDto(teamEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, teamEventDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamEventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamEvent in the database
        List<TeamEvent> teamEventList = teamEventRepository.findAll();
        assertThat(teamEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTeamEvent() throws Exception {
        int databaseSizeBeforeUpdate = teamEventRepository.findAll().size();
        teamEvent.setId(count.incrementAndGet());

        // Create the TeamEvent
        TeamEventDTO teamEventDTO = teamEventMapper.toDto(teamEvent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamEventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamEvent in the database
        List<TeamEvent> teamEventList = teamEventRepository.findAll();
        assertThat(teamEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTeamEvent() throws Exception {
        int databaseSizeBeforeUpdate = teamEventRepository.findAll().size();
        teamEvent.setId(count.incrementAndGet());

        // Create the TeamEvent
        TeamEventDTO teamEventDTO = teamEventMapper.toDto(teamEvent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamEventMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(teamEventDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TeamEvent in the database
        List<TeamEvent> teamEventList = teamEventRepository.findAll();
        assertThat(teamEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTeamEvent() throws Exception {
        // Initialize the database
        teamEventRepository.saveAndFlush(teamEvent);

        int databaseSizeBeforeDelete = teamEventRepository.findAll().size();

        // Delete the teamEvent
        restTeamEventMockMvc
            .perform(delete(ENTITY_API_URL_ID, teamEvent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TeamEvent> teamEventList = teamEventRepository.findAll();
        assertThat(teamEventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
