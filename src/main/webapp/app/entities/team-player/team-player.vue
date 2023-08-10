<template>
  <div>
    <h2 id="page-heading" data-cy="TeamPlayerHeading">
      <span id="team-player-heading">Team Players</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'TeamPlayerCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-team-player"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Team Player </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && teamPlayers && teamPlayers.length === 0">
      <span>No teamPlayers found</span>
    </div>
    <div class="table-responsive" v-if="teamPlayers && teamPlayers.length > 0">
      <table class="table table-striped" aria-describedby="teamPlayers">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tId')">
              <span>T Id</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('pId')">
              <span>P Id</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'pId'"></jhi-sort-indicator>
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
          <tr v-for="teamPlayer in teamPlayers" :key="teamPlayer.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TeamPlayerView', params: { teamPlayerId: teamPlayer.id } }">{{ teamPlayer.id }}</router-link>
            </td>
            <td>{{ teamPlayer.tId }}</td>
            <td>{{ teamPlayer.pId }}</td>
            <td>{{ teamPlayer.lstMtnUsr }}</td>
            <td>{{ teamPlayer.lstMtnDt | formatDate }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TeamPlayerView', params: { teamPlayerId: teamPlayer.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TeamPlayerEdit', params: { teamPlayerId: teamPlayer.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(teamPlayer)"
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
        ><span id="pbl230801App.teamPlayer.delete.question" data-cy="teamPlayerDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-teamPlayer-heading">Are you sure you want to delete this Team Player?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-teamPlayer"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeTeamPlayer()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="teamPlayers && teamPlayers.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./team-player.component.ts"></script>
