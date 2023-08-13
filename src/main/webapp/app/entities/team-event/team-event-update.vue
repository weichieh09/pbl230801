<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="pbl230801App.teamEvent.home.createOrEditLabel" data-cy="TeamEventCreateUpdateHeading">Create or edit a TeamEvent</h2>
        <div>
          <div class="form-group" v-if="teamEvent.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="teamEvent.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="team-event-tId">T Id</label>
            <input
              type="number"
              class="form-control"
              name="tId"
              id="team-event-tId"
              data-cy="tId"
              :class="{ valid: !$v.teamEvent.tId.$invalid, invalid: $v.teamEvent.tId.$invalid }"
              v-model.number="$v.teamEvent.tId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="team-event-eId">E Id</label>
            <input
              type="number"
              class="form-control"
              name="eId"
              id="team-event-eId"
              data-cy="eId"
              :class="{ valid: !$v.teamEvent.eId.$invalid, invalid: $v.teamEvent.eId.$invalid }"
              v-model.number="$v.teamEvent.eId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="team-event-lstMtnUsr">Lst Mtn Usr</label>
            <input
              type="text"
              class="form-control"
              name="lstMtnUsr"
              id="team-event-lstMtnUsr"
              data-cy="lstMtnUsr"
              :class="{ valid: !$v.teamEvent.lstMtnUsr.$invalid, invalid: $v.teamEvent.lstMtnUsr.$invalid }"
              v-model="$v.teamEvent.lstMtnUsr.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="team-event-lstMtnDt">Lst Mtn Dt</label>
            <div class="d-flex">
              <input
                id="team-event-lstMtnDt"
                data-cy="lstMtnDt"
                type="datetime-local"
                class="form-control"
                name="lstMtnDt"
                :class="{ valid: !$v.teamEvent.lstMtnDt.$invalid, invalid: $v.teamEvent.lstMtnDt.$invalid }"
                :value="convertDateTimeFromServer($v.teamEvent.lstMtnDt.$model)"
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
            :disabled="$v.teamEvent.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./team-event-update.component.ts"></script>
