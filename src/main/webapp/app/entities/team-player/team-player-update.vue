<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="pbl230801App.teamPlayer.home.createOrEditLabel" data-cy="TeamPlayerCreateUpdateHeading">Create or edit a TeamPlayer</h2>
        <div>
          <div class="form-group" v-if="teamPlayer.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="teamPlayer.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="team-player-tId">T Id</label>
            <input
              type="number"
              class="form-control"
              name="tId"
              id="team-player-tId"
              data-cy="tId"
              :class="{ valid: !$v.teamPlayer.tId.$invalid, invalid: $v.teamPlayer.tId.$invalid }"
              v-model.number="$v.teamPlayer.tId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="team-player-pId">P Id</label>
            <input
              type="number"
              class="form-control"
              name="pId"
              id="team-player-pId"
              data-cy="pId"
              :class="{ valid: !$v.teamPlayer.pId.$invalid, invalid: $v.teamPlayer.pId.$invalid }"
              v-model.number="$v.teamPlayer.pId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="team-player-lstMtnUsr">Lst Mtn Usr</label>
            <input
              type="text"
              class="form-control"
              name="lstMtnUsr"
              id="team-player-lstMtnUsr"
              data-cy="lstMtnUsr"
              :class="{ valid: !$v.teamPlayer.lstMtnUsr.$invalid, invalid: $v.teamPlayer.lstMtnUsr.$invalid }"
              v-model="$v.teamPlayer.lstMtnUsr.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="team-player-lstMtnDt">Lst Mtn Dt</label>
            <div class="d-flex">
              <input
                id="team-player-lstMtnDt"
                data-cy="lstMtnDt"
                type="datetime-local"
                class="form-control"
                name="lstMtnDt"
                :class="{ valid: !$v.teamPlayer.lstMtnDt.$invalid, invalid: $v.teamPlayer.lstMtnDt.$invalid }"
                :value="convertDateTimeFromServer($v.teamPlayer.lstMtnDt.$model)"
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
            :disabled="$v.teamPlayer.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./team-player-update.component.ts"></script>
