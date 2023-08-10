<template>
  <div>
    <h2 id="page-heading" data-cy="EventZHeading">
      <span id="event-z-heading">Event ZS</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh List</span>
        </button>
        <router-link :to="{ name: 'EventZCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-event-z"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span> Create a new Event Z </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && eventZS && eventZS.length === 0">
      <span>No eventZS found</span>
    </div>
    <div class="table-responsive" v-if="eventZS && eventZS.length > 0">
      <table class="table table-striped" aria-describedby="eventZS">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
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
            <th scope="row" v-on:click="changeOrder('eventBegTime')">
              <span>Event Beg Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'eventBegTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('eventEndTime')">
              <span>Event End Time</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'eventEndTime'"></jhi-sort-indicator>
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
          <tr v-for="eventZ in eventZS" :key="eventZ.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EventZView', params: { eventZId: eventZ.id } }">{{ eventZ.id }}</router-link>
            </td>
            <td>{{ eventZ.evntNm }}</td>
            <td>{{ eventZ.evntDt | formatDate }}</td>
            <td>{{ eventZ.venue }}</td>
            <td>{{ eventZ.eventBegTime | formatDate }}</td>
            <td>{{ eventZ.eventEndTime | formatDate }}</td>
            <td>{{ eventZ.lstMtnUsr }}</td>
            <td>{{ eventZ.lstMtnDt | formatDate }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'EventZView', params: { eventZId: eventZ.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'EventZEdit', params: { eventZId: eventZ.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(eventZ)"
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
        ><span id="pbl230801App.eventZ.delete.question" data-cy="eventZDeleteDialogHeading">Confirm delete operation</span></span
      >
      <div class="modal-body">
        <p id="jhi-delete-eventZ-heading">Are you sure you want to delete this Event Z?</p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-eventZ"
          data-cy="entityConfirmDeleteButton"
          v-on:click="removeEventZ()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="eventZS && eventZS.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./event-z.component.ts"></script>
