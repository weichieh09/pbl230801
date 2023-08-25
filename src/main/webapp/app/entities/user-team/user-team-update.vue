<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="pbl230801App.userTeam.home.createOrEditLabel" data-cy="UserTeamCreateUpdateHeading">Create or edit a UserTeam</h2>
        <div>
          <div class="form-group" v-if="userTeam.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="userTeam.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="user-team-uId">U Id</label>
            <input
              type="number"
              class="form-control"
              name="uId"
              id="user-team-uId"
              data-cy="uId"
              :class="{ valid: !$v.userTeam.uId.$invalid, invalid: $v.userTeam.uId.$invalid }"
              v-model.number="$v.userTeam.uId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="user-team-tId">T Id</label>
            <input
              type="number"
              class="form-control"
              name="tId"
              id="user-team-tId"
              data-cy="tId"
              :class="{ valid: !$v.userTeam.tId.$invalid, invalid: $v.userTeam.tId.$invalid }"
              v-model.number="$v.userTeam.tId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="user-team-lstMtnUsr">Lst Mtn Usr</label>
            <input
              type="text"
              class="form-control"
              name="lstMtnUsr"
              id="user-team-lstMtnUsr"
              data-cy="lstMtnUsr"
              :class="{ valid: !$v.userTeam.lstMtnUsr.$invalid, invalid: $v.userTeam.lstMtnUsr.$invalid }"
              v-model="$v.userTeam.lstMtnUsr.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="user-team-lstMtnDt">Lst Mtn Dt</label>
            <div class="d-flex">
              <input
                id="user-team-lstMtnDt"
                data-cy="lstMtnDt"
                type="datetime-local"
                class="form-control"
                name="lstMtnDt"
                :class="{ valid: !$v.userTeam.lstMtnDt.$invalid, invalid: $v.userTeam.lstMtnDt.$invalid }"
                :value="convertDateTimeFromServer($v.userTeam.lstMtnDt.$model)"
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
            :disabled="$v.userTeam.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./user-team-update.component.ts"></script>
