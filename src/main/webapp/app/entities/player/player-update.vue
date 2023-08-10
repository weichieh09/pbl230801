<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="pbl230801App.player.home.createOrEditLabel" data-cy="PlayerCreateUpdateHeading">Create or edit a Player</h2>
        <div>
          <div class="form-group" v-if="player.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="player.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="player-plyrNm">Plyr Nm</label>
            <input
              type="text"
              class="form-control"
              name="plyrNm"
              id="player-plyrNm"
              data-cy="plyrNm"
              :class="{ valid: !$v.player.plyrNm.$invalid, invalid: $v.player.plyrNm.$invalid }"
              v-model="$v.player.plyrNm.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="player-plyrLvl">Plyr Lvl</label>
            <input
              type="text"
              class="form-control"
              name="plyrLvl"
              id="player-plyrLvl"
              data-cy="plyrLvl"
              :class="{ valid: !$v.player.plyrLvl.$invalid, invalid: $v.player.plyrLvl.$invalid }"
              v-model="$v.player.plyrLvl.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="player-lstMtnUsr">Lst Mtn Usr</label>
            <input
              type="text"
              class="form-control"
              name="lstMtnUsr"
              id="player-lstMtnUsr"
              data-cy="lstMtnUsr"
              :class="{ valid: !$v.player.lstMtnUsr.$invalid, invalid: $v.player.lstMtnUsr.$invalid }"
              v-model="$v.player.lstMtnUsr.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="player-lstMtnDt">Lst Mtn Dt</label>
            <div class="d-flex">
              <input
                id="player-lstMtnDt"
                data-cy="lstMtnDt"
                type="datetime-local"
                class="form-control"
                name="lstMtnDt"
                :class="{ valid: !$v.player.lstMtnDt.$invalid, invalid: $v.player.lstMtnDt.$invalid }"
                :value="convertDateTimeFromServer($v.player.lstMtnDt.$model)"
                @change="updateZonedDateTimeField('lstMtnDt', $event)"
              />
            </div>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.player.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./player-update.component.ts"></script>
