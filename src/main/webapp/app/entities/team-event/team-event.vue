<template>
  <div>
    <h2 id="page-heading" data-cy="TeamEventHeading">
      <span id="team-event-heading">Team Events</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'TeamEventCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-team-event"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Team Event </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && teamEvents && teamEvents.length === 0">
      <span>No teamEvents found</span>
    </div>
    <div class="table-responsive" v-if="teamEvents && teamEvents.length > 0">
      <table class="table table-striped" aria-describedby="teamEvents">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tId')">
              <span>T Id</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('eId')">
              <span>E Id</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'eId'"></jhi-sort-indicator>
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
          <tr v-for="teamEvent in teamEvents" :key="teamEvent.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TeamEventView', params: { teamEventId: teamEvent.id } }">{{ teamEvent.id }}</router-link>
            </td>
            <td>{{ teamEvent.tId }}</td>
            <td>{{ teamEvent.eId }}</td>
            <td>{{ teamEvent.lstMtnUsr }}</td>
            <td>{{ teamEvent.lstMtnDt | formatDate }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TeamEventView', params: { teamEventId: teamEvent.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TeamEventEdit', params: { teamEventId: teamEvent.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(teamEvent)"
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
        ><span id="pbl230801App.teamEvent.delete.question" data-cy="teamEventDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-teamEvent-heading">Are you sure you want to delete this Team Event?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-teamEvent"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeTeamEvent()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="teamEvents && teamEvents.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./team-event.component.ts"></script>
