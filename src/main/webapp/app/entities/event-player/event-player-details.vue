<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="eventPlayer">
        <h2 class="jh-entity-heading" data-cy="eventPlayerDetailsHeading"><span>EventPlayer</span> {{ eventPlayer.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>E Id</span>
          </dt>
          <dd>
            <span>{{ eventPlayer.eId }}</span>
          </dd>
          <dt>
            <span>P Id</span>
          </dt>
          <dd>
            <span>{{ eventPlayer.pId }}</span>
          </dd>
          <dt>
            <span>Chk Fg</span>
          </dt>
          <dd>
            <span>{{ eventPlayer.chkFg }}</span>
          </dd>
          <dt>
            <span>Lst Mtn Usr</span>
          </dt>
          <dd>
            <span>{{ eventPlayer.lstMtnUsr }}</span>
          </dd>
          <dt>
            <span>Lst Mtn Dt</span>
          </dt>
          <dd>
            <span>{{ eventPlayer.lstMtnDt | formatDate }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="eventPlayer.id"
          :to="{ name: 'EventPlayerEdit', params: { eventPlayerId: eventPlayer.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./event-player-details.component.ts"></script>
