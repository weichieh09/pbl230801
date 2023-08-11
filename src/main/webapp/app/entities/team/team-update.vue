<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="pbl230801App.team.home.createOrEditLabel" data-cy="TeamCreateUpdateHeading">新增球隊</h2>
        <div>
          <div class="form-group" v-if="team.id">
            <label for="id">序號</label>
            <input type="text" class="form-control" id="id" name="id" v-model="team.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="team-teamNm">名稱</label>
            <input
              type="text"
              class="form-control"
              name="teamNm"
              id="team-teamNm"
              data-cy="teamNm"
              :class="{ valid: !$v.team.teamNm.$invalid, invalid: $v.team.teamNm.$invalid }"
              v-model="$v.team.teamNm.$model"
            />
          </div>
          <!-- <div class="form-group">
            <label class="form-control-label" for="team-lstMtnUsr">Lst Mtn Usr</label>
            <input
              type="text"
              class="form-control"
              name="lstMtnUsr"
              id="team-lstMtnUsr"
              data-cy="lstMtnUsr"
              :class="{ valid: !$v.team.lstMtnUsr.$invalid, invalid: $v.team.lstMtnUsr.$invalid }"
              v-model="$v.team.lstMtnUsr.$model"
            />
          </div> -->
          <!-- <div class="form-group">
            <label class="form-control-label" for="team-lstMtnDt">Lst Mtn Dt</label>
            <div class="d-flex">
              <input
                id="team-lstMtnDt"
                data-cy="lstMtnDt"
                type="datetime-local"
                class="form-control"
                name="lstMtnDt"
                :class="{ valid: !$v.team.lstMtnDt.$invalid, invalid: $v.team.lstMtnDt.$invalid }"
                :value="convertDateTimeFromServer($v.team.lstMtnDt.$model)"
                @change="updateZonedDateTimeField('lstMtnDt', $event)"
              />
            </div>
          </div> -->
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>取消</span>
          </button>
          <!-- <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.team.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button> -->
          <button type="submit" id="save-entity" data-cy="entityCreateSaveButton" :disabled="true" class="btn btn-primary">
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>保存</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./team-update.component.ts"></script>
