<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="pbl230801App.eventPlayer.home.createOrEditLabel" data-cy="EventPlayerCreateUpdateHeading">Create or edit a EventPlayer</h2>
        <div>
          <div class="form-group" v-if="eventPlayer.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="eventPlayer.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="event-player-eId">E Id</label>
            <input
              type="number"
              class="form-control"
              name="eId"
              id="event-player-eId"
              data-cy="eId"
              :class="{ valid: !$v.eventPlayer.eId.$invalid, invalid: $v.eventPlayer.eId.$invalid }"
              v-model.number="$v.eventPlayer.eId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="event-player-pId">P Id</label>
            <input
              type="number"
              class="form-control"
              name="pId"
              id="event-player-pId"
              data-cy="pId"
              :class="{ valid: !$v.eventPlayer.pId.$invalid, invalid: $v.eventPlayer.pId.$invalid }"
              v-model.number="$v.eventPlayer.pId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="event-player-chkFg">Chk Fg</label>
            <input
              type="text"
              class="form-control"
              name="chkFg"
              id="event-player-chkFg"
              data-cy="chkFg"
              :class="{ valid: !$v.eventPlayer.chkFg.$invalid, invalid: $v.eventPlayer.chkFg.$invalid }"
              v-model="$v.eventPlayer.chkFg.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="event-player-lstMtnUsr">Lst Mtn Usr</label>
            <input
              type="text"
              class="form-control"
              name="lstMtnUsr"
              id="event-player-lstMtnUsr"
              data-cy="lstMtnUsr"
              :class="{ valid: !$v.eventPlayer.lstMtnUsr.$invalid, invalid: $v.eventPlayer.lstMtnUsr.$invalid }"
              v-model="$v.eventPlayer.lstMtnUsr.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="event-player-lstMtnDt">Lst Mtn Dt</label>
            <div class="d-flex">
              <input
                id="event-player-lstMtnDt"
                data-cy="lstMtnDt"
                type="datetime-local"
                class="form-control"
                name="lstMtnDt"
                :class="{ valid: !$v.eventPlayer.lstMtnDt.$invalid, invalid: $v.eventPlayer.lstMtnDt.$invalid }"
                :value="convertDateTimeFromServer($v.eventPlayer.lstMtnDt.$model)"
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
            :disabled="$v.eventPlayer.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./event-player-update.component.ts"></script>
