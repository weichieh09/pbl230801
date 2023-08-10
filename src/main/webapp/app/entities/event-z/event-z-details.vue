<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="eventZ">
        <h2 class="jh-entity-heading" data-cy="eventZDetailsHeading"><span>EventZ</span> {{ eventZ.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Evnt Nm</span>
          </dt>
          <dd>
            <span>{{ eventZ.evntNm }}</span>
          </dd>
          <dt>
            <span>Evnt Dt</span>
          </dt>
          <dd>
            <span>{{ eventZ.evntDt | formatDate }}</span>
          </dd>
          <dt>
            <span>Venue</span>
          </dt>
          <dd>
            <span>{{ eventZ.venue }}</span>
          </dd>
          <dt>
            <span>Event Beg Time</span>
          </dt>
          <dd>
            <span>{{ eventZ.eventBegTime | formatDate }}</span>
          </dd>
          <dt>
            <span>Event End Time</span>
          </dt>
          <dd>
            <span>{{ eventZ.eventEndTime | formatDate }}</span>
          </dd>
          <dt>
            <span>Lst Mtn Usr</span>
          </dt>
          <dd>
            <span>{{ eventZ.lstMtnUsr }}</span>
          </dd>
          <dt>
            <span>Lst Mtn Dt</span>
          </dt>
          <dd>
            <span>{{ eventZ.lstMtnDt | formatDate }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="eventZ.id" :to="{ name: 'EventZEdit', params: { eventZId: eventZ.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./event-z-details.component.ts"></script>
