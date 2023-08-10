<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="pbl230801App.vwEventResult.home.createOrEditLabel" data-cy="VwEventResultCreateUpdateHeading">
          Create or edit a VwEventResult
        </h2>
        <div>
          <div class="form-group" v-if="vwEventResult.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="vwEventResult.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="vw-event-result-eId">E Id</label>
            <input
              type="number"
              class="form-control"
              name="eId"
              id="vw-event-result-eId"
              data-cy="eId"
              :class="{ valid: !$v.vwEventResult.eId.$invalid, invalid: $v.vwEventResult.eId.$invalid }"
              v-model.number="$v.vwEventResult.eId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="vw-event-result-pId">P Id</label>
            <input
              type="number"
              class="form-control"
              name="pId"
              id="vw-event-result-pId"
              data-cy="pId"
              :class="{ valid: !$v.vwEventResult.pId.$invalid, invalid: $v.vwEventResult.pId.$invalid }"
              v-model.number="$v.vwEventResult.pId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="vw-event-result-mId">M Id</label>
            <input
              type="number"
              class="form-control"
              name="mId"
              id="vw-event-result-mId"
              data-cy="mId"
              :class="{ valid: !$v.vwEventResult.mId.$invalid, invalid: $v.vwEventResult.mId.$invalid }"
              v-model.number="$v.vwEventResult.mId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="vw-event-result-winFg">Win Fg</label>
            <input
              type="text"
              class="form-control"
              name="winFg"
              id="vw-event-result-winFg"
              data-cy="winFg"
              :class="{ valid: !$v.vwEventResult.winFg.$invalid, invalid: $v.vwEventResult.winFg.$invalid }"
              v-model="$v.vwEventResult.winFg.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="vw-event-result-plyrNm">Plyr Nm</label>
            <input
              type="text"
              class="form-control"
              name="plyrNm"
              id="vw-event-result-plyrNm"
              data-cy="plyrNm"
              :class="{ valid: !$v.vwEventResult.plyrNm.$invalid, invalid: $v.vwEventResult.plyrNm.$invalid }"
              v-model="$v.vwEventResult.plyrNm.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="vw-event-result-plyrLvl">Plyr Lvl</label>
            <input
              type="text"
              class="form-control"
              name="plyrLvl"
              id="vw-event-result-plyrLvl"
              data-cy="plyrLvl"
              :class="{ valid: !$v.vwEventResult.plyrLvl.$invalid, invalid: $v.vwEventResult.plyrLvl.$invalid }"
              v-model="$v.vwEventResult.plyrLvl.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="vw-event-result-mtchEndTime">Mtch End Time</label>
            <div class="d-flex">
              <input
                id="vw-event-result-mtchEndTime"
                data-cy="mtchEndTime"
                type="datetime-local"
                class="form-control"
                name="mtchEndTime"
                :class="{ valid: !$v.vwEventResult.mtchEndTime.$invalid, invalid: $v.vwEventResult.mtchEndTime.$invalid }"
                :value="convertDateTimeFromServer($v.vwEventResult.mtchEndTime.$model)"
                @change="updateZonedDateTimeField('mtchEndTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="vw-event-result-totMatchs">Tot Matchs</label>
            <input
              type="text"
              class="form-control"
              name="totMatchs"
              id="vw-event-result-totMatchs"
              data-cy="totMatchs"
              :class="{ valid: !$v.vwEventResult.totMatchs.$invalid, invalid: $v.vwEventResult.totMatchs.$invalid }"
              v-model="$v.vwEventResult.totMatchs.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="vw-event-result-totWins">Tot Wins</label>
            <input
              type="text"
              class="form-control"
              name="totWins"
              id="vw-event-result-totWins"
              data-cy="totWins"
              :class="{ valid: !$v.vwEventResult.totWins.$invalid, invalid: $v.vwEventResult.totWins.$invalid }"
              v-model="$v.vwEventResult.totWins.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="vw-event-result-acmlWins">Acml Wins</label>
            <input
              type="text"
              class="form-control"
              name="acmlWins"
              id="vw-event-result-acmlWins"
              data-cy="acmlWins"
              :class="{ valid: !$v.vwEventResult.acmlWins.$invalid, invalid: $v.vwEventResult.acmlWins.$invalid }"
              v-model="$v.vwEventResult.acmlWins.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="vw-event-result-chkFg">Chk Fg</label>
            <input
              type="text"
              class="form-control"
              name="chkFg"
              id="vw-event-result-chkFg"
              data-cy="chkFg"
              :class="{ valid: !$v.vwEventResult.chkFg.$invalid, invalid: $v.vwEventResult.chkFg.$invalid }"
              v-model="$v.vwEventResult.chkFg.$model"
            />
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
            :disabled="$v.vwEventResult.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./vw-event-result-update.component.ts"></script>
