<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="pbl230801App.matchZ.home.createOrEditLabel" data-cy="MatchZCreateUpdateHeading">Create or edit a MatchZ</h2>
        <div>
          <div class="form-group" v-if="matchZ.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="matchZ.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="match-z-eId">E Id</label>
            <input
              type="number"
              class="form-control"
              name="eId"
              id="match-z-eId"
              data-cy="eId"
              :class="{ valid: !$v.matchZ.eId.$invalid, invalid: $v.matchZ.eId.$invalid }"
              v-model.number="$v.matchZ.eId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="match-z-mtchEndTime">Mtch End Time</label>
            <div class="d-flex">
              <input
                id="match-z-mtchEndTime"
                data-cy="mtchEndTime"
                type="datetime-local"
                class="form-control"
                name="mtchEndTime"
                :class="{ valid: !$v.matchZ.mtchEndTime.$invalid, invalid: $v.matchZ.mtchEndTime.$invalid }"
                :value="convertDateTimeFromServer($v.matchZ.mtchEndTime.$model)"
                @change="updateZonedDateTimeField('mtchEndTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="match-z-wPlyr1">W Plyr 1</label>
            <input
              type="text"
              class="form-control"
              name="wPlyr1"
              id="match-z-wPlyr1"
              data-cy="wPlyr1"
              :class="{ valid: !$v.matchZ.wPlyr1.$invalid, invalid: $v.matchZ.wPlyr1.$invalid }"
              v-model="$v.matchZ.wPlyr1.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="match-z-wPlyr2">W Plyr 2</label>
            <input
              type="text"
              class="form-control"
              name="wPlyr2"
              id="match-z-wPlyr2"
              data-cy="wPlyr2"
              :class="{ valid: !$v.matchZ.wPlyr2.$invalid, invalid: $v.matchZ.wPlyr2.$invalid }"
              v-model="$v.matchZ.wPlyr2.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="match-z-wScr">W Scr</label>
            <input
              type="text"
              class="form-control"
              name="wScr"
              id="match-z-wScr"
              data-cy="wScr"
              :class="{ valid: !$v.matchZ.wScr.$invalid, invalid: $v.matchZ.wScr.$invalid }"
              v-model="$v.matchZ.wScr.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="match-z-lPlyr1">L Plyr 1</label>
            <input
              type="text"
              class="form-control"
              name="lPlyr1"
              id="match-z-lPlyr1"
              data-cy="lPlyr1"
              :class="{ valid: !$v.matchZ.lPlyr1.$invalid, invalid: $v.matchZ.lPlyr1.$invalid }"
              v-model="$v.matchZ.lPlyr1.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="match-z-lPlyr2">L Plyr 2</label>
            <input
              type="text"
              class="form-control"
              name="lPlyr2"
              id="match-z-lPlyr2"
              data-cy="lPlyr2"
              :class="{ valid: !$v.matchZ.lPlyr2.$invalid, invalid: $v.matchZ.lPlyr2.$invalid }"
              v-model="$v.matchZ.lPlyr2.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="match-z-lScr">L Scr</label>
            <input
              type="text"
              class="form-control"
              name="lScr"
              id="match-z-lScr"
              data-cy="lScr"
              :class="{ valid: !$v.matchZ.lScr.$invalid, invalid: $v.matchZ.lScr.$invalid }"
              v-model="$v.matchZ.lScr.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="match-z-lstMtnUsr">Lst Mtn Usr</label>
            <input
              type="text"
              class="form-control"
              name="lstMtnUsr"
              id="match-z-lstMtnUsr"
              data-cy="lstMtnUsr"
              :class="{ valid: !$v.matchZ.lstMtnUsr.$invalid, invalid: $v.matchZ.lstMtnUsr.$invalid }"
              v-model="$v.matchZ.lstMtnUsr.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="match-z-lstMtnDt">Lst Mtn Dt</label>
            <div class="d-flex">
              <input
                id="match-z-lstMtnDt"
                data-cy="lstMtnDt"
                type="datetime-local"
                class="form-control"
                name="lstMtnDt"
                :class="{ valid: !$v.matchZ.lstMtnDt.$invalid, invalid: $v.matchZ.lstMtnDt.$invalid }"
                :value="convertDateTimeFromServer($v.matchZ.lstMtnDt.$model)"
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
            :disabled="$v.matchZ.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./match-z-update.component.ts"></script>
