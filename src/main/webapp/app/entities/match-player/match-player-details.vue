<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="matchPlayer">
        <h2 class="jh-entity-heading" data-cy="matchPlayerDetailsHeading"><span>MatchPlayer</span> {{ matchPlayer.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>M Id</span>
          </dt>
          <dd>
            <span>{{ matchPlayer.mId }}</span>
          </dd>
          <dt>
            <span>P Id</span>
          </dt>
          <dd>
            <span>{{ matchPlayer.pId }}</span>
          </dd>
          <dt>
            <span>E Id</span>
          </dt>
          <dd>
            <span>{{ matchPlayer.eId }}</span>
          </dd>
          <dt>
            <span>Mtch End Time</span>
          </dt>
          <dd>
            <span>{{ matchPlayer.mtchEndTime | formatDate }}</span>
          </dd>
          <dt>
            <span>Score</span>
          </dt>
          <dd>
            <span>{{ matchPlayer.score }}</span>
          </dd>
          <dt>
            <span>Win Fg</span>
          </dt>
          <dd>
            <span>{{ matchPlayer.winFg }}</span>
          </dd>
          <dt>
            <span>Lst Mtn Usr</span>
          </dt>
          <dd>
            <span>{{ matchPlayer.lstMtnUsr }}</span>
          </dd>
          <dt>
            <span>Lst Mtn Dt</span>
          </dt>
          <dd>
            <span>{{ matchPlayer.lstMtnDt | formatDate }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link
          v-if="matchPlayer.id"
          :to="{ name: 'MatchPlayerEdit', params: { matchPlayerId: matchPlayer.id } }"
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

<script lang="ts" src="./match-player-details.component.ts"></script>
