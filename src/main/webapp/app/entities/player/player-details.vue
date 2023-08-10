<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="player">
        <h2 class="jh-entity-heading" data-cy="playerDetailsHeading"><span>Player</span> {{ player.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Plyr Nm</span>
          </dt>
          <dd>
            <span>{{ player.plyrNm }}</span>
          </dd>
          <dt>
            <span>Plyr Lvl</span>
          </dt>
          <dd>
            <span>{{ player.plyrLvl }}</span>
          </dd>
          <dt>
            <span>Lst Mtn Usr</span>
          </dt>
          <dd>
            <span>{{ player.lstMtnUsr }}</span>
          </dd>
          <dt>
            <span>Lst Mtn Dt</span>
          </dt>
          <dd>
            <span>{{ player.lstMtnDt | formatDate }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="player.id" :to="{ name: 'PlayerEdit', params: { playerId: player.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./player-details.component.ts"></script>
