package com.wcc.pbl230801.web.rest;

import static com.wcc.pbl230801.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.wcc.pbl230801.IntegrationTest;
import com.wcc.pbl230801.domain.MatchZ;
import com.wcc.pbl230801.repository.MatchZRepository;
import com.wcc.pbl230801.service.criteria.MatchZCriteria;
import com.wcc.pbl230801.service.dto.MatchZDTO;
import com.wcc.pbl230801.service.mapper.MatchZMapper;
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
 * Integration tests for the {@link MatchZResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MatchZResourceIT {

    private static final Long DEFAULT_E_ID = 1L;
    private static final Long UPDATED_E_ID = 2L;
    private static final Long SMALLER_E_ID = 1L - 1L;

    private static final ZonedDateTime DEFAULT_MTCH_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MTCH_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_MTCH_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_W_PLYR_1 = "AAAAAAAAAA";
    private static final String UPDATED_W_PLYR_1 = "BBBBBBBBBB";

    private static final String DEFAULT_W_PLYR_2 = "AAAAAAAAAA";
    private static final String UPDATED_W_PLYR_2 = "BBBBBBBBBB";

    private static final String DEFAULT_W_SCR = "AAAAAAAAAA";
    private static final String UPDATED_W_SCR = "BBBBBBBBBB";

    private static final String DEFAULT_L_PLYR_1 = "AAAAAAAAAA";
    private static final String UPDATED_L_PLYR_1 = "BBBBBBBBBB";

    private static final String DEFAULT_L_PLYR_2 = "AAAAAAAAAA";
    private static final String UPDATED_L_PLYR_2 = "BBBBBBBBBB";

    private static final String DEFAULT_L_SCR = "AAAAAAAAAA";
    private static final String UPDATED_L_SCR = "BBBBBBBBBB";

    private static final String DEFAULT_LST_MTN_USR = "AAAAAAAAAA";
    private static final String UPDATED_LST_MTN_USR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LST_MTN_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LST_MTN_DT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LST_MTN_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/match-zs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MatchZRepository matchZRepository;

    @Autowired
    private MatchZMapper matchZMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatchZMockMvc;

    private MatchZ matchZ;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchZ createEntity(EntityManager em) {
        MatchZ matchZ = new MatchZ()
            .eId(DEFAULT_E_ID)
            .mtchEndTime(DEFAULT_MTCH_END_TIME)
            .wPlyr1(DEFAULT_W_PLYR_1)
            .wPlyr2(DEFAULT_W_PLYR_2)
            .wScr(DEFAULT_W_SCR)
            .lPlyr1(DEFAULT_L_PLYR_1)
            .lPlyr2(DEFAULT_L_PLYR_2)
            .lScr(DEFAULT_L_SCR)
            .lstMtnUsr(DEFAULT_LST_MTN_USR)
            .lstMtnDt(DEFAULT_LST_MTN_DT);
        return matchZ;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchZ createUpdatedEntity(EntityManager em) {
        MatchZ matchZ = new MatchZ()
            .eId(UPDATED_E_ID)
            .mtchEndTime(UPDATED_MTCH_END_TIME)
            .wPlyr1(UPDATED_W_PLYR_1)
            .wPlyr2(UPDATED_W_PLYR_2)
            .wScr(UPDATED_W_SCR)
            .lPlyr1(UPDATED_L_PLYR_1)
            .lPlyr2(UPDATED_L_PLYR_2)
            .lScr(UPDATED_L_SCR)
            .lstMtnUsr(UPDATED_LST_MTN_USR)
            .lstMtnDt(UPDATED_LST_MTN_DT);
        return matchZ;
    }

    @BeforeEach
    public void initTest() {
        matchZ = createEntity(em);
    }

    @Test
    @Transactional
    void createMatchZ() throws Exception {
        int databaseSizeBeforeCreate = matchZRepository.findAll().size();
        // Create the MatchZ
        MatchZDTO matchZDTO = matchZMapper.toDto(matchZ);
        restMatchZMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(matchZDTO)))
            .andExpect(status().isCreated());

        // Validate the MatchZ in the database
        List<MatchZ> matchZList = matchZRepository.findAll();
        assertThat(matchZList).hasSize(databaseSizeBeforeCreate + 1);
        MatchZ testMatchZ = matchZList.get(matchZList.size() - 1);
        assertThat(testMatchZ.geteId()).isEqualTo(DEFAULT_E_ID);
        assertThat(testMatchZ.getMtchEndTime()).isEqualTo(DEFAULT_MTCH_END_TIME);
        assertThat(testMatchZ.getwPlyr1()).isEqualTo(DEFAULT_W_PLYR_1);
        assertThat(testMatchZ.getwPlyr2()).isEqualTo(DEFAULT_W_PLYR_2);
        assertThat(testMatchZ.getwScr()).isEqualTo(DEFAULT_W_SCR);
        assertThat(testMatchZ.getlPlyr1()).isEqualTo(DEFAULT_L_PLYR_1);
        assertThat(testMatchZ.getlPlyr2()).isEqualTo(DEFAULT_L_PLYR_2);
        assertThat(testMatchZ.getlScr()).isEqualTo(DEFAULT_L_SCR);
        assertThat(testMatchZ.getLstMtnUsr()).isEqualTo(DEFAULT_LST_MTN_USR);
        assertThat(testMatchZ.getLstMtnDt()).isEqualTo(DEFAULT_LST_MTN_DT);
    }

    @Test
    @Transactional
    void createMatchZWithExistingId() throws Exception {
        // Create the MatchZ with an existing ID
        matchZ.setId(1L);
        MatchZDTO matchZDTO = matchZMapper.toDto(matchZ);

        int databaseSizeBeforeCreate = matchZRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchZMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(matchZDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MatchZ in the database
        List<MatchZ> matchZList = matchZRepository.findAll();
        assertThat(matchZList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMatchZS() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList
        restMatchZMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchZ.getId().intValue())))
            .andExpect(jsonPath("$.[*].eId").value(hasItem(DEFAULT_E_ID.intValue())))
            .andExpect(jsonPath("$.[*].mtchEndTime").value(hasItem(sameInstant(DEFAULT_MTCH_END_TIME))))
            .andExpect(jsonPath("$.[*].wPlyr1").value(hasItem(DEFAULT_W_PLYR_1)))
            .andExpect(jsonPath("$.[*].wPlyr2").value(hasItem(DEFAULT_W_PLYR_2)))
            .andExpect(jsonPath("$.[*].wScr").value(hasItem(DEFAULT_W_SCR)))
            .andExpect(jsonPath("$.[*].lPlyr1").value(hasItem(DEFAULT_L_PLYR_1)))
            .andExpect(jsonPath("$.[*].lPlyr2").value(hasItem(DEFAULT_L_PLYR_2)))
            .andExpect(jsonPath("$.[*].lScr").value(hasItem(DEFAULT_L_SCR)))
            .andExpect(jsonPath("$.[*].lstMtnUsr").value(hasItem(DEFAULT_LST_MTN_USR)))
            .andExpect(jsonPath("$.[*].lstMtnDt").value(hasItem(sameInstant(DEFAULT_LST_MTN_DT))));
    }

    @Test
    @Transactional
    void getMatchZ() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get the matchZ
        restMatchZMockMvc
            .perform(get(ENTITY_API_URL_ID, matchZ.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matchZ.getId().intValue()))
            .andExpect(jsonPath("$.eId").value(DEFAULT_E_ID.intValue()))
            .andExpect(jsonPath("$.mtchEndTime").value(sameInstant(DEFAULT_MTCH_END_TIME)))
            .andExpect(jsonPath("$.wPlyr1").value(DEFAULT_W_PLYR_1))
            .andExpect(jsonPath("$.wPlyr2").value(DEFAULT_W_PLYR_2))
            .andExpect(jsonPath("$.wScr").value(DEFAULT_W_SCR))
            .andExpect(jsonPath("$.lPlyr1").value(DEFAULT_L_PLYR_1))
            .andExpect(jsonPath("$.lPlyr2").value(DEFAULT_L_PLYR_2))
            .andExpect(jsonPath("$.lScr").value(DEFAULT_L_SCR))
            .andExpect(jsonPath("$.lstMtnUsr").value(DEFAULT_LST_MTN_USR))
            .andExpect(jsonPath("$.lstMtnDt").value(sameInstant(DEFAULT_LST_MTN_DT)));
    }

    @Test
    @Transactional
    void getMatchZSByIdFiltering() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        Long id = matchZ.getId();

        defaultMatchZShouldBeFound("id.equals=" + id);
        defaultMatchZShouldNotBeFound("id.notEquals=" + id);

        defaultMatchZShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMatchZShouldNotBeFound("id.greaterThan=" + id);

        defaultMatchZShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMatchZShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMatchZSByeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where eId equals to DEFAULT_E_ID
        defaultMatchZShouldBeFound("eId.equals=" + DEFAULT_E_ID);

        // Get all the matchZList where eId equals to UPDATED_E_ID
        defaultMatchZShouldNotBeFound("eId.equals=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllMatchZSByeIdIsInShouldWork() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where eId in DEFAULT_E_ID or UPDATED_E_ID
        defaultMatchZShouldBeFound("eId.in=" + DEFAULT_E_ID + "," + UPDATED_E_ID);

        // Get all the matchZList where eId equals to UPDATED_E_ID
        defaultMatchZShouldNotBeFound("eId.in=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllMatchZSByeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where eId is not null
        defaultMatchZShouldBeFound("eId.specified=true");

        // Get all the matchZList where eId is null
        defaultMatchZShouldNotBeFound("eId.specified=false");
    }

    @Test
    @Transactional
    void getAllMatchZSByeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where eId is greater than or equal to DEFAULT_E_ID
        defaultMatchZShouldBeFound("eId.greaterThanOrEqual=" + DEFAULT_E_ID);

        // Get all the matchZList where eId is greater than or equal to UPDATED_E_ID
        defaultMatchZShouldNotBeFound("eId.greaterThanOrEqual=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllMatchZSByeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where eId is less than or equal to DEFAULT_E_ID
        defaultMatchZShouldBeFound("eId.lessThanOrEqual=" + DEFAULT_E_ID);

        // Get all the matchZList where eId is less than or equal to SMALLER_E_ID
        defaultMatchZShouldNotBeFound("eId.lessThanOrEqual=" + SMALLER_E_ID);
    }

    @Test
    @Transactional
    void getAllMatchZSByeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where eId is less than DEFAULT_E_ID
        defaultMatchZShouldNotBeFound("eId.lessThan=" + DEFAULT_E_ID);

        // Get all the matchZList where eId is less than UPDATED_E_ID
        defaultMatchZShouldBeFound("eId.lessThan=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllMatchZSByeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where eId is greater than DEFAULT_E_ID
        defaultMatchZShouldNotBeFound("eId.greaterThan=" + DEFAULT_E_ID);

        // Get all the matchZList where eId is greater than SMALLER_E_ID
        defaultMatchZShouldBeFound("eId.greaterThan=" + SMALLER_E_ID);
    }

    @Test
    @Transactional
    void getAllMatchZSByMtchEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where mtchEndTime equals to DEFAULT_MTCH_END_TIME
        defaultMatchZShouldBeFound("mtchEndTime.equals=" + DEFAULT_MTCH_END_TIME);

        // Get all the matchZList where mtchEndTime equals to UPDATED_MTCH_END_TIME
        defaultMatchZShouldNotBeFound("mtchEndTime.equals=" + UPDATED_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllMatchZSByMtchEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where mtchEndTime in DEFAULT_MTCH_END_TIME or UPDATED_MTCH_END_TIME
        defaultMatchZShouldBeFound("mtchEndTime.in=" + DEFAULT_MTCH_END_TIME + "," + UPDATED_MTCH_END_TIME);

        // Get all the matchZList where mtchEndTime equals to UPDATED_MTCH_END_TIME
        defaultMatchZShouldNotBeFound("mtchEndTime.in=" + UPDATED_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllMatchZSByMtchEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where mtchEndTime is not null
        defaultMatchZShouldBeFound("mtchEndTime.specified=true");

        // Get all the matchZList where mtchEndTime is null
        defaultMatchZShouldNotBeFound("mtchEndTime.specified=false");
    }

    @Test
    @Transactional
    void getAllMatchZSByMtchEndTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where mtchEndTime is greater than or equal to DEFAULT_MTCH_END_TIME
        defaultMatchZShouldBeFound("mtchEndTime.greaterThanOrEqual=" + DEFAULT_MTCH_END_TIME);

        // Get all the matchZList where mtchEndTime is greater than or equal to UPDATED_MTCH_END_TIME
        defaultMatchZShouldNotBeFound("mtchEndTime.greaterThanOrEqual=" + UPDATED_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllMatchZSByMtchEndTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where mtchEndTime is less than or equal to DEFAULT_MTCH_END_TIME
        defaultMatchZShouldBeFound("mtchEndTime.lessThanOrEqual=" + DEFAULT_MTCH_END_TIME);

        // Get all the matchZList where mtchEndTime is less than or equal to SMALLER_MTCH_END_TIME
        defaultMatchZShouldNotBeFound("mtchEndTime.lessThanOrEqual=" + SMALLER_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllMatchZSByMtchEndTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where mtchEndTime is less than DEFAULT_MTCH_END_TIME
        defaultMatchZShouldNotBeFound("mtchEndTime.lessThan=" + DEFAULT_MTCH_END_TIME);

        // Get all the matchZList where mtchEndTime is less than UPDATED_MTCH_END_TIME
        defaultMatchZShouldBeFound("mtchEndTime.lessThan=" + UPDATED_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllMatchZSByMtchEndTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where mtchEndTime is greater than DEFAULT_MTCH_END_TIME
        defaultMatchZShouldNotBeFound("mtchEndTime.greaterThan=" + DEFAULT_MTCH_END_TIME);

        // Get all the matchZList where mtchEndTime is greater than SMALLER_MTCH_END_TIME
        defaultMatchZShouldBeFound("mtchEndTime.greaterThan=" + SMALLER_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllMatchZSBywPlyr1IsEqualToSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where wPlyr1 equals to DEFAULT_W_PLYR_1
        defaultMatchZShouldBeFound("wPlyr1.equals=" + DEFAULT_W_PLYR_1);

        // Get all the matchZList where wPlyr1 equals to UPDATED_W_PLYR_1
        defaultMatchZShouldNotBeFound("wPlyr1.equals=" + UPDATED_W_PLYR_1);
    }

    @Test
    @Transactional
    void getAllMatchZSBywPlyr1IsInShouldWork() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where wPlyr1 in DEFAULT_W_PLYR_1 or UPDATED_W_PLYR_1
        defaultMatchZShouldBeFound("wPlyr1.in=" + DEFAULT_W_PLYR_1 + "," + UPDATED_W_PLYR_1);

        // Get all the matchZList where wPlyr1 equals to UPDATED_W_PLYR_1
        defaultMatchZShouldNotBeFound("wPlyr1.in=" + UPDATED_W_PLYR_1);
    }

    @Test
    @Transactional
    void getAllMatchZSBywPlyr1IsNullOrNotNull() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where wPlyr1 is not null
        defaultMatchZShouldBeFound("wPlyr1.specified=true");

        // Get all the matchZList where wPlyr1 is null
        defaultMatchZShouldNotBeFound("wPlyr1.specified=false");
    }

    @Test
    @Transactional
    void getAllMatchZSBywPlyr1ContainsSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where wPlyr1 contains DEFAULT_W_PLYR_1
        defaultMatchZShouldBeFound("wPlyr1.contains=" + DEFAULT_W_PLYR_1);

        // Get all the matchZList where wPlyr1 contains UPDATED_W_PLYR_1
        defaultMatchZShouldNotBeFound("wPlyr1.contains=" + UPDATED_W_PLYR_1);
    }

    @Test
    @Transactional
    void getAllMatchZSBywPlyr1NotContainsSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where wPlyr1 does not contain DEFAULT_W_PLYR_1
        defaultMatchZShouldNotBeFound("wPlyr1.doesNotContain=" + DEFAULT_W_PLYR_1);

        // Get all the matchZList where wPlyr1 does not contain UPDATED_W_PLYR_1
        defaultMatchZShouldBeFound("wPlyr1.doesNotContain=" + UPDATED_W_PLYR_1);
    }

    @Test
    @Transactional
    void getAllMatchZSBywPlyr2IsEqualToSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where wPlyr2 equals to DEFAULT_W_PLYR_2
        defaultMatchZShouldBeFound("wPlyr2.equals=" + DEFAULT_W_PLYR_2);

        // Get all the matchZList where wPlyr2 equals to UPDATED_W_PLYR_2
        defaultMatchZShouldNotBeFound("wPlyr2.equals=" + UPDATED_W_PLYR_2);
    }

    @Test
    @Transactional
    void getAllMatchZSBywPlyr2IsInShouldWork() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where wPlyr2 in DEFAULT_W_PLYR_2 or UPDATED_W_PLYR_2
        defaultMatchZShouldBeFound("wPlyr2.in=" + DEFAULT_W_PLYR_2 + "," + UPDATED_W_PLYR_2);

        // Get all the matchZList where wPlyr2 equals to UPDATED_W_PLYR_2
        defaultMatchZShouldNotBeFound("wPlyr2.in=" + UPDATED_W_PLYR_2);
    }

    @Test
    @Transactional
    void getAllMatchZSBywPlyr2IsNullOrNotNull() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where wPlyr2 is not null
        defaultMatchZShouldBeFound("wPlyr2.specified=true");

        // Get all the matchZList where wPlyr2 is null
        defaultMatchZShouldNotBeFound("wPlyr2.specified=false");
    }

    @Test
    @Transactional
    void getAllMatchZSBywPlyr2ContainsSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where wPlyr2 contains DEFAULT_W_PLYR_2
        defaultMatchZShouldBeFound("wPlyr2.contains=" + DEFAULT_W_PLYR_2);

        // Get all the matchZList where wPlyr2 contains UPDATED_W_PLYR_2
        defaultMatchZShouldNotBeFound("wPlyr2.contains=" + UPDATED_W_PLYR_2);
    }

    @Test
    @Transactional
    void getAllMatchZSBywPlyr2NotContainsSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where wPlyr2 does not contain DEFAULT_W_PLYR_2
        defaultMatchZShouldNotBeFound("wPlyr2.doesNotContain=" + DEFAULT_W_PLYR_2);

        // Get all the matchZList where wPlyr2 does not contain UPDATED_W_PLYR_2
        defaultMatchZShouldBeFound("wPlyr2.doesNotContain=" + UPDATED_W_PLYR_2);
    }

    @Test
    @Transactional
    void getAllMatchZSBywScrIsEqualToSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where wScr equals to DEFAULT_W_SCR
        defaultMatchZShouldBeFound("wScr.equals=" + DEFAULT_W_SCR);

        // Get all the matchZList where wScr equals to UPDATED_W_SCR
        defaultMatchZShouldNotBeFound("wScr.equals=" + UPDATED_W_SCR);
    }

    @Test
    @Transactional
    void getAllMatchZSBywScrIsInShouldWork() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where wScr in DEFAULT_W_SCR or UPDATED_W_SCR
        defaultMatchZShouldBeFound("wScr.in=" + DEFAULT_W_SCR + "," + UPDATED_W_SCR);

        // Get all the matchZList where wScr equals to UPDATED_W_SCR
        defaultMatchZShouldNotBeFound("wScr.in=" + UPDATED_W_SCR);
    }

    @Test
    @Transactional
    void getAllMatchZSBywScrIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where wScr is not null
        defaultMatchZShouldBeFound("wScr.specified=true");

        // Get all the matchZList where wScr is null
        defaultMatchZShouldNotBeFound("wScr.specified=false");
    }

    @Test
    @Transactional
    void getAllMatchZSBywScrContainsSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where wScr contains DEFAULT_W_SCR
        defaultMatchZShouldBeFound("wScr.contains=" + DEFAULT_W_SCR);

        // Get all the matchZList where wScr contains UPDATED_W_SCR
        defaultMatchZShouldNotBeFound("wScr.contains=" + UPDATED_W_SCR);
    }

    @Test
    @Transactional
    void getAllMatchZSBywScrNotContainsSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where wScr does not contain DEFAULT_W_SCR
        defaultMatchZShouldNotBeFound("wScr.doesNotContain=" + DEFAULT_W_SCR);

        // Get all the matchZList where wScr does not contain UPDATED_W_SCR
        defaultMatchZShouldBeFound("wScr.doesNotContain=" + UPDATED_W_SCR);
    }

    @Test
    @Transactional
    void getAllMatchZSBylPlyr1IsEqualToSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lPlyr1 equals to DEFAULT_L_PLYR_1
        defaultMatchZShouldBeFound("lPlyr1.equals=" + DEFAULT_L_PLYR_1);

        // Get all the matchZList where lPlyr1 equals to UPDATED_L_PLYR_1
        defaultMatchZShouldNotBeFound("lPlyr1.equals=" + UPDATED_L_PLYR_1);
    }

    @Test
    @Transactional
    void getAllMatchZSBylPlyr1IsInShouldWork() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lPlyr1 in DEFAULT_L_PLYR_1 or UPDATED_L_PLYR_1
        defaultMatchZShouldBeFound("lPlyr1.in=" + DEFAULT_L_PLYR_1 + "," + UPDATED_L_PLYR_1);

        // Get all the matchZList where lPlyr1 equals to UPDATED_L_PLYR_1
        defaultMatchZShouldNotBeFound("lPlyr1.in=" + UPDATED_L_PLYR_1);
    }

    @Test
    @Transactional
    void getAllMatchZSBylPlyr1IsNullOrNotNull() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lPlyr1 is not null
        defaultMatchZShouldBeFound("lPlyr1.specified=true");

        // Get all the matchZList where lPlyr1 is null
        defaultMatchZShouldNotBeFound("lPlyr1.specified=false");
    }

    @Test
    @Transactional
    void getAllMatchZSBylPlyr1ContainsSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lPlyr1 contains DEFAULT_L_PLYR_1
        defaultMatchZShouldBeFound("lPlyr1.contains=" + DEFAULT_L_PLYR_1);

        // Get all the matchZList where lPlyr1 contains UPDATED_L_PLYR_1
        defaultMatchZShouldNotBeFound("lPlyr1.contains=" + UPDATED_L_PLYR_1);
    }

    @Test
    @Transactional
    void getAllMatchZSBylPlyr1NotContainsSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lPlyr1 does not contain DEFAULT_L_PLYR_1
        defaultMatchZShouldNotBeFound("lPlyr1.doesNotContain=" + DEFAULT_L_PLYR_1);

        // Get all the matchZList where lPlyr1 does not contain UPDATED_L_PLYR_1
        defaultMatchZShouldBeFound("lPlyr1.doesNotContain=" + UPDATED_L_PLYR_1);
    }

    @Test
    @Transactional
    void getAllMatchZSBylPlyr2IsEqualToSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lPlyr2 equals to DEFAULT_L_PLYR_2
        defaultMatchZShouldBeFound("lPlyr2.equals=" + DEFAULT_L_PLYR_2);

        // Get all the matchZList where lPlyr2 equals to UPDATED_L_PLYR_2
        defaultMatchZShouldNotBeFound("lPlyr2.equals=" + UPDATED_L_PLYR_2);
    }

    @Test
    @Transactional
    void getAllMatchZSBylPlyr2IsInShouldWork() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lPlyr2 in DEFAULT_L_PLYR_2 or UPDATED_L_PLYR_2
        defaultMatchZShouldBeFound("lPlyr2.in=" + DEFAULT_L_PLYR_2 + "," + UPDATED_L_PLYR_2);

        // Get all the matchZList where lPlyr2 equals to UPDATED_L_PLYR_2
        defaultMatchZShouldNotBeFound("lPlyr2.in=" + UPDATED_L_PLYR_2);
    }

    @Test
    @Transactional
    void getAllMatchZSBylPlyr2IsNullOrNotNull() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lPlyr2 is not null
        defaultMatchZShouldBeFound("lPlyr2.specified=true");

        // Get all the matchZList where lPlyr2 is null
        defaultMatchZShouldNotBeFound("lPlyr2.specified=false");
    }

    @Test
    @Transactional
    void getAllMatchZSBylPlyr2ContainsSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lPlyr2 contains DEFAULT_L_PLYR_2
        defaultMatchZShouldBeFound("lPlyr2.contains=" + DEFAULT_L_PLYR_2);

        // Get all the matchZList where lPlyr2 contains UPDATED_L_PLYR_2
        defaultMatchZShouldNotBeFound("lPlyr2.contains=" + UPDATED_L_PLYR_2);
    }

    @Test
    @Transactional
    void getAllMatchZSBylPlyr2NotContainsSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lPlyr2 does not contain DEFAULT_L_PLYR_2
        defaultMatchZShouldNotBeFound("lPlyr2.doesNotContain=" + DEFAULT_L_PLYR_2);

        // Get all the matchZList where lPlyr2 does not contain UPDATED_L_PLYR_2
        defaultMatchZShouldBeFound("lPlyr2.doesNotContain=" + UPDATED_L_PLYR_2);
    }

    @Test
    @Transactional
    void getAllMatchZSBylScrIsEqualToSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lScr equals to DEFAULT_L_SCR
        defaultMatchZShouldBeFound("lScr.equals=" + DEFAULT_L_SCR);

        // Get all the matchZList where lScr equals to UPDATED_L_SCR
        defaultMatchZShouldNotBeFound("lScr.equals=" + UPDATED_L_SCR);
    }

    @Test
    @Transactional
    void getAllMatchZSBylScrIsInShouldWork() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lScr in DEFAULT_L_SCR or UPDATED_L_SCR
        defaultMatchZShouldBeFound("lScr.in=" + DEFAULT_L_SCR + "," + UPDATED_L_SCR);

        // Get all the matchZList where lScr equals to UPDATED_L_SCR
        defaultMatchZShouldNotBeFound("lScr.in=" + UPDATED_L_SCR);
    }

    @Test
    @Transactional
    void getAllMatchZSBylScrIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lScr is not null
        defaultMatchZShouldBeFound("lScr.specified=true");

        // Get all the matchZList where lScr is null
        defaultMatchZShouldNotBeFound("lScr.specified=false");
    }

    @Test
    @Transactional
    void getAllMatchZSBylScrContainsSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lScr contains DEFAULT_L_SCR
        defaultMatchZShouldBeFound("lScr.contains=" + DEFAULT_L_SCR);

        // Get all the matchZList where lScr contains UPDATED_L_SCR
        defaultMatchZShouldNotBeFound("lScr.contains=" + UPDATED_L_SCR);
    }

    @Test
    @Transactional
    void getAllMatchZSBylScrNotContainsSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lScr does not contain DEFAULT_L_SCR
        defaultMatchZShouldNotBeFound("lScr.doesNotContain=" + DEFAULT_L_SCR);

        // Get all the matchZList where lScr does not contain UPDATED_L_SCR
        defaultMatchZShouldBeFound("lScr.doesNotContain=" + UPDATED_L_SCR);
    }

    @Test
    @Transactional
    void getAllMatchZSByLstMtnUsrIsEqualToSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lstMtnUsr equals to DEFAULT_LST_MTN_USR
        defaultMatchZShouldBeFound("lstMtnUsr.equals=" + DEFAULT_LST_MTN_USR);

        // Get all the matchZList where lstMtnUsr equals to UPDATED_LST_MTN_USR
        defaultMatchZShouldNotBeFound("lstMtnUsr.equals=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllMatchZSByLstMtnUsrIsInShouldWork() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lstMtnUsr in DEFAULT_LST_MTN_USR or UPDATED_LST_MTN_USR
        defaultMatchZShouldBeFound("lstMtnUsr.in=" + DEFAULT_LST_MTN_USR + "," + UPDATED_LST_MTN_USR);

        // Get all the matchZList where lstMtnUsr equals to UPDATED_LST_MTN_USR
        defaultMatchZShouldNotBeFound("lstMtnUsr.in=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllMatchZSByLstMtnUsrIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lstMtnUsr is not null
        defaultMatchZShouldBeFound("lstMtnUsr.specified=true");

        // Get all the matchZList where lstMtnUsr is null
        defaultMatchZShouldNotBeFound("lstMtnUsr.specified=false");
    }

    @Test
    @Transactional
    void getAllMatchZSByLstMtnUsrContainsSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lstMtnUsr contains DEFAULT_LST_MTN_USR
        defaultMatchZShouldBeFound("lstMtnUsr.contains=" + DEFAULT_LST_MTN_USR);

        // Get all the matchZList where lstMtnUsr contains UPDATED_LST_MTN_USR
        defaultMatchZShouldNotBeFound("lstMtnUsr.contains=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllMatchZSByLstMtnUsrNotContainsSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lstMtnUsr does not contain DEFAULT_LST_MTN_USR
        defaultMatchZShouldNotBeFound("lstMtnUsr.doesNotContain=" + DEFAULT_LST_MTN_USR);

        // Get all the matchZList where lstMtnUsr does not contain UPDATED_LST_MTN_USR
        defaultMatchZShouldBeFound("lstMtnUsr.doesNotContain=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllMatchZSByLstMtnDtIsEqualToSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lstMtnDt equals to DEFAULT_LST_MTN_DT
        defaultMatchZShouldBeFound("lstMtnDt.equals=" + DEFAULT_LST_MTN_DT);

        // Get all the matchZList where lstMtnDt equals to UPDATED_LST_MTN_DT
        defaultMatchZShouldNotBeFound("lstMtnDt.equals=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllMatchZSByLstMtnDtIsInShouldWork() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lstMtnDt in DEFAULT_LST_MTN_DT or UPDATED_LST_MTN_DT
        defaultMatchZShouldBeFound("lstMtnDt.in=" + DEFAULT_LST_MTN_DT + "," + UPDATED_LST_MTN_DT);

        // Get all the matchZList where lstMtnDt equals to UPDATED_LST_MTN_DT
        defaultMatchZShouldNotBeFound("lstMtnDt.in=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllMatchZSByLstMtnDtIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lstMtnDt is not null
        defaultMatchZShouldBeFound("lstMtnDt.specified=true");

        // Get all the matchZList where lstMtnDt is null
        defaultMatchZShouldNotBeFound("lstMtnDt.specified=false");
    }

    @Test
    @Transactional
    void getAllMatchZSByLstMtnDtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lstMtnDt is greater than or equal to DEFAULT_LST_MTN_DT
        defaultMatchZShouldBeFound("lstMtnDt.greaterThanOrEqual=" + DEFAULT_LST_MTN_DT);

        // Get all the matchZList where lstMtnDt is greater than or equal to UPDATED_LST_MTN_DT
        defaultMatchZShouldNotBeFound("lstMtnDt.greaterThanOrEqual=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllMatchZSByLstMtnDtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lstMtnDt is less than or equal to DEFAULT_LST_MTN_DT
        defaultMatchZShouldBeFound("lstMtnDt.lessThanOrEqual=" + DEFAULT_LST_MTN_DT);

        // Get all the matchZList where lstMtnDt is less than or equal to SMALLER_LST_MTN_DT
        defaultMatchZShouldNotBeFound("lstMtnDt.lessThanOrEqual=" + SMALLER_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllMatchZSByLstMtnDtIsLessThanSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lstMtnDt is less than DEFAULT_LST_MTN_DT
        defaultMatchZShouldNotBeFound("lstMtnDt.lessThan=" + DEFAULT_LST_MTN_DT);

        // Get all the matchZList where lstMtnDt is less than UPDATED_LST_MTN_DT
        defaultMatchZShouldBeFound("lstMtnDt.lessThan=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllMatchZSByLstMtnDtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        // Get all the matchZList where lstMtnDt is greater than DEFAULT_LST_MTN_DT
        defaultMatchZShouldNotBeFound("lstMtnDt.greaterThan=" + DEFAULT_LST_MTN_DT);

        // Get all the matchZList where lstMtnDt is greater than SMALLER_LST_MTN_DT
        defaultMatchZShouldBeFound("lstMtnDt.greaterThan=" + SMALLER_LST_MTN_DT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMatchZShouldBeFound(String filter) throws Exception {
        restMatchZMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchZ.getId().intValue())))
            .andExpect(jsonPath("$.[*].eId").value(hasItem(DEFAULT_E_ID.intValue())))
            .andExpect(jsonPath("$.[*].mtchEndTime").value(hasItem(sameInstant(DEFAULT_MTCH_END_TIME))))
            .andExpect(jsonPath("$.[*].wPlyr1").value(hasItem(DEFAULT_W_PLYR_1)))
            .andExpect(jsonPath("$.[*].wPlyr2").value(hasItem(DEFAULT_W_PLYR_2)))
            .andExpect(jsonPath("$.[*].wScr").value(hasItem(DEFAULT_W_SCR)))
            .andExpect(jsonPath("$.[*].lPlyr1").value(hasItem(DEFAULT_L_PLYR_1)))
            .andExpect(jsonPath("$.[*].lPlyr2").value(hasItem(DEFAULT_L_PLYR_2)))
            .andExpect(jsonPath("$.[*].lScr").value(hasItem(DEFAULT_L_SCR)))
            .andExpect(jsonPath("$.[*].lstMtnUsr").value(hasItem(DEFAULT_LST_MTN_USR)))
            .andExpect(jsonPath("$.[*].lstMtnDt").value(hasItem(sameInstant(DEFAULT_LST_MTN_DT))));

        // Check, that the count call also returns 1
        restMatchZMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMatchZShouldNotBeFound(String filter) throws Exception {
        restMatchZMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMatchZMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMatchZ() throws Exception {
        // Get the matchZ
        restMatchZMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMatchZ() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        int databaseSizeBeforeUpdate = matchZRepository.findAll().size();

        // Update the matchZ
        MatchZ updatedMatchZ = matchZRepository.findById(matchZ.getId()).get();
        // Disconnect from session so that the updates on updatedMatchZ are not directly saved in db
        em.detach(updatedMatchZ);
        updatedMatchZ
            .eId(UPDATED_E_ID)
            .mtchEndTime(UPDATED_MTCH_END_TIME)
            .wPlyr1(UPDATED_W_PLYR_1)
            .wPlyr2(UPDATED_W_PLYR_2)
            .wScr(UPDATED_W_SCR)
            .lPlyr1(UPDATED_L_PLYR_1)
            .lPlyr2(UPDATED_L_PLYR_2)
            .lScr(UPDATED_L_SCR)
            .lstMtnUsr(UPDATED_LST_MTN_USR)
            .lstMtnDt(UPDATED_LST_MTN_DT);
        MatchZDTO matchZDTO = matchZMapper.toDto(updatedMatchZ);

        restMatchZMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchZDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(matchZDTO))
            )
            .andExpect(status().isOk());

        // Validate the MatchZ in the database
        List<MatchZ> matchZList = matchZRepository.findAll();
        assertThat(matchZList).hasSize(databaseSizeBeforeUpdate);
        MatchZ testMatchZ = matchZList.get(matchZList.size() - 1);
        assertThat(testMatchZ.geteId()).isEqualTo(UPDATED_E_ID);
        assertThat(testMatchZ.getMtchEndTime()).isEqualTo(UPDATED_MTCH_END_TIME);
        assertThat(testMatchZ.getwPlyr1()).isEqualTo(UPDATED_W_PLYR_1);
        assertThat(testMatchZ.getwPlyr2()).isEqualTo(UPDATED_W_PLYR_2);
        assertThat(testMatchZ.getwScr()).isEqualTo(UPDATED_W_SCR);
        assertThat(testMatchZ.getlPlyr1()).isEqualTo(UPDATED_L_PLYR_1);
        assertThat(testMatchZ.getlPlyr2()).isEqualTo(UPDATED_L_PLYR_2);
        assertThat(testMatchZ.getlScr()).isEqualTo(UPDATED_L_SCR);
        assertThat(testMatchZ.getLstMtnUsr()).isEqualTo(UPDATED_LST_MTN_USR);
        assertThat(testMatchZ.getLstMtnDt()).isEqualTo(UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void putNonExistingMatchZ() throws Exception {
        int databaseSizeBeforeUpdate = matchZRepository.findAll().size();
        matchZ.setId(count.incrementAndGet());

        // Create the MatchZ
        MatchZDTO matchZDTO = matchZMapper.toDto(matchZ);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchZMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchZDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(matchZDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchZ in the database
        List<MatchZ> matchZList = matchZRepository.findAll();
        assertThat(matchZList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMatchZ() throws Exception {
        int databaseSizeBeforeUpdate = matchZRepository.findAll().size();
        matchZ.setId(count.incrementAndGet());

        // Create the MatchZ
        MatchZDTO matchZDTO = matchZMapper.toDto(matchZ);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchZMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(matchZDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchZ in the database
        List<MatchZ> matchZList = matchZRepository.findAll();
        assertThat(matchZList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMatchZ() throws Exception {
        int databaseSizeBeforeUpdate = matchZRepository.findAll().size();
        matchZ.setId(count.incrementAndGet());

        // Create the MatchZ
        MatchZDTO matchZDTO = matchZMapper.toDto(matchZ);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchZMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(matchZDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchZ in the database
        List<MatchZ> matchZList = matchZRepository.findAll();
        assertThat(matchZList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMatchZWithPatch() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        int databaseSizeBeforeUpdate = matchZRepository.findAll().size();

        // Update the matchZ using partial update
        MatchZ partialUpdatedMatchZ = new MatchZ();
        partialUpdatedMatchZ.setId(matchZ.getId());

        partialUpdatedMatchZ
            .eId(UPDATED_E_ID)
            .wPlyr1(UPDATED_W_PLYR_1)
            .wPlyr2(UPDATED_W_PLYR_2)
            .lPlyr1(UPDATED_L_PLYR_1)
            .lPlyr2(UPDATED_L_PLYR_2);

        restMatchZMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchZ.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMatchZ))
            )
            .andExpect(status().isOk());

        // Validate the MatchZ in the database
        List<MatchZ> matchZList = matchZRepository.findAll();
        assertThat(matchZList).hasSize(databaseSizeBeforeUpdate);
        MatchZ testMatchZ = matchZList.get(matchZList.size() - 1);
        assertThat(testMatchZ.geteId()).isEqualTo(UPDATED_E_ID);
        assertThat(testMatchZ.getMtchEndTime()).isEqualTo(DEFAULT_MTCH_END_TIME);
        assertThat(testMatchZ.getwPlyr1()).isEqualTo(UPDATED_W_PLYR_1);
        assertThat(testMatchZ.getwPlyr2()).isEqualTo(UPDATED_W_PLYR_2);
        assertThat(testMatchZ.getwScr()).isEqualTo(DEFAULT_W_SCR);
        assertThat(testMatchZ.getlPlyr1()).isEqualTo(UPDATED_L_PLYR_1);
        assertThat(testMatchZ.getlPlyr2()).isEqualTo(UPDATED_L_PLYR_2);
        assertThat(testMatchZ.getlScr()).isEqualTo(DEFAULT_L_SCR);
        assertThat(testMatchZ.getLstMtnUsr()).isEqualTo(DEFAULT_LST_MTN_USR);
        assertThat(testMatchZ.getLstMtnDt()).isEqualTo(DEFAULT_LST_MTN_DT);
    }

    @Test
    @Transactional
    void fullUpdateMatchZWithPatch() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        int databaseSizeBeforeUpdate = matchZRepository.findAll().size();

        // Update the matchZ using partial update
        MatchZ partialUpdatedMatchZ = new MatchZ();
        partialUpdatedMatchZ.setId(matchZ.getId());

        partialUpdatedMatchZ
            .eId(UPDATED_E_ID)
            .mtchEndTime(UPDATED_MTCH_END_TIME)
            .wPlyr1(UPDATED_W_PLYR_1)
            .wPlyr2(UPDATED_W_PLYR_2)
            .wScr(UPDATED_W_SCR)
            .lPlyr1(UPDATED_L_PLYR_1)
            .lPlyr2(UPDATED_L_PLYR_2)
            .lScr(UPDATED_L_SCR)
            .lstMtnUsr(UPDATED_LST_MTN_USR)
            .lstMtnDt(UPDATED_LST_MTN_DT);

        restMatchZMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchZ.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMatchZ))
            )
            .andExpect(status().isOk());

        // Validate the MatchZ in the database
        List<MatchZ> matchZList = matchZRepository.findAll();
        assertThat(matchZList).hasSize(databaseSizeBeforeUpdate);
        MatchZ testMatchZ = matchZList.get(matchZList.size() - 1);
        assertThat(testMatchZ.geteId()).isEqualTo(UPDATED_E_ID);
        assertThat(testMatchZ.getMtchEndTime()).isEqualTo(UPDATED_MTCH_END_TIME);
        assertThat(testMatchZ.getwPlyr1()).isEqualTo(UPDATED_W_PLYR_1);
        assertThat(testMatchZ.getwPlyr2()).isEqualTo(UPDATED_W_PLYR_2);
        assertThat(testMatchZ.getwScr()).isEqualTo(UPDATED_W_SCR);
        assertThat(testMatchZ.getlPlyr1()).isEqualTo(UPDATED_L_PLYR_1);
        assertThat(testMatchZ.getlPlyr2()).isEqualTo(UPDATED_L_PLYR_2);
        assertThat(testMatchZ.getlScr()).isEqualTo(UPDATED_L_SCR);
        assertThat(testMatchZ.getLstMtnUsr()).isEqualTo(UPDATED_LST_MTN_USR);
        assertThat(testMatchZ.getLstMtnDt()).isEqualTo(UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void patchNonExistingMatchZ() throws Exception {
        int databaseSizeBeforeUpdate = matchZRepository.findAll().size();
        matchZ.setId(count.incrementAndGet());

        // Create the MatchZ
        MatchZDTO matchZDTO = matchZMapper.toDto(matchZ);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchZMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, matchZDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(matchZDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchZ in the database
        List<MatchZ> matchZList = matchZRepository.findAll();
        assertThat(matchZList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMatchZ() throws Exception {
        int databaseSizeBeforeUpdate = matchZRepository.findAll().size();
        matchZ.setId(count.incrementAndGet());

        // Create the MatchZ
        MatchZDTO matchZDTO = matchZMapper.toDto(matchZ);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchZMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(matchZDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchZ in the database
        List<MatchZ> matchZList = matchZRepository.findAll();
        assertThat(matchZList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMatchZ() throws Exception {
        int databaseSizeBeforeUpdate = matchZRepository.findAll().size();
        matchZ.setId(count.incrementAndGet());

        // Create the MatchZ
        MatchZDTO matchZDTO = matchZMapper.toDto(matchZ);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchZMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(matchZDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchZ in the database
        List<MatchZ> matchZList = matchZRepository.findAll();
        assertThat(matchZList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMatchZ() throws Exception {
        // Initialize the database
        matchZRepository.saveAndFlush(matchZ);

        int databaseSizeBeforeDelete = matchZRepository.findAll().size();

        // Delete the matchZ
        restMatchZMockMvc
            .perform(delete(ENTITY_API_URL_ID, matchZ.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MatchZ> matchZList = matchZRepository.findAll();
        assertThat(matchZList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
