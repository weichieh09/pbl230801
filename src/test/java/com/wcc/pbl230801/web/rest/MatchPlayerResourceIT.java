package com.wcc.pbl230801.web.rest;

import static com.wcc.pbl230801.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.wcc.pbl230801.IntegrationTest;
import com.wcc.pbl230801.domain.MatchPlayer;
import com.wcc.pbl230801.repository.MatchPlayerRepository;
import com.wcc.pbl230801.service.criteria.MatchPlayerCriteria;
import com.wcc.pbl230801.service.dto.MatchPlayerDTO;
import com.wcc.pbl230801.service.mapper.MatchPlayerMapper;
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
 * Integration tests for the {@link MatchPlayerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MatchPlayerResourceIT {

    private static final Long DEFAULT_M_ID = 1L;
    private static final Long UPDATED_M_ID = 2L;
    private static final Long SMALLER_M_ID = 1L - 1L;

    private static final Long DEFAULT_P_ID = 1L;
    private static final Long UPDATED_P_ID = 2L;
    private static final Long SMALLER_P_ID = 1L - 1L;

    private static final Long DEFAULT_E_ID = 1L;
    private static final Long UPDATED_E_ID = 2L;
    private static final Long SMALLER_E_ID = 1L - 1L;

    private static final ZonedDateTime DEFAULT_MTCH_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MTCH_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_MTCH_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_SCORE = "AAAAAAAAAA";
    private static final String UPDATED_SCORE = "BBBBBBBBBB";

    private static final String DEFAULT_WIN_FG = "AAAAAAAAAA";
    private static final String UPDATED_WIN_FG = "BBBBBBBBBB";

    private static final String DEFAULT_LST_MTN_USR = "AAAAAAAAAA";
    private static final String UPDATED_LST_MTN_USR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LST_MTN_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LST_MTN_DT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LST_MTN_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/match-players";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MatchPlayerRepository matchPlayerRepository;

    @Autowired
    private MatchPlayerMapper matchPlayerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatchPlayerMockMvc;

    private MatchPlayer matchPlayer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchPlayer createEntity(EntityManager em) {
        MatchPlayer matchPlayer = new MatchPlayer()
            .mId(DEFAULT_M_ID)
            .pId(DEFAULT_P_ID)
            .eId(DEFAULT_E_ID)
            .mtchEndTime(DEFAULT_MTCH_END_TIME)
            .score(DEFAULT_SCORE)
            .winFg(DEFAULT_WIN_FG)
            .lstMtnUsr(DEFAULT_LST_MTN_USR)
            .lstMtnDt(DEFAULT_LST_MTN_DT);
        return matchPlayer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchPlayer createUpdatedEntity(EntityManager em) {
        MatchPlayer matchPlayer = new MatchPlayer()
            .mId(UPDATED_M_ID)
            .pId(UPDATED_P_ID)
            .eId(UPDATED_E_ID)
            .mtchEndTime(UPDATED_MTCH_END_TIME)
            .score(UPDATED_SCORE)
            .winFg(UPDATED_WIN_FG)
            .lstMtnUsr(UPDATED_LST_MTN_USR)
            .lstMtnDt(UPDATED_LST_MTN_DT);
        return matchPlayer;
    }

    @BeforeEach
    public void initTest() {
        matchPlayer = createEntity(em);
    }

    @Test
    @Transactional
    void createMatchPlayer() throws Exception {
        int databaseSizeBeforeCreate = matchPlayerRepository.findAll().size();
        // Create the MatchPlayer
        MatchPlayerDTO matchPlayerDTO = matchPlayerMapper.toDto(matchPlayer);
        restMatchPlayerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(matchPlayerDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MatchPlayer in the database
        List<MatchPlayer> matchPlayerList = matchPlayerRepository.findAll();
        assertThat(matchPlayerList).hasSize(databaseSizeBeforeCreate + 1);
        MatchPlayer testMatchPlayer = matchPlayerList.get(matchPlayerList.size() - 1);
        assertThat(testMatchPlayer.getmId()).isEqualTo(DEFAULT_M_ID);
        assertThat(testMatchPlayer.getpId()).isEqualTo(DEFAULT_P_ID);
        assertThat(testMatchPlayer.geteId()).isEqualTo(DEFAULT_E_ID);
        assertThat(testMatchPlayer.getMtchEndTime()).isEqualTo(DEFAULT_MTCH_END_TIME);
        assertThat(testMatchPlayer.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testMatchPlayer.getWinFg()).isEqualTo(DEFAULT_WIN_FG);
        assertThat(testMatchPlayer.getLstMtnUsr()).isEqualTo(DEFAULT_LST_MTN_USR);
        assertThat(testMatchPlayer.getLstMtnDt()).isEqualTo(DEFAULT_LST_MTN_DT);
    }

    @Test
    @Transactional
    void createMatchPlayerWithExistingId() throws Exception {
        // Create the MatchPlayer with an existing ID
        matchPlayer.setId(1L);
        MatchPlayerDTO matchPlayerDTO = matchPlayerMapper.toDto(matchPlayer);

        int databaseSizeBeforeCreate = matchPlayerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchPlayerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(matchPlayerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchPlayer in the database
        List<MatchPlayer> matchPlayerList = matchPlayerRepository.findAll();
        assertThat(matchPlayerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMatchPlayers() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList
        restMatchPlayerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchPlayer.getId().intValue())))
            .andExpect(jsonPath("$.[*].mId").value(hasItem(DEFAULT_M_ID.intValue())))
            .andExpect(jsonPath("$.[*].pId").value(hasItem(DEFAULT_P_ID.intValue())))
            .andExpect(jsonPath("$.[*].eId").value(hasItem(DEFAULT_E_ID.intValue())))
            .andExpect(jsonPath("$.[*].mtchEndTime").value(hasItem(sameInstant(DEFAULT_MTCH_END_TIME))))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].winFg").value(hasItem(DEFAULT_WIN_FG)))
            .andExpect(jsonPath("$.[*].lstMtnUsr").value(hasItem(DEFAULT_LST_MTN_USR)))
            .andExpect(jsonPath("$.[*].lstMtnDt").value(hasItem(sameInstant(DEFAULT_LST_MTN_DT))));
    }

    @Test
    @Transactional
    void getMatchPlayer() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get the matchPlayer
        restMatchPlayerMockMvc
            .perform(get(ENTITY_API_URL_ID, matchPlayer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matchPlayer.getId().intValue()))
            .andExpect(jsonPath("$.mId").value(DEFAULT_M_ID.intValue()))
            .andExpect(jsonPath("$.pId").value(DEFAULT_P_ID.intValue()))
            .andExpect(jsonPath("$.eId").value(DEFAULT_E_ID.intValue()))
            .andExpect(jsonPath("$.mtchEndTime").value(sameInstant(DEFAULT_MTCH_END_TIME)))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.winFg").value(DEFAULT_WIN_FG))
            .andExpect(jsonPath("$.lstMtnUsr").value(DEFAULT_LST_MTN_USR))
            .andExpect(jsonPath("$.lstMtnDt").value(sameInstant(DEFAULT_LST_MTN_DT)));
    }

    @Test
    @Transactional
    void getMatchPlayersByIdFiltering() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        Long id = matchPlayer.getId();

        defaultMatchPlayerShouldBeFound("id.equals=" + id);
        defaultMatchPlayerShouldNotBeFound("id.notEquals=" + id);

        defaultMatchPlayerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMatchPlayerShouldNotBeFound("id.greaterThan=" + id);

        defaultMatchPlayerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMatchPlayerShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMatchPlayersBymIdIsEqualToSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where mId equals to DEFAULT_M_ID
        defaultMatchPlayerShouldBeFound("mId.equals=" + DEFAULT_M_ID);

        // Get all the matchPlayerList where mId equals to UPDATED_M_ID
        defaultMatchPlayerShouldNotBeFound("mId.equals=" + UPDATED_M_ID);
    }

    @Test
    @Transactional
    void getAllMatchPlayersBymIdIsInShouldWork() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where mId in DEFAULT_M_ID or UPDATED_M_ID
        defaultMatchPlayerShouldBeFound("mId.in=" + DEFAULT_M_ID + "," + UPDATED_M_ID);

        // Get all the matchPlayerList where mId equals to UPDATED_M_ID
        defaultMatchPlayerShouldNotBeFound("mId.in=" + UPDATED_M_ID);
    }

    @Test
    @Transactional
    void getAllMatchPlayersBymIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where mId is not null
        defaultMatchPlayerShouldBeFound("mId.specified=true");

        // Get all the matchPlayerList where mId is null
        defaultMatchPlayerShouldNotBeFound("mId.specified=false");
    }

    @Test
    @Transactional
    void getAllMatchPlayersBymIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where mId is greater than or equal to DEFAULT_M_ID
        defaultMatchPlayerShouldBeFound("mId.greaterThanOrEqual=" + DEFAULT_M_ID);

        // Get all the matchPlayerList where mId is greater than or equal to UPDATED_M_ID
        defaultMatchPlayerShouldNotBeFound("mId.greaterThanOrEqual=" + UPDATED_M_ID);
    }

    @Test
    @Transactional
    void getAllMatchPlayersBymIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where mId is less than or equal to DEFAULT_M_ID
        defaultMatchPlayerShouldBeFound("mId.lessThanOrEqual=" + DEFAULT_M_ID);

        // Get all the matchPlayerList where mId is less than or equal to SMALLER_M_ID
        defaultMatchPlayerShouldNotBeFound("mId.lessThanOrEqual=" + SMALLER_M_ID);
    }

    @Test
    @Transactional
    void getAllMatchPlayersBymIdIsLessThanSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where mId is less than DEFAULT_M_ID
        defaultMatchPlayerShouldNotBeFound("mId.lessThan=" + DEFAULT_M_ID);

        // Get all the matchPlayerList where mId is less than UPDATED_M_ID
        defaultMatchPlayerShouldBeFound("mId.lessThan=" + UPDATED_M_ID);
    }

    @Test
    @Transactional
    void getAllMatchPlayersBymIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where mId is greater than DEFAULT_M_ID
        defaultMatchPlayerShouldNotBeFound("mId.greaterThan=" + DEFAULT_M_ID);

        // Get all the matchPlayerList where mId is greater than SMALLER_M_ID
        defaultMatchPlayerShouldBeFound("mId.greaterThan=" + SMALLER_M_ID);
    }

    @Test
    @Transactional
    void getAllMatchPlayersBypIdIsEqualToSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where pId equals to DEFAULT_P_ID
        defaultMatchPlayerShouldBeFound("pId.equals=" + DEFAULT_P_ID);

        // Get all the matchPlayerList where pId equals to UPDATED_P_ID
        defaultMatchPlayerShouldNotBeFound("pId.equals=" + UPDATED_P_ID);
    }

    @Test
    @Transactional
    void getAllMatchPlayersBypIdIsInShouldWork() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where pId in DEFAULT_P_ID or UPDATED_P_ID
        defaultMatchPlayerShouldBeFound("pId.in=" + DEFAULT_P_ID + "," + UPDATED_P_ID);

        // Get all the matchPlayerList where pId equals to UPDATED_P_ID
        defaultMatchPlayerShouldNotBeFound("pId.in=" + UPDATED_P_ID);
    }

    @Test
    @Transactional
    void getAllMatchPlayersBypIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where pId is not null
        defaultMatchPlayerShouldBeFound("pId.specified=true");

        // Get all the matchPlayerList where pId is null
        defaultMatchPlayerShouldNotBeFound("pId.specified=false");
    }

    @Test
    @Transactional
    void getAllMatchPlayersBypIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where pId is greater than or equal to DEFAULT_P_ID
        defaultMatchPlayerShouldBeFound("pId.greaterThanOrEqual=" + DEFAULT_P_ID);

        // Get all the matchPlayerList where pId is greater than or equal to UPDATED_P_ID
        defaultMatchPlayerShouldNotBeFound("pId.greaterThanOrEqual=" + UPDATED_P_ID);
    }

    @Test
    @Transactional
    void getAllMatchPlayersBypIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where pId is less than or equal to DEFAULT_P_ID
        defaultMatchPlayerShouldBeFound("pId.lessThanOrEqual=" + DEFAULT_P_ID);

        // Get all the matchPlayerList where pId is less than or equal to SMALLER_P_ID
        defaultMatchPlayerShouldNotBeFound("pId.lessThanOrEqual=" + SMALLER_P_ID);
    }

    @Test
    @Transactional
    void getAllMatchPlayersBypIdIsLessThanSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where pId is less than DEFAULT_P_ID
        defaultMatchPlayerShouldNotBeFound("pId.lessThan=" + DEFAULT_P_ID);

        // Get all the matchPlayerList where pId is less than UPDATED_P_ID
        defaultMatchPlayerShouldBeFound("pId.lessThan=" + UPDATED_P_ID);
    }

    @Test
    @Transactional
    void getAllMatchPlayersBypIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where pId is greater than DEFAULT_P_ID
        defaultMatchPlayerShouldNotBeFound("pId.greaterThan=" + DEFAULT_P_ID);

        // Get all the matchPlayerList where pId is greater than SMALLER_P_ID
        defaultMatchPlayerShouldBeFound("pId.greaterThan=" + SMALLER_P_ID);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where eId equals to DEFAULT_E_ID
        defaultMatchPlayerShouldBeFound("eId.equals=" + DEFAULT_E_ID);

        // Get all the matchPlayerList where eId equals to UPDATED_E_ID
        defaultMatchPlayerShouldNotBeFound("eId.equals=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByeIdIsInShouldWork() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where eId in DEFAULT_E_ID or UPDATED_E_ID
        defaultMatchPlayerShouldBeFound("eId.in=" + DEFAULT_E_ID + "," + UPDATED_E_ID);

        // Get all the matchPlayerList where eId equals to UPDATED_E_ID
        defaultMatchPlayerShouldNotBeFound("eId.in=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where eId is not null
        defaultMatchPlayerShouldBeFound("eId.specified=true");

        // Get all the matchPlayerList where eId is null
        defaultMatchPlayerShouldNotBeFound("eId.specified=false");
    }

    @Test
    @Transactional
    void getAllMatchPlayersByeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where eId is greater than or equal to DEFAULT_E_ID
        defaultMatchPlayerShouldBeFound("eId.greaterThanOrEqual=" + DEFAULT_E_ID);

        // Get all the matchPlayerList where eId is greater than or equal to UPDATED_E_ID
        defaultMatchPlayerShouldNotBeFound("eId.greaterThanOrEqual=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where eId is less than or equal to DEFAULT_E_ID
        defaultMatchPlayerShouldBeFound("eId.lessThanOrEqual=" + DEFAULT_E_ID);

        // Get all the matchPlayerList where eId is less than or equal to SMALLER_E_ID
        defaultMatchPlayerShouldNotBeFound("eId.lessThanOrEqual=" + SMALLER_E_ID);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where eId is less than DEFAULT_E_ID
        defaultMatchPlayerShouldNotBeFound("eId.lessThan=" + DEFAULT_E_ID);

        // Get all the matchPlayerList where eId is less than UPDATED_E_ID
        defaultMatchPlayerShouldBeFound("eId.lessThan=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where eId is greater than DEFAULT_E_ID
        defaultMatchPlayerShouldNotBeFound("eId.greaterThan=" + DEFAULT_E_ID);

        // Get all the matchPlayerList where eId is greater than SMALLER_E_ID
        defaultMatchPlayerShouldBeFound("eId.greaterThan=" + SMALLER_E_ID);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByMtchEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where mtchEndTime equals to DEFAULT_MTCH_END_TIME
        defaultMatchPlayerShouldBeFound("mtchEndTime.equals=" + DEFAULT_MTCH_END_TIME);

        // Get all the matchPlayerList where mtchEndTime equals to UPDATED_MTCH_END_TIME
        defaultMatchPlayerShouldNotBeFound("mtchEndTime.equals=" + UPDATED_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByMtchEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where mtchEndTime in DEFAULT_MTCH_END_TIME or UPDATED_MTCH_END_TIME
        defaultMatchPlayerShouldBeFound("mtchEndTime.in=" + DEFAULT_MTCH_END_TIME + "," + UPDATED_MTCH_END_TIME);

        // Get all the matchPlayerList where mtchEndTime equals to UPDATED_MTCH_END_TIME
        defaultMatchPlayerShouldNotBeFound("mtchEndTime.in=" + UPDATED_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByMtchEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where mtchEndTime is not null
        defaultMatchPlayerShouldBeFound("mtchEndTime.specified=true");

        // Get all the matchPlayerList where mtchEndTime is null
        defaultMatchPlayerShouldNotBeFound("mtchEndTime.specified=false");
    }

    @Test
    @Transactional
    void getAllMatchPlayersByMtchEndTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where mtchEndTime is greater than or equal to DEFAULT_MTCH_END_TIME
        defaultMatchPlayerShouldBeFound("mtchEndTime.greaterThanOrEqual=" + DEFAULT_MTCH_END_TIME);

        // Get all the matchPlayerList where mtchEndTime is greater than or equal to UPDATED_MTCH_END_TIME
        defaultMatchPlayerShouldNotBeFound("mtchEndTime.greaterThanOrEqual=" + UPDATED_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByMtchEndTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where mtchEndTime is less than or equal to DEFAULT_MTCH_END_TIME
        defaultMatchPlayerShouldBeFound("mtchEndTime.lessThanOrEqual=" + DEFAULT_MTCH_END_TIME);

        // Get all the matchPlayerList where mtchEndTime is less than or equal to SMALLER_MTCH_END_TIME
        defaultMatchPlayerShouldNotBeFound("mtchEndTime.lessThanOrEqual=" + SMALLER_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByMtchEndTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where mtchEndTime is less than DEFAULT_MTCH_END_TIME
        defaultMatchPlayerShouldNotBeFound("mtchEndTime.lessThan=" + DEFAULT_MTCH_END_TIME);

        // Get all the matchPlayerList where mtchEndTime is less than UPDATED_MTCH_END_TIME
        defaultMatchPlayerShouldBeFound("mtchEndTime.lessThan=" + UPDATED_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByMtchEndTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where mtchEndTime is greater than DEFAULT_MTCH_END_TIME
        defaultMatchPlayerShouldNotBeFound("mtchEndTime.greaterThan=" + DEFAULT_MTCH_END_TIME);

        // Get all the matchPlayerList where mtchEndTime is greater than SMALLER_MTCH_END_TIME
        defaultMatchPlayerShouldBeFound("mtchEndTime.greaterThan=" + SMALLER_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where score equals to DEFAULT_SCORE
        defaultMatchPlayerShouldBeFound("score.equals=" + DEFAULT_SCORE);

        // Get all the matchPlayerList where score equals to UPDATED_SCORE
        defaultMatchPlayerShouldNotBeFound("score.equals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByScoreIsInShouldWork() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where score in DEFAULT_SCORE or UPDATED_SCORE
        defaultMatchPlayerShouldBeFound("score.in=" + DEFAULT_SCORE + "," + UPDATED_SCORE);

        // Get all the matchPlayerList where score equals to UPDATED_SCORE
        defaultMatchPlayerShouldNotBeFound("score.in=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where score is not null
        defaultMatchPlayerShouldBeFound("score.specified=true");

        // Get all the matchPlayerList where score is null
        defaultMatchPlayerShouldNotBeFound("score.specified=false");
    }

    @Test
    @Transactional
    void getAllMatchPlayersByScoreContainsSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where score contains DEFAULT_SCORE
        defaultMatchPlayerShouldBeFound("score.contains=" + DEFAULT_SCORE);

        // Get all the matchPlayerList where score contains UPDATED_SCORE
        defaultMatchPlayerShouldNotBeFound("score.contains=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByScoreNotContainsSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where score does not contain DEFAULT_SCORE
        defaultMatchPlayerShouldNotBeFound("score.doesNotContain=" + DEFAULT_SCORE);

        // Get all the matchPlayerList where score does not contain UPDATED_SCORE
        defaultMatchPlayerShouldBeFound("score.doesNotContain=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByWinFgIsEqualToSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where winFg equals to DEFAULT_WIN_FG
        defaultMatchPlayerShouldBeFound("winFg.equals=" + DEFAULT_WIN_FG);

        // Get all the matchPlayerList where winFg equals to UPDATED_WIN_FG
        defaultMatchPlayerShouldNotBeFound("winFg.equals=" + UPDATED_WIN_FG);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByWinFgIsInShouldWork() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where winFg in DEFAULT_WIN_FG or UPDATED_WIN_FG
        defaultMatchPlayerShouldBeFound("winFg.in=" + DEFAULT_WIN_FG + "," + UPDATED_WIN_FG);

        // Get all the matchPlayerList where winFg equals to UPDATED_WIN_FG
        defaultMatchPlayerShouldNotBeFound("winFg.in=" + UPDATED_WIN_FG);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByWinFgIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where winFg is not null
        defaultMatchPlayerShouldBeFound("winFg.specified=true");

        // Get all the matchPlayerList where winFg is null
        defaultMatchPlayerShouldNotBeFound("winFg.specified=false");
    }

    @Test
    @Transactional
    void getAllMatchPlayersByWinFgContainsSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where winFg contains DEFAULT_WIN_FG
        defaultMatchPlayerShouldBeFound("winFg.contains=" + DEFAULT_WIN_FG);

        // Get all the matchPlayerList where winFg contains UPDATED_WIN_FG
        defaultMatchPlayerShouldNotBeFound("winFg.contains=" + UPDATED_WIN_FG);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByWinFgNotContainsSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where winFg does not contain DEFAULT_WIN_FG
        defaultMatchPlayerShouldNotBeFound("winFg.doesNotContain=" + DEFAULT_WIN_FG);

        // Get all the matchPlayerList where winFg does not contain UPDATED_WIN_FG
        defaultMatchPlayerShouldBeFound("winFg.doesNotContain=" + UPDATED_WIN_FG);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByLstMtnUsrIsEqualToSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where lstMtnUsr equals to DEFAULT_LST_MTN_USR
        defaultMatchPlayerShouldBeFound("lstMtnUsr.equals=" + DEFAULT_LST_MTN_USR);

        // Get all the matchPlayerList where lstMtnUsr equals to UPDATED_LST_MTN_USR
        defaultMatchPlayerShouldNotBeFound("lstMtnUsr.equals=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByLstMtnUsrIsInShouldWork() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where lstMtnUsr in DEFAULT_LST_MTN_USR or UPDATED_LST_MTN_USR
        defaultMatchPlayerShouldBeFound("lstMtnUsr.in=" + DEFAULT_LST_MTN_USR + "," + UPDATED_LST_MTN_USR);

        // Get all the matchPlayerList where lstMtnUsr equals to UPDATED_LST_MTN_USR
        defaultMatchPlayerShouldNotBeFound("lstMtnUsr.in=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByLstMtnUsrIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where lstMtnUsr is not null
        defaultMatchPlayerShouldBeFound("lstMtnUsr.specified=true");

        // Get all the matchPlayerList where lstMtnUsr is null
        defaultMatchPlayerShouldNotBeFound("lstMtnUsr.specified=false");
    }

    @Test
    @Transactional
    void getAllMatchPlayersByLstMtnUsrContainsSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where lstMtnUsr contains DEFAULT_LST_MTN_USR
        defaultMatchPlayerShouldBeFound("lstMtnUsr.contains=" + DEFAULT_LST_MTN_USR);

        // Get all the matchPlayerList where lstMtnUsr contains UPDATED_LST_MTN_USR
        defaultMatchPlayerShouldNotBeFound("lstMtnUsr.contains=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByLstMtnUsrNotContainsSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where lstMtnUsr does not contain DEFAULT_LST_MTN_USR
        defaultMatchPlayerShouldNotBeFound("lstMtnUsr.doesNotContain=" + DEFAULT_LST_MTN_USR);

        // Get all the matchPlayerList where lstMtnUsr does not contain UPDATED_LST_MTN_USR
        defaultMatchPlayerShouldBeFound("lstMtnUsr.doesNotContain=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByLstMtnDtIsEqualToSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where lstMtnDt equals to DEFAULT_LST_MTN_DT
        defaultMatchPlayerShouldBeFound("lstMtnDt.equals=" + DEFAULT_LST_MTN_DT);

        // Get all the matchPlayerList where lstMtnDt equals to UPDATED_LST_MTN_DT
        defaultMatchPlayerShouldNotBeFound("lstMtnDt.equals=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByLstMtnDtIsInShouldWork() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where lstMtnDt in DEFAULT_LST_MTN_DT or UPDATED_LST_MTN_DT
        defaultMatchPlayerShouldBeFound("lstMtnDt.in=" + DEFAULT_LST_MTN_DT + "," + UPDATED_LST_MTN_DT);

        // Get all the matchPlayerList where lstMtnDt equals to UPDATED_LST_MTN_DT
        defaultMatchPlayerShouldNotBeFound("lstMtnDt.in=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByLstMtnDtIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where lstMtnDt is not null
        defaultMatchPlayerShouldBeFound("lstMtnDt.specified=true");

        // Get all the matchPlayerList where lstMtnDt is null
        defaultMatchPlayerShouldNotBeFound("lstMtnDt.specified=false");
    }

    @Test
    @Transactional
    void getAllMatchPlayersByLstMtnDtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where lstMtnDt is greater than or equal to DEFAULT_LST_MTN_DT
        defaultMatchPlayerShouldBeFound("lstMtnDt.greaterThanOrEqual=" + DEFAULT_LST_MTN_DT);

        // Get all the matchPlayerList where lstMtnDt is greater than or equal to UPDATED_LST_MTN_DT
        defaultMatchPlayerShouldNotBeFound("lstMtnDt.greaterThanOrEqual=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByLstMtnDtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where lstMtnDt is less than or equal to DEFAULT_LST_MTN_DT
        defaultMatchPlayerShouldBeFound("lstMtnDt.lessThanOrEqual=" + DEFAULT_LST_MTN_DT);

        // Get all the matchPlayerList where lstMtnDt is less than or equal to SMALLER_LST_MTN_DT
        defaultMatchPlayerShouldNotBeFound("lstMtnDt.lessThanOrEqual=" + SMALLER_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByLstMtnDtIsLessThanSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where lstMtnDt is less than DEFAULT_LST_MTN_DT
        defaultMatchPlayerShouldNotBeFound("lstMtnDt.lessThan=" + DEFAULT_LST_MTN_DT);

        // Get all the matchPlayerList where lstMtnDt is less than UPDATED_LST_MTN_DT
        defaultMatchPlayerShouldBeFound("lstMtnDt.lessThan=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllMatchPlayersByLstMtnDtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        // Get all the matchPlayerList where lstMtnDt is greater than DEFAULT_LST_MTN_DT
        defaultMatchPlayerShouldNotBeFound("lstMtnDt.greaterThan=" + DEFAULT_LST_MTN_DT);

        // Get all the matchPlayerList where lstMtnDt is greater than SMALLER_LST_MTN_DT
        defaultMatchPlayerShouldBeFound("lstMtnDt.greaterThan=" + SMALLER_LST_MTN_DT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMatchPlayerShouldBeFound(String filter) throws Exception {
        restMatchPlayerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchPlayer.getId().intValue())))
            .andExpect(jsonPath("$.[*].mId").value(hasItem(DEFAULT_M_ID.intValue())))
            .andExpect(jsonPath("$.[*].pId").value(hasItem(DEFAULT_P_ID.intValue())))
            .andExpect(jsonPath("$.[*].eId").value(hasItem(DEFAULT_E_ID.intValue())))
            .andExpect(jsonPath("$.[*].mtchEndTime").value(hasItem(sameInstant(DEFAULT_MTCH_END_TIME))))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].winFg").value(hasItem(DEFAULT_WIN_FG)))
            .andExpect(jsonPath("$.[*].lstMtnUsr").value(hasItem(DEFAULT_LST_MTN_USR)))
            .andExpect(jsonPath("$.[*].lstMtnDt").value(hasItem(sameInstant(DEFAULT_LST_MTN_DT))));

        // Check, that the count call also returns 1
        restMatchPlayerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMatchPlayerShouldNotBeFound(String filter) throws Exception {
        restMatchPlayerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMatchPlayerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMatchPlayer() throws Exception {
        // Get the matchPlayer
        restMatchPlayerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMatchPlayer() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        int databaseSizeBeforeUpdate = matchPlayerRepository.findAll().size();

        // Update the matchPlayer
        MatchPlayer updatedMatchPlayer = matchPlayerRepository.findById(matchPlayer.getId()).get();
        // Disconnect from session so that the updates on updatedMatchPlayer are not directly saved in db
        em.detach(updatedMatchPlayer);
        updatedMatchPlayer
            .mId(UPDATED_M_ID)
            .pId(UPDATED_P_ID)
            .eId(UPDATED_E_ID)
            .mtchEndTime(UPDATED_MTCH_END_TIME)
            .score(UPDATED_SCORE)
            .winFg(UPDATED_WIN_FG)
            .lstMtnUsr(UPDATED_LST_MTN_USR)
            .lstMtnDt(UPDATED_LST_MTN_DT);
        MatchPlayerDTO matchPlayerDTO = matchPlayerMapper.toDto(updatedMatchPlayer);

        restMatchPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchPlayerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(matchPlayerDTO))
            )
            .andExpect(status().isOk());

        // Validate the MatchPlayer in the database
        List<MatchPlayer> matchPlayerList = matchPlayerRepository.findAll();
        assertThat(matchPlayerList).hasSize(databaseSizeBeforeUpdate);
        MatchPlayer testMatchPlayer = matchPlayerList.get(matchPlayerList.size() - 1);
        assertThat(testMatchPlayer.getmId()).isEqualTo(UPDATED_M_ID);
        assertThat(testMatchPlayer.getpId()).isEqualTo(UPDATED_P_ID);
        assertThat(testMatchPlayer.geteId()).isEqualTo(UPDATED_E_ID);
        assertThat(testMatchPlayer.getMtchEndTime()).isEqualTo(UPDATED_MTCH_END_TIME);
        assertThat(testMatchPlayer.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testMatchPlayer.getWinFg()).isEqualTo(UPDATED_WIN_FG);
        assertThat(testMatchPlayer.getLstMtnUsr()).isEqualTo(UPDATED_LST_MTN_USR);
        assertThat(testMatchPlayer.getLstMtnDt()).isEqualTo(UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void putNonExistingMatchPlayer() throws Exception {
        int databaseSizeBeforeUpdate = matchPlayerRepository.findAll().size();
        matchPlayer.setId(count.incrementAndGet());

        // Create the MatchPlayer
        MatchPlayerDTO matchPlayerDTO = matchPlayerMapper.toDto(matchPlayer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchPlayerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(matchPlayerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchPlayer in the database
        List<MatchPlayer> matchPlayerList = matchPlayerRepository.findAll();
        assertThat(matchPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMatchPlayer() throws Exception {
        int databaseSizeBeforeUpdate = matchPlayerRepository.findAll().size();
        matchPlayer.setId(count.incrementAndGet());

        // Create the MatchPlayer
        MatchPlayerDTO matchPlayerDTO = matchPlayerMapper.toDto(matchPlayer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(matchPlayerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchPlayer in the database
        List<MatchPlayer> matchPlayerList = matchPlayerRepository.findAll();
        assertThat(matchPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMatchPlayer() throws Exception {
        int databaseSizeBeforeUpdate = matchPlayerRepository.findAll().size();
        matchPlayer.setId(count.incrementAndGet());

        // Create the MatchPlayer
        MatchPlayerDTO matchPlayerDTO = matchPlayerMapper.toDto(matchPlayer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchPlayerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(matchPlayerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchPlayer in the database
        List<MatchPlayer> matchPlayerList = matchPlayerRepository.findAll();
        assertThat(matchPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMatchPlayerWithPatch() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        int databaseSizeBeforeUpdate = matchPlayerRepository.findAll().size();

        // Update the matchPlayer using partial update
        MatchPlayer partialUpdatedMatchPlayer = new MatchPlayer();
        partialUpdatedMatchPlayer.setId(matchPlayer.getId());

        partialUpdatedMatchPlayer.eId(UPDATED_E_ID).mtchEndTime(UPDATED_MTCH_END_TIME);

        restMatchPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchPlayer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMatchPlayer))
            )
            .andExpect(status().isOk());

        // Validate the MatchPlayer in the database
        List<MatchPlayer> matchPlayerList = matchPlayerRepository.findAll();
        assertThat(matchPlayerList).hasSize(databaseSizeBeforeUpdate);
        MatchPlayer testMatchPlayer = matchPlayerList.get(matchPlayerList.size() - 1);
        assertThat(testMatchPlayer.getmId()).isEqualTo(DEFAULT_M_ID);
        assertThat(testMatchPlayer.getpId()).isEqualTo(DEFAULT_P_ID);
        assertThat(testMatchPlayer.geteId()).isEqualTo(UPDATED_E_ID);
        assertThat(testMatchPlayer.getMtchEndTime()).isEqualTo(UPDATED_MTCH_END_TIME);
        assertThat(testMatchPlayer.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testMatchPlayer.getWinFg()).isEqualTo(DEFAULT_WIN_FG);
        assertThat(testMatchPlayer.getLstMtnUsr()).isEqualTo(DEFAULT_LST_MTN_USR);
        assertThat(testMatchPlayer.getLstMtnDt()).isEqualTo(DEFAULT_LST_MTN_DT);
    }

    @Test
    @Transactional
    void fullUpdateMatchPlayerWithPatch() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        int databaseSizeBeforeUpdate = matchPlayerRepository.findAll().size();

        // Update the matchPlayer using partial update
        MatchPlayer partialUpdatedMatchPlayer = new MatchPlayer();
        partialUpdatedMatchPlayer.setId(matchPlayer.getId());

        partialUpdatedMatchPlayer
            .mId(UPDATED_M_ID)
            .pId(UPDATED_P_ID)
            .eId(UPDATED_E_ID)
            .mtchEndTime(UPDATED_MTCH_END_TIME)
            .score(UPDATED_SCORE)
            .winFg(UPDATED_WIN_FG)
            .lstMtnUsr(UPDATED_LST_MTN_USR)
            .lstMtnDt(UPDATED_LST_MTN_DT);

        restMatchPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchPlayer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMatchPlayer))
            )
            .andExpect(status().isOk());

        // Validate the MatchPlayer in the database
        List<MatchPlayer> matchPlayerList = matchPlayerRepository.findAll();
        assertThat(matchPlayerList).hasSize(databaseSizeBeforeUpdate);
        MatchPlayer testMatchPlayer = matchPlayerList.get(matchPlayerList.size() - 1);
        assertThat(testMatchPlayer.getmId()).isEqualTo(UPDATED_M_ID);
        assertThat(testMatchPlayer.getpId()).isEqualTo(UPDATED_P_ID);
        assertThat(testMatchPlayer.geteId()).isEqualTo(UPDATED_E_ID);
        assertThat(testMatchPlayer.getMtchEndTime()).isEqualTo(UPDATED_MTCH_END_TIME);
        assertThat(testMatchPlayer.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testMatchPlayer.getWinFg()).isEqualTo(UPDATED_WIN_FG);
        assertThat(testMatchPlayer.getLstMtnUsr()).isEqualTo(UPDATED_LST_MTN_USR);
        assertThat(testMatchPlayer.getLstMtnDt()).isEqualTo(UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void patchNonExistingMatchPlayer() throws Exception {
        int databaseSizeBeforeUpdate = matchPlayerRepository.findAll().size();
        matchPlayer.setId(count.incrementAndGet());

        // Create the MatchPlayer
        MatchPlayerDTO matchPlayerDTO = matchPlayerMapper.toDto(matchPlayer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, matchPlayerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(matchPlayerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchPlayer in the database
        List<MatchPlayer> matchPlayerList = matchPlayerRepository.findAll();
        assertThat(matchPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMatchPlayer() throws Exception {
        int databaseSizeBeforeUpdate = matchPlayerRepository.findAll().size();
        matchPlayer.setId(count.incrementAndGet());

        // Create the MatchPlayer
        MatchPlayerDTO matchPlayerDTO = matchPlayerMapper.toDto(matchPlayer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(matchPlayerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchPlayer in the database
        List<MatchPlayer> matchPlayerList = matchPlayerRepository.findAll();
        assertThat(matchPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMatchPlayer() throws Exception {
        int databaseSizeBeforeUpdate = matchPlayerRepository.findAll().size();
        matchPlayer.setId(count.incrementAndGet());

        // Create the MatchPlayer
        MatchPlayerDTO matchPlayerDTO = matchPlayerMapper.toDto(matchPlayer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(matchPlayerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchPlayer in the database
        List<MatchPlayer> matchPlayerList = matchPlayerRepository.findAll();
        assertThat(matchPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMatchPlayer() throws Exception {
        // Initialize the database
        matchPlayerRepository.saveAndFlush(matchPlayer);

        int databaseSizeBeforeDelete = matchPlayerRepository.findAll().size();

        // Delete the matchPlayer
        restMatchPlayerMockMvc
            .perform(delete(ENTITY_API_URL_ID, matchPlayer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MatchPlayer> matchPlayerList = matchPlayerRepository.findAll();
        assertThat(matchPlayerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
