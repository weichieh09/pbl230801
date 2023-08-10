package com.wcc.pbl230801.web.rest;

import static com.wcc.pbl230801.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.wcc.pbl230801.IntegrationTest;
import com.wcc.pbl230801.domain.TeamPlayer;
import com.wcc.pbl230801.repository.TeamPlayerRepository;
import com.wcc.pbl230801.service.criteria.TeamPlayerCriteria;
import com.wcc.pbl230801.service.dto.TeamPlayerDTO;
import com.wcc.pbl230801.service.mapper.TeamPlayerMapper;
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
 * Integration tests for the {@link TeamPlayerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TeamPlayerResourceIT {

    private static final Long DEFAULT_T_ID = 1L;
    private static final Long UPDATED_T_ID = 2L;
    private static final Long SMALLER_T_ID = 1L - 1L;

    private static final Long DEFAULT_P_ID = 1L;
    private static final Long UPDATED_P_ID = 2L;
    private static final Long SMALLER_P_ID = 1L - 1L;

    private static final String DEFAULT_LST_MTN_USR = "AAAAAAAAAA";
    private static final String UPDATED_LST_MTN_USR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LST_MTN_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LST_MTN_DT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LST_MTN_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/team-players";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TeamPlayerRepository teamPlayerRepository;

    @Autowired
    private TeamPlayerMapper teamPlayerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTeamPlayerMockMvc;

    private TeamPlayer teamPlayer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamPlayer createEntity(EntityManager em) {
        TeamPlayer teamPlayer = new TeamPlayer()
            .tId(DEFAULT_T_ID)
            .pId(DEFAULT_P_ID)
            .lstMtnUsr(DEFAULT_LST_MTN_USR)
            .lstMtnDt(DEFAULT_LST_MTN_DT);
        return teamPlayer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamPlayer createUpdatedEntity(EntityManager em) {
        TeamPlayer teamPlayer = new TeamPlayer()
            .tId(UPDATED_T_ID)
            .pId(UPDATED_P_ID)
            .lstMtnUsr(UPDATED_LST_MTN_USR)
            .lstMtnDt(UPDATED_LST_MTN_DT);
        return teamPlayer;
    }

    @BeforeEach
    public void initTest() {
        teamPlayer = createEntity(em);
    }

    @Test
    @Transactional
    void createTeamPlayer() throws Exception {
        int databaseSizeBeforeCreate = teamPlayerRepository.findAll().size();
        // Create the TeamPlayer
        TeamPlayerDTO teamPlayerDTO = teamPlayerMapper.toDto(teamPlayer);
        restTeamPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teamPlayerDTO)))
            .andExpect(status().isCreated());

        // Validate the TeamPlayer in the database
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeCreate + 1);
        TeamPlayer testTeamPlayer = teamPlayerList.get(teamPlayerList.size() - 1);
        assertThat(testTeamPlayer.gettId()).isEqualTo(DEFAULT_T_ID);
        assertThat(testTeamPlayer.getpId()).isEqualTo(DEFAULT_P_ID);
        assertThat(testTeamPlayer.getLstMtnUsr()).isEqualTo(DEFAULT_LST_MTN_USR);
        assertThat(testTeamPlayer.getLstMtnDt()).isEqualTo(DEFAULT_LST_MTN_DT);
    }

    @Test
    @Transactional
    void createTeamPlayerWithExistingId() throws Exception {
        // Create the TeamPlayer with an existing ID
        teamPlayer.setId(1L);
        TeamPlayerDTO teamPlayerDTO = teamPlayerMapper.toDto(teamPlayer);

        int databaseSizeBeforeCreate = teamPlayerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teamPlayerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TeamPlayer in the database
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTeamPlayers() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList
        restTeamPlayerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamPlayer.getId().intValue())))
            .andExpect(jsonPath("$.[*].tId").value(hasItem(DEFAULT_T_ID.intValue())))
            .andExpect(jsonPath("$.[*].pId").value(hasItem(DEFAULT_P_ID.intValue())))
            .andExpect(jsonPath("$.[*].lstMtnUsr").value(hasItem(DEFAULT_LST_MTN_USR)))
            .andExpect(jsonPath("$.[*].lstMtnDt").value(hasItem(sameInstant(DEFAULT_LST_MTN_DT))));
    }

    @Test
    @Transactional
    void getTeamPlayer() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get the teamPlayer
        restTeamPlayerMockMvc
            .perform(get(ENTITY_API_URL_ID, teamPlayer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(teamPlayer.getId().intValue()))
            .andExpect(jsonPath("$.tId").value(DEFAULT_T_ID.intValue()))
            .andExpect(jsonPath("$.pId").value(DEFAULT_P_ID.intValue()))
            .andExpect(jsonPath("$.lstMtnUsr").value(DEFAULT_LST_MTN_USR))
            .andExpect(jsonPath("$.lstMtnDt").value(sameInstant(DEFAULT_LST_MTN_DT)));
    }

    @Test
    @Transactional
    void getTeamPlayersByIdFiltering() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        Long id = teamPlayer.getId();

        defaultTeamPlayerShouldBeFound("id.equals=" + id);
        defaultTeamPlayerShouldNotBeFound("id.notEquals=" + id);

        defaultTeamPlayerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTeamPlayerShouldNotBeFound("id.greaterThan=" + id);

        defaultTeamPlayerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTeamPlayerShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTeamPlayersBytIdIsEqualToSomething() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where tId equals to DEFAULT_T_ID
        defaultTeamPlayerShouldBeFound("tId.equals=" + DEFAULT_T_ID);

        // Get all the teamPlayerList where tId equals to UPDATED_T_ID
        defaultTeamPlayerShouldNotBeFound("tId.equals=" + UPDATED_T_ID);
    }

    @Test
    @Transactional
    void getAllTeamPlayersBytIdIsInShouldWork() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where tId in DEFAULT_T_ID or UPDATED_T_ID
        defaultTeamPlayerShouldBeFound("tId.in=" + DEFAULT_T_ID + "," + UPDATED_T_ID);

        // Get all the teamPlayerList where tId equals to UPDATED_T_ID
        defaultTeamPlayerShouldNotBeFound("tId.in=" + UPDATED_T_ID);
    }

    @Test
    @Transactional
    void getAllTeamPlayersBytIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where tId is not null
        defaultTeamPlayerShouldBeFound("tId.specified=true");

        // Get all the teamPlayerList where tId is null
        defaultTeamPlayerShouldNotBeFound("tId.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamPlayersBytIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where tId is greater than or equal to DEFAULT_T_ID
        defaultTeamPlayerShouldBeFound("tId.greaterThanOrEqual=" + DEFAULT_T_ID);

        // Get all the teamPlayerList where tId is greater than or equal to UPDATED_T_ID
        defaultTeamPlayerShouldNotBeFound("tId.greaterThanOrEqual=" + UPDATED_T_ID);
    }

    @Test
    @Transactional
    void getAllTeamPlayersBytIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where tId is less than or equal to DEFAULT_T_ID
        defaultTeamPlayerShouldBeFound("tId.lessThanOrEqual=" + DEFAULT_T_ID);

        // Get all the teamPlayerList where tId is less than or equal to SMALLER_T_ID
        defaultTeamPlayerShouldNotBeFound("tId.lessThanOrEqual=" + SMALLER_T_ID);
    }

    @Test
    @Transactional
    void getAllTeamPlayersBytIdIsLessThanSomething() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where tId is less than DEFAULT_T_ID
        defaultTeamPlayerShouldNotBeFound("tId.lessThan=" + DEFAULT_T_ID);

        // Get all the teamPlayerList where tId is less than UPDATED_T_ID
        defaultTeamPlayerShouldBeFound("tId.lessThan=" + UPDATED_T_ID);
    }

    @Test
    @Transactional
    void getAllTeamPlayersBytIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where tId is greater than DEFAULT_T_ID
        defaultTeamPlayerShouldNotBeFound("tId.greaterThan=" + DEFAULT_T_ID);

        // Get all the teamPlayerList where tId is greater than SMALLER_T_ID
        defaultTeamPlayerShouldBeFound("tId.greaterThan=" + SMALLER_T_ID);
    }

    @Test
    @Transactional
    void getAllTeamPlayersBypIdIsEqualToSomething() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where pId equals to DEFAULT_P_ID
        defaultTeamPlayerShouldBeFound("pId.equals=" + DEFAULT_P_ID);

        // Get all the teamPlayerList where pId equals to UPDATED_P_ID
        defaultTeamPlayerShouldNotBeFound("pId.equals=" + UPDATED_P_ID);
    }

    @Test
    @Transactional
    void getAllTeamPlayersBypIdIsInShouldWork() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where pId in DEFAULT_P_ID or UPDATED_P_ID
        defaultTeamPlayerShouldBeFound("pId.in=" + DEFAULT_P_ID + "," + UPDATED_P_ID);

        // Get all the teamPlayerList where pId equals to UPDATED_P_ID
        defaultTeamPlayerShouldNotBeFound("pId.in=" + UPDATED_P_ID);
    }

    @Test
    @Transactional
    void getAllTeamPlayersBypIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where pId is not null
        defaultTeamPlayerShouldBeFound("pId.specified=true");

        // Get all the teamPlayerList where pId is null
        defaultTeamPlayerShouldNotBeFound("pId.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamPlayersBypIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where pId is greater than or equal to DEFAULT_P_ID
        defaultTeamPlayerShouldBeFound("pId.greaterThanOrEqual=" + DEFAULT_P_ID);

        // Get all the teamPlayerList where pId is greater than or equal to UPDATED_P_ID
        defaultTeamPlayerShouldNotBeFound("pId.greaterThanOrEqual=" + UPDATED_P_ID);
    }

    @Test
    @Transactional
    void getAllTeamPlayersBypIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where pId is less than or equal to DEFAULT_P_ID
        defaultTeamPlayerShouldBeFound("pId.lessThanOrEqual=" + DEFAULT_P_ID);

        // Get all the teamPlayerList where pId is less than or equal to SMALLER_P_ID
        defaultTeamPlayerShouldNotBeFound("pId.lessThanOrEqual=" + SMALLER_P_ID);
    }

    @Test
    @Transactional
    void getAllTeamPlayersBypIdIsLessThanSomething() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where pId is less than DEFAULT_P_ID
        defaultTeamPlayerShouldNotBeFound("pId.lessThan=" + DEFAULT_P_ID);

        // Get all the teamPlayerList where pId is less than UPDATED_P_ID
        defaultTeamPlayerShouldBeFound("pId.lessThan=" + UPDATED_P_ID);
    }

    @Test
    @Transactional
    void getAllTeamPlayersBypIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where pId is greater than DEFAULT_P_ID
        defaultTeamPlayerShouldNotBeFound("pId.greaterThan=" + DEFAULT_P_ID);

        // Get all the teamPlayerList where pId is greater than SMALLER_P_ID
        defaultTeamPlayerShouldBeFound("pId.greaterThan=" + SMALLER_P_ID);
    }

    @Test
    @Transactional
    void getAllTeamPlayersByLstMtnUsrIsEqualToSomething() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where lstMtnUsr equals to DEFAULT_LST_MTN_USR
        defaultTeamPlayerShouldBeFound("lstMtnUsr.equals=" + DEFAULT_LST_MTN_USR);

        // Get all the teamPlayerList where lstMtnUsr equals to UPDATED_LST_MTN_USR
        defaultTeamPlayerShouldNotBeFound("lstMtnUsr.equals=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllTeamPlayersByLstMtnUsrIsInShouldWork() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where lstMtnUsr in DEFAULT_LST_MTN_USR or UPDATED_LST_MTN_USR
        defaultTeamPlayerShouldBeFound("lstMtnUsr.in=" + DEFAULT_LST_MTN_USR + "," + UPDATED_LST_MTN_USR);

        // Get all the teamPlayerList where lstMtnUsr equals to UPDATED_LST_MTN_USR
        defaultTeamPlayerShouldNotBeFound("lstMtnUsr.in=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllTeamPlayersByLstMtnUsrIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where lstMtnUsr is not null
        defaultTeamPlayerShouldBeFound("lstMtnUsr.specified=true");

        // Get all the teamPlayerList where lstMtnUsr is null
        defaultTeamPlayerShouldNotBeFound("lstMtnUsr.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamPlayersByLstMtnUsrContainsSomething() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where lstMtnUsr contains DEFAULT_LST_MTN_USR
        defaultTeamPlayerShouldBeFound("lstMtnUsr.contains=" + DEFAULT_LST_MTN_USR);

        // Get all the teamPlayerList where lstMtnUsr contains UPDATED_LST_MTN_USR
        defaultTeamPlayerShouldNotBeFound("lstMtnUsr.contains=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllTeamPlayersByLstMtnUsrNotContainsSomething() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where lstMtnUsr does not contain DEFAULT_LST_MTN_USR
        defaultTeamPlayerShouldNotBeFound("lstMtnUsr.doesNotContain=" + DEFAULT_LST_MTN_USR);

        // Get all the teamPlayerList where lstMtnUsr does not contain UPDATED_LST_MTN_USR
        defaultTeamPlayerShouldBeFound("lstMtnUsr.doesNotContain=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllTeamPlayersByLstMtnDtIsEqualToSomething() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where lstMtnDt equals to DEFAULT_LST_MTN_DT
        defaultTeamPlayerShouldBeFound("lstMtnDt.equals=" + DEFAULT_LST_MTN_DT);

        // Get all the teamPlayerList where lstMtnDt equals to UPDATED_LST_MTN_DT
        defaultTeamPlayerShouldNotBeFound("lstMtnDt.equals=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllTeamPlayersByLstMtnDtIsInShouldWork() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where lstMtnDt in DEFAULT_LST_MTN_DT or UPDATED_LST_MTN_DT
        defaultTeamPlayerShouldBeFound("lstMtnDt.in=" + DEFAULT_LST_MTN_DT + "," + UPDATED_LST_MTN_DT);

        // Get all the teamPlayerList where lstMtnDt equals to UPDATED_LST_MTN_DT
        defaultTeamPlayerShouldNotBeFound("lstMtnDt.in=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllTeamPlayersByLstMtnDtIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where lstMtnDt is not null
        defaultTeamPlayerShouldBeFound("lstMtnDt.specified=true");

        // Get all the teamPlayerList where lstMtnDt is null
        defaultTeamPlayerShouldNotBeFound("lstMtnDt.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamPlayersByLstMtnDtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where lstMtnDt is greater than or equal to DEFAULT_LST_MTN_DT
        defaultTeamPlayerShouldBeFound("lstMtnDt.greaterThanOrEqual=" + DEFAULT_LST_MTN_DT);

        // Get all the teamPlayerList where lstMtnDt is greater than or equal to UPDATED_LST_MTN_DT
        defaultTeamPlayerShouldNotBeFound("lstMtnDt.greaterThanOrEqual=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllTeamPlayersByLstMtnDtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where lstMtnDt is less than or equal to DEFAULT_LST_MTN_DT
        defaultTeamPlayerShouldBeFound("lstMtnDt.lessThanOrEqual=" + DEFAULT_LST_MTN_DT);

        // Get all the teamPlayerList where lstMtnDt is less than or equal to SMALLER_LST_MTN_DT
        defaultTeamPlayerShouldNotBeFound("lstMtnDt.lessThanOrEqual=" + SMALLER_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllTeamPlayersByLstMtnDtIsLessThanSomething() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where lstMtnDt is less than DEFAULT_LST_MTN_DT
        defaultTeamPlayerShouldNotBeFound("lstMtnDt.lessThan=" + DEFAULT_LST_MTN_DT);

        // Get all the teamPlayerList where lstMtnDt is less than UPDATED_LST_MTN_DT
        defaultTeamPlayerShouldBeFound("lstMtnDt.lessThan=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllTeamPlayersByLstMtnDtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList where lstMtnDt is greater than DEFAULT_LST_MTN_DT
        defaultTeamPlayerShouldNotBeFound("lstMtnDt.greaterThan=" + DEFAULT_LST_MTN_DT);

        // Get all the teamPlayerList where lstMtnDt is greater than SMALLER_LST_MTN_DT
        defaultTeamPlayerShouldBeFound("lstMtnDt.greaterThan=" + SMALLER_LST_MTN_DT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTeamPlayerShouldBeFound(String filter) throws Exception {
        restTeamPlayerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamPlayer.getId().intValue())))
            .andExpect(jsonPath("$.[*].tId").value(hasItem(DEFAULT_T_ID.intValue())))
            .andExpect(jsonPath("$.[*].pId").value(hasItem(DEFAULT_P_ID.intValue())))
            .andExpect(jsonPath("$.[*].lstMtnUsr").value(hasItem(DEFAULT_LST_MTN_USR)))
            .andExpect(jsonPath("$.[*].lstMtnDt").value(hasItem(sameInstant(DEFAULT_LST_MTN_DT))));

        // Check, that the count call also returns 1
        restTeamPlayerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTeamPlayerShouldNotBeFound(String filter) throws Exception {
        restTeamPlayerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTeamPlayerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTeamPlayer() throws Exception {
        // Get the teamPlayer
        restTeamPlayerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTeamPlayer() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        int databaseSizeBeforeUpdate = teamPlayerRepository.findAll().size();

        // Update the teamPlayer
        TeamPlayer updatedTeamPlayer = teamPlayerRepository.findById(teamPlayer.getId()).get();
        // Disconnect from session so that the updates on updatedTeamPlayer are not directly saved in db
        em.detach(updatedTeamPlayer);
        updatedTeamPlayer.tId(UPDATED_T_ID).pId(UPDATED_P_ID).lstMtnUsr(UPDATED_LST_MTN_USR).lstMtnDt(UPDATED_LST_MTN_DT);
        TeamPlayerDTO teamPlayerDTO = teamPlayerMapper.toDto(updatedTeamPlayer);

        restTeamPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teamPlayerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamPlayerDTO))
            )
            .andExpect(status().isOk());

        // Validate the TeamPlayer in the database
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeUpdate);
        TeamPlayer testTeamPlayer = teamPlayerList.get(teamPlayerList.size() - 1);
        assertThat(testTeamPlayer.gettId()).isEqualTo(UPDATED_T_ID);
        assertThat(testTeamPlayer.getpId()).isEqualTo(UPDATED_P_ID);
        assertThat(testTeamPlayer.getLstMtnUsr()).isEqualTo(UPDATED_LST_MTN_USR);
        assertThat(testTeamPlayer.getLstMtnDt()).isEqualTo(UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void putNonExistingTeamPlayer() throws Exception {
        int databaseSizeBeforeUpdate = teamPlayerRepository.findAll().size();
        teamPlayer.setId(count.incrementAndGet());

        // Create the TeamPlayer
        TeamPlayerDTO teamPlayerDTO = teamPlayerMapper.toDto(teamPlayer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teamPlayerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamPlayerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamPlayer in the database
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTeamPlayer() throws Exception {
        int databaseSizeBeforeUpdate = teamPlayerRepository.findAll().size();
        teamPlayer.setId(count.incrementAndGet());

        // Create the TeamPlayer
        TeamPlayerDTO teamPlayerDTO = teamPlayerMapper.toDto(teamPlayer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamPlayerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamPlayer in the database
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTeamPlayer() throws Exception {
        int databaseSizeBeforeUpdate = teamPlayerRepository.findAll().size();
        teamPlayer.setId(count.incrementAndGet());

        // Create the TeamPlayer
        TeamPlayerDTO teamPlayerDTO = teamPlayerMapper.toDto(teamPlayer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamPlayerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teamPlayerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TeamPlayer in the database
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTeamPlayerWithPatch() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        int databaseSizeBeforeUpdate = teamPlayerRepository.findAll().size();

        // Update the teamPlayer using partial update
        TeamPlayer partialUpdatedTeamPlayer = new TeamPlayer();
        partialUpdatedTeamPlayer.setId(teamPlayer.getId());

        partialUpdatedTeamPlayer.tId(UPDATED_T_ID).pId(UPDATED_P_ID).lstMtnUsr(UPDATED_LST_MTN_USR).lstMtnDt(UPDATED_LST_MTN_DT);

        restTeamPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeamPlayer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeamPlayer))
            )
            .andExpect(status().isOk());

        // Validate the TeamPlayer in the database
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeUpdate);
        TeamPlayer testTeamPlayer = teamPlayerList.get(teamPlayerList.size() - 1);
        assertThat(testTeamPlayer.gettId()).isEqualTo(UPDATED_T_ID);
        assertThat(testTeamPlayer.getpId()).isEqualTo(UPDATED_P_ID);
        assertThat(testTeamPlayer.getLstMtnUsr()).isEqualTo(UPDATED_LST_MTN_USR);
        assertThat(testTeamPlayer.getLstMtnDt()).isEqualTo(UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void fullUpdateTeamPlayerWithPatch() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        int databaseSizeBeforeUpdate = teamPlayerRepository.findAll().size();

        // Update the teamPlayer using partial update
        TeamPlayer partialUpdatedTeamPlayer = new TeamPlayer();
        partialUpdatedTeamPlayer.setId(teamPlayer.getId());

        partialUpdatedTeamPlayer.tId(UPDATED_T_ID).pId(UPDATED_P_ID).lstMtnUsr(UPDATED_LST_MTN_USR).lstMtnDt(UPDATED_LST_MTN_DT);

        restTeamPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeamPlayer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeamPlayer))
            )
            .andExpect(status().isOk());

        // Validate the TeamPlayer in the database
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeUpdate);
        TeamPlayer testTeamPlayer = teamPlayerList.get(teamPlayerList.size() - 1);
        assertThat(testTeamPlayer.gettId()).isEqualTo(UPDATED_T_ID);
        assertThat(testTeamPlayer.getpId()).isEqualTo(UPDATED_P_ID);
        assertThat(testTeamPlayer.getLstMtnUsr()).isEqualTo(UPDATED_LST_MTN_USR);
        assertThat(testTeamPlayer.getLstMtnDt()).isEqualTo(UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void patchNonExistingTeamPlayer() throws Exception {
        int databaseSizeBeforeUpdate = teamPlayerRepository.findAll().size();
        teamPlayer.setId(count.incrementAndGet());

        // Create the TeamPlayer
        TeamPlayerDTO teamPlayerDTO = teamPlayerMapper.toDto(teamPlayer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, teamPlayerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamPlayerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamPlayer in the database
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTeamPlayer() throws Exception {
        int databaseSizeBeforeUpdate = teamPlayerRepository.findAll().size();
        teamPlayer.setId(count.incrementAndGet());

        // Create the TeamPlayer
        TeamPlayerDTO teamPlayerDTO = teamPlayerMapper.toDto(teamPlayer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamPlayerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamPlayer in the database
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTeamPlayer() throws Exception {
        int databaseSizeBeforeUpdate = teamPlayerRepository.findAll().size();
        teamPlayer.setId(count.incrementAndGet());

        // Create the TeamPlayer
        TeamPlayerDTO teamPlayerDTO = teamPlayerMapper.toDto(teamPlayer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(teamPlayerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TeamPlayer in the database
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTeamPlayer() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        int databaseSizeBeforeDelete = teamPlayerRepository.findAll().size();

        // Delete the teamPlayer
        restTeamPlayerMockMvc
            .perform(delete(ENTITY_API_URL_ID, teamPlayer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
