<template>
  <div>
    <h2 id="page-heading" data-cy="VwWcc701ResultHeading">
      <span id="vw-wcc-701-result-heading">Vw Wcc 701 Results</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && vwWcc701Results && vwWcc701Results.length === 0">
      <span>No vwWcc701Results found</span>
    </div>
    <div class="table-responsive" v-if="vwWcc701Results && vwWcc701Results.length > 0">
      <table class="table table-striped" aria-describedby="vwWcc701Results">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('eId')">
              <span>E Id</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'eId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tId')">
              <span>T Id</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('evntNm')">
              <span>Evnt Nm</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'evntNm'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('evntDt')">
              <span>Evnt Dt</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'evntDt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('venue')">
              <span>Venue</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'venue'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('mId')">
              <span>M Id</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('mtchEndTime')">
              <span>Mtch End Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mtchEndTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('wPlyr1Id')">
              <span>W Plyr 1 Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'wPlyr1Id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('wPlyr1Lvl')">
              <span>W Plyr 1 Lvl</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'wPlyr1Lvl'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('wPlyr1Nm')">
              <span>W Plyr 1 Nm</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'wPlyr1Nm'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('wPlyr2Id')">
              <span>W Plyr 2 Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'wPlyr2Id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('wPlyr2Lvl')">
              <span>W Plyr 2 Lvl</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'wPlyr2Lvl'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('wPlyr2Nm')">
              <span>W Plyr 2 Nm</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'wPlyr2Nm'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('vs')">
              <span>Vs</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'vs'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lPlyr1Id')">
              <span>L Plyr 1 Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lPlyr1Id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lPlyr1Lvl')">
              <span>L Plyr 1 Lvl</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lPlyr1Lvl'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lPlyr1Nm')">
              <span>L Plyr 1 Nm</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lPlyr1Nm'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lPlyr2Id')">
              <span>L Plyr 2 Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lPlyr2Id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lPlyr2Lvl')">
              <span>L Plyr 2 Lvl</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lPlyr2Lvl'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lPlyr2Nm')">
              <span>L Plyr 2 Nm</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lPlyr2Nm'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('wScr')">
              <span>W Scr</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'wScr'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lScr')">
              <span>L Scr</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lScr'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="vwWcc701Result in vwWcc701Results" :key="vwWcc701Result.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'VwWcc701ResultView', params: { vwWcc701ResultId: vwWcc701Result.id } }">{{
                vwWcc701Result.id
              }}</router-link>
            </td>
            <td>{{ vwWcc701Result.eId }}</td>
            <td>{{ vwWcc701Result.tId }}</td>
            <td>{{ vwWcc701Result.evntNm }}</td>
            <td>{{ vwWcc701Result.evntDt | formatDate }}</td>
            <td>{{ vwWcc701Result.venue }}</td>
            <td>{{ vwWcc701Result.mId }}</td>
            <td>{{ vwWcc701Result.mtchEndTime | formatDate }}</td>
            <td>{{ vwWcc701Result.wPlyr1Id }}</td>
            <td>{{ vwWcc701Result.wPlyr1Lvl }}</td>
            <td>{{ vwWcc701Result.wPlyr1Nm }}</td>
            <td>{{ vwWcc701Result.wPlyr2Id }}</td>
            <td>{{ vwWcc701Result.wPlyr2Lvl }}</td>
            <td>{{ vwWcc701Result.wPlyr2Nm }}</td>
            <td>{{ vwWcc701Result.vs }}</td>
            <td>{{ vwWcc701Result.lPlyr1Id }}</td>
            <td>{{ vwWcc701Result.lPlyr1Lvl }}</td>
            <td>{{ vwWcc701Result.lPlyr1Nm }}</td>
            <td>{{ vwWcc701Result.lPlyr2Id }}</td>
            <td>{{ vwWcc701Result.lPlyr2Lvl }}</td>
            <td>{{ vwWcc701Result.lPlyr2Nm }}</td>
            <td>{{ vwWcc701Result.wScr }}</td>
            <td>{{ vwWcc701Result.lScr }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'VwWcc701ResultView', params: { vwWcc701ResultId: vwWcc701Result.id } }"
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
        ><span id="pbl230801App.vwWcc701Result.delete.question" data-cy="vwWcc701ResultDeleteDialogHeading"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-vwWcc701Result-heading">Are you sure you want to delete this Vw Wcc 701 Result?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-vwWcc701Result"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeVwWcc701Result()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="vwWcc701Results && vwWcc701Results.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./vw-wcc-701-result.component.ts"></script>
