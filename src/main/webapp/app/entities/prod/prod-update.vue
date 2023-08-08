<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="pbl230801App.prod.home.createOrEditLabel" data-cy="ProdCreateUpdateHeading">Create or edit a Prod</h2>
        <div>
          <div class="form-group" v-if="prod.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="prod.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="prod-prodNo">Prod No</label>
            <input
              type="text"
              class="form-control"
              name="prodNo"
              id="prod-prodNo"
              data-cy="prodNo"
              :class="{ valid: !$v.prod.prodNo.$invalid, invalid: $v.prod.prodNo.$invalid }"
              v-model="$v.prod.prodNo.$model"
            />
            <div v-if="$v.prod.prodNo.$anyDirty && $v.prod.prodNo.$invalid">
              <small class="form-text text-danger" v-if="!$v.prod.prodNo.maxLength">
                This field cannot be longer than 30 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="prod-chName">Ch Name</label>
            <input
              type="text"
              class="form-control"
              name="chName"
              id="prod-chName"
              data-cy="chName"
              :class="{ valid: !$v.prod.chName.$invalid, invalid: $v.prod.chName.$invalid }"
              v-model="$v.prod.chName.$model"
            />
            <div v-if="$v.prod.chName.$anyDirty && $v.prod.chName.$invalid">
              <small class="form-text text-danger" v-if="!$v.prod.chName.maxLength">
                This field cannot be longer than 100 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="prod-issuDt">Issu Dt</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="prod-issuDt"
                  v-model="$v.prod.issuDt.$model"
                  name="issuDt"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="prod-issuDt"
                data-cy="issuDt"
                type="text"
                class="form-control"
                name="issuDt"
                :class="{ valid: !$v.prod.issuDt.$invalid, invalid: $v.prod.issuDt.$invalid }"
                v-model="$v.prod.issuDt.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="prod-expDt">Exp Dt</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="prod-expDt"
                  v-model="$v.prod.expDt.$model"
                  name="expDt"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="prod-expDt"
                data-cy="expDt"
                type="text"
                class="form-control"
                name="expDt"
                :class="{ valid: !$v.prod.expDt.$invalid, invalid: $v.prod.expDt.$invalid }"
                v-model="$v.prod.expDt.$model"
              />
            </b-input-group>
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
            :disabled="$v.prod.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./prod-update.component.ts"></script>
