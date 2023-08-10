<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="matchZ">
        <h2 class="jh-entity-heading" data-cy="matchZDetailsHeading"><span>MatchZ</span> {{ matchZ.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>E Id</span>
          </dt>
          <dd>
            <span>{{ matchZ.eId }}</span>
          </dd>
          <dt>
            <span>Mtch End Time</span>
          </dt>
          <dd>
            <span>{{ matchZ.mtchEndTime | formatDate }}</span>
          </dd>
          <dt>
            <span>W Plyr 1</span>
          </dt>
          <dd>
            <span>{{ matchZ.wPlyr1 }}</span>
          </dd>
          <dt>
            <span>W Plyr 2</span>
          </dt>
          <dd>
            <span>{{ matchZ.wPlyr2 }}</span>
          </dd>
          <dt>
            <span>W Scr</span>
          </dt>
          <dd>
            <span>{{ matchZ.wScr }}</span>
          </dd>
          <dt>
            <span>L Plyr 1</span>
          </dt>
          <dd>
            <span>{{ matchZ.lPlyr1 }}</span>
          </dd>
          <dt>
            <span>L Plyr 2</span>
          </dt>
          <dd>
            <span>{{ matchZ.lPlyr2 }}</span>
          </dd>
          <dt>
            <span>L Scr</span>
          </dt>
          <dd>
            <span>{{ matchZ.lScr }}</span>
          </dd>
          <dt>
            <span>Lst Mtn Usr</span>
          </dt>
          <dd>
            <span>{{ matchZ.lstMtnUsr }}</span>
          </dd>
          <dt>
            <span>Lst Mtn Dt</span>
          </dt>
          <dd>
            <span>{{ matchZ.lstMtnDt | formatDate }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="matchZ.id" :to="{ name: 'MatchZEdit', params: { matchZId: matchZ.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./match-z-details.component.ts"></script>
