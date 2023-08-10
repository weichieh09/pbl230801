<template>
  <div>
    <h2 id="page-heading" data-cy="MatchZHeading">
      <span id="match-z-heading">Match ZS</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'MatchZCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-match-z"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Match Z </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && matchZS && matchZS.length === 0">
      <span>No matchZS found</span>
    </div>
    <div class="table-responsive" v-if="matchZS && matchZS.length > 0">
      <table class="table table-striped" aria-describedby="matchZS">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('eId')">
              <span>E Id</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'eId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('mtchEndTime')">
              <span>Mtch End Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mtchEndTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('wPlyr1')">
              <span>W Plyr 1</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'wPlyr1'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('wPlyr2')">
              <span>W Plyr 2</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'wPlyr2'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('wScr')">
              <span>W Scr</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'wScr'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lPlyr1')">
              <span>L Plyr 1</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lPlyr1'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lPlyr2')">
              <span>L Plyr 2</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lPlyr2'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lScr')">
              <span>L Scr</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lScr'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lstMtnUsr')">
              <span>Lst Mtn Usr</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lstMtnUsr'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lstMtnDt')">
              <span>Lst Mtn Dt</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lstMtnDt'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="matchZ in matchZS" :key="matchZ.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MatchZView', params: { matchZId: matchZ.id } }">{{ matchZ.id }}</router-link>
            </td>
            <td>{{ matchZ.eId }}</td>
            <td>{{ matchZ.mtchEndTime | formatDate }}</td>
            <td>{{ matchZ.wPlyr1 }}</td>
            <td>{{ matchZ.wPlyr2 }}</td>
            <td>{{ matchZ.wScr }}</td>
            <td>{{ matchZ.lPlyr1 }}</td>
            <td>{{ matchZ.lPlyr2 }}</td>
            <td>{{ matchZ.lScr }}</td>
            <td>{{ matchZ.lstMtnUsr }}</td>
            <td>{{ matchZ.lstMtnDt | formatDate }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'MatchZView', params: { matchZId: matchZ.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'MatchZEdit', params: { matchZId: matchZ.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(matchZ)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="pbl230801App.matchZ.delete.question" data-cy="matchZDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-matchZ-heading">Are you sure you want to delete this Match Z?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-matchZ"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeMatchZ()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="matchZS && matchZS.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./match-z.component.ts"></script>
