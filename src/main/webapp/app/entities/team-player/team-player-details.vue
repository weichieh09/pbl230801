<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="teamPlayer">
        <h2 class="jh-entity-heading" data-cy="teamPlayerDetailsHeading"><span>TeamPlayer</span> {{ teamPlayer.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>T Id</span>
          </dt>
          <dd>
            <span>{{ teamPlayer.tId }}</span>
          </dd>
          <dt>
            <span>P Id</span>
          </dt>
          <dd>
            <span>{{ teamPlayer.pId }}</span>
          </dd>
          <dt>
            <span>Lst Mtn Usr</span>
          </dt>
          <dd>
            <span>{{ teamPlayer.lstMtnUsr }}</span>
          </dd>
          <dt>
            <span>Lst Mtn Dt</span>
          </dt>
          <dd>
            <span>{{ teamPlayer.lstMtnDt | formatDate }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="teamPlayer.id"
          :to="{ name: 'TeamPlayerEdit', params: { teamPlayerId: teamPlayer.id } }"
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

<script lang="ts" src="./team-player-details.component.ts"></script>
