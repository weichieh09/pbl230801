<template>
  <div>
    <h2 id="page-heading" data-cy="VwEventResultHeading">
      <span id="vw-event-result-heading">Vw Event Results</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && vwEventResults && vwEventResults.length === 0">
      <span>No vwEventResults found</span>
    </div>
    <div class="table-responsive" v-if="vwEventResults && vwEventResults.length > 0">
      <table class="table table-striped" aria-describedby="vwEventResults">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('eId')">
              <span>E Id</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'eId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('pId')">
              <span>P Id</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('mId')">
              <span>M Id</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('winFg')">
              <span>Win Fg</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'winFg'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('plyrNm')">
              <span>Plyr Nm</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'plyrNm'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('plyrLvl')">
              <span>Plyr Lvl</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'plyrLvl'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('mtchEndTime')">
              <span>Mtch End Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mtchEndTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('totMatchs')">
              <span>Tot Matchs</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'totMatchs'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('totWins')">
              <span>Tot Wins</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'totWins'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('acmlWins')">
              <span>Acml Wins</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'acmlWins'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('chkFg')">
              <span>Chk Fg</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'chkFg'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="vwEventResult in vwEventResults" :key="vwEventResult.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'VwEventResultView', params: { vwEventResultId: vwEventResult.id } }">{{
                vwEventResult.id
              }}</router-link>
            </td>
            <td>{{ vwEventResult.eId }}</td>
            <td>{{ vwEventResult.pId }}</td>
            <td>{{ vwEventResult.mId }}</td>
            <td>{{ vwEventResult.winFg }}</td>
            <td>{{ vwEventResult.plyrNm }}</td>
            <td>{{ vwEventResult.plyrLvl }}</td>
            <td>{{ vwEventResult.mtchEndTime | formatDate }}</td>
            <td>{{ vwEventResult.totMatchs }}</td>
            <td>{{ vwEventResult.totWins }}</td>
            <td>{{ vwEventResult.acmlWins }}</td>
            <td>{{ vwEventResult.chkFg }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'VwEventResultView', params: { vwEventResultId: vwEventResult.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="pbl230801App.vwEventResult.delete.question" data-cy="vwEventResultDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-vwEventResult-heading">Are you sure you want to delete this Vw Event Result?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-vwEventResult"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeVwEventResult()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="vwEventResults && vwEventResults.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./vw-event-result.component.ts"></script>
