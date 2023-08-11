package com.wcc.pbl230801.web.rest;

import static com.wcc.pbl230801.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.wcc.pbl230801.IntegrationTest;
import com.wcc.pbl230801.domain.Team;
import com.wcc.pbl230801.repository.TeamRepository;
import com.wcc.pbl230801.service.criteria.TeamCriteria;
import com.wcc.pbl230801.service.dto.TeamDTO;
import com.wcc.pbl230801.service.mapper.TeamMapper;
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
 * Integration tests for the {@link TeamResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TeamResourceIT {

    private static final String DEFAULT_TEAM_NM = "AAAAAAAAAA";
    private static final String UPDATED_TEAM_NM = "BBBBBBBBBB";

    private static final String DEFAULT_LST_MTN_USR = "AAAAAAAAAA";
    private static final String UPDATED_LST_MTN_USR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LST_MTN_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LST_MTN_DT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LST_MTN_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/teams";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTeamMockMvc;

    private Team team;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Team createEntity(EntityManager em) {
        Team team = new Team().teamNm(DEFAULT_TEAM_NM).lstMtnUsr(DEFAULT_LST_MTN_USR).lstMtnDt(DEFAULT_LST_MTN_DT);
        return team;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Team createUpdatedEntity(EntityManager em) {
        Team team = new Team().teamNm(UPDATED_TEAM_NM).lstMtnUsr(UPDATED_LST_MTN_USR).lstMtnDt(UPDATED_LST_MTN_DT);
        return team;
    }

    @BeforeEach
    public void initTest() {
        team = createEntity(em);
    }

    @Test
    @Transactional
    void createTeam() throws Exception {
        int databaseSizeBeforeCreate = teamRepository.findAll().size();
        // Create the Team
        TeamDTO teamDTO = teamMapper.toDto(team);
        restTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teamDTO)))
            .andExpect(status().isCreated());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeCreate + 1);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getTeamNm()).isEqualTo(DEFAULT_TEAM_NM);
        assertThat(testTeam.getLstMtnUsr()).isEqualTo(DEFAULT_LST_MTN_USR);
        assertThat(testTeam.getLstMtnDt()).isEqualTo(DEFAULT_LST_MTN_DT);
    }

    @Test
    @Transactional
    void createTeamWithExistingId() throws Exception {
        // Create the Team with an existing ID
        team.setId(1L);
        TeamDTO teamDTO = teamMapper.toDto(team);

        int databaseSizeBeforeCreate = teamRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTeams() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList
        restTeamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(team.getId().intValue())))
            .andExpect(jsonPath("$.[*].teamNm").value(hasItem(DEFAULT_TEAM_NM)))
            .andExpect(jsonPath("$.[*].lstMtnUsr").value(hasItem(DEFAULT_LST_MTN_USR)))
            .andExpect(jsonPath("$.[*].lstMtnDt").value(hasItem(sameInstant(DEFAULT_LST_MTN_DT))));
    }

    @Test
    @Transactional
    void getTeam() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get the team
        restTeamMockMvc
            .perform(get(ENTITY_API_URL_ID, team.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(team.getId().intValue()))
            .andExpect(jsonPath("$.teamNm").value(DEFAULT_TEAM_NM))
            .andExpect(jsonPath("$.lstMtnUsr").value(DEFAULT_LST_MTN_USR))
            .andExpect(jsonPath("$.lstMtnDt").value(sameInstant(DEFAULT_LST_MTN_DT)));
    }

    @Test
    @Transactional
    void getTeamsByIdFiltering() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        Long id = team.getId();

        defaultTeamShouldBeFound("id.equals=" + id);
        defaultTeamShouldNotBeFound("id.notEquals=" + id);

        defaultTeamShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTeamShouldNotBeFound("id.greaterThan=" + id);

        defaultTeamShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTeamShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTeamsByTeamNmIsEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where teamNm equals to DEFAULT_TEAM_NM
        defaultTeamShouldBeFound("teamNm.equals=" + DEFAULT_TEAM_NM);

        // Get all the teamList where teamNm equals to UPDATED_TEAM_NM
        defaultTeamShouldNotBeFound("teamNm.equals=" + UPDATED_TEAM_NM);
    }

    @Test
    @Transactional
    void getAllTeamsByTeamNmIsInShouldWork() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where teamNm in DEFAULT_TEAM_NM or UPDATED_TEAM_NM
        defaultTeamShouldBeFound("teamNm.in=" + DEFAULT_TEAM_NM + "," + UPDATED_TEAM_NM);

        // Get all the teamList where teamNm equals to UPDATED_TEAM_NM
        defaultTeamShouldNotBeFound("teamNm.in=" + UPDATED_TEAM_NM);
    }

    @Test
    @Transactional
    void getAllTeamsByTeamNmIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where teamNm is not null
        defaultTeamShouldBeFound("teamNm.specified=true");

        // Get all the teamList where teamNm is null
        defaultTeamShouldNotBeFound("teamNm.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamsByTeamNmContainsSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where teamNm contains DEFAULT_TEAM_NM
        defaultTeamShouldBeFound("teamNm.contains=" + DEFAULT_TEAM_NM);

        // Get all the teamList where teamNm contains UPDATED_TEAM_NM
        defaultTeamShouldNotBeFound("teamNm.contains=" + UPDATED_TEAM_NM);
    }

    @Test
    @Transactional
    void getAllTeamsByTeamNmNotContainsSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where teamNm does not contain DEFAULT_TEAM_NM
        defaultTeamShouldNotBeFound("teamNm.doesNotContain=" + DEFAULT_TEAM_NM);

        // Get all the teamList where teamNm does not contain UPDATED_TEAM_NM
        defaultTeamShouldBeFound("teamNm.doesNotContain=" + UPDATED_TEAM_NM);
    }

    @Test
    @Transactional
    void getAllTeamsByLstMtnUsrIsEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where lstMtnUsr equals to DEFAULT_LST_MTN_USR
        defaultTeamShouldBeFound("lstMtnUsr.equals=" + DEFAULT_LST_MTN_USR);

        // Get all the teamList where lstMtnUsr equals to UPDATED_LST_MTN_USR
        defaultTeamShouldNotBeFound("lstMtnUsr.equals=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllTeamsByLstMtnUsrIsInShouldWork() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where lstMtnUsr in DEFAULT_LST_MTN_USR or UPDATED_LST_MTN_USR
        defaultTeamShouldBeFound("lstMtnUsr.in=" + DEFAULT_LST_MTN_USR + "," + UPDATED_LST_MTN_USR);

        // Get all the teamList where lstMtnUsr equals to UPDATED_LST_MTN_USR
        defaultTeamShouldNotBeFound("lstMtnUsr.in=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllTeamsByLstMtnUsrIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where lstMtnUsr is not null
        defaultTeamShouldBeFound("lstMtnUsr.specified=true");

        // Get all the teamList where lstMtnUsr is null
        defaultTeamShouldNotBeFound("lstMtnUsr.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamsByLstMtnUsrContainsSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where lstMtnUsr contains DEFAULT_LST_MTN_USR
        defaultTeamShouldBeFound("lstMtnUsr.contains=" + DEFAULT_LST_MTN_USR);

        // Get all the teamList where lstMtnUsr contains UPDATED_LST_MTN_USR
        defaultTeamShouldNotBeFound("lstMtnUsr.contains=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllTeamsByLstMtnUsrNotContainsSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where lstMtnUsr does not contain DEFAULT_LST_MTN_USR
        defaultTeamShouldNotBeFound("lstMtnUsr.doesNotContain=" + DEFAULT_LST_MTN_USR);

        // Get all the teamList where lstMtnUsr does not contain UPDATED_LST_MTN_USR
        defaultTeamShouldBeFound("lstMtnUsr.doesNotContain=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllTeamsByLstMtnDtIsEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where lstMtnDt equals to DEFAULT_LST_MTN_DT
        defaultTeamShouldBeFound("lstMtnDt.equals=" + DEFAULT_LST_MTN_DT);

        // Get all the teamList where lstMtnDt equals to UPDATED_LST_MTN_DT
        defaultTeamShouldNotBeFound("lstMtnDt.equals=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllTeamsByLstMtnDtIsInShouldWork() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where lstMtnDt in DEFAULT_LST_MTN_DT or UPDATED_LST_MTN_DT
        defaultTeamShouldBeFound("lstMtnDt.in=" + DEFAULT_LST_MTN_DT + "," + UPDATED_LST_MTN_DT);

        // Get all the teamList where lstMtnDt equals to UPDATED_LST_MTN_DT
        defaultTeamShouldNotBeFound("lstMtnDt.in=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllTeamsByLstMtnDtIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where lstMtnDt is not null
        defaultTeamShouldBeFound("lstMtnDt.specified=true");

        // Get all the teamList where lstMtnDt is null
        defaultTeamShouldNotBeFound("lstMtnDt.specified=false");
    }

    @Test
    @Transactional
    void getAllTeamsByLstMtnDtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where lstMtnDt is greater than or equal to DEFAULT_LST_MTN_DT
        defaultTeamShouldBeFound("lstMtnDt.greaterThanOrEqual=" + DEFAULT_LST_MTN_DT);

        // Get all the teamList where lstMtnDt is greater than or equal to UPDATED_LST_MTN_DT
        defaultTeamShouldNotBeFound("lstMtnDt.greaterThanOrEqual=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllTeamsByLstMtnDtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where lstMtnDt is less than or equal to DEFAULT_LST_MTN_DT
        defaultTeamShouldBeFound("lstMtnDt.lessThanOrEqual=" + DEFAULT_LST_MTN_DT);

        // Get all the teamList where lstMtnDt is less than or equal to SMALLER_LST_MTN_DT
        defaultTeamShouldNotBeFound("lstMtnDt.lessThanOrEqual=" + SMALLER_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllTeamsByLstMtnDtIsLessThanSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where lstMtnDt is less than DEFAULT_LST_MTN_DT
        defaultTeamShouldNotBeFound("lstMtnDt.lessThan=" + DEFAULT_LST_MTN_DT);

        // Get all the teamList where lstMtnDt is less than UPDATED_LST_MTN_DT
        defaultTeamShouldBeFound("lstMtnDt.lessThan=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllTeamsByLstMtnDtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        // Get all the teamList where lstMtnDt is greater than DEFAULT_LST_MTN_DT
        defaultTeamShouldNotBeFound("lstMtnDt.greaterThan=" + DEFAULT_LST_MTN_DT);

        // Get all the teamList where lstMtnDt is greater than SMALLER_LST_MTN_DT
        defaultTeamShouldBeFound("lstMtnDt.greaterThan=" + SMALLER_LST_MTN_DT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTeamShouldBeFound(String filter) throws Exception {
        restTeamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(team.getId().intValue())))
            .andExpect(jsonPath("$.[*].teamNm").value(hasItem(DEFAULT_TEAM_NM)))
            .andExpect(jsonPath("$.[*].lstMtnUsr").value(hasItem(DEFAULT_LST_MTN_USR)))
            .andExpect(jsonPath("$.[*].lstMtnDt").value(hasItem(sameInstant(DEFAULT_LST_MTN_DT))));

        // Check, that the count call also returns 1
        restTeamMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTeamShouldNotBeFound(String filter) throws Exception {
        restTeamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTeamMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTeam() throws Exception {
        // Get the team
        restTeamMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTeam() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // Update the team
        Team updatedTeam = teamRepository.findById(team.getId()).get();
        // Disconnect from session so that the updates on updatedTeam are not directly saved in db
        em.detach(updatedTeam);
        updatedTeam.teamNm(UPDATED_TEAM_NM).lstMtnUsr(UPDATED_LST_MTN_USR).lstMtnDt(UPDATED_LST_MTN_DT);
        TeamDTO teamDTO = teamMapper.toDto(updatedTeam);

        restTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teamDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamDTO))
            )
            .andExpect(status().isOk());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getTeamNm()).isEqualTo(UPDATED_TEAM_NM);
        assertThat(testTeam.getLstMtnUsr()).isEqualTo(UPDATED_LST_MTN_USR);
        assertThat(testTeam.getLstMtnDt()).isEqualTo(UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void putNonExistingTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(count.incrementAndGet());

        // Create the Team
        TeamDTO teamDTO = teamMapper.toDto(team);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teamDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(count.incrementAndGet());

        // Create the Team
        TeamDTO teamDTO = teamMapper.toDto(team);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(count.incrementAndGet());

        // Create the Team
        TeamDTO teamDTO = teamMapper.toDto(team);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(teamDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTeamWithPatch() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // Update the team using partial update
        Team partialUpdatedTeam = new Team();
        partialUpdatedTeam.setId(team.getId());

        partialUpdatedTeam.lstMtnDt(UPDATED_LST_MTN_DT);

        restTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeam))
            )
            .andExpect(status().isOk());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getTeamNm()).isEqualTo(DEFAULT_TEAM_NM);
        assertThat(testTeam.getLstMtnUsr()).isEqualTo(DEFAULT_LST_MTN_USR);
        assertThat(testTeam.getLstMtnDt()).isEqualTo(UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void fullUpdateTeamWithPatch() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // Update the team using partial update
        Team partialUpdatedTeam = new Team();
        partialUpdatedTeam.setId(team.getId());

        partialUpdatedTeam.teamNm(UPDATED_TEAM_NM).lstMtnUsr(UPDATED_LST_MTN_USR).lstMtnDt(UPDATED_LST_MTN_DT);

        restTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeam))
            )
            .andExpect(status().isOk());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getTeamNm()).isEqualTo(UPDATED_TEAM_NM);
        assertThat(testTeam.getLstMtnUsr()).isEqualTo(UPDATED_LST_MTN_USR);
        assertThat(testTeam.getLstMtnDt()).isEqualTo(UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void patchNonExistingTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(count.incrementAndGet());

        // Create the Team
        TeamDTO teamDTO = teamMapper.toDto(team);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, teamDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(count.incrementAndGet());

        // Create the Team
        TeamDTO teamDTO = teamMapper.toDto(team);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();
        team.setId(count.incrementAndGet());

        // Create the Team
        TeamDTO teamDTO = teamMapper.toDto(team);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(teamDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTeam() throws Exception {
        // Initialize the database
        teamRepository.saveAndFlush(team);

        int databaseSizeBeforeDelete = teamRepository.findAll().size();

        // Delete the team
        restTeamMockMvc
            .perform(delete(ENTITY_API_URL_ID, team.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
