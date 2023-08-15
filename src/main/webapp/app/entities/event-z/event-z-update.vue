<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="pbl230801App.eventZ.home.createOrEditLabel" data-cy="EventZCreateUpdateHeading">Create or edit a EventZ</h2>
        <div>
          <div class="form-group" v-if="eventZ.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="eventZ.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="event-z-evntNm">Evnt Nm</label>
            <input
              type="text"
              class="form-control"
              name="evntNm"
              id="event-z-evntNm"
              data-cy="evntNm"
              :class="{ valid: !$v.eventZ.evntNm.$invalid, invalid: $v.eventZ.evntNm.$invalid }"
              v-model="$v.eventZ.evntNm.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="event-z-evntDt">Evnt Dt</label>
            <div class="d-flex">
              <input
                id="event-z-evntDt"
                data-cy="evntDt"
                type="datetime-local"
                class="form-control"
                name="evntDt"
                :class="{ valid: !$v.eventZ.evntDt.$invalid, invalid: $v.eventZ.evntDt.$invalid }"
                :value="convertDateTimeFromServer($v.eventZ.evntDt.$model)"
                @change="updateZonedDateTimeField('evntDt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="event-z-venue">Venue</label>
            <input
              type="text"
              class="form-control"
              name="venue"
              id="event-z-venue"
              data-cy="venue"
              :class="{ valid: !$v.eventZ.venue.$invalid, invalid: $v.eventZ.venue.$invalid }"
              v-model="$v.eventZ.venue.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="event-z-eventBegTime">Event Beg Time</label>
            <div class="d-flex">
              <input
                id="event-z-eventBegTime"
                data-cy="eventBegTime"
                type="datetime-local"
                class="form-control"
                name="eventBegTime"
                :class="{ valid: !$v.eventZ.eventBegTime.$invalid, invalid: $v.eventZ.eventBegTime.$invalid }"
                :value="convertDateTimeFromServer($v.eventZ.eventBegTime.$model)"
                @change="updateZonedDateTimeField('eventBegTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="event-z-eventEndTime">Event End Time</label>
            <div class="d-flex">
              <input
                id="event-z-eventEndTime"
                data-cy="eventEndTime"
                type="datetime-local"
                class="form-control"
                name="eventEndTime"
                :class="{ valid: !$v.eventZ.eventEndTime.$invalid, invalid: $v.eventZ.eventEndTime.$invalid }"
                :value="convertDateTimeFromServer($v.eventZ.eventEndTime.$model)"
                @change="updateZonedDateTimeField('eventEndTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="event-z-lstMtnUsr">Lst Mtn Usr</label>
            <input
              type="text"
              class="form-control"
              name="lstMtnUsr"
              id="event-z-lstMtnUsr"
              data-cy="lstMtnUsr"
              :class="{ valid: !$v.eventZ.lstMtnUsr.$invalid, invalid: $v.eventZ.lstMtnUsr.$invalid }"
              v-model="$v.eventZ.lstMtnUsr.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="event-z-lstMtnDt">Lst Mtn Dt</label>
            <div class="d-flex">
              <input
                id="event-z-lstMtnDt"
                data-cy="lstMtnDt"
                type="datetime-local"
                class="form-control"
                name="lstMtnDt"
                :class="{ valid: !$v.eventZ.lstMtnDt.$invalid, invalid: $v.eventZ.lstMtnDt.$invalid }"
                :value="convertDateTimeFromServer($v.eventZ.lstMtnDt.$model)"
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
            :disabled="$v.eventZ.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./event-z-update.component.ts"></script>
