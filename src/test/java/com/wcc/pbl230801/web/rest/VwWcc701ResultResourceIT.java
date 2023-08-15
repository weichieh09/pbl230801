package com.wcc.pbl230801.web.rest;

import static com.wcc.pbl230801.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.wcc.pbl230801.IntegrationTest;
import com.wcc.pbl230801.domain.VwWcc701Result;
import com.wcc.pbl230801.repository.VwWcc701ResultRepository;
import com.wcc.pbl230801.service.criteria.VwWcc701ResultCriteria;
import com.wcc.pbl230801.service.dto.VwWcc701ResultDTO;
import com.wcc.pbl230801.service.mapper.VwWcc701ResultMapper;
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
 * Integration tests for the {@link VwWcc701ResultResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VwWcc701ResultResourceIT {

    private static final Long DEFAULT_E_ID = 1L;
    private static final Long UPDATED_E_ID = 2L;
    private static final Long SMALLER_E_ID = 1L - 1L;

    private static final String DEFAULT_EVNT_NM = "AAAAAAAAAA";
    private static final String UPDATED_EVNT_NM = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EVNT_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EVNT_DT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_EVNT_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_VENUE = "AAAAAAAAAA";
    private static final String UPDATED_VENUE = "BBBBBBBBBB";

    private static final Long DEFAULT_M_ID = 1L;
    private static final Long UPDATED_M_ID = 2L;
    private static final Long SMALLER_M_ID = 1L - 1L;

    private static final ZonedDateTime DEFAULT_MTCH_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MTCH_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_MTCH_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_W_PLYR_1_ID = 1L;
    private static final Long UPDATED_W_PLYR_1_ID = 2L;
    private static final Long SMALLER_W_PLYR_1_ID = 1L - 1L;

    private static final String DEFAULT_W_PLYR_1_LVL = "AAAAAAAAAA";
    private static final String UPDATED_W_PLYR_1_LVL = "BBBBBBBBBB";

    private static final String DEFAULT_W_PLYR_1_NM = "AAAAAAAAAA";
    private static final String UPDATED_W_PLYR_1_NM = "BBBBBBBBBB";

    private static final Long DEFAULT_W_PLYR_2_ID = 1L;
    private static final Long UPDATED_W_PLYR_2_ID = 2L;
    private static final Long SMALLER_W_PLYR_2_ID = 1L - 1L;

    private static final String DEFAULT_W_PLYR_2_LVL = "AAAAAAAAAA";
    private static final String UPDATED_W_PLYR_2_LVL = "BBBBBBBBBB";

    private static final String DEFAULT_W_PLYR_2_NM = "AAAAAAAAAA";
    private static final String UPDATED_W_PLYR_2_NM = "BBBBBBBBBB";

    private static final String DEFAULT_VS = "AAAAAAAAAA";
    private static final String UPDATED_VS = "BBBBBBBBBB";

    private static final Long DEFAULT_L_PLYR_1_ID = 1L;
    private static final Long UPDATED_L_PLYR_1_ID = 2L;
    private static final Long SMALLER_L_PLYR_1_ID = 1L - 1L;

    private static final String DEFAULT_L_PLYR_1_LVL = "AAAAAAAAAA";
    private static final String UPDATED_L_PLYR_1_LVL = "BBBBBBBBBB";

    private static final String DEFAULT_L_PLYR_1_NM = "AAAAAAAAAA";
    private static final String UPDATED_L_PLYR_1_NM = "BBBBBBBBBB";

    private static final Long DEFAULT_L_PLYR_2_ID = 1L;
    private static final Long UPDATED_L_PLYR_2_ID = 2L;
    private static final Long SMALLER_L_PLYR_2_ID = 1L - 1L;

    private static final String DEFAULT_L_PLYR_2_LVL = "AAAAAAAAAA";
    private static final String UPDATED_L_PLYR_2_LVL = "BBBBBBBBBB";

    private static final String DEFAULT_L_PLYR_2_NM = "AAAAAAAAAA";
    private static final String UPDATED_L_PLYR_2_NM = "BBBBBBBBBB";

    private static final String DEFAULT_W_SCR = "AAAAAAAAAA";
    private static final String UPDATED_W_SCR = "BBBBBBBBBB";

    private static final String DEFAULT_L_SCR = "AAAAAAAAAA";
    private static final String UPDATED_L_SCR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vw-wcc-701-results";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VwWcc701ResultRepository vwWcc701ResultRepository;

    @Autowired
    private VwWcc701ResultMapper vwWcc701ResultMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVwWcc701ResultMockMvc;

    private VwWcc701Result vwWcc701Result;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VwWcc701Result createEntity(EntityManager em) {
        VwWcc701Result vwWcc701Result = new VwWcc701Result()
            .eId(DEFAULT_E_ID)
            .evntNm(DEFAULT_EVNT_NM)
            .evntDt(DEFAULT_EVNT_DT)
            .venue(DEFAULT_VENUE)
            .mId(DEFAULT_M_ID)
            .mtchEndTime(DEFAULT_MTCH_END_TIME)
            .wPlyr1Id(DEFAULT_W_PLYR_1_ID)
            .wPlyr1Lvl(DEFAULT_W_PLYR_1_LVL)
            .wPlyr1Nm(DEFAULT_W_PLYR_1_NM)
            .wPlyr2Id(DEFAULT_W_PLYR_2_ID)
            .wPlyr2Lvl(DEFAULT_W_PLYR_2_LVL)
            .wPlyr2Nm(DEFAULT_W_PLYR_2_NM)
            .vs(DEFAULT_VS)
            .lPlyr1Id(DEFAULT_L_PLYR_1_ID)
            .lPlyr1Lvl(DEFAULT_L_PLYR_1_LVL)
            .lPlyr1Nm(DEFAULT_L_PLYR_1_NM)
            .lPlyr2Id(DEFAULT_L_PLYR_2_ID)
            .lPlyr2Lvl(DEFAULT_L_PLYR_2_LVL)
            .lPlyr2Nm(DEFAULT_L_PLYR_2_NM)
            .wScr(DEFAULT_W_SCR)
            .lScr(DEFAULT_L_SCR);
        return vwWcc701Result;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VwWcc701Result createUpdatedEntity(EntityManager em) {
        VwWcc701Result vwWcc701Result = new VwWcc701Result()
            .eId(UPDATED_E_ID)
            .evntNm(UPDATED_EVNT_NM)
            .evntDt(UPDATED_EVNT_DT)
            .venue(UPDATED_VENUE)
            .mId(UPDATED_M_ID)
            .mtchEndTime(UPDATED_MTCH_END_TIME)
            .wPlyr1Id(UPDATED_W_PLYR_1_ID)
            .wPlyr1Lvl(UPDATED_W_PLYR_1_LVL)
            .wPlyr1Nm(UPDATED_W_PLYR_1_NM)
            .wPlyr2Id(UPDATED_W_PLYR_2_ID)
            .wPlyr2Lvl(UPDATED_W_PLYR_2_LVL)
            .wPlyr2Nm(UPDATED_W_PLYR_2_NM)
            .vs(UPDATED_VS)
            .lPlyr1Id(UPDATED_L_PLYR_1_ID)
            .lPlyr1Lvl(UPDATED_L_PLYR_1_LVL)
            .lPlyr1Nm(UPDATED_L_PLYR_1_NM)
            .lPlyr2Id(UPDATED_L_PLYR_2_ID)
            .lPlyr2Lvl(UPDATED_L_PLYR_2_LVL)
            .lPlyr2Nm(UPDATED_L_PLYR_2_NM)
            .wScr(UPDATED_W_SCR)
            .lScr(UPDATED_L_SCR);
        return vwWcc701Result;
    }

    @BeforeEach
    public void initTest() {
        vwWcc701Result = createEntity(em);
    }

    @Test
    @Transactional
    void getAllVwWcc701Results() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList
        restVwWcc701ResultMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vwWcc701Result.getId().intValue())))
            .andExpect(jsonPath("$.[*].eId").value(hasItem(DEFAULT_E_ID.intValue())))
            .andExpect(jsonPath("$.[*].evntNm").value(hasItem(DEFAULT_EVNT_NM)))
            .andExpect(jsonPath("$.[*].evntDt").value(hasItem(sameInstant(DEFAULT_EVNT_DT))))
            .andExpect(jsonPath("$.[*].venue").value(hasItem(DEFAULT_VENUE)))
            .andExpect(jsonPath("$.[*].mId").value(hasItem(DEFAULT_M_ID.intValue())))
            .andExpect(jsonPath("$.[*].mtchEndTime").value(hasItem(sameInstant(DEFAULT_MTCH_END_TIME))))
            .andExpect(jsonPath("$.[*].wPlyr1Id").value(hasItem(DEFAULT_W_PLYR_1_ID.intValue())))
            .andExpect(jsonPath("$.[*].wPlyr1Lvl").value(hasItem(DEFAULT_W_PLYR_1_LVL)))
            .andExpect(jsonPath("$.[*].wPlyr1Nm").value(hasItem(DEFAULT_W_PLYR_1_NM)))
            .andExpect(jsonPath("$.[*].wPlyr2Id").value(hasItem(DEFAULT_W_PLYR_2_ID.intValue())))
            .andExpect(jsonPath("$.[*].wPlyr2Lvl").value(hasItem(DEFAULT_W_PLYR_2_LVL)))
            .andExpect(jsonPath("$.[*].wPlyr2Nm").value(hasItem(DEFAULT_W_PLYR_2_NM)))
            .andExpect(jsonPath("$.[*].vs").value(hasItem(DEFAULT_VS)))
            .andExpect(jsonPath("$.[*].lPlyr1Id").value(hasItem(DEFAULT_L_PLYR_1_ID.intValue())))
            .andExpect(jsonPath("$.[*].lPlyr1Lvl").value(hasItem(DEFAULT_L_PLYR_1_LVL)))
            .andExpect(jsonPath("$.[*].lPlyr1Nm").value(hasItem(DEFAULT_L_PLYR_1_NM)))
            .andExpect(jsonPath("$.[*].lPlyr2Id").value(hasItem(DEFAULT_L_PLYR_2_ID.intValue())))
            .andExpect(jsonPath("$.[*].lPlyr2Lvl").value(hasItem(DEFAULT_L_PLYR_2_LVL)))
            .andExpect(jsonPath("$.[*].lPlyr2Nm").value(hasItem(DEFAULT_L_PLYR_2_NM)))
            .andExpect(jsonPath("$.[*].wScr").value(hasItem(DEFAULT_W_SCR)))
            .andExpect(jsonPath("$.[*].lScr").value(hasItem(DEFAULT_L_SCR)));
    }

    @Test
    @Transactional
    void getVwWcc701Result() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get the vwWcc701Result
        restVwWcc701ResultMockMvc
            .perform(get(ENTITY_API_URL_ID, vwWcc701Result.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vwWcc701Result.getId().intValue()))
            .andExpect(jsonPath("$.eId").value(DEFAULT_E_ID.intValue()))
            .andExpect(jsonPath("$.evntNm").value(DEFAULT_EVNT_NM))
            .andExpect(jsonPath("$.evntDt").value(sameInstant(DEFAULT_EVNT_DT)))
            .andExpect(jsonPath("$.venue").value(DEFAULT_VENUE))
            .andExpect(jsonPath("$.mId").value(DEFAULT_M_ID.intValue()))
            .andExpect(jsonPath("$.mtchEndTime").value(sameInstant(DEFAULT_MTCH_END_TIME)))
            .andExpect(jsonPath("$.wPlyr1Id").value(DEFAULT_W_PLYR_1_ID.intValue()))
            .andExpect(jsonPath("$.wPlyr1Lvl").value(DEFAULT_W_PLYR_1_LVL))
            .andExpect(jsonPath("$.wPlyr1Nm").value(DEFAULT_W_PLYR_1_NM))
            .andExpect(jsonPath("$.wPlyr2Id").value(DEFAULT_W_PLYR_2_ID.intValue()))
            .andExpect(jsonPath("$.wPlyr2Lvl").value(DEFAULT_W_PLYR_2_LVL))
            .andExpect(jsonPath("$.wPlyr2Nm").value(DEFAULT_W_PLYR_2_NM))
            .andExpect(jsonPath("$.vs").value(DEFAULT_VS))
            .andExpect(jsonPath("$.lPlyr1Id").value(DEFAULT_L_PLYR_1_ID.intValue()))
            .andExpect(jsonPath("$.lPlyr1Lvl").value(DEFAULT_L_PLYR_1_LVL))
            .andExpect(jsonPath("$.lPlyr1Nm").value(DEFAULT_L_PLYR_1_NM))
            .andExpect(jsonPath("$.lPlyr2Id").value(DEFAULT_L_PLYR_2_ID.intValue()))
            .andExpect(jsonPath("$.lPlyr2Lvl").value(DEFAULT_L_PLYR_2_LVL))
            .andExpect(jsonPath("$.lPlyr2Nm").value(DEFAULT_L_PLYR_2_NM))
            .andExpect(jsonPath("$.wScr").value(DEFAULT_W_SCR))
            .andExpect(jsonPath("$.lScr").value(DEFAULT_L_SCR));
    }

    @Test
    @Transactional
    void getVwWcc701ResultsByIdFiltering() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        Long id = vwWcc701Result.getId();

        defaultVwWcc701ResultShouldBeFound("id.equals=" + id);
        defaultVwWcc701ResultShouldNotBeFound("id.notEquals=" + id);

        defaultVwWcc701ResultShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVwWcc701ResultShouldNotBeFound("id.greaterThan=" + id);

        defaultVwWcc701ResultShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVwWcc701ResultShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where eId equals to DEFAULT_E_ID
        defaultVwWcc701ResultShouldBeFound("eId.equals=" + DEFAULT_E_ID);

        // Get all the vwWcc701ResultList where eId equals to UPDATED_E_ID
        defaultVwWcc701ResultShouldNotBeFound("eId.equals=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByeIdIsInShouldWork() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where eId in DEFAULT_E_ID or UPDATED_E_ID
        defaultVwWcc701ResultShouldBeFound("eId.in=" + DEFAULT_E_ID + "," + UPDATED_E_ID);

        // Get all the vwWcc701ResultList where eId equals to UPDATED_E_ID
        defaultVwWcc701ResultShouldNotBeFound("eId.in=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where eId is not null
        defaultVwWcc701ResultShouldBeFound("eId.specified=true");

        // Get all the vwWcc701ResultList where eId is null
        defaultVwWcc701ResultShouldNotBeFound("eId.specified=false");
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where eId is greater than or equal to DEFAULT_E_ID
        defaultVwWcc701ResultShouldBeFound("eId.greaterThanOrEqual=" + DEFAULT_E_ID);

        // Get all the vwWcc701ResultList where eId is greater than or equal to UPDATED_E_ID
        defaultVwWcc701ResultShouldNotBeFound("eId.greaterThanOrEqual=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where eId is less than or equal to DEFAULT_E_ID
        defaultVwWcc701ResultShouldBeFound("eId.lessThanOrEqual=" + DEFAULT_E_ID);

        // Get all the vwWcc701ResultList where eId is less than or equal to SMALLER_E_ID
        defaultVwWcc701ResultShouldNotBeFound("eId.lessThanOrEqual=" + SMALLER_E_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where eId is less than DEFAULT_E_ID
        defaultVwWcc701ResultShouldNotBeFound("eId.lessThan=" + DEFAULT_E_ID);

        // Get all the vwWcc701ResultList where eId is less than UPDATED_E_ID
        defaultVwWcc701ResultShouldBeFound("eId.lessThan=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where eId is greater than DEFAULT_E_ID
        defaultVwWcc701ResultShouldNotBeFound("eId.greaterThan=" + DEFAULT_E_ID);

        // Get all the vwWcc701ResultList where eId is greater than SMALLER_E_ID
        defaultVwWcc701ResultShouldBeFound("eId.greaterThan=" + SMALLER_E_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByEvntNmIsEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where evntNm equals to DEFAULT_EVNT_NM
        defaultVwWcc701ResultShouldBeFound("evntNm.equals=" + DEFAULT_EVNT_NM);

        // Get all the vwWcc701ResultList where evntNm equals to UPDATED_EVNT_NM
        defaultVwWcc701ResultShouldNotBeFound("evntNm.equals=" + UPDATED_EVNT_NM);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByEvntNmIsInShouldWork() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where evntNm in DEFAULT_EVNT_NM or UPDATED_EVNT_NM
        defaultVwWcc701ResultShouldBeFound("evntNm.in=" + DEFAULT_EVNT_NM + "," + UPDATED_EVNT_NM);

        // Get all the vwWcc701ResultList where evntNm equals to UPDATED_EVNT_NM
        defaultVwWcc701ResultShouldNotBeFound("evntNm.in=" + UPDATED_EVNT_NM);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByEvntNmIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where evntNm is not null
        defaultVwWcc701ResultShouldBeFound("evntNm.specified=true");

        // Get all the vwWcc701ResultList where evntNm is null
        defaultVwWcc701ResultShouldNotBeFound("evntNm.specified=false");
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByEvntNmContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where evntNm contains DEFAULT_EVNT_NM
        defaultVwWcc701ResultShouldBeFound("evntNm.contains=" + DEFAULT_EVNT_NM);

        // Get all the vwWcc701ResultList where evntNm contains UPDATED_EVNT_NM
        defaultVwWcc701ResultShouldNotBeFound("evntNm.contains=" + UPDATED_EVNT_NM);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByEvntNmNotContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where evntNm does not contain DEFAULT_EVNT_NM
        defaultVwWcc701ResultShouldNotBeFound("evntNm.doesNotContain=" + DEFAULT_EVNT_NM);

        // Get all the vwWcc701ResultList where evntNm does not contain UPDATED_EVNT_NM
        defaultVwWcc701ResultShouldBeFound("evntNm.doesNotContain=" + UPDATED_EVNT_NM);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByEvntDtIsEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where evntDt equals to DEFAULT_EVNT_DT
        defaultVwWcc701ResultShouldBeFound("evntDt.equals=" + DEFAULT_EVNT_DT);

        // Get all the vwWcc701ResultList where evntDt equals to UPDATED_EVNT_DT
        defaultVwWcc701ResultShouldNotBeFound("evntDt.equals=" + UPDATED_EVNT_DT);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByEvntDtIsInShouldWork() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where evntDt in DEFAULT_EVNT_DT or UPDATED_EVNT_DT
        defaultVwWcc701ResultShouldBeFound("evntDt.in=" + DEFAULT_EVNT_DT + "," + UPDATED_EVNT_DT);

        // Get all the vwWcc701ResultList where evntDt equals to UPDATED_EVNT_DT
        defaultVwWcc701ResultShouldNotBeFound("evntDt.in=" + UPDATED_EVNT_DT);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByEvntDtIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where evntDt is not null
        defaultVwWcc701ResultShouldBeFound("evntDt.specified=true");

        // Get all the vwWcc701ResultList where evntDt is null
        defaultVwWcc701ResultShouldNotBeFound("evntDt.specified=false");
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByEvntDtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where evntDt is greater than or equal to DEFAULT_EVNT_DT
        defaultVwWcc701ResultShouldBeFound("evntDt.greaterThanOrEqual=" + DEFAULT_EVNT_DT);

        // Get all the vwWcc701ResultList where evntDt is greater than or equal to UPDATED_EVNT_DT
        defaultVwWcc701ResultShouldNotBeFound("evntDt.greaterThanOrEqual=" + UPDATED_EVNT_DT);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByEvntDtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where evntDt is less than or equal to DEFAULT_EVNT_DT
        defaultVwWcc701ResultShouldBeFound("evntDt.lessThanOrEqual=" + DEFAULT_EVNT_DT);

        // Get all the vwWcc701ResultList where evntDt is less than or equal to SMALLER_EVNT_DT
        defaultVwWcc701ResultShouldNotBeFound("evntDt.lessThanOrEqual=" + SMALLER_EVNT_DT);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByEvntDtIsLessThanSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where evntDt is less than DEFAULT_EVNT_DT
        defaultVwWcc701ResultShouldNotBeFound("evntDt.lessThan=" + DEFAULT_EVNT_DT);

        // Get all the vwWcc701ResultList where evntDt is less than UPDATED_EVNT_DT
        defaultVwWcc701ResultShouldBeFound("evntDt.lessThan=" + UPDATED_EVNT_DT);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByEvntDtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where evntDt is greater than DEFAULT_EVNT_DT
        defaultVwWcc701ResultShouldNotBeFound("evntDt.greaterThan=" + DEFAULT_EVNT_DT);

        // Get all the vwWcc701ResultList where evntDt is greater than SMALLER_EVNT_DT
        defaultVwWcc701ResultShouldBeFound("evntDt.greaterThan=" + SMALLER_EVNT_DT);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByVenueIsEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where venue equals to DEFAULT_VENUE
        defaultVwWcc701ResultShouldBeFound("venue.equals=" + DEFAULT_VENUE);

        // Get all the vwWcc701ResultList where venue equals to UPDATED_VENUE
        defaultVwWcc701ResultShouldNotBeFound("venue.equals=" + UPDATED_VENUE);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByVenueIsInShouldWork() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where venue in DEFAULT_VENUE or UPDATED_VENUE
        defaultVwWcc701ResultShouldBeFound("venue.in=" + DEFAULT_VENUE + "," + UPDATED_VENUE);

        // Get all the vwWcc701ResultList where venue equals to UPDATED_VENUE
        defaultVwWcc701ResultShouldNotBeFound("venue.in=" + UPDATED_VENUE);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByVenueIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where venue is not null
        defaultVwWcc701ResultShouldBeFound("venue.specified=true");

        // Get all the vwWcc701ResultList where venue is null
        defaultVwWcc701ResultShouldNotBeFound("venue.specified=false");
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByVenueContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where venue contains DEFAULT_VENUE
        defaultVwWcc701ResultShouldBeFound("venue.contains=" + DEFAULT_VENUE);

        // Get all the vwWcc701ResultList where venue contains UPDATED_VENUE
        defaultVwWcc701ResultShouldNotBeFound("venue.contains=" + UPDATED_VENUE);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByVenueNotContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where venue does not contain DEFAULT_VENUE
        defaultVwWcc701ResultShouldNotBeFound("venue.doesNotContain=" + DEFAULT_VENUE);

        // Get all the vwWcc701ResultList where venue does not contain UPDATED_VENUE
        defaultVwWcc701ResultShouldBeFound("venue.doesNotContain=" + UPDATED_VENUE);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBymIdIsEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where mId equals to DEFAULT_M_ID
        defaultVwWcc701ResultShouldBeFound("mId.equals=" + DEFAULT_M_ID);

        // Get all the vwWcc701ResultList where mId equals to UPDATED_M_ID
        defaultVwWcc701ResultShouldNotBeFound("mId.equals=" + UPDATED_M_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBymIdIsInShouldWork() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where mId in DEFAULT_M_ID or UPDATED_M_ID
        defaultVwWcc701ResultShouldBeFound("mId.in=" + DEFAULT_M_ID + "," + UPDATED_M_ID);

        // Get all the vwWcc701ResultList where mId equals to UPDATED_M_ID
        defaultVwWcc701ResultShouldNotBeFound("mId.in=" + UPDATED_M_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBymIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where mId is not null
        defaultVwWcc701ResultShouldBeFound("mId.specified=true");

        // Get all the vwWcc701ResultList where mId is null
        defaultVwWcc701ResultShouldNotBeFound("mId.specified=false");
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBymIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where mId is greater than or equal to DEFAULT_M_ID
        defaultVwWcc701ResultShouldBeFound("mId.greaterThanOrEqual=" + DEFAULT_M_ID);

        // Get all the vwWcc701ResultList where mId is greater than or equal to UPDATED_M_ID
        defaultVwWcc701ResultShouldNotBeFound("mId.greaterThanOrEqual=" + UPDATED_M_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBymIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where mId is less than or equal to DEFAULT_M_ID
        defaultVwWcc701ResultShouldBeFound("mId.lessThanOrEqual=" + DEFAULT_M_ID);

        // Get all the vwWcc701ResultList where mId is less than or equal to SMALLER_M_ID
        defaultVwWcc701ResultShouldNotBeFound("mId.lessThanOrEqual=" + SMALLER_M_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBymIdIsLessThanSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where mId is less than DEFAULT_M_ID
        defaultVwWcc701ResultShouldNotBeFound("mId.lessThan=" + DEFAULT_M_ID);

        // Get all the vwWcc701ResultList where mId is less than UPDATED_M_ID
        defaultVwWcc701ResultShouldBeFound("mId.lessThan=" + UPDATED_M_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBymIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where mId is greater than DEFAULT_M_ID
        defaultVwWcc701ResultShouldNotBeFound("mId.greaterThan=" + DEFAULT_M_ID);

        // Get all the vwWcc701ResultList where mId is greater than SMALLER_M_ID
        defaultVwWcc701ResultShouldBeFound("mId.greaterThan=" + SMALLER_M_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByMtchEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where mtchEndTime equals to DEFAULT_MTCH_END_TIME
        defaultVwWcc701ResultShouldBeFound("mtchEndTime.equals=" + DEFAULT_MTCH_END_TIME);

        // Get all the vwWcc701ResultList where mtchEndTime equals to UPDATED_MTCH_END_TIME
        defaultVwWcc701ResultShouldNotBeFound("mtchEndTime.equals=" + UPDATED_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByMtchEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where mtchEndTime in DEFAULT_MTCH_END_TIME or UPDATED_MTCH_END_TIME
        defaultVwWcc701ResultShouldBeFound("mtchEndTime.in=" + DEFAULT_MTCH_END_TIME + "," + UPDATED_MTCH_END_TIME);

        // Get all the vwWcc701ResultList where mtchEndTime equals to UPDATED_MTCH_END_TIME
        defaultVwWcc701ResultShouldNotBeFound("mtchEndTime.in=" + UPDATED_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByMtchEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where mtchEndTime is not null
        defaultVwWcc701ResultShouldBeFound("mtchEndTime.specified=true");

        // Get all the vwWcc701ResultList where mtchEndTime is null
        defaultVwWcc701ResultShouldNotBeFound("mtchEndTime.specified=false");
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByMtchEndTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where mtchEndTime is greater than or equal to DEFAULT_MTCH_END_TIME
        defaultVwWcc701ResultShouldBeFound("mtchEndTime.greaterThanOrEqual=" + DEFAULT_MTCH_END_TIME);

        // Get all the vwWcc701ResultList where mtchEndTime is greater than or equal to UPDATED_MTCH_END_TIME
        defaultVwWcc701ResultShouldNotBeFound("mtchEndTime.greaterThanOrEqual=" + UPDATED_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByMtchEndTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where mtchEndTime is less than or equal to DEFAULT_MTCH_END_TIME
        defaultVwWcc701ResultShouldBeFound("mtchEndTime.lessThanOrEqual=" + DEFAULT_MTCH_END_TIME);

        // Get all the vwWcc701ResultList where mtchEndTime is less than or equal to SMALLER_MTCH_END_TIME
        defaultVwWcc701ResultShouldNotBeFound("mtchEndTime.lessThanOrEqual=" + SMALLER_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByMtchEndTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where mtchEndTime is less than DEFAULT_MTCH_END_TIME
        defaultVwWcc701ResultShouldNotBeFound("mtchEndTime.lessThan=" + DEFAULT_MTCH_END_TIME);

        // Get all the vwWcc701ResultList where mtchEndTime is less than UPDATED_MTCH_END_TIME
        defaultVwWcc701ResultShouldBeFound("mtchEndTime.lessThan=" + UPDATED_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByMtchEndTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where mtchEndTime is greater than DEFAULT_MTCH_END_TIME
        defaultVwWcc701ResultShouldNotBeFound("mtchEndTime.greaterThan=" + DEFAULT_MTCH_END_TIME);

        // Get all the vwWcc701ResultList where mtchEndTime is greater than SMALLER_MTCH_END_TIME
        defaultVwWcc701ResultShouldBeFound("mtchEndTime.greaterThan=" + SMALLER_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr1IdIsEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr1Id equals to DEFAULT_W_PLYR_1_ID
        defaultVwWcc701ResultShouldBeFound("wPlyr1Id.equals=" + DEFAULT_W_PLYR_1_ID);

        // Get all the vwWcc701ResultList where wPlyr1Id equals to UPDATED_W_PLYR_1_ID
        defaultVwWcc701ResultShouldNotBeFound("wPlyr1Id.equals=" + UPDATED_W_PLYR_1_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr1IdIsInShouldWork() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr1Id in DEFAULT_W_PLYR_1_ID or UPDATED_W_PLYR_1_ID
        defaultVwWcc701ResultShouldBeFound("wPlyr1Id.in=" + DEFAULT_W_PLYR_1_ID + "," + UPDATED_W_PLYR_1_ID);

        // Get all the vwWcc701ResultList where wPlyr1Id equals to UPDATED_W_PLYR_1_ID
        defaultVwWcc701ResultShouldNotBeFound("wPlyr1Id.in=" + UPDATED_W_PLYR_1_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr1IdIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr1Id is not null
        defaultVwWcc701ResultShouldBeFound("wPlyr1Id.specified=true");

        // Get all the vwWcc701ResultList where wPlyr1Id is null
        defaultVwWcc701ResultShouldNotBeFound("wPlyr1Id.specified=false");
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr1IdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr1Id is greater than or equal to DEFAULT_W_PLYR_1_ID
        defaultVwWcc701ResultShouldBeFound("wPlyr1Id.greaterThanOrEqual=" + DEFAULT_W_PLYR_1_ID);

        // Get all the vwWcc701ResultList where wPlyr1Id is greater than or equal to UPDATED_W_PLYR_1_ID
        defaultVwWcc701ResultShouldNotBeFound("wPlyr1Id.greaterThanOrEqual=" + UPDATED_W_PLYR_1_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr1IdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr1Id is less than or equal to DEFAULT_W_PLYR_1_ID
        defaultVwWcc701ResultShouldBeFound("wPlyr1Id.lessThanOrEqual=" + DEFAULT_W_PLYR_1_ID);

        // Get all the vwWcc701ResultList where wPlyr1Id is less than or equal to SMALLER_W_PLYR_1_ID
        defaultVwWcc701ResultShouldNotBeFound("wPlyr1Id.lessThanOrEqual=" + SMALLER_W_PLYR_1_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr1IdIsLessThanSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr1Id is less than DEFAULT_W_PLYR_1_ID
        defaultVwWcc701ResultShouldNotBeFound("wPlyr1Id.lessThan=" + DEFAULT_W_PLYR_1_ID);

        // Get all the vwWcc701ResultList where wPlyr1Id is less than UPDATED_W_PLYR_1_ID
        defaultVwWcc701ResultShouldBeFound("wPlyr1Id.lessThan=" + UPDATED_W_PLYR_1_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr1IdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr1Id is greater than DEFAULT_W_PLYR_1_ID
        defaultVwWcc701ResultShouldNotBeFound("wPlyr1Id.greaterThan=" + DEFAULT_W_PLYR_1_ID);

        // Get all the vwWcc701ResultList where wPlyr1Id is greater than SMALLER_W_PLYR_1_ID
        defaultVwWcc701ResultShouldBeFound("wPlyr1Id.greaterThan=" + SMALLER_W_PLYR_1_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr1LvlIsEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr1Lvl equals to DEFAULT_W_PLYR_1_LVL
        defaultVwWcc701ResultShouldBeFound("wPlyr1Lvl.equals=" + DEFAULT_W_PLYR_1_LVL);

        // Get all the vwWcc701ResultList where wPlyr1Lvl equals to UPDATED_W_PLYR_1_LVL
        defaultVwWcc701ResultShouldNotBeFound("wPlyr1Lvl.equals=" + UPDATED_W_PLYR_1_LVL);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr1LvlIsInShouldWork() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr1Lvl in DEFAULT_W_PLYR_1_LVL or UPDATED_W_PLYR_1_LVL
        defaultVwWcc701ResultShouldBeFound("wPlyr1Lvl.in=" + DEFAULT_W_PLYR_1_LVL + "," + UPDATED_W_PLYR_1_LVL);

        // Get all the vwWcc701ResultList where wPlyr1Lvl equals to UPDATED_W_PLYR_1_LVL
        defaultVwWcc701ResultShouldNotBeFound("wPlyr1Lvl.in=" + UPDATED_W_PLYR_1_LVL);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr1LvlIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr1Lvl is not null
        defaultVwWcc701ResultShouldBeFound("wPlyr1Lvl.specified=true");

        // Get all the vwWcc701ResultList where wPlyr1Lvl is null
        defaultVwWcc701ResultShouldNotBeFound("wPlyr1Lvl.specified=false");
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr1LvlContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr1Lvl contains DEFAULT_W_PLYR_1_LVL
        defaultVwWcc701ResultShouldBeFound("wPlyr1Lvl.contains=" + DEFAULT_W_PLYR_1_LVL);

        // Get all the vwWcc701ResultList where wPlyr1Lvl contains UPDATED_W_PLYR_1_LVL
        defaultVwWcc701ResultShouldNotBeFound("wPlyr1Lvl.contains=" + UPDATED_W_PLYR_1_LVL);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr1LvlNotContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr1Lvl does not contain DEFAULT_W_PLYR_1_LVL
        defaultVwWcc701ResultShouldNotBeFound("wPlyr1Lvl.doesNotContain=" + DEFAULT_W_PLYR_1_LVL);

        // Get all the vwWcc701ResultList where wPlyr1Lvl does not contain UPDATED_W_PLYR_1_LVL
        defaultVwWcc701ResultShouldBeFound("wPlyr1Lvl.doesNotContain=" + UPDATED_W_PLYR_1_LVL);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr1NmIsEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr1Nm equals to DEFAULT_W_PLYR_1_NM
        defaultVwWcc701ResultShouldBeFound("wPlyr1Nm.equals=" + DEFAULT_W_PLYR_1_NM);

        // Get all the vwWcc701ResultList where wPlyr1Nm equals to UPDATED_W_PLYR_1_NM
        defaultVwWcc701ResultShouldNotBeFound("wPlyr1Nm.equals=" + UPDATED_W_PLYR_1_NM);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr1NmIsInShouldWork() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr1Nm in DEFAULT_W_PLYR_1_NM or UPDATED_W_PLYR_1_NM
        defaultVwWcc701ResultShouldBeFound("wPlyr1Nm.in=" + DEFAULT_W_PLYR_1_NM + "," + UPDATED_W_PLYR_1_NM);

        // Get all the vwWcc701ResultList where wPlyr1Nm equals to UPDATED_W_PLYR_1_NM
        defaultVwWcc701ResultShouldNotBeFound("wPlyr1Nm.in=" + UPDATED_W_PLYR_1_NM);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr1NmIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr1Nm is not null
        defaultVwWcc701ResultShouldBeFound("wPlyr1Nm.specified=true");

        // Get all the vwWcc701ResultList where wPlyr1Nm is null
        defaultVwWcc701ResultShouldNotBeFound("wPlyr1Nm.specified=false");
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr1NmContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr1Nm contains DEFAULT_W_PLYR_1_NM
        defaultVwWcc701ResultShouldBeFound("wPlyr1Nm.contains=" + DEFAULT_W_PLYR_1_NM);

        // Get all the vwWcc701ResultList where wPlyr1Nm contains UPDATED_W_PLYR_1_NM
        defaultVwWcc701ResultShouldNotBeFound("wPlyr1Nm.contains=" + UPDATED_W_PLYR_1_NM);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr1NmNotContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr1Nm does not contain DEFAULT_W_PLYR_1_NM
        defaultVwWcc701ResultShouldNotBeFound("wPlyr1Nm.doesNotContain=" + DEFAULT_W_PLYR_1_NM);

        // Get all the vwWcc701ResultList where wPlyr1Nm does not contain UPDATED_W_PLYR_1_NM
        defaultVwWcc701ResultShouldBeFound("wPlyr1Nm.doesNotContain=" + UPDATED_W_PLYR_1_NM);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr2IdIsEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr2Id equals to DEFAULT_W_PLYR_2_ID
        defaultVwWcc701ResultShouldBeFound("wPlyr2Id.equals=" + DEFAULT_W_PLYR_2_ID);

        // Get all the vwWcc701ResultList where wPlyr2Id equals to UPDATED_W_PLYR_2_ID
        defaultVwWcc701ResultShouldNotBeFound("wPlyr2Id.equals=" + UPDATED_W_PLYR_2_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr2IdIsInShouldWork() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr2Id in DEFAULT_W_PLYR_2_ID or UPDATED_W_PLYR_2_ID
        defaultVwWcc701ResultShouldBeFound("wPlyr2Id.in=" + DEFAULT_W_PLYR_2_ID + "," + UPDATED_W_PLYR_2_ID);

        // Get all the vwWcc701ResultList where wPlyr2Id equals to UPDATED_W_PLYR_2_ID
        defaultVwWcc701ResultShouldNotBeFound("wPlyr2Id.in=" + UPDATED_W_PLYR_2_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr2IdIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr2Id is not null
        defaultVwWcc701ResultShouldBeFound("wPlyr2Id.specified=true");

        // Get all the vwWcc701ResultList where wPlyr2Id is null
        defaultVwWcc701ResultShouldNotBeFound("wPlyr2Id.specified=false");
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr2IdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr2Id is greater than or equal to DEFAULT_W_PLYR_2_ID
        defaultVwWcc701ResultShouldBeFound("wPlyr2Id.greaterThanOrEqual=" + DEFAULT_W_PLYR_2_ID);

        // Get all the vwWcc701ResultList where wPlyr2Id is greater than or equal to UPDATED_W_PLYR_2_ID
        defaultVwWcc701ResultShouldNotBeFound("wPlyr2Id.greaterThanOrEqual=" + UPDATED_W_PLYR_2_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr2IdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr2Id is less than or equal to DEFAULT_W_PLYR_2_ID
        defaultVwWcc701ResultShouldBeFound("wPlyr2Id.lessThanOrEqual=" + DEFAULT_W_PLYR_2_ID);

        // Get all the vwWcc701ResultList where wPlyr2Id is less than or equal to SMALLER_W_PLYR_2_ID
        defaultVwWcc701ResultShouldNotBeFound("wPlyr2Id.lessThanOrEqual=" + SMALLER_W_PLYR_2_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr2IdIsLessThanSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr2Id is less than DEFAULT_W_PLYR_2_ID
        defaultVwWcc701ResultShouldNotBeFound("wPlyr2Id.lessThan=" + DEFAULT_W_PLYR_2_ID);

        // Get all the vwWcc701ResultList where wPlyr2Id is less than UPDATED_W_PLYR_2_ID
        defaultVwWcc701ResultShouldBeFound("wPlyr2Id.lessThan=" + UPDATED_W_PLYR_2_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr2IdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr2Id is greater than DEFAULT_W_PLYR_2_ID
        defaultVwWcc701ResultShouldNotBeFound("wPlyr2Id.greaterThan=" + DEFAULT_W_PLYR_2_ID);

        // Get all the vwWcc701ResultList where wPlyr2Id is greater than SMALLER_W_PLYR_2_ID
        defaultVwWcc701ResultShouldBeFound("wPlyr2Id.greaterThan=" + SMALLER_W_PLYR_2_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr2LvlIsEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr2Lvl equals to DEFAULT_W_PLYR_2_LVL
        defaultVwWcc701ResultShouldBeFound("wPlyr2Lvl.equals=" + DEFAULT_W_PLYR_2_LVL);

        // Get all the vwWcc701ResultList where wPlyr2Lvl equals to UPDATED_W_PLYR_2_LVL
        defaultVwWcc701ResultShouldNotBeFound("wPlyr2Lvl.equals=" + UPDATED_W_PLYR_2_LVL);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr2LvlIsInShouldWork() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr2Lvl in DEFAULT_W_PLYR_2_LVL or UPDATED_W_PLYR_2_LVL
        defaultVwWcc701ResultShouldBeFound("wPlyr2Lvl.in=" + DEFAULT_W_PLYR_2_LVL + "," + UPDATED_W_PLYR_2_LVL);

        // Get all the vwWcc701ResultList where wPlyr2Lvl equals to UPDATED_W_PLYR_2_LVL
        defaultVwWcc701ResultShouldNotBeFound("wPlyr2Lvl.in=" + UPDATED_W_PLYR_2_LVL);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr2LvlIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr2Lvl is not null
        defaultVwWcc701ResultShouldBeFound("wPlyr2Lvl.specified=true");

        // Get all the vwWcc701ResultList where wPlyr2Lvl is null
        defaultVwWcc701ResultShouldNotBeFound("wPlyr2Lvl.specified=false");
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr2LvlContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr2Lvl contains DEFAULT_W_PLYR_2_LVL
        defaultVwWcc701ResultShouldBeFound("wPlyr2Lvl.contains=" + DEFAULT_W_PLYR_2_LVL);

        // Get all the vwWcc701ResultList where wPlyr2Lvl contains UPDATED_W_PLYR_2_LVL
        defaultVwWcc701ResultShouldNotBeFound("wPlyr2Lvl.contains=" + UPDATED_W_PLYR_2_LVL);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr2LvlNotContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr2Lvl does not contain DEFAULT_W_PLYR_2_LVL
        defaultVwWcc701ResultShouldNotBeFound("wPlyr2Lvl.doesNotContain=" + DEFAULT_W_PLYR_2_LVL);

        // Get all the vwWcc701ResultList where wPlyr2Lvl does not contain UPDATED_W_PLYR_2_LVL
        defaultVwWcc701ResultShouldBeFound("wPlyr2Lvl.doesNotContain=" + UPDATED_W_PLYR_2_LVL);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr2NmIsEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr2Nm equals to DEFAULT_W_PLYR_2_NM
        defaultVwWcc701ResultShouldBeFound("wPlyr2Nm.equals=" + DEFAULT_W_PLYR_2_NM);

        // Get all the vwWcc701ResultList where wPlyr2Nm equals to UPDATED_W_PLYR_2_NM
        defaultVwWcc701ResultShouldNotBeFound("wPlyr2Nm.equals=" + UPDATED_W_PLYR_2_NM);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr2NmIsInShouldWork() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr2Nm in DEFAULT_W_PLYR_2_NM or UPDATED_W_PLYR_2_NM
        defaultVwWcc701ResultShouldBeFound("wPlyr2Nm.in=" + DEFAULT_W_PLYR_2_NM + "," + UPDATED_W_PLYR_2_NM);

        // Get all the vwWcc701ResultList where wPlyr2Nm equals to UPDATED_W_PLYR_2_NM
        defaultVwWcc701ResultShouldNotBeFound("wPlyr2Nm.in=" + UPDATED_W_PLYR_2_NM);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr2NmIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr2Nm is not null
        defaultVwWcc701ResultShouldBeFound("wPlyr2Nm.specified=true");

        // Get all the vwWcc701ResultList where wPlyr2Nm is null
        defaultVwWcc701ResultShouldNotBeFound("wPlyr2Nm.specified=false");
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr2NmContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr2Nm contains DEFAULT_W_PLYR_2_NM
        defaultVwWcc701ResultShouldBeFound("wPlyr2Nm.contains=" + DEFAULT_W_PLYR_2_NM);

        // Get all the vwWcc701ResultList where wPlyr2Nm contains UPDATED_W_PLYR_2_NM
        defaultVwWcc701ResultShouldNotBeFound("wPlyr2Nm.contains=" + UPDATED_W_PLYR_2_NM);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywPlyr2NmNotContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wPlyr2Nm does not contain DEFAULT_W_PLYR_2_NM
        defaultVwWcc701ResultShouldNotBeFound("wPlyr2Nm.doesNotContain=" + DEFAULT_W_PLYR_2_NM);

        // Get all the vwWcc701ResultList where wPlyr2Nm does not contain UPDATED_W_PLYR_2_NM
        defaultVwWcc701ResultShouldBeFound("wPlyr2Nm.doesNotContain=" + UPDATED_W_PLYR_2_NM);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByVsIsEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where vs equals to DEFAULT_VS
        defaultVwWcc701ResultShouldBeFound("vs.equals=" + DEFAULT_VS);

        // Get all the vwWcc701ResultList where vs equals to UPDATED_VS
        defaultVwWcc701ResultShouldNotBeFound("vs.equals=" + UPDATED_VS);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByVsIsInShouldWork() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where vs in DEFAULT_VS or UPDATED_VS
        defaultVwWcc701ResultShouldBeFound("vs.in=" + DEFAULT_VS + "," + UPDATED_VS);

        // Get all the vwWcc701ResultList where vs equals to UPDATED_VS
        defaultVwWcc701ResultShouldNotBeFound("vs.in=" + UPDATED_VS);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByVsIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where vs is not null
        defaultVwWcc701ResultShouldBeFound("vs.specified=true");

        // Get all the vwWcc701ResultList where vs is null
        defaultVwWcc701ResultShouldNotBeFound("vs.specified=false");
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByVsContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where vs contains DEFAULT_VS
        defaultVwWcc701ResultShouldBeFound("vs.contains=" + DEFAULT_VS);

        // Get all the vwWcc701ResultList where vs contains UPDATED_VS
        defaultVwWcc701ResultShouldNotBeFound("vs.contains=" + UPDATED_VS);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsByVsNotContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where vs does not contain DEFAULT_VS
        defaultVwWcc701ResultShouldNotBeFound("vs.doesNotContain=" + DEFAULT_VS);

        // Get all the vwWcc701ResultList where vs does not contain UPDATED_VS
        defaultVwWcc701ResultShouldBeFound("vs.doesNotContain=" + UPDATED_VS);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr1IdIsEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr1Id equals to DEFAULT_L_PLYR_1_ID
        defaultVwWcc701ResultShouldBeFound("lPlyr1Id.equals=" + DEFAULT_L_PLYR_1_ID);

        // Get all the vwWcc701ResultList where lPlyr1Id equals to UPDATED_L_PLYR_1_ID
        defaultVwWcc701ResultShouldNotBeFound("lPlyr1Id.equals=" + UPDATED_L_PLYR_1_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr1IdIsInShouldWork() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr1Id in DEFAULT_L_PLYR_1_ID or UPDATED_L_PLYR_1_ID
        defaultVwWcc701ResultShouldBeFound("lPlyr1Id.in=" + DEFAULT_L_PLYR_1_ID + "," + UPDATED_L_PLYR_1_ID);

        // Get all the vwWcc701ResultList where lPlyr1Id equals to UPDATED_L_PLYR_1_ID
        defaultVwWcc701ResultShouldNotBeFound("lPlyr1Id.in=" + UPDATED_L_PLYR_1_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr1IdIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr1Id is not null
        defaultVwWcc701ResultShouldBeFound("lPlyr1Id.specified=true");

        // Get all the vwWcc701ResultList where lPlyr1Id is null
        defaultVwWcc701ResultShouldNotBeFound("lPlyr1Id.specified=false");
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr1IdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr1Id is greater than or equal to DEFAULT_L_PLYR_1_ID
        defaultVwWcc701ResultShouldBeFound("lPlyr1Id.greaterThanOrEqual=" + DEFAULT_L_PLYR_1_ID);

        // Get all the vwWcc701ResultList where lPlyr1Id is greater than or equal to UPDATED_L_PLYR_1_ID
        defaultVwWcc701ResultShouldNotBeFound("lPlyr1Id.greaterThanOrEqual=" + UPDATED_L_PLYR_1_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr1IdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr1Id is less than or equal to DEFAULT_L_PLYR_1_ID
        defaultVwWcc701ResultShouldBeFound("lPlyr1Id.lessThanOrEqual=" + DEFAULT_L_PLYR_1_ID);

        // Get all the vwWcc701ResultList where lPlyr1Id is less than or equal to SMALLER_L_PLYR_1_ID
        defaultVwWcc701ResultShouldNotBeFound("lPlyr1Id.lessThanOrEqual=" + SMALLER_L_PLYR_1_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr1IdIsLessThanSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr1Id is less than DEFAULT_L_PLYR_1_ID
        defaultVwWcc701ResultShouldNotBeFound("lPlyr1Id.lessThan=" + DEFAULT_L_PLYR_1_ID);

        // Get all the vwWcc701ResultList where lPlyr1Id is less than UPDATED_L_PLYR_1_ID
        defaultVwWcc701ResultShouldBeFound("lPlyr1Id.lessThan=" + UPDATED_L_PLYR_1_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr1IdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr1Id is greater than DEFAULT_L_PLYR_1_ID
        defaultVwWcc701ResultShouldNotBeFound("lPlyr1Id.greaterThan=" + DEFAULT_L_PLYR_1_ID);

        // Get all the vwWcc701ResultList where lPlyr1Id is greater than SMALLER_L_PLYR_1_ID
        defaultVwWcc701ResultShouldBeFound("lPlyr1Id.greaterThan=" + SMALLER_L_PLYR_1_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr1LvlIsEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr1Lvl equals to DEFAULT_L_PLYR_1_LVL
        defaultVwWcc701ResultShouldBeFound("lPlyr1Lvl.equals=" + DEFAULT_L_PLYR_1_LVL);

        // Get all the vwWcc701ResultList where lPlyr1Lvl equals to UPDATED_L_PLYR_1_LVL
        defaultVwWcc701ResultShouldNotBeFound("lPlyr1Lvl.equals=" + UPDATED_L_PLYR_1_LVL);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr1LvlIsInShouldWork() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr1Lvl in DEFAULT_L_PLYR_1_LVL or UPDATED_L_PLYR_1_LVL
        defaultVwWcc701ResultShouldBeFound("lPlyr1Lvl.in=" + DEFAULT_L_PLYR_1_LVL + "," + UPDATED_L_PLYR_1_LVL);

        // Get all the vwWcc701ResultList where lPlyr1Lvl equals to UPDATED_L_PLYR_1_LVL
        defaultVwWcc701ResultShouldNotBeFound("lPlyr1Lvl.in=" + UPDATED_L_PLYR_1_LVL);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr1LvlIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr1Lvl is not null
        defaultVwWcc701ResultShouldBeFound("lPlyr1Lvl.specified=true");

        // Get all the vwWcc701ResultList where lPlyr1Lvl is null
        defaultVwWcc701ResultShouldNotBeFound("lPlyr1Lvl.specified=false");
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr1LvlContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr1Lvl contains DEFAULT_L_PLYR_1_LVL
        defaultVwWcc701ResultShouldBeFound("lPlyr1Lvl.contains=" + DEFAULT_L_PLYR_1_LVL);

        // Get all the vwWcc701ResultList where lPlyr1Lvl contains UPDATED_L_PLYR_1_LVL
        defaultVwWcc701ResultShouldNotBeFound("lPlyr1Lvl.contains=" + UPDATED_L_PLYR_1_LVL);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr1LvlNotContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr1Lvl does not contain DEFAULT_L_PLYR_1_LVL
        defaultVwWcc701ResultShouldNotBeFound("lPlyr1Lvl.doesNotContain=" + DEFAULT_L_PLYR_1_LVL);

        // Get all the vwWcc701ResultList where lPlyr1Lvl does not contain UPDATED_L_PLYR_1_LVL
        defaultVwWcc701ResultShouldBeFound("lPlyr1Lvl.doesNotContain=" + UPDATED_L_PLYR_1_LVL);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr1NmIsEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr1Nm equals to DEFAULT_L_PLYR_1_NM
        defaultVwWcc701ResultShouldBeFound("lPlyr1Nm.equals=" + DEFAULT_L_PLYR_1_NM);

        // Get all the vwWcc701ResultList where lPlyr1Nm equals to UPDATED_L_PLYR_1_NM
        defaultVwWcc701ResultShouldNotBeFound("lPlyr1Nm.equals=" + UPDATED_L_PLYR_1_NM);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr1NmIsInShouldWork() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr1Nm in DEFAULT_L_PLYR_1_NM or UPDATED_L_PLYR_1_NM
        defaultVwWcc701ResultShouldBeFound("lPlyr1Nm.in=" + DEFAULT_L_PLYR_1_NM + "," + UPDATED_L_PLYR_1_NM);

        // Get all the vwWcc701ResultList where lPlyr1Nm equals to UPDATED_L_PLYR_1_NM
        defaultVwWcc701ResultShouldNotBeFound("lPlyr1Nm.in=" + UPDATED_L_PLYR_1_NM);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr1NmIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr1Nm is not null
        defaultVwWcc701ResultShouldBeFound("lPlyr1Nm.specified=true");

        // Get all the vwWcc701ResultList where lPlyr1Nm is null
        defaultVwWcc701ResultShouldNotBeFound("lPlyr1Nm.specified=false");
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr1NmContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr1Nm contains DEFAULT_L_PLYR_1_NM
        defaultVwWcc701ResultShouldBeFound("lPlyr1Nm.contains=" + DEFAULT_L_PLYR_1_NM);

        // Get all the vwWcc701ResultList where lPlyr1Nm contains UPDATED_L_PLYR_1_NM
        defaultVwWcc701ResultShouldNotBeFound("lPlyr1Nm.contains=" + UPDATED_L_PLYR_1_NM);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr1NmNotContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr1Nm does not contain DEFAULT_L_PLYR_1_NM
        defaultVwWcc701ResultShouldNotBeFound("lPlyr1Nm.doesNotContain=" + DEFAULT_L_PLYR_1_NM);

        // Get all the vwWcc701ResultList where lPlyr1Nm does not contain UPDATED_L_PLYR_1_NM
        defaultVwWcc701ResultShouldBeFound("lPlyr1Nm.doesNotContain=" + UPDATED_L_PLYR_1_NM);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr2IdIsEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr2Id equals to DEFAULT_L_PLYR_2_ID
        defaultVwWcc701ResultShouldBeFound("lPlyr2Id.equals=" + DEFAULT_L_PLYR_2_ID);

        // Get all the vwWcc701ResultList where lPlyr2Id equals to UPDATED_L_PLYR_2_ID
        defaultVwWcc701ResultShouldNotBeFound("lPlyr2Id.equals=" + UPDATED_L_PLYR_2_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr2IdIsInShouldWork() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr2Id in DEFAULT_L_PLYR_2_ID or UPDATED_L_PLYR_2_ID
        defaultVwWcc701ResultShouldBeFound("lPlyr2Id.in=" + DEFAULT_L_PLYR_2_ID + "," + UPDATED_L_PLYR_2_ID);

        // Get all the vwWcc701ResultList where lPlyr2Id equals to UPDATED_L_PLYR_2_ID
        defaultVwWcc701ResultShouldNotBeFound("lPlyr2Id.in=" + UPDATED_L_PLYR_2_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr2IdIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr2Id is not null
        defaultVwWcc701ResultShouldBeFound("lPlyr2Id.specified=true");

        // Get all the vwWcc701ResultList where lPlyr2Id is null
        defaultVwWcc701ResultShouldNotBeFound("lPlyr2Id.specified=false");
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr2IdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr2Id is greater than or equal to DEFAULT_L_PLYR_2_ID
        defaultVwWcc701ResultShouldBeFound("lPlyr2Id.greaterThanOrEqual=" + DEFAULT_L_PLYR_2_ID);

        // Get all the vwWcc701ResultList where lPlyr2Id is greater than or equal to UPDATED_L_PLYR_2_ID
        defaultVwWcc701ResultShouldNotBeFound("lPlyr2Id.greaterThanOrEqual=" + UPDATED_L_PLYR_2_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr2IdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr2Id is less than or equal to DEFAULT_L_PLYR_2_ID
        defaultVwWcc701ResultShouldBeFound("lPlyr2Id.lessThanOrEqual=" + DEFAULT_L_PLYR_2_ID);

        // Get all the vwWcc701ResultList where lPlyr2Id is less than or equal to SMALLER_L_PLYR_2_ID
        defaultVwWcc701ResultShouldNotBeFound("lPlyr2Id.lessThanOrEqual=" + SMALLER_L_PLYR_2_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr2IdIsLessThanSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr2Id is less than DEFAULT_L_PLYR_2_ID
        defaultVwWcc701ResultShouldNotBeFound("lPlyr2Id.lessThan=" + DEFAULT_L_PLYR_2_ID);

        // Get all the vwWcc701ResultList where lPlyr2Id is less than UPDATED_L_PLYR_2_ID
        defaultVwWcc701ResultShouldBeFound("lPlyr2Id.lessThan=" + UPDATED_L_PLYR_2_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr2IdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr2Id is greater than DEFAULT_L_PLYR_2_ID
        defaultVwWcc701ResultShouldNotBeFound("lPlyr2Id.greaterThan=" + DEFAULT_L_PLYR_2_ID);

        // Get all the vwWcc701ResultList where lPlyr2Id is greater than SMALLER_L_PLYR_2_ID
        defaultVwWcc701ResultShouldBeFound("lPlyr2Id.greaterThan=" + SMALLER_L_PLYR_2_ID);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr2LvlIsEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr2Lvl equals to DEFAULT_L_PLYR_2_LVL
        defaultVwWcc701ResultShouldBeFound("lPlyr2Lvl.equals=" + DEFAULT_L_PLYR_2_LVL);

        // Get all the vwWcc701ResultList where lPlyr2Lvl equals to UPDATED_L_PLYR_2_LVL
        defaultVwWcc701ResultShouldNotBeFound("lPlyr2Lvl.equals=" + UPDATED_L_PLYR_2_LVL);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr2LvlIsInShouldWork() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr2Lvl in DEFAULT_L_PLYR_2_LVL or UPDATED_L_PLYR_2_LVL
        defaultVwWcc701ResultShouldBeFound("lPlyr2Lvl.in=" + DEFAULT_L_PLYR_2_LVL + "," + UPDATED_L_PLYR_2_LVL);

        // Get all the vwWcc701ResultList where lPlyr2Lvl equals to UPDATED_L_PLYR_2_LVL
        defaultVwWcc701ResultShouldNotBeFound("lPlyr2Lvl.in=" + UPDATED_L_PLYR_2_LVL);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr2LvlIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr2Lvl is not null
        defaultVwWcc701ResultShouldBeFound("lPlyr2Lvl.specified=true");

        // Get all the vwWcc701ResultList where lPlyr2Lvl is null
        defaultVwWcc701ResultShouldNotBeFound("lPlyr2Lvl.specified=false");
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr2LvlContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr2Lvl contains DEFAULT_L_PLYR_2_LVL
        defaultVwWcc701ResultShouldBeFound("lPlyr2Lvl.contains=" + DEFAULT_L_PLYR_2_LVL);

        // Get all the vwWcc701ResultList where lPlyr2Lvl contains UPDATED_L_PLYR_2_LVL
        defaultVwWcc701ResultShouldNotBeFound("lPlyr2Lvl.contains=" + UPDATED_L_PLYR_2_LVL);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr2LvlNotContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr2Lvl does not contain DEFAULT_L_PLYR_2_LVL
        defaultVwWcc701ResultShouldNotBeFound("lPlyr2Lvl.doesNotContain=" + DEFAULT_L_PLYR_2_LVL);

        // Get all the vwWcc701ResultList where lPlyr2Lvl does not contain UPDATED_L_PLYR_2_LVL
        defaultVwWcc701ResultShouldBeFound("lPlyr2Lvl.doesNotContain=" + UPDATED_L_PLYR_2_LVL);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr2NmIsEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr2Nm equals to DEFAULT_L_PLYR_2_NM
        defaultVwWcc701ResultShouldBeFound("lPlyr2Nm.equals=" + DEFAULT_L_PLYR_2_NM);

        // Get all the vwWcc701ResultList where lPlyr2Nm equals to UPDATED_L_PLYR_2_NM
        defaultVwWcc701ResultShouldNotBeFound("lPlyr2Nm.equals=" + UPDATED_L_PLYR_2_NM);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr2NmIsInShouldWork() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr2Nm in DEFAULT_L_PLYR_2_NM or UPDATED_L_PLYR_2_NM
        defaultVwWcc701ResultShouldBeFound("lPlyr2Nm.in=" + DEFAULT_L_PLYR_2_NM + "," + UPDATED_L_PLYR_2_NM);

        // Get all the vwWcc701ResultList where lPlyr2Nm equals to UPDATED_L_PLYR_2_NM
        defaultVwWcc701ResultShouldNotBeFound("lPlyr2Nm.in=" + UPDATED_L_PLYR_2_NM);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr2NmIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr2Nm is not null
        defaultVwWcc701ResultShouldBeFound("lPlyr2Nm.specified=true");

        // Get all the vwWcc701ResultList where lPlyr2Nm is null
        defaultVwWcc701ResultShouldNotBeFound("lPlyr2Nm.specified=false");
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr2NmContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr2Nm contains DEFAULT_L_PLYR_2_NM
        defaultVwWcc701ResultShouldBeFound("lPlyr2Nm.contains=" + DEFAULT_L_PLYR_2_NM);

        // Get all the vwWcc701ResultList where lPlyr2Nm contains UPDATED_L_PLYR_2_NM
        defaultVwWcc701ResultShouldNotBeFound("lPlyr2Nm.contains=" + UPDATED_L_PLYR_2_NM);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylPlyr2NmNotContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lPlyr2Nm does not contain DEFAULT_L_PLYR_2_NM
        defaultVwWcc701ResultShouldNotBeFound("lPlyr2Nm.doesNotContain=" + DEFAULT_L_PLYR_2_NM);

        // Get all the vwWcc701ResultList where lPlyr2Nm does not contain UPDATED_L_PLYR_2_NM
        defaultVwWcc701ResultShouldBeFound("lPlyr2Nm.doesNotContain=" + UPDATED_L_PLYR_2_NM);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywScrIsEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wScr equals to DEFAULT_W_SCR
        defaultVwWcc701ResultShouldBeFound("wScr.equals=" + DEFAULT_W_SCR);

        // Get all the vwWcc701ResultList where wScr equals to UPDATED_W_SCR
        defaultVwWcc701ResultShouldNotBeFound("wScr.equals=" + UPDATED_W_SCR);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywScrIsInShouldWork() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wScr in DEFAULT_W_SCR or UPDATED_W_SCR
        defaultVwWcc701ResultShouldBeFound("wScr.in=" + DEFAULT_W_SCR + "," + UPDATED_W_SCR);

        // Get all the vwWcc701ResultList where wScr equals to UPDATED_W_SCR
        defaultVwWcc701ResultShouldNotBeFound("wScr.in=" + UPDATED_W_SCR);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywScrIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wScr is not null
        defaultVwWcc701ResultShouldBeFound("wScr.specified=true");

        // Get all the vwWcc701ResultList where wScr is null
        defaultVwWcc701ResultShouldNotBeFound("wScr.specified=false");
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywScrContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wScr contains DEFAULT_W_SCR
        defaultVwWcc701ResultShouldBeFound("wScr.contains=" + DEFAULT_W_SCR);

        // Get all the vwWcc701ResultList where wScr contains UPDATED_W_SCR
        defaultVwWcc701ResultShouldNotBeFound("wScr.contains=" + UPDATED_W_SCR);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBywScrNotContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where wScr does not contain DEFAULT_W_SCR
        defaultVwWcc701ResultShouldNotBeFound("wScr.doesNotContain=" + DEFAULT_W_SCR);

        // Get all the vwWcc701ResultList where wScr does not contain UPDATED_W_SCR
        defaultVwWcc701ResultShouldBeFound("wScr.doesNotContain=" + UPDATED_W_SCR);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylScrIsEqualToSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lScr equals to DEFAULT_L_SCR
        defaultVwWcc701ResultShouldBeFound("lScr.equals=" + DEFAULT_L_SCR);

        // Get all the vwWcc701ResultList where lScr equals to UPDATED_L_SCR
        defaultVwWcc701ResultShouldNotBeFound("lScr.equals=" + UPDATED_L_SCR);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylScrIsInShouldWork() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lScr in DEFAULT_L_SCR or UPDATED_L_SCR
        defaultVwWcc701ResultShouldBeFound("lScr.in=" + DEFAULT_L_SCR + "," + UPDATED_L_SCR);

        // Get all the vwWcc701ResultList where lScr equals to UPDATED_L_SCR
        defaultVwWcc701ResultShouldNotBeFound("lScr.in=" + UPDATED_L_SCR);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylScrIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lScr is not null
        defaultVwWcc701ResultShouldBeFound("lScr.specified=true");

        // Get all the vwWcc701ResultList where lScr is null
        defaultVwWcc701ResultShouldNotBeFound("lScr.specified=false");
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylScrContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lScr contains DEFAULT_L_SCR
        defaultVwWcc701ResultShouldBeFound("lScr.contains=" + DEFAULT_L_SCR);

        // Get all the vwWcc701ResultList where lScr contains UPDATED_L_SCR
        defaultVwWcc701ResultShouldNotBeFound("lScr.contains=" + UPDATED_L_SCR);
    }

    @Test
    @Transactional
    void getAllVwWcc701ResultsBylScrNotContainsSomething() throws Exception {
        // Initialize the database
        vwWcc701ResultRepository.saveAndFlush(vwWcc701Result);

        // Get all the vwWcc701ResultList where lScr does not contain DEFAULT_L_SCR
        defaultVwWcc701ResultShouldNotBeFound("lScr.doesNotContain=" + DEFAULT_L_SCR);

        // Get all the vwWcc701ResultList where lScr does not contain UPDATED_L_SCR
        defaultVwWcc701ResultShouldBeFound("lScr.doesNotContain=" + UPDATED_L_SCR);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVwWcc701ResultShouldBeFound(String filter) throws Exception {
        restVwWcc701ResultMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vwWcc701Result.getId().intValue())))
            .andExpect(jsonPath("$.[*].eId").value(hasItem(DEFAULT_E_ID.intValue())))
            .andExpect(jsonPath("$.[*].evntNm").value(hasItem(DEFAULT_EVNT_NM)))
            .andExpect(jsonPath("$.[*].evntDt").value(hasItem(sameInstant(DEFAULT_EVNT_DT))))
            .andExpect(jsonPath("$.[*].venue").value(hasItem(DEFAULT_VENUE)))
            .andExpect(jsonPath("$.[*].mId").value(hasItem(DEFAULT_M_ID.intValue())))
            .andExpect(jsonPath("$.[*].mtchEndTime").value(hasItem(sameInstant(DEFAULT_MTCH_END_TIME))))
            .andExpect(jsonPath("$.[*].wPlyr1Id").value(hasItem(DEFAULT_W_PLYR_1_ID.intValue())))
            .andExpect(jsonPath("$.[*].wPlyr1Lvl").value(hasItem(DEFAULT_W_PLYR_1_LVL)))
            .andExpect(jsonPath("$.[*].wPlyr1Nm").value(hasItem(DEFAULT_W_PLYR_1_NM)))
            .andExpect(jsonPath("$.[*].wPlyr2Id").value(hasItem(DEFAULT_W_PLYR_2_ID.intValue())))
            .andExpect(jsonPath("$.[*].wPlyr2Lvl").value(hasItem(DEFAULT_W_PLYR_2_LVL)))
            .andExpect(jsonPath("$.[*].wPlyr2Nm").value(hasItem(DEFAULT_W_PLYR_2_NM)))
            .andExpect(jsonPath("$.[*].vs").value(hasItem(DEFAULT_VS)))
            .andExpect(jsonPath("$.[*].lPlyr1Id").value(hasItem(DEFAULT_L_PLYR_1_ID.intValue())))
            .andExpect(jsonPath("$.[*].lPlyr1Lvl").value(hasItem(DEFAULT_L_PLYR_1_LVL)))
            .andExpect(jsonPath("$.[*].lPlyr1Nm").value(hasItem(DEFAULT_L_PLYR_1_NM)))
            .andExpect(jsonPath("$.[*].lPlyr2Id").value(hasItem(DEFAULT_L_PLYR_2_ID.intValue())))
            .andExpect(jsonPath("$.[*].lPlyr2Lvl").value(hasItem(DEFAULT_L_PLYR_2_LVL)))
            .andExpect(jsonPath("$.[*].lPlyr2Nm").value(hasItem(DEFAULT_L_PLYR_2_NM)))
            .andExpect(jsonPath("$.[*].wScr").value(hasItem(DEFAULT_W_SCR)))
            .andExpect(jsonPath("$.[*].lScr").value(hasItem(DEFAULT_L_SCR)));

        // Check, that the count call also returns 1
        restVwWcc701ResultMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVwWcc701ResultShouldNotBeFound(String filter) throws Exception {
        restVwWcc701ResultMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVwWcc701ResultMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVwWcc701Result() throws Exception {
        // Get the vwWcc701Result
        restVwWcc701ResultMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
}
