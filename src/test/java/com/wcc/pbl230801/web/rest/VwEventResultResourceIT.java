package com.wcc.pbl230801.web.rest;

import static com.wcc.pbl230801.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.wcc.pbl230801.IntegrationTest;
import com.wcc.pbl230801.domain.VwEventResult;
import com.wcc.pbl230801.repository.VwEventResultRepository;
import com.wcc.pbl230801.service.criteria.VwEventResultCriteria;
import com.wcc.pbl230801.service.dto.VwEventResultDTO;
import com.wcc.pbl230801.service.mapper.VwEventResultMapper;
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
 * Integration tests for the {@link VwEventResultResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VwEventResultResourceIT {

    private static final Long DEFAULT_E_ID = 1L;
    private static final Long UPDATED_E_ID = 2L;
    private static final Long SMALLER_E_ID = 1L - 1L;

    private static final Long DEFAULT_P_ID = 1L;
    private static final Long UPDATED_P_ID = 2L;
    private static final Long SMALLER_P_ID = 1L - 1L;

    private static final Long DEFAULT_M_ID = 1L;
    private static final Long UPDATED_M_ID = 2L;
    private static final Long SMALLER_M_ID = 1L - 1L;

    private static final String DEFAULT_WIN_FG = "AAAAAAAAAA";
    private static final String UPDATED_WIN_FG = "BBBBBBBBBB";

    private static final String DEFAULT_PLYR_NM = "AAAAAAAAAA";
    private static final String UPDATED_PLYR_NM = "BBBBBBBBBB";

    private static final String DEFAULT_PLYR_LVL = "AAAAAAAAAA";
    private static final String UPDATED_PLYR_LVL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_MTCH_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MTCH_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_MTCH_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_TOT_MATCHS = "AAAAAAAAAA";
    private static final String UPDATED_TOT_MATCHS = "BBBBBBBBBB";

    private static final String DEFAULT_TOT_WINS = "AAAAAAAAAA";
    private static final String UPDATED_TOT_WINS = "BBBBBBBBBB";

    private static final String DEFAULT_ACML_WINS = "AAAAAAAAAA";
    private static final String UPDATED_ACML_WINS = "BBBBBBBBBB";

    private static final String DEFAULT_CHK_FG = "AAAAAAAAAA";
    private static final String UPDATED_CHK_FG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vw-event-results";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VwEventResultRepository vwEventResultRepository;

    @Autowired
    private VwEventResultMapper vwEventResultMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVwEventResultMockMvc;

    private VwEventResult vwEventResult;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VwEventResult createEntity(EntityManager em) {
        VwEventResult vwEventResult = new VwEventResult()
            .eId(DEFAULT_E_ID)
            .pId(DEFAULT_P_ID)
            .mId(DEFAULT_M_ID)
            .winFg(DEFAULT_WIN_FG)
            .plyrNm(DEFAULT_PLYR_NM)
            .plyrLvl(DEFAULT_PLYR_LVL)
            .mtchEndTime(DEFAULT_MTCH_END_TIME)
            .totMatchs(DEFAULT_TOT_MATCHS)
            .totWins(DEFAULT_TOT_WINS)
            .acmlWins(DEFAULT_ACML_WINS)
            .chkFg(DEFAULT_CHK_FG);
        return vwEventResult;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VwEventResult createUpdatedEntity(EntityManager em) {
        VwEventResult vwEventResult = new VwEventResult()
            .eId(UPDATED_E_ID)
            .pId(UPDATED_P_ID)
            .mId(UPDATED_M_ID)
            .winFg(UPDATED_WIN_FG)
            .plyrNm(UPDATED_PLYR_NM)
            .plyrLvl(UPDATED_PLYR_LVL)
            .mtchEndTime(UPDATED_MTCH_END_TIME)
            .totMatchs(UPDATED_TOT_MATCHS)
            .totWins(UPDATED_TOT_WINS)
            .acmlWins(UPDATED_ACML_WINS)
            .chkFg(UPDATED_CHK_FG);
        return vwEventResult;
    }

    @BeforeEach
    public void initTest() {
        vwEventResult = createEntity(em);
    }

    @Test
    @Transactional
    void getAllVwEventResults() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList
        restVwEventResultMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vwEventResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].eId").value(hasItem(DEFAULT_E_ID.intValue())))
            .andExpect(jsonPath("$.[*].pId").value(hasItem(DEFAULT_P_ID.intValue())))
            .andExpect(jsonPath("$.[*].mId").value(hasItem(DEFAULT_M_ID.intValue())))
            .andExpect(jsonPath("$.[*].winFg").value(hasItem(DEFAULT_WIN_FG)))
            .andExpect(jsonPath("$.[*].plyrNm").value(hasItem(DEFAULT_PLYR_NM)))
            .andExpect(jsonPath("$.[*].plyrLvl").value(hasItem(DEFAULT_PLYR_LVL)))
            .andExpect(jsonPath("$.[*].mtchEndTime").value(hasItem(sameInstant(DEFAULT_MTCH_END_TIME))))
            .andExpect(jsonPath("$.[*].totMatchs").value(hasItem(DEFAULT_TOT_MATCHS)))
            .andExpect(jsonPath("$.[*].totWins").value(hasItem(DEFAULT_TOT_WINS)))
            .andExpect(jsonPath("$.[*].acmlWins").value(hasItem(DEFAULT_ACML_WINS)))
            .andExpect(jsonPath("$.[*].chkFg").value(hasItem(DEFAULT_CHK_FG)));
    }

    @Test
    @Transactional
    void getVwEventResult() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get the vwEventResult
        restVwEventResultMockMvc
            .perform(get(ENTITY_API_URL_ID, vwEventResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vwEventResult.getId().intValue()))
            .andExpect(jsonPath("$.eId").value(DEFAULT_E_ID.intValue()))
            .andExpect(jsonPath("$.pId").value(DEFAULT_P_ID.intValue()))
            .andExpect(jsonPath("$.mId").value(DEFAULT_M_ID.intValue()))
            .andExpect(jsonPath("$.winFg").value(DEFAULT_WIN_FG))
            .andExpect(jsonPath("$.plyrNm").value(DEFAULT_PLYR_NM))
            .andExpect(jsonPath("$.plyrLvl").value(DEFAULT_PLYR_LVL))
            .andExpect(jsonPath("$.mtchEndTime").value(sameInstant(DEFAULT_MTCH_END_TIME)))
            .andExpect(jsonPath("$.totMatchs").value(DEFAULT_TOT_MATCHS))
            .andExpect(jsonPath("$.totWins").value(DEFAULT_TOT_WINS))
            .andExpect(jsonPath("$.acmlWins").value(DEFAULT_ACML_WINS))
            .andExpect(jsonPath("$.chkFg").value(DEFAULT_CHK_FG));
    }

    @Test
    @Transactional
    void getVwEventResultsByIdFiltering() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        Long id = vwEventResult.getId();

        defaultVwEventResultShouldBeFound("id.equals=" + id);
        defaultVwEventResultShouldNotBeFound("id.notEquals=" + id);

        defaultVwEventResultShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVwEventResultShouldNotBeFound("id.greaterThan=" + id);

        defaultVwEventResultShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVwEventResultShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where eId equals to DEFAULT_E_ID
        defaultVwEventResultShouldBeFound("eId.equals=" + DEFAULT_E_ID);

        // Get all the vwEventResultList where eId equals to UPDATED_E_ID
        defaultVwEventResultShouldNotBeFound("eId.equals=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByeIdIsInShouldWork() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where eId in DEFAULT_E_ID or UPDATED_E_ID
        defaultVwEventResultShouldBeFound("eId.in=" + DEFAULT_E_ID + "," + UPDATED_E_ID);

        // Get all the vwEventResultList where eId equals to UPDATED_E_ID
        defaultVwEventResultShouldNotBeFound("eId.in=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where eId is not null
        defaultVwEventResultShouldBeFound("eId.specified=true");

        // Get all the vwEventResultList where eId is null
        defaultVwEventResultShouldNotBeFound("eId.specified=false");
    }

    @Test
    @Transactional
    void getAllVwEventResultsByeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where eId is greater than or equal to DEFAULT_E_ID
        defaultVwEventResultShouldBeFound("eId.greaterThanOrEqual=" + DEFAULT_E_ID);

        // Get all the vwEventResultList where eId is greater than or equal to UPDATED_E_ID
        defaultVwEventResultShouldNotBeFound("eId.greaterThanOrEqual=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where eId is less than or equal to DEFAULT_E_ID
        defaultVwEventResultShouldBeFound("eId.lessThanOrEqual=" + DEFAULT_E_ID);

        // Get all the vwEventResultList where eId is less than or equal to SMALLER_E_ID
        defaultVwEventResultShouldNotBeFound("eId.lessThanOrEqual=" + SMALLER_E_ID);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where eId is less than DEFAULT_E_ID
        defaultVwEventResultShouldNotBeFound("eId.lessThan=" + DEFAULT_E_ID);

        // Get all the vwEventResultList where eId is less than UPDATED_E_ID
        defaultVwEventResultShouldBeFound("eId.lessThan=" + UPDATED_E_ID);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where eId is greater than DEFAULT_E_ID
        defaultVwEventResultShouldNotBeFound("eId.greaterThan=" + DEFAULT_E_ID);

        // Get all the vwEventResultList where eId is greater than SMALLER_E_ID
        defaultVwEventResultShouldBeFound("eId.greaterThan=" + SMALLER_E_ID);
    }

    @Test
    @Transactional
    void getAllVwEventResultsBypIdIsEqualToSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where pId equals to DEFAULT_P_ID
        defaultVwEventResultShouldBeFound("pId.equals=" + DEFAULT_P_ID);

        // Get all the vwEventResultList where pId equals to UPDATED_P_ID
        defaultVwEventResultShouldNotBeFound("pId.equals=" + UPDATED_P_ID);
    }

    @Test
    @Transactional
    void getAllVwEventResultsBypIdIsInShouldWork() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where pId in DEFAULT_P_ID or UPDATED_P_ID
        defaultVwEventResultShouldBeFound("pId.in=" + DEFAULT_P_ID + "," + UPDATED_P_ID);

        // Get all the vwEventResultList where pId equals to UPDATED_P_ID
        defaultVwEventResultShouldNotBeFound("pId.in=" + UPDATED_P_ID);
    }

    @Test
    @Transactional
    void getAllVwEventResultsBypIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where pId is not null
        defaultVwEventResultShouldBeFound("pId.specified=true");

        // Get all the vwEventResultList where pId is null
        defaultVwEventResultShouldNotBeFound("pId.specified=false");
    }

    @Test
    @Transactional
    void getAllVwEventResultsBypIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where pId is greater than or equal to DEFAULT_P_ID
        defaultVwEventResultShouldBeFound("pId.greaterThanOrEqual=" + DEFAULT_P_ID);

        // Get all the vwEventResultList where pId is greater than or equal to UPDATED_P_ID
        defaultVwEventResultShouldNotBeFound("pId.greaterThanOrEqual=" + UPDATED_P_ID);
    }

    @Test
    @Transactional
    void getAllVwEventResultsBypIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where pId is less than or equal to DEFAULT_P_ID
        defaultVwEventResultShouldBeFound("pId.lessThanOrEqual=" + DEFAULT_P_ID);

        // Get all the vwEventResultList where pId is less than or equal to SMALLER_P_ID
        defaultVwEventResultShouldNotBeFound("pId.lessThanOrEqual=" + SMALLER_P_ID);
    }

    @Test
    @Transactional
    void getAllVwEventResultsBypIdIsLessThanSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where pId is less than DEFAULT_P_ID
        defaultVwEventResultShouldNotBeFound("pId.lessThan=" + DEFAULT_P_ID);

        // Get all the vwEventResultList where pId is less than UPDATED_P_ID
        defaultVwEventResultShouldBeFound("pId.lessThan=" + UPDATED_P_ID);
    }

    @Test
    @Transactional
    void getAllVwEventResultsBypIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where pId is greater than DEFAULT_P_ID
        defaultVwEventResultShouldNotBeFound("pId.greaterThan=" + DEFAULT_P_ID);

        // Get all the vwEventResultList where pId is greater than SMALLER_P_ID
        defaultVwEventResultShouldBeFound("pId.greaterThan=" + SMALLER_P_ID);
    }

    @Test
    @Transactional
    void getAllVwEventResultsBymIdIsEqualToSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where mId equals to DEFAULT_M_ID
        defaultVwEventResultShouldBeFound("mId.equals=" + DEFAULT_M_ID);

        // Get all the vwEventResultList where mId equals to UPDATED_M_ID
        defaultVwEventResultShouldNotBeFound("mId.equals=" + UPDATED_M_ID);
    }

    @Test
    @Transactional
    void getAllVwEventResultsBymIdIsInShouldWork() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where mId in DEFAULT_M_ID or UPDATED_M_ID
        defaultVwEventResultShouldBeFound("mId.in=" + DEFAULT_M_ID + "," + UPDATED_M_ID);

        // Get all the vwEventResultList where mId equals to UPDATED_M_ID
        defaultVwEventResultShouldNotBeFound("mId.in=" + UPDATED_M_ID);
    }

    @Test
    @Transactional
    void getAllVwEventResultsBymIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where mId is not null
        defaultVwEventResultShouldBeFound("mId.specified=true");

        // Get all the vwEventResultList where mId is null
        defaultVwEventResultShouldNotBeFound("mId.specified=false");
    }

    @Test
    @Transactional
    void getAllVwEventResultsBymIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where mId is greater than or equal to DEFAULT_M_ID
        defaultVwEventResultShouldBeFound("mId.greaterThanOrEqual=" + DEFAULT_M_ID);

        // Get all the vwEventResultList where mId is greater than or equal to UPDATED_M_ID
        defaultVwEventResultShouldNotBeFound("mId.greaterThanOrEqual=" + UPDATED_M_ID);
    }

    @Test
    @Transactional
    void getAllVwEventResultsBymIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where mId is less than or equal to DEFAULT_M_ID
        defaultVwEventResultShouldBeFound("mId.lessThanOrEqual=" + DEFAULT_M_ID);

        // Get all the vwEventResultList where mId is less than or equal to SMALLER_M_ID
        defaultVwEventResultShouldNotBeFound("mId.lessThanOrEqual=" + SMALLER_M_ID);
    }

    @Test
    @Transactional
    void getAllVwEventResultsBymIdIsLessThanSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where mId is less than DEFAULT_M_ID
        defaultVwEventResultShouldNotBeFound("mId.lessThan=" + DEFAULT_M_ID);

        // Get all the vwEventResultList where mId is less than UPDATED_M_ID
        defaultVwEventResultShouldBeFound("mId.lessThan=" + UPDATED_M_ID);
    }

    @Test
    @Transactional
    void getAllVwEventResultsBymIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where mId is greater than DEFAULT_M_ID
        defaultVwEventResultShouldNotBeFound("mId.greaterThan=" + DEFAULT_M_ID);

        // Get all the vwEventResultList where mId is greater than SMALLER_M_ID
        defaultVwEventResultShouldBeFound("mId.greaterThan=" + SMALLER_M_ID);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByWinFgIsEqualToSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where winFg equals to DEFAULT_WIN_FG
        defaultVwEventResultShouldBeFound("winFg.equals=" + DEFAULT_WIN_FG);

        // Get all the vwEventResultList where winFg equals to UPDATED_WIN_FG
        defaultVwEventResultShouldNotBeFound("winFg.equals=" + UPDATED_WIN_FG);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByWinFgIsInShouldWork() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where winFg in DEFAULT_WIN_FG or UPDATED_WIN_FG
        defaultVwEventResultShouldBeFound("winFg.in=" + DEFAULT_WIN_FG + "," + UPDATED_WIN_FG);

        // Get all the vwEventResultList where winFg equals to UPDATED_WIN_FG
        defaultVwEventResultShouldNotBeFound("winFg.in=" + UPDATED_WIN_FG);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByWinFgIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where winFg is not null
        defaultVwEventResultShouldBeFound("winFg.specified=true");

        // Get all the vwEventResultList where winFg is null
        defaultVwEventResultShouldNotBeFound("winFg.specified=false");
    }

    @Test
    @Transactional
    void getAllVwEventResultsByWinFgContainsSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where winFg contains DEFAULT_WIN_FG
        defaultVwEventResultShouldBeFound("winFg.contains=" + DEFAULT_WIN_FG);

        // Get all the vwEventResultList where winFg contains UPDATED_WIN_FG
        defaultVwEventResultShouldNotBeFound("winFg.contains=" + UPDATED_WIN_FG);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByWinFgNotContainsSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where winFg does not contain DEFAULT_WIN_FG
        defaultVwEventResultShouldNotBeFound("winFg.doesNotContain=" + DEFAULT_WIN_FG);

        // Get all the vwEventResultList where winFg does not contain UPDATED_WIN_FG
        defaultVwEventResultShouldBeFound("winFg.doesNotContain=" + UPDATED_WIN_FG);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByPlyrNmIsEqualToSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where plyrNm equals to DEFAULT_PLYR_NM
        defaultVwEventResultShouldBeFound("plyrNm.equals=" + DEFAULT_PLYR_NM);

        // Get all the vwEventResultList where plyrNm equals to UPDATED_PLYR_NM
        defaultVwEventResultShouldNotBeFound("plyrNm.equals=" + UPDATED_PLYR_NM);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByPlyrNmIsInShouldWork() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where plyrNm in DEFAULT_PLYR_NM or UPDATED_PLYR_NM
        defaultVwEventResultShouldBeFound("plyrNm.in=" + DEFAULT_PLYR_NM + "," + UPDATED_PLYR_NM);

        // Get all the vwEventResultList where plyrNm equals to UPDATED_PLYR_NM
        defaultVwEventResultShouldNotBeFound("plyrNm.in=" + UPDATED_PLYR_NM);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByPlyrNmIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where plyrNm is not null
        defaultVwEventResultShouldBeFound("plyrNm.specified=true");

        // Get all the vwEventResultList where plyrNm is null
        defaultVwEventResultShouldNotBeFound("plyrNm.specified=false");
    }

    @Test
    @Transactional
    void getAllVwEventResultsByPlyrNmContainsSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where plyrNm contains DEFAULT_PLYR_NM
        defaultVwEventResultShouldBeFound("plyrNm.contains=" + DEFAULT_PLYR_NM);

        // Get all the vwEventResultList where plyrNm contains UPDATED_PLYR_NM
        defaultVwEventResultShouldNotBeFound("plyrNm.contains=" + UPDATED_PLYR_NM);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByPlyrNmNotContainsSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where plyrNm does not contain DEFAULT_PLYR_NM
        defaultVwEventResultShouldNotBeFound("plyrNm.doesNotContain=" + DEFAULT_PLYR_NM);

        // Get all the vwEventResultList where plyrNm does not contain UPDATED_PLYR_NM
        defaultVwEventResultShouldBeFound("plyrNm.doesNotContain=" + UPDATED_PLYR_NM);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByPlyrLvlIsEqualToSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where plyrLvl equals to DEFAULT_PLYR_LVL
        defaultVwEventResultShouldBeFound("plyrLvl.equals=" + DEFAULT_PLYR_LVL);

        // Get all the vwEventResultList where plyrLvl equals to UPDATED_PLYR_LVL
        defaultVwEventResultShouldNotBeFound("plyrLvl.equals=" + UPDATED_PLYR_LVL);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByPlyrLvlIsInShouldWork() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where plyrLvl in DEFAULT_PLYR_LVL or UPDATED_PLYR_LVL
        defaultVwEventResultShouldBeFound("plyrLvl.in=" + DEFAULT_PLYR_LVL + "," + UPDATED_PLYR_LVL);

        // Get all the vwEventResultList where plyrLvl equals to UPDATED_PLYR_LVL
        defaultVwEventResultShouldNotBeFound("plyrLvl.in=" + UPDATED_PLYR_LVL);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByPlyrLvlIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where plyrLvl is not null
        defaultVwEventResultShouldBeFound("plyrLvl.specified=true");

        // Get all the vwEventResultList where plyrLvl is null
        defaultVwEventResultShouldNotBeFound("plyrLvl.specified=false");
    }

    @Test
    @Transactional
    void getAllVwEventResultsByPlyrLvlContainsSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where plyrLvl contains DEFAULT_PLYR_LVL
        defaultVwEventResultShouldBeFound("plyrLvl.contains=" + DEFAULT_PLYR_LVL);

        // Get all the vwEventResultList where plyrLvl contains UPDATED_PLYR_LVL
        defaultVwEventResultShouldNotBeFound("plyrLvl.contains=" + UPDATED_PLYR_LVL);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByPlyrLvlNotContainsSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where plyrLvl does not contain DEFAULT_PLYR_LVL
        defaultVwEventResultShouldNotBeFound("plyrLvl.doesNotContain=" + DEFAULT_PLYR_LVL);

        // Get all the vwEventResultList where plyrLvl does not contain UPDATED_PLYR_LVL
        defaultVwEventResultShouldBeFound("plyrLvl.doesNotContain=" + UPDATED_PLYR_LVL);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByMtchEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where mtchEndTime equals to DEFAULT_MTCH_END_TIME
        defaultVwEventResultShouldBeFound("mtchEndTime.equals=" + DEFAULT_MTCH_END_TIME);

        // Get all the vwEventResultList where mtchEndTime equals to UPDATED_MTCH_END_TIME
        defaultVwEventResultShouldNotBeFound("mtchEndTime.equals=" + UPDATED_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByMtchEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where mtchEndTime in DEFAULT_MTCH_END_TIME or UPDATED_MTCH_END_TIME
        defaultVwEventResultShouldBeFound("mtchEndTime.in=" + DEFAULT_MTCH_END_TIME + "," + UPDATED_MTCH_END_TIME);

        // Get all the vwEventResultList where mtchEndTime equals to UPDATED_MTCH_END_TIME
        defaultVwEventResultShouldNotBeFound("mtchEndTime.in=" + UPDATED_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByMtchEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where mtchEndTime is not null
        defaultVwEventResultShouldBeFound("mtchEndTime.specified=true");

        // Get all the vwEventResultList where mtchEndTime is null
        defaultVwEventResultShouldNotBeFound("mtchEndTime.specified=false");
    }

    @Test
    @Transactional
    void getAllVwEventResultsByMtchEndTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where mtchEndTime is greater than or equal to DEFAULT_MTCH_END_TIME
        defaultVwEventResultShouldBeFound("mtchEndTime.greaterThanOrEqual=" + DEFAULT_MTCH_END_TIME);

        // Get all the vwEventResultList where mtchEndTime is greater than or equal to UPDATED_MTCH_END_TIME
        defaultVwEventResultShouldNotBeFound("mtchEndTime.greaterThanOrEqual=" + UPDATED_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByMtchEndTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where mtchEndTime is less than or equal to DEFAULT_MTCH_END_TIME
        defaultVwEventResultShouldBeFound("mtchEndTime.lessThanOrEqual=" + DEFAULT_MTCH_END_TIME);

        // Get all the vwEventResultList where mtchEndTime is less than or equal to SMALLER_MTCH_END_TIME
        defaultVwEventResultShouldNotBeFound("mtchEndTime.lessThanOrEqual=" + SMALLER_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByMtchEndTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where mtchEndTime is less than DEFAULT_MTCH_END_TIME
        defaultVwEventResultShouldNotBeFound("mtchEndTime.lessThan=" + DEFAULT_MTCH_END_TIME);

        // Get all the vwEventResultList where mtchEndTime is less than UPDATED_MTCH_END_TIME
        defaultVwEventResultShouldBeFound("mtchEndTime.lessThan=" + UPDATED_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByMtchEndTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where mtchEndTime is greater than DEFAULT_MTCH_END_TIME
        defaultVwEventResultShouldNotBeFound("mtchEndTime.greaterThan=" + DEFAULT_MTCH_END_TIME);

        // Get all the vwEventResultList where mtchEndTime is greater than SMALLER_MTCH_END_TIME
        defaultVwEventResultShouldBeFound("mtchEndTime.greaterThan=" + SMALLER_MTCH_END_TIME);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByTotMatchsIsEqualToSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where totMatchs equals to DEFAULT_TOT_MATCHS
        defaultVwEventResultShouldBeFound("totMatchs.equals=" + DEFAULT_TOT_MATCHS);

        // Get all the vwEventResultList where totMatchs equals to UPDATED_TOT_MATCHS
        defaultVwEventResultShouldNotBeFound("totMatchs.equals=" + UPDATED_TOT_MATCHS);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByTotMatchsIsInShouldWork() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where totMatchs in DEFAULT_TOT_MATCHS or UPDATED_TOT_MATCHS
        defaultVwEventResultShouldBeFound("totMatchs.in=" + DEFAULT_TOT_MATCHS + "," + UPDATED_TOT_MATCHS);

        // Get all the vwEventResultList where totMatchs equals to UPDATED_TOT_MATCHS
        defaultVwEventResultShouldNotBeFound("totMatchs.in=" + UPDATED_TOT_MATCHS);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByTotMatchsIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where totMatchs is not null
        defaultVwEventResultShouldBeFound("totMatchs.specified=true");

        // Get all the vwEventResultList where totMatchs is null
        defaultVwEventResultShouldNotBeFound("totMatchs.specified=false");
    }

    @Test
    @Transactional
    void getAllVwEventResultsByTotMatchsContainsSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where totMatchs contains DEFAULT_TOT_MATCHS
        defaultVwEventResultShouldBeFound("totMatchs.contains=" + DEFAULT_TOT_MATCHS);

        // Get all the vwEventResultList where totMatchs contains UPDATED_TOT_MATCHS
        defaultVwEventResultShouldNotBeFound("totMatchs.contains=" + UPDATED_TOT_MATCHS);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByTotMatchsNotContainsSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where totMatchs does not contain DEFAULT_TOT_MATCHS
        defaultVwEventResultShouldNotBeFound("totMatchs.doesNotContain=" + DEFAULT_TOT_MATCHS);

        // Get all the vwEventResultList where totMatchs does not contain UPDATED_TOT_MATCHS
        defaultVwEventResultShouldBeFound("totMatchs.doesNotContain=" + UPDATED_TOT_MATCHS);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByTotWinsIsEqualToSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where totWins equals to DEFAULT_TOT_WINS
        defaultVwEventResultShouldBeFound("totWins.equals=" + DEFAULT_TOT_WINS);

        // Get all the vwEventResultList where totWins equals to UPDATED_TOT_WINS
        defaultVwEventResultShouldNotBeFound("totWins.equals=" + UPDATED_TOT_WINS);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByTotWinsIsInShouldWork() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where totWins in DEFAULT_TOT_WINS or UPDATED_TOT_WINS
        defaultVwEventResultShouldBeFound("totWins.in=" + DEFAULT_TOT_WINS + "," + UPDATED_TOT_WINS);

        // Get all the vwEventResultList where totWins equals to UPDATED_TOT_WINS
        defaultVwEventResultShouldNotBeFound("totWins.in=" + UPDATED_TOT_WINS);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByTotWinsIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where totWins is not null
        defaultVwEventResultShouldBeFound("totWins.specified=true");

        // Get all the vwEventResultList where totWins is null
        defaultVwEventResultShouldNotBeFound("totWins.specified=false");
    }

    @Test
    @Transactional
    void getAllVwEventResultsByTotWinsContainsSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where totWins contains DEFAULT_TOT_WINS
        defaultVwEventResultShouldBeFound("totWins.contains=" + DEFAULT_TOT_WINS);

        // Get all the vwEventResultList where totWins contains UPDATED_TOT_WINS
        defaultVwEventResultShouldNotBeFound("totWins.contains=" + UPDATED_TOT_WINS);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByTotWinsNotContainsSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where totWins does not contain DEFAULT_TOT_WINS
        defaultVwEventResultShouldNotBeFound("totWins.doesNotContain=" + DEFAULT_TOT_WINS);

        // Get all the vwEventResultList where totWins does not contain UPDATED_TOT_WINS
        defaultVwEventResultShouldBeFound("totWins.doesNotContain=" + UPDATED_TOT_WINS);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByAcmlWinsIsEqualToSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where acmlWins equals to DEFAULT_ACML_WINS
        defaultVwEventResultShouldBeFound("acmlWins.equals=" + DEFAULT_ACML_WINS);

        // Get all the vwEventResultList where acmlWins equals to UPDATED_ACML_WINS
        defaultVwEventResultShouldNotBeFound("acmlWins.equals=" + UPDATED_ACML_WINS);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByAcmlWinsIsInShouldWork() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where acmlWins in DEFAULT_ACML_WINS or UPDATED_ACML_WINS
        defaultVwEventResultShouldBeFound("acmlWins.in=" + DEFAULT_ACML_WINS + "," + UPDATED_ACML_WINS);

        // Get all the vwEventResultList where acmlWins equals to UPDATED_ACML_WINS
        defaultVwEventResultShouldNotBeFound("acmlWins.in=" + UPDATED_ACML_WINS);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByAcmlWinsIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where acmlWins is not null
        defaultVwEventResultShouldBeFound("acmlWins.specified=true");

        // Get all the vwEventResultList where acmlWins is null
        defaultVwEventResultShouldNotBeFound("acmlWins.specified=false");
    }

    @Test
    @Transactional
    void getAllVwEventResultsByAcmlWinsContainsSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where acmlWins contains DEFAULT_ACML_WINS
        defaultVwEventResultShouldBeFound("acmlWins.contains=" + DEFAULT_ACML_WINS);

        // Get all the vwEventResultList where acmlWins contains UPDATED_ACML_WINS
        defaultVwEventResultShouldNotBeFound("acmlWins.contains=" + UPDATED_ACML_WINS);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByAcmlWinsNotContainsSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where acmlWins does not contain DEFAULT_ACML_WINS
        defaultVwEventResultShouldNotBeFound("acmlWins.doesNotContain=" + DEFAULT_ACML_WINS);

        // Get all the vwEventResultList where acmlWins does not contain UPDATED_ACML_WINS
        defaultVwEventResultShouldBeFound("acmlWins.doesNotContain=" + UPDATED_ACML_WINS);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByChkFgIsEqualToSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where chkFg equals to DEFAULT_CHK_FG
        defaultVwEventResultShouldBeFound("chkFg.equals=" + DEFAULT_CHK_FG);

        // Get all the vwEventResultList where chkFg equals to UPDATED_CHK_FG
        defaultVwEventResultShouldNotBeFound("chkFg.equals=" + UPDATED_CHK_FG);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByChkFgIsInShouldWork() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where chkFg in DEFAULT_CHK_FG or UPDATED_CHK_FG
        defaultVwEventResultShouldBeFound("chkFg.in=" + DEFAULT_CHK_FG + "," + UPDATED_CHK_FG);

        // Get all the vwEventResultList where chkFg equals to UPDATED_CHK_FG
        defaultVwEventResultShouldNotBeFound("chkFg.in=" + UPDATED_CHK_FG);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByChkFgIsNullOrNotNull() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where chkFg is not null
        defaultVwEventResultShouldBeFound("chkFg.specified=true");

        // Get all the vwEventResultList where chkFg is null
        defaultVwEventResultShouldNotBeFound("chkFg.specified=false");
    }

    @Test
    @Transactional
    void getAllVwEventResultsByChkFgContainsSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where chkFg contains DEFAULT_CHK_FG
        defaultVwEventResultShouldBeFound("chkFg.contains=" + DEFAULT_CHK_FG);

        // Get all the vwEventResultList where chkFg contains UPDATED_CHK_FG
        defaultVwEventResultShouldNotBeFound("chkFg.contains=" + UPDATED_CHK_FG);
    }

    @Test
    @Transactional
    void getAllVwEventResultsByChkFgNotContainsSomething() throws Exception {
        // Initialize the database
        vwEventResultRepository.saveAndFlush(vwEventResult);

        // Get all the vwEventResultList where chkFg does not contain DEFAULT_CHK_FG
        defaultVwEventResultShouldNotBeFound("chkFg.doesNotContain=" + DEFAULT_CHK_FG);

        // Get all the vwEventResultList where chkFg does not contain UPDATED_CHK_FG
        defaultVwEventResultShouldBeFound("chkFg.doesNotContain=" + UPDATED_CHK_FG);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVwEventResultShouldBeFound(String filter) throws Exception {
        restVwEventResultMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vwEventResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].eId").value(hasItem(DEFAULT_E_ID.intValue())))
            .andExpect(jsonPath("$.[*].pId").value(hasItem(DEFAULT_P_ID.intValue())))
            .andExpect(jsonPath("$.[*].mId").value(hasItem(DEFAULT_M_ID.intValue())))
            .andExpect(jsonPath("$.[*].winFg").value(hasItem(DEFAULT_WIN_FG)))
            .andExpect(jsonPath("$.[*].plyrNm").value(hasItem(DEFAULT_PLYR_NM)))
            .andExpect(jsonPath("$.[*].plyrLvl").value(hasItem(DEFAULT_PLYR_LVL)))
            .andExpect(jsonPath("$.[*].mtchEndTime").value(hasItem(sameInstant(DEFAULT_MTCH_END_TIME))))
            .andExpect(jsonPath("$.[*].totMatchs").value(hasItem(DEFAULT_TOT_MATCHS)))
            .andExpect(jsonPath("$.[*].totWins").value(hasItem(DEFAULT_TOT_WINS)))
            .andExpect(jsonPath("$.[*].acmlWins").value(hasItem(DEFAULT_ACML_WINS)))
            .andExpect(jsonPath("$.[*].chkFg").value(hasItem(DEFAULT_CHK_FG)));

        // Check, that the count call also returns 1
        restVwEventResultMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVwEventResultShouldNotBeFound(String filter) throws Exception {
        restVwEventResultMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVwEventResultMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVwEventResult() throws Exception {
        // Get the vwEventResult
        restVwEventResultMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
}
