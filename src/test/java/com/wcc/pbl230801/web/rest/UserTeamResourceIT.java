package com.wcc.pbl230801.web.rest;

import static com.wcc.pbl230801.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.wcc.pbl230801.IntegrationTest;
import com.wcc.pbl230801.domain.UserTeam;
import com.wcc.pbl230801.repository.UserTeamRepository;
import com.wcc.pbl230801.service.criteria.UserTeamCriteria;
import com.wcc.pbl230801.service.dto.UserTeamDTO;
import com.wcc.pbl230801.service.mapper.UserTeamMapper;
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
 * Integration tests for the {@link UserTeamResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserTeamResourceIT {

    private static final Long DEFAULT_U_ID = 1L;
    private static final Long UPDATED_U_ID = 2L;
    private static final Long SMALLER_U_ID = 1L - 1L;

    private static final Long DEFAULT_T_ID = 1L;
    private static final Long UPDATED_T_ID = 2L;
    private static final Long SMALLER_T_ID = 1L - 1L;

    private static final String DEFAULT_LST_MTN_USR = "AAAAAAAAAA";
    private static final String UPDATED_LST_MTN_USR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LST_MTN_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LST_MTN_DT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LST_MTN_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/user-teams";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserTeamRepository userTeamRepository;

    @Autowired
    private UserTeamMapper userTeamMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserTeamMockMvc;

    private UserTeam userTeam;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserTeam createEntity(EntityManager em) {
        UserTeam userTeam = new UserTeam().uId(DEFAULT_U_ID).tId(DEFAULT_T_ID).lstMtnUsr(DEFAULT_LST_MTN_USR).lstMtnDt(DEFAULT_LST_MTN_DT);
        return userTeam;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserTeam createUpdatedEntity(EntityManager em) {
        UserTeam userTeam = new UserTeam().uId(UPDATED_U_ID).tId(UPDATED_T_ID).lstMtnUsr(UPDATED_LST_MTN_USR).lstMtnDt(UPDATED_LST_MTN_DT);
        return userTeam;
    }

    @BeforeEach
    public void initTest() {
        userTeam = createEntity(em);
    }

    @Test
    @Transactional
    void createUserTeam() throws Exception {
        int databaseSizeBeforeCreate = userTeamRepository.findAll().size();
        // Create the UserTeam
        UserTeamDTO userTeamDTO = userTeamMapper.toDto(userTeam);
        restUserTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userTeamDTO)))
            .andExpect(status().isCreated());

        // Validate the UserTeam in the database
        List<UserTeam> userTeamList = userTeamRepository.findAll();
        assertThat(userTeamList).hasSize(databaseSizeBeforeCreate + 1);
        UserTeam testUserTeam = userTeamList.get(userTeamList.size() - 1);
        assertThat(testUserTeam.getuId()).isEqualTo(DEFAULT_U_ID);
        assertThat(testUserTeam.gettId()).isEqualTo(DEFAULT_T_ID);
        assertThat(testUserTeam.getLstMtnUsr()).isEqualTo(DEFAULT_LST_MTN_USR);
        assertThat(testUserTeam.getLstMtnDt()).isEqualTo(DEFAULT_LST_MTN_DT);
    }

    @Test
    @Transactional
    void createUserTeamWithExistingId() throws Exception {
        // Create the UserTeam with an existing ID
        userTeam.setId(1L);
        UserTeamDTO userTeamDTO = userTeamMapper.toDto(userTeam);

        int databaseSizeBeforeCreate = userTeamRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userTeamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserTeam in the database
        List<UserTeam> userTeamList = userTeamRepository.findAll();
        assertThat(userTeamList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserTeams() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList
        restUserTeamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userTeam.getId().intValue())))
            .andExpect(jsonPath("$.[*].uId").value(hasItem(DEFAULT_U_ID.intValue())))
            .andExpect(jsonPath("$.[*].tId").value(hasItem(DEFAULT_T_ID.intValue())))
            .andExpect(jsonPath("$.[*].lstMtnUsr").value(hasItem(DEFAULT_LST_MTN_USR)))
            .andExpect(jsonPath("$.[*].lstMtnDt").value(hasItem(sameInstant(DEFAULT_LST_MTN_DT))));
    }

    @Test
    @Transactional
    void getUserTeam() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get the userTeam
        restUserTeamMockMvc
            .perform(get(ENTITY_API_URL_ID, userTeam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userTeam.getId().intValue()))
            .andExpect(jsonPath("$.uId").value(DEFAULT_U_ID.intValue()))
            .andExpect(jsonPath("$.tId").value(DEFAULT_T_ID.intValue()))
            .andExpect(jsonPath("$.lstMtnUsr").value(DEFAULT_LST_MTN_USR))
            .andExpect(jsonPath("$.lstMtnDt").value(sameInstant(DEFAULT_LST_MTN_DT)));
    }

    @Test
    @Transactional
    void getUserTeamsByIdFiltering() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        Long id = userTeam.getId();

        defaultUserTeamShouldBeFound("id.equals=" + id);
        defaultUserTeamShouldNotBeFound("id.notEquals=" + id);

        defaultUserTeamShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserTeamShouldNotBeFound("id.greaterThan=" + id);

        defaultUserTeamShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserTeamShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUserTeamsByuIdIsEqualToSomething() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where uId equals to DEFAULT_U_ID
        defaultUserTeamShouldBeFound("uId.equals=" + DEFAULT_U_ID);

        // Get all the userTeamList where uId equals to UPDATED_U_ID
        defaultUserTeamShouldNotBeFound("uId.equals=" + UPDATED_U_ID);
    }

    @Test
    @Transactional
    void getAllUserTeamsByuIdIsInShouldWork() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where uId in DEFAULT_U_ID or UPDATED_U_ID
        defaultUserTeamShouldBeFound("uId.in=" + DEFAULT_U_ID + "," + UPDATED_U_ID);

        // Get all the userTeamList where uId equals to UPDATED_U_ID
        defaultUserTeamShouldNotBeFound("uId.in=" + UPDATED_U_ID);
    }

    @Test
    @Transactional
    void getAllUserTeamsByuIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where uId is not null
        defaultUserTeamShouldBeFound("uId.specified=true");

        // Get all the userTeamList where uId is null
        defaultUserTeamShouldNotBeFound("uId.specified=false");
    }

    @Test
    @Transactional
    void getAllUserTeamsByuIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where uId is greater than or equal to DEFAULT_U_ID
        defaultUserTeamShouldBeFound("uId.greaterThanOrEqual=" + DEFAULT_U_ID);

        // Get all the userTeamList where uId is greater than or equal to UPDATED_U_ID
        defaultUserTeamShouldNotBeFound("uId.greaterThanOrEqual=" + UPDATED_U_ID);
    }

    @Test
    @Transactional
    void getAllUserTeamsByuIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where uId is less than or equal to DEFAULT_U_ID
        defaultUserTeamShouldBeFound("uId.lessThanOrEqual=" + DEFAULT_U_ID);

        // Get all the userTeamList where uId is less than or equal to SMALLER_U_ID
        defaultUserTeamShouldNotBeFound("uId.lessThanOrEqual=" + SMALLER_U_ID);
    }

    @Test
    @Transactional
    void getAllUserTeamsByuIdIsLessThanSomething() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where uId is less than DEFAULT_U_ID
        defaultUserTeamShouldNotBeFound("uId.lessThan=" + DEFAULT_U_ID);

        // Get all the userTeamList where uId is less than UPDATED_U_ID
        defaultUserTeamShouldBeFound("uId.lessThan=" + UPDATED_U_ID);
    }

    @Test
    @Transactional
    void getAllUserTeamsByuIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where uId is greater than DEFAULT_U_ID
        defaultUserTeamShouldNotBeFound("uId.greaterThan=" + DEFAULT_U_ID);

        // Get all the userTeamList where uId is greater than SMALLER_U_ID
        defaultUserTeamShouldBeFound("uId.greaterThan=" + SMALLER_U_ID);
    }

    @Test
    @Transactional
    void getAllUserTeamsBytIdIsEqualToSomething() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where tId equals to DEFAULT_T_ID
        defaultUserTeamShouldBeFound("tId.equals=" + DEFAULT_T_ID);

        // Get all the userTeamList where tId equals to UPDATED_T_ID
        defaultUserTeamShouldNotBeFound("tId.equals=" + UPDATED_T_ID);
    }

    @Test
    @Transactional
    void getAllUserTeamsBytIdIsInShouldWork() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where tId in DEFAULT_T_ID or UPDATED_T_ID
        defaultUserTeamShouldBeFound("tId.in=" + DEFAULT_T_ID + "," + UPDATED_T_ID);

        // Get all the userTeamList where tId equals to UPDATED_T_ID
        defaultUserTeamShouldNotBeFound("tId.in=" + UPDATED_T_ID);
    }

    @Test
    @Transactional
    void getAllUserTeamsBytIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where tId is not null
        defaultUserTeamShouldBeFound("tId.specified=true");

        // Get all the userTeamList where tId is null
        defaultUserTeamShouldNotBeFound("tId.specified=false");
    }

    @Test
    @Transactional
    void getAllUserTeamsBytIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where tId is greater than or equal to DEFAULT_T_ID
        defaultUserTeamShouldBeFound("tId.greaterThanOrEqual=" + DEFAULT_T_ID);

        // Get all the userTeamList where tId is greater than or equal to UPDATED_T_ID
        defaultUserTeamShouldNotBeFound("tId.greaterThanOrEqual=" + UPDATED_T_ID);
    }

    @Test
    @Transactional
    void getAllUserTeamsBytIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where tId is less than or equal to DEFAULT_T_ID
        defaultUserTeamShouldBeFound("tId.lessThanOrEqual=" + DEFAULT_T_ID);

        // Get all the userTeamList where tId is less than or equal to SMALLER_T_ID
        defaultUserTeamShouldNotBeFound("tId.lessThanOrEqual=" + SMALLER_T_ID);
    }

    @Test
    @Transactional
    void getAllUserTeamsBytIdIsLessThanSomething() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where tId is less than DEFAULT_T_ID
        defaultUserTeamShouldNotBeFound("tId.lessThan=" + DEFAULT_T_ID);

        // Get all the userTeamList where tId is less than UPDATED_T_ID
        defaultUserTeamShouldBeFound("tId.lessThan=" + UPDATED_T_ID);
    }

    @Test
    @Transactional
    void getAllUserTeamsBytIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where tId is greater than DEFAULT_T_ID
        defaultUserTeamShouldNotBeFound("tId.greaterThan=" + DEFAULT_T_ID);

        // Get all the userTeamList where tId is greater than SMALLER_T_ID
        defaultUserTeamShouldBeFound("tId.greaterThan=" + SMALLER_T_ID);
    }

    @Test
    @Transactional
    void getAllUserTeamsByLstMtnUsrIsEqualToSomething() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where lstMtnUsr equals to DEFAULT_LST_MTN_USR
        defaultUserTeamShouldBeFound("lstMtnUsr.equals=" + DEFAULT_LST_MTN_USR);

        // Get all the userTeamList where lstMtnUsr equals to UPDATED_LST_MTN_USR
        defaultUserTeamShouldNotBeFound("lstMtnUsr.equals=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllUserTeamsByLstMtnUsrIsInShouldWork() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where lstMtnUsr in DEFAULT_LST_MTN_USR or UPDATED_LST_MTN_USR
        defaultUserTeamShouldBeFound("lstMtnUsr.in=" + DEFAULT_LST_MTN_USR + "," + UPDATED_LST_MTN_USR);

        // Get all the userTeamList where lstMtnUsr equals to UPDATED_LST_MTN_USR
        defaultUserTeamShouldNotBeFound("lstMtnUsr.in=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllUserTeamsByLstMtnUsrIsNullOrNotNull() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where lstMtnUsr is not null
        defaultUserTeamShouldBeFound("lstMtnUsr.specified=true");

        // Get all the userTeamList where lstMtnUsr is null
        defaultUserTeamShouldNotBeFound("lstMtnUsr.specified=false");
    }

    @Test
    @Transactional
    void getAllUserTeamsByLstMtnUsrContainsSomething() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where lstMtnUsr contains DEFAULT_LST_MTN_USR
        defaultUserTeamShouldBeFound("lstMtnUsr.contains=" + DEFAULT_LST_MTN_USR);

        // Get all the userTeamList where lstMtnUsr contains UPDATED_LST_MTN_USR
        defaultUserTeamShouldNotBeFound("lstMtnUsr.contains=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllUserTeamsByLstMtnUsrNotContainsSomething() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where lstMtnUsr does not contain DEFAULT_LST_MTN_USR
        defaultUserTeamShouldNotBeFound("lstMtnUsr.doesNotContain=" + DEFAULT_LST_MTN_USR);

        // Get all the userTeamList where lstMtnUsr does not contain UPDATED_LST_MTN_USR
        defaultUserTeamShouldBeFound("lstMtnUsr.doesNotContain=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllUserTeamsByLstMtnDtIsEqualToSomething() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where lstMtnDt equals to DEFAULT_LST_MTN_DT
        defaultUserTeamShouldBeFound("lstMtnDt.equals=" + DEFAULT_LST_MTN_DT);

        // Get all the userTeamList where lstMtnDt equals to UPDATED_LST_MTN_DT
        defaultUserTeamShouldNotBeFound("lstMtnDt.equals=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllUserTeamsByLstMtnDtIsInShouldWork() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where lstMtnDt in DEFAULT_LST_MTN_DT or UPDATED_LST_MTN_DT
        defaultUserTeamShouldBeFound("lstMtnDt.in=" + DEFAULT_LST_MTN_DT + "," + UPDATED_LST_MTN_DT);

        // Get all the userTeamList where lstMtnDt equals to UPDATED_LST_MTN_DT
        defaultUserTeamShouldNotBeFound("lstMtnDt.in=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllUserTeamsByLstMtnDtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where lstMtnDt is not null
        defaultUserTeamShouldBeFound("lstMtnDt.specified=true");

        // Get all the userTeamList where lstMtnDt is null
        defaultUserTeamShouldNotBeFound("lstMtnDt.specified=false");
    }

    @Test
    @Transactional
    void getAllUserTeamsByLstMtnDtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where lstMtnDt is greater than or equal to DEFAULT_LST_MTN_DT
        defaultUserTeamShouldBeFound("lstMtnDt.greaterThanOrEqual=" + DEFAULT_LST_MTN_DT);

        // Get all the userTeamList where lstMtnDt is greater than or equal to UPDATED_LST_MTN_DT
        defaultUserTeamShouldNotBeFound("lstMtnDt.greaterThanOrEqual=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllUserTeamsByLstMtnDtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where lstMtnDt is less than or equal to DEFAULT_LST_MTN_DT
        defaultUserTeamShouldBeFound("lstMtnDt.lessThanOrEqual=" + DEFAULT_LST_MTN_DT);

        // Get all the userTeamList where lstMtnDt is less than or equal to SMALLER_LST_MTN_DT
        defaultUserTeamShouldNotBeFound("lstMtnDt.lessThanOrEqual=" + SMALLER_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllUserTeamsByLstMtnDtIsLessThanSomething() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where lstMtnDt is less than DEFAULT_LST_MTN_DT
        defaultUserTeamShouldNotBeFound("lstMtnDt.lessThan=" + DEFAULT_LST_MTN_DT);

        // Get all the userTeamList where lstMtnDt is less than UPDATED_LST_MTN_DT
        defaultUserTeamShouldBeFound("lstMtnDt.lessThan=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllUserTeamsByLstMtnDtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        // Get all the userTeamList where lstMtnDt is greater than DEFAULT_LST_MTN_DT
        defaultUserTeamShouldNotBeFound("lstMtnDt.greaterThan=" + DEFAULT_LST_MTN_DT);

        // Get all the userTeamList where lstMtnDt is greater than SMALLER_LST_MTN_DT
        defaultUserTeamShouldBeFound("lstMtnDt.greaterThan=" + SMALLER_LST_MTN_DT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserTeamShouldBeFound(String filter) throws Exception {
        restUserTeamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userTeam.getId().intValue())))
            .andExpect(jsonPath("$.[*].uId").value(hasItem(DEFAULT_U_ID.intValue())))
            .andExpect(jsonPath("$.[*].tId").value(hasItem(DEFAULT_T_ID.intValue())))
            .andExpect(jsonPath("$.[*].lstMtnUsr").value(hasItem(DEFAULT_LST_MTN_USR)))
            .andExpect(jsonPath("$.[*].lstMtnDt").value(hasItem(sameInstant(DEFAULT_LST_MTN_DT))));

        // Check, that the count call also returns 1
        restUserTeamMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserTeamShouldNotBeFound(String filter) throws Exception {
        restUserTeamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserTeamMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUserTeam() throws Exception {
        // Get the userTeam
        restUserTeamMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserTeam() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        int databaseSizeBeforeUpdate = userTeamRepository.findAll().size();

        // Update the userTeam
        UserTeam updatedUserTeam = userTeamRepository.findById(userTeam.getId()).get();
        // Disconnect from session so that the updates on updatedUserTeam are not directly saved in db
        em.detach(updatedUserTeam);
        updatedUserTeam.uId(UPDATED_U_ID).tId(UPDATED_T_ID).lstMtnUsr(UPDATED_LST_MTN_USR).lstMtnDt(UPDATED_LST_MTN_DT);
        UserTeamDTO userTeamDTO = userTeamMapper.toDto(updatedUserTeam);

        restUserTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userTeamDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userTeamDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserTeam in the database
        List<UserTeam> userTeamList = userTeamRepository.findAll();
        assertThat(userTeamList).hasSize(databaseSizeBeforeUpdate);
        UserTeam testUserTeam = userTeamList.get(userTeamList.size() - 1);
        assertThat(testUserTeam.getuId()).isEqualTo(UPDATED_U_ID);
        assertThat(testUserTeam.gettId()).isEqualTo(UPDATED_T_ID);
        assertThat(testUserTeam.getLstMtnUsr()).isEqualTo(UPDATED_LST_MTN_USR);
        assertThat(testUserTeam.getLstMtnDt()).isEqualTo(UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void putNonExistingUserTeam() throws Exception {
        int databaseSizeBeforeUpdate = userTeamRepository.findAll().size();
        userTeam.setId(count.incrementAndGet());

        // Create the UserTeam
        UserTeamDTO userTeamDTO = userTeamMapper.toDto(userTeam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userTeamDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userTeamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserTeam in the database
        List<UserTeam> userTeamList = userTeamRepository.findAll();
        assertThat(userTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserTeam() throws Exception {
        int databaseSizeBeforeUpdate = userTeamRepository.findAll().size();
        userTeam.setId(count.incrementAndGet());

        // Create the UserTeam
        UserTeamDTO userTeamDTO = userTeamMapper.toDto(userTeam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userTeamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserTeam in the database
        List<UserTeam> userTeamList = userTeamRepository.findAll();
        assertThat(userTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserTeam() throws Exception {
        int databaseSizeBeforeUpdate = userTeamRepository.findAll().size();
        userTeam.setId(count.incrementAndGet());

        // Create the UserTeam
        UserTeamDTO userTeamDTO = userTeamMapper.toDto(userTeam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTeamMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userTeamDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserTeam in the database
        List<UserTeam> userTeamList = userTeamRepository.findAll();
        assertThat(userTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserTeamWithPatch() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        int databaseSizeBeforeUpdate = userTeamRepository.findAll().size();

        // Update the userTeam using partial update
        UserTeam partialUpdatedUserTeam = new UserTeam();
        partialUpdatedUserTeam.setId(userTeam.getId());

        partialUpdatedUserTeam.tId(UPDATED_T_ID).lstMtnUsr(UPDATED_LST_MTN_USR);

        restUserTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserTeam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserTeam))
            )
            .andExpect(status().isOk());

        // Validate the UserTeam in the database
        List<UserTeam> userTeamList = userTeamRepository.findAll();
        assertThat(userTeamList).hasSize(databaseSizeBeforeUpdate);
        UserTeam testUserTeam = userTeamList.get(userTeamList.size() - 1);
        assertThat(testUserTeam.getuId()).isEqualTo(DEFAULT_U_ID);
        assertThat(testUserTeam.gettId()).isEqualTo(UPDATED_T_ID);
        assertThat(testUserTeam.getLstMtnUsr()).isEqualTo(UPDATED_LST_MTN_USR);
        assertThat(testUserTeam.getLstMtnDt()).isEqualTo(DEFAULT_LST_MTN_DT);
    }

    @Test
    @Transactional
    void fullUpdateUserTeamWithPatch() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        int databaseSizeBeforeUpdate = userTeamRepository.findAll().size();

        // Update the userTeam using partial update
        UserTeam partialUpdatedUserTeam = new UserTeam();
        partialUpdatedUserTeam.setId(userTeam.getId());

        partialUpdatedUserTeam.uId(UPDATED_U_ID).tId(UPDATED_T_ID).lstMtnUsr(UPDATED_LST_MTN_USR).lstMtnDt(UPDATED_LST_MTN_DT);

        restUserTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserTeam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserTeam))
            )
            .andExpect(status().isOk());

        // Validate the UserTeam in the database
        List<UserTeam> userTeamList = userTeamRepository.findAll();
        assertThat(userTeamList).hasSize(databaseSizeBeforeUpdate);
        UserTeam testUserTeam = userTeamList.get(userTeamList.size() - 1);
        assertThat(testUserTeam.getuId()).isEqualTo(UPDATED_U_ID);
        assertThat(testUserTeam.gettId()).isEqualTo(UPDATED_T_ID);
        assertThat(testUserTeam.getLstMtnUsr()).isEqualTo(UPDATED_LST_MTN_USR);
        assertThat(testUserTeam.getLstMtnDt()).isEqualTo(UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void patchNonExistingUserTeam() throws Exception {
        int databaseSizeBeforeUpdate = userTeamRepository.findAll().size();
        userTeam.setId(count.incrementAndGet());

        // Create the UserTeam
        UserTeamDTO userTeamDTO = userTeamMapper.toDto(userTeam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userTeamDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userTeamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserTeam in the database
        List<UserTeam> userTeamList = userTeamRepository.findAll();
        assertThat(userTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserTeam() throws Exception {
        int databaseSizeBeforeUpdate = userTeamRepository.findAll().size();
        userTeam.setId(count.incrementAndGet());

        // Create the UserTeam
        UserTeamDTO userTeamDTO = userTeamMapper.toDto(userTeam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userTeamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserTeam in the database
        List<UserTeam> userTeamList = userTeamRepository.findAll();
        assertThat(userTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserTeam() throws Exception {
        int databaseSizeBeforeUpdate = userTeamRepository.findAll().size();
        userTeam.setId(count.incrementAndGet());

        // Create the UserTeam
        UserTeamDTO userTeamDTO = userTeamMapper.toDto(userTeam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTeamMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userTeamDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserTeam in the database
        List<UserTeam> userTeamList = userTeamRepository.findAll();
        assertThat(userTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserTeam() throws Exception {
        // Initialize the database
        userTeamRepository.saveAndFlush(userTeam);

        int databaseSizeBeforeDelete = userTeamRepository.findAll().size();

        // Delete the userTeam
        restUserTeamMockMvc
            .perform(delete(ENTITY_API_URL_ID, userTeam.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserTeam> userTeamList = userTeamRepository.findAll();
        assertThat(userTeamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
