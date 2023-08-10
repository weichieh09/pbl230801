package com.wcc.pbl230801.web.rest;

import static com.wcc.pbl230801.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.wcc.pbl230801.IntegrationTest;
import com.wcc.pbl230801.domain.Player;
import com.wcc.pbl230801.repository.PlayerRepository;
import com.wcc.pbl230801.service.criteria.PlayerCriteria;
import com.wcc.pbl230801.service.dto.PlayerDTO;
import com.wcc.pbl230801.service.mapper.PlayerMapper;
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
 * Integration tests for the {@link PlayerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlayerResourceIT {

    private static final String DEFAULT_PLYR_NM = "AAAAAAAAAA";
    private static final String UPDATED_PLYR_NM = "BBBBBBBBBB";

    private static final String DEFAULT_PLYR_LVL = "AAAAAAAAAA";
    private static final String UPDATED_PLYR_LVL = "BBBBBBBBBB";

    private static final String DEFAULT_LST_MTN_USR = "AAAAAAAAAA";
    private static final String UPDATED_LST_MTN_USR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LST_MTN_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LST_MTN_DT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LST_MTN_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/players";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerMapper playerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlayerMockMvc;

    private Player player;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Player createEntity(EntityManager em) {
        Player player = new Player()
            .plyrNm(DEFAULT_PLYR_NM)
            .plyrLvl(DEFAULT_PLYR_LVL)
            .lstMtnUsr(DEFAULT_LST_MTN_USR)
            .lstMtnDt(DEFAULT_LST_MTN_DT);
        return player;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Player createUpdatedEntity(EntityManager em) {
        Player player = new Player()
            .plyrNm(UPDATED_PLYR_NM)
            .plyrLvl(UPDATED_PLYR_LVL)
            .lstMtnUsr(UPDATED_LST_MTN_USR)
            .lstMtnDt(UPDATED_LST_MTN_DT);
        return player;
    }

    @BeforeEach
    public void initTest() {
        player = createEntity(em);
    }

    @Test
    @Transactional
    void createPlayer() throws Exception {
        int databaseSizeBeforeCreate = playerRepository.findAll().size();
        // Create the Player
        PlayerDTO playerDTO = playerMapper.toDto(player);
        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(playerDTO)))
            .andExpect(status().isCreated());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeCreate + 1);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getPlyrNm()).isEqualTo(DEFAULT_PLYR_NM);
        assertThat(testPlayer.getPlyrLvl()).isEqualTo(DEFAULT_PLYR_LVL);
        assertThat(testPlayer.getLstMtnUsr()).isEqualTo(DEFAULT_LST_MTN_USR);
        assertThat(testPlayer.getLstMtnDt()).isEqualTo(DEFAULT_LST_MTN_DT);
    }

    @Test
    @Transactional
    void createPlayerWithExistingId() throws Exception {
        // Create the Player with an existing ID
        player.setId(1L);
        PlayerDTO playerDTO = playerMapper.toDto(player);

        int databaseSizeBeforeCreate = playerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(playerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlayers() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList
        restPlayerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(player.getId().intValue())))
            .andExpect(jsonPath("$.[*].plyrNm").value(hasItem(DEFAULT_PLYR_NM)))
            .andExpect(jsonPath("$.[*].plyrLvl").value(hasItem(DEFAULT_PLYR_LVL)))
            .andExpect(jsonPath("$.[*].lstMtnUsr").value(hasItem(DEFAULT_LST_MTN_USR)))
            .andExpect(jsonPath("$.[*].lstMtnDt").value(hasItem(sameInstant(DEFAULT_LST_MTN_DT))));
    }

    @Test
    @Transactional
    void getPlayer() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get the player
        restPlayerMockMvc
            .perform(get(ENTITY_API_URL_ID, player.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(player.getId().intValue()))
            .andExpect(jsonPath("$.plyrNm").value(DEFAULT_PLYR_NM))
            .andExpect(jsonPath("$.plyrLvl").value(DEFAULT_PLYR_LVL))
            .andExpect(jsonPath("$.lstMtnUsr").value(DEFAULT_LST_MTN_USR))
            .andExpect(jsonPath("$.lstMtnDt").value(sameInstant(DEFAULT_LST_MTN_DT)));
    }

    @Test
    @Transactional
    void getPlayersByIdFiltering() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        Long id = player.getId();

        defaultPlayerShouldBeFound("id.equals=" + id);
        defaultPlayerShouldNotBeFound("id.notEquals=" + id);

        defaultPlayerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPlayerShouldNotBeFound("id.greaterThan=" + id);

        defaultPlayerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPlayerShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPlayersByPlyrNmIsEqualToSomething() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList where plyrNm equals to DEFAULT_PLYR_NM
        defaultPlayerShouldBeFound("plyrNm.equals=" + DEFAULT_PLYR_NM);

        // Get all the playerList where plyrNm equals to UPDATED_PLYR_NM
        defaultPlayerShouldNotBeFound("plyrNm.equals=" + UPDATED_PLYR_NM);
    }

    @Test
    @Transactional
    void getAllPlayersByPlyrNmIsInShouldWork() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList where plyrNm in DEFAULT_PLYR_NM or UPDATED_PLYR_NM
        defaultPlayerShouldBeFound("plyrNm.in=" + DEFAULT_PLYR_NM + "," + UPDATED_PLYR_NM);

        // Get all the playerList where plyrNm equals to UPDATED_PLYR_NM
        defaultPlayerShouldNotBeFound("plyrNm.in=" + UPDATED_PLYR_NM);
    }

    @Test
    @Transactional
    void getAllPlayersByPlyrNmIsNullOrNotNull() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList where plyrNm is not null
        defaultPlayerShouldBeFound("plyrNm.specified=true");

        // Get all the playerList where plyrNm is null
        defaultPlayerShouldNotBeFound("plyrNm.specified=false");
    }

    @Test
    @Transactional
    void getAllPlayersByPlyrNmContainsSomething() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList where plyrNm contains DEFAULT_PLYR_NM
        defaultPlayerShouldBeFound("plyrNm.contains=" + DEFAULT_PLYR_NM);

        // Get all the playerList where plyrNm contains UPDATED_PLYR_NM
        defaultPlayerShouldNotBeFound("plyrNm.contains=" + UPDATED_PLYR_NM);
    }

    @Test
    @Transactional
    void getAllPlayersByPlyrNmNotContainsSomething() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList where plyrNm does not contain DEFAULT_PLYR_NM
        defaultPlayerShouldNotBeFound("plyrNm.doesNotContain=" + DEFAULT_PLYR_NM);

        // Get all the playerList where plyrNm does not contain UPDATED_PLYR_NM
        defaultPlayerShouldBeFound("plyrNm.doesNotContain=" + UPDATED_PLYR_NM);
    }

    @Test
    @Transactional
    void getAllPlayersByPlyrLvlIsEqualToSomething() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList where plyrLvl equals to DEFAULT_PLYR_LVL
        defaultPlayerShouldBeFound("plyrLvl.equals=" + DEFAULT_PLYR_LVL);

        // Get all the playerList where plyrLvl equals to UPDATED_PLYR_LVL
        defaultPlayerShouldNotBeFound("plyrLvl.equals=" + UPDATED_PLYR_LVL);
    }

    @Test
    @Transactional
    void getAllPlayersByPlyrLvlIsInShouldWork() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList where plyrLvl in DEFAULT_PLYR_LVL or UPDATED_PLYR_LVL
        defaultPlayerShouldBeFound("plyrLvl.in=" + DEFAULT_PLYR_LVL + "," + UPDATED_PLYR_LVL);

        // Get all the playerList where plyrLvl equals to UPDATED_PLYR_LVL
        defaultPlayerShouldNotBeFound("plyrLvl.in=" + UPDATED_PLYR_LVL);
    }

    @Test
    @Transactional
    void getAllPlayersByPlyrLvlIsNullOrNotNull() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList where plyrLvl is not null
        defaultPlayerShouldBeFound("plyrLvl.specified=true");

        // Get all the playerList where plyrLvl is null
        defaultPlayerShouldNotBeFound("plyrLvl.specified=false");
    }

    @Test
    @Transactional
    void getAllPlayersByPlyrLvlContainsSomething() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList where plyrLvl contains DEFAULT_PLYR_LVL
        defaultPlayerShouldBeFound("plyrLvl.contains=" + DEFAULT_PLYR_LVL);

        // Get all the playerList where plyrLvl contains UPDATED_PLYR_LVL
        defaultPlayerShouldNotBeFound("plyrLvl.contains=" + UPDATED_PLYR_LVL);
    }

    @Test
    @Transactional
    void getAllPlayersByPlyrLvlNotContainsSomething() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList where plyrLvl does not contain DEFAULT_PLYR_LVL
        defaultPlayerShouldNotBeFound("plyrLvl.doesNotContain=" + DEFAULT_PLYR_LVL);

        // Get all the playerList where plyrLvl does not contain UPDATED_PLYR_LVL
        defaultPlayerShouldBeFound("plyrLvl.doesNotContain=" + UPDATED_PLYR_LVL);
    }

    @Test
    @Transactional
    void getAllPlayersByLstMtnUsrIsEqualToSomething() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList where lstMtnUsr equals to DEFAULT_LST_MTN_USR
        defaultPlayerShouldBeFound("lstMtnUsr.equals=" + DEFAULT_LST_MTN_USR);

        // Get all the playerList where lstMtnUsr equals to UPDATED_LST_MTN_USR
        defaultPlayerShouldNotBeFound("lstMtnUsr.equals=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllPlayersByLstMtnUsrIsInShouldWork() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList where lstMtnUsr in DEFAULT_LST_MTN_USR or UPDATED_LST_MTN_USR
        defaultPlayerShouldBeFound("lstMtnUsr.in=" + DEFAULT_LST_MTN_USR + "," + UPDATED_LST_MTN_USR);

        // Get all the playerList where lstMtnUsr equals to UPDATED_LST_MTN_USR
        defaultPlayerShouldNotBeFound("lstMtnUsr.in=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllPlayersByLstMtnUsrIsNullOrNotNull() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList where lstMtnUsr is not null
        defaultPlayerShouldBeFound("lstMtnUsr.specified=true");

        // Get all the playerList where lstMtnUsr is null
        defaultPlayerShouldNotBeFound("lstMtnUsr.specified=false");
    }

    @Test
    @Transactional
    void getAllPlayersByLstMtnUsrContainsSomething() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList where lstMtnUsr contains DEFAULT_LST_MTN_USR
        defaultPlayerShouldBeFound("lstMtnUsr.contains=" + DEFAULT_LST_MTN_USR);

        // Get all the playerList where lstMtnUsr contains UPDATED_LST_MTN_USR
        defaultPlayerShouldNotBeFound("lstMtnUsr.contains=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllPlayersByLstMtnUsrNotContainsSomething() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList where lstMtnUsr does not contain DEFAULT_LST_MTN_USR
        defaultPlayerShouldNotBeFound("lstMtnUsr.doesNotContain=" + DEFAULT_LST_MTN_USR);

        // Get all the playerList where lstMtnUsr does not contain UPDATED_LST_MTN_USR
        defaultPlayerShouldBeFound("lstMtnUsr.doesNotContain=" + UPDATED_LST_MTN_USR);
    }

    @Test
    @Transactional
    void getAllPlayersByLstMtnDtIsEqualToSomething() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList where lstMtnDt equals to DEFAULT_LST_MTN_DT
        defaultPlayerShouldBeFound("lstMtnDt.equals=" + DEFAULT_LST_MTN_DT);

        // Get all the playerList where lstMtnDt equals to UPDATED_LST_MTN_DT
        defaultPlayerShouldNotBeFound("lstMtnDt.equals=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllPlayersByLstMtnDtIsInShouldWork() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList where lstMtnDt in DEFAULT_LST_MTN_DT or UPDATED_LST_MTN_DT
        defaultPlayerShouldBeFound("lstMtnDt.in=" + DEFAULT_LST_MTN_DT + "," + UPDATED_LST_MTN_DT);

        // Get all the playerList where lstMtnDt equals to UPDATED_LST_MTN_DT
        defaultPlayerShouldNotBeFound("lstMtnDt.in=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllPlayersByLstMtnDtIsNullOrNotNull() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList where lstMtnDt is not null
        defaultPlayerShouldBeFound("lstMtnDt.specified=true");

        // Get all the playerList where lstMtnDt is null
        defaultPlayerShouldNotBeFound("lstMtnDt.specified=false");
    }

    @Test
    @Transactional
    void getAllPlayersByLstMtnDtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList where lstMtnDt is greater than or equal to DEFAULT_LST_MTN_DT
        defaultPlayerShouldBeFound("lstMtnDt.greaterThanOrEqual=" + DEFAULT_LST_MTN_DT);

        // Get all the playerList where lstMtnDt is greater than or equal to UPDATED_LST_MTN_DT
        defaultPlayerShouldNotBeFound("lstMtnDt.greaterThanOrEqual=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllPlayersByLstMtnDtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList where lstMtnDt is less than or equal to DEFAULT_LST_MTN_DT
        defaultPlayerShouldBeFound("lstMtnDt.lessThanOrEqual=" + DEFAULT_LST_MTN_DT);

        // Get all the playerList where lstMtnDt is less than or equal to SMALLER_LST_MTN_DT
        defaultPlayerShouldNotBeFound("lstMtnDt.lessThanOrEqual=" + SMALLER_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllPlayersByLstMtnDtIsLessThanSomething() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList where lstMtnDt is less than DEFAULT_LST_MTN_DT
        defaultPlayerShouldNotBeFound("lstMtnDt.lessThan=" + DEFAULT_LST_MTN_DT);

        // Get all the playerList where lstMtnDt is less than UPDATED_LST_MTN_DT
        defaultPlayerShouldBeFound("lstMtnDt.lessThan=" + UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void getAllPlayersByLstMtnDtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList where lstMtnDt is greater than DEFAULT_LST_MTN_DT
        defaultPlayerShouldNotBeFound("lstMtnDt.greaterThan=" + DEFAULT_LST_MTN_DT);

        // Get all the playerList where lstMtnDt is greater than SMALLER_LST_MTN_DT
        defaultPlayerShouldBeFound("lstMtnDt.greaterThan=" + SMALLER_LST_MTN_DT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPlayerShouldBeFound(String filter) throws Exception {
        restPlayerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(player.getId().intValue())))
            .andExpect(jsonPath("$.[*].plyrNm").value(hasItem(DEFAULT_PLYR_NM)))
            .andExpect(jsonPath("$.[*].plyrLvl").value(hasItem(DEFAULT_PLYR_LVL)))
            .andExpect(jsonPath("$.[*].lstMtnUsr").value(hasItem(DEFAULT_LST_MTN_USR)))
            .andExpect(jsonPath("$.[*].lstMtnDt").value(hasItem(sameInstant(DEFAULT_LST_MTN_DT))));

        // Check, that the count call also returns 1
        restPlayerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPlayerShouldNotBeFound(String filter) throws Exception {
        restPlayerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlayerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPlayer() throws Exception {
        // Get the player
        restPlayerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlayer() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        int databaseSizeBeforeUpdate = playerRepository.findAll().size();

        // Update the player
        Player updatedPlayer = playerRepository.findById(player.getId()).get();
        // Disconnect from session so that the updates on updatedPlayer are not directly saved in db
        em.detach(updatedPlayer);
        updatedPlayer.plyrNm(UPDATED_PLYR_NM).plyrLvl(UPDATED_PLYR_LVL).lstMtnUsr(UPDATED_LST_MTN_USR).lstMtnDt(UPDATED_LST_MTN_DT);
        PlayerDTO playerDTO = playerMapper.toDto(updatedPlayer);

        restPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, playerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(playerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getPlyrNm()).isEqualTo(UPDATED_PLYR_NM);
        assertThat(testPlayer.getPlyrLvl()).isEqualTo(UPDATED_PLYR_LVL);
        assertThat(testPlayer.getLstMtnUsr()).isEqualTo(UPDATED_LST_MTN_USR);
        assertThat(testPlayer.getLstMtnDt()).isEqualTo(UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void putNonExistingPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(count.incrementAndGet());

        // Create the Player
        PlayerDTO playerDTO = playerMapper.toDto(player);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, playerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(playerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(count.incrementAndGet());

        // Create the Player
        PlayerDTO playerDTO = playerMapper.toDto(player);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(playerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(count.incrementAndGet());

        // Create the Player
        PlayerDTO playerDTO = playerMapper.toDto(player);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(playerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlayerWithPatch() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        int databaseSizeBeforeUpdate = playerRepository.findAll().size();

        // Update the player using partial update
        Player partialUpdatedPlayer = new Player();
        partialUpdatedPlayer.setId(player.getId());

        partialUpdatedPlayer.plyrNm(UPDATED_PLYR_NM).plyrLvl(UPDATED_PLYR_LVL).lstMtnUsr(UPDATED_LST_MTN_USR);

        restPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlayer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlayer))
            )
            .andExpect(status().isOk());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getPlyrNm()).isEqualTo(UPDATED_PLYR_NM);
        assertThat(testPlayer.getPlyrLvl()).isEqualTo(UPDATED_PLYR_LVL);
        assertThat(testPlayer.getLstMtnUsr()).isEqualTo(UPDATED_LST_MTN_USR);
        assertThat(testPlayer.getLstMtnDt()).isEqualTo(DEFAULT_LST_MTN_DT);
    }

    @Test
    @Transactional
    void fullUpdatePlayerWithPatch() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        int databaseSizeBeforeUpdate = playerRepository.findAll().size();

        // Update the player using partial update
        Player partialUpdatedPlayer = new Player();
        partialUpdatedPlayer.setId(player.getId());

        partialUpdatedPlayer.plyrNm(UPDATED_PLYR_NM).plyrLvl(UPDATED_PLYR_LVL).lstMtnUsr(UPDATED_LST_MTN_USR).lstMtnDt(UPDATED_LST_MTN_DT);

        restPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlayer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlayer))
            )
            .andExpect(status().isOk());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getPlyrNm()).isEqualTo(UPDATED_PLYR_NM);
        assertThat(testPlayer.getPlyrLvl()).isEqualTo(UPDATED_PLYR_LVL);
        assertThat(testPlayer.getLstMtnUsr()).isEqualTo(UPDATED_LST_MTN_USR);
        assertThat(testPlayer.getLstMtnDt()).isEqualTo(UPDATED_LST_MTN_DT);
    }

    @Test
    @Transactional
    void patchNonExistingPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(count.incrementAndGet());

        // Create the Player
        PlayerDTO playerDTO = playerMapper.toDto(player);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, playerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(playerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(count.incrementAndGet());

        // Create the Player
        PlayerDTO playerDTO = playerMapper.toDto(player);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(playerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(count.incrementAndGet());

        // Create the Player
        PlayerDTO playerDTO = playerMapper.toDto(player);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(playerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlayer() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        int databaseSizeBeforeDelete = playerRepository.findAll().size();

        // Delete the player
        restPlayerMockMvc
            .perform(delete(ENTITY_API_URL_ID, player.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
