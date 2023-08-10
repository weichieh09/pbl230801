<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="pbl230801App.matchPlayer.home.createOrEditLabel" data-cy="MatchPlayerCreateUpdateHeading">Create or edit a MatchPlayer</h2>
        <div>
          <div class="form-group" v-if="matchPlayer.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="matchPlayer.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="match-player-mId">M Id</label>
            <input
              type="number"
              class="form-control"
              name="mId"
              id="match-player-mId"
              data-cy="mId"
              :class="{ valid: !$v.matchPlayer.mId.$invalid, invalid: $v.matchPlayer.mId.$invalid }"
              v-model.number="$v.matchPlayer.mId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="match-player-pId">P Id</label>
            <input
              type="number"
              class="form-control"
              name="pId"
              id="match-player-pId"
              data-cy="pId"
              :class="{ valid: !$v.matchPlayer.pId.$invalid, invalid: $v.matchPlayer.pId.$invalid }"
              v-model.number="$v.matchPlayer.pId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="match-player-eId">E Id</label>
            <input
              type="number"
              class="form-control"
              name="eId"
              id="match-player-eId"
              data-cy="eId"
              :class="{ valid: !$v.matchPlayer.eId.$invalid, invalid: $v.matchPlayer.eId.$invalid }"
              v-model.number="$v.matchPlayer.eId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="match-player-mtchEndTime">Mtch End Time</label>
            <div class="d-flex">
              <input
                id="match-player-mtchEndTime"
                data-cy="mtchEndTime"
                type="datetime-local"
                class="form-control"
                name="mtchEndTime"
                :class="{ valid: !$v.matchPlayer.mtchEndTime.$invalid, invalid: $v.matchPlayer.mtchEndTime.$invalid }"
                :value="convertDateTimeFromServer($v.matchPlayer.mtchEndTime.$model)"
                @change="updateZonedDateTimeField('mtchEndTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="match-player-score">Score</label>
            <input
              type="text"
              class="form-control"
              name="score"
              id="match-player-score"
              data-cy="score"
              :class="{ valid: !$v.matchPlayer.score.$invalid, invalid: $v.matchPlayer.score.$invalid }"
              v-model="$v.matchPlayer.score.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="match-player-winFg">Win Fg</label>
            <input
              type="text"
              class="form-control"
              name="winFg"
              id="match-player-winFg"
              data-cy="winFg"
              :class="{ valid: !$v.matchPlayer.winFg.$invalid, invalid: $v.matchPlayer.winFg.$invalid }"
              v-model="$v.matchPlayer.winFg.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="match-player-lstMtnUsr">Lst Mtn Usr</label>
            <input
              type="text"
              class="form-control"
              name="lstMtnUsr"
              id="match-player-lstMtnUsr"
              data-cy="lstMtnUsr"
              :class="{ valid: !$v.matchPlayer.lstMtnUsr.$invalid, invalid: $v.matchPlayer.lstMtnUsr.$invalid }"
              v-model="$v.matchPlayer.lstMtnUsr.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="match-player-lstMtnDt">Lst Mtn Dt</label>
            <div class="d-flex">
              <input
                id="match-player-lstMtnDt"
                data-cy="lstMtnDt"
                type="datetime-local"
                class="form-control"
                name="lstMtnDt"
                :class="{ valid: !$v.matchPlayer.lstMtnDt.$invalid, invalid: $v.matchPlayer.lstMtnDt.$invalid }"
                :value="convertDateTimeFromServer($v.matchPlayer.lstMtnDt.$model)"
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
            :disabled="$v.matchPlayer.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./match-player-update.component.ts"></script>
